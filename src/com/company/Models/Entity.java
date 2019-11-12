package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Scene;
import com.company.Search.Node;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;

public class Entity {
    private UUID id;
    int x, y, N;
    Cell[][] grid;
    Scene panel;
    Color color;

    Entity(int x, int y, int n, Scene panel) {
        this.x = x;
        this.y = y;
        N = n;
        this.panel = panel;
        grid = panel.getGrid();
        id = UUID.randomUUID();
    }

    Entity(int x, int y, int n, UUID id, Scene panel) {
        this.x = x;
        this.y = y;
        N = n;
        this.panel = panel;
        grid = panel.getGrid();
        this.id = id;
    }

    Vector<Cell> getNeighbours() {
        return grid[x][y].getNeighbors(panel.getGrid(), false);
    }

    // getters and setters
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

    public UUID getId() {
        return id;
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

    public Scene getPanel() {
        return panel;
    }

    public void setPanel(Scene panel) {
        this.panel = panel;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
//        return getX() == entity.getX() &&
//                getY() == entity.getY() &&
//                getColor().equals(entity.getColor());
        return entity.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getColor());
    }

    protected void interact(int x, int y) { }
}
