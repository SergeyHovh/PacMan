package com.company;

import com.company.Helpers.Cell;
import com.company.Helpers.GridPanel;
import com.company.Models.Enemy;
import com.company.Models.Entity;
import com.company.Models.Food;
import com.company.Models.Player;
import com.company.Search.Action;
import com.company.Search.*;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.IntStream;

public class Scene extends GridPanel implements State, ActionListener {
    public Vector<Entity> entities = new Vector<>();
    private Vector<Food> foodVector = new Vector<>();
    private Vector<Enemy> enemyVector = new Vector<>();
    private Player pacman;
    TreeSearch ASTS;
    EnemyGoalTest goalTest;
    Timer timer = new Timer(200, this);
    private int xDir = 1, yDir = 0;
    private int tick = 0, n;
    private double s;

    Scene(int N, double w, double h) {
        super(N, w, h);
        goalTest = new EnemyGoalTest();
        var heuristic = new EnemyHeuristicFunction(this);
        var search = new AStarFunction(heuristic);
        ASTS = new TreeSearch(new BestFirstFrontier(search));
        this.n = N;
        generateScene();
    }

    Scene(int N, double s) {
        super(N, s, s);
        goalTest = new EnemyGoalTest();
        var heuristic = new EnemyHeuristicFunction(this);
        var search = new AStarFunction(heuristic);
        ASTS = new TreeSearch(new BestFirstFrontier(search));
        this.n = N;
        this.s = s;
        generateScene();
    }

    Scene(Scene that) {
        super(that.getN(), that.getS(), that.getS());
        goalTest = new EnemyGoalTest();
        var heuristic = new EnemyHeuristicFunction(this);
        var search = new AStarFunction(heuristic);
        ASTS = new TreeSearch(new BestFirstFrontier(search));
        this.n = that.getN();
        this.s = that.getS();
        generateMaze(Constants.MAP); // TODO should be updated once the maze is dynamic
        pacman = new Player(that.pacman.getX(), that.pacman.getY(), that.pacman.getN(), that.pacman.getId(), this);
        copyExistingFood(that);
        copyExistingEnemies(that);
        entities.addAll(foodVector);
        entities.addAll(enemyVector);
        entities.add(pacman);
    }


    public Scene getActionResult(Action action, Action agent) {
        var newScene = new Scene(this);
        var cell = (Cell) action;
        var agentCell = (Cell) agent;
        if (agentCell.isPacman()) {
            if (cell.getI() == newScene.pacman.getX() - 1) newScene.pacman.tryMoveLeft();
            else if (cell.getI() == newScene.pacman.getX() + 1) newScene.pacman.tryMoveRight();
            else if (cell.getJ() == newScene.pacman.getY() + 1) newScene.pacman.tryMoveDown();
            else if (cell.getJ() == newScene.pacman.getY() - 1) newScene.pacman.tryMoveUp();
        } else if (agentCell.isEnemy()){
            var enemyOpt = newScene.enemyVector.stream().filter(l -> getGrid()[l.getX()][l.getY()].equals(agentCell)).findFirst();
            if (enemyOpt.isPresent()) {
                var enemy = enemyOpt.get();
                if (cell.getI() == enemy.getX() - 1) enemy.tryMoveLeft();
                else if (cell.getI() == enemy.getX() + 1) enemy.tryMoveRight();
                else if (cell.getJ() == enemy.getY() + 1) enemy.tryMoveDown();
                else if (cell.getJ() == enemy.getY() - 1) enemy.tryMoveUp();
            }
        }
        return newScene;
    }

    public Vector<Cell> getApplicableActions(Action agent) {
//        var result = new HashMap<Cell, Vector<Cell>>();
//        var pacmanNeighbors = getGrid()[pacman.getX()][pacman.getY()].getNeighbors(getGrid(), false);
//        result.put(getGrid()[pacman.getX()][pacman.getY()], pacmanNeighbors);
//        for (Enemy enemy : enemyVector) {
//            var enemyNeighbors = getGrid()[enemy.getX()][enemy.getY()].getNeighbors(getGrid(), false);
//            result.put(getGrid()[enemy.getX()][enemy.getY()], enemyNeighbors);
//        }
//        return result;
        return ((Cell)agent).getNeighbors(this.getGrid(), false);
    }

    public Player getPacman() {
        return pacman;
    }

    public Vector<Enemy> getEnemies() {
        return enemyVector;
    }

    public int getN() {
        return n;
    }

    public double getS() {
        return s;
    }

    private void generateScene() {
        // creating the maze from a code generated hard coded array
        generateMaze(Constants.MAP);

        pacman = new Player(n / 2, 4 * n / 5 - 1, n, this);
        generateFood(5);
//        generateEnemies(5);
        entities.addAll(foodVector);
        enemyVector.add(new Enemy(pacman.getX()+1, pacman.getY(), pacman.getN(), this));
        entities.addAll(enemyVector);
        entities.add(pacman);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the maze
        generateMaze(Constants.MAP);

        // draw the entities
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
        repaint();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                this.pacman.tryMoveDown();
//                xDir = 0;
//                yDir = -1;
                break;
            case KeyEvent.VK_UP:
                this.pacman.tryMoveUp();
//                xDir = 0;
//                yDir = 1;
                break;
            case KeyEvent.VK_LEFT:
                this.pacman.tryMoveLeft();
//                xDir = -1;
//                yDir = 0;
                break;
            case KeyEvent.VK_RIGHT:
                this.pacman.tryMoveRight();
//                xDir = 1;
//                yDir = 0;
                break;
            case KeyEvent.VK_SPACE:
//                timer.start();
                break;
        }
        System.out.println("pxikner");
        System.out.println("calculateEnemyMovement() = " + calculateEnemyMovement());
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

    private void copyExistingFood(Scene scene) {
        Vector<Food> food = new Vector<>();
        var quantity = scene.getFood();
        var cnt = 0;
        for (var f : scene.foodVector) {
            if (cnt >= quantity) break;
            food.add(new Food(f.getX(), f.getY(), f.getN(), f.getId(), scene));
            cnt--;
        }
        this.foodVector = food;
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

    private void copyExistingEnemies(Scene scene) {
        Vector<Enemy> enemies = new Vector<>();
        for (var f : scene.getEnemies()) {
            enemies.add(new Enemy(f.getX(), f.getY(), f.getN(), f.getId(), scene));
        }
        this.enemyVector = enemies;
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
                Cell cell = getGrid()[i][j];
                if (map[i][j] == 0) {
                    cell.setColor(Color.WHITE);
                    cell.setFood(false);
                    cell.setEnemy(false);
                    cell.setPacman(false);
                } else {
                    cell.setColor(Color.BLACK);
                    cell.setWall(true);
                }
            }
        }
    }

    public Node calculateEnemyMovement() {
        var roots = new Vector<Node>();
        for (Enemy enemy : enemyVector) {
            roots.add(new Node(null, getGrid()[enemy.getX()][enemy.getY()], this, 0, 0));
        }
        return ASTS.getSolution(roots, goalTest);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // dynamic change
//        movePacman(xDir, yDir);
        tick++;
        if (tick / 10 >= 1 && this.getFood() < 3) {
            generateFood(1);
            entities.add(foodVector.lastElement());
            tick = 0;
        }

        repaint();
    }
}
