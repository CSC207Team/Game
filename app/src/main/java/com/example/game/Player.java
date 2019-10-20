package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint;
import java.lang.Math;

public class Player implements GameObject {
    private Rect rectangle;
    private Animation walkRight, walkleft;
    private Animation idle;
    private AnimationManager animationManager;
    private int speed = 15;

    public Player() {
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.idle);
        this.rectangle = new Rect(100,100,200,200);
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
        canvas.drawRect(rectangle,new Paint()); // THIS HAS TO BE DELETED BUT SHOWS THE PLAYER RECT
        animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        animationManager.update();

    }
    public void update(Point point) {
        double oldLeft = rectangle.left;
        float[] normal = new float[2];
        normal[0] = point.x - rectangle.centerX();
        normal[1] = point.y - rectangle.centerY();
        float magnitude = (float)Math.sqrt(normal[0]*normal[0] + normal[1] * normal[1]);
        if (magnitude < speed) {
            animationManager.playAnimation(0);
            return;}
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int)(un[0]*speed);
        int move_y = (int)(un[1]*speed);

        rectangle.set((rectangle.centerX()+move_x) - rectangle.width()/2,
                (rectangle.centerY()+move_y) - rectangle.height()/2,
                (rectangle.centerX()+move_x) + rectangle.width()/2,
                (rectangle.centerY()+move_y) + rectangle.height()/2);

        int state = 0; // 0 idle, 1 walking , 2 walking left
        if (rectangle.left - oldLeft > 0) {
            state = 1;}
        else if (rectangle.left - oldLeft < 0) {
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();
    }
}
