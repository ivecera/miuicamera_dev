package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.p;
import java.util.Map;

/* compiled from: Headers */
public interface n {
    public static final n DEFAULT = new p.a().build();
    @Deprecated
    public static final n NONE = new m();

    Map<String, String> getHeaders();
}
