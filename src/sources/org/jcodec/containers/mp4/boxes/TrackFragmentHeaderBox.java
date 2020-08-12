package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class TrackFragmentHeaderBox extends FullBox {
    public static final int FLAG_BASE_DATA_OFFSET = 1;
    public static final int FLAG_DEFAILT_SAMPLE_DURATION = 8;
    public static final int FLAG_DEFAILT_SAMPLE_FLAGS = 32;
    public static final int FLAG_DEFAULT_SAMPLE_SIZE = 16;
    public static final int FLAG_SAMPLE_DESCRIPTION_INDEX = 2;
    /* access modifiers changed from: private */
    public long baseDataOffset;
    /* access modifiers changed from: private */
    public int defaultSampleDuration;
    /* access modifiers changed from: private */
    public int defaultSampleFlags;
    /* access modifiers changed from: private */
    public int defaultSampleSize;
    /* access modifiers changed from: private */
    public int sampleDescriptionIndex;
    private int trackId;

    public static class Factory {
        private TrackFragmentHeaderBox box;

        public Factory(TrackFragmentHeaderBox trackFragmentHeaderBox) {
            this.box = trackFragmentHeaderBox;
        }

        public Factory baseDataOffset(long j) {
            TrackFragmentHeaderBox trackFragmentHeaderBox = this.box;
            ((FullBox) trackFragmentHeaderBox).flags |= 1;
            long unused = trackFragmentHeaderBox.baseDataOffset = (long) ((int) j);
            return this;
        }

        public TrackFragmentHeaderBox create() {
            try {
                return this.box;
            } finally {
                this.box = null;
            }
        }

        public Factory defaultSampleDuration(long j) {
            TrackFragmentHeaderBox trackFragmentHeaderBox = this.box;
            ((FullBox) trackFragmentHeaderBox).flags |= 8;
            int unused = trackFragmentHeaderBox.defaultSampleDuration = (int) j;
            return this;
        }

        public Factory defaultSampleFlags(long j) {
            TrackFragmentHeaderBox trackFragmentHeaderBox = this.box;
            ((FullBox) trackFragmentHeaderBox).flags |= 32;
            int unused = trackFragmentHeaderBox.defaultSampleFlags = (int) j;
            return this;
        }

        public Factory defaultSampleSize(long j) {
            TrackFragmentHeaderBox trackFragmentHeaderBox = this.box;
            ((FullBox) trackFragmentHeaderBox).flags |= 16;
            int unused = trackFragmentHeaderBox.defaultSampleSize = (int) j;
            return this;
        }

        public Factory sampleDescriptionIndex(long j) {
            TrackFragmentHeaderBox trackFragmentHeaderBox = this.box;
            ((FullBox) trackFragmentHeaderBox).flags |= 2;
            int unused = trackFragmentHeaderBox.sampleDescriptionIndex = (int) j;
            return this;
        }
    }

    public TrackFragmentHeaderBox(Header header) {
        super(header);
    }

    public static Factory copy(TrackFragmentHeaderBox trackFragmentHeaderBox) {
        TrackFragmentHeaderBox tfhd = tfhd(trackFragmentHeaderBox.trackId, trackFragmentHeaderBox.baseDataOffset, trackFragmentHeaderBox.sampleDescriptionIndex, trackFragmentHeaderBox.defaultSampleDuration, trackFragmentHeaderBox.defaultSampleSize, trackFragmentHeaderBox.defaultSampleFlags);
        tfhd.setFlags(trackFragmentHeaderBox.getFlags());
        tfhd.setVersion(trackFragmentHeaderBox.getVersion());
        return new Factory(tfhd);
    }

    public static Factory create(int i) {
        return new Factory(createTrackFragmentHeaderBoxWithId(i));
    }

    public static TrackFragmentHeaderBox createTrackFragmentHeaderBox() {
        return new TrackFragmentHeaderBox(new Header(fourcc()));
    }

    public static TrackFragmentHeaderBox createTrackFragmentHeaderBoxWithId(int i) {
        TrackFragmentHeaderBox trackFragmentHeaderBox = new TrackFragmentHeaderBox(new Header(fourcc()));
        trackFragmentHeaderBox.trackId = i;
        return trackFragmentHeaderBox;
    }

    public static String fourcc() {
        return "tfhd";
    }

    public static TrackFragmentHeaderBox tfhd(int i, long j, int i2, int i3, int i4, int i5) {
        TrackFragmentHeaderBox trackFragmentHeaderBox = new TrackFragmentHeaderBox(new Header(fourcc()));
        trackFragmentHeaderBox.trackId = i;
        trackFragmentHeaderBox.baseDataOffset = j;
        trackFragmentHeaderBox.sampleDescriptionIndex = i2;
        trackFragmentHeaderBox.defaultSampleDuration = i3;
        trackFragmentHeaderBox.defaultSampleSize = i4;
        trackFragmentHeaderBox.defaultSampleFlags = i5;
        return trackFragmentHeaderBox;
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void doWrite(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
        byteBuffer.putInt(this.trackId);
        if (isBaseDataOffsetAvailable()) {
            byteBuffer.putLong(this.baseDataOffset);
        }
        if (isSampleDescriptionIndexAvailable()) {
            byteBuffer.putInt(this.sampleDescriptionIndex);
        }
        if (isDefaultSampleDurationAvailable()) {
            byteBuffer.putInt(this.defaultSampleDuration);
        }
        if (isDefaultSampleSizeAvailable()) {
            byteBuffer.putInt(this.defaultSampleSize);
        }
        if (isDefaultSampleFlagsAvailable()) {
            byteBuffer.putInt(this.defaultSampleFlags);
        }
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return 40;
    }

    public long getBaseDataOffset() {
        return this.baseDataOffset;
    }

    public int getDefaultSampleDuration() {
        return this.defaultSampleDuration;
    }

    public int getDefaultSampleFlags() {
        return this.defaultSampleFlags;
    }

    public int getDefaultSampleSize() {
        return this.defaultSampleSize;
    }

    public int getSampleDescriptionIndex() {
        return this.sampleDescriptionIndex;
    }

    public int getTrackId() {
        return this.trackId;
    }

    public boolean isBaseDataOffsetAvailable() {
        return (((FullBox) this).flags & 1) != 0;
    }

    public boolean isDefaultSampleDurationAvailable() {
        return (((FullBox) this).flags & 8) != 0;
    }

    public boolean isDefaultSampleFlagsAvailable() {
        return (((FullBox) this).flags & 32) != 0;
    }

    public boolean isDefaultSampleSizeAvailable() {
        return (((FullBox) this).flags & 16) != 0;
    }

    public boolean isSampleDescriptionIndexAvailable() {
        return (((FullBox) this).flags & 2) != 0;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void parse(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
        this.trackId = byteBuffer.getInt();
        if (isBaseDataOffsetAvailable()) {
            this.baseDataOffset = byteBuffer.getLong();
        }
        if (isSampleDescriptionIndexAvailable()) {
            this.sampleDescriptionIndex = byteBuffer.getInt();
        }
        if (isDefaultSampleDurationAvailable()) {
            this.defaultSampleDuration = byteBuffer.getInt();
        }
        if (isDefaultSampleSizeAvailable()) {
            this.defaultSampleSize = byteBuffer.getInt();
        }
        if (isDefaultSampleFlagsAvailable()) {
            this.defaultSampleFlags = byteBuffer.getInt();
        }
    }

    public void setDefaultSampleFlags(int i) {
        this.defaultSampleFlags = i;
    }

    public void setTrackId(int i) {
        this.trackId = i;
    }
}
