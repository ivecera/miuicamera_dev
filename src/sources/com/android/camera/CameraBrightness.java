package com.android.camera;

import android.app.Activity;
import android.content.ContentResolver;
import android.hardware.display.DisplayManager;
import android.miui.R;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Spline;
import android.view.WindowManager;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.internal.R;
import d.g.c;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import miui.reflect.NoSuchFieldException;
import miui.reflect.b;

public class CameraBrightness implements CameraBrightnessCallback {
    private static final float SCREEN_AUTO_BRIGHTNESS_RATIO = 0.5f;
    private static final String TAG = "CameraBrightness";
    private int mBrightness = -1;
    private int mBrightnessMode = -1;
    private AsyncTask<Void, Void, Integer> mCameraBrightnessTask;
    private Activity mCurrentActivity;
    private DisplayManager mDisplayManager;
    private boolean mFirstFocusChanged;
    private boolean mPaused;
    private float mScreenAutoBrightnessRatio = 0.0f;
    private boolean mUseDefaultValue;

    private static class CameraBrightnessTask extends AsyncTask<Void, Void, Integer> {
        private final float ADJUST_RATIO_BASE = 0.1f;
        private final float ADJUST_RATIO_RANGE = 0.3f;
        private final boolean DEBUG = false;
        private WeakReference<Activity> mActivityWeakReference;
        private int mBrightnessMode = -1;
        private WeakReference<CameraBrightnessCallback> mCallbackWeakReference;
        private boolean mPaused;
        private Spline mPositiveScreenManualBrightnessSpline;
        private float mScreenAutoBrightnessRatioInner;
        private Spline mScreenManualBrightnessSpline;
        private boolean mUseDefaultValue;

        public CameraBrightnessTask(Activity activity, CameraBrightnessCallback cameraBrightnessCallback, boolean z, boolean z2, float f2) {
            this.mActivityWeakReference = new WeakReference<>(activity);
            this.mCallbackWeakReference = new WeakReference<>(cameraBrightnessCallback);
            this.mPaused = z2;
            this.mUseDefaultValue = z;
            this.mScreenAutoBrightnessRatioInner = f2;
        }

        private Spline createManualBrightnessSpline(int[] iArr, int[] iArr2) {
            try {
                int length = iArr.length;
                float[] fArr = new float[length];
                float[] fArr2 = new float[length];
                for (int i = 0; i < length; i++) {
                    fArr[i] = (float) iArr[i];
                    fArr2[i] = (float) iArr2[i];
                }
                return new MonotoneCubicSpline(fArr, fArr2);
            } catch (IllegalArgumentException e2) {
                Log.e(CameraBrightness.TAG, "Could not create manual-brightness spline.", e2);
                return null;
            }
        }

        private void createSpline() {
            if ((this.mScreenManualBrightnessSpline == null || this.mPositiveScreenManualBrightnessSpline == null) && getAndroidBoolRes("config_manual_spline_available", true)) {
                int[] androidArrayRes = getAndroidArrayRes("config_manualBrightnessRemapIn");
                int[] androidArrayRes2 = getAndroidArrayRes("config_manualBrightnessRemapOut");
                this.mScreenManualBrightnessSpline = createManualBrightnessSpline(androidArrayRes2, androidArrayRes);
                this.mPositiveScreenManualBrightnessSpline = createManualBrightnessSpline(androidArrayRes, androidArrayRes2);
                if (this.mScreenManualBrightnessSpline == null || this.mPositiveScreenManualBrightnessSpline == null) {
                    Log.e(CameraBrightness.TAG, "Error to create manual brightness spline");
                }
            }
        }

        private String execCommand(String str) {
            long currentTimeMillis = System.currentTimeMillis();
            try {
                Process exec = Runtime.getRuntime().exec(str);
                if (exec.waitFor() != 0) {
                    Log.e(CameraBrightness.TAG, "exit value = " + exec.exitValue());
                    return "";
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        stringBuffer.append(readLine);
                    } else {
                        bufferedReader.close();
                        String stringBuffer2 = stringBuffer.toString();
                        Log.v(CameraBrightness.TAG, "execCommand lcd value=" + stringBuffer2 + " cost=" + (System.currentTimeMillis() - currentTimeMillis));
                        return stringBuffer2;
                    }
                }
            } catch (InterruptedException e2) {
                Log.e(CameraBrightness.TAG, "execCommand InterruptedException");
                e2.printStackTrace();
                return "";
            } catch (IOException e3) {
                Log.e(CameraBrightness.TAG, "execCommand IOException");
                e3.printStackTrace();
                return "";
            }
        }

