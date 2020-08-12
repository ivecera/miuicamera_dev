package com.ss.android.ttve.nativePort;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.ss.android.vesdk.runtime.VESafelyLibsLoader;
import java.util.ArrayList;
import java.util.List;

public class TENativeLibsLoader {
    /* access modifiers changed from: private */
    public static final String TAG = "TENativeLibsLoader";
    private static boolean isLibraryLoadedOpt = false;
    private static ILibraryLoader mLibraryLoader;
    /* access modifiers changed from: private */
    public static Context sContext;
    private static ILibraryLoader sDefaultLibraryLoader = new DefaultLibraryLoader();
    private static boolean sLibraryLoaded;
    private static boolean sLoadOptLibrary = false;

    public static class DefaultLibraryLoader implements ILibraryLoader {
        @Override // com.ss.android.ttve.nativePort.TENativeLibsLoader.ILibraryLoader
        public void onLoadNativeLibs(List<String> list) {
            for (String str : list) {
                if (!VESafelyLibsLoader.loadLibrary(str, TENativeLibsLoader.sContext)) {
                    String access$100 = TENativeLibsLoader.TAG;
                    Log.e(access$100, "loadLibrary " + str + " failed");
                }
            }
        }
    }

    public interface ILibraryLoader {
        void onLoadNativeLibs(List<String> list);
    }

    public static void enableLoadOptLibrary(boolean z) {
        sLoadOptLibrary = z;
    }

    public static int getLibraryLoadedVersion() {
        if (!sLibraryLoaded) {
            return -1;
        }
        return isLibraryLoadedOpt ? 1 : 0;
    }

    public static synchronized void loadLibrary() {
        synchronized (TENativeLibsLoader.class) {
            if (!sLibraryLoaded) {
                ArrayList arrayList = new ArrayList();
                arrayList.add("ttffmpeg");
                arrayList.add("ttyuv");
                arrayList.add("tt_effect");
                arrayList.add("ttvebase");
                arrayList.add("ttvideorecorder");
                if (sLoadOptLibrary) {
                    arrayList.add("ttvideoeditor-import");
                    isLibraryLoadedOpt = true;
                } else {
                    arrayList.add("ttvideoeditor");
                    isLibraryLoadedOpt = false;
                }
                if (mLibraryLoader != null) {
                    mLibraryLoader.onLoadNativeLibs(arrayList);
                    sLibraryLoaded = true;
                    return;
                }
                sDefaultLibraryLoader.onLoadNativeLibs(arrayList);
                sLibraryLoaded = true;
            }
        }
    }

    public static synchronized void setContext(@NonNull Context context) {
        synchronized (TENativeLibsLoader.class) {
            sContext = context;
        }
    }

    public static void setLibraryLoad(ILibraryLoader iLibraryLoader) {
        mLibraryLoader = iLibraryLoader;
    }
}
