package com.android.camera2;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.params.Face;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.log.Log;
import java.lang.reflect.Field;

@TargetApi(21)
public class CameraHardwareFace {
    public static final int CAMERA_META_DATA_T2T = 64206;
    private static final String TAG = "CameraHardwareFace";
    public float ageFemale;
    public float ageMale;
    public float beautyscore;
    public int blinkDetected = 0;
    public int faceRecognised = 0;
    public int faceType = 0;
    public float gender;
    public int id = -1;
    public Point leftEye = null;
    public Point mouth = null;
    public float prob;
    public Rect rect;
    public Point rightEye = null;
    public int score;
    public int smileDegree = 0;
    public int smileScore = 0;
    public int t2tStop = 0;

    public static CameraHardwareFace[] convertCameraHardwareFace(Face[] faceArr) {
        CameraHardwareFace[] cameraHardwareFaceArr = new CameraHardwareFace[faceArr.length];
        for (int i = 0; i < faceArr.length; i++) {
            cameraHardwareFaceArr[i] = new CameraHardwareFace();
            copyFace(cameraHardwareFaceArr[i], faceArr[i]);
        }
        return cameraHardwareFaceArr;
    }

    public static CameraHardwareFace[] convertExCameraHardwareFace(Face[] faceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        int min = Math.min(faceArr.length, faceAnalyzeInfo.mAge.length);
        CameraHardwareFace[] cameraHardwareFaceArr = new CameraHardwareFace[min];
        for (int i = 0; i < min; i++) {
            cameraHardwareFaceArr[i] = new CameraHardwareFace();
            CameraHardwareFace cameraHardwareFace = cameraHardwareFaceArr[i];
            Face face = faceArr[i];
            float f2 = faceAnalyzeInfo.mAge[i];
            float f3 = faceAnalyzeInfo.mGender[i];
            float[] fArr = faceAnalyzeInfo.mFaceScore;
            copyExFace(cameraHardwareFace, face, f2, f3, fArr == null ? 0.0f : fArr[i], faceAnalyzeInfo.mProp[i]);
        }
        return cameraHardwareFaceArr;
    }

    private static void copyExFace(CameraHardwareFace cameraHardwareFace, Face face, float f2, float f3, float f4, float f5) {
        cameraHardwareFace.rect = face.getBounds();
        cameraHardwareFace.score = face.getScore();
        cameraHardwareFace.id = face.getId();
        Field[] fields = face.getClass().getFields();
        for (Field field : fields) {
            try {
                cameraHardwareFace.getClass().getField(field.getName()).set(cameraHardwareFace, field.get(face));
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e2) {
                Log.e(TAG, e2.getMessage());
            }
        }
        cameraHardwareFace.gender = f3;
        cameraHardwareFace.beautyscore = f4;
        cameraHardwareFace.ageMale = f2;
        cameraHardwareFace.ageFemale = f2;
        cameraHardwareFace.prob = f5;
    }

    private static void copyFace(CameraHardwareFace cameraHardwareFace, Face face) {
        cameraHardwareFace.rect = face.getBounds();
        cameraHardwareFace.score = face.getScore();
        cameraHardwareFace.id = face.getId();
    }
}
