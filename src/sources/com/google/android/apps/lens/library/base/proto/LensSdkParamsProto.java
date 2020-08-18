package com.google.android.apps.lens.library.base.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoField;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoPresenceBits;
import com.google.protobuf.ProtoPresenceCheckedField;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class LensSdkParamsProto {

    /* renamed from: com.google.android.apps.lens.library.base.proto.LensSdkParamsProto$1  reason: invalid class name */
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

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class LensSdkParams extends GeneratedMessageLite<LensSdkParams, Builder> implements LensSdkParamsOrBuilder {
        public static final int AGSA_VERSION_NAME_FIELD_NUMBER = 2;
        public static final int AR_STICKERS_AVAILABILITY_STATUS_FIELD_NUMBER = 4;
        /* access modifiers changed from: private */
        public static final LensSdkParams DEFAULT_INSTANCE = new LensSdkParams();
        public static final int LENS_AVAILABILITY_STATUS_FIELD_NUMBER = 3;
        public static final int LENS_SDK_VERSION_FIELD_NUMBER = 1;
        private static volatile Parser<LensSdkParams> PARSER;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String agsaVersionName_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int arStickersAvailabilityStatus_ = -1;
        @ProtoPresenceBits(id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int lensAvailabilityStatus_ = -1;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String lensSdkVersion_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<LensSdkParams, Builder> implements LensSdkParamsOrBuilder {
            private Builder() {
                super(LensSdkParams.DEFAULT_INSTANCE);
            }

            /* synthetic */ Builder(AnonymousClass1 r1) {
                this();
            }

            public Builder clearAgsaVersionName() {
                copyOnWrite();
                this.instance.clearAgsaVersionName();
                return this;
            }

            public Builder clearArStickersAvailabilityStatus() {
                copyOnWrite();
                this.instance.clearArStickersAvailabilityStatus();
                return this;
            }

            public Builder clearLensAvailabilityStatus() {
                copyOnWrite();
                this.instance.clearLensAvailabilityStatus();
                return this;
            }

            public Builder clearLensSdkVersion() {
                copyOnWrite();
                this.instance.clearLensSdkVersion();
                return this;
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public String getAgsaVersionName() {
                return this.instance.getAgsaVersionName();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public ByteString getAgsaVersionNameBytes() {
                return this.instance.getAgsaVersionNameBytes();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public LensAvailabilityStatus getArStickersAvailabilityStatus() {
                return this.instance.getArStickersAvailabilityStatus();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public LensAvailabilityStatus getLensAvailabilityStatus() {
                return this.instance.getLensAvailabilityStatus();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public String getLensSdkVersion() {
                return this.instance.getLensSdkVersion();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public ByteString getLensSdkVersionBytes() {
                return this.instance.getLensSdkVersionBytes();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public boolean hasAgsaVersionName() {
                return this.instance.hasAgsaVersionName();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public boolean hasArStickersAvailabilityStatus() {
                return this.instance.hasArStickersAvailabilityStatus();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public boolean hasLensAvailabilityStatus() {
                return this.instance.hasLensAvailabilityStatus();
            }

            @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
            public boolean hasLensSdkVersion() {
                return this.instance.hasLensSdkVersion();
            }

            public Builder setAgsaVersionName(String str) {
                copyOnWrite();
                this.instance.setAgsaVersionName(str);
                return this;
            }

            public Builder setAgsaVersionNameBytes(ByteString byteString) {
                copyOnWrite();
                this.instance.setAgsaVersionNameBytes(byteString);
                return this;
            }

            public Builder setArStickersAvailabilityStatus(LensAvailabilityStatus lensAvailabilityStatus) {
                copyOnWrite();
                this.instance.setArStickersAvailabilityStatus(lensAvailabilityStatus);
                return this;
            }

            public Builder setLensAvailabilityStatus(LensAvailabilityStatus lensAvailabilityStatus) {
                copyOnWrite();
                this.instance.setLensAvailabilityStatus(lensAvailabilityStatus);
                return this;
            }

            public Builder setLensSdkVersion(String str) {
                copyOnWrite();
                this.instance.setLensSdkVersion(str);
                return this;
            }

            public Builder setLensSdkVersionBytes(ByteString byteString) {
                copyOnWrite();
                this.instance.setLensSdkVersionBytes(byteString);
                return this;
            }
        }

        public enum LensAvailabilityStatus implements Internal.EnumLite {
            LENS_AVAILABILITY_UNKNOWN(-1),
            LENS_READY(0),
            LENS_UNAVAILABLE(1),
            LENS_UNAVAILABLE_LOCALE_NOT_SUPPORTED(2),
            LENS_UNAVAILABLE_DEVICE_INCOMPATIBLE(3),
            LENS_UNAVAILABLE_INVALID_CURSOR(4),
            LENS_UNAVAILABLE_DEVICE_LOCKED(5),
            LENS_UNAVAILABLE_UNKNOWN_ERROR_CODE(6);
            
            public static final int LENS_AVAILABILITY_UNKNOWN_VALUE = -1;
            public static final int LENS_READY_VALUE = 0;
            public static final int LENS_UNAVAILABLE_DEVICE_INCOMPATIBLE_VALUE = 3;
            public static final int LENS_UNAVAILABLE_DEVICE_LOCKED_VALUE = 5;
            public static final int LENS_UNAVAILABLE_INVALID_CURSOR_VALUE = 4;
            public static final int LENS_UNAVAILABLE_LOCALE_NOT_SUPPORTED_VALUE = 2;
            public static final int LENS_UNAVAILABLE_UNKNOWN_ERROR_CODE_VALUE = 6;
            public static final int LENS_UNAVAILABLE_VALUE = 1;
            private static final Internal.EnumLiteMap<LensAvailabilityStatus> internalValueMap = new Internal.EnumLiteMap<LensAvailabilityStatus>() {
                /* class com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParams.LensAvailabilityStatus.AnonymousClass1 */

                public LensAvailabilityStatus findValueByNumber(int i) {
                    return LensAvailabilityStatus.forNumber(i);
                }
            };
            private final int value;

            private LensAvailabilityStatus(int i) {
                this.value = i;
            }

            public static LensAvailabilityStatus forNumber(int i) {
                switch (i) {
                    case -1:
                        return LENS_AVAILABILITY_UNKNOWN;
                    case 0:
                        return LENS_READY;
                    case 1:
                        return LENS_UNAVAILABLE;
                    case 2:
                        return LENS_UNAVAILABLE_LOCALE_NOT_SUPPORTED;
                    case 3:
                        return LENS_UNAVAILABLE_DEVICE_INCOMPATIBLE;
                    case 4:
                        return LENS_UNAVAILABLE_INVALID_CURSOR;
                    case 5:
                        return LENS_UNAVAILABLE_DEVICE_LOCKED;
                    case 6:
                        return LENS_UNAVAILABLE_UNKNOWN_ERROR_CODE;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<LensAvailabilityStatus> internalGetValueMap() {
                return internalValueMap;
            }

            public final int getNumber() {
                return this.value;
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(LensSdkParams.class, DEFAULT_INSTANCE);
        }

        private LensSdkParams() {
        }

        /* access modifiers changed from: private */
        public void clearAgsaVersionName() {
            this.bitField0_ &= -3;
            this.agsaVersionName_ = getDefaultInstance().getAgsaVersionName();
        }

        /* access modifiers changed from: private */
        public void clearArStickersAvailabilityStatus() {
            this.bitField0_ &= -9;
            this.arStickersAvailabilityStatus_ = -1;
        }

        /* access modifiers changed from: private */
        public void clearLensAvailabilityStatus() {
            this.bitField0_ &= -5;
            this.lensAvailabilityStatus_ = -1;
        }

        /* access modifiers changed from: private */
        public void clearLensSdkVersion() {
            this.bitField0_ &= -2;
            this.lensSdkVersion_ = getDefaultInstance().getLensSdkVersion();
        }

        public static LensSdkParams getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(LensSdkParams lensSdkParams) {
            return DEFAULT_INSTANCE.createBuilder(lensSdkParams);
        }

        public static LensSdkParams parseDelimitedFrom(InputStream inputStream) throws IOException {
            return parseDelimitedFrom((GeneratedMessageLite) DEFAULT_INSTANCE, inputStream);
        }

        public static LensSdkParams parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LensSdkParams parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LensSdkParams parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LensSdkParams parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LensSdkParams parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static LensSdkParams parseFrom(InputStream inputStream) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LensSdkParams parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LensSdkParams parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static LensSdkParams parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static LensSdkParams parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LensSdkParams parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Parser<LensSdkParams> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* access modifiers changed from: private */
        public void setAgsaVersionName(String str) {
            if (str != null) {
                this.bitField0_ |= 2;
                this.agsaVersionName_ = str;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAgsaVersionNameBytes(ByteString byteString) {
            if (byteString != null) {
                this.bitField0_ |= 2;
                this.agsaVersionName_ = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setArStickersAvailabilityStatus(LensAvailabilityStatus lensAvailabilityStatus) {
            if (lensAvailabilityStatus != null) {
                this.bitField0_ |= 8;
                this.arStickersAvailabilityStatus_ = lensAvailabilityStatus.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setLensAvailabilityStatus(LensAvailabilityStatus lensAvailabilityStatus) {
            if (lensAvailabilityStatus != null) {
                this.bitField0_ |= 4;
                this.lensAvailabilityStatus_ = lensAvailabilityStatus.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setLensSdkVersion(String str) {
            if (str != null) {
                this.bitField0_ |= 1;
                this.lensSdkVersion_ = str;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setLensSdkVersionBytes(ByteString byteString) {
            if (byteString != null) {
                this.bitField0_ |= 1;
                this.lensSdkVersion_ = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LensSdkParams();
                case 2:
                    return new Builder(null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\f\u0002\u0004\f\u0003", new Object[]{"bitField0_", "lensSdkVersion_", "agsaVersionName_", "lensAvailabilityStatus_", LensAvailabilityStatus.internalGetValueMap(), "arStickersAvailabilityStatus_", LensAvailabilityStatus.internalGetValueMap()});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (LensSdkParams.class) {
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

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public String getAgsaVersionName() {
            return this.agsaVersionName_;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public ByteString getAgsaVersionNameBytes() {
            return ByteString.copyFromUtf8(this.agsaVersionName_);
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public LensAvailabilityStatus getArStickersAvailabilityStatus() {
            LensAvailabilityStatus forNumber = LensAvailabilityStatus.forNumber(this.arStickersAvailabilityStatus_);
            return forNumber == null ? LensAvailabilityStatus.LENS_AVAILABILITY_UNKNOWN : forNumber;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public LensAvailabilityStatus getLensAvailabilityStatus() {
            LensAvailabilityStatus forNumber = LensAvailabilityStatus.forNumber(this.lensAvailabilityStatus_);
            return forNumber == null ? LensAvailabilityStatus.LENS_AVAILABILITY_UNKNOWN : forNumber;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public String getLensSdkVersion() {
            return this.lensSdkVersion_;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public ByteString getLensSdkVersionBytes() {
            return ByteString.copyFromUtf8(this.lensSdkVersion_);
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public boolean hasAgsaVersionName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public boolean hasArStickersAvailabilityStatus() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public boolean hasLensAvailabilityStatus() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // com.google.android.apps.lens.library.base.proto.LensSdkParamsProto.LensSdkParamsOrBuilder
        public boolean hasLensSdkVersion() {
            return (this.bitField0_ & 1) == 1;
        }
    }

    public interface LensSdkParamsOrBuilder extends MessageLiteOrBuilder {
        String getAgsaVersionName();

        ByteString getAgsaVersionNameBytes();

        LensSdkParams.LensAvailabilityStatus getArStickersAvailabilityStatus();

        LensSdkParams.LensAvailabilityStatus getLensAvailabilityStatus();

        String getLensSdkVersion();

        ByteString getLensSdkVersionBytes();

        boolean hasAgsaVersionName();

        boolean hasArStickersAvailabilityStatus();

        boolean hasLensAvailabilityStatus();

        boolean hasLensSdkVersion();
    }

    private LensSdkParamsProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
