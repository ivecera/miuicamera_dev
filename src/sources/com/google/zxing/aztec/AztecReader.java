package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.DecoderResult;
import java.util.List;
import java.util.Map;

public final class AztecReader implements Reader {
    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return decode(binaryBitmap, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005c A[LOOP:0: B:29:0x005a->B:30:0x005c, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008d  */
    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        ResultPoint[] resultPointArr;
        FormatException formatException;
        List<byte[]> byteSegments;
        String eCLevel;
        ResultPointCallback resultPointCallback;
        ResultPoint[] resultPointArr2;
        Detector detector = new Detector(binaryBitmap.getBlackMatrix());
        DecoderResult decoderResult = null;
        try {
            AztecDetectorResult detect = detector.detect(false);
            resultPointArr2 = detect.getPoints();
            try {
                resultPointArr = resultPointArr2;
                formatException = null;
                decoderResult = new Decoder().decode(detect);
                e = null;
            } catch (NotFoundException e2) {
                e = e2;
                resultPointArr = resultPointArr2;
                formatException = null;
                if (decoderResult == null) {
                }
                while (r12 < r13) {
                }
                Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), decoderResult.getNumBits(), resultPointArr, BarcodeFormat.AZTEC, System.currentTimeMillis());
                byteSegments = decoderResult.getByteSegments();
                if (byteSegments != null) {
                }
                eCLevel = decoderResult.getECLevel();
                if (eCLevel != null) {
                }
                return result;
            } catch (FormatException e3) {
                e = e3;
                resultPointArr = resultPointArr2;
                formatException = e;
                e = null;
                if (decoderResult == null) {
                }
                while (r12 < r13) {
                }
                Result result2 = new Result(decoderResult.getText(), decoderResult.getRawBytes(), decoderResult.getNumBits(), resultPointArr, BarcodeFormat.AZTEC, System.currentTimeMillis());
                byteSegments = decoderResult.getByteSegments();
                if (byteSegments != null) {
                }
                eCLevel = decoderResult.getECLevel();
                if (eCLevel != null) {
                }
                return result2;
            }
        } catch (NotFoundException e4) {
            e = e4;
            resultPointArr2 = null;
            resultPointArr = resultPointArr2;
            formatException = null;
            if (decoderResult == null) {
            }
            while (r12 < r13) {
            }
            Result result22 = new Result(decoderResult.getText(), decoderResult.getRawBytes(), decoderResult.getNumBits(), resultPointArr, BarcodeFormat.AZTEC, System.currentTimeMillis());
            byteSegments = decoderResult.getByteSegments();
            if (byteSegments != null) {
            }
            eCLevel = decoderResult.getECLevel();
            if (eCLevel != null) {
            }
            return result22;
        } catch (FormatException e5) {
            e = e5;
            resultPointArr2 = null;
            resultPointArr = resultPointArr2;
            formatException = e;
            e = null;
            if (decoderResult == null) {
            }
            while (r12 < r13) {
            }
            Result result222 = new Result(decoderResult.getText(), decoderResult.getRawBytes(), decoderResult.getNumBits(), resultPointArr, BarcodeFormat.AZTEC, System.currentTimeMillis());
            byteSegments = decoderResult.getByteSegments();
            if (byteSegments != null) {
            }
            eCLevel = decoderResult.getECLevel();
            if (eCLevel != null) {
            }
            return result222;
        }
        if (decoderResult == null) {
            try {
                AztecDetectorResult detect2 = detector.detect(true);
                resultPointArr = detect2.getPoints();
                decoderResult = new Decoder().decode(detect2);
            } catch (FormatException | NotFoundException e6) {
                if (e != null) {
                    throw e;
                } else if (formatException != null) {
                    throw formatException;
                } else {
                    throw e6;
                }
            }
        }
        if (!(map == null || (resultPointCallback = (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) == null)) {
            for (ResultPoint resultPoint : resultPointArr) {
                resultPointCallback.foundPossibleResultPoint(resultPoint);
            }
        }
        Result result2222 = new Result(decoderResult.getText(), decoderResult.getRawBytes(), decoderResult.getNumBits(), resultPointArr, BarcodeFormat.AZTEC, System.currentTimeMillis());
        byteSegments = decoderResult.getByteSegments();
        if (byteSegments != null) {
            result2222.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        eCLevel = decoderResult.getECLevel();
        if (eCLevel != null) {
            result2222.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        return result2222;
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }
}
