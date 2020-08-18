package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.util.a.d;

/* compiled from: Engine */
class p implements d.a<DecodeJob<?>> {
    final /* synthetic */ Engine.DecodeJobFactory this$0;

    p(Engine.DecodeJobFactory decodeJobFactory) {
        this.this$0 = decodeJobFactory;
    }

    @Override // com.bumptech.glide.util.a.d.a
    public DecodeJob<?> create() {
        Engine.DecodeJobFactory decodeJobFactory = this.this$0;
        return new DecodeJob<>(decodeJobFactory.sm, decodeJobFactory.pool);
    }
}
