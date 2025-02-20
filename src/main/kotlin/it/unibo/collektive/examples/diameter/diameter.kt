package it.unibo.collektive.examples.diameter

import it.unibo.collektive.stdlib.*
import it.unibo.alchemist.collektive.device.DistanceSensor

/**
 * Third example - Tutorial:
 * 1. Determine the diameter of the three subnetworks corresponding to the nodes with the maximum values ​​(one global and two local) of the last exercise.
 * 2. The nodes that are the farthest, in terms of hop count, from the maximum-value nodes (which serve as the center of the connected subnetwork) must be colored with different colors.
*/

fun Aggregate<Int>.diameter(source: Boolean): Int {
    return share(if (source) 0 else Int.MIN_VALUE) { previous ->
        val maxDistance = nodeNeighborhood().maxBy { hopDistanceTo(it) }?.let { hopDistanceTo(it) } ?: 0
        maxOf(previous, maxDistance)
    }
}

