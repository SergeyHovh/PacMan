package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;

import java.awt.*;

public class Player {
    private int x,y, N, points = 0;
    private Cell[][] grid;
    private GridPanel panel;
    public Player(int x, int y, int N, GridPanel grid) {
        this.grid = grid.getGrid();
        this.panel = grid;
        this.x = x;
        this.y = y;
        this.N = N;
        this.grid[x][y].setColor(Color.YELLOW);
    }
    public int getPoints() {return points;}

    public void tryMoveLeft() {
        if (this.x > 0) {
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
        if (this.y > 0) {
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
        if (this.x < N - 1) {
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
        if (this.y < N - 1) {
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
