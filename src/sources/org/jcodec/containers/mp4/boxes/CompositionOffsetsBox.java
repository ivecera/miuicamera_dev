package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class CompositionOffsetsBox extends FullBox {
    private Entry[] entries;

    public static class Entry {
        public int count;
        public int offset;

        public Entry(int i, int i2) {
            this.count = i;
            this.offset = i2;
        }

        public int getCount() {
            return this.count;
        }

        public int getOffset() {
            return this.offset;
        }
    }

    public static class LongEntry {
        public long count;
        public long offset;

        public LongEntry(long j, long j2) {
            this.count = j;
            this.offset = j2;
        }

        public long getCount() {
            return this.count;
        }

        public long getOffset() {
            return this.offset;
        }
    }

    public CompositionOffsetsBox(Header header) {
        super(header);
    }

    public static CompositionOffsetsBox createCompositionOffsetsBox(Entry[] entryArr) {
        CompositionOffsetsBox compositionOffsetsBox = new CompositionOffsetsBox(new Header(fourcc()));
        compositionOffsetsBox.entries = entryArr;
        return compositionOffsetsBox;
    }

    public static String fourcc() {
        return "ctts";
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void doWrite(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
        byteBuffer.putInt(this.entries.length);
        int i = 0;
        while (true) {
            Entry[] entryArr = this.entries;
            if (i < entryArr.length) {
                byteBuffer.putInt(entryArr[i].count);
                byteBuffer.putInt(this.entries[i].offset);
                i++;
            } else {
                return;
            }
        }
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return (this.entries.length * 8) + 16;
    }

    public Entry[] getEntries() {
        return this.entries;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void parse(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
        int i = byteBuffer.getInt();
        this.entries = new Entry[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.entries[i2] = new Entry(byteBuffer.getInt(), byteBuffer.getInt());
        }
    }
}
