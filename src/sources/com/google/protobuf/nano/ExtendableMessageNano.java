package com.google.protobuf.nano;

import com.google.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;

public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>> extends MessageNano {
    protected FieldArray unknownFieldData;

    private void storeUnknownFieldData(int i, UnknownFieldData unknownFieldData2) throws IOException {
        FieldData fieldData;
        FieldArray fieldArray = this.unknownFieldData;
        if (fieldArray == null) {
            this.unknownFieldData = new FieldArray();
            fieldData = null;
        } else {
            fieldData = fieldArray.get(i);
        }
        if (fieldData == null) {
            fieldData = new FieldData();
            this.unknownFieldData.put(i, fieldData);
        }
        fieldData.addUnknownField(unknownFieldData2);
    }

    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.MessageNano
    public M clone() throws CloneNotSupportedException {
        M clone = super.clone();
        InternalNano.cloneUnknownFieldData(this, clone);
        return clone;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        if (this.unknownFieldData == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.unknownFieldData.size(); i2++) {
            i += this.unknownFieldData.dataAt(i2).computeSerializedSize();
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int computeSerializedSizeAsMessageSet() {
        if (this.unknownFieldData == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.unknownFieldData.size(); i2++) {
            i += this.unknownFieldData.dataAt(i2).computeSerializedSizeAsMessageSet();
        }
        return i;
    }

    public final <T> T getExtension(Extension<M, T> extension) {
        FieldData fieldData;
        FieldArray fieldArray = this.unknownFieldData;
        if (fieldArray == null || (fieldData = fieldArray.get(WireFormatNano.getTagFieldNumber(extension.tag))) == null) {
            return null;
        }
        return fieldData.getValue(extension);
    }

    public final FieldArray getUnknownFieldArray() {
        return this.unknownFieldData;
    }

    public final boolean hasExtension(Extension<M, ?> extension) {
        FieldArray fieldArray = this.unknownFieldData;
        return (fieldArray == null || fieldArray.get(WireFormatNano.getTagFieldNumber(extension.tag)) == null) ? false : true;
    }

    public final <T> M setExtension(Extension<M, T> extension, T t) {
        int tagFieldNumber = WireFormatNano.getTagFieldNumber(extension.tag);
        FieldData fieldData = null;
        if (t == null) {
            FieldArray fieldArray = this.unknownFieldData;
            if (fieldArray != null) {
                fieldArray.remove(tagFieldNumber);
                if (this.unknownFieldData.isEmpty()) {
                    this.unknownFieldData = null;
                }
            }
        } else {
            FieldArray fieldArray2 = this.unknownFieldData;
            if (fieldArray2 == null) {
                this.unknownFieldData = new FieldArray();
            } else {
                fieldData = fieldArray2.get(tagFieldNumber);
            }
            if (fieldData == null) {
                this.unknownFieldData.put(tagFieldNumber, new FieldData(extension, t));
            } else {
                fieldData.setValue(extension, t);
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public final boolean storeUnknownField(CodedInputByteBufferNano codedInputByteBufferNano, int i) throws IOException {
        int position = codedInputByteBufferNano.getPosition();
        if (!codedInputByteBufferNano.skipField(i)) {
            return false;
        }
        storeUnknownFieldData(WireFormatNano.getTagFieldNumber(i), new UnknownFieldData(i, codedInputByteBufferNano.getData(position, codedInputByteBufferNano.getPosition() - position)));
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean storeUnknownFieldAsMessageSet(CodedInputByteBufferNano codedInputByteBufferNano, int i) throws IOException {
        if (i != WireFormatNano.MESSAGE_SET_ITEM_TAG) {
            return storeUnknownField(codedInputByteBufferNano, i);
        }
        int i2 = 0;
        byte[] bArr = null;
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == WireFormatNano.MESSAGE_SET_TYPE_ID_TAG) {
                i2 = codedInputByteBufferNano.readUInt32();
            } else if (readTag == WireFormatNano.MESSAGE_SET_MESSAGE_TAG) {
                int position = codedInputByteBufferNano.getPosition();
                codedInputByteBufferNano.skipField(readTag);
                bArr = codedInputByteBufferNano.getData(position, codedInputByteBufferNano.getPosition() - position);
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        codedInputByteBufferNano.checkLastTagWas(WireFormatNano.MESSAGE_SET_ITEM_END_TAG);
        if (bArr == null || i2 == 0) {
            return true;
        }
        storeUnknownFieldData(i2, new UnknownFieldData(i2, bArr));
        return true;
    }

    /* access modifiers changed from: protected */
    public void writeAsMessageSetTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.unknownFieldData != null) {
            for (int i = 0; i < this.unknownFieldData.size(); i++) {
                this.unknownFieldData.dataAt(i).writeAsMessageSetTo(codedOutputByteBufferNano);
            }
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.unknownFieldData != null) {
            for (int i = 0; i < this.unknownFieldData.size(); i++) {
                this.unknownFieldData.dataAt(i).writeTo(codedOutputByteBufferNano);
            }
        }
    }
}
