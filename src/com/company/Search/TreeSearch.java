package com.company.Search;

import java.util.Vector;

public class TreeSearch implements Search {
    private Frontier frontier;
    private int generatedNodes;

    public TreeSearch(Frontier _frontier) {
        frontier = _frontier;
        generatedNodes = 0;
    }

    public Node getSolution(Vector<Node> roots, GoalTest test) {
        for (Node root : roots) {
            frontier.addToFrontier(root);
            while (!frontier.isEmpty()) {
                Node leaf = frontier.removeFromFrontier();
                if (test.isGoal(leaf.state)) {
                    return leaf;
                } else {
                    for (var action : leaf.state.getApplicableActions().get(root.action)) {
                        State newState = leaf.state.getActionResult(action);
                        frontier.addToFrontier(new Node(leaf, action, newState, 0, 0));
                        generatedNodes++;
                    }
                }
            }
        }
        return null;
    }

    public int getGeneratedNodes() {
        return generatedNodes;
    }
}
