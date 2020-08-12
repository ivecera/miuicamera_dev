package e.a.a;

import android.support.annotation.RestrictTo;
import com.ss.android.medialib.audio.AudioDataProcessThread;

/* compiled from: AudioRecorderInterface */
public interface a extends AudioDataProcessThread.OnProcessDataListener {
    int addPCMData(byte[] bArr, int i);

    int closeWavFile(boolean z);

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    int initAudioConfig(int i, int i2);

    int initWavFile(int i, int i2, double d2);

    void lackPermission();

    @Override // com.ss.android.medialib.audio.AudioDataProcessThread.OnProcessDataListener
    int onProcessData(byte[] bArr, int i);

    void recordStatus(boolean z);
}
