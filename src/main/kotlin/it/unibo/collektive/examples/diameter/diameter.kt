package it.unibo.collektive.examples.diameter

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.operators.share
import it.unibo.collektive.stdlib.*
import kotlin.Int.Companion.MIN_VALUE

/**
 * Third example - Tutorial:
 * 1. Determine the diameter of the three subnetworks corresponding to the nodes with the maximum values ​​(one global and two local) of the last exercise.
 * 2. The nodes that are the farthest, in terms of hop count, from the maximum-value nodes (which serve as the center of the connected subnetwork) must be colored with different colors.
*/

fun Aggregate<Int>.diameter(source: Boolean): Int {
    return share(if (source) 0 else MIN_VALUE) { previous ->
        val distance = hopDistanceTo(source)
        maxOf(previous, distance)
    }
    
}

