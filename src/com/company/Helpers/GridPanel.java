package com.company.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridPanel extends JPanel implements MouseListener, KeyListener {
    private int food = 0;
    private Cell[][] grid;
    private double scaleX, scaleY;
    private int I, J;
    private JComponent points;

    protected int getFood() {
        return food;
    }

    public void addFood(int q) {
        food += q;
    }

    protected GridPanel(int N, double w, double h) {
        this.points = new JLabel("Points: 0");
        this.add(points);
        this.grid = new Cell[N][N];
        this.scaleX = (w - 10) / N;
        this.scaleY = (h - 35) / N;
        addMouseListener(this);
        addKeyListener(this);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell(i, j, scaleX, scaleY);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Cell[] rectangle2DS : grid) {
            for (Cell rectangle2D : rectangle2DS) {
                if (!rectangle2D.isEmpty() && !rectangle2D.isWall() && !rectangle2D.isEnemy()) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.fill(rectangle2D);
                    graphics2D.setColor(rectangle2D.getColor());
                    var width = (int)rectangle2D.getWidth();
                    var factor = rectangle2D.isFood() ? 2 : 1;
                    graphics2D.fillOval((int)rectangle2D.getCenterX() - width/(2 * factor), (int)rectangle2D.getCenterY() - width/(2*factor), width / factor, width / factor);
                } else {
                    graphics2D.setColor(rectangle2D.getColor());
                    graphics2D.fill(rectangle2D);
                    graphics2D.setColor(rectangle2D.getBorder());
                    graphics2D.draw(rectangle2D);
                }
            }
        }
    }

    public void setPoints(int point) {
        ((JLabel)this.points).setText("Points: " + point);
    }

    /**
     * @return 2D array of {@link Cell}s
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * @return first index of the clicked place
     */
    public int getClickedI() {
        return I;
    }

    /**
     * @return second index of the clicked place
     */
    public int getClickedJ() {
        return J;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // actual computing the i and j coordinates
        this.I = (int) (e.getX() / scaleX);
        this.J = (int) (e.getY() / scaleY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        repaint();
    }
}
