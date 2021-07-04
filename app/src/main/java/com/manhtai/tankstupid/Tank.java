package com.manhtai.tankstupid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Tank {
    int x,y,width,height;
    Bitmap tank;

    Tank(Resources res,int screenX,int screenY) {
        tank = BitmapFactory.decodeResource(res, R.drawable.tank);

        width = tank.getWidth() / 10;
        height = tank.getHeight() / 10;

        tank = Bitmap.createScaledBitmap(tank, width, height, false);
         x = screenX/3;
         y = screenY /2;
    }

    public Bitmap getTank() {
        return tank;
    }

    public Rect getCollision(){
        return new Rect(x,y,x + width, y + height);
    }
}
