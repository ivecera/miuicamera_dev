package com.arcsoft.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.android.camera.storage.Storage;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.extrascene.ExtraSceneEngine;
import com.arcsoft.avatar.gl.GLFramebuffer;
import com.arcsoft.avatar.gl.GLRenderEngine;
import com.arcsoft.avatar.gl.TextureHelper;
import com.arcsoft.avatar.recoder.MediaManager;
import com.arcsoft.avatar.recoder.RecordingListener;
import com.arcsoft.avatar.util.ASVLOFFSCREEN;
import com.arcsoft.avatar.util.AsvloffscreenUtil;
import com.arcsoft.avatar.util.LOG;
import com.arcsoft.avatar.util.TimeConsumingUtil;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class RecordModule {

    /* renamed from: a  reason: collision with root package name */
    private static final String f48a = "RecordModule";
    private static final int ae = 50;
    private static final String ag = "arcsoft_enable_log";
    private static final String ah = "arcsoft_performance";

    /* renamed from: b  reason: collision with root package name */
    private static final int f49b = 1;

    /* renamed from: c  reason: collision with root package name */
    private static final int f50c = 512000;
    private boolean A;
    private boolean B;
    private int C = 270;
    private long D = 0;
    private final int E = 1000000;
    private long F = 0;
    private long G = 0;
    private AvatarEngine H;
    private MediaManager I;
    private volatile boolean J;
    private volatile boolean K;
    private boolean L;
    private RecordingListener M = null;
    private AvatarConfig.ASAvatarProcessInfo N = null;
    private MediaResultCallback O = null;
    private boolean P = false;
    private boolean[] Q = new boolean[3];
    private int R = 0;
    private Boolean S;
    private Bitmap T;
    private ASVLOFFSCREEN U;
    private ASVLOFFSCREEN V;
    private volatile boolean W = true;
    private volatile boolean X = false;
    private volatile boolean Y = false;
    private volatile boolean Z = false;
    private int aa = 0;
    private int ab = 0;
    private int ac = 0;
    private long ad = 0;
    private boolean af = false;
    private ExtraSceneEngine ai = null;
    private volatile boolean aj = false;

    /* renamed from: d  reason: collision with root package name */
    private Context f51d;

    /* renamed from: e  reason: collision with root package name */
    private Lock f52e = new ReentrantLock();

    /* renamed from: f  reason: collision with root package name */
    private Lock f53f = new ReentrantLock();
    private Lock g = new ReentrantLock();
    private volatile boolean h = false;
    private volatile boolean i = false;
    private volatile boolean j = false;
    private volatile boolean k = false;
    private boolean l;
    private EGLDisplay m;
    private EGLContext n;
    private EGLSurface o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private Queue<ASVLOFFSCREEN> v;
    private GLRenderEngine w;
    private GLRenderEngine x;
    private GLFramebuffer y;
    private TextureHelper z;

    public interface MediaResultCallback {
        void onCaptureResult(ByteBuffer byteBuffer);

        void onVideoResult(boolean z);
    }

    public RecordModule(Context context, MediaResultCallback mediaResultCallback) {
        this.f51d = context;
        this.O = mediaResultCallback;
    }

    private long a(long j2) {
        if (j2 <= 0) {
            return 0;
        }
        long j3 = j2 - 512000;
        if (j3 <= 0) {
            return 1;
        }
        return j3;
    }

    private String a(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return str2;
        }
    }

    private void a() {
        long muxerTimeElapsed = this.I.getMuxerTimeElapsed();
        long muxerSizeRecorded = this.I.getMuxerSizeRecorded();
        long a2 = a(this.D);
        long j2 = muxerTimeElapsed / 1000000;
        if (j2 > this.F) {
            this.F = j2;
            RecordingListener recordingListener = this.M;
            if (recordingListener != null) {
                recordingListener.onRecordingListener(258, Long.valueOf(muxerTimeElapsed));
                this.M.onRecordingListener(260, Long.valueOf(muxerSizeRecorded));
            }
        }
        long j3 = this.G;
        if (j3 > 0 && muxerTimeElapsed > j3) {
            stopRecording();
            RecordingListener recordingListener2 = this.M;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(257, Long.valueOf(muxerTimeElapsed));
            }
        }
        if (a2 > 0 && muxerSizeRecorded > a2) {
            stopRecording();
            RecordingListener recordingListener3 = this.M;
            if (recordingListener3 != null) {
                recordingListener3.onRecordingListener(259, Long.valueOf(muxerSizeRecorded));
            }
        }
    }

    private void a(int i2) {
        if (i2 <= 0) {
            LOG.d(f48a, "recordingIfNeed textureId = " + i2);
        } else if (!this.J && this.I != null && this.L) {
            if (!this.af || !c()) {
                this.I.drawSurfaceWithTextureId(i2);
                a();
            }
        }
    }

    private void a(int i2, int i3, int i4, int i5, boolean z2) {
        if ((!this.K || !z2 || !(i3 == i4 || i3 == i5)) && this.K && i2 > 0) {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.r * this.s * 4);
            allocateDirect.order(ByteOrder.nativeOrder());
            GLES20.glReadPixels(this.t, this.u, this.r, this.s, 6408, 5121, allocateDirect);
            this.K = false;
            MediaResultCallback mediaResultCallback = this.O;
            if (mediaResultCallback != null) {
                mediaResultCallback.onCaptureResult(allocateDirect);
            }
        }
    }

    private void a(ASVLOFFSCREEN asvloffscreen) {
        if (this.v.size() >= 1) {
            LOG.d("CKK", "mPreviewQueue.poll()");
            this.v.poll();
        }
        try {
            this.v.offer(asvloffscreen);
        } catch (ClassCastException e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
        }
    }

    private void a(boolean z2) {
        int i2 = 0;
        int i3 = z2 ? this.t : 0;
        if (z2) {
            i2 = this.u;
        }
        GLES20.glViewport(i3, i2, this.r, this.s);
    }

    private byte[] a(byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7) {
        TimeConsumingUtil.startTheTimer("cropNV21");
        if (i4 > i2 || i5 > i3) {
            return null;
        }
        int i8 = (i4 / 2) * 2;
        int i9 = (i5 / 2) * 2;
        int i10 = (i6 / 2) * 2;
        int i11 = (i7 / 2) * 2;
        int i12 = i10 * i11;
        byte[] bArr2 = new byte[((i12 * 3) >> 1)];
        int i13 = i9 * i2;
        int i14 = 0;
        int i15 = i12 - ((i9 / 2) * i10);
        int i16 = (i3 * i2) + i8;
        for (int i17 = i9; i17 < i9 + i11; i17++) {
            System.arraycopy(bArr, i13 + i8, bArr2, i14, i10);
            i13 += i2;
            i14 += i10;
            if ((i17 & 1) == 0) {
                System.arraycopy(bArr, i16, bArr2, i15, i10);
                i16 += i2;
                i15 += i10;
            }
        }
        TimeConsumingUtil.stopTiming("performance", "cropNV21");
        return bArr2;
    }

    private void b(ASVLOFFSCREEN asvloffscreen) {
        if (asvloffscreen.getPixelFormat() == 2050) {
            int width = asvloffscreen.getWidth();
            int height = asvloffscreen.getHeight();
            asvloffscreen.setYData(a(asvloffscreen.getYData(), asvloffscreen.getRowStride()[0], height, (width - height) / 2, 0, height, height));
            asvloffscreen.setWidth(asvloffscreen.getHeight());
            asvloffscreen.setRowStride(asvloffscreen.getHeight());
        } else if (asvloffscreen.getPixelFormat() != 773) {
        } else {
            if (asvloffscreen.getWidth() > asvloffscreen.getHeight()) {
                asvloffscreen.setRGBA8888(b(asvloffscreen.getRGBA8888(), asvloffscreen.getWidth(), asvloffscreen.getHeight(), (asvloffscreen.getWidth() - asvloffscreen.getHeight()) / 2, 0, asvloffscreen.getHeight(), asvloffscreen.getHeight()));
                asvloffscreen.setWidth(asvloffscreen.getHeight());
                return;
            }
            asvloffscreen.setRGBA8888(b(asvloffscreen.getRGBA8888(), asvloffscreen.getWidth(), asvloffscreen.getHeight(), 0, (asvloffscreen.getHeight() - asvloffscreen.getWidth()) / 2, asvloffscreen.getWidth(), asvloffscreen.getWidth()));
            asvloffscreen.setHeight(asvloffscreen.getWidth());
        }
    }

    private boolean b() {
        AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo = this.N;
        if (aSAvatarProcessInfo == null) {
            LOG.d("CheckOutLine", "null");
            this.P = true;
        } else if (aSAvatarProcessInfo.shelterIsNull()) {
            LOG.d("CheckOutLine", "shelterFlags == null");
            this.P = true;
        } else {
            LOG.d("CheckOutLine", "faceCount = " + this.N.getFaceCount());
            if (this.N.getFaceCount() <= 0) {
                this.P = true;
            } else {
                boolean checkFaceBlocking = this.N.checkFaceBlocking();
                if (this.R > 2) {
                    this.R = 0;
                }
                boolean[] zArr = this.Q;
                int i2 = this.R;
                zArr[i2] = checkFaceBlocking;
                this.R = i2 + 1;
                if (!zArr[0] || !zArr[1] || !zArr[2]) {
                    boolean[] zArr2 = this.Q;
                    if (!zArr2[0] && !zArr2[1] && !zArr2[2]) {
                        this.P = false;
                    }
                } else {
                    this.P = true;
                }
            }
        }
        LOG.d("CheckOutLine", "--- > mBlockingFaces <---" + this.P);
        return this.P;
    }

    private byte[] b(byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7) {
        TimeConsumingUtil.startTheTimer("cropRGBA");
        if (i4 > i2 || i5 > i3) {
            return null;
        }
        int i8 = (i4 / 2) * 2;
        int i9 = (i5 / 2) * 2;
        int i10 = (i6 / 2) * 2;
        int i11 = (i7 / 2) * 2;
        byte[] bArr2 = new byte[(i10 * i11 * 4)];
        int i12 = 0;
        int i13 = i9 * i2 * 4;
        for (int i14 = i9; i14 < i9 + i11; i14++) {
            int i15 = i10 * 4;
            System.arraycopy(bArr, (i8 * 4) + i13, bArr2, i12, i15);
            i13 += i2 * 4;
            i12 += i15;
        }
        TimeConsumingUtil.stopTiming("performance", "cropRGBA");
        return bArr2;
    }

    private boolean c() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.ad == 0) {
            this.ad = currentTimeMillis;
        }
        long j2 = (long) (this.ac * 50);
        long j3 = currentTimeMillis - this.ad;
        LOG.d(f48a, "  mFrameCount = " + this.ac + "time = " + j2 + " - " + j3);
        this.ac = this.ac + 1;
        if (j2 - j3 < 50) {
            return false;
        }
        this.ac = 1;
        this.ad = currentTimeMillis;
        return true;
    }

    public void capture() {
        try {
            this.f52e.lock();
            if (this.h) {
                this.f52e.unlock();
                this.K = true;
            }
        } finally {
            this.f52e.unlock();
        }
    }

    public void changeHumanTemplate(String str, String str2) {
        this.X = true;
        this.H.setTemplatePath(str);
        this.H.loadConfig(str2);
        this.X = false;
    }

    public AvatarEngine getAvatarEngine() {
        return this.H;
    }

    public ArrayList<BackgroundInfo> getBackgroundBmpInfo(String str) {
        String str2;
        try {
            LOG.d(f48a, "getBgXmlInfo = " + str);
            ArrayList<BackgroundInfo> arrayList = new ArrayList<>();
            FileInputStream fileInputStream = new FileInputStream(str + "/" + BackgroundInfo.getXMLName());
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(fileInputStream, "UTF-8");
            String str3 = null;
            BackgroundInfo backgroundInfo = null;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        str2 = newPullParser.getName();
                        if (str2.equals(BackgroundInfo.getXMLInfoTag())) {
                            backgroundInfo = new BackgroundInfo();
                        }
                    } else if (eventType == 3) {
                        if (newPullParser.getName().equals(BackgroundInfo.getXMLInfoTag())) {
                            ArrayList<String> arrayList2 = new ArrayList<>();
                            for (int i2 = 0; i2 < backgroundInfo.getCount(); i2++) {
                                String str4 = str + "/" + backgroundInfo.getName() + "/" + backgroundInfo.getName() + "_" + i2 + Storage.JPEG_SUFFIX;
                                LOG.d(f48a, "bmpPath = " + str4);
                                arrayList2.add(str4);
                            }
                            backgroundInfo.setResolution_FullSize_PathList(arrayList2);
                            arrayList.add(backgroundInfo);
                        }
                        str2 = "";
                    } else if (eventType == 4) {
                        if (str3.equals(BackgroundInfo.getXMLNameTag())) {
                            backgroundInfo.setName(newPullParser.getText());
                        } else if (str3.equals(BackgroundInfo.getXMLCountTag())) {
                            backgroundInfo.setCount(Integer.parseInt(newPullParser.getText()));
                        } else if (str3.equals(BackgroundInfo.getXMLDelayTag())) {
                            backgroundInfo.setDelayMillis(Integer.parseInt(newPullParser.getText()));
                        } else if (str3.equals(BackgroundInfo.getXMLCrop4_3_XY())) {
                            backgroundInfo.setCrop4_3_XY(newPullParser.getText());
                        } else if (str3.equals(BackgroundInfo.getXMLCrop16_9_XY())) {
                            backgroundInfo.setCrop16_9_XY(newPullParser.getText());
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

    public void init(int i2, int i3, int i4, AvatarEngine avatarEngine, boolean z2) {
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = false;
        this.A = false;
        this.L = false;
        this.J = false;
        this.p = i3;
        this.q = i4;
        this.H = avatarEngine;
        this.v = new LinkedList();
        this.v.clear();
        this.B = z2;
        this.C = i2;
        this.l = false;
        this.m = EGL14.EGL_NO_DISPLAY;
        this.n = EGL14.EGL_NO_CONTEXT;
        this.o = EGL14.EGL_NO_SURFACE;
        this.N = new AvatarConfig.ASAvatarProcessInfo();
        boolean z3 = true;
        this.h = true;
        try {
            LOG.DEBUG = Integer.parseInt(a(ag, "0")) == 1;
            if (Integer.parseInt(a(ah, "0")) != 1) {
                z3 = false;
            }
            TimeConsumingUtil.DEBUG = z3;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }

    public boolean isRequestPause() {
        return this.J;
    }

    public void pause() {
        this.U = null;
    }

    public void pauseRecording() {
        try {
            this.f52e.lock();
            if (this.h) {
                this.f52e.unlock();
                MediaManager mediaManager = this.I;
                if (mediaManager != null && this.L) {
                    this.J = true;
                    mediaManager.pauseRecording();
                }
            }
        } finally {
            this.f52e.unlock();
        }
    }

    public void reset() {
        this.W = true;
    }

    public void resetExtraScene() {
        ExtraSceneEngine extraSceneEngine = this.ai;
        if (extraSceneEngine != null) {
            extraSceneEngine.resetExtraScene();
        }
    }

    public void resumeRecording() {
        try {
            this.f52e.lock();
            if (this.h) {
                this.f52e.unlock();
                if (this.I != null && this.L && this.J) {
                    this.I.resumeRecording();
                    this.J = false;
                }
            }
        } finally {
            this.f52e.unlock();
        }
    }

    public void setAvatarEngine(AvatarEngine avatarEngine) {
        this.H = avatarEngine;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0055 A[Catch:{ all -> 0x00d6 }] */
    public void setBackground(Bitmap bitmap, BackgroundInfo backgroundInfo) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (bitmap != null) {
            try {
                this.g.lock();
                this.V = AsvloffscreenUtil.buildRGBA(bitmap);
                TimeConsumingUtil.startTheTimer("setBackground");
                if (!this.Z) {
                    int resolutionMode = backgroundInfo.getResolutionMode();
                    int width = this.V.getWidth();
                    if (resolutionMode == 2) {
                        i5 = (((width * 16) / 9) / 2) * 2;
                        i4 = backgroundInfo.getCrop16_9_XY()[0];
                        i3 = backgroundInfo.getCrop16_9_XY()[1];
                    } else if (resolutionMode == 1) {
                        i5 = (((width * 4) / 3) / 2) * 2;
                        i4 = backgroundInfo.getCrop4_3_XY()[0];
                        i3 = backgroundInfo.getCrop4_3_XY()[1];
                    } else {
                        i4 = 0;
                        i3 = 0;
                        i2 = 0;
                        if (resolutionMode != 3) {
                            int i6 = i4 + width > this.V.getWidth() ? 0 : i4;
                            int i7 = i3 + i2 > this.V.getHeight() ? 0 : i3;
                            byte[] b2 = b(this.V.getRGBA8888(), this.V.getWidth(), this.V.getHeight(), i6, i7, width, i2);
                            this.V.setHeight(i2);
                            this.V.setRGBA8888(b2);
                            LOG.d("setBackground", "mode = " + resolutionMode + ", cropX = " + i6 + ", cropY = " + i7 + ", w = " + width + ", h = " + i2);
                        }
                    }
                    i2 = i5;
                    if (resolutionMode != 3) {
                    }
                }
                TimeConsumingUtil.stopTiming("zhangs0997", "setBackground");
            } finally {
                this.g.unlock();
                this.Y = true;
            }
        } else {
            this.Y = false;
        }
    }

    public void setBackgroundToSquare(boolean z2) {
        this.Z = z2;
        this.af = z2;
    }

    public void setDrawScope(int i2, int i3, int i4, int i5) {
        this.t = i2;
        this.u = i3;
        this.r = i4;
        this.s = i5;
    }

    public void setExtraSceneTemplatePath(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.ai = new ExtraSceneEngine(str);
        }
    }

    public void setMirror(boolean z2) {
        this.B = z2;
    }

    public void setPreviewSize(int i2, int i3) {
        this.p = i2;
        this.q = i3;
        this.W = true;
    }

    public void setmImageOrientation(int i2) {
        this.C = i2;
    }

    /* JADX INFO: finally extract failed */
    public boolean startProcess(@NonNull ASVLOFFSCREEN asvloffscreen, int i2, boolean z2) {
        try {
            this.f52e.lock();
            if (!this.h) {
                LOG.d(f48a, "startProcess_1() failed, engine is not inited. ");
                return true;
            }
            this.f52e.unlock();
            if (asvloffscreen == null) {
                return true;
            }
            try {
                this.j = true;
                try {
                    LOG.d(f48a, "lock -> process lock");
                    this.f53f.lock();
                    this.U = asvloffscreen;
                    this.f53f.unlock();
                    LOG.d(f48a, "lock -> process unlock");
                    this.i = true;
                    if (!z2) {
                        this.j = false;
                        return true;
                    }
                    TimeConsumingUtil.startTheTimer("avatarProcessWithInfo");
                    if (this.H.nativeProcessWithInfoToPreview(asvloffscreen, 90, this.B, i2, this.N, this.Y)) {
                        if (this.ai != null) {
                            this.ai.checkExtraScene(this.N);
                        }
                        this.H.setProcessInfo(this.N);
                    }
                    TimeConsumingUtil.stopTiming("performance", "avatarProcessWithInfo");
                    this.W = this.Y ? false : b();
                    this.j = false;
                    return this.W;
                } catch (Throwable th) {
                    this.f53f.unlock();
                    LOG.d(f48a, "lock -> process unlock");
                    throw th;
                }
            } catch (Exception e2) {
                LOG.d(f48a, "e -> " + e2.toString());
                e2.printStackTrace();
            } catch (Throwable th2) {
                this.j = false;
                throw th2;
            }
        } finally {
            this.f52e.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public boolean startProcess(@NonNull byte[] bArr, int i2, int i3, int i4, boolean z2) {
        try {
            this.f52e.lock();
            if (!this.h) {
                LOG.d(f48a, "startProcess_1() failed, engine is not inited. ");
                return true;
            }
            this.f52e.unlock();
            if (bArr == null || bArr.length <= 0 || i3 <= 0 || i4 <= 0) {
                return true;
            }
            try {
                this.j = true;
                try {
                    LOG.d(f48a, "lock -> process lock");
                    this.f53f.lock();
                    this.U = new ASVLOFFSCREEN(bArr, i3, i3, i4);
                    this.f53f.unlock();
                    LOG.d(f48a, "lock -> process unlock");
                    this.i = true;
                    if (!z2) {
                        this.j = false;
                        return true;
                    }
                    TimeConsumingUtil.startTheTimer("avatarProcessWithInfo");
                    this.H.avatarProcessWithInfo(bArr, i3, i4, i3, 90, this.B, i2, this.N);
                    TimeConsumingUtil.stopTiming("performance", "avatarProcessWithInfo");
                    this.W = b();
                    this.j = false;
                    return this.W;
                } catch (Throwable th) {
                    this.f53f.unlock();
                    LOG.d(f48a, "lock -> process unlock");
                    throw th;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } catch (Throwable th2) {
                this.j = false;
                throw th2;
            }
        } finally {
            this.f52e.unlock();
        }
    }

    public void startRecording(@NonNull FileDescriptor fileDescriptor, RecordingListener recordingListener, int i2, @NonNull int i3, @NonNull int i4, int i5, String str) {
        try {
            this.f52e.lock();
            if (!this.h) {
                LOG.d(f48a, "startRecording-> StickerApi is not init.");
                return;
            }
            this.f52e.unlock();
            if (i3 != 0 && i4 != 0 && fileDescriptor != null) {
                if (this.I != null) {
                    throw new RuntimeException("Recording has been started already.");
                } else if (i2 == 0 || 90 == i2 || 180 == i2 || 270 == i2) {
                    this.aj = false;
                    this.M = recordingListener;
                    if (EGL14.EGL_NO_CONTEXT == this.n) {
                        this.n = EGL14.eglGetCurrentContext();
                    }
                    this.I = new MediaManager(fileDescriptor, i3, i4, this.C, this.B, i2, recordingListener);
                    this.I.setEncoderCount(2);
                    this.I.initVideoEncoderWithSharedContext(this.n, i5, true, str);
                    this.I.initAudioEncoder();
                    this.I.startRecording();
                    this.L = true;
                    this.J = false;
                } else {
                    throw new RuntimeException("StickerApi-> startRecording(...) screenOrientation = " + i2 + " is invalid");
                }
            }
        } finally {
            this.f52e.unlock();
        }
    }

    public void startRecording(@NonNull String str, RecordingListener recordingListener, int i2, @NonNull int i3, @NonNull int i4, int i5, String str2) {
        try {
            this.f52e.lock();
            if (!this.h) {
                LOG.d(f48a, "startRecording-> StickerApi is not init.");
                return;
            }
            this.f52e.unlock();
            if (i3 != 0 && i4 != 0 && str.length() != 0) {
                if (this.I != null) {
                    throw new RuntimeException("Recording has been started already.");
                } else if (i2 == 0 || 90 == i2 || 180 == i2 || 270 == i2) {
                    this.aj = false;
                    this.M = recordingListener;
                    if (EGL14.EGL_NO_CONTEXT == this.n) {
                        this.n = EGL14.eglGetCurrentContext();
                    }
                    this.I = new MediaManager(str, i3, i4, this.C, this.B, i2, recordingListener);
                    this.I.setEncoderCount(2);
                    this.I.initVideoEncoderWithSharedContext(this.n, i5, true, str2);
                    this.I.initAudioEncoder();
                    this.I.startRecording();
                    this.L = true;
                    this.J = false;
                } else {
                    throw new RuntimeException("StickerApi-> startRecording(...) screenOrientation = " + i2 + " is invalid");
                }
            }
        } finally {
            this.f52e.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public void startRender(int i2, boolean z2, int i3, int i4, boolean z3, int[] iArr, byte[] bArr, boolean z4) {
        boolean z5;
        int i5;
        boolean z6;
        int i6;
        ASVLOFFSCREEN asvloffscreen;
        boolean z7;
        try {
            this.f52e.lock();
            if (!this.h || !this.i) {
                LOG.d(f48a, "startRender() failed, engine is not inited or startRender process not ready! ");
                this.f52e.unlock();
                return;
            }
            this.f52e.unlock();
            if (this.U == null) {
                LOG.d(f48a, "mBackgroundBuffer == null");
                return;
            }
            try {
                this.k = true;
                if (this.Y) {
                    try {
                        this.g.lock();
                        asvloffscreen = (ASVLOFFSCREEN) this.V.clone();
                        this.g.unlock();
                        i6 = 0;
                        z6 = false;
                        i5 = 0;
                    } catch (Throwable th) {
                        this.g.unlock();
                        throw th;
                    }
                } else {
                    try {
                        this.f53f.lock();
                        asvloffscreen = (ASVLOFFSCREEN) this.U.clone();
                        this.f53f.unlock();
                        i6 = i2;
                        z6 = z2;
                        i5 = i3;
                    } catch (Throwable th2) {
                        this.f53f.unlock();
                        throw th2;
                    }
                }
                this.aa = this.U.getHeight();
                this.ab = this.U.getWidth();
                if (asvloffscreen == null || asvloffscreen.getHeight() <= 0 || asvloffscreen.getWidth() <= 0) {
                    this.k = false;
                    return;
                }
                if (this.Z) {
                    b(asvloffscreen);
                    this.aa = this.U.getHeight();
                    this.ab = this.U.getHeight();
                }
                if (this.ab == 0 || this.aa == 0) {
                    this.aa = this.U.getHeight();
                    this.ab = this.U.getWidth();
                }
                if (!z4 || this.W || this.X) {
                    this.H.renderBackgroundWithImageData(asvloffscreen, i6, z6, iArr);
                    z7 = false;
                } else {
                    if (this.K) {
                        this.aa = this.U.getHeight() * 2;
                        this.ab = (this.Z ? this.U.getHeight() : this.U.getWidth()) * 2;
                    }
                    this.aj = true;
                    this.H.renderWithBackground(null, asvloffscreen, i6, z6, i5, this.aa, this.ab, i4, z3, iArr, bArr);
                    z7 = true;
                }
                if (iArr != null) {
                    this.H.renderBackgroundWithTexture(iArr[0], 0, false);
                    z5 = false;
                    try {
                        a(iArr[0], asvloffscreen.getHeight(), this.aa, this.ab, z7);
                        a(iArr[0]);
                    } catch (Exception e2) {
                        e = e2;
                    }
                } else {
                    z5 = false;
                }
                this.k = z5;
            } catch (Exception e3) {
                e = e3;
                z5 = false;
                try {
                    e.printStackTrace();
                    this.k = z5;
                } catch (Throwable th3) {
                    th = th3;
                    this.k = z5;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                z5 = false;
                this.k = z5;
                throw th;
            }
        } catch (Throwable th5) {
            this.f52e.unlock();
            throw th5;
        }
    }

    public void stopRecording() {
        try {
            this.f52e.lock();
            if (!this.h) {
                this.f52e.unlock();
                return;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.f52e.unlock();
            throw th;
        }
        this.f52e.unlock();
        if (this.L) {
            this.ad = 0;
            this.ac = 0;
            if (this.I != null) {
                resumeRecording();
                this.L = false;
                this.I.stopRecording();
                this.I = null;
                this.J = false;
                this.G = 0;
                this.F = 0;
                this.D = 0;
                MediaResultCallback mediaResultCallback = this.O;
                if (mediaResultCallback != null) {
                    mediaResultCallback.onVideoResult(this.aj);
                }
            }
        }
    }

    public void unInit() {
        stopRecording();
        try {
            this.f52e.lock();
            if (!this.h) {
                LOG.d(f48a, "uninit () failed, engine is not inited. ");
                return;
            }
            this.h = false;
            int i2 = 100;
            while (true) {
                if ((this.j || this.k) && i2 - 1 > 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            this.l = false;
            this.K = false;
            this.v.clear();
            this.v = null;
            if (this.T != null && !this.T.isRecycled()) {
                this.T.isRecycled();
                this.T = null;
            }
            if (this.z != null) {
                this.z.deleteTexture();
                this.z = null;
            }
            if (this.y != null) {
                this.y.unInit();
                this.y = null;
            }
            if (this.w != null) {
                this.w.unInit();
                this.w = null;
            }
            if (this.x != null) {
                this.x.unInit();
                this.x = null;
            }
            this.U = null;
            this.n = EGL14.EGL_NO_CONTEXT;
            this.o = EGL14.EGL_NO_SURFACE;
            this.m = EGL14.EGL_NO_DISPLAY;
            this.r = 0;
            this.s = 0;
            this.j = false;
            this.k = false;
            this.i = false;
            this.f52e.unlock();
        } finally {
            this.f52e.unlock();
        }
    }

    public void updateAvatarConfigInfo(AvatarEngine avatarEngine) {
        ExtraSceneEngine extraSceneEngine = this.ai;
        if (extraSceneEngine != null) {
            extraSceneEngine.setAvatarEngine(avatarEngine);
        }
    }
}
