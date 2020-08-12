package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.g;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamEncoder */
public class z implements a<InputStream> {
    private static final String TAG = "StreamEncoder";
    private final b Ll;

    public z(b bVar) {
        this.Ll = bVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038 A[Catch:{ all -> 0x002e }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004a A[SYNTHETIC, Splitter:B:28:0x004a] */
    public boolean a(@NonNull InputStream inputStream, @NonNull File file, @NonNull g gVar) {
        byte[] bArr = (byte[]) this.Ll.a(65536, byte[].class);
        boolean z = false;
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream2.write(bArr, 0, read);
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    try {
                        if (Log.isLoggable(TAG, 3)) {
                        }
                        if (fileOutputStream != null) {
                        }
                        this.Ll.put(bArr);
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        this.Ll.put(bArr);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                    }
                    this.Ll.put(bArr);
                    throw th;
                }
            }
            fileOutputStream2.close();
            z = true;
            try {
                fileOutputStream2.close();
            } catch (IOException unused2) {
            }
        } catch (IOException e3) {
            e = e3;
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to encode data onto the OutputStream", e);
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            this.Ll.put(bArr);
            return z;
        }
        this.Ll.put(bArr);
        return z;
    }
}
