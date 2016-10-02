package com.example.mzx.stepdetector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

/**
 * Created by mzx on 01/10/2016.
 */

public class Pedometer {

    private Context context;
    private Handler handler;
    private SensorEventListener mPedometerListener;
    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private float mDetector;
    private static final int sensorTypeD=Sensor.TYPE_STEP_DETECTOR;

    public Pedometer(Context context, final Handler handler){

        this.context = context;
        this.handler = handler;
        mSensorManager =  (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(sensorTypeD);
        mPedometerListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.sensor.getType() == sensorTypeD) {

                    if (event.values[0] == 1.0) {

                        mDetector++;
                        Message stepsTrue = Message.obtain();
                        stepsTrue.what = 1;
                        handler.sendMessage(stepsTrue);

                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void register(){

        registerPedometer(mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unRegister(){

        mSensorManager.unregisterListener(mPedometerListener);
    }

    public void registerPedometer(Sensor sensor, int rateus){

        mSensorManager.registerListener(mPedometerListener,sensor,rateus);
    }

    public float getmDetector() {

        return mDetector;
    }

    public void clearDetector() {

        mDetector = 0;
    }

    public float getDistance(float stepSize){

        return mDetector*stepSize;
    }



}
