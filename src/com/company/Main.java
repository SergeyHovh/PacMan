package com.company;

import com.company.Helpers.Base;

public class Main {
    public static void main(String[] args) {
        final int N = 20;
        final int W = 600;
        final int H = 600;

        Base base = new Base("PacMan", W, H);
        Scene scene = new Scene(N, W, H);
        // add grid panel to the window
        base.addComponent(scene);
//        scene.putFood(5);
    }
}
