package com.company;

import com.company.Helpers.Base;
import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Scene extends GridPanel {
    protected Scene(int N, double w, double h) {
        super(N, w, h);
    }

    public static void main(String[] args) {
        // create window
        Base base = new Base("PacMan", 600, 600);

        // create grid panel
        // N - grid density
        // w - grid width
        // h - grid height
        Scene scene = new Scene(20, 600, 600);

        // add grid panel to the window
        base.addComponent(scene);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Cell cell = getGrid()[getClickedI()][getClickedJ()];
        cell.setColor(Color.RED);
        for (Cell neighbor : cell.getNeighbors(getGrid(), true)) {
            neighbor.setColor(Color.BLUE);
        }
    }
}
