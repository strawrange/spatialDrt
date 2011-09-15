/* *********************************************************************** *
 * project: org.matsim.*
 * RoutingNetworkLink.java
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

package org.matsim.core.router.util;

import java.util.Set;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

public class RoutingNetworkLink implements Link {

	private final Link link;
	private final RoutingNetworkNode toNode;
	private final RoutingNetworkNode fromNode;
	
	/*package*/ RoutingNetworkLink(Link link, RoutingNetworkNode fromNode, RoutingNetworkNode toNode) {
		this.link = link;
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
	
	public Link getLink() {
		return link;
	}
	
	public Id getId() {
		return link.getId();
	}

	public RoutingNetworkNode getFromNode() {
		return fromNode;
	}
	
	public RoutingNetworkNode getToNode() {
		return toNode;
	}

	@Override
	public Set<String> getAllowedModes() {
		return link.getAllowedModes();
	}

	@Override
	public double getCapacity() {
		return link.getCapacity();
	}

	@Override
	public double getCapacity(double time) {
		return link.getCapacity(time);
	}

	@Override
	public double getFreespeed() {
		return link.getFreespeed();
	}

	@Override
	public double getFreespeed(double time) {
		return link.getFreespeed(time);
	}

	@Override
	public double getLength() {
		return link.getLength();
	}

	@Override
	public double getNumberOfLanes() {
		return link.getNumberOfLanes();
	}

	@Override
	public double getNumberOfLanes(double time) {
		return link.getNumberOfLanes(time);
	}

	@Override
	public void setAllowedModes(Set<String> modes) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public void setCapacity(double capacity) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public void setFreespeed(double freespeed) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public boolean setFromNode(Node node) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public void setLength(double length) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public void setNumberOfLanes(double lanes) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public boolean setToNode(Node node) {
		throw new RuntimeException("Not supported operation!");
	}

	@Override
	public Coord getCoord() {
		return this.link.getCoord();
	}

}