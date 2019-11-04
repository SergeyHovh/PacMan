package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Scene;

import java.awt.*;
import java.util.Vector;

public class Player extends Entity {
    private int points = 0;

    public Player(int x, int y, int N, Scene gridPanel) {
        super(x, y, N, gridPanel);
        color = Color.YELLOW;
    }

    public int getPoints() {
        return points;
    }

    public void tryMoveLeft() {
        if (this.x > 0 && getGrid()[this.x - 1][this.y].getColor() != Color.BLACK) {
            this.x--;
            interact(this.x, this.y);
        }
    }

    public void tryMoveUp() {
        if (this.y > 0 && getGrid()[this.x][this.y - 1].getColor() != Color.BLACK) {
            this.y--;
            interact(this.x, this.y);
        }
    }

    public void tryMoveRight() {
        if (this.x < N - 1 && getGrid()[this.x + 1][this.y].getColor() != Color.BLACK) {
            this.x++;
            interact(this.x, this.y);
        }
    }

    public void tryMoveDown() {
        if (this.y < N - 1 && getGrid()[this.x][this.y + 1].getColor() != Color.BLACK) {
            this.y++;
            interact(this.x, this.y);
        }
    }

    private void interact(int x, int y) {
        Cell cell = grid[x][y];
        if (cell.isFood()) {
            points++;
            panel.addFood(-1);
            Vector<Entity> foodVector = panel.entities;
            for (int i = 0; i < foodVector.size(); i++) {
                Entity food = foodVector.get(i);
                if (food.getX() == x && food.getY() == y && food instanceof Food) {
                    panel.entities.remove(food);
                }
            }
        }
    }
}
