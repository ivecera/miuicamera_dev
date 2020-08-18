package com.android.camera;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.internal.R;
import com.arcsoft.supernight.SuperNightProcess;
import miui.reflect.b;
import miui.reflect.d;

public class GeneralUtils {
    public static final String STREET_SNAP_CHANNEL_ID = "com.android.camera.streetsnap";

    public static void applyNotchFlag(Window window) {
        d.a(Window.class, window, "addExtraFlags", "(I)V", Integer.valueOf((int) SuperNightProcess.ARC_SN_CAMERA_MODE_XIAOMI_SDM855_12MB_IMX586));
    }

    public static void clearInterpolator(View view) {
    }

    public static int editModeTitleLayout() {
        return R.layout.edit_mode_title;
    }

    public static final boolean isAppLocked(Context context, String str) {
        return false;
    }

    public static b miuiResArrayField(String str, String str2) {
        return b.a(R.array.class, str, str2);
    }

    public static b miuiResBoolField(String str, String str2) {
        return b.a(R.bool.class, str, str2);
    }

    public static int miuiWidgetButtonDialog() {
        return 0;
    }

    public static void notifyForDetail(Context context, Uri uri, String str, String str2, boolean z) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(uri, z ? "video/*" : "image/*");
            Notification.Builder when = new Notification.Builder(context).setContentTitle(str).setContentText(str2).setContentIntent(PendingIntent.getActivity(context, 0, intent, 0)).setSmallIcon(17301569).setWhen(System.currentTimeMillis());
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            CompatibilityUtils.addChannelForNotificationBuilder(notificationManager, "com.android.camera.streetsnap", context.getResources().getString(R.string.camera_label), when);
            Notification build = when.build();
            build.flags |= 16;
            notificationManager.notify(0, build);
        } catch (NullPointerException unused) {
        }
    }
}
