package com.example.hooligan;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ScoringService extends Service {

    private Timer mAccelerometerScoreTimer;
    private Timer mAirPressureScoreTimer;
    private Timer mAmbientLightScoreTimer;
    private Timer mBatteryScoreTimer;
    private Timer mCallStateScoreTimer;
    private Timer mCameraScoreTimer;
    private Timer mWifiScoreTimer;
    private Timer mCellScoreTimer;
    private Timer mBluetoothScoreTimer;
    private Timer mForegroundScoreTimer;
    private Timer mLocationScoreTimer;
    private Timer mMagneticScoreTimer;
    private Timer mProximityScoreTimer;
    private Timer mRotationScoreTimer;
    private Timer mScreenScoreTimer;
    private Timer mTemperatureScoreTimer;

    private Timer mCumulativeScoreTimer;

    // Sensors
    public static FifoQueue ACCELEROMETER_SCORE = new FifoQueue(5);
    public static FifoQueue AIR_PRESSURE_SCORE = new FifoQueue(5);
    public static FifoQueue AMBIENT_LIGHT_SCORE = new FifoQueue(5);
    public static FifoQueue MAGNETIC_SCORE = new FifoQueue(5);
    public static FifoQueue PROXIMITY_SCORE = new FifoQueue(5);
    public static FifoQueue ROTATION_SCORE = new FifoQueue(5);
    public static FifoQueue TEMPERATURE_SCORE = new FifoQueue(5);
    // Phone
    public static FifoQueue BATTERY_SCORE = new FifoQueue(5);
    public static FifoQueue CALL_STATE_SCORE = new FifoQueue(5);
    public static FifoQueue CAMERA_SCORE = new FifoQueue(5);
    public static FifoQueue WIFI_SCORE = new FifoQueue(5);
    public static FifoQueue CELL_SCORE = new FifoQueue(5);
    public static FifoQueue BLUETOOTH_SCORE = new FifoQueue(5);
    public static FifoQueue FOREGROUND_SCORE = new FifoQueue(5);
    public static FifoQueue LOCATION_SCORE = new FifoQueue(5);
    public static FifoQueue SCREEN_SCORE = new FifoQueue(5);

    private static String mLogTag = "ScoringService";

    public ScoringService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(mLogTag, "On Start Command");
        setupTimers();
        return START_STICKY;
    }

    private void setupTimers() {
        TimerTask mAccelerometerTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateAccelerometerScore();
            }
        };
        mAccelerometerScoreTimer = new Timer("AccelerometerScore");
        mAccelerometerScoreTimer.schedule(mAccelerometerTimerTask, Constants.ACCLEROMETER_SCORING_RATE, Constants.ACCLEROMETER_SCORING_RATE);

        TimerTask mAirPressureTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateAirPressureScore();
            }
        };
        mAirPressureScoreTimer = new Timer("AirPressureScore");
        mAirPressureScoreTimer.schedule(mAirPressureTimerTask, Constants.AIR_PRESSURE_SCORING_RATE, Constants.AIR_PRESSURE_SCORING_RATE);

        TimerTask mAmbientTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateAmbientLightScore();
            }
        };

        mAmbientLightScoreTimer = new Timer("AmbientLightScore");
        mAmbientLightScoreTimer.schedule(mAmbientTimerTask, Constants.AMBIENT_LIGHT_SCORING_RATE, Constants.AMBIENT_LIGHT_SCORING_RATE);

        TimerTask mBatteryTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateBatteryScore();
            }
        };

        mBatteryScoreTimer = new Timer("BatteryScore");
        mBatteryScoreTimer.schedule(mBatteryTimerTask, Constants.BATTERY_SCORING_RATE, Constants.BATTERY_SCORING_RATE);

        TimerTask mCallStateTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateCallScore();
            }
        };

        mCallStateScoreTimer = new Timer("CallStateScore");
        mCallStateScoreTimer.schedule(mCallStateTimerTask, Constants.CALL_SCORING_RATE, Constants.CALL_SCORING_RATE);

        TimerTask mCameraTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateCameraScore();
            }
        };

        mCameraScoreTimer = new Timer("CameraScore");
        mCameraScoreTimer.schedule(mCameraTimerTask, Constants.CAMERA_SCORING_RATE, Constants.CAMERA_SCORING_RATE);

        TimerTask mWifiTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateWifiScore();
            }
        };

        mWifiScoreTimer = new Timer("WifiScore");
        mWifiScoreTimer.schedule(mWifiTimerTask, Constants.WIFI_SCORING_RATE, Constants.WIFI_SCORING_RATE);

        TimerTask mCellTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateCellScore();
            }
        };

        mCellScoreTimer = new Timer("CellScore");
        mCellScoreTimer.schedule(mCellTimerTask, Constants.CELLULAR_SCORING_RATE, Constants.CELLULAR_SCORING_RATE);

        TimerTask mBluetoothTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateBluetoothScore();
            }
        };

        mBluetoothScoreTimer = new Timer("BluetoothScore");
        mBluetoothScoreTimer.schedule(mBluetoothTimerTask, Constants.BLUETOOTH_SCORING_RATE, Constants.BLUETOOTH_SCORING_RATE);

        TimerTask mForegroundTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateForegroundScore();
            }
        };

        mForegroundScoreTimer = new Timer("ForegroundScore");
        mForegroundScoreTimer.schedule(mForegroundTimerTask, Constants.FOREGROUND_SCORING_RATE, Constants.FOREGROUND_SCORING_RATE);

        TimerTask mLocationTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateLocationScore();
            }
        };

        mLocationScoreTimer = new Timer("LocationScore");
        mLocationScoreTimer.schedule(mLocationTimerTask, Constants.LOCATION_SCORING_RATE, Constants.LOCATION_SCORING_RATE);

        TimerTask mMagneticTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateMagneticScore();
            }
        };

        mMagneticScoreTimer = new Timer("MagneticScore");
        mMagneticScoreTimer.schedule(mMagneticTimerTask, Constants.MAGNETIC_SCORING_RATE, Constants.MAGNETIC_SCORING_RATE);

        TimerTask mProximityTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateProximityScore();
            }
        };

        mProximityScoreTimer = new Timer("ProximityScore");
        mProximityScoreTimer.schedule(mProximityTimerTask, Constants.PROXIMITY_SCORING_RATE, Constants.PROXIMITY_SCORING_RATE);

        TimerTask mRotationTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateRotationScore();
            }
        };

        mRotationScoreTimer = new Timer("RotationScore");
        mRotationScoreTimer.schedule(mRotationTimerTask, Constants.ROTATION_SCORING_RATE, Constants.ROTATION_SCORING_RATE);

        TimerTask mScreenTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateScreenScore();
            }
        };

        mScreenScoreTimer = new Timer("ScreenScore");
        mScreenScoreTimer.schedule(mScreenTimerTask,  Constants.SCREEN_SCORING_RATE, Constants.SCREEN_SCORING_RATE);

        TimerTask mTemperatureTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateTemperatureScore();
            }
        };

        mTemperatureScoreTimer = new Timer("TemperatureScore");
        mTemperatureScoreTimer.schedule(mTemperatureTimerTask, Constants.TEMPERATURE_SCORING_RATE, Constants.TEMPERATURE_SCORING_RATE);

        TimerTask mCumulativeTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateCumulativeScore();
            }
        };

        mCumulativeScoreTimer = new Timer("CumulativeScore");
        mCumulativeScoreTimer.schedule(mCumulativeTimerTask, Constants.CUMULATIVE_SCORING_RATE, Constants.CUMULATIVE_SCORING_RATE);

    }

    private void stopTimers() {
        mAccelerometerScoreTimer.cancel();
        mAccelerometerScoreTimer.purge();

        mAirPressureScoreTimer.cancel();
        mAirPressureScoreTimer.purge();

        mAmbientLightScoreTimer.cancel();
        mAmbientLightScoreTimer.purge();

        mBatteryScoreTimer.cancel();
        mBatteryScoreTimer.purge();

        mCallStateScoreTimer.cancel();
        mCallStateScoreTimer.purge();

        mCameraScoreTimer.cancel();
        mCameraScoreTimer.purge();

        mWifiScoreTimer.cancel();
        mWifiScoreTimer.purge();

        mCellScoreTimer.cancel();
        mCellScoreTimer.purge();

        mBluetoothScoreTimer.cancel();
        mBluetoothScoreTimer.purge();

        mForegroundScoreTimer.cancel();
        mForegroundScoreTimer.purge();

        mLocationScoreTimer.cancel();
        mLocationScoreTimer.purge();

        mMagneticScoreTimer.cancel();
        mMagneticScoreTimer.purge();

        mProximityScoreTimer.cancel();
        mProximityScoreTimer.purge();

        mRotationScoreTimer.cancel();
        mRotationScoreTimer.purge();

        mTemperatureScoreTimer.cancel();
        mTemperatureScoreTimer.purge();

        mCumulativeScoreTimer.cancel();
        mCumulativeScoreTimer.purge();
    }

    @Override
    public void onDestroy() {
        stopTimers();
        super.onDestroy();
    }

    private void calculateCumulativeScore() {
        // use the static scores
        Log.i(mLogTag, "Calculate cumulative score");
    }

    private void calculateAccelerometerScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.ACCELEROMETER_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            ACCELEROMETER_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Accelerometer files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setAccelerometerScore();
        } else {
            ACCELEROMETER_SCORE.put(-1);
        }
    }

    private void calculateAirPressureScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.AIR_PRESSURE_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            AIR_PRESSURE_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Air pressure files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setAirPressureScore();
        } else {
            AIR_PRESSURE_SCORE.put(-1);
        }
    }

    private void calculateAmbientLightScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.AMBIENT_LIGHT_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            AMBIENT_LIGHT_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Ambient light files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setAmbientLightScore();
        } else {
            AMBIENT_LIGHT_SCORE.put(-1);
        }
    }

    private void calculateBatteryScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.BATTERY_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            BATTERY_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Battery files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setBatteryLevelScore();
        } else {
            BATTERY_SCORE.put(-1);
        }
    }

    private void calculateCallScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.CELLULAR_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            CALL_STATE_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Call files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setCallStateScore();
        } else {
            CALL_STATE_SCORE.put(-1);
        }
    }

    private void calculateCameraScore() {

        try {
            File[] pictures = new File(SensorDataDumperActivity.mParentDir, Constants.CAMERA_DIR).listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile();
                }
            });
            File[] metaFiles = new File(SensorDataDumperActivity.mParentDir, Constants.META_DIR).listFiles();
            if (pictures.length > 0 && metaFiles.length > 0) {
                // calculate scores here
                CAMERA_SCORE.put(0);
                Log.i(mLogTag, "Pictures files: " + Integer.toString(pictures.length));
                Log.i(mLogTag, "Meta files: " + Integer.toString(metaFiles.length));
            } else {
                CAMERA_SCORE.put(-1);
            }
        } catch (NullPointerException e) {
            Log.i(mLogTag, e.getMessage());
            e.printStackTrace();
        }
    }

    private void calculateWifiScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.WIFI_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            WIFI_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Wifi files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setWifiScore();
        } else {
            WIFI_SCORE.put(-1);
        }
    }

    private void calculateCellScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.CELLULAR_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            CELL_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Cellular files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setCellularScore();
        } else {
            CELL_SCORE.put(-1);
        }
    }

    private void calculateBluetoothScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.BLUETOOTH_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            BLUETOOTH_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Bluetooth files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setBluetoothScore();
        } else {
            BLUETOOTH_SCORE.put(-1);
        }
    }

    private void calculateForegroundScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.FOREGROUND_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            FOREGROUND_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Foreground files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setForegroundScore();
        } else {
            FOREGROUND_SCORE.put(-1);
        }
    }

    private void calculateLocationScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.LOCATION_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            LOCATION_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Location files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setLocationScore();
        } else {
            LOCATION_SCORE.put(-1);
        }
    }

    private void calculateMagneticScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.MAGNETIC_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            MAGNETIC_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Magnetic files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setMagneticScore();
        } else {
            MAGNETIC_SCORE.put(-1);
        }
    }

    private void calculateProximityScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.PROXIMITY_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            PROXIMITY_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Proximity files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setProximityScore();
        } else {
            CAMERA_SCORE.put(-1);
        }
    }

    private void calculateRotationScore() {
        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.ROTATION_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            ROTATION_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Rotation files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setRotationScore();
        } else {
            ROTATION_SCORE.put(-1);
        }
    }

    private void calculateScreenScore() {

        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.SCREEN_DIR);
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            SCREEN_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Screen files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setScreenScore();
        } else {
            SCREEN_SCORE.put(-1);
        }
    }

    private void calculateTemperatureScore() {

        File dir = new File(SensorDataDumperActivity.mParentDir, Constants.TEMPERATURE_DIR);
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length > 0) {
            // calculate score here
            TEMPERATURE_SCORE.put(generateRandomNumber());
            Log.i(mLogTag, "Temperature files: " + Integer.toString(files.length));
            SensorDataDumperActivity.mSensorDataDumperActivity.setTemperatureScore();
        } else {
            TEMPERATURE_SCORE.put(-1);
        }
    }

    private int generateRandomNumber() {
        int randint = (int)(100 * Math.random());
        return randint;
    }
}
