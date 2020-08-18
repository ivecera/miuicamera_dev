package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.miui.filtersdk.beauty.BeautyParameterType;

@Deprecated
public class LegacyMakeupParamsFragment extends MakeupParamsFragment {
    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public AdapterView.OnItemClickListener initOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.beauty.LegacyMakeupParamsFragment.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ((BaseBeautyMakeupFragment) LegacyMakeupParamsFragment.this).mSelectedParam = i;
                if (i == 0) {
                    BeautyParameterType beautyParameterType = BeautyParameterType.SHRINK_FACE_RATIO;
                } else if (i == 1) {
                    BeautyParameterType beautyParameterType2 = BeautyParameterType.WHITEN_STRENGTH;
                } else if (i != 2) {
                    BeautyParameterType beautyParameterType3 = BeautyParameterType.WHITEN_STRENGTH;
                } else {
                    BeautyParameterType beautyParameterType4 = BeautyParameterType.SMOOTH_STRENGTH;
                }
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                if (makeupProtocol != null) {
                    makeupProtocol.onMakeupItemSelected(null, true);
                }
            }
        };
    }
}
