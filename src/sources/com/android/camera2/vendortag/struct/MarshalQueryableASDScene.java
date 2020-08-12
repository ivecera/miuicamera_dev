package com.android.camera2.vendortag.struct;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

public class MarshalQueryableASDScene implements MarshalQueryable<ASDScene> {

    public static class ASDScene {
        public int type;
        public int value;

        public String toString() {
            return this.type + ",,," + this.value;
        }
    }

    public class MarshalerASDScene extends Marshaler<ASDScene> {
        private static final int SIZE = 8;

        protected MarshalerASDScene(TypeReference<ASDScene> typeReference, int i) {
            super(MarshalQueryableASDScene.this, typeReference, i);
        }

        public int getNativeSize() {
            return 8;
        }

        public void marshal(ASDScene aSDScene, ByteBuffer byteBuffer) {
            byteBuffer.putInt(aSDScene.type);
            byteBuffer.putInt(aSDScene.value);
        }

        public ASDScene unmarshal(ByteBuffer byteBuffer) {
            ASDScene aSDScene = new ASDScene();
            aSDScene.type = byteBuffer.getInt();
            aSDScene.value = byteBuffer.getInt();
            return aSDScene;
        }
    }

    public Marshaler<ASDScene> createMarshaler(TypeReference<ASDScene> typeReference, int i) {
        return new MarshalerASDScene(typeReference, i);
    }

    public boolean isTypeMappingSupported(TypeReference<ASDScene> typeReference, int i) {
        return ASDScene.class.equals(typeReference.getType());
    }
}
