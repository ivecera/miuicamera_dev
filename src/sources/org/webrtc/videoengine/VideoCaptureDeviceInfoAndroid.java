package org.webrtc.videoengine;

import android.hardware.Camera;
import android.util.Log;
import com.android.camera.statistic.MistatsConstants;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoCaptureDeviceInfoAndroid {
    private static final String TAG = "WEBRTC-JC-VideoCaptureDeviceInfoAndroid";

    private static String deviceUniqueName(int i, Camera.CameraInfo cameraInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera ");
        sb.append(i);
        sb.append(", Facing ");
        sb.append(isFrontFacing(cameraInfo) ? "front" : MistatsConstants.BaseEvent.BACK);
        sb.append(", Orientation ");
        sb.append(cameraInfo.orientation);
        return sb.toString();
    }

    private static String getDeviceInfo() {
        try {
            JSONArray jSONArray = new JSONArray();
            int numberOfCameras = Camera.getNumberOfCameras();
            Log.d(TAG, "Number of cameras:");
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                String deviceUniqueName = deviceUniqueName(i, cameraInfo);
                JSONObject jSONObject = new JSONObject();
                jSONArray.put(jSONObject);
                Camera camera = null;
                try {
                    Log.e(TAG, "Start open camera:" + i);
                    camera = Camera.open(i);
                    Log.e(TAG, "success open camera:" + i);
                    Camera.Parameters parameters = camera.getParameters();
                    List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                    List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
                    Log.d(TAG, deviceUniqueName);
                    if (camera != null) {
                        camera.release();
                    }
                    JSONArray jSONArray2 = new JSONArray();
                    for (Camera.Size size : supportedPreviewSizes) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("width", size.width);
                        jSONObject2.put("height", size.height);
                        jSONArray2.put(jSONObject2);
                    }
                    JSONArray jSONArray3 = new JSONArray();
                    for (int[] iArr : supportedPreviewFpsRange) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("min_mfps", iArr[0]);
                        jSONObject3.put("max_mfps", iArr[1]);
                        jSONArray3.put(jSONObject3);
                    }
                    jSONObject.put("name", deviceUniqueName);
                    jSONObject.put("front_facing", isFrontFacing(cameraInfo)).put("orientation", cameraInfo.orientation).put("sizes", jSONArray2).put("mfpsRanges", jSONArray3);
                } catch (RuntimeException e2) {
                    Log.e(TAG, "Failed to open " + deviceUniqueName + ", skipping", e2);
                    if (camera != null) {
                        camera.release();
                    }
                } catch (Throwable th) {
                    if (camera != null) {
                        camera.release();
                    }
                    throw th;
                }
            }
            return jSONArray.toString(2);
        } catch (JSONException e3) {
            throw new RuntimeException(e3);
        }
    }

    private static boolean isFrontFacing(Camera.CameraInfo cameraInfo) {
        return cameraInfo.facing == 1;
    }
}
