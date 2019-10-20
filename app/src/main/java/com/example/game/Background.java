package com.example.game;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint;
import android.view.Display;



public class Background implements GameObject {
    private Rect rectangle;
    private Animation idle;
    private AnimationManager animationManager;

    public Background() {
        Display display = ((MainActivity) Constants.CURRENT_CONTEXT).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),
                R.drawable.grass);
        this.rectangle = new Rect(0,0,size.x, size.y);
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
