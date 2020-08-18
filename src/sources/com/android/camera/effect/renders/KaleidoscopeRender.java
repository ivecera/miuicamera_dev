package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class KaleidoscopeRender extends PixelEffectRender {
    private static final int DOUBLE_FLIP = 3;
    private static final String FRAG = "precision highp float;\n#define PI 3.1415926\nuniform int uMode;\nuniform vec2 uResolution;\nuniform float uRotation;\nuniform sampler2D sTexture;\nvarying vec2 vTexCoord;\nint H_FLIP = 0x01;\nint V_FLIP = 0x02;\nint DOUBLE_FLIP = 0x03;\nint QUADRUPLE = 0x04;\nint SEXTUPLE = 0x05;\nint REPEATED = 0x06;\nvec2 rotateUV(vec2 uv, float rotation, float mid){\n    float ratio = (uResolution.x / uResolution.y);\n    float s = sin ( rotation );\n    float c = cos ( rotation );\n    mat2 rotationMatrix = mat2( c, -s, s, c);\n    vec2 coord = vec2((uv.x - mid) * ratio ,(uv.y -mid)*1.0);\n    vec2 scaled = rotationMatrix * coord;\n    return vec2(scaled.x / ratio + mid,scaled.y + mid);\n}\nvec2 scaleUV(vec2 uv, float scale){\n    return (uv - vec2(0.5,0.5))*scale + vec2(0.5,0.5);\n}\nvoid main() {\n    vec2 p = vTexCoord;\n    vec2 uv = vTexCoord;\n    if(uMode==H_FLIP){\n        if(uv.x < 0.5){\n           p.x = 1.0 - p.x;\n        }\n        p.x -= 0.25;\n        gl_FragColor = texture2D(sTexture, p);\n    }\n    else if(uMode==V_FLIP){\n        if(uv.y > 0.5){\n           p.y = 1.0 - p.y;\n        }\n        p.y += 0.25;\n        gl_FragColor = texture2D(sTexture, p);\n    }\n    else if(uMode==DOUBLE_FLIP){\n        if(uv.x < 0.5){\n           p.x = 1.0 - p.x;\n        }\n        p.x -= 0.25;\n        if(uv.y > 0.5){\n           p.y = 1.0 - p.y;\n        }\n        p.y += 0.25;\n        gl_FragColor = texture2D(sTexture, p);\n    }\n    else if(uMode==QUADRUPLE){\n        float alpha = (uResolution.y - (uResolution.y - uResolution.x)/2.0)/uResolution.y;\n        float beta = ((uResolution.y - uResolution.x)/2.0)/uResolution.y;\n        if(p.y < p.x && p.x + p.y < 1.0){\n            //keep origin\n            //top\n            uv.y += 0.25;\n            gl_FragColor = texture2D(sTexture,uv);\n        }\n        else if(p.y > p.x && p.x + p.y > 1.0){\n            //rotation PI\n            //bottom\n            uv = rotateUV(uv,PI,0.5);\n            uv.y += 0.25;\n            gl_FragColor = texture2D(sTexture,uv);\n        }\n        else if(p.y < p.x && p.x + p.y > 1.0){\n            //rotation -90\n            //right\n            if(p.y > alpha)\n            {\n               uv = rotateUV(p,PI/2.0,0.5);\n               uv.x = 1.0 - uv.x;\n               uv.x += 1.0;\n               uv.y += 0.25;\n               gl_FragColor = texture2D(sTexture,uv);\n           }\n           else if(p.y < beta)\n           {\n               uv = rotateUV(p,PI/2.0,0.5);\n               uv.x = 1.0 - uv.x;\n               uv.x -= 1.0;\n               uv.y += 0.25;\n               gl_FragColor = texture2D(sTexture,uv);\n           }\n           else\n           {\n              uv = rotateUV(p,PI/2.0,0.5);\n              uv.y += 0.25;\n              gl_FragColor = texture2D(sTexture,uv);\n           }        }\n        else{\n            //rotation -90\n            //left\n            if(p.y > alpha)\n            {\n               uv = rotateUV(p,-PI/2.0,0.5);\n               uv.x = 1.0 - uv.x;\n               uv.x -= 1.0;\n               uv.y += 0.25;\n               gl_FragColor = texture2D(sTexture,uv);\n           }\n           else if(p.y < beta)\n           {\n               uv = rotateUV(p,-PI/2.0,0.5);\n               uv.x = 1.0 - uv.x;\n               uv.x += 1.0;\n               uv.y += 0.25;\n               gl_FragColor = texture2D(sTexture,uv);\n           }\n           else\n           {\n              uv = rotateUV(p,-PI/2.0,0.5);\n              uv.y += 0.25;\n              gl_FragColor = texture2D(sTexture,uv);\n           }        }\n    }\n    else if(uMode==SEXTUPLE){\n        float rot = PI/2.0 - atan(uResolution.y,uResolution.x)/2.0;\n        float alpha = atan(uResolution.y,uResolution.x)/2.0;\n        float beta = atan(uResolution.x,uResolution.y);\n        if(p.y < p.x && p.x + p.y < 1.0){\n            //keep origin\n            //top\n            uv.y += 0.25;\n            gl_FragColor = texture2D(sTexture,uv);\n        }\n        else if(p.y > p.x && p.x + p.y > 1.0){\n            //rotation PI\n            //bottom\n            uv = rotateUV(uv,PI,0.5);\n            uv.y += 0.25;\n            gl_FragColor = texture2D(sTexture,uv);\n        }\n        else if(p.y < p.x && p.x + p.y > 1.0){\n            //rotation -90\n            //right\n            if(p.y > 0.5){\n                uv = rotateUV(uv,PI - rot,0.5);\n            }\n            else{\n                uv = rotateUV(uv,rot,0.5);\n            }\n            uv = scaleUV(uv,cos(beta)/cos(alpha));\n            uv.y += 0.25;\n            gl_FragColor = texture2D(sTexture,uv);\n        }\n        else{\n            //rotation -90\n            //left\n            //\n            if(p.y > 0.5){\n                uv = rotateUV(uv,rot -PI,0.5);\n            }\n            else{\n                uv = rotateUV(uv,-rot,0.5);\n            }\n            uv = scaleUV(uv,cos(beta)/cos(alpha));\n            uv.y += 0.25;\n            gl_FragColor = texture2D(sTexture,uv);\n        }\n    }\n    else if(uMode==REPEATED){\n       float param = (1.0 - 0.9)/2.0;\n       vec2 bl = step(vec2(param,param),uv);\n       vec2 tr = step(vec2(param,param),vec2(1.0,1.0)-uv);\n       float param1 = (1.0 - 0.7)/2.0;\n       vec2 bl1 = step(vec2(param1,param1),uv);\n       vec2 tr1 = step(vec2(param1,param1),vec2(1.0,1.0)-uv);\n       float param2 = (1.0 - 0.4)/2.0;\n       vec2 bl2 = step(vec2(param2,param2),uv);\n       vec2 tr2 = step(vec2(param2,param2),vec2(1.0,1.0)-uv);\n       float xxx = bl.x*bl.y*tr.x*tr.y;\n       float xxx1 = bl1.x*bl1.y*tr1.x*tr1.y;\n       float xxx2 = bl2.x*bl2.y*tr2.x*tr2.y;\n       if(xxx < 0.5){\n           gl_FragColor = texture2D(sTexture,uv);\n       }\n       else if(xxx >  0.5 && xxx1 < 0.5){\n           uv = scaleUV(uv,1.0/(1.0 - 2.0 * param));\n           gl_FragColor = texture2D(sTexture,uv);\n       }\n       else if(xxx1 > 0.5 && xxx2 < 0.5){\n           uv = scaleUV(uv,1.0/(1.0 - 2.0 * param1));\n           gl_FragColor = texture2D(sTexture,uv);\n       }\n       else{\n           uv = scaleUV(uv,1.0/(1.0 - 2.0 *param2));\n           gl_FragColor = texture2D(sTexture,uv);\n       }\n    }    else{\n        gl_FragColor = texture2D(sTexture, uv);\n    }\n}";
    private static final int H_FLIP = 1;
    private static final int QUADRUPLE = 4;
    private static final int REPEATED = 6;
    private static final int SEXTUPLE = 5;
    private static final String TAG = "KaleidoscopeRender";
    private static final int V_FLIP = 2;
    private int mKaleidoscopeId;
    private int mUniformModeHandle;
    private int mUniformResolutionHandle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface KaleidoscopeEffectId {
    }

    public KaleidoscopeRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public KaleidoscopeRender(GLCanvas gLCanvas, int i, String str) {
        super(gLCanvas, i);
        convertToKaleidoscopeEffectId(str);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void convertToKaleidoscopeEffectId(String str) {
        char c2;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 52:
                if (str.equals("4")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 53:
                if (str.equals("5")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 54:
                if (str.equals("6")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        if (c2 == 0) {
            this.mKaleidoscopeId = 5;
        } else if (c2 == 1) {
            this.mKaleidoscopeId = 4;
        } else if (c2 == 2) {
            this.mKaleidoscopeId = 3;
        } else if (c2 == 3) {
            this.mKaleidoscopeId = 2;
        } else if (c2 == 4) {
            this.mKaleidoscopeId = 1;
        } else if (c2 == 5) {
            this.mKaleidoscopeId = 6;
        }
        Log.d(TAG, "setKaleidoscope: " + this.mKaleidoscopeId);
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return FRAG;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        super.initShader();
        this.mUniformModeHandle = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uMode");
        this.mUniformResolutionHandle = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uResolution");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        GLES20.glUniform1i(this.mUniformModeHandle, this.mKaleidoscopeId);
        GLES20.glUniform2f(this.mUniformResolutionHandle, (float) ((Render) this).mPreviewWidth, (float) ((Render) this).mPreviewHeight);
    }

    @Override // com.android.camera.effect.renders.Render
    public void setKaleidoscope(String str) {
        convertToKaleidoscopeEffectId(str);
    }
}
