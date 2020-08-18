package com.bumptech.glide.load;

/* JADX INFO: Failed to restore enum class, 'enum' modifier removed */
public final class DecodeFormat extends Enum<DecodeFormat> {
    private static final /* synthetic */ DecodeFormat[] $VALUES;
    public static final DecodeFormat DEFAULT;
    public static final DecodeFormat Gx = new DecodeFormat("PREFER_ARGB_8888", 0);
    @Deprecated
    public static final DecodeFormat Hx = new DecodeFormat("PREFER_ARGB_8888_DISALLOW_HARDWARE", 1);
    public static final DecodeFormat Ix = new DecodeFormat("PREFER_RGB_565", 2);

    static {
        DecodeFormat decodeFormat = Hx;
        $VALUES = new DecodeFormat[]{Gx, decodeFormat, Ix};
        DEFAULT = decodeFormat;
    }

    private DecodeFormat(String str, int i) {
    }

    public static DecodeFormat valueOf(String str) {
        return (DecodeFormat) Enum.valueOf(DecodeFormat.class, str);
    }

    public static DecodeFormat[] values() {
        return (DecodeFormat[]) $VALUES.clone();
    }
}
