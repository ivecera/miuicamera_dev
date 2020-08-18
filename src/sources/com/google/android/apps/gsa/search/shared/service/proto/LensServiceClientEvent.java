package com.google.android.apps.gsa.search.shared.service.proto;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.WireFormat;

public final class LensServiceClientEvent {
    public static final int LENS_SERVICE_CLIENT_EVENT_DATA_FIELD_NUMBER = 152666889;
    public static final GeneratedMessageLite.GeneratedExtension<ClientEventProto, LensServiceClientEventData> lensServiceClientEventData = GeneratedMessageLite.newSingularGeneratedExtension(ClientEventProto.getDefaultInstance(), LensServiceClientEventData.getDefaultInstance(), LensServiceClientEventData.getDefaultInstance(), (Internal.EnumLiteMap) null, (int) LENS_SERVICE_CLIENT_EVENT_DATA_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, LensServiceClientEventData.class);

    private LensServiceClientEvent() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(lensServiceClientEventData);
    }
}
