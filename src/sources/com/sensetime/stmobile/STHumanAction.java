package com.sensetime.stmobile;

import com.sensetime.stmobile.model.STImage;
import com.sensetime.stmobile.model.STMobile106;
import com.sensetime.stmobile.model.STMobileFaceAction;
import com.sensetime.stmobile.model.STMobileHandAction;

public class STHumanAction {
    public int backGroundRet;
    public int faceCount;
    public STMobileFaceAction[] faces;
    public int handCount;
    public STMobileHandAction[] hands;
    public STImage image;

    public STMobile106[] getMobileFaces() {
        int i = this.faceCount;
        if (i == 0) {
            return null;
        }
        STMobile106[] sTMobile106Arr = new STMobile106[i];
        for (int i2 = 0; i2 < this.faceCount; i2++) {
            sTMobile106Arr[i2] = this.faces[i2].face;
        }
        return sTMobile106Arr;
    }

    public boolean replaceMobile106(STMobile106[] sTMobile106Arr) {
        if (sTMobile106Arr == null || sTMobile106Arr.length == 0 || this.faces == null || this.faceCount != sTMobile106Arr.length) {
            return false;
        }
        for (int i = 0; i < sTMobile106Arr.length; i++) {
            this.faces[i].face = sTMobile106Arr[i];
        }
        return true;
    }
}
