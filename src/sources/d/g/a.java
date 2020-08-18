package d.g;

import android.os.Build;
import android.util.Log;
import com.android.camera.CameraAppImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: FeatureParser */
public class a {
    private static HashMap<String, Integer> Ay = new HashMap<>();
    private static HashMap<String, Boolean> By = new HashMap<>();
    private static HashMap<String, String> Cy = new HashMap<>();
    private static HashMap<String, ArrayList<Integer>> Dy = new HashMap<>();
    private static HashMap<String, ArrayList<String>> Ey = new HashMap<>();
    private static HashMap<String, Float> Fy = new HashMap<>();
    private static final String TAG = "FeatureParser";
    public static final int TYPE_BOOL = 1;
    public static final int TYPE_FLOAT = 6;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_STRING = 3;
    private static final String qy = "device_features/";
    private static final String ry = "/system/etc/device_features";
    private static final String sy = "bool";
    private static final String ty = "integer";
    private static final String uy = "string";
    private static final String vy = "string-array";
    private static final String wy = "integer-array";
    private static final String xy = "item";
    public static final int yi = 4;
    private static final String yy = "float";
    public static final int zy = 5;

    static {
        read();
    }

    public static boolean e(String str, int i) {
        switch (i) {
            case 1:
                return By.containsKey(str);
            case 2:
                return Ay.containsKey(str);
            case 3:
                return Cy.containsKey(str);
            case 4:
                return Ey.containsKey(str);
            case 5:
                return Dy.containsKey(str);
            case 6:
                return Fy.containsKey(str);
            default:
                return false;
        }
    }

    public static boolean getBoolean(String str, boolean z) {
        Boolean bool = By.get(str);
        return bool != null ? bool.booleanValue() : z;
    }

    public static Float getFloat(String str, float f2) {
        Float f3 = Fy.get(str);
        if (f3 != null) {
            f2 = f3.floatValue();
        }
        return Float.valueOf(f2);
    }

    public static int[] getIntArray(String str) {
        ArrayList<Integer> arrayList = Dy.get(str);
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).intValue();
        }
        return iArr;
    }

    public static int getInteger(String str, int i) {
        Integer num = Ay.get(str);
        return num != null ? num.intValue() : i;
    }

    public static String getString(String str) {
        return Cy.get(str);
    }

    public static String[] getStringArray(String str) {
        ArrayList<String> arrayList = Ey.get(str);
        if (arrayList != null) {
            return (String[]) arrayList.toArray(new String[0]);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005c, code lost:
        android.util.Log.i(d.g.a.TAG, "can't find " + r4 + " in assets/" + d.g.a.qy + ",it may be in " + d.g.a.ry);
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01ae, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01af, code lost:
        r1 = r0;
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01b7, code lost:
        r0 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01ae A[ExcHandler: all (r0v6 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x0009] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01b3 A[SYNTHETIC, Splitter:B:83:0x01b3] */
    /* JADX WARNING: Removed duplicated region for block: B:88:? A[ExcHandler: XmlPullParserException (unused org.xmlpull.v1.XmlPullParserException), SYNTHETIC, Splitter:B:1:0x0009] */
    public static void read() {
        InputStream inputStream;
        Throwable th;
        String str;
        try {
            if ("cancro".equals(Build.DEVICE)) {
                str = Build.MODEL.startsWith("MI 3") ? "cancro_MI3.xml" : Build.MODEL.startsWith("MI 4") ? "cancro_MI4.xml" : null;
            } else {
                str = Build.DEVICE + ".xml";
            }
            InputStream inputStream2 = CameraAppImpl.getAndroidContext().getAssets().open(qy + str);
            if (inputStream2 == null) {
                try {
                    File file = new File(ry, str);
                    if (file.exists()) {
                        inputStream = new FileInputStream(file);
                    } else {
                        Log.e(TAG, "both assets/device_features/ and /system/etc/device_features don't exist " + str);
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                                return;
                            } catch (IOException unused) {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } catch (IOException unused2) {
                    inputStream = inputStream2;
                    if (inputStream == null) {
                    }
                    inputStream.close();
                } catch (XmlPullParserException unused3) {
                    inputStream = inputStream2;
                    if (inputStream == null) {
                    }
                    inputStream.close();
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                    if (inputStream != null) {
                    }
                    throw th;
                }
            } else {
                inputStream = inputStream2;
            }
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(inputStream, "UTF-8");
                String str2 = null;
                ArrayList<Integer> arrayList = null;
                ArrayList<String> arrayList2 = null;
                for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (newPullParser.getAttributeCount() > 0) {
                            str2 = newPullParser.getAttributeValue(0);
                        }
                        if (wy.equals(name)) {
                            arrayList = new ArrayList<>();
                        } else if (vy.equals(name)) {
                            arrayList2 = new ArrayList<>();
                        } else if (sy.equals(name)) {
                            By.put(str2, Boolean.valueOf(newPullParser.nextText()));
                        } else if (ty.equals(name)) {
                            Ay.put(str2, Integer.valueOf(newPullParser.nextText()));
                        } else if ("string".equals(name)) {
                            Cy.put(str2, newPullParser.nextText());
                        } else if (yy.equals(name)) {
                            Fy.put(str2, Float.valueOf(Float.parseFloat(newPullParser.nextText())));
                        } else if (xy.equals(name)) {
                            if (arrayList != null) {
                                arrayList.add(Integer.valueOf(newPullParser.nextText()));
                            } else if (arrayList2 != null) {
                                arrayList2.add(newPullParser.nextText());
                            }
                        }
                    } else if (eventType == 3) {
                        String name2 = newPullParser.getName();
                        if (wy.equals(name2)) {
                            Dy.put(str2, arrayList);
                            arrayList = null;
                        } else if (vy.equals(name2)) {
                            Ey.put(str2, arrayList2);
                            arrayList2 = null;
                        }
                    }
                }
                if (inputStream == null) {
                    return;
                }
            } catch (IOException unused4) {
                if (inputStream == null) {
                    return;
                }
                inputStream.close();
            } catch (XmlPullParserException unused5) {
                if (inputStream == null) {
                    return;
                }
                inputStream.close();
            } catch (Throwable th3) {
                th = th3;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused6) {
                    }
                }
                throw th;
            }
        } catch (IOException unused7) {
            inputStream = null;
            if (inputStream == null) {
            }
            inputStream.close();
        } catch (XmlPullParserException unused8) {
        } catch (Throwable th4) {
        }
        try {
            inputStream.close();
        } catch (IOException unused9) {
        }
    }
}
