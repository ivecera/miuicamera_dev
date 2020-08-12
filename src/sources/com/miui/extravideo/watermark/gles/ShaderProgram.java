package com.miui.extravideo.watermark.gles;

import android.opengl.GLES20;
import java.util.HashMap;

public class ShaderProgram {
    private static int INVALID_VALUE = -1;
    private HashMap<String, Integer> attributes = new HashMap<>();
    private int programHandle = INVALID_VALUE;

    private int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        OpenGlUtils.checkGlError("glCreateShader");
        GLES20.glShaderSource(glCreateShader, str);
        OpenGlUtils.checkGlError("glShaderSource");
        GLES20.glCompileShader(glCreateShader);
        OpenGlUtils.checkGlError("glCompileShader");
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        String glGetShaderInfoLog = GLES20.glGetShaderInfoLog(glCreateShader);
        GLES20.glDeleteShader(glCreateShader);
        throw new IllegalArgumentException("Shader compilation failed with: " + glGetShaderInfoLog);
    }

    public void create(String str, String str2) {
        int i = INVALID_VALUE;
        if (str != null) {
            i = loadShader(35633, str);
        }
        if (i == 0) {
            this.programHandle = 0;
            return;
        }
        int i2 = INVALID_VALUE;
        if (str2 != null) {
            i2 = loadShader(35632, str2);
        }
        if (i2 == 0) {
            this.programHandle = 0;
            return;
        }
        this.programHandle = GLES20.glCreateProgram();
        OpenGlUtils.checkGlError("glCreateProgram");
        GLES20.glAttachShader(this.programHandle, i);
        OpenGlUtils.checkGlError("glAttachShader");
        GLES20.glAttachShader(this.programHandle, i2);
        OpenGlUtils.checkGlError("glAttachShader");
        GLES20.glLinkProgram(this.programHandle);
    }

    public int getAttributeLocation(String str) {
        if (this.attributes.containsKey(str)) {
            return this.attributes.get(str).intValue();
        }
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.programHandle, str);
        OpenGlUtils.checkGlError("glGetAttribLocation " + str);
        if (glGetAttribLocation == -1) {
            glGetAttribLocation = GLES20.glGetUniformLocation(this.programHandle, str);
            OpenGlUtils.checkGlError("glGetUniformLocation " + str);
        }
        if (glGetAttribLocation != -1) {
            this.attributes.put(str, Integer.valueOf(glGetAttribLocation));
            return glGetAttribLocation;
        }
        throw new IllegalStateException("Can't find a location for attribute " + str);
    }

    public int getProgramHandle() {
        return this.programHandle;
    }

    public void unUse() {
        GLES20.glUseProgram(0);
    }

    public void use() {
        GLES20.glUseProgram(this.programHandle);
    }
}
