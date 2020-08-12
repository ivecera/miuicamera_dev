package com.android.camera.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class AutoFocus {
    public static final String LEGACY_AUTO = "auto";
    public static final String LEGACY_CONTINUOUS_PICTURE = "continuous-picture";
    public static final String LEGACY_CONTINUOUS_VIDEO = "continuous-video";
    public static final String LEGACY_EDOF = "edof";
    public static final String LEGACY_MACRO = "macro";
    public static final String LEGACY_MANUAL = "manual";

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusMode {
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int convertToFocusMode(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1081415738:
                if (str.equals("manual")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -194628547:
                if (str.equals(LEGACY_CONTINUOUS_VIDEO)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 3005871:
                if (str.equals("auto")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 3108534:
                if (str.equals(LEGACY_EDOF)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 103652300:
                if (str.equals("macro")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 910005312:
                if (str.equals(LEGACY_CONTINUOUS_PICTURE)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        if (c2 == 0) {
            return 1;
        }
        if (c2 == 1) {
            return 2;
        }
        if (c2 == 2) {
            return 3;
        }
        if (c2 == 3) {
            return 4;
        }
        if (c2 == 4) {
            return 5;
        }
        if (c2 != 5) {
        }
        return 0;
    }

    public static List<String> convertToLegacyFocusModes(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            if (i == 0) {
                arrayList.add("manual");
            } else if (i == 1) {
                arrayList.add("auto");
            } else if (i == 2) {
                arrayList.add("macro");
            } else if (i == 3) {
                arrayList.add(LEGACY_CONTINUOUS_VIDEO);
            } else if (i == 4) {
                arrayList.add(LEGACY_CONTINUOUS_PICTURE);
            } else if (i == 5) {
                arrayList.add(LEGACY_EDOF);
            }
        }
        return arrayList;
    }
}
