package com.android.camera.resource;

public class BaseRequestException extends Exception {
    private int mErrorType;

    public BaseRequestException(int i, String str) {
        super(str);
        this.mErrorType = i;
    }

    public BaseRequestException(int i, String str, Throwable th) {
        super(str, th);
        this.mErrorType = i;
    }

    public int getErrorCode() {
        return this.mErrorType;
    }
}
