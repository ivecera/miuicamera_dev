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
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorActivateTextView;

public class BackBeautyMenu extends AbBottomMenu implements View.OnClickListener {
    private SparseArray<MenuItem> mBackBeautyMenuTabList;
    private SparseArray<ColorActivateTextView> mMenuTextViewList;

    public BackBeautyMenu(Context context, LinearLayout linearLayout, BeautyMenuAnimator beautyMenuAnimator) {
        super(context, linearLayout, beautyMenuAnimator);
    }

    private boolean isJustBeautyTab() {
        return false;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void addAllView() {
        this.mMenuTextViewList = new SparseArray<>();
        SparseArray<MenuItem> menuData = getMenuData();
        for (int i = 0; i < menuData.size(); i++) {
            MenuItem valueAt = menuData.valueAt(i);
            if (!isJustBeautyTab() || valueAt.type == 1) {
                ColorActivateTextView colorActivateTextView = (ColorActivateTextView) LayoutInflater.from(((AbBottomMenu) this).mContext).inflate(R.layout.beauty_menu_select_item, (ViewGroup) ((AbBottomMenu) this).mContainerView, false);
                colorActivateTextView.setNormalCor(-1711276033);
                if (isJustBeautyTab()) {
                    colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_NORMAL);
                } else {
                    colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_SELECTED);
                }
                colorActivateTextView.setText(valueAt.text);
                colorActivateTextView.setTag(Integer.valueOf(valueAt.type));
                colorActivateTextView.setOnClickListener(this);
                if (1 == valueAt.type) {
                    colorActivateTextView.setActivated(true);
                    ((AbBottomMenu) this).mCurrentBeautyTextView = colorActivateTextView;
                } else {
                    colorActivateTextView.setActivated(false);
                }
                this.mMenuTextViewList.put(valueAt.type, colorActivateTextView);
                ((AbBottomMenu) this).mContainerView.addView(colorActivateTextView);
            }
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
        return 1;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public SparseArray<MenuItem> getMenuData() {
        SparseArray<MenuItem> sparseArray = this.mBackBeautyMenuTabList;
        if (sparseArray != null) {
            return sparseArray;
        }
        this.mBackBeautyMenuTabList = new SparseArray<>();
        String string = CameraAppImpl.getAndroidContext().getString(R.string.beauty_fragment_tab_name_3d_beauty);
        MenuItem menuItem = new MenuItem();
        menuItem.type = 1;
        menuItem.text = string;
        menuItem.number = 0;
        this.mBackBeautyMenuTabList.put(1, menuItem);
        if (DataRepository.dataItemFeature().isSupportBeautyBody()) {
            String string2 = CameraAppImpl.getAndroidContext().getString(R.string.beauty_body);
            MenuItem menuItem2 = new MenuItem();
            menuItem2.type = 5;
            menuItem2.text = string2;
            menuItem2.number = 1;
            this.mBackBeautyMenuTabList.put(5, menuItem2);
        }
        return this.mBackBeautyMenuTabList;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public boolean isRefreshUI() {
        return true;
    }

    public void onClick(View view) {
        if (isClickEnable()) {
            selectBeautyType(((Integer) view.getTag()).intValue());
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.hideQrCodeTip();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void switchMenu() {
        ((AbBottomMenu) this).mContainerView.removeAllViews();
        addAllView();
        selectBeautyType(getDefaultType());
    }
}
