package org.jcodec.containers.mp4;

import org.jcodec.containers.mp4.boxes.EndianBox;
import org.jcodec.containers.mp4.boxes.FormatBox;

public class WaveExtBoxes extends Boxes {
    public WaveExtBoxes() {
        ((Boxes) this).mappings.put(FormatBox.fourcc(), FormatBox.class);
        ((Boxes) this).mappings.put(EndianBox.fourcc(), EndianBox.class);
    }
}
