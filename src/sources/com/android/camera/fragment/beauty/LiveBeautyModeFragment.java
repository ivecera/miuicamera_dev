package com.android.camera.fragment.beauty;

import android.support.v7.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.module.ModuleManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;

public class LiveBeautyModeFragment extends BaseBeautyMakeupFragment {
    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getClassString() {
        return LiveBeautyModeFragment.class.getSimpleName();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public int getListItemMargin() {
        return super.getListItemMargin();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getShineType() {
        return ComponentRunningShine.SHINE_LIVE_BEAUTY;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void initExtraType() {
        ((BaseBeautyMakeupFragment) this).mHeaderElement = 1;
        ((BaseBeautyMakeupFragment) this).mHeaderCustomWidth = getResources().getDimensionPixelSize(R.dimen.live_beauty_list_heard_width);
        ((BaseBeautyMakeupFragment) this).mFooterElement = -1;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onAdapterItemClick(TypeItem typeItem) {
        ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
        if (makeupProtocol != null) {
            makeupProtocol.onMakeupItemSelected(typeItem.mKeyOrType, true);
        }
        if (ModuleManager.isLiveModule()) {
            CameraStatUtils.trackLiveBeautyCounter(typeItem.mKeyOrType);
        } else if (ModuleManager.isMiLiveModule()) {
            CameraStatUtils.trackMiLiveBeautyCounter(typeItem.mKeyOrType);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onClearClick() {
        ShineHelper.clearBeauty();
        selectFirstItem();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onResetClick() {
        ShineHelper.resetBeauty();
        selectFirstItem();
        toast(getResources().getString(R.string.beauty_reset_toast));
        if (ModuleManager.isLiveModule()) {
            CameraStatUtils.trackLiveBeautyCounter(BeautyConstant.BEAUTY_RESET);
        } else if (ModuleManager.isMiLiveModule()) {
            CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_BEAUTY_RESET);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void setListPadding(RecyclerView recyclerView) {
        super.setListPadding(recyclerView);
        if (recyclerView != null) {
            recyclerView.setPadding(0, 0, 0, 0);
        }
    }
}
