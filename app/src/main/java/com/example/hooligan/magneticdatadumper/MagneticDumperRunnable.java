package com.example.hooligan.magneticdatadumper;

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
public class MagneticDumperRunnable extends Thread implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mMagneticSensor;
    private final float[] values = new float[3];

    private int mSamplingCounter = 0;

    private String mLogTag = "MagneticRunnable";
    DataToFileWriter mDataToFileWriter;

    public MagneticDumperRunnable(SensorManager sensorManager) {
        this.mSensorManager = sensorManager;
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }


    @Override
    public void run() {
        Looper.prepare();
        mSensorManager.registerListener(this,mMagneticSensor, Constants.MAGNETIC_SENSOR_SAMPLING_RATE);
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

        if (mSamplingCounter % Constants.MAGNETIC_SENSOR_DOWNSAMPLING_RATE == 0) {
            values[0] = event.values[0];
            values[1] = event.values[1];
            values[2] = event.values[2];
            String toDump = Float.toString(values[0]) + ", "
                    + Float.toString(values[1]) + ", "
                    + Float.toString(values[2]);
            Log.i(mLogTag, toDump);
            mDataToFileWriter = new DataToFileWriter(DataToFileWriter.MODALITY.MAGNETIC);
            mDataToFileWriter.writeToFile("Time, X, Y, Z", false);
            mDataToFileWriter.writeToFile(toDump);
            mDataToFileWriter.closeFile();
        }

        mSamplingCounter += 1;
    }
}
