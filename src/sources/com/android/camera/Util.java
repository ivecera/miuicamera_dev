package com.android.camera;

import a.c.a;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.YuvImage;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.MeteringRectangle;
import android.location.Country;
import android.location.CountryDetector;
import android.location.Location;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.ViewCompat;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.SparseArray;
import android.util.Xml;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.effect.renders.CustomTextWaterMark;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.effect.renders.ImageWaterMark;
import com.android.camera.effect.renders.NewStyleTextWaterMark;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.network.download.Verifier;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.storage.Storage;
import com.android.camera2.ArcsoftDepthMap;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.gallery3d.exif.ExifInterface;
import com.android.gallery3d.exif.Rational;
import com.android.gallery3d.ui.StringTexture;
import com.mi.config.b;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.stat.d;
import d.d.a;
import d.h.a.E;
import dalvik.system.VMRuntime;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import miui.reflect.NoSuchClassException;
import miui.reflect.NoSuchFieldException;
import miui.reflect.NoSuchMethodException;
import miui.reflect.c;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public final class Util {
    public static final String ACTION_BIND_GALLERY_SERVICE = "com.miui.gallery.action.BIND_SERVICE";
    public static final String ACTION_DISMISS_KEY_GUARD = "xiaomi.intent.action.SHOW_SECURE_KEYGUARD";
    public static final String ACTION_KILL_CAMERA_SERVICE = "com.android.camera.action.KILL_CAMERA_SERVICE";
    public static final String ACTION_RESET_CAMERA_PREF = "miui.intent.action.RESET_CAMERA_PREF";
    public static final String ALGORITHM_NAME_MIMOJI_CAPTURE = "mimoji";
    public static final String ALGORITHM_NAME_PORTRAIT = "portrait";
    public static final String ALGORITHM_NAME_SOFT_PORTRAIT = "soft-portrait";
    public static final String ALGORITHM_NAME_SOFT_PORTRAIT_ENCRYPTED = "soft-portrait-enc";
    public static final String ANDROID_ONE_EXTRA_IS_SECURE_MODE = "com.google.android.apps.photos.api.secure_mode";
    public static final String ANDROID_ONE_EXTRA_SECURE_MODE_MEDIA_STORE_IDS = "com.google.android.apps.photos.api.secure_mode_ids";
    public static final String ANDROID_ONE_REVIEW_ACTIVITY_PACKAGE = "com.google.android.apps.photos";
    private static HashSet<String> ANTIBANDING_60_COUNTRY = new HashSet<>(Arrays.asList("TW", "KR", "SA", "US", "CA", "BR", "CO", "MX", "PH"));
    private static final Long APERTURE_VALUE_PRECISION = 100L;
    public static final double ASPECT_TOLERANCE = 0.02d;
    private static final int BACK_LIGHT_SENSOR_TYPE = 33171055;
    public static final int BLUR_DURATION = 100;
    private static final int BYTES_COPY_BUFFER_LENGTH = 1024;
    public static final String CAMERA_CHANNEL_ID = "MiuiCamera";
    private static final List<Integer> COLOR_TEMPERATURE_LIST = new ArrayList();
    private static final List<Integer> COLOR_TEMPERATURE_MAP = new ArrayList();
    public static final String CUSTOM_DEFAULT_WATERMARK_PREFIX = "1";
    public static final boolean DEBUG = (!a.bh);
    public static final String EXTRAS_SKIP_LOCK = "skip_interception";
    private static final String EXTRAS_START_WITH_EFFECT_RENDER = "android.intent.extras.START_WITH_EFFECT_RENDER";
    public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = Integer.MIN_VALUE;
    private static final Long FOCAL_LENGTH_PRECISION = 100L;
    private static final String FORCE_CAMERA_0_FILE = "force_camera_0";
    private static final String FORCE_NAME_SUFFIX_FILE = "force_name_suffix";
    private static final Long F_NUMBER_PRECISION = 100L;
    public static final int GOING_TO_CROP = 5;
    public static final int GOING_TO_DETAIL = 3;
    public static final int GOING_TO_GALLERY = 1;
    public static final int GOING_TO_MIUI_EXTRA_PHOTO = 6;
    public static final int GOING_TO_PLAYBACK = 4;
    public static final int GOING_TO_SETTING = 2;
    public static final float GYROSCOPE_MAX_X = 0.7f;
    public static final float GYROSCOPE_MAX_Y = 5.0f;
    public static final float GYROSCOPE_MAX_Z = 0.7f;
    private static final File INTERNAL_STORAGE_DIRECTORY = new File("/data/sdcard");
    public static final int KEYCODE_SLIDE_OFF = 701;
    public static final int KEYCODE_SLIDE_ON = 700;
    public static final String KEY_CAMERA_BRIGHTNESS = "camera-brightness";
    public static final String KEY_CAMERA_BRIGHTNESS_AUTO = "camera-brightness-auto";
    public static final String KEY_CAMERA_BRIGHTNESS_MANUAL = "camera-brightness-manual";
    public static final String KEY_KILLED_MODULE_INDEX = "killed-moduleIndex";
    public static final String KEY_REVIEW_FROM_MIUICAMERA = "from_MiuiCamera";
    public static final String KEY_SECURE_ITEMS = "SecureUri";
    private static final String LAB_OPTIONS_VISIBLE_FILE = "lab_options_visible";
    public static final String LAST_FRAME_GAUSSIAN_FILE_NAME = "blur.jpg";
    public static final int LIMIT_SURFACE_WIDTH = 720;
    private static final double LOG_2 = Math.log(2.0d);
    public static final int MAX_SECURE_SIZE = 100;
    private static final Long MS_TO_S = 1000000L;
    private static final String NONUI_MODE_PROPERTY = "sys.power.nonui";
    private static final Long NS_TO_S = 1000000000L;
    public static final int ORIENTATION_HYSTERESIS = 5;
    public static final String QRCODE_RECEIVER_ACTION = "com.xiaomi.scanner.receiver.senderbarcodescanner";
    public static final float RATIO_16_9 = 1.7777777f;
    public static final float RATIO_18_7_5_9 = 2.0833333f;
    public static final float RATIO_18_9 = 2.0f;
    public static final float RATIO_19P5_9 = 2.1666667f;
    public static final float RATIO_19_9 = 2.1111112f;
    public static final float RATIO_1_1 = 1.0f;
    public static final float RATIO_20_9 = 2.2222223f;
    public static final float RATIO_4_3 = 1.3333333f;
    public static final int REQUEST_CODE_OPEN_MIUI_EXTRA_PHOTO = 35893;
    public static final String REVIEW_ACTION = "com.android.camera.action.REVIEW";
    public static final String REVIEW_ACTIVITY_PACKAGE = "com.miui.gallery";
    public static final String REVIEW_SCAN_RESULT_PACKAGE = "com.xiaomi.scanner";
    private static final String SCHEME_PHOTO = "photo";
    public static final int SCREEN_EFFECT_CAMERA_STATE = 14;
    public static final Uri SCREEN_SLIDE_STATUS_SETTING_URI = Settings.System.getUriFor("sc_status");
    private static String SCREEN_VENDOR = null;
    private static final Long SHUTTER_SPEED_VALUE_PRECISION = 100L;
    private static final String TAG = "CameraUtil";
    private static final String TEMP_SUFFIX = ".tmp";
    public static final String WATERMARK_DEFAULT_FILE_NAME = "dualcamera.png";
    public static final String WATERMARK_FRONT_FILE_NAME = (Build.DEVICE + "_front_watermark.png");
    public static String WATERMARK_SPACE = null;
    public static final String WATERMARK_STORAGE_DIRECTORY = "/mnt/vendor/persist/camera/";
    public static final String WATERMARK_ULTRA_PIXEL_FILE_NAME = (Build.DEVICE + "_" + WATERMARK_SPACE + "_ultra_pixel_custom_watermark.png");
    private static final String ZOOM_ANIMATION_PROPERTY = "camera_zoom_animation";
    public static boolean isDarkMode = false;
    private static boolean isHasBackLightSensor = false;
    public static boolean isNotchDevice = false;
    private static String mCountryIso = null;
    private static int mLockedOrientation = -1;
    public static String sAAID = null;
    public static int sBottomBarHeight = 0;
    public static int sBottomMargin = 0;
    public static int sCenterDisplayHeight = 0;
    public static final double sCinematicAspectRatio = 2.39d;
    public static final int sCinematicAspectWaterMarkPadding = 15;
    public static final double sCinematicAspectWaterMarkRatio = 0.95d;
    private static boolean sClearMemoryLimit;
    private static boolean sHasNavigationBar;
    private static ImageFileNamer sImageFileNamer;
    private static boolean sIsAccessibilityEnable;
    public static boolean sIsCameraUnderScreen;
    private static Boolean sIsDumpImageEnabled;
    public static boolean sIsDumpLog;
    public static boolean sIsDumpOrigJpg;
    private static Boolean sIsForceNameSuffix;
    public static boolean sIsFullScreenNavBarHidden;
    public static boolean sIsKillCameraService;
    private static Boolean sIsLabOptionsVisible;
    public static boolean sIsnotchScreenHidden;
    public static int sNavigationBarHeight;
    public static float sPixelDensity = 1.0f;
    public static String sRegion;
    public static int sStatusBarHeight;
    public static boolean sSuperNightDefaultModeEnable;
    public static int sTopBarHeight;
    public static int sTopMargin;
    private static HashMap<String, Typeface> sTypefaces = new HashMap<>();
    public static int sWindowHeight = 1080;
    private static IWindowManager sWindowManager;
    public static int sWindowWidth = LIMIT_SURFACE_WIDTH;

    private static class ImageFileNamer {
        private SimpleDateFormat mFormat;
        private long mLastDate;
        private int mSameSecondCount;

        public ImageFileNamer(String str) {
            this.mFormat = new SimpleDateFormat(str);
        }

        public String generateName(long j) {
            String format = this.mFormat.format(new Date(j));
            if (j / 1000 == this.mLastDate / 1000) {
                this.mSameSecondCount++;
                return format + "_" + this.mSameSecondCount;
            }
            this.mLastDate = j;
            this.mSameSecondCount = 0;
            return format;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        SCREEN_VENDOR = SystemProperties.get("sys.panel.display");
        WATERMARK_SPACE = "other_space";
        if (UserHandle.myUserId() == 0) {
            WATERMARK_SPACE = "main_space";
        }
        if (TextUtils.isEmpty(SCREEN_VENDOR)) {
            SCREEN_VENDOR = SystemProperties.get("vendor.panel.display");
        }
    }

    private Util() {
    }

    public static void Assert(boolean z) {
        if (!z) {
            throw new AssertionError();
        }
    }

    public static byte[] RGBA2RGB(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return null;
        }
        int i3 = i * i2;
        byte[] bArr2 = new byte[(i3 * 3)];
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            int i6 = i5 + 1;
            int i7 = i4 * 4;
            bArr2[i5] = bArr[i7];
            int i8 = i6 + 1;
            bArr2[i6] = bArr[i7 + 1];
            bArr2[i8] = bArr[i7 + 2];
            i4++;
            i5 = i8 + 1;
        }
        return bArr2;
    }

    private static String addDebugInfo(String str) {
        if (str == null) {
            return "";
        }
        return ("\t " + str) + "\n";
    }

    private static String addProperties(String str) {
        if (SystemProperties.get(str) == null) {
            return "";
        }
        return ("\t " + SystemProperties.get(str)) + "\n";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0259, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        $closeResource(r7, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x025d, code lost:
        throw r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x025e, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x025f, code lost:
        com.android.camera.log.Log.d(com.android.camera.Util.TAG, "appendExif(): Failed to append exif metadata");
     */
    public static byte[] appendCaptureResultToExif(byte[] bArr, int i, int i2, int i3, long j, Location location, CameraMetadataNative cameraMetadataNative) {
        Integer num;
        if ((!b.isMTKPlatform() && !DataRepository.dataItemFeature().ze()) || cameraMetadataNative == null) {
            return bArr;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExifInterface exif = ExifInterface.getExif(bArr);
        setTagValue(exif, ExifInterface.TAG_ORIENTATION, Short.valueOf(ExifInterface.getExifOrientationValue(i3)));
        setTagValue(exif, ExifInterface.TAG_PIXEL_X_DIMENSION, Integer.valueOf(i));
        setTagValue(exif, ExifInterface.TAG_PIXEL_Y_DIMENSION, Integer.valueOf(i2));
        setTagValue(exif, ExifInterface.TAG_IMAGE_WIDTH, Integer.valueOf(i));
        setTagValue(exif, ExifInterface.TAG_IMAGE_LENGTH, Integer.valueOf(i2));
        setTagValue(exif, ExifInterface.TAG_MODEL, Build.MODEL);
        setTagValue(exif, ExifInterface.TAG_MAKE, Build.MANUFACTURER);
        if (j > 0) {
            exif.addDateTimeStampTag(ExifInterface.TAG_DATE_TIME, j, TimeZone.getDefault());
            exif.addSubTagSecTime(ExifInterface.TAG_SUB_SEC_TIME, j, TimeZone.getDefault());
        }
        Float f2 = (Float) cameraMetadataNative.get(CaptureResult.LENS_FOCAL_LENGTH);
        Log.d(TAG, "LENS_FOCAL_LENGTH: " + f2);
        if (f2 != null) {
            setTagValue(exif, ExifInterface.TAG_FOCAL_LENGTH, doubleToRational((double) f2.floatValue(), FOCAL_LENGTH_PRECISION.longValue()));
        }
        Float f3 = (Float) cameraMetadataNative.get(CaptureResult.LENS_APERTURE);
        Log.d(TAG, "LENS_APERTURE: " + f3);
        if (f3 != null) {
            setTagValue(exif, ExifInterface.TAG_F_NUMBER, doubleToRational((double) f3.floatValue(), F_NUMBER_PRECISION.longValue()));
            setTagValue(exif, ExifInterface.TAG_APERTURE_VALUE, doubleToRational(log2((double) f3.floatValue()) * 2.0d, APERTURE_VALUE_PRECISION.longValue()));
        }
        if (DataRepository.dataItemFeature().ze()) {
            num = (Integer) cameraMetadataNative.get(CaptureResultVendorTags.ISO_VALUE);
            Log.d(TAG, "[ALGOUP|MMCAMERA] SENSOR_SENSITIVITY: " + num);
        } else {
            num = (Integer) cameraMetadataNative.get(CaptureResult.SENSOR_SENSITIVITY);
            Log.d(TAG, "SENSOR_SENSITIVITY: " + num);
        }
        if (num != null) {
            setTagValue(exif, ExifInterface.TAG_ISO_SPEED_RATINGS, num);
        }
        Long l = (Long) cameraMetadataNative.get(CaptureResult.SENSOR_EXPOSURE_TIME);
        Log.d(TAG, "SENSOR_EXPOSURE_TIME: " + l);
        if (l != null) {
            if (l.longValue() <= 4000000000L) {
                setTagValue(exif, ExifInterface.TAG_EXPOSURE_TIME, new Rational(l.longValue(), NS_TO_S.longValue()));
            } else {
                setTagValue(exif, ExifInterface.TAG_EXPOSURE_TIME, new Rational(l.longValue() / 1000, MS_TO_S.longValue()));
            }
            setTagValue(exif, ExifInterface.TAG_SHUTTER_SPEED_VALUE, doubleToRational(log2(((double) l.longValue()) / ((double) NS_TO_S.longValue())), SHUTTER_SPEED_VALUE_PRECISION.longValue()));
        }
        Location location2 = (Location) cameraMetadataNative.get(CaptureResult.JPEG_GPS_LOCATION);
        if (location2 == null) {
            location2 = location;
        }
        Log.d(TAG, "JPEG_GPS_LOCATION: " + location2);
        if (location2 != null) {
            exif.addGpsTags(location2.getLatitude(), location2.getLongitude());
            exif.addGpsDateTimeStampTag(location2.getTime());
            double altitude = location2.getAltitude();
            if (altitude != 0.0d) {
                exif.setTag(exif.buildTag(ExifInterface.TAG_GPS_ALTITUDE_REF, Short.valueOf(altitude < 0.0d ? (short) 1 : 0)));
                exif.addGpsTags(location2.getLatitude(), location2.getLongitude());
            }
        }
        Integer num2 = (Integer) cameraMetadataNative.get(CaptureResult.FLASH_STATE);
        Log.d(TAG, "FLASH_STATE: " + num2);
        if (num2 == null || num2.intValue() != 3) {
            setTagValue(exif, ExifInterface.TAG_FLASH, (short) 0);
        } else {
            setTagValue(exif, ExifInterface.TAG_FLASH, (short) 1);
        }
        exif.writeExif(bArr, byteArrayOutputStream);
        byte[] bArr2 = byteArrayOutputStream.toByteArray();
        try {
            $closeResource(null, byteArrayOutputStream);
        } catch (IOException | RuntimeException unused) {
        }
        return (bArr2 != null || bArr2.length < bArr.length) ? bArr : bArr2;
    }

    public static SpannableStringBuilder appendInApi26(SpannableStringBuilder spannableStringBuilder, CharSequence charSequence, Object obj, int i) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan(obj, length, spannableStringBuilder.length(), i);
        return spannableStringBuilder;
    }

    public static <T> int binarySearchRightMost(List<? extends Comparable<? super T>> list, T t) {
        int size = list.size() - 1;
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            if (((Comparable) list.get(i2)).compareTo(t) >= 0) {
                size = i2 - 1;
            } else {
                i = i2 + 1;
            }
        }
        return i;
    }

    public static void broadcastKillService(Context context, boolean z) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        CameraSettings.setBroadcastKillServiceTime(elapsedRealtime);
        Intent intent = new Intent(ACTION_KILL_CAMERA_SERVICE);
        intent.putExtra("time", elapsedRealtime + 2000);
        intent.putExtra("dump_backtrace", z);
        intent.putExtra("process_name", new String[]{"android.hardware.camera.provider@2.4-service", "android.hardware.camera.provider@2.4-service_64"});
        context.sendBroadcast(intent);
        CameraStatUtils.trackBroadcastKillService();
    }

    public static void broadcastNewPicture(Context context, Uri uri) {
        int i = Build.VERSION.SDK_INT;
        if (i < 24) {
            context.sendBroadcast(new Intent("android.hardware.action.NEW_PICTURE", uri));
            context.sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", uri));
        } else if (i == 29) {
            context.sendBroadcast(new Intent("android.hardware.action.NEW_PICTURE", uri));
        }
    }

    public static int[] calcDualCameraWatermarkLocation(int i, int i2, int i3, int i4, float f2, float f3, float f4) {
        float min = ((float) Math.min(i, i2)) / 1080.0f;
        boolean s_c_w_m = DataRepository.dataItemFeature().s_c_w_m();
        float f5 = 1.0f;
        int round = Math.round(f2 * min * (s_c_w_m ? CameraSettings.getResourceFloat(R.dimen.custom_watermark_height_scale, 1.0f) : 1.0f)) & -2;
        int i5 = ((i3 * round) / i4) & -2;
        int round2 = Math.round(f3 * min) & -2;
        if (s_c_w_m) {
            f5 = CameraSettings.getResourceFloat(R.dimen.custom_watermark_pandingY_scale, 1.0f);
        }
        return new int[]{i5, round, round2, Math.round(f4 * min * f5) & -2};
    }

    public static final int calculateDefaultPreviewEdgeSlop(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f2 = ((float) displayMetrics.widthPixels) / displayMetrics.xdpi;
        float f3 = ((float) displayMetrics.heightPixels) / displayMetrics.ydpi;
        return context.getResources().getDimensionPixelSize(((float) Math.sqrt((double) ((f2 * f2) + (f3 * f3)))) < 5.0f ? R.dimen.preview_edge_touch_slop_small_screen : R.dimen.preview_edge_touch_slop);
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        if (sWindowManager == null) {
            sWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            try {
                sHasNavigationBar = CompatibilityUtils.hasNavigationBar(context, sWindowManager);
            } catch (Exception unused) {
                Log.e(TAG, "checkDeviceHasNavigationBar exception");
            }
        }
        return sHasNavigationBar;
    }

    public static void checkHasBackLightSensot(Context context) {
        Sensor defaultSensor = ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(BACK_LIGHT_SENSOR_TYPE);
        if (defaultSensor != null && BACK_LIGHT_SENSOR_TYPE == defaultSensor.getType()) {
            isHasBackLightSensor = true;
        }
    }

    public static void checkLockedOrientation(Activity activity) {
        try {
            if (Settings.System.getInt(activity.getContentResolver(), "accelerometer_rotation") == 0) {
                mLockedOrientation = Settings.System.getInt(activity.getContentResolver(), "user_rotation");
            } else {
                mLockedOrientation = -1;
            }
        } catch (Settings.SettingNotFoundException unused) {
            Log.e(TAG, "user rotation cannot found");
        }
    }

    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static float clamp(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    public static int clamp(int i, int i2, int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i;
    }

    public static long clamp(long j, long j2, long j3) {
        return j > j3 ? j3 : j < j2 ? j2 : j;
    }

    public static void clearMemoryLimit() {
        if (!sClearMemoryLimit) {
            long currentTimeMillis = System.currentTimeMillis();
            VMRuntime.getRuntime().clearGrowthLimit();
            sClearMemoryLimit = true;
            long currentTimeMillis2 = System.currentTimeMillis();
            Log.v(TAG, "clearMemoryLimit() consume:" + (currentTimeMillis2 - currentTimeMillis));
        }
    }

    public static void closeSafely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0034  */
    public static byte[] composeDepthMapPicture(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, int[] iArr, boolean z, boolean z2, int i, String str, int i2, int i3, boolean z3, boolean z4, int i4, DeviceWatermarkParam deviceWatermarkParam, PictureInfo pictureInfo, long j) {
        byte[] bArr5;
        byte[] bArr6;
        byte[] frontCameraWatermarkData;
        Log.d(TAG, "composeDepthMapPicture: process in portrait depth map picture");
        long currentTimeMillis = System.currentTimeMillis();
        ArcsoftDepthMap arcsoftDepthMap = new ArcsoftDepthMap(bArr2);
        int[] iArr2 = new int[4];
        if (z) {
            frontCameraWatermarkData = getDualCameraWatermarkData(i2, i3, iArr2, i4, deviceWatermarkParam);
        } else if (z2) {
            frontCameraWatermarkData = getFrontCameraWatermarkData(i2, i3, iArr2, i4, deviceWatermarkParam);
        } else {
            bArr5 = null;
            int[] iArr3 = new int[4];
            if (str == null) {
                Log.d(TAG, "generate a TimeWaterMarkData with :" + i2 + "x" + i3);
                bArr6 = getTimeWaterMarkData(i2, i3, str, iArr3, i4, deviceWatermarkParam);
            } else {
                bArr6 = null;
            }
            byte[] depthMapData = arcsoftDepthMap.getDepthMapData();
            byte[] writePortraitExif = arcsoftDepthMap.writePortraitExif(DataRepository.dataItemFeature().xb(), bArr, bArr5, iArr2, bArr6, iArr3, bArr4, iArr, i, z3, z4, deviceWatermarkParam.isCinematicAspectRatio(), pictureInfo, bArr3.length, depthMapData.length, j);
            byte[] bArr7 = new byte[(writePortraitExif.length + bArr3.length + depthMapData.length)];
            System.arraycopy(writePortraitExif, 0, bArr7, 0, writePortraitExif.length);
            System.arraycopy(bArr3, 0, bArr7, writePortraitExif.length, bArr3.length);
            System.arraycopy(depthMapData, 0, bArr7, writePortraitExif.length + bArr3.length, depthMapData.length);
            Log.d(TAG, "composeDepthMapPicture: compose portrait picture cost: " + (System.currentTimeMillis() - currentTimeMillis));
            return bArr7;
        }
        bArr5 = frontCameraWatermarkData;
        int[] iArr32 = new int[4];
        if (str == null) {
        }
        byte[] depthMapData2 = arcsoftDepthMap.getDepthMapData();
        byte[] writePortraitExif2 = arcsoftDepthMap.writePortraitExif(DataRepository.dataItemFeature().xb(), bArr, bArr5, iArr2, bArr6, iArr32, bArr4, iArr, i, z3, z4, deviceWatermarkParam.isCinematicAspectRatio(), pictureInfo, bArr3.length, depthMapData2.length, j);
        byte[] bArr72 = new byte[(writePortraitExif2.length + bArr3.length + depthMapData2.length)];
        System.arraycopy(writePortraitExif2, 0, bArr72, 0, writePortraitExif2.length);
        System.arraycopy(bArr3, 0, bArr72, writePortraitExif2.length, bArr3.length);
        System.arraycopy(depthMapData2, 0, bArr72, writePortraitExif2.length + bArr3.length, depthMapData2.length);
        Log.d(TAG, "composeDepthMapPicture: compose portrait picture cost: " + (System.currentTimeMillis() - currentTimeMillis));
        return bArr72;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0274, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0275, code lost:
        $closeResource(r0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0279, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x02d5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x02d6, code lost:
        $closeResource(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x02da, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0086, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        $closeResource(r0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008c, code lost:
        throw r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x028e  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0206  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x020c A[SYNTHETIC, Splitter:B:92:0x020c] */
    public static byte[] composeLiveShotPicture(byte[] bArr, int i, int i2, String str, long j, boolean z, boolean z2, String str2, int i3, DeviceWatermarkParam deviceWatermarkParam, byte[] bArr2, int[] iArr) {
        byte[] bArr3;
        String str3;
        int[] iArr2;
        byte[] bArr4;
        byte[] bArr5;
        String str4;
        String str5;
        byte[] bArr6;
        byte[] bArr7;
        Throwable th;
        String str6;
        StringWriter stringWriter;
        Log.d(TAG, "composeLiveShotPicture(): E");
        if (bArr == null || bArr.length == 0) {
            Log.w(TAG, "composeLiveShotPicture(): The primary photo of LiveShot is empty");
            return new byte[0];
        } else if (str == null || CircularMediaRecorder.VideoClipSavingCallback.EMPTY_VIDEO_PATH.equals(str)) {
            Log.w(TAG, "composeLiveShotPicture(): The corresponding movie of LiveShot is empty");
            return bArr;
        } else {
            File file = new File(str);
            if (!file.exists()) {
                Log.w(TAG, "composeLiveShotPicture(): not found LiveShot movie file " + str);
                return bArr;
            }
            int length = (int) file.length();
            if (length == 0) {
                Log.w(TAG, "composeLiveShotPicture(): The corresponding movie of LiveShot length is 0");
                return bArr;
            }
            int[] iArr3 = new int[4];
            int[] iArr4 = new int[4];
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ExifInterface exifInterface = new ExifInterface();
                exifInterface.readExif(bArr);
                exifInterface.addFileTypeLiveShot(true);
                exifInterface.writeExif(bArr, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    $closeResource(null, byteArrayOutputStream);
                    bArr3 = byteArray;
                } catch (IOException unused) {
                    bArr3 = byteArray;
                    Log.d(TAG, "composeLiveShotPicture(): Failed to insert xiaomi specific metadata");
                    if (bArr3 != null) {
                    }
                    Log.d(TAG, "composeLiveShotPicture(): #1: return original jpeg");
                    return bArr;
                }
            } catch (IOException unused2) {
                bArr3 = null;
                Log.d(TAG, "composeLiveShotPicture(): Failed to insert xiaomi specific metadata");
                if (bArr3 != null) {
                }
                Log.d(TAG, "composeLiveShotPicture(): #1: return original jpeg");
                return bArr;
            }
            if (bArr3 != null || bArr3.length <= bArr.length) {
                Log.d(TAG, "composeLiveShotPicture(): #1: return original jpeg");
                return bArr;
            }
            byte[] dualCameraWatermarkData = z ? getDualCameraWatermarkData(i, i2, iArr3, i3, deviceWatermarkParam) : z2 ? getFrontCameraWatermarkData(i, i2, iArr3, i3, deviceWatermarkParam) : null;
            if (str2 == null || str2.isEmpty()) {
                iArr2 = iArr3;
                str3 = XmpHelper.GOOGLE_MICROVIDEO_NAMESPACE;
                bArr4 = null;
            } else {
                iArr2 = iArr3;
                str3 = XmpHelper.GOOGLE_MICROVIDEO_NAMESPACE;
                bArr4 = getTimeWaterMarkData(i, i2, str2, iArr4, i3, deviceWatermarkParam);
            }
            try {
                XmlSerializer newSerializer = Xml.newSerializer();
                StringWriter stringWriter2 = new StringWriter();
                newSerializer.setOutput(stringWriter2);
                newSerializer.startDocument("UTF-8", true);
                bArr5 = bArr3;
                if (bArr2 != null) {
                    str6 = TAG;
                    try {
                        if (bArr2.length <= 0 || iArr == null) {
                            stringWriter = stringWriter2;
                        } else {
                            stringWriter = stringWriter2;
                            if (iArr.length >= 4) {
                                newSerializer.startTag(null, "subimage");
                                newSerializer.attribute(null, "offset", String.valueOf(bArr2.length + (dualCameraWatermarkData != null ? dualCameraWatermarkData.length : 0) + (bArr4 != null ? bArr4.length : 0) + length));
                                newSerializer.attribute(null, "length", String.valueOf(bArr2.length));
                                newSerializer.attribute(null, "paddingx", String.valueOf(iArr[0]));
                                newSerializer.attribute(null, "paddingy", String.valueOf(iArr[1]));
                                newSerializer.attribute(null, "width", String.valueOf(iArr[2]));
                                newSerializer.attribute(null, "height", String.valueOf(iArr[3]));
                                newSerializer.endTag(null, "subimage");
                            }
                        }
                    } catch (IOException unused3) {
                        str5 = str6;
                        Log.d(str5, "composeLiveShotPicture(): Failed to generate xiaomi xmp metadata");
                        str4 = null;
                        if (str4 == null) {
                        }
                    }
                } else {
                    stringWriter = stringWriter2;
                    str6 = TAG;
                }
                if (dualCameraWatermarkData != null && dualCameraWatermarkData.length > 0) {
                    newSerializer.startTag(null, "lenswatermark");
                    newSerializer.attribute(null, "offset", String.valueOf(dualCameraWatermarkData.length + (bArr4 != null ? bArr4.length : 0) + length));
                    newSerializer.attribute(null, "length", String.valueOf(dualCameraWatermarkData.length));
                    newSerializer.attribute(null, "width", String.valueOf(iArr2[0]));
                    newSerializer.attribute(null, "height", String.valueOf(iArr2[1]));
                    newSerializer.attribute(null, "paddingx", String.valueOf(iArr2[2]));
                    newSerializer.attribute(null, "paddingy", String.valueOf(iArr2[3]));
                    newSerializer.endTag(null, "lenswatermark");
                }
                if (bArr4 != null && bArr4.length > 0) {
                    newSerializer.startTag(null, "timewatermark");
                    newSerializer.attribute(null, "offset", String.valueOf(bArr4.length + length));
                    newSerializer.attribute(null, "length", String.valueOf(bArr4.length));
                    newSerializer.attribute(null, "width", String.valueOf(iArr4[0]));
                    newSerializer.attribute(null, "height", String.valueOf(iArr4[1]));
                    newSerializer.attribute(null, "paddingx", String.valueOf(iArr4[2]));
                    newSerializer.attribute(null, "paddingy", String.valueOf(iArr4[3]));
                    newSerializer.endTag(null, "timewatermark");
                }
                newSerializer.endDocument();
                str4 = stringWriter.toString();
                str5 = str6;
            } catch (IOException unused4) {
                str6 = TAG;
                bArr5 = bArr3;
                str5 = str6;
                Log.d(str5, "composeLiveShotPicture(): Failed to generate xiaomi xmp metadata");
                str4 = null;
                if (str4 == null) {
                }
            }
            if (str4 == null) {
                Log.d(str5, "composeLiveShotPicture(): #2: return original jpeg");
                return bArr;
            }
            try {
                bArr7 = bArr5;
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr7);
                    try {
                        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                        XMPMeta createXMPMeta = XmpHelper.createXMPMeta();
                        createXMPMeta.setPropertyInteger(str3, XmpHelper.MICROVIDEO_VERSION, 1);
                        createXMPMeta.setPropertyInteger(str3, XmpHelper.MICROVIDEO_TYPE, 1);
                        createXMPMeta.setPropertyInteger(str3, XmpHelper.MICROVIDEO_OFFSET, length);
                        createXMPMeta.setPropertyLong(str3, XmpHelper.MICROVIDEO_PRESENTATION_TIMESTAMP, j);
                        if (str4 != null) {
                            createXMPMeta.setProperty(XmpHelper.XIAOMI_XMP_METADATA_NAMESPACE, XmpHelper.XIAOMI_XMP_METADATA_PROPERTY_NAME, str4);
                        }
                        XmpHelper.writeXMPMeta(byteArrayInputStream, byteArrayOutputStream2, createXMPMeta);
                        if (bArr2 != null && bArr2.length > 0 && iArr != null && iArr.length >= 4) {
                            byteArrayOutputStream2.write(bArr2);
                        }
                        if (dualCameraWatermarkData != null && dualCameraWatermarkData.length > 0) {
                            byteArrayOutputStream2.write(dualCameraWatermarkData);
                        }
                        if (bArr4 != null && bArr4.length > 0) {
                            byteArrayOutputStream2.write(bArr4);
                        }
                        byteArrayOutputStream2.flush();
                        bArr6 = byteArrayOutputStream2.toByteArray();
                        try {
                            $closeResource(null, byteArrayOutputStream2);
                            try {
                                $closeResource(null, byteArrayInputStream);
                            } catch (Exception unused5) {
                            }
                            if (bArr6 != null || bArr6.length <= bArr7.length) {
                                Log.d(str5, "composeLiveShotPicture(): #3: return original jpeg");
                                return bArr;
                            }
                            int length2 = bArr6.length + length;
                            Log.d(str5, "composeLiveShotPicture(): fileSize = " + length2 + " videoLength = " + length);
                            byte[] bArr8 = new byte[length2];
                            System.arraycopy(bArr6, 0, bArr8, 0, bArr6.length);
                            try {
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                                bufferedInputStream.read(bArr8, bArr6.length, length);
                                $closeResource(null, bufferedInputStream);
                                Log.d(str5, "composeLiveShotPicture(): X");
                                return bArr8;
                            } catch (IOException e2) {
                                Log.d(str5, "composeLiveShotPicture(): failed to load the mp4 file content into memory: " + e2);
                                return bArr;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                throw th;
                            } catch (Throwable th3) {
                                $closeResource(th, byteArrayInputStream);
                                throw th3;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        throw th;
                    }
                } catch (Exception unused6) {
                    bArr6 = null;
                    Log.d(str5, "composeLiveShotPicture(): failed to insert xmp metadata");
                    if (bArr6 != null) {
                    }
                    Log.d(str5, "composeLiveShotPicture(): #3: return original jpeg");
                    return bArr;
                }
            } catch (Exception unused7) {
                bArr7 = bArr5;
                bArr6 = null;
                Log.d(str5, "composeLiveShotPicture(): failed to insert xmp metadata");
                if (bArr6 != null) {
                }
                Log.d(str5, "composeLiveShotPicture(): #3: return original jpeg");
                return bArr;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c1, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c2, code lost:
        $closeResource(r10, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c5, code lost:
        throw r11;
     */
    public static byte[] composeMainSubPicture(byte[] bArr, byte[] bArr2, int[] iArr) {
        String str;
        if (!(bArr2 == null || iArr == null || iArr.length < 3)) {
            try {
                ExifInterface exifInterface = new ExifInterface();
                exifInterface.readExif(bArr);
                if (exifInterface.getMimeType() != 4) {
                    Log.e(TAG, "composeMainSubPicture(): HEIC does not support watermark removal");
                    return bArr;
                }
                byte[] bArr3 = null;
                try {
                    XmlSerializer newSerializer = Xml.newSerializer();
                    StringWriter stringWriter = new StringWriter();
                    newSerializer.setOutput(stringWriter);
                    newSerializer.startDocument("UTF-8", true);
                    newSerializer.startTag(null, "subimage");
                    newSerializer.attribute(null, "offset", String.valueOf(bArr2.length));
                    newSerializer.attribute(null, "length", String.valueOf(bArr2.length));
                    newSerializer.attribute(null, "paddingx", String.valueOf(iArr[0]));
                    newSerializer.attribute(null, "paddingy", String.valueOf(iArr[1]));
                    newSerializer.attribute(null, "width", String.valueOf(iArr[2]));
                    newSerializer.attribute(null, "height", String.valueOf(iArr[3]));
                    newSerializer.endTag(null, "subimage");
                    newSerializer.endDocument();
                    str = stringWriter.toString();
                } catch (IOException unused) {
                    Log.e(TAG, "composeMainSubPicture(): Failed to generate xiaomi specific xmp metadata");
                    str = null;
                }
                if (str == null) {
                    return bArr;
                }
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    XMPMeta createXMPMeta = XmpHelper.createXMPMeta();
                    createXMPMeta.setProperty(XmpHelper.XIAOMI_XMP_METADATA_NAMESPACE, XmpHelper.XIAOMI_XMP_METADATA_PROPERTY_NAME, str);
                    XmpHelper.writeXMPMeta(byteArrayInputStream, byteArrayOutputStream, createXMPMeta);
                    byteArrayOutputStream.write(bArr2);
                    byteArrayOutputStream.flush();
                    byte[] bArr4 = byteArrayOutputStream.toByteArray();
                    try {
                        $closeResource(null, byteArrayOutputStream);
                        try {
                            $closeResource(null, byteArrayInputStream);
                        } catch (XMPException | IOException unused2) {
                        }
                        if (bArr4 == null && bArr4.length >= bArr.length) {
                            return bArr4;
                        }
                        Log.e(TAG, "composeMainSubPicture(): Failed to append sub image, return original jpeg");
                        return bArr;
                    } catch (Throwable th) {
                        th = th;
                        bArr3 = bArr4;
                        try {
                            throw th;
                        } catch (XMPException | IOException unused3) {
                            bArr4 = bArr3;
                            Log.d(TAG, "composeMainSubPicture(): Failed to insert xiaomi specific xmp metadata");
                            if (bArr4 == null) {
                            }
                            Log.e(TAG, "composeMainSubPicture(): Failed to append sub image, return original jpeg");
                            return bArr;
                        } catch (Throwable th2) {
                            $closeResource(th, byteArrayInputStream);
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    throw th;
                }
            } catch (IOException unused4) {
                Log.e(TAG, "composeMainSubPicture(): Failed to check file type");
            }
        }
        return bArr;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3;
        double d2 = (double) options.outWidth;
        double d3 = (double) options.outHeight;
        int ceil = i2 < 0 ? 1 : (int) Math.ceil(Math.sqrt((d2 * d3) / ((double) i2)));
        if (i < 0) {
            i3 = 128;
        } else {
            double d4 = (double) i;
            i3 = (int) Math.min(Math.floor(d2 / d4), Math.floor(d3 / d4));
        }
        if (i3 < ceil) {
            return ceil;
        }
        if (i2 >= 0 || i >= 0) {
            return i < 0 ? ceil : i3;
        }
        return 1;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int i, int i2) {
        int computeInitialSampleSize = computeInitialSampleSize(options, i, i2);
        if (computeInitialSampleSize > 8) {
            return 8 * ((computeInitialSampleSize + 7) / 8);
        }
        int i3 = 1;
        while (i3 < computeInitialSampleSize) {
            i3 <<= 1;
        }
        return i3;
    }

    private static float computeScale(int i, int i2, float f2) {
        double atan = Math.atan(((double) i) / ((double) i2));
        return (float) ((Math.sin(Math.toRadians(((double) normalizeDegree(f2)) + Math.toDegrees(atan))) / Math.sin(atan)) + ((double) (10.0f / ((float) i))));
    }

    public static String controlAEStateToString(Integer num) {
        if (num == null) {
            return "null";
        }
        int intValue = num.intValue();
        if (intValue == 0) {
            return "inactive";
        }
        if (intValue == 1) {
            return "searching";
        }
        if (intValue == 2) {
            return "converged";
        }
        if (intValue == 3) {
            return "locked";
        }
        if (intValue == 4) {
            return "flash_required";
        }
        if (intValue == 5) {
            return "precapture";
        }
        return "unknown: " + num;
    }

    public static String controlAFStateToString(Integer num) {
        if (num == null) {
            return "null";
        }
        switch (num.intValue()) {
            case 0:
                return "inactive";
            case 1:
                return "passive_scan";
            case 2:
                return "passive_focused";
            case 3:
                return "active_scan";
            case 4:
                return "focused_locked";
            case 5:
                return "not_focus_locked";
            case 6:
                return "passive_unfocused";
            default:
                return "unknown: " + num;
        }
    }

    public static String controlAWBStateToString(Integer num) {
        if (num == null) {
            return "null";
        }
        int intValue = num.intValue();
        return intValue != 0 ? intValue != 1 ? intValue != 2 ? intValue != 3 ? EnvironmentCompat.MEDIA_UNKNOWN : "locked" : "converged" : "searching" : "inactive";
    }

    public static String convertOutputFormatToFileExt(int i) {
        return i == 2 ? ".mp4" : ".3gp";
    }

    public static final String convertOutputFormatToMimeType(int i) {
        return i == 2 ? "video/mp4" : "video/3gpp";
    }

    public static Bitmap convertResToBitmap(int i) {
        return BitmapFactory.decodeResource(CameraAppImpl.getAndroidContext().getResources(), i);
    }

    public static int convertSizeToQuality(CameraSize cameraSize) {
        int i = cameraSize.width;
        int i2 = cameraSize.height;
        if (i >= i2) {
            i = i2;
            i2 = i;
        }
        if (i2 == 1920 && i == 1080) {
            return 6;
        }
        if (i2 == 3840 && i == 2160) {
            return 8;
        }
        if (i2 == 1280 && i == 720) {
            return 5;
        }
        return (i2 < 640 || i != 480) ? -1 : 4;
    }

    public static void coverSubYuvImage(byte[] bArr, int i, int i2, int i3, int i4, byte[] bArr2, int[] iArr) {
        int i5 = (iArr[1] * i3) + iArr[0];
        int i6 = 0;
        for (int i7 = 0; i7 < iArr[3]; i7++) {
            System.arraycopy(bArr2, i6, bArr, i5, iArr[2]);
            i6 += iArr[2];
            i5 += i3;
        }
        int i8 = (i3 * (i2 - 1)) + i + ((iArr[1] / 2) * i4) + iArr[0];
        for (int i9 = 0; i9 < iArr[3] / 2; i9++) {
            System.arraycopy(bArr2, i6, bArr, i8, iArr[2]);
            i8 += i4;
            i6 += iArr[2];
        }
    }

    public static boolean createFile(File file) {
        if (file.exists()) {
            return false;
        }
        String parent = file.getParent();
        if (parent != null) {
            mkdirs(new File(parent), 511, -1, -1);
        }
        try {
            file.createNewFile();
            return true;
        } catch (IOException unused) {
            return true;
        }
    }

    public static String createJpegName(long j) {
        String generateName;
        synchronized (sImageFileNamer) {
            generateName = sImageFileNamer.generateName(j);
        }
        return generateName;
    }

    public static MeteringRectangle createMeteringRectangleFrom(int i, int i2, int i3, int i4, int i5) {
        try {
            MeteringRectangle meteringRectangle = new MeteringRectangle(0, 0, 0, 0, 0);
            try {
                modify(meteringRectangle, "mX", i);
            } catch (Exception unused) {
                return new MeteringRectangle(i, i2, i3, i4, i5);
            }
            try {
                modify(meteringRectangle, "mY", i2);
            } catch (Exception unused2) {
                return new MeteringRectangle(i, i2, i3, i4, i5);
            }
            try {
                modify(meteringRectangle, "mWidth", i3);
            } catch (Exception unused3) {
                return new MeteringRectangle(i, i2, i3, i4, i5);
            }
            try {
                modify(meteringRectangle, "mHeight", i4);
            } catch (Exception unused4) {
                return new MeteringRectangle(i, i2, i3, i4, i5);
            }
            try {
                modify(meteringRectangle, "mWeight", i5);
                return meteringRectangle;
            } catch (Exception unused5) {
                return new MeteringRectangle(i, i2, i3, i4, i5);
            }
        } catch (Exception unused6) {
            return new MeteringRectangle(i, i2, i3, i4, i5);
        }
    }

    public static MeteringRectangle createMeteringRectangleFrom(Rect rect, int i) {
        try {
            return createMeteringRectangleFrom(rect.left, rect.top, rect.width(), rect.height(), i);
        } catch (Exception unused) {
            return new MeteringRectangle(rect, i);
        }
    }

    public static Bitmap cropBitmap(Bitmap bitmap, float f2, boolean z, float f3, boolean z2, boolean z3) {
        int i;
        int i2;
        if (z || z2 || z3) {
            Bitmap bitmap2 = null;
            if (bitmap == null || bitmap.isRecycled()) {
                Log.w(TAG, "cropBitmap: bitmap is invalid!");
                return null;
            }
            Matrix matrix = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (z) {
                int i3 = (f3 == 90.0f || f3 == 270.0f) ? 1 : -1;
                matrix.postScale(((float) i3) * 1.0f, ((float) (i3 * -1)) * 1.0f, ((float) width) / 2.0f, ((float) height) / 2.0f);
            }
            if (z2) {
                i2 = Math.min(width, height);
                matrix.postTranslate(((float) (i2 - width)) / 2.0f, ((float) (i2 - height)) / 2.0f);
                i = i2;
            } else {
                i = width;
                i2 = height;
            }
            if (z3) {
                i = (int) (((double) i2) / getCinematicAspectRatio());
                matrix.postTranslate(((float) (i - width)) / 2.0f, 0.0f);
            }
            try {
                bitmap2 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap2);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                canvas.drawBitmap(bitmap, matrix, paint);
                bitmap.recycle();
                if (z3) {
                    Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(createBitmap);
                    canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
                    Paint paint2 = new Paint();
                    paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
                    paint2.setAntiAlias(true);
                    paint2.setFilterBitmap(true);
                    canvas2.drawBitmap(bitmap2, ((float) (width - i)) / 2.0f, 0.0f, paint2);
                    bitmap2.recycle();
                    return createBitmap;
                }
            } catch (Exception | OutOfMemoryError e2) {
                Log.w(TAG, "Failed to adjust bitmap", e2);
            }
            return bitmap2;
        }
        Log.w(TAG, "cropBitmap: no effect!");
        return bitmap;
    }

    public static void deleteFile(String str) {
        if (str != null) {
            if (!(Storage.isUseDocumentMode() ? FileCompat.deleteFile(str) : new File(str).delete())) {
                Log.w(TAG, "Failed to delete file: " + str);
            }
        }
    }

    public static void displayMode(int i) {
        if (DataRepository.dataItemFeature().c_19039_0x0003()) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DISPLAY_MODE_CHANGED");
            if (i == 1) {
                intent.putExtra("display_mode", 2);
            } else {
                intent.putExtra("display_mode", 1);
            }
            try {
                CameraAppImpl.getAndroidContext().sendBroadcast(intent);
            } catch (Exception e2) {
                Log.e(TAG, "send broadcast fail!", e2);
            }
        }
    }

    public static float distance(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return (float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)));
    }

    private static Rational doubleToRational(double d2, long j) {
        return new Rational((long) ((d2 * ((double) j)) + 0.5d), j);
    }

    public static int dpToPixel(float f2) {
        return Math.round(sPixelDensity * f2);
    }

    public static void drawMiMovieBlackBridge(Image image) {
        int height = (int) ((((double) image.getHeight()) - (((double) image.getWidth()) / getCinematicAspectRatio())) / 2.0d);
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        ByteBuffer buffer2 = planes[2].getBuffer();
        int[] iArr = {planes[0].getRowStride(), planes[2].getRowStride()};
        int limit = buffer.limit();
        int limit2 = buffer2.limit();
        for (int i = 0; i < planes[0].getRowStride() * height; i++) {
            buffer.put(i, (byte) 0);
        }
        for (int rowStride = limit - (planes[0].getRowStride() * height); rowStride < limit; rowStride++) {
            buffer.put(rowStride, (byte) 0);
        }
        for (int i2 = 0; i2 < (planes[1].getRowStride() * height) / 2; i2++) {
            buffer2.put(i2, Byte.MIN_VALUE);
        }
        for (int rowStride2 = limit2 - ((height * planes[1].getRowStride()) / 2); rowStride2 < limit2; rowStride2++) {
            buffer2.put(rowStride2, Byte.MIN_VALUE);
        }
    }

    public static void dumpBackTrace(String str) {
        RuntimeException runtimeException = new RuntimeException();
        Log.d(TAG, "[" + str + "]\n");
        Log.d(TAG, "**********print backtrace start *************");
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            Log.d(TAG, "[" + str + "]:backtrace: " + stackTraceElement.getClassName() + " " + stackTraceElement.getMethodName() + " " + stackTraceElement.getLineNumber());
        }
        Log.d(TAG, "**********print backtrace end *************");
        Log.d(TAG, "[" + str + "]\n");
    }

    public static void dumpImageInfo(String str, Image image) {
        StringBuilder sb = new StringBuilder();
        Image.Plane[] planes = image.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            Image.Plane plane = planes[i];
            sb.append("plane_");
            sb.append(i);
            sb.append(": ");
            sb.append(plane.getPixelStride());
            sb.append("|");
            sb.append(plane.getRowStride());
            sb.append("|");
            sb.append(plane.getBuffer().remaining());
            sb.append("\n");
        }
        Log.d(str, sb.toString());
    }

    public static String dumpMatrix(float[] fArr) {
        int length = fArr.length;
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%f", Float.valueOf(fArr[i])));
            if (i != length - 1) {
                sb.append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void dumpRect(RectF rectF, String str) {
        Log.v(TAG, str + "=(" + rectF.left + "," + rectF.top + "," + rectF.right + "," + rectF.bottom + ")");
    }

    public static ByteBuffer dumpToBitmap(int i, int i2, int i3, int i4, String str) {
        ByteBuffer allocate = ByteBuffer.allocate(i3 * i4 * 4);
        GLES20.glReadPixels(i, i2, i3, i4, 6408, 5121, allocate);
        if (allocate != null) {
            String generateFilepath = Storage.generateFilepath("tex_" + createJpegName(System.currentTimeMillis()) + str, Storage.JPEG_SUFFIX);
            saveBitmap(allocate, i3, i4, Bitmap.Config.ARGB_8888, generateFilepath);
            Log.d(TAG, "dump to " + generateFilepath);
        }
        allocate.rewind();
        return allocate;
    }

    public static void enterLightsOutMode(Window window) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.systemUiVisibility |= 1;
        window.setAttributes(attributes);
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static String execCommand(String str, boolean z) {
        String[] strArr = {"sh", "-c", str};
        long currentTimeMillis = System.currentTimeMillis();
        try {
            Process exec = Runtime.getRuntime().exec(strArr);
            if (exec.waitFor() != 0) {
                Log.e(TAG, "exit value = " + exec.exitValue());
                return "";
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            if (!z) {
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuffer.append(readLine);
                }
            } else {
                while (true) {
                    String readLine2 = bufferedReader.readLine();
                    if (readLine2 == null) {
                        break;
                    }
                    stringBuffer.append(readLine2 + "\r\n");
                }
            }
            bufferedReader.close();
            String stringBuffer2 = stringBuffer.toString();
            Log.v(TAG, "execCommand value=" + stringBuffer2 + " cost=" + (System.currentTimeMillis() - currentTimeMillis));
            return stringBuffer2;
        } catch (InterruptedException e2) {
            Log.e(TAG, "execCommand InterruptedException");
            e2.printStackTrace();
            return "";
        } catch (IOException e3) {
            Log.e(TAG, "execCommand IOException");
            e3.printStackTrace();
            return "";
        }
    }

    public static void expandViewTouchDelegate(View view) {
        if (view.isShown()) {
            Rect rect = new Rect();
            view.getHitRect(rect);
            int dpToPixel = dpToPixel(10.0f);
            rect.top -= dpToPixel;
            rect.bottom += dpToPixel;
            rect.left -= dpToPixel;
            rect.right += dpToPixel;
            TouchDelegate touchDelegate = new TouchDelegate(rect, view);
            if (View.class.isInstance(view.getParent())) {
                ((View) view.getParent()).setTouchDelegate(touchDelegate);
            }
        } else if (View.class.isInstance(view.getParent())) {
            ((View) view.getParent()).setTouchDelegate(null);
        }
    }

    public static void fadeIn(View view) {
        fadeIn(view, 400);
    }

    public static void fadeIn(View view, int i) {
        if (view != null && view.getVisibility() != 0) {
            view.setVisibility(0);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration((long) i);
            view.clearAnimation();
            view.startAnimation(alphaAnimation);
        }
    }

    public static void fadeOut(View view) {
        fadeOut(view, 400);
    }

    public static void fadeOut(View view, int i) {
        if (view != null && view.getVisibility() == 0) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration((long) i);
            view.clearAnimation();
            view.startAnimation(alphaAnimation);
            view.setVisibility(8);
        }
    }

    public static Bitmap flipBitmap(@NonNull Bitmap bitmap, int i) {
        Bitmap bitmap2 = null;
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d(TAG, "flipBitmap: " + width + " x " + height);
        Matrix matrix = new Matrix();
        if (i == 1) {
            matrix.postScale(1.0f, -1.0f, (float) (width / 2), (float) (height / 2));
        } else {
            matrix.postScale(-1.0f, 1.0f, (float) (width / 2), (float) (height / 2));
        }
        try {
            bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (NullPointerException | OutOfMemoryError e2) {
            e2.printStackTrace();
        }
        if (bitmap2 == null) {
            return bitmap;
        }
        Canvas canvas = new Canvas(bitmap2);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        bitmap.recycle();
        return bitmap2;
    }

    public static int fromByteArray(byte[] bArr) {
        return (bArr[3] & 255) | (bArr[0] << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
    }

    public static ContentValues genContentValues(int i, String str) {
        Log.v(TAG, "genContentValues: path=" + str);
        ContentValues contentValues = new ContentValues(8);
        String fileName = FileUtils.getFileName(str);
        String convertOutputFormatToMimeType = convertOutputFormatToMimeType(i);
        contentValues.put("title", fileName);
        contentValues.put("_display_name", fileName);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    public static ContentValues genContentValues(int i, String str, int i2, int i3) {
        String fileName = FileUtils.getFileName(str);
        String convertOutputFormatToMimeType = convertOutputFormatToMimeType(i);
        Log.v(TAG, "genContentValues: path=" + str);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", fileName);
        contentValues.put("_display_name", fileName);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str);
        contentValues.put("resolution", i2 + "x" + i3);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    public static Bitmap generateFrontWatermark2File() {
        Bitmap loadFrontCameraWatermark = loadFrontCameraWatermark(CameraAppImpl.getAndroidContext());
        saveCustomWatermark2File(loadFrontCameraWatermark, false, true);
        return loadFrontCameraWatermark;
    }

    public static Bitmap generateUltraPixelWatermark2File() {
        Bitmap bitmap;
        int i;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = true;
        if (DataRepository.dataItemFeature().s_c_w_m()) {
            Bitmap loadAppCameraWatermark = loadAppCameraWatermark(CameraAppImpl.getAndroidContext(), options, Build.DEVICE);
            if (loadAppCameraWatermark == null) {
                loadAppCameraWatermark = loadAppCameraWatermark(CameraAppImpl.getAndroidContext(), options, "common");
            }
            String string = CameraSettings.getString(R.string.device_ultra_pixel_watermark_default_text);
            if (isGlobalVersion()) {
                i = CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.global_custom_watermark_startx);
                if (string.startsWith("1")) {
                    i = CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.global_special_custom_watermark_startx);
                }
            } else {
                i = CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.custom_watermark_startx);
                if (string.startsWith("1")) {
                    i = CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.special_custom_watermark_startx);
                }
            }
            bitmap = CustomTextWaterMark.newInstance(loadAppCameraWatermark, (float) i, (float) CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.custom_watermark_starty), string, CameraSettings.getResourceFloat(R.dimen.custom_watermark_letter_spacing, 0.0f)).drawToBitmap();
        } else {
            Context androidContext = CameraAppImpl.getAndroidContext();
            bitmap = loadAppCameraWatermark(androidContext, options, Build.DEVICE + CameraSettings.getString(R.string.device_ultra_pixel_app_watermark_family_name_suffix));
        }
        saveCustomWatermark2File(bitmap, true, false);
        return bitmap;
    }

    public static Bitmap generateWatermark2File() {
        long currentTimeMillis = System.currentTimeMillis();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = true;
        if (!DataRepository.dataItemFeature().s_o_a_w() && !DataRepository.dataItemFeature().qf()) {
            return null;
        }
        Bitmap loadAppCameraWatermark = loadAppCameraWatermark(CameraAppImpl.getAndroidContext(), options, Build.DEVICE);
        if (loadAppCameraWatermark == null) {
            loadAppCameraWatermark = loadAppCameraWatermark(CameraAppImpl.getAndroidContext(), options, "common");
        }
        if (DataRepository.dataItemFeature().s_c_w_m()) {
            int integer = CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.global_custom_watermark_startx);
            if (!isGlobalVersion() || integer == 0) {
                integer = CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.custom_watermark_startx);
            }
            loadAppCameraWatermark = CustomTextWaterMark.newInstance(loadAppCameraWatermark, (float) integer, (float) CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.custom_watermark_starty), CameraSettings.getCustomWatermark(), CameraSettings.getResourceFloat(R.dimen.custom_watermark_letter_spacing, 0.0f)).drawToBitmap();
        }
        saveCustomWatermark2File(loadAppCameraWatermark, false, false);
        DataRepository.dataItemGlobal().updateCustomWatermarkVersion();
        Log.d(TAG, "generateWatermark2File cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        return loadAppCameraWatermark;
    }

    public static int[] getAIWatermarkRange(int i, int i2, int[] iArr, float f2, Rect rect) {
        int[] iArr2 = new int[4];
        if (Math.abs(i - ((i2 + 270) % 360)) != 180) {
            iArr2[0] = (int) Math.ceil((double) (((float) iArr[1]) * f2));
            iArr2[1] = (int) Math.ceil((double) (((float) ((rect.right - rect.left) - iArr[2])) * f2));
            iArr2[2] = (int) Math.ceil((double) (((float) (iArr[3] - iArr[1])) * f2));
            iArr2[3] = (int) Math.ceil((double) (((float) (iArr[2] - iArr[0])) * f2));
        } else {
            iArr2[0] = (int) Math.ceil((double) (((float) ((rect.bottom - rect.top) - iArr[3])) * f2));
            iArr2[1] = (int) Math.ceil((double) (((float) iArr[0]) * f2));
            iArr2[2] = (int) Math.ceil((double) (((float) (iArr[3] - iArr[1])) * f2));
            iArr2[3] = (int) Math.ceil((double) (((float) (iArr[2] - iArr[0])) * f2));
        }
        iArr2[0] = (iArr2[0] / 2) * 2;
        iArr2[1] = (iArr2[1] / 2) * 2;
        iArr2[2] = (iArr2[2] / 4) * 4;
        iArr2[3] = (iArr2[3] / 4) * 4;
        return iArr2;
    }

    public static CameraSize getAlgorithmPreviewSize(List<CameraSize> list, double d2, CameraSize cameraSize) {
        if (cameraSize == null) {
            throw new IllegalArgumentException("limitSize can not be null!");
        } else if (list == null || list.isEmpty()) {
            Log.w(TAG, "null preview size list");
            return cameraSize;
        } else {
            int max = Math.max(SystemProperties.getInt("algorithm_limit_height", cameraSize.height), 500);
            Iterator<CameraSize> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CameraSize next = it.next();
                if (Math.abs((((double) next.width) / ((double) next.height)) - d2) <= 0.02d && next.height < max) {
                    cameraSize = next;
                    break;
                }
            }
            Log.d(TAG, "getAlgorithmPreviewSize: algorithmSize = " + cameraSize);
            return cameraSize;
        }
    }

    public static int getArrayIndex(int[] iArr, int i) {
        if (iArr == null) {
            return -1;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public static <T> int getArrayIndex(T[] tArr, T t) {
        if (tArr == null) {
            return -1;
        }
        int i = 0;
        for (T t2 : tArr) {
            if (Objects.equals(t2, t)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static int getAttributeIntValue(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return i;
        }
        try {
            return Integer.parseInt(attributeValue);
        } catch (Exception e2) {
            Log.w(TAG, "get attribute " + str + " failed", e2);
            return i;
        }
    }

    public static byte[] getBitmapData(Bitmap bitmap, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Size getBitmapSize(int i) {
        Bitmap decodeResource = BitmapFactory.decodeResource(CameraAppImpl.getAndroidContext().getResources(), i);
        if (decodeResource == null) {
            return new Size(0, 0);
        }
        int width = decodeResource.getWidth();
        int height = decodeResource.getHeight();
        decodeResource.recycle();
        return new Size(width, height);
    }

    public static int getBottomHeight() {
        return sBottomMargin + sBottomBarHeight;
    }

    private static String getCaller(StackTraceElement[] stackTraceElementArr, int i) {
        int i2 = i + 4;
        if (i2 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        StackTraceElement stackTraceElement = stackTraceElementArr[i2];
        return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
    }

    public static String getCallers(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(getCaller(stackTrace, i2));
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [int] */
    /* JADX WARNING: Unknown variable types count: 1 */
    public static int getCenterFocusDepthIndex(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length < 25) {
            return 1;
        }
        int length = bArr.length - 25;
        int i3 = length + 1;
        if (bArr[length] != 0) {
            return 1;
        }
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        byte b2 = ((bArr[i4] & 255) << 16) | ((bArr[i3] & 255) << 24);
        int i6 = i5 + 1;
        byte b3 = b2 | ((bArr[i5] & 255) << 8);
        int i7 = i6 + 1;
        byte b4 = b3 | (bArr[i6] & 255);
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        byte b5 = ((bArr[i8] & 255) << 16) | ((bArr[i7] & 255) << 24) | ((bArr[i9] & 255) << 8) | (bArr[i9 + 1] & 255);
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.focus_area_width);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.focus_area_height);
        int i10 = sWindowWidth;
        int i11 = (dimensionPixelSize * b4) / i10;
        int i12 = (int) (((float) (dimensionPixelSize2 * b5)) / ((((float) i10) * ((float) i2)) / ((float) i)));
        int[] iArr = new int[5];
        int i13 = 0;
        byte b6 = (b5 - i12) / 2;
        int i14 = 0;
        while (i14 < i12) {
            ? r7 = b6 + 1;
            int i15 = (b6 * b4) + ((b4 - i11) / 2);
            int i16 = 0;
            while (i16 < i11) {
                int i17 = i15 + 1;
                byte b7 = bArr[i15];
                iArr[b7] = iArr[b7] + 1;
                i16++;
                i15 = i17;
            }
            i14++;
            b6 = r7;
        }
        for (int i18 = 1; i18 < 5; i18++) {
            if (iArr[i13] < iArr[i18]) {
                i13 = i18;
            }
        }
        return i13;
    }

    public static int getChildMeasureWidth(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
        int measuredWidth = view.getMeasuredWidth();
        if (measuredWidth > 0) {
            return measuredWidth + i;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        view.measure(makeMeasureSpec, makeMeasureSpec);
        return view.getMeasuredWidth() + i;
    }

    public static double getCinematicAspectRatio() {
        return 2.39d;
    }

    public static int getCinematicAspectRatioMargin() {
        int i = sWindowWidth;
        return (int) ((((double) i) - (((double) ((i * 16) / 9)) / 2.39d)) / 2.0d);
    }

    private static File getColorMapXmlMapFile() {
        if (Build.VERSION.SDK_INT >= 26) {
            File file = (!b.ev || !SystemProperties.get("ro.boot.hwc").equalsIgnoreCase("India")) ? new File("/vendor/etc/screen_light.xml") : new File("/vendor/etc/screen_light_ind.xml");
            if (file.exists()) {
                return file;
            }
            Log.e(TAG, "screen_light.xml do not found under /vendor/etc, roll back to /system/etc");
        }
        File file2 = new File("/system/etc/screen_light.xml");
        if (file2.exists()) {
            return file2;
        }
        Log.e(TAG, "screen_light.xml do not found under /system/etc");
        return null;
    }

    public static String getDebugInfo() {
        StringBuilder sb = new StringBuilder();
        if ("1".equals(SystemProperties.get("persist.camera.debug.show_af")) || "1".equals(SystemProperties.get("persist.camera.debug.enable"))) {
            sb.append(addProperties("persist.camera.debug.param0"));
            sb.append(addProperties("persist.camera.debug.param1"));
            sb.append(addProperties("persist.camera.debug.param2"));
            sb.append(addProperties("persist.camera.debug.param3"));
            sb.append(addProperties("persist.camera.debug.param4"));
            sb.append(addProperties("persist.camera.debug.param5"));
            sb.append(addProperties("persist.camera.debug.param6"));
            sb.append(addProperties("persist.camera.debug.param7"));
            sb.append(addProperties("persist.camera.debug.param8"));
            sb.append(addProperties("persist.camera.debug.param9"));
        }
        if ("1".equals(SystemProperties.get("persist.camera.debug.show_awb"))) {
            sb.append(addProperties("persist.camera.debug.param10"));
            sb.append(addProperties("persist.camera.debug.param11"));
            sb.append(addProperties("persist.camera.debug.param12"));
            sb.append(addProperties("persist.camera.debug.param13"));
            sb.append(addProperties("persist.camera.debug.param14"));
            sb.append(addProperties("persist.camera.debug.param15"));
            sb.append(addProperties("persist.camera.debug.param16"));
            sb.append(addProperties("persist.camera.debug.param17"));
            sb.append(addProperties("persist.camera.debug.param18"));
            sb.append(addProperties("persist.camera.debug.param19"));
        }
        if ("1".equals(SystemProperties.get("persist.camera.debug.show_aec"))) {
            sb.append(addProperties("persist.camera.debug.param20"));
            sb.append(addProperties("persist.camera.debug.param21"));
            sb.append(addProperties("persist.camera.debug.param22"));
            sb.append(addProperties("persist.camera.debug.param23"));
            sb.append(addProperties("persist.camera.debug.param24"));
            sb.append(addProperties("persist.camera.debug.param25"));
            sb.append(addProperties("persist.camera.debug.param26"));
            sb.append(addProperties("persist.camera.debug.param27"));
            sb.append(addProperties("persist.camera.debug.param28"));
            sb.append(addProperties("persist.camera.debug.param29"));
        }
        sb.append(addProperties("persist.camera.debug.checkerf"));
        sb.append(addProperties("persist.camera.debug.fc"));
        if ("1".equals(SystemProperties.get("persist.camera.debug.hht"))) {
            sb.append(addProperties("camera.debug.hht.luma"));
        }
        if ("1".equals(SystemProperties.get("persist.camera.debug.autoscene"))) {
            sb.append(addProperties("camera.debug.hht.iso"));
        }
        return sb.toString();
    }

    public static String getDebugInformation(CaptureResult captureResult, String str) {
        byte[] satDbgInfo;
        MeteringRectangle[] meteringRectangleArr;
        StringBuilder sb = new StringBuilder();
        AECFrameControl aECFrameControl = CaptureResultParser.getAECFrameControl(captureResult);
        AFFrameControl aFFrameControl = CaptureResultParser.getAFFrameControl(captureResult);
        if (!(!"1".equals(SystemProperties.get("camera.preview.debug.show_shortGain")) || aECFrameControl == null || aECFrameControl.getAecExposureDatas() == null)) {
            sb.append(addDebugInfo("short gain : " + aECFrameControl.getAecExposureDatas()[0].getLinearGain()));
        }
        if (!(!"1".equals(SystemProperties.get("camera.preview.debug.show_adrcGain")) || aECFrameControl == null || aECFrameControl.getAecExposureDatas() == null)) {
            sb.append(addDebugInfo("adrc gain : " + (aECFrameControl.getAecExposureDatas()[2].getSensitivity() / aECFrameControl.getAecExposureDatas()[0].getSensitivity())));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.show_afRegion")) && (meteringRectangleArr = (MeteringRectangle[]) captureResult.get(CaptureResult.CONTROL_AF_REGIONS)) != null) {
            sb.append(addDebugInfo("af region : " + meteringRectangleArr[0].getRect().toString()));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.show_afMode"))) {
            sb.append(addDebugInfo("af mode : " + captureResult.get(CaptureResult.CONTROL_AF_MODE)));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.show_afStatus"))) {
            sb.append(addDebugInfo("af state : " + captureResult.get(CaptureResult.CONTROL_AF_STATE)));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.show_afLensPosition")) && aFFrameControl != null) {
            String str2 = "";
            if (aFFrameControl.getUseDACValue() == 0) {
                str2 = aFFrameControl.getTargetLensPosition() + str2;
            }
            sb.append(addDebugInfo("af lens position : " + str2));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.show_distance")) && captureResult.get(CaptureResult.LENS_FOCUS_DISTANCE) != null) {
            float floatValue = ((Float) captureResult.get(CaptureResult.LENS_FOCUS_DISTANCE)).floatValue();
            sb.append(addDebugInfo("distance : " + floatValue));
            sb.append(addDebugInfo("distance(m) : " + (1.0f / floatValue)));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.show_gyro")) && aFFrameControl != null) {
            for (int i = 0; i < aFFrameControl.getAFGyroData().getSampleCount(); i++) {
                sb.append(addDebugInfo("gyro : x: " + aFFrameControl.getAFGyroData().getpAngularVelocityX()[i] + ", y: " + aFFrameControl.getAFGyroData().getpAngularVelocityY()[i] + ", z: " + aFFrameControl.getAFGyroData().getpAngularVelocityZ()[i]));
            }
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.sat_info")) && (satDbgInfo = CaptureResultParser.getSatDbgInfo(captureResult)) != null) {
            sb.append(addDebugInfo(new String(satDbgInfo)));
        }
        if ("1".equals(SystemProperties.get("camera.preview.debug.xp_content"))) {
            byte[] exifValues = CaptureResultParser.getExifValues(captureResult);
            if (exifValues != null && exifValues.length > 0) {
                String str3 = new String(exifValues);
                Log.i("exifInfoString", "exifString:" + str3);
                sb.append(str3);
            }
            if (str != null) {
                Log.i("exifInfoString", "exifInfoString:" + str);
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static String getDefaultWatermarkFileName() {
        if (!DataRepository.dataItemFeature().s_c_w_m() || !DataRepository.dataItemFeature().qf()) {
            return WATERMARK_DEFAULT_FILE_NAME;
        }
        return Build.DEVICE + "_" + WATERMARK_SPACE + "_custom_watermark.png";
    }

    public static int getDisplayOrientation(int i, int i2) {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i2);
        if (capabilities == null) {
            return 90;
        }
        int sensorOrientation = capabilities.getSensorOrientation();
        return capabilities.getFacing() == 0 ? (360 - ((sensorOrientation + i) % 360)) % 360 : ((sensorOrientation - i) + 360) % 360;
    }

    public static Rect getDisplayRect() {
        return getDisplayRect(DataRepository.dataItemRunning().getUiStyle());
    }

    public static Rect getDisplayRect(int i) {
        int i2;
        int i3;
        if (i == 3) {
            i2 = sWindowHeight;
            i3 = 0;
        } else if (i != 4) {
            i3 = sTopMargin + sTopBarHeight;
            i2 = (int) (((float) sWindowWidth) * (i == 0 ? 1.3333334f : 1.7777778f));
        } else {
            i2 = sWindowWidth;
            i3 = (sTopBarHeight + (sWindowWidth / 4)) - dpToPixel(18.0f);
        }
        Rect rect = new Rect(0, i3, sWindowWidth, i2 + i3);
        Log.d(TAG, "getDisplayRect:" + rect);
        return rect;
    }

    public static int getDisplayRotation(Activity activity) {
        int i;
        if (!b.el() || !CameraSettings.isFrontCamera() || activity.getRequestedOrientation() != 7) {
            int i2 = mLockedOrientation;
            i = (i2 == 0 || i2 == 2) ? mLockedOrientation : 0;
        } else {
            i = activity.getWindowManager().getDefaultDisplay().getRotation();
        }
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 90;
        }
        if (i != 2) {
            return i != 3 ? 0 : 270;
        }
        return 180;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0052, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0053, code lost:
        $closeResource(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
        throw r0;
     */
    private static byte[] getDualCameraWatermarkData(int i, int i2, int[] iArr, int i3, DeviceWatermarkParam deviceWatermarkParam) {
        String str;
        byte[] bArr;
        Bitmap decodeByteArray;
        if (DataRepository.dataItemFeature().s_o_a_w() || DataRepository.dataItemFeature().qf()) {
            str = new File(CameraAppImpl.getAndroidContext().getFilesDir(), getDefaultWatermarkFileName()).getPath();
            if (!new File(str).exists()) {
                generateWatermark2File();
            }
        } else {
            str = CameraSettings.getDualCameraWaterMarkFilePathVendor();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            bArr = d.g.b.l(fileInputStream);
            try {
                $closeResource(null, fileInputStream);
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            bArr = null;
            Log.d(TAG, "Failed to load dual camera water mark", e);
            ImageWaterMark imageWaterMark = new ImageWaterMark(decodeByteArray, i, i2, i3, deviceWatermarkParam.getSize(), deviceWatermarkParam.getPaddingX(), deviceWatermarkParam.getPaddingY(), deviceWatermarkParam.isCinematicAspectRatio());
            iArr[0] = imageWaterMark.getWidth();
            iArr[1] = imageWaterMark.getHeight();
            iArr[2] = imageWaterMark.getPaddingX();
            iArr[3] = imageWaterMark.getPaddingY();
            return bArr;
        }
        if (!(bArr == null || iArr == null || iArr.length < 4 || (decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length)) == null)) {
            ImageWaterMark imageWaterMark2 = new ImageWaterMark(decodeByteArray, i, i2, i3, deviceWatermarkParam.getSize(), deviceWatermarkParam.getPaddingX(), deviceWatermarkParam.getPaddingY(), deviceWatermarkParam.isCinematicAspectRatio());
            iArr[0] = imageWaterMark2.getWidth();
            iArr[1] = imageWaterMark2.getHeight();
            iArr[2] = imageWaterMark2.getPaddingX();
            iArr[3] = imageWaterMark2.getPaddingY();
        }
        return bArr;
    }

    /* JADX INFO: finally extract failed */
    public static long getDuration(FileDescriptor fileDescriptor) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(fileDescriptor);
            long parseLong = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
            mediaMetadataRetriever.release();
            return parseLong;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, e2.getMessage(), e2);
            mediaMetadataRetriever.release();
            return 0;
        } catch (Throwable th) {
            mediaMetadataRetriever.release();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public static long getDuration(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            long parseLong = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
            mediaMetadataRetriever.release();
            return parseLong;
        } catch (Exception e2) {
            Log.e(TAG, e2.getMessage(), e2);
            mediaMetadataRetriever.release();
            return 0;
        } catch (Throwable th) {
            mediaMetadataRetriever.release();
            throw th;
        }
    }

    public static ExifInterface getExif(String str) {
        ExifInterface exifInterface = new ExifInterface();
        try {
            exifInterface.readExif(str);
        } catch (IOException e2) {
            Log.d(TAG, e2.getMessage());
        }
        return exifInterface;
    }

    public static ExifInterface getExif(byte[] bArr) {
        ExifInterface exifInterface = new ExifInterface();
        try {
            exifInterface.readExif(bArr);
        } catch (IOException e2) {
            Log.d(TAG, e2.getMessage());
        }
        return exifInterface;
    }

    public static Typeface getFZMiaoWuJWTypeface(Context context) {
        return getTypefaceFromFile(context, "vendor/camera/fonts/FZMiaoWuJW.ttf");
    }

    public static String getFileTitleFromPath(String str) {
        int lastIndexOf;
        if (TextUtils.isEmpty(str) || (lastIndexOf = str.lastIndexOf("/")) < 0 || lastIndexOf >= str.length() - 1) {
            return null;
        }
        String substring = str.substring(lastIndexOf + 1);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        int indexOf = substring.indexOf(".");
        return indexOf < 0 ? substring : substring.substring(0, indexOf);
    }

    public static byte[] getFirstPlane(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0050, code lost:
        if (r3 != null) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0052, code lost:
        $closeResource(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0055, code lost:
        throw r0;
     */
    private static byte[] getFrontCameraWatermarkData(int i, int i2, int[] iArr, int i3, DeviceWatermarkParam deviceWatermarkParam) {
        byte[] bArr;
        Bitmap decodeByteArray;
        String str = Build.DEVICE + "_front" + b.Uk() + ".webp";
        try {
            InputStream open = CameraAppImpl.getAndroidContext().getAssets().open("watermarks/" + str);
            bArr = d.g.b.l(open);
            if (open != null) {
                try {
                    $closeResource(null, open);
                } catch (IOException e2) {
                    e = e2;
                }
            }
        } catch (IOException e3) {
            e = e3;
            bArr = null;
            Log.d(TAG, "Failed to load front camera water mark", e);
            ImageWaterMark imageWaterMark = new ImageWaterMark(decodeByteArray, i, i2, i3, deviceWatermarkParam.getSize(), deviceWatermarkParam.getPaddingX(), deviceWatermarkParam.getPaddingY(), deviceWatermarkParam.isCinematicAspectRatio());
            iArr[0] = imageWaterMark.getWidth();
            iArr[1] = imageWaterMark.getHeight();
            iArr[2] = imageWaterMark.getPaddingX();
            iArr[3] = imageWaterMark.getPaddingY();
            return bArr;
        }
        if (!(bArr == null || iArr == null || iArr.length < 4 || (decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length)) == null)) {
            ImageWaterMark imageWaterMark2 = new ImageWaterMark(decodeByteArray, i, i2, i3, deviceWatermarkParam.getSize(), deviceWatermarkParam.getPaddingX(), deviceWatermarkParam.getPaddingY(), deviceWatermarkParam.isCinematicAspectRatio());
            iArr[0] = imageWaterMark2.getWidth();
            iArr[1] = imageWaterMark2.getHeight();
            iArr[2] = imageWaterMark2.getPaddingX();
            iArr[3] = imageWaterMark2.getPaddingY();
        }
        return bArr;
    }

    public static int getHeader2Int(File file, int i) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[4];
            fileInputStream.skip((long) i);
            fileInputStream.read(bArr);
            fileInputStream.close();
            return fromByteArray(bArr);
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static int getIntField(String str, Object obj, String str2, String str3) {
        try {
            return miui.reflect.b.c(str, str2, str3).getInt(obj);
        } catch (NoSuchClassException e2) {
            Log.e(TAG, "no class " + str, e2);
            return Integer.MIN_VALUE;
        } catch (NoSuchFieldException e3) {
            Log.e(TAG, "no field ", e3);
            return Integer.MIN_VALUE;
        }
    }

    public static int getJpegRotation(int i, int i2) {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        int sensorOrientation = capabilities.getSensorOrientation();
        if (i2 != -1) {
            return capabilities.getFacing() == 0 ? ((sensorOrientation - i2) + 360) % 360 : (sensorOrientation + i2) % 360;
        }
        Log.w(TAG, "getJpegRotation: orientation UNKNOWN!!! return sensorOrientation...");
        return sensorOrientation;
    }

    public static Typeface getLanTineGBTypeface(Context context) {
        return getTypefaceFromFile(context, "vendor/etc/camera/fonts/MI+LanTing_GB+Outside+YS_V2.3_20160322.ttf");
    }

    public static Typeface getMFYueYuanTypeface(Context context) {
        return getTypefaceFromFile(context, "vendor/etc/camera/fonts/MFYueYuan-Regular.ttf");
    }

    public static c getMethod(Class<?>[] clsArr, String str, String str2) {
        c cVar = null;
        if (clsArr != null) {
            try {
                if (clsArr.length == 1) {
                    cVar = c.a(clsArr[0], str, str2);
                }
            } catch (NoSuchMethodException unused) {
                if (clsArr[0].getSuperclass() != null) {
                    clsArr[0] = clsArr[0].getSuperclass();
                    cVar = getMethod(clsArr, str, str2);
                }
            }
        }
        if (cVar == null) {
            Log.e(TAG, "getMethod fail, " + str + "[" + str2 + "]");
        }
        return cVar;
    }

    public static Typeface getMiuiTimeTypeface(Context context) {
        return getTypefaceFromAssets(context, "fonts/MIUI_Time.ttf");
    }

    public static Typeface getMiuiTypeface(Context context) {
        return getTypefaceFromAssets(context, "fonts/MIUI_Normal.ttf");
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"));
        Log.v(TAG, "navBarHeight=" + dimensionPixelSize);
        return dimensionPixelSize;
    }

    public static CameraSize getOptimalJpegThumbnailSize(List<CameraSize> list, double d2) {
        CameraSize cameraSize = null;
        if (list == null) {
            Log.w(TAG, "null thumbnail size list");
            return null;
        }
        double d3 = 0.0d;
        for (CameraSize cameraSize2 : list) {
            if (!(cameraSize2.getWidth() == 0 || cameraSize2.getHeight() == 0)) {
                double width = ((double) cameraSize2.getWidth()) / ((double) cameraSize2.getHeight());
                double abs = Math.abs(width - d2);
                double d4 = d3 - d2;
                if ((abs <= Math.abs(d4) || abs <= 0.001d) && (cameraSize == null || abs < Math.abs(d4) || cameraSize2.getWidth() > cameraSize.getWidth())) {
                    cameraSize = cameraSize2;
                    d3 = width;
                }
            }
        }
        if (cameraSize == null) {
            Log.w(TAG, "No thumbnail size match the aspect ratio");
            for (CameraSize cameraSize3 : list) {
                if (cameraSize == null || cameraSize3.getWidth() > cameraSize.getWidth()) {
                    cameraSize = cameraSize3;
                }
            }
        }
        return cameraSize;
    }

    public static CameraSize getOptimalPreviewSize(boolean z, int i, List<CameraSize> list, double d2) {
        return getOptimalPreviewSize(z, i, list, d2, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x015d  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0160  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01aa  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0158 A[SYNTHETIC] */
    public static CameraSize getOptimalPreviewSize(boolean z, int i, List<CameraSize> list, double d2, CameraSize cameraSize) {
        boolean z2;
        Point point;
        int i2;
        Iterator<CameraSize> it;
        Point point2;
        CameraSize cameraSize2;
        CameraSize cameraSize3 = null;
        if (list == null) {
            Log.w(TAG, "null preview size list");
            return null;
        }
        int integer = com.mi.config.d.getInteger(com.mi.config.d.pw, 0);
        int i3 = 1080;
        if (integer != 0) {
            boolean z3 = i == Camera2DataContainer.getInstance().getFrontCameraId();
            if (sWindowWidth < 1080) {
                integer &= -15;
            }
            if ((integer & (((z3 ? 2 : 1) << (!z ? 0 : 2)) | 0)) != 0) {
                z2 = true;
                int i4 = sWindowWidth;
                int i5 = sWindowHeight;
                if (z2) {
                    i5 = Math.min(i5, 1920);
                }
                point = new Point(i4, i5);
                if (!b.sl() && b.om()) {
                    i3 = LIMIT_SURFACE_WIDTH;
                }
                i2 = point.x;
                if (i2 > i3) {
                    point.y = (point.y * i3) / i2;
                    point.x = i3;
                }
                if (cameraSize != null) {
                    if (point.x > cameraSize.height || point.y > cameraSize.width) {
                        double d3 = ((double) point.y) / ((double) point.x);
                        int i6 = cameraSize.width;
                        int i7 = cameraSize.height;
                        if (i6 <= i7) {
                            i7 = i6;
                        }
                        point.x = i7;
                        point.y = (int) (d3 * ((double) point.x));
                    }
                    z2 = false;
                }
                it = list.iterator();
                CameraSize cameraSize4 = null;
                double d4 = Double.MAX_VALUE;
                double d5 = Double.MAX_VALUE;
                while (true) {
                    if (it.hasNext()) {
                        point2 = point;
                        cameraSize2 = cameraSize4;
                        break;
                    }
                    cameraSize2 = it.next();
                    double d6 = ((double) cameraSize2.width) / ((double) cameraSize2.height);
                    Log.e(TAG, "getOptimalPreviewSize: height:" + cameraSize2.height + " width: " + cameraSize2.width + " ratio:" + d6);
                    if (Math.abs(d6 - d2) > 0.02d) {
                        point2 = point;
                    } else {
                        point2 = point;
                        if (!z2 || (point2.x > cameraSize2.height && point2.y > cameraSize2.width)) {
                            int abs = Math.abs(point2.x - cameraSize2.height) + Math.abs(point2.y - cameraSize2.width);
                            if (abs == 0) {
                                cameraSize3 = cameraSize2;
                                break;
                            }
                            if (cameraSize2.height <= point2.x && cameraSize2.width <= point2.y) {
                                double d7 = (double) abs;
                                if (d7 < d4) {
                                    d4 = d7;
                                    cameraSize4 = cameraSize2;
                                }
                            }
                            double d8 = (double) abs;
                            if (d8 < d5) {
                                d5 = d8;
                                cameraSize3 = cameraSize2;
                            }
                        } else {
                            Log.e(TAG, "getOptimalPreviewSize: " + cameraSize2.toString() + " | " + point2.toString());
                        }
                    }
                    point = point2;
                }
                if (cameraSize2 == null) {
                    cameraSize2 = cameraSize3;
                }
                if (cameraSize2 == null) {
                    Log.w(TAG, String.format(Locale.ENGLISH, "no preview size match the aspect ratio: %.2f", Double.valueOf(d2)));
                    double d9 = Double.MAX_VALUE;
                    for (CameraSize cameraSize5 : list) {
                        double abs2 = (double) (Math.abs(point2.x - cameraSize5.getHeight()) + Math.abs(point2.y - cameraSize5.getWidth()));
                        if (abs2 < d9) {
                            cameraSize2 = cameraSize5;
                            d9 = abs2;
                        }
                    }
                }
                if (cameraSize2 != null) {
                    Log.i(TAG, String.format(Locale.ENGLISH, "best preview size: %dx%d", Integer.valueOf(cameraSize2.getWidth()), Integer.valueOf(cameraSize2.getHeight())));
                }
                return cameraSize2;
            }
        }
        z2 = false;
        int i42 = sWindowWidth;
        int i52 = sWindowHeight;
        if (z2) {
        }
        point = new Point(i42, i52);
        i3 = LIMIT_SURFACE_WIDTH;
        i2 = point.x;
        if (i2 > i3) {
        }
        if (cameraSize != null) {
        }
        it = list.iterator();
        CameraSize cameraSize42 = null;
        double d42 = Double.MAX_VALUE;
        double d52 = Double.MAX_VALUE;
        while (true) {
            if (it.hasNext()) {
            }
            point = point2;
        }
        if (cameraSize2 == null) {
        }
        if (cameraSize2 == null) {
        }
        if (cameraSize2 != null) {
        }
        return cameraSize2;
    }

    public static CameraSize getOptimalVideoSnapshotPictureSize(List<CameraSize> list, double d2, int i, int i2) {
        CameraSize cameraSize = null;
        if (list == null) {
            Log.e(TAG, "null size list");
            return null;
        }
        for (CameraSize cameraSize2 : list) {
            if (Math.abs((((double) cameraSize2.getWidth()) / ((double) cameraSize2.getHeight())) - d2) <= 0.02d && ((cameraSize == null || cameraSize2.getWidth() > cameraSize.getWidth()) && cameraSize2.getWidth() <= i && cameraSize2.getHeight() <= i2)) {
                cameraSize = cameraSize2;
            }
        }
        if (cameraSize == null) {
            Log.w(TAG, "No picture size match the aspect ratio");
            for (CameraSize cameraSize3 : list) {
                if (cameraSize == null || cameraSize3.getWidth() > cameraSize.getWidth()) {
                    cameraSize = cameraSize3;
                }
            }
        }
        return cameraSize;
    }

    public static byte[] getPixels(byte[] bArr, int i, int i2, int[] iArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[(iArr[2] * iArr[3] * i2)];
        int i3 = ((iArr[1] * i) + iArr[0]) * i2;
        int i4 = 0;
        for (int i5 = 0; i5 < iArr[3]; i5++) {
            System.arraycopy(bArr, i3, bArr2, i4, iArr[2] * i2);
            i3 += i * i2;
            i4 += iArr[2] * i2;
        }
        return bArr2;
    }

    public static Rect getPreviewRect(Context context) {
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        Rect displayRect = getDisplayRect(uiStyle);
        if (uiStyle == 3 && isNotchDevice && (DataRepository.dataItemFeature().te() || DataRepository.dataItemFeature().ve())) {
            displayRect.top = 0;
        }
        return displayRect;
    }

    public static int[] getRange(int i, int i2, int i3) {
        int[] iArr = {0, 0, (int) (((double) i) * 0.3d), i2};
        iArr[0] = (iArr[0] / 2) * 2;
        iArr[1] = (iArr[1] / 2) * 2;
        iArr[2] = (iArr[2] / 4) * 4;
        iArr[3] = (iArr[3] / 4) * 4;
        return iArr;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static float getRatio(String str) {
        char c2;
        switch (str.hashCode()) {
            case -2109552250:
                if (str.equals(ComponentConfigRatio.RATIO_FULL_18_7_5X9)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 50858:
                if (str.equals(ComponentConfigRatio.RATIO_1X1)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 53743:
                if (str.equals(ComponentConfigRatio.RATIO_4X3)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 1515430:
                if (str.equals(ComponentConfigRatio.RATIO_16X9)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 1517352:
                if (str.equals(ComponentConfigRatio.RATIO_FULL_18X9)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1518313:
                if (str.equals(ComponentConfigRatio.RATIO_FULL_19X9)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 1539455:
                if (str.equals(ComponentConfigRatio.RATIO_FULL_20X9)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 1456894192:
                if (str.equals(ComponentConfigRatio.RATIO_FULL_195X9)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                return 1.3333333f;
            case 1:
                return 1.7777777f;
            case 2:
                return 1.0f;
            case 3:
                return 2.0f;
            case 4:
                return 2.1111112f;
            case 5:
                return 2.1666667f;
            case 6:
                return 2.0833333f;
            case 7:
                return 2.2222223f;
            default:
                return 1.3333333f;
        }
    }

    public static int[] getRelativeLocation(View view, View view2) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        view2.getLocationInWindow(iArr);
        iArr[0] = iArr[0] - i;
        iArr[1] = iArr[1] - i2;
        return iArr;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawRoundRect(rectF, f2, f2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static double getScreenInches(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        double sqrt = Math.sqrt(Math.pow((double) (((float) sWindowWidth) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) sWindowHeight) / displayMetrics.ydpi), 2.0d));
        Log.d(TAG, "getScreenInches=" + sqrt);
        return sqrt;
    }

    public static int getScreenLightColor(int i) {
        initScreenLightColorMap();
        if (COLOR_TEMPERATURE_LIST.size() == 0 || COLOR_TEMPERATURE_MAP.size() == 0) {
            Log.e(TAG, "color temperature list empty!");
            return -1;
        }
        int binarySearchRightMost = binarySearchRightMost(COLOR_TEMPERATURE_LIST, Integer.valueOf(i));
        if (binarySearchRightMost >= COLOR_TEMPERATURE_LIST.size()) {
            binarySearchRightMost = COLOR_TEMPERATURE_LIST.size() - 1;
        } else if (binarySearchRightMost > 0) {
            int i2 = binarySearchRightMost - 1;
            if (COLOR_TEMPERATURE_LIST.get(binarySearchRightMost).intValue() - i > i - COLOR_TEMPERATURE_LIST.get(i2).intValue()) {
                binarySearchRightMost = i2;
            }
        }
        Log.d(TAG, "getScreenLightColor " + i + "K -> " + COLOR_TEMPERATURE_LIST.get(binarySearchRightMost) + ExifInterface.GpsSpeedRef.KILOMETERS);
        return COLOR_TEMPERATURE_MAP.get(binarySearchRightMost).intValue();
    }

    public static int getSensorOrientation(int i) {
        return Camera2DataContainer.getInstance().getCapabilities(i).getSensorOrientation();
    }

    public static int getShootOrientation(Activity activity, int i) {
        return ((i - getDisplayRotation(activity)) + 360) % 360;
    }

    public static float getShootRotation(Activity activity, float f2) {
        float displayRotation = f2 - ((float) getDisplayRotation(activity));
        while (displayRotation < 0.0f) {
            displayRotation += 360.0f;
        }
        while (displayRotation > 360.0f) {
            displayRotation -= 360.0f;
        }
        return displayRotation;
    }

    private static Object getStaticObjectField(Class<?> cls, String str) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(str);
    }

    public static int getStatusBarHeight(Context context) {
        int i;
        if (DataRepository.dataItemFeature().c_0x44()) {
            i = context.getResources().getDimensionPixelSize(R.dimen.camera_status_bar_height);
        } else {
            int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            i = identifier > 0 ? context.getResources().getDimensionPixelSize(identifier) : 0;
        }
        Log.v(TAG, "StatusBarHeight=" + i);
        return i;
    }

    public static File getStorageDirectory() {
        return isExternalStorageMounted() ? Environment.getExternalStorageDirectory() : INTERNAL_STORAGE_DIRECTORY;
    }

    public static MiYuvImage getSubYuvImage(byte[] bArr, int i, int i2, int i3, int i4, int[] iArr) {
        byte[] bArr2 = new byte[(((iArr[2] * iArr[3]) * 3) / 2)];
        int i5 = (iArr[1] * i3) + iArr[0];
        int i6 = 0;
        for (int i7 = 0; i7 < iArr[3]; i7++) {
            System.arraycopy(bArr, i5, bArr2, i6, iArr[2]);
            i5 += i3;
            i6 += iArr[2];
        }
        int i8 = (i3 * (i2 - 1)) + i + ((iArr[1] / 2) * i4) + iArr[0];
        for (int i9 = 0; i9 < iArr[3] / 2; i9++) {
            System.arraycopy(bArr, i8, bArr2, i6, iArr[2]);
            i8 += i4;
            i6 += iArr[2];
        }
        return new MiYuvImage(bArr2, iArr[2], iArr[3], 35);
    }

    public static Rect getTapableRectWithEdgeSlop(boolean z, Rect rect, int i, Context context) {
        Rect rect2 = new Rect(rect);
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (z) {
            rect2 = getDisplayRect(0);
            if (uiStyle == 0 && i == 165) {
                int height = (rect2.height() - rect2.width()) / 2;
                rect2.top += height;
                rect2.bottom -= height;
            } else if (uiStyle == 3) {
                rect2.top = sTopMargin;
            }
        }
        int calculateDefaultPreviewEdgeSlop = calculateDefaultPreviewEdgeSlop(context);
        int i2 = SystemProperties.getInt("camera_touch_edge_slop", -1);
        if (i2 != -1) {
            calculateDefaultPreviewEdgeSlop = dpToPixel((float) i2);
        }
        rect2.inset(calculateDefaultPreviewEdgeSlop, calculateDefaultPreviewEdgeSlop);
        Log.d(TAG, "getTapableRectWithEdgeSlop: after rect = " + rect2 + ", edgeSlop = " + calculateDefaultPreviewEdgeSlop);
        return rect2;
    }

    public static byte[] getTimeWaterMarkData(int i, int i2, String str, int[] iArr, int i3, DeviceWatermarkParam deviceWatermarkParam) {
        NewStyleTextWaterMark newStyleTextWaterMark = new NewStyleTextWaterMark(str, i, i2, i3, deviceWatermarkParam.isCinematicAspectRatio());
        if (iArr != null && iArr.length >= 4) {
            iArr[0] = newStyleTextWaterMark.getWidth();
            iArr[1] = newStyleTextWaterMark.getHeight();
            iArr[2] = newStyleTextWaterMark.getPaddingX();
            iArr[3] = newStyleTextWaterMark.getPaddingY();
        }
        return ((StringTexture) newStyleTextWaterMark.getTexture()).getBitmapData(Bitmap.CompressFormat.PNG);
    }

    public static String getTimeWatermark() {
        return getTimeWatermark(b.bm());
    }

    public static String getTimeWatermark(boolean z) {
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append(new SimpleDateFormat("yyyy/M/d", Locale.ENGLISH).format(new Date()).toCharArray());
        } else {
            sb.append(new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).format(new Date()).toCharArray());
        }
        sb.append(" ");
        Time time = new Time();
        time.set(System.currentTimeMillis());
        sb.append(String.format(Locale.ENGLISH, "%02d", Integer.valueOf(time.hour)));
        sb.append(":");
        sb.append(String.format(Locale.ENGLISH, "%02d", Integer.valueOf(time.minute)));
        return sb.toString();
    }

    public static int getTopHeight() {
        return sTopMargin + sTopBarHeight;
    }

    public static long getTotalMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    private static synchronized Typeface getTypefaceFromAssets(Context context, String str) {
        Typeface typeface;
        synchronized (Util.class) {
            if (!sTypefaces.containsKey(str)) {
                sTypefaces.put(str, Typeface.createFromAsset(context.getAssets(), str));
            }
            typeface = sTypefaces.get(str);
        }
        return typeface;
    }

    private static synchronized Typeface getTypefaceFromFile(Context context, String str) {
        Typeface typeface;
        synchronized (Util.class) {
            if (!sTypefaces.containsKey(str)) {
                sTypefaces.put(str, Typeface.createFromFile(new File(str)));
            }
            typeface = sTypefaces.get(str);
        }
        return typeface;
    }

    public static String getWatermarkFileName() {
        return (!CameraSettings.getCustomWatermark().equals(CameraSettings.getDefaultWatermarkStr()) || !CameraSettings.isUltraPixelRearOn() || DataRepository.dataItemFeature().Vb()) ? CameraSettings.isFrontCameraWaterMarkOpen() ? WATERMARK_FRONT_FILE_NAME : getDefaultWatermarkFileName() : WATERMARK_ULTRA_PIXEL_FILE_NAME;
    }

    public static int[] getWatermarkRange(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        float f2 = z3 ? 0.31f : 0.11f;
        int cinematicAspectRatio = z3 ? i > i2 ? ((int) (((double) i2) - (((double) i) / getCinematicAspectRatio()))) / 2 : ((int) (((double) i) - (((double) i2) / getCinematicAspectRatio()))) / 2 : 0;
        int[] iArr = new int[4];
        if (i3 != 0) {
            if (i3 != 90) {
                if (i3 != 180) {
                    if (i3 == 270) {
                        if (z && z2) {
                            iArr[0] = 0;
                            int i4 = (int) (((float) i2) * f2);
                            iArr[1] = i2 - i4;
                            iArr[2] = i;
                            iArr[3] = i4 - cinematicAspectRatio;
                        } else if (z) {
                            iArr[0] = 0;
                            int i5 = (int) (((float) i2) * f2);
                            iArr[1] = i2 - i5;
                            iArr[2] = i / 2;
                            iArr[3] = i5 - cinematicAspectRatio;
                        } else {
                            int i6 = i / 2;
                            iArr[0] = i6;
                            int i7 = (int) (((float) i2) * f2);
                            iArr[1] = i2 - i7;
                            iArr[2] = i6;
                            iArr[3] = i7 - cinematicAspectRatio;
                        }
                    }
                } else if (z && z2) {
                    iArr[0] = 0;
                    iArr[1] = cinematicAspectRatio;
                    iArr[2] = (int) (((float) i) * f2);
                    iArr[3] = i2 - (cinematicAspectRatio * 2);
                } else if (z) {
                    iArr[0] = 0;
                    iArr[1] = cinematicAspectRatio;
                    iArr[2] = (int) (((float) i) * f2);
                    iArr[3] = ((int) (((float) i2) * 0.6f)) - cinematicAspectRatio;
                } else {
                    iArr[0] = 0;
                    int i8 = i2 / 2;
                    iArr[1] = i8;
                    iArr[2] = (int) (((float) i) * f2);
                    iArr[3] = i8 - cinematicAspectRatio;
                }
            } else if (z && z2) {
                iArr[0] = 0;
                iArr[1] = cinematicAspectRatio;
                iArr[2] = i;
                iArr[3] = ((int) (((float) i2) * f2)) - cinematicAspectRatio;
            } else if (z) {
                int i9 = i / 2;
                iArr[0] = i9;
                iArr[1] = cinematicAspectRatio;
                iArr[2] = i9;
                iArr[3] = ((int) (((float) i2) * f2)) - cinematicAspectRatio;
            } else {
                iArr[0] = 0;
                iArr[1] = cinematicAspectRatio;
                iArr[2] = i / 2;
                iArr[3] = ((int) (((float) i2) * f2)) - cinematicAspectRatio;
            }
        } else if (z && z2) {
            int i10 = (int) (((float) i) * f2);
            iArr[0] = i - i10;
            iArr[1] = cinematicAspectRatio;
            iArr[2] = i10;
            iArr[3] = i2 - (cinematicAspectRatio * 2);
        } else if (z) {
            int i11 = (int) (((float) i) * f2);
            iArr[0] = i - i11;
            float f3 = (float) i2;
            iArr[1] = (int) (0.4f * f3);
            iArr[2] = i11;
            iArr[3] = ((int) (f3 * 0.6f)) - cinematicAspectRatio;
        } else {
            int i12 = (int) (((float) i) * f2);
            iArr[0] = i - i12;
            iArr[1] = cinematicAspectRatio;
            iArr[2] = i12;
            iArr[3] = (i2 / 2) - cinematicAspectRatio;
        }
        iArr[0] = (iArr[0] / 2) * 2;
        iArr[1] = (iArr[1] / 2) * 2;
        iArr[2] = (iArr[2] / 4) * 4;
        iArr[3] = (iArr[3] / 4) * 4;
        return iArr;
    }

    public static String getZoomRatioText(float f2) {
        StringBuilder sb = new StringBuilder();
        float decimal = HybridZoomingSystem.toDecimal(f2);
        int i = (int) decimal;
        if (((int) ((10.0f * decimal) - ((float) (i * 10)))) == 0) {
            sb.append(String.valueOf(i));
        } else {
            sb.append(String.valueOf(decimal));
        }
        sb.append("X");
        return sb.toString();
    }

    private static void initIsCameraUnderScreen() {
        sIsCameraUnderScreen = b.bl();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0078 A[Catch:{ XmlPullParserException -> 0x011c, IOException -> 0x0114, all -> 0x0106 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00fe A[EDGE_INSN: B:62:0x00fe->B:47:0x00fe ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    private static void initScreenLightColorMap() {
        FileReader fileReader;
        XmlResourceParser xmlResourceParser;
        if (COLOR_TEMPERATURE_LIST.size() <= 0 && COLOR_TEMPERATURE_MAP.size() <= 0) {
            File colorMapXmlMapFile = getColorMapXmlMapFile();
            if (colorMapXmlMapFile != null) {
                try {
                    fileReader = new FileReader(colorMapXmlMapFile);
                    try {
                        XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
                        newInstance.setNamespaceAware(false);
                        xmlResourceParser = newInstance.newPullParser();
                        try {
                            xmlResourceParser.setInput(fileReader);
                        } catch (FileNotFoundException | XmlPullParserException e2) {
                            e = e2;
                        }
                    } catch (FileNotFoundException | XmlPullParserException e3) {
                        e = e3;
                        xmlResourceParser = null;
                        e.printStackTrace();
                        if (xmlResourceParser == null) {
                        }
                        while (true) {
                            try {
                                if (xmlResourceParser.next() == 3) {
                                }
                            } catch (XmlPullParserException unused) {
                                closeSafely(fileReader);
                                if (!(xmlResourceParser instanceof XmlResourceParser)) {
                                    return;
                                }
                            } catch (IOException unused2) {
                                closeSafely(fileReader);
                                if (!(xmlResourceParser instanceof XmlResourceParser)) {
                                    return;
                                }
                            } catch (Throwable th) {
                                closeSafely(fileReader);
                                if (xmlResourceParser instanceof XmlResourceParser) {
                                    ((XmlResourceParser) xmlResourceParser).close();
                                }
                                throw th;
                            }
                        }
                        closeSafely(fileReader);
                        if (!(xmlResourceParser instanceof XmlResourceParser)) {
                        }
                        ((XmlResourceParser) xmlResourceParser).close();
                    }
                } catch (FileNotFoundException | XmlPullParserException e4) {
                    e = e4;
                    xmlResourceParser = null;
                    fileReader = null;
                    e.printStackTrace();
                    if (xmlResourceParser == null) {
                    }
                    while (true) {
                        if (xmlResourceParser.next() == 3) {
                        }
                    }
                    closeSafely(fileReader);
                    if (!(xmlResourceParser instanceof XmlResourceParser)) {
                    }
                    ((XmlResourceParser) xmlResourceParser).close();
                }
            } else {
                xmlResourceParser = null;
                fileReader = null;
            }
            if (xmlResourceParser == null) {
                Log.d(TAG, "Cannot find screen color map in system, try local resource.");
                int identifier = CameraAppImpl.getAndroidContext().getResources().getIdentifier("screen_light", "xml", CameraAppImpl.getAndroidContext().getPackageName());
                if (identifier <= 0) {
                    Log.e(TAG, "res/xml/screen_light.xml not found!");
                    return;
                }
                xmlResourceParser = CameraAppImpl.getAndroidContext().getResources().getXml(identifier);
            }
            while (true) {
                if (xmlResourceParser.next() == 3) {
                    break;
                } else if (xmlResourceParser.getEventType() == 2) {
                    if (!"screen".equals(xmlResourceParser.getName())) {
                        continue;
                    } else if (!SCREEN_VENDOR.equals(xmlResourceParser.getAttributeValue(null, com.mi.config.d.VENDOR))) {
                        skip(xmlResourceParser);
                    } else {
                        Log.d(TAG, "load screen light parameters for " + SCREEN_VENDOR);
                        while (true) {
                            if (xmlResourceParser.next() == 1) {
                                break;
                            } else if (xmlResourceParser.getEventType() == 2) {
                                if (!"light".equals(xmlResourceParser.getName())) {
                                    break;
                                }
                                int attributeIntValue = getAttributeIntValue(xmlResourceParser, "CCT", 0);
                                int attributeIntValue2 = getAttributeIntValue(xmlResourceParser, "R", 0);
                                int attributeIntValue3 = getAttributeIntValue(xmlResourceParser, "G", 0);
                                int attributeIntValue4 = getAttributeIntValue(xmlResourceParser, miui.reflect.b.fy, 0);
                                COLOR_TEMPERATURE_LIST.add(Integer.valueOf(attributeIntValue));
                                COLOR_TEMPERATURE_MAP.add(Integer.valueOf(Color.rgb(attributeIntValue2, attributeIntValue3, attributeIntValue4)));
                            }
                        }
                    }
                }
            }
            closeSafely(fileReader);
            if (!(xmlResourceParser instanceof XmlResourceParser)) {
                return;
            }
            ((XmlResourceParser) xmlResourceParser).close();
        }
    }

    public static void initStatusBarHeight(Context context) {
        sNavigationBarHeight = checkDeviceHasNavigationBar(context) ? getNavigationBarHeight(context) : 0;
        sStatusBarHeight = getStatusBarHeight(context);
    }

    public static void initialize(Context context) {
        initIsCameraUnderScreen();
        updateDeviceConfig(context);
        sIsnotchScreenHidden = isNotchScreenHidden(context);
        isNotchDevice = SystemProperties.getInt("ro.miui.notch", 0) == 1 && !sIsnotchScreenHidden;
        if (Build.DEVICE.equalsIgnoreCase("laurel_sprout") || Build.DEVICE.equalsIgnoreCase("toco")) {
            isNotchDevice = !sIsnotchScreenHidden;
        }
        sIsFullScreenNavBarHidden = isFullScreenNavBarHidden(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        sPixelDensity = displayMetrics.noncompatDensity;
        sImageFileNamer = new ImageFileNamer(context.getString(R.string.image_file_name_format));
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        int i = point.x;
        int i2 = point.y;
        if (i < i2) {
            sWindowWidth = i;
            sWindowHeight = i2;
        } else {
            sWindowWidth = i2;
            sWindowHeight = i;
        }
        if ("hercules".equals(Build.DEVICE)) {
            sWindowHeight = 2244;
        }
        sCenterDisplayHeight = Math.round(((float) (sWindowWidth * 16)) / 9.0f);
        sTopMargin = (isNotchDevice || isCameraUnderScreen()) ? getStatusBarHeight(context) : 0;
        int round = Math.round(((float) ((sWindowHeight - sTopMargin) - sCenterDisplayHeight)) * 0.5f);
        sBottomMargin = round;
        sTopBarHeight = round;
        sBottomBarHeight = sCenterDisplayHeight - Math.round(((float) (sWindowWidth * 4)) / 3.0f);
        Log.w(TAG, "initialize: sCenterDisplayHeight = " + sCenterDisplayHeight + ", sTopMargin = " + sTopMargin + ", sTopBarHeight = " + sTopBarHeight + ", sBottomMargin = " + sBottomMargin + ", sBottomBarHeight = " + sBottomBarHeight + ", windowSize = " + sWindowWidth + "x" + sWindowHeight);
        CameraSettings.BOTTOM_CONTROL_HEIGHT = getBottomHeight();
        sAAID = Settings.Global.getString(context.getContentResolver(), "ad_aaid");
        isDarkMode = context.getResources().getConfiguration().uiMode == 32;
        Log.i(TAG, String.format(Locale.ENGLISH, "windowSize=%dx%d density=%.4f", Integer.valueOf(sWindowWidth), Integer.valueOf(sWindowHeight), Float.valueOf(sPixelDensity)));
        checkHasBackLightSensot(context);
    }

    public static void installPackage(Context context, String str, CompatibilityUtils.PackageInstallerListener packageInstallerListener, boolean z, boolean z2) {
        if (context == null || TextUtils.isEmpty(str)) {
            Log.w(TAG, "invalid params. pkgName=" + str);
            return;
        }
        try {
            Object packageInstallObserver = CompatibilityUtils.getPackageInstallObserver(packageInstallerListener);
            Class<?> cls = Class.forName("miui.content.pm.PreloadedAppPolicy");
            boolean b2 = c.a(cls, "installPreloadedDataApp", CompatibilityUtils.getInstallMethodDescription()).b(cls, null, context, str, packageInstallObserver, Integer.valueOf(z ? 1 : z2 ? 2 : 0));
            Log.d(TAG, "installPackage: result=" + b2);
        } catch (Exception e2) {
            Log.e(TAG, e2.getMessage(), e2);
            if (packageInstallerListener != null) {
                packageInstallerListener.onPackageInstalled(str, false);
            }
        }
    }

    public static boolean isAEStable(int i) {
        return i == 2 || i == 3 || i == 4;
    }

    public static boolean isAWBStable(int i) {
        return i == 2 || i == 3;
    }

    private static boolean isAccessibilityEnable() {
        return sIsAccessibilityEnable;
    }

    public static boolean isAccessible() {
        return Build.VERSION.SDK_INT >= 14 && isAccessibilityEnable();
    }

    public static boolean isActivityInvert(Activity activity) {
        return getDisplayRotation(activity) == 180;
    }

    public static boolean isAntibanding60() {
        return ANTIBANDING_60_COUNTRY.contains(mCountryIso);
    }

    public static final boolean isAppLocked(Context context, String str) {
        return GeneralUtils.isAppLocked(context, str);
    }

    public static boolean isCameraUnderScreen() {
        return sIsCameraUnderScreen;
    }

    public static boolean isContains(RectF rectF, RectF rectF2) {
        if (rectF == null || rectF2 == null) {
            return false;
        }
        float f2 = rectF.left;
        float f3 = rectF.right;
        if (f2 >= f3) {
            return false;
        }
        float f4 = rectF.top;
        float f5 = rectF.bottom;
        return f4 < f5 && f2 <= rectF2.left && f4 <= rectF2.top && f3 >= rectF2.right && f5 >= rectF2.bottom;
    }

    public static boolean isContentViewExtendToTopEdges() {
        return isNotchDevice || isCameraUnderScreen();
    }

    public static boolean isDebugOsBuild() {
        return "userdebug".equals(Build.TYPE) || "eng".equals(Build.TYPE) || sIsDumpLog;
    }

    private static boolean isDevices(String str) {
        Object staticObjectField;
        try {
            Class<?> cls = Class.forName("d.d.a");
            if (cls == null || (staticObjectField = getStaticObjectField(cls, str)) == null) {
                return false;
            }
            return Boolean.parseBoolean(staticObjectField.toString());
        } catch (Exception e2) {
            Log.e(TAG, "getClass error", e2);
        }
        return false;
    }

    public static boolean isDumpImageEnabled() {
        if (sIsDumpImageEnabled == null) {
            sIsDumpImageEnabled = Boolean.valueOf(new File(Storage.generatePrimaryFilepath("algoup_dump_images")).exists());
        }
        return sIsDumpImageEnabled.booleanValue();
    }

    public static boolean isEnglishOrNum(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0064 A[SYNTHETIC, Splitter:B:29:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0073 A[SYNTHETIC, Splitter:B:35:0x0073] */
    private static boolean isEqual(byte[] bArr, File file) {
        if (bArr == null || bArr.length == 0 || !file.exists()) {
            return false;
        }
        FileInputStream fileInputStream = null;
        byte[] bArr2 = new byte[512];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream2 = new FileInputStream(file);
            while (true) {
                try {
                    int read = fileInputStream2.read(bArr2, 0, 512);
                    if (read == -1) {
                        break;
                    }
                    instance.update(bArr2, 0, read);
                } catch (IOException | NoSuchAlgorithmException e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    try {
                        Log.e(TAG, e.getMessage(), e);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                                Log.e(TAG, e3.getMessage(), e3);
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e4) {
                            Log.e(TAG, e4.getMessage(), e4);
                        }
                    }
                    throw th;
                }
            }
            String str = new String(instance.digest());
            instance.reset();
            boolean equals = str.equals(new String(instance.digest(bArr)));
            try {
                fileInputStream2.close();
            } catch (IOException e5) {
                Log.e(TAG, e5.getMessage(), e5);
            }
            return equals;
        } catch (IOException | NoSuchAlgorithmException e6) {
            e = e6;
            Log.e(TAG, e.getMessage(), e);
            if (fileInputStream != null) {
            }
            return false;
        }
    }

    public static boolean isEqualsZero(double d2) {
        return Math.abs(d2) < 1.0E-8d;
    }

    public static boolean isExternalStorageMounted() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean isFingerPrintKeyEvent(KeyEvent keyEvent) {
        return keyEvent != null && 27 == keyEvent.getKeyCode() && keyEvent.getDevice() != null && b.Tk().contains(keyEvent.getDevice().getName());
    }

    public static boolean isForceNameSuffix() {
        if (sIsForceNameSuffix == null) {
            sIsForceNameSuffix = Boolean.valueOf(new File(Storage.generatePrimaryFilepath(FORCE_NAME_SUFFIX_FILE)).exists());
        }
        return sIsForceNameSuffix.booleanValue();
    }

    public static boolean isFullScreenNavBarHidden(Context context) {
        return a.C0002a.a(context.getContentResolver(), a.C0002a.yh);
    }

    public static boolean isGlobalVersion() {
        return SystemProperties.get("ro.boot.hwc", "").contains("GLOBAL") || DataRepository.dataItemFeature().c_0x44();
    }

    private static boolean isGyroscopeStable(float[] fArr) {
        return fArr != null && fArr.length == 3 && Math.abs(fArr[0]) < 0.7f && Math.abs(fArr[1]) < 5.0f && Math.abs(fArr[2]) < 0.7f;
    }

    public static boolean isGyroscopeStable(float[] fArr, float[] fArr2) {
        if (fArr == null) {
            return true;
        }
        boolean isGyroscopeStable = isGyroscopeStable(fArr);
        if (isGyroscopeStable) {
            if (fArr2 == null) {
                return true;
            }
            if (isGyroscopeStable(fArr2)) {
                return isGyroscopeStable;
            }
        }
        return false;
    }

    public static boolean isHasBackLightSensor() {
        return isHasBackLightSensor;
    }

    public static boolean isInternationalBuild() {
        return SystemProperties.get("ro.boot.hwc", "").endsWith("GLOBAL");
    }

    public static boolean isLabOptionsVisible() {
        if (sIsLabOptionsVisible == null) {
            sIsLabOptionsVisible = Boolean.valueOf(new File(Storage.generatePrimaryFilepath(LAB_OPTIONS_VISIBLE_FILE)).exists());
        }
        return sIsLabOptionsVisible.booleanValue();
    }

    public static boolean isLayoutRTL(Context context) {
        return context != null && context.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    public static boolean isLivePhotoStable(LivePhotoResult livePhotoResult, int i) {
        if (livePhotoResult == null) {
            return true;
        }
        return isAEStable(livePhotoResult.getAEState()) && isAWBStable(livePhotoResult.getAWBState()) && livePhotoResult.isGyroScopeStable() && livePhotoResult.getFilterId() == i;
    }

    public static boolean isLocaleChinese() {
        return Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
    }

    public static boolean isMemoryRich(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem > 419430400;
    }

    public static boolean isNonUI() {
        return SystemProperties.getBoolean(NONUI_MODE_PROPERTY, false);
    }

    public static boolean isNonUIEnabled() {
        return !SystemProperties.get(NONUI_MODE_PROPERTY).equals("");
    }

    public static boolean isNotchScreenHidden(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Settings.Global.getInt(context.getContentResolver(), "force_black_v2", 0) == 1;
        }
        return false;
    }

    public static boolean isPackageAvailable(Context context, String str) {
        if (context == null || str == null || str.isEmpty()) {
            Log.w(TAG, "invalid params. packageName=" + str);
            return false;
        }
        try {
            int applicationEnabledSetting = context.getPackageManager().getApplicationEnabledSetting(str);
            return applicationEnabledSetting == 0 || applicationEnabledSetting == 1;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, e2.getMessage());
            return false;
        } catch (NullPointerException e3) {
            Log.e(TAG, e3.getMessage());
            return false;
        }
    }

    public static boolean isPathExist(String str) {
        return !TextUtils.isEmpty(str) && new File(str).exists();
    }

    public static boolean isProduceFocusInfoSuccess(byte[] bArr) {
        return bArr != null && 25 < bArr.length && bArr[bArr.length - 25] == 0;
    }

    public static boolean isQuotaExceeded(Exception exc) {
        if (exc == null || !(exc instanceof FileNotFoundException)) {
            return false;
        }
        String message = exc.getMessage();
        Log.e(TAG, "isQuotaExceeded: msg=" + message);
        if (message != null) {
            return message.toLowerCase().contains("quota exceeded");
        }
        return false;
    }

    public static boolean isSaveToHidenFolder(int i) {
        return CameraSettings.isDocumentModeOn(i) || i == 182;
    }

    public static boolean isScreenSlideOff(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "sc_status", -1) == 1;
    }

    public static boolean isSetContentDesc() {
        return "1".equals(SystemProperties.get("camera.content.description.debug"));
    }

    public static boolean isShowAfRegionView() {
        return "1".equals(SystemProperties.get("camera.preview.debug.afRegion_view"));
    }

    public static boolean isShowDebugInfo() {
        return "1".equals(SystemProperties.get("persist.camera.enable.log")) || "1".equals(SystemProperties.get("persist.camera.debug.show_af")) || "1".equals(SystemProperties.get("persist.camera.debug.show_awb")) || "1".equals(SystemProperties.get("persist.camera.debug.show_aec")) || "1".equals(SystemProperties.get("persist.camera.debug.autoscene")) || "1".equals(SystemProperties.get("persist.camera.debug.hht"));
    }

    public static boolean isShowDebugInfoView() {
        return "1".equals(SystemProperties.get("camera.preview.debug.debugInfo_view"));
    }

    public static boolean isShowPreviewDebugInfo() {
        return "1".equals(SystemProperties.get("camera.preview.enable.log"));
    }

    public static boolean isStringValueContained(Object obj, int i) {
        return isStringValueContained(obj, CameraAppImpl.getAndroidContext().getResources().getStringArray(i));
    }

    public static boolean isStringValueContained(Object obj, List<? extends CharSequence> list) {
        if (!(list == null || obj == null)) {
            for (CharSequence charSequence : list) {
                if (charSequence.equals(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isStringValueContained(Object obj, CharSequence[] charSequenceArr) {
        if (!(charSequenceArr == null || obj == null)) {
            for (CharSequence charSequence : charSequenceArr) {
                if (charSequence.equals(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSupported(int i, int[] iArr) {
        return getArrayIndex(iArr, i) != -1;
    }

    public static <T> boolean isSupported(T t, T[] tArr) {
        return getArrayIndex(tArr, t) != -1;
    }

    public static boolean isSupported(String str, List<String> list) {
        return list != null && list.indexOf(str) >= 0;
    }

    public static boolean isTimeout(long j, long j2, long j3) {
        return j < j2 || j - j2 > j3;
    }

    public static boolean isUriValid(Uri uri, ContentResolver contentResolver) {
        if (uri == null) {
            return false;
        }
        try {
            ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            if (openFileDescriptor == null) {
                Log.e(TAG, "Fail to open URI. URI=" + uri);
                return false;
            }
            openFileDescriptor.close();
            return true;
        } catch (IOException e2) {
            Log.e(TAG, "IOException occurs when opening URI: " + e2.getMessage(), e2);
            return false;
        } catch (IllegalArgumentException e3) {
            Log.e(TAG, "IllegalArgumentException occurs when Volume not found: " + e3.getMessage(), e3);
            return false;
        }
    }

    public static boolean isUserUnlocked(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        return userManager != null && userManager.isUserUnlocked();
    }

    public static boolean isValidValue(String str) {
        return !TextUtils.isEmpty(str) && str.matches("^[0-9]+$");
    }

    public static boolean isViewIntersectWindow(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[0] < sWindowWidth && iArr[0] + view.getWidth() >= 0 && iArr[1] < sWindowHeight && iArr[1] + view.getHeight() >= 0;
    }

    public static boolean isZoomAnimationEnabled() {
        return SystemProperties.getBoolean(ZOOM_ANIMATION_PROPERTY, !DataRepository.dataItemFeature().s_f_z_i());
    }

    public static String join(String str, List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                stringBuffer.append(list.get(i));
            } else {
                stringBuffer.append(list.get(i));
                stringBuffer.append(str);
            }
        }
        return stringBuffer.toString();
    }

    public static boolean keepLiveShotMicroVideoInCache() {
        return android.util.Log.isLoggable("liveshotsmv", 3);
    }

    public static Bitmap load960fpsCameraWatermark(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = true;
        return loadAppCameraWatermark(context, options, Build.DEVICE + "_960fps");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0063, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0064, code lost:
        if (r3 != null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0066, code lost:
        $closeResource(r4, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0069, code lost:
        throw r5;
     */
    protected static Bitmap loadAppCameraWatermark(Context context, BitmapFactory.Options options, String str) {
        String str2;
        if (str == null) {
            return null;
        }
        if (str.equalsIgnoreCase("common")) {
            str2 = "common.webp";
        } else if (DataRepository.dataItemFeature().s_c_w_m() || !DataRepository.dataItemFeature().qf()) {
            str2 = str + b.Uk() + ".webp";
        } else {
            str2 = "dualcamera.webp";
        }
        try {
            InputStream open = context.getAssets().open("watermarks/" + str2);
            Bitmap decodeStream = BitmapFactory.decodeStream(open, null, options);
            if (open != null) {
                $closeResource(null, open);
            }
            return decodeStream;
        } catch (Exception e2) {
            Log.d(TAG, "Failed to load app camera watermark ", e2);
            return null;
        }
    }

    public static Bitmap loadFrontCameraWatermark(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = true;
        return loadAppCameraWatermark(context, options, Build.DEVICE + "_front");
    }

    private static double log2(double d2) {
        return Math.log(d2) / LOG_2;
    }

    public static Bitmap makeBitmap(byte[] bArr, int i) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (!options.mCancel && options.outWidth != -1) {
                if (options.outHeight != -1) {
                    options.inSampleSize = computeSampleSize(options, -1, i);
                    options.inJustDecodeBounds = false;
                    options.inDither = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                }
            }
            return null;
        } catch (OutOfMemoryError e2) {
            Log.e(TAG, "Got oom exception ", e2);
            return null;
        }
    }

    public static boolean makeSureNoMedia(String str) {
        File file = new File(str, Storage.AVOID_SCAN_FILE_NAME);
        if (file.exists()) {
            return true;
        }
        try {
            file.createNewFile();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("UTF8"));
            byte[] digest = instance.digest();
            String str2 = "";
            for (int i = 0; i < digest.length; i++) {
                str2 = str2 + Integer.toHexString((digest[i] & 255) | -256).substring(6);
            }
            return str2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static final String millisecondToTimeString(long j, boolean z) {
        return millisecondToTimeString(j, z, false);
    }

    public static final String millisecondToTimeString(long j, boolean z, boolean z2) {
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j3 / 60;
        long j5 = j3 - (j4 * 60);
        long j6 = j3 * 60;
        long j7 = j2 - j6;
        StringBuilder sb = new StringBuilder();
        if (j4 > 0) {
            if (j4 < 10) {
                sb.append('0');
            }
            sb.append(j4);
            sb.append(':');
        }
        if (j5 < 10) {
            sb.append('0');
        }
        sb.append(j5);
        sb.append(':');
        if (!z && z2) {
            j7 = (long) Math.ceil((((double) j) / 1000.0d) - ((double) j6));
        }
        if (j7 < 10) {
            sb.append('0');
        }
        sb.append(j7);
        if (z) {
            sb.append('.');
            long j8 = (j - (j2 * 1000)) / 10;
            if (j8 < 10) {
                sb.append('0');
            }
            sb.append(j8);
        }
        return sb.toString();
    }

    public static boolean mkdirs(File file, int i, int i2, int i3) {
        if (file.exists()) {
            return false;
        }
        String parent = file.getParent();
        if (parent != null) {
            mkdirs(new File(parent), i, i2, i3);
        }
        return file.mkdir();
    }

    private static void modify(Object obj, String str, int i) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.setInt(obj, i);
    }

    public static int nextPowerOf2(int i) {
        int i2 = i - 1;
        int i3 = i2 | (i2 >>> 16);
        int i4 = i3 | (i3 >>> 8);
        int i5 = i4 | (i4 >>> 4);
        int i6 = i5 | (i5 >>> 2);
        return (i6 | (i6 >>> 1)) + 1;
    }

    private static float normalizeDegree(float f2) {
        if (f2 < 0.0f) {
            f2 += 360.0f;
        } else if (f2 > 360.0f) {
            f2 %= 360.0f;
        }
        return f2 <= 45.0f ? f2 : f2 <= 90.0f ? 90.0f - f2 : f2 <= 135.0f ? f2 - 90.0f : f2 <= 180.0f ? 180.0f - f2 : f2 <= 225.0f ? f2 - 180.0f : f2 <= 270.0f ? 270.0f - f2 : f2 <= 315.0f ? f2 - 270.0f : 360.0f - f2;
    }

    public static int parseInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            Log.e(TAG, e2.getMessage(), e2);
            if (!isDebugOsBuild()) {
                return i;
            }
            throw e2;
        }
    }

    public static Uri photoUri(String str) {
        return new Uri.Builder().scheme("photo").path(str).build();
    }

    public static boolean pointInView(float f2, float f3, View view) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return f2 >= ((float) iArr[0]) && f2 < ((float) (iArr[0] + view.getWidth())) && f3 >= ((float) iArr[1]) && f3 < ((float) (iArr[1] + view.getHeight()));
    }

    public static void prepareMatrix(Matrix matrix, boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        matrix.setScale(z ? -1.0f : 1.0f, 1.0f);
        matrix.postRotate((float) i);
        if (i == 90 || i == 270) {
            matrix.postScale(((float) i2) / ((float) i7), ((float) i3) / ((float) i6));
        } else {
            matrix.postScale(((float) i2) / ((float) i6), ((float) i3) / ((float) i7));
        }
        matrix.postTranslate((float) i4, (float) i5);
    }

    public static void printLog(String str, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objArr.length; i += 2) {
            sb.append(objArr[i].toString());
            sb.append(" = ");
            sb.append(objArr[i + 1].toString());
            sb.append(" ");
        }
        Log.d(str, sb.toString());
    }

    public static void rectFToRect(RectF rectF, Rect rect) {
        rect.left = Math.round(rectF.left);
        rect.top = Math.round(rectF.top);
        rect.right = Math.round(rectF.right);
        rect.bottom = Math.round(rectF.bottom);
    }

    public static void removeCustomWatermark() {
        if (DataRepository.dataItemFeature().qf()) {
            WatermarkMiSysUtils.eraseFile(getDefaultWatermarkFileName());
            WatermarkMiSysUtils.eraseFile(WATERMARK_FRONT_FILE_NAME);
            WatermarkMiSysUtils.eraseFile(WATERMARK_ULTRA_PIXEL_FILE_NAME);
        }
        File filesDir = CameraAppImpl.getAndroidContext().getFilesDir();
        File file = new File(filesDir, getDefaultWatermarkFileName());
        File file2 = new File(filesDir, WATERMARK_FRONT_FILE_NAME);
        File file3 = new File(filesDir, WATERMARK_ULTRA_PIXEL_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        if (file2.exists()) {
            file2.delete();
        }
        if (file3.exists()) {
            file3.delete();
        }
    }

    public static int replaceStartEffectRender(Activity activity) {
        String stringExtra;
        int identifier;
        if (!b.gm() || (stringExtra = activity.getIntent().getStringExtra(EXTRAS_START_WITH_EFFECT_RENDER)) == null || (identifier = activity.getResources().getIdentifier(stringExtra, "integer", activity.getPackageName())) == 0) {
            return FilterInfo.FILTER_ID_NONE;
        }
        int integer = activity.getResources().getInteger(identifier);
        CameraSettings.setShaderEffect(integer);
        return integer;
    }

    public static void reverseAnimatorSet(AnimatorSet animatorSet) {
        Iterator<Animator> it = animatorSet.getChildAnimations().iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (next instanceof ValueAnimator) {
                ((ValueAnimator) next).reverse();
            } else if (next instanceof AnimatorSet) {
                reverseAnimatorSet((AnimatorSet) next);
            }
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int i) {
        return rotateAndMirror(bitmap, i, false);
    }

    public static Bitmap rotateAndMirror(Bitmap bitmap, int i, boolean z) {
        if ((i == 0 && !z) || bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        if (z) {
            matrix.postScale(-1.0f, 1.0f);
            i = (i + 360) % 360;
            if (i == 0 || i == 180) {
                matrix.postTranslate((float) bitmap.getWidth(), 0.0f);
            } else if (i == 90 || i == 270) {
                matrix.postTranslate((float) bitmap.getHeight(), 0.0f);
            } else {
                throw new IllegalArgumentException("Invalid degrees=" + i);
            }
        }
        if (i != 0) {
            matrix.postRotate((float) i, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap == createBitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            return bitmap;
        }
    }

    public static int roundOrientation(int i, int i2) {
        boolean z = true;
        if (i2 != -1) {
            int abs = Math.abs(i - i2);
            if (Math.min(abs, 360 - abs) < 50) {
                z = false;
            }
        }
        if (!z) {
            return i2;
        }
        int i3 = (((i + 45) / 90) * 90) % 360;
        Log.d(TAG, "onOrientationChanged: orientation = " + i3);
        return i3;
    }

    public static int safeDelete(Uri uri, String str, String[] strArr) {
        int i = -1;
        try {
            i = CameraAppImpl.getAndroidContext().getContentResolver().delete(uri, str, strArr);
            Log.v(TAG, "safeDelete url=" + uri + " where=" + str + " selectionArgs=" + strArr + " result=" + i);
            return i;
        } catch (Exception e2) {
            e2.printStackTrace();
            return i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0041 A[SYNTHETIC, Splitter:B:22:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0052 A[SYNTHETIC, Splitter:B:28:0x0052] */
    public static boolean saveBitmap(Buffer buffer, int i, int i2, Bitmap.Config config, String str) {
        if (buffer != null) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, config);
            createBitmap.copyPixelsFromBuffer(buffer);
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                    try {
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    createBitmap.recycle();
                    return true;
                } catch (FileNotFoundException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    try {
                        Log.e(TAG, "saveBitmap failed!", e);
                        if (fileOutputStream != null) {
                        }
                        createBitmap.recycle();
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        createBitmap.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                    }
                    createBitmap.recycle();
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                Log.e(TAG, "saveBitmap failed!", e);
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                createBitmap.recycle();
                return false;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
        $closeResource(r4, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
        throw r2;
     */
    public static void saveBlobToFile(byte[] bArr, String str) {
        Objects.requireNonNull(str, "The target filepath must not be null");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            Log.d(TAG, "successfully write blob into file: " + str);
            $closeResource(null, fileOutputStream);
        } catch (IOException unused) {
            Log.d(TAG, "failed to write blob into file: " + str);
        }
    }

    public static boolean saveCameraCalibrationToFile(byte[] bArr, String str) {
        Context androidContext = CameraAppImpl.getAndroidContext();
        if (!(bArr == null || androidContext == null)) {
            if (isEqual(bArr, androidContext.getFileStreamPath(str))) {
                return true;
            }
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = androidContext.openFileOutput(str, 0);
                fileOutputStream.write(bArr);
                if (fileOutputStream == null) {
                    return true;
                }
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return true;
                }
            } catch (FileNotFoundException e3) {
                Log.e(TAG, "saveCameraCalibrationToFile: FileNotFoundException", e3);
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e4) {
                Log.e(TAG, "saveCameraCalibrationToFile: IOException", e4);
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
        }
        return false;
    }

    protected static void saveCustomWatermark2File(Bitmap bitmap, boolean z, boolean z2) {
        boolean z3;
        File filesDir;
        File file;
        FileOutputStream fileOutputStream;
        Log.d(TAG, "saveCustomWatermark2File: start... watermarkBitmap = " + bitmap);
        long currentTimeMillis = System.currentTimeMillis();
        String defaultWatermarkFileName = z2 ? WATERMARK_FRONT_FILE_NAME : z ? WATERMARK_ULTRA_PIXEL_FILE_NAME : getDefaultWatermarkFileName();
        if (bitmap != 0 && !bitmap.isRecycled()) {
            boolean z4 = true;
            if (DataRepository.dataItemFeature().qf()) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                z4 = WatermarkMiSysUtils.writeFileToPersist(byteArrayOutputStream.toByteArray(), defaultWatermarkFileName);
            }
            if (z4) {
                FileOutputStream fileOutputStream2 = null;
                try {
                    filesDir = CameraAppImpl.getAndroidContext().getFilesDir();
                    file = new File(filesDir, defaultWatermarkFileName + TEMP_SUFFIX);
                    fileOutputStream = new FileOutputStream(file);
                } catch (IOException e2) {
                    e = e2;
                    try {
                        Log.e(TAG, "saveCustomWatermark2File Failed to write image", e);
                        closeSilently(fileOutputStream2);
                        z3 = false;
                        Log.d(TAG, "saveCustomWatermark2File: watermarkBitmap = " + bitmap + ", save result = " + z3 + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
                    } catch (Throwable th) {
                        th = th;
                        closeSilently(fileOutputStream2);
                        throw th;
                    }
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                    fileOutputStream.flush();
                    z3 = file.renameTo(new File(filesDir, defaultWatermarkFileName));
                    closeSilently(fileOutputStream);
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream2 = fileOutputStream;
                    Log.e(TAG, "saveCustomWatermark2File Failed to write image", e);
                    closeSilently(fileOutputStream2);
                    z3 = false;
                    Log.d(TAG, "saveCustomWatermark2File: watermarkBitmap = " + bitmap + ", save result = " + z3 + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream2 = fileOutputStream;
                    closeSilently(fileOutputStream2);
                    throw th;
                }
                Log.d(TAG, "saveCustomWatermark2File: watermarkBitmap = " + bitmap + ", save result = " + z3 + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
            }
        }
        z3 = false;
        Log.d(TAG, "saveCustomWatermark2File: watermarkBitmap = " + bitmap + ", save result = " + z3 + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    public static void saveImageToJpeg(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        ByteBuffer buffer2 = planes[2].getBuffer();
        int[] iArr = {planes[0].getRowStride(), planes[2].getRowStride()};
        int limit = buffer.limit();
        int limit2 = buffer2.limit();
        byte[] bArr = new byte[(limit + limit2)];
        buffer.rewind();
        buffer2.rewind();
        buffer.get(bArr, 0, limit);
        buffer2.get(bArr, limit, limit2);
        ImageHelper.saveYuvToJpg(bArr, image.getWidth(), image.getHeight(), iArr, System.currentTimeMillis());
        Log.d(TAG, "saveImageToJpeg: " + buffer.remaining() + "|" + buffer2.remaining());
    }

    public static void saveLastFrameGaussian2File(Bitmap bitmap) {
        boolean z;
        FileOutputStream fileOutputStream;
        IOException e2;
        File filesDir;
        File file;
        Log.d(TAG, "saveLastFrameGaussian2File: start... blurBitmap = " + bitmap);
        long currentTimeMillis = System.currentTimeMillis();
        if (bitmap != 0 && !bitmap.isRecycled()) {
            try {
                filesDir = CameraAppImpl.getAndroidContext().getFilesDir();
                file = new File(filesDir, "blur.jpg.tmp");
                fileOutputStream = new FileOutputStream(file);
            } catch (IOException e3) {
                fileOutputStream = null;
                e2 = e3;
                try {
                    Log.e(TAG, "saveLastFrameGaussian2File Failed to write image", e2);
                    closeSilently(fileOutputStream);
                    z = false;
                    Log.d(TAG, "saveLastFrameGaussian2File: blurBitmap = " + bitmap + ", save result = " + z + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
                } catch (Throwable th) {
                    th = th;
                    closeSilently(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                closeSilently(fileOutputStream);
                throw th;
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                fileOutputStream.flush();
                z = file.renameTo(new File(filesDir, LAST_FRAME_GAUSSIAN_FILE_NAME));
                closeSilently(fileOutputStream);
            } catch (IOException e4) {
                e2 = e4;
                Log.e(TAG, "saveLastFrameGaussian2File Failed to write image", e2);
                closeSilently(fileOutputStream);
                z = false;
                Log.d(TAG, "saveLastFrameGaussian2File: blurBitmap = " + bitmap + ", save result = " + z + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
            }
            Log.d(TAG, "saveLastFrameGaussian2File: blurBitmap = " + bitmap + ", save result = " + z + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        }
        z = false;
        Log.d(TAG, "saveLastFrameGaussian2File: blurBitmap = " + bitmap + ", save result = " + z + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        $closeResource(r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        throw r3;
     */
    public static boolean saveToFile(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat) throws IOException {
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        bitmap.compress(compressFormat, 100, fileOutputStream);
        $closeResource(null, fileOutputStream);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005c A[SYNTHETIC, Splitter:B:21:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    public static void saveYuv(byte[] bArr, long j) {
        FileOutputStream fileOutputStream = null;
        try {
            String str = "sdcard/DCIM/Camera/dump_" + j + ".yuv";
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                fileOutputStream2.write(bArr);
                Log.v(TAG, "saveYuv: " + str);
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (Exception e2) {
                    Log.e(TAG, "Failed to flush/close stream", e2);
                }
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                try {
                    Log.e(TAG, "Failed to write image", e);
                    if (fileOutputStream == null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e4) {
                            Log.e(TAG, "Failed to flush/close stream", e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            Log.e(TAG, "Failed to write image", e);
            if (fileOutputStream == null) {
            }
        }
    }

    public static void saveYuvToJpg(byte[] bArr, int i, int i2, int[] iArr, long j) {
        if (bArr == null) {
            Log.w(TAG, "saveYuvToJpg: null data");
            return;
        }
        YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, iArr);
        String str = "sdcard/DCIM/Camera/dump_" + j + Storage.JPEG_SUFFIX;
        Log.v(TAG, "saveYuvToJpg: " + str);
        try {
            yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 100, new FileOutputStream(str));
        } catch (FileNotFoundException e2) {
            Log.e(TAG, e2.getMessage(), e2);
        }
    }

    public static void scaleCamera2Matrix(Matrix matrix, Rect rect, float f2) {
        matrix.postScale(f2, f2);
        matrix.preTranslate(((float) (-rect.width())) / 2.0f, ((float) (-rect.height())) / 2.0f);
    }

    public static void setAccessibilityFocusable(View view, boolean z) {
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        if (z) {
            ViewCompat.setImportantForAccessibility(view, 1);
        } else {
            ViewCompat.setImportantForAccessibility(view, 2);
        }
    }

    public static void setBrightnessRampRate(int i) {
    }

    public static void setPixels(byte[] bArr, int i, int i2, byte[] bArr2, int[] iArr) {
        if (bArr != null && bArr2 != null) {
            int i3 = ((iArr[1] * i) + iArr[0]) * i2;
            int i4 = 0;
            for (int i5 = 0; i5 < iArr[3]; i5++) {
                System.arraycopy(bArr2, i4, bArr, i3, iArr[2] * i2);
                i4 += iArr[2] * i2;
                i3 += i * i2;
            }
        }
    }

    public static void setScreenEffect(boolean z) {
        if (b.Ll()) {
            try {
                d.b.a.a.getInstance().p(14, z ? 1 : 0);
            } catch (Exception e2) {
                Log.d(TAG, "Meet Exception when calling DisplayFeatureManager#setScreenEffect()", e2);
            }
        }
    }

    private static void setTagValue(ExifInterface exifInterface, int i, Object obj) {
        if (!exifInterface.setTagValue(i, obj)) {
            exifInterface.setTag(exifInterface.buildTag(i, obj));
        }
    }

    public static void showErrorAndFinish(final Activity activity, int i, boolean z) {
        if (!activity.isFinishing()) {
            AlertDialog show = new AlertDialog.Builder(activity).setCancelable(false).setIconAttribute(16843605).setTitle(R.string.camera_error_title).setMessage(i).setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                /* class com.android.camera.Util.AnonymousClass1 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Camera2DataContainer.getInstance().reset();
                    activity.finish();
                }
            }).show();
            boolean z2 = i == R.string.cannot_connect_camera_twice || i == R.string.cannot_connect_camera_once;
            if (z2) {
                CameraStatUtils.trackCameraErrorDialogShow();
            }
            if (sIsKillCameraService && Build.VERSION.SDK_INT >= 26 && b.wl() && z2) {
                if (SystemClock.elapsedRealtime() - CameraSettings.getBroadcastKillServiceTime() > 60000) {
                    broadcastKillService(activity, z);
                }
                final Button button = show.getButton(-3);
                button.setTextAppearance(GeneralUtils.miuiWidgetButtonDialog());
                button.setEnabled(false);
                final CountDownTimer start = new CountDownTimer(5000, 1000) {
                    /* class com.android.camera.Util.AnonymousClass2 */

                    public void onFinish() {
                        if (!((ActivityBase) activity).isActivityPaused()) {
                            button.setEnabled(true);
                            button.setText(activity.getResources().getString(R.string.dialog_ok));
                        }
                    }

                    public void onTick(long j) {
                        if (!((ActivityBase) activity).isActivityPaused()) {
                            button.setText(activity.getResources().getString(R.string.dialog_ok_time, Long.valueOf(j / 1000)));
                        }
                    }
                }.start();
                show.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class com.android.camera.Util.AnonymousClass3 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        CountDownTimer countDownTimer = start;
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                });
            }
            ((ActivityBase) activity).setErrorDialog(show);
        }
    }

    private static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (xmlPullParser.getEventType() == 2) {
            int i = 1;
            while (i != 0) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    i++;
                } else if (next == 3) {
                    i--;
                }
            }
            return;
        }
        throw new IllegalStateException();
    }

    public static boolean startActivityForResultCatchException(Activity activity, Intent intent, int i) {
        try {
            activity.startActivityForResult(intent, i);
            return true;
        } catch (ActivityNotFoundException e2) {
            Log.e(TAG, "failed to start activity", e2);
            return false;
        }
    }

    public static void startScreenSlideAlphaInAnimation(View view) {
        ViewCompat.setAlpha(view, 0.0f);
        ViewCompat.animate(view).alpha(1.0f).setDuration(350).setStartDelay(400).setInterpolator(new E()).start();
    }

    public static int stringSparseArraysIndexOf(SparseArray<String> sparseArray, String str) {
        if (str == null) {
            return -1;
        }
        for (int i = 0; i < sparseArray.size(); i++) {
            if (str.equals(sparseArray.valueAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String toHumanString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(j));
    }

    public static void updateAccessibility(Context context) {
        sIsAccessibilityEnable = ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    public static void updateDeviceConfig(Context context) {
        Country detectCountry;
        sRegion = SystemProperties.get("ro.miui.region");
        String str = ((TelephonyManager) context.getSystemService("phone")).getSimState() != 5 ? sRegion : null;
        if (TextUtils.isEmpty(str) && (detectCountry = ((CountryDetector) context.getSystemService("country_detector")).detectCountry()) != null) {
            str = detectCountry.getCountryIso();
        }
        mCountryIso = str;
        Log.d(TAG, "antiBanding mCountryIso=" + mCountryIso + " sRegion=" + sRegion);
        sIsDumpLog = SystemProperties.getBoolean("camera_dump_parameters", DEBUG);
        sIsDumpOrigJpg = SystemProperties.getBoolean("camera_dump_orig_jpg", false);
        sIsKillCameraService = SystemProperties.getBoolean("kill_camera_service_enable", true);
        sSuperNightDefaultModeEnable = SystemProperties.getBoolean("super_night_default_mode_enable", false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0017, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        if (r0 != null) goto L_0x0017;
     */
    public static void verifyAssetZip(Context context, String str, String str2, int i) throws IOException {
        InputStream open = context.getAssets().open(str);
        verifyZip(open, str2, i);
        if (open != null) {
            $closeResource(null, open);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002b, code lost:
        $closeResource(r1, r2);
     */
    public static void verifyFileZip(Context context, String str, String str2, int i) throws IOException {
        Log.d(TAG, "verifyAssetZip " + str);
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        verifyZip(fileInputStream, str2, i);
        $closeResource(null, fileInputStream);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        $closeResource(r1, r0);
     */
    public static void verifySdcardZip(Context context, String str, String str2, int i) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        verifyZip(fileInputStream, str2, i);
        $closeResource(null, fileInputStream);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c0, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c1, code lost:
        $closeResource(r11, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c4, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00cb, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00cc, code lost:
        $closeResource(r10, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00cf, code lost:
        throw r11;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008e A[Catch:{ all -> 0x00cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0013 A[SYNTHETIC] */
    public static void verifyZip(InputStream inputStream, String str, int i) throws IOException {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    File file2 = new File(str + "/" + nextEntry.getName());
                    boolean z = true;
                    if (!file2.exists()) {
                        if (nextEntry.isDirectory()) {
                            file2.mkdirs();
                        } else {
                            File file3 = new File(file2.getParent());
                            if (!file3.exists()) {
                                file3.mkdirs();
                            }
                            file2.createNewFile();
                            if (z) {
                                Log.w(TAG, "corrupted " + nextEntry.getName());
                                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                byte[] bArr = new byte[i];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read <= 0) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                $closeResource(null, fileOutputStream);
                            }
                        }
                    } else if (!nextEntry.isDirectory()) {
                        if (!file2.isFile()) {
                            file2.delete();
                            file2.createNewFile();
                        }
                        if (Verifier.crc32(file2, i) != nextEntry.getCrc()) {
                            if (z) {
                            }
                        }
                    } else if (!file2.isDirectory()) {
                        file2.delete();
                        file2.mkdirs();
                    }
                    z = false;
                    if (z) {
                    }
                } else {
                    $closeResource(null, zipInputStream);
                    return;
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d3, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d4, code lost:
        $closeResource(r11, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d7, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00da, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00db, code lost:
        if (r1 != null) goto L_0x00dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00dd, code lost:
        $closeResource(r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e0, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e7, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e8, code lost:
        $closeResource(r11, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00eb, code lost:
        throw r12;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0098 A[Catch:{ all -> 0x00e7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0017 A[SYNTHETIC] */
    public static void verifyZip(String str, String str2, int i) throws IOException {
        ZipFile zipFile = new ZipFile(str);
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            File file2 = new File(str2 + "/" + zipEntry.getName());
            boolean z = true;
            if (!file2.exists()) {
                if (zipEntry.isDirectory()) {
                    file2.mkdirs();
                } else {
                    File file3 = new File(file2.getParent());
                    if (!file3.exists()) {
                        file3.mkdirs();
                    }
                    file2.createNewFile();
                    if (z) {
                        Log.w(TAG, "corrupted " + zipEntry.getName());
                        InputStream inputStream = zipFile.getInputStream(zipEntry);
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        byte[] bArr = new byte[i];
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        $closeResource(null, fileOutputStream);
                        if (inputStream != null) {
                            $closeResource(null, inputStream);
                        }
                    }
                }
            } else if (!zipEntry.isDirectory()) {
                if (!file2.isFile()) {
                    file2.delete();
                    file2.createNewFile();
                }
                if (Verifier.crc32(file2, i) != zipEntry.getCrc()) {
                    if (z) {
                    }
                }
            } else if (!file2.isDirectory()) {
                file2.delete();
                file2.mkdirs();
            }
            z = false;
            if (z) {
            }
        }
        $closeResource(null, zipFile);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:4|5|6|7|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0037, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0038, code lost:
        com.android.camera.log.Log.e(com.android.camera.Util.TAG, "review image fail. uri=" + r3, r4);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x002c */
    public static void viewUri(Uri uri, Context context) {
        if (!isUriValid(uri, context.getContentResolver())) {
            Log.e(TAG, "Uri invalid. uri=" + uri);
            return;
        }
        context.startActivity(new Intent(REVIEW_ACTION, uri));
        context.startActivity(new Intent("android.intent.action.VIEW", uri));
    }

    public static String viewVisibilityToString(int i) {
        return i != 0 ? i != 4 ? i != 8 ? "UNKNOWN" : "GONE" : "INVISIBLE" : "VISIBLE";
    }

    public static boolean workaroundForJ1() {
        return b.vu.equals("cmi");
    }

    public static void writeFile2Stream(File file, ByteArrayOutputStream byteArrayOutputStream, int i) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[1024];
            fileInputStream.skip((long) i);
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read < 0) {
                    fileInputStream.close();
                    return;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
