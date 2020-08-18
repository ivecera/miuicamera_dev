package org.jcodec.containers.mp4.boxes;

public class EncodedPixelBox extends ClearApertureBox {
    public static final String ENOF = "enof";

    public EncodedPixelBox(Header header) {
        super(header);
    }

    public static EncodedPixelBox createEncodedPixelBox(int i, int i2) {
        EncodedPixelBox encodedPixelBox = new EncodedPixelBox(new Header(ENOF));
        ((ClearApertureBox) encodedPixelBox).width = (float) i;
        ((ClearApertureBox) encodedPixelBox).height = (float) i2;
        return encodedPixelBox;
    }
}
