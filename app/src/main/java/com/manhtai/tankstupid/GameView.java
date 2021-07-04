package com.manhtai.tankstupid;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    int screenX, screenY;
    Activity mActivity;
    Thread thread;
    Tank tank;
    Paint paint;
    int score = 0;
    Random random;
    Collumn[] collumn;
    Collumn[] collumnBot;

    GameView(Activity activity, int screenX, int screenY) {
        super(activity);
        this.screenX = screenX;
        this.screenY = screenY;
        this.mActivity = activity;

        tank = new Tank(getResources(), screenX,screenY);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        paint.setStrokeWidth(3);
        random = new Random();

        collumn = new Collumn[4];
        for (int i = 0; i < collumn.length; i++) {
            collumn[i] = new Collumn(screenX, screenY);
            collumn[i].y = 0;
            collumn[i].x = (screenX - screenX /3 ) + (collumn[i].width * i * 3);
        }

        collumnBot = new Collumn[4];
        for (int i = 0; i < collumnBot.length; i++) {
            collumnBot[i] = new Collumn(screenX, screenY);
            collumnBot[i].y = screenY;
            collumnBot[i].x =  (screenX - screenX /3 )+ (collumnBot[i].width * i * 3);
        }

    }

    @Override
    public void run() {
        while (true) {

            update();
            drawView();
            sleep();
        }

    }

    private void update() {

        tank.y += 1;
        if (tank.y > screenY - tank.height) {
            tank.y = screenY - tank.height;
        }

        if (tank.y < 0) {
            tank.y = 0;
        }

        for (Collumn c : collumn) {
            c.x -= 1;
            if( c.x <  - c.width ){
                c.x = screenX + c.width;
                c.height = random.nextInt(screenY/2);
            }
        }
        for (Collumn c : collumnBot) {
            c.x -= 1;
            if( c.x <  - c.width ){
                c.x = screenX + c.width;
                c.height = random.nextInt(screenY/2);
            }
        }



    }

    private void drawView() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            canvas.drawRect(0, 0, screenX, screenY, paint1);
            canvas.drawBitmap(tank.getTank(), tank.x, tank.y, paint);
            canvas.drawText(score + "", screenX - 200, 0, paint);

            for (Collumn c : collumn)
                canvas.drawRect(c.x, c.y, c.x + c.width, c.height, paint);

            for (int i = 0; i < collumnBot.length; i++) {
                for (int j = 0; j < collumn.length; j++) {
                    if (i == j) {
                        canvas.drawRect(collumnBot[i].x,
                                collumnBot[i].y,
                                collumnBot[i].x + collumnBot[i].width,
                                collumn[i].height + screenY / 4f, paint);
                        break;
                    }

                }
            }


            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tank.y -= 20;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public void resume() {
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
