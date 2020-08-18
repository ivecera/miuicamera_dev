package android.arch.core.a;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: TaskExecutor */
public abstract class e {
    public abstract void b(@NonNull Runnable runnable);

    public void c(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            d(runnable);
        }
    }

    public abstract void d(@NonNull Runnable runnable);

    public abstract boolean isMainThread();
}
