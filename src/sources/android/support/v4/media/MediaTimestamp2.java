package android.support.v4.media;

import android.annotation.TargetApi;
import android.media.MediaTimestamp;
import android.support.annotation.RestrictTo;

public final class MediaTimestamp2 {
    public static final MediaTimestamp2 TIMESTAMP_UNKNOWN = new MediaTimestamp2(-1, -1, 0.0f);
    private final float mClockRate;
    private final long mMediaTimeUs;
    private final long mNanoTime;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    MediaTimestamp2() {
        this.mMediaTimeUs = 0;
        this.mNanoTime = 0;
        this.mClockRate = 1.0f;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    MediaTimestamp2(long j, long j2, float f2) {
        this.mMediaTimeUs = j;
        this.mNanoTime = j2;
        this.mClockRate = f2;
    }

    @TargetApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    MediaTimestamp2(MediaTimestamp mediaTimestamp) {
        this.mMediaTimeUs = mediaTimestamp.getAnchorMediaTimeUs();
        this.mNanoTime = mediaTimestamp.getAnchorSytemNanoTime();
        this.mClockRate = mediaTimestamp.getMediaClockRate();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MediaTimestamp2.class != obj.getClass()) {
            return false;
        }
        MediaTimestamp2 mediaTimestamp2 = (MediaTimestamp2) obj;
        return this.mMediaTimeUs == mediaTimestamp2.mMediaTimeUs && this.mNanoTime == mediaTimestamp2.mNanoTime && this.mClockRate == mediaTimestamp2.mClockRate;
    }

    public long getAnchorMediaTimeUs() {
        return this.mMediaTimeUs;
    }

    public long getAnchorSytemNanoTime() {
        return this.mNanoTime;
    }

    public float getMediaClockRate() {
        return this.mClockRate;
    }

    public int hashCode() {
        return (int) (((float) (((int) (((long) (Long.valueOf(this.mMediaTimeUs).hashCode() * 31)) + this.mNanoTime)) * 31)) + this.mClockRate);
    }

    public String toString() {
        return MediaTimestamp2.class.getName() + "{AnchorMediaTimeUs=" + this.mMediaTimeUs + " AnchorSystemNanoTime=" + this.mNanoTime + " ClockRate=" + this.mClockRate + "}";
    }
}
