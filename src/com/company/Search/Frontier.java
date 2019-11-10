
package com.company.Search;

public interface Frontier {
    void addToFrontier(Node n);

    boolean isEmpty();

    boolean contains(Node n);

    Node removeFromFrontier();
}
