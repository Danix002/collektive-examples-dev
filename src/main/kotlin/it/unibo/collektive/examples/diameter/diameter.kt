package it.unibo.collektive.examples.diameter

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.operators.share
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.stdlib.spreading.hopDistanceTo
import it.unibo.collektive.stdlib.ints.FieldedInts.plus
import it.unibo.collektive.field.operations.maxBy
import it.unibo.collektive.field.operations.max
import kotlin.Int.Companion.MIN_VALUE

/**
 * Third example - Tutorial:
 * 1. Determine the diameter of the three subnetworks corresponding to the nodes with the maximum values ​​(one global and two local) of the last exercise.
 * 2. The nodes that are the farthest, in terms of hop count, from the maximum-value nodes (which serve as the center of the connected subnetwork) must be colored with different colors.
*/

fun Aggregate<Int>.diameter(source: Boolean): Int {
    return share(Int.MIN_VALUE) { previous ->
        val closestDistance = hopDistanceTo(source) 
        previous.max(closestDistance)
    }
}

