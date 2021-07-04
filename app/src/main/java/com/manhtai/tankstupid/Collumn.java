package com.manhtai.tankstupid;

import android.graphics.Rect;

import java.util.Random;

public class Collumn {

    int x = 0, y, width, height;

    Random random;

    Collumn(int screenX, int screenY) {
        random = new Random();

        width = screenX / 10;
        height = random.nextInt((screenY / 2));
    }

}
