package com.company.Models;

import com.company.Helpers.GridPanel;

import java.awt.*;

public class Player extends Entity {
    private int points = 0;

    public Player(int x, int y, int N, GridPanel gridPanel) {
        super(x, y, N, gridPanel);
//        grid[x][y].setColor(Color.YELLOW);
        color = Color.YELLOW;
    }

    public int getPoints() {
        return points;
    }

    public void tryMoveLeft() {
        if (this.x > 0) {
            this.x = this.x - 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
        }
    }

    public void tryMoveUp() {
        if (this.y > 0) {
            this.y = this.y - 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
        }
    }

    public void tryMoveRight() {
        if (this.x < N - 1) {
            this.x = this.x + 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
        }
    }

    public void tryMoveDown() {
        if (this.y < N - 1) {
            this.y = this.y + 1;
            if (this.grid[x][y].getColor() == Color.RED) {
                points++;
                panel.addFood(-1);
            }
        }
    }
}
