package com.example.hamlet.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    float ax, ay;
    GameView gameView;
    static int delay;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        delay = 250;
        gameView = new GameView(this, size.y, size.x);
        gameView.setOnTouchListener(handleTouch);
        setContentView(gameView);
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        assert sensorManager != null;
//        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//
//        sensorManager.registerListener(this,
//                sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            int x = (int) ax;
            int y = (int) ay;
            //Log.d("Rotation Senor", "x:" + x + " y:" + y);
            //drawCircle.move(x,y);
            //drawCircle.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            v.performClick();
            final float x = event.getX();
            final float y = event.getY();
            Log.d("Screen touched", "x - " + x + " y - " + y);
            //gameView.move(x,y);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_MOVE:
                    Log.d("Screen event", "Action Down");
                    BallMover.setPaused(true);
                    BallMover.down = true;
                    for (Ball ball : gameView.getBalls()) {
                        ball.setToX(x);
                        ball.setToY(y);
                        ball.calculateConstants();
                        ball.setDynamicRadius(ball.calculateDistanceTo());
                    }
                    BallMover.setPaused(false);
                    break;

                case MotionEvent.ACTION_UP:
                    Log.d("Screen event", "Action UP");
                    BallMover.down = false;
                    BallMover.setPaused(true);
                    for (Ball ball : gameView.getBalls()) {
                        ball.setToX(gameView.random.nextInt((int) gameView.width));
                        ball.setToY(gameView.random.nextInt((int) gameView.height));
                        ball.calculateConstants();
                    }
                    BallMover.setPaused(false);
                    break;
            }
            return true;
        }
    };
}
