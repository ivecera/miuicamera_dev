package com.android.camera.fragment.top;

import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentConfigAuxiliary;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigMeter;
import com.android.camera.data.data.config.ComponentConfigUltraWide;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.config.TopConfigItem;
import com.android.camera.data.data.runing.ComponentRunningColorEnhance;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.music.FragmentLiveMusic;
import com.android.camera.fragment.top.ExpandAdapter;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.xiaomi.stat.MiStat;
import d.h.a.k;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FragmentTopConfig extends BaseFragment implements View.OnClickListener, ExpandAdapter.ExpandListener, ModeProtocol.TopAlert, ModeProtocol.HandleBackTrace, ModeProtocol.HandleBeautyRecording {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int EXPAND_STATE_CENTER = 2;
    private static final int EXPAND_STATE_LEFT = 0;
    private static final int EXPAND_STATE_LEFT_FROM_SIBLING = 1;
    private static final int EXPAND_STATE_RIGHT = 4;
    private static final int EXPAND_STATE_RIGHT_FROM_SIBLING = 3;
    private static final String TAG = "FragmentTopConfig";
    public static final int TIP_HINT_DURATION_2S = 2000;
    public static final int TIP_HINT_DURATION_3S = 3000;
    private int[] mAiSceneResources;
    private int[] mAutoZoomResources;
    private int[] mCinematicRatioResources;
    private List<ImageView> mConfigViews;
    private int mCurrentAiSceneLevel;
    private SparseBooleanArray mDisabledFunctionMenu;
    private int mDisplayRectTopMargin;
    private int[] mDocumentResources;
    private RecyclerView mExpandView;
    private int[] mFilterResources;
    private FragmentTopAlert mFragmentTopAlert;
    private FragmentTopConfigExtra mFragmentTopConfigExtra;
    private int[] mGifResource;
    private boolean mIsRTL;
    private boolean mIsShowExtraMenu;
    private boolean mIsShowTopLyingDirectHint;
    private LastAnimationComponent mLastAnimationComponent;
    private int[] mLightingResource;
    private int[] mLiveMusicSelectResources;
    private ObjectAnimator mLiveShotAnimator;
    private int[] mLiveShotResource;
    private int[] mMacroResources;
    private View mMimojiCreateLayout;
    private int[] mSuperEISResources;
    private SupportedConfigs mSupportedConfigs;
    private View mTopConfigMenu;
    private int mTopDrawableWidth;
    private ViewGroup mTopExtraParent;
    private int mTotalWidth;
    private int[] mUltraPixelPhotographyIconResources;
    private int[] mUltraPixelPhotographyTipResources;
    private String[] mUltraPixelPhotographyTipString;
    private int[] mUltraPixelPortraitResources;
    private int[] mUltraWideBokehResources;
    private int[] mUltraWideResource;
    private Map<Integer, Boolean> mUpdateTipState = new HashMap();
    private int[] mVideo8KResource;
    private int[] mVideoBokehResource;
    private boolean mVideoRecordingStarted;
    private int mViewPadding;

    private void alertHDR(int i, boolean z, boolean z2, boolean z3) {
        ImageView topImage;
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert) && !topAlert.isShowMoonSelector()) {
            if (z3) {
                if (i != 0) {
                    this.mLastAnimationComponent.reverse(true);
                } else if (z2 && (topImage = getTopImage(194)) != null) {
                    topImage.performClick();
                }
            }
            topAlert.alertHDR(i, z);
        }
    }

    private void alertTopMusicHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertMusicTip(i, str);
        }
    }

    private void configBottomPopupTips(boolean z) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        if (DataRepository.dataItemFeature().qd() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
            bottomPopupTips.showIDCardTip(z);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x010b  */
    private void expandExtra(ComponentData componentData, View view, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int right;
        int i6;
        if (!this.mLastAnimationComponent.reverse(true)) {
            ExpandAdapter expandAdapter = new ExpandAdapter(componentData, this);
            int width = Util.sWindowWidth - view.getWidth();
            expandAdapter.setMaxWidthItemView((width / componentData.getItems().size()) - (getResources().getDimensionPixelSize(R.dimen.expanded_text_item_margin) * 2));
            this.mExpandView.getLayoutParams().width = width;
            this.mExpandView.setAdapter(expandAdapter);
            int i7 = 0;
            this.mExpandView.setVisibility(0);
            this.mExpandView.setTag(Integer.valueOf(i));
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.panel_imageview_button_padding_width) * 3;
            int i8 = ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity == 8388611 ? 1 : 0;
            int i9 = i8 ^ 1;
            if (this.mIsRTL) {
                i9 = 4 - i9;
            }
            if (i9 == 0) {
                this.mLastAnimationComponent.setExpandGravity(5);
                right = view.getRight() - dimensionPixelSize;
                i5 = view.getWidth();
            } else if (i9 != 1) {
                if (i9 == 2) {
                    i4 = getView().getWidth() - view.getWidth();
                    i6 = view.getLeft();
                } else if (i9 == 3) {
                    i4 = getView().getWidth() - view.getWidth();
                    i6 = view.getLeft();
                } else if (i9 != 4) {
                    i2 = 0;
                    i4 = 0;
                    i3 = 0;
                    LastAnimationComponent lastAnimationComponent = this.mLastAnimationComponent;
                    lastAnimationComponent.mRecyclerView = this.mExpandView;
                    lastAnimationComponent.mReverseLeft = view.getLeft();
                    LastAnimationComponent lastAnimationComponent2 = this.mLastAnimationComponent;
                    lastAnimationComponent2.mReverseRecyclerViewLeft = i2;
                    lastAnimationComponent2.hideOtherViews(i, this.mConfigViews);
                    if (i8 == 0) {
                        LastAnimationComponent lastAnimationComponent3 = this.mLastAnimationComponent;
                        lastAnimationComponent3.mAnchorView = view;
                        lastAnimationComponent3.translateAnchorView(i4 - view.getLeft());
                    }
                    if (this.mIsRTL) {
                        i7 = getView().getWidth() - width;
                    }
                    this.mLastAnimationComponent.showExtraView(i2 - i7, i3 - i7);
                } else {
                    this.mLastAnimationComponent.setExpandGravity(3);
                    i4 = getView().getWidth() - view.getWidth();
                    i2 = dimensionPixelSize + (view.getLeft() - width);
                    i3 = i4 - width;
                    LastAnimationComponent lastAnimationComponent4 = this.mLastAnimationComponent;
                    lastAnimationComponent4.mRecyclerView = this.mExpandView;
                    lastAnimationComponent4.mReverseLeft = view.getLeft();
                    LastAnimationComponent lastAnimationComponent22 = this.mLastAnimationComponent;
                    lastAnimationComponent22.mReverseRecyclerViewLeft = i2;
                    lastAnimationComponent22.hideOtherViews(i, this.mConfigViews);
                    if (i8 == 0) {
                    }
                    if (this.mIsRTL) {
                    }
                    this.mLastAnimationComponent.showExtraView(i2 - i7, i3 - i7);
                }
                i2 = (i6 - width) - dimensionPixelSize;
                i3 = i4 - width;
                LastAnimationComponent lastAnimationComponent42 = this.mLastAnimationComponent;
                lastAnimationComponent42.mRecyclerView = this.mExpandView;
                lastAnimationComponent42.mReverseLeft = view.getLeft();
                LastAnimationComponent lastAnimationComponent222 = this.mLastAnimationComponent;
                lastAnimationComponent222.mReverseRecyclerViewLeft = i2;
                lastAnimationComponent222.hideOtherViews(i, this.mConfigViews);
                if (i8 == 0) {
                }
                if (this.mIsRTL) {
                }
                this.mLastAnimationComponent.showExtraView(i2 - i7, i3 - i7);
            } else {
                this.mLastAnimationComponent.setExpandGravity(3);
                right = view.getRight();
                i5 = view.getWidth();
            }
            i3 = i5 + 0;
            i4 = 0;
            LastAnimationComponent lastAnimationComponent422 = this.mLastAnimationComponent;
            lastAnimationComponent422.mRecyclerView = this.mExpandView;
            lastAnimationComponent422.mReverseLeft = view.getLeft();
            LastAnimationComponent lastAnimationComponent2222 = this.mLastAnimationComponent;
            lastAnimationComponent2222.mReverseRecyclerViewLeft = i2;
            lastAnimationComponent2222.hideOtherViews(i, this.mConfigViews);
            if (i8 == 0) {
            }
            if (this.mIsRTL) {
            }
            this.mLastAnimationComponent.showExtraView(i2 - i7, i3 - i7);
        }
    }

    private Drawable getAiSceneDrawable(int i) {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_drawables);
        Drawable drawable = null;
        if (i >= 0 && i < obtainTypedArray.length()) {
            drawable = obtainTypedArray.getDrawable(i);
        }
        obtainTypedArray.recycle();
        return drawable;
    }

    private int[] getAiSceneResources() {
        return new int[]{R.drawable.ic_new_ai_scene_off, R.drawable.ic_new_ai_scene_on};
    }

    private int[] getAutoZoomResources() {
        return new int[]{R.drawable.ic_autozoom_off, R.drawable.ic_autozoom_on};
    }

    private int[] getCinematicRatioResources() {
        return new int[]{R.drawable.ic_cinematic_aspect_ratio_off, R.drawable.ic_cinematic_aspect_ratio_on};
    }

    private int[] getDocumentResources() {
        return new int[]{R.drawable.document_mode_normal, R.drawable.document_mode_selected};
    }

    private int[] getExposureFeedbackResources() {
        return new int[]{R.drawable.ic_config_exposure_feedback_off, R.drawable.ic_config_exposure_feedback_on};
    }

    private int[] getFilterResources() {
        return new int[]{R.drawable.ic_new_effect_button_normal, R.drawable.ic_new_effect_button_selected};
    }

    @DrawableRes
    private int getFocusPeakImageResource() {
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key") ? R.drawable.ic_config_focus_peak_on : R.drawable.ic_config_focus_peak_off;
    }

    private int[] getGifRecource() {
        return new int[]{R.drawable.ic_config_gif_off, R.drawable.ic_config_gif_on};
    }

    private int getInitialMargin(TopConfigItem topConfigItem, ImageView imageView) {
        SupportedConfigs supportedConfigs = this.mSupportedConfigs;
        int configsSize = supportedConfigs == null ? 0 : supportedConfigs.getConfigsSize();
        if (configsSize <= 0) {
            return 0;
        }
        int i = topConfigItem.index;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.gravity = 0;
        if (configsSize == 1) {
            layoutParams.leftMargin = 0;
            int i2 = topConfigItem.gravity;
            if (i2 == 0) {
                i2 = 8388613;
            }
            layoutParams.gravity = i2;
            imageView.setLayoutParams(layoutParams);
            return 0;
        } else if (configsSize == 2) {
            if (i == 0) {
                layoutParams.leftMargin = 0;
                int i3 = topConfigItem.gravity;
                if (i3 == 0) {
                    i3 = 8388611;
                }
                layoutParams.gravity = i3;
            } else if (i == 1) {
                layoutParams.leftMargin = 0;
                int i4 = topConfigItem.gravity;
                if (i4 == 0) {
                    i4 = 8388613;
                }
                layoutParams.gravity = i4;
            }
            imageView.setLayoutParams(layoutParams);
            return 0;
        } else if (i == 0) {
            layoutParams.leftMargin = 0;
            layoutParams.gravity = GravityCompat.START;
            imageView.setLayoutParams(layoutParams);
            return 0;
        } else {
            int i5 = configsSize - 1;
            if (i == i5) {
                layoutParams.leftMargin = 0;
                layoutParams.gravity = GravityCompat.END;
                imageView.setLayoutParams(layoutParams);
                return 0;
            }
            int i6 = this.mTotalWidth;
            int i7 = this.mViewPadding;
            return (((i6 - (i7 * 2)) / i5) * i) + i7;
        }
    }

    private int[] getLightingResources() {
        return new int[]{R.drawable.ic_new_lighting_off, R.drawable.ic_new_lighting_on};
    }

    private int[] getLiveShotResources() {
        return new int[]{R.drawable.ic_motionphoto, R.drawable.ic_motionphoto_highlight};
    }

    private int[] getMacroResources() {
        return new int[]{R.drawable.ic_config_macro_mode_off, R.drawable.ic_config_macro_mode_on};
    }

    private int getMoreResources() {
        return R.drawable.ic_new_more;
    }

    private int[] getMusicSelectResources() {
        return new int[]{R.drawable.ic_live_music_normal, R.drawable.ic_live_music_selected};
    }

    private int getPortraitResources() {
        return R.drawable.ic_new_portrait_button_normal;
    }

    private int getSettingResources() {
        return R.drawable.ic_new_config_setting;
    }

    private int[] getSuperEISResources() {
        return new int[]{R.drawable.ic_config_super_eis_off, R.drawable.ic_config_super_eis_on};
    }

    private FragmentTopAlert getTopAlert() {
        FragmentTopAlert fragmentTopAlert = this.mFragmentTopAlert;
        if (fragmentTopAlert == null) {
            Log.d(TAG, "getTopAlert(): fragment is null");
            return null;
        } else if (fragmentTopAlert.isAdded()) {
            return this.mFragmentTopAlert;
        } else {
            Log.d(TAG, "getTopAlert(): fragment is not added yet");
            return null;
        }
    }

    private FragmentTopConfigExtra getTopExtra() {
        return (FragmentTopConfigExtra) getChildFragmentManager().findFragmentByTag(String.valueOf(245));
    }

    private int[] getUltraPixelPortraitResources() {
        return new int[]{R.drawable.ic_config_ultrapixelportrait_off, R.drawable.ic_config_ultrapixelportrait_on};
    }

    private int[] getUltraWideBokehResources() {
        return new int[]{R.drawable.ic_ultra_wide_bokeh, R.drawable.ic_ultra_wide_bokeh_highlight};
    }

    private int[] getUltraWideResources() {
        return new int[]{R.drawable.icon_config_ultra_wide_off, R.drawable.icon_config_ultra_wide_on};
    }

    private int[] getVideo8KRecource() {
        return new int[]{R.drawable.ic_config_video_8k_normal, R.drawable.ic_config_video_8k_highlight};
    }

    private int[] getVideoBokehResources() {
        return new int[]{R.drawable.ic_new_portrait_button_normal, R.drawable.ic_new_portrait_button_on};
    }

    private void initTopView() {
        ImageView imageView = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_00);
        ImageView imageView2 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_01);
        ImageView imageView3 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_02);
        ImageView imageView4 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_03);
        ImageView imageView5 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_04);
        ImageView imageView6 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_05);
        ImageView imageView7 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_06);
        ImageView imageView8 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_07);
        ImageView imageView9 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_08);
        ImageView imageView10 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_09);
        ImageView imageView11 = (ImageView) this.mTopConfigMenu.findViewById(R.id.top_config_10);
        imageView.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        imageView9.setOnClickListener(this);
        imageView10.setOnClickListener(this);
        imageView11.setOnClickListener(this);
        this.mConfigViews = new ArrayList();
        this.mConfigViews.add(imageView);
        this.mConfigViews.add(imageView2);
        this.mConfigViews.add(imageView3);
        this.mConfigViews.add(imageView4);
        this.mConfigViews.add(imageView5);
        this.mConfigViews.add(imageView6);
        this.mConfigViews.add(imageView7);
        this.mConfigViews.add(imageView8);
        this.mConfigViews.add(imageView9);
        this.mConfigViews.add(imageView10);
        this.mConfigViews.add(imageView11);
    }

    private boolean isMenuConfigShortShow(int i) {
        return !isTopConfig(i) && !isExtraMenuShowing() && getUpdateTipState(i);
    }

    private boolean isTopConfig(int i) {
        SupportedConfigs supportedConfigs = this.mSupportedConfigs;
        return supportedConfigs != null && supportedConfigs.contains(i);
    }

    private boolean isTopConfigFromModeSelected(int i) {
        return isTopConfig(i) && getUpdateTipState(i);
    }

    private void notifyExtraMenuVisibilityChange(boolean z) {
        ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
        if (topConfigProtocol != null) {
            topConfigProtocol.onExtraMenuVisibilityChange(z);
        }
    }

    private void reConfigTipOfFlash(boolean z) {
        if (!isExtraMenuShowing()) {
            ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
            if (!componentFlash.isEmpty()) {
                String componentValue = componentFlash.getComponentValue(((BaseFragment) this).mCurrentMode);
                if ("1".equals(componentValue) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
                    alertFlash(0, "1", false, z);
                } else if ("2".equals(componentValue)) {
                    alertFlash(0, "2", false, z);
                } else if ("5".equals(componentValue)) {
                    alertFlash(0, "5", false, z);
                } else {
                    alertFlash(8, "1", false, z);
                }
            }
        }
    }

    private void reConfigTipOfHdr(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty()) {
            String componentValue = componentHdr.getComponentValue(((BaseFragment) this).mCurrentMode);
            if ("on".equals(componentValue) || "normal".equals(componentValue)) {
                alertHDR(0, false, false, z);
            } else if ("live".equals(componentValue)) {
                alertHDR(0, true, false, z);
            } else {
                alertHDR(8, false, false, z);
            }
        }
    }

    private void reConfigTipOfMusicHint(boolean z) {
        int i = ((BaseFragment) this).mCurrentMode;
        if (i != 174 && i != 183) {
            alertTopMusicHint(8, null);
        } else if (!isExtraMenuShowing()) {
            String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
            if (!currentLiveMusic[1].isEmpty()) {
                alertTopMusicHint(0, currentLiveMusic[1]);
            }
        }
    }

    private void reConfigTipOfSubtitle() {
        if (DataRepository.dataItemRunning().getComponentRunningSubtitle().isEnabled(((BaseFragment) this).mCurrentMode)) {
            alertSubtitleHint(0, R.string.pref_video_subtitle);
        } else {
            alertSubtitleHint(8, R.string.pref_video_subtitle);
        }
    }

    private void reConfigTipOfSuperNightSe() {
        if (!isExtraMenuShowing()) {
            alertSuperNightSeTip(CameraSettings.isSuperNightOn() ? 0 : 8);
        }
    }

    private void resetImages() {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(((BaseFragment) this).mCurrentMode, DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().isNormalIntent());
        for (int i = 0; i < this.mConfigViews.size(); i++) {
            ImageView imageView = this.mConfigViews.get(i);
            imageView.setEnabled(true);
            imageView.setColorFilter((ColorFilter) null);
            TopConfigItem configItem = this.mSupportedConfigs.getConfigItem(i);
            boolean topImageResource = setTopImageResource(configItem, imageView, ((BaseFragment) this).mCurrentMode, dataItemConfig, false);
            TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
            if (topConfigItem == null || topConfigItem.configItem != configItem.configItem) {
                imageView.setTag(configItem);
                imageView.clearAnimation();
                imageView.setVisibility(0);
                if (topImageResource) {
                    ViewCompat.setAlpha(imageView, 0.0f);
                    ViewCompat.animate(imageView).alpha(1.0f).setDuration(150).setStartDelay(150).start();
                } else {
                    imageView.setVisibility(4);
                }
            } else {
                imageView.setTag(configItem);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:249:0x045d  */
    private boolean setTopImageResource(TopConfigItem topConfigItem, ImageView imageView, int i, DataItemConfig dataItemConfig, boolean z) {
        CharSequence charSequence;
        int i2;
        int i3;
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        int i4 = topConfigItem.configItem;
        int i5 = R.string.autozoom_hint;
        i5 = R.string.autozoom_hint;
        i5 = R.string.autozoom_hint;
        i5 = R.string.autozoom_hint;
        int i6 = 0;
        int i7 = 0;
        i6 = 0;
        if (i4 != 162) {
            if (i4 != 209) {
                int i8 = R.drawable.ic_beauty_on;
                if (i4 != 212) {
                    if (i4 == 225) {
                        i7 = getSettingResources();
                        i5 = R.string.accessibility_setting;
                    } else if (i4 == 227) {
                        ComponentRunningColorEnhance componentRunningColorEnhance = DataRepository.dataItemRunning().getComponentRunningColorEnhance();
                        i2 = componentRunningColorEnhance.getResIcon(componentRunningColorEnhance.isEnabled(((BaseFragment) this).mCurrentMode));
                    } else if (i4 == 239) {
                        i7 = i != 174 ? i != 183 ? CameraSettings.isFaceBeautyOn(((BaseFragment) this).mCurrentMode, null) : CameraSettings.isMiLiveBeautyOpen() : CameraSettings.isLiveBeautyOpen() ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off;
                        i5 = R.string.accessibility_beauty_panel_open;
                    } else if (i4 != 245) {
                        if (i4 != 251) {
                            if (i4 == 253) {
                                i2 = CameraSettings.isAutoZoomEnabled(i) ? this.mAutoZoomResources[1] : this.mAutoZoomResources[0];
                            } else if (i4 == 176) {
                                return false;
                            } else {
                                if (i4 == 177) {
                                    i2 = R.drawable.ic_new_config_flash_off;
                                } else if (i4 == 220) {
                                    i7 = CameraSettings.isSubtitleEnabled(i) ? this.mAutoZoomResources[1] : this.mAutoZoomResources[0];
                                    i5 = R.string.pref_video_subtitle;
                                    i5 = R.string.pref_video_subtitle;
                                    if (!z) {
                                        reConfigTipOfSubtitle();
                                    }
                                } else if (i4 == 221) {
                                    i7 = CameraSettings.isDocumentModeOn(i) ? this.mDocumentResources[1] : this.mDocumentResources[0];
                                    i5 = R.string.pref_document_mode;
                                } else if (i4 != 242) {
                                    if (i4 != 243) {
                                        switch (i4) {
                                            case 193:
                                                ComponentConfigFlash componentFlash = dataItemConfig.getComponentFlash();
                                                if (!componentFlash.isEmpty()) {
                                                    i7 = componentFlash.getValueSelectedDrawableIgnoreClose(i);
                                                    i5 = componentFlash.getValueSelectedStringIdIgnoreClose(i);
                                                    if (z) {
                                                        if (z && ((BaseFragment) this).mCurrentMode == 167) {
                                                            reConfigTipOfFlash(true);
                                                            break;
                                                        }
                                                    } else {
                                                        reConfigTipOfFlash(true);
                                                        break;
                                                    }
                                                }
                                                charSequence = null;
                                                i5 = 0;
                                                break;
                                            case 194:
                                                ComponentConfigHdr componentHdr = dataItemConfig.getComponentHdr();
                                                if (!componentHdr.isEmpty()) {
                                                    i7 = componentHdr.getValueSelectedDrawableIgnoreClose(i);
                                                    i5 = componentHdr.getValueSelectedStringIdIgnoreClose(i);
                                                    if (!z) {
                                                        reConfigTipOfHdr(true);
                                                        break;
                                                    }
                                                }
                                                charSequence = null;
                                                i5 = 0;
                                                break;
                                            case 195:
                                                i7 = getPortraitResources();
                                                i5 = R.string.accessibility_protrait;
                                                break;
                                            case 196:
                                                int parseInt = Integer.parseInt(z ? DataRepository.getInstance().backUp().getBackupFilter(i, DataRepository.dataItemGlobal().getCurrentCameraId()) : DataRepository.dataItemRunning().getComponentConfigFilter().getComponentValue(i));
                                                i2 = (parseInt == FilterInfo.FILTER_ID_NONE || parseInt <= 0) ? this.mFilterResources[0] : this.mFilterResources[1];
                                                if (actionProcessing != null) {
                                                    if (actionProcessing.isShowFilterView()) {
                                                        i5 = R.string.accessibility_filter_close_panel;
                                                        break;
                                                    }
                                                }
                                                break;
                                            case 197:
                                                i7 = getMoreResources();
                                                i5 = R.string.accessibility_more;
                                                break;
                                            default:
                                                switch (i4) {
                                                    case 199:
                                                        i7 = getFocusPeakImageResource();
                                                        i5 = R.string.accessibility_foucs_peak;
                                                        break;
                                                    case 200:
                                                        String componentValue = dataItemConfig.getComponentBokeh().getComponentValue(i);
                                                        i7 = "on".equals(componentValue) ? R.drawable.ic_portrait_button_on : R.drawable.ic_portrait_button_normal;
                                                        if (!"on".equals(componentValue)) {
                                                            i3 = R.string.accessibility_bokeh_normal;
                                                            break;
                                                        } else {
                                                            i3 = R.string.accessibility_bokeh_on;
                                                            break;
                                                        }
                                                    case 201:
                                                        int i9 = CameraSettings.getAiSceneOpen(i) ? this.mAiSceneResources[1] : this.mAiSceneResources[0];
                                                        i5 = CameraSettings.getAiSceneOpen(i) ? R.string.accessibility_ai_scene_on : R.string.accessibility_ai_scene_off;
                                                        configBottomPopupTips(false);
                                                        i7 = i9;
                                                        break;
                                                    default:
                                                        switch (i4) {
                                                            case 203:
                                                                i2 = DataRepository.dataItemRunning().getComponentRunningLighting().isSwitchOn(i) ? this.mLightingResource[1] : this.mLightingResource[0];
                                                                if (actionProcessing != null) {
                                                                    if (!actionProcessing.isShowLightingView()) {
                                                                        i5 = R.string.accessibility_camera_lighting_open_panel;
                                                                        break;
                                                                    } else {
                                                                        i5 = R.string.accessibility_camera_lighting_close_panel;
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            case 204:
                                                                i7 = dataItemConfig.getComponentConfigSlowMotion().getImageResource();
                                                                i5 = dataItemConfig.getComponentConfigSlowMotion().getContentDesc();
                                                                break;
                                                            case 205:
                                                                ComponentConfigUltraWide componentConfigUltraWide = dataItemConfig.getComponentConfigUltraWide();
                                                                if (!componentConfigUltraWide.isEmpty()) {
                                                                    i7 = componentConfigUltraWide.getValueSelectedDrawableIgnoreClose(i);
                                                                    i5 = componentConfigUltraWide.getValueSelectedStringIdIgnoreClose(i);
                                                                    break;
                                                                }
                                                                charSequence = null;
                                                                i5 = 0;
                                                                break;
                                                            case 206:
                                                                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.LiveShotAttr.VALUE_TOPMENU_LIVESHOT_CLICK, null, null);
                                                                boolean isLiveShotOn = CameraSettings.isLiveShotOn();
                                                                int[] iArr = this.mLiveShotResource;
                                                                i7 = isLiveShotOn ? iArr[1] : iArr[0];
                                                                if (!isLiveShotOn) {
                                                                    i3 = R.string.accessibility_camera_liveshot_off;
                                                                    break;
                                                                } else {
                                                                    i3 = R.string.accessibility_camera_liveshot_on;
                                                                    break;
                                                                }
                                                            case 207:
                                                                boolean backupSwitchState = z ? DataRepository.getInstance().backUp().getBackupSwitchState(i, "pref_ultra_wide_bokeh_enabled", DataRepository.dataItemGlobal().getCurrentCameraId()) : DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
                                                                i7 = backupSwitchState ? this.mUltraWideBokehResources[1] : this.mUltraWideBokehResources[0];
                                                                if (!backupSwitchState) {
                                                                    i3 = R.string.accessibility_camera_ultra_wide_bokeh_off;
                                                                    break;
                                                                } else {
                                                                    i3 = R.string.accessibility_camera_ultra_wide_bokeh_on;
                                                                    break;
                                                                }
                                                            default:
                                                                switch (i4) {
                                                                    case 214:
                                                                        ComponentConfigMeter componentConfigMeter = dataItemConfig.getComponentConfigMeter();
                                                                        if (!componentConfigMeter.isEmpty()) {
                                                                            i7 = componentConfigMeter.getValueSelectedDrawableIgnoreClose(i);
                                                                            i5 = componentConfigMeter.getValueSelectedStringIdIgnoreClose(i);
                                                                            break;
                                                                        }
                                                                        charSequence = null;
                                                                        i5 = 0;
                                                                        break;
                                                                    case 215:
                                                                        i7 = CameraSettings.isUltraPixelPortraitFrontOn() ? this.mUltraPixelPortraitResources[1] : this.mUltraPixelPortraitResources[0];
                                                                        i5 = R.string.ultra_pixel_portrait_hint;
                                                                        break;
                                                                    case 216:
                                                                        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                                                                        if (baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523) {
                                                                            i2 = R.drawable.ic_config_vv_on;
                                                                            break;
                                                                        } else {
                                                                            i2 = R.drawable.ic_config_vv_normal;
                                                                            break;
                                                                        }
                                                                    case 217:
                                                                        i2 = R.drawable.ic_back;
                                                                        break;
                                                                    case 218:
                                                                        i7 = CameraSettings.isSuperEISEnabled(i) ? this.mSuperEISResources[1] : this.mSuperEISResources[0];
                                                                        i5 = R.string.super_eis;
                                                                        break;
                                                                    default:
                                                                        switch (i4) {
                                                                            case 255:
                                                                                if (!CameraSettings.isMacroModeEnabled(i)) {
                                                                                    i2 = this.mMacroResources[0];
                                                                                    break;
                                                                                } else {
                                                                                    i2 = this.mMacroResources[1];
                                                                                    break;
                                                                                }
                                                                            case 256:
                                                                                if (((CameraSettings.isVideoQuality8KOpen(((BaseFragment) this).mCurrentMode) || ComponentConfigVideoQuality.QUALITY_8K.equalsIgnoreCase(DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(((BaseFragment) this).mCurrentMode))) ? 1 : null) == null) {
                                                                                    i2 = this.mVideo8KResource[0];
                                                                                    break;
                                                                                } else {
                                                                                    i2 = this.mVideo8KResource[1];
                                                                                    break;
                                                                                }
                                                                            case 257:
                                                                                ComponentConfigAuxiliary componentConfigAuxiliary = dataItemConfig.getComponentConfigAuxiliary();
                                                                                if (!componentConfigAuxiliary.isEmpty()) {
                                                                                    i7 = componentConfigAuxiliary.getValueSelectedDrawable(i);
                                                                                    i5 = componentConfigAuxiliary.getValueSelectedStringIdIgnoreClose(i);
                                                                                    break;
                                                                                }
                                                                            default:
                                                                                charSequence = null;
                                                                                i5 = 0;
                                                                                break;
                                                                        }
                                                                        break;
                                                                }
                                                        }
                                                }
                                        }
                                    } else {
                                        boolean isVideoBokehOn = CameraSettings.isVideoBokehOn();
                                        Log.d(TAG, "setTopImageResource: VIDEO_BOKEH isSwitchOn = " + isVideoBokehOn);
                                        i7 = isVideoBokehOn ? this.mVideoBokehResource[1] : this.mVideoBokehResource[0];
                                        i3 = isVideoBokehOn ? R.string.pref_camera_video_bokeh_on : R.string.pref_camera_video_bokeh_off;
                                    }
                                } else if (Util.isGlobalVersion()) {
                                    i7 = R.drawable.ic_config_ai_glens_outer;
                                    i5 = R.string.pref_google_lens;
                                } else {
                                    i7 = R.drawable.ic_config_ai_detect_unselected;
                                    i5 = R.string.pref_ai_detect;
                                }
                            }
                            i7 = i2;
                        } else {
                            i7 = CameraSettings.isCinematicAspectRatioEnabled(i) ? this.mCinematicRatioResources[1] : this.mCinematicRatioResources[0];
                            i3 = CameraSettings.getAiSceneOpen(i) ? R.string.accessibility_mimovie_on : R.string.accessibility_mimovie_off;
                        }
                        i5 = i3;
                    } else {
                        i2 = !CameraSettings.getCurrentLiveMusic()[1].isEmpty() ? this.mLiveMusicSelectResources[1] : this.mLiveMusicSelectResources[0];
                        reConfigTipOfMusicHint(true);
                    }
                    charSequence = null;
                } else {
                    ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                    if (componentRunningShine.isSmoothBarVideoBeautyVersion(i)) {
                        boolean isVideoBeautyOpen = DataRepository.dataItemRunning().getComponentRunningShine().isVideoBeautyOpen(i);
                        if (componentRunningShine.getItems() == null || componentRunningShine.getItems().size() != 1) {
                            i2 = isVideoBeautyOpen ? R.drawable.ic_shine_on : R.drawable.ic_shine_off;
                        } else if ((i == 162 || i == 169) && componentRunningShine.supportSmoothLevel()) {
                            if (!isVideoBeautyOpen) {
                                i8 = R.drawable.ic_beauty_off;
                            }
                            i6 = i8;
                        } else if (i == 180 && componentRunningShine.supportVideoFilter()) {
                            i2 = isVideoBeautyOpen ? R.drawable.ic_new_effect_button_selected : R.drawable.ic_new_effect_button_normal;
                        }
                    } else {
                        i6 = componentRunningShine.getTopConfigEntryRes(i);
                    }
                    i5 = R.string.accessibility_filter_open_panel;
                    charSequence = null;
                }
                i6 = i2;
                i5 = R.string.accessibility_filter_open_panel;
                charSequence = null;
            } else {
                boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
                int i10 = isUltraPixelOn ? this.mUltraPixelPhotographyIconResources[1] : this.mUltraPixelPhotographyIconResources[0];
                charSequence = isUltraPixelOn ? this.mUltraPixelPhotographyTipString[1] : this.mUltraPixelPhotographyTipString[0];
                i5 = 0;
                i7 = i10;
            }
            if (i7 > 0) {
                Drawable drawable = getResources().getDrawable(i7);
                topConfigItem.margin = getInitialMargin(topConfigItem, imageView);
                if (topConfigItem.margin > 0) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    topConfigItem.margin -= (drawable.getIntrinsicWidth() / 2) + this.mViewPadding;
                    if (this.mIsRTL) {
                        layoutParams.leftMargin = ((this.mTotalWidth - topConfigItem.margin) - drawable.getIntrinsicWidth()) - (this.mViewPadding * 2);
                    } else {
                        layoutParams.leftMargin = topConfigItem.margin;
                    }
                    imageView.setLayoutParams(layoutParams);
                }
                if (topConfigItem.configItem == 177) {
                    imageView.setImageDrawable(null);
                } else {
                    imageView.setImageDrawable(drawable);
                }
                if (topConfigItem.configItem == 193) {
                    if (((BaseFragment) this).mCurrentMode != 167 || CameraSettings.isFlashSupportedInManualMode()) {
                        imageView.setAlpha(1.0f);
                    } else {
                        imageView.setAlpha(0.4f);
                    }
                }
                if (Util.isAccessible() || Util.isSetContentDesc()) {
                    if (i5 > 0) {
                        imageView.setContentDescription(getString(i5));
                    } else if (!TextUtils.isEmpty(charSequence)) {
                        imageView.setContentDescription(charSequence);
                    }
                }
            }
            return true;
        }
        i2 = CameraSettings.isGifOn() ? this.mGifResource[1] : this.mGifResource[0];
        i5 = 0;
        i7 = i2;
        charSequence = null;
        if (i7 > 0) {
        }
        return true;
    }

    private void showExtraMenu() {
        this.mTopConfigMenu.setVisibility(8);
        hideSwitchHint();
        hideAlert();
        this.mFragmentTopConfigExtra = new FragmentTopConfigExtra();
        this.mFragmentTopConfigExtra.setDegree(((BaseFragment) this).mDegree);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTopConfigExtra fragmentTopConfigExtra = this.mFragmentTopConfigExtra;
        FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.top_config_extra, fragmentTopConfigExtra, fragmentTopConfigExtra.getFragmentTag());
        this.mIsShowExtraMenu = true;
        notifyExtraMenuVisibilityChange(true);
    }

    private void showTips(ModeProtocol.ConfigChanges configChanges, boolean z) {
        configChanges.reCheckUltraPixel();
        configChanges.reCheckEyeLight();
        configChanges.reCheckUltraPixelPortrait();
        if (getUpdateTipState(212)) {
            setUpdateTipState(212, false);
            configChanges.reCheckVideoBeautify();
        }
        if (isMenuConfigShortShow(251) || isTopConfigFromModeSelected(251)) {
            setUpdateTipState(251, false);
            configChanges.reCheckCinematicAspectRatio(z);
        }
        if (isMenuConfigShortShow(253) || isTopConfigFromModeSelected(253)) {
            setUpdateTipState(253, false);
            configChanges.reCheckAutoZoom();
        }
        if (isMenuConfigShortShow(223)) {
            setUpdateTipState(223, false);
            configChanges.reCheckAIWatermark(true);
        }
        if (isTopConfigFromModeSelected(255) || isMenuConfigShortShow(255)) {
            setUpdateTipState(255, false);
            configChanges.reCheckMacroMode();
        }
        if (isTopConfigFromModeSelected(218)) {
            setUpdateTipState(218, false);
            configChanges.reCheckSuperEIS();
        }
        if (!isTopConfig(201)) {
            configChanges.reCheckAiScene();
        }
        if (!isTopConfig(200)) {
            configChanges.reCheckFrontBokenTip();
        }
        if (!isTopConfig(227)) {
            configChanges.reCheckColorEnhance();
        }
        if (!isTopConfig(206)) {
            configChanges.reCheckLiveShot();
        }
        if (!isTopConfig(252)) {
            configChanges.reCheckHandGesture();
        }
        if (!isTopConfig(220)) {
            configChanges.reCheckSubtitleMode();
        }
        if (!isTopConfig(216)) {
            configChanges.reCheckLiveVV();
        }
    }

    private void trackAuxiliary(String str) {
        boolean equals = str.equals("close");
        String str2 = MistatsConstants.ModuleName.MANUAL;
        if (equals) {
            if (((BaseFragment) this).mCurrentMode != 167) {
                str2 = MistatsConstants.ModuleName.PROVIDEO;
            }
            MistatsWrapper.moduleUIClickEvent(str2, MistatsConstants.Manual.AUXILIAYR_CLOSE, "on");
        } else if (str.equals(ComponentConfigAuxiliary.A_FOCUS_PEAK)) {
            if (((BaseFragment) this).mCurrentMode != 167) {
                str2 = MistatsConstants.ModuleName.PROVIDEO;
            }
            MistatsWrapper.moduleUIClickEvent(str2, MistatsConstants.Manual.MANUAL_FOCUS_PEAK, "on");
        } else {
            if (((BaseFragment) this).mCurrentMode != 167) {
                str2 = MistatsConstants.ModuleName.PROVIDEO;
            }
            MistatsWrapper.moduleUIClickEvent(str2, "exposure_feedback", "on");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiDetectTipHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertRecommendTipHint(i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiSceneSelector(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertAiSceneSelector(i);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertFlash(int i, String str, boolean z) {
        alertFlash(i, str, z, true);
    }

    public void alertFlash(int i, String str, boolean z, boolean z2) {
        ImageView topImage;
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            if (z2) {
                if (i != 0) {
                    this.mLastAnimationComponent.reverse(true);
                } else if (z && (topImage = getTopImage(193)) != null) {
                    topImage.performClick();
                }
            }
            topAlert.alertFlash(i, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertHDR(int i, boolean z, boolean z2) {
        alertHDR(i, z, z2, true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertHandGestureHint(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertHandGestureHint(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertLightingHint(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertLightingHint(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertLightingTitle(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertLightingTitle(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertLiveShotHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertLiveShotHint(i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMacroModeHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMacroModeHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMimojiFaceDetect(boolean z, int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMimojiFaceDetect(z, i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMoonModeSelector(int i, boolean z) {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMoonSelector(getResources().getString(R.string.ai_scene_top_tip), getResources().getString(R.string.ai_scene_top_moon_off), i, z);
            if (((BaseFragment) this).mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                cameraModuleSpecial.showOrHideChip(i != 0);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMusicClose(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertMusicClose(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertParameterDescriptionTip(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertParameterDescriptionTip(i, true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertParameterResetTip(boolean z, int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            FragmentTopConfigExtra fragmentTopConfigExtra = this.mFragmentTopConfigExtra;
            topAlert.alertParameterResetTip(z, i, i2, Util.getDisplayRect(0).top + getResources().getDimensionPixelSize(R.dimen.reset_manually_parameter_tip_margin_top), !(fragmentTopConfigExtra == null || !fragmentTopConfigExtra.isAdded()));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSubtitleHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSubtitleHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSuperNightSeTip(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSuperNightSeTip(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSwitchHint(int i, @StringRes int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSwitchHint(i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSwitchHint(int i, String str, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSwitchHint(i, str, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertUpdateValue(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertUpdateValue(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertVideoBeautifyHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertVideoBeautifyHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertVideoUltraClear(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            FragmentTopConfigExtra fragmentTopConfigExtra = this.mFragmentTopConfigExtra;
            topAlert.alertVideoUltraClear(i, i2, Util.getDisplayRect(0).top + getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top) + getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top_error), !(fragmentTopConfigExtra == null || !fragmentTopConfigExtra.isAdded()));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void clearAlertStatus() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clearAlertStatus();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void clearVideoUltraClear() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clearVideoUltraClear();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void directHideLyingDirectHint() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void disableMenuItem(boolean z, int... iArr) {
        ImageView topImage;
        if (iArr != null) {
            for (int i : iArr) {
                this.mDisabledFunctionMenu.put(i, z);
                if (z && (topImage = getTopImage(i)) != null) {
                    AlphaOutOnSubscribe.directSetResult(topImage);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void enableMenuItem(boolean z, int... iArr) {
        ImageView topImage;
        SparseBooleanArray sparseBooleanArray = this.mDisabledFunctionMenu;
        if (sparseBooleanArray != null && sparseBooleanArray.size() != 0) {
            for (int i : iArr) {
                this.mDisabledFunctionMenu.delete(i);
                if (z && (topImage = getTopImage(i)) != null) {
                    AlphaInOnSubscribe.directSetResult(topImage);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean getAlertIsShow() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isShow();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public int getCurrentAiSceneLevel() {
        return this.mCurrentAiSceneLevel;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 244;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_top_config;
    }

    @Override // com.android.camera.fragment.top.ExpandAdapter.ExpandListener
    public ImageView getTopImage(int i) {
        for (ImageView imageView : this.mConfigViews) {
            TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
            if (topConfigItem != null && topConfigItem.configItem == i) {
                return imageView;
            }
        }
        return null;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean getUpdateTipState(int i) {
        Boolean bool = this.mUpdateTipState.get(Integer.valueOf(i));
        return bool != null && bool.booleanValue();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideAlert() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clear(true);
            topAlert.setShow(false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideConfigMenu() {
        Completable.create(new AlphaOutOnSubscribe(this.mTopConfigMenu)).subscribe();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideExtraMenu() {
        onBackEvent(6);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideSwitchHint() {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.hideSwitchHint();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mAiSceneResources = getAiSceneResources();
        this.mCinematicRatioResources = getCinematicRatioResources();
        this.mAutoZoomResources = getAutoZoomResources();
        this.mUltraWideResource = getUltraWideResources();
        this.mUltraWideBokehResources = getUltraWideBokehResources();
        this.mUltraPixelPhotographyIconResources = ComponentRunningUltraPixel.getUltraPixelTopMenuResources();
        this.mUltraPixelPhotographyTipString = ComponentRunningUltraPixel.getUltraPixelSwitchTipsString();
        this.mLiveShotResource = getLiveShotResources();
        this.mLightingResource = getLightingResources();
        this.mVideoBokehResource = getVideoBokehResources();
        this.mFilterResources = getFilterResources();
        this.mLiveMusicSelectResources = getMusicSelectResources();
        this.mMacroResources = getMacroResources();
        this.mUltraPixelPortraitResources = getUltraPixelPortraitResources();
        this.mSuperEISResources = getSuperEISResources();
        this.mVideo8KResource = getVideo8KRecource();
        this.mGifResource = getGifRecource();
        this.mDocumentResources = getDocumentResources();
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mLastAnimationComponent = new LastAnimationComponent();
        this.mDisabledFunctionMenu = new SparseBooleanArray(1);
        this.mTopExtraParent = (ViewGroup) view.findViewById(R.id.top_config_extra);
        this.mTopConfigMenu = view.findViewById(R.id.top_config_menu);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopConfigMenu.getLayoutParams();
        marginLayoutParams.topMargin = Util.sTopMargin;
        marginLayoutParams.height = Util.sTopBarHeight;
        initTopView();
        this.mExpandView = (RecyclerView) view.findViewById(R.id.top_config_expand_view);
        this.mExpandView.setFocusable(false);
        AnonymousClass1 r4 = new LinearLayoutManagerWrapper(getContext(), "top_config_expand_view") {
            /* class com.android.camera.fragment.top.FragmentTopConfig.AnonymousClass1 */

            @Override // android.support.v7.widget.RecyclerView.LayoutManager, android.support.v7.widget.LinearLayoutManager
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        r4.setOrientation(0);
        this.mExpandView.setLayoutManager(r4);
        this.mViewPadding = getResources().getDimensionPixelSize(R.dimen.panel_imageview_button_padding_width);
        this.mTopDrawableWidth = getResources().getDrawable(R.drawable.ic_new_config_flash_off).getIntrinsicWidth();
        this.mTotalWidth = Util.sWindowWidth;
        if (((ActivityBase) getContext()).getCameraIntentManager().isFromScreenSlide().booleanValue()) {
            Util.startScreenSlideAlphaInAnimation(this.mTopConfigMenu);
        }
        provideAnimateElement(((BaseFragment) this).mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void insertConfigItem(int i) {
        resetImages();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isContainAlertRecommendTip(int... iArr) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isContainAlertRecommendTip(iArr);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isCurrentRecommendTipText(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (!isTopAlertShowing(topAlert)) {
            return false;
        }
        return topAlert.isCurrentRecommendTipText(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isExtraMenuShowing() {
        FragmentTopConfigExtra fragmentTopConfigExtra = this.mFragmentTopConfigExtra;
        return fragmentTopConfigExtra != null && fragmentTopConfigExtra.isAdded() && this.mIsShowExtraMenu;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isHDRShowing() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isHDRShowing();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isShowBacklightSelector() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isShowBacklightSelector();
    }

    public boolean isTopAlertShowing(FragmentTopAlert fragmentTopAlert) {
        return fragmentTopAlert != null && fragmentTopAlert.isShow() && !isExtraMenuShowing();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isTopToastShowing() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isTopToastShowing();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (getTopAlert() != null && ((((BaseFragment) this).mCurrentMode != 177 || !DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate()) && (((BaseFragment) this).mCurrentMode != 184 || !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()))) {
            clearVideoUltraClear();
            reConfigTipOfFlash(false);
            reConfigTipOfHdr(false);
            reConfigTipOfMusicHint(false);
            alertUpdateValue(4);
            reConfigTipOfSuperNightSe();
        }
        FragmentTopConfigExtra topExtra = getTopExtra();
        if (topExtra != null) {
            topExtra.notifyAfterFrameAvailable(i);
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            showTips(configChanges, false);
            configChanges.reCheckMutexConfigs(((BaseFragment) this).mCurrentMode);
            configChanges.reCheckFocusPeakConfig();
            configChanges.reCheckAuxiliaryConfig();
            configChanges.reCheckParameterResetTip(true);
            configChanges.reCheckParameterDescriptionTip();
            configChanges.reCheckRaw();
            configChanges.reCheckDocumentMode();
            configChanges.reCheckGradienter();
            configChanges.reCheckVideoUltraClearTip();
            configChanges.reCheckVideoLog();
            configChanges.reCheckAIWatermark(false);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        this.mDisplayRectTopMargin = Util.getDisplayRect().top;
        int i3 = ((BaseFragment) this).mCurrentMode;
        int i4 = 7;
        if (((BaseFragment) this).mResetType != 7) {
            i4 = 2;
        }
        provideAnimateElement(i3, null, i4);
        if (this.mFragmentTopAlert == null) {
            this.mFragmentTopAlert = new FragmentTopAlert();
            this.mFragmentTopAlert.setShow(!isExtraMenuShowing());
            this.mFragmentTopAlert.setDegree(((BaseFragment) this).mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTopAlert fragmentTopAlert = this.mFragmentTopAlert;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.top_alert, fragmentTopAlert, fragmentTopAlert.getFragmentTag());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onAngleChanged(float f2) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0078  */
    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (this.mLastAnimationComponent.reverse(i != 4)) {
            return true;
        }
        boolean z = this.mIsShowExtraMenu;
        FragmentTopConfigExtra topExtra = getTopExtra();
        if (topExtra == null) {
            return false;
        }
        if (i == 1 || i == 2) {
            if (!this.mIsShowExtraMenu) {
                return false;
            }
            topExtra.animateOut();
            Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu).setStartDelayTime(200)).subscribe();
        } else if (i != 6) {
            if (i != 7) {
                FragmentUtils.removeFragmentByTag(getChildFragmentManager(), String.valueOf(245));
                this.mTopConfigMenu.setVisibility(0);
            }
            if (!(i == 4 || i == 7)) {
                reInitAlert(true);
            }
            this.mIsShowExtraMenu = z;
            if (!this.mIsShowExtraMenu) {
                notifyExtraMenuVisibilityChange(false);
            }
            return true;
        } else {
            topExtra.animateOut();
            Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu).setStartDelayTime(200)).subscribe();
        }
        z = false;
        reInitAlert(true);
        this.mIsShowExtraMenu = z;
        if (!this.mIsShowExtraMenu) {
        }
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onBeautyRecordingStart() {
        onBackEvent(5);
        ViewCompat.animate(this.mTopConfigMenu).alpha(0.0f).start();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onBeautyRecordingStop() {
        ViewCompat.animate(this.mTopConfigMenu).alpha(1.0f).start();
    }

    public void onClick(View view) {
        ModeProtocol.ConfigChanges configChanges;
        TopConfigItem topConfigItem;
        Log.d(TAG, "top config onclick");
        if (isEnableClick() && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction != null && cameraAction.isDoingAction()) {
                return;
            }
            if ((CameraSettings.isFrontCamera() && ((Camera) getContext()).isScreenSlideOff()) || (topConfigItem = (TopConfigItem) view.getTag()) == null) {
                return;
            }
            if (this.mDisabledFunctionMenu.size() <= 0 || this.mDisabledFunctionMenu.indexOfKey(topConfigItem.configItem) < 0) {
                ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                int i = topConfigItem.configItem;
                if (i == 162) {
                    CameraSettings.isFrontCamera();
                    configChanges.onConfigChanged(162);
                } else if (i == 209) {
                    configChanges.onConfigChanged(209);
                } else if (i == 212) {
                    MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BeautyAttr.VALUE_BEAUTY_TOP_BUTTON, null, null);
                    configChanges.onConfigChanged(212);
                } else if (i == 220) {
                    configChanges.onConfigChanged(220);
                } else if (i == 225) {
                    configChanges.showSetting();
                } else if (i == 227) {
                    configChanges.onConfigChanged(227);
                } else if (i == 239) {
                    configChanges.onConfigChanged(239);
                } else if (i == 245) {
                    Fragment fragmentByTag = FragmentUtils.getFragmentByTag(getFragmentManager(), FragmentLiveMusic.TAG);
                    int i2 = ((BaseFragment) this).mCurrentMode;
                    if (i2 == 174) {
                        CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_MUSIC_ICON_CLICK);
                    } else if (i2 == 183) {
                        CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_MUSIC);
                    }
                    if (fragmentByTag == null) {
                        FragmentLiveMusic fragmentLiveMusic = new FragmentLiveMusic();
                        fragmentLiveMusic.setStyle(2, R.style.TTMusicDialogFragment);
                        getFragmentManager().beginTransaction().add(fragmentLiveMusic, FragmentLiveMusic.TAG).commitAllowingStateLoss();
                    }
                } else if (i == 251) {
                    configChanges.onConfigChanged(251);
                    if (cameraClickObservable != null) {
                        cameraClickObservable.subscribe(169);
                    }
                } else if (i == 253) {
                    configChanges.onConfigChanged(253);
                } else if (i == 242) {
                    ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
                    if (topConfigProtocol != null) {
                        topConfigProtocol.startAiLens();
                    }
                    MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_AI_DETECT_CHANGED, null, null);
                } else if (i != 243) {
                    switch (i) {
                        case 193:
                            ComponentConfigFlash componentFlash = ((DataItemConfig) DataRepository.provider().dataConfig()).getComponentFlash();
                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.FlashAttr.VALUE_FLASH_OUT_BUTTON, null);
                            if (componentFlash.disableUpdate()) {
                                int disableReasonString = componentFlash.getDisableReasonString();
                                if (disableReasonString != 0) {
                                    ToastUtils.showToast(getActivity(), disableReasonString);
                                }
                                Log.w(TAG, "ignore click flash for disable update");
                            } else if (((BaseFragment) this).mCurrentMode != 171 || !DataRepository.dataItemFeature().hd() || !CameraSettings.isBackCamera()) {
                                expandExtra(componentFlash, view, topConfigItem.configItem);
                            } else {
                                String componentValue = componentFlash.getComponentValue(((BaseFragment) this).mCurrentMode);
                                String str = componentValue == "0" ? "5" : "0";
                                componentFlash.setComponentValue(((BaseFragment) this).mCurrentMode, str);
                                onExpandValueChange(componentFlash, componentValue, str);
                            }
                            if (cameraClickObservable != null) {
                                cameraClickObservable.subscribe(161);
                                return;
                            }
                            return;
                        case 194:
                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.AlgoAttr.VALUE_HDR_OUT_BUTTON, null);
                            expandExtra(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentHdr(), view, topConfigItem.configItem);
                            if (cameraClickObservable != null) {
                                cameraClickObservable.subscribe(162);
                                return;
                            }
                            return;
                        case 195:
                            configChanges.onConfigChanged(195);
                            return;
                        case 196:
                            configChanges.onConfigChanged(196);
                            return;
                        case 197:
                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_MENU_MORE, 1, null);
                            showExtraMenu();
                            if (cameraClickObservable != null) {
                                cameraClickObservable.subscribe(164);
                                return;
                            }
                            return;
                        default:
                            switch (i) {
                                case 199:
                                    configChanges.onConfigChanged(199);
                                    ((ImageView) view).setImageResource(getFocusPeakImageResource());
                                    return;
                                case 200:
                                    DataItemConfig dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig();
                                    dataItemConfig.getComponentBokeh().toggle(((BaseFragment) this).mCurrentMode);
                                    String componentValue2 = dataItemConfig.getComponentBokeh().getComponentValue(((BaseFragment) this).mCurrentMode);
                                    CameraStatUtils.tarckBokenChanged(((BaseFragment) this).mCurrentMode, componentValue2);
                                    updateConfigItem(200);
                                    if (dataItemConfig.reConfigHdrIfBokehChanged(((BaseFragment) this).mCurrentMode, componentValue2)) {
                                        updateConfigItem(194);
                                    }
                                    configChanges.configBokeh(componentValue2);
                                    return;
                                case 201:
                                    configChanges.onConfigChanged(201);
                                    if (cameraClickObservable != null) {
                                        cameraClickObservable.subscribe(166);
                                        return;
                                    }
                                    return;
                                default:
                                    switch (i) {
                                        case 203:
                                            configChanges.onConfigChanged(203);
                                            return;
                                        case 204:
                                            configChanges.onConfigChanged(204);
                                            return;
                                        case 205:
                                            configChanges.onConfigChanged(205);
                                            return;
                                        case 206:
                                            configChanges.onConfigChanged(206);
                                            if (cameraClickObservable != null) {
                                                cameraClickObservable.subscribe(163);
                                                return;
                                            }
                                            return;
                                        case 207:
                                            configChanges.onConfigChanged(207);
                                            return;
                                        default:
                                            switch (i) {
                                                case 214:
                                                    CameraStatUtils.trackMeterClick();
                                                    expandExtra(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentConfigMeter(), view, topConfigItem.configItem);
                                                    return;
                                                case 215:
                                                    configChanges.onConfigChanged(215);
                                                    return;
                                                case 216:
                                                    CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_ICON_CLICK);
                                                    configChanges.onConfigChanged(216);
                                                    return;
                                                case 217:
                                                    configChanges.onConfigChanged(217);
                                                    return;
                                                case 218:
                                                    configChanges.onConfigChanged(218);
                                                    return;
                                                default:
                                                    switch (i) {
                                                        case 255:
                                                            configChanges.onConfigChanged(255);
                                                            return;
                                                        case 256:
                                                            configChanges.onConfigChanged(256);
                                                            return;
                                                        case 257:
                                                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.Manual.AUXILIARY, null);
                                                            expandExtra(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentConfigAuxiliary(), view, topConfigItem.configItem);
                                                            if (cameraClickObservable != null) {
                                                                cameraClickObservable.subscribe(162);
                                                                return;
                                                            }
                                                            return;
                                                        default:
                                                            return;
                                                    }
                                            }
                                    }
                            }
                    }
                } else {
                    configChanges.onConfigChanged(243);
                }
            }
        }
    }

    @Override // com.android.camera.fragment.top.ExpandAdapter.ExpandListener
    public void onExpandValueChange(ComponentData componentData, String str, String str2) {
        if (isEnableClick()) {
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                switch (componentData.getDisplayTitleString()) {
                    case R.string.pref_camera_autoexposure_title:
                        MistatsWrapper.moduleUIClickEvent(componentData.getKey(((BaseFragment) this).mCurrentMode), MistatsConstants.Manual.AUTOEXPOSURE, str2);
                        updateConfigItem(214);
                        configChanges.configMeter(str2);
                        break;
                    case R.string.pref_camera_auxiliary_title:
                        updateConfigItem(257);
                        trackAuxiliary(str2);
                        configChanges.configAuxiliary(str2);
                        break;
                    case R.string.pref_camera_flashmode_title:
                        if (componentData.getDisplayTitleString() == R.string.pref_camera_flashmode_title && !((str != "5" && str2 != "5") || str2 == "0" || str2 == ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF)) {
                            configChanges.configBackSoftLightSwitch("0");
                            CameraStatUtils.trackFlashChanged(((BaseFragment) this).mCurrentMode, "0");
                        }
                        CameraStatUtils.trackFlashChanged(((BaseFragment) this).mCurrentMode, str2);
                        updateConfigItem(193);
                        if (dataItemConfig.reConfigHhrIfFlashChanged(((BaseFragment) this).mCurrentMode, str2)) {
                            updateConfigItem(194);
                        }
                        configChanges.configFlash(str2);
                        break;
                    case R.string.pref_camera_hdr_title:
                        CameraStatUtils.trackHdrChanged(((BaseFragment) this).mCurrentMode, str2);
                        updateConfigItem(194);
                        configChanges.restoreMutexFlash("e");
                        if (dataItemConfig.reConfigFlashIfHdrChanged(((BaseFragment) this).mCurrentMode, str2)) {
                            updateConfigItem(193);
                        }
                        if (dataItemConfig.reConfigBokehIfHdrChanged(((BaseFragment) this).mCurrentMode, str2)) {
                            updateConfigItem(200);
                        }
                        configChanges.configHdr(str2);
                        break;
                }
                this.mLastAnimationComponent.reverse(true);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0081, code lost:
        if (r18 != 183) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0088, code lost:
        if (r18 != 180) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008b, code lost:
        if (r18 == 161) goto L_0x008d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0100 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0101  */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        boolean z;
        FragmentTopAlert topAlert;
        int i3 = ((BaseFragment) this).mCurrentMode;
        boolean z2 = true;
        boolean z3 = i2 == 3;
        MimojiStatusManager mimojiStatusManager = DataRepository.dataItemLive().getMimojiStatusManager();
        if (((BaseFragment) this).mCurrentMode != 177 || !mimojiStatusManager.IsInMimojiCreate() || i2 == 3) {
            if (i3 == 184) {
                if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate() && i2 != 3) {
                    FragmentTopAlert topAlert2 = getTopAlert();
                    if (topAlert2 != null) {
                        topAlert2.provideAnimateElement(i, list, i2);
                        return;
                    }
                    return;
                } else if (i != i3) {
                    enableMenuItem(true, 193);
                }
            }
            super.provideAnimateElement(i, list, i2);
            if (isInModeChanging() || i2 == 3) {
                this.mIsShowTopLyingDirectHint = false;
            }
            if (i3 != 161) {
                if (!(i3 == 162 || i3 == 169)) {
                    if (i3 != 174) {
                        if (i3 != 180) {
                            if (i3 == 183) {
                            }
                        }
                    }
                    z = true;
                    if (z) {
                        onBackEvent(i2 == 7 ? 7 : 4);
                    }
                    if (isExtraMenuShowing() && i2 == 7) {
                        this.mFragmentTopConfigExtra.provideAnimateElement(i, list, i2);
                    }
                    if (z3) {
                        if (!DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview() || i3 == 183) {
                            enableMenuItem(true, 197, 193);
                        }
                        this.mDisabledFunctionMenu.clear();
                    }
                    topAlert = getTopAlert();
                    if (topAlert != null) {
                        topAlert.provideAnimateElement(i, list, i2);
                    }
                    DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
                    int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
                    if (z3 && this.mTopConfigMenu.getVisibility() != 0) {
                        AlphaInOnSubscribe.directSetResult(this.mTopConfigMenu);
                    }
                    this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(((BaseFragment) this).mCurrentMode, currentCameraId, DataRepository.dataItemGlobal().isNormalIntent());
                    if (this.mSupportedConfigs == null) {
                        int i4 = 0;
                        while (i4 < this.mConfigViews.size()) {
                            ImageView imageView = this.mConfigViews.get(i4);
                            imageView.setEnabled(z2);
                            TopConfigItem configItem = this.mSupportedConfigs.getConfigItem(i4);
                            boolean topImageResource = setTopImageResource(configItem, imageView, i, dataItemConfig, list != null ? z2 : false);
                            if (!topImageResource || this.mDisabledFunctionMenu.indexOfKey(configItem.configItem) < 0 || !this.mDisabledFunctionMenu.get(configItem.configItem)) {
                                TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
                                if (topConfigItem == null || topConfigItem.configItem != configItem.configItem) {
                                    imageView.setTag(configItem);
                                    if (list == null) {
                                        if (topImageResource) {
                                            AlphaInOnSubscribe.directSetResult(imageView);
                                        } else {
                                            AlphaOutOnSubscribe.directSetResult(imageView);
                                        }
                                    } else if (topImageResource) {
                                        AlphaInOnSubscribe alphaInOnSubscribe = new AlphaInOnSubscribe(imageView);
                                        if (((BaseFragment) this).mCurrentMode == 167 && !CameraSettings.isFlashSupportedInManualMode() && 193 == configItem.configItem) {
                                            alphaInOnSubscribe.setTargetAlpha(0.4f);
                                        }
                                        alphaInOnSubscribe.setStartDelayTime(150).setDurationTime(150);
                                        list.add(Completable.create(alphaInOnSubscribe));
                                    } else if (i3 == 165 || ((BaseFragment) this).mCurrentMode == 165) {
                                        AlphaOutOnSubscribe.directSetResult(imageView);
                                    } else {
                                        list.add(Completable.create(new AlphaOutOnSubscribe(imageView).setDurationTime(150)));
                                    }
                                } else {
                                    imageView.setTag(configItem);
                                }
                            }
                            i4++;
                            z2 = true;
                        }
                        return;
                    }
                    return;
                }
                if (i != 162) {
                    if (i != 169) {
                    }
                }
            }
            z = false;
            if (z) {
            }
            this.mFragmentTopConfigExtra.provideAnimateElement(i, list, i2);
            if (z3) {
            }
            topAlert = getTopAlert();
            if (topAlert != null) {
            }
            DataItemConfig dataItemConfig2 = DataRepository.dataItemConfig();
            int currentCameraId2 = DataRepository.dataItemGlobal().getCurrentCameraId();
            AlphaInOnSubscribe.directSetResult(this.mTopConfigMenu);
            this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(((BaseFragment) this).mCurrentMode, currentCameraId2, DataRepository.dataItemGlobal().isNormalIntent());
            if (this.mSupportedConfigs == null) {
            }
        } else {
            FragmentTopAlert topAlert3 = getTopAlert();
            if (topAlert3 != null) {
                topAlert3.provideAnimateElement(i, list, i2);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        FragmentTopConfigExtra topExtra = getTopExtra();
        if (topExtra != null) {
            topExtra.provideRotateItem(list, i);
        }
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.provideRotateItem(list, i);
        }
    }

    public /* synthetic */ void q(boolean z) {
        int i;
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setShow(true);
            reConfigTipOfFlash(z);
            reConfigTipOfHdr(z);
            reConfigTipOfMusicHint(z);
            alertUpdateValue(4);
            updateLyingDirectHint(false, true);
            reConfigTipOfSuperNightSe();
            ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
            if (topConfigProtocol != null && ((i = ((BaseFragment) this).mCurrentMode) == 162 || i == 163)) {
                topConfigProtocol.reShowMoon();
            }
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                showTips(configChanges, true);
                configChanges.reCheckVideoUltraClearTip();
                configChanges.reCheckParameterResetTip(true);
                configChanges.reCheckParameterDescriptionTip();
                configChanges.reCheckVideoLog();
                configChanges.reCheckRaw();
                configChanges.reCheckDocumentMode();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void reInitAlert(boolean z) {
        if (!CameraSettings.isHandGestureOpen() || DataRepository.dataItemRunning().getHandGestureRunning()) {
            AndroidSchedulers.mainThread().scheduleDirect(new n(this, z), this.mIsShowExtraMenu ? 120 : 0, TimeUnit.MILLISECONDS);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void refreshExtraMenu() {
        FragmentTopConfigExtra fragmentTopConfigExtra = this.mFragmentTopConfigExtra;
        if (fragmentTopConfigExtra != null && fragmentTopConfigExtra.isAdded()) {
            this.mFragmentTopConfigExtra.reFresh();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(172, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void removeConfigItem(int i) {
        resetImages();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void removeExtraMenu(int i) {
        onBackEvent(i);
    }

    public void rotate() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setAiSceneImageLevel(int i) {
        if (i == 25) {
            i = 23;
        }
        this.mCurrentAiSceneLevel = i;
        Drawable aiSceneDrawable = getAiSceneDrawable(i);
        boolean z = true;
        if (aiSceneDrawable == null) {
            aiSceneDrawable = getResources().getDrawable(this.mAiSceneResources[1]);
        }
        ImageView topImage = getTopImage(201);
        if (aiSceneDrawable != null && topImage != null) {
            topImage.setImageDrawable(aiSceneDrawable);
            if (i != 38) {
                z = false;
            }
            configBottomPopupTips(z);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setClickEnable(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setLiveShotHintVisibility(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.setLiveShotHintVisibility(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setRecordingTimeState(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setRecordingTimeState(i);
        } else {
            FragmentTopAlert.setPendingRecordingState(i);
        }
    }

    public void setShow(boolean z) {
        if (getTopAlert() != null) {
            getTopAlert().setShow(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setUpdateTipState(int i, boolean z) {
        this.mUpdateTipState.put(Integer.valueOf(i), Boolean.valueOf(z));
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void showConfigMenu() {
        Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu)).subscribe();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void showDocumentStateButton(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.showDocumentStateButton(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void startLiveShotAnimation() {
        ImageView topImage = getTopImage(206);
        if (topImage != null) {
            Drawable drawable = topImage.getDrawable();
            if (drawable instanceof LayerDrawable) {
                RotateDrawable rotateDrawable = (RotateDrawable) ((LayerDrawable) drawable).getDrawable(0);
                ObjectAnimator objectAnimator = this.mLiveShotAnimator;
                if (objectAnimator == null || objectAnimator.getTarget() != rotateDrawable) {
                    this.mLiveShotAnimator = ObjectAnimator.ofInt(rotateDrawable, MiStat.Param.LEVEL, 0, 10000);
                    this.mLiveShotAnimator.setDuration(1000L);
                    this.mLiveShotAnimator.setInterpolator(new k());
                }
                if (this.mLiveShotAnimator.isRunning()) {
                    this.mLiveShotAnimator.cancel();
                }
                this.mLiveShotAnimator.start();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(172, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateConfigItem(int... iArr) {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        for (int i : iArr) {
            ImageView topImage = getTopImage(i);
            if (topImage != null) {
                setTopImageResource((TopConfigItem) topImage.getTag(), topImage, ((BaseFragment) this).mCurrentMode, dataItemConfig, false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateContentDescription() {
        ImageView topImage = getTopImage(196);
        if (topImage != null) {
            topImage.setContentDescription(getString(R.string.accessibility_filter_open_panel));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void updateLyingDirectHint(boolean z, boolean z2) {
        if (!z2) {
            this.mIsShowTopLyingDirectHint = z;
        }
        if (!isExtraMenuShowing()) {
            FragmentTopAlert topAlert = getTopAlert();
            if (isTopAlertShowing(topAlert)) {
                topAlert.updateLyingDirectHint(this.mIsShowTopLyingDirectHint);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateRecordingTime(String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.updateRecordingTime(str);
        }
    }
}
