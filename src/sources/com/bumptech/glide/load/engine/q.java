package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.util.a.d;

/* compiled from: Engine */
class q implements d.a<EngineJob<?>> {
    final /* synthetic */ Engine.EngineJobFactory this$0;

    q(Engine.EngineJobFactory engineJobFactory) {
        this.this$0 = engineJobFactory;
    }

    @Override // com.bumptech.glide.util.a.d.a
    public EngineJob<?> create() {
        Engine.EngineJobFactory engineJobFactory = this.this$0;
        return new EngineJob<>(engineJobFactory.jj, engineJobFactory.ij, engineJobFactory.en, engineJobFactory.nj, engineJobFactory.listener, engineJobFactory.pool);
    }
}
