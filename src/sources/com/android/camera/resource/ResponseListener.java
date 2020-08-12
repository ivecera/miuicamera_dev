package com.android.camera.resource;

public interface ResponseListener<T> {
    void onResponse(T t, boolean z);

    void onResponseError(int i, String str, Object obj);
}
