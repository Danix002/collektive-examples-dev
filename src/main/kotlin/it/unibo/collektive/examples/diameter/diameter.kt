package it.unibo.collektive.examples.diameter

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.aggregate.api.operators.share
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.stdlib.spreading.hopDistanceTo
import it.unibo.collektive.stdlib.spreading.distanceTo

/**
 * Third example - Tutorial:
 * 1. Determine the diameter of the subnetworks corresponding to the nodes with the maximum global values of the last exercise.
 * 2. The nodes that are the farthest, in terms of hop count, from the maximum-value nodes (which serve as the center of the connected subnetwork) must be colored with different colors.
*/

fun Aggregate<Int>.diameter(source: Boolean, sourceID: Int): Int {
    // TODO: isolate subnetworks
    return share(0) { previous ->
        val closestDistance = myHopDistanceTo(source)
        previous.max(closestDistance)
    }
}

fun <ID : Any> Aggregate<ID>.myHopDistanceTo(source: Boolean): Int = 
    distanceTo(
        source,                        
        0,                             
        Int.MAX_VALUE / 2,             
        { a: Int, b: Int ->            
            if (a == Int.MAX_VALUE / 2 || b == Int.MAX_VALUE / 2) Int.MAX_VALUE / 2
            else (a + b).coerceAtMost(Int.MAX_VALUE / 2) 
        }
    ) {
        neighboring(1)                 
    }

