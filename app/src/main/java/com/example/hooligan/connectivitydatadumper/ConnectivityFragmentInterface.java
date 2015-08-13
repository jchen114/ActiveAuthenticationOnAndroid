package com.example.hooligan.connectivitydatadumper;

import android.view.View;

/**
 * Created by Hooligan on 6/3/2015.
 */

public interface ConnectivityFragmentInterface {

    void turnOnService();
    void turnOffService();
    void didPressConnectivityButton(View v);
    void setWifiScore(int score);
    void setCellularScore(int score);
    void setBluetoothScore(int score);
}
