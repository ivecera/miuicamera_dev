package android.support.v4.graphics;

import android.graphics.Path;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class PathParser {
    private static final String LOGTAG = "PathParser";

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public float[] mParams;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public char mType;

        PathDataNode(char c2, float[] fArr) {
            this.mType = c2;
            this.mParams = fArr;
        }

        PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.copyOfRange(fArr, 0, fArr.length);
        }

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        private static void addCommand(Path path, float[] fArr, char c2, char c3, float[] fArr2) {
            int i;
            int i2;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            int i3;
            char c4 = c3;
            char c5 = 0;
            float f12 = fArr[0];
            float f13 = fArr[1];
            float f14 = fArr[2];
            float f15 = fArr[3];
            float f16 = fArr[4];
            float f17 = fArr[5];
            switch (c4) {
                case 'A':
                case 'a':
                    i3 = 7;
                    i = i3;
                    break;
                case 'C':
                case 'c':
                    i3 = 6;
                    i = i3;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                default:
                    i = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f16, f17);
                    f12 = f16;
                    f14 = f12;
                    f13 = f17;
                    f15 = f13;
                    i = 2;
                    break;
            }
            float f18 = f12;
            float f19 = f13;
            float f20 = f16;
            float f21 = f17;
            int i4 = 0;
            char c6 = c2;
            while (i4 < fArr2.length) {
                if (c4 != 'A') {
                    if (c4 == 'C') {
                        i2 = i4;
                        int i5 = i2 + 2;
                        int i6 = i2 + 3;
                        int i7 = i2 + 4;
                        int i8 = i2 + 5;
                        path.cubicTo(fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i5], fArr2[i6], fArr2[i7], fArr2[i8]);
                        f18 = fArr2[i7];
                        float f22 = fArr2[i8];
                        float f23 = fArr2[i5];
                        float f24 = fArr2[i6];
                        f19 = f22;
                        f15 = f24;
                        f14 = f23;
                    } else if (c4 == 'H') {
                        i2 = i4;
                        int i9 = i2 + 0;
                        path.lineTo(fArr2[i9], f19);
                        f18 = fArr2[i9];
                    } else if (c4 == 'Q') {
                        i2 = i4;
                        int i10 = i2 + 0;
                        int i11 = i2 + 1;
                        int i12 = i2 + 2;
                        int i13 = i2 + 3;
                        path.quadTo(fArr2[i10], fArr2[i11], fArr2[i12], fArr2[i13]);
                        float f25 = fArr2[i10];
                        float f26 = fArr2[i11];
                        f18 = fArr2[i12];
                        f19 = fArr2[i13];
                        f14 = f25;
                        f15 = f26;
                    } else if (c4 == 'V') {
                        i2 = i4;
                        int i14 = i2 + 0;
                        path.lineTo(f18, fArr2[i14]);
                        f19 = fArr2[i14];
                    } else if (c4 != 'a') {
                        if (c4 == 'c') {
                            int i15 = i4 + 2;
                            int i16 = i4 + 3;
                            int i17 = i4 + 4;
                            int i18 = i4 + 5;
                            path.rCubicTo(fArr2[i4 + 0], fArr2[i4 + 1], fArr2[i15], fArr2[i16], fArr2[i17], fArr2[i18]);
                            f5 = fArr2[i15] + f18;
                            f4 = fArr2[i16] + f19;
                            f18 += fArr2[i17];
                            f6 = fArr2[i18];
                            f19 += f6;
                            f14 = f5;
                            f15 = f4;
                        } else if (c4 != 'h') {
                            if (c4 != 'q') {
                                if (c4 == 'v') {
                                    int i19 = i4 + 0;
                                    path.rLineTo(0.0f, fArr2[i19]);
                                    f7 = fArr2[i19];
                                } else if (c4 != 'L') {
                                    if (c4 == 'M') {
                                        int i20 = i4 + 0;
                                        f18 = fArr2[i20];
                                        int i21 = i4 + 1;
                                        f19 = fArr2[i21];
                                        if (i4 > 0) {
                                            path.lineTo(fArr2[i20], fArr2[i21]);
                                        } else {
                                            path.moveTo(fArr2[i20], fArr2[i21]);
                                        }
                                    } else if (c4 == 'S') {
                                        if (c6 == 'c' || c6 == 's' || c6 == 'C' || c6 == 'S') {
                                            f18 = (f18 * 2.0f) - f14;
                                            f19 = (f19 * 2.0f) - f15;
                                        }
                                        int i22 = i4 + 0;
                                        int i23 = i4 + 1;
                                        int i24 = i4 + 2;
                                        int i25 = i4 + 3;
                                        path.cubicTo(f18, f19, fArr2[i22], fArr2[i23], fArr2[i24], fArr2[i25]);
                                        f5 = fArr2[i22];
                                        f4 = fArr2[i23];
                                        f18 = fArr2[i24];
                                        f19 = fArr2[i25];
                                        f14 = f5;
                                        f15 = f4;
                                    } else if (c4 == 'T') {
                                        if (c6 == 'q' || c6 == 't' || c6 == 'Q' || c6 == 'T') {
                                            f18 = (f18 * 2.0f) - f14;
                                            f19 = (f19 * 2.0f) - f15;
                                        }
                                        int i26 = i4 + 0;
                                        int i27 = i4 + 1;
                                        path.quadTo(f18, f19, fArr2[i26], fArr2[i27]);
                                        float f27 = fArr2[i26];
                                        float f28 = fArr2[i27];
                                        f15 = f19;
                                        f14 = f18;
                                        i2 = i4;
                                        f18 = f27;
                                        f19 = f28;
                                    } else if (c4 == 'l') {
                                        int i28 = i4 + 0;
                                        int i29 = i4 + 1;
                                        path.rLineTo(fArr2[i28], fArr2[i29]);
                                        f18 += fArr2[i28];
                                        f7 = fArr2[i29];
                                    } else if (c4 == 'm') {
                                        int i30 = i4 + 0;
                                        f18 += fArr2[i30];
                                        int i31 = i4 + 1;
                                        f19 += fArr2[i31];
                                        if (i4 > 0) {
                                            path.rLineTo(fArr2[i30], fArr2[i31]);
                                        } else {
                                            path.rMoveTo(fArr2[i30], fArr2[i31]);
                                        }
                                    } else if (c4 == 's') {
                                        if (c6 == 'c' || c6 == 's' || c6 == 'C' || c6 == 'S') {
                                            float f29 = f18 - f14;
                                            f8 = f19 - f15;
                                            f9 = f29;
                                        } else {
                                            f9 = 0.0f;
                                            f8 = 0.0f;
                                        }
                                        int i32 = i4 + 0;
                                        int i33 = i4 + 1;
                                        int i34 = i4 + 2;
                                        int i35 = i4 + 3;
                                        path.rCubicTo(f9, f8, fArr2[i32], fArr2[i33], fArr2[i34], fArr2[i35]);
                                        f5 = fArr2[i32] + f18;
                                        f4 = fArr2[i33] + f19;
                                        f18 += fArr2[i34];
                                        f6 = fArr2[i35];
                                    } else if (c4 == 't') {
                                        if (c6 == 'q' || c6 == 't' || c6 == 'Q' || c6 == 'T') {
                                            f10 = f18 - f14;
                                            f11 = f19 - f15;
                                        } else {
                                            f11 = 0.0f;
                                            f10 = 0.0f;
                                        }
                                        int i36 = i4 + 0;
                                        int i37 = i4 + 1;
                                        path.rQuadTo(f10, f11, fArr2[i36], fArr2[i37]);
                                        float f30 = f10 + f18;
                                        float f31 = f11 + f19;
                                        f18 += fArr2[i36];
                                        f19 += fArr2[i37];
                                        f15 = f31;
                                        f14 = f30;
                                    }
                                    f21 = f19;
                                    f20 = f18;
                                } else {
                                    int i38 = i4 + 0;
                                    int i39 = i4 + 1;
                                    path.lineTo(fArr2[i38], fArr2[i39]);
                                    f18 = fArr2[i38];
                                    f19 = fArr2[i39];
                                }
                                f19 += f7;
                            } else {
                                int i40 = i4 + 0;
                                int i41 = i4 + 1;
                                int i42 = i4 + 2;
                                int i43 = i4 + 3;
                                path.rQuadTo(fArr2[i40], fArr2[i41], fArr2[i42], fArr2[i43]);
                                f5 = fArr2[i40] + f18;
                                f4 = fArr2[i41] + f19;
                                f18 += fArr2[i42];
                                f6 = fArr2[i43];
                            }
                            f19 += f6;
                            f14 = f5;
                            f15 = f4;
                        } else {
                            int i44 = i4 + 0;
                            path.rLineTo(fArr2[i44], 0.0f);
                            f18 += fArr2[i44];
                        }
                        i2 = i4;
                    } else {
                        int i45 = i4 + 5;
                        int i46 = i4 + 6;
                        i2 = i4;
                        drawArc(path, f18, f19, fArr2[i45] + f18, fArr2[i46] + f19, fArr2[i4 + 0], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3] != 0.0f, fArr2[i4 + 4] != 0.0f);
                        f2 = f18 + fArr2[i45];
                        f3 = f19 + fArr2[i46];
                    }
                    i4 = i2 + i;
                    c6 = c3;
                    c4 = c6;
                    c5 = 0;
                } else {
                    i2 = i4;
                    int i47 = i2 + 5;
                    int i48 = i2 + 6;
                    drawArc(path, f18, f19, fArr2[i47], fArr2[i48], fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f, fArr2[i2 + 4] != 0.0f);
                    f2 = fArr2[i47];
                    f3 = fArr2[i48];
                }
                f15 = f19;
                f14 = f18;
                i4 = i2 + i;
                c6 = c3;
                c4 = c6;
                c5 = 0;
            }
            fArr[c5] = f18;
            fArr[1] = f19;
            fArr[2] = f14;
            fArr[3] = f15;
            fArr[4] = f20;
            fArr[5] = f21;
        }

        private static void arcToBezier(Path path, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
            double d11 = d4;
            int ceil = (int) Math.ceil(Math.abs((d10 * 4.0d) / 3.141592653589793d));
            double cos = Math.cos(d8);
            double sin = Math.sin(d8);
            double cos2 = Math.cos(d9);
            double sin2 = Math.sin(d9);
            double d12 = -d11;
            double d13 = d12 * cos;
            double d14 = d5 * sin;
            double d15 = (d13 * sin2) - (d14 * cos2);
            double d16 = d12 * sin;
            double d17 = d5 * cos;
            double d18 = (sin2 * d16) + (cos2 * d17);
            double d19 = d10 / ((double) ceil);
            double d20 = d7;
            double d21 = d18;
            double d22 = d15;
            int i = 0;
            double d23 = d6;
            double d24 = d9;
            while (i < ceil) {
                double d25 = d24 + d19;
                double sin3 = Math.sin(d25);
                double cos3 = Math.cos(d25);
                double d26 = (d2 + ((d11 * cos) * cos3)) - (d14 * sin3);
                double d27 = d3 + (d11 * sin * cos3) + (d17 * sin3);
                double d28 = (d13 * sin3) - (d14 * cos3);
                double d29 = (sin3 * d16) + (cos3 * d17);
                double d30 = d25 - d24;
                double tan = Math.tan(d30 / 2.0d);
                double sin4 = (Math.sin(d30) * (Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d)) / 3.0d;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) (d23 + (d22 * sin4)), (float) (d20 + (d21 * sin4)), (float) (d26 - (sin4 * d28)), (float) (d27 - (sin4 * d29)), (float) d26, (float) d27);
                i++;
                d19 = d19;
                ceil = ceil;
                sin = sin;
                d20 = d27;
                d16 = d16;
                d24 = d25;
                d21 = d29;
                d22 = d28;
                cos = cos;
                d11 = d4;
                d23 = d26;
            }
        }

        private static void drawArc(Path path, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z, boolean z2) {
            double d2;
            double d3;
            double radians = Math.toRadians((double) f8);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double d4 = (double) f2;
            double d5 = d4 * cos;
            double d6 = (double) f3;
            double d7 = (double) f6;
            double d8 = (d5 + (d6 * sin)) / d7;
            double d9 = (double) f7;
            double d10 = ((((double) (-f2)) * sin) + (d6 * cos)) / d9;
            double d11 = (double) f5;
            double d12 = ((((double) f4) * cos) + (d11 * sin)) / d7;
            double d13 = ((((double) (-f4)) * sin) + (d11 * cos)) / d9;
            double d14 = d8 - d12;
            double d15 = d10 - d13;
            double d16 = (d8 + d12) / 2.0d;
            double d17 = (d10 + d13) / 2.0d;
            double d18 = (d14 * d14) + (d15 * d15);
            if (d18 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d19 = (1.0d / d18) - 0.25d;
            if (d19 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d18);
                float sqrt = (float) (Math.sqrt(d18) / 1.99999d);
                drawArc(path, f2, f3, f4, f5, f6 * sqrt, f7 * sqrt, f8, z, z2);
                return;
            }
            double sqrt2 = Math.sqrt(d19);
            double d20 = d14 * sqrt2;
            double d21 = sqrt2 * d15;
            if (z == z2) {
                d3 = d16 - d21;
                d2 = d17 + d20;
            } else {
                d3 = d16 + d21;
                d2 = d17 - d20;
            }
            double atan2 = Math.atan2(d10 - d2, d8 - d3);
            double atan22 = Math.atan2(d13 - d2, d12 - d3) - atan2;
            int i = (atan22 > 0.0d ? 1 : (atan22 == 0.0d ? 0 : -1));
            if (z2 != (i >= 0)) {
                atan22 = i > 0 ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
            }
            double d22 = d3 * d7;
            double d23 = d2 * d9;
            arcToBezier(path, (d22 * cos) - (d23 * sin), (d22 * sin) + (d23 * cos), d7, d9, d4, d6, radians, atan2, atan22);
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            float[] fArr = new float[6];
            char c2 = 'm';
            for (int i = 0; i < pathDataNodeArr.length; i++) {
                addCommand(path, fArr, c2, pathDataNodeArr[i].mType, pathDataNodeArr[i].mParams);
                c2 = pathDataNodeArr[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f2) {
            int i = 0;
            while (true) {
                float[] fArr = pathDataNode.mParams;
                if (i < fArr.length) {
                    this.mParams[i] = (fArr[i] * (1.0f - f2)) + (pathDataNode2.mParams[i] * f2);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c2, float[] fArr) {
        arrayList.add(new PathDataNode(c2, fArr));
    }

    public static boolean canMorph(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            if (pathDataNodeArr[i].mType != pathDataNodeArr2[i].mType || pathDataNodeArr[i].mParams.length != pathDataNodeArr2[i].mParams.length) {
                return false;
            }
        }
        return true;
    }

    static float[] copyOfRange(float[] fArr, int i, int i2) {
        if (i <= i2) {
            int length = fArr.length;
            if (i < 0 || i > length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int i3 = i2 - i;
            int min = Math.min(i3, length - i);
            float[] fArr2 = new float[i3];
            System.arraycopy(fArr, i, fArr2, 0, min);
            return fArr2;
        }
        throw new IllegalArgumentException();
    }

    public static PathDataNode[] createNodesFromPathData(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int i2 = 0;
        while (i < str.length()) {
            int nextStart = nextStart(str, i);
            String trim = str.substring(i2, nextStart).trim();
            if (trim.length() > 0) {
                addNode(arrayList, trim.charAt(0), getFloats(trim));
            }
            i2 = nextStart;
            i = nextStart + 1;
        }
        if (i - i2 == 1 && i2 < str.length()) {
            addNode(arrayList, str.charAt(i2), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        PathDataNode[] createNodesFromPathData = createNodesFromPathData(str);
        if (createNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(createNodesFromPathData, path);
            return path;
        } catch (RuntimeException e2) {
            throw new RuntimeException("Error in parsing " + str, e2);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = new PathDataNode(pathDataNodeArr[i]);
        }
        return pathDataNodeArr2;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003a A[LOOP:0: B:1:0x0007->B:20:0x003a, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003d A[SYNTHETIC] */
    private static void extract(String str, int i, ExtractFloatResult extractFloatResult) {
        extractFloatResult.mEndWithNegOrDot = false;
        int i2 = i;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (charAt != ' ') {
                if (charAt == 'E' || charAt == 'e') {
                    z = true;
                    if (z3) {
                        extractFloatResult.mEndPosition = i2;
                    }
                    i2++;
                } else {
                    switch (charAt) {
                        case ',':
                            break;
                        case '-':
                            if (i2 != i && !z) {
                                extractFloatResult.mEndWithNegOrDot = true;
                                break;
                            }
                            z = false;
                            break;
                        case '.':
                            if (z2) {
                                extractFloatResult.mEndWithNegOrDot = true;
                                break;
                            } else {
                                z = false;
                                z2 = true;
                                break;
                            }
                        default:
                            z = false;
                            break;
                    }
                    if (z3) {
                    }
                }
            }
            z = false;
            z3 = true;
            if (z3) {
            }
        }
        extractFloatResult.mEndPosition = i2;
    }

    private static float[] getFloats(String str) {
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = str.length();
            int i = 1;
            int i2 = 0;
            while (i < length) {
                extract(str, i, extractFloatResult);
                int i3 = extractFloatResult.mEndPosition;
                if (i < i3) {
                    fArr[i2] = Float.parseFloat(str.substring(i, i3));
                    i2++;
                }
                i = extractFloatResult.mEndWithNegOrDot ? i3 : i3 + 1;
            }
            return copyOfRange(fArr, 0, i2);
        } catch (NumberFormatException e2) {
            throw new RuntimeException("error in parsing \"" + str + "\"", e2);
        }
    }

    private static int nextStart(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (((charAt - 'A') * (charAt - 'Z') <= 0 || (charAt - 'a') * (charAt - 'z') <= 0) && charAt != 'e' && charAt != 'E') {
                return i;
            }
            i++;
        }
        return i;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        for (int i = 0; i < pathDataNodeArr2.length; i++) {
            pathDataNodeArr[i].mType = pathDataNodeArr2[i].mType;
            for (int i2 = 0; i2 < pathDataNodeArr2[i].mParams.length; i2++) {
                pathDataNodeArr[i].mParams[i2] = pathDataNodeArr2[i].mParams[i2];
            }
        }
    }
}
