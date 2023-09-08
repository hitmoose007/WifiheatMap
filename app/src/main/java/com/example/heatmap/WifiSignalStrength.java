package com.example.heatmap;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiSignalStrength {
    private Context context;
    private WifiManager wifiManager;

    public WifiSignalStrength(Context context) {
        this.context = context;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    public WifiInfo getWifiInfo() {
        if (isWifiEnabled()) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    public int getSignalStrength() {
        WifiInfo wifiInfo = getWifiInfo();
        if (wifiInfo != null) {
            return wifiInfo.getRssi(); // Returns the signal strength in dBm
        }
        return 0; // Return a default value if WiFi is not enabled or not connected
    }
}
