package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* compiled from: ImageHeaderParserUtils */
public final class b {
    private static final int wl = 5242880;

    private b() {
    }

    /* JADX INFO: finally extract failed */
    public static int a(@NonNull List<ImageHeaderParser> list, @Nullable InputStream inputStream, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        if (inputStream == null) {
            return -1;
        }
        if (!inputStream.markSupported()) {
            inputStream = new RecyclableBufferedInputStream(inputStream, bVar);
        }
        inputStream.mark(wl);
        int i = 0;
        int size = list.size();
        while (i < size) {
            try {
                int a2 = list.get(i).a(inputStream, bVar);
                if (a2 != -1) {
                    inputStream.reset();
                    return a2;
                }
                inputStream.reset();
                i++;
            } catch (Throwable th) {
                inputStream.reset();
                throw th;
            }
        }
        return -1;
    }

    @NonNull
    public static ImageHeaderParser.ImageType a(@NonNull List<ImageHeaderParser> list, @Nullable ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ImageHeaderParser.ImageType a2 = list.get(i).a(byteBuffer);
            if (a2 != ImageHeaderParser.ImageType.UNKNOWN) {
                return a2;
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    /* JADX INFO: finally extract failed */
    @NonNull
    public static ImageHeaderParser.ImageType b(@NonNull List<ImageHeaderParser> list, @Nullable InputStream inputStream, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b bVar) throws IOException {
        if (inputStream == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        if (!inputStream.markSupported()) {
            inputStream = new RecyclableBufferedInputStream(inputStream, bVar);
        }
        inputStream.mark(wl);
        int i = 0;
        int size = list.size();
        while (i < size) {
            try {
                ImageHeaderParser.ImageType c2 = list.get(i).c(inputStream);
                if (c2 != ImageHeaderParser.ImageType.UNKNOWN) {
                    inputStream.reset();
                    return c2;
                }
                inputStream.reset();
                i++;
            } catch (Throwable th) {
                inputStream.reset();
                throw th;
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }
}
