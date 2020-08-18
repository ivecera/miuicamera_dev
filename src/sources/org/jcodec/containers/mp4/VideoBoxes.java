package org.jcodec.containers.mp4;

import org.jcodec.codecs.h264.mp4.AvcCBox;
import org.jcodec.containers.mp4.boxes.CleanApertureExtension;
import org.jcodec.containers.mp4.boxes.ColorExtension;
import org.jcodec.containers.mp4.boxes.FielExtension;
import org.jcodec.containers.mp4.boxes.GamaExtension;
import org.jcodec.containers.mp4.boxes.PixelAspectExt;

public class VideoBoxes extends Boxes {
    public VideoBoxes() {
        ((Boxes) this).mappings.put(PixelAspectExt.fourcc(), PixelAspectExt.class);
        ((Boxes) this).mappings.put(AvcCBox.fourcc(), AvcCBox.class);
        ((Boxes) this).mappings.put(ColorExtension.fourcc(), ColorExtension.class);
        ((Boxes) this).mappings.put(GamaExtension.fourcc(), GamaExtension.class);
        ((Boxes) this).mappings.put(CleanApertureExtension.fourcc(), CleanApertureExtension.class);
        ((Boxes) this).mappings.put(FielExtension.fourcc(), FielExtension.class);
    }
}
