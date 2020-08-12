package com.android.camera.permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.log.Log;
import d.d.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionManager {
    private static final int CAM_REQUEST_CODE_ASK_LOCATION_PERMISSIONS = 101;
    private static final int CAM_REQUEST_CODE_ASK_RUNTIME_PERMISSIONS = 100;
    private static final String TAG = "PermissionManager";
    private static List<String> mLaunchPermissionList = new ArrayList();
    private static List<String> sLocationPermissionList = new ArrayList();
    private static List<String> sRuntimePermissions = new ArrayList();
    private static List<String> sStoragePermissionList = new ArrayList();

    static {
        sLocationPermissionList.add("android.permission.ACCESS_FINE_LOCATION");
        sLocationPermissionList.add("android.permission.ACCESS_COARSE_LOCATION");
        sStoragePermissionList.add("android.permission.READ_EXTERNAL_STORAGE");
        sStoragePermissionList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        mLaunchPermissionList.add("android.permission.CAMERA");
        mLaunchPermissionList.add("android.permission.RECORD_AUDIO");
        mLaunchPermissionList.addAll(sStoragePermissionList);
        sRuntimePermissions.addAll(mLaunchPermissionList);
        sRuntimePermissions.addAll(sLocationPermissionList);
    }

    public static boolean checkCameraLaunchPermissions() {
        if (getNeedCheckPermissionList(mLaunchPermissionList).size() > 0) {
            return false;
        }
        Log.i(TAG, "CheckCameraPermissions(), all on");
        return true;
    }

    public static boolean checkCameraLocationPermissions() {
        if (getNeedCheckPermissionList(sLocationPermissionList).size() > 0) {
            return false;
        }
        Log.i(TAG, "checkCameraLocationPermissions(), all on");
        return true;
    }

    public static boolean checkStoragePermissions() {
        if (getNeedCheckPermissionList(sStoragePermissionList).size() > 0) {
            return false;
        }
        Log.i(TAG, "checkStoragePermissions on");
        return true;
    }

    public static int getCameraLocationPermissionRequestCode() {
        return 101;
    }

    public static int getCameraRuntimePermissionRequestCode() {
        return 100;
    }

    private static List<String> getNeedCheckPermissionList(List<String> list) {
        if (list.size() <= 0) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (ContextCompat.checkSelfPermission(CameraAppImpl.getAndroidContext(), str) != 0) {
                Log.i(TAG, "getNeedCheckPermissionList() permission =" + str);
                arrayList.add(str);
                if (!a.ro_boot_hwc_contains_GLOBAL_or_is_android_one && (str.equals("android.permission.ACCESS_COARSE_LOCATION") || str.equals("android.permission.ACCESS_FINE_LOCATION"))) {
                    arrayList.add(CameraAppImpl.getAndroidContext().getString(R.string.location_permission_des));
                }
            }
        }
        Log.i(TAG, "getNeedCheckPermissionList() listSize =" + arrayList.size());
        return arrayList;
    }

    public static boolean isCameraLaunchPermissionsResultReady(String[] strArr, int[] iArr) {
        HashMap hashMap = new HashMap();
        hashMap.put("android.permission.CAMERA", 0);
        hashMap.put("android.permission.RECORD_AUDIO", 0);
        hashMap.put("android.permission.WRITE_EXTERNAL_STORAGE", 0);
        for (int i = 0; i < strArr.length; i++) {
            hashMap.put(strArr[i], Integer.valueOf(iArr[i]));
        }
        return ((Integer) hashMap.get("android.permission.CAMERA")).intValue() == 0 && ((Integer) hashMap.get("android.permission.RECORD_AUDIO")).intValue() == 0 && ((Integer) hashMap.get("android.permission.WRITE_EXTERNAL_STORAGE")).intValue() == 0;
    }

    public static boolean isCameraLocationPermissionsResultReady(String[] strArr, int[] iArr) {
        HashMap hashMap = new HashMap();
        hashMap.put("android.permission.ACCESS_COARSE_LOCATION", 0);
        hashMap.put("android.permission.ACCESS_FINE_LOCATION", 0);
        for (int i = 0; i < strArr.length; i++) {
            hashMap.put(strArr[i], Integer.valueOf(iArr[i]));
        }
        return ((Integer) hashMap.get("android.permission.ACCESS_COARSE_LOCATION")).intValue() == 0 && ((Integer) hashMap.get("android.permission.ACCESS_FINE_LOCATION")).intValue() == 0;
    }

    public static boolean isContainLocationPermissions(String[] strArr) {
        if (strArr != null && strArr.length >= 1) {
            for (String str : strArr) {
                if (sLocationPermissionList.contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean requestCameraLocationPermissions(Activity activity) {
        List<String> needCheckPermissionList = getNeedCheckPermissionList(sLocationPermissionList);
        if (needCheckPermissionList.size() > 0) {
            Log.i(TAG, "requestCameraLocationPermissions(), user check");
            ActivityCompat.requestPermissions(activity, (String[]) needCheckPermissionList.toArray(new String[needCheckPermissionList.size()]), 101);
            return false;
        }
        Log.i(TAG, "requestCameraLocationPermissions(), all on");
        return true;
    }

    public static boolean requestCameraRuntimePermissions(Activity activity) {
        List<String> needCheckPermissionList = getNeedCheckPermissionList(sRuntimePermissions);
        if (needCheckPermissionList.size() > 0) {
            Log.i(TAG, "requestCameraRuntimePermissions(), user check");
            ActivityCompat.requestPermissions(activity, (String[]) needCheckPermissionList.toArray(new String[needCheckPermissionList.size()]), 100);
            return false;
        }
        Log.i(TAG, "requestCameraRuntimePermissions(), all on");
        return true;
    }
}
