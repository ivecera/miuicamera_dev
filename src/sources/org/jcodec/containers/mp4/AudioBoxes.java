package org.jcodec.containers.mp4;

import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.ChannelBox;
import org.jcodec.containers.mp4.boxes.WaveExtension;

public class AudioBoxes extends Boxes {
    public AudioBoxes() {
        ((Boxes) this).mappings.put(WaveExtension.fourcc(), WaveExtension.class);
        ((Boxes) this).mappings.put(ChannelBox.fourcc(), ChannelBox.class);
        ((Boxes) this).mappings.put("esds", Box.LeafBox.class);
    }
}
