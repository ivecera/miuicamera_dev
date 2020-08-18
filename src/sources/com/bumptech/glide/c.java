package com.bumptech.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.a.l;
import com.bumptech.glide.load.b.a.a;
import com.bumptech.glide.load.b.b.e;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.prefill.a;
import com.bumptech.glide.load.engine.prefill.c;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.model.A;
import com.bumptech.glide.load.model.B;
import com.bumptech.glide.load.model.C;
import com.bumptech.glide.load.model.D;
import com.bumptech.glide.load.model.a;
import com.bumptech.glide.load.model.a.b;
import com.bumptech.glide.load.model.a.c;
import com.bumptech.glide.load.model.a.d;
import com.bumptech.glide.load.model.a.e;
import com.bumptech.glide.load.model.a.f;
import com.bumptech.glide.load.model.c;
import com.bumptech.glide.load.model.f;
import com.bumptech.glide.load.model.g;
import com.bumptech.glide.load.model.i;
import com.bumptech.glide.load.model.q;
import com.bumptech.glide.load.model.y;
import com.bumptech.glide.load.model.z;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bitmap.i;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.load.resource.bitmap.v;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder;
import com.bumptech.glide.manager.n;
import com.bumptech.glide.request.f;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: Glide */
public class c implements ComponentCallbacks2 {
    private static final String TAG = "Glide";
    private static final String fj = "image_manager_disk_cache";
    private static volatile c gj;
    private static volatile boolean hj;
    private final b Ma;
    private final Engine Qa;
    private final d Xi;
    private final o Yi;
    private final a Zi;
    private final e _i;
    private final n bj;
    private final com.bumptech.glide.manager.d cj;
    private final List<m> dj = new ArrayList();
    private MemoryCategory ej = MemoryCategory.NORMAL;
    private final Registry registry;

