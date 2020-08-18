package com.android.camera.sticker;

import android.content.Context;
import android.widget.Toast;
import com.android.camera.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StickerHelper {
    private static final String FACE_TRACK_MODEL_NAME = "face_track_3.3.0.model";
    private static final String STICKER_MODEL_PATH = "sticker_model";
    private static final String STICKER_RES_PATH = "sticker_res";
    private static StickerHelper mInstance;
    public static String[] sStickLocal = {"rabbiteating.zip", "bunny.zip"};
    private static boolean sStickerEnable = false;
    private Context mContext;

    private StickerHelper() {
    }

    private void copyModelFiles() {
        FileUtils.copyFileIfNeed(this.mContext, new File(getModelPath(), FACE_TRACK_MODEL_NAME), FACE_TRACK_MODEL_NAME);
    }

    private void copyStickerFiles() {
        String[] strArr = sStickLocal;
        for (String str : strArr) {
            FileUtils.copyFileIfNeed(this.mContext, new File(getStickerPath(), str), str);
        }
    }

    public static StickerHelper getInstance() {
        if (mInstance == null) {
            synchronized (StickerHelper.class) {
                if (mInstance == null) {
                    mInstance = new StickerHelper();
                }
            }
        }
        return mInstance;
    }

    public List<StickerInfo> getLocalStick() {
        ArrayList arrayList = new ArrayList();
        StickerInfo stickerInfo = new StickerInfo(null, R.drawable.icon_sticker_none);
        StickerInfo stickerInfo2 = new StickerInfo(getStickerPath(sStickLocal[0]), R.drawable.icon_sticker1);
        StickerInfo stickerInfo3 = new StickerInfo(getStickerPath(sStickLocal[1]), R.drawable.icon_sticker2);
        arrayList.add(stickerInfo);
        arrayList.add(stickerInfo2);
        arrayList.add(stickerInfo3);
        return arrayList;
    }

    public String getModelPath() {
        File filesDir = this.mContext.getFilesDir();
        if (filesDir == null) {
            return null;
        }
        return filesDir.getAbsolutePath() + File.separator + STICKER_MODEL_PATH;
    }

    public String getStickerPath() {
        return this.mContext.getExternalFilesDir(STICKER_RES_PATH).getAbsolutePath();
    }

    public String getStickerPath(long j) {
        return getStickerPath() + File.separator + Long.toString(j);
    }

    public String getStickerPath(String str) {
        return getStickerPath() + File.separator + str;
    }

    public String getTrackModelPath() {
        return getModelPath() + File.separator + FACE_TRACK_MODEL_NAME;
    }

    public void initStickFiles(Context context) {
        if (sStickerEnable) {
            this.mContext = context.getApplicationContext();
            if (!STLicenseUtils.checkLicense(this.mContext)) {
                Toast.makeText(this.mContext, "You should be authorized first!", 0).show();
            }
            copyStickerFiles();
            copyModelFiles();
        }
    }
}
