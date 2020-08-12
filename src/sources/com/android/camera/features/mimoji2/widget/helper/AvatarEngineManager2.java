package com.android.camera.features.mimoji2.widget.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Size;
import com.android.camera.R;
import com.android.camera.features.mimoji2.bean.MimojiLevelBean2;
import com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import com.arcsoft.avatar.BackgroundInfo;
import com.arcsoft.avatar.emoticon.AvatarEmoManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AvatarEngineManager2 {
    public static final String BearTemplatePath = (MimojiHelper2.MODEL_PATH + "bear_v_0_0_0_5");
    public static final String BgTemplatePath = (MimojiHelper2.MODEL_PATH + "background_v_0_0_0_4");
    public static final int CONFIGTYPE_EARRING = 16;
    public static final int CONFIGTYPE_EAR_SHAPE = 24;
    public static final int CONFIGTYPE_EYEBROW_COLOR = 19;
    public static final int CONFIGTYPE_EYEBROW_SHAPE = 25;
    public static final int CONFIGTYPE_EYEGLASS = 9;
    public static final int CONFIGTYPE_EYEGLASS_COLOR = 10;
    public static final int CONFIGTYPE_EYELASH = 18;
    public static final int CONFIGTYPE_EYE_COLOR = 4;
    public static final int CONFIGTYPE_EYE_SHAPE = 21;
    public static final int CONFIGTYPE_FACE_COLOR = 3;
    public static final int CONFIGTYPE_FEATURED_FACE = 20;
    public static final int CONFIGTYPE_FRECKLE = 7;
    public static final int CONFIGTYPE_HAIR_COLOR = 2;
    public static final int CONFIGTYPE_HAIR_STYLE = 1;
    public static final int CONFIGTYPE_HEADWEAR = 12;
    public static final int CONFIGTYPE_HEADWEAR_COLOR = 13;
    public static final int CONFIGTYPE_LENS_COLOR = 11;
    public static final int CONFIGTYPE_LIPS_COLOR = 5;
    public static final int CONFIGTYPE_MOUSE_SHAPE = 22;
    public static final int CONFIGTYPE_MUSTACHE = 14;
    public static final int CONFIGTYPE_MUSTACHE_COLOR = 15;
    public static final int CONFIGTYPE_NEVUS = 8;
    public static final int CONFIGTYPE_NOSE_SHAPE = 23;
    public static final Size CONFIG_EMO_THUM_SIZE = new Size(240, 240);
    public static final String FACE_MODEL = (MimojiHelper2.DATA_DIR + "config.txt");
    public static final String FAKE_BEAR_CONFIGPATH = "bear";
    public static final String FAKE_PIG_CONFIGPATH = "pig";
    public static final String FAKE_RABBIT_CONFIGPATH = "rabbit";
    public static final String FAKE_ROYAN_CONFIGPATH = "royan";
    public static final String GifTemplatePath = (MimojiHelper2.MODEL_PATH + "gif_v_0_0_0_7");
    public static final String PersonTemplatePath = (MimojiHelper2.MODEL_PATH + "cartoon_xiaomi_v_0_0_0_49");
    public static final String PigTemplatePath = (MimojiHelper2.MODEL_PATH + "pig_v_0_0_0_3");
    public static final String RabbitTemplatePath = (MimojiHelper2.MODEL_PATH + "rabbit_v_0_0_0_4");
    public static final String RoyanTemplatePath = (MimojiHelper2.MODEL_PATH + "royan_v_0_0_0_7");
    public static final int THUMB_HEIGHT = 500;
    public static final int THUMB_WIDTH = 500;
    public static final String TRACK_DATA = (MimojiHelper2.DATA_DIR + "track_data.dat");
    public static final String TempEditConfigPath = (MimojiHelper2.DATA_DIR + "edit_config.dat");
    public static final String TempOriginalConfigPath = (MimojiHelper2.DATA_DIR + "origin_config.dat");
    private static AvatarEngineManager2 mInstance;
    private AvatarConfig.ASAvatarConfigValue mASAvatarConfigValue;
    private AvatarConfig.ASAvatarConfigValue mASAvatarConfigValueDefault;
    private boolean mAllNeedUpdate = false;
    private AvatarEngine mAvatar;
    private int mAvatarRef = 0;
    private CopyOnWriteArrayList<BackgroundInfo> mBackgroundInfos = new CopyOnWriteArrayList<>();
    private Map<Integer, LinearLayoutManagerWrapper> mColorLayoutManagerMap = new ConcurrentHashMap();
    private Map<Integer, ArrayList<AvatarConfig.ASAvatarConfigInfo>> mConfigMap = new ConcurrentHashMap();
    private AvatarEmoManager mEmoManager;
    private Map<Integer, Float> mInnerConfigSelectMap = new ConcurrentHashMap();
    private Map<Integer, Integer> mInterruptMap = new ConcurrentHashMap();
    private boolean mIsColorSelected = false;
    private Map<Integer, Boolean> mNeedUpdateMap = new ConcurrentHashMap();
    private int mSelectTabIndex = 0;
    private int mSelectType = 0;
    private CopyOnWriteArrayList<MimojiLevelBean2> mSubConfigs = new CopyOnWriteArrayList<>();
    private ArrayList<AvatarConfig.ASAvatarConfigType> mTypeList = new ArrayList<>();

    public static boolean filterTypeTitle(int i) {
        if (i == 1 || i == 12 || i == 14 || i == 25 || i == 8 || i == 9) {
            return false;
        }
        switch (i) {
            case 20:
            case 21:
            case 22:
                return false;
            default:
                return true;
        }
    }

    public static synchronized AvatarEngineManager2 getInstance() {
        AvatarEngineManager2 avatarEngineManager2;
        synchronized (AvatarEngineManager2.class) {
            if (mInstance == null) {
                mInstance = new AvatarEngineManager2();
            }
            avatarEngineManager2 = mInstance;
        }
        return avatarEngineManager2;
    }

    public static Map<String, String> getMimojiConfigValue(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_HARISTYLE, String.valueOf(aSAvatarConfigValue.configHairStyleID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_FEATURE_FACE, String.valueOf(aSAvatarConfigValue.configFaceShapeID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_EYE_SHAPE, String.valueOf(aSAvatarConfigValue.configEyeShapeID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_MOUTH_SHAPE, String.valueOf(aSAvatarConfigValue.configMouthShapeID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_MUSTACHE, String.valueOf(aSAvatarConfigValue.configBeardStyleID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_FRECKLE, String.valueOf(aSAvatarConfigValue.configFrecklesID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_EYEGLASS, String.valueOf(aSAvatarConfigValue.configEyewearStyleID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_HEADWEAR, String.valueOf(aSAvatarConfigValue.configHeadwearStyleID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_EAR, String.valueOf(aSAvatarConfigValue.configEarShapeID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_EYELASH, String.valueOf(aSAvatarConfigValue.configEyelashStyleID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_EYEBROW_SHAPE, String.valueOf(aSAvatarConfigValue.configEyebrowShapeID));
        hashMap.put("attr_nose", String.valueOf(aSAvatarConfigValue.configNoseShapeID));
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_CONFIG_EARING, String.valueOf(aSAvatarConfigValue.configEarringStyleID));
        return hashMap;
    }

    public static boolean isPrefabModel(String str) {
        return str.equals("pig") || str.equals("bear") || str.equals("royan") || str.equals("rabbit");
    }

    public static String replaceTabTitle(Context context, int i) {
        Resources resources = context.getResources();
        if (i == 1) {
            return resources.getString(R.string.mimoji_hairstyle);
        }
        if (i == 12) {
            return resources.getString(R.string.mimoji_ornament);
        }
        if (i == 14) {
            return resources.getString(R.string.mimoji_mustache);
        }
        if (i == 25) {
            return resources.getString(R.string.mimoji_eyebrow);
        }
        if (i == 8) {
            return resources.getString(R.string.mimoji_freckle);
        }
        if (i == 9) {
            return resources.getString(R.string.mimoji_eyeglass);
        }
        switch (i) {
            case 20:
                return resources.getString(R.string.mimoji_featured_face);
            case 21:
                return resources.getString(R.string.mimoji_eye);
            case 22:
                return resources.getString(R.string.mimoji_nose_lisps);
            default:
                return "";
        }
    }

    public static boolean showConfigTypeName(int i) {
        return (i == 1 || i == 7 || i == 9 || i == 12 || i == 14 || i == 23 || i == 25 || i == 20 || i == 21) ? false : true;
    }

    public synchronized AvatarEngine addAvatarConfig(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (aSAvatarConfigInfo == null) {
            Log.d("AvatarEngineManager2", "AvatarConfig.ASAvatarConfigInfo is null");
            return null;
        }
        if (this.mAvatar == null) {
            Log.d("AvatarEngineManager2", "avatar create");
            this.mAvatar = new AvatarEngine();
            this.mAvatar.setRenderScene(false, 0.85f);
        }
        this.mAvatar.setConfig(aSAvatarConfigInfo);
        return this.mAvatar;
    }

    public void clear() {
        this.mSelectType = 0;
        this.mSelectTabIndex = 0;
        this.mSubConfigs.clear();
        this.mColorLayoutManagerMap.clear();
    }

    public AvatarConfig.ASAvatarConfigValue getASAvatarConfigValue() {
        return this.mASAvatarConfigValue;
    }

    public synchronized AvatarEngine getAvatar() {
        return this.mAvatar;
    }

    public CopyOnWriteArrayList<BackgroundInfo> getBackgroundInfos() {
        return this.mBackgroundInfos;
    }

    public LinearLayoutManagerWrapper getColorLayoutManagerMap(int i) {
        return this.mColorLayoutManagerMap.get(Integer.valueOf(i));
    }

    public int getColorType(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 12) {
            return 13;
        }
        if (i == 14) {
            return 15;
        }
        if (i == 20) {
            return 3;
        }
        if (i != 22) {
            return i != 25 ? -1 : 19;
        }
        return 5;
    }

    public synchronized ArrayList<AvatarConfig.ASAvatarConfigInfo> getConfigList(int i) {
        return this.mConfigMap.size() <= 0 ? null : this.mConfigMap.get(Integer.valueOf(i));
    }

    public AvatarConfig.ASAvatarConfigType getConfigTypeForIndex(int i) {
        ArrayList<AvatarConfig.ASAvatarConfigType> arrayList = this.mTypeList;
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        return this.mTypeList.get(i);
    }

    public ArrayList<AvatarConfig.ASAvatarConfigType> getConfigTypeList() {
        return this.mTypeList;
    }

    public synchronized AvatarEmoManager getEmoManager() {
        return this.mEmoManager;
    }

    public float getInnerConfigSelectIndex(int i) {
        if (this.mInnerConfigSelectMap.get(Integer.valueOf(i)) == null) {
            return -1.0f;
        }
        return this.mInnerConfigSelectMap.get(Integer.valueOf(i)).floatValue();
    }

    public int getInterruptIndex(int i) {
        Integer num = this.mInterruptMap.get(Integer.valueOf(i));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public ArrayList<AvatarConfig.ASAvatarConfigInfo> getSelectConfigList() {
        if (this.mConfigMap.size() <= 0) {
            return null;
        }
        return this.mConfigMap.get(Integer.valueOf(this.mSelectType));
    }

    public int getSelectType() {
        return this.mSelectType;
    }

    public int getSelectTypeIndex() {
        return this.mSelectTabIndex;
    }

    public ArrayList<AvatarConfig.ASAvatarConfigInfo> getSubConfigColorList(int i) {
        if (i == 1) {
            return getConfigList(2);
        }
        if (i == 12) {
            return getConfigList(13);
        }
        if (i == 14) {
            return getConfigList(15);
        }
        if (i == 25) {
            return getConfigList(19);
        }
        switch (i) {
            case 20:
                return getConfigList(3);
            case 21:
                return getConfigList(4);
            case 22:
                return getConfigList(5);
            default:
                return null;
        }
    }

    public CopyOnWriteArrayList<MimojiLevelBean2> getSubConfigList(Context context) {
        return getSubConfigList(context, this.mSelectType);
    }

    public CopyOnWriteArrayList<MimojiLevelBean2> getSubConfigList(Context context, int i) {
        this.mSubConfigs.clear();
        Resources resources = context.getResources();
        if (i == 1) {
            MimojiLevelBean2 mimojiLevelBean2 = new MimojiLevelBean2();
            mimojiLevelBean2.mThumnails = getConfigList(1);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = mimojiLevelBean2.mThumnails;
            if (arrayList != null && arrayList.size() > 0) {
                mimojiLevelBean2.mConfigType = 1;
                mimojiLevelBean2.mConfigTypeName = resources.getString(R.string.mimoji_hairstyle);
                this.mSubConfigs.add(mimojiLevelBean2);
            }
        } else if (i == 12) {
            MimojiLevelBean2 mimojiLevelBean22 = new MimojiLevelBean2();
            mimojiLevelBean22.mThumnails = getConfigList(12);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList2 = mimojiLevelBean22.mThumnails;
            if (arrayList2 != null && arrayList2.size() > 0) {
                mimojiLevelBean22.mConfigType = 12;
                mimojiLevelBean22.mConfigTypeName = resources.getString(R.string.mimoji_headwear);
                this.mSubConfigs.add(mimojiLevelBean22);
            }
            MimojiLevelBean2 mimojiLevelBean23 = new MimojiLevelBean2();
            mimojiLevelBean23.mThumnails = getConfigList(16);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList3 = mimojiLevelBean23.mThumnails;
            if (arrayList3 != null && arrayList3.size() > 0) {
                mimojiLevelBean23.mConfigType = 16;
                mimojiLevelBean23.mConfigTypeName = resources.getString(R.string.mimoji_earring);
                this.mSubConfigs.add(mimojiLevelBean23);
            }
        } else if (i == 14) {
            MimojiLevelBean2 mimojiLevelBean24 = new MimojiLevelBean2();
            mimojiLevelBean24.mThumnails = getConfigList(14);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList4 = mimojiLevelBean24.mThumnails;
            if (arrayList4 != null && arrayList4.size() > 0) {
                mimojiLevelBean24.mConfigType = 14;
                mimojiLevelBean24.mConfigTypeName = resources.getString(R.string.mimoji_mustache);
                this.mSubConfigs.add(mimojiLevelBean24);
            }
        } else if (i == 25) {
            MimojiLevelBean2 mimojiLevelBean25 = new MimojiLevelBean2();
            mimojiLevelBean25.mThumnails = getConfigList(25);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList5 = mimojiLevelBean25.mThumnails;
            if (arrayList5 != null && arrayList5.size() > 0) {
                mimojiLevelBean25.mConfigType = 25;
                mimojiLevelBean25.mConfigTypeName = resources.getString(R.string.mimoji_eyebrow_shape);
                this.mSubConfigs.add(mimojiLevelBean25);
            }
        } else if (i == 8) {
            MimojiLevelBean2 mimojiLevelBean26 = new MimojiLevelBean2();
            mimojiLevelBean26.mThumnails = getConfigList(7);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList6 = mimojiLevelBean26.mThumnails;
            if (arrayList6 != null && arrayList6.size() > 0) {
                mimojiLevelBean26.mConfigType = 7;
                mimojiLevelBean26.mConfigTypeName = resources.getString(R.string.mimoji_freckle);
                this.mSubConfigs.add(mimojiLevelBean26);
            }
            MimojiLevelBean2 mimojiLevelBean27 = new MimojiLevelBean2();
            mimojiLevelBean27.mThumnails = getConfigList(8);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList7 = mimojiLevelBean27.mThumnails;
            if (arrayList7 != null && arrayList7.size() > 0) {
                mimojiLevelBean27.mConfigType = 8;
                mimojiLevelBean27.mConfigTypeName = resources.getString(R.string.mimoji_mole);
                this.mSubConfigs.add(mimojiLevelBean27);
            }
        } else if (i != 9) {
            switch (i) {
                case 20:
                    MimojiLevelBean2 mimojiLevelBean28 = new MimojiLevelBean2();
                    mimojiLevelBean28.mThumnails.addAll(getConfigList(20));
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList8 = mimojiLevelBean28.mThumnails;
                    if (arrayList8 != null && arrayList8.size() > 0) {
                        mimojiLevelBean28.mConfigType = 20;
                        mimojiLevelBean28.mConfigTypeName = resources.getString(R.string.mimoji_featured_face);
                        this.mSubConfigs.add(mimojiLevelBean28);
                    }
                    MimojiLevelBean2 mimojiLevelBean29 = new MimojiLevelBean2();
                    mimojiLevelBean29.mThumnails = getConfigList(24);
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList9 = mimojiLevelBean29.mThumnails;
                    if (arrayList9 != null && arrayList9.size() > 0) {
                        mimojiLevelBean29.mConfigType = 24;
                        mimojiLevelBean29.mConfigTypeName = resources.getString(R.string.mimoji_ear);
                        this.mSubConfigs.add(mimojiLevelBean29);
                        break;
                    }
                case 21:
                    MimojiLevelBean2 mimojiLevelBean210 = new MimojiLevelBean2();
                    mimojiLevelBean210.mThumnails = getConfigList(21);
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList10 = mimojiLevelBean210.mThumnails;
                    if (arrayList10 != null && arrayList10.size() > 0) {
                        mimojiLevelBean210.mConfigType = 21;
                        mimojiLevelBean210.mConfigTypeName = resources.getString(R.string.mimoji_eye_shape);
                        this.mSubConfigs.add(mimojiLevelBean210);
                    }
                    MimojiLevelBean2 mimojiLevelBean211 = new MimojiLevelBean2();
                    mimojiLevelBean211.mThumnails = getConfigList(18);
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList11 = mimojiLevelBean211.mThumnails;
                    if (arrayList11 != null && arrayList11.size() > 0) {
                        mimojiLevelBean211.mConfigType = 18;
                        mimojiLevelBean211.mConfigTypeName = resources.getString(R.string.mimoji_eyelash);
                        this.mSubConfigs.add(mimojiLevelBean211);
                        break;
                    }
                case 22:
                    MimojiLevelBean2 mimojiLevelBean212 = new MimojiLevelBean2();
                    mimojiLevelBean212.mThumnails = getConfigList(23);
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList12 = mimojiLevelBean212.mThumnails;
                    if (arrayList12 != null && arrayList12.size() > 0) {
                        mimojiLevelBean212.mConfigType = 23;
                        mimojiLevelBean212.mConfigTypeName = resources.getString(R.string.mimoji_nose);
                        this.mSubConfigs.add(mimojiLevelBean212);
                    }
                    MimojiLevelBean2 mimojiLevelBean213 = new MimojiLevelBean2();
                    mimojiLevelBean213.mThumnails = getConfigList(22);
                    ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList13 = mimojiLevelBean213.mThumnails;
                    if (arrayList13 != null && arrayList13.size() > 0) {
                        mimojiLevelBean213.mConfigType = 22;
                        mimojiLevelBean213.mConfigTypeName = resources.getString(R.string.mimoji_mouth_type);
                        this.mSubConfigs.add(mimojiLevelBean213);
                        break;
                    }
            }
        } else {
            MimojiLevelBean2 mimojiLevelBean214 = new MimojiLevelBean2();
            mimojiLevelBean214.mThumnails = getConfigList(9);
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList14 = mimojiLevelBean214.mThumnails;
            if (arrayList14 != null && arrayList14.size() > 0) {
                mimojiLevelBean214.mConfigType = 9;
                mimojiLevelBean214.mConfigTypeName = resources.getString(R.string.mimoji_eyeglass);
                this.mSubConfigs.add(mimojiLevelBean214);
            }
        }
        return this.mSubConfigs;
    }

    public void initUpdatePara() {
        this.mInterruptMap.clear();
        this.mNeedUpdateMap.clear();
        this.mAllNeedUpdate = true;
    }

    public boolean isColorSelected() {
        return this.mIsColorSelected;
    }

    public boolean isNeedUpdate(int i) {
        Boolean bool = this.mNeedUpdateMap.get(Integer.valueOf(i));
        if (bool != null) {
            return bool.booleanValue() || this.mAllNeedUpdate;
        }
        this.mNeedUpdateMap.put(Integer.valueOf(i), false);
        return true;
    }

    public void putColorLayoutManagerMap(int i, LinearLayoutManagerWrapper linearLayoutManagerWrapper) {
        this.mColorLayoutManagerMap.put(Integer.valueOf(i), linearLayoutManagerWrapper);
    }

    public void putConfigList(int i, ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList) {
        if (!this.mConfigMap.containsKey(Integer.valueOf(i))) {
            this.mConfigMap.put(Integer.valueOf(i), arrayList);
        }
    }

    public synchronized AvatarEngine queryAvatar() {
        if (this.mAvatar == null) {
            Log.d("AvatarEngineManager2", "avatar create");
            this.mAvatar = new AvatarEngine();
        }
        this.mAvatarRef++;
        return this.mAvatar;
    }

    public synchronized void release() {
        Log.d("AvatarEngineManager2", "avatar destroy");
        if (this.mAvatar != null) {
            this.mAvatar.destroy();
        }
        if (this.mEmoManager != null) {
            this.mEmoManager.release();
        }
        this.mEmoManager = null;
        this.mAvatar = null;
    }

    public void resetData() {
        this.mInnerConfigSelectMap.clear();
        this.mASAvatarConfigValue = (AvatarConfig.ASAvatarConfigValue) this.mASAvatarConfigValueDefault.clone();
        setASAvatarConfigValue(this.mASAvatarConfigValue);
        initUpdatePara();
    }

    public void setASAvatarConfigValue(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        this.mASAvatarConfigValue = aSAvatarConfigValue;
        if (aSAvatarConfigValue != null) {
            String str = FragmentMimojiEdit2.TAG;
            Log.i(str, "value 属性:gender = " + aSAvatarConfigValue.gender + " configHairStyleID = " + aSAvatarConfigValue.configHairStyleID + " configHairColorID = " + aSAvatarConfigValue.configHairColorID + " configHairColorValue = " + aSAvatarConfigValue.configHairColorValue + " configFaceColorID = " + aSAvatarConfigValue.configFaceColorID + " configFaceColorValue = " + aSAvatarConfigValue.configFaceColorValue + " configEyeColorID = " + aSAvatarConfigValue.configEyeColorID + " configEyeColorValue = " + aSAvatarConfigValue.configEyeColorValue + " configLipColorID = " + aSAvatarConfigValue.configLipColorID + " configLipColorValue = " + aSAvatarConfigValue.configLipColorValue + " configHairHighlightColorID = " + aSAvatarConfigValue.configHairHighlightColorID + " configHairHighlightColorValue = " + aSAvatarConfigValue.configHairHighlightColorValue + " configFrecklesID = " + aSAvatarConfigValue.configFrecklesID + " configNevusID = " + aSAvatarConfigValue.configNevusID + " configEyewearStyleID = " + aSAvatarConfigValue.configEyewearStyleID + " configEyewearFrameID = " + aSAvatarConfigValue.configEyewearFrameID + " configEyewearFrameValue = " + aSAvatarConfigValue.configEyewearFrameValue + " configEyewearLensesID = " + aSAvatarConfigValue.configEyewearLensesID + " configEyewearLensesValue = " + aSAvatarConfigValue.configEyewearLensesValue + " configHeadwearStyleID = " + aSAvatarConfigValue.configHeadwearStyleID + " configHeadwearColorID = " + aSAvatarConfigValue.configHeadwearColorID + " configHeadwearColorValue = " + aSAvatarConfigValue.configHeadwearColorValue + " configBeardStyleID = " + aSAvatarConfigValue.configBeardStyleID + " configBeardColorID = " + aSAvatarConfigValue.configBeardColorID + " configBeardColorValue = " + aSAvatarConfigValue.configBeardColorValue + " configEarringStyleID = " + aSAvatarConfigValue.configEarringStyleID + " configEyelashStyleID = " + aSAvatarConfigValue.configEyelashStyleID + " configEyebrowColorID = " + aSAvatarConfigValue.configEyebrowColorID + " configEyebrowColorValue = " + aSAvatarConfigValue.configEyebrowColorValue + " configFaceShapeID = " + aSAvatarConfigValue.configFaceShapeID + " configFaceShapeValue = " + aSAvatarConfigValue.configFaceShapeValue + " configEyeShapeID = " + aSAvatarConfigValue.configEyeShapeID + " configEyeShapeValue = " + aSAvatarConfigValue.configEyeShapeValue + " configMouthShapeID = " + aSAvatarConfigValue.configMouthShapeID + " configMouthShapeValue = " + aSAvatarConfigValue.configMouthShapeValue + " configNoseShapeID = " + aSAvatarConfigValue.configNoseShapeID + " configNoseShapeValue = " + aSAvatarConfigValue.configNoseShapeValue + " configEarShapeID = " + aSAvatarConfigValue.configEarShapeID + " configEarShapeValue = " + aSAvatarConfigValue.configEarShapeValue + " configEyebrowShapeID = " + aSAvatarConfigValue.configEyebrowShapeID + " configEyebrowShapeValue = " + aSAvatarConfigValue.configEyebrowShapeValue);
            this.mInnerConfigSelectMap.put(1, Float.valueOf((float) aSAvatarConfigValue.configHairStyleID));
            this.mInnerConfigSelectMap.put(2, Float.valueOf((float) aSAvatarConfigValue.configHairColorID));
            this.mInnerConfigSelectMap.put(3, Float.valueOf((float) aSAvatarConfigValue.configFaceColorID));
            this.mInnerConfigSelectMap.put(20, Float.valueOf((float) aSAvatarConfigValue.configFaceShapeID));
            this.mInnerConfigSelectMap.put(4, Float.valueOf(aSAvatarConfigValue.configEyeColorValue));
            this.mInnerConfigSelectMap.put(5, Float.valueOf((float) aSAvatarConfigValue.configLipColorID));
            this.mInnerConfigSelectMap.put(7, Float.valueOf((float) aSAvatarConfigValue.configFrecklesID));
            this.mInnerConfigSelectMap.put(8, Float.valueOf((float) aSAvatarConfigValue.configNevusID));
            this.mInnerConfigSelectMap.put(9, Float.valueOf((float) aSAvatarConfigValue.configEyewearStyleID));
            this.mInnerConfigSelectMap.put(14, Float.valueOf((float) aSAvatarConfigValue.configBeardStyleID));
            this.mInnerConfigSelectMap.put(15, Float.valueOf((float) aSAvatarConfigValue.configBeardColorID));
            this.mInnerConfigSelectMap.put(18, Float.valueOf((float) aSAvatarConfigValue.configEyelashStyleID));
            this.mInnerConfigSelectMap.put(19, Float.valueOf((float) aSAvatarConfigValue.configEyebrowColorID));
            this.mInnerConfigSelectMap.put(21, Float.valueOf((float) aSAvatarConfigValue.configEyeShapeID));
            this.mInnerConfigSelectMap.put(22, Float.valueOf((float) aSAvatarConfigValue.configMouthShapeID));
            this.mInnerConfigSelectMap.put(23, Float.valueOf((float) aSAvatarConfigValue.configNoseShapeID));
            this.mInnerConfigSelectMap.put(24, Float.valueOf((float) aSAvatarConfigValue.configEarShapeID));
            this.mInnerConfigSelectMap.put(25, Float.valueOf((float) aSAvatarConfigValue.configEyebrowShapeID));
            this.mInnerConfigSelectMap.put(13, Float.valueOf((float) aSAvatarConfigValue.configHeadwearColorID));
            this.mInnerConfigSelectMap.put(12, Float.valueOf((float) aSAvatarConfigValue.configHeadwearStyleID));
        }
    }

    public void setASAvatarConfigValueDefault(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        this.mASAvatarConfigValueDefault = (AvatarConfig.ASAvatarConfigValue) aSAvatarConfigValue.clone();
    }

    public void setAllNeedUpdate(boolean z, boolean z2) {
        this.mAllNeedUpdate = z;
        this.mIsColorSelected = z2;
        this.mInterruptMap.clear();
    }

    public void setBackgroundInfos(ArrayList<BackgroundInfo> arrayList) {
        this.mBackgroundInfos.clear();
        this.mBackgroundInfos.addAll(arrayList);
    }

    public void setConfigTypeList(ArrayList<AvatarConfig.ASAvatarConfigType> arrayList) {
        this.mTypeList = arrayList;
    }

    public synchronized void setEmoManager(AvatarEmoManager avatarEmoManager) {
        this.mEmoManager = avatarEmoManager;
    }

    public void setInnerConfigSelectIndex(int i, float f2) {
        this.mInnerConfigSelectMap.put(Integer.valueOf(i), Float.valueOf(f2));
    }

    public void setInterruptIndex(int i, int i2) {
        this.mInterruptMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void setIsColorSelected(boolean z) {
        this.mIsColorSelected = z;
    }

    public void setSelectType(int i) {
        this.mSelectType = i;
    }

    public void setSelectTypeIndex(int i) {
        this.mSelectTabIndex = i;
    }

    public void setTypeNeedUpdate(int i, boolean z) {
        this.mNeedUpdateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    }
}
