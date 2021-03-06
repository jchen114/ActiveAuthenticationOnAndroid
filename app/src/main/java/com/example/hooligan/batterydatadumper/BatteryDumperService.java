package com.example.hooligan.batterydatadumper;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.hooligan.Constants;
import com.example.hooligan.DataToFileWriter;
import com.example.hooligan.SensorDataDumperActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BatteryDumperService extends Service {

    private static final String mLogTag = "BatteryService";
    Intent batteryIntent;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private DataToFileWriter mDataToFileWriter;

    public BatteryDumperService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Battery Dumper Service Starting", Toast.LENGTH_SHORT).show();
        batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                float level = getBatteryLevel();
                String toDump = Float.toString(level);
                Log.i(mLogTag, toDump);
                mDataToFileWriter = new DataToFileWriter(DataToFileWriter.MODALITY.BATTERY);
                mDataToFileWriter.writeToFile("Time, Level", false);
                mDataToFileWriter.writeToFile(toDump);
                mDataToFileWriter.closeFile();
            }
        };
        mTimer = new Timer(mLogTag);
        mTimer.schedule(mTimerTask, 0, Constants.BATTERY_LEVEL_SAMPLING_RATE);

        return START_STICKY;
    }


    public float getBatteryLevel() {

        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if(level == -1 || scale == -1) {
            return 50.0f;
        }

        return ((float)level / (float)scale) * 100.0f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(mLogTag, "onDestroy");
        mTimer.cancel();
    }
}
