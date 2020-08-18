package okhttp3.internal.publicsuffix;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

public final class PublicSuffixDatabase {
    private static final String[] EMPTY_RULE = new String[0];
    private static final byte EXCEPTION_MARKER = 33;
    private static final String[] PREVAILING_RULE = {"*"};
    public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
    private static final byte[] WILDCARD_LABEL = {42};
    private static final PublicSuffixDatabase instance = new PublicSuffixDatabase();
    private final AtomicBoolean listRead = new AtomicBoolean(false);
    private byte[] publicSuffixExceptionListBytes;
    private byte[] publicSuffixListBytes;
    private final CountDownLatch readCompleteLatch = new CountDownLatch(1);

    private static String binarySearchBytes(byte[] bArr, byte[][] bArr2, int i) {
        int i2;
        byte b2;
        int i3;
        int length = bArr.length;
        int i4 = 0;
        while (i4 < length) {
            int i5 = (i4 + length) / 2;
            while (i5 > -1 && bArr[i5] != 10) {
                i5--;
            }
            int i6 = i5 + 1;
            int i7 = 1;
            while (true) {
                i2 = i6 + i7;
                if (bArr[i2] == 10) {
                    break;
                }
                i7++;
            }
            int i8 = i2 - i6;
            int i9 = i;
            boolean z = false;
            int i10 = 0;
            int i11 = 0;
            while (true) {
                if (z) {
                    b2 = 46;
                    z = false;
                } else {
                    b2 = bArr2[i9][i10] & 255;
                }
                i3 = b2 - (bArr[i6 + i11] & 255);
                if (i3 == 0) {
                    i11++;
                    i10++;
                    if (i11 == i8) {
                        break;
                    } else if (bArr2[i9].length == i10) {
                        if (i9 == bArr2.length - 1) {
                            break;
                        }
                        i9++;
                        i10 = -1;
                        z = true;
                    }
                } else {
                    break;
                }
            }
            if (i3 >= 0) {
                if (i3 <= 0) {
                    int i12 = i8 - i11;
                    int length2 = bArr2[i9].length - i10;
                    while (true) {
                        i9++;
                        if (i9 >= bArr2.length) {
                            break;
                        }
                        length2 += bArr2[i9].length;
                    }
                    if (length2 >= i12) {
                        if (length2 <= i12) {
                            return new String(bArr, i6, i8, Util.UTF_8);
                        }
                    }
                }
                i4 = i2 + 1;
            }
            length = i6 - 1;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0068 A[LOOP:3: B:34:0x0068->B:38:0x0075, LOOP_START, PHI: r1 
      PHI: (r1v2 int) = (r1v0 int), (r1v3 int) binds: [B:33:0x0066, B:38:0x0075] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0093  */
    private String[] findMatchingRule(String[] strArr) {
        String str;
        String str2;
        String str3;
        int i = 0;
        if (this.listRead.get() || !this.listRead.compareAndSet(false, true)) {
            try {
                this.readCompleteLatch.await();
            } catch (InterruptedException unused) {
            }
        } else {
            readTheListUninterruptibly();
        }
        synchronized (this) {
            if (this.publicSuffixListBytes == null) {
                throw new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.");
            }
        }
        byte[][] bArr = new byte[strArr.length][];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            bArr[i2] = strArr[i2].getBytes(Util.UTF_8);
        }
        int i3 = 0;
        while (true) {
            if (i3 >= bArr.length) {
                str = null;
                break;
            }
            str = binarySearchBytes(this.publicSuffixListBytes, bArr, i3);
            if (str != null) {
                break;
            }
            i3++;
        }
        if (bArr.length > 1) {
            byte[][] bArr2 = (byte[][]) bArr.clone();
            int i4 = 0;
            while (true) {
                if (i4 >= bArr2.length - 1) {
                    break;
                }
                bArr2[i4] = WILDCARD_LABEL;
                str2 = binarySearchBytes(this.publicSuffixListBytes, bArr2, i4);
                if (str2 != null) {
                    break;
                }
                i4++;
            }
            if (str2 != null) {
                while (true) {
                    if (i >= bArr.length - 1) {
                        break;
                    }
                    str3 = binarySearchBytes(this.publicSuffixExceptionListBytes, bArr, i);
                    if (str3 != null) {
                        break;
                    }
                    i++;
                }
            }
            str3 = null;
            if (str3 == null) {
                return ("!" + str3).split("\\.");
            } else if (str == null && str2 == null) {
                return PREVAILING_RULE;
            } else {
                String[] split = str != null ? str.split("\\.") : EMPTY_RULE;
                String[] split2 = str2 != null ? str2.split("\\.") : EMPTY_RULE;
                return split.length > split2.length ? split : split2;
            }
        }
        str2 = null;
        if (str2 != null) {
        }
        str3 = null;
        if (str3 == null) {
        }
    }

    public static PublicSuffixDatabase get() {
        return instance;
    }

    private void readTheList() throws IOException {
        InputStream resourceAsStream = PublicSuffixDatabase.class.getResourceAsStream(PUBLIC_SUFFIX_RESOURCE);
        if (resourceAsStream != null) {
            BufferedSource buffer = Okio.buffer(new GzipSource(Okio.source(resourceAsStream)));
            try {
                byte[] bArr = new byte[buffer.readInt()];
                buffer.readFully(bArr);
                byte[] bArr2 = new byte[buffer.readInt()];
                buffer.readFully(bArr2);
                synchronized (this) {
                    this.publicSuffixListBytes = bArr;
                    this.publicSuffixExceptionListBytes = bArr2;
                }
                this.readCompleteLatch.countDown();
            } finally {
                Util.closeQuietly(buffer);
            }
        }
    }

    private void readTheListUninterruptibly() {
        boolean z = false;
        while (true) {
            try {
                readTheList();
                break;
            } catch (InterruptedIOException unused) {
                z = true;
            } catch (IOException e2) {
                Platform.get().log(5, "Failed to read public suffix list", e2);
                if (z) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public String getEffectiveTldPlusOne(String str) {
        int i;
        int i2;
        if (str != null) {
            String[] split = IDN.toUnicode(str).split("\\.");
            String[] findMatchingRule = findMatchingRule(split);
            if (split.length == findMatchingRule.length && findMatchingRule[0].charAt(0) != '!') {
                return null;
            }
            if (findMatchingRule[0].charAt(0) == '!') {
                i2 = split.length;
                i = findMatchingRule.length;
            } else {
                i2 = split.length;
                i = findMatchingRule.length + 1;
            }
            StringBuilder sb = new StringBuilder();
            String[] split2 = str.split("\\.");
            for (int i3 = i2 - i; i3 < split2.length; i3++) {
                sb.append(split2[i3]);
                sb.append('.');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        throw new NullPointerException("domain == null");
    }

    /* access modifiers changed from: package-private */
    public void setListBytes(byte[] bArr, byte[] bArr2) {
        this.publicSuffixListBytes = bArr;
        this.publicSuffixExceptionListBytes = bArr2;
        this.listRead.set(true);
        this.readCompleteLatch.countDown();
    }
}
