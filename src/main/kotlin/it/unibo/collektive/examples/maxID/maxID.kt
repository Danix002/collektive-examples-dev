package it.unibo.collektive.examples.maxID

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.operators.neighboringViaExchange
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.alchemist.device.sensors.EnvironmentVariables

/**
 * 1. Identify the maximum value among the neighboring nodes.
 * 2. Assign a distinct color to the nodes with the identified maximum values.
*/

fun Aggregate<Int>.maxID(environment: EnvironmentVariables) =
    when (environment.get<Boolean>("isMaxID")) {
        true -> searchMaxNeighborIDValue(environment)
        false -> environment.get<Int>("maxNeighborID")
    }

fun Aggregate<Int>.searchMaxNeighborIDValue(environment: EnvironmentVariables): Int {
    // Step 1: Exchange the localId with neighbors and obtain a field of values
    val neighborValues = neighboringViaExchange(local = localId)

    // Step 2: Find the maximum value among neighbors (including self)
    val maxValue = neighborValues.max(base = localId)

    // Step 3: Assign the result to a molecule
    environment.set<Boolean>("isMaxID", localId == maxValue)
    environment.set<Int>("localID", localId)
    environment.set<Int>("maxNeighborID", maxValue)

    return maxValue
}
