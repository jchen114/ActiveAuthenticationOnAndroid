package com.example.hooligan;

import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Hooligan on 6/15/2015.
 */
public class DataToFileWriter {

    public enum MODALITY {
        ACCELEROMETER, AIR, AMBIENCE, BATTERY, CALL,
        WIFI, CELLULAR, BLUETOOTH, FOREGROUND, LOCATION,
        MAGNETIC, PROXIMITY, ROTATION, SCREEN, TEMPERATURE
    }

    private File mFile;
    private FileWriter mFileWriter;
    private static final String mLogTag = "DataToFileWriter";


    public DataToFileWriter(MODALITY modality) {

        File dir;

        switch (modality) {
            case ACCELEROMETER:
                // Check to see if the directory exists.
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.ACCELEROMETER_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.ACCELEROMETER_WINDOW_SIZE);
                break;
            case AIR:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.AIR_PRESSURE_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.AIR_PRESSURE_WINDOW_SIZE);
                break;
            case AMBIENCE:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.AMBIENT_LIGHT_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.AMBIENT_LIGHT_WINDOW_SIZE);
                break;
            case BATTERY:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.BATTERY_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.BATTERY_WINDOW_SIZE);
                break;
            case CALL:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.CALL_STATE_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.CALL_STATE_WINDOW_SIZE);
                break;
            case WIFI:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.WIFI_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.WIFI_WINDOW_SIZE);
                break;
            case CELLULAR:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.CELLULAR_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.CELLULAR_WINDOW_SIZE);
                break;
            case BLUETOOTH:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.BLUETOOTH_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.BLUETOOTH_WINDOW_SIZE);
                break;
            case FOREGROUND:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.FOREGROUND_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.FOREGROUND_WINDOW_SIZE);
                break;
            case LOCATION:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.LOCATION_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.LOCATION_WINDOW_SIZE);
                break;
            case MAGNETIC:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.MAGNETIC_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.MAGNETIC_WINDOW_SIZE);
                break;
            case PROXIMITY:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.PROXIMITY_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.PROXIMITY_WINDOW_SIZE);
                break;
            case ROTATION:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.ROTATION_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.ROTATION_WINDOW_SIZE);
                break;
            case SCREEN:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.SCREEN_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.SCREEN_WINDOW_SIZE);
                break;
            case TEMPERATURE:
                dir = new File(SensorDataDumperActivity.mParentDir, Constants.TEMPERATURE_DIR);
                dir.mkdir();
                createFileWriter(dir);
                deleteFile(dir, Constants.TEMPERATURE_WINDOW_SIZE);
                break;
        }

    }

    private synchronized void deleteFile(File dir, int windowSize) {
        if (dir == null) {
            return;
        }
        try {
            if (dir.listFiles().length > windowSize) {
                File[] files = dir.listFiles();
                File lastCreated = files[0];
                for (File f : files) {
                    if (f.lastModified() < lastCreated.lastModified()) {
                        lastCreated = f;
                    }
                }
                lastCreated.delete();
            }
        } catch (NullPointerException | SecurityException e) {
            Log.i(mLogTag, e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void createFileWriter(File dir) {
        try {
            String fName = Long.toString(new Timestamp(new Date().getTime()).getTime()) + ".txt"; // Timestamp of file
            mFile = new File(dir, fName);
            mFile.createNewFile();

            if (!mFile.canWrite()) {
                mFile.setWritable(true);
            }
            mFileWriter = new FileWriter(mFile, true);
            Log.i(mLogTag, mFile.getPath());
        } catch (IOException | NullPointerException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean writeToFile(String toWrite, Boolean timestamp) {
        try {
            Date date = new Date();
            if (mFileWriter == null) {
                mFileWriter = new FileWriter(mFile, true);
            }
            if (timestamp) {
                mFileWriter.append(new Timestamp(date.getTime()).getTime() + ", " + toWrite + "\r\n");
            } else {
                mFileWriter.append(toWrite + "\r\n");
            }
            mFileWriter.flush();
        } catch (IOException | NullPointerException e) {
            Log.i(mLogTag, "Error writing to file");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean writeToFile(String toWrite) {
        try {
            Date date = new Date();
            if (mFileWriter == null) {
                mFileWriter = new FileWriter(mFile, true);
            }
            mFileWriter.append(new Timestamp(date.getTime()).getTime() + ", " + toWrite + "\r\n");
            mFileWriter.flush();
        } catch (IOException | NullPointerException e) {
            Log.i(mLogTag, "Error writing to file");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean closeFile() {
        try {
            mFileWriter.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
