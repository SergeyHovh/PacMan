package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Scene;

import java.awt.*;
import java.util.UUID;

public class Player extends Entity {
    private int points = 0;
    private boolean isFrozen = false;

    public Player(int x, int y, int N, Scene gridPanel) {
        super(x, y, N, gridPanel);
        color = Color.YELLOW;
    }
    public Player(int x, int y, int N, UUID id, Scene gridPanel) {
        super(x, y, N, id, gridPanel);
        color = Color.YELLOW;
    }

    public int getPoints() {
        return points;
    }

    public boolean tryMoveLeft() {
        if (!isFrozen && this.x > 0 && !getGrid()[this.x - 1][this.y].isWall()) {
            this.x--;
            interact(this.x, this.y);
            return true;
        }
        return false;
    }

    public boolean tryMoveUp() {
        if (!isFrozen && this.y > 0 && !getGrid()[this.x][this.y - 1].isWall()) {
            this.y--;
            interact(this.x, this.y);
            return true;
        }
        return false;
    }

    public boolean tryMoveRight() {
        if (!isFrozen && this.x < N - 1 && !getGrid()[this.x + 1][this.y].isWall()) {
            this.x++;
            interact(this.x, this.y);
            return true;
        }
        return false;
    }

    public boolean tryMoveDown() {
        if (!isFrozen && this.y < N - 1 && !getGrid()[this.x][this.y + 1].isWall()) {
            this.y++;
            interact(this.x, this.y);
            return true;
        }
        return false;
    }

    protected void interact(int x, int y) {
        Cell cell = grid[x][y];
        if (cell.isFood()) {
            panel.entities.remove(new Food(x, y, N, panel));
            points++;
            panel.addFood(-1);
            System.out.println(points);
        } else if (cell.isEnemy()) {
            points = 0;
            isFrozen = true;
        }
    }
}
