package com.company.Helpers;

import javax.swing.*;
import java.awt.*;

public class Base extends JFrame {
    public Base(String name, int width, int height) {
        super(name);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public Base(String name) {
        this(name, 640, 480);
    }

    public Base(String name, int side) {
        this(name, side, side);
    }

    public void addComponent(Component comp) {
        super.add(comp);
        comp.setFocusable(true);
        comp.requestFocus();
    }
}
