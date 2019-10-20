package com.example.game;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Background implements GameObject {
    private Rect rectangle;
    private Animation idle;
    private AnimationManager animationManager;

    Background() {
        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.grass);
        this.rectangle = new Rect(0,0,Constants.DISPLAY_SIZE.x, Constants.DISPLAY_SIZE.y);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animationManager = new AnimationManager((new Animation[]{idle}));

    }
    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        idle.play();
        animationManager.update();

    }

}
