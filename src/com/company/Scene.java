package com.company;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;
import com.company.Models.Enemy;
import com.company.Models.Entity;
import com.company.Models.Food;
import com.company.Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class Scene extends GridPanel implements ActionListener {
    public Vector<Entity> entities = new Vector<>();
    private Vector<Food> foodVector = new Vector<>();
    private Vector<Enemy> enemyVector = new Vector<>();
    private Player pacman;
    Timer timer = new Timer(200, this);
    private int xDir = 1, yDir = 0;
    private int tick = 0, n;

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
        // creating the maze from a code generated hard coded array
        generateMaze(Constants.MAP);
        pacman = new Player(n / 2, 4 * n / 5 - 1, n, this);
        generateFood(5);
        generateEnemies(5);
        entities.addAll(foodVector);
        entities.addAll(enemyVector);
        entities.add(pacman);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        generateMaze(Constants.MAP);
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

    private void movePacman(int x, int y) {
        if (x == 1) {
            pacman.tryMoveRight();
        } else if (x == -1) {
            pacman.tryMoveLeft();
        } else if (y == 1) {
            pacman.tryMoveUp();
        } else if (y == -1) {
            pacman.tryMoveDown();
        }
    }

    // listeners
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyTyped(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
//                this.pacman.tryMoveDown();
                xDir = 0;
                yDir = -1;
                break;
            case KeyEvent.VK_UP:
//                this.pacman.tryMoveUp();
                xDir = 0;
                yDir = 1;
                break;
            case KeyEvent.VK_LEFT:
//                this.pacman.tryMoveLeft();
                xDir = -1;
                yDir = 0;
                break;
            case KeyEvent.VK_RIGHT:
//                this.pacman.tryMoveRight();
                xDir = 1;
                yDir = 0;
                break;
            case KeyEvent.VK_SPACE:
                timer.start();
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

    /**
     * draws the maze with given map, where 1 is wall (uncrossable)
     * and 0 is floor (passable)
     *
     * @param map 2D int array of 0s and 1s
     */
    private void generateMaze(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    getGrid()[i][j].setColor(Color.WHITE);
                } else {
                    getGrid()[i][j].setColor(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // dynamic change
        movePacman(xDir, yDir);
        repaint();
    }
}
