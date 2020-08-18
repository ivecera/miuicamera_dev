package com.ss.android.ugc.effectmanager.effect.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.common.model.UrlModel;
import java.util.ArrayList;
import java.util.List;

public class Effect implements Parcelable {
    public static final Parcelable.Creator<Effect> CREATOR = new Parcelable.Creator<Effect>() {
        /* class com.ss.android.ugc.effectmanager.effect.model.Effect.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public Effect createFromParcel(Parcel parcel) {
            return new Effect(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Effect[] newArray(int i) {
            return new Effect[i];
        }
    };
    private String app_version;
    @Nullable
    private List<Effect> childEffects;
    private List<String> children;
    private String designer_id;
    private String device_platform;
    private boolean downloaded;
    private String effect_id;
    private int effect_type;
    private String extra;
    private UrlModel file_url;
    private String hint;
    private UrlModel hint_icon;
    private UrlModel icon_url;
    private String id;
    private boolean is_busi;
    private boolean is_poi;
    private List<String> music;
    private String name;
    private String parent;
    private String poi_id;
    private List<String> requirements;
    private String schema;
    private String sdk_version;
    private int source;
    private List<String> tags;
    private String tags_updated_at;
    private String type;
    private List<String> types;
    private String unzipPath;
    private String zipPath;

    public Effect() {
    }

    protected Effect(Parcel parcel) {
        this.name = parcel.readString();
        this.hint = parcel.readString();
        this.sdk_version = parcel.readString();
        this.app_version = parcel.readString();
        this.file_url = (UrlModel) parcel.readParcelable(UrlModel.class.getClassLoader());
        this.icon_url = (UrlModel) parcel.readParcelable(UrlModel.class.getClassLoader());
        this.id = parcel.readString();
        this.effect_id = parcel.readString();
        this.type = parcel.readString();
        this.types = parcel.createStringArrayList();
        this.device_platform = parcel.readString();
        this.zipPath = parcel.readString();
        this.unzipPath = parcel.readString();
        boolean z = true;
        this.downloaded = parcel.readByte() != 0;
        this.tags = parcel.createStringArrayList();
        this.tags_updated_at = parcel.readString();
        this.hint_icon = (UrlModel) parcel.readParcelable(UrlModel.class.getClassLoader());
        this.children = parcel.createStringArrayList();
        this.childEffects = parcel.createTypedArrayList(CREATOR);
        this.effect_type = parcel.readInt();
        this.parent = parcel.readString();
        this.source = parcel.readInt();
        this.designer_id = parcel.readString();
        this.schema = parcel.readString();
        this.requirements = parcel.createStringArrayList();
        this.music = parcel.createStringArrayList();
        this.extra = parcel.readString();
        this.is_busi = parcel.readByte() != 0;
        this.poi_id = parcel.readString();
        this.is_poi = parcel.readByte() == 0 ? false : z;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof Effect ? TextUtils.equals(this.effect_id, ((Effect) obj).effect_id) : super.equals(obj);
    }

    public String getAppVersion() {
        return this.app_version;
    }

    public List<Effect> getChildEffects() {
        return this.childEffects;
    }

    public List<String> getChildren() {
        return this.children;
    }

    public String getDesignerId() {
        return this.designer_id;
    }

    public String getDevicePlatform() {
        return this.device_platform;
    }

    public String getEffectId() {
        return this.effect_id;
    }

    public int getEffectType() {
        return this.effect_type;
    }

    public String getExtra() {
        return this.extra;
    }

    public UrlModel getFileUrl() {
        return this.file_url;
    }

    public String getHint() {
        return this.hint;
    }

    public UrlModel getHintIcon() {
        return this.hint_icon;
    }

    public UrlModel getIconUrl() {
        return this.icon_url;
    }

    public String getId() {
        return this.id;
    }

    public List<String> getMusic() {
        return this.music;
    }

    public String getName() {
        return this.name;
    }

    public String getParentId() {
        return this.parent;
    }

    public String getPoiId() {
        return this.poi_id;
    }

    public List<String> getRequirements() {
        return this.requirements;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getSdkVersion() {
        return this.sdk_version;
    }

    public int getSource() {
        return this.source;
    }

    public List<String> getTags() {
        List<String> list = this.tags;
        return list == null ? new ArrayList() : list;
    }

    public String getTagsUpdatedAt() {
        return this.tags_updated_at;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public String getUnzipPath() {
        return this.unzipPath;
    }

    public String getZipPath() {
        return this.zipPath;
    }

    public boolean isBusiness() {
        return this.is_busi;
    }

    public boolean isDownloaded() {
        return this.downloaded;
    }

    public boolean isIsPoi() {
        return this.is_poi;
    }

    public void setAppVersion(String str) {
        this.app_version = str;
    }

    public void setChildEffects(List<Effect> list) {
        this.childEffects = list;
    }

    public void setChildren(List<String> list) {
        this.children = list;
    }

    public void setDesignerId(String str) {
        this.designer_id = str;
    }

    public void setDevicePlatform(String str) {
        this.device_platform = str;
    }

    public void setDownloaded(boolean z) {
        this.downloaded = z;
    }

    public void setEffectId(String str) {
        this.effect_id = str;
    }

    public void setEffectType(int i) {
        this.effect_type = i;
    }

    public void setExtra(String str) {
        this.extra = str;
    }

    public void setFileUrl(UrlModel urlModel) {
        this.file_url = urlModel;
    }

    public void setHint(String str) {
        this.hint = str;
    }

    public void setHintIcon(UrlModel urlModel) {
        this.hint_icon = urlModel;
    }

    public void setIconUrl(UrlModel urlModel) {
        this.icon_url = urlModel;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setIsBusiness(boolean z) {
        this.is_busi = z;
    }

    public void setIsPoi(boolean z) {
        this.is_poi = z;
    }

    public void setMusic(List<String> list) {
        this.music = list;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setParentId(String str) {
        this.parent = str;
    }

    public void setPoiId(String str) {
        this.poi_id = str;
    }

    public Effect setRequirements(List<String> list) {
        this.requirements = list;
        return this;
    }

    public void setSchema(String str) {
        this.schema = str;
    }

    public void setSdkVersion(String str) {
        this.sdk_version = str;
    }

    public void setSource(int i) {
        this.source = i;
    }

    public void setTags(List<String> list) {
        this.tags = list;
    }

    public void setTagsUpdatedAt(String str) {
        this.tags_updated_at = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setTypes(List<String> list) {
        this.types = list;
    }

    public void setUnzipPath(String str) {
        this.unzipPath = str;
    }

    public void setZipPath(String str) {
        this.zipPath = str;
    }

    public String toString() {
        return "Effect{name='" + this.name + '\'' + ", effect_id='" + this.effect_id + '\'' + ", type='" + this.type + '\'' + ", unzipPath='" + this.unzipPath + '\'' + ", children=" + this.children + ", effectType=" + this.effect_type + ", parentId=" + this.parent + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.hint);
        parcel.writeString(this.sdk_version);
        parcel.writeString(this.app_version);
        parcel.writeParcelable(this.file_url, i);
        parcel.writeParcelable(this.icon_url, i);
        parcel.writeString(this.id);
        parcel.writeString(this.effect_id);
        parcel.writeString(this.type);
        parcel.writeStringList(this.types);
        parcel.writeString(this.device_platform);
        parcel.writeString(this.zipPath);
        parcel.writeString(this.unzipPath);
        parcel.writeByte(this.downloaded ? (byte) 1 : 0);
        parcel.writeStringList(this.tags);
        parcel.writeString(this.tags_updated_at);
        parcel.writeParcelable(this.hint_icon, i);
        parcel.writeStringList(this.children);
        parcel.writeTypedList(this.childEffects);
        parcel.writeInt(this.effect_type);
        parcel.writeString(this.parent);
        parcel.writeInt(this.source);
        parcel.writeString(this.designer_id);
        parcel.writeString(this.schema);
        parcel.writeStringList(this.requirements);
        parcel.writeStringList(this.music);
        parcel.writeString(this.extra);
        parcel.writeByte(this.is_busi ? (byte) 1 : 0);
        parcel.writeString(this.poi_id);
        parcel.writeByte(this.is_poi ? (byte) 1 : 0);
    }
}
