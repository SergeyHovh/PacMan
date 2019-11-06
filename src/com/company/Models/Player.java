package com.company.Models;

import com.company.Helpers.Cell;
import com.company.Scene;

import java.awt.*;

public class Player extends Entity {
    private int points = 0;

    public Player(int x, int y, int N, Scene gridPanel) {
        super(x, y, N, gridPanel);
        color = Color.BLUE;
    }

    public int getPoints() {
        return points;
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

    private void interact(int x, int y) {
        Cell cell = grid[x][y];
        if (cell.isFood()) {
            panel.entities.remove(new Food(x, y, N, panel));
            points++;
            panel.addFood(-1);
            System.out.println(points);
        } else if (cell.isEnemy()) {
//             TODO: 11/6/2019 implement better enemy interaction
//            enemy interaction
            panel.entities.remove(new Enemy(x, y, N, panel));
            points--;
            System.out.println(points);
        }
    }
}
