package com.example.hooligan.airpressuredatadumper;

import android.app.usage.ConfigurationStats;
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
public class AirPressureDataRunnable extends Thread implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private DataToFileWriter mDataToFileWriter;
    private String mLogTag = "AirPressureRunnable";

    private int mSamplingCounter = 0;

    public AirPressureDataRunnable(SensorManager sensorManager) throws NullPointerException {
        mSensorManager = sensorManager;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (mSensor == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public void run() {
        Looper.prepare();
        mSensorManager.registerListener(this, mSensor, Constants.AIR_PRESSURE_SENSOR_SAMPLING_RATE);
        Looper.loop();
    }

    public void startDumping() {
        start();
    }

    public void stopDumping() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mSamplingCounter % Constants.AIR_PRESSURE_SENSOR_DOWNSAMPLING_RATE == 0) {
            float pressure = event.values[0];
            String toDump = Float.toString(pressure);
            Log.i(mLogTag, toDump);
            mDataToFileWriter = new DataToFileWriter(DataToFileWriter.MODALITY.AIR);
            mDataToFileWriter.writeToFile("Time, Pressure", false);
            mDataToFileWriter.writeToFile(toDump);
            mDataToFileWriter.closeFile();
        }

        mSamplingCounter += 1;

    }
}
