package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import org.jcodec.common.Preconditions;
import org.jcodec.common.StringUtils;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.containers.mp4.IBoxFactory;
import org.jcodec.platform.Platform;

public abstract class Box {
    public static final int MAX_BOX_SIZE = 134217728;
    public Header header;

    public static class LeafBox extends Box {
        ByteBuffer data;

        public LeafBox(Header header) {
            super(header);
        }

        /* access modifiers changed from: protected */
        @Override // org.jcodec.containers.mp4.boxes.Box
        public void doWrite(ByteBuffer byteBuffer) {
            NIOUtils.write(byteBuffer, this.data);
        }

        @Override // org.jcodec.containers.mp4.boxes.Box
        public int estimateSize() {
            return this.data.remaining() + Header.estimateHeaderSize(this.data.remaining());
        }

        public ByteBuffer getData() {
            return this.data.duplicate();
        }

        @Override // org.jcodec.containers.mp4.boxes.Box
        public void parse(ByteBuffer byteBuffer) {
            this.data = NIOUtils.read(byteBuffer, (int) ((Box) this).header.getBodySize());
        }
    }

    public Box(Header header2) {
        this.header = header2;
    }

    public static <T extends Box> T asBox(Class<T> cls, Box box) {
        try {
            T newInstance = Platform.newInstance(cls, new Object[]{box.getHeader()});
            ByteBuffer allocate = ByteBuffer.allocate((int) box.getHeader().getBodySize());
            box.doWrite(allocate);
            allocate.flip();
            newInstance.parse(allocate);
            return newInstance;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static LeafBox createLeafBox(Header header2, ByteBuffer byteBuffer) {
        LeafBox leafBox = new LeafBox(header2);
        leafBox.data = byteBuffer;
        return leafBox;
    }

    public static Box parseBox(ByteBuffer byteBuffer, Header header2, IBoxFactory iBoxFactory) {
        Box newBox = iBoxFactory.newBox(header2);
        if (header2.getBodySize() >= 134217728) {
            return new LeafBox(Header.createHeader("free", 8));
        }
        newBox.parse(byteBuffer);
        return newBox;
    }

    public static String[] path(String str) {
        return StringUtils.splitC(str, '.');
    }

    public static Box terminatorAtom() {
        return createLeafBox(new Header(Platform.stringFromBytes(new byte[4])), ByteBuffer.allocate(0));
    }

    /* access modifiers changed from: protected */
    public abstract void doWrite(ByteBuffer byteBuffer);

    /* access modifiers changed from: protected */
    public void dump(StringBuilder sb) {
        sb.append("{\"tag\":\"" + this.header.getFourcc() + "\"}");
    }

    public abstract int estimateSize();

    public String getFourcc() {
        return this.header.getFourcc();
    }

    public Header getHeader() {
        return this.header;
    }

    public abstract void parse(ByteBuffer byteBuffer);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        dump(sb);
        return sb.toString();
    }

    public void write(ByteBuffer byteBuffer) {
        ByteBuffer duplicate = byteBuffer.duplicate();
        NIOUtils.skip(byteBuffer, 8);
        doWrite(byteBuffer);
        this.header.setBodySize((byteBuffer.position() - duplicate.position()) - 8);
        Preconditions.checkState(this.header.headerSize() == 8);
        this.header.write(duplicate);
    }
}
