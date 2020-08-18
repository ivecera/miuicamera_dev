package com.android.camera.fragment.beauty;

import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.data.TypeItem;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import java.util.HashMap;

public class MakeupBeautyFragment extends BaseBeautyMakeupFragment {
    private static final String TAG = "MakeupBeautyFragment";

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getClassString() {
        return MakeupBeautyFragment.class.getSimpleName();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getShineType() {
        return "5";
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void initExtraType() {
        ((BaseBeautyMakeupFragment) this).mHeaderElement = 1;
        ((BaseBeautyMakeupFragment) this).mFooterElement = -1;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onAdapterItemClick(TypeItem typeItem) {
        ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
        if (makeupProtocol != null) {
            makeupProtocol.onMakeupItemSelected(typeItem.mKeyOrType, true);
        }
        CameraStatUtils.trackBeautyClick("5", typeItem.mKeyOrType);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onClearClick() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_MAKEUP_CLEAR);
        MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_FACE, hashMap);
        CameraSettings.resetEyeLight();
        ShineHelper.clearBeauty();
        selectFirstItem();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onResetClick() {
        CameraSettings.resetEyeLight();
        ShineHelper.resetBeauty();
        selectFirstItem();
        CameraStatUtils.trackBeautyClick("5", BeautyConstant.BEAUTY_RESET);
        toast(getResources().getString(R.string.makeup_reset_toast));
    }
}
