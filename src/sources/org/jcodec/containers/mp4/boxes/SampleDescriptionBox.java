package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class SampleDescriptionBox extends NodeBox {
    public SampleDescriptionBox(Header header) {
        super(header);
    }

    public static SampleDescriptionBox createSampleDescriptionBox(SampleEntry[] sampleEntryArr) {
        SampleDescriptionBox sampleDescriptionBox = new SampleDescriptionBox(new Header(fourcc()));
        for (SampleEntry sampleEntry : sampleEntryArr) {
            ((NodeBox) sampleDescriptionBox).boxes.add(sampleEntry);
        }
        return sampleDescriptionBox;
    }

    public static String fourcc() {
        return "stsd";
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.putInt(0);
        byteBuffer.putInt(Math.max(1, ((NodeBox) this).boxes.size()));
        super.doWrite(byteBuffer);
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public int estimateSize() {
        return super.estimateSize() + 8;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public void parse(ByteBuffer byteBuffer) {
        byteBuffer.getInt();
        byteBuffer.getInt();
        super.parse(byteBuffer);
    }
}
