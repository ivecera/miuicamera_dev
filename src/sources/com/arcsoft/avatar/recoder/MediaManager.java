package com.arcsoft.avatar.recoder;

import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.support.annotation.NonNull;
import com.android.camera.module.impl.component.FileUtils;
import com.arcsoft.avatar.gl.GLRender;
import com.arcsoft.avatar.util.CodecLog;
import com.arcsoft.avatar.util.NotifyMessage;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MediaManager implements RecordingListener {
    public static final int MUXER_AUDIO_ENCODER = 1;
    public static final int MUXER_VIDEO_AND_AUDIO_ENCODER = 2;
    public static final int MUXER_VIDEO_ENCODER = 1;

    /* renamed from: a  reason: collision with root package name */
    private static final String f152a = "Arc_VideoEncoder";
    private static final int r = 2;
    /* access modifiers changed from: private */

    /* renamed from: b  reason: collision with root package name */
    public int f153b;
    /* access modifiers changed from: private */

    /* renamed from: c  reason: collision with root package name */
    public int f154c;

    /* renamed from: d  reason: collision with root package name */
    private int f155d;

    /* renamed from: e  reason: collision with root package name */
    private boolean f156e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f157f;
    private String g;
    private BaseEncoder h;
    private BaseEncoder i;
    private MuxerWrapper j;
    private boolean k;
    private GLRender l;
    private int m;
    private int n;
    private Object o;
    private RecordingListener p;
    private FrameQueue q;
    private int s;
    private int t;
    private int[] u;

    public class SaveThread extends Thread {

        /* renamed from: b  reason: collision with root package name */
        private ByteBuffer f159b;

        public SaveThread(ByteBuffer byteBuffer) {
            this.f159b = byteBuffer;
        }

        public void run() {
            super.run();
            Bitmap createBitmap = Bitmap.createBitmap(MediaManager.this.f153b, MediaManager.this.f154c, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.f159b);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/Pictures/_" + System.currentTimeMillis() + FileUtils.FILTER_FILE_SUFFIX);
                createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                createBitmap.recycle();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public MediaManager(@NonNull FileDescriptor fileDescriptor, int i2, int i3, int i4, boolean z, int i5, RecordingListener recordingListener) {
        this.p = recordingListener;
        this.f153b = i2;
        this.f154c = i3;
        if (90 == i4 || 270 == i4) {
            int i6 = this.f153b;
            int i7 = this.f154c;
            this.f153b = i6 ^ i7;
            int i8 = this.f153b;
            this.f154c = i7 ^ i8;
            this.f153b = i8 ^ this.f154c;
        }
        this.f155d = i4;
        this.f156e = z;
        this.m = 0;
        this.n = 0;
        this.j = new MuxerWrapper(fileDescriptor, i5, this);
        EGLDisplay eglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
        EGLSurface eglGetCurrentSurface = EGL14.eglGetCurrentSurface(12378);
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12375, iArr, 0);
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12374, iArr2, 0);
        this.s = iArr[0];
        this.t = iArr2[0];
        this.o = new Object();
        CodecLog.d(f152a, "MediaManager constructor mFrameWidth = " + i2 + " ,mFrameHeight = " + i3);
    }

    public MediaManager(@NonNull String str, int i2, int i3, int i4, boolean z, int i5, RecordingListener recordingListener) {
        this.p = recordingListener;
        this.f153b = i2;
        this.f154c = i3;
        if (90 == i4 || 270 == i4) {
            int i6 = this.f153b;
            int i7 = this.f154c;
            this.f153b = i6 ^ i7;
            int i8 = this.f153b;
            this.f154c = i7 ^ i8;
            this.f153b = i8 ^ this.f154c;
        }
        this.f155d = i4;
        this.f156e = z;
        this.m = 0;
        this.n = 0;
        this.j = new MuxerWrapper(str, i5, this);
        EGLDisplay eglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
        EGLSurface eglGetCurrentSurface = EGL14.eglGetCurrentSurface(12378);
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12375, iArr, 0);
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12374, iArr2, 0);
        this.s = iArr[0];
        this.t = iArr2[0];
        this.o = new Object();
        CodecLog.d(f152a, "MediaManager constructor mFrameWidth = " + i2 + " ,mFrameHeight = " + i3);
    }

    private void a() {
        int i2 = this.m;
        int i3 = this.n;
        if (i2 == i3) {
            this.f157f = true;
        } else if (i3 >= 3) {
            throw new RuntimeException("Init encoder count great than need. need=" + this.m + " ,but got=" + this.n);
        }
    }

    private void b() {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.f153b * this.f154c * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, this.f153b, this.f154c, 6408, 5121, allocateDirect);
        CodecLog.d(f152a, "glReadPixels() glError = " + GLES20.glGetError());
        new SaveThread(allocateDirect).start();
    }

    /* JADX INFO: finally extract failed */
    public void drawSurfaceWithTextureId(int i2) {
        boolean z;
        if (!this.f157f) {
            CodecLog.e(f152a, "drawSurfaceWithTextureId()-> MediaManager has not been initialized.");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("textureId must >0 , your textureId=" + i2);
        } else if (this.l != null) {
            FrameItem frameItem = null;
            try {
                this.h.lock();
                if (this.q.isIsInited()) {
                    frameItem = this.q.getFrameForProducer();
                    if (frameItem != null) {
                        if (frameItem.mIsInited) {
                            z = true;
                        }
                    }
                    CodecLog.d(f152a, "drawSurfaceWithTextureId()-> get a null frame item.");
                    this.h.unLock();
                    return;
                }
                z = false;
                this.h.unLock();
            } catch (Exception e2) {
                e2.printStackTrace();
                this.h.unLock();
                z = false;
            } catch (Throwable th) {
                this.h.unLock();
                throw th;
            }
            if (z) {
                if (this.u == null) {
                    this.u = new int[4];
                    GLES30.glGetIntegerv(2978, this.u, 0);
                }
                this.q.deleteSync(frameItem);
                frameItem.mFramebuffer.bind(false, false);
                GLES20.glViewport(0, 0, this.f153b, this.f154c);
                this.l.renderWithTextureId(i2);
                frameItem.f145a = GLES30.glFenceSync(37143, 0);
                frameItem.mFramebuffer.unBind(false, false);
                frameItem.mIsEmpty = false;
                int[] iArr = this.u;
                GLES20.glViewport(iArr[0], iArr[1], iArr[2], iArr[3]);
                try {
                    this.h.lock();
                    this.q.addFrameForProducer();
                } catch (Exception e3) {
                    e3.printStackTrace();
                } catch (Throwable th2) {
                    this.h.sinalCondition();
                    this.h.unLock();
                    throw th2;
                }
                this.h.sinalCondition();
                this.h.unLock();
            }
        } else {
            throw new RuntimeException("Could not call drawSurfaceWithTextureId() in with a null GLRender.");
        }
    }

    public long getMuxerSizeRecorded() {
        MuxerWrapper muxerWrapper = this.j;
        if (muxerWrapper == null) {
            return 0;
        }
        return muxerWrapper.getSizeRecordFile();
    }

    public long getMuxerTimeElapsed() {
        MuxerWrapper muxerWrapper = this.j;
        if (muxerWrapper == null) {
            return 0;
        }
        return muxerWrapper.getTimeElapse();
    }

    public void initAudioEncoder() {
        this.i = new AudioEncoder(this.j, this.o, this);
        this.i.prepare(false);
        this.n++;
        a();
    }

    public void initVideoEncoder(String str) {
        CodecLog.e(f152a, "MediaManager initVideoEncoder in");
        this.h = new VideoEncoder(this.j, this.f153b, this.f154c, this.o, this, EGL14.EGL_NO_CONTEXT, 10000000, str);
        this.h.prepare(false);
        this.f157f = true;
        this.n++;
        a();
        CodecLog.e(f152a, "MediaManager initVideoEncoder out mInitedEncoderCount = " + this.n);
    }

    public void initVideoEncoderWithSharedContext(EGLContext eGLContext, int i2, boolean z, String str) {
        this.h = new VideoEncoder(this.j, this.f153b, this.f154c, this.o, this, eGLContext, i2, str);
        this.k = true;
        if (this.k) {
            if (this.h.getInputSurface() != null) {
                this.l = new GLRender(this.f153b, this.f154c, this.f155d, this.f156e);
                this.l.initRender(z);
            } else {
                RecordingListener recordingListener = this.p;
                if (recordingListener != null) {
                    recordingListener.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE, 0);
                }
            }
        }
        this.q = new FrameQueue();
        this.q.init(2, this.f153b, this.f154c, false);
        this.h.setFrameQueue(this.q);
        this.n++;
        a();
    }

    @Override // com.arcsoft.avatar.recoder.RecordingListener
    public void onRecordingListener(int i2, Object obj) {
        CodecLog.d(f152a, "onRecordingListener()->in msg = " + i2 + " ,value = " + ((Integer) obj));
        int i3 = NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER;
        switch (i2) {
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_CREATE:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_CONFIGURE:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_START:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_STOP:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_RELEASE:
                i3 = 544;
                break;
            default:
                switch (i2) {
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CREATE:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_START:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_STOP:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_RELEASE:
                        i3 = 560;
                        break;
                    default:
                        switch (i2) {
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_CREATE:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_ADD_TRACK:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_START:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_WRITE_SAMPLE_DATA:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_STOP:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_RELEASE:
                                break;
                            default:
                                switch (i2) {
                                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_AUDIO_RECORD_CREATE:
                                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_AUDIO_RECORD_START_RECORDING:
                                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_AUDIO_RECORD_STOP:
                                        i3 = 624;
                                        break;
                                    default:
                                        i3 = i2;
                                        break;
                                }
                        }
                }
        }
        RecordingListener recordingListener = this.p;
        if (recordingListener != null) {
            recordingListener.onRecordingListener(i3, obj);
        }
        CodecLog.d(f152a, "onRecordingListener()->out");
    }

    public int pauseRecording() {
        BaseEncoder baseEncoder = this.i;
        if (baseEncoder != null) {
            baseEncoder.pauseRecording();
        }
        BaseEncoder baseEncoder2 = this.h;
        if (baseEncoder2 == null) {
            return 0;
        }
        baseEncoder2.pauseRecording();
        return 0;
    }

    public int resumeRecording() {
        synchronized (this.o) {
            if (this.i != null) {
                this.i.resumeRecording();
            }
            if (this.h != null) {
                this.h.resumeRecording();
            }
            this.o.notifyAll();
        }
        return 0;
    }

    public void setEncoderCount(int i2) {
        MuxerWrapper muxerWrapper = this.j;
        if (muxerWrapper != null) {
            muxerWrapper.setEncoderCount(i2);
        }
        this.m = i2;
    }

    public void startRecording() {
        if (!this.f157f || this.j == null) {
            throw new RuntimeException("Unit Encoder or Muxer is null.");
        }
        BaseEncoder baseEncoder = this.h;
        if (baseEncoder != null) {
            baseEncoder.startRecording();
        } else {
            CodecLog.i(f152a, "startRecording()-> VideoEncoder is null. maxEncoderCount=" + this.m);
        }
        BaseEncoder baseEncoder2 = this.i;
        if (baseEncoder2 != null) {
            baseEncoder2.startRecording();
            return;
        }
        CodecLog.i(f152a, "startRecording()-> AudioEncoder is null. maxEncoderCount=" + this.m);
    }

    public void stopRecording() {
        synchronized (this.o) {
            this.o.notifyAll();
        }
        BaseEncoder baseEncoder = this.h;
        if (baseEncoder != null) {
            baseEncoder.stopRecording();
            this.h.release(true);
            this.h = null;
        }
        BaseEncoder baseEncoder2 = this.i;
        if (baseEncoder2 != null) {
            baseEncoder2.stopRecording();
            this.i.release(false);
            this.i = null;
        }
        GLRender gLRender = this.l;
        if (gLRender != null) {
            gLRender.unInitRender();
            this.l = null;
        }
        FrameQueue frameQueue = this.q;
        if (frameQueue != null) {
            frameQueue.unInit();
            this.q = null;
        }
        this.j = null;
        this.o = null;
        this.u = null;
    }
}
