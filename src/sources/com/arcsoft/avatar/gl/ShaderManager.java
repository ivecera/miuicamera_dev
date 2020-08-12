package com.arcsoft.avatar.gl;

import android.opengl.GLES20;
import com.arcsoft.avatar.util.CodecLog;

public class ShaderManager {

    /* renamed from: a  reason: collision with root package name */
    private static final String f132a = "Arc_ShaderManager";

    private static int a(int i, String str) {
        String a2 = a(i);
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            CodecLog.e(f132a, "create shader error, shader type=" + a2 + " , error=" + GLES20.glGetError());
            return 0;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        String glGetShaderInfoLog = GLES20.glGetShaderInfoLog(glCreateShader);
        CodecLog.e(f132a, "createShader shader = " + a2 + "  error: " + glGetShaderInfoLog);
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    private static String a(int i) {
        switch (i) {
            case 35632:
                return "fragment_shader";
            case 35633:
                return "vertext_shader";
            default:
                return "invalid shader type = " + i;
        }
    }

    public static int createProgram(String str, String str2) {
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram == 0) {
            CodecLog.e(f132a, "create program error ,error=" + GLES20.glGetError());
            return 0;
        }
        int a2 = a(35633, str);
        int a3 = a(35632, str2);
        if (a2 == 0 || a3 == 0) {
            GLES20.glDeleteShader(a2);
            GLES20.glDeleteShader(a3);
            GLES20.glDeleteProgram(glCreateProgram);
            return 0;
        }
        GLES20.glAttachShader(glCreateProgram, a2);
        GLES20.glAttachShader(glCreateProgram, a3);
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateProgram;
        }
        String glGetProgramInfoLog = GLES20.glGetProgramInfoLog(glCreateProgram);
        CodecLog.e(f132a, "createProgram error : " + glGetProgramInfoLog);
        GLES20.glDeleteShader(a2);
        GLES20.glDeleteShader(a3);
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }
}
