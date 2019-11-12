package com.company.Search;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstFrontier implements Frontier {
    public BestFirstFrontier(NodeFunction func) {
        frontier = new PriorityQueue<Node>(Comparator.comparingDouble(node -> node.value));
        function = func;
    }

    public BestFirstFrontier(NodeFunction func, PriorityQueue<Node> _frontier) {
        frontier = _frontier;
        function = func;
    }

    private Queue<Node> frontier;
    private NodeFunction function;

    @Override
    public void addToFrontier(Node n) {
        n.value = this.function.produce(n);
        frontier.add(n);
    }

    @Override
    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    @Override
    public boolean contains(Node n) {
        return frontier.contains(n);
    }

    @Override
    public Node removeFromFrontier() {
        return frontier.remove();
    }
}
