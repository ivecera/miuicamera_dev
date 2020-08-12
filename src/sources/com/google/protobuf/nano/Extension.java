package com.google.protobuf.nano;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLite;
import com.google.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Extension<M extends ExtendableMessageNano<M>, T> {
    public static final int TYPE_BOOL = 8;
    public static final int TYPE_BYTES = 12;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_ENUM = 14;
    public static final int TYPE_FIXED32 = 7;
    public static final int TYPE_FIXED64 = 6;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_GROUP = 10;
    public static final int TYPE_INT32 = 5;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_MESSAGE = 11;
    public static final int TYPE_SFIXED32 = 15;
    public static final int TYPE_SFIXED64 = 16;
    public static final int TYPE_SINT32 = 17;
    public static final int TYPE_SINT64 = 18;
    public static final int TYPE_STRING = 9;
    public static final int TYPE_UINT32 = 13;
    public static final int TYPE_UINT64 = 4;
    protected final Class<T> clazz;
    protected final GeneratedMessageLite<?, ?> defaultInstance;
    protected final boolean repeated;
    public final int tag;
    protected final int type;

    private static class PrimitiveExtension<M extends ExtendableMessageNano<M>, T> extends Extension<M, T> {
        private final int nonPackedTag;
        private final int packedTag;

        public PrimitiveExtension(int i, Class<T> cls, int i2, boolean z, int i3, int i4) {
            super(i, cls, i2, z);
            this.nonPackedTag = i3;
            this.packedTag = i4;
        }

        private int computePackedDataSize(Object obj) {
            int length = Array.getLength(obj);
            int i = ((Extension) this).type;
            int i2 = 0;
            switch (i) {
                case 1:
                case 6:
                case 16:
                    length *= 8;
                    break;
                case 2:
                case 7:
                case 15:
                    length *= 4;
                    break;
                case 3:
                    int i3 = 0;
                    while (i2 < length) {
                        i3 += CodedOutputByteBufferNano.computeInt64SizeNoTag(Array.getLong(obj, i2));
                        i2++;
                    }
                    return i3;
                case 4:
                    int i4 = 0;
                    while (i2 < length) {
                        i4 += CodedOutputByteBufferNano.computeUInt64SizeNoTag(Array.getLong(obj, i2));
                        i2++;
                    }
                    return i4;
                case 5:
                    int i5 = 0;
                    while (i2 < length) {
                        i5 += CodedOutputByteBufferNano.computeInt32SizeNoTag(Array.getInt(obj, i2));
                        i2++;
                    }
                    return i5;
                case 8:
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                default:
                    StringBuilder sb = new StringBuilder(40);
                    sb.append("Unexpected non-packable type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                case 13:
                    int i6 = 0;
                    while (i2 < length) {
                        i6 += CodedOutputByteBufferNano.computeUInt32SizeNoTag(Array.getInt(obj, i2));
                        i2++;
                    }
                    return i6;
                case 14:
                    int i7 = 0;
                    while (i2 < length) {
                        i7 += CodedOutputByteBufferNano.computeEnumSizeNoTag(Array.getInt(obj, i2));
                        i2++;
                    }
                    return i7;
                case 17:
                    int i8 = 0;
                    while (i2 < length) {
                        i8 += CodedOutputByteBufferNano.computeSInt32SizeNoTag(Array.getInt(obj, i2));
                        i2++;
                    }
                    return i8;
                case 18:
                    int i9 = 0;
                    while (i2 < length) {
                        i9 += CodedOutputByteBufferNano.computeSInt64SizeNoTag(Array.getLong(obj, i2));
                        i2++;
                    }
                    return i9;
            }
            return length;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.Extension
        public int computeRepeatedSerializedSize(Object obj) {
            int i = ((Extension) this).tag;
            int i2 = this.nonPackedTag;
            if (i == i2) {
                return Extension.super.computeRepeatedSerializedSize(obj);
            }
            int i3 = this.packedTag;
            if (i == i3) {
                int computePackedDataSize = computePackedDataSize(obj);
                return computePackedDataSize + CodedOutputByteBufferNano.computeRawVarint32Size(computePackedDataSize) + CodedOutputByteBufferNano.computeRawVarint32Size(((Extension) this).tag);
            }
            StringBuilder sb = new StringBuilder(124);
            sb.append("Unexpected repeated extension tag ");
            sb.append(i);
            sb.append(", unequal to both non-packed variant ");
            sb.append(i2);
            sb.append(" and packed variant ");
            sb.append(i3);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.Extension
        public final int computeSingularSerializedSize(Object obj) {
            int tagFieldNumber = WireFormatNano.getTagFieldNumber(((Extension) this).tag);
            int i = ((Extension) this).type;
            switch (i) {
                case 1:
                    return CodedOutputByteBufferNano.computeDoubleSize(tagFieldNumber, ((Double) obj).doubleValue());
                case 2:
                    return CodedOutputByteBufferNano.computeFloatSize(tagFieldNumber, ((Float) obj).floatValue());
                case 3:
                    return CodedOutputByteBufferNano.computeInt64Size(tagFieldNumber, ((Long) obj).longValue());
                case 4:
                    return CodedOutputByteBufferNano.computeUInt64Size(tagFieldNumber, ((Long) obj).longValue());
                case 5:
                    return CodedOutputByteBufferNano.computeInt32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 6:
                    return CodedOutputByteBufferNano.computeFixed64Size(tagFieldNumber, ((Long) obj).longValue());
                case 7:
                    return CodedOutputByteBufferNano.computeFixed32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 8:
                    return CodedOutputByteBufferNano.computeBoolSize(tagFieldNumber, ((Boolean) obj).booleanValue());
                case 9:
                    return CodedOutputByteBufferNano.computeStringSize(tagFieldNumber, (String) obj);
                case 10:
                case 11:
                default:
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                case 12:
                    return CodedOutputByteBufferNano.computeBytesSize(tagFieldNumber, (byte[]) obj);
                case 13:
                    return CodedOutputByteBufferNano.computeUInt32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 14:
                    return CodedOutputByteBufferNano.computeEnumSize(tagFieldNumber, ((Integer) obj).intValue());
                case 15:
                    return CodedOutputByteBufferNano.computeSFixed32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 16:
                    return CodedOutputByteBufferNano.computeSFixed64Size(tagFieldNumber, ((Long) obj).longValue());
                case 17:
                    return CodedOutputByteBufferNano.computeSInt32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 18:
                    return CodedOutputByteBufferNano.computeSInt64Size(tagFieldNumber, ((Long) obj).longValue());
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.Extension
        public Object readData(CodedInputByteBufferNano codedInputByteBufferNano) {
            try {
                switch (((Extension) this).type) {
                    case 1:
                        return Double.valueOf(codedInputByteBufferNano.readDouble());
                    case 2:
                        return Float.valueOf(codedInputByteBufferNano.readFloat());
                    case 3:
                        return Long.valueOf(codedInputByteBufferNano.readInt64());
                    case 4:
                        return Long.valueOf(codedInputByteBufferNano.readUInt64());
                    case 5:
                        return Integer.valueOf(codedInputByteBufferNano.readInt32());
                    case 6:
                        return Long.valueOf(codedInputByteBufferNano.readFixed64());
                    case 7:
                        return Integer.valueOf(codedInputByteBufferNano.readFixed32());
                    case 8:
                        return Boolean.valueOf(codedInputByteBufferNano.readBool());
                    case 9:
                        return codedInputByteBufferNano.readString();
                    case 10:
                    case 11:
                    default:
                        int i = ((Extension) this).type;
                        StringBuilder sb = new StringBuilder(24);
                        sb.append("Unknown type ");
                        sb.append(i);
                        throw new IllegalArgumentException(sb.toString());
                    case 12:
                        return codedInputByteBufferNano.readBytes();
                    case 13:
                        return Integer.valueOf(codedInputByteBufferNano.readUInt32());
                    case 14:
                        return Integer.valueOf(codedInputByteBufferNano.readEnum());
                    case 15:
                        return Integer.valueOf(codedInputByteBufferNano.readSFixed32());
                    case 16:
                        return Long.valueOf(codedInputByteBufferNano.readSFixed64());
                    case 17:
                        return Integer.valueOf(codedInputByteBufferNano.readSInt32());
                    case 18:
                        return Long.valueOf(codedInputByteBufferNano.readSInt64());
                }
            } catch (IOException e2) {
                throw new IllegalArgumentException("Error reading extension field", e2);
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.Extension
        public void readDataInto(UnknownFieldData unknownFieldData, List<Object> list) {
            if (unknownFieldData.tag == this.nonPackedTag) {
                list.add(readData(CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
                return;
            }
            CodedInputByteBufferNano newInstance = CodedInputByteBufferNano.newInstance(unknownFieldData.bytes);
            try {
                newInstance.pushLimit(newInstance.readRawVarint32());
                while (!newInstance.isAtEnd()) {
                    list.add(readData(newInstance));
                }
            } catch (IOException e2) {
                throw new IllegalArgumentException("Error reading extension field", e2);
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.Extension
        public void writeRepeatedData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
            int i = ((Extension) this).tag;
            int i2 = this.nonPackedTag;
            if (i == i2) {
                Extension.super.writeRepeatedData(obj, codedOutputByteBufferNano);
                return;
            }
            int i3 = this.packedTag;
            if (i == i3) {
                int length = Array.getLength(obj);
                int computePackedDataSize = computePackedDataSize(obj);
                try {
                    codedOutputByteBufferNano.writeRawVarint32(((Extension) this).tag);
                    codedOutputByteBufferNano.writeRawVarint32(computePackedDataSize);
                    int i4 = 0;
                    switch (((Extension) this).type) {
                        case 1:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeDoubleNoTag(Array.getDouble(obj, i4));
                                i4++;
                            }
                            return;
                        case 2:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeFloatNoTag(Array.getFloat(obj, i4));
                                i4++;
                            }
                            return;
                        case 3:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeInt64NoTag(Array.getLong(obj, i4));
                                i4++;
                            }
                            return;
                        case 4:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeUInt64NoTag(Array.getLong(obj, i4));
                                i4++;
                            }
                            return;
                        case 5:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeInt32NoTag(Array.getInt(obj, i4));
                                i4++;
                            }
                            return;
                        case 6:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeFixed64NoTag(Array.getLong(obj, i4));
                                i4++;
                            }
                            return;
                        case 7:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeFixed32NoTag(Array.getInt(obj, i4));
                                i4++;
                            }
                            return;
                        case 8:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeBoolNoTag(Array.getBoolean(obj, i4));
                                i4++;
                            }
                            return;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        default:
                            int i5 = ((Extension) this).type;
                            StringBuilder sb = new StringBuilder(27);
                            sb.append("Unpackable type ");
                            sb.append(i5);
                            throw new IllegalArgumentException(sb.toString());
                        case 13:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeUInt32NoTag(Array.getInt(obj, i4));
                                i4++;
                            }
                            return;
                        case 14:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeEnumNoTag(Array.getInt(obj, i4));
                                i4++;
                            }
                            return;
                        case 15:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeSFixed32NoTag(Array.getInt(obj, i4));
                                i4++;
                            }
                            return;
                        case 16:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeSFixed64NoTag(Array.getLong(obj, i4));
                                i4++;
                            }
                            return;
                        case 17:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeSInt32NoTag(Array.getInt(obj, i4));
                                i4++;
                            }
                            return;
                        case 18:
                            while (i4 < length) {
                                codedOutputByteBufferNano.writeSInt64NoTag(Array.getLong(obj, i4));
                                i4++;
                            }
                            return;
                    }
                } catch (IOException e2) {
                    throw new IllegalStateException(e2);
                }
            } else {
                StringBuilder sb2 = new StringBuilder(124);
                sb2.append("Unexpected repeated extension tag ");
                sb2.append(i);
                sb2.append(", unequal to both non-packed variant ");
                sb2.append(i2);
                sb2.append(" and packed variant ");
                sb2.append(i3);
                throw new IllegalArgumentException(sb2.toString());
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.google.protobuf.nano.Extension
        public final void writeSingularData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
            try {
                codedOutputByteBufferNano.writeRawVarint32(((Extension) this).tag);
                switch (((Extension) this).type) {
                    case 1:
                        codedOutputByteBufferNano.writeDoubleNoTag(((Double) obj).doubleValue());
                        return;
                    case 2:
                        codedOutputByteBufferNano.writeFloatNoTag(((Float) obj).floatValue());
                        return;
                    case 3:
                        codedOutputByteBufferNano.writeInt64NoTag(((Long) obj).longValue());
                        return;
                    case 4:
                        codedOutputByteBufferNano.writeUInt64NoTag(((Long) obj).longValue());
                        return;
                    case 5:
                        codedOutputByteBufferNano.writeInt32NoTag(((Integer) obj).intValue());
                        return;
                    case 6:
                        codedOutputByteBufferNano.writeFixed64NoTag(((Long) obj).longValue());
                        return;
                    case 7:
                        codedOutputByteBufferNano.writeFixed32NoTag(((Integer) obj).intValue());
                        return;
                    case 8:
                        codedOutputByteBufferNano.writeBoolNoTag(((Boolean) obj).booleanValue());
                        return;
                    case 9:
                        codedOutputByteBufferNano.writeStringNoTag((String) obj);
                        return;
                    case 10:
                    case 11:
                    default:
                        int i = ((Extension) this).type;
                        StringBuilder sb = new StringBuilder(24);
                        sb.append("Unknown type ");
                        sb.append(i);
                        throw new IllegalArgumentException(sb.toString());
                    case 12:
                        codedOutputByteBufferNano.writeBytesNoTag((byte[]) obj);
                        return;
                    case 13:
                        codedOutputByteBufferNano.writeUInt32NoTag(((Integer) obj).intValue());
                        return;
                    case 14:
                        codedOutputByteBufferNano.writeEnumNoTag(((Integer) obj).intValue());
                        return;
                    case 15:
                        codedOutputByteBufferNano.writeSFixed32NoTag(((Integer) obj).intValue());
                        return;
                    case 16:
                        codedOutputByteBufferNano.writeSFixed64NoTag(((Long) obj).longValue());
                        return;
                    case 17:
                        codedOutputByteBufferNano.writeSInt32NoTag(((Integer) obj).intValue());
                        return;
                    case 18:
                        codedOutputByteBufferNano.writeSInt64NoTag(((Long) obj).longValue());
                        return;
                }
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    private Extension(int i, Class<T> cls, int i2, boolean z) {
        this(i, cls, (GeneratedMessageLite<?, ?>) null, i2, z);
    }

    private Extension(int i, Class<T> cls, GeneratedMessageLite<?, ?> generatedMessageLite, int i2, boolean z) {
        this.type = i;
        this.clazz = cls;
        this.tag = i2;
        this.repeated = z;
        this.defaultInstance = generatedMessageLite;
    }

    public static <M extends ExtendableMessageNano<M>, T extends GeneratedMessageLite<?, ?>> Extension<M, T> createMessageLiteTyped(int i, Class<T> cls, T t, long j) {
        return new Extension<>(i, (Class) cls, (GeneratedMessageLite<?, ?>) t, (int) j, false);
    }

    @Deprecated
    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int i, Class<T> cls, int i2) {
        return new Extension<>(i, cls, i2, false);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int i, Class<T> cls, long j) {
        return new Extension<>(i, cls, (int) j, false);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createPrimitiveTyped(int i, Class<T> cls, long j) {
        return new PrimitiveExtension(i, cls, (int) j, false, 0, 0);
    }

    public static <M extends ExtendableMessageNano<M>, T extends GeneratedMessageLite<?, ?>> Extension<M, T[]> createRepeatedMessageLiteTyped(int i, Class<T[]> cls, T t, long j) {
        return new Extension<>(i, cls, (GeneratedMessageLite<?, ?>) t, (int) j, true);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T[]> createRepeatedMessageTyped(int i, Class<T[]> cls, long j) {
        return new Extension<>(i, cls, (int) j, true);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createRepeatedPrimitiveTyped(int i, Class<T> cls, long j, long j2, long j3) {
        return new PrimitiveExtension(i, cls, (int) j, true, (int) j2, (int) j3);
    }

    private T getRepeatedValueFrom(List<UnknownFieldData> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            UnknownFieldData unknownFieldData = list.get(i);
            if (unknownFieldData.bytes.length != 0) {
                readDataInto(unknownFieldData, arrayList);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        Class<T> cls = this.clazz;
        T cast = cls.cast(Array.newInstance(cls.getComponentType(), size));
        for (int i2 = 0; i2 < size; i2++) {
            Array.set(cast, i2, arrayList.get(i2));
        }
        return cast;
    }

    private T getSingularValueFrom(List<UnknownFieldData> list) {
        if (list.isEmpty()) {
            return null;
        }
        return this.clazz.cast(readData(CodedInputByteBufferNano.newInstance(list.get(list.size() - 1).bytes)));
    }

    /* access modifiers changed from: protected */
    public int computeRepeatedSerializedSize(Object obj) {
        int length = Array.getLength(obj);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Array.get(obj, i2) != null) {
                i += computeSingularSerializedSize(Array.get(obj, i2));
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int computeRepeatedSerializedSizeAsMessageSet(Object obj) {
        int length = Array.getLength(obj);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Array.get(obj, i2) != null) {
                i += computeSingularSerializedSizeAsMessageSet(Array.get(obj, i2));
            }
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public int computeSerializedSize(Object obj) {
        return this.repeated ? computeRepeatedSerializedSize(obj) : computeSingularSerializedSize(obj);
    }

    /* access modifiers changed from: package-private */
    public int computeSerializedSizeAsMessageSet(Object obj) {
        return this.repeated ? computeRepeatedSerializedSizeAsMessageSet(obj) : computeSingularSerializedSizeAsMessageSet(obj);
    }

    /* access modifiers changed from: protected */
    public int computeSingularSerializedSize(Object obj) {
        int tagFieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
        int i = this.type;
        if (i == 10) {
            return this.defaultInstance == null ? CodedOutputByteBufferNano.computeGroupSize(tagFieldNumber, (MessageNano) obj) : CodedOutputStream.computeGroupSize(tagFieldNumber, (MessageLite) obj);
        }
        if (i == 11) {
            return this.defaultInstance == null ? CodedOutputByteBufferNano.computeMessageSize(tagFieldNumber, (MessageNano) obj) : CodedOutputStream.computeMessageSize(tagFieldNumber, (MessageLite) obj);
        }
        StringBuilder sb = new StringBuilder(24);
        sb.append("Unknown type ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public int computeSingularSerializedSizeAsMessageSet(Object obj) {
        return CodedOutputByteBufferNano.computeMessageSetExtensionSize(WireFormatNano.getTagFieldNumber(this.tag), (MessageNano) obj);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Extension)) {
            return false;
        }
        Extension extension = (Extension) obj;
        return this.type == extension.type && this.clazz == extension.clazz && this.tag == extension.tag && this.repeated == extension.repeated;
    }

    /* access modifiers changed from: package-private */
    public final T getValueFrom(List<UnknownFieldData> list) {
        if (list == null) {
            return null;
        }
        return this.repeated ? getRepeatedValueFrom(list) : getSingularValueFrom(list);
    }

    public int hashCode() {
        return ((((((1147 + this.type) * 31) + this.clazz.hashCode()) * 31) + this.tag) * 31) + (this.repeated ? 1 : 0);
    }

    /* access modifiers changed from: protected */
    public Object readData(CodedInputByteBufferNano codedInputByteBufferNano) {
        Class componentType = this.repeated ? this.clazz.getComponentType() : this.clazz;
        try {
            int i = this.type;
            if (i == 10) {
                T newInstance = componentType.newInstance();
                codedInputByteBufferNano.readGroup(newInstance, WireFormatNano.getTagFieldNumber(this.tag));
                return newInstance;
            } else if (i != 11) {
                int i2 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.defaultInstance != null) {
                return codedInputByteBufferNano.readMessageLite(this.defaultInstance.getParserForType());
            } else {
                T newInstance2 = componentType.newInstance();
                codedInputByteBufferNano.readMessage(newInstance2);
                return newInstance2;
            }
        } catch (InstantiationException e2) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e2);
        } catch (IllegalAccessException e3) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e3);
        } catch (IOException e4) {
            throw new IllegalArgumentException("Error reading extension field", e4);
        }
    }

    /* access modifiers changed from: protected */
    public void readDataInto(UnknownFieldData unknownFieldData, List<Object> list) {
        list.add(readData(CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
    }

    /* access modifiers changed from: package-private */
    public void writeAsMessageSetTo(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.repeated) {
            writeRepeatedDataAsMessageSet(obj, codedOutputByteBufferNano);
        } else {
            writeSingularDataAsMessageSet(obj, codedOutputByteBufferNano);
        }
    }

    /* access modifiers changed from: protected */
    public void writeRepeatedData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                writeSingularData(obj2, codedOutputByteBufferNano);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeRepeatedDataAsMessageSet(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                writeSingularDataAsMessageSet(obj2, codedOutputByteBufferNano);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeSingularData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
        try {
            codedOutputByteBufferNano.writeRawVarint32(this.tag);
            int i = this.type;
            if (i == 10) {
                int tagFieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
                if (this.defaultInstance == null) {
                    codedOutputByteBufferNano.writeGroupNoTag((MessageNano) obj);
                } else {
                    codedOutputByteBufferNano.writeGroupNoTag((MessageLite) obj);
                }
                codedOutputByteBufferNano.writeTag(tagFieldNumber, 4);
            } else if (i != 11) {
                int i2 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.defaultInstance == null) {
                codedOutputByteBufferNano.writeMessageNoTag((MessageNano) obj);
            } else {
                codedOutputByteBufferNano.writeMessageNoTag((MessageLite) obj);
            }
        } catch (IOException e2) {
            throw new IllegalStateException(e2);
        }
    }

    /* access modifiers changed from: protected */
    public void writeSingularDataAsMessageSet(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        codedOutputByteBufferNano.writeMessageSetExtension(WireFormatNano.getTagFieldNumber(this.tag), (MessageNano) obj);
    }

    /* access modifiers changed from: package-private */
    public void writeTo(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.repeated) {
            writeRepeatedData(obj, codedOutputByteBufferNano);
        } else {
            writeSingularData(obj, codedOutputByteBufferNano);
        }
    }
}
