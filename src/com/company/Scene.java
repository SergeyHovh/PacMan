package com.company;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;
import com.company.Models.Entity;
import com.company.Models.Food;
import com.company.Models.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class Scene extends GridPanel {
    private Vector<Entity> entities = new Vector<>();
    private Vector<Food> foodVector = new Vector<>();
    private Player pacman;
    private int tick = 0, n, w, h;

    Scene(int N, double w, double h) {
        super(N, w, h);
        this.n = N;
        this.w = (int) w;
        this.h = (int) h;
        generateScene();

    }

    private void generateScene() {
        pacman = new Player(n / 2, n / 2, n, this);
        generateFood(5);
        entities.addAll(foodVector);
        entities.add(pacman);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getGrid()[i][j].setColor(Color.WHITE);
            }
        }
        for (Entity entity : entities) {
            getGrid()[entity.getX()][entity.getY()].setColor(entity.getColor());
        }
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
            generateFood(1);
            tick = 0;
        }
    }

    private void generateFood(int quantity) {
        Vector<Food> food = new Vector<>();
        var random = new Random();
        IntStream range = random.ints(0, n);
        PrimitiveIterator.OfInt answer = range.iterator();
        int count = 0;
        while (count < quantity) {
            var x = answer.nextInt();
            var y = answer.nextInt();
            if (getGrid()[x][y].getColor().equals(Color.WHITE)) {
                food.add(new Food(x, y, n, this));
                count++;
            }
        }
        this.addFood(quantity);
        foodVector.addAll(food);
    }
}
