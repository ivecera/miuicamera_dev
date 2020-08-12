package com.android.camera.fragment.top;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigSlowMotionQuality;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningAutoZoom;
import com.android.camera.data.data.runing.ComponentRunningDocument;
import com.android.camera.data.data.runing.ComponentRunningDualVideo;
import com.android.camera.data.data.runing.ComponentRunningSubtitle;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.module.ModuleManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorImageView;

public class ExtraAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnTouchListener {
    private DataItemConfig mDataItemConfig;
    private DataItemRunning mDataItemRunning;
    private int mDegree;
    private int mImageNormalColor;
    private int mItemHeight;
    private View.OnClickListener mOnClickListener;
    private int mSelectedColor;
    private SupportedConfigs mSupportedConfigs;
    private int mTAG = -1;
    private int mTextNormalColor;
    private int mUnableClickColor;

    public ExtraAdapter(SupportedConfigs supportedConfigs, View.OnClickListener onClickListener, int i) {
        this.mSupportedConfigs = supportedConfigs;
        this.mOnClickListener = onClickListener;
        this.mDataItemRunning = DataRepository.dataItemRunning();
        this.mDataItemConfig = DataRepository.dataItemConfig();
        this.mTextNormalColor = ColorConstant.COLOR_COMMON_NORMAL;
        this.mImageNormalColor = -1315861;
        this.mSelectedColor = -15101209;
        this.mUnableClickColor = 1207959551;
        this.mItemHeight = i;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mSupportedConfigs.getLength();
    }

