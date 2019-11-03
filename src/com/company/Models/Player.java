package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;

import java.awt.*;

public class Player {
    int x,y, N, points = 0;
    Cell[][] grid;
    GridPanel panel;
    public Player(int x, int y, int N, GridPanel grid) {
        this.grid = grid.getGrid();
        this.panel = grid;
        this.x = x;
        this.y = y;
        this.N = N;
        this.grid[x][y].setColor(Color.YELLOW);
    }
    public int getPoints() {return points;}

    private boolean[] getApplicablePositions() {
        var result = new boolean[] {this.x > 0, this.y > 0, this.x < N - 1, this.y < N - 1};
        return result;
    }
    public void tryMoveLeft() {
        if (getApplicablePositions()[0]) {
            this.grid[x][y].setColor(Color.WHITE);
            this.x = this.x - 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
            this.grid[x][y].setColor(Color.YELLOW);
        }
    }
    public void tryMoveUp() {
        if (getApplicablePositions()[1]) {
            this.grid[x][y].setColor(Color.WHITE);
            this.y = this.y - 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
            this.grid[x][y].setColor(Color.YELLOW);
        }
    }
    public void tryMoveRight() {
        if (getApplicablePositions()[2]) {
            this.grid[x][y].setColor(Color.WHITE);
            this.x = this.x + 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
            this.grid[x][y].setColor(Color.YELLOW);
        }
    }
    public void tryMoveDown() {
        if (getApplicablePositions()[3]) {
            this.grid[x][y].setColor(Color.WHITE);
            this.y = this.y + 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
            this.grid[x][y].setColor(Color.YELLOW);
        }
    }
}
