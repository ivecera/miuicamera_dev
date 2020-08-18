package org.jcodec.containers.mp4.boxes;

import android.support.v4.view.ViewCompat;
import java.nio.ByteBuffer;

public abstract class FullBox extends Box {
    protected int flags;
    protected byte version;

    public FullBox(Header header) {
        super(header);
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.putInt((this.flags & ViewCompat.MEASURED_SIZE_MASK) | (this.version << 24));
    }

    public int getFlags() {
        return this.flags;
    }

    public byte getVersion() {
        return this.version;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public void parse(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt();
        this.version = (byte) ((i >> 24) & 255);
        this.flags = i & ViewCompat.MEASURED_SIZE_MASK;
    }

    public void setFlags(int i) {
        this.flags = i;
    }

    public void setVersion(byte b2) {
        this.version = b2;
    }
}
