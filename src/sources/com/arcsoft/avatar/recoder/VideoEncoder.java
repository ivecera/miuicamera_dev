package com.arcsoft.avatar.recoder;

import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.view.Surface;
import com.android.camera.storage.Storage;
import com.arcsoft.avatar.gl.EGLWrapper;
import com.arcsoft.avatar.gl.GLFramebuffer;
import com.arcsoft.avatar.gl.GLRender;
import com.arcsoft.avatar.util.CodecLog;
import com.arcsoft.avatar.util.NotifyMessage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.locks.ReentrantLock;

public class VideoEncoder extends BaseEncoder {
    private static String E = "video/hevc";
    public static final String ENCODER_THREAD_NAME = "Arc_Video_Encoder";
    public static final String NAME = "ARC_V";
    private static final String u = "Arc_VideoEncoder";
    private static final long v = 1000000000;
    private static final int w = 10000000;
    private static final int x = 30;
    private static final int y = 10;
    /* access modifiers changed from: private */
    public int A;
    /* access modifiers changed from: private */
    public int B;
    private boolean C;
    private int D;
    private Surface F;
    private Thread G;
    /* access modifiers changed from: private */
    public EGLWrapper H;
    private EGLContext I = EGL14.EGL_NO_CONTEXT;
    /* access modifiers changed from: private */
    public GLRender J;
    private int K;
    protected long t;
    private MediaFormat z;

    public class SaveThread extends Thread {

        /* renamed from: b  reason: collision with root package name */
        private ByteBuffer f168b;

        public SaveThread(ByteBuffer byteBuffer) {
            this.f168b = byteBuffer;
        }

