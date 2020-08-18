package com.bumptech.glide.load.resource.bitmap;

public abstract class DownsampleStrategy {
    public static final DownsampleStrategy AT_MOST = new b();
    public static final DownsampleStrategy CENTER_INSIDE = new c();
    public static final DownsampleStrategy DEFAULT = Uq;
    public static final DownsampleStrategy FIT_CENTER = new e();
    public static final DownsampleStrategy NONE = new f();
    public static final DownsampleStrategy Uq = new d();
    public static final DownsampleStrategy Vq = new a();
    public static final com.bumptech.glide.load.f<DownsampleStrategy> Wq = com.bumptech.glide.load.f.a("com.bumptech.glide.load.resource.bitmap.Downsampler.DownsampleStrategy", DEFAULT);

    public enum SampleSizeRounding {
        MEMORY,
        QUALITY
    }

    private static class a extends DownsampleStrategy {
        a() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public SampleSizeRounding a(int i, int i2, int i3, int i4) {
            return SampleSizeRounding.QUALITY;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public float b(int i, int i2, int i3, int i4) {
            int min = Math.min(i2 / i4, i / i3);
            if (min == 0) {
                return 1.0f;
            }
            return 1.0f / ((float) Integer.highestOneBit(min));
        }
    }

    private static class b extends DownsampleStrategy {
        b() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public SampleSizeRounding a(int i, int i2, int i3, int i4) {
            return SampleSizeRounding.MEMORY;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public float b(int i, int i2, int i3, int i4) {
            int ceil = (int) Math.ceil((double) Math.max(((float) i2) / ((float) i4), ((float) i) / ((float) i3)));
            int i5 = 1;
            int max = Math.max(1, Integer.highestOneBit(ceil));
            if (max >= ceil) {
                i5 = 0;
            }
            return 1.0f / ((float) (max << i5));
        }
    }

    private static class c extends DownsampleStrategy {
        c() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public SampleSizeRounding a(int i, int i2, int i3, int i4) {
            return SampleSizeRounding.QUALITY;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public float b(int i, int i2, int i3, int i4) {
            return Math.min(1.0f, DownsampleStrategy.FIT_CENTER.b(i, i2, i3, i4));
        }
    }

    private static class d extends DownsampleStrategy {
        d() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public SampleSizeRounding a(int i, int i2, int i3, int i4) {
            return SampleSizeRounding.QUALITY;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public float b(int i, int i2, int i3, int i4) {
            return Math.max(((float) i3) / ((float) i), ((float) i4) / ((float) i2));
        }
    }

    private static class e extends DownsampleStrategy {
        e() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public SampleSizeRounding a(int i, int i2, int i3, int i4) {
            return SampleSizeRounding.QUALITY;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public float b(int i, int i2, int i3, int i4) {
            return Math.min(((float) i3) / ((float) i), ((float) i4) / ((float) i2));
        }
    }

    private static class f extends DownsampleStrategy {
        f() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public SampleSizeRounding a(int i, int i2, int i3, int i4) {
            return SampleSizeRounding.QUALITY;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
        public float b(int i, int i2, int i3, int i4) {
            return 1.0f;
        }
    }

    public abstract SampleSizeRounding a(int i, int i2, int i3, int i4);

    public abstract float b(int i, int i2, int i3, int i4);
}
