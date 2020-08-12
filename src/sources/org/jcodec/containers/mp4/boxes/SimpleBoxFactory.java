package org.jcodec.containers.mp4.boxes;

import org.jcodec.containers.mp4.Boxes;
import org.jcodec.containers.mp4.IBoxFactory;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.platform.Platform;

public class SimpleBoxFactory implements IBoxFactory {
    private Boxes boxes;

    public SimpleBoxFactory(Boxes boxes2) {
        this.boxes = boxes2;
    }

    @Override // org.jcodec.containers.mp4.IBoxFactory
    public Box newBox(Header header) {
        Class<? extends Box> cls = this.boxes.toClass(header.getFourcc());
        if (cls == null) {
            return new Box.LeafBox(header);
        }
        return (Box) Platform.newInstance(cls, new Object[]{header});
    }
}
