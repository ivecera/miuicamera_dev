package com.arcsoft.supernight;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class StreamPreviewGL extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final float[] s = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] t = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] u = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    private static final float[] v = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    private static final float[] w = {1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final float[] x = {1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
    private static final float[] y = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f};
    private static final float[] z = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private OnRenderListener A = null;
    private RectF B;
    private boolean C = false;
    private boolean D = false;
    private int E;
    private int F;
    private int G;
    private ByteBuffer[] H;
    private boolean I = true;
    private int J = 90;
    private boolean K = true;
    private boolean L;

    /* renamed from: a  reason: collision with root package name */
    private Context f264a;

    /* renamed from: b  reason: collision with root package name */
    private boolean f265b = false;

    /* renamed from: c  reason: collision with root package name */
    private int f266c;

    /* renamed from: d  reason: collision with root package name */
    private int f267d;

    /* renamed from: e  reason: collision with root package name */
    private int[] f268e;

    /* renamed from: f  reason: collision with root package name */
    private String f269f;
    private String g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private FloatBuffer n;
    private FloatBuffer o;
    private ByteBuffer p = null;
    private int q = 0;
    private int r = 0;

    public interface OnRenderListener {
        void onRenderNotify();
    }

    public StreamPreviewGL(Context context) {
        super(context);
        init(context);
    }

    public StreamPreviewGL(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void a() {
        float[] fArr = {-1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f};
        this.n = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.n.put(fArr).position(0);
        this.C = false;
        this.D = false;
    }

    private void b() {
        this.f268e = new int[2];
        GLES20.glGenTextures(2, this.f268e, 0);
        GLES20.glBindTexture(3553, this.f268e[0]);
        GLES20.glTexParameterf(3553, 10241, 9728.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glBindTexture(3553, this.f268e[1]);
        GLES20.glTexParameterf(3553, 10241, 9728.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glPixelStorei(3317, 1);
    }

    private void c() {
        this.f269f = ShaderUtils.loadFromAssetsFile("arcsoft/vertex.sh", this.f264a.getResources());
        this.g = ShaderUtils.loadFromAssetsFile("arcsoft/frag.sh", this.f264a.getResources());
        this.h = ShaderUtils.createProgram(this.f269f, this.g);
        this.i = GLES20.glGetAttribLocation(this.h, "aPosition");
        this.j = GLES20.glGetAttribLocation(this.h, "aTexCoord");
        this.k = GLES20.glGetUniformLocation(this.h, "uTextureY");
        this.l = GLES20.glGetUniformLocation(this.h, "uTextureUV");
        this.m = GLES20.glGetUniformLocation(this.h, "uNV12");
    }

    private void d() {
        if (this.K) {
            float[] fArr = s;
            int i2 = this.J;
            if (i2 == 0) {
                fArr = e();
            } else if (i2 == 90) {
                fArr = f();
            } else if (i2 == 180) {
                fArr = g();
            } else if (i2 == 270) {
                fArr = h();
            }
            this.o = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.o.put(fArr).position(0);
            this.K = false;
        }
    }

    private float[] e() {
        return this.I ? w : s;
    }

    private float[] f() {
        return this.I ? x : t;
    }

    private float[] g() {
        return this.I ? y : u;
    }

    private float[] h() {
        return this.I ? z : v;
    }

    private void i() {
        if (this.H != null) {
            GLES20.glUseProgram(this.h);
            GLES20.glVertexAttribPointer(this.i, 3, 5126, false, 0, (Buffer) this.n);
            GLES20.glEnableVertexAttribArray(this.i);
            GLES20.glVertexAttribPointer(this.j, 2, 5126, false, 0, (Buffer) this.o);
            GLES20.glEnableVertexAttribArray(this.j);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.f268e[0]);
            GLES20.glPixelStorei(3314, this.G);
            GLES20.glTexImage2D(3553, 0, 6409, this.f266c, this.f267d, 0, 6409, 5121, this.H[0].position(0));
            GLES20.glUniform1i(this.k, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, this.f268e[1]);
            GLES20.glPixelStorei(3314, this.G / 2);
            GLES20.glTexImage2D(3553, 0, 6410, this.f266c / 2, this.f267d / 2, 0, 6410, 5121, this.H[1].position(0));
            GLES20.glUniform1i(this.l, 1);
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.i);
            GLES20.glDisableVertexAttribArray(this.j);
            GLES20.glBindTexture(3553, 0);
            GLES20.glUniform1i(this.m, !this.L);
        }
    }

    public void drawBlackScreen() {
        this.f265b = true;
        requestRender();
        OnRenderListener onRenderListener = this.A;
        if (onRenderListener != null) {
            onRenderListener.onRenderNotify();
        }
    }

    public boolean drawPreviewFrame(ByteBuffer[] byteBufferArr, int i2, int i3, int i4) {
        this.H = byteBufferArr;
        this.G = i4;
        this.f265b = false;
        requestRender();
        OnRenderListener onRenderListener = this.A;
        if (onRenderListener != null) {
            onRenderListener.onRenderNotify();
        }
        return this.H != null;
    }

    public void init(Context context) {
        this.f264a = context;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(0);
    }

    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(16640);
        if (this.f265b) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            return;
        }
        d();
        a();
        i();
    }

    public void onSurfaceChanged(GL10 gl10, int i2, int i3) {
        GLES20.glViewport(0, 0, i2, i3);
        if (this.E != i2 || this.F != i3) {
            this.E = i2;
            this.F = i3;
            this.D = true;
        }
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        b();
        c();
    }

    public void releaseGLView() {
        int[] iArr = this.f268e;
        if (iArr != null) {
            GLES20.glDeleteTextures(1, iArr, 0);
        }
    }

    public void setDataFormat(boolean z2) {
        this.L = z2;
    }

    public void setOrientationMirror(int i2, boolean z2) {
        if (this.J != i2 || this.I != z2) {
            this.J = i2;
            this.I = z2;
            this.K = true;
        }
    }

    public void setPreviewArea(RectF rectF) {
        this.B = rectF;
        this.C = true;
    }

    public void setPreviewSize(int i2, int i3) {
        if (this.f266c != i2 || this.f267d != i3) {
            this.f266c = i2;
            this.f267d = i3;
            this.H = null;
        }
    }

    public void setRenderListener(OnRenderListener onRenderListener) {
        this.A = onRenderListener;
    }
}
