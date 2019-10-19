package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    private boolean isPlaying = false;
    private float frameTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animationTime) {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animationTime / frames.length;
        lastFrame = System.currentTimeMillis();
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
        frameIndex = 0;
        isPlaying = true;
        lastFrame = System.currentTimeMillis();
    }

    public void stop() {
        isPlaying = false;
    }

    public void update() {
        if (!isPlaying) {return;}
        if (System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying) {return;}
        canvas.drawBitmap(frames[frameIndex],null, destination, new Paint());
    }

}

