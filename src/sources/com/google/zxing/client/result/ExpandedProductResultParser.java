package com.google.zxing.client.result;

import com.android.camera.data.data.runing.ComponentRunningShine;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.HashMap;

public final class ExpandedProductResultParser extends ResultParser {
    private static String findAIvalue(int i, String str) {
        if (str.charAt(i) != '(') {
            return null;
        }
        String substring = str.substring(i + 1);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == ')') {
                return sb.toString();
            }
            if (charAt < '0' || charAt > '9') {
                return null;
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    private static String findValue(int i, String str) {
        StringBuilder sb = new StringBuilder();
        String substring = str.substring(i);
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == '(') {
                if (findAIvalue(i2, substring) != null) {
                    break;
                }
                sb.append('(');
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0214  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x023b  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0261  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0265  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x026d  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0271  */
    @Override // com.google.zxing.client.result.ResultParser
    public ExpandedProductParsedResult parse(Result result) {
        char c2;
        ExpandedProductParsedResult expandedProductParsedResult = null;
        if (result.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        String massagedText = ResultParser.getMassagedText(result);
        HashMap hashMap = new HashMap();
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        String str11 = null;
        String str12 = null;
        String str13 = null;
        int i = 0;
        while (i < massagedText.length()) {
            String findAIvalue = findAIvalue(i, massagedText);
            if (findAIvalue == null) {
                return expandedProductParsedResult;
            }
            int length = i + findAIvalue.length() + 2;
            String findValue = findValue(length, massagedText);
            int length2 = length + findValue.length();
            int hashCode = findAIvalue.hashCode();
            if (hashCode != 1536) {
                if (hashCode != 1537) {
                    if (hashCode != 1567) {
                        if (hashCode != 1568) {
                            if (hashCode != 1570) {
                                if (hashCode != 1572) {
                                    if (hashCode != 1574) {
                                        switch (hashCode) {
                                            case 1567966:
                                                if (findAIvalue.equals("3100")) {
                                                    c2 = 7;
                                                    break;
                                                }
                                                break;
                                            case 1567967:
                                                if (findAIvalue.equals("3101")) {
                                                    c2 = '\b';
                                                    break;
                                                }
                                                break;
                                            case 1567968:
                                                if (findAIvalue.equals("3102")) {
                                                    c2 = '\t';
                                                    break;
                                                }
                                                break;
                                            case 1567969:
                                                if (findAIvalue.equals("3103")) {
                                                    c2 = '\n';
                                                    break;
                                                }
                                                break;
                                            case 1567970:
                                                if (findAIvalue.equals("3104")) {
                                                    c2 = 11;
                                                    break;
                                                }
                                                break;
                                            case 1567971:
                                                if (findAIvalue.equals("3105")) {
                                                    c2 = '\f';
                                                    break;
                                                }
                                                break;
                                            case 1567972:
                                                if (findAIvalue.equals("3106")) {
                                                    c2 = '\r';
                                                    break;
                                                }
                                                break;
                                            case 1567973:
                                                if (findAIvalue.equals("3107")) {
                                                    c2 = 14;
                                                    break;
                                                }
                                                break;
                                            case 1567974:
                                                if (findAIvalue.equals("3108")) {
                                                    c2 = 15;
                                                    break;
                                                }
                                                break;
                                            case 1567975:
                                                if (findAIvalue.equals("3109")) {
                                                    c2 = 16;
                                                    break;
                                                }
                                                break;
                                            default:
                                                switch (hashCode) {
                                                    case 1568927:
                                                        if (findAIvalue.equals("3200")) {
                                                            c2 = 17;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568928:
                                                        if (findAIvalue.equals("3201")) {
                                                            c2 = 18;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568929:
                                                        if (findAIvalue.equals("3202")) {
                                                            c2 = 19;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568930:
                                                        if (findAIvalue.equals("3203")) {
                                                            c2 = 20;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568931:
                                                        if (findAIvalue.equals("3204")) {
                                                            c2 = 21;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568932:
                                                        if (findAIvalue.equals("3205")) {
                                                            c2 = 22;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568933:
                                                        if (findAIvalue.equals("3206")) {
                                                            c2 = 23;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568934:
                                                        if (findAIvalue.equals("3207")) {
                                                            c2 = 24;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568935:
                                                        if (findAIvalue.equals("3208")) {
                                                            c2 = 25;
                                                            break;
                                                        }
                                                        break;
                                                    case 1568936:
                                                        if (findAIvalue.equals("3209")) {
                                                            c2 = 26;
                                                            break;
                                                        }
                                                        break;
                                                    default:
                                                        switch (hashCode) {
                                                            case 1575716:
                                                                if (findAIvalue.equals("3920")) {
                                                                    c2 = 27;
                                                                    break;
                                                                }
                                                                break;
                                                            case 1575717:
                                                                if (findAIvalue.equals("3921")) {
                                                                    c2 = 28;
                                                                    break;
                                                                }
                                                                break;
                                                            case 1575718:
                                                                if (findAIvalue.equals("3922")) {
                                                                    c2 = 29;
                                                                    break;
                                                                }
                                                                break;
                                                            case 1575719:
                                                                if (findAIvalue.equals("3923")) {
                                                                    c2 = 30;
                                                                    break;
                                                                }
                                                                break;
                                                            default:
                                                                switch (hashCode) {
                                                                    case 1575747:
                                                                        if (findAIvalue.equals("3930")) {
                                                                            c2 = 31;
                                                                            break;
                                                                        }
                                                                        break;
                                                                    case 1575748:
                                                                        if (findAIvalue.equals("3931")) {
                                                                            c2 = ' ';
                                                                            break;
                                                                        }
                                                                        break;
                                                                    case 1575749:
                                                                        if (findAIvalue.equals("3932")) {
                                                                            c2 = '!';
                                                                            break;
                                                                        }
                                                                        break;
                                                                    case 1575750:
                                                                        if (findAIvalue.equals("3933")) {
                                                                            c2 = '\"';
                                                                            break;
                                                                        }
                                                                        break;
                                                                }
                                                        }
                                                }
                                        }
                                        switch (c2) {
                                            case 0:
                                                i = length2;
                                                str2 = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 1:
                                                i = length2;
                                                str = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 2:
                                                i = length2;
                                                str3 = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 3:
                                                i = length2;
                                                str4 = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 4:
                                                i = length2;
                                                str5 = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 5:
                                                i = length2;
                                                str6 = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 6:
                                                i = length2;
                                                str7 = findValue;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 7:
                                            case '\b':
                                            case '\t':
                                            case '\n':
                                            case 11:
                                            case '\f':
                                            case '\r':
                                            case 14:
                                            case 15:
                                            case 16:
                                                str10 = findAIvalue.substring(3);
                                                str9 = ExpandedProductParsedResult.KILOGRAM;
                                                i = length2;
                                                str8 = findValue;
                                                str11 = str11;
                                                expandedProductParsedResult = null;
                                            case 17:
                                            case 18:
                                            case 19:
                                            case 20:
                                            case 21:
                                            case 22:
                                            case 23:
                                            case 24:
                                            case 25:
                                            case 26:
                                                str10 = findAIvalue.substring(3);
                                                str9 = ExpandedProductParsedResult.POUND;
                                                i = length2;
                                                str8 = findValue;
                                                str11 = str11;
                                                expandedProductParsedResult = null;
                                            case 27:
                                            case 28:
                                            case 29:
                                            case 30:
                                                str12 = findAIvalue.substring(3);
                                                i = length2;
                                                str11 = findValue;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            case 31:
                                            case ' ':
                                            case '!':
                                            case '\"':
                                                if (findValue.length() < 4) {
                                                    return null;
                                                }
                                                str11 = findValue.substring(3);
                                                str13 = findValue.substring(0, 3);
                                                str12 = findAIvalue.substring(3);
                                                i = length2;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                            default:
                                                hashMap.put(findAIvalue, findValue);
                                                i = length2;
                                                str11 = str11;
                                                str10 = str10;
                                                expandedProductParsedResult = null;
                                        }
                                    } else if (findAIvalue.equals("17")) {
                                        c2 = 6;
                                        switch (c2) {
                                        }
                                    }
                                } else if (findAIvalue.equals("15")) {
                                    c2 = 5;
                                    switch (c2) {
                                    }
                                }
                            } else if (findAIvalue.equals("13")) {
                                c2 = 4;
                                switch (c2) {
                                }
                            }
                        } else if (findAIvalue.equals(ComponentRunningShine.SHINE_LIVE_BEAUTY)) {
                            c2 = 3;
                            switch (c2) {
                            }
                        }
                    } else if (findAIvalue.equals("10")) {
                        c2 = 2;
                        switch (c2) {
                        }
                    }
                } else if (findAIvalue.equals("01")) {
                    c2 = 1;
                    switch (c2) {
                    }
                }
            } else if (findAIvalue.equals("00")) {
                c2 = 0;
                switch (c2) {
                }
            }
            c2 = 65535;
            switch (c2) {
            }
        }
        return new ExpandedProductParsedResult(massagedText, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, hashMap);
    }
}
