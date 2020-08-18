package com.google.protobuf.nano;

import java.io.IOException;
import java.util.Arrays;

public abstract class DescriptorProtos {

    public static final class DescriptorProto extends ExtendableMessageNano<DescriptorProto> {
        private static volatile DescriptorProto[] _emptyArray;
        public EnumDescriptorProto[] enumType;
        public FieldDescriptorProto[] extension;
        public ExtensionRange[] extensionRange;
        public FieldDescriptorProto[] field;
        public String name;
        public DescriptorProto[] nestedType;
        public OneofDescriptorProto[] oneofDecl;
        public MessageOptions options;
        public String[] reservedName;
        public ReservedRange[] reservedRange;

        public static final class ExtensionRange extends ExtendableMessageNano<ExtensionRange> {
            private static volatile ExtensionRange[] _emptyArray;
            public int end;
            public ExtensionRangeOptions options;
            public int start;

            public ExtensionRange() {
                clear();
            }

            public static ExtensionRange[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new ExtensionRange[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static ExtensionRange parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ExtensionRange().mergeFrom(codedInputByteBufferNano);
            }

            public static ExtensionRange parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                ExtensionRange extensionRange = new ExtensionRange();
                MessageNano.mergeFrom(extensionRange, bArr);
                return extensionRange;
            }

            public ExtensionRange clear() {
                this.start = 0;
                this.end = 0;
                this.options = null;
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                int computeSerializedSize = super.computeSerializedSize();
                int i = this.start;
                if (i != 0) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
                }
                int i2 = this.end;
                if (i2 != 0) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
                }
                ExtensionRangeOptions extensionRangeOptions = this.options;
                return extensionRangeOptions != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(3, extensionRangeOptions) : computeSerializedSize;
            }

