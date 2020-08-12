package com.android.camera.data.data.config;

import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.data.cloud.DataCloud;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import com.mi.config.a;
import com.mi.config.b;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class SupportedConfigFactory {
    public static final int AI_DETECT = 242;
    public static final int AI_SCENE = 201;
    public static final int AI_WATERMARK = 223;
    public static final int AUTO_ZOOM = 253;
    public static final int AUXILIARY = 257;
    public static final int BACK = 217;
    public static final int BACKLIGHT = 249;
    public static final int BEAUTY = 239;
    public static final int BOKEH = 200;
    public static final int CINEMATIC_ASPECT_RATIO = 251;
    public static final String CLOSE_BY_AI = "e";
    public static final String CLOSE_BY_BOKEH = "f";
    public static final String CLOSE_BY_BURST_SHOOT = "d";
    public static final String CLOSE_BY_DOCUMENT_MODE = "p";
    public static final String CLOSE_BY_FILTER = "k";
    public static final String CLOSE_BY_GROUP = "b";
    public static final String CLOSE_BY_HDR = "g";
    public static final String CLOSE_BY_HHT = "a";
    public static final String CLOSE_BY_MACRO_MODE = "m";
    public static final String CLOSE_BY_MANUAL_MODE = "mm";
    public static final String CLOSE_BY_RATIO = "l";
    public static final String CLOSE_BY_RAW = "n";
    public static final String CLOSE_BY_SUPER_RESOLUTION = "c";
    public static final String CLOSE_BY_ULTRA_PIXEL = "j";
    public static final String CLOSE_BY_ULTRA_PIXEL_PORTRAIT = "o";
    public static final String CLOSE_BY_ULTRA_WIDE = "i";
    public static final String CLOSE_BY_VIDEO = "h";
    public static final int COLOR_ENHANCE = 227;
    public static final int DOCUMENT = 221;
    public static final int DUAL_VIDEO = 222;
    public static final int DUAL_WATER_MARK = 240;
    public static final int EXPOSURE_FEEDBACK = 258;
    public static final int EYE_LIGHT = 254;
    public static final int FAST = 233;
    public static final int FILTER = 196;
    public static final int FLASH = 193;
    public static final int FLASH_BLANK = 177;
    public static final int FOCUS_PEAK = 199;
    public static final int GENDER_AGE = 238;
    public static final int GRADIENTER = 229;
    public static final int GROUP = 235;
    public static final int HAND_GESTURE = 252;
    public static final int HDR = 194;
    public static final int HHT = 230;
    public static final int INVALID = 176;
    public static final int LIGHTING = 203;
    public static final int LIVE_MUSIC_SELECT = 245;
    public static final int LIVE_SHOT = 206;
    public static final int MACRO_MODE = 255;
    public static final int MAGIC_FOCUS = 231;
    public static final int MAGIC_MIRROR = 236;
    public static final int METER = 214;
    public static final int MIMOJI_EDIT = 161;
    public static final int MIMOJI_GIF = 162;
    public static final int MOON = 246;
    public static final int MORE = 197;
    public static final int[] MUTEX_MENU_CONFIGS = {236, 235, 228, 230, 241, 234, 195, 238, 203, 206, 209};
    public static final int[] MUTEX_VIDEO_FEATURES = {233, 212, 218, 220, 253, 255, 216};
    public static final int NEW_SLOW_MOTION = 204;
    public static final int NIGHT = 247;
    public static final int PORTRAIT = 195;
    public static final int RATIO = 210;
    public static final int RAW = 237;
    public static final int REFERENCE_LINE = 219;
    public static final int RGB_HISTOGRAM = 261;
    public static final int SCENE = 234;
    public static final int SETTING = 225;
    public static final int SHINE = 212;
    public static final int SILHOUETTE = 248;
    public static final int SLOW_QUALITY = 213;
    public static final int SUBTITLE = 220;
    public static final int SUPER_EIS = 218;
    public static final int SUPER_RESOLUTION = 241;
    public static final int TILT = 228;
    public static final int TIMER = 226;
    public static final int ULTRA_PIXEL = 209;
    public static final int ULTRA_PIXEL_PORTRAIT = 215;
    public static final int ULTRA_WIDE = 205;
    public static final int ULTRA_WIDE_BOKEH = 207;
    public static final int VIDEO_8K = 256;
    public static final int VIDEO_BOKEH = 243;
    public static final int VIDEO_LOG = 260;
    public static final int VIDEO_QUALITY = 208;
    public static final int VV = 216;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClosableElement {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CloseElementTrigger {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigItem {
    }

    private static TopConfigItem createConfigItem(int i) {
        return new TopConfigItem(i);
    }

    private static TopConfigItem createConfigItem(int i, int i2) {
        return new TopConfigItem(i, i2);
    }

    public static String getConfigKey(int i) {
        if (i == 195) {
            return "pref_camera_portrait_mode_key";
        }
        if (i == 199) {
            return "pref_camera_peak_key";
        }
        if (i == 201) {
            return "pref_camera_ai_scene_mode_key";
        }
        if (i == 206) {
            return "pref_live_shot_enabled";
        }
        if (i == 209) {
            return "pref_ultra_pixel";
        }
        if (i == 221) {
            return "pref_document_mode_key";
        }
        if (i == 223) {
            return "pref_watermark_key";
        }
        if (i == 230) {
            return "pref_camera_hand_night_key";
        }
        if (i == 241) {
            return "pref_camera_super_resolution_key";
        }
        if (i == 258) {
            return "pref_camera_exposure_feedback";
        }
        if (i == 227) {
            return "pref_color_enhance";
        }
        if (i == 228) {
            return "pref_camera_tilt_shift_mode";
        }
        if (i == 252) {
            return "pref_hand_gesture";
        }
        if (i == 253) {
            return "pref_camera_auto_zoom";
        }
        switch (i) {
            case 234:
                return "pref_camera_scenemode_setting_key";
            case 235:
                return "pref_camera_groupshot_mode_key";
            case 236:
                return "pref_camera_magic_mirror_key";
            case 237:
                return "pref_camera_raw_key";
            case 238:
                return "pref_camera_show_gender_age_key";
            default:
                throw new RuntimeException("unknown config item: " + Integer.toHexString(i));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:95:0x0148  */
    public static final SupportedConfigs getSupportedExtraConfigs(int i, int i2, DataCloud.CloudFeature cloudFeature, CameraCapabilities cameraCapabilities, boolean z) {
        SupportedConfigs supportedConfigs = new SupportedConfigs();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        a dataItemFeature = DataRepository.dataItemFeature();
        if (i != 161) {
            boolean z2 = true;
            if (i != 162) {
                if (i != 166) {
                    if (i == 167) {
                        supportedConfigs.add(225);
                        if (dataItemConfig.supportRatio()) {
                            supportedConfigs.add(210);
                        }
                        if (DataRepository.dataItemFeature().c_r_i_m_m() && cameraCapabilities.isSupportRaw()) {
                            supportedConfigs.add(237);
                        }
                        supportedConfigs.add(226);
                        supportedConfigs.add(219);
                        if (b.Fl()) {
                            supportedConfigs.add(229);
                        }
                    } else if (i != 169) {
                        if (i == 180) {
                            supportedConfigs.add(225);
                            supportedConfigs.add(208);
                            if (cameraCapabilities.isSupportedVideoMiMovie()) {
                                supportedConfigs.add(251);
                            }
                            if (cameraCapabilities.isSupportedVideoLogFormat()) {
                                supportedConfigs.add(260);
                            }
                            supportedConfigs.add(219);
                            if (b.Fl()) {
                                supportedConfigs.add(229);
                            }
                            if (cameraCapabilities.isSupportHistogram()) {
                                supportedConfigs.add(261);
                            }
                        } else if (i != 183) {
                            if (i != 184) {
                                switch (i) {
                                    case 171:
                                        supportedConfigs.add(225);
                                        if (dataItemConfig.supportRatio()) {
                                            supportedConfigs.add(210);
                                        }
                                        supportedConfigs.add(226);
                                        if (i2 == 1 && dataItemFeature.c_0x35_OR_T()) {
                                            if (b.Nl() && b.hm()) {
                                                supportedConfigs.add(238);
                                            }
                                            if (b.Yl()) {
                                                supportedConfigs.add(236);
                                            }
                                            if (dataItemRunning.supportHandGesture()) {
                                                supportedConfigs.add(252);
                                            }
                                        }
                                        supportedConfigs.add(219);
                                        break;
                                    case 172:
                                        supportedConfigs.add(225);
                                        supportedConfigs.add(213);
                                        if (dataItemFeature.zd() && i2 == 0) {
                                            supportedConfigs.add(createConfigItem(255));
                                        }
                                        supportedConfigs.add(219);
                                        break;
                                    case 173:
                                    case 177:
                                        break;
                                    case 174:
                                        if (i2 == 0 && cameraCapabilities.isSupportVideoBeauty()) {
                                            supportedConfigs.add(225);
                                        }
                                        supportedConfigs.add(219);
                                        break;
                                    case 175:
                                        supportedConfigs.add(225).add(226);
                                        supportedConfigs.add(219);
                                        break;
                                    case 176:
                                        break;
                                    default:
                                        if (!z && cameraCapabilities.isSupportLightTripartite()) {
                                            supportedConfigs.add(225);
                                            if (dataItemConfig.supportRatio()) {
                                                supportedConfigs.add(210);
                                            }
                                            supportedConfigs.add(226);
                                            break;
                                        } else {
                                            supportedConfigs.add(225);
                                            if (dataItemConfig.supportRatio()) {
                                                supportedConfigs.add(210);
                                            }
                                            supportedConfigs.add(226);
                                            if (i2 == 0) {
                                                if (b.jm()) {
                                                    supportedConfigs.add(228);
                                                }
                                                supportedConfigs.add(219);
                                                if (b.Fl()) {
                                                    supportedConfigs.add(229);
                                                }
                                                if (b.Kl()) {
                                                    supportedConfigs.add(234);
                                                }
                                                if (i != 165 && z && b.Gl()) {
                                                    supportedConfigs.add(235);
                                                }
                                                if (z && dataItemRunning.supportUltraPixel()) {
                                                    supportedConfigs.add(209);
                                                }
                                                if (z && dataItemRunning.supportMacroMode() && dataItemFeature.zc()) {
                                                    supportedConfigs.add(255);
                                                }
                                                if (z) {
                                                    if (!dataItemFeature.rc()) {
                                                        if (!CameraSettings.checkLensAvailability(CameraAppImpl.getAndroidContext())) {
                                                            if (dataItemFeature.vd() && dataItemFeature.xc()) {
                                                                supportedConfigs.add(206);
                                                            }
                                                        }
                                                        if (z2) {
                                                            supportedConfigs.add(242);
                                                        }
                                                        if (z && dataItemFeature.c_35893_0x0002()) {
                                                            supportedConfigs.add(221);
                                                        }
                                                    } else if (dataItemFeature.vd()) {
                                                        supportedConfigs.add(206);
                                                    }
                                                }
                                                z2 = false;
                                                if (z2) {
                                                }
                                                supportedConfigs.add(221);
                                            } else if (i2 == 1) {
                                                if (z && b.Gl() && i != 165) {
                                                    supportedConfigs.add(235);
                                                }
                                                if (i == 165 || i == 163) {
                                                    if (b.Nl() && b.hm()) {
                                                        supportedConfigs.add(238);
                                                    }
                                                    if (b.Yl()) {
                                                        supportedConfigs.add(236);
                                                    }
                                                    if (z && dataItemRunning.supportUltraPixel()) {
                                                        supportedConfigs.add(209);
                                                    }
                                                    if (dataItemRunning.supportHandGesture()) {
                                                        supportedConfigs.add(252);
                                                    }
                                                    supportedConfigs.add(219);
                                                }
                                            }
                                            if (dataItemFeature.we()) {
                                                supportedConfigs.add(223);
                                                break;
                                            }
                                        }
                                        break;
                                }
                            }
                            supportedConfigs.add(225);
                            if (dataItemConfig.supportRatio()) {
                                supportedConfigs.add(210);
                            }
                            supportedConfigs.add(219);
                        } else {
                            supportedConfigs.add(225);
                            supportedConfigs.add(208);
                            supportedConfigs.add(219);
                        }
                    }
                }
                supportedConfigs.add(225);
                supportedConfigs.add(219);
            }
            supportedConfigs.add(225);
            supportedConfigs.add(208);
            if (i2 == 0) {
                supportedConfigs.add(233);
                if (cameraCapabilities.isSupportedVideoMiMovie()) {
                    supportedConfigs.add(251);
                }
                if (dataItemRunning.supportMacroMode() && dataItemFeature.zc()) {
                    supportedConfigs.add(255);
                }
                if (z && dataItemFeature.c_0x17() && dataItemFeature.c_22367_0x0008_OR_0_EQ_1()) {
                    supportedConfigs.add(253);
                }
                if (z && dataItemFeature.wd()) {
                    supportedConfigs.add(216);
                }
                if (z && dataItemFeature.c_35905_0x0001()) {
                    supportedConfigs.add(222);
                }
                supportedConfigs.add(219);
                if (b.Fl()) {
                    supportedConfigs.add(229);
                }
            } else if (i2 == 1) {
                if (dataItemFeature.nd()) {
                    supportedConfigs.add(233);
                }
                if (cameraCapabilities.isSupportedVideoMiMovie()) {
                    supportedConfigs.add(251);
                }
                supportedConfigs.add(219);
            }
            if (dataItemFeature.Pd() && z) {
                supportedConfigs.add(220);
            }
        } else {
            supportedConfigs.add(225);
            supportedConfigs.add(208);
            supportedConfigs.add(219);
        }
        return cloudFeature.filterFeature(supportedConfigs);
    }

    public static final SupportedConfigs getSupportedTopConfigs(int i, int i2, boolean z) {
        CameraCapabilities capabilities;
        CameraCapabilities capabilities2;
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        a dataItemFeature = DataRepository.dataItemFeature();
        dataItemRunning.reInitSupport(i, i2);
        CameraCapabilities capabilitiesByBogusCameraId = Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(i2, i);
        if (capabilitiesByBogusCameraId == null) {
            return null;
        }
        dataItemRunning.reInitComponent(i, i2, capabilitiesByBogusCameraId);
        dataItemConfig.reInitComponent(i, i2, capabilitiesByBogusCameraId);
        ArrayList arrayList = new ArrayList();
        if (dataItemConfig.supportFlash()) {
            arrayList.add(createConfigItem(193));
        }
        switch (i) {
            case 161:
                if (z && i2 != 0 && dataItemFeature.rf()) {
                    arrayList.add(createConfigItem(243));
                }
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem(), 17));
                }
                arrayList.add(createConfigItem(197));
                break;
            case 162:
            case 169:
                if (i2 != 0) {
                    if (z && i == 162 && dataItemFeature.rf()) {
                        arrayList.add(createConfigItem(243));
                    }
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem(), 17));
                    }
                    if (z) {
                        arrayList.add(createConfigItem(197));
                        break;
                    } else {
                        arrayList.add(createConfigItem(225));
                        break;
                    }
                } else {
                    if (z && dataItemFeature.c_22367_0x0007()) {
                        arrayList.add(createConfigItem(218));
                    }
                    if (z && (capabilities = Camera2DataContainer.getInstance().getCapabilities(0)) != null && ComponentConfigVideoQuality.is8K30FpsCamcorderSupported(0, capabilities)) {
                        arrayList.add(createConfigItem(256));
                    }
                    if (dataItemFeature.c_22367_0x0008_OR_0_EQ_0() && dataItemFeature.c_0x17() && z) {
                        arrayList.add(createConfigItem(253, 17));
                    }
                    if (z && dataItemFeature.xd()) {
                        arrayList.add(createConfigItem(216, 17));
                    }
                    if (i == 162 && dataItemRunning.supportMacroMode() && dataItemFeature.c_19039_0x0005_EQ_1()) {
                        arrayList.add(createConfigItem(255, 17));
                    }
                    if (z) {
                        if (dataItemConfig.supportHdr()) {
                            arrayList.add(createConfigItem(194));
                        }
                        if (dataItemRunning.supportTopShineEntry()) {
                            arrayList.add(createConfigItem(212, 17));
                        }
                        arrayList.add(createConfigItem(197));
                        break;
                    } else {
                        if (dataItemRunning.supportTopShineEntry()) {
                            arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem(), 17));
                        }
                        arrayList.add(createConfigItem(225));
                        break;
                    }
                }
            case 163:
            case 164:
            case 168:
            case 170:
            case 178:
            case 181:
            default:
                if (!z && capabilitiesByBogusCameraId.isSupportLightTripartite()) {
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                    }
                    arrayList.add(createConfigItem(197));
                    break;
                } else {
                    if (dataItemConfig.supportHdr()) {
                        arrayList.add(createConfigItem(194));
                    }
                    if (dataItemConfig.supportAi()) {
                        arrayList.add(createConfigItem(201));
                    }
                    if (dataItemConfig.supportBokeh()) {
                        arrayList.add(createConfigItem(200));
                    }
                    if (z) {
                        if (!dataItemFeature.rc() || i2 != 0) {
                            if ((!dataItemFeature.xc() || i2 == 1) && dataItemFeature.vd()) {
                                arrayList.add(createConfigItem(206));
                            }
                        } else if (CameraSettings.checkLensAvailability(CameraAppImpl.getAndroidContext())) {
                            arrayList.add(createConfigItem(242));
                        }
                    }
                    if (dataItemFeature.supportColorEnhance() && capabilitiesByBogusCameraId.isSupportedColorEnhance() && i2 == 0) {
                        arrayList.add(createConfigItem(227));
                    }
                    if (dataItemRunning.supportMacroMode() && dataItemFeature.c_19039_0x0005_EQ_1()) {
                        arrayList.add(createConfigItem(255));
                    }
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                    }
                    if (dataItemRunning.supportUltraPixelPortrait() && capabilitiesByBogusCameraId.isSupportedSuperPortrait()) {
                        arrayList.add(createConfigItem(215));
                    }
                    arrayList.add(createConfigItem(197));
                    break;
                }
                break;
            case 165:
                if (!z && capabilitiesByBogusCameraId.isSupportLightTripartite()) {
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                    }
                    arrayList.add(createConfigItem(197));
                    break;
                } else {
                    if (dataItemConfig.supportHdr()) {
                        arrayList.add(createConfigItem(194));
                    }
                    if (dataItemConfig.supportAi()) {
                        arrayList.add(createConfigItem(201));
                    }
                    if (dataItemConfig.supportBokeh()) {
                        arrayList.add(createConfigItem(200));
                    }
                    if (dataItemFeature.supportColorEnhance() && capabilitiesByBogusCameraId.isSupportedColorEnhance() && i2 == 0) {
                        arrayList.add(createConfigItem(227));
                    }
                    if (dataItemRunning.supportMacroMode() && dataItemFeature.c_19039_0x0005_EQ_1()) {
                        arrayList.add(createConfigItem(255));
                    }
                    if (z && dataItemFeature.vd() && !dataItemFeature.xc()) {
                        arrayList.add(createConfigItem(206));
                    }
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                    }
                    if (dataItemRunning.supportUltraPixelPortrait() && capabilitiesByBogusCameraId.isSupportedSuperPortrait()) {
                        arrayList.add(createConfigItem(215));
                    }
                    arrayList.add(createConfigItem(197));
                    break;
                }
                break;
            case 166:
            case 176:
                arrayList.clear();
                arrayList.add(createConfigItem(197));
                break;
            case 167:
                arrayList.add(createConfigItem(214));
                if (z && dataItemRunning.supportUltraPixel()) {
                    arrayList.add(createConfigItem(209));
                }
                arrayList.add(createConfigItem(257));
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                }
                arrayList.add(createConfigItem(197));
                break;
            case 171:
                if (dataItemFeature.isSupportUltraWide() && CameraSettings.isSupportedOpticalZoom() && i2 == 0 && z && dataItemFeature.c_22367_0x0002_OR_T()) {
                    arrayList.add(createConfigItem(207));
                }
                if (dataItemConfig.supportAi()) {
                    arrayList.add(createConfigItem(201, 17));
                }
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                }
                if (dataItemFeature.isCinematicPhotoSupported()) {
                    arrayList.add(createConfigItem(251));
                }
                arrayList.add(createConfigItem(197));
                break;
            case 172:
                if (dataItemConfig.getComponentConfigSlowMotion().getSupportedFpsOptions().length > 1) {
                    arrayList.add(createConfigItem(204, 17));
                }
                arrayList.add(createConfigItem(197));
                break;
            case 173:
                arrayList.add(createConfigItem(197));
                break;
            case 174:
                if (z && i2 != 0 && dataItemFeature.rf()) {
                    arrayList.add(createConfigItem(243));
                }
                arrayList.add(createConfigItem(245, 17));
                arrayList.add(createConfigItem(225));
                break;
            case 175:
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                }
                arrayList.add(createConfigItem(197));
                break;
            case 177:
                arrayList.add(createConfigItem(197));
                break;
            case 179:
            case 182:
                arrayList.add(createConfigItem(217));
                break;
            case 180:
                arrayList.add(createConfigItem(214));
                if (z && i2 == 0 && (capabilities2 = Camera2DataContainer.getInstance().getCapabilities(0)) != null && ComponentConfigVideoQuality.is8K30FpsCamcorderSupported(0, capabilities2)) {
                    arrayList.add(createConfigItem(256));
                }
                ComponentManuallyFocus manuallyFocus = dataItemConfig.getManuallyFocus();
                if (b.dm() && !manuallyFocus.getComponentValue(i).equals(manuallyFocus.getDefaultValue(i))) {
                    capabilitiesByBogusCameraId.isAFRegionSupported();
                }
                arrayList.add(createConfigItem(257));
                if (z && dataItemRunning.supportUltraPixel()) {
                    arrayList.add(createConfigItem(209));
                }
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                }
                arrayList.add(createConfigItem(197));
                break;
            case 183:
                arrayList.add(createConfigItem(197));
                break;
            case 184:
                arrayList.add(createConfigItem(162));
                arrayList.add(createConfigItem(197));
                break;
        }
        return TopViewPositionArray.fillNotUseViewPosition(arrayList);
    }

    public static boolean isMutexConfig(int i) {
        for (int i2 : MUTEX_MENU_CONFIGS) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
