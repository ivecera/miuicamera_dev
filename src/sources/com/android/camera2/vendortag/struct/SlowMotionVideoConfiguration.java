package com.android.camera2.vendortag.struct;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

public class SlowMotionVideoConfiguration {
    public final int batchNbr;
    public final int height;
    public final int maxFps;
    public final int width;

    public static class MarshalQueryableSlowMotionVideoConfiguration implements MarshalQueryable<SlowMotionVideoConfiguration> {

        private class SlowMotionVideoConfigurationMarshaler extends Marshaler<SlowMotionVideoConfiguration> {
            private static final int NATIVE_SIZE = 16;

            private SlowMotionVideoConfigurationMarshaler(MarshalQueryable<SlowMotionVideoConfiguration> marshalQueryable, TypeReference<SlowMotionVideoConfiguration> typeReference, int i) {
                super(marshalQueryable, typeReference, i);
            }

            public int getNativeSize() {
                return 16;
            }

            public void marshal(SlowMotionVideoConfiguration slowMotionVideoConfiguration, ByteBuffer byteBuffer) {
                byteBuffer.putInt(slowMotionVideoConfiguration.width);
                byteBuffer.putInt(slowMotionVideoConfiguration.height);
                byteBuffer.putInt(slowMotionVideoConfiguration.maxFps);
                byteBuffer.putInt(slowMotionVideoConfiguration.batchNbr);
            }

            public SlowMotionVideoConfiguration unmarshal(ByteBuffer byteBuffer) {
                return new SlowMotionVideoConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt());
            }
        }

        public Marshaler<SlowMotionVideoConfiguration> createMarshaler(TypeReference<SlowMotionVideoConfiguration> typeReference, int i) {
            return new SlowMotionVideoConfigurationMarshaler(this, typeReference, i);
        }

        public boolean isTypeMappingSupported(TypeReference<SlowMotionVideoConfiguration> typeReference, int i) {
            return i == 1 && SlowMotionVideoConfiguration.class.equals(typeReference.getType());
        }
    }

    public SlowMotionVideoConfiguration(int i, int i2, int i3, int i4) {
        this.width = i;
        this.height = i2;
        this.maxFps = i3;
        this.batchNbr = i4;
    }
}
