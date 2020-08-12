package com.google.android.apps.gsa.search.shared.service.proto;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.WireFormat;

public final class LensServiceEvent {
    public static final int LENS_SERVICE_EVENT_DATA_FIELD_NUMBER = 152666888;
    public static final GeneratedMessageLite.GeneratedExtension<ServiceEventProto, LensServiceEventData> lensServiceEventData = GeneratedMessageLite.newSingularGeneratedExtension(ServiceEventProto.getDefaultInstance(), LensServiceEventData.getDefaultInstance(), LensServiceEventData.getDefaultInstance(), (Internal.EnumLiteMap) null, (int) LENS_SERVICE_EVENT_DATA_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, LensServiceEventData.class);

    private LensServiceEvent() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(lensServiceEventData);
    }
}
