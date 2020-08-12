package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class MovieFragmentHeaderBox extends FullBox {
    private int sequenceNumber;

    public MovieFragmentHeaderBox(Header header) {
        super(header);
    }

    public static MovieFragmentHeaderBox createMovieFragmentHeaderBox() {
        return new MovieFragmentHeaderBox(new Header(fourcc()));
    }

    public static String fourcc() {
        return "mfhd";
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void doWrite(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
        byteBuffer.putInt(this.sequenceNumber);
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return 16;
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void parse(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
        this.sequenceNumber = byteBuffer.getInt();
    }

    public void setSequenceNumber(int i) {
        this.sequenceNumber = i;
    }
}
