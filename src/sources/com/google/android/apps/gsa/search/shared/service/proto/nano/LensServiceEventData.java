package com.google.android.apps.gsa.search.shared.service.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.ExtendableMessageNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class LensServiceEventData extends ExtendableMessageNano<LensServiceEventData> implements Cloneable {
    private static volatile LensServiceEventData[] _emptyArray;
    private int bitField0_;
    private int serviceApiVersion_;

    public LensServiceEventData() {
        clear();
    }

    public static LensServiceEventData[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new LensServiceEventData[0];
                }
            }
        }
        return _emptyArray;
    }

    public static LensServiceEventData parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new LensServiceEventData().mergeFrom(codedInputByteBufferNano);
    }

    public static LensServiceEventData parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        LensServiceEventData lensServiceEventData = new LensServiceEventData();
        MessageNano.mergeFrom(lensServiceEventData, bArr);
        return lensServiceEventData;
    }

    public LensServiceEventData clear() {
        this.bitField0_ = 0;
        this.serviceApiVersion_ = 0;
        ((ExtendableMessageNano) this).unknownFieldData = null;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public LensServiceEventData clearServiceApiVersion() {
        this.serviceApiVersion_ = 0;
        this.bitField0_ &= -2;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano, com.google.protobuf.nano.ExtendableMessageNano, com.google.protobuf.nano.ExtendableMessageNano, java.lang.Object
    public LensServiceEventData clone() {
        try {
            return (LensServiceEventData) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        return (this.bitField0_ & 1) != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, this.serviceApiVersion_) : computeSerializedSize;
    }

    public int getServiceApiVersion() {
        return this.serviceApiVersion_;
    }

    public boolean hasServiceApiVersion() {
        return (this.bitField0_ & 1) != 0;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public LensServiceEventData mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                this.serviceApiVersion_ = codedInputByteBufferNano.readInt32();
                this.bitField0_ |= 1;
            } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public LensServiceEventData setServiceApiVersion(int i) {
        this.bitField0_ |= 1;
        this.serviceApiVersion_ = i;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if ((this.bitField0_ & 1) != 0) {
            codedOutputByteBufferNano.writeInt32(1, this.serviceApiVersion_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}
