package com.zak.zstocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class BouncingBallsView extends View {

    private static class Ball {
        float x, y;
        float radius;
        float vx, vy;
        int color;
    }

    private ArrayList<Ball> balls = new ArrayList<>();
    private Paint paint = new Paint();
    private Random random = new Random();

    public BouncingBallsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        for (int i = 0; i < 18; i++) {
            Ball b = new Ball();

            b.radius = 15 + random.nextInt(25);
            b.x = random.nextInt(1000);
            b.y = random.nextInt(1600);

            b.vx = -4 + random.nextFloat() * 8;
            b.vy = 2 + random.nextFloat() * 6;

            b.color = Color.argb(90,
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255));

            balls.add(b);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Ball b : balls) {

            paint.setColor(b.color);
            canvas.drawCircle(b.x, b.y, b.radius, paint);

            b.x += b.vx;
            b.y += b.vy;

            // bounce left/right
            if (b.x <= 0 || b.x >= getWidth()) {
                b.vx *= -1;
            }

            // bounce top/bottom
            if (b.y <= 0 || b.y >= getHeight()) {
                b.vy *= -1;
            }
        }

        invalidate(); // continuous animation
    }
}
