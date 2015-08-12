package com.example.hooligan.proximitydatadumper;

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
public class ProximityDataRunnable extends Thread implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private DataToFileWriter mDataToFileWriter;
    private String mLogTag = "ProximityRunnable";
    private int mSamplingCounter = 0;

    public ProximityDataRunnable(SensorManager sensorManager) {
        mSensorManager = sensorManager;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    @Override
    public void run() {
        Looper.prepare();
        mSensorManager.registerListener(this, mSensor, Constants.PROXIMITY_SENSOR_SAMPLING_RATE);
        Looper.loop();
    }

    public void startDumping() {
        start();
    }

    public void stopDumping() {
        mSensorManager.unregisterListener(this);
        mDataToFileWriter.closeFile();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mSamplingCounter % Constants.PROXIMITY_SENSOR_DOWNSAMPLING_RATE == 0) {
            float distance = event.values[0];
            String toDump = distance > 0 ? "F" : "N";
            Log.i(mLogTag, toDump);
            mDataToFileWriter = new DataToFileWriter(DataToFileWriter.MODALITY.PROXIMITY);
            mDataToFileWriter.writeToFile("Time, Distance", false);
            mDataToFileWriter.writeToFile(toDump);
        }

        mSamplingCounter += 1;
    }
}