            @Override // com.google.protobuf.nano.MessageNano
            public ExtensionRange mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 8) {
                        this.start = codedInputByteBufferNano.readInt32();
                    } else if (readTag == 16) {
                        this.end = codedInputByteBufferNano.readInt32();
                    } else if (readTag == 26) {
                        if (this.options == null) {
                            this.options = new ExtensionRangeOptions();
                        }
                        codedInputByteBufferNano.readMessage(this.options);
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int i = this.start;
                if (i != 0) {
                    codedOutputByteBufferNano.writeInt32(1, i);
                }
                int i2 = this.end;
                if (i2 != 0) {
                    codedOutputByteBufferNano.writeInt32(2, i2);
                }
                ExtensionRangeOptions extensionRangeOptions = this.options;
                if (extensionRangeOptions != null) {
                    codedOutputByteBufferNano.writeMessage(3, extensionRangeOptions);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class ReservedRange extends ExtendableMessageNano<ReservedRange> {
            private static volatile ReservedRange[] _emptyArray;
            public int end;
            public int start;

            public ReservedRange() {
                clear();
            }

            public static ReservedRange[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new ReservedRange[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static ReservedRange parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ReservedRange().mergeFrom(codedInputByteBufferNano);
            }

            public static ReservedRange parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                ReservedRange reservedRange = new ReservedRange();
                MessageNano.mergeFrom(reservedRange, bArr);
                return reservedRange;
            }

            public ReservedRange clear() {
                this.start = 0;
                this.end = 0;
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                int computeSerializedSize = super.computeSerializedSize();
                int i = this.start;
                if (i != 0) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
                }
                int i2 = this.end;
                return i2 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, i2) : computeSerializedSize;
            }

            @Override // com.google.protobuf.nano.MessageNano
            public ReservedRange mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 8) {
                        this.start = codedInputByteBufferNano.readInt32();
                    } else if (readTag == 16) {
                        this.end = codedInputByteBufferNano.readInt32();
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int i = this.start;
                if (i != 0) {
                    codedOutputByteBufferNano.writeInt32(1, i);
                }
                int i2 = this.end;
                if (i2 != 0) {
                    codedOutputByteBufferNano.writeInt32(2, i2);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public DescriptorProto() {
            clear();
        }

        public static DescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static DescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static DescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            DescriptorProto descriptorProto = new DescriptorProto();
            MessageNano.mergeFrom(descriptorProto, bArr);
            return descriptorProto;
        }

        public DescriptorProto clear() {
            this.name = "";
            this.field = FieldDescriptorProto.emptyArray();
            this.extension = FieldDescriptorProto.emptyArray();
            this.nestedType = emptyArray();
            this.enumType = EnumDescriptorProto.emptyArray();
            this.extensionRange = ExtensionRange.emptyArray();
            this.oneofDecl = OneofDescriptorProto.emptyArray();
            this.options = null;
            this.reservedRange = ReservedRange.emptyArray();
            this.reservedName = WireFormatNano.EMPTY_STRING_ARRAY;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            FieldDescriptorProto[] fieldDescriptorProtoArr = this.field;
            int i = 0;
            if (fieldDescriptorProtoArr != null && fieldDescriptorProtoArr.length > 0) {
                int i2 = computeSerializedSize;
                int i3 = 0;
                while (true) {
                    FieldDescriptorProto[] fieldDescriptorProtoArr2 = this.field;
                    if (i3 >= fieldDescriptorProtoArr2.length) {
                        break;
                    }
                    FieldDescriptorProto fieldDescriptorProto = fieldDescriptorProtoArr2[i3];
                    if (fieldDescriptorProto != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(2, fieldDescriptorProto);
                    }
                    i3++;
                }
                computeSerializedSize = i2;
            }
            DescriptorProto[] descriptorProtoArr = this.nestedType;
            if (descriptorProtoArr != null && descriptorProtoArr.length > 0) {
                int i4 = computeSerializedSize;
                int i5 = 0;
                while (true) {
                    DescriptorProto[] descriptorProtoArr2 = this.nestedType;
                    if (i5 >= descriptorProtoArr2.length) {
                        break;
                    }
                    DescriptorProto descriptorProto = descriptorProtoArr2[i5];
                    if (descriptorProto != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(3, descriptorProto);
                    }
                    i5++;
                }
                computeSerializedSize = i4;
            }
            EnumDescriptorProto[] enumDescriptorProtoArr = this.enumType;
            if (enumDescriptorProtoArr != null && enumDescriptorProtoArr.length > 0) {
                int i6 = computeSerializedSize;
                int i7 = 0;
                while (true) {
                    EnumDescriptorProto[] enumDescriptorProtoArr2 = this.enumType;
                    if (i7 >= enumDescriptorProtoArr2.length) {
                        break;
                    }
                    EnumDescriptorProto enumDescriptorProto = enumDescriptorProtoArr2[i7];
                    if (enumDescriptorProto != null) {
                        i6 += CodedOutputByteBufferNano.computeMessageSize(4, enumDescriptorProto);
                    }
                    i7++;
                }
                computeSerializedSize = i6;
            }
            ExtensionRange[] extensionRangeArr = this.extensionRange;
            if (extensionRangeArr != null && extensionRangeArr.length > 0) {
                int i8 = computeSerializedSize;
                int i9 = 0;
                while (true) {
                    ExtensionRange[] extensionRangeArr2 = this.extensionRange;
                    if (i9 >= extensionRangeArr2.length) {
                        break;
                    }
                    ExtensionRange extensionRange2 = extensionRangeArr2[i9];
                    if (extensionRange2 != null) {
                        i8 += CodedOutputByteBufferNano.computeMessageSize(5, extensionRange2);
                    }
                    i9++;
                }
                computeSerializedSize = i8;
            }
            FieldDescriptorProto[] fieldDescriptorProtoArr3 = this.extension;
            if (fieldDescriptorProtoArr3 != null && fieldDescriptorProtoArr3.length > 0) {
                int i10 = computeSerializedSize;
                int i11 = 0;
                while (true) {
                    FieldDescriptorProto[] fieldDescriptorProtoArr4 = this.extension;
                    if (i11 >= fieldDescriptorProtoArr4.length) {
                        break;
                    }
                    FieldDescriptorProto fieldDescriptorProto2 = fieldDescriptorProtoArr4[i11];
                    if (fieldDescriptorProto2 != null) {
                        i10 += CodedOutputByteBufferNano.computeMessageSize(6, fieldDescriptorProto2);
                    }
                    i11++;
                }
                computeSerializedSize = i10;
            }
            MessageOptions messageOptions = this.options;
            if (messageOptions != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, messageOptions);
            }
            OneofDescriptorProto[] oneofDescriptorProtoArr = this.oneofDecl;
            if (oneofDescriptorProtoArr != null && oneofDescriptorProtoArr.length > 0) {
                int i12 = computeSerializedSize;
                int i13 = 0;
                while (true) {
                    OneofDescriptorProto[] oneofDescriptorProtoArr2 = this.oneofDecl;
                    if (i13 >= oneofDescriptorProtoArr2.length) {
                        break;
                    }
                    OneofDescriptorProto oneofDescriptorProto = oneofDescriptorProtoArr2[i13];
                    if (oneofDescriptorProto != null) {
                        i12 += CodedOutputByteBufferNano.computeMessageSize(8, oneofDescriptorProto);
                    }
                    i13++;
                }
                computeSerializedSize = i12;
            }
            ReservedRange[] reservedRangeArr = this.reservedRange;
            if (reservedRangeArr != null && reservedRangeArr.length > 0) {
                int i14 = computeSerializedSize;
                int i15 = 0;
                while (true) {
                    ReservedRange[] reservedRangeArr2 = this.reservedRange;
                    if (i15 >= reservedRangeArr2.length) {
                        break;
                    }
                    ReservedRange reservedRange2 = reservedRangeArr2[i15];
                    if (reservedRange2 != null) {
                        i14 += CodedOutputByteBufferNano.computeMessageSize(9, reservedRange2);
                    }
                    i15++;
                }
                computeSerializedSize = i14;
            }
            String[] strArr = this.reservedName;
            if (strArr == null || strArr.length <= 0) {
                return computeSerializedSize;
            }
            int i16 = 0;
            int i17 = 0;
            while (true) {
                String[] strArr2 = this.reservedName;
                if (i >= strArr2.length) {
                    return computeSerializedSize + i16 + (i17 * 1);
                }
                String str2 = strArr2[i];
                if (str2 != null) {
                    i17++;
                    i16 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                }
                i++;
            }
        }

        @Override // com.google.protobuf.nano.MessageNano
        public DescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.name = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                        FieldDescriptorProto[] fieldDescriptorProtoArr = this.field;
                        int length = fieldDescriptorProtoArr == null ? 0 : fieldDescriptorProtoArr.length;
                        FieldDescriptorProto[] fieldDescriptorProtoArr2 = new FieldDescriptorProto[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.field, 0, fieldDescriptorProtoArr2, 0, length);
                        }
                        while (length < fieldDescriptorProtoArr2.length - 1) {
                            fieldDescriptorProtoArr2[length] = new FieldDescriptorProto();
                            codedInputByteBufferNano.readMessage(fieldDescriptorProtoArr2[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        fieldDescriptorProtoArr2[length] = new FieldDescriptorProto();
                        codedInputByteBufferNano.readMessage(fieldDescriptorProtoArr2[length]);
                        this.field = fieldDescriptorProtoArr2;
                        break;
                    case 26:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        DescriptorProto[] descriptorProtoArr = this.nestedType;
                        int length2 = descriptorProtoArr == null ? 0 : descriptorProtoArr.length;
                        DescriptorProto[] descriptorProtoArr2 = new DescriptorProto[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.nestedType, 0, descriptorProtoArr2, 0, length2);
                        }
                        while (length2 < descriptorProtoArr2.length - 1) {
                            descriptorProtoArr2[length2] = new DescriptorProto();
                            codedInputByteBufferNano.readMessage(descriptorProtoArr2[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        descriptorProtoArr2[length2] = new DescriptorProto();
                        codedInputByteBufferNano.readMessage(descriptorProtoArr2[length2]);
                        this.nestedType = descriptorProtoArr2;
                        break;
                    case 34:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        EnumDescriptorProto[] enumDescriptorProtoArr = this.enumType;
                        int length3 = enumDescriptorProtoArr == null ? 0 : enumDescriptorProtoArr.length;
                        EnumDescriptorProto[] enumDescriptorProtoArr2 = new EnumDescriptorProto[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.enumType, 0, enumDescriptorProtoArr2, 0, length3);
                        }
                        while (length3 < enumDescriptorProtoArr2.length - 1) {
                            enumDescriptorProtoArr2[length3] = new EnumDescriptorProto();
                            codedInputByteBufferNano.readMessage(enumDescriptorProtoArr2[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        enumDescriptorProtoArr2[length3] = new EnumDescriptorProto();
                        codedInputByteBufferNano.readMessage(enumDescriptorProtoArr2[length3]);
                        this.enumType = enumDescriptorProtoArr2;
                        break;
                    case 42:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                        ExtensionRange[] extensionRangeArr = this.extensionRange;
                        int length4 = extensionRangeArr == null ? 0 : extensionRangeArr.length;
                        ExtensionRange[] extensionRangeArr2 = new ExtensionRange[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.extensionRange, 0, extensionRangeArr2, 0, length4);
                        }
                        while (length4 < extensionRangeArr2.length - 1) {
                            extensionRangeArr2[length4] = new ExtensionRange();
                            codedInputByteBufferNano.readMessage(extensionRangeArr2[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        extensionRangeArr2[length4] = new ExtensionRange();
                        codedInputByteBufferNano.readMessage(extensionRangeArr2[length4]);
                        this.extensionRange = extensionRangeArr2;
                        break;
                    case 50:
                        int repeatedFieldArrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                        FieldDescriptorProto[] fieldDescriptorProtoArr3 = this.extension;
                        int length5 = fieldDescriptorProtoArr3 == null ? 0 : fieldDescriptorProtoArr3.length;
                        FieldDescriptorProto[] fieldDescriptorProtoArr4 = new FieldDescriptorProto[(repeatedFieldArrayLength5 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.extension, 0, fieldDescriptorProtoArr4, 0, length5);
                        }
                        while (length5 < fieldDescriptorProtoArr4.length - 1) {
                            fieldDescriptorProtoArr4[length5] = new FieldDescriptorProto();
                            codedInputByteBufferNano.readMessage(fieldDescriptorProtoArr4[length5]);
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        fieldDescriptorProtoArr4[length5] = new FieldDescriptorProto();
                        codedInputByteBufferNano.readMessage(fieldDescriptorProtoArr4[length5]);
                        this.extension = fieldDescriptorProtoArr4;
                        break;
                    case 58:
                        if (this.options == null) {
                            this.options = new MessageOptions();
                        }
                        codedInputByteBufferNano.readMessage(this.options);
                        break;
                    case 66:
                        int repeatedFieldArrayLength6 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        OneofDescriptorProto[] oneofDescriptorProtoArr = this.oneofDecl;
                        int length6 = oneofDescriptorProtoArr == null ? 0 : oneofDescriptorProtoArr.length;
                        OneofDescriptorProto[] oneofDescriptorProtoArr2 = new OneofDescriptorProto[(repeatedFieldArrayLength6 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.oneofDecl, 0, oneofDescriptorProtoArr2, 0, length6);
                        }
                        while (length6 < oneofDescriptorProtoArr2.length - 1) {
                            oneofDescriptorProtoArr2[length6] = new OneofDescriptorProto();
                            codedInputByteBufferNano.readMessage(oneofDescriptorProtoArr2[length6]);
                            codedInputByteBufferNano.readTag();
                            length6++;
                        }
                        oneofDescriptorProtoArr2[length6] = new OneofDescriptorProto();
                        codedInputByteBufferNano.readMessage(oneofDescriptorProtoArr2[length6]);
                        this.oneofDecl = oneofDescriptorProtoArr2;
                        break;
                    case 74:
                        int repeatedFieldArrayLength7 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        ReservedRange[] reservedRangeArr = this.reservedRange;
                        int length7 = reservedRangeArr == null ? 0 : reservedRangeArr.length;
                        ReservedRange[] reservedRangeArr2 = new ReservedRange[(repeatedFieldArrayLength7 + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.reservedRange, 0, reservedRangeArr2, 0, length7);
                        }
                        while (length7 < reservedRangeArr2.length - 1) {
                            reservedRangeArr2[length7] = new ReservedRange();
                            codedInputByteBufferNano.readMessage(reservedRangeArr2[length7]);
                            codedInputByteBufferNano.readTag();
                            length7++;
                        }
                        reservedRangeArr2[length7] = new ReservedRange();
                        codedInputByteBufferNano.readMessage(reservedRangeArr2[length7]);
                        this.reservedRange = reservedRangeArr2;
                        break;
                    case 82:
                        int repeatedFieldArrayLength8 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        String[] strArr = this.reservedName;
                        int length8 = strArr == null ? 0 : strArr.length;
                        String[] strArr2 = new String[(repeatedFieldArrayLength8 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.reservedName, 0, strArr2, 0, length8);
                        }
                        while (length8 < strArr2.length - 1) {
                            strArr2[length8] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length8++;
                        }
                        strArr2[length8] = codedInputByteBufferNano.readString();
                        this.reservedName = strArr2;
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            FieldDescriptorProto[] fieldDescriptorProtoArr = this.field;
            int i = 0;
            if (fieldDescriptorProtoArr != null && fieldDescriptorProtoArr.length > 0) {
                int i2 = 0;
                while (true) {
                    FieldDescriptorProto[] fieldDescriptorProtoArr2 = this.field;
                    if (i2 >= fieldDescriptorProtoArr2.length) {
                        break;
                    }
                    FieldDescriptorProto fieldDescriptorProto = fieldDescriptorProtoArr2[i2];
                    if (fieldDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(2, fieldDescriptorProto);
                    }
                    i2++;
                }
            }
            DescriptorProto[] descriptorProtoArr = this.nestedType;
            if (descriptorProtoArr != null && descriptorProtoArr.length > 0) {
                int i3 = 0;
                while (true) {
                    DescriptorProto[] descriptorProtoArr2 = this.nestedType;
                    if (i3 >= descriptorProtoArr2.length) {
                        break;
                    }
                    DescriptorProto descriptorProto = descriptorProtoArr2[i3];
                    if (descriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(3, descriptorProto);
                    }
                    i3++;
                }
            }
            EnumDescriptorProto[] enumDescriptorProtoArr = this.enumType;
            if (enumDescriptorProtoArr != null && enumDescriptorProtoArr.length > 0) {
                int i4 = 0;
                while (true) {
                    EnumDescriptorProto[] enumDescriptorProtoArr2 = this.enumType;
                    if (i4 >= enumDescriptorProtoArr2.length) {
                        break;
                    }
                    EnumDescriptorProto enumDescriptorProto = enumDescriptorProtoArr2[i4];
                    if (enumDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(4, enumDescriptorProto);
                    }
                    i4++;
                }
            }
            ExtensionRange[] extensionRangeArr = this.extensionRange;
            if (extensionRangeArr != null && extensionRangeArr.length > 0) {
                int i5 = 0;
                while (true) {
                    ExtensionRange[] extensionRangeArr2 = this.extensionRange;
                    if (i5 >= extensionRangeArr2.length) {
                        break;
                    }
                    ExtensionRange extensionRange2 = extensionRangeArr2[i5];
                    if (extensionRange2 != null) {
                        codedOutputByteBufferNano.writeMessage(5, extensionRange2);
                    }
                    i5++;
                }
            }
            FieldDescriptorProto[] fieldDescriptorProtoArr3 = this.extension;
            if (fieldDescriptorProtoArr3 != null && fieldDescriptorProtoArr3.length > 0) {
                int i6 = 0;
                while (true) {
                    FieldDescriptorProto[] fieldDescriptorProtoArr4 = this.extension;
                    if (i6 >= fieldDescriptorProtoArr4.length) {
                        break;
                    }
                    FieldDescriptorProto fieldDescriptorProto2 = fieldDescriptorProtoArr4[i6];
                    if (fieldDescriptorProto2 != null) {
                        codedOutputByteBufferNano.writeMessage(6, fieldDescriptorProto2);
                    }
                    i6++;
                }
            }
            MessageOptions messageOptions = this.options;
            if (messageOptions != null) {
                codedOutputByteBufferNano.writeMessage(7, messageOptions);
            }
            OneofDescriptorProto[] oneofDescriptorProtoArr = this.oneofDecl;
            if (oneofDescriptorProtoArr != null && oneofDescriptorProtoArr.length > 0) {
                int i7 = 0;
                while (true) {
                    OneofDescriptorProto[] oneofDescriptorProtoArr2 = this.oneofDecl;
                    if (i7 >= oneofDescriptorProtoArr2.length) {
                        break;
                    }
                    OneofDescriptorProto oneofDescriptorProto = oneofDescriptorProtoArr2[i7];
                    if (oneofDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(8, oneofDescriptorProto);
                    }
                    i7++;
                }
            }
            ReservedRange[] reservedRangeArr = this.reservedRange;
            if (reservedRangeArr != null && reservedRangeArr.length > 0) {
                int i8 = 0;
                while (true) {
                    ReservedRange[] reservedRangeArr2 = this.reservedRange;
                    if (i8 >= reservedRangeArr2.length) {
                        break;
                    }
                    ReservedRange reservedRange2 = reservedRangeArr2[i8];
                    if (reservedRange2 != null) {
                        codedOutputByteBufferNano.writeMessage(9, reservedRange2);
                    }
                    i8++;
                }
            }
            String[] strArr = this.reservedName;
            if (strArr != null && strArr.length > 0) {
                while (true) {
                    String[] strArr2 = this.reservedName;
                    if (i >= strArr2.length) {
                        break;
                    }
                    String str2 = strArr2[i];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(10, str2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class EnumDescriptorProto extends ExtendableMessageNano<EnumDescriptorProto> {
        private static volatile EnumDescriptorProto[] _emptyArray;
        public String name;
        public EnumOptions options;
        public String[] reservedName;
        public EnumReservedRange[] reservedRange;
        public EnumValueDescriptorProto[] value;

        public static final class EnumReservedRange extends ExtendableMessageNano<EnumReservedRange> {
            private static volatile EnumReservedRange[] _emptyArray;
            public int end;
            public int start;

            public EnumReservedRange() {
                clear();
            }

            public static EnumReservedRange[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new EnumReservedRange[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static EnumReservedRange parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new EnumReservedRange().mergeFrom(codedInputByteBufferNano);
            }

            public static EnumReservedRange parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                EnumReservedRange enumReservedRange = new EnumReservedRange();
                MessageNano.mergeFrom(enumReservedRange, bArr);
                return enumReservedRange;
            }

            public EnumReservedRange clear() {
                this.start = 0;
                this.end = 0;
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                int computeSerializedSize = super.computeSerializedSize();
                int i = this.start;
                if (i != 0) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
                }
                int i2 = this.end;
                return i2 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, i2) : computeSerializedSize;
            }

            @Override // com.google.protobuf.nano.MessageNano
            public EnumReservedRange mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 8) {
                        this.start = codedInputByteBufferNano.readInt32();
                    } else if (readTag == 16) {
                        this.end = codedInputByteBufferNano.readInt32();
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int i = this.start;
                if (i != 0) {
                    codedOutputByteBufferNano.writeInt32(1, i);
                }
                int i2 = this.end;
                if (i2 != 0) {
                    codedOutputByteBufferNano.writeInt32(2, i2);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public EnumDescriptorProto() {
            clear();
        }

        public static EnumDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new EnumDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static EnumDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new EnumDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static EnumDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            EnumDescriptorProto enumDescriptorProto = new EnumDescriptorProto();
            MessageNano.mergeFrom(enumDescriptorProto, bArr);
            return enumDescriptorProto;
        }

        public EnumDescriptorProto clear() {
            this.name = "";
            this.value = EnumValueDescriptorProto.emptyArray();
            this.options = null;
            this.reservedRange = EnumReservedRange.emptyArray();
            this.reservedName = WireFormatNano.EMPTY_STRING_ARRAY;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            EnumValueDescriptorProto[] enumValueDescriptorProtoArr = this.value;
            int i = 0;
            if (enumValueDescriptorProtoArr != null && enumValueDescriptorProtoArr.length > 0) {
                int i2 = computeSerializedSize;
                int i3 = 0;
                while (true) {
                    EnumValueDescriptorProto[] enumValueDescriptorProtoArr2 = this.value;
                    if (i3 >= enumValueDescriptorProtoArr2.length) {
                        break;
                    }
                    EnumValueDescriptorProto enumValueDescriptorProto = enumValueDescriptorProtoArr2[i3];
                    if (enumValueDescriptorProto != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(2, enumValueDescriptorProto);
                    }
                    i3++;
                }
                computeSerializedSize = i2;
            }
            EnumOptions enumOptions = this.options;
            if (enumOptions != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, enumOptions);
            }
            EnumReservedRange[] enumReservedRangeArr = this.reservedRange;
            if (enumReservedRangeArr != null && enumReservedRangeArr.length > 0) {
                int i4 = computeSerializedSize;
                int i5 = 0;
                while (true) {
                    EnumReservedRange[] enumReservedRangeArr2 = this.reservedRange;
                    if (i5 >= enumReservedRangeArr2.length) {
                        break;
                    }
                    EnumReservedRange enumReservedRange = enumReservedRangeArr2[i5];
                    if (enumReservedRange != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(4, enumReservedRange);
                    }
                    i5++;
                }
                computeSerializedSize = i4;
            }
            String[] strArr = this.reservedName;
            if (strArr == null || strArr.length <= 0) {
                return computeSerializedSize;
            }
            int i6 = 0;
            int i7 = 0;
            while (true) {
                String[] strArr2 = this.reservedName;
                if (i >= strArr2.length) {
                    return computeSerializedSize + i6 + (i7 * 1);
                }
                String str2 = strArr2[i];
                if (str2 != null) {
                    i7++;
                    i6 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                }
                i++;
            }
        }

        @Override // com.google.protobuf.nano.MessageNano
        public EnumDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    EnumValueDescriptorProto[] enumValueDescriptorProtoArr = this.value;
                    int length = enumValueDescriptorProtoArr == null ? 0 : enumValueDescriptorProtoArr.length;
                    EnumValueDescriptorProto[] enumValueDescriptorProtoArr2 = new EnumValueDescriptorProto[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.value, 0, enumValueDescriptorProtoArr2, 0, length);
                    }
                    while (length < enumValueDescriptorProtoArr2.length - 1) {
                        enumValueDescriptorProtoArr2[length] = new EnumValueDescriptorProto();
                        codedInputByteBufferNano.readMessage(enumValueDescriptorProtoArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    enumValueDescriptorProtoArr2[length] = new EnumValueDescriptorProto();
                    codedInputByteBufferNano.readMessage(enumValueDescriptorProtoArr2[length]);
                    this.value = enumValueDescriptorProtoArr2;
                } else if (readTag == 26) {
                    if (this.options == null) {
                        this.options = new EnumOptions();
                    }
                    codedInputByteBufferNano.readMessage(this.options);
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    EnumReservedRange[] enumReservedRangeArr = this.reservedRange;
                    int length2 = enumReservedRangeArr == null ? 0 : enumReservedRangeArr.length;
                    EnumReservedRange[] enumReservedRangeArr2 = new EnumReservedRange[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.reservedRange, 0, enumReservedRangeArr2, 0, length2);
                    }
                    while (length2 < enumReservedRangeArr2.length - 1) {
                        enumReservedRangeArr2[length2] = new EnumReservedRange();
                        codedInputByteBufferNano.readMessage(enumReservedRangeArr2[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    enumReservedRangeArr2[length2] = new EnumReservedRange();
                    codedInputByteBufferNano.readMessage(enumReservedRangeArr2[length2]);
                    this.reservedRange = enumReservedRangeArr2;
                } else if (readTag == 42) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                    String[] strArr = this.reservedName;
                    int length3 = strArr == null ? 0 : strArr.length;
                    String[] strArr2 = new String[(repeatedFieldArrayLength3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.reservedName, 0, strArr2, 0, length3);
                    }
                    while (length3 < strArr2.length - 1) {
                        strArr2[length3] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    strArr2[length3] = codedInputByteBufferNano.readString();
                    this.reservedName = strArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            EnumValueDescriptorProto[] enumValueDescriptorProtoArr = this.value;
            int i = 0;
            if (enumValueDescriptorProtoArr != null && enumValueDescriptorProtoArr.length > 0) {
                int i2 = 0;
                while (true) {
                    EnumValueDescriptorProto[] enumValueDescriptorProtoArr2 = this.value;
                    if (i2 >= enumValueDescriptorProtoArr2.length) {
                        break;
                    }
                    EnumValueDescriptorProto enumValueDescriptorProto = enumValueDescriptorProtoArr2[i2];
                    if (enumValueDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(2, enumValueDescriptorProto);
                    }
                    i2++;
                }
            }
            EnumOptions enumOptions = this.options;
            if (enumOptions != null) {
                codedOutputByteBufferNano.writeMessage(3, enumOptions);
            }
            EnumReservedRange[] enumReservedRangeArr = this.reservedRange;
            if (enumReservedRangeArr != null && enumReservedRangeArr.length > 0) {
                int i3 = 0;
                while (true) {
                    EnumReservedRange[] enumReservedRangeArr2 = this.reservedRange;
                    if (i3 >= enumReservedRangeArr2.length) {
                        break;
                    }
                    EnumReservedRange enumReservedRange = enumReservedRangeArr2[i3];
                    if (enumReservedRange != null) {
                        codedOutputByteBufferNano.writeMessage(4, enumReservedRange);
                    }
                    i3++;
                }
            }
            String[] strArr = this.reservedName;
            if (strArr != null && strArr.length > 0) {
                while (true) {
                    String[] strArr2 = this.reservedName;
                    if (i >= strArr2.length) {
                        break;
                    }
                    String str2 = strArr2[i];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(5, str2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class EnumOptions extends ExtendableMessageNano<EnumOptions> {
        private static volatile EnumOptions[] _emptyArray;
        public boolean allowAlias;
        public boolean deprecated;
        public String proto1Name;
        public UninterpretedOption[] uninterpretedOption;

        public EnumOptions() {
            clear();
        }

        public static EnumOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new EnumOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static EnumOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new EnumOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static EnumOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            EnumOptions enumOptions = new EnumOptions();
            MessageNano.mergeFrom(enumOptions, bArr);
            return enumOptions;
        }

        public EnumOptions clear() {
            this.proto1Name = "";
            this.allowAlias = false;
            this.deprecated = false;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.proto1Name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.proto1Name);
            }
            boolean z = this.allowAlias;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(2, z);
            }
            boolean z2 = this.deprecated;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(3, z2);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public EnumOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.proto1Name = codedInputByteBufferNano.readString();
                } else if (readTag == 16) {
                    this.allowAlias = codedInputByteBufferNano.readBool();
                } else if (readTag == 24) {
                    this.deprecated = codedInputByteBufferNano.readBool();
                } else if (readTag == 7994) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                    UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                    int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                    UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                    }
                    while (length < uninterpretedOptionArr2.length - 1) {
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    uninterpretedOptionArr2[length] = new UninterpretedOption();
                    codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                    this.uninterpretedOption = uninterpretedOptionArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.proto1Name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.proto1Name);
            }
            boolean z = this.allowAlias;
            if (z) {
                codedOutputByteBufferNano.writeBool(2, z);
            }
            boolean z2 = this.deprecated;
            if (z2) {
                codedOutputByteBufferNano.writeBool(3, z2);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class EnumValueDescriptorProto extends ExtendableMessageNano<EnumValueDescriptorProto> {
        private static volatile EnumValueDescriptorProto[] _emptyArray;
        public String name;
        public int number;
        public EnumValueOptions options;

        public EnumValueDescriptorProto() {
            clear();
        }

        public static EnumValueDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new EnumValueDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new EnumValueDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            EnumValueDescriptorProto enumValueDescriptorProto = new EnumValueDescriptorProto();
            MessageNano.mergeFrom(enumValueDescriptorProto, bArr);
            return enumValueDescriptorProto;
        }

        public EnumValueDescriptorProto clear() {
            this.name = "";
            this.number = 0;
            this.options = null;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            int i = this.number;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            EnumValueOptions enumValueOptions = this.options;
            return enumValueOptions != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(3, enumValueOptions) : computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public EnumValueDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 16) {
                    this.number = codedInputByteBufferNano.readInt32();
                } else if (readTag == 26) {
                    if (this.options == null) {
                        this.options = new EnumValueOptions();
                    }
                    codedInputByteBufferNano.readMessage(this.options);
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            int i = this.number;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            EnumValueOptions enumValueOptions = this.options;
            if (enumValueOptions != null) {
                codedOutputByteBufferNano.writeMessage(3, enumValueOptions);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class EnumValueOptions extends ExtendableMessageNano<EnumValueOptions> {
        private static volatile EnumValueOptions[] _emptyArray;
        public boolean deprecated;
        public UninterpretedOption[] uninterpretedOption;

        public EnumValueOptions() {
            clear();
        }

        public static EnumValueOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new EnumValueOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static EnumValueOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new EnumValueOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static EnumValueOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            EnumValueOptions enumValueOptions = new EnumValueOptions();
            MessageNano.mergeFrom(enumValueOptions, bArr);
            return enumValueOptions;
        }

        public EnumValueOptions clear() {
            this.deprecated = false;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            boolean z = this.deprecated;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(1, z);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public EnumValueOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.deprecated = codedInputByteBufferNano.readBool();
                } else if (readTag == 7994) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                    UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                    int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                    UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                    }
                    while (length < uninterpretedOptionArr2.length - 1) {
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    uninterpretedOptionArr2[length] = new UninterpretedOption();
                    codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                    this.uninterpretedOption = uninterpretedOptionArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            boolean z = this.deprecated;
            if (z) {
                codedOutputByteBufferNano.writeBool(1, z);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ExtensionRangeOptions extends ExtendableMessageNano<ExtensionRangeOptions> {
        private static volatile ExtensionRangeOptions[] _emptyArray;
        public UninterpretedOption[] uninterpretedOption;

        public ExtensionRangeOptions() {
            clear();
        }

        public static ExtensionRangeOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ExtensionRangeOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static ExtensionRangeOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ExtensionRangeOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static ExtensionRangeOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            ExtensionRangeOptions extensionRangeOptions = new ExtensionRangeOptions();
            MessageNano.mergeFrom(extensionRangeOptions, bArr);
            return extensionRangeOptions;
        }

        public ExtensionRangeOptions clear() {
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public ExtensionRangeOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 7994) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                    UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                    int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                    UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                    }
                    while (length < uninterpretedOptionArr2.length - 1) {
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    uninterpretedOptionArr2[length] = new UninterpretedOption();
                    codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                    this.uninterpretedOption = uninterpretedOptionArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class FieldDescriptorProto extends ExtendableMessageNano<FieldDescriptorProto> {
        private static volatile FieldDescriptorProto[] _emptyArray;
        public String defaultValue;
        public String extendee;
        public String jsonName;
        @NanoEnumValue(legacy = false, value = Label.class)
        public int label;
        public String name;
        public int number;
        public int oneofIndex;
        public FieldOptions options;
        @NanoEnumValue(legacy = false, value = Type.class)
        public int type;
        public String typeName;

        public interface Label {
            @NanoEnumValue(legacy = false, value = Label.class)
            public static final int LABEL_OPTIONAL = 1;
            @NanoEnumValue(legacy = false, value = Label.class)
            public static final int LABEL_REPEATED = 3;
            @NanoEnumValue(legacy = false, value = Label.class)
            public static final int LABEL_REQUIRED = 2;
        }

        public interface Type {
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_BOOL = 8;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_BYTES = 12;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_DOUBLE = 1;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_ENUM = 14;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_FIXED32 = 7;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_FIXED64 = 6;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_FLOAT = 2;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_GROUP = 10;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_INT32 = 5;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_INT64 = 3;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_MESSAGE = 11;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_SFIXED32 = 15;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_SFIXED64 = 16;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_SINT32 = 17;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_SINT64 = 18;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_STRING = 9;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_UINT32 = 13;
            @NanoEnumValue(legacy = false, value = Type.class)
            public static final int TYPE_UINT64 = 4;
        }

        public FieldDescriptorProto() {
            clear();
        }

        @NanoEnumValue(legacy = false, value = Label.class)
        public static int checkLabelOrThrow(int i) {
            if (i >= 1 && i <= 3) {
                return i;
            }
            StringBuilder sb = new StringBuilder(37);
            sb.append(i);
            sb.append(" is not a valid enum Label");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = Label.class)
        public static int[] checkLabelOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkLabelOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = Type.class)
        public static int checkTypeOrThrow(int i) {
            if (i >= 1 && i <= 18) {
                return i;
            }
            StringBuilder sb = new StringBuilder(36);
            sb.append(i);
            sb.append(" is not a valid enum Type");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = Type.class)
        public static int[] checkTypeOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkTypeOrThrow(i);
            }
            return iArr2;
        }

        public static FieldDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new FieldDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static FieldDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new FieldDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static FieldDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            FieldDescriptorProto fieldDescriptorProto = new FieldDescriptorProto();
            MessageNano.mergeFrom(fieldDescriptorProto, bArr);
            return fieldDescriptorProto;
        }

        public FieldDescriptorProto clear() {
            this.name = "";
            this.number = 0;
            this.label = 1;
            this.type = 1;
            this.typeName = "";
            this.extendee = "";
            this.defaultValue = "";
            this.oneofIndex = 0;
            this.jsonName = "";
            this.options = null;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            String str2 = this.extendee;
            if (str2 != null && !str2.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.extendee);
            }
            int i = this.number;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
            }
            int i2 = this.label;
            if (i2 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i2);
            }
            int i3 = this.type;
            if (i3 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i3);
            }
            String str3 = this.typeName;
            if (str3 != null && !str3.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.typeName);
            }
            String str4 = this.defaultValue;
            if (str4 != null && !str4.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(7, this.defaultValue);
            }
            FieldOptions fieldOptions = this.options;
            if (fieldOptions != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, fieldOptions);
            }
            int i4 = this.oneofIndex;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, i4);
            }
            String str5 = this.jsonName;
            return (str5 == null || str5.equals("")) ? computeSerializedSize : computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(10, this.jsonName);
        }

        @Override // com.google.protobuf.nano.MessageNano
        public FieldDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.name = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.extendee = codedInputByteBufferNano.readString();
                        break;
                    case 24:
                        this.number = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        int position = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt32 = codedInputByteBufferNano.readInt32();
                            checkLabelOrThrow(readInt32);
                            this.label = readInt32;
                            break;
                        } catch (IllegalArgumentException unused) {
                            codedInputByteBufferNano.rewindToPosition(position);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 40:
                        int position2 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt322 = codedInputByteBufferNano.readInt32();
                            checkTypeOrThrow(readInt322);
                            this.type = readInt322;
                            break;
                        } catch (IllegalArgumentException unused2) {
                            codedInputByteBufferNano.rewindToPosition(position2);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 50:
                        this.typeName = codedInputByteBufferNano.readString();
                        break;
                    case 58:
                        this.defaultValue = codedInputByteBufferNano.readString();
                        break;
                    case 66:
                        if (this.options == null) {
                            this.options = new FieldOptions();
                        }
                        codedInputByteBufferNano.readMessage(this.options);
                        break;
                    case 72:
                        this.oneofIndex = codedInputByteBufferNano.readInt32();
                        break;
                    case 82:
                        this.jsonName = codedInputByteBufferNano.readString();
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            String str2 = this.extendee;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.extendee);
            }
            int i = this.number;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(3, i);
            }
            int i2 = this.label;
            if (i2 != 1) {
                codedOutputByteBufferNano.writeInt32(4, i2);
            }
            int i3 = this.type;
            if (i3 != 1) {
                codedOutputByteBufferNano.writeInt32(5, i3);
            }
            String str3 = this.typeName;
            if (str3 != null && !str3.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.typeName);
            }
            String str4 = this.defaultValue;
            if (str4 != null && !str4.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.defaultValue);
            }
            FieldOptions fieldOptions = this.options;
            if (fieldOptions != null) {
                codedOutputByteBufferNano.writeMessage(8, fieldOptions);
            }
            int i4 = this.oneofIndex;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(9, i4);
            }
            String str5 = this.jsonName;
            if (str5 != null && !str5.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.jsonName);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class FieldOptions extends ExtendableMessageNano<FieldOptions> {
        private static volatile FieldOptions[] _emptyArray;
        @NanoEnumValue(legacy = false, value = CType.class)
        public int ctype;
        public boolean deprecated;
        public boolean deprecatedRawMessage;
        public boolean enforceUtf8;
        @NanoEnumValue(legacy = false, value = JSType.class)
        public int jstype;
        public boolean lazy;
        public boolean packed;
        public UninterpretedOption[] uninterpretedOption;
        public UpgradedOption[] upgradedOption;
        public boolean weak;

        public interface CType {
            @NanoEnumValue(legacy = false, value = CType.class)
            public static final int CORD = 1;
            @NanoEnumValue(legacy = false, value = CType.class)
            public static final int STRING = 0;
            @NanoEnumValue(legacy = false, value = CType.class)
            public static final int STRING_PIECE = 2;
        }

        public interface JSType {
            @NanoEnumValue(legacy = false, value = JSType.class)
            public static final int JS_NORMAL = 0;
            @NanoEnumValue(legacy = false, value = JSType.class)
            public static final int JS_NUMBER = 2;
            @NanoEnumValue(legacy = false, value = JSType.class)
            public static final int JS_STRING = 1;
        }

        public static final class UpgradedOption extends ExtendableMessageNano<UpgradedOption> {
            private static volatile UpgradedOption[] _emptyArray;
            public String name;
            public String value;

            public UpgradedOption() {
                clear();
            }

            public static UpgradedOption[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new UpgradedOption[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static UpgradedOption parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new UpgradedOption().mergeFrom(codedInputByteBufferNano);
            }

            public static UpgradedOption parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                UpgradedOption upgradedOption = new UpgradedOption();
                MessageNano.mergeFrom(upgradedOption, bArr);
                return upgradedOption;
            }

            public UpgradedOption clear() {
                this.name = "";
                this.value = "";
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                int computeSerializedSize = super.computeSerializedSize();
                String str = this.name;
                if (str != null && !str.equals("")) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
                }
                String str2 = this.value;
                return (str2 == null || str2.equals("")) ? computeSerializedSize : computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.value);
            }

            @Override // com.google.protobuf.nano.MessageNano
            public UpgradedOption mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 10) {
                        this.name = codedInputByteBufferNano.readString();
                    } else if (readTag == 18) {
                        this.value = codedInputByteBufferNano.readString();
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                String str = this.name;
                if (str != null && !str.equals("")) {
                    codedOutputByteBufferNano.writeString(1, this.name);
                }
                String str2 = this.value;
                if (str2 != null && !str2.equals("")) {
                    codedOutputByteBufferNano.writeString(2, this.value);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public FieldOptions() {
            clear();
        }

        @NanoEnumValue(legacy = false, value = CType.class)
        public static int checkCTypeOrThrow(int i) {
            if (i >= 0 && i <= 2) {
                return i;
            }
            StringBuilder sb = new StringBuilder(37);
            sb.append(i);
            sb.append(" is not a valid enum CType");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = CType.class)
        public static int[] checkCTypeOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkCTypeOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = JSType.class)
        public static int checkJSTypeOrThrow(int i) {
            if (i >= 0 && i <= 2) {
                return i;
            }
            StringBuilder sb = new StringBuilder(38);
            sb.append(i);
            sb.append(" is not a valid enum JSType");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = JSType.class)
        public static int[] checkJSTypeOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkJSTypeOrThrow(i);
            }
            return iArr2;
        }

        public static FieldOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new FieldOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static FieldOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new FieldOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static FieldOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            FieldOptions fieldOptions = new FieldOptions();
            MessageNano.mergeFrom(fieldOptions, bArr);
            return fieldOptions;
        }

        public FieldOptions clear() {
            this.ctype = 0;
            this.packed = false;
            this.jstype = 0;
            this.lazy = false;
            this.deprecated = false;
            this.weak = false;
            this.upgradedOption = UpgradedOption.emptyArray();
            this.deprecatedRawMessage = false;
            this.enforceUtf8 = true;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.ctype;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            boolean z = this.packed;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(2, z);
            }
            boolean z2 = this.deprecated;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(3, z2);
            }
            boolean z3 = this.lazy;
            if (z3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(5, z3);
            }
            int i2 = this.jstype;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, i2);
            }
            boolean z4 = this.weak;
            if (z4) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(10, z4);
            }
            UpgradedOption[] upgradedOptionArr = this.upgradedOption;
            int i3 = 0;
            if (upgradedOptionArr != null && upgradedOptionArr.length > 0) {
                int i4 = computeSerializedSize;
                int i5 = 0;
                while (true) {
                    UpgradedOption[] upgradedOptionArr2 = this.upgradedOption;
                    if (i5 >= upgradedOptionArr2.length) {
                        break;
                    }
                    UpgradedOption upgradedOption2 = upgradedOptionArr2[i5];
                    if (upgradedOption2 != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(11, upgradedOption2);
                    }
                    i5++;
                }
                computeSerializedSize = i4;
            }
            boolean z5 = this.deprecatedRawMessage;
            if (z5) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(12, z5);
            }
            boolean z6 = this.enforceUtf8;
            if (!z6) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(13, z6);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i3 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i3];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i3++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public FieldOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        int position = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt32 = codedInputByteBufferNano.readInt32();
                            checkCTypeOrThrow(readInt32);
                            this.ctype = readInt32;
                            break;
                        } catch (IllegalArgumentException unused) {
                            codedInputByteBufferNano.rewindToPosition(position);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 16:
                        this.packed = codedInputByteBufferNano.readBool();
                        break;
                    case 24:
                        this.deprecated = codedInputByteBufferNano.readBool();
                        break;
                    case 40:
                        this.lazy = codedInputByteBufferNano.readBool();
                        break;
                    case 48:
                        int position2 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt322 = codedInputByteBufferNano.readInt32();
                            checkJSTypeOrThrow(readInt322);
                            this.jstype = readInt322;
                            break;
                        } catch (IllegalArgumentException unused2) {
                            codedInputByteBufferNano.rewindToPosition(position2);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 80:
                        this.weak = codedInputByteBufferNano.readBool();
                        break;
                    case 90:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        UpgradedOption[] upgradedOptionArr = this.upgradedOption;
                        int length = upgradedOptionArr == null ? 0 : upgradedOptionArr.length;
                        UpgradedOption[] upgradedOptionArr2 = new UpgradedOption[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.upgradedOption, 0, upgradedOptionArr2, 0, length);
                        }
                        while (length < upgradedOptionArr2.length - 1) {
                            upgradedOptionArr2[length] = new UpgradedOption();
                            codedInputByteBufferNano.readMessage(upgradedOptionArr2[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        upgradedOptionArr2[length] = new UpgradedOption();
                        codedInputByteBufferNano.readMessage(upgradedOptionArr2[length]);
                        this.upgradedOption = upgradedOptionArr2;
                        break;
                    case 96:
                        this.deprecatedRawMessage = codedInputByteBufferNano.readBool();
                        break;
                    case 104:
                        this.enforceUtf8 = codedInputByteBufferNano.readBool();
                        break;
                    case 7994:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                        UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                        int length2 = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                        UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length2);
                        }
                        while (length2 < uninterpretedOptionArr2.length - 1) {
                            uninterpretedOptionArr2[length2] = new UninterpretedOption();
                            codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        uninterpretedOptionArr2[length2] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length2]);
                        this.uninterpretedOption = uninterpretedOptionArr2;
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.ctype;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            boolean z = this.packed;
            if (z) {
                codedOutputByteBufferNano.writeBool(2, z);
            }
            boolean z2 = this.deprecated;
            if (z2) {
                codedOutputByteBufferNano.writeBool(3, z2);
            }
            boolean z3 = this.lazy;
            if (z3) {
                codedOutputByteBufferNano.writeBool(5, z3);
            }
            int i2 = this.jstype;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(6, i2);
            }
            boolean z4 = this.weak;
            if (z4) {
                codedOutputByteBufferNano.writeBool(10, z4);
            }
            UpgradedOption[] upgradedOptionArr = this.upgradedOption;
            int i3 = 0;
            if (upgradedOptionArr != null && upgradedOptionArr.length > 0) {
                int i4 = 0;
                while (true) {
                    UpgradedOption[] upgradedOptionArr2 = this.upgradedOption;
                    if (i4 >= upgradedOptionArr2.length) {
                        break;
                    }
                    UpgradedOption upgradedOption2 = upgradedOptionArr2[i4];
                    if (upgradedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(11, upgradedOption2);
                    }
                    i4++;
                }
            }
            boolean z5 = this.deprecatedRawMessage;
            if (z5) {
                codedOutputByteBufferNano.writeBool(12, z5);
            }
            boolean z6 = this.enforceUtf8;
            if (!z6) {
                codedOutputByteBufferNano.writeBool(13, z6);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i3 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i3];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i3++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class FileDescriptorProto extends ExtendableMessageNano<FileDescriptorProto> {
        private static volatile FileDescriptorProto[] _emptyArray;
        public String[] dependency;
        public EnumDescriptorProto[] enumType;
        public FieldDescriptorProto[] extension;
        public DescriptorProto[] messageType;
        public String name;
        public FileOptions options;
        public String package_;
        public int[] publicDependency;
        public ServiceDescriptorProto[] service;
        public SourceCodeInfo sourceCodeInfo;
        public String syntax;
        public int[] weakDependency;

        public FileDescriptorProto() {
            clear();
        }

        public static FileDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new FileDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static FileDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new FileDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static FileDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            FileDescriptorProto fileDescriptorProto = new FileDescriptorProto();
            MessageNano.mergeFrom(fileDescriptorProto, bArr);
            return fileDescriptorProto;
        }

        public FileDescriptorProto clear() {
            this.name = "";
            this.package_ = "";
            this.dependency = WireFormatNano.EMPTY_STRING_ARRAY;
            int[] iArr = WireFormatNano.EMPTY_INT_ARRAY;
            this.publicDependency = iArr;
            this.weakDependency = iArr;
            this.messageType = DescriptorProto.emptyArray();
            this.enumType = EnumDescriptorProto.emptyArray();
            this.service = ServiceDescriptorProto.emptyArray();
            this.extension = FieldDescriptorProto.emptyArray();
            this.options = null;
            this.sourceCodeInfo = null;
            this.syntax = "";
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int[] iArr;
            int[] iArr2;
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            String str2 = this.package_;
            if (str2 != null && !str2.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.package_);
            }
            String[] strArr = this.dependency;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    String[] strArr2 = this.dependency;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str3 = strArr2[i2];
                    if (str3 != null) {
                        i4++;
                        i3 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
                    }
                    i2++;
                }
                computeSerializedSize = computeSerializedSize + i3 + (i4 * 1);
            }
            DescriptorProto[] descriptorProtoArr = this.messageType;
            if (descriptorProtoArr != null && descriptorProtoArr.length > 0) {
                int i5 = computeSerializedSize;
                int i6 = 0;
                while (true) {
                    DescriptorProto[] descriptorProtoArr2 = this.messageType;
                    if (i6 >= descriptorProtoArr2.length) {
                        break;
                    }
                    DescriptorProto descriptorProto = descriptorProtoArr2[i6];
                    if (descriptorProto != null) {
                        i5 += CodedOutputByteBufferNano.computeMessageSize(4, descriptorProto);
                    }
                    i6++;
                }
                computeSerializedSize = i5;
            }
            EnumDescriptorProto[] enumDescriptorProtoArr = this.enumType;
            if (enumDescriptorProtoArr != null && enumDescriptorProtoArr.length > 0) {
                int i7 = computeSerializedSize;
                int i8 = 0;
                while (true) {
                    EnumDescriptorProto[] enumDescriptorProtoArr2 = this.enumType;
                    if (i8 >= enumDescriptorProtoArr2.length) {
                        break;
                    }
                    EnumDescriptorProto enumDescriptorProto = enumDescriptorProtoArr2[i8];
                    if (enumDescriptorProto != null) {
                        i7 += CodedOutputByteBufferNano.computeMessageSize(5, enumDescriptorProto);
                    }
                    i8++;
                }
                computeSerializedSize = i7;
            }
            ServiceDescriptorProto[] serviceDescriptorProtoArr = this.service;
            if (serviceDescriptorProtoArr != null && serviceDescriptorProtoArr.length > 0) {
                int i9 = computeSerializedSize;
                int i10 = 0;
                while (true) {
                    ServiceDescriptorProto[] serviceDescriptorProtoArr2 = this.service;
                    if (i10 >= serviceDescriptorProtoArr2.length) {
                        break;
                    }
                    ServiceDescriptorProto serviceDescriptorProto = serviceDescriptorProtoArr2[i10];
                    if (serviceDescriptorProto != null) {
                        i9 += CodedOutputByteBufferNano.computeMessageSize(6, serviceDescriptorProto);
                    }
                    i10++;
                }
                computeSerializedSize = i9;
            }
            FieldDescriptorProto[] fieldDescriptorProtoArr = this.extension;
            if (fieldDescriptorProtoArr != null && fieldDescriptorProtoArr.length > 0) {
                int i11 = computeSerializedSize;
                int i12 = 0;
                while (true) {
                    FieldDescriptorProto[] fieldDescriptorProtoArr2 = this.extension;
                    if (i12 >= fieldDescriptorProtoArr2.length) {
                        break;
                    }
                    FieldDescriptorProto fieldDescriptorProto = fieldDescriptorProtoArr2[i12];
                    if (fieldDescriptorProto != null) {
                        i11 += CodedOutputByteBufferNano.computeMessageSize(7, fieldDescriptorProto);
                    }
                    i12++;
                }
                computeSerializedSize = i11;
            }
            FileOptions fileOptions = this.options;
            if (fileOptions != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, fileOptions);
            }
            SourceCodeInfo sourceCodeInfo2 = this.sourceCodeInfo;
            if (sourceCodeInfo2 != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, sourceCodeInfo2);
            }
            int[] iArr3 = this.publicDependency;
            if (iArr3 != null && iArr3.length > 0) {
                int i13 = 0;
                int i14 = 0;
                while (true) {
                    iArr2 = this.publicDependency;
                    if (i13 >= iArr2.length) {
                        break;
                    }
                    i14 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i13]);
                    i13++;
                }
                computeSerializedSize = computeSerializedSize + i14 + (iArr2.length * 1);
            }
            int[] iArr4 = this.weakDependency;
            if (iArr4 != null && iArr4.length > 0) {
                int i15 = 0;
                while (true) {
                    iArr = this.weakDependency;
                    if (i >= iArr.length) {
                        break;
                    }
                    i15 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr[i]);
                    i++;
                }
                computeSerializedSize = computeSerializedSize + i15 + (iArr.length * 1);
            }
            String str4 = this.syntax;
            return (str4 == null || str4.equals("")) ? computeSerializedSize : computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(12, this.syntax);
        }

        @Override // com.google.protobuf.nano.MessageNano
        public FileDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.name = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.package_ = codedInputByteBufferNano.readString();
                        break;
                    case 26:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        String[] strArr = this.dependency;
                        int length = strArr == null ? 0 : strArr.length;
                        String[] strArr2 = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.dependency, 0, strArr2, 0, length);
                        }
                        while (length < strArr2.length - 1) {
                            strArr2[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr2[length] = codedInputByteBufferNano.readString();
                        this.dependency = strArr2;
                        break;
                    case 34:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        DescriptorProto[] descriptorProtoArr = this.messageType;
                        int length2 = descriptorProtoArr == null ? 0 : descriptorProtoArr.length;
                        DescriptorProto[] descriptorProtoArr2 = new DescriptorProto[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.messageType, 0, descriptorProtoArr2, 0, length2);
                        }
                        while (length2 < descriptorProtoArr2.length - 1) {
                            descriptorProtoArr2[length2] = new DescriptorProto();
                            codedInputByteBufferNano.readMessage(descriptorProtoArr2[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        descriptorProtoArr2[length2] = new DescriptorProto();
                        codedInputByteBufferNano.readMessage(descriptorProtoArr2[length2]);
                        this.messageType = descriptorProtoArr2;
                        break;
                    case 42:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                        EnumDescriptorProto[] enumDescriptorProtoArr = this.enumType;
                        int length3 = enumDescriptorProtoArr == null ? 0 : enumDescriptorProtoArr.length;
                        EnumDescriptorProto[] enumDescriptorProtoArr2 = new EnumDescriptorProto[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.enumType, 0, enumDescriptorProtoArr2, 0, length3);
                        }
                        while (length3 < enumDescriptorProtoArr2.length - 1) {
                            enumDescriptorProtoArr2[length3] = new EnumDescriptorProto();
                            codedInputByteBufferNano.readMessage(enumDescriptorProtoArr2[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        enumDescriptorProtoArr2[length3] = new EnumDescriptorProto();
                        codedInputByteBufferNano.readMessage(enumDescriptorProtoArr2[length3]);
                        this.enumType = enumDescriptorProtoArr2;
                        break;
                    case 50:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                        ServiceDescriptorProto[] serviceDescriptorProtoArr = this.service;
                        int length4 = serviceDescriptorProtoArr == null ? 0 : serviceDescriptorProtoArr.length;
                        ServiceDescriptorProto[] serviceDescriptorProtoArr2 = new ServiceDescriptorProto[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.service, 0, serviceDescriptorProtoArr2, 0, length4);
                        }
                        while (length4 < serviceDescriptorProtoArr2.length - 1) {
                            serviceDescriptorProtoArr2[length4] = new ServiceDescriptorProto();
                            codedInputByteBufferNano.readMessage(serviceDescriptorProtoArr2[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        serviceDescriptorProtoArr2[length4] = new ServiceDescriptorProto();
                        codedInputByteBufferNano.readMessage(serviceDescriptorProtoArr2[length4]);
                        this.service = serviceDescriptorProtoArr2;
                        break;
                    case 58:
                        int repeatedFieldArrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                        FieldDescriptorProto[] fieldDescriptorProtoArr = this.extension;
                        int length5 = fieldDescriptorProtoArr == null ? 0 : fieldDescriptorProtoArr.length;
                        FieldDescriptorProto[] fieldDescriptorProtoArr2 = new FieldDescriptorProto[(repeatedFieldArrayLength5 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.extension, 0, fieldDescriptorProtoArr2, 0, length5);
                        }
                        while (length5 < fieldDescriptorProtoArr2.length - 1) {
                            fieldDescriptorProtoArr2[length5] = new FieldDescriptorProto();
                            codedInputByteBufferNano.readMessage(fieldDescriptorProtoArr2[length5]);
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        fieldDescriptorProtoArr2[length5] = new FieldDescriptorProto();
                        codedInputByteBufferNano.readMessage(fieldDescriptorProtoArr2[length5]);
                        this.extension = fieldDescriptorProtoArr2;
                        break;
                    case 66:
                        if (this.options == null) {
                            this.options = new FileOptions();
                        }
                        codedInputByteBufferNano.readMessage(this.options);
                        break;
                    case 74:
                        if (this.sourceCodeInfo == null) {
                            this.sourceCodeInfo = new SourceCodeInfo();
                        }
                        codedInputByteBufferNano.readMessage(this.sourceCodeInfo);
                        break;
                    case 80:
                        int repeatedFieldArrayLength6 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 80);
                        int[] iArr = this.publicDependency;
                        int length6 = iArr == null ? 0 : iArr.length;
                        int[] iArr2 = new int[(repeatedFieldArrayLength6 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.publicDependency, 0, iArr2, 0, length6);
                        }
                        while (length6 < iArr2.length - 1) {
                            iArr2[length6] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length6++;
                        }
                        iArr2[length6] = codedInputByteBufferNano.readInt32();
                        this.publicDependency = iArr2;
                        break;
                    case 82:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int[] iArr3 = this.publicDependency;
                        int length7 = iArr3 == null ? 0 : iArr3.length;
                        int[] iArr4 = new int[(i + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.publicDependency, 0, iArr4, 0, length7);
                        }
                        while (length7 < iArr4.length) {
                            iArr4[length7] = codedInputByteBufferNano.readInt32();
                            length7++;
                        }
                        this.publicDependency = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 88:
                        int repeatedFieldArrayLength7 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 88);
                        int[] iArr5 = this.weakDependency;
                        int length8 = iArr5 == null ? 0 : iArr5.length;
                        int[] iArr6 = new int[(repeatedFieldArrayLength7 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.weakDependency, 0, iArr6, 0, length8);
                        }
                        while (length8 < iArr6.length - 1) {
                            iArr6[length8] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length8++;
                        }
                        iArr6[length8] = codedInputByteBufferNano.readInt32();
                        this.weakDependency = iArr6;
                        break;
                    case 90:
                        int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position2 = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position2);
                        int[] iArr7 = this.weakDependency;
                        int length9 = iArr7 == null ? 0 : iArr7.length;
                        int[] iArr8 = new int[(i2 + length9)];
                        if (length9 != 0) {
                            System.arraycopy(this.weakDependency, 0, iArr8, 0, length9);
                        }
                        while (length9 < iArr8.length) {
                            iArr8[length9] = codedInputByteBufferNano.readInt32();
                            length9++;
                        }
                        this.weakDependency = iArr8;
                        codedInputByteBufferNano.popLimit(pushLimit2);
                        break;
                    case 98:
                        this.syntax = codedInputByteBufferNano.readString();
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            String str2 = this.package_;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.package_);
            }
            String[] strArr = this.dependency;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                while (true) {
                    String[] strArr2 = this.dependency;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str3 = strArr2[i2];
                    if (str3 != null) {
                        codedOutputByteBufferNano.writeString(3, str3);
                    }
                    i2++;
                }
            }
            DescriptorProto[] descriptorProtoArr = this.messageType;
            if (descriptorProtoArr != null && descriptorProtoArr.length > 0) {
                int i3 = 0;
                while (true) {
                    DescriptorProto[] descriptorProtoArr2 = this.messageType;
                    if (i3 >= descriptorProtoArr2.length) {
                        break;
                    }
                    DescriptorProto descriptorProto = descriptorProtoArr2[i3];
                    if (descriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(4, descriptorProto);
                    }
                    i3++;
                }
            }
            EnumDescriptorProto[] enumDescriptorProtoArr = this.enumType;
            if (enumDescriptorProtoArr != null && enumDescriptorProtoArr.length > 0) {
                int i4 = 0;
                while (true) {
                    EnumDescriptorProto[] enumDescriptorProtoArr2 = this.enumType;
                    if (i4 >= enumDescriptorProtoArr2.length) {
                        break;
                    }
                    EnumDescriptorProto enumDescriptorProto = enumDescriptorProtoArr2[i4];
                    if (enumDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(5, enumDescriptorProto);
                    }
                    i4++;
                }
            }
            ServiceDescriptorProto[] serviceDescriptorProtoArr = this.service;
            if (serviceDescriptorProtoArr != null && serviceDescriptorProtoArr.length > 0) {
                int i5 = 0;
                while (true) {
                    ServiceDescriptorProto[] serviceDescriptorProtoArr2 = this.service;
                    if (i5 >= serviceDescriptorProtoArr2.length) {
                        break;
                    }
                    ServiceDescriptorProto serviceDescriptorProto = serviceDescriptorProtoArr2[i5];
                    if (serviceDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(6, serviceDescriptorProto);
                    }
                    i5++;
                }
            }
            FieldDescriptorProto[] fieldDescriptorProtoArr = this.extension;
            if (fieldDescriptorProtoArr != null && fieldDescriptorProtoArr.length > 0) {
                int i6 = 0;
                while (true) {
                    FieldDescriptorProto[] fieldDescriptorProtoArr2 = this.extension;
                    if (i6 >= fieldDescriptorProtoArr2.length) {
                        break;
                    }
                    FieldDescriptorProto fieldDescriptorProto = fieldDescriptorProtoArr2[i6];
                    if (fieldDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(7, fieldDescriptorProto);
                    }
                    i6++;
                }
            }
            FileOptions fileOptions = this.options;
            if (fileOptions != null) {
                codedOutputByteBufferNano.writeMessage(8, fileOptions);
            }
            SourceCodeInfo sourceCodeInfo2 = this.sourceCodeInfo;
            if (sourceCodeInfo2 != null) {
                codedOutputByteBufferNano.writeMessage(9, sourceCodeInfo2);
            }
            int[] iArr = this.publicDependency;
            if (iArr != null && iArr.length > 0) {
                int i7 = 0;
                while (true) {
                    int[] iArr2 = this.publicDependency;
                    if (i7 >= iArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(10, iArr2[i7]);
                    i7++;
                }
            }
            int[] iArr3 = this.weakDependency;
            if (iArr3 != null && iArr3.length > 0) {
                while (true) {
                    int[] iArr4 = this.weakDependency;
                    if (i >= iArr4.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(11, iArr4[i]);
                    i++;
                }
            }
            String str4 = this.syntax;
            if (str4 != null && !str4.equals("")) {
                codedOutputByteBufferNano.writeString(12, this.syntax);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class FileDescriptorSet extends ExtendableMessageNano<FileDescriptorSet> {
        private static volatile FileDescriptorSet[] _emptyArray;
        public FileDescriptorProto[] file;

        public FileDescriptorSet() {
            clear();
        }

        public static FileDescriptorSet[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new FileDescriptorSet[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static FileDescriptorSet parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new FileDescriptorSet().mergeFrom(codedInputByteBufferNano);
        }

        public static FileDescriptorSet parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            FileDescriptorSet fileDescriptorSet = new FileDescriptorSet();
            MessageNano.mergeFrom(fileDescriptorSet, bArr);
            return fileDescriptorSet;
        }

        public FileDescriptorSet clear() {
            this.file = FileDescriptorProto.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            FileDescriptorProto[] fileDescriptorProtoArr = this.file;
            if (fileDescriptorProtoArr != null && fileDescriptorProtoArr.length > 0) {
                int i = 0;
                while (true) {
                    FileDescriptorProto[] fileDescriptorProtoArr2 = this.file;
                    if (i >= fileDescriptorProtoArr2.length) {
                        break;
                    }
                    FileDescriptorProto fileDescriptorProto = fileDescriptorProtoArr2[i];
                    if (fileDescriptorProto != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, fileDescriptorProto);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public FileDescriptorSet mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    FileDescriptorProto[] fileDescriptorProtoArr = this.file;
                    int length = fileDescriptorProtoArr == null ? 0 : fileDescriptorProtoArr.length;
                    FileDescriptorProto[] fileDescriptorProtoArr2 = new FileDescriptorProto[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.file, 0, fileDescriptorProtoArr2, 0, length);
                    }
                    while (length < fileDescriptorProtoArr2.length - 1) {
                        fileDescriptorProtoArr2[length] = new FileDescriptorProto();
                        codedInputByteBufferNano.readMessage(fileDescriptorProtoArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    fileDescriptorProtoArr2[length] = new FileDescriptorProto();
                    codedInputByteBufferNano.readMessage(fileDescriptorProtoArr2[length]);
                    this.file = fileDescriptorProtoArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            FileDescriptorProto[] fileDescriptorProtoArr = this.file;
            if (fileDescriptorProtoArr != null && fileDescriptorProtoArr.length > 0) {
                int i = 0;
                while (true) {
                    FileDescriptorProto[] fileDescriptorProtoArr2 = this.file;
                    if (i >= fileDescriptorProtoArr2.length) {
                        break;
                    }
                    FileDescriptorProto fileDescriptorProto = fileDescriptorProtoArr2[i];
                    if (fileDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(1, fileDescriptorProto);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class FileOptions extends ExtendableMessageNano<FileOptions> {
        private static volatile FileOptions[] _emptyArray;
        public int ccApiVersion;
        public boolean ccEnableArenas;
        public boolean ccGenericServices;
        public boolean ccUtf8Verification;
        public String csharpNamespace;
        public boolean deprecated;
        public String goPackage;
        public String javaAltApiPackage;
        public int javaApiVersion;
        public boolean javaEnableDualGenerateMutableApi;
        public boolean javaGenericServices;
        public boolean javaJava5Enums;
        public boolean javaMultipleFiles;
        public String javaMultipleFilesMutablePackage;
        public boolean javaMutableApi;
        public String javaOuterClassname;
        public String javaPackage;
        public boolean javaStringCheckUtf8;
        public boolean javaUseJavaproto2;
        public boolean javaUseJavastrings;
        public String javascriptPackage;
        public String objcClassPrefix;
        @NanoEnumValue(legacy = false, value = OptimizeMode.class)
        public int optimizeFor;
        public String phpClassPrefix;
        public boolean phpGenericServices;
        public String phpNamespace;
        public int pyApiVersion;
        public boolean pyGenericServices;
        public String swiftPrefix;
        public int szlApiVersion;
        public UninterpretedOption[] uninterpretedOption;

        public interface CompatibilityLevel {
            @NanoEnumValue(legacy = false, value = CompatibilityLevel.class)
            public static final int NO_COMPATIBILITY = 0;
            @NanoEnumValue(legacy = false, value = CompatibilityLevel.class)
            public static final int PROTO1_COMPATIBLE = 100;
        }

        public interface OptimizeMode {
            @NanoEnumValue(legacy = false, value = OptimizeMode.class)
            public static final int CODE_SIZE = 2;
            @NanoEnumValue(legacy = false, value = OptimizeMode.class)
            public static final int LITE_RUNTIME = 3;
            @NanoEnumValue(legacy = false, value = OptimizeMode.class)
            public static final int SPEED = 1;
        }

        public FileOptions() {
            clear();
        }

        @NanoEnumValue(legacy = false, value = CompatibilityLevel.class)
        public static int checkCompatibilityLevelOrThrow(int i) {
            if (i >= 0 && i <= 0) {
                return i;
            }
            if (i >= 100 && i <= 100) {
                return i;
            }
            StringBuilder sb = new StringBuilder(50);
            sb.append(i);
            sb.append(" is not a valid enum CompatibilityLevel");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = CompatibilityLevel.class)
        public static int[] checkCompatibilityLevelOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkCompatibilityLevelOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = OptimizeMode.class)
        public static int checkOptimizeModeOrThrow(int i) {
            if (i >= 1 && i <= 3) {
                return i;
            }
            StringBuilder sb = new StringBuilder(44);
            sb.append(i);
            sb.append(" is not a valid enum OptimizeMode");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = OptimizeMode.class)
        public static int[] checkOptimizeModeOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkOptimizeModeOrThrow(i);
            }
            return iArr2;
        }

        public static FileOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new FileOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static FileOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new FileOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static FileOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            FileOptions fileOptions = new FileOptions();
            MessageNano.mergeFrom(fileOptions, bArr);
            return fileOptions;
        }

        public FileOptions clear() {
            this.ccApiVersion = 2;
            this.ccUtf8Verification = true;
            this.javaPackage = "";
            this.pyApiVersion = 2;
            this.javaApiVersion = 2;
            this.javaUseJavaproto2 = true;
            this.javaJava5Enums = true;
            this.javaUseJavastrings = false;
            this.javaAltApiPackage = "";
            this.javaEnableDualGenerateMutableApi = false;
            this.javaOuterClassname = "";
            this.javaMultipleFiles = false;
            this.javaStringCheckUtf8 = false;
            this.javaMutableApi = false;
            this.javaMultipleFilesMutablePackage = "";
            this.optimizeFor = 1;
            this.goPackage = "";
            this.javascriptPackage = "";
            this.szlApiVersion = 1;
            this.ccGenericServices = false;
            this.javaGenericServices = false;
            this.pyGenericServices = false;
            this.phpGenericServices = false;
            this.deprecated = false;
            this.ccEnableArenas = false;
            this.objcClassPrefix = "";
            this.csharpNamespace = "";
            this.swiftPrefix = "";
            this.phpClassPrefix = "";
            this.phpNamespace = "";
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.javaPackage;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.javaPackage);
            }
            int i = this.ccApiVersion;
            if (i != 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.pyApiVersion;
            if (i2 != 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i2);
            }
            int i3 = this.javaApiVersion;
            if (i3 != 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i3);
            }
            boolean z = this.javaUseJavaproto2;
            if (!z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(6, z);
            }
            boolean z2 = this.javaJava5Enums;
            if (!z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(7, z2);
            }
            String str2 = this.javaOuterClassname;
            if (str2 != null && !str2.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(8, this.javaOuterClassname);
            }
            int i4 = this.optimizeFor;
            if (i4 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, i4);
            }
            boolean z3 = this.javaMultipleFiles;
            if (z3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(10, z3);
            }
            String str3 = this.goPackage;
            if (str3 != null && !str3.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.goPackage);
            }
            String str4 = this.javascriptPackage;
            if (str4 != null && !str4.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(12, this.javascriptPackage);
            }
            int i5 = this.szlApiVersion;
            if (i5 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(14, i5);
            }
            boolean z4 = this.ccGenericServices;
            if (z4) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(16, z4);
            }
            boolean z5 = this.javaGenericServices;
            if (z5) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(17, z5);
            }
            boolean z6 = this.pyGenericServices;
            if (z6) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(18, z6);
            }
            String str5 = this.javaAltApiPackage;
            if (str5 != null && !str5.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(19, this.javaAltApiPackage);
            }
            boolean z7 = this.javaUseJavastrings;
            if (z7) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(21, z7);
            }
            boolean z8 = this.deprecated;
            if (z8) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(23, z8);
            }
            boolean z9 = this.ccUtf8Verification;
            if (!z9) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(24, z9);
            }
            boolean z10 = this.javaEnableDualGenerateMutableApi;
            if (z10) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(26, z10);
            }
            boolean z11 = this.javaStringCheckUtf8;
            if (z11) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(27, z11);
            }
            boolean z12 = this.javaMutableApi;
            if (z12) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(28, z12);
            }
            String str6 = this.javaMultipleFilesMutablePackage;
            if (str6 != null && !str6.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(29, this.javaMultipleFilesMutablePackage);
            }
            boolean z13 = this.ccEnableArenas;
            if (z13) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(31, z13);
            }
            String str7 = this.objcClassPrefix;
            if (str7 != null && !str7.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(36, this.objcClassPrefix);
            }
            String str8 = this.csharpNamespace;
            if (str8 != null && !str8.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(37, this.csharpNamespace);
            }
            String str9 = this.swiftPrefix;
            if (str9 != null && !str9.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(39, this.swiftPrefix);
            }
            String str10 = this.phpClassPrefix;
            if (str10 != null && !str10.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(40, this.phpClassPrefix);
            }
            String str11 = this.phpNamespace;
            if (str11 != null && !str11.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(41, this.phpNamespace);
            }
            boolean z14 = this.phpGenericServices;
            if (z14) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(42, z14);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i6 = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i6 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i6];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i6++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public FileOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.javaPackage = codedInputByteBufferNano.readString();
                        break;
                    case 16:
                        this.ccApiVersion = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.pyApiVersion = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.javaApiVersion = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.javaUseJavaproto2 = codedInputByteBufferNano.readBool();
                        break;
                    case 56:
                        this.javaJava5Enums = codedInputByteBufferNano.readBool();
                        break;
                    case 66:
                        this.javaOuterClassname = codedInputByteBufferNano.readString();
                        break;
                    case 72:
                        int position = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt32 = codedInputByteBufferNano.readInt32();
                            checkOptimizeModeOrThrow(readInt32);
                            this.optimizeFor = readInt32;
                            break;
                        } catch (IllegalArgumentException unused) {
                            codedInputByteBufferNano.rewindToPosition(position);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 80:
                        this.javaMultipleFiles = codedInputByteBufferNano.readBool();
                        break;
                    case 90:
                        this.goPackage = codedInputByteBufferNano.readString();
                        break;
                    case 98:
                        this.javascriptPackage = codedInputByteBufferNano.readString();
                        break;
                    case 112:
                        this.szlApiVersion = codedInputByteBufferNano.readInt32();
                        break;
                    case 128:
                        this.ccGenericServices = codedInputByteBufferNano.readBool();
                        break;
                    case 136:
                        this.javaGenericServices = codedInputByteBufferNano.readBool();
                        break;
                    case 144:
                        this.pyGenericServices = codedInputByteBufferNano.readBool();
                        break;
                    case 154:
                        this.javaAltApiPackage = codedInputByteBufferNano.readString();
                        break;
                    case 168:
                        this.javaUseJavastrings = codedInputByteBufferNano.readBool();
                        break;
                    case 184:
                        this.deprecated = codedInputByteBufferNano.readBool();
                        break;
                    case 192:
                        this.ccUtf8Verification = codedInputByteBufferNano.readBool();
                        break;
                    case 208:
                        this.javaEnableDualGenerateMutableApi = codedInputByteBufferNano.readBool();
                        break;
                    case 216:
                        this.javaStringCheckUtf8 = codedInputByteBufferNano.readBool();
                        break;
                    case 224:
                        this.javaMutableApi = codedInputByteBufferNano.readBool();
                        break;
                    case 234:
                        this.javaMultipleFilesMutablePackage = codedInputByteBufferNano.readString();
                        break;
                    case 248:
                        this.ccEnableArenas = codedInputByteBufferNano.readBool();
                        break;
                    case 290:
                        this.objcClassPrefix = codedInputByteBufferNano.readString();
                        break;
                    case 298:
                        this.csharpNamespace = codedInputByteBufferNano.readString();
                        break;
                    case 314:
                        this.swiftPrefix = codedInputByteBufferNano.readString();
                        break;
                    case 322:
                        this.phpClassPrefix = codedInputByteBufferNano.readString();
                        break;
                    case 330:
                        this.phpNamespace = codedInputByteBufferNano.readString();
                        break;
                    case 336:
                        this.phpGenericServices = codedInputByteBufferNano.readBool();
                        break;
                    case 7994:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                        UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                        int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                        UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                        }
                        while (length < uninterpretedOptionArr2.length - 1) {
                            uninterpretedOptionArr2[length] = new UninterpretedOption();
                            codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        this.uninterpretedOption = uninterpretedOptionArr2;
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.javaPackage;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.javaPackage);
            }
            int i = this.ccApiVersion;
            if (i != 2) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.pyApiVersion;
            if (i2 != 2) {
                codedOutputByteBufferNano.writeInt32(4, i2);
            }
            int i3 = this.javaApiVersion;
            if (i3 != 2) {
                codedOutputByteBufferNano.writeInt32(5, i3);
            }
            boolean z = this.javaUseJavaproto2;
            if (!z) {
                codedOutputByteBufferNano.writeBool(6, z);
            }
            boolean z2 = this.javaJava5Enums;
            if (!z2) {
                codedOutputByteBufferNano.writeBool(7, z2);
            }
            String str2 = this.javaOuterClassname;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.javaOuterClassname);
            }
            int i4 = this.optimizeFor;
            if (i4 != 1) {
                codedOutputByteBufferNano.writeInt32(9, i4);
            }
            boolean z3 = this.javaMultipleFiles;
            if (z3) {
                codedOutputByteBufferNano.writeBool(10, z3);
            }
            String str3 = this.goPackage;
            if (str3 != null && !str3.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.goPackage);
            }
            String str4 = this.javascriptPackage;
            if (str4 != null && !str4.equals("")) {
                codedOutputByteBufferNano.writeString(12, this.javascriptPackage);
            }
            int i5 = this.szlApiVersion;
            if (i5 != 1) {
                codedOutputByteBufferNano.writeInt32(14, i5);
            }
            boolean z4 = this.ccGenericServices;
            if (z4) {
                codedOutputByteBufferNano.writeBool(16, z4);
            }
            boolean z5 = this.javaGenericServices;
            if (z5) {
                codedOutputByteBufferNano.writeBool(17, z5);
            }
            boolean z6 = this.pyGenericServices;
            if (z6) {
                codedOutputByteBufferNano.writeBool(18, z6);
            }
            String str5 = this.javaAltApiPackage;
            if (str5 != null && !str5.equals("")) {
                codedOutputByteBufferNano.writeString(19, this.javaAltApiPackage);
            }
            boolean z7 = this.javaUseJavastrings;
            if (z7) {
                codedOutputByteBufferNano.writeBool(21, z7);
            }
            boolean z8 = this.deprecated;
            if (z8) {
                codedOutputByteBufferNano.writeBool(23, z8);
            }
            boolean z9 = this.ccUtf8Verification;
            if (!z9) {
                codedOutputByteBufferNano.writeBool(24, z9);
            }
            boolean z10 = this.javaEnableDualGenerateMutableApi;
            if (z10) {
                codedOutputByteBufferNano.writeBool(26, z10);
            }
            boolean z11 = this.javaStringCheckUtf8;
            if (z11) {
                codedOutputByteBufferNano.writeBool(27, z11);
            }
            boolean z12 = this.javaMutableApi;
            if (z12) {
                codedOutputByteBufferNano.writeBool(28, z12);
            }
            String str6 = this.javaMultipleFilesMutablePackage;
            if (str6 != null && !str6.equals("")) {
                codedOutputByteBufferNano.writeString(29, this.javaMultipleFilesMutablePackage);
            }
            boolean z13 = this.ccEnableArenas;
            if (z13) {
                codedOutputByteBufferNano.writeBool(31, z13);
            }
            String str7 = this.objcClassPrefix;
            if (str7 != null && !str7.equals("")) {
                codedOutputByteBufferNano.writeString(36, this.objcClassPrefix);
            }
            String str8 = this.csharpNamespace;
            if (str8 != null && !str8.equals("")) {
                codedOutputByteBufferNano.writeString(37, this.csharpNamespace);
            }
            String str9 = this.swiftPrefix;
            if (str9 != null && !str9.equals("")) {
                codedOutputByteBufferNano.writeString(39, this.swiftPrefix);
            }
            String str10 = this.phpClassPrefix;
            if (str10 != null && !str10.equals("")) {
                codedOutputByteBufferNano.writeString(40, this.phpClassPrefix);
            }
            String str11 = this.phpNamespace;
            if (str11 != null && !str11.equals("")) {
                codedOutputByteBufferNano.writeString(41, this.phpNamespace);
            }
            boolean z14 = this.phpGenericServices;
            if (z14) {
                codedOutputByteBufferNano.writeBool(42, z14);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i6 = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i6 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i6];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i6++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class GeneratedCodeInfo extends ExtendableMessageNano<GeneratedCodeInfo> {
        private static volatile GeneratedCodeInfo[] _emptyArray;
        public Annotation[] annotation;

        public static final class Annotation extends ExtendableMessageNano<Annotation> {
            private static volatile Annotation[] _emptyArray;
            public int begin;
            public int end;
            public int[] path;
            public String sourceFile;

            public Annotation() {
                clear();
            }

            public static Annotation[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new Annotation[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static Annotation parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new Annotation().mergeFrom(codedInputByteBufferNano);
            }

            public static Annotation parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                Annotation annotation = new Annotation();
                MessageNano.mergeFrom(annotation, bArr);
                return annotation;
            }

            public Annotation clear() {
                this.path = WireFormatNano.EMPTY_INT_ARRAY;
                this.sourceFile = "";
                this.begin = 0;
                this.end = 0;
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                int computeSerializedSize = super.computeSerializedSize();
                int[] iArr = this.path;
                if (iArr != null && iArr.length > 0) {
                    int i = 0;
                    int i2 = 0;
                    while (true) {
                        int[] iArr2 = this.path;
                        if (i >= iArr2.length) {
                            break;
                        }
                        i2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i]);
                        i++;
                    }
                    computeSerializedSize = computeSerializedSize + i2 + 1 + CodedOutputByteBufferNano.computeRawVarint32Size(i2);
                }
                String str = this.sourceFile;
                if (str != null && !str.equals("")) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.sourceFile);
                }
                int i3 = this.begin;
                if (i3 != 0) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
                }
                int i4 = this.end;
                return i4 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, i4) : computeSerializedSize;
            }

            @Override // com.google.protobuf.nano.MessageNano
            public Annotation mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 8) {
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 8);
                        int[] iArr = this.path;
                        int length = iArr == null ? 0 : iArr.length;
                        int[] iArr2 = new int[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.path, 0, iArr2, 0, length);
                        }
                        while (length < iArr2.length - 1) {
                            iArr2[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr2[length] = codedInputByteBufferNano.readInt32();
                        this.path = iArr2;
                    } else if (readTag == 10) {
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int[] iArr3 = this.path;
                        int length2 = iArr3 == null ? 0 : iArr3.length;
                        int[] iArr4 = new int[(i + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.path, 0, iArr4, 0, length2);
                        }
                        while (length2 < iArr4.length) {
                            iArr4[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.path = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit);
                    } else if (readTag == 18) {
                        this.sourceFile = codedInputByteBufferNano.readString();
                    } else if (readTag == 24) {
                        this.begin = codedInputByteBufferNano.readInt32();
                    } else if (readTag == 32) {
                        this.end = codedInputByteBufferNano.readInt32();
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int[] iArr = this.path;
                if (iArr != null && iArr.length > 0) {
                    int i = 0;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        int[] iArr2 = this.path;
                        if (i2 >= iArr2.length) {
                            break;
                        }
                        i3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i2]);
                        i2++;
                    }
                    codedOutputByteBufferNano.writeRawVarint32(10);
                    codedOutputByteBufferNano.writeRawVarint32(i3);
                    while (true) {
                        int[] iArr3 = this.path;
                        if (i >= iArr3.length) {
                            break;
                        }
                        codedOutputByteBufferNano.writeInt32NoTag(iArr3[i]);
                        i++;
                    }
                }
                String str = this.sourceFile;
                if (str != null && !str.equals("")) {
                    codedOutputByteBufferNano.writeString(2, this.sourceFile);
                }
                int i4 = this.begin;
                if (i4 != 0) {
                    codedOutputByteBufferNano.writeInt32(3, i4);
                }
                int i5 = this.end;
                if (i5 != 0) {
                    codedOutputByteBufferNano.writeInt32(4, i5);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public GeneratedCodeInfo() {
            clear();
        }

        public static GeneratedCodeInfo[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new GeneratedCodeInfo[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static GeneratedCodeInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new GeneratedCodeInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static GeneratedCodeInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            GeneratedCodeInfo generatedCodeInfo = new GeneratedCodeInfo();
            MessageNano.mergeFrom(generatedCodeInfo, bArr);
            return generatedCodeInfo;
        }

        public GeneratedCodeInfo clear() {
            this.annotation = Annotation.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            Annotation[] annotationArr = this.annotation;
            if (annotationArr != null && annotationArr.length > 0) {
                int i = 0;
                while (true) {
                    Annotation[] annotationArr2 = this.annotation;
                    if (i >= annotationArr2.length) {
                        break;
                    }
                    Annotation annotation2 = annotationArr2[i];
                    if (annotation2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, annotation2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public GeneratedCodeInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    Annotation[] annotationArr = this.annotation;
                    int length = annotationArr == null ? 0 : annotationArr.length;
                    Annotation[] annotationArr2 = new Annotation[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.annotation, 0, annotationArr2, 0, length);
                    }
                    while (length < annotationArr2.length - 1) {
                        annotationArr2[length] = new Annotation();
                        codedInputByteBufferNano.readMessage(annotationArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    annotationArr2[length] = new Annotation();
                    codedInputByteBufferNano.readMessage(annotationArr2[length]);
                    this.annotation = annotationArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Annotation[] annotationArr = this.annotation;
            if (annotationArr != null && annotationArr.length > 0) {
                int i = 0;
                while (true) {
                    Annotation[] annotationArr2 = this.annotation;
                    if (i >= annotationArr2.length) {
                        break;
                    }
                    Annotation annotation2 = annotationArr2[i];
                    if (annotation2 != null) {
                        codedOutputByteBufferNano.writeMessage(1, annotation2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class MessageOptions extends ExtendableMessageNano<MessageOptions> {
        private static volatile MessageOptions[] _emptyArray;
        public boolean deprecated;
        public String[] experimentalJavaBuilderInterface;
        public String[] experimentalJavaInterfaceExtends;
        public String[] experimentalJavaMessageInterface;
        public boolean mapEntry;
        public boolean messageSetWireFormat;
        public boolean noStandardDescriptorAccessor;
        public UninterpretedOption[] uninterpretedOption;

        public MessageOptions() {
            clear();
        }

        public static MessageOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new MessageOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static MessageOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new MessageOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static MessageOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            MessageOptions messageOptions = new MessageOptions();
            MessageNano.mergeFrom(messageOptions, bArr);
            return messageOptions;
        }

        public MessageOptions clear() {
            String[] strArr = WireFormatNano.EMPTY_STRING_ARRAY;
            this.experimentalJavaMessageInterface = strArr;
            this.experimentalJavaBuilderInterface = strArr;
            this.experimentalJavaInterfaceExtends = strArr;
            this.messageSetWireFormat = false;
            this.noStandardDescriptorAccessor = false;
            this.deprecated = false;
            this.mapEntry = false;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            boolean z = this.messageSetWireFormat;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(1, z);
            }
            boolean z2 = this.noStandardDescriptorAccessor;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(2, z2);
            }
            boolean z3 = this.deprecated;
            if (z3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(3, z3);
            }
            String[] strArr = this.experimentalJavaMessageInterface;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    String[] strArr2 = this.experimentalJavaMessageInterface;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        i4++;
                        i3 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                    i2++;
                }
                computeSerializedSize = computeSerializedSize + i3 + (i4 * 1);
            }
            String[] strArr3 = this.experimentalJavaBuilderInterface;
            if (strArr3 != null && strArr3.length > 0) {
                int i5 = 0;
                int i6 = 0;
                int i7 = 0;
                while (true) {
                    String[] strArr4 = this.experimentalJavaBuilderInterface;
                    if (i5 >= strArr4.length) {
                        break;
                    }
                    String str2 = strArr4[i5];
                    if (str2 != null) {
                        i7++;
                        i6 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                    i5++;
                }
                computeSerializedSize = computeSerializedSize + i6 + (i7 * 1);
            }
            String[] strArr5 = this.experimentalJavaInterfaceExtends;
            if (strArr5 != null && strArr5.length > 0) {
                int i8 = 0;
                int i9 = 0;
                int i10 = 0;
                while (true) {
                    String[] strArr6 = this.experimentalJavaInterfaceExtends;
                    if (i8 >= strArr6.length) {
                        break;
                    }
                    String str3 = strArr6[i8];
                    if (str3 != null) {
                        i10++;
                        i9 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
                    }
                    i8++;
                }
                computeSerializedSize = computeSerializedSize + i9 + (i10 * 1);
            }
            boolean z4 = this.mapEntry;
            if (z4) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(7, z4);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public MessageOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.messageSetWireFormat = codedInputByteBufferNano.readBool();
                } else if (readTag == 16) {
                    this.noStandardDescriptorAccessor = codedInputByteBufferNano.readBool();
                } else if (readTag == 24) {
                    this.deprecated = codedInputByteBufferNano.readBool();
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    String[] strArr = this.experimentalJavaMessageInterface;
                    int length = strArr == null ? 0 : strArr.length;
                    String[] strArr2 = new String[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.experimentalJavaMessageInterface, 0, strArr2, 0, length);
                    }
                    while (length < strArr2.length - 1) {
                        strArr2[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr2[length] = codedInputByteBufferNano.readString();
                    this.experimentalJavaMessageInterface = strArr2;
                } else if (readTag == 42) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                    String[] strArr3 = this.experimentalJavaBuilderInterface;
                    int length2 = strArr3 == null ? 0 : strArr3.length;
                    String[] strArr4 = new String[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.experimentalJavaBuilderInterface, 0, strArr4, 0, length2);
                    }
                    while (length2 < strArr4.length - 1) {
                        strArr4[length2] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    strArr4[length2] = codedInputByteBufferNano.readString();
                    this.experimentalJavaBuilderInterface = strArr4;
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    String[] strArr5 = this.experimentalJavaInterfaceExtends;
                    int length3 = strArr5 == null ? 0 : strArr5.length;
                    String[] strArr6 = new String[(repeatedFieldArrayLength3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.experimentalJavaInterfaceExtends, 0, strArr6, 0, length3);
                    }
                    while (length3 < strArr6.length - 1) {
                        strArr6[length3] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    strArr6[length3] = codedInputByteBufferNano.readString();
                    this.experimentalJavaInterfaceExtends = strArr6;
                } else if (readTag == 56) {
                    this.mapEntry = codedInputByteBufferNano.readBool();
                } else if (readTag == 7994) {
                    int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                    UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                    int length4 = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                    UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength4 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length4);
                    }
                    while (length4 < uninterpretedOptionArr2.length - 1) {
                        uninterpretedOptionArr2[length4] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length4]);
                        codedInputByteBufferNano.readTag();
                        length4++;
                    }
                    uninterpretedOptionArr2[length4] = new UninterpretedOption();
                    codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length4]);
                    this.uninterpretedOption = uninterpretedOptionArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            boolean z = this.messageSetWireFormat;
            if (z) {
                codedOutputByteBufferNano.writeBool(1, z);
            }
            boolean z2 = this.noStandardDescriptorAccessor;
            if (z2) {
                codedOutputByteBufferNano.writeBool(2, z2);
            }
            boolean z3 = this.deprecated;
            if (z3) {
                codedOutputByteBufferNano.writeBool(3, z3);
            }
            String[] strArr = this.experimentalJavaMessageInterface;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                while (true) {
                    String[] strArr2 = this.experimentalJavaMessageInterface;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(4, str);
                    }
                    i2++;
                }
            }
            String[] strArr3 = this.experimentalJavaBuilderInterface;
            if (strArr3 != null && strArr3.length > 0) {
                int i3 = 0;
                while (true) {
                    String[] strArr4 = this.experimentalJavaBuilderInterface;
                    if (i3 >= strArr4.length) {
                        break;
                    }
                    String str2 = strArr4[i3];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(5, str2);
                    }
                    i3++;
                }
            }
            String[] strArr5 = this.experimentalJavaInterfaceExtends;
            if (strArr5 != null && strArr5.length > 0) {
                int i4 = 0;
                while (true) {
                    String[] strArr6 = this.experimentalJavaInterfaceExtends;
                    if (i4 >= strArr6.length) {
                        break;
                    }
                    String str3 = strArr6[i4];
                    if (str3 != null) {
                        codedOutputByteBufferNano.writeString(6, str3);
                    }
                    i4++;
                }
            }
            boolean z4 = this.mapEntry;
            if (z4) {
                codedOutputByteBufferNano.writeBool(7, z4);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class MethodDescriptorProto extends ExtendableMessageNano<MethodDescriptorProto> {
        private static volatile MethodDescriptorProto[] _emptyArray;
        public boolean clientStreaming;
        public String inputType;
        public String name;
        public MethodOptions options;
        public String outputType;
        public boolean serverStreaming;

        public MethodDescriptorProto() {
            clear();
        }

        public static MethodDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new MethodDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static MethodDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new MethodDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static MethodDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            MethodDescriptorProto methodDescriptorProto = new MethodDescriptorProto();
            MessageNano.mergeFrom(methodDescriptorProto, bArr);
            return methodDescriptorProto;
        }

        public MethodDescriptorProto clear() {
            this.name = "";
            this.inputType = "";
            this.outputType = "";
            this.options = null;
            this.clientStreaming = false;
            this.serverStreaming = false;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            String str2 = this.inputType;
            if (str2 != null && !str2.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.inputType);
            }
            String str3 = this.outputType;
            if (str3 != null && !str3.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.outputType);
            }
            MethodOptions methodOptions = this.options;
            if (methodOptions != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, methodOptions);
            }
            boolean z = this.clientStreaming;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(5, z);
            }
            boolean z2 = this.serverStreaming;
            return z2 ? computeSerializedSize + CodedOutputByteBufferNano.computeBoolSize(6, z2) : computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public MethodDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.inputType = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.outputType = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    if (this.options == null) {
                        this.options = new MethodOptions();
                    }
                    codedInputByteBufferNano.readMessage(this.options);
                } else if (readTag == 40) {
                    this.clientStreaming = codedInputByteBufferNano.readBool();
                } else if (readTag == 48) {
                    this.serverStreaming = codedInputByteBufferNano.readBool();
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            String str2 = this.inputType;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.inputType);
            }
            String str3 = this.outputType;
            if (str3 != null && !str3.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.outputType);
            }
            MethodOptions methodOptions = this.options;
            if (methodOptions != null) {
                codedOutputByteBufferNano.writeMessage(4, methodOptions);
            }
            boolean z = this.clientStreaming;
            if (z) {
                codedOutputByteBufferNano.writeBool(5, z);
            }
            boolean z2 = this.serverStreaming;
            if (z2) {
                codedOutputByteBufferNano.writeBool(6, z2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class MethodOptions extends ExtendableMessageNano<MethodOptions> {
        private static volatile MethodOptions[] _emptyArray;
        public int clientLogging;
        public boolean clientStreaming;
        public double deadline;
        public boolean deprecated;
        public boolean duplicateSuppression;
        public boolean endUserCredsRequested;
        public boolean failFast;
        public boolean goLegacyChannelApi;
        @NanoEnumValue(legacy = false, value = IdempotencyLevel.class)
        public int idempotencyLevel;
        public long legacyClientInitialTokens;
        public String legacyResultType;
        public long legacyServerInitialTokens;
        public String legacyStreamType;
        @NanoEnumValue(legacy = false, value = TokenUnit.class)
        public int legacyTokenUnit;
        @NanoEnumValue(legacy = false, value = LogLevel.class)
        public int logLevel;
        @NanoEnumValue(legacy = false, value = Protocol.class)
        public int protocol;
        @NanoEnumValue(legacy = false, value = Format.class)
        public int requestFormat;
        @NanoEnumValue(legacy = false, value = Format.class)
        public int responseFormat;
        public String securityLabel;
        @NanoEnumValue(legacy = false, value = SecurityLevel.class)
        public int securityLevel;
        public int serverLogging;
        public boolean serverStreaming;
        public String streamType;
        public UninterpretedOption[] uninterpretedOption;

        public interface Format {
            @NanoEnumValue(legacy = false, value = Format.class)
            public static final int UNCOMPRESSED = 0;
            @NanoEnumValue(legacy = false, value = Format.class)
            public static final int ZIPPY_COMPRESSED = 1;
        }

        public interface IdempotencyLevel {
            @NanoEnumValue(legacy = false, value = IdempotencyLevel.class)
            public static final int IDEMPOTENCY_UNKNOWN = 0;
            @NanoEnumValue(legacy = false, value = IdempotencyLevel.class)
            public static final int IDEMPOTENT = 2;
            @NanoEnumValue(legacy = false, value = IdempotencyLevel.class)
            public static final int NO_SIDE_EFFECTS = 1;
        }

        public interface LogLevel {
            @NanoEnumValue(legacy = false, value = LogLevel.class)
            public static final int LOG_HEADER_AND_FILTERED_PAYLOAD = 3;
            @NanoEnumValue(legacy = false, value = LogLevel.class)
            public static final int LOG_HEADER_AND_NON_PRIVATE_PAYLOAD_INTERNAL = 2;
            @NanoEnumValue(legacy = false, value = LogLevel.class)
            public static final int LOG_HEADER_AND_PAYLOAD = 4;
            @NanoEnumValue(legacy = false, value = LogLevel.class)
            public static final int LOG_HEADER_ONLY = 1;
            @NanoEnumValue(legacy = false, value = LogLevel.class)
            public static final int LOG_NONE = 0;
        }

        public interface Protocol {
            @NanoEnumValue(legacy = false, value = Protocol.class)
            public static final int TCP = 0;
            @NanoEnumValue(legacy = false, value = Protocol.class)
            public static final int UDP = 1;
        }

        public interface SecurityLevel {
            @NanoEnumValue(legacy = false, value = SecurityLevel.class)
            public static final int INTEGRITY = 1;
            @NanoEnumValue(legacy = false, value = SecurityLevel.class)
            public static final int NONE = 0;
            @NanoEnumValue(legacy = false, value = SecurityLevel.class)
            public static final int PRIVACY_AND_INTEGRITY = 2;
            @NanoEnumValue(legacy = false, value = SecurityLevel.class)
            public static final int STRONG_PRIVACY_AND_INTEGRITY = 3;
        }

        public interface TokenUnit {
            @NanoEnumValue(legacy = false, value = TokenUnit.class)
            public static final int BYTE = 1;
            @NanoEnumValue(legacy = false, value = TokenUnit.class)
            public static final int MESSAGE = 0;
        }

        public MethodOptions() {
            clear();
        }

        @NanoEnumValue(legacy = false, value = Format.class)
        public static int checkFormatOrThrow(int i) {
            if (i >= 0 && i <= 1) {
                return i;
            }
            StringBuilder sb = new StringBuilder(38);
            sb.append(i);
            sb.append(" is not a valid enum Format");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = Format.class)
        public static int[] checkFormatOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkFormatOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = IdempotencyLevel.class)
        public static int checkIdempotencyLevelOrThrow(int i) {
            if (i >= 0 && i <= 2) {
                return i;
            }
            StringBuilder sb = new StringBuilder(48);
            sb.append(i);
            sb.append(" is not a valid enum IdempotencyLevel");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = IdempotencyLevel.class)
        public static int[] checkIdempotencyLevelOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkIdempotencyLevelOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = LogLevel.class)
        public static int checkLogLevelOrThrow(int i) {
            if (i >= 0 && i <= 4) {
                return i;
            }
            StringBuilder sb = new StringBuilder(40);
            sb.append(i);
            sb.append(" is not a valid enum LogLevel");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = LogLevel.class)
        public static int[] checkLogLevelOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkLogLevelOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = Protocol.class)
        public static int checkProtocolOrThrow(int i) {
            if (i >= 0 && i <= 1) {
                return i;
            }
            StringBuilder sb = new StringBuilder(40);
            sb.append(i);
            sb.append(" is not a valid enum Protocol");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = Protocol.class)
        public static int[] checkProtocolOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkProtocolOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = SecurityLevel.class)
        public static int checkSecurityLevelOrThrow(int i) {
            if (i >= 0 && i <= 3) {
                return i;
            }
            StringBuilder sb = new StringBuilder(45);
            sb.append(i);
            sb.append(" is not a valid enum SecurityLevel");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = SecurityLevel.class)
        public static int[] checkSecurityLevelOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkSecurityLevelOrThrow(i);
            }
            return iArr2;
        }

        @NanoEnumValue(legacy = false, value = TokenUnit.class)
        public static int checkTokenUnitOrThrow(int i) {
            if (i >= 0 && i <= 1) {
                return i;
            }
            StringBuilder sb = new StringBuilder(41);
            sb.append(i);
            sb.append(" is not a valid enum TokenUnit");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = TokenUnit.class)
        public static int[] checkTokenUnitOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkTokenUnitOrThrow(i);
            }
            return iArr2;
        }

        public static MethodOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new MethodOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static MethodOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new MethodOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static MethodOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            MethodOptions methodOptions = new MethodOptions();
            MessageNano.mergeFrom(methodOptions, bArr);
            return methodOptions;
        }

        public MethodOptions clear() {
            this.protocol = 0;
            this.deadline = -1.0d;
            this.duplicateSuppression = false;
            this.failFast = false;
            this.endUserCredsRequested = false;
            this.clientLogging = 256;
            this.serverLogging = 256;
            this.securityLevel = 0;
            this.responseFormat = 0;
            this.requestFormat = 0;
            this.streamType = "";
            this.securityLabel = "";
            this.clientStreaming = false;
            this.serverStreaming = false;
            this.legacyStreamType = "";
            this.legacyResultType = "";
            this.goLegacyChannelApi = false;
            this.legacyClientInitialTokens = -1;
            this.legacyServerInitialTokens = -1;
            this.legacyTokenUnit = 1;
            this.logLevel = 2;
            this.deprecated = false;
            this.idempotencyLevel = 0;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.protocol;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, i);
            }
            if (Double.doubleToLongBits(this.deadline) != Double.doubleToLongBits(-1.0d)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(8, this.deadline);
            }
            boolean z = this.duplicateSuppression;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(9, z);
            }
            boolean z2 = this.failFast;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(10, z2);
            }
            int i2 = this.clientLogging;
            if (i2 != 256) {
                computeSerializedSize += CodedOutputByteBufferNano.computeSInt32Size(11, i2);
            }
            int i3 = this.serverLogging;
            if (i3 != 256) {
                computeSerializedSize += CodedOutputByteBufferNano.computeSInt32Size(12, i3);
            }
            int i4 = this.securityLevel;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(13, i4);
            }
            int i5 = this.responseFormat;
            if (i5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(15, i5);
            }
            int i6 = this.requestFormat;
            if (i6 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(17, i6);
            }
            String str = this.streamType;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(18, this.streamType);
            }
            String str2 = this.securityLabel;
            if (str2 != null && !str2.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(19, this.securityLabel);
            }
            boolean z3 = this.clientStreaming;
            if (z3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(20, z3);
            }
            boolean z4 = this.serverStreaming;
            if (z4) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(21, z4);
            }
            String str3 = this.legacyStreamType;
            if (str3 != null && !str3.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(22, this.legacyStreamType);
            }
            String str4 = this.legacyResultType;
            if (str4 != null && !str4.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(23, this.legacyResultType);
            }
            long j = this.legacyClientInitialTokens;
            if (j != -1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(24, j);
            }
            long j2 = this.legacyServerInitialTokens;
            if (j2 != -1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(25, j2);
            }
            boolean z5 = this.endUserCredsRequested;
            if (z5) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(26, z5);
            }
            int i7 = this.logLevel;
            if (i7 != 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(27, i7);
            }
            int i8 = this.legacyTokenUnit;
            if (i8 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(28, i8);
            }
            boolean z6 = this.goLegacyChannelApi;
            if (z6) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(29, z6);
            }
            boolean z7 = this.deprecated;
            if (z7) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(33, z7);
            }
            int i9 = this.idempotencyLevel;
            if (i9 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(34, i9);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i10 = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i10 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i10];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i10++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public MethodOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 56:
                        int position = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt32 = codedInputByteBufferNano.readInt32();
                            checkProtocolOrThrow(readInt32);
                            this.protocol = readInt32;
                            break;
                        } catch (IllegalArgumentException unused) {
                            codedInputByteBufferNano.rewindToPosition(position);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 65:
                        this.deadline = codedInputByteBufferNano.readDouble();
                        break;
                    case 72:
                        this.duplicateSuppression = codedInputByteBufferNano.readBool();
                        break;
                    case 80:
                        this.failFast = codedInputByteBufferNano.readBool();
                        break;
                    case 88:
                        this.clientLogging = codedInputByteBufferNano.readSInt32();
                        break;
                    case 96:
                        this.serverLogging = codedInputByteBufferNano.readSInt32();
                        break;
                    case 104:
                        int position2 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt322 = codedInputByteBufferNano.readInt32();
                            checkSecurityLevelOrThrow(readInt322);
                            this.securityLevel = readInt322;
                            break;
                        } catch (IllegalArgumentException unused2) {
                            codedInputByteBufferNano.rewindToPosition(position2);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 120:
                        int position3 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt323 = codedInputByteBufferNano.readInt32();
                            checkFormatOrThrow(readInt323);
                            this.responseFormat = readInt323;
                            break;
                        } catch (IllegalArgumentException unused3) {
                            codedInputByteBufferNano.rewindToPosition(position3);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 136:
                        int position4 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt324 = codedInputByteBufferNano.readInt32();
                            checkFormatOrThrow(readInt324);
                            this.requestFormat = readInt324;
                            break;
                        } catch (IllegalArgumentException unused4) {
                            codedInputByteBufferNano.rewindToPosition(position4);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 146:
                        this.streamType = codedInputByteBufferNano.readString();
                        break;
                    case 154:
                        this.securityLabel = codedInputByteBufferNano.readString();
                        break;
                    case 160:
                        this.clientStreaming = codedInputByteBufferNano.readBool();
                        break;
                    case 168:
                        this.serverStreaming = codedInputByteBufferNano.readBool();
                        break;
                    case 178:
                        this.legacyStreamType = codedInputByteBufferNano.readString();
                        break;
                    case 186:
                        this.legacyResultType = codedInputByteBufferNano.readString();
                        break;
                    case 192:
                        this.legacyClientInitialTokens = codedInputByteBufferNano.readInt64();
                        break;
                    case 200:
                        this.legacyServerInitialTokens = codedInputByteBufferNano.readInt64();
                        break;
                    case 208:
                        this.endUserCredsRequested = codedInputByteBufferNano.readBool();
                        break;
                    case 216:
                        int position5 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt325 = codedInputByteBufferNano.readInt32();
                            checkLogLevelOrThrow(readInt325);
                            this.logLevel = readInt325;
                            break;
                        } catch (IllegalArgumentException unused5) {
                            codedInputByteBufferNano.rewindToPosition(position5);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 224:
                        int position6 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt326 = codedInputByteBufferNano.readInt32();
                            checkTokenUnitOrThrow(readInt326);
                            this.legacyTokenUnit = readInt326;
                            break;
                        } catch (IllegalArgumentException unused6) {
                            codedInputByteBufferNano.rewindToPosition(position6);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 232:
                        this.goLegacyChannelApi = codedInputByteBufferNano.readBool();
                        break;
                    case 264:
                        this.deprecated = codedInputByteBufferNano.readBool();
                        break;
                    case 272:
                        int position7 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt327 = codedInputByteBufferNano.readInt32();
                            checkIdempotencyLevelOrThrow(readInt327);
                            this.idempotencyLevel = readInt327;
                            break;
                        } catch (IllegalArgumentException unused7) {
                            codedInputByteBufferNano.rewindToPosition(position7);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 7994:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                        UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                        int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                        UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                        }
                        while (length < uninterpretedOptionArr2.length - 1) {
                            uninterpretedOptionArr2[length] = new UninterpretedOption();
                            codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        this.uninterpretedOption = uninterpretedOptionArr2;
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.protocol;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(7, i);
            }
            if (Double.doubleToLongBits(this.deadline) != Double.doubleToLongBits(-1.0d)) {
                codedOutputByteBufferNano.writeDouble(8, this.deadline);
            }
            boolean z = this.duplicateSuppression;
            if (z) {
                codedOutputByteBufferNano.writeBool(9, z);
            }
            boolean z2 = this.failFast;
            if (z2) {
                codedOutputByteBufferNano.writeBool(10, z2);
            }
            int i2 = this.clientLogging;
            if (i2 != 256) {
                codedOutputByteBufferNano.writeSInt32(11, i2);
            }
            int i3 = this.serverLogging;
            if (i3 != 256) {
                codedOutputByteBufferNano.writeSInt32(12, i3);
            }
            int i4 = this.securityLevel;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(13, i4);
            }
            int i5 = this.responseFormat;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(15, i5);
            }
            int i6 = this.requestFormat;
            if (i6 != 0) {
                codedOutputByteBufferNano.writeInt32(17, i6);
            }
            String str = this.streamType;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(18, this.streamType);
            }
            String str2 = this.securityLabel;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(19, this.securityLabel);
            }
            boolean z3 = this.clientStreaming;
            if (z3) {
                codedOutputByteBufferNano.writeBool(20, z3);
            }
            boolean z4 = this.serverStreaming;
            if (z4) {
                codedOutputByteBufferNano.writeBool(21, z4);
            }
            String str3 = this.legacyStreamType;
            if (str3 != null && !str3.equals("")) {
                codedOutputByteBufferNano.writeString(22, this.legacyStreamType);
            }
            String str4 = this.legacyResultType;
            if (str4 != null && !str4.equals("")) {
                codedOutputByteBufferNano.writeString(23, this.legacyResultType);
            }
            long j = this.legacyClientInitialTokens;
            if (j != -1) {
                codedOutputByteBufferNano.writeInt64(24, j);
            }
            long j2 = this.legacyServerInitialTokens;
            if (j2 != -1) {
                codedOutputByteBufferNano.writeInt64(25, j2);
            }
            boolean z5 = this.endUserCredsRequested;
            if (z5) {
                codedOutputByteBufferNano.writeBool(26, z5);
            }
            int i7 = this.logLevel;
            if (i7 != 2) {
                codedOutputByteBufferNano.writeInt32(27, i7);
            }
            int i8 = this.legacyTokenUnit;
            if (i8 != 1) {
                codedOutputByteBufferNano.writeInt32(28, i8);
            }
            boolean z6 = this.goLegacyChannelApi;
            if (z6) {
                codedOutputByteBufferNano.writeBool(29, z6);
            }
            boolean z7 = this.deprecated;
            if (z7) {
                codedOutputByteBufferNano.writeBool(33, z7);
            }
            int i9 = this.idempotencyLevel;
            if (i9 != 0) {
                codedOutputByteBufferNano.writeInt32(34, i9);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i10 = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i10 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i10];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i10++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class OneofDescriptorProto extends ExtendableMessageNano<OneofDescriptorProto> {
        private static volatile OneofDescriptorProto[] _emptyArray;
        public String name;
        public OneofOptions options;

        public OneofDescriptorProto() {
            clear();
        }

        public static OneofDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new OneofDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static OneofDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new OneofDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static OneofDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            OneofDescriptorProto oneofDescriptorProto = new OneofDescriptorProto();
            MessageNano.mergeFrom(oneofDescriptorProto, bArr);
            return oneofDescriptorProto;
        }

        public OneofDescriptorProto clear() {
            this.name = "";
            this.options = null;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            OneofOptions oneofOptions = this.options;
            return oneofOptions != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(2, oneofOptions) : computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public OneofDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    if (this.options == null) {
                        this.options = new OneofOptions();
                    }
                    codedInputByteBufferNano.readMessage(this.options);
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            OneofOptions oneofOptions = this.options;
            if (oneofOptions != null) {
                codedOutputByteBufferNano.writeMessage(2, oneofOptions);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class OneofOptions extends ExtendableMessageNano<OneofOptions> {
        private static volatile OneofOptions[] _emptyArray;
        public UninterpretedOption[] uninterpretedOption;

        public OneofOptions() {
            clear();
        }

        public static OneofOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new OneofOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static OneofOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new OneofOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static OneofOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            OneofOptions oneofOptions = new OneofOptions();
            MessageNano.mergeFrom(oneofOptions, bArr);
            return oneofOptions;
        }

        public OneofOptions clear() {
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public OneofOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 7994) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                    UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                    int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                    UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                    }
                    while (length < uninterpretedOptionArr2.length - 1) {
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    uninterpretedOptionArr2[length] = new UninterpretedOption();
                    codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                    this.uninterpretedOption = uninterpretedOptionArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ServiceDescriptorProto extends ExtendableMessageNano<ServiceDescriptorProto> {
        private static volatile ServiceDescriptorProto[] _emptyArray;
        public MethodDescriptorProto[] method;
        public String name;
        public ServiceOptions options;
        public StreamDescriptorProto[] stream;

        public ServiceDescriptorProto() {
            clear();
        }

        public static ServiceDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ServiceDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static ServiceDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ServiceDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static ServiceDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            ServiceDescriptorProto serviceDescriptorProto = new ServiceDescriptorProto();
            MessageNano.mergeFrom(serviceDescriptorProto, bArr);
            return serviceDescriptorProto;
        }

        public ServiceDescriptorProto clear() {
            this.name = "";
            this.method = MethodDescriptorProto.emptyArray();
            this.stream = StreamDescriptorProto.emptyArray();
            this.options = null;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            MethodDescriptorProto[] methodDescriptorProtoArr = this.method;
            int i = 0;
            if (methodDescriptorProtoArr != null && methodDescriptorProtoArr.length > 0) {
                int i2 = computeSerializedSize;
                int i3 = 0;
                while (true) {
                    MethodDescriptorProto[] methodDescriptorProtoArr2 = this.method;
                    if (i3 >= methodDescriptorProtoArr2.length) {
                        break;
                    }
                    MethodDescriptorProto methodDescriptorProto = methodDescriptorProtoArr2[i3];
                    if (methodDescriptorProto != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(2, methodDescriptorProto);
                    }
                    i3++;
                }
                computeSerializedSize = i2;
            }
            ServiceOptions serviceOptions = this.options;
            if (serviceOptions != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, serviceOptions);
            }
            StreamDescriptorProto[] streamDescriptorProtoArr = this.stream;
            if (streamDescriptorProtoArr != null && streamDescriptorProtoArr.length > 0) {
                while (true) {
                    StreamDescriptorProto[] streamDescriptorProtoArr2 = this.stream;
                    if (i >= streamDescriptorProtoArr2.length) {
                        break;
                    }
                    StreamDescriptorProto streamDescriptorProto = streamDescriptorProtoArr2[i];
                    if (streamDescriptorProto != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, streamDescriptorProto);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public ServiceDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    MethodDescriptorProto[] methodDescriptorProtoArr = this.method;
                    int length = methodDescriptorProtoArr == null ? 0 : methodDescriptorProtoArr.length;
                    MethodDescriptorProto[] methodDescriptorProtoArr2 = new MethodDescriptorProto[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.method, 0, methodDescriptorProtoArr2, 0, length);
                    }
                    while (length < methodDescriptorProtoArr2.length - 1) {
                        methodDescriptorProtoArr2[length] = new MethodDescriptorProto();
                        codedInputByteBufferNano.readMessage(methodDescriptorProtoArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    methodDescriptorProtoArr2[length] = new MethodDescriptorProto();
                    codedInputByteBufferNano.readMessage(methodDescriptorProtoArr2[length]);
                    this.method = methodDescriptorProtoArr2;
                } else if (readTag == 26) {
                    if (this.options == null) {
                        this.options = new ServiceOptions();
                    }
                    codedInputByteBufferNano.readMessage(this.options);
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    StreamDescriptorProto[] streamDescriptorProtoArr = this.stream;
                    int length2 = streamDescriptorProtoArr == null ? 0 : streamDescriptorProtoArr.length;
                    StreamDescriptorProto[] streamDescriptorProtoArr2 = new StreamDescriptorProto[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.stream, 0, streamDescriptorProtoArr2, 0, length2);
                    }
                    while (length2 < streamDescriptorProtoArr2.length - 1) {
                        streamDescriptorProtoArr2[length2] = new StreamDescriptorProto();
                        codedInputByteBufferNano.readMessage(streamDescriptorProtoArr2[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    streamDescriptorProtoArr2[length2] = new StreamDescriptorProto();
                    codedInputByteBufferNano.readMessage(streamDescriptorProtoArr2[length2]);
                    this.stream = streamDescriptorProtoArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            MethodDescriptorProto[] methodDescriptorProtoArr = this.method;
            int i = 0;
            if (methodDescriptorProtoArr != null && methodDescriptorProtoArr.length > 0) {
                int i2 = 0;
                while (true) {
                    MethodDescriptorProto[] methodDescriptorProtoArr2 = this.method;
                    if (i2 >= methodDescriptorProtoArr2.length) {
                        break;
                    }
                    MethodDescriptorProto methodDescriptorProto = methodDescriptorProtoArr2[i2];
                    if (methodDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(2, methodDescriptorProto);
                    }
                    i2++;
                }
            }
            ServiceOptions serviceOptions = this.options;
            if (serviceOptions != null) {
                codedOutputByteBufferNano.writeMessage(3, serviceOptions);
            }
            StreamDescriptorProto[] streamDescriptorProtoArr = this.stream;
            if (streamDescriptorProtoArr != null && streamDescriptorProtoArr.length > 0) {
                while (true) {
                    StreamDescriptorProto[] streamDescriptorProtoArr2 = this.stream;
                    if (i >= streamDescriptorProtoArr2.length) {
                        break;
                    }
                    StreamDescriptorProto streamDescriptorProto = streamDescriptorProtoArr2[i];
                    if (streamDescriptorProto != null) {
                        codedOutputByteBufferNano.writeMessage(4, streamDescriptorProto);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ServiceOptions extends ExtendableMessageNano<ServiceOptions> {
        private static volatile ServiceOptions[] _emptyArray;
        public boolean deprecated;
        public double failureDetectionDelay;
        public boolean multicastStub;
        public UninterpretedOption[] uninterpretedOption;

        public ServiceOptions() {
            clear();
        }

        public static ServiceOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ServiceOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static ServiceOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ServiceOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static ServiceOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            ServiceOptions serviceOptions = new ServiceOptions();
            MessageNano.mergeFrom(serviceOptions, bArr);
            return serviceOptions;
        }

        public ServiceOptions clear() {
            this.multicastStub = false;
            this.failureDetectionDelay = -1.0d;
            this.deprecated = false;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (Double.doubleToLongBits(this.failureDetectionDelay) != Double.doubleToLongBits(-1.0d)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(16, this.failureDetectionDelay);
            }
            boolean z = this.multicastStub;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(20, z);
            }
            boolean z2 = this.deprecated;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(33, z2);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public ServiceOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 129) {
                    this.failureDetectionDelay = codedInputByteBufferNano.readDouble();
                } else if (readTag == 160) {
                    this.multicastStub = codedInputByteBufferNano.readBool();
                } else if (readTag == 264) {
                    this.deprecated = codedInputByteBufferNano.readBool();
                } else if (readTag == 7994) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                    UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                    int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                    UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                    }
                    while (length < uninterpretedOptionArr2.length - 1) {
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    uninterpretedOptionArr2[length] = new UninterpretedOption();
                    codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                    this.uninterpretedOption = uninterpretedOptionArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (Double.doubleToLongBits(this.failureDetectionDelay) != Double.doubleToLongBits(-1.0d)) {
                codedOutputByteBufferNano.writeDouble(16, this.failureDetectionDelay);
            }
            boolean z = this.multicastStub;
            if (z) {
                codedOutputByteBufferNano.writeBool(20, z);
            }
            boolean z2 = this.deprecated;
            if (z2) {
                codedOutputByteBufferNano.writeBool(33, z2);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class SourceCodeInfo extends ExtendableMessageNano<SourceCodeInfo> {
        private static volatile SourceCodeInfo[] _emptyArray;
        public Location[] location;

        public static final class Location extends ExtendableMessageNano<Location> {
            private static volatile Location[] _emptyArray;
            public String leadingComments;
            public String[] leadingDetachedComments;
            public int[] path;
            public int[] span;
            public String trailingComments;

            public Location() {
                clear();
            }

            public static Location[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new Location[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static Location parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new Location().mergeFrom(codedInputByteBufferNano);
            }

            public static Location parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                Location location = new Location();
                MessageNano.mergeFrom(location, bArr);
                return location;
            }

            public Location clear() {
                int[] iArr = WireFormatNano.EMPTY_INT_ARRAY;
                this.path = iArr;
                this.span = iArr;
                this.leadingComments = "";
                this.trailingComments = "";
                this.leadingDetachedComments = WireFormatNano.EMPTY_STRING_ARRAY;
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                int computeSerializedSize = super.computeSerializedSize();
                int[] iArr = this.path;
                int i = 0;
                if (iArr != null && iArr.length > 0) {
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        int[] iArr2 = this.path;
                        if (i2 >= iArr2.length) {
                            break;
                        }
                        i3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i2]);
                        i2++;
                    }
                    computeSerializedSize = computeSerializedSize + i3 + 1 + CodedOutputByteBufferNano.computeRawVarint32Size(i3);
                }
                int[] iArr3 = this.span;
                if (iArr3 != null && iArr3.length > 0) {
                    int i4 = 0;
                    int i5 = 0;
                    while (true) {
                        int[] iArr4 = this.span;
                        if (i4 >= iArr4.length) {
                            break;
                        }
                        i5 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr4[i4]);
                        i4++;
                    }
                    computeSerializedSize = computeSerializedSize + i5 + 1 + CodedOutputByteBufferNano.computeRawVarint32Size(i5);
                }
                String str = this.leadingComments;
                if (str != null && !str.equals("")) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.leadingComments);
                }
                String str2 = this.trailingComments;
                if (str2 != null && !str2.equals("")) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.trailingComments);
                }
                String[] strArr = this.leadingDetachedComments;
                if (strArr == null || strArr.length <= 0) {
                    return computeSerializedSize;
                }
                int i6 = 0;
                int i7 = 0;
                while (true) {
                    String[] strArr2 = this.leadingDetachedComments;
                    if (i >= strArr2.length) {
                        return computeSerializedSize + i6 + (i7 * 1);
                    }
                    String str3 = strArr2[i];
                    if (str3 != null) {
                        i7++;
                        i6 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
                    }
                    i++;
                }
            }

            @Override // com.google.protobuf.nano.MessageNano
            public Location mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 8) {
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 8);
                        int[] iArr = this.path;
                        int length = iArr == null ? 0 : iArr.length;
                        int[] iArr2 = new int[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.path, 0, iArr2, 0, length);
                        }
                        while (length < iArr2.length - 1) {
                            iArr2[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr2[length] = codedInputByteBufferNano.readInt32();
                        this.path = iArr2;
                    } else if (readTag == 10) {
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int[] iArr3 = this.path;
                        int length2 = iArr3 == null ? 0 : iArr3.length;
                        int[] iArr4 = new int[(i + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.path, 0, iArr4, 0, length2);
                        }
                        while (length2 < iArr4.length) {
                            iArr4[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.path = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit);
                    } else if (readTag == 16) {
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 16);
                        int[] iArr5 = this.span;
                        int length3 = iArr5 == null ? 0 : iArr5.length;
                        int[] iArr6 = new int[(repeatedFieldArrayLength2 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.span, 0, iArr6, 0, length3);
                        }
                        while (length3 < iArr6.length - 1) {
                            iArr6[length3] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        iArr6[length3] = codedInputByteBufferNano.readInt32();
                        this.span = iArr6;
                    } else if (readTag == 18) {
                        int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position2 = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position2);
                        int[] iArr7 = this.span;
                        int length4 = iArr7 == null ? 0 : iArr7.length;
                        int[] iArr8 = new int[(i2 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.span, 0, iArr8, 0, length4);
                        }
                        while (length4 < iArr8.length) {
                            iArr8[length4] = codedInputByteBufferNano.readInt32();
                            length4++;
                        }
                        this.span = iArr8;
                        codedInputByteBufferNano.popLimit(pushLimit2);
                    } else if (readTag == 26) {
                        this.leadingComments = codedInputByteBufferNano.readString();
                    } else if (readTag == 34) {
                        this.trailingComments = codedInputByteBufferNano.readString();
                    } else if (readTag == 50) {
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                        String[] strArr = this.leadingDetachedComments;
                        int length5 = strArr == null ? 0 : strArr.length;
                        String[] strArr2 = new String[(repeatedFieldArrayLength3 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.leadingDetachedComments, 0, strArr2, 0, length5);
                        }
                        while (length5 < strArr2.length - 1) {
                            strArr2[length5] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        strArr2[length5] = codedInputByteBufferNano.readString();
                        this.leadingDetachedComments = strArr2;
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int[] iArr = this.path;
                int i = 0;
                if (iArr != null && iArr.length > 0) {
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        int[] iArr2 = this.path;
                        if (i2 >= iArr2.length) {
                            break;
                        }
                        i3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i2]);
                        i2++;
                    }
                    codedOutputByteBufferNano.writeRawVarint32(10);
                    codedOutputByteBufferNano.writeRawVarint32(i3);
                    int i4 = 0;
                    while (true) {
                        int[] iArr3 = this.path;
                        if (i4 >= iArr3.length) {
                            break;
                        }
                        codedOutputByteBufferNano.writeInt32NoTag(iArr3[i4]);
                        i4++;
                    }
                }
                int[] iArr4 = this.span;
                if (iArr4 != null && iArr4.length > 0) {
                    int i5 = 0;
                    int i6 = 0;
                    while (true) {
                        int[] iArr5 = this.span;
                        if (i5 >= iArr5.length) {
                            break;
                        }
                        i6 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr5[i5]);
                        i5++;
                    }
                    codedOutputByteBufferNano.writeRawVarint32(18);
                    codedOutputByteBufferNano.writeRawVarint32(i6);
                    int i7 = 0;
                    while (true) {
                        int[] iArr6 = this.span;
                        if (i7 >= iArr6.length) {
                            break;
                        }
                        codedOutputByteBufferNano.writeInt32NoTag(iArr6[i7]);
                        i7++;
                    }
                }
                String str = this.leadingComments;
                if (str != null && !str.equals("")) {
                    codedOutputByteBufferNano.writeString(3, this.leadingComments);
                }
                String str2 = this.trailingComments;
                if (str2 != null && !str2.equals("")) {
                    codedOutputByteBufferNano.writeString(4, this.trailingComments);
                }
                String[] strArr = this.leadingDetachedComments;
                if (strArr != null && strArr.length > 0) {
                    while (true) {
                        String[] strArr2 = this.leadingDetachedComments;
                        if (i >= strArr2.length) {
                            break;
                        }
                        String str3 = strArr2[i];
                        if (str3 != null) {
                            codedOutputByteBufferNano.writeString(6, str3);
                        }
                        i++;
                    }
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public SourceCodeInfo() {
            clear();
        }

        public static SourceCodeInfo[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new SourceCodeInfo[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static SourceCodeInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SourceCodeInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static SourceCodeInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            SourceCodeInfo sourceCodeInfo = new SourceCodeInfo();
            MessageNano.mergeFrom(sourceCodeInfo, bArr);
            return sourceCodeInfo;
        }

        public SourceCodeInfo clear() {
            this.location = Location.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            Location[] locationArr = this.location;
            if (locationArr != null && locationArr.length > 0) {
                int i = 0;
                while (true) {
                    Location[] locationArr2 = this.location;
                    if (i >= locationArr2.length) {
                        break;
                    }
                    Location location2 = locationArr2[i];
                    if (location2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, location2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public SourceCodeInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    Location[] locationArr = this.location;
                    int length = locationArr == null ? 0 : locationArr.length;
                    Location[] locationArr2 = new Location[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.location, 0, locationArr2, 0, length);
                    }
                    while (length < locationArr2.length - 1) {
                        locationArr2[length] = new Location();
                        codedInputByteBufferNano.readMessage(locationArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    locationArr2[length] = new Location();
                    codedInputByteBufferNano.readMessage(locationArr2[length]);
                    this.location = locationArr2;
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Location[] locationArr = this.location;
            if (locationArr != null && locationArr.length > 0) {
                int i = 0;
                while (true) {
                    Location[] locationArr2 = this.location;
                    if (i >= locationArr2.length) {
                        break;
                    }
                    Location location2 = locationArr2[i];
                    if (location2 != null) {
                        codedOutputByteBufferNano.writeMessage(1, location2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class StreamDescriptorProto extends ExtendableMessageNano<StreamDescriptorProto> {
        private static volatile StreamDescriptorProto[] _emptyArray;
        public String clientMessageType;
        public String name;
        public StreamOptions options;
        public String serverMessageType;

        public StreamDescriptorProto() {
            clear();
        }

        public static StreamDescriptorProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new StreamDescriptorProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static StreamDescriptorProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new StreamDescriptorProto().mergeFrom(codedInputByteBufferNano);
        }

        public static StreamDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            StreamDescriptorProto streamDescriptorProto = new StreamDescriptorProto();
            MessageNano.mergeFrom(streamDescriptorProto, bArr);
            return streamDescriptorProto;
        }

        public StreamDescriptorProto clear() {
            this.name = "";
            this.clientMessageType = "";
            this.serverMessageType = "";
            this.options = null;
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            String str = this.name;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            String str2 = this.clientMessageType;
            if (str2 != null && !str2.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.clientMessageType);
            }
            String str3 = this.serverMessageType;
            if (str3 != null && !str3.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.serverMessageType);
            }
            StreamOptions streamOptions = this.options;
            return streamOptions != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(4, streamOptions) : computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public StreamDescriptorProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.clientMessageType = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.serverMessageType = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    if (this.options == null) {
                        this.options = new StreamOptions();
                    }
                    codedInputByteBufferNano.readMessage(this.options);
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String str = this.name;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            String str2 = this.clientMessageType;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.clientMessageType);
            }
            String str3 = this.serverMessageType;
            if (str3 != null && !str3.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.serverMessageType);
            }
            StreamOptions streamOptions = this.options;
            if (streamOptions != null) {
                codedOutputByteBufferNano.writeMessage(4, streamOptions);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class StreamOptions extends ExtendableMessageNano<StreamOptions> {
        private static volatile StreamOptions[] _emptyArray;
        public long clientInitialTokens;
        public int clientLogging;
        public double deadline;
        public boolean deprecated;
        public boolean endUserCredsRequested;
        public boolean failFast;
        @NanoEnumValue(legacy = false, value = MethodOptions.LogLevel.class)
        public int logLevel;
        public String securityLabel;
        @NanoEnumValue(legacy = false, value = MethodOptions.SecurityLevel.class)
        public int securityLevel;
        public long serverInitialTokens;
        public int serverLogging;
        @NanoEnumValue(legacy = false, value = TokenUnit.class)
        public int tokenUnit;
        public UninterpretedOption[] uninterpretedOption;

        public interface TokenUnit {
            @NanoEnumValue(legacy = false, value = TokenUnit.class)
            public static final int BYTE = 1;
            @NanoEnumValue(legacy = false, value = TokenUnit.class)
            public static final int MESSAGE = 0;
        }

        public StreamOptions() {
            clear();
        }

        @NanoEnumValue(legacy = false, value = TokenUnit.class)
        public static int checkTokenUnitOrThrow(int i) {
            if (i >= 0 && i <= 1) {
                return i;
            }
            StringBuilder sb = new StringBuilder(41);
            sb.append(i);
            sb.append(" is not a valid enum TokenUnit");
            throw new IllegalArgumentException(sb.toString());
        }

        @NanoEnumValue(legacy = false, value = TokenUnit.class)
        public static int[] checkTokenUnitOrThrow(int[] iArr) {
            int[] iArr2 = (int[]) iArr.clone();
            for (int i : iArr2) {
                checkTokenUnitOrThrow(i);
            }
            return iArr2;
        }

        public static StreamOptions[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new StreamOptions[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static StreamOptions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new StreamOptions().mergeFrom(codedInputByteBufferNano);
        }

        public static StreamOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            StreamOptions streamOptions = new StreamOptions();
            MessageNano.mergeFrom(streamOptions, bArr);
            return streamOptions;
        }

        public StreamOptions clear() {
            this.clientInitialTokens = -1;
            this.serverInitialTokens = -1;
            this.tokenUnit = 0;
            this.securityLevel = 0;
            this.securityLabel = "";
            this.clientLogging = 256;
            this.serverLogging = 256;
            this.deadline = -1.0d;
            this.failFast = false;
            this.endUserCredsRequested = false;
            this.logLevel = 2;
            this.deprecated = false;
            this.uninterpretedOption = UninterpretedOption.emptyArray();
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.clientInitialTokens;
            if (j != -1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            long j2 = this.serverInitialTokens;
            if (j2 != -1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(2, j2);
            }
            int i = this.tokenUnit;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
            }
            int i2 = this.securityLevel;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i2);
            }
            String str = this.securityLabel;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.securityLabel);
            }
            int i3 = this.clientLogging;
            if (i3 != 256) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, i3);
            }
            int i4 = this.serverLogging;
            if (i4 != 256) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, i4);
            }
            if (Double.doubleToLongBits(this.deadline) != Double.doubleToLongBits(-1.0d)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(8, this.deadline);
            }
            boolean z = this.failFast;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(9, z);
            }
            boolean z2 = this.endUserCredsRequested;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(10, z2);
            }
            int i5 = this.logLevel;
            if (i5 != 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(11, i5);
            }
            boolean z3 = this.deprecated;
            if (z3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(33, z3);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i6 = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i6 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i6];
                    if (uninterpretedOption2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(999, uninterpretedOption2);
                    }
                    i6++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public StreamOptions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.clientInitialTokens = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.serverInitialTokens = codedInputByteBufferNano.readInt64();
                        break;
                    case 24:
                        int position = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt32 = codedInputByteBufferNano.readInt32();
                            checkTokenUnitOrThrow(readInt32);
                            this.tokenUnit = readInt32;
                            break;
                        } catch (IllegalArgumentException unused) {
                            codedInputByteBufferNano.rewindToPosition(position);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 32:
                        int position2 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt322 = codedInputByteBufferNano.readInt32();
                            MethodOptions.checkSecurityLevelOrThrow(readInt322);
                            this.securityLevel = readInt322;
                            break;
                        } catch (IllegalArgumentException unused2) {
                            codedInputByteBufferNano.rewindToPosition(position2);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 42:
                        this.securityLabel = codedInputByteBufferNano.readString();
                        break;
                    case 48:
                        this.clientLogging = codedInputByteBufferNano.readInt32();
                        break;
                    case 56:
                        this.serverLogging = codedInputByteBufferNano.readInt32();
                        break;
                    case 65:
                        this.deadline = codedInputByteBufferNano.readDouble();
                        break;
                    case 72:
                        this.failFast = codedInputByteBufferNano.readBool();
                        break;
                    case 80:
                        this.endUserCredsRequested = codedInputByteBufferNano.readBool();
                        break;
                    case 88:
                        int position3 = codedInputByteBufferNano.getPosition();
                        try {
                            int readInt323 = codedInputByteBufferNano.readInt32();
                            MethodOptions.checkLogLevelOrThrow(readInt323);
                            this.logLevel = readInt323;
                            break;
                        } catch (IllegalArgumentException unused3) {
                            codedInputByteBufferNano.rewindToPosition(position3);
                            storeUnknownField(codedInputByteBufferNano, readTag);
                            break;
                        }
                    case 264:
                        this.deprecated = codedInputByteBufferNano.readBool();
                        break;
                    case 7994:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 7994);
                        UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
                        int length = uninterpretedOptionArr == null ? 0 : uninterpretedOptionArr.length;
                        UninterpretedOption[] uninterpretedOptionArr2 = new UninterpretedOption[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.uninterpretedOption, 0, uninterpretedOptionArr2, 0, length);
                        }
                        while (length < uninterpretedOptionArr2.length - 1) {
                            uninterpretedOptionArr2[length] = new UninterpretedOption();
                            codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        uninterpretedOptionArr2[length] = new UninterpretedOption();
                        codedInputByteBufferNano.readMessage(uninterpretedOptionArr2[length]);
                        this.uninterpretedOption = uninterpretedOptionArr2;
                        break;
                    default:
                        if (super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.clientInitialTokens;
            if (j != -1) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            long j2 = this.serverInitialTokens;
            if (j2 != -1) {
                codedOutputByteBufferNano.writeInt64(2, j2);
            }
            int i = this.tokenUnit;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(3, i);
            }
            int i2 = this.securityLevel;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i2);
            }
            String str = this.securityLabel;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.securityLabel);
            }
            int i3 = this.clientLogging;
            if (i3 != 256) {
                codedOutputByteBufferNano.writeInt32(6, i3);
            }
            int i4 = this.serverLogging;
            if (i4 != 256) {
                codedOutputByteBufferNano.writeInt32(7, i4);
            }
            if (Double.doubleToLongBits(this.deadline) != Double.doubleToLongBits(-1.0d)) {
                codedOutputByteBufferNano.writeDouble(8, this.deadline);
            }
            boolean z = this.failFast;
            if (z) {
                codedOutputByteBufferNano.writeBool(9, z);
            }
            boolean z2 = this.endUserCredsRequested;
            if (z2) {
                codedOutputByteBufferNano.writeBool(10, z2);
            }
            int i5 = this.logLevel;
            if (i5 != 2) {
                codedOutputByteBufferNano.writeInt32(11, i5);
            }
            boolean z3 = this.deprecated;
            if (z3) {
                codedOutputByteBufferNano.writeBool(33, z3);
            }
            UninterpretedOption[] uninterpretedOptionArr = this.uninterpretedOption;
            if (uninterpretedOptionArr != null && uninterpretedOptionArr.length > 0) {
                int i6 = 0;
                while (true) {
                    UninterpretedOption[] uninterpretedOptionArr2 = this.uninterpretedOption;
                    if (i6 >= uninterpretedOptionArr2.length) {
                        break;
                    }
                    UninterpretedOption uninterpretedOption2 = uninterpretedOptionArr2[i6];
                    if (uninterpretedOption2 != null) {
                        codedOutputByteBufferNano.writeMessage(999, uninterpretedOption2);
                    }
                    i6++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class UninterpretedOption extends ExtendableMessageNano<UninterpretedOption> {
        private static volatile UninterpretedOption[] _emptyArray;
        public String aggregateValue;
        public double doubleValue;
        public String identifierValue;
        public NamePart[] name;
        public long negativeIntValue;
        public long positiveIntValue;
        public byte[] stringValue;

        public static final class NamePart extends ExtendableMessageNano<NamePart> {
            private static volatile NamePart[] _emptyArray;
            public boolean isExtension;
            public String namePart;

            public NamePart() {
                clear();
            }

            public static NamePart[] emptyArray() {
                if (_emptyArray == null) {
                    synchronized (InternalNano.LAZY_INIT_LOCK) {
                        if (_emptyArray == null) {
                            _emptyArray = new NamePart[0];
                        }
                    }
                }
                return _emptyArray;
            }

            public static NamePart parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new NamePart().mergeFrom(codedInputByteBufferNano);
            }

            public static NamePart parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
                NamePart namePart2 = new NamePart();
                MessageNano.mergeFrom(namePart2, bArr);
                return namePart2;
            }

            public NamePart clear() {
                this.namePart = "";
                this.isExtension = false;
                ((ExtendableMessageNano) this).unknownFieldData = null;
                ((MessageNano) this).cachedSize = -1;
                return this;
            }

            /* access modifiers changed from: protected */
            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public int computeSerializedSize() {
                return super.computeSerializedSize() + CodedOutputByteBufferNano.computeStringSize(1, this.namePart) + CodedOutputByteBufferNano.computeBoolSize(2, this.isExtension);
            }

            @Override // com.google.protobuf.nano.MessageNano
            public NamePart mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                while (true) {
                    int readTag = codedInputByteBufferNano.readTag();
                    if (readTag == 0) {
                        return this;
                    }
                    if (readTag == 10) {
                        this.namePart = codedInputByteBufferNano.readString();
                    } else if (readTag == 16) {
                        this.isExtension = codedInputByteBufferNano.readBool();
                    } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                }
            }

            @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                codedOutputByteBufferNano.writeString(1, this.namePart);
                codedOutputByteBufferNano.writeBool(2, this.isExtension);
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public UninterpretedOption() {
            clear();
        }

        public static UninterpretedOption[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new UninterpretedOption[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static UninterpretedOption parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new UninterpretedOption().mergeFrom(codedInputByteBufferNano);
        }

        public static UninterpretedOption parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            UninterpretedOption uninterpretedOption = new UninterpretedOption();
            MessageNano.mergeFrom(uninterpretedOption, bArr);
            return uninterpretedOption;
        }

        public UninterpretedOption clear() {
            this.name = NamePart.emptyArray();
            this.identifierValue = "";
            this.positiveIntValue = 0;
            this.negativeIntValue = 0;
            this.doubleValue = 0.0d;
            this.stringValue = WireFormatNano.EMPTY_BYTES;
            this.aggregateValue = "";
            ((ExtendableMessageNano) this).unknownFieldData = null;
            ((MessageNano) this).cachedSize = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            NamePart[] namePartArr = this.name;
            if (namePartArr != null && namePartArr.length > 0) {
                int i = 0;
                while (true) {
                    NamePart[] namePartArr2 = this.name;
                    if (i >= namePartArr2.length) {
                        break;
                    }
                    NamePart namePart = namePartArr2[i];
                    if (namePart != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, namePart);
                    }
                    i++;
                }
            }
            String str = this.identifierValue;
            if (str != null && !str.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.identifierValue);
            }
            long j = this.positiveIntValue;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeUInt64Size(4, j);
            }
            long j2 = this.negativeIntValue;
            if (j2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, j2);
            }
            if (Double.doubleToLongBits(this.doubleValue) != Double.doubleToLongBits(0.0d)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(6, this.doubleValue);
            }
            if (!Arrays.equals(this.stringValue, WireFormatNano.EMPTY_BYTES)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBytesSize(7, this.stringValue);
            }
            String str2 = this.aggregateValue;
            return (str2 == null || str2.equals("")) ? computeSerializedSize : computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(8, this.aggregateValue);
        }

        @Override // com.google.protobuf.nano.MessageNano
        public UninterpretedOption mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    NamePart[] namePartArr = this.name;
                    int length = namePartArr == null ? 0 : namePartArr.length;
                    NamePart[] namePartArr2 = new NamePart[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.name, 0, namePartArr2, 0, length);
                    }
                    while (length < namePartArr2.length - 1) {
                        namePartArr2[length] = new NamePart();
                        codedInputByteBufferNano.readMessage(namePartArr2[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    namePartArr2[length] = new NamePart();
                    codedInputByteBufferNano.readMessage(namePartArr2[length]);
                    this.name = namePartArr2;
                } else if (readTag == 26) {
                    this.identifierValue = codedInputByteBufferNano.readString();
                } else if (readTag == 32) {
                    this.positiveIntValue = codedInputByteBufferNano.readUInt64();
                } else if (readTag == 40) {
                    this.negativeIntValue = codedInputByteBufferNano.readInt64();
                } else if (readTag == 49) {
                    this.doubleValue = codedInputByteBufferNano.readDouble();
                } else if (readTag == 58) {
                    this.stringValue = codedInputByteBufferNano.readBytes();
                } else if (readTag == 66) {
                    this.aggregateValue = codedInputByteBufferNano.readString();
                } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }

        @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            NamePart[] namePartArr = this.name;
            if (namePartArr != null && namePartArr.length > 0) {
                int i = 0;
                while (true) {
                    NamePart[] namePartArr2 = this.name;
                    if (i >= namePartArr2.length) {
                        break;
                    }
                    NamePart namePart = namePartArr2[i];
                    if (namePart != null) {
                        codedOutputByteBufferNano.writeMessage(2, namePart);
                    }
                    i++;
                }
            }
            String str = this.identifierValue;
            if (str != null && !str.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.identifierValue);
            }
            long j = this.positiveIntValue;
            if (j != 0) {
                codedOutputByteBufferNano.writeUInt64(4, j);
            }
            long j2 = this.negativeIntValue;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(5, j2);
            }
            if (Double.doubleToLongBits(this.doubleValue) != Double.doubleToLongBits(0.0d)) {
                codedOutputByteBufferNano.writeDouble(6, this.doubleValue);
            }
            if (!Arrays.equals(this.stringValue, WireFormatNano.EMPTY_BYTES)) {
                codedOutputByteBufferNano.writeBytes(7, this.stringValue);
            }
            String str2 = this.aggregateValue;
            if (str2 != null && !str2.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.aggregateValue);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    private DescriptorProtos() {
    }
}
