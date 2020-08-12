package com.android.camera.fragment.beauty;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.EyeLightConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.beauty.EyeLightSingleCheckAdapter;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import d.h.a.A;
import d.h.a.C;
import java.util.Arrays;
import java.util.List;

public class BeautyEyeLightFragment extends BaseBeautyFragment implements View.OnClickListener {
    private static final String DEFAULT_TYPE = "1";
    private static List<EyeLightSingleCheckAdapter.EyeLightItem> EYE_LIGHT_TYPE_LIST = Arrays.asList(new EyeLightSingleCheckAdapter.EyeLightItem("-1", EyeLightConstant.getDrawable("-1"), EyeLightConstant.getString("-1")), new EyeLightSingleCheckAdapter.EyeLightItem("1", EyeLightConstant.getDrawable("1"), EyeLightConstant.getString("1")), new EyeLightSingleCheckAdapter.EyeLightItem("4", EyeLightConstant.getDrawable("4"), EyeLightConstant.getString("4")), new EyeLightSingleCheckAdapter.EyeLightItem("0", EyeLightConstant.getDrawable("0"), EyeLightConstant.getString("0")), new EyeLightSingleCheckAdapter.EyeLightItem("5", EyeLightConstant.getDrawable("5"), EyeLightConstant.getString("5")), new EyeLightSingleCheckAdapter.EyeLightItem("6", EyeLightConstant.getDrawable("6"), EyeLightConstant.getString("6")), new EyeLightSingleCheckAdapter.EyeLightItem("3", EyeLightConstant.getDrawable("3"), EyeLightConstant.getString("3")), new EyeLightSingleCheckAdapter.EyeLightItem("2", EyeLightConstant.getDrawable("2"), EyeLightConstant.getString("2")));
    private static final String TAG = "BeautyEyeLightFragment";
    private EyeLightSingleCheckAdapter mAdapter;
    private int mBackButtonWidth;
    private View mBackView;
    private int mItemWidth;
    private LinearLayoutManagerWrapper mLayoutManager;
    private RecyclerView mRecyclerView;
    private int mSelectPosition;
    private int mTotalWidth;

    private void eyeLightMutexGroup(String str, ModeProtocol.ConfigChanges configChanges) {
        ModeProtocol.TopAlert topAlert;
        if (configChanges != null && !"-1".equals(str) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && DataRepository.dataItemRunning().isSwitchOn("pref_camera_groupshot_mode_key")) {
            configChanges.onConfigChanged(235);
            topAlert.refreshExtraMenu();
        }
    }

    public static int getEyeLightHintText(String str) {
        for (int i = 0; i < EYE_LIGHT_TYPE_LIST.size(); i++) {
            EyeLightSingleCheckAdapter.EyeLightItem eyeLightItem = EYE_LIGHT_TYPE_LIST.get(i);
            if (str.equals(eyeLightItem.getType())) {
                return eyeLightItem.getTextResource();
            }
        }
        return 0;
    }

    private static boolean hasFrontFlash() {
        return DataRepository.dataItemConfig().getComponentFlash().isHardwareSupported();
    }

