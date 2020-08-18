package com.android.camera.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import com.ss.android.vesdk.VEEditor;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class TextureVideoView extends TextureView implements TextureView.SurfaceTextureListener, Handler.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    public static final int CENTER = 2;
    public static final int CENTER_CROP = 1;
    public static final int FIT_CENTER = 4;
    public static final int FIT_LEFT_TOP_FULL_SCREEN = 6;
    public static final int FIT_XY = 3;
    private static final int MSG_PAUSE = 2;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 3;
    public static final int NONE = 5;
    private static final int PIVOT_CENTER = 2;
    private static final int PIVOT_LEFT_TOP = 1;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static final String TAG = "TextureVideoView";
    private static final HandlerThread sThread = new HandlerThread(TAG);
    private AudioManager mAudioManager;
    private boolean mClearSurface;
    private Context mContext;
    private volatile int mCurrentState = 0;
    private Handler mHandler;
    private boolean mHasAudio;
    private boolean mLoop;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    /* access modifiers changed from: private */
    public MediaPlayerCallback mMediaPlayerCallback;
    private int mScaleType = 4;
    private boolean mSoundMute;
    /* access modifiers changed from: private */
    public Surface mSurface;
    private volatile int mTargetState = 0;
    private Uri mUri;
    private Handler mVideoHandler;

    public interface MediaPlayerCallback {
        void onBufferingUpdate(MediaPlayer mediaPlayer, int i);

        void onCompletion(MediaPlayer mediaPlayer);

        boolean onError(MediaPlayer mediaPlayer, int i, int i2);

        boolean onInfo(MediaPlayer mediaPlayer, int i, int i2);

        void onPrepared(MediaPlayer mediaPlayer);

        void onSurfaceReady(Surface surface);

        void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PivotPoint {
    }

    private static class ScaleManager {
        private Size mVideoSize;
        private Size mViewSize;

        public ScaleManager(Size size, Size size2) {
            this.mViewSize = size;
            this.mVideoSize = size2;
        }

        private Matrix fitCenter() {
            return getFitScale(2);
        }

        private Matrix fitXY() {
            return getMatrix(1.0f, 1.0f, 1);
        }

        private Matrix getCropScale(int i) {
            float width = ((float) this.mViewSize.getWidth()) / ((float) this.mVideoSize.getWidth());
            float height = ((float) this.mViewSize.getHeight()) / ((float) this.mVideoSize.getHeight());
            float max = Math.max(width, height);
            return getMatrix(max / width, max / height, i);
        }

        private Matrix getFitScale(int i) {
            float width = ((float) this.mViewSize.getWidth()) / ((float) this.mVideoSize.getWidth());
            float height = ((float) this.mViewSize.getHeight()) / ((float) this.mVideoSize.getHeight());
            float min = Math.min(width, height);
            return getMatrix(min / width, min / height, i);
        }

        private Matrix getLeftTopFullScale() {
            Matrix fitCenter = fitCenter();
            fitCenter.postRotate(90.0f, (float) (this.mViewSize.getWidth() / 2), (float) (this.mViewSize.getHeight() / 2));
            fitCenter.postScale(1.7777778f, 1.7777778f, (float) (this.mViewSize.getWidth() / 2), (float) (this.mViewSize.getHeight() / 2));
            return fitCenter;
        }

        private Matrix getMatrix(float f2, float f3, float f4, float f5) {
            Matrix matrix = new Matrix();
            matrix.setScale(f2, f3, f4, f5);
            return matrix;
        }

        private Matrix getMatrix(float f2, float f3, int i) {
            if (i == 1) {
                return getMatrix(f2, f3, 0.0f, 0.0f);
            }
            if (i == 2) {
                return getMatrix(f2, f3, ((float) this.mViewSize.getWidth()) / 2.0f, ((float) this.mViewSize.getHeight()) / 2.0f);
            }
            throw new IllegalArgumentException("Illegal PivotPoint");
        }

        private Matrix getNoScale() {
            return getMatrix(((float) this.mVideoSize.getWidth()) / ((float) this.mViewSize.getWidth()), ((float) this.mVideoSize.getHeight()) / ((float) this.mViewSize.getHeight()), 1);
        }

        private Matrix getOriginalScale(int i) {
            return getMatrix(((float) this.mVideoSize.getWidth()) / ((float) this.mViewSize.getWidth()), ((float) this.mVideoSize.getHeight()) / ((float) this.mViewSize.getHeight()), i);
        }

        public Matrix getScaleMatrix(int i) {
            switch (i) {
                case 1:
                    return getCropScale(2);
                case 2:
                    return getOriginalScale(2);
                case 3:
                    return fitXY();
                case 4:
                    return fitCenter();
                case 5:
                    return getNoScale();
                case 6:
                    return getLeftTopFullScale();
                default:
                    return null;
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScaleType {
    }

    private static class Size {
        private int mHeight;
        private int mWidth;

        public Size(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getWidth() {
            return this.mWidth;
        }
    }

    static {
        sThread.start();
    }

    public TextureVideoView(Context context) {
        super(context);
        init();
    }

    public TextureVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TextureVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void clearSurface() {
        if (this.mSurface != null) {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            egl10.eglInitialize(eglGetDisplay, null);
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            egl10.eglChooseConfig(eglGetDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344, 0, 12344}, eGLConfigArr, eGLConfigArr.length, new int[1]);
            EGLConfig eGLConfig = eGLConfigArr[0];
            EGLContext eglCreateContext = egl10.eglCreateContext(eglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            EGLSurface eglCreateWindowSurface = egl10.eglCreateWindowSurface(eglGetDisplay, eGLConfig, this.mSurface, new int[]{12344});
            egl10.eglMakeCurrent(eglGetDisplay, eglCreateWindowSurface, eglCreateWindowSurface, eglCreateContext);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            egl10.eglSwapBuffers(eglGetDisplay, eglCreateWindowSurface);
            egl10.eglDestroySurface(eglGetDisplay, eglCreateWindowSurface);
            EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
            egl10.eglMakeCurrent(eglGetDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
            egl10.eglDestroyContext(eglGetDisplay, eglCreateContext);
            egl10.eglTerminate(eglGetDisplay);
        }
    }

    private void init() {
        this.mContext = getContext();
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mHandler = new Handler();
        this.mVideoHandler = new Handler(sThread.getLooper(), this);
        setSurfaceTextureListener(this);
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    private void openVideo() {
        if (this.mUri == null || this.mSurface == null || this.mTargetState != 3) {
            String str = TAG;
            Log.e(str, "openVideo error " + this.mUri + " " + this.mSurface + " " + this.mTargetState);
        } else if (this.mUri.getPath() == null || new File(this.mUri.getPath()).exists()) {
            this.mAudioManager = (AudioManager) this.mContext.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
            this.mAudioManager.requestAudioFocus(null, 3, 1);
            release(false);
            try {
                this.mMediaPlayer = new MediaPlayer();
                this.mMediaPlayer.setOnPreparedListener(this);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this);
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnErrorListener(this);
                this.mMediaPlayer.setOnInfoListener(this);
                this.mMediaPlayer.setOnBufferingUpdateListener(this);
                this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
                this.mMediaPlayer.setSurface(this.mSurface);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setLooping(this.mLoop);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
                this.mTargetState = 1;
                MediaExtractor mediaExtractor = new MediaExtractor();
                mediaExtractor.setDataSource(this.mContext, this.mUri, (Map<String, String>) null);
                this.mHasAudio = false;
                for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                    if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith("audio/")) {
                        this.mHasAudio = true;
                        return;
                    }
                }
            } catch (IOException unused) {
                this.mCurrentState = -1;
                this.mTargetState = -1;
                if (this.mMediaPlayerCallback != null) {
                    this.mHandler.post(new Runnable() {
                        /* class com.android.camera.ui.TextureVideoView.AnonymousClass1 */

                        public void run() {
                            if (TextureVideoView.this.mMediaPlayerCallback != null) {
                                TextureVideoView.this.mMediaPlayerCallback.onError(TextureVideoView.this.mMediaPlayer, 1, 0);
                            }
                        }
                    });
                }
            } catch (IllegalArgumentException unused2) {
                this.mCurrentState = -1;
                this.mTargetState = -1;
                if (this.mMediaPlayerCallback != null) {
                    this.mHandler.post(new Runnable() {
                        /* class com.android.camera.ui.TextureVideoView.AnonymousClass2 */

                        public void run() {
                            if (TextureVideoView.this.mMediaPlayerCallback != null) {
                                TextureVideoView.this.mMediaPlayerCallback.onError(TextureVideoView.this.mMediaPlayer, 1, 0);
                            }
                        }
                    });
                }
            }
        } else {
            Log.e(TAG, "openVideo error file not found");
        }
    }

    private void release(boolean z) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.reset();
            this.mMediaPlayer.setSurface(null);
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (z) {
                this.mTargetState = 0;
            }
        }
        if (this.mClearSurface) {
            clearSurface();
        }
    }

    public Surface getPreviewSurface() {
        return this.mSurface;
    }

    public boolean handleMessage(Message message) {
        synchronized (TextureVideoView.class) {
            int i = message.what;
            if (i == 1) {
                this.mTargetState = 3;
                openVideo();
            } else if (i == 2) {
                this.mTargetState = 4;
                if (this.mMediaPlayer != null) {
                    this.mMediaPlayer.pause();
                }
                this.mCurrentState = 4;
            } else if (i == 3) {
                this.mTargetState = 5;
                release(true);
            }
        }
        return true;
    }

    public boolean isHasAudio() {
        return this.mHasAudio;
    }

    public boolean isMute() {
        return this.mSoundMute;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public void mute() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.0f, 0.0f);
            this.mSoundMute = true;
        }
    }

    public void onBufferingUpdate(final MediaPlayer mediaPlayer, final int i) {
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.ui.TextureVideoView.AnonymousClass8 */

                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onBufferingUpdate(mediaPlayer, i);
                    }
                }
            });
        }
    }

    public void onCompletion(final MediaPlayer mediaPlayer) {
        this.mCurrentState = 5;
        this.mTargetState = 5;
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.ui.TextureVideoView.AnonymousClass4 */

                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onCompletion(mediaPlayer);
                    }
                }
            });
        }
    }

    public boolean onError(final MediaPlayer mediaPlayer, final int i, final int i2) {
        this.mCurrentState = -1;
        this.mTargetState = -1;
        if (this.mMediaPlayerCallback == null) {
            return true;
        }
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.ui.TextureVideoView.AnonymousClass5 */

            public void run() {
                if (TextureVideoView.this.mMediaPlayerCallback != null) {
                    TextureVideoView.this.mMediaPlayerCallback.onError(mediaPlayer, i, i2);
                }
            }
        });
        return true;
    }

    public boolean onInfo(final MediaPlayer mediaPlayer, final int i, final int i2) {
        if (this.mMediaPlayerCallback == null) {
            return true;
        }
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.ui.TextureVideoView.AnonymousClass9 */

            public void run() {
                if (TextureVideoView.this.mMediaPlayerCallback != null) {
                    TextureVideoView.this.mMediaPlayerCallback.onInfo(mediaPlayer, i, i2);
                }
            }
        });
        return true;
    }

    public void onPrepared(final MediaPlayer mediaPlayer) {
        if (this.mTargetState == 1 && this.mCurrentState == 1) {
            this.mCurrentState = 2;
            if (isInPlaybackState()) {
                this.mMediaPlayer.start();
                this.mCurrentState = 3;
                this.mTargetState = 3;
            }
            if (this.mMediaPlayerCallback != null) {
                this.mHandler.post(new Runnable() {
                    /* class com.android.camera.ui.TextureVideoView.AnonymousClass6 */

                    public void run() {
                        if (TextureVideoView.this.mMediaPlayerCallback != null) {
                            TextureVideoView.this.mMediaPlayerCallback.onPrepared(mediaPlayer);
                        }
                    }
                });
            }
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurface = new Surface(surfaceTexture);
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.ui.TextureVideoView.AnonymousClass3 */

                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onSurfaceReady(TextureVideoView.this.mSurface);
                    }
                }
            });
        }
        if (this.mTargetState == 3) {
            start();
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurface = null;
        stop();
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, final int i, final int i2) {
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.ui.TextureVideoView.AnonymousClass7 */

                public void run() {
                    int i;
                    int i2 = i;
                    if (i2 != 0 && (i = i2) != 0) {
                        TextureVideoView.this.setVideoSpecifiedSize(i2, i);
                    }
                }
            });
        }
    }

    public void pause() {
        if (isPlaying()) {
            this.mVideoHandler.obtainMessage(2).sendToTarget();
        }
    }

    public void resume() {
        if (!isPlaying()) {
            this.mVideoHandler.obtainMessage(1).sendToTarget();
        }
    }

    public void setClearSurface(boolean z) {
        this.mClearSurface = z;
    }

    public void setLoop(boolean z) {
        this.mLoop = z;
    }

    public void setMediaPlayerCallback(MediaPlayerCallback mediaPlayerCallback) {
        this.mMediaPlayerCallback = mediaPlayerCallback;
        if (mediaPlayerCallback == null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void setScaleType(int i) {
        this.mScaleType = i;
    }

    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    public void setVideoSpecifiedSize(int i, int i2) {
        Matrix scaleMatrix = new ScaleManager(new Size(getWidth(), getHeight()), new Size(i, i2)).getScaleMatrix(this.mScaleType);
        if (scaleMatrix != null) {
            setTransform(scaleMatrix);
        }
        MediaPlayerCallback mediaPlayerCallback = this.mMediaPlayerCallback;
        if (mediaPlayerCallback != null) {
            mediaPlayerCallback.onVideoSizeChanged(this.mMediaPlayer, i, i2);
        }
    }

    public void setVideoURI(Uri uri) {
        this.mUri = uri;
    }

    public void start() {
        start(0);
    }

    public void start(long j) {
        if (isInPlaybackState()) {
            this.mVideoHandler.obtainMessage(3).sendToTarget();
        }
        if (this.mUri == null || this.mSurface == null) {
            this.mTargetState = 3;
        } else {
            this.mVideoHandler.sendEmptyMessageDelayed(1, j);
        }
    }

    public void stop() {
        if (isInPlaybackState()) {
            this.mVideoHandler.removeMessages(1);
            this.mVideoHandler.obtainMessage(3).sendToTarget();
        }
    }

    public void unMute() {
        if (this.mAudioManager != null && this.mMediaPlayer != null) {
            float log = (float) (1.0d - (0.0d / Math.log((double) 100)));
            this.mMediaPlayer.setVolume(log, log);
            this.mSoundMute = false;
        }
    }
}