        private int[] getAndroidArrayRes(String str) {
            try {
                return CameraAppImpl.getAndroidContext().getResources().getIntArray(b.a(R.array.class, str, b.iy).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(CameraBrightness.TAG, e2.getMessage());
                try {
                    return CameraAppImpl.getAndroidContext().getResources().getIntArray(GeneralUtils.miuiResArrayField(str, b.iy).getInt(null));
                } catch (NoSuchFieldException e3) {
                    Log.e(CameraBrightness.TAG, e3.getMessage());
                    return new int[]{0, 255};
                } catch (IllegalArgumentException e4) {
                    Log.e(CameraBrightness.TAG, e4.getMessage());
                    return new int[]{0, 255};
                }
            } catch (IllegalArgumentException e5) {
                Log.e(CameraBrightness.TAG, e5.getMessage());
                return CameraAppImpl.getAndroidContext().getResources().getIntArray(GeneralUtils.miuiResArrayField(str, b.iy).getInt(null));
            }
        }

        private boolean getAndroidBoolRes(String str, boolean z) {
            try {
                return CameraAppImpl.getAndroidContext().getResources().getBoolean(b.a(R.bool.class, str, b.iy).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(CameraBrightness.TAG, e2.getMessage());
                try {
                    return CameraAppImpl.getAndroidContext().getResources().getBoolean(GeneralUtils.miuiResBoolField(str, b.iy).getInt(null));
                } catch (NoSuchFieldException e3) {
                    Log.e(CameraBrightness.TAG, e3.getMessage());
                    return z;
                } catch (IllegalArgumentException e4) {
                    Log.e(CameraBrightness.TAG, e4.getMessage());
                    return z;
                }
            } catch (IllegalArgumentException e5) {
                Log.e(CameraBrightness.TAG, e5.getMessage());
                return CameraAppImpl.getAndroidContext().getResources().getBoolean(GeneralUtils.miuiResBoolField(str, b.iy).getInt(null));
            }
        }

        private int getAndroidIntResource(String str) {
            try {
                return CameraAppImpl.getAndroidContext().getResources().getInteger(b.a(R.integer.class, str, b.iy).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(CameraBrightness.TAG, e2.getMessage());
                return 0;
            } catch (IllegalArgumentException e3) {
                Log.e(CameraBrightness.TAG, e3.getMessage());
                return 0;
            }
        }

        @Nullable
        private Integer getBrightIsAndroidP(WindowManager.LayoutParams layoutParams, Activity activity) {
            ContentResolver contentResolver = activity.getContentResolver();
            try {
                this.mBrightnessMode = Settings.System.getInt(contentResolver, "screen_brightness_mode");
            } catch (Settings.SettingNotFoundException unused) {
            }
            CameraBrightnessCallback cameraBrightnessCallback = this.mCallbackWeakReference.get();
            if (com.mi.config.b.deviceIsPocoXx && cameraBrightnessCallback != null) {
                if (!this.mPaused && !this.mUseDefaultValue) {
                    cameraBrightnessCallback.setPreviousBrightnessMode(this.mBrightnessMode);
                    if (this.mBrightnessMode == 1) {
                        Settings.System.putInt(contentResolver, "screen_brightness_mode", 0);
                    }
                } else if (cameraBrightnessCallback.getPreviousBrightnessMode() == 1) {
                    Settings.System.putInt(contentResolver, "screen_brightness_mode", 1);
                }
            }
            if (cameraBrightnessCallback != null && this.mBrightnessMode == 1) {
                if (!this.mUseDefaultValue && !this.mPaused && this.mScreenAutoBrightnessRatioInner == 0.0f) {
                    Log.d(CameraBrightness.TAG, "adjustBrightnessInAutoMode(0.5)");
                    cameraBrightnessCallback.adjustBrightnessInAutoMode(0.5f);
                } else if (this.mScreenAutoBrightnessRatioInner == 0.5f) {
                    Log.d(CameraBrightness.TAG, "adjustBrightnessInAutoMode(0)");
                    cameraBrightnessCallback.adjustBrightnessInAutoMode(0.0f);
                }
                cameraBrightnessCallback.setBrightness((int) layoutParams.screenBrightness);
                return null;
            } else if (this.mBrightnessMode != 0) {
                return null;
            } else {
                if (this.mUseDefaultValue || this.mPaused) {
                    return -1;
                }
                try {
                    int i = c.c(PowerManager.class, "BRIGHTNESS_ON").getInt(PowerManager.class);
                    int i2 = Settings.System.getInt(activity.getContentResolver(), "screen_brightness");
                    Log.d(CameraBrightness.TAG, "android P -> current back light -> " + i2);
                    float enLargeBrightness = (float) toEnLargeBrightness((int) Math.ceil((double) (((float) (i2 * 256)) / ((float) (i + 1)))));
                    float f2 = layoutParams.screenBrightness;
                    if (f2 <= 0.0f || enLargeBrightness != ((float) ((int) (f2 * 255.0f)))) {
                        return Integer.valueOf((int) enLargeBrightness);
                    }
                    Log.v(CameraBrightness.TAG, "android P -> doInBackground brightness unchanged");
                    return null;
                } catch (Exception e2) {
                    Log.e(CameraBrightness.TAG, e2.toString());
                    return null;
                }
            }
        }

        @Nullable
        private Integer getBrightNotAndroidP(WindowManager.LayoutParams layoutParams) {
            int currentBackLight = getCurrentBackLight();
            Log.d(CameraBrightness.TAG, "current back light -> " + currentBackLight);
            if (currentBackLight <= 0) {
                return null;
            }
            createSpline();
            float f2 = layoutParams.screenBrightness;
            if (f2 > 0.0f) {
                float f3 = f2 * 255.0f;
                Spline spline = this.mPositiveScreenManualBrightnessSpline;
                if (Math.abs((spline != null ? Math.round(spline.interpolate(f3)) : Math.round(f3)) - currentBackLight) <= 1) {
                    Log.v(CameraBrightness.TAG, "doInBackground brightness unchanged");
                    return null;
                }
            }
            Spline spline2 = this.mScreenManualBrightnessSpline;
            if (spline2 != null) {
                currentBackLight = (int) spline2.interpolate((float) currentBackLight);
            }
            return Integer.valueOf(toEnLargeBrightness(currentBackLight));
        }

        private int getCurrentBackLight() {
            String str = null;
            int i = 0;
            while (true) {
                if (("0".equals(str) || str == null) && i < 3 && !isCancelled()) {
                    str = execCommand("cat sys/class/backlight/panel0-backlight/brightness");
                    if ("0".equals(str) || str == null) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e2) {
                            Log.e(CameraBrightness.TAG, e2.getMessage());
                        }
                        i++;
                    }
                }
            }
            Log.v(CameraBrightness.TAG, "getCurrentBackLight currentSetting=" + str);
            if (!TextUtils.isEmpty(str)) {
                int androidIntResource = getAndroidIntResource("config_backlightBits");
                if (androidIntResource <= 0) {
                    androidIntResource = getMiuiIntResource("config_backlightBit");
                }
                int parseFloat = (int) Float.parseFloat(str);
                if (androidIntResource > 8) {
                    int i2 = androidIntResource - 8;
                    int i3 = 1;
                    if (parseFloat >= (1 << i2)) {
                        i3 = parseFloat >> i2;
                    }
                    Log.v(CameraBrightness.TAG, "getCurrentBackLight convert to " + i3);
                    return i3;
                }
            }
            return -1;
        }

        private int getMiuiIntResource(String str) {
            try {
                return CameraAppImpl.getAndroidContext().getResources().getInteger(b.a(R.integer.class, str, b.iy).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(CameraBrightness.TAG, e2.getMessage());
                return 0;
            } catch (IllegalArgumentException e3) {
                Log.e(CameraBrightness.TAG, e3.getMessage());
                return 0;
            }
        }

        private int toEnLargeBrightness(int i) {
            return Integer.valueOf(Util.clamp((int) (((double) i) * (((double) (((((float) Util.clamp(i, 0, 185)) / 185.0f) * 0.3f) + 0.1f)) + 1.0d)), 0, 255)).intValue();
        }

        private void updateBrightness(int i) {
            Activity activity = this.mActivityWeakReference.get();
            if (activity != null) {
                WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
                if (this.mUseDefaultValue || this.mPaused) {
                    attributes.screenBrightness = -1.0f;
                } else {
                    attributes.screenBrightness = ((float) i) / 255.0f;
                }
                Log.v(CameraBrightness.TAG, "updateBrightness setting=" + i + " useDefaultValue=" + this.mUseDefaultValue + " screenBrightness=" + attributes.screenBrightness);
                activity.getWindow().setAttributes(attributes);
                CameraBrightnessCallback cameraBrightnessCallback = this.mCallbackWeakReference.get();
                if (cameraBrightnessCallback != null) {
                    cameraBrightnessCallback.setBrightness(i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... voidArr) {
            Log.v(CameraBrightness.TAG, "doInBackground useDefaultValue=" + this.mUseDefaultValue + " paused=" + this.mPaused);
            Activity activity = this.mActivityWeakReference.get();
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            if (Build.VERSION.SDK_INT >= 28) {
                return getBrightIsAndroidP(attributes, activity);
            }
            if (activity != null) {
                return getBrightNotAndroidP(attributes);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            if (num != null) {
                CameraBrightnessCallback cameraBrightnessCallback = this.mCallbackWeakReference.get();
                if (cameraBrightnessCallback != null) {
                    cameraBrightnessCallback.setBrightness(num.intValue());
                }
                if (!isCancelled()) {
                    updateBrightness(num.intValue());
                }
            }
        }
    }

    public static class MonotoneCubicSpline extends Spline {
        private float[] mM;
        private float[] mX;
        private float[] mY;

        public MonotoneCubicSpline(float[] fArr, float[] fArr2) {
            if (fArr == null || fArr2 == null || fArr.length != fArr2.length || fArr.length < 2) {
                throw new IllegalArgumentException("There must be at least two control points and the arrays must be of equal length.");
            }
            int length = fArr.length;
            int i = length - 1;
            float[] fArr3 = new float[i];
            float[] fArr4 = new float[length];
            int i2 = 0;
            while (i2 < i) {
                int i3 = i2 + 1;
                float f2 = fArr[i3] - fArr[i2];
                if (f2 > 0.0f) {
                    fArr3[i2] = (fArr2[i3] - fArr2[i2]) / f2;
                    i2 = i3;
                } else {
                    throw new IllegalArgumentException("The control points must all have strictly increasing X values.");
                }
            }
            fArr4[0] = fArr3[0];
            for (int i4 = 1; i4 < i; i4++) {
                fArr4[i4] = (fArr3[i4 - 1] + fArr3[i4]) * 0.5f;
            }
            fArr4[i] = fArr3[length - 2];
            for (int i5 = 0; i5 < i; i5++) {
                if (fArr3[i5] == 0.0f) {
                    fArr4[i5] = 0.0f;
                    fArr4[i5 + 1] = 0.0f;
                } else {
                    float f3 = fArr4[i5] / fArr3[i5];
                    int i6 = i5 + 1;
                    float f4 = fArr4[i6] / fArr3[i5];
                    if (f3 < 0.0f || f4 < 0.0f) {
                        throw new IllegalArgumentException("The control points must have monotonic Y values.");
                    }
                    float hypot = (float) Math.hypot((double) f3, (double) f4);
                    if (hypot > 9.0f) {
                        float f5 = 3.0f / hypot;
                        fArr4[i5] = f3 * f5 * fArr3[i5];
                        fArr4[i6] = f5 * f4 * fArr3[i5];
                    }
                }
            }
            this.mX = fArr;
            this.mY = fArr2;
            this.mM = fArr4;
        }

        public float interpolate(float f2) {
            int length = this.mX.length;
            if (Float.isNaN(f2)) {
                return f2;
            }
            float[] fArr = this.mX;
            int i = 0;
            if (f2 <= fArr[0]) {
                return this.mY[0];
            }
            int i2 = length - 1;
            if (f2 >= fArr[i2]) {
                return this.mY[i2];
            }
            while (true) {
                float[] fArr2 = this.mX;
                int i3 = i + 1;
                if (f2 < fArr2[i3]) {
                    float f3 = fArr2[i3] - fArr2[i];
                    float f4 = (f2 - fArr2[i]) / f3;
                    float[] fArr3 = this.mY;
                    float f5 = 2.0f * f4;
                    float[] fArr4 = this.mM;
                    float f6 = (fArr3[i] * (f5 + 1.0f)) + (fArr4[i] * f3 * f4);
                    float f7 = 1.0f - f4;
                    return (f6 * f7 * f7) + (((fArr3[i3] * (3.0f - f5)) + (f3 * fArr4[i3] * (f4 - 1.0f))) * f4 * f4);
                } else if (f2 == fArr2[i3]) {
                    return this.mY[i3];
                } else {
                    i = i3;
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int length = this.mX.length;
            sb.append("MonotoneCubicSpline{[");
            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append("(");
                sb.append(this.mX[i]);
                sb.append(", ");
                sb.append(this.mY[i]);
                sb.append(": ");
                sb.append(this.mM[i]);
                sb.append(")");
            }
            sb.append("]}");
            return sb.toString();
        }
    }

    public CameraBrightness(Activity activity) {
        this.mCurrentActivity = activity;
        this.mUseDefaultValue = true;
        this.mFirstFocusChanged = false;
        this.mDisplayManager = (DisplayManager) CameraAppImpl.getAndroidContext().getSystemService(DisplayManager.class);
    }

    private void adjustBrightness() {
        if (this.mCurrentActivity != null && com.mi.config.b.Qk()) {
            cancelLastTask();
            this.mCameraBrightnessTask = new CameraBrightnessTask(this.mCurrentActivity, this, this.mUseDefaultValue, this.mPaused, this.mScreenAutoBrightnessRatio).execute(new Void[0]);
        }
    }

    private void cancelLastTask() {
        AsyncTask<Void, Void, Integer> asyncTask = this.mCameraBrightnessTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mCameraBrightnessTask = null;
        }
    }

    @Override // com.android.camera.CameraBrightnessCallback
    public void adjustBrightnessInAutoMode(float f2) {
        try {
            CompatibilityUtils.setTemporaryAutoBrightnessAdjustment(this.mDisplayManager, f2);
            this.mScreenAutoBrightnessRatio = f2;
        } catch (SecurityException e2) {
            Log.e(TAG, "Meet exception when adjustBrightnessInAutoMode(): " + e2);
        }
    }

    public int getCurrentBrightness() {
        return this.mBrightness;
    }

    public float getCurrentBrightnessAuto() {
        return this.mScreenAutoBrightnessRatio;
    }

    public int getCurrentBrightnessManual() {
        return this.mBrightness;
    }

    @Override // com.android.camera.CameraBrightnessCallback
    public int getPreviousBrightnessMode() {
        return this.mBrightnessMode;
    }

    public void onPause() {
        Log.v(TAG, "onPause mUseDefaultValue=" + this.mUseDefaultValue);
        this.mFirstFocusChanged = false;
        this.mPaused = true;
        if (!this.mUseDefaultValue) {
            this.mUseDefaultValue = true;
            adjustBrightness();
        }
    }

    public void onResume() {
        this.mUseDefaultValue = false;
        this.mPaused = false;
        this.mScreenAutoBrightnessRatio = 0.0f;
        Log.v(TAG, "onResume adjustBrightness");
        adjustBrightness();
    }

    public void onWindowFocusChanged(boolean z) {
        Log.v(TAG, "onWindowFocusChanged hasFocus=" + z + " mFirstFocusChanged=" + this.mFirstFocusChanged);
        if (!this.mFirstFocusChanged && z) {
            this.mFirstFocusChanged = true;
        } else if (this.mUseDefaultValue == z) {
            this.mUseDefaultValue = !z;
            adjustBrightness();
        }
    }

    @Override // com.android.camera.CameraBrightnessCallback
    public void setBrightness(int i) {
        this.mBrightness = i;
    }

    @Override // com.android.camera.CameraBrightnessCallback
    public void setPreviousBrightnessMode(int i) {
        this.mBrightnessMode = i;
    }
}
