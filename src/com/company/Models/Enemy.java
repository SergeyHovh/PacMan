package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Scene;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Enemy extends Entity {
    private static Color[] possibleColors = {Color.CYAN, Color.RED, Color.PINK, Color.ORANGE};
    private static int firstPosition = -1;
    private static int currentPosition = -1;
    public int direction = -1;
    public Enemy(int x, int y, int n, Scene panel) {
        super(x, y, n, panel);
        if (firstPosition == -1) {
            firstPosition = (new Random()).nextInt(3);
            currentPosition = firstPosition;
        }
        color = possibleColors[currentPosition % 4];
        currentPosition++;
    }

    public Enemy(int x, int y, int n, UUID id, Scene panel) {
        super(x, y, n, id, panel);
        color = possibleColors[(new Random()).nextInt(3)];
    }

    public void tryMoveLeft() {
        if (this.x > 0 && !getGrid()[this.x - 1][this.y].isWall()) {
            this.x--;
            interact(this.x, this.y);
        }
    }

    public void tryMoveUp() {
        if (this.y > 0 && !getGrid()[this.x][this.y - 1].isWall()) {
            this.y--;
            interact(this.x, this.y);
        }
    }

    public void tryMoveRight() {
        if (this.x < N - 1 && !getGrid()[this.x + 1][this.y].isWall()) {
            this.x++;
            interact(this.x, this.y);
        }
    }

    public void tryMoveDown() {
        if (this.y < N - 1 && !getGrid()[this.x][this.y + 1].isWall()) {
            this.y++;
            interact(this.x, this.y);
        }
    }

    @Override
    protected void interact(int x, int y) {
        Cell cell = grid[x][y];
        if (!cell.isPacman())
            super.interact(x, y);
        else {
            this.getPanel().endGame();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return o.getClass() == this.getClass() ? ((Enemy)o).getId().equals(getId()) : super.equals(o);
    }
}
