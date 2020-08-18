package com.ss.android.ugc.effectmanager.link.model.host;

import com.ss.android.ugc.effectmanager.link.model.blackRoom.BlackRoomItem;
import java.net.URI;

public class Host extends BlackRoomItem {
    private String host;
    private int port = -1;
    private String schema;
    private long sortTime;
    private long weightTime;

    public Host(String str) {
        URI create = URI.create(str);
        this.host = create.getHost();
        this.schema = create.getScheme();
        this.port = create.getPort();
    }

    public Host(String str, String str2) {
        this.host = str;
        this.schema = str2;
    }

    public Host(String str, String str2, long j) {
        this.host = str;
        this.schema = str2;
        this.weightTime = j;
    }

    public Host(URI uri) {
        this.host = uri.getHost();
        this.schema = uri.getScheme();
    }

    public String getHost() {
        return this.host;
    }

    @Override // com.ss.android.ugc.effectmanager.link.model.blackRoom.BlackRoomItem
    public String getItemName() {
        String str = getSchema() + "://" + getHost();
        if (this.port == -1) {
            return str;
        }
        return str + ":" + this.port;
    }

    public int getPort() {
        return this.port;
    }

    public String getSchema() {
        return this.schema;
    }

    public long getSortTime() {
        return this.sortTime + this.weightTime;
    }

    public long getWeightTime() {
        return this.weightTime;
    }

    public boolean hostEquals(Host host2) {
        return host2 != null && host2.getHost().equals(getHost()) && host2.getSchema().equals(getSchema());
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public void setSchema(String str) {
        this.schema = str;
    }

    public void setSortTime(long j) {
        this.sortTime = j;
    }

    public void setWeightTime(long j) {
        this.weightTime = j;
    }

    @Override // com.ss.android.ugc.effectmanager.link.model.blackRoom.BlackRoomItem
    public String toString() {
        return "Host{weightTime=" + this.weightTime + ", schema='" + this.schema + '\'' + ", host='" + this.host + '\'' + '}';
    }
}
