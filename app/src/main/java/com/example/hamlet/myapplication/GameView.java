package com.example.hamlet.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    public static final int BALL_COUNT = 200;
    public static final int RASENGAN_RADIUS = 150;
    public static final float BALL_RADIUS = 3f;
    float height, width;
    final static float SPEED = 1;
    Random random;
    private static ArrayList<Ball> balls;

    static Thread moveThread;



    static BallMover ballMover;


    public GameView(Context context, float height, float width) {
        super(context);
        this.height = height;
        this.width = width;
        random = new Random(getDrawingTime());
        balls = new ArrayList<>();
        for (int i = 0; i < BALL_COUNT; i++) {
            Ball ball = new Ball(this, random, BALL_RADIUS, SPEED, width, height);
            ball.setX0(random.nextInt((int) width));
            ball.setY0(random.nextInt((int) height));
            ball.setToX(random.nextInt((int) width));
            ball.setToY(random.nextInt((int) height));
            balls.add(ball);
        }
        ballMover = new BallMover(balls, this);
        moveThread = new Thread(ballMover);
        moveThread.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        for (Ball ball : balls) {
            ball.draw(canvas);
        }
    }


    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public static Thread getMoveThread() {
        return moveThread;
    }

    public static void setMoveThread(Thread moveThread) {
        GameView.moveThread = moveThread;
    }

    public static BallMover getBallMover() {
        return ballMover;
    }

    public static void setBallMover(BallMover ballMover) {
        GameView.ballMover = ballMover;
    }
    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }
}
