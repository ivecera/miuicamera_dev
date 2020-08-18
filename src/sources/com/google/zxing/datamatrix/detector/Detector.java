package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.WhiteRectangleDetector;

public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    private ResultPoint correctTopRight(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint = resultPointArr[0];
        ResultPoint resultPoint2 = resultPointArr[1];
        ResultPoint resultPoint3 = resultPointArr[2];
        ResultPoint resultPoint4 = resultPointArr[3];
        int transitionsBetween = transitionsBetween(resultPoint, resultPoint4);
        ResultPoint shiftPoint = shiftPoint(resultPoint, resultPoint2, (transitionsBetween(resultPoint2, resultPoint4) + 1) << 2);
        ResultPoint shiftPoint2 = shiftPoint(resultPoint3, resultPoint2, (transitionsBetween + 1) << 2);
        int transitionsBetween2 = transitionsBetween(shiftPoint, resultPoint4);
        int transitionsBetween3 = transitionsBetween(shiftPoint2, resultPoint4);
        float f2 = (float) (transitionsBetween2 + 1);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + ((resultPoint3.getX() - resultPoint2.getX()) / f2), resultPoint4.getY() + ((resultPoint3.getY() - resultPoint2.getY()) / f2));
        float f3 = (float) (transitionsBetween3 + 1);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + ((resultPoint.getX() - resultPoint2.getX()) / f3), resultPoint4.getY() + ((resultPoint.getY() - resultPoint2.getY()) / f3));
        if (isValid(resultPoint5)) {
            return (isValid(resultPoint6) && transitionsBetween(shiftPoint, resultPoint5) + transitionsBetween(shiftPoint2, resultPoint5) <= transitionsBetween(shiftPoint, resultPoint6) + transitionsBetween(shiftPoint2, resultPoint6)) ? resultPoint6 : resultPoint5;
        }
        if (isValid(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private ResultPoint[] detectSolid1(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint = resultPointArr[0];
        ResultPoint resultPoint2 = resultPointArr[1];
        ResultPoint resultPoint3 = resultPointArr[3];
        ResultPoint resultPoint4 = resultPointArr[2];
        int transitionsBetween = transitionsBetween(resultPoint, resultPoint2);
        int transitionsBetween2 = transitionsBetween(resultPoint2, resultPoint3);
        int transitionsBetween3 = transitionsBetween(resultPoint3, resultPoint4);
        int transitionsBetween4 = transitionsBetween(resultPoint4, resultPoint);
        ResultPoint[] resultPointArr2 = {resultPoint4, resultPoint, resultPoint2, resultPoint3};
        if (transitionsBetween > transitionsBetween2) {
            resultPointArr2[0] = resultPoint;
            resultPointArr2[1] = resultPoint2;
            resultPointArr2[2] = resultPoint3;
            resultPointArr2[3] = resultPoint4;
            transitionsBetween = transitionsBetween2;
        }
        if (transitionsBetween > transitionsBetween3) {
            resultPointArr2[0] = resultPoint2;
            resultPointArr2[1] = resultPoint3;
            resultPointArr2[2] = resultPoint4;
            resultPointArr2[3] = resultPoint;
            transitionsBetween = transitionsBetween3;
        }
        if (transitionsBetween > transitionsBetween4) {
            resultPointArr2[0] = resultPoint3;
            resultPointArr2[1] = resultPoint4;
            resultPointArr2[2] = resultPoint;
            resultPointArr2[3] = resultPoint2;
        }
        return resultPointArr2;
    }

    private ResultPoint[] detectSolid2(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint = resultPointArr[0];
        ResultPoint resultPoint2 = resultPointArr[1];
        ResultPoint resultPoint3 = resultPointArr[2];
        ResultPoint resultPoint4 = resultPointArr[3];
        int transitionsBetween = (transitionsBetween(resultPoint, resultPoint4) + 1) << 2;
        if (transitionsBetween(shiftPoint(resultPoint2, resultPoint3, transitionsBetween), resultPoint) < transitionsBetween(shiftPoint(resultPoint3, resultPoint2, transitionsBetween), resultPoint4)) {
            resultPointArr[0] = resultPoint;
            resultPointArr[1] = resultPoint2;
            resultPointArr[2] = resultPoint3;
            resultPointArr[3] = resultPoint4;
        } else {
            resultPointArr[0] = resultPoint2;
            resultPointArr[1] = resultPoint3;
            resultPointArr[2] = resultPoint4;
            resultPointArr[3] = resultPoint;
        }
        return resultPointArr;
    }

    private boolean isValid(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < ((float) this.image.getWidth()) && resultPoint.getY() > 0.0f && resultPoint.getY() < ((float) this.image.getHeight());
    }

    private static ResultPoint moveAway(ResultPoint resultPoint, float f2, float f3) {
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        return new ResultPoint(x < f2 ? x - 1.0f : x + 1.0f, y < f3 ? y - 1.0f : y + 1.0f);
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException {
        float f2 = ((float) i) - 0.5f;
        float f3 = ((float) i2) - 0.5f;
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i2, 0.5f, 0.5f, f2, 0.5f, f2, f3, 0.5f, f3, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private static ResultPoint shiftPoint(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        float f2 = (float) (i + 1);
        return new ResultPoint(resultPoint.getX() + ((resultPoint2.getX() - resultPoint.getX()) / f2), resultPoint.getY() + ((resultPoint2.getY() - resultPoint.getY()) / f2));
    }

    private ResultPoint[] shiftToModuleCenter(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint = resultPointArr[0];
        ResultPoint resultPoint2 = resultPointArr[1];
        ResultPoint resultPoint3 = resultPointArr[2];
        ResultPoint resultPoint4 = resultPointArr[3];
        ResultPoint shiftPoint = shiftPoint(resultPoint, resultPoint2, (transitionsBetween(resultPoint3, resultPoint4) + 1) << 2);
        ResultPoint shiftPoint2 = shiftPoint(resultPoint3, resultPoint2, (transitionsBetween(resultPoint, resultPoint4) + 1) << 2);
        int transitionsBetween = transitionsBetween(shiftPoint, resultPoint4) + 1;
        int transitionsBetween2 = transitionsBetween(shiftPoint2, resultPoint4) + 1;
        if ((transitionsBetween & 1) == 1) {
            transitionsBetween++;
        }
        if ((transitionsBetween2 & 1) == 1) {
            transitionsBetween2++;
        }
        float x = (((resultPoint.getX() + resultPoint2.getX()) + resultPoint3.getX()) + resultPoint4.getX()) / 4.0f;
        float y = (((resultPoint.getY() + resultPoint2.getY()) + resultPoint3.getY()) + resultPoint4.getY()) / 4.0f;
        ResultPoint moveAway = moveAway(resultPoint, x, y);
        ResultPoint moveAway2 = moveAway(resultPoint2, x, y);
        ResultPoint moveAway3 = moveAway(resultPoint3, x, y);
        ResultPoint moveAway4 = moveAway(resultPoint4, x, y);
        int i = transitionsBetween2 << 2;
        int i2 = transitionsBetween << 2;
        return new ResultPoint[]{shiftPoint(shiftPoint(moveAway, moveAway2, i), moveAway4, i2), shiftPoint(shiftPoint(moveAway2, moveAway, i), moveAway3, i2), shiftPoint(shiftPoint(moveAway3, moveAway4, i), moveAway2, i2), shiftPoint(shiftPoint(moveAway4, moveAway3, i), moveAway, i2)};
    }

    private int transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int x = (int) resultPoint.getX();
        int y = (int) resultPoint.getY();
        int x2 = (int) resultPoint2.getX();
        int y2 = (int) resultPoint2.getY();
        int i = 0;
        int i2 = 1;
        boolean z = Math.abs(y2 - y) > Math.abs(x2 - x);
        if (z) {
            y = x;
            x = y;
            y2 = x2;
            x2 = y2;
        }
        int abs = Math.abs(x2 - x);
        int abs2 = Math.abs(y2 - y);
        int i3 = (-abs) / 2;
        int i4 = y < y2 ? 1 : -1;
        if (x >= x2) {
            i2 = -1;
        }
        boolean z2 = this.image.get(z ? y : x, z ? x : y);
        while (x != x2) {
            boolean z3 = this.image.get(z ? y : x, z ? x : y);
            if (z3 != z2) {
                i++;
                z2 = z3;
            }
            i3 += abs2;
            if (i3 > 0) {
                if (y == y2) {
                    break;
                }
                y += i4;
                i3 -= abs;
            }
            x += i2;
        }
        return i;
    }

    public DetectorResult detect() throws NotFoundException {
        int i;
        int i2;
        ResultPoint[] detectSolid1 = detectSolid1(this.rectangleDetector.detect());
        detectSolid2(detectSolid1);
        detectSolid1[3] = correctTopRight(detectSolid1);
        if (detectSolid1[3] != null) {
            ResultPoint[] shiftToModuleCenter = shiftToModuleCenter(detectSolid1);
            ResultPoint resultPoint = shiftToModuleCenter[0];
            ResultPoint resultPoint2 = shiftToModuleCenter[1];
            ResultPoint resultPoint3 = shiftToModuleCenter[2];
            ResultPoint resultPoint4 = shiftToModuleCenter[3];
            int transitionsBetween = transitionsBetween(resultPoint, resultPoint4) + 1;
            int transitionsBetween2 = transitionsBetween(resultPoint3, resultPoint4) + 1;
            if ((transitionsBetween & 1) == 1) {
                transitionsBetween++;
            }
            if ((transitionsBetween2 & 1) == 1) {
                transitionsBetween2++;
            }
            if (transitionsBetween * 4 >= transitionsBetween2 * 7 || transitionsBetween2 * 4 >= transitionsBetween * 7) {
                i2 = transitionsBetween;
                i = transitionsBetween2;
            } else {
                i2 = Math.max(transitionsBetween, transitionsBetween2);
                i = i2;
            }
            return new DetectorResult(sampleGrid(this.image, resultPoint, resultPoint2, resultPoint3, resultPoint4, i2, i), new ResultPoint[]{resultPoint, resultPoint2, resultPoint3, resultPoint4});
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
