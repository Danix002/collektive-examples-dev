package it.unibo.collektive.examples.exercises

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.alchemist.device.sensors.EnvironmentVariables
import it.unibo.collektive.examples.exercises.minNetworkID
import it.unibo.collektive.stdlib.spreading.distanceTo

/**
 * 2) Compute the distances between any node and the "source" using the adaptive bellman-ford algorithm.
*/

fun Aggregate<Int>.distanceToSource(environment: EnvironmentVariables) = calculateDistance(environment) 


fun Aggregate<Int>.calculateDistance(environment: EnvironmentVariables): Int {
    // Individuate source from the previous exercise 
    minNetworkID(environment)

    // TODO explanation comment
    environment["distanceToSource"] = distanceTo(
        environment["source"],                        
        0,                             
        Int.MAX_VALUE,             
        { a: Int, b: Int ->            
            if (a == Int.MAX_VALUE || b == Int.MAX_VALUE) Int.MAX_VALUE
            else (a + b).coerceAtMost(Int.MAX_VALUE) 
        }
    ) {
        neighboring(1)                 
    }

    return localId
}