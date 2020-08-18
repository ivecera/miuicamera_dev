package com.android.camera;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.android.camera.log.Log;
import java.util.Locale;

public class ActivityLauncher {
    public static final String TAG = "ActivityLauncher";
    private static String URL_MIUI_PRIVACY_POLICY = "https://privacy.mi.com/all/";

    private ActivityLauncher() {
    }

    public static void launchPopupCameraSetting(Context context) {
        try {
            context.startActivity(new Intent("android.settings.POPUP_CAMERA_SETTINGS"));
        } catch (Exception unused) {
            Log.w(TAG, "launchPopupCameraSetting error");
        }
    }

    public static void launchPrivacyPolicyWebpage(Context context) {
        String str;
        Intent intent = new Intent("android.intent.action.VIEW");
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (TextUtils.isEmpty(language) || TextUtils.isEmpty(country)) {
            str = URL_MIUI_PRIVACY_POLICY;
        } else {
            str = String.format(URL_MIUI_PRIVACY_POLICY + "%s_%s", language, country);
        }
        intent.setData(Uri.parse(str));
        context.startActivity(intent);
    }
}
