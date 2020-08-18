package org.webrtc.hwvideocodec;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: MediaCodecVideoDecoder */
class H264Decoder {
    private static final String AVC_MIME_TYPE = "video/avc";
    private static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    private static final int DEQUEUE_TIMEOUT = 100000;
    private static final String HEVC_MIME_TYPE = "video/hevc";
    private static final String TAG = "H264Decoder";
    private static final int[] supportedColorList = {19, 21, 2141391872, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    private static final String[] supportedHwCodecPrefixes = {"OMX.qcom.", "OMX.Nvidia.", "OMX.IMG.TOPAZ", "OMX.Exynos", "OMX.MTK", "OMX.hantro", "OMX.Intel", "OMX.IMG.MSVDX"};
    private int colorFormat;
    int counter = 0;
    private int cropBottom;
    private int cropLeft;
    private int cropRight;
    private int cropTop;
    private int dequedBufferIndex = -1;
    private boolean drop_frame;
    int frame_rate = 30;
    private int height;
    private ByteBuffer[] inputBuffers;
    boolean last_drop = false;
    private MediaCodec mediaCodec;
    private long nativeContext;
    long next_want_time = 0;
    private ByteBuffer[] outputBuffers;
    private int outputColorFormat;
    private Thread outputThread;
    long pre_time = 0;
    private Queue<Integer> queue = new LinkedList();
    /* access modifiers changed from: private */
    public volatile boolean running;
    private int sliceHeight;
    private int stride;
    int sum_time = 0;
    long timeStamp;
    private int width;

    /* compiled from: MediaCodecVideoDecoder */
    private static class DecoderProperties {
        public final String codecName;
        public final int colorFormat;

        DecoderProperties(String str, int i) {
            this.codecName = str;
            this.colorFormat = i;
        }
    }

    private void averageFrameRate(long j) {
        try {
            if (this.drop_frame) {
                if (this.pre_time != 0) {
                    int i = (int) (j - this.pre_time);
                    this.queue.offer(Integer.valueOf(i));
                    this.sum_time += i;
                    if (this.queue.size() > 25) {
                        this.sum_time -= this.queue.poll().intValue();
                    }
                    this.frame_rate = (this.queue.size() * 1000) / this.sum_time;
                }
                this.pre_time = j;
            }
        } catch (Exception e2) {
            Log.e(TAG, "find exception at averageFrameRate:", e2);
        }
    }

    private Thread createOutputThread() {
        return new Thread("Mediacodec_outputThread") {
            /* class org.webrtc.hwvideocodec.H264Decoder.AnonymousClass1 */

            public void run() {
                while (H264Decoder.this.running) {
                    H264Decoder.this.deliverDecodedFrame();
                }
            }
        };
    }

    private int dequeueInputBuffer() {
        try {
            return this.mediaCodec.dequeueInputBuffer(100000);
        } catch (Exception e2) {
            Log.e(TAG, "find exception at dequeueIntputBuffer:", e2);
            return -2;
        }
    }

    private int dequeueOutputBuffer() {
        int i = -2;
        try {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 100000);
            this.timeStamp = bufferInfo.presentationTimeUs;
            while (true) {
                if (dequeueOutputBuffer != -3) {
                    if (dequeueOutputBuffer != i) {
                        if ((bufferInfo.flags & 4) != 0) {
                            return -3;
                        }
                        return dequeueOutputBuffer;
                    }
                }
                if (dequeueOutputBuffer == -3) {
                    this.outputBuffers = this.mediaCodec.getOutputBuffers();
                } else if (dequeueOutputBuffer == i) {
                    MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
                    Log.d(TAG, "Format changed: " + outputFormat.toString());
                    this.width = outputFormat.getInteger("width");
                    this.height = outputFormat.getInteger("height");
                    Log.d(TAG, "new width: " + this.width + "new height:" + this.height);
                    if (outputFormat.containsKey("crop-top")) {
                        this.cropTop = outputFormat.getInteger("crop-top");
                        Log.d(TAG, "Crop-top:" + this.cropTop);
                    }
                    if (outputFormat.containsKey("crop-bottom")) {
                        this.cropBottom = outputFormat.getInteger("crop-bottom");
                        Log.d(TAG, "Crop-bottom:" + this.cropBottom);
                    }
                    if (outputFormat.containsKey("crop-left")) {
                        this.cropLeft = outputFormat.getInteger("crop-left");
                        Log.d(TAG, "Crop-left:" + this.cropLeft);
                    }
                    if (outputFormat.containsKey("crop-right")) {
                        this.cropRight = outputFormat.getInteger("crop-right");
                        Log.d(TAG, "Crop-right:" + this.cropRight);
                    }
                    if (outputFormat.containsKey("color-format")) {
                        int integer = outputFormat.getInteger("color-format");
                        this.colorFormat = integer;
                        this.outputColorFormat = integer;
                        Log.d(TAG, "Color: 0x" + Integer.toHexString(this.colorFormat));
                        int[] iArr = supportedColorList;
                        int length = iArr.length;
                        boolean z = false;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            if (this.colorFormat == iArr[i2]) {
                                z = true;
                                break;
                            }
                            i2++;
                        }
                        if (!z) {
                            Log.e(TAG, "Non supported color format");
                            return -2;
                        }
                    }
                    if (outputFormat.containsKey("stride")) {
                        this.stride = outputFormat.getInteger("stride");
                    }
                    if (outputFormat.containsKey("slice-height")) {
                        this.sliceHeight = outputFormat.getInteger("slice-height");
                    }
                    Log.i(TAG, "Frame stride and slice height: " + this.stride + " x " + this.sliceHeight);
                    this.stride = Math.max(this.width, this.stride);
                    this.sliceHeight = Math.max(this.height, this.sliceHeight);
                }
                int dequeueOutputBuffer2 = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 100000);
                this.timeStamp = bufferInfo.presentationTimeUs;
                dequeueOutputBuffer = dequeueOutputBuffer2;
                i = -2;
            }
        } catch (Exception e2) {
            Log.e(TAG, "find exception at dequeueOutputBuffer:" + e2);
            return -2;
        }
    }

    private boolean dropFrame(int i, long j) {
        try {
            int max = 1000 / Math.max(Math.min(i, 120), 8);
            boolean z = j - this.next_want_time > 0 && this.next_want_time != 0;
            if (this.next_want_time == 0) {
                this.next_want_time = ((long) max) + j;
            } else {
                this.next_want_time += (long) max;
            }
            if (Math.abs(j - this.next_want_time) > ((long) (max * 3))) {
                this.next_want_time = j + ((long) max);
            }
            return z;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at averageFrameRate:", e2);
            return false;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: org.webrtc.hwvideocodec.H264Decoder$DecoderProperties} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: org.webrtc.hwvideocodec.H264Decoder$DecoderProperties} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: org.webrtc.hwvideocodec.H264Decoder$DecoderProperties} */
    /* JADX WARN: Multi-variable type inference failed */
    private static DecoderProperties findHwDecoder(String str) {
        String str2;
        DecoderProperties decoderProperties = null;
        DecoderProperties decoderProperties2 = null;
        try {
            if (Build.VERSION.SDK_INT < 19) {
                Log.i(TAG, "sdk version too low");
                return null;
            }
            int i = 0;
            while (i < MediaCodecList.getCodecCount()) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                if (!codecInfoAt.isEncoder()) {
                    String[] supportedTypes = codecInfoAt.getSupportedTypes();
                    int length = supportedTypes.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            str2 = decoderProperties2;
                            break;
                        }
                        String str3 = supportedTypes[i2];
                        if (str3.equals(str)) {
                            Log.i(TAG, "mimeType is " + str3);
                            str2 = codecInfoAt.getName();
                            Log.i(TAG, "name is  " + str2);
                            break;
                        }
                        i2++;
                    }
                    if (str2 != 0) {
                        Log.d(TAG, "Found candidate decoder " + str2);
                        MediaCodecInfo.CodecCapabilities capabilitiesForType = codecInfoAt.getCapabilitiesForType(str);
                        int[] iArr = capabilitiesForType.colorFormats;
                        for (int i3 : iArr) {
                            Log.d(TAG, "   Color: 0x" + Integer.toHexString(i3));
                        }
                        String[] strArr = supportedHwCodecPrefixes;
                        for (String str4 : strArr) {
                            Log.i(TAG, " hwCodecPrefix :" + str4);
                            if (str2.startsWith(str4)) {
                                int[] iArr2 = supportedColorList;
                                for (int i4 : iArr2) {
                                    int[] iArr3 = capabilitiesForType.colorFormats;
                                    int length2 = iArr3.length;
                                    int i5 = 0;
                                    while (i5 < length2) {
                                        try {
                                            int i6 = iArr3[i5];
                                            if (i6 == i4) {
                                                Log.d(TAG, "Found target decoder " + str2 + ". Color: 0x" + Integer.toHexString(i6));
                                                return new DecoderProperties(str2, i6);
                                            }
                                            i5++;
                                        } catch (Exception e2) {
                                            e = e2;
                                            decoderProperties = null;
                                            Log.e(TAG, "find exception at findHwDecoder:", e);
                                            return decoderProperties;
                                        }
                                    }
                                }
                                continue;
                            }
                        }
                        continue;
                    }
                }
                i++;
                decoderProperties2 = null;
            }
            return decoderProperties2;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "find exception at findHwDecoder:", e);
            return decoderProperties;
        }
    }

    private static boolean isPlatformSupported() {
        return findHwDecoder(AVC_MIME_TYPE) != null;
    }

    private boolean queueInputBuffer(int i, int i2, long j) {
        try {
            this.inputBuffers[i].position(0);
            this.inputBuffers[i].limit(i2);
            this.mediaCodec.queueInputBuffer(i, 0, i2, j, 0);
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at queueInputBuffer:", e2);
            return false;
        }
    }

    private boolean releaseOutputBuffer(int i) {
        try {
            this.mediaCodec.releaseOutputBuffer(i, false);
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at releaseOutputBuffer:", e2);
            return false;
        }
    }

    public native void DeliverFrame(ByteBuffer byteBuffer, long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8);

    public boolean decodeFrameInputStream(H264I420Frame h264I420Frame) {
        try {
            int dequeueInputBuffer = dequeueInputBuffer();
            if (dequeueInputBuffer >= 0) {
                ByteBuffer byteBuffer = this.inputBuffers[dequeueInputBuffer];
                byteBuffer.clear();
                byteBuffer.put(h264I420Frame.buffer);
                this.counter++;
                return queueInputBuffer(dequeueInputBuffer, h264I420Frame.size, h264I420Frame.timeStamp);
            }
            Log.i(TAG, " get inputBuffer error, maybe discard a frame");
            return false;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at decodeFrameInputStream:", e2);
            return false;
        }
    }

    public void decodeFramePushPicture(boolean z) {
        String str;
        String str2 = TAG;
        if (z) {
            try {
                Log.i(str2, "flush decoder output queue");
            } catch (Exception e2) {
                e = e2;
                str = str2;
                Log.e(str, "find exception at decodeFramePushPicture:", e);
                return;
            }
        }
        int dequeueOutputBuffer = dequeueOutputBuffer();
        boolean z2 = dequeueOutputBuffer >= 0;
        if (z) {
            z2 = dequeueOutputBuffer == -3;
        }
        int i = 300;
        while (z2 && i > 0) {
            if (dequeueOutputBuffer >= 0) {
                this.counter--;
                if (this.nativeContext != 0) {
                    str = str2;
                    try {
                        DeliverFrame(this.outputBuffers[dequeueOutputBuffer], this.nativeContext, this.width, this.height, this.stride, this.cropTop, this.cropBottom, this.cropLeft, this.cropRight, this.timeStamp, this.outputColorFormat);
                    } catch (Exception e3) {
                        e = e3;
                        Log.e(str, "find exception at decodeFramePushPicture:", e);
                        return;
                    }
                } else {
                    str = str2;
                }
                releaseOutputBuffer(dequeueOutputBuffer);
            } else {
                str = str2;
                try {
                    Thread.sleep(10);
                    i -= 10;
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            dequeueOutputBuffer = dequeueOutputBuffer();
            boolean z3 = dequeueOutputBuffer >= 0;
            if (z) {
                z3 = dequeueOutputBuffer != -3;
                str2 = str;
            } else {
                str2 = str;
            }
        }
    }

    public void deliverDecodedFrame() {
        String str;
        int i;
        try {
            int dequeueOutputBuffer = dequeueOutputBuffer();
            if (dequeueOutputBuffer >= 0) {
                if (this.nativeContext != 0) {
                    averageFrameRate(System.currentTimeMillis());
                    if (!this.drop_frame || this.last_drop || !dropFrame(this.frame_rate, System.currentTimeMillis())) {
                        ByteBuffer byteBuffer = this.outputBuffers[dequeueOutputBuffer];
                        long j = this.nativeContext;
                        int i2 = this.width;
                        int i3 = this.height;
                        int i4 = this.stride;
                        int i5 = this.cropTop;
                        int i6 = this.cropBottom;
                        int i7 = this.cropLeft;
                        int i8 = this.cropRight;
                        long j2 = this.timeStamp;
                        int i9 = this.outputColorFormat;
                        str = TAG;
                        i = dequeueOutputBuffer;
                        try {
                            DeliverFrame(byteBuffer, j, i2, i3, i4, i5, i6, i7, i8, j2, i9);
                            this.last_drop = false;
                            releaseOutputBuffer(i);
                        } catch (Exception e2) {
                            e = e2;
                            Log.e(str, "find exception at deliverOutPutsTimer:" + e);
                        }
                    } else {
                        Log.i(TAG, "drop this frame! frame rate: " + this.frame_rate);
                        this.last_drop = true;
                    }
                }
                i = dequeueOutputBuffer;
                releaseOutputBuffer(i);
            }
        } catch (Exception e3) {
            e = e3;
            str = TAG;
            Log.e(str, "find exception at deliverOutPutsTimer:" + e);
        }
    }

    public boolean flush() {
        decodeFramePushPicture(true);
        return true;
    }

    public boolean initDecoder(int i, int i2, int i3, int i4, boolean z, long j) throws IOException {
        try {
            Log.i(TAG, "decoder init with:" + i2 + " height:" + i3 + " getWidth:" + " context:" + j + " frameRate:" + i4);
            String str = i == 0 ? AVC_MIME_TYPE : i == 1 ? HEVC_MIME_TYPE : null;
            DecoderProperties findHwDecoder = findHwDecoder(str);
            if (findHwDecoder == null) {
                return false;
            }
            Log.d(TAG, "Java initDecode: " + i2 + " x " + i3 + " drop_frame " + z + ". Color: 0x" + Integer.toHexString(findHwDecoder.colorFormat));
            this.width = i2;
            this.height = i3;
            this.drop_frame = z;
            this.stride = i2;
            this.sliceHeight = i3;
            this.cropTop = 0;
            this.cropBottom = i3 + -1;
            this.cropLeft = 0;
            this.cropRight = i2 + -1;
            this.dequedBufferIndex = -1;
            this.nativeContext = j;
            this.timeStamp = 0;
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str, i2, i3);
            if (i4 == 0) {
                i4 = 60;
            }
            createVideoFormat.setFloat("frame-rate", (float) i4);
            this.mediaCodec = MediaCodec.createByCodecName(findHwDecoder.codecName);
            if (this.mediaCodec == null) {
                Log.i("hevc decoder", "decoder init error null");
                return false;
            }
            this.mediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 0);
            this.mediaCodec.start();
            this.colorFormat = findHwDecoder.colorFormat;
            this.outputBuffers = this.mediaCodec.getOutputBuffers();
            this.inputBuffers = this.mediaCodec.getInputBuffers();
            Log.d(TAG, "Input buffers: " + this.inputBuffers.length + ". Output buffers: " + this.outputBuffers.length);
            MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
            if (outputFormat.containsKey("color-format")) {
                this.outputColorFormat = outputFormat.getInteger("color-format");
            }
            this.running = true;
            this.outputThread = createOutputThread();
            this.outputThread.start();
            Log.i(TAG, "decoder init done");
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at initDecode :", e2);
            return false;
        }
    }

    public void release() {
        try {
            Log.i(TAG, "decoder release begin");
            this.running = false;
            this.mediaCodec.stop();
            this.mediaCodec.release();
            this.mediaCodec = null;
            Log.i(TAG, "decoder release done");
        } catch (Exception e2) {
            Log.e(TAG, "find exception at release:", e2);
        }
    }
}
