package com.android.camera.sticker;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.camera.log.Log;
import com.sensetime.stmobile.STMobileAuthentificationNative;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class STLicenseUtils {
    private static final String LICENSE_NAME = "SenseME.lic";
    private static final String PREF_ACTIVATE_CODE = "activate_code";
    private static final String PREF_ACTIVATE_CODE_FILE = "activate_code_file";
    private static final String TAG = "STLicenseUtils";

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004d A[SYNTHETIC, Splitter:B:26:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f9 A[SYNTHETIC, Splitter:B:59:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0103 A[SYNTHETIC, Splitter:B:64:0x0103] */
    public static boolean checkLicense(Context context) {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            inputStreamReader = new InputStreamReader(context.getResources().getAssets().open(LICENSE_NAME));
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb.append(readLine);
                            sb.append("\n");
                        } else {
                            try {
                                break;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (IOException e3) {
                        e = e3;
                        try {
                            e.printStackTrace();
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (sb.toString().length() != 0) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader2 = bufferedReader;
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                }
                inputStreamReader.close();
                try {
                    bufferedReader.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            } catch (IOException e8) {
                e = e8;
                bufferedReader = null;
                e.printStackTrace();
                if (inputStreamReader != null) {
                }
                if (bufferedReader != null) {
                }
                if (sb.toString().length() != 0) {
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStreamReader != null) {
                }
                if (bufferedReader2 != null) {
                }
                throw th;
            }
        } catch (IOException e9) {
            e = e9;
            inputStreamReader = null;
            bufferedReader = null;
            e.printStackTrace();
            if (inputStreamReader != null) {
            }
            if (bufferedReader != null) {
            }
            if (sb.toString().length() != 0) {
            }
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = null;
            if (inputStreamReader != null) {
            }
            if (bufferedReader2 != null) {
            }
            throw th;
        }
        if (sb.toString().length() != 0) {
            Log.e(TAG, "read license data error");
            return false;
        }
        String sb2 = sb.toString();
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_ACTIVATE_CODE_FILE, 0);
        String string = sharedPreferences.getString(PREF_ACTIVATE_CODE, null);
        if (string == null || STMobileAuthentificationNative.checkActiveCodeFromBuffer(sb2, sb2.length(), string) != 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("activeCode: ");
            sb3.append(string == null);
            Log.e(TAG, sb3.toString());
            String generateActiveCodeFromBuffer = STMobileAuthentificationNative.generateActiveCodeFromBuffer(sb2, sb2.length());
            if (generateActiveCodeFromBuffer == null || generateActiveCodeFromBuffer.length() == 0) {
                Log.e(TAG, "generate license error: " + ((Object) -1));
                return false;
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(PREF_ACTIVATE_CODE, generateActiveCodeFromBuffer);
            edit.commit();
            return true;
        }
        Log.e(TAG, "activeCode: " + string);
        return true;
    }
}
