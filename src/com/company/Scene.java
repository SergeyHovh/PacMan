package com.company;

import com.company.Helpers.Base;
import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;
import com.company.Models.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.IntStream;

public class Scene extends GridPanel {
    private Scene(int N, double w, double h) {
        super(N, w, h);
        this.pacman = new Player(0,0, N,this);
    }
    private Player pacman;
    private int tick = 0;
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
        scene.putFood(5);
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

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyTyped(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                this.pacman.tryMoveDown();
                break;
            case KeyEvent.VK_UP:
                this.pacman.tryMoveUp();
                break;
            case KeyEvent.VK_LEFT:
                this.pacman.tryMoveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                this.pacman.tryMoveRight();
                break;
        }
        tick++;
        if (tick / 20 >= 1 && this.getFood() < 3) {
            putFood(1);
            tick = 0;
        }
    }

    private void putFood(int quantity) {
        var random = new Random();
        IntStream range = random.ints(0, 20);
        PrimitiveIterator.OfInt answer = range.iterator();
        for (int i = 0; i < quantity; i++) {
            var x = answer.nextInt();
            var y = answer.nextInt();
            if (this.getGrid()[x][y].getColor() == Color.WHITE)
                this.getGrid()[x][y].setColor(Color.RED);
        }
        this.addFood(quantity);
    }
}
