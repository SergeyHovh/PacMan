package com.company.Search;

public class AStarFunction implements NodeFunction {
    private final NodeFunction heuristicFunction;
    public AStarFunction(NodeFunction heuristic) {
        this.heuristicFunction = heuristic;
    }
    @Override
    public double produce(Node n) {
        return n.cost + heuristicFunction.produce(n);
    }
}