    /* JADX WARNING: Removed duplicated region for block: B:121:0x0392  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0396  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x03a7  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x03b6  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x03c7  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x03e5  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x03ed  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x03f1  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x03f4  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x03fc  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0400  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0414  */
    public void onBindViewHolder(final CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        boolean z;
        int i2;
        boolean z2;
        int i3;
        int i4;
        boolean isLiveShotOn;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z3;
        boolean z4;
        int i9;
        int valueSelectedDrawable;
        int displayTitleString;
        boolean z5;
        int valueDisplayString;
        int resIcon;
        int i10;
        int i11;
        int i12;
        int i13;
        boolean z6;
        int i14;
        int i15;
        int i16;
        boolean z7;
        int config = this.mSupportedConfigs.getConfig(i);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setMinimumHeight(this.mItemHeight);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setTag(Integer.valueOf(config));
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setOnClickListener(this.mOnClickListener);
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        boolean z8 = false;
        String str = null;
        if (config != 206) {
            if (config == 213) {
                ComponentConfigSlowMotionQuality componentConfigSlowMotionQuality = this.mDataItemConfig.getComponentConfigSlowMotionQuality();
                valueSelectedDrawable = componentConfigSlowMotionQuality.getValueSelectedDrawable(currentMode);
                displayTitleString = componentConfigSlowMotionQuality.getDisplayTitleString();
                z5 = !componentConfigSlowMotionQuality.disableUpdate();
                valueDisplayString = componentConfigSlowMotionQuality.getValueDisplayString(currentMode);
            } else if (config != 216) {
                if (config != 255) {
                    if (config != 225) {
                        if (config == 226) {
                            ComponentRunningTimer componentRunningTimer = this.mDataItemRunning.getComponentRunningTimer();
                            boolean isSwitchOn = componentRunningTimer.isSwitchOn();
                            i7 = componentRunningTimer.getValueSelectedDrawable(160);
                            i14 = componentRunningTimer.getValueDisplayString(160);
                            z4 = isSwitchOn;
                            z = true;
                        } else if (config == 260) {
                            isLiveShotOn = CameraSettings.isProVideoLogOpen(currentMode);
                            i5 = R.drawable.ic_config_video_log_off;
                            i6 = R.string.log_format;
                        } else if (config != 261) {
                            switch (config) {
                                case 208:
                                    ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setOnTouchListener(this);
                                    ComponentConfigVideoQuality componentConfigVideoQuality = this.mDataItemConfig.getComponentConfigVideoQuality();
                                    valueSelectedDrawable = componentConfigVideoQuality.getValueSelectedDrawable(currentMode);
                                    displayTitleString = componentConfigVideoQuality.getDisplayTitleString();
                                    z5 = !componentConfigVideoQuality.disableUpdate();
                                    valueDisplayString = componentConfigVideoQuality.getValueDisplayString(currentMode);
                                    break;
                                case 209:
                                    ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
                                    int menuDrawable = componentUltraPixel.getMenuDrawable();
                                    str = componentUltraPixel.getMenuString();
                                    z2 = CameraSettings.isUltraPixelOn();
                                    i2 = menuDrawable;
                                    i4 = -1;
                                    i3 = -1;
                                    z = true;
                                    break;
                                case 210:
                                    ComponentConfigRatio componentConfigRatio = this.mDataItemConfig.getComponentConfigRatio();
                                    int valueSelectedDrawable2 = componentConfigRatio.getValueSelectedDrawable(currentMode);
                                    int displayTitleString2 = componentConfigRatio.getDisplayTitleString();
                                    int valueDisplayString2 = componentConfigRatio.getValueDisplayString(currentMode);
                                    boolean z9 = !(currentMode == 163 || currentMode == 165) || !TextUtils.equals(componentConfigRatio.getComponentValue(currentMode), ComponentConfigRatio.RATIO_4X3) || !this.mDataItemRunning.getComponentRunningAIWatermark().getAIWatermarkEnable();
                                    if (!CameraSettings.isGifOn() || currentMode != 184) {
                                        i15 = valueSelectedDrawable2;
                                    } else {
                                        i15 = R.drawable.ic_config_1_1;
                                        z9 = false;
                                    }
                                    i2 = i15;
                                    z2 = false;
                                    i4 = valueDisplayString2;
                                    z8 = true;
                                    z = z9;
                                    i3 = displayTitleString2;
                                    break;
                                default:
                                    switch (config) {
                                        case 219:
                                            i16 = R.drawable.ic_config_reference_line;
                                            i3 = R.string.pref_camera_referenceline_title;
                                            z2 = DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false);
                                            i2 = i16;
                                            i4 = -1;
                                            z = true;
                                            break;
                                        case 220:
                                            ComponentRunningSubtitle componentRunningSubtitle = DataRepository.dataItemRunning().getComponentRunningSubtitle();
                                            z3 = componentRunningSubtitle.isEnabled(currentMode);
                                            resIcon = componentRunningSubtitle.getResIcon(z3);
                                            i8 = componentRunningSubtitle.getResText();
                                            break;
                                        case 221:
                                            ComponentRunningDocument componentRunningDocument = DataRepository.dataItemRunning().getComponentRunningDocument();
                                            z3 = componentRunningDocument.isEnabled(currentMode);
                                            resIcon = componentRunningDocument.getResIcon(z3);
                                            i8 = componentRunningDocument.getResText();
                                            break;
                                        case 222:
                                            ComponentRunningDualVideo componentRunningDualVideo = DataRepository.dataItemRunning().getmComponentRunningDualVideo();
                                            z3 = componentRunningDualVideo.isEnabled(currentMode);
                                            resIcon = componentRunningDualVideo.getResIcon(z3);
                                            i8 = componentRunningDualVideo.getDisplayTitleString();
                                            break;
                                        case 223:
                                            ComponentRunningAIWatermark componentRunningAIWatermark = this.mDataItemRunning.getComponentRunningAIWatermark();
                                            ComponentConfigRatio componentConfigRatio2 = this.mDataItemConfig.getComponentConfigRatio();
                                            int cameraId = CameraSettings.getCameraId();
                                            boolean z10 = 21 == cameraId || 22 == cameraId;
                                            if (!TextUtils.equals(componentConfigRatio2.getComponentValue(currentMode), ComponentConfigRatio.RATIO_4X3) || z10) {
                                                this.mDataItemRunning.getComponentRunningAIWatermark().setAIWatermarkEnable(false);
                                                z7 = false;
                                            } else {
                                                z7 = true;
                                            }
                                            boolean aIWatermarkEnable = componentRunningAIWatermark.getAIWatermarkEnable();
                                            z = z7;
                                            i4 = -1;
                                            i2 = componentRunningAIWatermark.getResIcon(aIWatermarkEnable);
                                            i3 = R.string.ai_watermark_title;
                                            z2 = aIWatermarkEnable;
                                            break;
                                        default:
                                            switch (config) {
                                                case 228:
                                                    ComponentRunningTiltValue componentRunningTiltValue = this.mDataItemRunning.getComponentRunningTiltValue();
                                                    boolean isSwitchOn2 = this.mDataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode");
                                                    if (!isSwitchOn2) {
                                                        i14 = R.string.config_name_tilt;
                                                        i7 = R.drawable.ic_config_tilt;
                                                        z = true;
                                                        z4 = isSwitchOn2;
                                                        break;
                                                    } else {
                                                        z4 = isSwitchOn2;
                                                        i8 = componentRunningTiltValue.getValueDisplayString(160);
                                                        z = true;
                                                        i7 = componentRunningTiltValue.getValueSelectedDrawable(160);
                                                        i4 = -1;
                                                        break;
                                                    }
                                                case 229:
                                                    boolean isGradienterOn = CameraSettings.isGradienterOn();
                                                    if ((baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523) || !CameraSettings.isAutoZoomEnabled(currentMode)) {
                                                        i3 = R.string.config_name_straighten;
                                                        i4 = -1;
                                                        z = true;
                                                        i2 = R.drawable.ic_config_straighten;
                                                        z2 = isGradienterOn;
                                                        break;
                                                    } else {
                                                        i10 = R.string.config_name_straighten;
                                                        i13 = -1;
                                                        z6 = true;
                                                        i12 = R.drawable.ic_config_straighten;
                                                        z2 = false;
                                                        break;
                                                    }
                                                case 230:
                                                    i16 = R.drawable.ic_config_hht;
                                                    i3 = R.string.config_name_hht;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_hand_night_key");
                                                    i2 = i16;
                                                    i4 = -1;
                                                    z = true;
                                                    break;
                                                case 231:
                                                    i16 = R.drawable.ic_config_magic;
                                                    i3 = R.string.config_name_magic;
                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_camera_ubifocus_key");
                                                    i2 = i16;
                                                    i4 = -1;
                                                    z = true;
                                                    break;
                                                default:
                                                    switch (config) {
                                                        case 233:
                                                            i16 = R.drawable.ic_config_fast_motion;
                                                            i3 = R.string.pref_video_speed_fast_title;
                                                            z2 = ModuleManager.isFastMotionModule();
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 234:
                                                            i16 = R.drawable.ic_config_scene;
                                                            i3 = R.string.config_name_scene;
                                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key");
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 235:
                                                            i16 = R.drawable.ic_config_group;
                                                            i3 = R.string.config_name_group;
                                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_groupshot_mode_key");
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 236:
                                                            i16 = R.drawable.ic_config_magic_mirror;
                                                            i3 = R.string.pref_camera_magic_mirror_title;
                                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_magic_mirror_key");
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 237:
                                                            i8 = R.string.pref_camera_picture_format_entry_raw;
                                                            z3 = DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(currentMode);
                                                            if (!z3) {
                                                                resIcon = R.drawable.ic_raw_off;
                                                                break;
                                                            } else {
                                                                resIcon = R.drawable.ic_raw_on;
                                                                break;
                                                            }
                                                        case 238:
                                                            i16 = R.drawable.ic_config_gender_age;
                                                            i3 = R.string.pref_camera_show_gender_age_config_title;
                                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_show_gender_age_key");
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 239:
                                                            ComponentConfigBeauty componentConfigBeauty = this.mDataItemConfig.getComponentConfigBeauty();
                                                            z2 = componentConfigBeauty.isSwitchOn(currentMode);
                                                            i2 = componentConfigBeauty.getValueSelectedDrawable(currentMode);
                                                            i3 = componentConfigBeauty.getValueDisplayString(currentMode);
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 240:
                                                            i16 = R.drawable.ic_config_dual_watermark;
                                                            i3 = R.string.pref_camera_device_watermark_title;
                                                            z2 = CameraSettings.isDualCameraWaterMarkOpen();
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 241:
                                                            i16 = R.drawable.ic_config_super_resolution;
                                                            i3 = R.string.config_name_super_resolution;
                                                            z2 = this.mDataItemRunning.isSwitchOn("pref_camera_super_resolution_key");
                                                            i2 = i16;
                                                            i4 = -1;
                                                            z = true;
                                                            break;
                                                        case 242:
                                                            if (!Util.isGlobalVersion()) {
                                                                i11 = R.drawable.ic_config_ai_detect_unselected;
                                                                i10 = R.string.pref_ai_detect;
                                                                break;
                                                            } else {
                                                                i11 = R.drawable.ic_config_ai_glens;
                                                                i10 = R.string.pref_google_lens;
                                                                break;
                                                            }
                                                        default:
                                                            switch (config) {
                                                                case 251:
                                                                    isLiveShotOn = CameraSettings.isCinematicAspectRatioEnabled(currentMode);
                                                                    i5 = R.drawable.ic_cinematic_aspect_ratio_menu;
                                                                    i6 = R.string.moive_frame;
                                                                    break;
                                                                case 252:
                                                                    i16 = R.drawable.ic_config_hand_gesture;
                                                                    i3 = R.string.hand_gesture_tip;
                                                                    z2 = this.mDataItemRunning.isSwitchOn("pref_hand_gesture");
                                                                    i2 = i16;
                                                                    i4 = -1;
                                                                    z = true;
                                                                    break;
                                                                case 253:
                                                                    ComponentRunningAutoZoom componentRunningAutoZoom = DataRepository.dataItemRunning().getComponentRunningAutoZoom();
                                                                    z3 = componentRunningAutoZoom.isEnabled(currentMode);
                                                                    resIcon = componentRunningAutoZoom.getResIcon(z3);
                                                                    i8 = componentRunningAutoZoom.getResText();
                                                                    break;
                                                                default:
                                                                    i4 = -1;
                                                                    i3 = -1;
                                                                    z = true;
                                                                    z2 = false;
                                                                    i2 = 0;
                                                                    break;
                                                            }
                                                    }
                                            }
                                    }
                            }
                            TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
                            ColorImageView colorImageView = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
                            textView.setSelected(true);
                            if (i3 == -1) {
                                textView.setText(i3);
                            } else {
                                textView.setText(str);
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(textView.getText());
                            if (i4 != -1) {
                                sb.append(((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getString(i4));
                            }
                            if (!z2) {
                                sb.append(((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getString(R.string.accessibility_open));
                            } else if (!z8) {
                                sb.append(((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getString(R.string.accessibility_closed));
                            }
                            ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setContentDescription(sb);
                            ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setEnabled(z);
                            int i17 = !z ? z2 ? this.mSelectedColor : this.mTextNormalColor : this.mUnableClickColor;
                            int i18 = !z2 ? this.mSelectedColor : this.mImageNormalColor;
                            textView.setTextColor(i17);
                            switch (i2) {
                                case R.drawable.ai_watermark_enter_normal:
                                case R.drawable.ai_watermark_enter_select:
                                case R.drawable.ic_config_16_9:
                                case R.drawable.ic_config_1_1:
                                case R.drawable.ic_config_4_3:
                                case R.drawable.ic_config_4k_30_disable:
                                case R.drawable.ic_config_8k_30_disable:
                                case R.drawable.ic_config_fullscreen:
                                case R.drawable.ic_config_straighten:
                                    if (z) {
                                        colorImageView.setColor(i18);
                                        break;
                                    } else {
                                        colorImageView.setColor(this.mUnableClickColor);
                                        break;
                                    }
                                default:
                                    colorImageView.setColor(i18);
                                    break;
                            }
                            colorImageView.setImageResource(i2);
                            if ((!Util.isAccessible() || Util.isSetContentDesc()) && this.mTAG == config) {
                                ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.postDelayed(new Runnable() {
                                    /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

                                    public void run() {
                                        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.sendAccessibilityEvent(128);
                                    }
                                }, 100);
                            }
                            return;
                        } else {
                            isLiveShotOn = CameraSettings.isProVideoHistogramOpen(currentMode);
                            i5 = R.drawable.ic_config_histogram_off;
                            i6 = R.string.parameter_histogram_title;
                        }
                        i8 = i14;
                        i4 = -1;
                        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
                        ColorImageView colorImageView2 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
                        textView2.setSelected(true);
                        if (i3 == -1) {
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(textView2.getText());
                        if (i4 != -1) {
                        }
                        if (!z2) {
                        }
                        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setContentDescription(sb2);
                        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setEnabled(z);
                        if (!z) {
                        }
                        if (!z2) {
                        }
                        textView2.setTextColor(i17);
                        switch (i2) {
                            case R.drawable.ai_watermark_enter_normal:
                            case R.drawable.ai_watermark_enter_select:
                            case R.drawable.ic_config_16_9:
                            case R.drawable.ic_config_1_1:
                            case R.drawable.ic_config_4_3:
                            case R.drawable.ic_config_4k_30_disable:
                            case R.drawable.ic_config_8k_30_disable:
                            case R.drawable.ic_config_fullscreen:
                            case R.drawable.ic_config_straighten:
                                break;
                        }
                        colorImageView2.setImageResource(i2);
                        if (!Util.isAccessible()) {
                        }
                        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.postDelayed(new Runnable() {
                            /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

                            public void run() {
                                ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.sendAccessibilityEvent(128);
                            }
                        }, 100);
                    }
                    i11 = R.drawable.ic_config_extra_setting;
                    i10 = R.string.config_name_setting;
                    i12 = i11;
                    i13 = -1;
                    z6 = true;
                    z2 = false;
                    TextView textView22 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
                    ColorImageView colorImageView22 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
                    textView22.setSelected(true);
                    if (i3 == -1) {
                    }
                    StringBuilder sb22 = new StringBuilder();
                    sb22.append(textView22.getText());
                    if (i4 != -1) {
                    }
                    if (!z2) {
                    }
                    ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setContentDescription(sb22);
                    ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setEnabled(z);
                    if (!z) {
                    }
                    if (!z2) {
                    }
                    textView22.setTextColor(i17);
                    switch (i2) {
                        case R.drawable.ai_watermark_enter_normal:
                        case R.drawable.ai_watermark_enter_select:
                        case R.drawable.ic_config_16_9:
                        case R.drawable.ic_config_1_1:
                        case R.drawable.ic_config_4_3:
                        case R.drawable.ic_config_4k_30_disable:
                        case R.drawable.ic_config_8k_30_disable:
                        case R.drawable.ic_config_fullscreen:
                        case R.drawable.ic_config_straighten:
                            break;
                    }
                    colorImageView22.setImageResource(i2);
                    if (!Util.isAccessible()) {
                    }
                    ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.postDelayed(new Runnable() {
                        /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

                        public void run() {
                            ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.sendAccessibilityEvent(128);
                        }
                    }, 100);
                }
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                z3 = componentRunningMacroMode.isSwitchOn(currentMode);
                resIcon = componentRunningMacroMode.getResIcon(z3);
                i8 = componentRunningMacroMode.getResText();
                i9 = resIcon;
                z = true;
                z4 = z3;
                i4 = -1;
                TextView textView222 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
                ColorImageView colorImageView222 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
                textView222.setSelected(true);
                if (i3 == -1) {
                }
                StringBuilder sb222 = new StringBuilder();
                sb222.append(textView222.getText());
                if (i4 != -1) {
                }
                if (!z2) {
                }
                ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setContentDescription(sb222);
                ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setEnabled(z);
                if (!z) {
                }
                if (!z2) {
                }
                textView222.setTextColor(i17);
                switch (i2) {
                    case R.drawable.ai_watermark_enter_normal:
                    case R.drawable.ai_watermark_enter_select:
                    case R.drawable.ic_config_16_9:
                    case R.drawable.ic_config_1_1:
                    case R.drawable.ic_config_4_3:
                    case R.drawable.ic_config_4k_30_disable:
                    case R.drawable.ic_config_8k_30_disable:
                    case R.drawable.ic_config_fullscreen:
                    case R.drawable.ic_config_straighten:
                        break;
                }
                colorImageView222.setImageResource(i2);
                if (!Util.isAccessible()) {
                }
                ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.postDelayed(new Runnable() {
                    /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

                    public void run() {
                        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.sendAccessibilityEvent(128);
                    }
                }, 100);
            } else {
                isLiveShotOn = baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523;
                i5 = R.drawable.ic_config_vv_normal;
                i6 = R.string.vv_mode_menu_item_name;
            }
            i3 = displayTitleString;
            i2 = valueSelectedDrawable;
            z2 = false;
            z8 = true;
            TextView textView2222 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
            ColorImageView colorImageView2222 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
            textView2222.setSelected(true);
            if (i3 == -1) {
            }
            StringBuilder sb2222 = new StringBuilder();
            sb2222.append(textView2222.getText());
            if (i4 != -1) {
            }
            if (!z2) {
            }
            ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setContentDescription(sb2222);
            ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setEnabled(z);
            if (!z) {
            }
            if (!z2) {
            }
            textView2222.setTextColor(i17);
            switch (i2) {
                case R.drawable.ai_watermark_enter_normal:
                case R.drawable.ai_watermark_enter_select:
                case R.drawable.ic_config_16_9:
                case R.drawable.ic_config_1_1:
                case R.drawable.ic_config_4_3:
                case R.drawable.ic_config_4k_30_disable:
                case R.drawable.ic_config_8k_30_disable:
                case R.drawable.ic_config_fullscreen:
                case R.drawable.ic_config_straighten:
                    break;
            }
            colorImageView2222.setImageResource(i2);
            if (!Util.isAccessible()) {
            }
            ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.postDelayed(new Runnable() {
                /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

                public void run() {
                    ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.sendAccessibilityEvent(128);
                }
            }, 100);
        }
        isLiveShotOn = CameraSettings.isLiveShotOn();
        i5 = isLiveShotOn ? R.drawable.ic_motionphoto_highlight_extra : R.drawable.ic_motionphoto_extra;
        i6 = R.string.camera_liveshot_on_tip;
        i9 = i5;
        i8 = i6;
        z = true;
        z4 = z3;
        i4 = -1;
        TextView textView22222 = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
        ColorImageView colorImageView22222 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
        textView22222.setSelected(true);
        if (i3 == -1) {
        }
        StringBuilder sb22222 = new StringBuilder();
        sb22222.append(textView22222.getText());
        if (i4 != -1) {
        }
        if (!z2) {
        }
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setContentDescription(sb22222);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setEnabled(z);
        if (!z) {
        }
        if (!z2) {
        }
        textView22222.setTextColor(i17);
        switch (i2) {
            case R.drawable.ai_watermark_enter_normal:
            case R.drawable.ai_watermark_enter_select:
            case R.drawable.ic_config_16_9:
            case R.drawable.ic_config_1_1:
            case R.drawable.ic_config_4_3:
            case R.drawable.ic_config_4k_30_disable:
            case R.drawable.ic_config_8k_30_disable:
            case R.drawable.ic_config_fullscreen:
            case R.drawable.ic_config_straighten:
                break;
        }
        colorImageView22222.setImageResource(i2);
        if (!Util.isAccessible()) {
        }
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.postDelayed(new Runnable() {
            /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

            public void run() {
                ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.sendAccessibilityEvent(128);
            }
        }, 100);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_top_config_extra_item, viewGroup, false);
        int i2 = this.mDegree;
        if (i2 != 0) {
            ViewCompat.setRotation(inflate, (float) i2);
        }
        return new CommonRecyclerViewHolder(inflate);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        View findViewById = view.findViewById(R.id.extra_item_image);
        if (findViewById == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1 || (action != 2 && action == 3)) {
                findViewById.setScaleX(1.0f);
                findViewById.setScaleY(1.0f);
            }
            return false;
        }
        findViewById.setScaleX(0.93f);
        findViewById.setScaleY(0.93f);
        return false;
    }

    public void setNewDegree(int i) {
        this.mDegree = i;
    }

    public void setOnClictTag(int i) {
        this.mTAG = i;
    }
}
