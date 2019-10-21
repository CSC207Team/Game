package com.example.game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Background background;
    private Player player;
    private Point playerPoint;
    private Monster monster;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;
        Display display = ((MainActivity) Constants.CURRENT_CONTEXT).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Constants.DISPLAY_SIZE = size;

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        player = new Player();
        monster = new Monster();
        background = new Background();
        playerPoint = new Point(size.x/2, size.y);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        try{
            thread.setRunning(false);
            thread.join();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int)event.getX(), (int)event.getY());
        }
        return true;
    }

    public void update() {
        background.update();
        player.update(playerPoint);
        monster.update(player);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        background.draw(canvas);
        player.draw(canvas);
        monster.draw(canvas);
    }
}
