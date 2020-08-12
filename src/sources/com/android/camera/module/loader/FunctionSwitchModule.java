package com.android.camera.module.loader;

import com.android.camera.effect.EffectController;
import com.android.camera.module.BaseModule;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

public class FunctionSwitchModule extends Func1Base<BaseModule, BaseModule> {
    public FunctionSwitchModule(int i) {
        super(i);
    }

    public NullHolder<BaseModule> apply(@NonNull NullHolder<BaseModule> nullHolder) throws Exception {
        if (!nullHolder.isPresent()) {
            return nullHolder;
        }
        if (167 != ((Func1Base) this).mTargetMode) {
            EffectController.getInstance().reset();
        }
        nullHolder.get().isCreated();
        return nullHolder;
    }

    @Override // com.android.camera.module.loader.Func1Base
    public Scheduler getWorkThread() {
        return AndroidSchedulers.mainThread();
    }
}
