package com.android.camera2.vendortag.struct;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

public class MarshalQueryableSuperNightExif implements MarshalQueryable<SuperNightExif> {

    public class MarshalerSuperNightExif extends Marshaler<SuperNightExif> {
        private static final int SIZE = 20;

        protected MarshalerSuperNightExif(TypeReference<SuperNightExif> typeReference, int i) {
            super(MarshalQueryableSuperNightExif.this, typeReference, i);
        }

        public int getNativeSize() {
            return 20;
        }

        public void marshal(SuperNightExif superNightExif, ByteBuffer byteBuffer) {
            byteBuffer.putFloat(superNightExif.luxIndex);
            byteBuffer.putFloat(superNightExif.light);
            byteBuffer.putFloat(superNightExif.darkRatio);
            byteBuffer.putFloat(superNightExif.middleRatio);
            byteBuffer.putFloat(superNightExif.brightRatio);
        }

        public SuperNightExif unmarshal(ByteBuffer byteBuffer) {
            SuperNightExif superNightExif = new SuperNightExif();
            superNightExif.luxIndex = byteBuffer.getFloat();
            superNightExif.light = byteBuffer.getFloat();
            superNightExif.darkRatio = byteBuffer.getFloat();
            superNightExif.middleRatio = byteBuffer.getFloat();
            superNightExif.brightRatio = byteBuffer.getFloat();
            return superNightExif;
        }
    }

    public static class SuperNightExif {
        public float brightRatio;
        public float darkRatio;
        public float light;
        public float luxIndex;
        public float middleRatio;

        public String toString() {
            return "luxIndex:" + this.luxIndex + ",light:" + this.light + ",darkRatio:" + this.darkRatio + ",middleRatio:" + this.middleRatio + ",brightRatio:" + this.brightRatio;
        }
    }

    public Marshaler<SuperNightExif> createMarshaler(TypeReference<SuperNightExif> typeReference, int i) {
        return new MarshalerSuperNightExif(typeReference, i);
    }

    public boolean isTypeMappingSupported(TypeReference<SuperNightExif> typeReference, int i) {
        return SuperNightExif.class.equals(typeReference.getType());
    }
}
