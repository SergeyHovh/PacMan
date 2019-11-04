package com.company.Models;

import com.company.Scene;

import java.awt.*;

public class Enemy extends Entity {

    public Enemy(int x, int y, int n, Scene panel) {
        super(x, y, n, panel);
        color = Color.RED;
    }
}
