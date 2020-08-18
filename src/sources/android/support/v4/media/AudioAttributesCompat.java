package android.support.v4.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.AudioAttributesCompatApi21;
import android.util.SparseIntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class AudioAttributesCompat {
    private static final String AUDIO_ATTRIBUTES_CONTENT_TYPE = "android.support.v4.media.audio_attrs.CONTENT_TYPE";
    private static final String AUDIO_ATTRIBUTES_FLAGS = "android.support.v4.media.audio_attrs.FLAGS";
    private static final String AUDIO_ATTRIBUTES_FRAMEWORKS = "android.support.v4.media.audio_attrs.FRAMEWORKS";
    private static final String AUDIO_ATTRIBUTES_LEGACY_STREAM_TYPE = "android.support.v4.media.audio_attrs.LEGACY_STREAM_TYPE";
    private static final String AUDIO_ATTRIBUTES_USAGE = "android.support.v4.media.audio_attrs.USAGE";
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    private static final int FLAG_ALL = 1023;
    private static final int FLAG_ALL_PUBLIC = 273;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    private static final int FLAG_BEACON = 8;
    private static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
    private static final int FLAG_BYPASS_MUTE = 128;
    private static final int FLAG_DEEP_BUFFER = 512;
    public static final int FLAG_HW_AV_SYNC = 16;
    private static final int FLAG_HW_HOTWORD = 32;
    private static final int FLAG_LOW_LATENCY = 256;
    private static final int FLAG_SCO = 4;
    private static final int FLAG_SECURE = 2;
    private static final int[] SDK_USAGES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
    private static final int SUPPRESSIBLE_CALL = 2;
    private static final int SUPPRESSIBLE_NOTIFICATION = 1;
    private static final SparseIntArray SUPPRESSIBLE_USAGES = new SparseIntArray();
    private static final String TAG = "AudioAttributesCompat";
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    private static final int USAGE_VIRTUAL_SOURCE = 15;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    /* access modifiers changed from: private */
    public static boolean sForceLegacyBehavior;
    /* access modifiers changed from: private */
    public AudioAttributesCompatApi21.Wrapper mAudioAttributesWrapper;
    int mContentType;
    int mFlags;
    Integer mLegacyStream;
    int mUsage;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AttributeContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AttributeUsage {
    }

    private static abstract class AudioManagerHidden {
        public static final int STREAM_ACCESSIBILITY = 10;
        public static final int STREAM_BLUETOOTH_SCO = 6;
        public static final int STREAM_SYSTEM_ENFORCED = 7;
        public static final int STREAM_TTS = 9;

        private AudioManagerHidden() {
        }
    }

    public static class Builder {
        private Object mAAObject;
        private int mContentType = 0;
        private int mFlags = 0;
        private Integer mLegacyStream;
        private int mUsage = 0;

        public Builder() {
        }

        public Builder(AudioAttributesCompat audioAttributesCompat) {
            this.mUsage = audioAttributesCompat.mUsage;
            this.mContentType = audioAttributesCompat.mContentType;
            this.mFlags = audioAttributesCompat.mFlags;
            this.mLegacyStream = audioAttributesCompat.mLegacyStream;
            this.mAAObject = audioAttributesCompat.unwrap();
        }

        public AudioAttributesCompat build() {
            if (AudioAttributesCompat.sForceLegacyBehavior || Build.VERSION.SDK_INT < 21) {
                AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
                audioAttributesCompat.mContentType = this.mContentType;
                audioAttributesCompat.mFlags = this.mFlags;
                audioAttributesCompat.mUsage = this.mUsage;
                audioAttributesCompat.mLegacyStream = this.mLegacyStream;
                AudioAttributesCompatApi21.Wrapper unused = audioAttributesCompat.mAudioAttributesWrapper = null;
                return audioAttributesCompat;
            }
            Object obj = this.mAAObject;
            if (obj != null) {
                return AudioAttributesCompat.wrap(obj);
            }
            AudioAttributes.Builder usage = new AudioAttributes.Builder().setContentType(this.mContentType).setFlags(this.mFlags).setUsage(this.mUsage);
            Integer num = this.mLegacyStream;
            if (num != null) {
                usage.setLegacyStreamType(num.intValue());
            }
            return AudioAttributesCompat.wrap(usage.build());
        }

        public Builder setContentType(int i) {
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
                this.mContentType = i;
            } else {
                this.mUsage = 0;
            }
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = (i & AudioAttributesCompat.FLAG_ALL) | this.mFlags;
            return this;
        }

        public Builder setLegacyStreamType(int i) {
            if (i != 10) {
                this.mLegacyStream = Integer.valueOf(i);
                this.mUsage = AudioAttributesCompat.usageForStreamType(i);
                return this;
            }
            throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
        }

        public Builder setUsage(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    this.mUsage = i;
                    break;
                case 16:
                    if (!AudioAttributesCompat.sForceLegacyBehavior && Build.VERSION.SDK_INT > 25) {
                        this.mUsage = i;
                        break;
                    } else {
                        this.mUsage = 12;
                        break;
                    }
                default:
                    this.mUsage = 0;
                    break;
            }
            return this;
        }
    }

    static {
        SUPPRESSIBLE_USAGES.put(5, 1);
        SUPPRESSIBLE_USAGES.put(6, 2);
        SUPPRESSIBLE_USAGES.put(7, 2);
        SUPPRESSIBLE_USAGES.put(8, 1);
        SUPPRESSIBLE_USAGES.put(9, 1);
        SUPPRESSIBLE_USAGES.put(10, 1);
    }

    private AudioAttributesCompat() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mFlags = 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static AudioAttributesCompat fromBundle(Bundle bundle) {
        Integer num = null;
        if (bundle == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttributes = (AudioAttributes) bundle.getParcelable(AUDIO_ATTRIBUTES_FRAMEWORKS);
            if (audioAttributes == null) {
                return null;
            }
            return wrap(audioAttributes);
        }
        int i = bundle.getInt(AUDIO_ATTRIBUTES_USAGE, 0);
        int i2 = bundle.getInt(AUDIO_ATTRIBUTES_CONTENT_TYPE, 0);
        int i3 = bundle.getInt(AUDIO_ATTRIBUTES_FLAGS, 0);
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        audioAttributesCompat.mUsage = i;
        audioAttributesCompat.mContentType = i2;
        audioAttributesCompat.mFlags = i3;
        if (bundle.containsKey(AUDIO_ATTRIBUTES_LEGACY_STREAM_TYPE)) {
            num = Integer.valueOf(bundle.getInt(AUDIO_ATTRIBUTES_LEGACY_STREAM_TYPE));
        }
        audioAttributesCompat.mLegacyStream = num;
        return audioAttributesCompat;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void setForceLegacyBehavior(boolean z) {
        sForceLegacyBehavior = z;
    }

    static int toVolumeStreamType(boolean z, int i, int i2) {
        if ((i & 1) == 1) {
            return z ? 1 : 7;
        }
        if ((i & 4) == 4) {
            return z ? 0 : 6;
        }
        switch (i2) {
            case 0:
                return z ? Integer.MIN_VALUE : 3;
            case 1:
            case 12:
            case 14:
            case 16:
                return 3;
            case 2:
                return 0;
            case 3:
                return z ? 0 : 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
                return 10;
            case 13:
                return 1;
            case 15:
            default:
                if (!z) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown usage value " + i2 + " in audio attributes");
        }
    }

    static int toVolumeStreamType(boolean z, AudioAttributesCompat audioAttributesCompat) {
        return toVolumeStreamType(z, audioAttributesCompat.getFlags(), audioAttributesCompat.getUsage());
    }

    /* access modifiers changed from: private */
    public static int usageForStreamType(int i) {
        switch (i) {
            case 0:
                return 2;
            case 1:
            case 7:
                return 13;
            case 2:
                return 6;
            case 3:
                return 1;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 2;
            case 8:
                return 3;
            case 9:
            default:
                return 0;
            case 10:
                return 11;
        }
    }

    static String usageToString(int i) {
        switch (i) {
            case 0:
                return new String("USAGE_UNKNOWN");
            case 1:
                return new String("USAGE_MEDIA");
            case 2:
                return new String("USAGE_VOICE_COMMUNICATION");
            case 3:
                return new String("USAGE_VOICE_COMMUNICATION_SIGNALLING");
            case 4:
                return new String("USAGE_ALARM");
            case 5:
                return new String("USAGE_NOTIFICATION");
            case 6:
                return new String("USAGE_NOTIFICATION_RINGTONE");
            case 7:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST");
            case 8:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT");
            case 9:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED");
            case 10:
                return new String("USAGE_NOTIFICATION_EVENT");
            case 11:
                return new String("USAGE_ASSISTANCE_ACCESSIBILITY");
            case 12:
                return new String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE");
            case 13:
                return new String("USAGE_ASSISTANCE_SONIFICATION");
            case 14:
                return new String("USAGE_GAME");
            case 15:
            default:
                return new String("unknown usage " + i);
            case 16:
                return new String("USAGE_ASSISTANT");
        }
    }

    @Nullable
    public static AudioAttributesCompat wrap(@NonNull Object obj) {
        if (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior) {
            return null;
        }
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        audioAttributesCompat.mAudioAttributesWrapper = AudioAttributesCompatApi21.Wrapper.wrap((AudioAttributes) obj);
        return audioAttributesCompat;
    }

    public boolean equals(Object obj) {
        AudioAttributesCompatApi21.Wrapper wrapper;
        if (this == obj) {
            return true;
        }
        if (obj == null || AudioAttributesCompat.class != obj.getClass()) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && (wrapper = this.mAudioAttributesWrapper) != null) {
            return wrapper.unwrap().equals(audioAttributesCompat.unwrap());
        }
        if (this.mContentType == audioAttributesCompat.getContentType() && this.mFlags == audioAttributesCompat.getFlags() && this.mUsage == audioAttributesCompat.getUsage()) {
            Integer num = this.mLegacyStream;
            if (num != null) {
                if (num.equals(audioAttributesCompat.mLegacyStream)) {
                    return true;
                }
            } else if (audioAttributesCompat.mLegacyStream == null) {
                return true;
            }
        }
        return false;
    }

    public int getContentType() {
        AudioAttributesCompatApi21.Wrapper wrapper;
        return (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior || (wrapper = this.mAudioAttributesWrapper) == null) ? this.mContentType : wrapper.unwrap().getContentType();
    }

    public int getFlags() {
        AudioAttributesCompatApi21.Wrapper wrapper;
        if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && (wrapper = this.mAudioAttributesWrapper) != null) {
            return wrapper.unwrap().getFlags();
        }
        int i = this.mFlags;
        int legacyStreamType = getLegacyStreamType();
        if (legacyStreamType == 6) {
            i |= 4;
        } else if (legacyStreamType == 7) {
            i |= 1;
        }
        return i & 273;
    }

    public int getLegacyStreamType() {
        Integer num = this.mLegacyStream;
        return num != null ? num.intValue() : (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior) ? toVolumeStreamType(false, this.mFlags, this.mUsage) : AudioAttributesCompatApi21.toLegacyStreamType(this.mAudioAttributesWrapper);
    }

    public int getUsage() {
        AudioAttributesCompatApi21.Wrapper wrapper;
        return (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior || (wrapper = this.mAudioAttributesWrapper) == null) ? this.mUsage : wrapper.unwrap().getUsage();
    }

    public int getVolumeControlStream() {
        return (Build.VERSION.SDK_INT < 26 || sForceLegacyBehavior || unwrap() == null) ? toVolumeStreamType(true, this) : ((AudioAttributes) unwrap()).getVolumeControlStream();
    }

    public int hashCode() {
        AudioAttributesCompatApi21.Wrapper wrapper;
        return (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior || (wrapper = this.mAudioAttributesWrapper) == null) ? Arrays.hashCode(new Object[]{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), this.mLegacyStream}) : wrapper.unwrap().hashCode();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= 21) {
            bundle.putParcelable(AUDIO_ATTRIBUTES_FRAMEWORKS, this.mAudioAttributesWrapper.unwrap());
        } else {
            bundle.putInt(AUDIO_ATTRIBUTES_USAGE, this.mUsage);
            bundle.putInt(AUDIO_ATTRIBUTES_CONTENT_TYPE, this.mContentType);
            bundle.putInt(AUDIO_ATTRIBUTES_FLAGS, this.mFlags);
            Integer num = this.mLegacyStream;
            if (num != null) {
                bundle.putInt(AUDIO_ATTRIBUTES_LEGACY_STREAM_TYPE, num.intValue());
            }
        }
        return bundle;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (unwrap() != null) {
            sb.append(" audioattributes=");
            sb.append(unwrap());
        } else {
            if (this.mLegacyStream != null) {
                sb.append(" stream=");
                sb.append(this.mLegacyStream);
                sb.append(" derived");
            }
            sb.append(" usage=");
            sb.append(usageToString());
            sb.append(" content=");
            sb.append(this.mContentType);
            sb.append(" flags=0x");
            sb.append(Integer.toHexString(this.mFlags).toUpperCase());
        }
        return sb.toString();
    }

    @Nullable
    public Object unwrap() {
        AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
        if (wrapper != null) {
            return wrapper.unwrap();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public String usageToString() {
        return usageToString(this.mUsage);
    }
}
