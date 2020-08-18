package com.android.camera.fragment.bottom;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.ui.ColorActivateTextView;

public class LiveStickerMenu extends AbBottomMenu implements View.OnClickListener {
    private static final int LIVE_STICKER_TYPE = 0;
    private SparseArray<MenuItem> mLiveStickerMenuTabList;
    private SparseArray<ColorActivateTextView> mMenuTextViewList;

    public LiveStickerMenu(Context context, LinearLayout linearLayout, BeautyMenuAnimator beautyMenuAnimator) {
        super(context, linearLayout, beautyMenuAnimator);
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void addAllView() {
        this.mMenuTextViewList = new SparseArray<>();
        SparseArray<MenuItem> menuData = getMenuData();
        for (int i = 0; i < menuData.size(); i++) {
            MenuItem valueAt = menuData.valueAt(i);
            ColorActivateTextView colorActivateTextView = (ColorActivateTextView) LayoutInflater.from(((AbBottomMenu) this).mContext).inflate(R.layout.beauty_menu_select_item, (ViewGroup) ((AbBottomMenu) this).mContainerView, false);
            colorActivateTextView.setNormalCor(-1711276033);
            colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_NORMAL);
            colorActivateTextView.setText(valueAt.text);
            colorActivateTextView.setTag(Integer.valueOf(valueAt.type));
            colorActivateTextView.setOnClickListener(this);
            if (valueAt.type == 0) {
                colorActivateTextView.setActivated(true);
                ((AbBottomMenu) this).mCurrentBeautyTextView = colorActivateTextView;
            } else {
                colorActivateTextView.setActivated(false);
            }
            this.mMenuTextViewList.put(valueAt.type, colorActivateTextView);
            ((AbBottomMenu) this).mContainerView.addView(colorActivateTextView);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public SparseArray<ColorActivateTextView> getChildMenuViewList() {
        return this.mMenuTextViewList;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public int getDefaultType() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public SparseArray<MenuItem> getMenuData() {
        SparseArray<MenuItem> sparseArray = this.mLiveStickerMenuTabList;
        if (sparseArray != null) {
            return sparseArray;
        }
        this.mLiveStickerMenuTabList = new SparseArray<>();
        MenuItem menuItem = new MenuItem();
        menuItem.type = 0;
        menuItem.text = CameraAppImpl.getAndroidContext().getString(R.string.live_sticker_tab_name);
        menuItem.number = 0;
        this.mLiveStickerMenuTabList.put(0, menuItem);
        return this.mLiveStickerMenuTabList;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public boolean isRefreshUI() {
        return true;
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void switchMenu() {
        ((AbBottomMenu) this).mContainerView.removeAllViews();
        addAllView();
        selectBeautyType(getDefaultType());
    }
}
