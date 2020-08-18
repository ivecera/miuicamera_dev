package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Map;

public final class ByQuadrantReader implements Reader {
    private final Reader delegate;

    public ByQuadrantReader(Reader reader) {
        this.delegate = reader;
    }

    private static void makeAbsolute(ResultPoint[] resultPointArr, int i, int i2) {
        if (resultPointArr != null) {
            for (int i3 = 0; i3 < resultPointArr.length; i3++) {
                ResultPoint resultPoint = resultPointArr[i3];
                if (resultPoint != null) {
                    resultPointArr[i3] = new ResultPoint(resultPoint.getX() + ((float) i), resultPoint.getY() + ((float) i2));
                }
            }
        }
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    /* JADX WARN: Failed to insert an additional move for type inference into block B:1:0x000d */
    /* JADX DEBUG: Additional 1 move instruction added to help type inference */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:10|11|12) */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:4|5|6) */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:7|8|9) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r2 = r5.delegate.decode(r6.crop(r0, r1, r0, r1), r7);
        makeAbsolute(r2.getResultPoints(), r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004d, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004e, code lost:
        r2 = r0 / 2;
        r3 = r1 / 2;
        r5 = r5.delegate.decode(r6.crop(r2, r3, r0, r1), r7);
        makeAbsolute(r5.getResultPoints(), r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0063, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
        r3 = r5.delegate.decode(r6.crop(r0, 0, r0, r1), r7);
        makeAbsolute(r3.getResultPoints(), r0, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0029, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r3 = r5.delegate.decode(r6.crop(0, r1, r0, r1), r7);
        makeAbsolute(r3.getResultPoints(), 0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003b, code lost:
        return r3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x003c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0018 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int width = binaryBitmap.getWidth() / 2;
        int height = binaryBitmap.getHeight() / 2;
        Result decode = this.delegate.decode(binaryBitmap.crop(0, 0, width, height), map);
        this = decode;
        return decode;
    }

    @Override // com.google.zxing.Reader
    public void reset() {
        this.delegate.reset();
    }
}
