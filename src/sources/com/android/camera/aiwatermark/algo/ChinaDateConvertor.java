package com.android.camera.aiwatermark.algo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ChinaDateConvertor {
    private static final long[] lunarInfo = {19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432, 59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 103846, 38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 86390, 21168, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19415, 19152, 42192, 118966, 53840, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936, 44448};

    private ChinaDateConvertor() {
    }

    public static final long[] calElement(int i, int i2, int i3) {
        int i4;
        long[] jArr = new long[7];
        long offset = getOffset(i, i2, i3);
        jArr[5] = 40 + offset;
        jArr[4] = 14;
        int i5 = 1900;
        int i6 = 0;
        while (i5 < 2050 && offset > 0) {
            i6 = lYearDays(i5);
            offset -= (long) i6;
            jArr[4] = jArr[4] + 12;
            i5++;
        }
        if (offset < 0) {
            offset += (long) i6;
            i5--;
            jArr[4] = jArr[4] - 12;
        }
        jArr[0] = (long) i5;
        jArr[3] = (long) (i5 - 1864);
        int leapMonth = leapMonth(i5);
        jArr[6] = 0;
        long j = offset;
        int i7 = 1;
        while (i7 < 13 && j > 0) {
            if (leapMonth > 0 && i7 == leapMonth + 1 && jArr[6] == 0) {
                i7--;
                jArr[6] = 1;
                i4 = leapDays((int) jArr[0]);
            } else {
                i4 = monthDays((int) jArr[0], i7);
            }
            i6 = i4;
            if (jArr[6] == 1 && i7 == leapMonth + 1) {
                jArr[6] = 0;
            }
            j -= (long) i6;
            if (jArr[6] == 0) {
                jArr[4] = jArr[4] + 1;
            }
            i7++;
        }
        int i8 = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i8 == 0 && leapMonth > 0 && i7 == leapMonth + 1) {
            if (jArr[6] == 1) {
                jArr[6] = 0;
            } else {
                jArr[6] = 1;
                i7--;
                jArr[4] = jArr[4] - 1;
            }
        }
        if (i8 < 0) {
            j += (long) i6;
            i7--;
            jArr[4] = jArr[4] - 1;
        }
        jArr[1] = (long) i7;
        jArr[2] = j + 1;
        return jArr;
    }

    private static String formatData(long j) {
        String str = j < 10 ? "0" : "";
        return str + j;
    }

    public static final String getDate() {
        return new SimpleDateFormat("MMdd").format(new Date());
    }

    private static long getOffset(int i, int i2, int i3) {
        return (new GregorianCalendar(i, i2 - 1, i3).getTime().getTime() - new GregorianCalendar(1900, 0, 31).getTime().getTime()) / 86400000;
    }

    public static final boolean isChineseEve() {
        Calendar instance = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        long[] calElement = calElement(instance.get(1), instance.get(2) + 1, instance.get(5));
        if (calElement[1] == 12) {
            try {
                if (((int) calElement[2]) == monthDays((int) calElement[0], (int) calElement[1])) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private static final int lYearDays(int i) {
        int i2 = 348;
        for (int i3 = 32768; i3 > 8; i3 >>= 1) {
            if ((lunarInfo[i - 1900] & ((long) i3)) != 0) {
                i2++;
            }
        }
        return i2 + leapDays(i);
    }

    private static final int leapDays(int i) {
        if (leapMonth(i) != 0) {
            return (lunarInfo[i + -1900] & 65536) != 0 ? 30 : 29;
        }
        return 0;
    }

    private static final int leapMonth(int i) {
        return (int) (lunarInfo[i - 1900] & 15);
    }

    private static final int monthDays(int i, int i2) {
        return (((long) (65536 >> i2)) & lunarInfo[i + -1900]) == 0 ? 29 : 30;
    }

    public static String today() {
        Calendar instance = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        long[] calElement = calElement(instance.get(1), instance.get(2) + 1, instance.get(5));
        return formatData(calElement[1]) + formatData(calElement[2]);
    }
}
