package org.jcodec.containers.mp4;

import org.jcodec.containers.mp4.boxes.AliasBox;
import org.jcodec.containers.mp4.boxes.UrlBox;

public class DataBoxes extends Boxes {
    public DataBoxes() {
        ((Boxes) this).mappings.put(UrlBox.fourcc(), UrlBox.class);
        ((Boxes) this).mappings.put(AliasBox.fourcc(), AliasBox.class);
        ((Boxes) this).mappings.put("cios", AliasBox.class);
    }
}
