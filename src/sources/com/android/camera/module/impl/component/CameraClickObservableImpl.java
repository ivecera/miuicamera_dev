package com.android.camera.module.impl.component;

import android.content.res.Resources;
import com.android.camera.CameraAppImpl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraClickObservableImpl implements ModeProtocol.CameraClickObservable {
    private List<int[]> mBottomTipObservableArray = new ArrayList();
    private List<ModeProtocol.CameraClickObservable.ClickObserver> mBottomTipObserverArray = new ArrayList();
    private List<int[]> mBottomTipTipMsgArray = new ArrayList();

    public static CameraClickObservableImpl create() {
        return new CameraClickObservableImpl();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraClickObservable
    public void addObservable(int[] iArr, ModeProtocol.CameraClickObservable.ClickObserver clickObserver, int... iArr2) {
        if (iArr2 != null) {
            this.mBottomTipObservableArray.add(Arrays.copyOf(iArr2, iArr2.length));
            this.mBottomTipTipMsgArray.add(Arrays.copyOf(iArr, iArr.length));
            this.mBottomTipObserverArray.add(clickObserver);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(227, this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0059, code lost:
        r5 = r2;
     */
    @Override // com.android.camera.protocol.ModeProtocol.CameraClickObservable
    public void subscribe(int i) {
        int[] iArr;
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        String currentBottomTipMsg = bottomPopupTips != null ? bottomPopupTips.getCurrentBottomTipMsg() : null;
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int i2 = -1;
        int i3 = 0;
        while (i3 < this.mBottomTipTipMsgArray.size()) {
            int[] iArr2 = this.mBottomTipTipMsgArray.get(i3);
            int length = iArr2.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                }
                int i5 = iArr2[i4];
                try {
                    String string = resources.getString(i5);
                    if (string != null && string.equals(currentBottomTipMsg)) {
                        break;
                    }
                    if (i5 > 0 && topAlert != null && topAlert.isCurrentRecommendTipText(i5)) {
                        break;
                    }
                    i4++;
                } catch (Resources.NotFoundException unused) {
                }
            }
            i3++;
        }
        if (i2 >= 0 && (iArr = this.mBottomTipObservableArray.get(i2)) != null && iArr.length > 0) {
            for (int i6 : iArr) {
                if (i6 == i) {
                    this.mBottomTipObserverArray.get(i2).action();
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        this.mBottomTipObservableArray.clear();
        this.mBottomTipObserverArray.clear();
        this.mBottomTipTipMsgArray.clear();
        ModeCoordinatorImpl.getInstance().detachProtocol(227, this);
    }
}
