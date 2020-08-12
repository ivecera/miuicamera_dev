package com.xiaomi.rendermanager.videoRender;

import android.util.Log;
import com.xiaomi.rendermanager.videoRender.VideoRenderer;
import java.util.LinkedList;

class FramePool {
    private static final int MAX_DIMENSION = 2048;
    private static final String TAG = "FramePool";
    private static int frameCount = 16;
    private BufferPoolInfo poolInfo = new BufferPoolInfo();

    class BufferPoolInfo {
        LinkedList<VideoRenderer.I420Frame> freeFrameList = new LinkedList<>();
        int totalAllocateCount = 2;

        public BufferPoolInfo() {
            int[] iArr = {2048, 1024, 1024};
            int i = 0;
            while (i < this.totalAllocateCount) {
                this.freeFrameList.add(new VideoRenderer.I420Frame(2048, 2048, false, false, iArr, null, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0));
                i++;
                iArr = iArr;
            }
        }
    }

    FramePool() {
    }

    private static long summarizeFrameDimensions(VideoRenderer.I420Frame i420Frame) {
        int[] iArr = i420Frame.yuvStrides;
        return (((((((((long) i420Frame.width) * 2048) + ((long) i420Frame.height)) * 2048) + ((long) iArr[0])) * 2048) + ((long) iArr[1])) * 2048) + ((long) iArr[2]);
    }

    public static boolean validateDimensions(VideoRenderer.I420Frame i420Frame) {
        if (i420Frame.width <= 2048 && i420Frame.height <= 2048) {
            int[] iArr = i420Frame.yuvStrides;
            if (iArr[0] <= 2048 && iArr[1] <= 2048 && iArr[2] <= 2048) {
                return true;
            }
        }
        return false;
    }

    public int getFrameSize() {
        return frameCount;
    }

    public void returnFrame(VideoRenderer.I420Frame i420Frame) {
        BufferPoolInfo bufferPoolInfo = this.poolInfo;
        if (bufferPoolInfo != null) {
            synchronized (bufferPoolInfo) {
                this.poolInfo.freeFrameList.add(i420Frame);
                frameCount++;
            }
            return;
        }
        throw new IllegalArgumentException("Unexpected frame dimensions");
    }

    public VideoRenderer.I420Frame takeFrame(VideoRenderer.I420Frame i420Frame) {
        VideoRenderer.I420Frame i420Frame2;
        synchronized (this.poolInfo) {
            LinkedList<VideoRenderer.I420Frame> linkedList = this.poolInfo.freeFrameList;
            if (i420Frame.width > 2048 || i420Frame.height > 2048) {
                throw new RuntimeException("resolution is out of boundary, width: " + i420Frame.width + ", height: " + i420Frame.height);
            } else if (!linkedList.isEmpty()) {
                i420Frame2 = linkedList.pop();
                i420Frame2.localPreview = i420Frame.localPreview;
                i420Frame2.backCamera = i420Frame.backCamera;
                i420Frame2.width = i420Frame.width;
                i420Frame2.height = i420Frame.height;
                i420Frame2.yuvStrides = i420Frame.yuvStrides;
                i420Frame2.offset = i420Frame.offset;
                i420Frame2.slope = i420Frame.offset;
                i420Frame2.sourceCoff = i420Frame.sourceCoff;
                i420Frame2.sharpCoff = i420Frame.sharpCoff;
                i420Frame2.sharpStrength = i420Frame.sharpStrength;
                i420Frame2.rotateAngle = i420Frame.rotateAngle;
                frameCount--;
            } else {
                Log.e(TAG, "Buffer pool new a frame, totalAllocateCount: " + this.poolInfo.totalAllocateCount + " size:" + i420Frame.width + "x" + i420Frame.height + " for strid:" + i420Frame.yuvStrides[0] + " " + i420Frame.yuvStrides[1] + " " + i420Frame.yuvStrides[2]);
                i420Frame2 = null;
            }
        }
        return i420Frame2;
    }
}
