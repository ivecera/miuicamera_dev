package android.support.v4.media;

import android.media.AudioAttributes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
class AudioAttributesCompatApi21 {
    private static final String TAG = "AudioAttributesCompat";
    private static Method sAudioAttributesToLegacyStreamType;

    /* access modifiers changed from: package-private */
    public static final class Wrapper {
        private AudioAttributes mWrapped;

        private Wrapper(AudioAttributes audioAttributes) {
            this.mWrapped = audioAttributes;
        }

        public static Wrapper wrap(@NonNull AudioAttributes audioAttributes) {
            if (audioAttributes != null) {
                return new Wrapper(audioAttributes);
            }
            throw new IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null");
        }

        public AudioAttributes unwrap() {
            return this.mWrapped;
        }
    }

    private AudioAttributesCompatApi21() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002c, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002d, code lost:
        android.util.Log.w(android.support.v4.media.AudioAttributesCompatApi21.TAG, "getLegacyStreamType() failed on API21+", r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0035, code lost:
        return -1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x002c A[ExcHandler: ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException (r5v5 'e' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:3:0x0014] */
    public static int toLegacyStreamType(Wrapper wrapper) {
        AudioAttributes unwrap = wrapper.unwrap();
        if (sAudioAttributesToLegacyStreamType == null) {
            try {
                sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", AudioAttributes.class);
            } catch (ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
            }
        }
        return ((Integer) sAudioAttributesToLegacyStreamType.invoke(null, unwrap)).intValue();
    }
}