    private void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (i <= 0 || bottomPopupTips.containTips(i)) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            this.mAdapter.notifyItemChanged(i);
        }
        if (i2 > -1) {
            this.mAdapter.notifyItemChanged(i2);
        }
    }

    /* access modifiers changed from: private */
    public void onItemSelected(int i) {
        int i2 = this.mSelectPosition;
        this.mSelectPosition = i;
        String type = EYE_LIGHT_TYPE_LIST.get(i).getType();
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.setEyeLight(type);
        }
        if (scrollIfNeed(this.mSelectPosition)) {
            notifyItemChanged(i2, this.mSelectPosition);
        }
        eyeLightMutexGroup(type, configChanges);
    }

    private void reSelectItem() {
        String eyeLightType = CameraSettings.getEyeLightType();
        if ("-1".equals(eyeLightType)) {
            eyeLightType = "1";
        }
        int i = 0;
        while (true) {
            if (i >= EYE_LIGHT_TYPE_LIST.size()) {
                break;
            } else if (eyeLightType.equals(EYE_LIGHT_TYPE_LIST.get(i).getType())) {
                onItemSelected(i);
                break;
            } else {
                i++;
            }
        }
        this.mAdapter.setSelectedPosition(this.mSelectPosition);
        setItemInCenter(this.mSelectPosition);
        this.mAdapter.notifyDataSetChanged();
    }

    private boolean scrollIfNeed(int i) {
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = this.mLayoutManager;
        if (linearLayoutManagerWrapper == null) {
            String str = TAG;
            Log.e(str, "LinearLayoutManager is NULL!! current fragment isAdd:" + isAdded(), new Exception());
            return false;
        }
        int max = (i == linearLayoutManagerWrapper.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) ? Math.max(0, i - 1) : (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) ? Math.min(i + 1, this.mLayoutManager.getItemCount() - 1) : i;
        if (max == i) {
            return false;
        }
        this.mLayoutManager.scrollToPosition(max);
        return true;
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, ((this.mTotalWidth / 2) - (this.mItemWidth / 2)) - this.mBackButtonWidth);
    }

    private void updateTipMessage(int i, @StringRes int i2, int i3) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).showTips(i, i2, i3);
    }

    public void enterAnim(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setTranslationX(100.0f);
        view.setAlpha(0.0f);
        ViewCompat.animate(view).translationX(0.0f).alpha(1.0f).setDuration(280).setInterpolator(new C()).setListener(viewPropertyAnimatorListener).setStartDelay(120).start();
    }

    public void exitAnim(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setTranslationX(0.0f);
        view.setAlpha(1.0f);
        ViewCompat.animate(view).translationX(100.0f).alpha(0.0f).setDuration(120).setStartDelay(0).setInterpolator(new A()).setListener(viewPropertyAnimatorListener).start();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return null;
    }

    public final String getFragmentTag() {
        return BeautyEyeLightFragment.class.getSimpleName();
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.eye_light_item_width);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mBackButtonWidth = getResources().getDimensionPixelSize(R.dimen.fragment_eye_light_back_width);
        this.mBackView = view.findViewById(R.id.eye_light_back_button);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.eye_light_type_list);
        this.mRecyclerView.setFocusable(false);
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "eye_light_type_list");
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("LayoutManager init status:");
        sb.append(this.mLayoutManager != null);
        Log.d(str, sb.toString());
        this.mLayoutManager.setOrientation(0);
        this.mAdapter = new EyeLightSingleCheckAdapter(getContext(), EYE_LIGHT_TYPE_LIST, 0);
        final boolean isLayoutRTL = Util.isLayoutRTL(getContext());
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            /* class com.android.camera.fragment.beauty.BeautyEyeLightFragment.AnonymousClass1 */
            final int mLeftMargin = BeautyEyeLightFragment.this.getResources().getDimensionPixelSize(R.dimen.eye_light_item_margin_left);
            final int mRightMargin = BeautyEyeLightFragment.this.getResources().getDimensionPixelSize(R.dimen.eye_light_item_margin_right);

            @Override // android.support.v7.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                if (isLayoutRTL) {
                    if (childAdapterPosition == 0) {
                        rect.set(this.mRightMargin, 0, this.mLeftMargin, 0);
                    } else {
                        rect.set(this.mRightMargin, 0, 0, 0);
                    }
                } else if (childAdapterPosition == 0) {
                    rect.set(this.mLeftMargin, 0, this.mRightMargin, 0);
                } else {
                    rect.set(0, 0, this.mRightMargin, 0);
                }
            }
        });
        this.mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.beauty.BeautyEyeLightFragment.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BeautyEyeLightFragment.this.onItemSelected(i);
            }
        });
        this.mBackView.setOnClickListener(this);
    }

    public void onClick(View view) {
        ModeProtocol.MakeupProtocol makeupProtocol;
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if ((cameraAction == null || !cameraAction.isDoingAction()) && view.getId() == R.id.eye_light_back_button && (makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180)) != null) {
            makeupProtocol.onMakeupItemSelected("pref_eye_light_type_key", true);
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_eye_light, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        reSelectItem();
    }

    public void userInvisibleHit() {
        if (hasFrontFlash()) {
            hideTipMessage(R.string.hint_eye_light);
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (baseDelegate == null || miBeautyProtocol == null) {
        }
    }

    public void userVisibleHint() {
        if (hasFrontFlash()) {
            updateTipMessage(10, R.string.hint_eye_light, 2);
        }
        if (((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) == null) {
        }
    }
}
