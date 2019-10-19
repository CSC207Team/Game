package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject {
    private Rect rectangle;
    private int color;
    private Animation walkRight, walkleft;
    private Animation idle;
    private AnimationManager animationManager;

    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.idle);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.walkright1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.walkright2);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1,1);
        walk1 = Bitmap.createBitmap(walk1,0,0,walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2,0,0,walk2.getWidth(), walk2.getHeight(), m, false);
        walkleft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animationManager = new AnimationManager((new Animation[]{idle, walkRight, walkleft}));

    }
    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        animationManager.update();

    }
    public void update(Point point) {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2,
                point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0; // 0 idle, 1 walking , 2 walking left
        if (rectangle.left -oldLeft > 0) {
            state = 1;}
        else if (rectangle.left - oldLeft < 0) {
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();
    }
}
