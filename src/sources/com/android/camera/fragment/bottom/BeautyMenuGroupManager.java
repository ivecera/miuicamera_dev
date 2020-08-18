package com.android.camera.fragment.bottom;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

@Deprecated
public class BeautyMenuGroupManager {
    private BeautyMenuAnimator mBeautyMenuAnimator;
    private SparseArray<AbBottomMenu> mBeautyMenuList;
    private AbBottomMenu mBottomMenu;
    private LinearLayout mContainerView;
    private Context mContext;
    private int mCurrentMenuGroupType = 160;

    public BeautyMenuGroupManager(Context context, LinearLayout linearLayout) {
        this.mContainerView = linearLayout;
        this.mContext = context;
        this.mBeautyMenuAnimator = BeautyMenuAnimator.animator(this.mContainerView);
        initView();
    }

    private void initView() {
        updateCurrentMenu(getCurrentBeautyMenuType());
        this.mBottomMenu.addAllView();
    }

    private void updateCurrentMenu(int i) {
        AbBottomMenu abBottomMenu;
        SparseArray<AbBottomMenu> sparseArray = this.mBeautyMenuList;
        if (sparseArray == null || (abBottomMenu = sparseArray.get(i)) == null) {
            this.mBeautyMenuList = new SparseArray<>();
            switch (i) {
                case 161:
                    FrontBeautyMenu frontBeautyMenu = new FrontBeautyMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = frontBeautyMenu;
                    this.mBeautyMenuList.put(i, frontBeautyMenu);
                    return;
                case 162:
                    BackBeautyMenu backBeautyMenu = new BackBeautyMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = backBeautyMenu;
                    this.mBeautyMenuList.put(i, backBeautyMenu);
                    return;
                case 163:
                    LiveBeautyMenu liveBeautyMenu = new LiveBeautyMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = liveBeautyMenu;
                    this.mBeautyMenuList.put(i, liveBeautyMenu);
                    return;
                case 164:
                    LiveStickerMenu liveStickerMenu = new LiveStickerMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = liveStickerMenu;
                    this.mBeautyMenuList.put(i, liveStickerMenu);
                    return;
                case 165:
                    LiveSpeedMenu liveSpeedMenu = new LiveSpeedMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = liveSpeedMenu;
                    this.mBeautyMenuList.put(i, liveSpeedMenu);
                    return;
                case 166:
                    KaleidoscopeMenu kaleidoscopeMenu = new KaleidoscopeMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = kaleidoscopeMenu;
                    this.mBeautyMenuList.put(i, kaleidoscopeMenu);
                    return;
                default:
                    FrontBeautyMenu frontBeautyMenu2 = new FrontBeautyMenu(this.mContext, this.mContainerView, this.mBeautyMenuAnimator);
                    this.mBottomMenu = frontBeautyMenu2;
                    this.mBeautyMenuList.put(i, frontBeautyMenu2);
                    return;
            }
        } else {
            this.mBottomMenu = abBottomMenu;
        }
    }

    public void animateExpanding(boolean z) {
        if (z) {
            this.mBeautyMenuAnimator.expandAnimate();
        } else {
            this.mBeautyMenuAnimator.shrinkAnimate();
        }
    }

    public AbBottomMenu getBottomMenu() {
        return this.mBottomMenu;
    }

    public int getCurrentBeautyMenuType() {
        return this.mCurrentMenuGroupType;
    }

    public View getView() {
        return this.mContainerView;
    }

    public void setCurrentBeautyMenuType(int i) {
        this.mCurrentMenuGroupType = i;
        updateCurrentMenu(i);
    }

    public void setVisibility(int i) {
        LinearLayout linearLayout = this.mContainerView;
        if (linearLayout != null) {
            linearLayout.setVisibility(i);
        }
    }

    public void switchMenu() {
        this.mBottomMenu.switchMenu();
    }
}
