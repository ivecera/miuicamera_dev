package com.android.camera.features.mimoji2.bean;

public class MimojiInfo2 implements Comparable<MimojiInfo2> {
    public String mAvatarTemplatePath;
    public String mConfigPath;
    public byte[] mData;
    public long mDirectoryName;
    public String mPackPath;
    public String mThumbnailUrl;

    public int compareTo(MimojiInfo2 mimojiInfo2) {
        long j = this.mDirectoryName;
        long j2 = mimojiInfo2.mDirectoryName;
        if (j > j2) {
            return -1;
        }
        return j < j2 ? 1 : 0;
    }
}
