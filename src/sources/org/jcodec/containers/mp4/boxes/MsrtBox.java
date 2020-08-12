package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import org.jcodec.common.io.NIOUtils;

public class MsrtBox extends Box {
    private static final String FOURCC = "msrt";
    private byte[] data;

    public MsrtBox(Header header) {
        super(header);
    }

    public static MsrtBox createMsrtBox(byte[] bArr) {
        MsrtBox msrtBox = new MsrtBox(Header.createHeader(FOURCC, 0));
        msrtBox.data = bArr;
        return msrtBox;
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
