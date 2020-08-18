package com.ss.android.ttve.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class TETrackIndexManager {
    public static final int TRACK_TYPE_AUDIO = 1;
    public static final int TRACK_TYPE_VIDEO = 2;
    private List<Integer> mAudioTrackIndexList = new ArrayList();
    private int mFirstAudioIndex = -1;
    private int mFirstVideoIndex = -1;
    private List<Integer> mVideoTrackIndexList = new ArrayList();

    @Retention(RetentionPolicy.SOURCE)
    public @interface TETrackType {
    }

    public int addTrack(int i, int i2) {
        if (i == 1) {
            if (this.mFirstAudioIndex == -1) {
                this.mFirstAudioIndex = i2;
            }
            if (this.mAudioTrackIndexList.size() > 0) {
                List<Integer> list = this.mAudioTrackIndexList;
                i2 = list.get(list.size() - 1).intValue() + 1;
            }
            this.mAudioTrackIndexList.add(Integer.valueOf(i2));
            return i2;
        } else if (i != 2) {
            return i2;
        } else {
            if (this.mFirstVideoIndex == -1) {
                this.mFirstVideoIndex = i2;
            }
            if (this.mVideoTrackIndexList.size() > 0) {
                List<Integer> list2 = this.mVideoTrackIndexList;
                i2 = list2.get(list2.size() - 1).intValue() + 1;
            }
            this.mVideoTrackIndexList.add(Integer.valueOf(i2));
            return i2;
        }
    }

    public int getNativeTrackIndex(int i, int i2) {
        int i3;
        int i4;
        int i5 = 0;
        if (i == 1) {
            int i6 = this.mFirstAudioIndex;
            if (i2 >= i6 && i6 != -1) {
                while (i5 < this.mAudioTrackIndexList.size()) {
                    if (i2 == this.mAudioTrackIndexList.get(i5).intValue()) {
                        i3 = this.mFirstAudioIndex;
                    } else {
                        i5++;
                    }
                }
            }
            return i2;
        } else if (i != 2 || i2 < (i4 = this.mFirstVideoIndex) || i4 == -1) {
            return i2;
        } else {
            while (i5 < this.mVideoTrackIndexList.size()) {
                if (i2 == this.mVideoTrackIndexList.get(i5).intValue()) {
                    i3 = this.mFirstVideoIndex;
                } else {
                    i5++;
                }
            }
            return i2;
        }
        return i5 + i3;
    }

    public void removeTrack(int i, int i2) {
        if (i == 1) {
            this.mAudioTrackIndexList.remove(Integer.valueOf(i2));
        } else if (i == 2) {
            this.mVideoTrackIndexList.remove(Integer.valueOf(i2));
        }
    }
}
