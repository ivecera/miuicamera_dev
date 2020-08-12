package com.arcsoft.avatar.emoticon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Size;
import com.android.camera.module.impl.component.FileUtils;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import com.arcsoft.avatar.emoticon.EmoInfo;
import com.arcsoft.avatar.recoder.MediaManager;
import com.arcsoft.avatar.recoder.RecordingListener;
import com.arcsoft.avatar.util.ASVLOFFSCREEN;
import com.arcsoft.avatar.util.AsvloffscreenUtil;
import com.arcsoft.avatar.util.LOG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class AvatarEmoManager {
    private static final String A = "RIGHT_EYE_ROTATE";
    private static final String B = "TONGUE_STATE";
    private static final String C = "MORPH_SIZE";
    private static final String D = "MORPH_VALUE";
    private static final String E = "KEY_X";
    public static final int EMO_GIF_MODE = 1;
    public static final int EMO_VIDEO_MODE = 0;
    private static final String F = "KEY_Y";
    private static final String G = "KEY_COLOR";
    private static final String H = "BACK_GROUND";
    private static final String I = "THUMB_INDEX";
    private static final String J = "SCALE_LEVEL";

    /* renamed from: a  reason: collision with root package name */
    private static final String f60a = "AvatarEmoManager";
    private static final String p = "emo";
    private static final String q = "emo_map.xml";
    private static final String r = "foreground";
    private static final String s = "background";
    private static final String t = "thumbnail";
    private static final String u = "ANIM_NAME";
    private static final String v = "KEY_SIZE";
    private static final String w = "KEY_INDEX";
    private static final String x = "KEY_TIME";
    private static final String y = "HEAD_ROTATE";
    private static final String z = "LEFT_EYE_ROTATE";

    /* renamed from: b  reason: collision with root package name */
    private AvatarEmoResCallback f61b;

    /* renamed from: c  reason: collision with root package name */
    private AvatarEngine f62c = null;

    /* renamed from: d  reason: collision with root package name */
    private int[] f63d = new int[1];

    /* renamed from: e  reason: collision with root package name */
    private boolean f64e = false;

    /* renamed from: f  reason: collision with root package name */
    private MediaManager f65f;
    private EGLContext g;
    private int h = 0;
    private ArrayList<EmoInfo> i = null;
    private volatile boolean j = false;
    private int k = -1;
    private ASVLOFFSCREEN l = null;
    private byte[] m = null;
    private Size n = null;
    private int o = -1;

    public interface AvatarEmoResCallback {
        void onFrameRefresh(EmoInfo.EmoExtraInfo emoExtraInfo);

        void onMakeMediaEnd();
    }

    public AvatarEmoManager(AvatarEngine avatarEngine, String str, int i2, AvatarEmoResCallback avatarEmoResCallback) {
        this.f61b = avatarEmoResCallback;
        this.f62c = avatarEngine;
        this.g = EGL14.EGL_NO_CONTEXT;
        this.i = new ArrayList<>();
        this.k = i2 + 1;
        LOG.d(f60a, "floderPath = " + str);
        LOG.d(f60a, "faceColorId = " + i2);
        ArrayList<String> d2 = d(str + "/" + q);
        if (d2 != null && d2.size() > 0) {
            Iterator<String> it = d2.iterator();
            while (it.hasNext()) {
                String next = it.next();
                String str2 = str + "/" + next;
                LOG.d(f60a, "file = " + str2);
                File file = new File(str2);
                if (file.exists() && file.isDirectory()) {
                    EmoInfo a2 = a(str2 + "/" + next + ".txt", str2);
                    if (a2 != null) {
                        this.i.add(a2);
                    }
                }
            }
        }
    }

    private int a() {
        return this.h;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:132:0x038d, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x038e, code lost:
        r16 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0394, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0395, code lost:
        r3 = r7;
        r4 = r8;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0399, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x039a, code lost:
        r3 = r7;
        r4 = r8;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x039e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x039f, code lost:
        r3 = r7;
        r4 = r8;
        r1 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x038d A[ExcHandler: NullPointerException (e java.lang.NullPointerException), Splitter:B:1:0x0010] */
    private EmoInfo a(String str, String str2) {
        String str3;
        String str4;
        FileNotFoundException fileNotFoundException;
        String str5;
        String str6;
        IOException iOException;
        String str7;
        String str8;
        NumberFormatException numberFormatException;
        String str9;
        String str10;
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader;
        FileInputStream fileInputStream;
        String[] split;
        String str11 = "thumbnail";
        String str12 = "error = ";
        String str13 = f60a;
        try {
            LOG.d(str13, "infoFilePath = " + str + "\n parentPath = " + str2);
            FileInputStream fileInputStream2 = new FileInputStream(str);
            InputStreamReader inputStreamReader2 = new InputStreamReader(fileInputStream2);
            BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);
            String str14 = "";
            String str15 = str14;
            EmoInfo emoInfo = null;
            EmoInfo.EmoExtraInfo emoExtraInfo = null;
            while (true) {
                String readLine = bufferedReader2.readLine();
                if (readLine != null) {
                    StringBuilder sb = new StringBuilder();
                    String str16 = str12;
                    try {
                        sb.append("read_file line -> ");
                        sb.append(readLine);
                        LOG.d(str13, sb.toString());
                        if (!TextUtils.isEmpty(readLine)) {
                            String trim = readLine.trim();
                            str9 = str13;
                            fileInputStream = fileInputStream2;
                            inputStreamReader = inputStreamReader2;
                            bufferedReader = bufferedReader2;
                            if (trim.equals(u)) {
                                try {
                                    EmoInfo emoInfo2 = new EmoInfo();
                                    emoInfo2.setFilePath(str2);
                                    emoInfo2.setThumbForGroundPath(str2 + "/" + r + "/" + str11 + FileUtils.FILTER_FILE_SUFFIX);
                                    emoInfo2.setThumbBgGroundPath(str2 + "/" + s + "/" + str11 + FileUtils.FILTER_FILE_SUFFIX);
                                    str10 = str11;
                                    emoInfo = emoInfo2;
                                } catch (FileNotFoundException e2) {
                                    fileNotFoundException = e2;
                                    str4 = str16;
                                    str3 = str9;
                                    LOG.d(str3, str4 + fileNotFoundException.toString());
                                    fileNotFoundException.printStackTrace();
                                    return null;
                                } catch (IOException e3) {
                                    iOException = e3;
                                    str6 = str16;
                                    str5 = str9;
                                    LOG.d(str5, str6 + iOException.toString());
                                    iOException.printStackTrace();
                                    return null;
                                } catch (NumberFormatException e4) {
                                    numberFormatException = e4;
                                    str8 = str16;
                                    str7 = str9;
                                    LOG.d(str7, str8 + numberFormatException.toString());
                                    numberFormatException.printStackTrace();
                                    return null;
                                } catch (NullPointerException e5) {
                                    e = e5;
                                    LOG.d(str9, str16 + e.toString());
                                    e.printStackTrace();
                                    return null;
                                }
                            } else {
                                if (trim.equals(w)) {
                                    emoInfo.getClass();
                                    emoExtraInfo = new EmoInfo.EmoExtraInfo();
                                } else if (str15.equals(u)) {
                                    emoInfo.setEmoName(trim);
                                } else if (str15.equals(v)) {
                                    emoInfo.setEmoMaxCount(Integer.parseInt(trim));
                                } else if (str15.equals(E)) {
                                    emoInfo.setTranslationX(Float.parseFloat(trim));
                                } else if (str15.equals(F)) {
                                    emoInfo.setTranslationY(Float.parseFloat(trim));
                                } else {
                                    str10 = str11;
                                    boolean z2 = true;
                                    if (str15.equals(G)) {
                                        if (Integer.parseInt(trim) != 1) {
                                            z2 = false;
                                        }
                                        emoInfo.needFaceColor(z2);
                                    } else if (str15.equals(H)) {
                                        if (Integer.parseInt(trim) != 1) {
                                            z2 = false;
                                        }
                                        emoInfo.setMultipleBG(z2);
                                    } else if (str15.equals(I)) {
                                        emoInfo.setThumbFrameIndex(Integer.parseInt(trim));
                                    } else if (str15.equals(J)) {
                                        emoInfo.setScaleLevel(Float.parseFloat(trim));
                                    } else if (str15.equals(w)) {
                                        emoExtraInfo.index = Integer.parseInt(trim);
                                        String str17 = str2 + "/" + r + "/" + (emoExtraInfo.index + 1) + FileUtils.FILTER_FILE_SUFFIX;
                                        if (emoInfo.isNeedFaceColor()) {
                                            str17 = str2 + "/" + r + "/skin_" + this.k + "/" + (emoExtraInfo.index + 1) + FileUtils.FILTER_FILE_SUFFIX;
                                        }
                                        emoExtraInfo.foreGroundPath = str17;
                                        if (emoInfo.getEmoImageSize() == null) {
                                            emoInfo.setEmoImageSize(c(str17));
                                        }
                                        if (emoInfo.isMultipleBG()) {
                                            emoExtraInfo.backGroundPath = str2 + "/" + s + "/" + (emoExtraInfo.index + 1) + FileUtils.FILTER_FILE_SUFFIX;
                                        } else if (this.l == null) {
                                            String str18 = str2 + "/" + s + "/" + (emoExtraInfo.index + 1) + FileUtils.FILTER_FILE_SUFFIX;
                                            this.l = b(str18);
                                            emoExtraInfo.backGroundPath = str18;
                                        }
                                    } else if (str15.equals(x)) {
                                        emoExtraInfo.time = Float.parseFloat(trim);
                                    } else if (str15.equals(y)) {
                                        String[] split2 = trim.split(",");
                                        float[] fArr = new float[split2.length];
                                        for (int i2 = 0; i2 < split2.length; i2++) {
                                            fArr[i2] = Float.parseFloat(split2[i2]);
                                        }
                                        emoExtraInfo.processInfo.setOrientations(fArr);
                                    } else if (str15.equals(z)) {
                                        String[] split3 = trim.split(",");
                                        float[] fArr2 = new float[split3.length];
                                        for (int i3 = 0; i3 < split3.length; i3++) {
                                            fArr2[i3] = Float.parseFloat(split3[i3]);
                                        }
                                        emoExtraInfo.processInfo.setOrientationLeftEyes(fArr2);
                                    } else if (str15.equals(A)) {
                                        String[] split4 = trim.split(",");
                                        float[] fArr3 = new float[split4.length];
                                        for (int i4 = 0; i4 < split4.length; i4++) {
                                            fArr3[i4] = Float.parseFloat(split4[i4]);
                                        }
                                        emoExtraInfo.processInfo.setOrientationRightEyes(fArr3);
                                    } else if (str15.equals(B)) {
                                        emoExtraInfo.processInfo.setTongueStatus(Integer.parseInt(trim));
                                    } else if (!str15.equals(C) && str15.equals(D)) {
                                        float[] fArr4 = new float[AvatarConfig.ASAvatarProcessInfo.getMaxExpressNum()];
                                        for (String str19 : trim.split(";")) {
                                            String[] split5 = str19.split(",");
                                            fArr4[Integer.parseInt(split5[1])] = Float.parseFloat(split5[0]);
                                        }
                                        emoExtraInfo.processInfo.setExpWeights(fArr4);
                                        emoInfo.getEmoExtraInfoList().add(emoExtraInfo);
                                    }
                                }
                                str10 = str11;
                            }
                            str14 = str14;
                            str15 = trim;
                        } else {
                            inputStreamReader = inputStreamReader2;
                            str10 = str11;
                            str9 = str13;
                            fileInputStream = fileInputStream2;
                            bufferedReader = bufferedReader2;
                            if (str15.equals(D)) {
                                emoExtraInfo.processInfo.setExpWeights(new float[AvatarConfig.ASAvatarProcessInfo.getMaxExpressNum()]);
                                emoInfo.getEmoExtraInfoList().add(emoExtraInfo);
                                str14 = str14;
                                str15 = str14;
                            } else {
                                str14 = str14;
                            }
                        }
                        str12 = str16;
                        str13 = str9;
                        fileInputStream2 = fileInputStream;
                        inputStreamReader2 = inputStreamReader;
                        bufferedReader2 = bufferedReader;
                        str11 = str10;
                    } catch (FileNotFoundException e6) {
                        fileNotFoundException = e6;
                        str3 = str13;
                        str4 = str16;
                        LOG.d(str3, str4 + fileNotFoundException.toString());
                        fileNotFoundException.printStackTrace();
                        return null;
                    } catch (IOException e7) {
                        iOException = e7;
                        str5 = str13;
                        str6 = str16;
                        LOG.d(str5, str6 + iOException.toString());
                        iOException.printStackTrace();
                        return null;
                    } catch (NumberFormatException e8) {
                        numberFormatException = e8;
                        str7 = str13;
                        str8 = str16;
                        LOG.d(str7, str8 + numberFormatException.toString());
                        numberFormatException.printStackTrace();
                        return null;
                    } catch (NullPointerException e9) {
                        e = e9;
                        str9 = str13;
                        LOG.d(str9, str16 + e.toString());
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    bufferedReader2.close();
                    inputStreamReader2.close();
                    fileInputStream2.close();
                    return emoInfo;
                }
            }
        } catch (FileNotFoundException e10) {
            fileNotFoundException = e10;
            str4 = str12;
            str3 = str13;
            LOG.d(str3, str4 + fileNotFoundException.toString());
            fileNotFoundException.printStackTrace();
            return null;
        } catch (IOException e11) {
            iOException = e11;
            str6 = str12;
            str5 = str13;
            LOG.d(str5, str6 + iOException.toString());
            iOException.printStackTrace();
            return null;
        } catch (NumberFormatException e12) {
            numberFormatException = e12;
            str8 = str12;
            str7 = str13;
            LOG.d(str7, str8 + numberFormatException.toString());
            numberFormatException.printStackTrace();
            return null;
        } catch (NullPointerException e13) {
        }
    }

    private ASVLOFFSCREEN a(String str) {
        LOG.d(f60a, "getBackGroundInfo -> " + str);
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        ASVLOFFSCREEN buildRGBA = AsvloffscreenUtil.buildRGBA(decodeFile);
        if (decodeFile != null) {
            decodeFile.recycle();
        }
        return buildRGBA;
    }

    private void a(int i2) {
        LOG.d(f60a, "mode -> " + i2);
        this.h = i2;
    }

    private ASVLOFFSCREEN b(String str) {
        LOG.d(f60a, "getRGBAInfo -> " + str);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        byte[] bArr = new byte[(i2 * i3 * 4)];
        this.f62c.readRGBA(str, i2, i3, bArr);
        return new ASVLOFFSCREEN(i2, i3, bArr);
    }

    private void b(int i2) {
        MediaManager mediaManager;
        if (i2 > 0 && (mediaManager = this.f65f) != null && this.f64e) {
            mediaManager.drawSurfaceWithTextureId(i2);
        }
    }

    private Size c(String str) {
        LOG.d(f60a, "getBitmapSize -> : " + str);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        options.inJustDecodeBounds = true;
        return new Size(decodeFile.getWidth(), decodeFile.getHeight());
    }

    private void c(int i2) {
        this.f62c.setTongueAnimationParam(new AvatarConfig.ASAvatarTongueAnimationParam(i2, i2 > 0 ? 1.0f : 0.0f, i2 > 0 ? 1.0f : 0.0f, i2 > 0 ? 1 : 0, i2 > 0 ? 1.0f : 0.0f));
    }

    private ArrayList<String> d(String str) {
        String str2;
        try {
            LOG.d(f60a, "getFileNameList = " + str);
            ArrayList<String> arrayList = new ArrayList<>();
            FileInputStream fileInputStream = new FileInputStream(str);
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(fileInputStream, "UTF-8");
            String str3 = null;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        str2 = newPullParser.getName();
                    } else if (eventType == 3) {
                        str2 = newPullParser.getName();
                    } else if (eventType == 4) {
                        if (str3.equals(p)) {
                            arrayList.add(newPullParser.getText());
                        }
                    }
                    str3 = str2;
                }
            }
            return arrayList;
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
            return null;
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private boolean e(String str) {
        try {
            File file = new File(str);
            if (file.isDirectory()) {
                return false;
            }
            return file.exists();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void emoGLRender(EmoInfo.EmoExtraInfo emoExtraInfo) {
        int[] iArr;
        if (this.f62c != null) {
            if (this.h == 1) {
                int width = this.n.getWidth() * this.n.getHeight() * 4;
                byte[] bArr = this.m;
                if (bArr == null || bArr.length != width) {
                    this.m = new byte[width];
                }
            }
            AvatarEngine avatarEngine = this.f62c;
            String str = emoExtraInfo.foreGroundPath;
            ASVLOFFSCREEN asvloffscreen = emoExtraInfo.asBackGround;
            if (asvloffscreen == null) {
                asvloffscreen = this.l;
            }
            avatarEngine.renderWithBackground(str, asvloffscreen, 0, false, 0, this.n.getWidth() * 2, this.n.getHeight() * 2, 0, false, this.f63d, this.m);
            if (this.h == 0 && (iArr = this.f63d) != null) {
                b(iArr[0]);
            }
            emoExtraInfo.asForeGround = null;
            emoExtraInfo.asBackGround = null;
        }
    }

    public void emoProcess(EmoInfo emoInfo) {
        if (this.f62c != null) {
            float[] fArr = new float[3];
            fArr[0] = emoInfo.getTranslationX();
            fArr[1] = emoInfo.getTranslationY();
            this.f62c.setRenderScene3F(false, emoInfo.getScaleLevel(), fArr);
        }
        this.n = new Size(emoInfo.getEmoImageSize().getWidth(), emoInfo.getEmoImageSize().getHeight());
        Iterator<EmoInfo.EmoExtraInfo> it = emoInfo.getEmoExtraInfoList().iterator();
        long j2 = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            EmoInfo.EmoExtraInfo next = it.next();
            if (this.j) {
                LOG.d(f60a, "process_render -> emoProcess release & break");
                break;
            }
            int i2 = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
            if (i2 > 0 && this.h == 0) {
                if (i2 < 0) {
                    j2 = 0;
                }
                SystemClock.sleep(j2);
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (emoInfo.isMultipleBG()) {
                next.asBackGround = a(next.backGroundPath);
            }
            if (this.o != next.processInfo.getTongueStatus()) {
                LOG.d(f60a, "mTongueState = " + this.o + ", info tong = " + next.processInfo.getTongueStatus());
                c(next.processInfo.getTongueStatus());
                this.o = next.processInfo.getTongueStatus();
            }
            this.f62c.setProcessInfo(next.processInfo);
            j2 = currentTimeMillis + (40 - System.currentTimeMillis());
            LOG.d(f60a, "sleepTime = " + j2);
            AvatarEmoResCallback avatarEmoResCallback = this.f61b;
            if (avatarEmoResCallback != null) {
                avatarEmoResCallback.onFrameRefresh(next);
            }
        }
        AvatarEmoResCallback avatarEmoResCallback2 = this.f61b;
        if (avatarEmoResCallback2 != null) {
            avatarEmoResCallback2.onMakeMediaEnd();
        }
    }

    public ArrayList<EmoInfo> getEmoList() {
        return this.i;
    }

    public int getFaceColorId() {
        return this.k;
    }

    public byte[] getImageData(EmoInfo emoInfo, int i2, int i3) {
        EmoInfo.EmoExtraInfo emoExtraInfo;
        if (this.f62c == null || emoInfo == null || i2 <= 0 || i3 <= 0 || emoInfo.getThumbFrameIndex() > emoInfo.getEmoMaxCount() || (emoExtraInfo = emoInfo.getEmoExtraInfoList().get(emoInfo.getThumbFrameIndex())) == null) {
            return null;
        }
        float[] fArr = new float[3];
        fArr[0] = emoInfo.getTranslationX();
        fArr[1] = emoInfo.getTranslationY();
        this.f62c.setRenderScene3F(false, emoInfo.getScaleLevel(), fArr);
        byte[] bArr = new byte[(i2 * i3 * 4)];
        if (emoInfo.isMultipleBG()) {
            emoExtraInfo.asBackGround = a(e(emoInfo.getThumbBgGroundPath()) ? emoInfo.getThumbBgGroundPath() : emoExtraInfo.backGroundPath);
        }
        c(emoExtraInfo.processInfo.getTongueStatus());
        this.f62c.setProcessInfo(emoExtraInfo.processInfo);
        AvatarEngine avatarEngine = this.f62c;
        String thumbForGroundPath = e(emoInfo.getThumbForGroundPath()) ? emoInfo.getThumbForGroundPath() : emoExtraInfo.foreGroundPath;
        ASVLOFFSCREEN asvloffscreen = emoExtraInfo.asBackGround;
        if (asvloffscreen == null) {
            asvloffscreen = this.l;
        }
        avatarEngine.renderWithBackground(thumbForGroundPath, asvloffscreen, 0, false, 0, i2, i3, 0, false, this.f63d, bArr);
        emoExtraInfo.asBackGround = null;
        return bArr;
    }

    public boolean isRelease() {
        return this.j;
    }

    public void release() {
        LOG.d(f60a, "-> AvatarEmoManager release");
        this.j = true;
    }

    public boolean renderEmoThumb(EmoInfo emoInfo, int i2, int i3) {
        EmoInfo.EmoExtraInfo emoExtraInfo;
        if (this.f62c == null || emoInfo == null || i2 <= 0 || i3 <= 0 || emoInfo.getThumbFrameIndex() > emoInfo.getEmoMaxCount() || (emoExtraInfo = emoInfo.getEmoExtraInfoList().get(emoInfo.getThumbFrameIndex())) == null) {
            return false;
        }
        float[] fArr = new float[3];
        fArr[0] = emoInfo.getTranslationX();
        fArr[1] = emoInfo.getTranslationY();
        this.f62c.setRenderScene3F(false, emoInfo.getScaleLevel(), fArr);
        byte[] bArr = new byte[(i2 * i3 * 4)];
        if (emoInfo.isMultipleBG()) {
            emoExtraInfo.asBackGround = a(e(emoInfo.getThumbBgGroundPath()) ? emoInfo.getThumbBgGroundPath() : emoExtraInfo.backGroundPath);
        }
        c(emoExtraInfo.processInfo.getTongueStatus());
        this.f62c.setProcessInfo(emoExtraInfo.processInfo);
        AvatarEngine avatarEngine = this.f62c;
        String thumbForGroundPath = e(emoInfo.getThumbForGroundPath()) ? emoInfo.getThumbForGroundPath() : emoExtraInfo.foreGroundPath;
        ASVLOFFSCREEN asvloffscreen = emoExtraInfo.asBackGround;
        if (asvloffscreen == null) {
            asvloffscreen = this.l;
        }
        avatarEngine.renderWithBackground(thumbForGroundPath, asvloffscreen, 0, false, 0, i2, i3, 0, false, this.f63d, bArr);
        emoInfo.setThumbnailData(bArr);
        emoExtraInfo.asBackGround = null;
        return true;
    }

    public ByteBuffer renderImageData(EmoInfo emoInfo, int i2, int i3) {
        EmoInfo.EmoExtraInfo emoExtraInfo;
        if (this.f62c == null || emoInfo == null || i2 <= 0 || i3 <= 0 || emoInfo.getThumbFrameIndex() > emoInfo.getEmoMaxCount() || (emoExtraInfo = emoInfo.getEmoExtraInfoList().get(emoInfo.getThumbFrameIndex())) == null) {
            return null;
        }
        float[] fArr = new float[3];
        fArr[0] = emoInfo.getTranslationX();
        fArr[1] = emoInfo.getTranslationY();
        this.f62c.setRenderScene3F(false, emoInfo.getScaleLevel(), fArr);
        if (emoInfo.isMultipleBG()) {
            emoExtraInfo.asBackGround = a(e(emoInfo.getThumbBgGroundPath()) ? emoInfo.getThumbBgGroundPath() : emoExtraInfo.backGroundPath);
        }
        c(emoExtraInfo.processInfo.getTongueStatus());
        this.f62c.setProcessInfo(emoExtraInfo.processInfo);
        AvatarEngine avatarEngine = this.f62c;
        String thumbForGroundPath = e(emoInfo.getThumbForGroundPath()) ? emoInfo.getThumbForGroundPath() : emoExtraInfo.foreGroundPath;
        ASVLOFFSCREEN asvloffscreen = emoExtraInfo.asBackGround;
        if (asvloffscreen == null) {
            asvloffscreen = this.l;
        }
        avatarEngine.renderWithBackground(thumbForGroundPath, asvloffscreen, 0, false, 0, i2 * 2, i3 * 2, 0, false, this.f63d, null);
        this.f62c.renderBackgroundWithTexture(this.f63d[0], 0, false);
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i2 * i3 * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, i2, i3, 6408, 5121, allocateDirect);
        emoExtraInfo.asBackGround = null;
        return allocateDirect;
    }

    public void reset() {
        AvatarEngine avatarEngine = this.f62c;
        if (avatarEngine != null) {
            avatarEngine.setRenderScene(false, 1.0f);
        }
        this.o = -1;
    }

    public void resumeRecording() {
        MediaManager mediaManager = this.f65f;
        if (mediaManager != null && this.f64e) {
            mediaManager.resumeRecording();
        }
    }

    public void setEmoList(ArrayList<EmoInfo> arrayList) {
        this.i = arrayList;
    }

    public void setFaceColorId(int i2) {
        if (i2 != this.k) {
            this.k = i2 + 1;
            ArrayList<EmoInfo> arrayList = this.i;
            if (arrayList != null) {
                Iterator<EmoInfo> it = arrayList.iterator();
                while (it.hasNext()) {
                    EmoInfo next = it.next();
                    if (next.isNeedFaceColor()) {
                        Iterator<EmoInfo.EmoExtraInfo> it2 = next.getEmoExtraInfoList().iterator();
                        while (it2.hasNext()) {
                            EmoInfo.EmoExtraInfo next2 = it2.next();
                            next2.foreGroundPath = next.getFilePath() + "/" + r + "/skin_" + this.k + "/" + (next2.index + 1) + FileUtils.FILTER_FILE_SUFFIX;
                            StringBuilder sb = new StringBuilder();
                            sb.append("setFaceColorId -> ");
                            sb.append(next2.foreGroundPath);
                            LOG.d(f60a, sb.toString());
                        }
                    }
                }
            }
        }
    }

    public void startRecording(@NonNull FileDescriptor fileDescriptor, int i2, @NonNull int i3, @NonNull int i4, int i5, String str) {
        if (i3 != 0 && i4 != 0 && fileDescriptor != null) {
            if (i3 % 2 != 0) {
                i3++;
            }
            if (i4 % 2 != 0) {
                i4++;
            }
            if (this.f65f != null) {
                throw new RuntimeException("Recording has been started already.");
            } else if (i2 == 0 || 90 == i2 || 180 == i2 || 270 == i2) {
                if (EGL14.EGL_NO_CONTEXT == this.g) {
                    this.g = EGL14.eglGetCurrentContext();
                }
                this.f65f = new MediaManager(fileDescriptor, i3, i4, 90, false, i2, (RecordingListener) null);
                this.f65f.setEncoderCount(1);
                this.f65f.initVideoEncoderWithSharedContext(this.g, i5, true, str);
                this.f65f.startRecording();
                this.f64e = true;
            } else {
                throw new RuntimeException("StickerApi-> startRecording(...) screenOrientation = " + i2 + " is invalid");
            }
        }
    }

    public void startRecording(@NonNull String str, int i2, @NonNull int i3, @NonNull int i4, int i5, String str2) {
        if (i3 != 0 && i4 != 0 && str.length() != 0) {
            if (i3 % 2 != 0) {
                i3++;
            }
            if (i4 % 2 != 0) {
                i4++;
            }
            if (this.f65f != null) {
                throw new RuntimeException("Recording has been started already.");
            } else if (i2 == 0 || 90 == i2 || 180 == i2 || 270 == i2) {
                if (EGL14.EGL_NO_CONTEXT == this.g) {
                    this.g = EGL14.eglGetCurrentContext();
                }
                this.f65f = new MediaManager(str, i3, i4, 90, false, i2, (RecordingListener) null);
                this.f65f.setEncoderCount(1);
                this.f65f.initVideoEncoderWithSharedContext(this.g, i5, true, str2);
                this.f65f.startRecording();
                this.f64e = true;
            } else {
                throw new RuntimeException("StickerApi-> startRecording(...) screenOrientation = " + i2 + " is invalid");
            }
        }
    }

    public void stopRecording() {
        LOG.d(f60a, "process_render -> stopRecording 0");
        if (this.f64e) {
            if (this.f65f != null) {
                LOG.d(f60a, "process_render -> stopRecording 1");
                resumeRecording();
                this.f64e = false;
                this.f65f.stopRecording();
                this.f65f = null;
            }
            LOG.d(f60a, "process_render -> stopRecording 2");
        }
    }
}
