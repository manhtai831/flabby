package com.manhtai.tankstupid;

import android.graphics.Rect;

import java.util.Random;

public class Collumn {

    public int speed = 20;
    int x = 0, y, width, height;

    Random random;


    Collumn(int screenX, int screenY, int orientation) {
        random = new Random();

        if (orientation == 1) {
            width = screenX / 5;
            height = random.nextInt((screenY / 2));
            speed = 20;
        } else {
            width = screenX / 10;
            height = random.nextInt((screenY / 2));
        }

    }

    public Rect getCollision(int x, int y, int width, float height) {
        return new Rect(x, y, width, (int) height);
    }



}
