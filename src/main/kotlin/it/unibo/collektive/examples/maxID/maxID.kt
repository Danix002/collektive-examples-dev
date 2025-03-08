package it.unibo.collektive.examples.maxID

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.alchemist.device.sensors.EnvironmentVariables
import it.unibo.collektive.examples.subnetDiameter.subnetDiameter
import it.unibo.collektive.stdlib.spreading.hopDistanceTo
import it.unibo.collektive.stdlib.spreading.distanceTo

/**
 * First example - Tutorial:
 * 1. Identify the maximum value among the neighboring nodes.
 * 2. Assign a distinct color to the nodes with the identified maximum values.
 * Second example - Tutorial:
 * 3. Identify the maximum value in the network.
 * 4. Assign a distinct color to the nodes with the identified maximum values.
*/

fun Aggregate<Int>.maxID(environment: EnvironmentVariables) = maxNetworkID(environment)

fun Aggregate<Int>.maxNeighborID(): Int {
    // Step 1: Exchange the localId with neighbors and obtain a field of values
    val neighborValues = neighboring(local = localId)

    // Step 2: Find the maximum value among neighbors (including self)
    val maxValue = neighborValues.max(base = localId)

    return maxValue
}

fun Aggregate<Int>.maxNetworkID(environment: EnvironmentVariables): Int {
    val maxLocalValue = maxNeighborID()

    // Step 3: Assign the result to a molecule
    environment["isMaxLocalID"] = localId == maxLocalValue
    environment["localID"] = localId
    environment["maxNeighborID"] = maxLocalValue

    // Step 1: Exchange the maxNeighborID with neighbors and obtain a field of values
    val neighborValues = neighboring(local = maxLocalValue)

    // Step 2: Find the maximum value among neighbors (including self)
    val maxValue = neighborValues.max(base = maxLocalValue)

    // Step 3: Assign the result to a molecule (only if using Alchemist)
    environment["isMaxID"] = localId == maxValue
    environment["maxNetworkID"] = maxValue

    /* Third example */

    // Preliminary step: the distance from the nearest source is calculated using the hopDistanceTo library function 
    // (see the "Tutorial > Standard Library Functions" section for a detailed description)
    
    // Note: use hopDistanceTo library function 
    environment["distanceToSource"] = myHopDistanceTo(environment["isMaxID"])

    val networkDiameter = subnetDiameter(environment["maxNetworkID"], environment["distanceToSource"])

    environment["diameter"] = networkDiameter

    val diameterDistance = networkDiameter.distance

    environment["diameterValue"] = diameterDistance
    environment["isDiameterDistance"] = diameterDistance == environment["distanceToSource"]
    environment["nothing"] =  !(environment["isDiameterDistance"] || environment["isMaxID"] || environment["isMaxLocalID"])

    return maxValue
}

fun <ID : Any> Aggregate<ID>.myHopDistanceTo(source: Boolean): Int = 
    distanceTo(
        source,                        
        0,                             
        Int.MAX_VALUE,             
        { a: Int, b: Int ->            
            if (a == Int.MAX_VALUE || b == Int.MAX_VALUE) Int.MAX_VALUE
            else (a + b).coerceAtMost(Int.MAX_VALUE) 
        }
    ) {
        neighboring(1)                 
    }