package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import org.jcodec.containers.mp4.Boxes;

public class KeysBox extends NodeBox {
    private static final String FOURCC = "keys";

    private static class LocalBoxes extends Boxes {
        LocalBoxes() {
            ((Boxes) this).mappings.put(MdtaBox.fourcc(), MdtaBox.class);
        }
    }

    public KeysBox(Header header) {
        super(header);
        ((NodeBox) this).factory = new SimpleBoxFactory(new LocalBoxes());
    }

    public static KeysBox createKeysBox() {
        return new KeysBox(Header.createHeader(FOURCC, 0));
    }

    public static String fourcc() {
        return FOURCC;
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.NodeBox
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.putInt(0);
        byteBuffer.putInt(((NodeBox) this).boxes.size());
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
