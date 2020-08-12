package com.android.camera.statistic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Map;

public class ScenarioTrackUtil {
    private static final String TAG = "ScenarioTrackUtil";
    public static final CameraEventScenario sCaptureTimeScenario = new CameraEventScenario("CaptureTime");
    public static final CameraEventScenario sLaunchTimeScenario = new CameraEventScenario("CameraLaunchTime");
    public static final CameraEventScenario sShotToGalleryTimeScenario = new CameraEventScenario("ShotToGallery");
    public static final CameraEventScenario sShotToViewTimeScenario = new CameraEventScenario("ShotToView");
    public static final CameraEventScenario sStartVideoRecordTimeScenario = new CameraEventScenario("StartVideoRecordTime");
    public static final CameraEventScenario sStopVideoRecordTimeScenario = new CameraEventScenario("StopVideoRecordTime");
    private static final CameraEventScenario sSwitchCameraTimeScenario = new CameraEventScenario("SwitchCameraTime");
    public static final CameraEventScenario sSwitchModeTimeScenario = new CameraEventScenario("SwitchModeTime");

    public static class CameraEventScenario {
        private static final String CAMERA_PACKAGE = "com.android.camera";
        private static final String CATEGORY_PERFORMANCE = "Performance";
        String mEventName;

        CameraEventScenario(String str) {
            this.mEventName = str;
        }

        public String toString() {
            return this.mEventName;
        }
    }

    public static void trackAppLunchTimeEnd(@Nullable Map map, Context context) {
    }

    public static void trackAppLunchTimeStart(@NonNull boolean z) {
    }

    public static void trackCaptureTimeEnd() {
    }

    public static void trackCaptureTimeStart(@NonNull boolean z, @NonNull int i) {
    }

    public static void trackScenarioAbort(@NonNull CameraEventScenario cameraEventScenario) {
    }

    public static void trackScenarioAbort(@NonNull CameraEventScenario cameraEventScenario, @Nullable String str) {
    }

    public static void trackShotToGalleryEnd(@NonNull boolean z, @NonNull long j) {
    }

    public static void trackShotToGalleryStart(@NonNull boolean z, @NonNull int i, @NonNull long j) {
    }

    public static void trackShotToViewEnd(@NonNull boolean z, @NonNull long j) {
    }

    public static void trackShotToViewStart(@NonNull boolean z, @NonNull int i, @NonNull long j) {
    }

    public static void trackStartVideoRecordEnd() {
    }

    public static void trackStartVideoRecordStart(@NonNull String str, @NonNull boolean z) {
    }

    public static void trackStopVideoRecordEnd() {
    }

    public static void trackStopVideoRecordStart(@NonNull String str, @NonNull boolean z) {
    }

    public static void trackSwitchCameraEnd() {
    }

    public static void trackSwitchCameraStart(@NonNull boolean z, @NonNull boolean z2, @NonNull int i) {
    }

    public static void trackSwitchModeEnd() {
    }

    public static void trackSwitchModeStart(@NonNull int i, @NonNull int i2, @NonNull boolean z) {
    }
}
