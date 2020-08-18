package com.android.camera.network.net;

import com.android.camera.log.Log;
import com.android.camera.network.net.base.Cacheable;
import com.android.volley.Cache;
import com.android.volley.toolbox.DiskBasedCache;
import java.io.File;
import java.util.HashMap;

public class GalleryCache extends DiskBasedCache {
    private static final String TAG = "GalleryCache";

    public GalleryCache(File file, int i) {
        super(file, i);
        Log.d(TAG, "Network cache path: " + file.getAbsolutePath());
        initialize();
    }

    @Override // com.android.volley.Cache, com.android.volley.toolbox.DiskBasedCache
    public synchronized Cache.Entry get(String str) {
        Cache.Entry entry;
        entry = super.get(str);
        if (entry != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(Cacheable.HEADER_FROM_CACHE, String.valueOf(true));
            entry.responseHeaders = hashMap;
            Log.d(TAG, String.format("get cache: key %s, isExpired: %s", Integer.valueOf(str.hashCode()), Boolean.valueOf(entry.isExpired())));
            if (!entry.isExpired()) {
                Log.d(TAG, "cache hit.");
            }
        }
        return entry;
    }

    public synchronized boolean isCacheValid(String str) {
        Cache.Entry entry = super.get(str);
        return entry != null && !entry.isExpired();
    }

    @Override // com.android.volley.Cache, com.android.volley.toolbox.DiskBasedCache
    @Deprecated
    public synchronized void put(String str, Cache.Entry entry) {
    }

    public synchronized void put(String str, byte[] bArr, long j, long j2) {
        Cache.Entry entry = new Cache.Entry();
        entry.data = bArr;
        entry.ttl = j;
        entry.softTtl = j2;
        super.put(str, entry);
        Object[] objArr = new Object[3];
        int i = 0;
        objArr[0] = Integer.valueOf(str.hashCode());
        if (bArr != null) {
            i = bArr.length;
        }
        objArr[1] = Integer.valueOf(i);
        objArr[2] = Long.valueOf(j);
        Log.d(TAG, String.format("put cache. key %s size %s expires %s", objArr));
    }
}
