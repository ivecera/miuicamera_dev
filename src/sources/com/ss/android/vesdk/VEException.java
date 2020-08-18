package com.ss.android.vesdk;

public class VEException extends RuntimeException {
    private String msgDes;
    private int ret;

    public VEException(int i, String str) {
        super("VESDK exception ret: " + i + "msg: " + str);
        this.ret = i;
        this.msgDes = str;
    }

    public String getMsgDes() {
        return this.msgDes;
    }

    public int getRetCd() {
        return this.ret;
    }
}
