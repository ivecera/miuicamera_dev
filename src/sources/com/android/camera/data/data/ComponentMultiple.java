package com.android.camera.data.data;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import com.android.camera.data.provider.DataProvider;
import com.android.camera2.CameraCapabilities;
import java.util.Arrays;
import java.util.List;

public abstract class ComponentMultiple extends ComponentData {
    private List<TypeItem> mTypeItemList;

    public <D extends DataItemBase> ComponentMultiple(D d2) {
        super(d2);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        throw new RuntimeException("provided by TypeItem");
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        throw new RuntimeException("refactor later");
    }

    @Override // com.android.camera.data.data.ComponentData
    @CallSuper
    public String getKey(int i) {
        throw new RuntimeException("provided by TypeItem");
    }

    public List<TypeItem> getTypeItemList() {
        return this.mTypeItemList;
    }

    public <T> T getValueByItem(TypeItem<T> typeItem) {
        if (!typeItem.mExpandable) {
            String str = typeItem.mKeyOrType;
            if (typeItem.asString()) {
                return ((ComponentData) this).mParentDataItem.getString(str, typeItem.mDefaultValue);
            }
            if (typeItem.asInteger()) {
                return Integer.valueOf(((ComponentData) this).mParentDataItem.getInt(str, typeItem.mDefaultValue.intValue()));
            }
            if (typeItem.asBoolean()) {
                return Boolean.valueOf(((ComponentData) this).mParentDataItem.getBoolean(str, typeItem.mDefaultValue.booleanValue()));
            }
            return null;
        }
        throw new RuntimeException("complex result");
    }

    public abstract void initTypeElements(Context context, int i, CameraCapabilities cameraCapabilities, int i2);

    /* access modifiers changed from: protected */
    public void insert(TypeItem... typeItemArr) {
        List<TypeItem> list = this.mTypeItemList;
        if (list == null) {
            this.mTypeItemList = Arrays.asList(typeItemArr);
        } else if (typeItemArr.length == 1) {
            list.add(typeItemArr[0]);
        } else {
            list.addAll(Arrays.asList(typeItemArr));
        }
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isEmpty() {
        List<TypeItem> list = this.mTypeItemList;
        return list == null || list.isEmpty();
    }

    public <T> void putValueWithItem(TypeItem<T> typeItem, T t) {
        if (!typeItem.mExpandable) {
            String str = typeItem.mKeyOrType;
            DataProvider.ProviderEditor editor = ((ComponentData) this).mParentDataItem.editor();
            if (typeItem.asString()) {
                editor.putString(str, t);
            } else if (typeItem.asInteger()) {
                editor.putInt(str, t.intValue());
            } else if (typeItem.asBoolean()) {
                editor.putBoolean(str, t.booleanValue());
            }
            editor.apply();
            return;
        }
        throw new RuntimeException("complex result");
    }
}
