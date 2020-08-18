package com.ss.android.ugc.effectmanager.common.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class UrlModel implements Parcelable {
    public static final Parcelable.Creator<UrlModel> CREATOR = new Parcelable.Creator<UrlModel>() {
        /* class com.ss.android.ugc.effectmanager.common.model.UrlModel.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public UrlModel createFromParcel(Parcel parcel) {
            return new UrlModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public UrlModel[] newArray(int i) {
            return new UrlModel[i];
        }
    };
    private String uri;
    private List<String> url_list;

    public UrlModel() {
    }

    private UrlModel(Parcel parcel) {
        this.url_list = parcel.createStringArrayList();
        this.uri = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String getUri() {
        return this.uri;
    }

    public List<String> getUrlList() {
        return this.url_list;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public void setUrlList(List<String> list) {
        this.url_list = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> list = this.url_list;
        if (list != null) {
            for (String str : list) {
                sb.append(" [");
                sb.append(str);
                sb.append("] ");
            }
        }
        return "UrlModel{url_list=" + sb.toString() + ", uri='" + this.uri + '\'' + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.url_list);
        parcel.writeString(this.uri);
    }
}
