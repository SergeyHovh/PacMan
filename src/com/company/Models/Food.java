package com.company.Models;

import com.company.Helpers.GridPanel;

import java.awt.*;

public class Food extends Entity {
    int score = 1; // may change

    public Food(int x, int y, int n, GridPanel panel) {
        super(x, y, n, panel);
        color = Color.GREEN;
    }
}
