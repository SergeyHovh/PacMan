package com.company.Helpers;

import com.company.Search.Action;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Vector;

public class Cell extends Rectangle2D.Double implements Action {
    private Color color;
    private Color border;
    private int i, j;
    private boolean enemy = false;
    private boolean pacman = false;
    private boolean food = false;
    private boolean empty = false;
    private boolean wall = false;

    Cell(double x, double y, double w, double h) {
        super(x * w, y * h, w, h);
        this.i = (int) x;
        this.j = (int) y;
        color = Color.WHITE;
        border = Color.WHITE;
    }

    @Override
    public int cost() {
        return 1;
    }

    public Vector<Cell> getNeighbors(Cell[][] grid, boolean diagonals) {
        Vector<Cell> result = new Vector<>();
        int rows = grid.length;
        int cols = grid[0].length;
        if (i > 0 && !grid[i - 1][j].isWall()) result.add(grid[i - 1][j]);
        if (i < rows - 1 && !grid[i + 1][j].isWall()) result.add(grid[i + 1][j]);
        if (j > 0 && !grid[i][j - 1].isWall()) result.add(grid[i][j - 1]);
        if (j < cols - 1 && !grid[i][j + 1].isWall()) result.add(grid[i][j + 1]);
        if (diagonals) {
            if (i > 0 && j > 0) result.add(grid[i - 1][j - 1]);
            if (i > 0 && j < cols - 1) result.add(grid[i - 1][j + 1]);
            if (i < rows - 1 && j > 0) result.add(grid[i + 1][j - 1]);
            if (i < rows - 1 && j < cols - 1) result.add(grid[i + 1][j + 1]);
        }
        return result;
    }

    public Vector<Cell> getNeighborsPositioned(Cell[][] grid, boolean diagonals) {
        Vector<Cell> result = new Vector<>();
        int rows = grid.length;
        int cols = grid[0].length;
        if (i > 0 && !grid[i - 1][j].isWall()) {
            result.add(grid[i - 1][j]);
        } else {
            result.add(null);
        }
        if (i < rows - 1 && !grid[i + 1][j].isWall()) result.add(grid[i + 1][j]);
        else {
            result.add(null);
        }
        if (j > 0 && !grid[i][j - 1].isWall()) result.add(grid[i][j - 1]);
        else {
            result.add(null);
        }
        if (j < cols - 1 && !grid[i][j + 1].isWall()) result.add(grid[i][j + 1]);
        else {
            result.add(null);
        }
        if (diagonals) {
            if (i > 0 && j > 0) result.add(grid[i - 1][j - 1]);
            else {
                result.add(null);
            }
            if (i > 0 && j < cols - 1) result.add(grid[i - 1][j + 1]);
            else {
                result.add(null);
            }
            if (i < rows - 1 && j > 0) result.add(grid[i + 1][j - 1]);
            else {
                result.add(null);
            }
            if (i < rows - 1 && j < cols - 1) result.add(grid[i + 1][j + 1]);
            else {
                result.add(null);
            }
        }
        return result;
    }

    public Color getColor() {
        return color;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    public Color getBorder() {
        return border;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public void reset() {
        color = Color.WHITE;
        food = false;
        enemy = false;
        pacman = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
        Cell cell = (Cell) o;
        if (cell.i == 18) {
            var a = 1;
        }
        return i == cell.i &&
                j == cell.j &&
                Objects.equals(color, cell.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, i, j);
    }

    public boolean isPacman() {
        return pacman;
    }

    public void setPacman(boolean pacman) {
        this.pacman = pacman;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
