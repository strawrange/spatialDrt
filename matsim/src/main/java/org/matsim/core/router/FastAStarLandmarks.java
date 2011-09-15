/* *********************************************************************** *
 * project: org.matsim.*
 * FastAStarLandmarks.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2011 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.core.router;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.AStarNodeData;
import org.matsim.core.router.util.AStarNodeDataFactory;
import org.matsim.core.router.util.RoutingNetworkFactory;
import org.matsim.core.router.util.PreProcessLandmarks;
import org.matsim.core.router.util.RoutingNetwork;
import org.matsim.core.router.util.RoutingNetworkNode;
import org.matsim.core.router.util.TravelCost;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.utils.collections.PseudoRemovePriorityQueue;

/**
 * <p>
 * Performance optimized version of the Dijkstra {@link org.matsim.core.router.FastAStarLandmarks} 
 * least cost path router which uses its own network to route within. Note that 
 * this network requires additional memory for each FastDijkstra instance.
 * </p>
 * 
 * @see org.matsim.core.router.FastAStarLandmarks
 * @see org.matsim.core.router.util.RoutingNetwork
 * @author cdobler
 */
public class FastAStarLandmarks extends AStarLandmarks {

	private RoutingNetwork routingNetwork;
	private FastRouterDelegate fastRouter;

	public FastAStarLandmarks(final Network network, final PreProcessLandmarks preProcessData,
			final TravelCost costFunction, final TravelTime timeFunction) {
		this(network, preProcessData, costFunction, timeFunction, 1);
	}

	public FastAStarLandmarks(final Network network, final PreProcessLandmarks preProcessData,
			final TravelTime timeFunction) {
		this(network, preProcessData, preProcessData.getCostFunction(), timeFunction, 1);
	}

	public FastAStarLandmarks(final Network network, final PreProcessLandmarks preProcessData,
			final TravelCost costFunction, final TravelTime timeFunction, final double overdoFactor) {
		super(network, preProcessData, costFunction, timeFunction, overdoFactor);

		this.routingNetwork = new RoutingNetworkFactory().createDijkstraNetwork(network);
		this.nodeData.clear();

		this.fastRouter = new FastRouterDelegate(this, this.routingNetwork, new AStarNodeDataFactory());
		this.fastRouter.prepareRoutingNetwork(preProcessData);
	}

	/*
	 * Replace the references to the from and to nodes with their corresponding
	 * nodes in the routing network.
	 */
	@Override
	public Path calcLeastCostPath(final Node fromNode, final Node toNode, final double startTime) {
		RoutingNetworkNode routingNetworkFromNode = routingNetwork.getNodes().get(fromNode.getId());
		RoutingNetworkNode routingNetworkToNode = routingNetwork.getNodes().get(toNode.getId());

		return super.calcLeastCostPath(routingNetworkFromNode, routingNetworkToNode, startTime);
	}
	
	/*
	 * Constructs the path and replaces the nodes and links from the routing network
	 * with their corresponding nodes and links from the network.
	 */
	@Override
	protected Path constructPath(Node fromNode, Node toNode, double startTime, double arrivalTime) {
		return fastRouter.constructPath(fromNode, toNode, startTime, arrivalTime);
	}
	
	/*
	 * For performance reasons the outgoing links of a node are stored in
	 * the routing network in an array instead of a map. Therefore we have
	 * to iterate over an array instead of over a map. 
	 */
	@Override
	protected void relaxNode(final Node outNode, final Node toNode, final PseudoRemovePriorityQueue<Node> pendingNodes) {
		fastRouter.relaxNode(outNode, toNode, pendingNodes);
	}
	
	/*
	 * The DijkstraNodeData is taken from the RoutingNetworkNode and not from a map.
	 */
	@Override
	protected AStarNodeData getData(final Node n) {
		return (AStarNodeData) fastRouter.getData(n);
	}

	/*
	 * The LandmarksData is taken from the RoutingNetworkNode and not from a map.
	 */
	@Override
	protected PreProcessLandmarks.LandmarksData getPreProcessData(final Node n) {
		return (PreProcessLandmarks.LandmarksData) fastRouter.getPreProcessData(n);
	}
}
