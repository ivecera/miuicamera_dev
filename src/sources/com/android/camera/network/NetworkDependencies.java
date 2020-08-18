package com.android.camera.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.camera.network.net.HttpManager;
import com.android.camera.network.util.NetworkUtils;
import java.io.File;

public class NetworkDependencies {
    public static void depend(Application application) {
        HttpManager.getInstance().initRequestQueue(application);
        NetworkUtils.bind(application);
    }

    public static File getRequestCache(Context context) {
        return context.getExternalCacheDir();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (!(connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null)) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
