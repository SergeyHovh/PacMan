package com.company.Search;

import com.company.Helpers.Cell;
import com.company.Scene;

import java.util.Objects;

public class Node {
	public final Node parent;
	public final Cell action;
	final Scene state;
	final int cost;
	double value;

	public Node(Node parent, Action action, State state, int depth, double value) {
		this.parent = parent;
		this.action = (Cell)action;
		this.state = (Scene)state;
		this.value = value;
		cost = this.parent != null ? this.parent.cost + this.action.cost() : 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		var a = this.state.equals(node.state);
		var b = this.action.equals(node.action); // TODO wtf
		return a && b;
	}

	@Override
	public int hashCode() {
		return Objects.hash(parent, action, state, cost, value);
	}
}
