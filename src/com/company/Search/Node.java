package com.company.Search;

import java.util.Objects;

public class Node {
	public final Node parent;
	public final Action action;
	public final State state;
	public final int depth;
	public final int cost;
	public double value;

	public Node(Node parent, Action action, State state, int depth, double value) {
		this.parent = parent;
		this.action = action;
		this.state = state;
		this.depth = depth;
		this.value = value;
		cost = this.parent != null ? this.parent.cost + this.action.cost() : 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return getDepth() == node.getDepth() &&
				cost == node.cost &&
				Double.compare(node.value, value) == 0 &&
				Objects.equals(parent, node.parent) &&
				Objects.equals(action, node.action) &&
				Objects.equals(state, node.state);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parent, action, state, getDepth(), cost, value);
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public String toString() {
		return "Node{" +
				"depth=" + depth +
				", cost=" + cost +
				", value=" + value +
				'}';
	}
}
