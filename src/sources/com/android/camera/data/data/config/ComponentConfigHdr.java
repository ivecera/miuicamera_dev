package com.android.camera.data.data.config;

import android.text.TextUtils;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentConfigHdr extends ComponentData {
    public static final int HDR_UI_STATUS_AUTO = 2;
    public static final int HDR_UI_STATUS_OFF = 0;
    public static final int HDR_UI_STATUS_ON = 1;
    public static final String HDR_VALUE_AUTO = "auto";
    public static final String HDR_VALUE_LIVE = "live";
    public static final String HDR_VALUE_NORMAL = "normal";
    public static final String HDR_VALUE_OFF = "off";
    public static final String HDR_VALUE_ON = "on";
    private boolean mAutoSupported;
    private boolean mIsClosed;
    private boolean mSupportHdrCheckerWhenOn;

    public ComponentConfigHdr(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        ((ComponentData) this).mItems = new ArrayList();
        ((ComponentData) this).mItems.add(new ComponentDataItem(getConfigHDROffRes(), getConfigHDROffRes(), (int) R.string.pref_camera_hdr_entry_off, "off"));
    }

    private int getConfigHDRAutoRes() {
        return R.drawable.ic_new_config_hdr_auto;
    }

    private int getConfigHDRLiveRes() {
        return R.drawable.ic_new_config_hdr_live;
    }

    private int getConfigHDRNormalRes() {
        return R.drawable.ic_new_config_hdr_normal;
    }

    private int getConfigHDROffRes() {
        return R.drawable.ic_new_config_hdr_off;
    }

    public static int getHdrUIStatus(String str) {
        if ("on".equals(str) || "normal".equals(str)) {
            return 1;
        }
        return "auto".equals(str) ? 2 : 0;
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return (!isClosed() && !isEmpty()) ? (171 != i || !CameraSettings.isBackCamera() || !DataRepository.dataItemFeature().Ke()) ? super.getComponentValue(i) : "auto" : "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        if (isClosed() || isEmpty() || CameraSettings.isFrontCamera()) {
            return "off";
        }
        if (171 == i && CameraSettings.isBackCamera() && DataRepository.dataItemFeature().Ke()) {
            return "auto";
        }
        String Bb = DataRepository.dataItemFeature().Bb();
        String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.pref_hdr_value_default);
        if (TextUtils.isEmpty(Bb)) {
            Bb = string;
        }
        if (!TextUtils.isEmpty(Bb)) {
            char c2 = 65535;
            int hashCode = Bb.hashCode();
            if (hashCode != 3551) {
                if (hashCode != 109935) {
                    if (hashCode == 3005871 && Bb.equals("auto")) {
                        c2 = 0;
                    }
                } else if (Bb.equals("off")) {
                    c2 = 2;
                }
            } else if (Bb.equals("on")) {
                c2 = 1;
            }
            if (c2 == 0) {
                return this.mAutoSupported ? "auto" : "off";
            }
            if (c2 == 1) {
                return "on";
            }
            if (c2 == 2) {
                return "off";
            }
        }
        return this.mAutoSupported ? "auto" : "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_hdr_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i != 160) {
            return (i == 162 || i == 169) ? CameraSettings.KEY_VIDEO_HDR : i != 171 ? i != 180 ? CameraSettings.KEY_CAMERA_HDR : CameraSettings.KEY_VIDEO_HDR : CameraSettings.KEY_PORTRAIT_HDR;
        }
        throw new RuntimeException("unspecified hdr");
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getPersistValue(int i) {
        return super.getComponentValue(i);
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return getConfigHDROffRes();
        }
        if ("auto".equals(componentValue)) {
            return getConfigHDRAutoRes();
        }
        if ("normal".equals(componentValue)) {
            return getConfigHDRNormalRes();
        }
        if ("live".equals(componentValue)) {
            return getConfigHDRLiveRes();
        }
        if ("on".equals(componentValue)) {
            return getConfigHDRNormalRes();
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return R.string.accessibility_hdr_off;
        }
        if ("auto".equals(componentValue)) {
            return R.string.accessibility_hdr_auto;
        }
        if ("normal".equals(componentValue)) {
            return R.string.accessibility_hdr_on;
        }
        if ("live".equals(componentValue)) {
            return R.string.accessibility_hdr_live;
        }
        if ("on".equals(componentValue)) {
            return R.string.accessibility_hdr_on;
        }
        return -1;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isHdrOnWithChecker(String str) {
        if ("on".equals(str) || "normal".equals(str)) {
            return this.mSupportHdrCheckerWhenOn;
        }
        return false;
    }

    public boolean isSupportAutoHdr() {
        return this.mAutoSupported;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0075, code lost:
        if (r10 != 0) goto L_0x00f6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00f4  */
    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, int i3) {
        ArrayList arrayList = new ArrayList();
        this.mAutoSupported = false;
        this.mSupportHdrCheckerWhenOn = false;
        if (cameraCapabilities.isSupportHdr()) {
            if (i != 165) {
                if (!(i == 167 || i == 169)) {
                    if (i != 171) {
                        if (i != 180) {
                            switch (i) {
                            }
                        }
                        arrayList.add(new ComponentDataItem(getConfigHDROffRes(), getConfigHDROffRes(), (int) R.string.pref_camera_hdr_entry_off, "off"));
                        if (cameraCapabilities.isSupportAutoHdr()) {
                            this.mAutoSupported = true;
                            arrayList.add(new ComponentDataItem(getConfigHDRAutoRes(), getConfigHDRAutoRes(), (int) R.string.pref_camera_hdr_entry_auto, "auto"));
                        }
                        if (!b.lg || !b.Ol()) {
                            arrayList.add(new ComponentDataItem(getConfigHDRNormalRes(), getConfigHDRNormalRes(), (int) R.string.pref_simple_hdr_entry_on, "normal"));
                        } else {
                            if (!b.og) {
                                arrayList.add(new ComponentDataItem(getConfigHDRNormalRes(), getConfigHDRNormalRes(), (int) R.string.pref_camera_hdr_entry_normal, "normal"));
                            }
                            arrayList.add(new ComponentDataItem(getConfigHDRLiveRes(), getConfigHDRLiveRes(), (int) R.string.pref_camera_hdr_entry_live, "live"));
                        }
                        if (cameraCapabilities.isSupportHdrCheckerStatus()) {
                            this.mSupportHdrCheckerWhenOn = true;
                        }
                    } else if (DataRepository.dataItemFeature().Ke() && CameraSettings.isBackCamera()) {
                        arrayList.add(new ComponentDataItem(getConfigHDROffRes(), getConfigHDROffRes(), (int) R.string.pref_camera_hdr_entry_off, "off"));
                        if (cameraCapabilities.isSupportAutoHdr()) {
                            this.mAutoSupported = true;
                            arrayList.add(new ComponentDataItem(getConfigHDRAutoRes(), getConfigHDRAutoRes(), (int) R.string.pref_camera_hdr_entry_auto, "auto"));
                        }
                    }
                }
                ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
            }
            if (cameraCapabilities.isSupportLightTripartite()) {
            }
            arrayList.add(new ComponentDataItem(getConfigHDROffRes(), getConfigHDROffRes(), (int) R.string.pref_camera_hdr_entry_off, "off"));
            if (cameraCapabilities.isSupportAutoHdr()) {
            }
            if (!b.lg) {
            }
            arrayList.add(new ComponentDataItem(getConfigHDRNormalRes(), getConfigHDRNormalRes(), (int) R.string.pref_simple_hdr_entry_on, "normal"));
            if (cameraCapabilities.isSupportHdrCheckerStatus()) {
            }
            ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
        }
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        setClosed(false);
        super.setComponentValue(i, str);
    }
}
