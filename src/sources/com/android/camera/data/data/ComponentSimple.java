package com.android.camera.data.data;

import android.support.annotation.NonNull;
import java.util.List;

public class ComponentSimple<T> extends ComponentData {
    private TypeItem mTypeItem;

    public <D extends DataItemBase> ComponentSimple(D d2) {
        super(d2);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return this.mTypeItem.mDisplayNameRes;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return this.mTypeItem.mKeyOrType;
    }

    public <T> T getValue(String str) {
        return null;
    }

    public <T> void putValue(int i, T t) {
    }
}
