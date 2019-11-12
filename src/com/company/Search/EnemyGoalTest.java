package com.company.Search;

import com.company.Models.Enemy;
import com.company.Scene;

public class EnemyGoalTest implements GoalTest {
    public boolean isGoal(State state) {
        var scene = (Scene) state;
        var pacman = scene.getPacman();
        for (Enemy enemy : scene.getEnemies()) {
            if (enemy.getX() == pacman.getX() && enemy.getY() == pacman.getY()) return true;
        }
        return false;
    }
}
