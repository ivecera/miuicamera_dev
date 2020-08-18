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
public final class ServiceEventProto extends GeneratedMessageLite.ExtendableMessage<ServiceEventProto, Builder> implements ServiceEventProtoOrBuilder {
    /* access modifiers changed from: private */
    public static final ServiceEventProto DEFAULT_INSTANCE = new ServiceEventProto();
    public static final int EVENT_ID_FIELD_NUMBER = 1;
    private static volatile Parser<ServiceEventProto> PARSER;
    @ProtoPresenceBits(id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private int eventId_;
    private byte memoizedIsInitialized = 2;

    /* renamed from: com.google.android.apps.gsa.search.shared.service.proto.ServiceEventProto$1  reason: invalid class name */
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

    public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<ServiceEventProto, Builder> implements ServiceEventProtoOrBuilder {
        private Builder() {
            super(ServiceEventProto.DEFAULT_INSTANCE);
        }

        /* synthetic */ Builder(AnonymousClass1 r1) {
            this();
        }

        public Builder clearEventId() {
            copyOnWrite();
            this.instance.clearEventId();
            return this;
        }

        @Override // com.google.android.apps.gsa.search.shared.service.proto.ServiceEventProtoOrBuilder
        public int getEventId() {
            return this.instance.getEventId();
        }

        @Override // com.google.android.apps.gsa.search.shared.service.proto.ServiceEventProtoOrBuilder
        public boolean hasEventId() {
            return this.instance.hasEventId();
        }

        public Builder setEventId(int i) {
            copyOnWrite();
            this.instance.setEventId(i);
            return this;
        }
    }

    static {
        GeneratedMessageLite.registerDefaultInstance(ServiceEventProto.class, DEFAULT_INSTANCE);
    }

    private ServiceEventProto() {
    }

    /* access modifiers changed from: private */
    public void clearEventId() {
        this.bitField0_ &= -2;
        this.eventId_ = 0;
    }

    public static ServiceEventProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(ServiceEventProto serviceEventProto) {
        return DEFAULT_INSTANCE.createBuilder(serviceEventProto);
    }

    public static ServiceEventProto parseDelimitedFrom(InputStream inputStream) throws IOException {
        return parseDelimitedFrom((GeneratedMessageLite) DEFAULT_INSTANCE, inputStream);
    }

    public static ServiceEventProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ServiceEventProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static ServiceEventProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static ServiceEventProto parseFrom(CodedInputStream codedInputStream) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static ServiceEventProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static ServiceEventProto parseFrom(InputStream inputStream) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ServiceEventProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ServiceEventProto parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
    }

    public static ServiceEventProto parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static ServiceEventProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static ServiceEventProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Parser<ServiceEventProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* access modifiers changed from: private */
    public void setEventId(int i) {
        this.bitField0_ |= 1;
        this.eventId_ = i;
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        int i = 1;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
            case 1:
                return new ServiceEventProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0002\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "eventId_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (ServiceEventProto.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                    }
                }
                return defaultInstanceBasedParser;
            case 6:
                return Byte.valueOf(this.memoizedIsInitialized);
            case 7:
                if (obj == null) {
                    i = 0;
                }
                this.memoizedIsInitialized = (byte) i;
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // com.google.android.apps.gsa.search.shared.service.proto.ServiceEventProtoOrBuilder
    public int getEventId() {
        return this.eventId_;
    }

    @Override // com.google.android.apps.gsa.search.shared.service.proto.ServiceEventProtoOrBuilder
    public boolean hasEventId() {
        return (this.bitField0_ & 1) == 1;
    }
}
