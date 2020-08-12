package org.webrtc.hwvideocodec;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;

/* compiled from: MediaCodecVideoEncoder */
class H264Encoder {
    private static final String AVC_MIME_TYPE = "video/avc";
    private static final String HEVC_MIME_TYPE = "video/hevc";
    private static final String TAG = "H264Encoder";
    private static final int VIDEO_ControlRateConstant = 2;
    private static boolean isQcomPlatform;
    private static final int[] supportedColorList = {21, 19};
    private static final String[] supportedHwCodecPrefixes = {"OMX.qcom.", "OMX.Nvidia.", "OMX.IMG.TOPAZ", "OMX.Exynos", "OMX.MTK", "OMX.hantro", "OMX.Intel", "OMX.ARM"};
    private boolean Constructed = false;
    int counter = 0;
    private int dequedBufferIndex = -1;
    int frameCounter = 0;
    int m_height;
    byte[] m_info = null;
    int m_width;
    private MediaCodec mediaCodec;
    private long nativeContext = 0;
    private int supportColorFormat = 0;

    /* compiled from: MediaCodecVideoEncoder */
    private static class EncoderProperties {
        public final String codecName;
        public final int colorFormat;

        EncoderProperties(String str, int i) {
            this.codecName = str;
            this.colorFormat = i;
        }
    }

    H264Encoder() {
    }

