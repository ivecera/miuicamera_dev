package com.android.camera.network.net.base;

import com.android.volley.Request;

public interface Method extends Request.Method {
    public static final int SECURE_BEGIN = 1001;
    public static final int SECURE_GET = 1001;
    public static final int SECURE_POST = 1002;
}
