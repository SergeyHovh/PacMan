package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;

import java.awt.*;
import java.util.Vector;

public class Entity {
    int x, y, N;
    Cell[][] grid;
    GridPanel panel;
    Color color;

    Entity(int x, int y, int n, GridPanel panel) {
        this.x = x;
        this.y = y;
        N = n;
        this.panel = panel;
        this.grid = panel.getGrid();
    }

    Vector<Cell> getNeighbours() {
        return grid[x][y].getNeighbors(panel.getGrid(), false);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public GridPanel getPanel() {
        return panel;
    }

    public void setPanel(GridPanel panel) {
        this.panel = panel;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
