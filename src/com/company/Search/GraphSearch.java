package com.company.Search;


import java.util.*;

public class GraphSearch implements Search {
    private Frontier frontier;
    private Set<Node> explored;
    private int generatedNodes;

    public GraphSearch(Frontier _frontier) {
        frontier = _frontier;
        explored = new LinkedHashSet<>();
        generatedNodes = 0;
    }

    public Node getSolution(Vector<Node> roots, GoalTest test, NodeFunction function) {
        Node root = null;
        var cost = Double.MAX_VALUE;
        for (Node enemy : roots) {
            var newCost = function.produce(enemy);
            if (newCost < cost) {
                root = enemy;
                cost = newCost;
            }
        }
        if (root != null) {
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;
            frontier.addToFrontier(root);
            while (!frontier.isEmpty()) {
                Node leaf = frontier.removeFromFrontier();
                if (test.isGoal(leaf.state) || elapsedTime >= 5000) {
                    System.out.println(generatedNodes + " nodes generated");
                    return leaf;
                }
                explored.add(leaf);
                for (Action action : leaf.state.getApplicableActions(leaf.action)) {
                    State newState = leaf.state.getActionResult(action, leaf.action);
                    Node nodeToAdd = new Node(leaf, action, newState, 0, 0);
                    if (!explored.contains(nodeToAdd) && !frontier.contains(nodeToAdd)) {
                        frontier.addToFrontier(nodeToAdd);
                        generatedNodes++;
                    }
                }
                elapsedTime = (new Date()).getTime() - startTime;
            }
        }
        return null;
    }

    public int getGeneratedNodes() {
        return generatedNodes;
    }
}
