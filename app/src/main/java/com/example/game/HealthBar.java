package com.example.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Point;

public class HealthBar implements GameObject {
    private int max_health;
    private int curr_health;
    private Rect rectangle;
    private Paint paint;
    private int length;

    HealthBar(int max_health, Point point, int color, int length) {
        this.rectangle = new Rect(point.x - length / 2, point.y, point.x + length / 2, point.y - 5);
        paint = new Paint();
        paint.setColor(color);
        curr_health = max_health;
        this.max_health = max_health;
        this.length = length;

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rectangle, paint);

    }

    @Override
    public void update() {

    }


    void move(int move_x, int move_y) {
        rectangle.set((rectangle.centerX() + move_x) - rectangle.width() / 2,
                (rectangle.centerY() + move_y) - rectangle.height() / 2,
                (rectangle.centerX() + move_x) + rectangle.width() / 2,
                (rectangle.centerY() + move_y) + rectangle.height() / 2);
    }

    void take_damage(int dmg) {
        curr_health -= dmg;
        curr_health = Math.max(0, curr_health);
        float scale = ((float) curr_health / (float) max_health) * length;
        int change = (rectangle.right - (rectangle.left + (int) (scale)))/2;
        rectangle.right = rectangle.right - change;
        rectangle.left = rectangle.left + change;

    }
}

