package android.arch.core.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.util.concurrent.Executor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: ArchTaskExecutor */
public class c extends e {
    @NonNull
    private static final Executor Ea = new b();
    private static volatile c sInstance;
    @NonNull
    private static final Executor sMainThreadExecutor = new a();
    @NonNull
    private e Ca = this.Da;
    @NonNull
    private e Da = new d();

    private c() {
    }

    @NonNull
    public static c getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (c.class) {
            if (sInstance == null) {
                sInstance = new c();
            }
        }
        return sInstance;
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor oa() {
        return Ea;
    }

    public void a(@Nullable e eVar) {
        if (eVar == null) {
            eVar = this.Da;
        }
        this.Ca = eVar;
    }

    @Override // android.arch.core.a.e
    public void b(Runnable runnable) {
        this.Ca.b(runnable);
    }

    @Override // android.arch.core.a.e
    public void d(Runnable runnable) {
        this.Ca.d(runnable);
    }

    @Override // android.arch.core.a.e
    public boolean isMainThread() {
        return this.Ca.isMainThread();
    }
}
