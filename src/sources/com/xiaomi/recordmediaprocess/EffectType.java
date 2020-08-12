package com.xiaomi.recordmediaprocess;

public enum EffectType {
    BasicTransitionFilter2(0),
    ExtractCoverFilter(1),
    PhotoFilter(2),
    PngTransformFilter(3),
    ReverseFilter(4),
    RotateFilter(5),
    ScaleFilter(6),
    SetptsExtFilter(7),
    TrimFilter(8),
    TransitionFilter(9),
    TransitionOverlappFilter(10),
    TransitionEraseFilter(11),
    TransitionRotateFilter(12),
    TransitionZoomFilter(13),
    AF_Mp3MixFilter(14),
    AF_SpeedFilter(15),
    AudioMixerFilter(16),
    BasicImageFilter(17),
    CropFilter(18),
    ShakeFilter(19);
    
    private int nCode;

    private EffectType(int i) {
        this.nCode = i;
    }

    public static EffectType int2enum(int i) {
        EffectType effectType = BasicTransitionFilter2;
        EffectType[] values = values();
        for (EffectType effectType2 : values) {
            if (effectType2.ordinal() == i) {
                effectType = effectType2;
            }
        }
        return effectType;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
