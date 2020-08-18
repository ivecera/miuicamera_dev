package com.bef.effectsdk;

import android.media.MediaPlayer;
import android.support.annotation.Keep;
import android.util.Log;

@Keep
public class AudioPlayer {
    public static final String TAG = "AudioPlayer";
    private String mFilename;
    /* access modifiers changed from: private */
    public boolean mIsPrepared;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    /* access modifiers changed from: private */
    public long mNativePtr;

    @Keep
    public AudioPlayer() {
        this.mIsPrepared = false;
        this.mMediaPlayer = null;
        this.mIsPrepared = false;
        this.mFilename = null;
    }

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnCompletion(long j);

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnError(long j, int i, int i2);

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnInfo(long j, int i, int i2);

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnPrepared(long j);

    @Keep
    public float getCurrentPlayTime() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            return ((float) mediaPlayer.getCurrentPosition()) / 1000.0f;
        }
        Log.e(TAG, "MediaPlayer is null!");
        return 0.0f;
    }

    @Keep
    public float getTotalPlayTime() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            return ((float) mediaPlayer.getDuration()) / 1000.0f;
        }
        Log.e(TAG, "MediaPlayer is null!");
        return 0.0f;
    }

    @Keep
    public int init() {
        this.mIsPrepared = false;
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
        }
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            /* class com.bef.effectsdk.AudioPlayer.AnonymousClass1 */

            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                String str = AudioPlayer.TAG;
                Log.i(str, "MediaPlayer onInfo: [what, extra] = [" + i + ", " + i2 + "]");
                AudioPlayer audioPlayer = AudioPlayer.this;
                audioPlayer.nativeOnInfo(audioPlayer.mNativePtr, i, i2);
                return false;
            }
        });
        this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            /* class com.bef.effectsdk.AudioPlayer.AnonymousClass2 */

            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                String str = AudioPlayer.TAG;
                Log.d(str, "MediaPlayer onError: [what, extra] = [" + i + ", " + i2 + "]");
                try {
                    AudioPlayer.this.mMediaPlayer.stop();
                    AudioPlayer.this.mMediaPlayer.release();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    String str2 = AudioPlayer.TAG;
                    Log.e(str2, "MediaPlayer stop exception on error " + e2.toString());
                }
                MediaPlayer unused = AudioPlayer.this.mMediaPlayer = null;
                AudioPlayer audioPlayer = AudioPlayer.this;
                audioPlayer.nativeOnError(audioPlayer.mNativePtr, i, i2);
                return false;
            }
        });
        this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /* class com.bef.effectsdk.AudioPlayer.AnonymousClass3 */

            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.i(AudioPlayer.TAG, "MediaPlayer onPrepared...");
                boolean unused = AudioPlayer.this.mIsPrepared = true;
                AudioPlayer audioPlayer = AudioPlayer.this;
                audioPlayer.nativeOnPrepared(audioPlayer.mNativePtr);
            }
        });
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            /* class com.bef.effectsdk.AudioPlayer.AnonymousClass4 */

            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.i(AudioPlayer.TAG, "MediaPlayer onCompletion...");
                AudioPlayer audioPlayer = AudioPlayer.this;
                audioPlayer.nativeOnCompletion(audioPlayer.mNativePtr);
            }
        });
        return 0;
    }

    @Keep
    public boolean isPlaying() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            try {
                return mediaPlayer.isPlaying();
            } catch (Exception e2) {
                e2.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer isPlaying exception. " + e2.toString());
                return false;
            }
        }
    }

    @Keep
    public boolean pause() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            mediaPlayer.pause();
            return true;
        }
    }

    @Keep
    public int release() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            return 0;
        }
        try {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
        } catch (Exception e2) {
            e2.printStackTrace();
            String str = TAG;
            Log.e(str, "MediaPlayer stop exception on release " + e2.toString());
        }
        this.mMediaPlayer = null;
        return 0;
    }

    @Keep
    public boolean resume() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            mediaPlayer.start();
            return true;
        }
    }

    @Keep
    public boolean seek(int i) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            try {
                mediaPlayer.seekTo(i);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer seek exception. " + e2.toString());
                return true;
            }
        }
    }

    @Keep
    public void setDataSource(String str) {
        if (this.mMediaPlayer == null) {
            init();
        }
        if (!str.equals(this.mFilename) || !this.mIsPrepared || !this.mMediaPlayer.isPlaying()) {
            try {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.setDataSource(str);
            } catch (Exception e2) {
                e2.printStackTrace();
                String str2 = TAG;
                Log.e(str2, "MediaPlayer setDataSource exception. " + e2.toString());
            }
            this.mFilename = str;
        }
    }

    @Keep
    public boolean setLoop(boolean z) {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        }
        String str = TAG;
        Log.i(str, "set isLoop " + z);
        this.mMediaPlayer.setLooping(z);
        return true;
    }

    @Keep
    public void setNativePtr(long j) {
        this.mNativePtr = j;
    }

    @Keep
    public boolean setVolume(float f2) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            mediaPlayer.setVolume(f2, f2);
            return true;
        }
    }

    @Keep
    public void startPlay() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            try {
                if (!this.mIsPrepared) {
                    mediaPlayer.prepare();
                    this.mIsPrepared = true;
                }
                this.mMediaPlayer.start();
            } catch (Exception e2) {
                e2.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer setDataSource exception. " + e2.toString());
            }
        }
    }

    @Keep
    public void stopPlay() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
        } else if (this.mIsPrepared) {
            try {
                mediaPlayer.stop();
                this.mMediaPlayer.release();
            } catch (Exception e2) {
                e2.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer stop exception on stop " + e2.toString());
            }
            this.mMediaPlayer = null;
            this.mIsPrepared = false;
        }
    }
}
