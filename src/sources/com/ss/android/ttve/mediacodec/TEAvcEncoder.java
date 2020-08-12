package com.ss.android.ttve.mediacodec;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.math.MathUtils;
import android.util.Log;
import android.view.Surface;
import com.android.camera.Util;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.ss.android.ttve.common.TEEglStateSaver;
import com.ss.android.ttve.common.TESharedContext;
import com.ss.android.ttve.common.TETextureDrawer;
import com.ss.android.vesdk.VELogUtil;
import com.ss.android.vesdk.VEResult;
import com.xiaomi.stat.MiStat;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Keep
public class TEAvcEncoder {
    private static boolean DEBUG = false;
    public static boolean FORCE_FAILED = false;
    private static final int MASK_BIT_RATE = 1;
    private static final int MASK_FORCE_RESTART = 4;
    private static final int MASK_FRAME_RATE = 2;
    private static int MAX_FRAME_RATE = 2000;
    private static int MIN_FRAME_RATE = 7;
    private static final String TAG = "TEAvcEncoder";
    private static final int TIMEOUT_USEC = 0;
    private static final int TIMEOUT_USEC_EOS = 10000;
    private static final String VIDEO_MIME_TYPE = "video/avc";
    private static int avcqueuesize = 25;
    public ArrayBlockingQueue<CodecData> AVCQueue = new ArrayBlockingQueue<>(avcqueuesize);
    public byte[] configByte = null;
    private int mBufferIndex = -1;
    private boolean mEndOfStream = false;
    private boolean mFirstFrame = true;
    private Queue<Long> m_PTSQueue = new LinkedList();
    private boolean m_bNeedSingalEnd = false;
    private boolean m_bSignalEndOfStream = false;
    private boolean m_bSuccessInit = false;
    private int m_bitRate = 0;
    private MediaFormat m_codecFormat = null;
    private int m_colorFormat = 0;
    private int m_configStatus = 0;
    private TEEglStateSaver m_eglStateSaver;
    private long m_encodeStartTime = -1;
    private int m_frameRate = 0;
    private long m_getnerateIndex = 0;
    private int m_height = 0;
    private int m_iFrameInternal = 0;
    private boolean m_isNeedReconfigure = false;
    private CodecData m_lastCodecData = null;
    private int m_level = 0;
    private int m_maxBitRate = 0;
    private MediaCodec m_mediaCodec = null;
    private int m_profile = 0;
    private TESharedContext m_sharedContext;
    private Surface m_surface = null;
    private TETextureDrawer m_textureDrawer;
    private boolean m_useInputSurface = true;
    private int m_width = 0;
    private byte[] pps;
    private byte[] sps;

    public static class CodecData {
        public byte[] data = null;
        public long dts = 0;
        public int flag;
        public long pts = 0;
    }

