package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


import java.lang.Math;

public class Player implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    private int speed;
    private int max_health = 100;
    HealthBar healthBar;

    Player() {
        speed = 15;
        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.idle);
        this.rectangle = new Rect(Constants.DISPLAY_SIZE.x/2 - 50,
                Constants.DISPLAY_SIZE.y - 50, Constants.DISPLAY_SIZE.x/2 + 50,
                Constants.DISPLAY_SIZE.y + 50);
        Bitmap walk1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.walkright1);
        Bitmap walk2 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.walkright2);

        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m,
                false);
        Animation walkleft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        // All animations in Player
        animationManager = new AnimationManager((new Animation[]{idle, walkRight, walkleft}));
        Point p = new Point();
        p.x = this.rectangle.centerX();
        p.y = this.rectangle.centerY() - (idleImg.getWidth()/2);
        healthBar = new HealthBar(max_health, p, Color.GREEN, 150);
    }

    @Override
    public void draw(Canvas canvas) {
        // canvas.drawRect(rectangle, new Paint()); // THIS HAS TO BE DELETED BUT SHOWS THE PLAYER RECT
        animationManager.draw(canvas, rectangle);
        healthBar.draw(canvas);

    }

    @Override
    public void update() {
        animationManager.update();

    }

    void update(Point point) {
        double oldLeft = rectangle.left;
        float[] normal = new float[2];
        normal[0] = point.x - rectangle.centerX();
        normal[1] = point.y - rectangle.centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        if (magnitude < speed) {
            animationManager.playAnimation(0);
            return;
        }
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int) (un[0] * speed);
        int move_y = (int) (un[1] * speed);

        rectangle.set((rectangle.centerX() + move_x) - rectangle.width() / 2,
                (rectangle.centerY() + move_y) - rectangle.height() / 2,
                (rectangle.centerX() + move_x) + rectangle.width() / 2,
                (rectangle.centerY() + move_y) + rectangle.height() / 2);
        healthBar.move(move_x, move_y);

        int state = 0; // 0 idle, 1 walking , 2 walking left
        if (rectangle.left - oldLeft > 0) {
            state = 1;
        } else if (rectangle.left - oldLeft < 0) {
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();

    }

    Point get_center_location() {
        return new Point(rectangle.centerX(), rectangle.centerY());
    }
}
