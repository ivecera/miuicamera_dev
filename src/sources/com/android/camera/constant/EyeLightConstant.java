package com.android.camera.constant;

import com.android.camera.R;

public class EyeLightConstant {
    public static final String ANGEL = "5";
    public static final String COMIC = "1";
    public static final String CRESCENT = "4";
    public static final String NATURE = "0";
    public static final String OFF = "-1";
    public static final String RAINBOW = "3";
    public static final String STAR = "2";
    public static final String WINDOW = "6";

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0061 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0062 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0066 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0072 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0076 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007e A[RETURN] */
    public static int getDrawable(String str) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode != 1444) {
            switch (hashCode) {
                case 48:
                    if (str.equals("0")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 49:
                    if (str.equals("1")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 52:
                    if (str.equals("4")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 53:
                    if (str.equals("5")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 54:
                    if (str.equals("6")) {
                        c2 = 7;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    return R.drawable.ic_eye_light_off;
                case 1:
                    return R.drawable.ic_eye_light_nature;
                case 2:
                    return R.drawable.ic_eye_light_comic;
                case 3:
                    return R.drawable.ic_eye_light_star;
                case 4:
                    return R.drawable.ic_eye_light_rainbow;
                case 5:
                    return R.drawable.ic_eye_light_crescent;
                case 6:
                    return R.drawable.ic_eye_light_angel;
                case 7:
                    return R.drawable.ic_eye_light_window;
                default:
                    return 0;
            }
        } else if (str.equals("-1")) {
            c2 = 0;
            switch (c2) {
            }
        }
        c2 = 65535;
        switch (c2) {
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0061 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0062 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0066 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0072 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0076 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007e A[RETURN] */
    public static int getString(String str) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode != 1444) {
            switch (hashCode) {
                case 48:
                    if (str.equals("0")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 49:
                    if (str.equals("1")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 52:
                    if (str.equals("4")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 53:
                    if (str.equals("5")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 54:
                    if (str.equals("6")) {
                        c2 = 7;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    return R.string.eye_light_off;
                case 1:
                    return R.string.eye_light_nature;
                case 2:
                    return R.string.eye_light_comic;
                case 3:
                    return R.string.eye_light_star;
                case 4:
                    return R.string.eye_light_rainbow;
                case 5:
                    return R.string.eye_light_crescent;
                case 6:
                    return R.string.eye_light_angel;
                case 7:
                    return R.string.eye_light_window;
                default:
                    return 0;
            }
        } else if (str.equals("-1")) {
            c2 = 0;
            switch (c2) {
            }
        }
        c2 = 65535;
        switch (c2) {
        }
    }
}
