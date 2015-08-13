package com.example.hooligan;

/**
 * Created by Hooligan on 8/7/2015.
 */
public class Constants {
    /**
     * Sensor sampling rates. These are not definite, they are more of hints to the system.
     * See --> http://developer.android.com/reference/android/hardware/SensorManager.html#registerListener(android.hardware.SensorEventListener, android.hardware.Sensor, int)
     */
    // These are in microseconds
    public static int ACCELEROMETER_SENSOR_SAMPLING_RATE = 2000000;
    public static int AIR_PRESSURE_SENSOR_SAMPLING_RATE = 2000000;
    public static int AMBIENT_LIGHT_SENSOR_SAMPLING_RATE = 3000000;
    public static int MAGNETIC_SENSOR_SAMPLING_RATE = 2000000;
    public static int PROXIMITY_SENSOR_SAMPLING_RATE = 2000000;
    public static int ROTATION_SENSOR_SAMPLING_RATE = 2000000;
    public static int TEMPERATURE_SENSOR_SAMPLING_RATE = 2000000;

    // These are in milliseconds, these are precise.
    public static int BATTERY_LEVEL_SAMPLING_RATE = 3000;
    public static int CAMERA_CAPTURE_RATE = 7000; // Be careful with this, because the camera needs enough time to manage its resources.
    public static int WIFI_CELLULAR_SAMPLING_RATE = 5000;
    public static int BLUETOOTH_SAMPLING_RATE = 5000;
    public static int FOREGROUND_SAMPLING_RATE = 3000;
    public static int LOCATION_FASTEST_SAMPLING_RATE = 5000;
    public static int LOCATION_SAMPLING_RATE = 10000; // Must be bigger than LOCATION_FASTEST_SAMPLING_RATEj

    // Call state, Screen state do NOT have sampling rates since they are broadcasted by the system to any listeners.

    /**
     * Sensor Downsampling rates.
     */
    public static int ACCELEROMETER_SENSOR_DOWNSAMPLING_RATE = 2;
    public static int AIR_PRESSURE_SENSOR_DOWNSAMPLING_RATE = 1;
    public static int AMBIENT_LIGHT_SENSOR_DOWNSAMPLING_RATE = 1;
    public static int ROTATION_SENSOR_DOWNSAMPLING_RATE = 1;
    public static int MAGNETIC_SENSOR_DOWNSAMPLING_RATE = 1;
    public static int PROXIMITY_SENSOR_DOWNSAMPLING_RATE = 1;
    public static int TEMPERATURE_SENSOR_DOWNSAMPLING_RATE = 1;

    // Every other modalities can just be changed with the sampling rate.

    /**
     * Window sizes for each modality
     */
    public static int ACCELEROMETER_WINDOW_SIZE = 10;
    public static int AIR_PRESSURE_WINDOW_SIZE = 10;
    public static int AMBIENT_LIGHT_WINDOW_SIZE = 10;
    public static int MAGNETIC_WINDOW_SIZE = 10;
    public static int PROXIMITY_WINDOW_SIZE = 10;
    public static int ROTATION_WINDOW_SIZE = 10;
    public static int TEMPERATURE_WINDOW_SIZE = 10;
    public static int CALL_STATE_WINDOW_SIZE = 10;
    public static int BATTERY_WINDOW_SIZE = 10;
    public static int CAMERA_WINDOW_SIZE = 20;
    public static int WIFI_WINDOW_SIZE = 10;
    public static int CELLULAR_WINDOW_SIZE = 10;
    public static int BLUETOOTH_WINDOW_SIZE = 10;
    public static int FOREGROUND_WINDOW_SIZE = 10;
    public static int LOCATION_WINDOW_SIZE = 10;
    public static int SCREEN_WINDOW_SIZE = 10;

    /**
     * Scoring
     */
    public static int ACCLEROMETER_SCORING_RATE = 5000;
    public static int AIR_PRESSURE_SCORING_RATE = 5000;
    public static int AMBIENT_LIGHT_SCORING_RATE = 5000;
    public static int MAGNETIC_SCORING_RATE = 5000;
    public static int PROXIMITY_SCORING_RATE = 5000;
    public static int ROTATION_SCORING_RATE = 5000;
    public static int TEMPERATURE_SCORING_RATE = 5000;
    public static int CALL_SCORING_RATE = 5000;
    public static int BATTERY_SCORING_RATE = 5000;
    public static int CAMERA_SCORING_RATE = 5000;
    public static int WIFI_SCORING_RATE = 5000;
    public static int CELLULAR_SCORING_RATE = 5000;
    public static int BLUETOOTH_SCORING_RATE = 5000;
    public static int FOREGROUND_SCORING_RATE = 5000;
    public static int LOCATION_SCORING_RATE = 5000;
    public static int SCREEN_SCORING_RATE = 5000;
    public static int CUMULATIVE_SCORING_RATE = 10000;

    /**
     * Directory names
     */
    public static String ACCELEROMETER_DIR = "Accelerometer";
    public static String AIR_PRESSURE_DIR = "Air_Pressure";
    public static String AMBIENT_LIGHT_DIR = "Ambient_Light";
    public static String MAGNETIC_DIR = "Magnetic";
    public static String PROXIMITY_DIR = "Proximity";
    public static String ROTATION_DIR = "Rotation";
    public static String TEMPERATURE_DIR = "Temperature";
    public static String CALL_STATE_DIR = "Call_State";
    public static String BATTERY_DIR = "Battery";
    public static String CAMERA_DIR = "Camera";
    public static String META_DIR = "Camera/Meta";
    public static String WIFI_DIR = "Wifi";
    public static String CELLULAR_DIR = "Cell";
    public static String BLUETOOTH_DIR = "Bluetooth";
    public static String FOREGROUND_DIR = "Foreground";
    public static String LOCATION_DIR = "Location";
    public static String SCREEN_DIR = "Screen_State";

}
