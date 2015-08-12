package com.example.hooligan.temperaturedatadumper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
import android.util.Log;

import com.example.hooligan.Constants;
import com.example.hooligan.DataToFileWriter;

/**
 * Created by Hooligan on 6/22/2015.
 */
public class TemperatureDataRunnable extends Thread implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;
    private DataToFileWriter mDataToFileWriter;
    private String mLogTag = "TemperatureRunnable";

    private int mSamplingCounter = 0;

    public TemperatureDataRunnable(SensorManager sensorManager) throws NullPointerException {
        Log.i(mLogTag, "Runnable create");
        mSensorManager = sensorManager;
        mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (mTemperatureSensor == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public void run() {
        Looper.prepare();
        mSensorManager.registerListener(this, mTemperatureSensor, Constants.TEMPERATURE_SENSOR_SAMPLING_RATE);
        Looper.loop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startDumping() {
        start();
    }

    public void stopDumping() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mSamplingCounter % Constants.TEMPERATURE_SENSOR_DOWNSAMPLING_RATE == 0) {
            Float temp = event.values[0];
            String toDump = Float.toString(temp);
            Log.i(mLogTag, toDump);
            mDataToFileWriter = new DataToFileWriter(DataToFileWriter.MODALITY.TEMPERATURE);
            mDataToFileWriter.writeToFile("Time, Value in C", false);
            mDataToFileWriter.writeToFile(toDump);
            mDataToFileWriter.closeFile();
        }

        mSamplingCounter += 1;

    }
}
