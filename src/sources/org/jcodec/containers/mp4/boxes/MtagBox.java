package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import org.jcodec.common.io.NIOUtils;

public class MtagBox extends Box {
    private static final String FOURCC = "mtag";
    private byte[] data;

    public MtagBox(Header header) {
        super(header);
    }

    public static MtagBox createMtagBox(byte[] bArr) {
        MtagBox mtagBox = new MtagBox(Header.createHeader(FOURCC, 0));
        mtagBox.data = bArr;
        return mtagBox;
    }

    public static String fourcc() {
        return FOURCC;
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.put(this.data);
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return this.data.length + 8;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public void parse(ByteBuffer byteBuffer) {
        this.data = NIOUtils.toArray(NIOUtils.readBuf(byteBuffer));
    }
}
