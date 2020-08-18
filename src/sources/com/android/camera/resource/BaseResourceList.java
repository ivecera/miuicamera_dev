package com.android.camera.resource;

import com.android.camera.resource.BaseResourceItem;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BaseResourceList<T extends BaseResourceItem> {
    public static final int RESOURCE_TYPE_TM_MUSIC = 2;
    public static final int RESOURCE_TYPE_VV = 1;
    private List<T> mResourceList = new ArrayList();
    private int mResourceType;
    public boolean mVerified;
    public String version;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourceType {
    }

    /* access modifiers changed from: protected */
    public void addItem(JSONObject jSONObject, int i) {
        T parseSingleItem = parseSingleItem(jSONObject, i);
        if (parseSingleItem != null) {
            this.mResourceList.add(parseSingleItem);
        }
    }

    public void createResourcesList(JSONObject jSONObject) {
        JSONArray itemJsonArray = getItemJsonArray(jSONObject);
        if (itemJsonArray != null && itemJsonArray.length() != 0) {
            for (int i = 0; i <= itemJsonArray.length(); i++) {
                JSONObject optJSONObject = itemJsonArray.optJSONObject(i);
                if (optJSONObject != null) {
                    addItem(optJSONObject, i);
                }
            }
        }
    }

    public T getItem(int i) {
        return this.mResourceList.get(i);
    }

    public abstract JSONArray getItemJsonArray(JSONObject jSONObject);

    public abstract String getLocalVersion();

    public List<T> getResourceList() {
        return this.mResourceList;
    }

    public abstract int getResourceType();

    public int getSize() {
        return this.mResourceList.size();
    }

    public abstract void parseInitialData(JSONObject jSONObject);

    public abstract T parseSingleItem(JSONObject jSONObject, int i);

    public abstract void setLocalVersion(String str);
}
