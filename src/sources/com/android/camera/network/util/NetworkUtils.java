package com.android.camera.network.util;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.camera.CameraSettings;
import com.android.camera.network.live.TTLiveStickerResourceRequest;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.ResponseListener;
import com.ss.android.ugc.effectmanager.common.EffectConstants;

public class NetworkUtils {
    private static boolean DBG = false;
    private static final String TAG = "NetworkUtils";
    private static Application sApp;

    private NetworkUtils() {
    }

    public static void bind(Application application) {
        sApp = application;
    }

    public static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) sApp.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isActiveNetworkMetered() {
        ConnectivityManager connectivityManager = (ConnectivityManager) sApp.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        return connectivityManager.isActiveNetworkMetered();
    }

    public static boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) sApp.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static void tryRequestTTSticker() {
        new TTLiveStickerResourceRequest(CameraSettings.isLiveStickerInternalChannel() ? EffectConstants.CHANNEL_LOCAL_TEST : "default", "default").execute(new ResponseListener() {
            /* class com.android.camera.network.util.NetworkUtils.AnonymousClass1 */

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponse(Object... objArr) {
            }

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponseError(ErrorCode errorCode, String str, Object obj) {
            }
        });
    }
}
