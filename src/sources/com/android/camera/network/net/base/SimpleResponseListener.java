package com.android.camera.network.net.base;

public abstract class SimpleResponseListener<T> implements ResponseListener {
    public abstract void onResponse(T t);

    @Override // com.android.camera.network.net.base.ResponseListener
    public final void onResponse(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            onResponse((Object) null);
        } else {
            onResponse(objArr[0]);
        }
    }

    @Override // com.android.camera.network.net.base.ResponseListener
    public void onResponseError(ErrorCode errorCode, String str, Object obj) {
    }
}
