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
    private GraphSearch ASTS;
    private NodeFunction search;
    private EnemyGoalTest goalTest;
    Timer timer = new Timer(200, this);
    private int xDir = 1, yDir = 0;
    private int tick = 0, n;
    private double s;

    Scene(int N, double w, double h) {
        super(N, w, h);
        goalTest = new EnemyGoalTest();
        setAStar();
        this.n = N;
        generateScene();
    }

    private void setAStar() {
        var heuristicFunction = new EnemyHeuristicFunction(this);
        search = new AStarFunction(heuristicFunction);
        ASTS = new GraphSearch(new BestFirstFrontier(this.search));
    }
    Scene(int N, double s) {
        super(N, s, s);
        goalTest = new EnemyGoalTest();
        setAStar();
        this.n = N;
        this.s = s;
        generateScene();
    }

    Scene(Scene that) {
        super(that.getN(), that.getS(), that.getS());
        goalTest = new EnemyGoalTest();
        setAStar();
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
        } else if (agentCell.isEnemy()) {
            moveBasedOnTwoCells(newScene, agentCell, cell);
        }
        return newScene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (Scene) o;
        return Arrays.equals(that.getGrid(), this.getGrid());
    }

    public Vector<Cell> getApplicableActions(Action agent) {
        return ((Cell) agent).getNeighbors(this.getGrid(), false);
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

    private double getS() {
        return s;
    }

    private void generateScene() {
        // creating the maze from a code generated hard coded array
        generateMaze(Constants.MAP);

        pacman = new Player(n / 2, 4 * n / 5 - 1, n, this);
        System.out.println(pacman.getX() + " " + pacman.getY());
        generateFood(5);
        generateEnemies(5);
//        entities.add(new Food(17, 23, n, this));
        entities.addAll (foodVector);
//        enemyVector.add(new Enemy(pacman.getX() + 3, pacman.getY(), pacman.getN(), this));
//        enemyVector.add(new Enemy(pacman.getX() - 4, pacman.getY(), pacman.getN(), this));
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
                cell.setEmpty(false);
            } else if (entity instanceof Food) {
                cell.setFood(true);
                cell.setEmpty(false);
            } else if (entity instanceof Enemy) {
                cell.setEnemy(true);
                cell.setEmpty(false);
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
                if(this.pacman.tryMoveDown())
                calculateEnemyMovement();
                break;
            case KeyEvent.VK_UP:
                if(this.pacman.tryMoveUp())
                calculateEnemyMovement();
                break;
            case KeyEvent.VK_LEFT:
                if(this.pacman.tryMoveLeft())
                calculateEnemyMovement();
                break;
            case KeyEvent.VK_RIGHT:
                if(this.pacman.tryMoveRight())
                calculateEnemyMovement();
                break;
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
            if (getGrid()[x][y].isEmpty()) {
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
            if (getGrid()[x][y].isEmpty()) {
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
                    cell.setColor(Color.BLACK);
                    cell.setBorder(Color.BLACK);
                    cell.setFood(false);
                    cell.setEnemy(false);
                    cell.setPacman(false);
                    cell.setEmpty(true);
                } else {
                    cell.setBorder(Color.GRAY);
                    cell.setColor(Color.GRAY);
                    cell.setWall(true);
                }
            }
        }
    }

    private void calculateEnemyMovement() {
        Node root = null;
        var cost = Double.MAX_VALUE;
        for (Enemy enemy : enemyVector) {
            var newRoot = new Node(null, getGrid()[enemy.getX()][enemy.getY()], this, 0, 0);
            var newCost = search.produce(newRoot);
            if (newCost < cost) {
                root = newRoot;
                cost = newCost;
            }
        }
        setAStar();
        var solution = ASTS.getSolution(root, goalTest, search);

        Stack<Node> stack = new Stack<Node>();
        Node node = solution;
        while (node != null) {
            stack.push(node);
            node = node.parent;
        }
        if (!stack.isEmpty() && stack.size() > 1) {
            var agent = stack.pop().action;
            node = stack.pop();
            var action = node.action;
            moveBasedOnTwoCells(this, agent, action);
        }
    }

    private Enemy moveBasedOnTwoCells(Scene scene, Cell agentCell, Cell cell) {
        Enemy enemyOpt = null;
        for (Enemy enemy : scene.enemyVector) {
            if (enemy.getX() == agentCell.getI() && enemy.getY() == agentCell.getJ() && agentCell.isEnemy()) {
                enemyOpt = enemy;
            }
        }
        if (enemyOpt != null) {
            scene.entities.remove(enemyOpt);
            if (cell.getI() == enemyOpt.getX() - 1) {
                enemyOpt.tryMoveLeft();
                cell.setEnemy(true);
            } else if (cell.getI() == enemyOpt.getX() + 1) {
                enemyOpt.tryMoveRight();
                cell.setEnemy(true);
            } else if (cell.getJ() == enemyOpt.getY() + 1) {
                enemyOpt.tryMoveDown();
                cell.setEnemy(true);
            } else if (cell.getJ() == enemyOpt.getY() - 1) {
                enemyOpt.tryMoveUp();
                cell.setEnemy(true);
            }
            scene.entities.add(enemyOpt);
            return enemyOpt;
        }
        return null;
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
