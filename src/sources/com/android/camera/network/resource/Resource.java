package com.android.camera.network.resource;

import android.text.TextUtils;

public class Resource {
    public int category;
    public String content;
    public String extra;
    public String icon;
    public long id;
    public String label;
    public long parent;
    public String type;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Resource)) {
            return false;
        }
        Resource resource = (Resource) obj;
        return this.id == resource.id && this.parent == resource.parent && this.category == resource.category && TextUtils.equals(this.type, resource.type) && TextUtils.equals(this.label, resource.label) && TextUtils.equals(this.icon, resource.icon) && TextUtils.equals(this.extra, resource.extra) && TextUtils.equals(this.content, resource.content);
    }

    public String toString() {
        return "Resource{id=" + this.id + ", parent=" + this.parent + ", category=" + this.category + ", type='" + this.type + '\'' + ", label='" + this.label + '\'' + '}';
    }
}
