package com.ss.android.ugc.effectmanager.link.model.host;

public class HostStatus {
    private long duration;
    private Exception exception;
    private Host host;
    private boolean isSuccess;
    private long sendTime;
    private int status;
    private String traceCode;
    private String url;

    public HostStatus(String str, Host host2, int i, long j, long j2, String str2, Exception exc, boolean z) {
        this.url = str;
        this.host = host2;
        this.status = i;
        this.duration = j;
        this.sendTime = j2;
        this.traceCode = str2;
        this.exception = exc;
        this.isSuccess = z;
    }

    public long getDuration() {
        return this.duration;
    }

    public Exception getException() {
        return this.exception;
    }

    public Host getHost() {
        return this.host;
    }

    public long getSendTime() {
        return this.sendTime;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTraceCode() {
        return this.traceCode;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public void setException(Exception exc) {
        this.exception = exc;
    }

    public void setHost(Host host2) {
        this.host = host2;
    }

    public void setSendTime(long j) {
        this.sendTime = j;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setSuccess(boolean z) {
        this.isSuccess = z;
    }

    public void setTraceCode(String str) {
        this.traceCode = str;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String toString() {
        return "HostStatus{url='" + this.url + '\'' + ", host=" + this.host.toString() + ", status=" + this.status + ", duration=" + this.duration + ", sendTime=" + this.sendTime + ", traceCode='" + this.traceCode + '\'' + ", exception=" + this.exception + ", isSuccess=" + this.isSuccess + '}';
    }
}
