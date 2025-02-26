package it.unibo.collektive.examples.diameter

import it.unibo.collektive.aggregate.api.Aggregate
import it.unibo.collektive.aggregate.api.Aggregate.Companion.neighboring
import it.unibo.collektive.aggregate.api.operators.share
import it.unibo.collektive.field.operations.max
import it.unibo.collektive.field.operations.maxBy

/**
 * Third example - Tutorial:
 * 1. Determine the diameter of the subnetworks corresponding to the nodes with the maximum global values of the last exercise.
 * 2. The nodes that are the farthest, in terms of hop count, from the maximum-value nodes (which serve as the center of the connected subnetwork) must be colored with different colors.
*/

data class SourceDistance(val sourceID: Int, val distance: Int)

fun Aggregate<Int>.diameter(sourceID: Int, distanceToSource: Int): SourceDistance {
    val distances = neighboring(SourceDistance(sourceID, distanceToSource))
    return distances.maxBy(SourceDistance(sourceID, distanceToSource)){ if(sourceID == it.sourceID) it.distance else Int.MIN_VALUE }
}



