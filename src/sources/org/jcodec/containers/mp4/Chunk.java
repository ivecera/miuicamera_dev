package org.jcodec.containers.mp4;

public class Chunk {
    private int entry;
    private long offset;
    private int sampleCount;
    private int sampleDur;
    private int[] sampleDurs;
    private int sampleSize;
    private int[] sampleSizes;
    private long startTv;

    public Chunk(long j, long j2, int i, int i2, int[] iArr, int i3, int[] iArr2, int i4) {
        this.offset = j;
        this.startTv = j2;
        this.sampleCount = i;
        this.sampleSize = i2;
        this.sampleSizes = iArr;
        this.sampleDur = i3;
        this.sampleDurs = iArr2;
        this.entry = i4;
    }

    public int getDuration() {
        int i = this.sampleDur;
        if (i > 0) {
            return i * this.sampleCount;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.sampleDurs;
            if (i2 >= iArr.length) {
                return i3;
            }
            i3 += iArr[i2];
            i2++;
        }
    }

    public int getEntry() {
        return this.entry;
    }

    public long getOffset() {
        return this.offset;
    }

    public int getSampleCount() {
        return this.sampleCount;
    }

    public int getSampleDur() {
        return this.sampleDur;
    }

    public int[] getSampleDurs() {
        return this.sampleDurs;
    }

    public int getSampleSize() {
        return this.sampleSize;
    }

    public int[] getSampleSizes() {
        return this.sampleSizes;
    }

    public long getSize() {
        int i = this.sampleSize;
        if (i > 0) {
            return (long) (i * this.sampleCount);
        }
        long j = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.sampleSizes;
            if (i2 >= iArr.length) {
                return j;
            }
            j += (long) iArr[i2];
            i2++;
        }
    }

    public long getStartTv() {
        return this.startTv;
    }
}