    private void addOutputData(byte[] bArr, MediaCodec.BufferInfo bufferInfo) {
        VELogUtil.d(TAG, "encode: pts queue size = " + this.m_PTSQueue.size());
        if (this.m_PTSQueue.size() <= 0 && this.m_profile >= 8) {
            VELogUtil.w(TAG, "encode: no available pts!!! profile " + this.m_profile);
        } else if (this.m_PTSQueue.size() <= 0) {
            VELogUtil.w(TAG, "encode: no available pts!!!");
        } else {
            long longValue = this.m_PTSQueue.poll().longValue();
            long j = bufferInfo.presentationTimeUs;
            if (j <= 0) {
                j = 0;
            }
            long j2 = this.m_profile >= 8 ? longValue - 200000 : j;
            VELogUtil.d(TAG, "dts = " + j2 + ", pts = " + j);
            CodecData codecData = new CodecData();
            codecData.data = bArr;
            codecData.pts = j;
            codecData.dts = j2;
            codecData.flag = bufferInfo.flags;
            try {
                this.AVCQueue.add(codecData);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @TargetApi(18)
    private int configEncode() {
        try {
            if (this.m_surface != null) {
                this.m_surface.release();
                this.m_surface = null;
            }
            if (this.m_mediaCodec != null) {
                this.m_mediaCodec.release();
            }
            this.m_mediaCodec = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE);
            MediaCodecInfo codecInfo = this.m_mediaCodec.getCodecInfo();
            if (codecInfo.getName().startsWith("OMX.google.")) {
                return -2;
            }
            if (reconfigureMediaFormat(codecInfo) != 0) {
                return -3;
            }
            this.m_mediaCodec.configure(this.m_codecFormat, (Surface) null, (MediaCrypto) null, 1);
            if (!this.m_useInputSurface) {
                return 0;
            }
            VELogUtil.d(TAG, "m_mediaCodec.createInputSurface()");
            this.m_surface = this.m_mediaCodec.createInputSurface();
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -4;
        }
    }

    public static TEAvcEncoder createEncoderObject() {
        return new TEAvcEncoder();
    }

    @SuppressLint({"WrongConstant"})
    @TargetApi(16)
    private int drainOutputBuffer(long j) {
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        this.mBufferIndex = -1;
        try {
            this.mBufferIndex = this.m_mediaCodec.dequeueOutputBuffer(bufferInfo, j);
            while (true) {
                int i = this.mBufferIndex;
                if (i < 0) {
                    break;
                }
                ByteBuffer outputBufferByIdx = getOutputBufferByIdx(i);
                byte[] bArr = new byte[bufferInfo.size];
                outputBufferByIdx.position(bufferInfo.offset);
                outputBufferByIdx.limit(bufferInfo.offset + bufferInfo.size);
                outputBufferByIdx.get(bArr);
                int i2 = bufferInfo.flags;
                if (i2 == 2) {
                    this.configByte = bArr;
                } else if (i2 == 1) {
                    byte[] bArr2 = this.configByte;
                    if (bArr2 == null) {
                        Log.e(TAG, "I can't find configByte!!!! NEED extract from I frame!!!");
                    } else if (bArr[4] == bArr2[4] && (bArr[bArr2.length + 4] & 31) == 5) {
                        byte[] bArr3 = new byte[(bArr.length - bArr2.length)];
                        System.arraycopy(bArr, bArr2.length, bArr3, 0, bArr3.length);
                        bArr = bArr3;
                    }
                    addOutputData(bArr, bufferInfo);
                } else if (i2 == 4) {
                    this.mEndOfStream = true;
                    break;
                } else {
                    addOutputData(bArr, bufferInfo);
                }
                try {
                    this.m_mediaCodec.releaseOutputBuffer(this.mBufferIndex, false);
                    this.mBufferIndex = this.m_mediaCodec.dequeueOutputBuffer(bufferInfo, j);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return VEResult.TER_MEDIA_CODEC_DEQUEUE_OUTPUT_BUFFER_EXCEPTION;
                }
            }
            if (this.mBufferIndex == -2) {
                MediaFormat outputFormat = this.m_mediaCodec.getOutputFormat();
                ByteBuffer byteBuffer = outputFormat.getByteBuffer("csd-0");
                ByteBuffer byteBuffer2 = outputFormat.getByteBuffer("csd-1");
                if (!(byteBuffer == null || byteBuffer2 == null)) {
                    this.sps = (byte[]) byteBuffer.array().clone();
                    this.pps = (byte[]) byteBuffer2.array().clone();
                    byte[] bArr4 = this.sps;
                    this.configByte = new byte[(bArr4.length + this.pps.length)];
                    System.arraycopy(bArr4, 0, this.configByte, 0, bArr4.length);
                    byte[] bArr5 = this.pps;
                    System.arraycopy(bArr5, 0, this.configByte, this.sps.length, bArr5.length);
                }
            }
            return 0;
        } catch (Exception e3) {
            e3.printStackTrace();
            return VEResult.TER_MEDIA_CODEC_DEQUEUE_OUTPUT_BUFFER_EXCEPTION;
        }
    }

    @TargetApi(16)
    private ByteBuffer getOutputBufferByIdx(int i) {
        return Build.VERSION.SDK_INT >= 21 ? this.m_mediaCodec.getOutputBuffer(i) : this.m_mediaCodec.getOutputBuffers()[i];
    }

    @TargetApi(18)
    private int reconfigureMediaFormat(MediaCodecInfo mediaCodecInfo) {
        String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
        VELogUtil.d(TAG, "CodecNames:");
        for (String str : supportedTypes) {
            VELogUtil.d(TAG, "Codec: " + str);
        }
        this.m_codecFormat = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, this.m_width, this.m_height);
        this.m_codecFormat.setInteger("color-format", this.m_colorFormat);
        this.m_codecFormat.setInteger("bitrate", this.m_bitRate);
        this.m_codecFormat.setInteger("frame-rate", this.m_frameRate);
        this.m_codecFormat.setInteger("i-frame-interval", this.m_iFrameInternal);
        setProfile(mediaCodecInfo.getCapabilitiesForType(VIDEO_MIME_TYPE));
        Log.i(TAG, String.format("width:[%d] height:[%d] frameRate:[%d] iFrameInternal:[%d] bitRate:[%d] colorFormat:[%d]", Integer.valueOf(this.m_width), Integer.valueOf(this.m_height), Integer.valueOf(this.m_frameRate), Integer.valueOf(this.m_iFrameInternal), Integer.valueOf(this.m_bitRate), Integer.valueOf(this.m_colorFormat)));
        return 0;
    }

    private void setBitRateMode(MediaCodecInfo.CodecCapabilities codecCapabilities) {
        if (Build.VERSION.SDK_INT >= 21) {
            String[] strArr = {"BITRATE_MODE_CQ", "BITRATE_MODE_VBR", "BITRATE_MODE_CBR"};
            MediaCodecInfo.EncoderCapabilities encoderCapabilities = codecCapabilities.getEncoderCapabilities();
            for (int i = 0; i < 3; i++) {
                String str = TAG;
                VELogUtil.d(str, strArr[i] + ": " + encoderCapabilities.isBitrateModeSupported(i));
            }
            this.m_codecFormat.setInteger("bitrate-mode", 1);
        }
    }

    @TargetApi(16)
    private void setEncoder(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.m_configStatus = 0;
        if (i > 0) {
            this.m_width = i;
        }
        if (i2 > 0) {
            this.m_height = i2;
        }
        if (i3 > 0) {
            if (i3 < MIN_FRAME_RATE) {
                VELogUtil.w(TAG, String.format(Locale.getDefault(), "_frameRate:[%d] is too small, change to %d", Integer.valueOf(i3), Integer.valueOf(MIN_FRAME_RATE)));
                i3 = MIN_FRAME_RATE;
            } else if (i3 > MAX_FRAME_RATE) {
                VELogUtil.w(TAG, String.format(Locale.getDefault(), "_frameRate:[%d] is too large, change to %d", Integer.valueOf(i3), Integer.valueOf(MAX_FRAME_RATE)));
                i3 = MAX_FRAME_RATE;
            }
            if (this.m_frameRate != i3) {
                this.m_frameRate = i3;
                if (i3 < this.m_iFrameInternal) {
                    this.m_iFrameInternal = i3;
                }
                this.m_isNeedReconfigure = true;
                this.m_configStatus |= 2;
            }
        }
        if (i4 > 0) {
            this.m_maxBitRate = i4;
            if (this.m_bitRate != i4) {
                this.m_bitRate = i4;
                this.m_isNeedReconfigure = true;
                this.m_configStatus |= 1;
            }
        }
        if (i5 >= 0) {
            this.m_iFrameInternal = i5;
        }
        if (i6 > 0) {
            this.m_colorFormat = i6;
        }
        this.m_profile = MathUtils.clamp(i7, 1, 64);
    }

    private void setProfile(MediaCodecInfo.CodecCapabilities codecCapabilities) {
        MediaCodecInfo.CodecProfileLevel findBestMatchedProfile;
        if (Build.VERSION.SDK_INT >= 24 && (findBestMatchedProfile = MediaCodecUtils.findBestMatchedProfile(codecCapabilities, this.m_profile)) != null) {
            String str = TAG;
            VELogUtil.d(str, "Set Profile: " + findBestMatchedProfile.profile + ", Level = " + findBestMatchedProfile.level);
            this.m_codecFormat.setInteger("profile", findBestMatchedProfile.profile);
            this.m_codecFormat.setInteger(MiStat.Param.LEVEL, findBestMatchedProfile.level);
            int i = findBestMatchedProfile.profile;
            if (i == 2) {
                VELogUtil.d(TAG, "Set Main Profile");
                this.m_bitRate = (int) (((float) this.m_maxBitRate) * 0.85f);
            } else if (i == 8) {
                VELogUtil.d(TAG, "Set High Profile");
                this.m_bitRate = (int) (((float) this.m_maxBitRate) * 0.75f);
            }
            this.m_codecFormat.setInteger("bitrate", this.m_bitRate);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:85:0x01c7 A[SYNTHETIC, Splitter:B:85:0x01c7] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01d3 A[SYNTHETIC, Splitter:B:91:0x01d3] */
    @TargetApi(18)
    public int encodeVideoFromTexture(int i, long j, boolean z) {
        Throwable th;
        if (!this.m_bSuccessInit) {
            return 0;
        }
        VELogUtil.d(TAG, "encodeVideoFromTexture:: pts = " + j);
        this.m_PTSQueue.offer(Long.valueOf(j));
        if (this.m_eglStateSaver == null) {
            this.m_eglStateSaver = new TEEglStateSaver();
            this.m_eglStateSaver.saveEGLState();
        }
        if (this.m_isNeedReconfigure || (this.m_configStatus & 4) != 0) {
            if (this.m_mediaCodec == null || this.m_configStatus != 1 || Build.VERSION.SDK_INT < 19) {
                restartEncoder();
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("video-bitrate", this.m_bitRate);
                this.m_mediaCodec.setParameters(bundle);
                this.m_configStatus = 0;
            }
            this.m_isNeedReconfigure = false;
        }
        if (this.m_encodeStartTime == -1) {
            this.m_encodeStartTime = System.nanoTime();
        }
        int drainOutputBuffer = drainOutputBuffer(0);
        if (drainOutputBuffer != 0) {
            return drainOutputBuffer;
        }
        int i2 = i & -1;
        int i3 = 30;
        if (i2 != 0) {
            FileOutputStream fileOutputStream = null;
            try {
                if (this.m_textureDrawer != null) {
                    this.m_sharedContext.makeCurrent();
                    GLES20.glViewport(0, 0, this.m_width, this.m_height);
                    this.m_textureDrawer.drawTexture(i2);
                    if (this.mFirstFrame) {
                        if (DEBUG) {
                            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(3686400);
                            allocateDirect.order(ByteOrder.nativeOrder());
                            allocateDirect.position(0);
                            GLES20.glReadPixels(0, 0, 1280, Util.LIMIT_SURFACE_WIDTH, 6408, 5121, allocateDirect);
                            FileOutputStream fileOutputStream2 = new FileOutputStream("/mnt/sdcard/ttt.rgba");
                            try {
                                fileOutputStream2.write(allocateDirect.array());
                                fileOutputStream = fileOutputStream2;
                            } catch (Exception e2) {
                                e = e2;
                                fileOutputStream = fileOutputStream2;
                                try {
                                    e.printStackTrace();
                                    if (fileOutputStream != null) {
                                    }
                                    return VEResult.TER_MEDIA_CODEC_ENC_EXCEPTION;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (fileOutputStream != null) {
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        this.mFirstFrame = false;
                    }
                    long j2 = j * 1000;
                    this.m_sharedContext.setPresentationTime(j2);
                    this.m_sharedContext.swapBuffers();
                    this.m_bNeedSingalEnd = true;
                    int drainOutputBuffer2 = drainOutputBuffer(0);
                    if (drainOutputBuffer2 != 0) {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        return drainOutputBuffer2;
                    } else if (this.configByte == null) {
                        int i4 = 0;
                        do {
                            VELogUtil.d(TAG, "Encoder first frame, count = " + i4);
                            GLES20.glViewport(0, 0, this.m_width, this.m_height);
                            this.m_textureDrawer.drawTexture(i2);
                            this.m_sharedContext.setPresentationTime(j2);
                            this.m_sharedContext.swapBuffers();
                            int drainOutputBuffer3 = drainOutputBuffer(0);
                            if (drainOutputBuffer3 != 0) {
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                                return drainOutputBuffer3;
                            }
                            i4++;
                            if (i4 > 30) {
                                break;
                            }
                            Thread.sleep(10, 0);
                        } while (this.configByte == null);
                        if (this.configByte == null) {
                            Log.e(TAG, "Generate configData failed!!!" + i4);
                        } else {
                            Log.e(TAG, "Generate configData succeed!!!" + i4);
                        }
                        this.AVCQueue.clear();
                        restartEncoder();
                        this.m_getnerateIndex = 0;
                        this.m_sharedContext.makeCurrent();
                        GLES20.glViewport(0, 0, this.m_width, this.m_height);
                        this.m_textureDrawer.drawTexture(i2);
                        this.m_sharedContext.setPresentationTime(j2);
                        this.m_sharedContext.swapBuffers();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                this.m_getnerateIndex++;
            } catch (Exception e7) {
                e = e7;
                e.printStackTrace();
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                return VEResult.TER_MEDIA_CODEC_ENC_EXCEPTION;
            }
        } else if (this.m_mediaCodec != null && !this.m_bSignalEndOfStream && this.m_bNeedSingalEnd) {
            try {
                Log.i(TAG, "m_mediaCodec.flush()");
                this.m_bSignalEndOfStream = true;
                this.mEndOfStream = false;
                this.m_mediaCodec.signalEndOfInputStream();
            } catch (Exception e9) {
                e9.printStackTrace();
                return VEResult.TER_MEDIA_CODEC_SIG_END_EXCEPTION;
            }
        }
        this.m_eglStateSaver.makeSavedStateCurrent();
        if (this.m_bSignalEndOfStream) {
            while (!this.mEndOfStream) {
                int drainOutputBuffer4 = drainOutputBuffer(FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
                if (drainOutputBuffer4 == 0) {
                    if (this.mBufferIndex >= 0) {
                        break;
                    }
                    i3--;
                    if (i3 <= 0) {
                        break;
                    }
                } else {
                    return drainOutputBuffer4;
                }
            }
        } else {
            int drainOutputBuffer5 = drainOutputBuffer(0);
            if (drainOutputBuffer5 != 0) {
                return drainOutputBuffer5;
            }
        }
        this.m_lastCodecData = this.AVCQueue.poll();
        CodecData codecData = this.m_lastCodecData;
        if (codecData != null) {
            return codecData.data.length;
        }
        return 0;
    }

    public byte[] getCodecData(int i) {
        CodecData codecData = this.m_lastCodecData;
        if (codecData != null) {
            return codecData.data;
        }
        return null;
    }

    public byte[] getExtraData() {
        byte[] bArr = this.configByte;
        return bArr != null ? bArr : new byte[0];
    }

    public int getInfoByFlag(int[] iArr, int i) {
        if (i != 1) {
            return -1;
        }
        CodecData codecData = this.m_lastCodecData;
        long j = codecData.pts;
        iArr[0] = (int) (j & -1);
        iArr[1] = (int) ((j >> 32) & -1);
        long j2 = codecData.dts;
        iArr[2] = (int) (j2 & -1);
        iArr[3] = (int) ((j2 >> 32) & -1);
        return 4;
    }

    public Surface getInputSurface() {
        return this.m_surface;
    }

    @TargetApi(18)
    public int initEncoder(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        if (z && Build.VERSION.SDK_INT < 18) {
            return -1;
        }
        this.m_useInputSurface = z;
        if (this.m_useInputSurface) {
            this.m_colorFormat = 2130708361;
        } else {
            this.m_colorFormat = i4;
        }
        setEncoder(i, i2, i3, i6, i5, this.m_colorFormat, i7);
        this.m_isNeedReconfigure = true;
        this.m_bSuccessInit = true;
        this.m_bSignalEndOfStream = false;
        return configEncode();
    }

    public boolean initTextureDrawer() {
        TETextureDrawer tETextureDrawer = this.m_textureDrawer;
        if (tETextureDrawer != null) {
            tETextureDrawer.release();
            this.m_textureDrawer = null;
        }
        this.m_textureDrawer = TETextureDrawer.create();
        TETextureDrawer tETextureDrawer2 = this.m_textureDrawer;
        if (tETextureDrawer2 == null) {
            return false;
        }
        tETextureDrawer2.setRotation(0.0f);
        this.m_textureDrawer.setFlipScale(1.0f, -1.0f);
        return true;
    }

    @TargetApi(16)
    public void releaseEncoder() {
        stopEncoder();
        VELogUtil.d(TAG, "releaseEncoder");
        if (this.m_surface != null) {
            VELogUtil.d(TAG, "release surface");
            this.m_surface.release();
            this.m_surface = null;
        }
        if (this.m_mediaCodec != null) {
            VELogUtil.d(TAG, "release mediaCodec");
            this.m_mediaCodec.release();
            this.m_mediaCodec = null;
        }
        TESharedContext tESharedContext = this.m_sharedContext;
        if (tESharedContext != null) {
            tESharedContext.release();
            this.m_sharedContext = null;
        }
    }

    public void releaseTextureDrawer() {
        TETextureDrawer tETextureDrawer = this.m_textureDrawer;
        if (tETextureDrawer != null) {
            tETextureDrawer.release();
            this.m_textureDrawer = null;
        }
    }

    public int restartEncoder() {
        VELogUtil.i(TAG, "restartEncoder...");
        this.m_bNeedSingalEnd = false;
        stopEncoder();
        int configEncode = configEncode();
        return configEncode < 0 ? configEncode : startEncoder();
    }

    @TargetApi(16)
    public int startEncoder() {
        VELogUtil.d(TAG, "startEncoder...");
        try {
            if (Build.VERSION.SDK_INT >= 18 && this.m_useInputSurface) {
                if (this.m_sharedContext == null) {
                    this.m_sharedContext = TESharedContext.create(this.m_eglStateSaver.getSavedEGLContext(), 64, 64, 12610, this.m_surface);
                    if (this.m_sharedContext == null) {
                        return -2;
                    }
                } else if (!this.m_sharedContext.updateSurface(0, 0, 12610, this.m_surface)) {
                    return -2;
                }
                if (!initTextureDrawer()) {
                    return -3;
                }
            }
            this.m_mediaCodec.start();
            this.m_encodeStartTime = System.nanoTime();
            this.m_isNeedReconfigure = false;
            this.mEndOfStream = false;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @TargetApi(16)
    public void stopEncoder() {
        try {
            if (this.m_sharedContext != null) {
                this.m_sharedContext.makeCurrent();
            }
            if (this.m_textureDrawer != null) {
                this.m_textureDrawer.release();
                this.m_textureDrawer = null;
            }
            if (this.m_mediaCodec != null) {
                this.m_mediaCodec.stop();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
