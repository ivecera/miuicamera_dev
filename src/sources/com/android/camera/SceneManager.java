package com.android.camera;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SceneManager {
    public static final int SCENE_BURST = 4;
    public static final int SCENE_HDR = 2;
    public static final int SCENE_HHT = 3;
    public static final int SCENE_NORMAL = 1;
    private int[] mSceneStacks = new int[0];

    @Retention(RetentionPolicy.SOURCE)
    public @interface SceneType {
    }

    public int getCurrentScene() {
        int[] iArr = this.mSceneStacks;
        if (iArr.length > 1) {
            return iArr[0];
        }
        return 1;
    }

    public String getSuffix() {
        int currentScene = getCurrentScene();
        return currentScene != 2 ? currentScene != 3 ? "" : "_HHT" : "_HDR";
    }

    public int popStacks() {
        int[] iArr = this.mSceneStacks;
        int length = iArr.length;
        if (length <= 1) {
            return 1;
        }
        int i = length - 2;
        int i2 = iArr[i];
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        this.mSceneStacks = iArr2;
        return i2;
    }

    public void pushStacks(int i) {
        int length = this.mSceneStacks.length;
        if (length >= 1) {
            for (int i2 = 0; i2 < length; i2++) {
                int[] iArr = this.mSceneStacks;
                if (iArr[i2] == i) {
                    int[] iArr2 = new int[length];
                    System.arraycopy(iArr, 0, iArr2, 0, i2);
                    System.arraycopy(this.mSceneStacks, i2 + 1, iArr2, i2, (length - i2) - 1);
                    iArr2[length - 1] = i;
                    this.mSceneStacks = iArr2;
                    return;
                }
            }
        }
        int[] iArr3 = new int[(length + 1)];
        System.arraycopy(this.mSceneStacks, 0, iArr3, 0, length);
        iArr3[length] = i;
        this.mSceneStacks = iArr3;
    }
}
