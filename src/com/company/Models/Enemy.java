package com.company.Models;

import com.company.Scene;

import java.awt.*;
import java.util.UUID;

public class Enemy extends Entity {

    public Enemy(int x, int y, int n, Scene panel) {
        super(x, y, n, panel);
        color = Color.RED;
    }

    public Enemy(int x, int y, int n, UUID id, Scene panel) {
        super(x, y, n, id, panel);
        color = Color.RED;
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
        super.interact(x, y);
    }
}
