package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class SyncSamplesBox extends FullBox {
    public static final String STSS = "stss";
    protected int[] syncSamples;

    public SyncSamplesBox(Header header) {
        super(header);
    }

    public static SyncSamplesBox createSyncSamplesBox(int[] iArr) {
        SyncSamplesBox syncSamplesBox = new SyncSamplesBox(new Header(STSS));
        syncSamplesBox.syncSamples = iArr;
        return syncSamplesBox;
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void doWrite(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
        byteBuffer.putInt(this.syncSamples.length);
        int i = 0;
        while (true) {
            int[] iArr = this.syncSamples;
            if (i < iArr.length) {
                byteBuffer.putInt(iArr[i]);
                i++;
            } else {
                return;
            }
        }
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return (this.syncSamples.length * 4) + 16;
    }

    public int[] getSyncSamples() {
        return this.syncSamples;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void parse(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
        int i = byteBuffer.getInt();
        this.syncSamples = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.syncSamples[i2] = byteBuffer.getInt();
        }
    }
}
