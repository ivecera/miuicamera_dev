package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;
import com.android.camera.R;
import com.android.camera.data.data.TypeItem;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import java.util.HashMap;
import java.util.List;

public class RemodelingParamsFragment extends MakeupParamsFragment {
    public /* synthetic */ void a(AdapterView adapterView, View view, int i, long j) {
        ((BaseBeautyMakeupFragment) this).mSelectedParam = i;
        String str = ((BaseBeautyMakeupFragment) this).mItemList.get(i).mKeyOrType;
        ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
        if (makeupProtocol != null) {
            makeupProtocol.onMakeupItemSelected(str, true);
            CameraStatUtils.trackBeautyClick("4", str);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.MakeupParamsFragment, com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getShineType() {
        return "4";
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.MakeupParamsFragment, com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void initExtraType() {
        ((BaseBeautyMakeupFragment) this).mHeaderElement = 1;
        ((BaseBeautyMakeupFragment) this).mFooterElement = -1;
        List<TypeItem> list = ((BaseBeautyMakeupFragment) this).mItemList;
        if (list != null && !list.isEmpty() && "pref_beautify_skin_smooth_ratio_key".equals(((BaseBeautyMakeupFragment) this).mItemList.get(0).mKeyOrType)) {
            ((BaseBeautyMakeupFragment) this).mFooterElement = 2;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public AdapterView.OnItemClickListener initOnItemClickListener() {
        return new a(this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.MakeupParamsFragment, com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onClearClick() {
        super.onClearClick();
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_FACE_CLEAR);
        MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_FACE, hashMap);
        toast(getResources().getString(R.string.beauty_clear_toast));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.MakeupParamsFragment, com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onResetClick() {
        super.onResetClick();
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_FACE_RESET);
        MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_FACE, hashMap);
    }
}
