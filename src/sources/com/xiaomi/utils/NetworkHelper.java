package com.xiaomi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkHelper {
    public static final int NET_2G = 2;
    public static final int NET_3G = 3;
    public static final int NET_4G = 4;
    public static final int NET_NO_NET = 1;
    public static final int NET_UNKNOWN = 0;
    public static final int NET_WIFI = 5;

    public static int getAPNType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        int networkType = ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
        if (activeNetworkInfo == null) {
            return 1;
        }
        int type = activeNetworkInfo.getType();
        if (type != 0) {
            return type == 1 ? 5 : 0;
        }
        switch (networkType) {
            case 0:
            case 1:
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 2;
            case 5:
            case 6:
                return 3;
            case 7:
                return 2;
            case 8:
            case 9:
            case 10:
                return 3;
            case 11:
                return 2;
            case 12:
                return 3;
            case 13:
                return 4;
            case 14:
            case 15:
                return 3;
            default:
                return 2;
        }
    }
}
