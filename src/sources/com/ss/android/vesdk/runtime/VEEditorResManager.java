package com.ss.android.vesdk.runtime;

import java.io.File;

public class VEEditorResManager {
    public String[] mAudioPaths;
    private String mComposedVideoPath;
    private String mCropedAudioPath;
    private String mCropedVideoPath;
    public int mOriginalSoundTrackIndex;
    public int mOriginalSoundTrackType;
    public String[] mReverseAudioPaths;
    public boolean mReverseDone = false;
    public String[] mReverseVideoPath;
    public String[] mTransitions;
    public String[] mVideoPaths;
    private String mWorkSpace;

    public VEEditorResManager(String str) {
        this.mWorkSpace = str;
    }

    public String genComposedVideoPath() {
        return VEResManager.getFolder(this.mWorkSpace, "compose") + File.separator + System.currentTimeMillis() + "_" + "composed" + ".mp4";
    }

    public String genReverseVideoPath(int i) {
        return VEResManager.getFolder(this.mWorkSpace, "concat") + File.separator + i + "_" + "reverse" + ".mp4";
    }

    public String genSeqAudioPath(int i) {
        return VEResManager.getFolder(this.mWorkSpace, "concat") + File.separator + i + "_" + "reverse" + ".wav";
    }

    public String getWorkspace() {
        return this.mWorkSpace;
    }
}
