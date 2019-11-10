package com.company.Search;

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

	public boolean equals(Object obj) {
		if (getClass() != obj.getClass()) return false;
		Node that = (Node)obj;
		return that.state.equals(this.state);
	}

	public int hashCode(){
		return (parent != null ? parent.state.hashCode() : 0) + state.hashCode();
	}
	public int getDepth() {
		return depth;
	}
}
