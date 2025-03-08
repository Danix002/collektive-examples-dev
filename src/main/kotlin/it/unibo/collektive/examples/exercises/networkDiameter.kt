package it.unibo.collektive.examples.exercises

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.alchemist.device.sensors.EnvironmentVariables
import it.unibo.alchemist.collektive.device.DistanceSensor
import it.unibo.collektive.examples.exercises.calculateDistance
import it.unibo.collektive.examples.channel.broadcast
import it.unibo.collektive.field.operations.maxBy
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.field.operations.maxWithSelf
import it.unibo.collektive.field.operations.minBy

/**
 * 3) Calculate in the source an estimate of the true diameter of the network (the maximum distance of a device in the network).
 * 4) Broadcast the diameter to every node in the network.
*/

data class DistanceFrom(val from: Int, val distance: Int)

fun Aggregate<Int>.networkDiameter(environment: EnvironmentVariables, distanceSensor: DistanceSensor) = calculateDiameter(environment, distanceSensor) 

fun Aggregate<Int>.calculateDiameter(environment: EnvironmentVariables, distanceSensor: DistanceSensor): Int {
    // Individuate source and calculate distance from the previous exercises
    calculateDistance(environment)
    val distance: Int = environment["distanceToSource"]

    val distances = neighboring(DistanceFrom(localId, distance))
    
    // Use the map to calculate the sum of the distances for each pair of neighboring nodes
    var maxHop = distances.map { (from, otherDistance) ->
        // Adds the distance of the current neighbor to that of the other neighbors
        if (from != localId) {
            otherDistance + distance
        } else {
            // Don't add the distance to yourself
            0 
        }
    }.max(distance)

    // Propagate result in the network
    val maxLocalDistance = neighboring(maxHop).max(maxHop)
    val maxNetworkDistance = neighboring(maxLocalDistance).max(maxLocalDistance)
    environment["maxHop"] = maxNetworkDistance

    // Identifies the node with the maximum number of hops corresponding to the diameter of the entire network
    val flagNodeWithMaxHop = maxNetworkDistance == maxHop

    // Only the identified node will broadcast its maximum number of hops to the entire network.
    var broadcastMessage = broadcast(distanceSensor, from = flagNodeWithMaxHop, payload = environment["maxHop"]).toInt()

    if(maxHop <= broadcastMessage){
        environment["networkDiameter"] = broadcastMessage
    }else{
        environment["networkDiameter"] = maxHop
    }

    environment["broadcastMessagePayload"] = broadcastMessage

    return localId
}
