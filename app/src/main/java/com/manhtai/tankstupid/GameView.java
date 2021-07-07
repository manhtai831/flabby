package com.manhtai.tankstupid;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    Activity mActivity;
    Thread thread;
    Random random;
    Paint paint;
    int color;

    Tank tank;
    Background[] background;
    Cloud cloud;
    Collumn[] collumn;


    boolean isUp;
    boolean isPlay = false;
    int screenX, screenY;
    int score = 0;
    int orientation;

    int[] lightColor;


    GameView(Activity activity, int screenX, int screenY) {
        super(activity);
        this.screenX = screenX;
        this.screenY = screenY;
        this.mActivity = activity;
        orientation = getResources().getConfiguration().orientation;
        tank = new Tank(getResources(), screenX, screenY, orientation);

        background = new Background[2];
        for (int i = 0; i < background.length; i++) {
            background[i] = new Background(getResources(), screenX, screenY);
        }
        background[0].x = 0;
        background[1].x = screenX;
        cloud = new Cloud(getResources(), screenX, screenY);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize((int) (screenY / 15));
        paint.setStrokeWidth(3);

        collumn = new Collumn[2];
        for (int i = 0; i < collumn.length; i++) {
            if (orientation == 1) {
                collumn[i] = new Collumn(screenX, screenY, orientation);
                collumn[i].y = 0;
                collumn[i].x = screenX * (i + 1);
            } else {
                collumn[i] = new Collumn(screenX, screenY, orientation);
                collumn[i].y = 0;
                collumn[i].x = (screenX - screenX / 3) + (collumn[i].width * i * 3);
            }

        }

        random = new Random();
        lightColor = getResources().getIntArray(R.array.light);

    }

    @Override
    public void run() {
        sleep(1000);
        update();
        drawView();
        while (true) {
            while (isPlay) {
                update();
                drawView();
                sleep(10);
            }
        }
    }


    private void update() {
        tank.y += tank.speed;
        if (tank.y > screenY - tank.height) {
            tank.y = screenY - tank.height;
        }
        if (tank.y < 0) {
            tank.y = 0;
        }

        for (Collumn value : collumn) {
            value.x -= value.speed;
            if (orientation == 1) {
                if (value.x < -value.width) {
                    value.x = screenX + screenX / 2 + value.width;
                    value.height = random.nextInt(screenY / 2);
                }
                if (value.x < tank.x && tank.x < value.x + value.width) {
                    score++;
                }


            } else {
                if (value.x < -value.width) {
                    value.x = screenX + value.width;
                    value.height = random.nextInt(screenY / 2);
                }
                if (value.x < tank.x && tank.x < value.x + 6) {
                    score++;
                }
                if (Rect.intersects(value.getCollision(value.x, value.y, value.x + value.width, value.height), tank.getCollision()) ||
                        Rect.intersects(value.getCollision(value.x, (int) (value.height + screenY / 4f), value.x + value.width, screenY), tank.getCollision()) ||
                        Rect.intersects(value.getCollision(0, screenY - screenY / 7, screenX, screenY), tank.getCollision())) {
                    isPlay = false;
                }

            }
        }


        cloud.x -= cloud.speed;
        if (cloud.x < -cloud.width) {
            cloud.x = screenX + cloud.width + 100;
            cloud.y = random.nextInt(screenY / 15);
            cloud.type = random.nextInt(3);
        }

        for (int i = 0; i < lightColor.length; i++) {
            if (score % 100 == 0) {
                if (score / 100 < lightColor.length) {
                    color = lightColor[score / 100];

                }
            }
        }

        for (Background background : background) {
            background.x -= 20;

            if (background.x + screenX < 0) {
                background.x = screenX - 20;
            }

            if (Rect.intersects(background.getCollision(), tank.getCollision())) {
                isPlay = false;
            }
        }


    }

    private void drawView() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            paint.setColor(color);
            canvas.drawRect(0, 0, screenX, screenY, paint);
            paint.setColor(Color.GREEN);
            canvas.drawBitmap(cloud.getCloud(), cloud.x, cloud.y, paint);
            for (Collumn c : collumn) {
                if (orientation == 1) {
                    canvas.drawRect(c.x, c.y, c.x + c.width, c.height, paint);
                    canvas.drawRect(c.x, c.height + screenY / 3f, c.x + c.width, screenY, paint);
                } else {
                    canvas.drawRect(c.x, c.y, c.x + c.width, c.height, paint);
                    canvas.drawRect(c.x, c.height + screenY / 4f, c.x + c.width, screenY, paint);
                }

            }
            for (Collumn value : collumn) {
                if (Rect.intersects(value.getCollision(value.x, value.y, value.x + value.width, value.height), tank.getCollision())) {
                    isPlay = false;
                }
                if (Rect.intersects(value.getCollision(value.x + 10, value.y, value.x + value.width, value.height), tank.getCollision())) {
                    tank.y = value.height + tank.height;
                    isPlay = false;
                }
                if (Rect.intersects(value.getCollision(value.x, value.height + screenY / 3, value.x + value.width, screenY - screenY / 7f), tank.getCollision())) {
                    isPlay = false;
                }
            }

            canvas.drawCircle(tank.x, tank.y, tank.width, paint);


            paint.setColor(Color.RED);
            canvas.drawText(score / 10 + "", 10, screenY / 15f, paint);
            for (Background background : background)
                canvas.drawBitmap(background.getBackground(), background.x, background.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isPlay) {
                    score = 0;
                    if (orientation == 1) {

                        tank.x = screenX / 5;
                        tank.y = screenY / 2 - tank.width;
                        tank.speed = 20;
                        for (int i = 0; i < collumn.length; i++) {
                            collumn[i].y = 0;
                            collumn[i].x = screenX * (i + 1);
                        }
                    } else {
                        tank.x = screenX / 3;
                        tank.y = screenY / 2 - tank.width;
                        for (int i = 0; i < collumn.length; i++) {
                            collumn[i].y = 0;
                            collumn[i].x = (screenX - screenX / 3) + (collumn[i].width * i * 3);
                        }
                    }

                    isPlay = true;
                }
                isUp = true;
                tank.y -= screenY / 6;
                break;
            case MotionEvent.ACTION_UP:
                isUp = false;
                performClick();
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
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
