package com.android.camera.fragment.manually;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.dialog.BaseDialogFragment;
import com.android.camera.fragment.manually.adapter.ParameterDescriptionAdapter;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentParameterDescription extends BaseDialogFragment {
    public static final String TAG = "FragmentParameterDescription";
    private int mCurrentMode;
    private List<ComponentDataItem> mManuallyDataItems;
    private List<ComponentDataItem> mProVideoDataItems;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public View mSplitView;

    private List<ComponentDataItem> getParameterData() {
        List<ComponentDataItem> list;
        List<ComponentDataItem> list2;
        if (this.mCurrentMode == 167 && (list2 = this.mManuallyDataItems) != null) {
            return list2;
        }
        if (this.mCurrentMode == 180 && (list = this.mProVideoDataItems) != null) {
            return list;
        }
        CameraCapabilities capabilitiesByBogusCameraId = Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), this.mCurrentMode);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_parameter_metering, -1, (int) R.string.parameter_metering_title, getContext().getString(R.string.parameter_metering_description)));
        int i = this.mCurrentMode;
        if (i == 167) {
            if (b.dm()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_parameter_peak_focus, -1, (int) R.string.parameter_peak_focus_title, getContext().getString(R.string.parameter_peak_focus_description)));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_parameter_exposure_feedback, -1, (int) R.string.parameter_exposure_feecback_title, getContext().getString(R.string.parameter_exposure_feecback_description)));
            }
            if (DataRepository.dataItemFeature().c_r_i_m_m() && capabilitiesByBogusCameraId.isSupportRaw()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_parameter_raw, -1, (int) R.string.parameter_peak_raw_title, getContext().getString(R.string.parameter_peak_raw_description)));
            }
        } else if (i == 180) {
            arrayList.addAll(Arrays.asList(new ComponentDataItem((int) R.drawable.ic_parameter_peak_focus, -1, (int) R.string.parameter_peak_focus_title, getContext().getString(R.string.parameter_peak_focus_description)), new ComponentDataItem((int) R.drawable.ic_parameter_exposure_feedback, -1, (int) R.string.parameter_exposure_feecback_title, getContext().getString(R.string.parameter_exposure_feecback_description)), new ComponentDataItem((int) R.drawable.ic_parameter_log, -1, (int) R.string.parameter_log_format_title, getContext().getString(R.string.parameter_log_format_description)), new ComponentDataItem((int) R.drawable.ic_parameter_histogram, -1, (int) R.string.parameter_histogram_title, getContext().getString(R.string.parameter_histogram_description)), new ComponentDataItem((int) R.drawable.ic_parameter_slider, -1, (int) R.string.parameter_zoom_slider_title, getContext().getString(R.string.parameter_zoom_slider_description))));
        }
        String str = getContext().getString(R.string.parameter_lens_description) + getContext().getString(R.string.parameter_lens_w);
        if (DataRepository.dataItemFeature().isSupportUltraWide()) {
            str = str + getContext().getString(R.string.parameter_lens_uw);
        }
        if (DataRepository.dataItemFeature().c_0x50() && DataRepository.dataItemFeature().jf()) {
            str = str + getContext().getString(R.string.parameter_lens_marco);
        }
        if (CameraSettings.isSupportedOpticalZoom()) {
            if (DataRepository.dataItemFeature().c_22367_0x0001()) {
                str = str + getContext().getString(R.string.parameter_lens_tele_2x);
            } else if (Camera2DataContainer.getInstance().getAuxCameraId() >= 0) {
                str = str + getContext().getString(R.string.parameter_lens_t);
            }
        }
        if (DataRepository.dataItemFeature().c_22367_0x0001()) {
            str = str + getContext().getString(R.string.parameter_lens_tele_5x);
        }
        arrayList.addAll(Arrays.asList(new ComponentDataItem((int) R.drawable.ic_parameter_ef_split, -1, (int) R.string.parameter_ef_split_title, getContext().getString(R.string.parameter_ef_split_description)), new ComponentDataItem((int) R.drawable.ic_parameter_wb, -1, (int) R.string.parameter_wb_title, getContext().getString(R.string.parameter_wb_description)), new ComponentDataItem((int) R.drawable.ic_parameter_focus, -1, (int) R.string.parameter_focus_title, getContext().getString(R.string.parameter_focus_description)), new ComponentDataItem((int) R.drawable.ic_parameter_et, -1, (int) R.string.parameter_et_title, getContext().getString(R.string.parameter_et_description)), new ComponentDataItem((int) R.drawable.ic_parameter_ev, -1, (int) R.string.parameter_exposure_title, getContext().getString(R.string.parameter_exposure_description)), new ComponentDataItem((int) R.drawable.ic_parameter_iso, -1, (int) R.string.parameter_iso_title, getContext().getString(R.string.parameter_iso_description))));
        if (CameraSettings.isSupportedOpticalZoom() || DataRepository.dataItemFeature().isSupportUltraWide()) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_parameter_lens, -1, (int) R.string.parameter_lens_title, str));
        }
        int i2 = this.mCurrentMode;
        if (i2 == 167) {
            this.mManuallyDataItems = arrayList;
        } else if (i2 == 180) {
            this.mProVideoDataItems = arrayList;
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        LinearLayout.LayoutParams layoutParams;
        getDialog().setCanceledOnTouchOutside(false);
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ((ImageView) view.findViewById(R.id.parameter_description_back)).setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.manually.FragmentParameterDescription.AnonymousClass1 */

            public void onClick(View view) {
                FragmentUtils.removeFragmentByTag(FragmentParameterDescription.this.getFragmentManager(), FragmentParameterDescription.TAG);
            }
        });
        this.mSplitView = view.findViewById(R.id.parameter_description_split_view);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.manually_parameter_description_list);
        this.mRecyclerView.setFocusable(false);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "parameter_recycler_view");
        linearLayoutManagerWrapper.setOrientation(1);
        this.mRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.setAdapter(new ParameterDescriptionAdapter(getContext(), getParameterData()));
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.manually.FragmentParameterDescription.AnonymousClass2 */

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                if (!FragmentParameterDescription.this.mRecyclerView.canScrollVertically(-1)) {
                    FragmentParameterDescription.this.mSplitView.setVisibility(4);
                } else if (i2 > 0) {
                    FragmentParameterDescription.this.mSplitView.setVisibility(0);
                }
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.parameter_description_title);
        int i = this.mCurrentMode;
        if (i == 167) {
            textView.setText(R.string.parameter_description_pro_capture_title);
        } else if (i == 180) {
            textView.setText(R.string.parameter_description_pro_video_title);
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null && (layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams()) != null) {
            if (!Util.isFullScreenNavBarHidden(getContext())) {
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.mimoji_edit_config_bottom);
            } else {
                layoutParams.bottomMargin = 0;
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_manually_description, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
