package com.company.Search;

import com.company.Helpers.Cell;
import com.company.Models.Entity;
import com.company.Scene;

public class EnemyHeuristicFunction implements NodeFunction {
    private Scene scene;
    public EnemyHeuristicFunction(Scene scene) {this.scene = scene;}
    public double produce(Node n) {
        var pacman = scene.getPacman();
        Cell c = pacman.getGrid()[pacman.getX()][pacman.getY()];
        Cell c2 = (Cell)n.action;
        return dist(c.getX(), c.getY(), c2.getX(), c2.getY());
    }
    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
