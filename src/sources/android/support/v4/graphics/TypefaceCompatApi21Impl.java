package android.support.v4.graphics;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.provider.FontsContractCompat;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            String readlink = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd());
            if (OsConstants.S_ISREG(Os.stat(readlink).st_mode)) {
                return new File(readlink);
            }
        } catch (ErrnoException unused) {
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0047, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004c, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        r3.addSuppressed(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0050, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0053, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0054, code lost:
        if (r5 != null) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x005a, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x005b, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x005e, code lost:
        throw r4;
     */
    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        if (fontInfoArr.length < 1) {
            return null;
        }
        FontsContractCompat.FontInfo findBestInfo = findBestInfo(fontInfoArr, i);
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(findBestInfo.getUri(), "r", cancellationSignal);
            File file = getFile(openFileDescriptor);
            if (file != null) {
                if (file.canRead()) {
                    Typeface createFromFile = Typeface.createFromFile(file);
                    if (openFileDescriptor != null) {
                        openFileDescriptor.close();
                    }
                    return createFromFile;
                }
            }
            FileInputStream fileInputStream = new FileInputStream(openFileDescriptor.getFileDescriptor());
            Typeface createFromInputStream = super.createFromInputStream(context, fileInputStream);
            fileInputStream.close();
            if (openFileDescriptor != null) {
                openFileDescriptor.close();
            }
            return createFromInputStream;
        } catch (IOException unused) {
            return null;
        }
    }
}
