package com.manhtai.tankstupid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Tank {
    int x, y, width = 50, height =50, speed = 20;
    Bitmap tankUp;
    Bitmap tankDrop;

    Tank(Resources res, int screenX, int screenY, int orientation) {

        if (orientation == 1) {
            x = screenX / 5;
            y = screenY / 2 - width;
            speed = 20;
        } else {
            x = screenX / 3;
            y = screenY / 2 - width;
        }


    }

    public Bitmap getTank() {
        return tankUp;
    }

    public Bitmap getTankDrop() {
        return tankDrop;
    }

    public Rect getCollision() {
        return new Rect(x, y, x + width , y + height);
    }
}
