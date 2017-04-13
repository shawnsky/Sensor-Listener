package com.xtlog.shawn.snesorpig;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Shawn on 2017/4/13.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public SurfaceHolder holder;
    public MyThread thread;

    public MySurfaceView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        thread = new MyThread(holder);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = this.getHolder();
        holder.addCallback(this);
        thread = new MyThread(holder);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        holder = this.getHolder();
        holder.addCallback(this);
        thread = new MyThread(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        thread.isRun = true;
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.isRun = false;
    }

    class MyThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;
        private float x, y;
        private int type;

        public void setType(int t) {
            type = t;
        }

        public void setX(float x1) {
            x = x1;
        }

        public void setY(float y1) {
            y = y1;
        }

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            while (isRun) {
                Canvas c = null;
                try {
                    c = holder.lockCanvas();
                    if (type == 0) {
                        c.drawColor(Color.BLACK);
                        Paint p = new Paint();
                        p.setColor(Color.LTGRAY);
                        p.setAlpha(100);
                        float cx = c.getWidth() / 2, cy = c.getHeight() / 2,
                                cr = (c.getWidth() < c.getHeight()) ? c.getWidth() / 2 - 5 :
                                        c.getHeight() / 2 - 20;
                        c.drawCircle(cx, cy, cr, p);
                        p.setColor(Color.WHITE);
                        p.setAlpha(255);
                        c.drawCircle(cx + (float) Math.sin(x) * cr, cy + (float) Math.cos(x) * cr,
                                10, p);
                    } else if (type == 1) {
                        c.drawColor(Color.BLACK);
                        Paint p = new Paint();
                        p.setColor(Color.LTGRAY);
                        p.setAlpha(100);
                        float cx = c.getWidth() / 2, cy = c.getHeight() / 2,
                                cr = (c.getWidth() < c.getHeight()) ?
                                        c.getWidth() / 2 - 20 : c.getHeight() / 2 - 20,
                                cx1, cy1;
                        c.drawCircle(cx, cy, cr, p);
                        c.drawCircle(cx, cy, 30, p);
                        cx1 = -(float) Math.sin(x) * cr + cx;
                        cy1 = -(float) Math.sin(y) * cr + cy;
                        p.setColor(Color.WHITE);
                        p.setAlpha(255);
                        c.drawCircle(cx1, cy1, 10, p);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
}

