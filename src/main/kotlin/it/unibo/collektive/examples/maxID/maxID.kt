package it.unibo.collektive.examples.maxID

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.operators.neighboringViaExchange
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.alchemist.device.sensors.EnvironmentVariables

/**
 * First example - Tutorial:
 * 1. Identify the maximum value among the neighboring nodes.
 * 2. Assign a distinct color to the nodes with the identified maximum values.
 * Second example - Tutorial:
 * 3. Identify the maximum value in the network.
 * 4. Assign a distinct color to the nodes with the identified maximum values.
*/

fun Aggregate<Int>.maxID(environment: EnvironmentVariables) =
    when (environment.get<Boolean>("isMaxID")) {
        true -> searchMaxNetworkIDValue(environment)
        false -> environment.get<Int>("maxNetworkID")
    }

fun Aggregate<Int>.searchMaxNeighborIDValue(environment: EnvironmentVariables): Int {
    // Step 1: Exchange the localId with neighbors and obtain a field of values
    val neighborValues = neighboringViaExchange(local = localId)

    // Step 2: Find the maximum value among neighbors (including self)
    val maxValue = neighborValues.max(base = localId)

    // Step 3: Assign the result to a molecule
    environment.set<Boolean>("isMaxLocalID", localId == maxValue)
    environment.set<Int>("maxNeighborID", maxValue)

    return maxValue
}

fun Aggregate<Int>.searchMaxNetworkIDValue(environment: EnvironmentVariables): Int {
    environment.set<Int>("localID", localId)

    when (environment.get<Boolean>("isMaxLocalID")) {
        true -> searchMaxNeighborIDValue(environment)
        false -> environment.get<Int>("maxNeighborID")
    }

    // Step 1: Exchange the maxNeighborID with neighbors and obtain a field of values
    val neighborValues = neighboringViaExchange(local = environment.get<Int>("maxNeighborID"))

    // Step 2: Find the maximum value among neighbors (including self)
    val maxValue = neighborValues.max(base = environment.get<Int>("maxNeighborID"))

    // Step 3: Assign the result to a molecule
    environment.set<Boolean>("isMaxID", localId == maxValue)
    environment.set<Int>("maxNetworkID", maxValue)
    
    return maxValue
}
