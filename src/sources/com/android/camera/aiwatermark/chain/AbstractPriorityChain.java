package com.android.camera.aiwatermark.chain;

import android.content.Context;
import com.android.camera.aiwatermark.handler.AbstractHandler;

public abstract class AbstractPriorityChain {
    public abstract AbstractHandler createASDChain(Context context);

    public abstract AbstractHandler createChain(Context context);
}
