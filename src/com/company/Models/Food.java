package com.company.Models;

import com.company.Scene;

import java.awt.*;
import java.util.UUID;

public class Food extends Entity {
    public Food(int x, int y, int n, Scene panel) {
        super(x, y, n, panel);
        color = Color.ORANGE;
    }

    public Food(int x, int y, int n, UUID id, Scene panel) {
        super(x, y, n, id, panel);
        color = Color.ORANGE;
    }

    public void tryMoveLeft() { }

    public void tryMoveUp() { }

    public void tryMoveRight() { }

    public void tryMoveDown() { }
}
