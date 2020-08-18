package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class SampleEntry extends NodeBox {
    protected short drefInd;

    public SampleEntry(Header header) {
        super(header);
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.put(new byte[]{0, 0, 0, 0, 0, 0});
        byteBuffer.putShort(this.drefInd);
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public int estimateSize() {
        return super.estimateSize() + 8;
    }

    public short getDrefInd() {
        return this.drefInd;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public void parse(ByteBuffer byteBuffer) {
        byteBuffer.getInt();
        byteBuffer.getShort();
        this.drefInd = byteBuffer.getShort();
    }

    /* access modifiers changed from: protected */
    public void parseExtensions(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
    }

    public void setDrefInd(short s) {
        this.drefInd = s;
    }

    public void setMediaType(String str) {
        ((Box) this).header = new Header(str);
    }

    /* access modifiers changed from: protected */
    public void writeExtensions(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
    }
}