    public static int byteArrayToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += (bArr[i2] & 255) << ((3 - i2) * 8);
        }
        return i;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: org.webrtc.hwvideocodec.H264Encoder$EncoderProperties} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: org.webrtc.hwvideocodec.H264Encoder$EncoderProperties} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: org.webrtc.hwvideocodec.H264Encoder$EncoderProperties} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: java.lang.String} */
    /* JADX WARN: Multi-variable type inference failed */
    private static EncoderProperties findHwEncoder(String str) {
        String str2;
        String str3 = str;
        EncoderProperties encoderProperties = null;
        try {
            Log.i(TAG, "sdk version is: " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT < 16) {
                return null;
            }
            int i = 0;
            for (int i2 = 0; i2 < MediaCodecList.getCodecCount(); i2++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
                if (codecInfoAt.isEncoder()) {
                    for (String str4 : codecInfoAt.getSupportedTypes()) {
                        Log.i(TAG, "codec name: " + str4 + " company:" + codecInfoAt.getName());
                    }
                }
            }
            int i3 = 0;
            while (i3 < MediaCodecList.getCodecCount()) {
                MediaCodecInfo codecInfoAt2 = MediaCodecList.getCodecInfoAt(i3);
                if (codecInfoAt2.isEncoder()) {
                    String[] supportedTypes = codecInfoAt2.getSupportedTypes();
                    int length = supportedTypes.length;
                    int i4 = i;
                    while (true) {
                        if (i4 >= length) {
                            str2 = encoderProperties;
                            break;
                        }
                        String str5 = supportedTypes[i4];
                        Log.i(TAG, "codec name: " + str5);
                        if (str5.equals(str3)) {
                            str2 = codecInfoAt2.getName();
                            break;
                        }
                        i4++;
                    }
                    if (str2 == 0) {
                        continue;
                    } else {
                        Log.i(TAG, "Found candidate encoder " + str2);
                        MediaCodecInfo.CodecCapabilities capabilitiesForType = codecInfoAt2.getCapabilitiesForType(str3);
                        int[] iArr = capabilitiesForType.colorFormats;
                        int length2 = iArr.length;
                        for (int i5 = i; i5 < length2; i5++) {
                            int i6 = iArr[i5];
                            Log.i(TAG, "   Color: 0x" + Integer.toHexString(i6));
                        }
                        isQcomPlatform = str2.startsWith(supportedHwCodecPrefixes[i]);
                        String[] strArr = supportedHwCodecPrefixes;
                        int length3 = strArr.length;
                        int i7 = i;
                        while (i7 < length3) {
                            if (str2.startsWith(strArr[i7])) {
                                int[] iArr2 = supportedColorList;
                                int length4 = iArr2.length;
                                for (int i8 = i; i8 < length4; i8++) {
                                    int i9 = iArr2[i8];
                                    int[] iArr3 = capabilitiesForType.colorFormats;
                                    for (int i10 : iArr3) {
                                        if (i10 == i9) {
                                            Log.i(TAG, "Found target encoder " + str2 + ". Color: 0x" + Integer.toHexString(i10));
                                            return new EncoderProperties(str2, i10);
                                        }
                                    }
                                }
                                continue;
                            }
                            i7++;
                            i = 0;
                        }
                        continue;
                    }
                }
                i3++;
                str3 = str;
                encoderProperties = null;
                i = 0;
            }
            return encoderProperties;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at findHwEncoder:", e2);
            return null;
        }
    }

    public static void intToByteArray(int i, byte[] bArr) {
        bArr[0] = (byte) ((i >> 24) & 255);
        bArr[1] = (byte) ((i >> 16) & 255);
        bArr[2] = (byte) ((i >> 8) & 255);
        bArr[3] = (byte) (i & 255);
    }

    private static boolean isPlatformSupported() {
        return findHwEncoder(AVC_MIME_TYPE) != null;
    }

    public native void SendFrame(byte[] bArr, long j, long j2, boolean z);

    public boolean encodeFrameInput(H264I420Frame h264I420Frame, boolean z) {
        if (z) {
            try {
                Log.i(TAG, "force a key frame");
            } catch (Exception e2) {
                Log.e(TAG, "find exception at encodeFrameInput encoder:", e2);
                return false;
            }
        }
        int dequeueInputBuffer = this.mediaCodec.dequeueInputBuffer(-1);
        if (z && Build.VERSION.SDK_INT >= 19) {
            Bundle bundle = new Bundle();
            bundle.putInt("request-sync", 0);
            this.mediaCodec.setParameters(bundle);
        }
        this.counter++;
        if (dequeueInputBuffer < 0) {
            return false;
        }
        ByteBuffer[] inputBuffers = this.mediaCodec.getInputBuffers();
        this.frameCounter++;
        ByteBuffer byteBuffer = inputBuffers[dequeueInputBuffer];
        byteBuffer.clear();
        byteBuffer.put(h264I420Frame.buffer);
        this.mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, h264I420Frame.size, h264I420Frame.timeStamp * 1000, 0);
        encodeFrameOutput(false);
        return true;
    }

    public void encodeFrameOutput(boolean z) {
        try {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            if (z) {
                Log.i(TAG, "flush output queue");
            }
            int dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
            boolean z2 = dequeueOutputBuffer >= 0;
            if (z) {
                z2 = (bufferInfo.flags & 4) == 0;
            }
            int i = 300;
            while (z2 && i > 0) {
                if (dequeueOutputBuffer >= 0) {
                    int i2 = bufferInfo.size;
                    long j = bufferInfo.presentationTimeUs / 1000;
                    ByteBuffer[] outputBuffers = this.mediaCodec.getOutputBuffers();
                    byte[] bArr = new byte[i2];
                    this.frameCounter--;
                    outputBuffers[dequeueOutputBuffer].get(bArr);
                    byte b2 = bArr[4] & 31;
                    boolean z3 = b2 >= 5 && b2 <= 8;
                    if (z3) {
                        Log.i(TAG, "h264 add frame header  cdr flag");
                    }
                    SendFrame(bArr, this.nativeContext, j, z3);
                    this.mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                } else {
                    try {
                        Thread.sleep(10);
                        i -= 10;
                    } catch (Exception e2) {
                        Log.e(TAG, "find exception at ThreadSleep:", e2);
                    }
                }
                dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                boolean z4 = dequeueOutputBuffer >= 0;
                if (z) {
                    z4 = (bufferInfo.flags & 4) == 0;
                }
            }
        } catch (Exception e3) {
            Log.e(TAG, "find exception at encodeFrameOutput:", e3);
        }
    }

    public boolean flush() {
        encodeFrameOutput(true);
        return true;
    }

    public boolean initEncoder(int i, int i2, int i3, int i4, int i5, long j, boolean z) {
        try {
            Log.i(TAG, "H264 encoder creat width" + i2 + "height:" + i3 + "framerate:" + i4 + "bitrate:" + i5 + "this:" + this);
            String str = i == 0 ? AVC_MIME_TYPE : i == 1 ? HEVC_MIME_TYPE : null;
            this.m_width = i2;
            this.m_height = i3;
            this.nativeContext = j;
            this.m_info = null;
            this.dequedBufferIndex = -1;
            EncoderProperties findHwEncoder = findHwEncoder(str);
            if (findHwEncoder == null) {
                Log.i(TAG, "Can not find HW AVC encoder");
                return false;
            }
            this.mediaCodec = MediaCodec.createByCodecName(findHwEncoder.codecName);
            if (this.mediaCodec == null) {
                Log.i(TAG, "creatByCodecName failed");
                return false;
            }
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str, i2, i3);
            createVideoFormat.setInteger("bitrate", i5);
            createVideoFormat.setInteger("frame-rate", i4);
            if (!z) {
                createVideoFormat.setInteger("bitrate-mode", 2);
            }
            this.supportColorFormat = findHwEncoder.colorFormat;
            createVideoFormat.setInteger("color-format", findHwEncoder.colorFormat);
            createVideoFormat.setInteger("i-frame-interval", 2);
            this.mediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
            this.mediaCodec.start();
            this.Constructed = true;
            StringBuilder sb = new StringBuilder();
            sb.append("avc encoder creat done, isSemiPlanar:");
            sb.append(findHwEncoder.colorFormat == 21);
            Log.i(TAG, sb.toString());
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at initEncoder:", e2);
            return false;
        }
    }

    public boolean isSemiPlanarSupport() {
        return this.supportColorFormat == 21;
    }

    public void release() {
        try {
            this.Constructed = false;
            Log.i(TAG, "avc encoder release begin");
            this.mediaCodec.stop();
            this.mediaCodec.release();
            this.mediaCodec = null;
            this.m_info = null;
            Log.i(TAG, "avc encoder release done");
        } catch (Exception e2) {
            Log.e(TAG, "find exception at release encoder:", e2);
        }
    }

    public void reset() {
        try {
            if (this.Constructed) {
                this.mediaCodec.flush();
                Log.i(TAG, "avc encoder reset done");
            }
        } catch (Exception e2) {
            Log.e(TAG, "find exception at reset encoder:", e2);
        }
    }

    public void setBitrate(int i) {
        try {
            if (this.Constructed && Build.VERSION.SDK_INT >= 19) {
                Log.i(TAG, "setRates: " + i + " kbps ");
                Bundle bundle = new Bundle();
                bundle.putInt("video-bitrate", i);
                this.mediaCodec.setParameters(bundle);
            }
        } catch (Exception e2) {
            Log.e(TAG, "find exception at setBitrate encoder:", e2);
        }
    }
}
