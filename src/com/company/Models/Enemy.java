package com.company.Models;

import com.company.Helpers.GridPanel;

import java.awt.*;

public class Enemy extends Entity {

    public Enemy(int x, int y, int n, GridPanel panel) {
        super(x, y, n, panel);
        color = Color.RED;
    }
}
