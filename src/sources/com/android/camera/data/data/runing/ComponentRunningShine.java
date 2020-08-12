package com.android.camera.data.data.runing;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComponentRunningShine extends ComponentData {
    public static final int ENTRY_NONE = -1;
    public static final int ENTRY_POPUP_BEAUTY = 5;
    public static final int ENTRY_POPUP_SHINE = 4;
    public static final int ENTRY_TOP_BEAUTY = 2;
    public static final int ENTRY_TOP_FILTER = 3;
    public static final int ENTRY_TOP_SHINE = 1;
    public static final int FILTER_NATIVE_NONE_ID = 0;
    public static final String SHINE_BEAUTY_LEVEL_SMOOTH = "2";
    public static final String SHINE_BEAUTY_LEVEL_SWITCH = "1";
    public static final String SHINE_EYE_LIGHT = "9";
    public static final String SHINE_FIGURE = "6";
    public static final String SHINE_FILTER = "7";
    public static final String SHINE_LIGHTING = "8";
    public static final String SHINE_LIVE_BEAUTY = "11";
    public static final String SHINE_LIVE_FILTER = "10";
    public static final String SHINE_LIVE_SPEED = "13";
    public static final String SHINE_LIVE_STICKER = "12";
    public static final String SHINE_MAKEUP = "5";
    public static final String SHINE_MODEL_ADVANCE = "3";
    public static final String SHINE_MODEL_REMODELING = "4";
    public static final String SHINE_VIDEO_BOKEH_LEVEL = "14";
    private static final String TAG = "ComponentRunningShine";
    private BeautyValues mBeautyValues;
    private int mBeautyVersion;
    private boolean mCurrentStatus;
    private String mCurrentTipType;
    @ShineType
    private String mCurrentType;
    @ShineType
    private String mDefaultType;
    private boolean mIsClosed;
    private HashMap<String, Boolean> mIsVideoBeautySwitchedOnMap = new HashMap<>();
    @ShineEntry
    private int mShineEntry;
    private boolean mSupportBeautyBody;
    private boolean mSupportBeautyLevel;
    private boolean mSupportBeautyMakeUp;
    private boolean mSupportBeautyModel;
    private boolean mSupportColorRententionBack;
    private boolean mSupportColorRententionFront;
    private boolean mSupportSmoothLevel;
    private boolean mSupportVideoBokehLevel;
    private boolean mSupportVideoFilter;
    private TypeElementsBeauty mTypeElementsBeauty = new TypeElementsBeauty(this);

    public @interface ShineEntry {
    }

    public @interface ShineType {
    }

    public ComponentRunningShine(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private ComponentDataItem generateBeautyLevelItem(boolean z) {
        return b.Il() ? new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "1") : new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_beauty, "1");
    }

    private ComponentDataItem generateFigureItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, isSmoothDependBeautyVersion() ? R.string.beauty_fragment_tab_name_3d_beauty : R.string.beauty_body, "6");
    }

    private ComponentDataItem generateFilterItem() {
        return new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "7");
    }

    private ComponentDataItem generateMakeupItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_makeup, "5");
    }

    private ComponentDataItem generateModelItem() {
        if (!b.Il()) {
            return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_makeup, "3");
        }
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, isSmoothDependBeautyVersion() ? R.string.beauty_fragment_tab_name_3d_beauty : R.string.beauty_fragment_tab_name_3d_remodeling, "4");
    }

    private ComponentDataItem generateSmoothLevelItem(boolean z) {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "2");
    }

    private ComponentDataItem generateVideoBokeh() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.fragment_tab_name_bokeh, SHINE_VIDEO_BOKEH_LEVEL);
    }

    public void clearArrayMap() {
        this.mIsVideoBeautySwitchedOnMap.clear();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00cb A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00dc A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00ee A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0036 A[SYNTHETIC] */
    public boolean determineStatus(int i) {
        char c2;
        int shaderEffect;
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        boolean z = false;
        if (isClosed()) {
            this.mCurrentStatus = false;
        } else if (isSmoothBarVideoBeautyVersion(i)) {
            this.mCurrentStatus = DataRepository.dataItemRunning().getComponentRunningShine().isVideoBeautyOpen(i);
            return this.mCurrentStatus;
        } else {
            Boolean bool = null;
            Boolean bool2 = null;
            Boolean bool3 = null;
            for (ComponentDataItem componentDataItem : ((ComponentData) this).mItems) {
                if (componentDataItem != null) {
                    String str = componentDataItem.mValue;
                    int hashCode = str.hashCode();
                    if (hashCode != 1567) {
                        if (hashCode != 1568) {
                            if (hashCode != 1571) {
                                switch (hashCode) {
                                    case 49:
                                        if (str.equals("1")) {
                                            c2 = 0;
                                            break;
                                        }
                                        break;
                                    case 50:
                                        if (str.equals("2")) {
                                            c2 = 5;
                                            break;
                                        }
                                        break;
                                    case 51:
                                        if (str.equals("3")) {
                                            c2 = 1;
                                            break;
                                        }
                                        break;
                                    case 52:
                                        if (str.equals("4")) {
                                            c2 = 2;
                                            break;
                                        }
                                        break;
                                    case 53:
                                        if (str.equals("5")) {
                                            c2 = 3;
                                            break;
                                        }
                                        break;
                                    case 54:
                                        if (str.equals("6")) {
                                            c2 = 4;
                                            break;
                                        }
                                        break;
                                    case 55:
                                        if (str.equals("7")) {
                                            c2 = 7;
                                            break;
                                        }
                                        break;
                                }
                                switch (c2) {
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                        if (bool == null) {
                                            bool = Boolean.valueOf(CameraSettings.isFaceBeautyOn(i, this.mBeautyValues));
                                            break;
                                        } else {
                                            continue;
                                            continue;
                                            continue;
                                            continue;
                                            continue;
                                        }
                                    case 5:
                                        if (bool == null) {
                                            bool = Boolean.valueOf(CameraSettings.isFaceBeautyOn(i, this.mBeautyValues));
                                            break;
                                        } else {
                                            continue;
                                            continue;
                                            continue;
                                            continue;
                                            continue;
                                        }
                                    case 6:
                                        if (bool == null) {
                                            if (i != 183) {
                                                bool = Boolean.valueOf(CameraSettings.isLiveBeautyOpen());
                                                break;
                                            } else {
                                                bool = Boolean.valueOf(CameraSettings.isMiLiveBeautyOpen());
                                                break;
                                            }
                                        } else {
                                            continue;
                                            continue;
                                            continue;
                                            continue;
                                            continue;
                                        }
                                    case 7:
                                        if (bool2 == null && (shaderEffect = CameraSettings.getShaderEffect()) != FilterInfo.FILTER_ID_NONE && shaderEffect > 0) {
                                            bool2 = true;
                                            break;
                                        }
                                    case '\b':
                                        if (bool2 == null && DataRepository.dataItemLive().getLiveFilter() != 0) {
                                            bool2 = true;
                                            break;
                                        }
                                    case '\t':
                                        if (bool3 == null && CameraSettings.getVideoBokehRatio() != 0.0f) {
                                            bool3 = true;
                                            break;
                                        }
                                }
                            } else if (str.equals(SHINE_VIDEO_BOKEH_LEVEL)) {
                                c2 = '\t';
                                switch (c2) {
                                }
                            }
                        } else if (str.equals(SHINE_LIVE_BEAUTY)) {
                            c2 = 6;
                            switch (c2) {
                            }
                        }
                    } else if (str.equals("10")) {
                        c2 = '\b';
                        switch (c2) {
                        }
                    }
                    c2 = 65535;
                    switch (c2) {
                    }
                }
            }
            if ((bool != null && bool.booleanValue()) || ((bool2 != null && bool2.booleanValue()) || (bool3 != null && bool3.booleanValue()))) {
                z = true;
            }
            this.mCurrentStatus = z;
        }
        return this.mCurrentStatus;
    }

    public int getBeautyVersion() {
        return this.mBeautyVersion;
    }

    @DrawableRes
    public int getBottomEntryRes(int i) {
        this.mCurrentStatus = determineStatus(i);
        int i2 = this.mShineEntry;
        return i2 != 4 ? i2 != 5 ? R.drawable.ic_shine_off : this.mCurrentStatus ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off : this.mCurrentStatus ? R.drawable.ic_beauty_tips_on : R.drawable.ic_beauty_tips_normal;
    }

    public boolean getCurrentStatus() {
        return this.mCurrentStatus;
    }

    public String getCurrentTipType() {
        return this.mCurrentTipType;
    }

    @ShineType
    public String getCurrentType() {
        return this.mCurrentType;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return this.mDefaultType;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return null;
    }

    @DrawableRes
    public int getTopConfigEntryRes(int i) {
        this.mCurrentStatus = determineStatus(i);
        int i2 = this.mShineEntry;
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? R.drawable.ic_shine_off : this.mCurrentStatus ? R.drawable.ic_new_effect_button_selected : R.drawable.ic_new_effect_button_normal : this.mCurrentStatus ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off : this.mCurrentStatus ? R.drawable.ic_shine_on : R.drawable.ic_shine_off;
    }

    public int getTopConfigItem() {
        int i = this.mShineEntry;
        if (i == 1 || i == 2 || i == 3) {
            return 212;
        }
        throw new RuntimeException("unknown Shine" + this.mShineEntry);
    }

    public TypeElementsBeauty getTypeElementsBeauty() {
        return this.mTypeElementsBeauty;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isLegacyBeautyVersion() {
        return this.mBeautyVersion == 1;
    }

    public boolean isSmoothBarVideoBeautyVersion(int i) {
        if (i == 162 && this.mSupportSmoothLevel) {
            return true;
        }
        if (i != 169 || !this.mSupportSmoothLevel) {
            return i == 180 && this.mSupportVideoFilter;
        }
        return true;
    }

    public boolean isSmoothDependBeautyVersion() {
        return this.mBeautyVersion == 3;
    }

    public boolean isTopBeautyEntry() {
        return this.mShineEntry == 2;
    }

    public boolean isTopShineEntry() {
        return this.mShineEntry == 1;
    }

    public boolean isVideoBeautyOpen(int i) {
        String str = i + (CameraSettings.isFrontCamera() ? "front" : MistatsConstants.BaseEvent.BACK);
        if (i == 162 || i == 180) {
            return this.mIsVideoBeautySwitchedOnMap.getOrDefault(str, Boolean.FALSE).booleanValue();
        }
        return false;
    }

    public void reInit() {
        if (((ComponentData) this).mItems == null) {
            ((ComponentData) this).mItems = new CopyOnWriteArrayList();
        } else {
            ((ComponentData) this).mItems.clear();
        }
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        reInit();
        this.mBeautyVersion = cameraCapabilities.getBeautyVersion();
        boolean z = true;
        if (this.mBeautyVersion < 0) {
            if (b.Il()) {
                this.mBeautyVersion = 2;
            } else {
                this.mBeautyVersion = 1;
            }
        }
        this.mShineEntry = -1;
        this.mDefaultType = null;
        this.mSupportBeautyLevel = false;
        this.mSupportSmoothLevel = false;
        this.mSupportBeautyModel = false;
        this.mSupportBeautyMakeUp = false;
        this.mSupportBeautyBody = false;
        switch (i) {
            case 161:
                if (!cameraCapabilities.isSupportVideoBeauty()) {
                    this.mShineEntry = 3;
                    ((ComponentData) this).mItems.add(generateFilterItem());
                    break;
                } else {
                    this.mShineEntry = 4;
                    if (i2 == 0) {
                        this.mDefaultType = "7";
                        if (!isSmoothDependBeautyVersion()) {
                            this.mSupportBeautyLevel = true;
                            ((ComponentData) this).mItems.add(generateBeautyLevelItem(i2 == 1));
                            if (DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                this.mSupportBeautyBody = true;
                                ((ComponentData) this).mItems.add(generateFigureItem());
                            }
                        } else {
                            this.mSupportSmoothLevel = true;
                            if (DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                this.mSupportBeautyBody = true;
                                ((ComponentData) this).mItems.add(generateFigureItem());
                            } else {
                                List<ComponentDataItem> list = ((ComponentData) this).mItems;
                                if (i2 != 1) {
                                    z = false;
                                }
                                list.add(generateSmoothLevelItem(z));
                            }
                        }
                    } else if (!isSmoothDependBeautyVersion()) {
                        this.mSupportBeautyLevel = true;
                        List<ComponentDataItem> list2 = ((ComponentData) this).mItems;
                        if (i2 != 1) {
                            z = false;
                        }
                        list2.add(generateBeautyLevelItem(z));
                    } else {
                        this.mSupportSmoothLevel = true;
                        List<ComponentDataItem> list3 = ((ComponentData) this).mItems;
                        if (i2 != 1) {
                            z = false;
                        }
                        list3.add(generateSmoothLevelItem(z));
                    }
                    ((ComponentData) this).mItems.add(generateFilterItem());
                    break;
                }
            case 162:
            case 169:
                if (cameraCapabilities.isSupportVideoBeauty()) {
                    this.mCurrentTipType = "2";
                    if (!isSmoothDependBeautyVersion()) {
                        this.mShineEntry = 2;
                        this.mSupportBeautyLevel = true;
                        ((ComponentData) this).mItems.add(generateBeautyLevelItem(i2 == 1));
                    } else {
                        this.mShineEntry = 1;
                        this.mSupportSmoothLevel = true;
                        ((ComponentData) this).mItems.add(generateSmoothLevelItem(i2 == 1));
                    }
                }
                boolean isSupportVideoFilter = cameraCapabilities.isSupportVideoFilter();
                Log.i(TAG, "isSupportVideoFilter: " + isSupportVideoFilter);
                if (isSupportVideoFilter) {
                    this.mSupportVideoFilter = true;
                    if (i2 == 0) {
                        this.mDefaultType = "7";
                        this.mCurrentTipType = "7";
                    }
                    ((ComponentData) this).mItems.add(generateFilterItem());
                }
                boolean isSupportVideoBokehAdjust = cameraCapabilities.isSupportVideoBokehAdjust();
                Log.i(TAG, "isSupportVideoBokehLevel:" + isSupportVideoBokehAdjust);
                if (isSupportVideoBokehAdjust) {
                    this.mSupportVideoBokehLevel = true;
                    ((ComponentData) this).mItems.add(generateVideoBokeh());
                }
                this.mSupportColorRententionFront = cameraCapabilities.isSupportVideoFilterColorRetentionFront();
                this.mSupportColorRententionBack = cameraCapabilities.isSupportVideoFilterColorRetentionBack();
                Log.i(TAG, "mSupportColorRententionFront:" + this.mSupportColorRententionFront + "  mSupportColorRententionBack:" + this.mSupportColorRententionBack);
                if (((ComponentData) this).mItems.size() > 1) {
                    this.mShineEntry = 1;
                    break;
                }
                break;
            case 163:
            case 165:
                if (!CameraSettings.isUltraPixelRearOn()) {
                    if (!isSmoothDependBeautyVersion()) {
                        this.mSupportBeautyLevel = true;
                        ((ComponentData) this).mItems.add(generateBeautyLevelItem(i2 == 1));
                    } else {
                        this.mSupportSmoothLevel = true;
                    }
                    if (i2 == 0) {
                        this.mShineEntry = 1;
                        this.mDefaultType = "7";
                        if (DataRepository.dataItemFeature().isSupportBeautyBody()) {
                            this.mSupportBeautyBody = true;
                            ((ComponentData) this).mItems.add(generateFigureItem());
                        } else if (isSmoothDependBeautyVersion()) {
                            ((ComponentData) this).mItems.add(generateSmoothLevelItem(false));
                        }
                    } else {
                        this.mShineEntry = 4;
                        if (!DataRepository.dataItemFeature().nc()) {
                            this.mSupportBeautyModel = true;
                            ((ComponentData) this).mItems.add(generateModelItem());
                            if (DataRepository.dataItemFeature().Ce() && cameraCapabilities.isSupportBeautyMakeup()) {
                                this.mSupportBeautyMakeUp = true;
                                ((ComponentData) this).mItems.add(generateMakeupItem());
                            }
                        } else if (isSmoothDependBeautyVersion()) {
                            ((ComponentData) this).mItems.add(generateSmoothLevelItem(true));
                        }
                    }
                } else if (i2 == 0) {
                    this.mShineEntry = 3;
                } else {
                    this.mShineEntry = 4;
                }
                ((ComponentData) this).mItems.add(generateFilterItem());
                break;
            case 167:
            case 175:
                this.mShineEntry = 3;
                ((ComponentData) this).mItems.add(generateFilterItem());
                break;
            case 171:
                this.mShineEntry = 4;
                if (!isSmoothDependBeautyVersion()) {
                    this.mSupportBeautyLevel = true;
                    List<ComponentDataItem> list4 = ((ComponentData) this).mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list4.add(generateBeautyLevelItem(z));
                } else {
                    this.mSupportSmoothLevel = true;
                    List<ComponentDataItem> list5 = ((ComponentData) this).mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list5.add(generateSmoothLevelItem(z));
                }
                ((ComponentData) this).mItems.add(generateFilterItem());
                break;
            case 174:
                this.mShineEntry = 4;
                this.mDefaultType = "10";
                ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, SHINE_LIVE_BEAUTY));
                ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "10"));
                break;
            case 176:
                this.mShineEntry = 4;
                if (isSmoothDependBeautyVersion()) {
                    this.mSupportSmoothLevel = true;
                    List<ComponentDataItem> list6 = ((ComponentData) this).mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list6.add(generateSmoothLevelItem(z));
                    break;
                } else {
                    this.mSupportBeautyLevel = true;
                    List<ComponentDataItem> list7 = ((ComponentData) this).mItems;
                    if (i2 != 1) {
                        z = false;
                    }
                    list7.add(generateBeautyLevelItem(z));
                    break;
                }
            case 177:
            case 184:
                this.mSupportSmoothLevel = true;
                break;
            case 180:
                if (cameraCapabilities.isSupportVideoFilter()) {
                    this.mShineEntry = 3;
                    ((ComponentData) this).mItems.add(generateFilterItem());
                    this.mSupportVideoFilter = true;
                    this.mSupportColorRententionBack = cameraCapabilities.isSupportVideoFilterColorRetentionBack();
                    Log.i(TAG, "mSupportColorRententionFront:" + this.mSupportColorRententionFront + "  mSupportColorRententionBack:" + this.mSupportColorRententionBack);
                    break;
                }
                break;
            case 183:
                this.mShineEntry = 4;
                this.mDefaultType = "7";
                ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, SHINE_LIVE_BEAUTY));
                ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "7"));
                break;
        }
        if (this.mDefaultType == null && !((ComponentData) this).mItems.isEmpty()) {
            this.mDefaultType = ((ComponentData) this).mItems.get(0).mValue;
        }
        this.mCurrentType = this.mDefaultType;
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setCurrentTipType(String str) {
        this.mCurrentTipType = str;
    }

    public void setCurrentType(@ShineType String str) {
        this.mCurrentType = str;
    }

    public void setVideoBeautySwitch(int i, boolean z) {
        String str = CameraSettings.isFrontCamera() ? "front" : MistatsConstants.BaseEvent.BACK;
        this.mIsVideoBeautySwitchedOnMap.put(i + str, Boolean.valueOf(z));
    }

    public boolean supportBeautyBody() {
        return this.mSupportBeautyBody;
    }

    public boolean supportBeautyLevel() {
        return this.mSupportBeautyLevel;
    }

    public boolean supportBeautyMakeUp() {
        return this.mSupportBeautyMakeUp;
    }

    public boolean supportBeautyModel() {
        return this.mSupportBeautyModel;
    }

    public boolean supportColorRentention() {
        return CameraSettings.isFrontCamera() ? this.mSupportColorRententionFront : this.mSupportColorRententionBack;
    }

    public boolean supportPopUpEntry() {
        int i = this.mShineEntry;
        return i == 4 || i == 5;
    }

    public boolean supportSmoothLevel() {
        return this.mSupportSmoothLevel;
    }

    public boolean supportTopConfigEntry() {
        int i = this.mShineEntry;
        return i == 1 || i == 2 || i == 3;
    }

    public boolean supportVideoFilter() {
        return this.mSupportVideoFilter;
    }
}