        public void run() {
            super.run();
            Bitmap createBitmap = Bitmap.createBitmap(VideoEncoder.this.A, VideoEncoder.this.B, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.f168b);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/Pictures/_" + System.currentTimeMillis() + Storage.JPEG_SUFFIX);
                createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                createBitmap.recycle();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public VideoEncoder(MuxerWrapper muxerWrapper, int i, int i2, Object obj, RecordingListener recordingListener, EGLContext eGLContext, int i3, String str) {
        super(muxerWrapper, obj, recordingListener);
        this.A = i;
        this.B = i2;
        this.G = null;
        this.K = i3;
        this.I = eGLContext;
        E = str;
        prepare(true);
        b();
        ((BaseEncoder) this).q = new ReentrantLock();
        ((BaseEncoder) this).r = ((BaseEncoder) this).q.newCondition();
        CodecLog.d(u, "VideoEncoder constructor mCustomerBitRate = " + this.K);
        CodecLog.d(u, "VideoEncoder constructor mWidth = " + i + " ,mHeight = " + i2);
    }

    private void a(boolean z2) {
        CodecLog.d(u, "initVideoEncoder()->in");
        this.z = MediaFormat.createVideoFormat(E, this.A, this.B);
        this.z.setInteger("color-format", 2130708361);
        this.z.setInteger("bitrate", this.K);
        this.z.setInteger("frame-rate", 30);
        this.z.setInteger("i-frame-interval", 10);
        try {
            ((BaseEncoder) this).i = MediaCodec.createEncoderByType(E);
            CodecLog.i(u, "initVideoEncoder(): selected_codec_name = " + ((BaseEncoder) this).i.getName());
        } catch (IOException e2) {
            CodecLog.e(u, "initVideoEncoder()->createEncoderByType failed.");
            e2.printStackTrace();
            RecordingListener recordingListener = ((BaseEncoder) this).o;
            if (recordingListener != null) {
                recordingListener.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CREATE, 0);
            }
        }
        try {
            ((BaseEncoder) this).i.configure(this.z, (Surface) null, (MediaCrypto) null, 1);
        } catch (Exception e3) {
            CodecLog.e(u, "initVideoEncoder()->configure failed.");
            e3.printStackTrace();
            RecordingListener recordingListener2 = ((BaseEncoder) this).o;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE, 0);
            }
        }
        if (z2) {
            try {
                this.F = ((BaseEncoder) this).i.createInputSurface();
            } catch (Exception e4) {
                CodecLog.e(u, "initVideoEncoder()->createInputSurface failed.");
                e4.printStackTrace();
                RecordingListener recordingListener3 = ((BaseEncoder) this).o;
                if (recordingListener3 != null) {
                    recordingListener3.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE, 0);
                }
            }
        } else {
            this.F = null;
        }
        CodecLog.d(u, "initVideoEncoder()->out");
    }

    private void b() {
        this.H = new EGLWrapper(getInputSurface(), this.I);
    }

    /* access modifiers changed from: private */
    public void c() {
        this.J = new GLRender(this.A, this.B, this.D, true);
        this.J.initRender(false);
        CodecLog.d(u, "VideoEncoder initGL glError = " + GLES20.glGetError());
    }

    /* access modifiers changed from: private */
    public void d() {
        this.J.unInitRender();
        this.J = null;
    }

    private void e() {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.A * this.B * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, this.A, this.B, 6408, 5121, allocateDirect);
        new SaveThread(allocateDirect).start();
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public Surface getInputSurface() {
        return ((BaseEncoder) this).i != null ? this.F : super.getInputSurface();
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void notifyNewFrameAvailable() {
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void pauseRecording() {
        if (!((BaseEncoder) this).f143e) {
            ((BaseEncoder) this).f143e = true;
            this.t = System.nanoTime();
        }
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void prepare(boolean z2) {
        a(z2);
        if (((BaseEncoder) this).i == null) {
            throw new RuntimeException("Init video encoder is failed.");
        }
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void release(boolean z2) {
        try {
            ((BaseEncoder) this).q.lock();
        } catch (Exception e2) {
            CodecLog.e(u, "release()-> meet error when get lock : " + e2.getMessage());
        } catch (Throwable th) {
            sinalCondition();
            ((BaseEncoder) this).q.unlock();
            throw th;
        }
        sinalCondition();
        ((BaseEncoder) this).q.unlock();
        Thread thread = this.G;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e3) {
                CodecLog.d(u, "Encoder Thread has been Interrupted, errors may be occurred.");
                e3.printStackTrace();
            } catch (Throwable th2) {
                this.G = null;
                throw th2;
            }
            this.G = null;
        }
        EGLWrapper eGLWrapper = this.H;
        if (eGLWrapper != null) {
            eGLWrapper.release();
            this.H = null;
        }
        this.I = EGL14.EGL_NO_CONTEXT;
        CodecLog.d(u, "VideoEncoder release() encoder thread exit. threadName =" + ENCODER_THREAD_NAME);
        this.F = null;
        ((BaseEncoder) this).q = null;
        ((BaseEncoder) this).r = null;
        ((BaseEncoder) this).s = null;
        super.release(z2);
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void resumeRecording() {
        if (((BaseEncoder) this).f143e) {
            ((BaseEncoder) this).f143e = false;
            ((BaseEncoder) this).g += System.nanoTime() - this.t;
            ((BaseEncoder) this).n.add(Long.valueOf(((BaseEncoder) this).g));
        }
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void startRecording() {
        if (this.G == null) {
            super.startRecording();
            this.G = new Thread(ENCODER_THREAD_NAME) {
                /* class com.arcsoft.avatar.recoder.VideoEncoder.AnonymousClass1 */

                public void run() {
                    super.run();
                    setName(VideoEncoder.NAME);
                    try {
                        ((BaseEncoder) VideoEncoder.this).i.start();
                        VideoEncoder.this.H.makeCurrent();
                        VideoEncoder.this.c();
                        while (true) {
                            VideoEncoder videoEncoder = VideoEncoder.this;
                            if (!((BaseEncoder) videoEncoder).f142d) {
                                FrameItem frameItem = null;
                                try {
                                    videoEncoder.lock();
                                    while (((BaseEncoder) VideoEncoder.this).s.queueSize() == 0 && !((BaseEncoder) VideoEncoder.this).f142d) {
                                        try {
                                            ((BaseEncoder) VideoEncoder.this).r.await();
                                        } catch (InterruptedException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    frameItem = ((BaseEncoder) VideoEncoder.this).s.getFrameForConsumer();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                } catch (Throwable th) {
                                    VideoEncoder.this.unLock();
                                    throw th;
                                }
                                VideoEncoder.this.unLock();
                                if (frameItem != null) {
                                    GLFramebuffer gLFramebuffer = frameItem.mFramebuffer;
                                    VideoEncoder.this.drain();
                                    CodecLog.d(VideoEncoder.u, "VideoEncoder frame_item_index = " + frameItem.mFrameIndex);
                                    long j = frameItem.f145a;
                                    if (0 != j) {
                                        GLES30.glWaitSync(j, 0, -1);
                                    }
                                    VideoEncoder.this.J.renderWithTextureId(gLFramebuffer.getTextureId());
                                    try {
                                        VideoEncoder.this.lock();
                                        ((BaseEncoder) VideoEncoder.this).s.addEmptyFrameForConsumer();
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                        CodecLog.e(VideoEncoder.u, "VideoEncoder meet exception when add item : " + e4.getMessage());
                                    } catch (Throwable th2) {
                                        VideoEncoder.this.unLock();
                                        throw th2;
                                    }
                                    VideoEncoder.this.unLock();
                                    VideoEncoder.this.H.swapBuffers();
                                }
                            } else {
                                ((BaseEncoder) videoEncoder).f139a = true;
                                ((BaseEncoder) videoEncoder).i.signalEndOfInputStream();
                                VideoEncoder.this.drain();
                                VideoEncoder.this.d();
                                VideoEncoder.this.H.makeUnCurrent();
                                return;
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        RecordingListener recordingListener = ((BaseEncoder) VideoEncoder.this).o;
                        if (recordingListener != null) {
                            recordingListener.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_START, 0);
                        }
                    }
                }
            };
            this.G.start();
            CodecLog.d(u, "VideoEncoder is started.");
            return;
        }
        throw new RuntimeException("Video encoder thread has been started already, can not start twice.");
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void stopRecording() {
        super.stopRecording();
        try {
            ((BaseEncoder) this).q.lock();
        } catch (Exception e2) {
            CodecLog.e(u, "stopRecording()-> meet error when get lock : " + e2.getMessage());
        } catch (Throwable th) {
            sinalCondition();
            ((BaseEncoder) this).q.unlock();
            throw th;
        }
        sinalCondition();
        ((BaseEncoder) this).q.unlock();
    }
}
