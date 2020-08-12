package com.android.camera.module.encoder;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.view.Surface;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.MediaEncoder;
import java.io.IOException;
import java.util.Locale;

public class MediaVideoEncoder extends MediaEncoder {
    private static final float BPP = 0.25f;
    private static final int FRAME_RATE = 25;
    private static final String MIME_TYPE = "video/avc";
    private static final String TAG = "MediaVideoEncoder";
    protected static int[] sRecognizedFormats = {2130708361};
    private int mExtraFrames;
    private RenderHandler mRenderHandler;
    private long mRequestStopTime;
    private EGLContext mSharedEGLContext;
    private Surface mSurface;
    private final int mVideoHeight;
    private final int mVideoWidth;

    public MediaVideoEncoder(EGLContext eGLContext, MediaMuxerWrapper mediaMuxerWrapper, MediaEncoder.MediaEncoderListener mediaEncoderListener, int i, int i2) {
        super(mediaMuxerWrapper, mediaEncoderListener);
        Log.d(TAG, String.format(Locale.ENGLISH, "init: videoSize=%dx%d", Integer.valueOf(i), Integer.valueOf(i2)));
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        this.mRenderHandler = RenderHandler.createHandler(TAG, i, i2);
        this.mSharedEGLContext = eGLContext;
    }

    private int calcBitRate() {
        int i = (int) (((float) this.mVideoWidth) * 6.25f * ((float) this.mVideoHeight));
        Log.d(TAG, String.format(Locale.ENGLISH, "bitrate=%d", Integer.valueOf(i)));
        return i;
    }

    private static boolean isRecognizedVideoFormat(int i) {
        int[] iArr = sRecognizedFormats;
        int length = iArr != null ? iArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (sRecognizedFormats[i2] == i) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    protected static final int selectColorFormat(MediaCodecInfo mediaCodecInfo, String str) {
        Log.v(TAG, "selectColorFormat>>>codec:" + mediaCodecInfo.getName() + "/" + str);
        try {
            Thread.currentThread().setPriority(10);
            MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
            Thread.currentThread().setPriority(5);
            int i = 0;
            int i2 = 0;
            while (true) {
                int[] iArr = capabilitiesForType.colorFormats;
                if (i2 >= iArr.length) {
                    break;
                }
                int i3 = iArr[i2];
                if (isRecognizedVideoFormat(i3)) {
                    i = i3;
                    break;
                }
                i2++;
            }
            Log.v(TAG, "selectColorFormat<<<colorFormat=" + Integer.toHexString(i));
            return i;
        } catch (Throwable th) {
            Thread.currentThread().setPriority(5);
            throw th;
        }
    }

    protected static final MediaCodecInfo selectVideoCodec(String str) {
        String[] supportedTypes;
        Log.v(TAG, "selectVideoCodec>>>" + str);
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str) && selectColorFormat(codecInfoAt, str) > 0) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        Log.v(TAG, "selectVideoCodec<<<");
        return null;
    }

    public boolean frameAvailableSoon(DrawExtTexAttribute drawExtTexAttribute) {
        boolean frameAvailableSoon = super.frameAvailableSoon();
        if (frameAvailableSoon && !((MediaEncoder) this).mSkipFrame) {
            this.mRenderHandler.draw(drawExtTexAttribute);
            if (this.mRequestStopTime > 0) {
                this.mExtraFrames--;
                if (this.mExtraFrames <= 0) {
                    super.stopRecording();
                }
            }
        }
        return frameAvailableSoon;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public void prepare() throws IOException {
        Log.v(TAG, "prepare>>>");
        ((MediaEncoder) this).mTrackIndex = -1;
        ((MediaEncoder) this).mMuxerStarted = false;
        ((MediaEncoder) this).mIsEOS = false;
        MediaCodecInfo selectVideoCodec = selectVideoCodec(MIME_TYPE);
        if (selectVideoCodec == null) {
            Log.e(TAG, "no appropriate codec for video/avc");
            return;
        }
        String str = TAG;
        Log.d(str, "selected codec: " + selectVideoCodec.getName());
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(MIME_TYPE, this.mVideoWidth, this.mVideoHeight);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", calcBitRate());
        createVideoFormat.setInteger("frame-rate", 25);
        createVideoFormat.setFloat("i-frame-interval", 1.0f);
        String str2 = TAG;
        Log.d(str2, "format: " + createVideoFormat);
        ((MediaEncoder) this).mMediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
        ((MediaEncoder) this).mMediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mSurface = ((MediaEncoder) this).mMediaCodec.createInputSurface();
        this.mRenderHandler.setEglContext(this.mSharedEGLContext, this.mSurface, true);
        ((MediaEncoder) this).mMediaCodec.start();
        MediaEncoder.MediaEncoderListener mediaEncoderListener = ((MediaEncoder) this).mListener;
        if (mediaEncoderListener != null) {
            mediaEncoderListener.onPrepared(this);
        }
        Log.v(TAG, "prepare<<<");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public void release() {
        Log.d(TAG, "release");
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
            this.mSurface = null;
        }
        RenderHandler renderHandler = this.mRenderHandler;
        if (renderHandler != null) {
            renderHandler.release();
            this.mSharedEGLContext = null;
            this.mRenderHandler = null;
        }
        super.release();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public void signalEndOfInputStream() {
        Log.d(TAG, "signalEndOfInputStream");
        ((MediaEncoder) this).mMediaCodec.signalEndOfInputStream();
        ((MediaEncoder) this).mIsEOS = true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public boolean startRecording(long j) {
        boolean startRecording = super.startRecording(j);
        this.mRequestStopTime = -1;
        this.mExtraFrames = 0;
        return startRecording;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public void stopRecording() {
        super.stopRecording();
    }
}
