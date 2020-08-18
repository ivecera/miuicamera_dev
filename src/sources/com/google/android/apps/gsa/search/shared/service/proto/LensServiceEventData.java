package com.google.android.apps.gsa.search.shared.service.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoField;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoPresenceBits;
import com.google.protobuf.ProtoPresenceCheckedField;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
public final class LensServiceEventData extends GeneratedMessageLite<LensServiceEventData, Builder> implements LensServiceEventDataOrBuilder {
    /* access modifiers changed from: private */
    public static final LensServiceEventData DEFAULT_INSTANCE = new LensServiceEventData();
    private static volatile Parser<LensServiceEventData> PARSER = null;
    public static final int SERVICE_API_VERSION_FIELD_NUMBER = 1;
    @ProtoPresenceBits(id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private int serviceApiVersion_;

    /* renamed from: com.google.android.apps.gsa.search.shared.service.proto.LensServiceEventData$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = new int[GeneratedMessageLite.MethodToInvoke.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<LensServiceEventData, Builder> implements LensServiceEventDataOrBuilder {
        private Builder() {
            super(LensServiceEventData.DEFAULT_INSTANCE);
        }

        /* synthetic */ Builder(AnonymousClass1 r1) {
            this();
        }

        public Builder clearServiceApiVersion() {
            copyOnWrite();
            this.instance.clearServiceApiVersion();
            return this;
        }

        @Override // com.google.android.apps.gsa.search.shared.service.proto.LensServiceEventDataOrBuilder
        public int getServiceApiVersion() {
            return this.instance.getServiceApiVersion();
        }

        @Override // com.google.android.apps.gsa.search.shared.service.proto.LensServiceEventDataOrBuilder
        public boolean hasServiceApiVersion() {
            return this.instance.hasServiceApiVersion();
        }

        public Builder setServiceApiVersion(int i) {
            copyOnWrite();
            this.instance.setServiceApiVersion(i);
            return this;
        }
    }

    static {
        GeneratedMessageLite.registerDefaultInstance(LensServiceEventData.class, DEFAULT_INSTANCE);
    }

    private LensServiceEventData() {
    }

    /* access modifiers changed from: private */
    public void clearServiceApiVersion() {
        this.bitField0_ &= -2;
        this.serviceApiVersion_ = 0;
    }

    public static LensServiceEventData getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(LensServiceEventData lensServiceEventData) {
        return DEFAULT_INSTANCE.createBuilder(lensServiceEventData);
    }

    public static LensServiceEventData parseDelimitedFrom(InputStream inputStream) throws IOException {
        return parseDelimitedFrom((GeneratedMessageLite) DEFAULT_INSTANCE, inputStream);
    }

    public static LensServiceEventData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static LensServiceEventData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static LensServiceEventData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static LensServiceEventData parseFrom(CodedInputStream codedInputStream) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static LensServiceEventData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static LensServiceEventData parseFrom(InputStream inputStream) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static LensServiceEventData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static LensServiceEventData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
    }

    public static LensServiceEventData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static LensServiceEventData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static LensServiceEventData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Parser<LensServiceEventData> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* access modifiers changed from: private */
    public void setServiceApiVersion(int i) {
        this.bitField0_ |= 1;
        this.serviceApiVersion_ = i;
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
            case 1:
                return new LensServiceEventData();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0002\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "serviceApiVersion_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (LensServiceEventData.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                    }
                }
                return defaultInstanceBasedParser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // com.google.android.apps.gsa.search.shared.service.proto.LensServiceEventDataOrBuilder
    public int getServiceApiVersion() {
        return this.serviceApiVersion_;
    }

    @Override // com.google.android.apps.gsa.search.shared.service.proto.LensServiceEventDataOrBuilder
    public boolean hasServiceApiVersion() {
        return (this.bitField0_ & 1) == 1;
    }
}
