package com.ss.android.vesdk.runtime;

import com.ss.android.vesdk.VEException;
import java.io.File;

public class VEResManager {
    static final String AAC_SURFIX = ".aac";
    static final String AUDIO_FOLDER = "audio";
    static final String COMPOSE_FOLDER = "compose";
    static final String CONCAT_FOLDER = "concat";
    static final String RECORD_AUDIO_SURFIX = ".wav";
    static final String RECORD_VIDEO_SURFIX = ".mp4";
    static final String SEGMENT_FOLDER = "segments";
    static final String UNDERLINE_CONCAT = "_";
    private String mWorkspace = VERuntime.getInstance().getEnv().getWorkspace();

    VEResManager() {
    }

    private String getFolder(String str) throws VEException {
        File file = new File(this.mWorkspace, str);
        if (file.exists() || file.mkdirs()) {
            return file.getAbsolutePath();
        }
        throw new VEException(-100, "mkdirs failed, workspace path: " + this.mWorkspace);
    }

    public static String getFolder(String str, String str2) throws VEException {
        File file = new File(str, str2);
        if (file.exists() || file.mkdirs()) {
            return file.getAbsolutePath();
        }
        throw new VEException(-100, "mkdirs failed, workspace path: " + str);
    }

    public String genRecordAacPath() {
        return getFolder("audio") + File.separator + System.currentTimeMillis() + UNDERLINE_CONCAT + "record" + AAC_SURFIX;
    }

    public String genRecordWavPath() {
        return getFolder("audio") + File.separator + System.currentTimeMillis() + UNDERLINE_CONCAT + "record" + RECORD_AUDIO_SURFIX;
    }
}
