package com.company;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;
import com.company.Models.Enemy;
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
    public Vector<Entity> entities = new Vector<>();
    private Vector<Food> foodVector = new Vector<>();
    private Vector<Enemy> enemyVector = new Vector<>();
    private Player pacman;
    private int tick = 0;
    private int n;

    Scene(int N, double w, double h) {
        super(N, w, h);
        this.n = N;
        generateScene();
    }

    Scene(int N, double s) {
        super(N, s, s);
        this.n = N;
        generateScene();
    }

    private void generateScene() {
        pacman = new Player(n / 2, 4 * n / 5, n, this);
        generateFood(5);
        generateEnemies(5);
        entities.addAll(foodVector);
        entities.addAll(enemyVector);
        entities.add(pacman);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getGrid()[i][j].reset();
            }
        }

        for (Entity entity : entities) {
            Cell cell = getGrid()[entity.getX()][entity.getY()];
            if (entity instanceof Player) {
                cell.setPacman(true);
            } else if (entity instanceof Food) {
                cell.setFood(true);
            } else if (entity instanceof Enemy) {
                cell.setEnemy(true);
            }
            cell.setColor(entity.getColor());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
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
        if (tick / 10 >= 1 && this.getFood() < 3) {
            generateFood(1);
            entities.add(foodVector.lastElement());
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

    private void generateEnemies(int quantity) {
        Vector<Enemy> enemy = new Vector<>();
        var random = new Random();
        IntStream range = random.ints(0, n);
        PrimitiveIterator.OfInt answer = range.iterator();
        int count = 0;
        while (count < quantity) {
            var x = answer.nextInt();
            var y = answer.nextInt();
            if (getGrid()[x][y].getColor().equals(Color.WHITE)) {
                enemy.add(new Enemy(x, y, n, this));
                count++;
            }
        }
        enemyVector.addAll(enemy);
    }
}
