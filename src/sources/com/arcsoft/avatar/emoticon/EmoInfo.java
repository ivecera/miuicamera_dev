package com.arcsoft.avatar.emoticon;

import android.util.Size;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.util.ASVLOFFSCREEN;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class EmoInfo {

    /* renamed from: a  reason: collision with root package name */
    private static final String f66a = "EmoInfo";

    /* renamed from: b  reason: collision with root package name */
    private boolean f67b = true;

    /* renamed from: c  reason: collision with root package name */
    private String f68c = "";

    /* renamed from: d  reason: collision with root package name */
    private String f69d;

    /* renamed from: e  reason: collision with root package name */
    private FileDescriptor f70e;

    /* renamed from: f  reason: collision with root package name */
    private int f71f = 0;
    private float g = 0.0f;
    private float h = 0.0f;
    private boolean i = false;
    private boolean j = false;
    private ByteBuffer k = null;
    private byte[] l = null;
    private String m = "";
    private ArrayList<EmoExtraInfo> n = new ArrayList<>();
    private Size o = null;
    private int p = 25;
    private float q = 1.0f;
    private String r;
    private String s;

    public class EmoExtraInfo {
        public ASVLOFFSCREEN asBackGround;
        public ASVLOFFSCREEN asForeGround;
        public String backGroundPath;
        public String foreGroundPath;
        public int index;
        public AvatarConfig.ASAvatarProcessInfo processInfo;
        public float time;

        public EmoExtraInfo() {
            this.index = 0;
            this.time = 0.0f;
            this.asForeGround = null;
            this.asBackGround = null;
            this.processInfo = null;
            this.processInfo = new AvatarConfig.ASAvatarProcessInfo(256, 256, 0, false);
        }
    }

    public ArrayList<EmoExtraInfo> getEmoExtraInfoList() {
        return this.n;
    }

    public Size getEmoImageSize() {
        return this.o;
    }

    public int getEmoMaxCount() {
        return this.f71f;
    }

    public String getEmoName() {
        return this.f68c;
    }

    public FileDescriptor getFileDescriptor() {
        return this.f70e;
    }

    public String getFilePath() {
        return this.m;
    }

    public float getScaleLevel() {
        return this.q;
    }

    public boolean getSelect() {
        return this.f67b;
    }

    public String getThumbBgGroundPath() {
        return this.s;
    }

    public String getThumbForGroundPath() {
        return this.r;
    }

    public int getThumbFrameIndex() {
        return this.p;
    }

    public ByteBuffer getThumbnail() {
        return this.k;
    }

    public byte[] getThumbnailData() {
        return this.l;
    }

    public float getTranslationX() {
        return this.g;
    }

    public float getTranslationY() {
        return this.h;
    }

    public String getVideoPath() {
        return this.f69d;
    }

    public boolean isMultipleBG() {
        return this.j;
    }

    public boolean isNeedFaceColor() {
        return this.i;
    }

    public void needFaceColor(boolean z) {
        this.i = z;
    }

    public void setEmoExtraInfoList(ArrayList<EmoExtraInfo> arrayList) {
        this.n = arrayList;
    }

    public void setEmoImageSize(Size size) {
        this.o = size;
    }

    public void setEmoMaxCount(int i2) {
        this.f71f = i2;
    }

    public void setEmoName(String str) {
        this.f68c = str;
    }

    public void setFileDescriptor(FileDescriptor fileDescriptor) {
        this.f70e = fileDescriptor;
    }

    public void setFilePath(String str) {
        this.m = str;
    }

    public void setMultipleBG(boolean z) {
        this.j = z;
    }

    public void setScaleLevel(float f2) {
        this.q = f2;
    }

    public void setSelect(boolean z) {
        this.f67b = z;
    }

    public void setThumbBgGroundPath(String str) {
        this.s = str;
    }

    public void setThumbForGroundPath(String str) {
        this.r = str;
    }

    public void setThumbFrameIndex(int i2) {
        this.p = i2;
    }

    public void setThumbnail(ByteBuffer byteBuffer) {
        this.k = byteBuffer;
    }

    public void setThumbnailData(byte[] bArr) {
        this.l = bArr;
    }

    public void setTranslationX(float f2) {
        this.g = f2;
    }

    public void setTranslationY(float f2) {
        this.h = f2;
    }

    public void setVideoPath(String str) {
        this.f69d = str;
    }

    public String toString() {
        String str = "emoName = " + this.f68c + ", emoMaxCount = " + this.f71f + "\n";
        Iterator<EmoExtraInfo> it = this.n.iterator();
        String str2 = "";
        while (it.hasNext()) {
            EmoExtraInfo next = it.next();
            str2 = "index = " + next.index + ", time = " + next.time + ", foreGroundPath = " + next.foreGroundPath + "tongueStatus = " + next.processInfo.getTongueStatus() + ", Orientations = " + Arrays.toString(next.processInfo.getOrientations()) + ", LeftEyes = " + Arrays.toString(next.processInfo.getOrientationLeftEyes()) + ", RightEyes = " + Arrays.toString(next.processInfo.getOrientationRightEyes()) + ", ExpWeights = " + Arrays.toString(next.processInfo.getExpWeights());
        }
        return str + str2;
    }
}
