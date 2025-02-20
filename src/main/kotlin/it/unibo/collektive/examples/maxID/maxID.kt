package it.unibo.collektive.examples.maxID

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.alchemist.device.sensors.EnvironmentVariables
import it.unibo.collektive.examples.diameter.diameter

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

    environment["diameter"] = diameter((environment["isMaxID"] || environment["isMaxLocalID"]))

    return maxValue
}
