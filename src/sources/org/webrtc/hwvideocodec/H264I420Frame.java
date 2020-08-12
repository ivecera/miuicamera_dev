package org.webrtc.hwvideocodec;

import java.nio.ByteBuffer;

class H264I420Frame {
    private static final String TAG = "H264I420Frame";
    public final ByteBuffer buffer;
    public int height;
    public int size;
    public long timeStamp;
    public int width;

    public H264I420Frame(int i, int i2, ByteBuffer byteBuffer, int i3, long j) {
        this.width = i;
        this.height = i2;
        this.size = i3;
        this.buffer = byteBuffer;
        this.timeStamp = j;
    }

    public H264I420Frame copyFrom(H264I420Frame h264I420Frame) {
        System.currentTimeMillis();
        if (this.size == h264I420Frame.size && this.width == h264I420Frame.width && this.height == h264I420Frame.height) {
            this.timeStamp = h264I420Frame.timeStamp;
            ByteBuffer byteBuffer = h264I420Frame.buffer;
            ByteBuffer byteBuffer2 = this.buffer;
            byteBuffer.position(0).limit(byteBuffer.capacity());
            byteBuffer2.put(byteBuffer);
            byteBuffer2.position(0).limit(byteBuffer2.capacity());
            return this;
        }
        throw new RuntimeException("Mismatched dimensions!  Source: " + h264I420Frame.toString() + ", destination: " + toString());
    }
}
