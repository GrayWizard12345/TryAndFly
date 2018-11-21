package com.example.hamlet.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class Ball {

    private Random random;
    private float toX;
    private float toY;
    private Paint paint;
    private float r;
    private float x0;
    private double y0;
    private int id;
    private float a;
    private float b;
    int width;
    int height;
    float speed;
    View view;
    int delay;
    private double length;
    private float xDistance;
    private double yDistance;
    private double dynamicRadius;
    private double t;
    private int constantRadius;

    public Ball(View view, Random random, float r, float speed, float width, float height) {
        this.random = random;
        this.r = r;
        this.speed = speed;
        this.view = view;
        this.width = (int) width;
        this.height = (int) height;
        t = 0;
        constantRadius = random.nextInt(GameView.RASENGAN_RADIUS) + 100;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        x0 = random.nextInt(this.width);
        y0 = random.nextInt(this.height);
        paint.setColor(Color.rgb(random.nextInt(50), random.nextInt(156) + 100, random.nextInt(150) + 70));
        //paint.setColor(Color.BLUE);
    }

    public synchronized void move() {

        if (x0 > toX) {
            if (x0 - toX > 0) {
                x0 -= speed;
                double temp = y0;
                y0 = a * x0 + b;
                if (y0 > height || y0 < 0)
                    y0 = temp;

            }
            else
            {
                toX = random.nextInt(width);
                toY = random.nextInt(height);
                calculateConstants();
            }
        }
        else
        {
            if (toX - x0 > 0) {
                x0 += speed;
                double temp = y0;
                y0 = a * x0 + b;
                if (y0 > height || y0 < 0)
                    y0 = temp;
            }
            else
            {
                toX = random.nextInt(width);
                toY = random.nextInt(height);
                calculateConstants();
            }
        }
        //Log.d("Move event", "Linear move\t" + "\tx = " + toX + "\ty = " + toY);
    }

    public void calculateConstants()
    {
        a = (float) ((y0 - toY)/(x0 - toX));
        b = (float) (-a*x0 + y0);
//        length = Math.sqrt((x0-toX)*(x0-toX) + (y0 - toY)*(y0 - toY));
        xDistance = Math.abs(toX - x0);
        yDistance = Math.abs(toY - y0);
    }

    public synchronized void spiralMove()
    {

//        if(y0 > toY)
//        {
//            x0 += 5;
//            y0 =  (int) Math.sqrt((dynamicRadius + x0 - toX)*(dynamicRadius - x0 + toX)) + toY;
//        }
//        if(y0 == toY)
//        {
//            x0 -= 2;
//            y0 = - (int) Math.sqrt((dynamicRadius + x0 - toX)*(dynamicRadius - x0 + toX)) + toY;
//        }
//
//
//        if (y0 < toY)
//        {
//            x0 -= 5;
//            y0 = - (int) Math.sqrt((dynamicRadius + x0 - toX)*(dynamicRadius - x0 + toX)) + toY;
//        }
//
//        if(y0 == toY)
//        {
//            x0 += 2;
//            y0 = (int) Math.sqrt((dynamicRadius + x0 - toX)*(dynamicRadius - x0 + toX)) + toY;
//        }
        x0 = (int) (dynamicRadius * Math.cos(t)) + toX;
        y0 = (int) dynamicRadius * Math.sin(t) + toY;
        t += 0.05;


        Log.d("Move event", "Spiral move, radius: " + dynamicRadius + "\tY = " + y0);
        if(dynamicRadius > constantRadius)
            dynamicRadius -= random.nextInt(8);
    }

    public double calculateDistanceTo()
    {
        t = Math.atan(xDistance / yDistance);
        if (x0 > toX)
        {
            if(y0 < toY)
                t = 360 - t;
        }else
        {
            if (y0 > toY)
                t = 180 - t;
            else
                t = 180 + t;
        }
        //t = 0;
        return Math.sqrt((x0 - toX)*(x0 - toX) + (int)((y0 - toY)*(y0 - toY)));
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public float getToX() {
        return toX;
    }

    public void setToX(float toX) {
        this.toX = toX;
    }

    public float getToY() {
        return toY;
    }

    public void setToY(float toY) {
        this.toY = toY;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getX0() {
        return x0;
    }

    public void setX0(float x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(float y0) {
        this.y0 = y0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x0, (float) y0, r, paint);
    }


    public double getDynamicRadius() {
        return dynamicRadius;
    }

    public void setDynamicRadius(double dynamicRadius) {
        this.dynamicRadius = dynamicRadius;
    }

}
