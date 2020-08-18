package com.android.camera.fragment.top;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import d.h.a.D;
import d.h.a.y;
import d.h.a.z;
import io.reactivex.Completable;
import java.util.HashMap;
import java.util.List;

public class FragmentTopConfigExtra extends BaseFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 245;
    private static final String TAG = "FragmentTopConfigExtra";
    public static final int TOP_CONFIG_EXTRA_ANIM_OUT_ALPHA_TIME = 120;
    private View mBackgroundView;
    private ExtraAdapter mExtraAdapter;
    private RecyclerView mRecyclerView;

    private void addContentDescriptionForConfigs(int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        StringBuilder sb = new StringBuilder();
        switch (i) {
            case 219:
                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, "reference_line", null);
                boolean z = DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false);
                sb.append(getString(R.string.pref_camera_referenceline_title));
                if (!z) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 226:
                ComponentRunningTimer componentRunningTimer = dataItemRunning.getComponentRunningTimer();
                boolean isSwitchOn = componentRunningTimer.isSwitchOn();
                sb.append(getString(componentRunningTimer.getValueDisplayString(160)));
                if (!isSwitchOn) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                }
                break;
            case 228:
                ComponentRunningTiltValue componentRunningTiltValue = dataItemRunning.getComponentRunningTiltValue();
                if (!dataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode")) {
                    sb.append(getString(R.string.config_name_tilt));
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(componentRunningTiltValue.getValueDisplayString(160)));
                    break;
                }
            case 229:
                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.FeatureName.VALUE_TOPMENU_GRADIENTER, null);
                boolean isGradienterOn = CameraSettings.isGradienterOn();
                sb.append(getString(R.string.config_name_straighten));
                if (!isGradienterOn) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 230:
                boolean isSwitchOn2 = dataItemRunning.isSwitchOn("pref_camera_hand_night_key");
                sb.append(getString(R.string.config_name_hht));
                if (!isSwitchOn2) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 231:
                boolean isSwitchOn3 = dataItemRunning.isSwitchOn("pref_camera_ubifocus_key");
                sb.append(getString(R.string.config_name_magic));
                if (!isSwitchOn3) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 233:
                boolean isSwitchOn4 = dataItemRunning.isSwitchOn("pref_video_speed_fast_key");
                sb.append(getString(R.string.pref_video_speed_fast_title));
                if (!isSwitchOn4) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 234:
                boolean isSwitchOn5 = dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key");
                sb.append(getString(R.string.config_name_scene));
                if (!isSwitchOn5) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 235:
                boolean isSwitchOn6 = dataItemRunning.isSwitchOn("pref_camera_groupshot_mode_key");
                sb.append(getString(R.string.config_name_group));
                if (!isSwitchOn6) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 236:
                boolean isSwitchOn7 = dataItemRunning.isSwitchOn("pref_camera_magic_mirror_key");
                sb.append(getString(R.string.pref_camera_magic_mirror_title));
                if (!isSwitchOn7) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 238:
                boolean isSwitchOn8 = dataItemRunning.isSwitchOn("pref_camera_show_gender_age_key");
                sb.append(getString(R.string.pref_camera_show_gender_age_config_title));
                if (!isSwitchOn8) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 239:
                int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
                ComponentConfigBeauty componentConfigBeauty = dataItemConfig.getComponentConfigBeauty();
                boolean isSwitchOn9 = componentConfigBeauty.isSwitchOn(currentMode);
                sb.append(getString(componentConfigBeauty.getValueDisplayString(currentMode)));
                if (!isSwitchOn9) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
            case 240:
                boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
                sb.append(getString(R.string.pref_camera_device_watermark_title));
                if (!isDualCameraWaterMarkOpen) {
                    sb.append(getString(R.string.accessibility_closed));
                    break;
                } else {
                    sb.append(getString(R.string.accessibility_open));
                    break;
                }
        }
        if (!TextUtils.isEmpty(sb) && Util.isAccessible()) {
            this.mRecyclerView.setContentDescription(sb);
            this.mRecyclerView.sendAccessibilityEvent(4);
        }
    }

    private void adjustViewBackground() {
        if (DataRepository.dataItemRunning().getUiStyle() != 3) {
            this.mBackgroundView.setBackgroundResource(R.color.black);
        } else {
            this.mBackgroundView.setBackgroundResource(R.color.fullscreen_background);
        }
    }

    public void animateIn() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        scaleAnimation.setInterpolator(new y());
        scaleAnimation.setDuration(320);
        this.mBackgroundView.startAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setInterpolator(new z());
        alphaAnimation.setStartOffset(150);
        alphaAnimation.setDuration(280);
        this.mRecyclerView.startAnimation(alphaAnimation);
    }

    public void animateOut() {
        AnonymousClass1 r0 = new Animation.AnimationListener() {
            /* class com.android.camera.fragment.top.FragmentTopConfigExtra.AnonymousClass1 */

            public void onAnimationEnd(Animation animation) {
                if (FragmentTopConfigExtra.this.canProvide()) {
                    FragmentUtils.removeFragmentByTag(FragmentTopConfigExtra.this.getFragmentManager(), FragmentTopConfigExtra.this.getFragmentTag());
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                FragmentTopConfigExtra.this.setClickEnable(false);
            }
        };
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
        scaleAnimation.setInterpolator(new y());
        scaleAnimation.setDuration(320);
        scaleAnimation.setAnimationListener(r0);
        this.mBackgroundView.startAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setInterpolator(new D());
        alphaAnimation.setDuration(120);
        alphaAnimation.setFillAfter(true);
        this.mRecyclerView.startAnimation(alphaAnimation);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 245;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_top_config_extra;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mBackgroundView = view.findViewById(R.id.top_config_extra_background);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.top_config_extra_recyclerview);
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        SupportedConfigs supportedExtraConfigs = SupportedConfigFactory.getSupportedExtraConfigs(((BaseFragment) this).mCurrentMode, currentCameraId, DataRepository.dataCloudMgr().DataCloudFeature(), Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(currentCameraId, ((BaseFragment) this).mCurrentMode), DataRepository.dataItemGlobal().isNormalIntent());
        int integer = getResources().getInteger(R.integer.back_top_extra_column_count);
        int integer2 = getResources().getInteger(R.integer.front_top_extra_column_count);
        if (DataRepository.dataItemGlobal().getDisplayMode() == 2) {
            integer = integer2;
        }
        int max = Math.max(1, supportedExtraConfigs.getLength() > integer ? integer : supportedExtraConfigs.getLength());
        int ceil = (int) Math.ceil((double) (((float) supportedExtraConfigs.getLength()) / ((float) max)));
        int round = Math.round((float) ((Util.sWindowWidth - (Util.dpToPixel(8.0f) * 2)) / integer));
        int i = round * ceil;
        float f2 = Util.sTopMargin == 0 ? 0.022916667f : 0.039583333f;
        int round2 = Math.round(((float) Util.sCenterDisplayHeight) * f2);
        Log.d(TAG, "initView: RecyclerView margin = " + round2 + ", ratio = " + f2 + ", height = " + i + ", itemHeight = " + round + ", columnCount = " + max + ", lineCount = " + ceil);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mRecyclerView.getLayoutParams();
        marginLayoutParams.topMargin = round2;
        marginLayoutParams.bottomMargin = round2;
        marginLayoutParams.height = i;
        provideAnimateElement(((BaseFragment) this).mCurrentMode, null, 2);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), max));
        this.mExtraAdapter = new ExtraAdapter(supportedExtraConfigs, this, round);
        this.mExtraAdapter.setNewDegree(((BaseFragment) this).mDegree);
        this.mRecyclerView.setAdapter(this.mExtraAdapter);
        this.mRecyclerView.setFocusable(false);
        adjustViewBackground();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        adjustViewBackground();
        reFresh();
    }

    public void onClick(View view) {
        ModeProtocol.ConfigChanges configChanges;
        if (isEnableClick() && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            int intValue = ((Integer) view.getTag()).intValue();
            configChanges.onConfigChanged(intValue);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (Util.isAccessible()) {
                this.mExtraAdapter.setOnClictTag(intValue);
            }
            if (intValue == 225) {
                topAlert.removeExtraMenu(5);
            } else if (intValue != 242) {
                this.mRecyclerView.getAdapter().notifyDataSetChanged();
            } else {
                topAlert.hideExtraMenu();
                ((ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193)).startAiLens();
                HashMap hashMap = new HashMap();
                hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.AlgoAttr.VAULE_START_AI_DETECT_BUTTON);
                MistatsWrapper.mistatEvent(MistatsConstants.GoogleLens.KEY_GOOGLE_LENS, hashMap);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        animateIn();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i == 165) {
            this.mBackgroundView.setBackgroundResource(R.color.black);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return null;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        this.mExtraAdapter.setNewDegree(i);
        for (int i2 = 0; i2 < this.mRecyclerView.getChildCount(); i2++) {
            list.add(this.mRecyclerView.getChildAt(i2));
        }
    }

    public void reFresh() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            this.mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
