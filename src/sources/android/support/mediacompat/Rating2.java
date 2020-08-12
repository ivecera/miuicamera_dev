package android.support.mediacompat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.ObjectsCompat;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Rating2 {
    private static final String KEY_STYLE = "android.media.rating2.style";
    private static final String KEY_VALUE = "android.media.rating2.value";
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating2";
    private final int mRatingStyle;
    private final float mRatingValue;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Style {
    }

    private Rating2(int i, float f2) {
        this.mRatingStyle = i;
        this.mRatingValue = f2;
    }

    public static Rating2 fromBundle(@Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return new Rating2(bundle.getInt(KEY_STYLE), bundle.getFloat(KEY_VALUE));
    }

    @Nullable
    public static Rating2 newHeartRating(boolean z) {
        return new Rating2(1, z ? 1.0f : 0.0f);
    }

    @Nullable
    public static Rating2 newPercentageRating(float f2) {
        if (f2 >= 0.0f && f2 <= 100.0f) {
            return new Rating2(6, f2);
        }
        Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    @Nullable
    public static Rating2 newStarRating(int i, float f2) {
        float f3;
        if (i == 3) {
            f3 = 3.0f;
        } else if (i == 4) {
            f3 = 4.0f;
        } else if (i != 5) {
            Log.e(TAG, "Invalid rating style (" + i + ") for a star rating");
            return null;
        } else {
            f3 = 5.0f;
        }
        if (f2 >= 0.0f && f2 <= f3) {
            return new Rating2(i, f2);
        }
        Log.e(TAG, "Trying to set out of range star-based rating");
        return null;
    }

    @Nullable
    public static Rating2 newThumbRating(boolean z) {
        return new Rating2(2, z ? 1.0f : 0.0f);
    }

    @Nullable
    public static Rating2 newUnratedRating(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new Rating2(i, -1.0f);
            default:
                return null;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Rating2)) {
            return false;
        }
        Rating2 rating2 = (Rating2) obj;
        return this.mRatingStyle == rating2.mRatingStyle && this.mRatingValue == rating2.mRatingValue;
    }

    public float getPercentRating() {
        if (this.mRatingStyle != 6 || !isRated()) {
            return -1.0f;
        }
        return this.mRatingValue;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public float getStarRating() {
        int i = this.mRatingStyle;
        if ((i == 3 || i == 4 || i == 5) && isRated()) {
            return this.mRatingValue;
        }
        return -1.0f;
    }

    public boolean hasHeart() {
        return this.mRatingStyle == 1 && this.mRatingValue == 1.0f;
    }

    public int hashCode() {
        return ObjectsCompat.hash(Integer.valueOf(this.mRatingStyle), Float.valueOf(this.mRatingValue));
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_STYLE, this.mRatingStyle);
        bundle.putFloat(KEY_VALUE, this.mRatingValue);
        return bundle;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rating2:style=");
        sb.append(this.mRatingStyle);
        sb.append(" rating=");
        float f2 = this.mRatingValue;
        sb.append(f2 < 0.0f ? "unrated" : String.valueOf(f2));
        return sb.toString();
    }
}
