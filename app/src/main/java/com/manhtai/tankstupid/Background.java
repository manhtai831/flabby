package com.manhtai.tankstupid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x, y, width, height;
    Bitmap picture;
    Bitmap[] background;

    Background(Resources resources, int screenX, int screenY) {
        picture = BitmapFactory.decodeResource(resources, R.drawable.background);
        width = picture.getWidth();
        height = picture.getHeight();
        background = new Bitmap[4];
        for (int i = 0; i < background.length; i++) {
            int w = width;
            int h = height / 4;
            background[i] = Bitmap.createBitmap(picture, 0, h * i, w, h);
            background[i] = Bitmap.createScaledBitmap(background[i], screenX,h/2,false);
        }
        y = screenY - height/8;
        x = 0;

    }

    public Bitmap[] getBackground() {
        return background;
    }
}
