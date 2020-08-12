package com.android.camera.ui;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PopupManager {
    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    private static HashMap<Context, PopupManager> sMap = new HashMap<>();
    private OnOtherPopupShowedListener mLastListener;
    private ArrayList<OnOtherPopupShowedListener> mListeners = new ArrayList<>();

    public interface OnOtherPopupShowedListener {
        boolean onOtherPopupShowed(int i);

        void recoverIfNeeded();
    }

    private PopupManager() {
    }

    public static PopupManager getInstance(Context context) {
        PopupManager popupManager = sMap.get(context);
        if (popupManager != null) {
            return popupManager;
        }
        PopupManager popupManager2 = new PopupManager();
        sMap.put(context, popupManager2);
        return popupManager2;
    }

    public static void removeInstance(Context context) {
        if (sMap.get(context) != null) {
            sMap.remove(context);
        }
    }

    public void clearRecoveredPopupListenerIfNeeded(OnOtherPopupShowedListener onOtherPopupShowedListener) {
        if (this.mLastListener == onOtherPopupShowedListener) {
            this.mLastListener = null;
        }
    }

    public OnOtherPopupShowedListener getLastOnOtherPopupShowedListener() {
        return this.mLastListener;
    }

    public void notifyDismissPopup() {
        OnOtherPopupShowedListener onOtherPopupShowedListener = this.mLastListener;
        if (onOtherPopupShowedListener != null) {
            onOtherPopupShowedListener.recoverIfNeeded();
            this.mLastListener = null;
        }
    }

    public void notifyShowPopup(View view, int i) {
        Iterator<OnOtherPopupShowedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            OnOtherPopupShowedListener next = it.next();
            if (((View) next) != view && next.onOtherPopupShowed(i)) {
                this.mLastListener = next;
            }
        }
    }

    public void removeOnOtherPopupShowedListener(OnOtherPopupShowedListener onOtherPopupShowedListener) {
        this.mListeners.remove(onOtherPopupShowedListener);
    }

    public void setOnOtherPopupShowedListener(OnOtherPopupShowedListener onOtherPopupShowedListener) {
        if (!this.mListeners.contains(onOtherPopupShowedListener)) {
            this.mListeners.add(onOtherPopupShowedListener);
        }
    }
}
