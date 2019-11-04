package com.company;

import com.company.Helpers.Base;

public class Main {
    public static void main(String[] args) {
        final int N = 20;
        final int S = 600;

        Base base = new Base("PacMan", S);
        Scene scene = new Scene(N, S);
        // add grid panel to the window
        base.addComponent(scene);
//        scene.putFood(5);
    }
}
