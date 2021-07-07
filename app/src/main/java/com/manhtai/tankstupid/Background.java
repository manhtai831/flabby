package com.manhtai.tankstupid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Background {
    int x, y, width, height;


    Bitmap background;

    Background(Resources resources, int screenX, int screenY) {
        background = BitmapFactory.decodeResource(resources, R.drawable.background);
        width = screenX;
        height = screenY;
        background = Bitmap.createScaledBitmap(background, screenX, screenY / 5, false);

        y = screenY - screenY / 5;
        x = 0;

    }

    public Bitmap getBackground() {
        return background;
    }

    public Rect getCollision(){
        return new Rect(x -height/4,y,width,height);
    }
}
