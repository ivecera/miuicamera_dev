package com.bumptech.glide;

import android.widget.ImageView;

/* compiled from: RequestBuilder */
/* synthetic */ class i {
    static final /* synthetic */ int[] Nj = new int[ImageView.ScaleType.values().length];
    static final /* synthetic */ int[] Oj = new int[Priority.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(24:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|(3:31|32|34)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|(3:31|32|34)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(28:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|34) */
    /* JADX WARNING: Can't wrap try/catch for region: R(29:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|34) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0066 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0071 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x007c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0087 */
    static {
        try {
            Oj[Priority.LOW.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            Oj[Priority.NORMAL.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            Oj[Priority.HIGH.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            Oj[Priority.IMMEDIATE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        Nj[ImageView.ScaleType.CENTER_CROP.ordinal()] = 1;
        Nj[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 2;
        Nj[ImageView.ScaleType.FIT_CENTER.ordinal()] = 3;
        Nj[ImageView.ScaleType.FIT_START.ordinal()] = 4;
        Nj[ImageView.ScaleType.FIT_END.ordinal()] = 5;
        Nj[ImageView.ScaleType.FIT_XY.ordinal()] = 6;
        Nj[ImageView.ScaleType.CENTER.ordinal()] = 7;
        try {
            Nj[ImageView.ScaleType.MATRIX.ordinal()] = 8;
        } catch (NoSuchFieldError unused5) {
        }
    }
}
