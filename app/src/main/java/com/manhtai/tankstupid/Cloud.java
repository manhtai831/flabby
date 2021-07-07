package com.manhtai.tankstupid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

public class Cloud {
    int x,y,width,height;
    Bitmap[] cloud;
    Random random;
    int type;
    int speed = 5;
    private static final String TAG = "Cloud";
    Cloud(Resources resources,int screenX,int screenY){
        random = new Random();
        cloud = new Bitmap[3];
        cloud[0] = BitmapFactory.decodeResource(resources,R.drawable.cloud_1);
        cloud[1] = BitmapFactory.decodeResource(resources,R.drawable.cloud_2);
        cloud[2] = BitmapFactory.decodeResource(resources,R.drawable.cloud_3);
        for(int i = 0; i < cloud.length;i++){
            width = screenX/2;
            height = screenY/5;
            cloud[i] = Bitmap.createScaledBitmap(cloud[i],width,height,false);
            Log.d(TAG, "Cloud: create cloud" + i);
        }
        x = screenX + width;
        y = screenY/4;
    }

    public Bitmap getCloud() {
        Log.d(TAG, "getCloud: " +type);
        if(type == 0) return cloud[0];
        if(type == 1) return cloud[1];
        return cloud[2];
    }
}