    c(@NonNull Context context, @NonNull Engine engine, @NonNull o oVar, @NonNull d dVar, @NonNull b bVar, @NonNull n nVar, @NonNull com.bumptech.glide.manager.d dVar2, int i, @NonNull f fVar, @NonNull Map<Class<?>, n<?, ?>> map) {
        this.Qa = engine;
        this.Xi = dVar;
        this.Ma = bVar;
        this.Yi = oVar;
        this.bj = nVar;
        this.cj = dVar2;
        this.Zi = new a(oVar, dVar, (DecodeFormat) fVar.getOptions().a(com.bumptech.glide.load.resource.bitmap.o.Yq));
        Resources resources = context.getResources();
        this.registry = new Registry();
        this.registry.a(new m());
        com.bumptech.glide.load.resource.bitmap.o oVar2 = new com.bumptech.glide.load.resource.bitmap.o(this.registry.Ji(), resources.getDisplayMetrics(), dVar, bVar);
        ByteBufferGifDecoder byteBufferGifDecoder = new ByteBufferGifDecoder(context, this.registry.Ji(), dVar, bVar);
        h<ParcelFileDescriptor, Bitmap> c2 = VideoDecoder.c(dVar);
        i iVar = new i(oVar2);
        x xVar = new x(oVar2, bVar);
        e eVar = new e(context);
        y.c cVar = new y.c(resources);
        y.d dVar3 = new y.d(resources);
        y.b bVar2 = new y.b(resources);
        y.a aVar = new y.a(resources);
        com.bumptech.glide.load.resource.bitmap.e eVar2 = new com.bumptech.glide.load.resource.bitmap.e(bVar);
        com.bumptech.glide.load.b.d.a aVar2 = new com.bumptech.glide.load.b.d.a();
        com.bumptech.glide.load.b.d.d dVar4 = new com.bumptech.glide.load.b.d.d();
        ContentResolver contentResolver = context.getContentResolver();
        this.registry.a(ByteBuffer.class, new com.bumptech.glide.load.model.e()).a(InputStream.class, new z(bVar)).a(Registry.Jj, ByteBuffer.class, Bitmap.class, iVar).a(Registry.Jj, InputStream.class, Bitmap.class, xVar).a(Registry.Jj, ParcelFileDescriptor.class, Bitmap.class, c2).a(Registry.Jj, AssetFileDescriptor.class, Bitmap.class, VideoDecoder.b(dVar)).a(Bitmap.class, Bitmap.class, B.a.getInstance()).a(Registry.Jj, Bitmap.class, Bitmap.class, new com.bumptech.glide.load.resource.bitmap.z()).a(Bitmap.class, (com.bumptech.glide.load.i) eVar2).a(Registry.Kj, ByteBuffer.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, iVar)).a(Registry.Kj, InputStream.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, xVar)).a(Registry.Kj, ParcelFileDescriptor.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, c2)).a(BitmapDrawable.class, (com.bumptech.glide.load.i) new com.bumptech.glide.load.resource.bitmap.b(dVar, eVar2)).a(Registry.Ij, InputStream.class, com.bumptech.glide.load.resource.gif.b.class, new com.bumptech.glide.load.resource.gif.h(this.registry.Ji(), byteBufferGifDecoder, bVar)).a(Registry.Ij, ByteBuffer.class, com.bumptech.glide.load.resource.gif.b.class, byteBufferGifDecoder).a(com.bumptech.glide.load.resource.gif.b.class, (com.bumptech.glide.load.i) new com.bumptech.glide.load.resource.gif.c()).a(com.bumptech.glide.b.a.class, com.bumptech.glide.b.a.class, B.a.getInstance()).a(Registry.Jj, com.bumptech.glide.b.a.class, Bitmap.class, new com.bumptech.glide.load.resource.gif.f(dVar)).a(Uri.class, Drawable.class, eVar).a(Uri.class, Bitmap.class, new v(eVar, dVar)).a(new a.C0008a()).a(File.class, ByteBuffer.class, new f.b()).a(File.class, InputStream.class, new i.e()).a(File.class, File.class, new com.bumptech.glide.load.b.c.a()).a(File.class, ParcelFileDescriptor.class, new i.b()).a(File.class, File.class, B.a.getInstance()).a(new l.a(bVar)).a(Integer.TYPE, InputStream.class, cVar).a(Integer.TYPE, ParcelFileDescriptor.class, bVar2).a(Integer.class, InputStream.class, cVar).a(Integer.class, ParcelFileDescriptor.class, bVar2).a(Integer.class, Uri.class, dVar3).a(Integer.TYPE, AssetFileDescriptor.class, aVar).a(Integer.class, AssetFileDescriptor.class, aVar).a(Integer.TYPE, Uri.class, dVar3).a(String.class, InputStream.class, new g.c()).a(Uri.class, InputStream.class, new g.c()).a(String.class, InputStream.class, new A.c()).a(String.class, ParcelFileDescriptor.class, new A.b()).a(String.class, AssetFileDescriptor.class, new A.a()).a(Uri.class, InputStream.class, new c.a()).a(Uri.class, InputStream.class, new a.c(context.getAssets())).a(Uri.class, ParcelFileDescriptor.class, new a.b(context.getAssets())).a(Uri.class, InputStream.class, new d.a(context)).a(Uri.class, InputStream.class, new e.a(context)).a(Uri.class, InputStream.class, new C.d(contentResolver)).a(Uri.class, ParcelFileDescriptor.class, new C.b(contentResolver)).a(Uri.class, AssetFileDescriptor.class, new C.a(contentResolver)).a(Uri.class, InputStream.class, new D.a()).a(URL.class, InputStream.class, new f.a()).a(Uri.class, File.class, new q.a(context)).a(com.bumptech.glide.load.model.l.class, InputStream.class, new b.a()).a(byte[].class, ByteBuffer.class, new c.a()).a(byte[].class, InputStream.class, new c.d()).a(Uri.class, Uri.class, B.a.getInstance()).a(Drawable.class, Drawable.class, B.a.getInstance()).a(Drawable.class, Drawable.class, new com.bumptech.glide.load.b.b.f()).a(Bitmap.class, BitmapDrawable.class, new com.bumptech.glide.load.b.d.b(resources)).a(Bitmap.class, byte[].class, aVar2).a(Drawable.class, byte[].class, new com.bumptech.glide.load.b.d.c(dVar, aVar2, dVar4)).a(com.bumptech.glide.load.resource.gif.b.class, byte[].class, dVar4);
        this._i = new e(context, bVar, this.registry, new com.bumptech.glide.request.target.i(), fVar, map, engine, i);
    }

    @Nullable
    public static File G(@NonNull Context context) {
        return g(context, "image_manager_disk_cache");
    }

    @NonNull
    public static m H(@NonNull Context context) {
        return K(context).get(context);
    }

    private static void J(@NonNull Context context) {
        if (!hj) {
            hj = true;
            L(context);
            hj = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    @NonNull
    private static n K(@Nullable Context context) {
        com.bumptech.glide.util.i.b(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return get(context).Ii();
    }

    private static void L(@NonNull Context context) {
        b(context, new d());
    }

    @Nullable
    private static a Tm() {
        try {
            return (a) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            }
            return null;
        } catch (InstantiationException e2) {
            b(e2);
            throw null;
        } catch (IllegalAccessException e3) {
            b(e3);
            throw null;
        } catch (NoSuchMethodException e4) {
            b(e4);
            throw null;
        } catch (InvocationTargetException e5) {
            b(e5);
            throw null;
        }
    }

    @NonNull
    public static m a(@NonNull Activity activity) {
        return K(activity).get(activity);
    }

    @NonNull
    public static m a(@NonNull FragmentActivity fragmentActivity) {
        return K(fragmentActivity).b(fragmentActivity);
    }

    @NonNull
    @Deprecated
    public static m b(@NonNull Fragment fragment) {
        return K(fragment.getActivity()).c(fragment);
    }

    @NonNull
    public static m b(@NonNull android.support.v4.app.Fragment fragment) {
        return K(fragment.getActivity()).c(fragment);
    }

    private static void b(@NonNull Context context, @NonNull d dVar) {
        Context applicationContext = context.getApplicationContext();
        a Tm = Tm();
        List<com.bumptech.glide.c.c> emptyList = Collections.emptyList();
        if (Tm == null || Tm.Nj()) {
            emptyList = new com.bumptech.glide.c.e(applicationContext).parse();
        }
        if (Tm != null && !Tm.Oj().isEmpty()) {
            Set<Class<?>> Oj = Tm.Oj();
            Iterator<com.bumptech.glide.c.c> it = emptyList.iterator();
            while (it.hasNext()) {
                com.bumptech.glide.c.c next = it.next();
                if (Oj.contains(next.getClass())) {
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "AppGlideModule excludes manifest GlideModule: " + next);
                    }
                    it.remove();
                }
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            Iterator<com.bumptech.glide.c.c> it2 = emptyList.iterator();
            while (it2.hasNext()) {
                Log.d(TAG, "Discovered GlideModule from manifest: " + it2.next().getClass());
            }
        }
        dVar.a(Tm != null ? Tm.Pj() : null);
        for (com.bumptech.glide.c.c cVar : emptyList) {
            cVar.a(applicationContext, dVar);
        }
        if (Tm != null) {
            Tm.a(applicationContext, dVar);
        }
        c E = dVar.E(applicationContext);
        for (com.bumptech.glide.c.c cVar2 : emptyList) {
            cVar2.a(applicationContext, E, E.registry);
        }
        if (Tm != null) {
            Tm.a(applicationContext, E, E.registry);
        }
        applicationContext.registerComponentCallbacks(E);
        gj = E;
    }

    private static void b(Exception exc) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", exc);
    }

    @NonNull
    public static m c(@NonNull View view) {
        return K(view.getContext()).get(view);
    }

    @Nullable
    public static File g(@NonNull Context context, @NonNull String str) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File file = new File(cacheDir, str);
            if (file.mkdirs() || (file.exists() && file.isDirectory())) {
                return file;
            }
            return null;
        }
        if (Log.isLoggable(TAG, 6)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return null;
    }

    @NonNull
    public static c get(@NonNull Context context) {
        if (gj == null) {
            synchronized (c.class) {
                if (gj == null) {
                    J(context);
                }
            }
        }
        return gj;
    }

    @VisibleForTesting
    public static synchronized void init(@NonNull Context context, @NonNull d dVar) {
        synchronized (c.class) {
            if (gj != null) {
                tearDown();
            }
            b(context, dVar);
        }
    }

    @VisibleForTesting
    @Deprecated
    public static synchronized void init(c cVar) {
        synchronized (c.class) {
            if (gj != null) {
                tearDown();
            }
            gj = cVar;
        }
    }

    @VisibleForTesting
    public static synchronized void tearDown() {
        synchronized (c.class) {
            if (gj != null) {
                gj.getContext().getApplicationContext().unregisterComponentCallbacks(gj);
                gj.Qa.shutdown();
            }
            gj = null;
        }
    }

    public void Ei() {
        com.bumptech.glide.util.l.Kk();
        this.Qa.Ei();
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.d Fi() {
        return this.Xi;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.manager.d Gi() {
        return this.cj;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public e Hi() {
        return this._i;
    }

    @NonNull
    public n Ii() {
        return this.bj;
    }

    @NonNull
    public MemoryCategory a(@NonNull MemoryCategory memoryCategory) {
        com.bumptech.glide.util.l.Lk();
        this.Yi.a(memoryCategory.getMultiplier());
        this.Xi.a(memoryCategory.getMultiplier());
        MemoryCategory memoryCategory2 = this.ej;
        this.ej = memoryCategory;
        return memoryCategory2;
    }

    public void a(@NonNull c.a... aVarArr) {
        this.Zi.b(aVarArr);
    }

    /* access modifiers changed from: package-private */
    public boolean a(@NonNull com.bumptech.glide.request.target.o<?> oVar) {
        synchronized (this.dj) {
            for (m mVar : this.dj) {
                if (mVar.e(oVar)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void aa() {
        com.bumptech.glide.util.l.Lk();
        this.Yi.aa();
        this.Xi.aa();
        this.Ma.aa();
    }

    /* access modifiers changed from: package-private */
    public void b(m mVar) {
        synchronized (this.dj) {
            if (!this.dj.contains(mVar)) {
                this.dj.add(mVar);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(m mVar) {
        synchronized (this.dj) {
            if (this.dj.contains(mVar)) {
                this.dj.remove(mVar);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    @NonNull
    public Context getContext() {
        return this._i.getBaseContext();
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onLowMemory() {
        aa();
    }

    public void onTrimMemory(int i) {
        trimMemory(i);
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.b sa() {
        return this.Ma;
    }

    public void trimMemory(int i) {
        com.bumptech.glide.util.l.Lk();
        this.Yi.trimMemory(i);
        this.Xi.trimMemory(i);
        this.Ma.trimMemory(i);
    }

    @NonNull
    public Registry wa() {
        return this.registry;
    }
}
