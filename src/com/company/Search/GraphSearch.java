package com.company.Search;


import java.util.LinkedHashSet;
import java.util.Set;

public class GraphSearch implements Search {
    private Frontier frontier;
    private Set<Node> explored;
    private int generatedNodes;

    public GraphSearch(Frontier _frontier) {
        frontier = _frontier;
        explored = new LinkedHashSet<>();
        generatedNodes = 0;
    }

    public Node getSolution(Node root, GoalTest test, NodeFunction function) {
        frontier.addToFrontier(root);
        while (!frontier.isEmpty()) {
            Node leaf = frontier.removeFromFrontier();
            if (test.isGoal(leaf.state) || generatedNodes >= 200) {
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
        }
        return null;
    }

    public int getGeneratedNodes() {
        return generatedNodes;
    }
}
