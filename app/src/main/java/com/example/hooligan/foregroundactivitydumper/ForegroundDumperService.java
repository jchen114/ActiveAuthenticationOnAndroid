package com.example.hooligan.foregroundactivitydumper;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.hooligan.Constants;
import com.example.hooligan.DataToFileWriter;
import com.example.hooligan.SensorDataDumperActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ForegroundDumperService extends Service {

    private ActivityManager mActivityManager;
    private PackageManager mPackageManager;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private static final String mLogTag = "ForegroundService";
    private DataToFileWriter mDataToFileWriter;

    public ForegroundDumperService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Foreground Dumper Service Starting", Toast.LENGTH_SHORT).show();
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mPackageManager = this.getPackageManager();
        Log.i(mLogTag, "Service is starting");
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mDataToFileWriter = new DataToFileWriter(DataToFileWriter.MODALITY.FOREGROUND);
                mDataToFileWriter.writeToFile("Time, Background Apps", false);
                StringBuilder toDump = new StringBuilder();
                List<ActivityManager.RunningAppProcessInfo> processes = mActivityManager.getRunningAppProcesses();
                if (processes.size() > 0) {
                    for (int i = 0; i < processes.size(); i ++) {
                        ActivityManager.RunningAppProcessInfo process = processes.get(i);
                        try {
                            CharSequence appName = mPackageManager.getApplicationLabel(mPackageManager.getApplicationInfo(process.processName, PackageManager.GET_META_DATA));
                            toDump.append(Integer.toString(i) + ": " + (String) appName + " | ");
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i(mLogTag, toDump.toString());
                    mDataToFileWriter.writeToFile(toDump.toString());
                    mDataToFileWriter.closeFile();
                }
            }
        };
        mTimer = new Timer(mLogTag);
        mTimer.schedule(mTimerTask, 0, Constants.FOREGROUND_SAMPLING_RATE);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(mLogTag, "Stopping");
        mTimer.cancel();
        mTimer.purge();
    }
}
