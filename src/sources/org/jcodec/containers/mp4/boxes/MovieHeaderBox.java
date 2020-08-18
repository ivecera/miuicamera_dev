package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.containers.mp4.TimeUtil;

public class MovieHeaderBox extends FullBox {
    private long created;
    private long duration;
    private int[] matrix;
    private long modified;
    private int nextTrackId;
    private float rate;
    private int timescale;
    private float volume;

    public MovieHeaderBox(Header header) {
        super(header);
    }

    public static MovieHeaderBox createMovieHeaderBox(int i, long j, float f2, float f3, long j2, long j3, int[] iArr, int i2) {
        MovieHeaderBox movieHeaderBox = new MovieHeaderBox(new Header(fourcc()));
        movieHeaderBox.timescale = i;
        movieHeaderBox.duration = j;
        movieHeaderBox.rate = f2;
        movieHeaderBox.volume = f3;
        movieHeaderBox.created = j2;
        movieHeaderBox.modified = j3;
        movieHeaderBox.matrix = iArr;
        movieHeaderBox.nextTrackId = i2;
        return movieHeaderBox;
    }

    public static String fourcc() {
        return "mvhd";
    }

    private int[] readMatrix(ByteBuffer byteBuffer) {
        int[] iArr = new int[9];
        for (int i = 0; i < 9; i++) {
            iArr[i] = byteBuffer.getInt();
        }
        return iArr;
    }

    private float readRate(ByteBuffer byteBuffer) {
        return ((float) byteBuffer.getInt()) / 65536.0f;
    }

    private float readVolume(ByteBuffer byteBuffer) {
        return ((float) byteBuffer.getShort()) / 256.0f;
    }

    private void writeFixed1616(ByteBuffer byteBuffer, float f2) {
        byteBuffer.putInt((int) (((double) f2) * 65536.0d));
    }

    private void writeFixed88(ByteBuffer byteBuffer, float f2) {
        byteBuffer.putShort((short) ((int) (((double) f2) * 256.0d)));
    }

    private void writeMatrix(ByteBuffer byteBuffer) {
        for (int i = 0; i < Math.min(9, this.matrix.length); i++) {
            byteBuffer.putInt(this.matrix[i]);
        }
        for (int min = Math.min(9, this.matrix.length); min < 9; min++) {
            byteBuffer.putInt(0);
        }
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void doWrite(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
        byteBuffer.putInt(TimeUtil.toMovTime(this.created));
        byteBuffer.putInt(TimeUtil.toMovTime(this.modified));
        byteBuffer.putInt(this.timescale);
        byteBuffer.putInt((int) this.duration);
        writeFixed1616(byteBuffer, this.rate);
        writeFixed88(byteBuffer, this.volume);
        byteBuffer.put(new byte[10]);
        writeMatrix(byteBuffer);
        byteBuffer.put(new byte[24]);
        byteBuffer.putInt(this.nextTrackId);
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return 144;
    }

    public long getCreated() {
        return this.created;
    }

    public long getDuration() {
        return this.duration;
    }

    public int[] getMatrix() {
        return this.matrix;
    }

    public long getModified() {
        return this.modified;
    }

    public int getNextTrackId() {
        return this.nextTrackId;
    }

    public float getRate() {
        return this.rate;
    }

    public int getTimescale() {
        return this.timescale;
    }

    public float getVolume() {
        return this.volume;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void parse(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
        byte b2 = ((FullBox) this).version;
        if (b2 == 0) {
            this.created = TimeUtil.fromMovTime(byteBuffer.getInt());
            this.modified = TimeUtil.fromMovTime(byteBuffer.getInt());
            this.timescale = byteBuffer.getInt();
            this.duration = (long) byteBuffer.getInt();
        } else if (b2 == 1) {
            this.created = TimeUtil.fromMovTime((int) byteBuffer.getLong());
            this.modified = TimeUtil.fromMovTime((int) byteBuffer.getLong());
            this.timescale = byteBuffer.getInt();
            this.duration = byteBuffer.getLong();
        } else {
            throw new RuntimeException("Unsupported version");
        }
        this.rate = readRate(byteBuffer);
        this.volume = readVolume(byteBuffer);
        NIOUtils.skip(byteBuffer, 10);
        this.matrix = readMatrix(byteBuffer);
        NIOUtils.skip(byteBuffer, 24);
        this.nextTrackId = byteBuffer.getInt();
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public void setNextTrackId(int i) {
        this.nextTrackId = i;
    }

    public void setTimescale(int i) {
        this.timescale = i;
    }
}
