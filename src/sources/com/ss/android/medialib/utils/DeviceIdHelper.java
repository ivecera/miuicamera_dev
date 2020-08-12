package com.ss.android.medialib.utils;

import android.content.Context;
import com.ss.android.medialib.util.SharedPrefUtil;
import java.util.UUID;

public class DeviceIdHelper {
    public static String getIdentity(Context context) {
        String deviceId = SharedPrefUtil.getDeviceId(context);
        if (deviceId != null) {
            return deviceId;
        }
        String uuid = UUID.randomUUID().toString();
        SharedPrefUtil.setDeviceId(context, uuid);
        return uuid;
    }
}
