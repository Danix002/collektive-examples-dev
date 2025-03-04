/**
 * COMBINING SPATIAL COMPUTING BLOCKS:
 * 
 * 1) Select a node called "source", chosen by finding the node with minimum uid 
 * in the network, assuming that the diameter of the network is no more than 10 hops.
 * 
 * 2) Compute the distances between any node and the "source" using the adaptive bellman-ford algorithm.
 * 
 * 3) Calculate in the source an estimate of the true diameter of the network
 * (the maximum distance of a device in the network).
 * 
 * 4) Broadcast the diameter to every node in the network.
*/