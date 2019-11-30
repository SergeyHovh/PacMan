package com.company.Search;

import java.util.Vector;

public interface Search {

    Node getSolution(Vector<Node> root, GoalTest test, NodeFunction function);
}
