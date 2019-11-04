package com.company.Models;

import com.company.Scene;

import java.awt.*;

public class Food extends Entity {
    public Food(int x, int y, int n, Scene panel) {
        super(x, y, n, panel);
        color = Color.GREEN;
    }
}
