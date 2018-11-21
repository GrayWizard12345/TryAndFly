package com.example.hamlet.myapplication;

import android.view.View;

import java.util.List;

public class BallMover implements Runnable {

    private List<Ball> balls;
    public static View VIEW;
    public static boolean paused = false;
    public static boolean down = false;
    public BallMover(List<Ball> balls , View view) {
        this.balls = balls;
        VIEW = view;
    }


    @Override
    public void run() {
        while (true)
        {
            for (Ball ball : balls) {
                if(!paused)
                {
                    if(!down)
                        ball.move();
                    else
                        ball.spiralMove();
                }
            }
            VIEW.invalidate();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        BallMover.paused = paused;
    }



}
