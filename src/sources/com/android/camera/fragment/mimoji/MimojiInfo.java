package com.android.camera.fragment.mimoji;

public class MimojiInfo implements Comparable<MimojiInfo> {
    public int height;
    public String mAvatarTemplatePath;
    public String mConfigPath;
    public byte[] mData;
    public long mDirectoryName;
    public String mPackPath;
    public String mThumbnailUrl;
    public int width;

    public int compareTo(MimojiInfo mimojiInfo) {
        long j = this.mDirectoryName;
        long j2 = mimojiInfo.mDirectoryName;
        if (j > j2) {
            return -1;
        }
        return j < j2 ? 1 : 0;
    }
}
