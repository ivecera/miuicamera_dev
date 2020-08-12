package com.android.camera.aftersales.interfaces;

public interface ICounter {
    void clean();

    void count(long j, int i, int i2);

    boolean exceedLimit();

    void trim();
}
