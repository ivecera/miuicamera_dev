package com.google.protobuf.nano;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class MessageNano {
    public static final int UNSET_ENUM_VALUE = Integer.MIN_VALUE;
    protected volatile int cachedSize = -1;

    public interface GeneratedMapEntry {
    }

    public static final <T extends MessageNano> T cloneUsingSerialization(T t) {
        try {
            T t2 = (MessageNano) t.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
            mergeFrom(t2, toByteArray(t));
            return t2;
        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException(e2);
        } catch (InstantiationException e3) {
            throw new IllegalStateException(e3);
        } catch (InvocationTargetException e4) {
            throw new IllegalStateException(e4);
        } catch (IllegalAccessException e5) {
            throw new IllegalStateException(e5);
        } catch (InvalidProtocolBufferNanoException e6) {
            throw new IllegalStateException(e6);
        }
    }

    public static final <T extends MessageNano> T mergeFrom(T t, byte[] bArr) throws InvalidProtocolBufferNanoException {
        mergeFrom(t, bArr, 0, bArr.length);
        return t;
    }

    public static final <T extends MessageNano> T mergeFrom(T t, byte[] bArr, int i, int i2) throws InvalidProtocolBufferNanoException {
        try {
            CodedInputByteBufferNano newInstance = CodedInputByteBufferNano.newInstance(bArr, i, i2);
            t.mergeFrom(newInstance);
            newInstance.checkLastTagWas(0);
            return t;
        } catch (InvalidProtocolBufferNanoException e2) {
            throw e2;
        } catch (IOException e3) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e3);
        }
    }

    public static final boolean messageNanoEquals(MessageNano messageNano, MessageNano messageNano2) {
        int serializedSize;
        if (messageNano == messageNano2) {
            return true;
        }
        if (messageNano == null || messageNano2 == null || messageNano.getClass() != messageNano2.getClass() || messageNano2.getSerializedSize() != (serializedSize = messageNano.getSerializedSize())) {
            return false;
        }
        byte[] bArr = new byte[serializedSize];
        byte[] bArr2 = new byte[serializedSize];
        toByteArray(messageNano, bArr, 0, serializedSize);
        toByteArray(messageNano2, bArr2, 0, serializedSize);
        return Arrays.equals(bArr, bArr2);
    }

    public static final void toByteArray(MessageNano messageNano, byte[] bArr, int i, int i2) {
        try {
            CodedOutputByteBufferNano newInstance = CodedOutputByteBufferNano.newInstance(bArr, i, i2);
            messageNano.writeTo(newInstance);
            newInstance.checkNoSpaceLeft();
        } catch (IOException e2) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e2);
        }
    }

    public static final byte[] toByteArray(MessageNano messageNano) {
        byte[] bArr = new byte[messageNano.getSerializedSize()];
        toByteArray(messageNano, bArr, 0, bArr.length);
        return bArr;
    }

    public MessageNano clone() throws CloneNotSupportedException {
        return (MessageNano) super.clone();
    }

    /* access modifiers changed from: protected */
    public int computeSerializedSize() {
        return 0;
    }

    public int getCachedSize() {
        if (this.cachedSize < 0) {
            getSerializedSize();
        }
        return this.cachedSize;
    }

    public int getSerializedSize() {
        int computeSerializedSize = computeSerializedSize();
        this.cachedSize = computeSerializedSize;
        return computeSerializedSize;
    }

    public abstract MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException;

    public String toString() {
        return MessageNanoPrinter.print(this);
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
    }
}
