package com.arcsoft.avatar.extrascene;

import android.text.TextUtils;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import com.arcsoft.avatar.BackgroundInfo;
import com.arcsoft.avatar.extrascene.ExtraSceneInfo;
import com.arcsoft.avatar.util.LOG;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ExtraSceneEngine {
    private static final String A = "EmojiHelmet";
    private static final String B = "AngelWings";
    private static final String C = "ClownHat";
    private static final String D = "MagicHat";
    private static final String E = "AstronautHat";
    private static final String F = "DogHat";
    private static final String G = "hairId";
    private static final String H = "expId";
    private static final String I = "blendshape";
    private static final String J = "mask";
    private static final int K = 2;
    private static final int L = 1;
    private static final int M = 2;
    private static final int N = 3;

    /* renamed from: a  reason: collision with root package name */
    private static final String f73a = "ExtraSceneEngine";

    /* renamed from: b  reason: collision with root package name */
    private static final String f74b = "extraScene.xml";

    /* renamed from: c  reason: collision with root package name */
    private static final String f75c = "info";

    /* renamed from: d  reason: collision with root package name */
    private static final String f76d = "name";

    /* renamed from: e  reason: collision with root package name */
    private static final String f77e = "headwear";

    /* renamed from: f  reason: collision with root package name */
    private static final String f78f = "headwearcolor";
    private static final String g = "expid";
    private static final String h = "expvalue";
    private static final String i = "outline";
    private static final String j = "oldheadwear";
    private static final String k = "newheadwear";
    private static final String l = "expcheckid";
    private static final String m = "expcheckvalue";
    private static final String n = "oldtooth";
    private static final String o = "newtooth";
    private static final String p = "loopstyle";
    private static final String q = "render";
    private static final String r = "loop";
    private static final String s = "random";
    private static final String t = "pause";
    private static final String u = "expidin";
    private static final String v = "expidout";
    private static final String w = "RabbitTeeth";
    private static final String x = "RabbitEars";
    private static final String y = "ChickHat";
    private static final String z = "WizardHat";
    private String O;
    private AvatarEngine P;
    private AvatarConfig.ASAvatarConfigValue Q;
    private ExtraSceneInfo R = null;
    private ArrayList<ExtraSceneInfo> S = null;
    private String T;
    private int U = 0;
    private int V = 0;
    private int W = 0;
    private int X = 0;
    private int Y = 0;
    private int Z = 0;
    private int aa = 0;
    private Random ab = null;
    private List<ExtraSceneInfo.HairMaskInfo> ac = null;

    public ExtraSceneEngine(String str) {
        this.T = str;
        this.Q = new AvatarConfig.ASAvatarConfigValue();
        this.S = new ArrayList<>();
        this.O = str + "/" + f74b;
        a();
        this.ac = new ArrayList();
    }

    private void a() {
        String str;
        try {
            LOG.d(f73a, "getXmlInfo = " + this.O);
            FileInputStream fileInputStream = new FileInputStream(this.O);
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(fileInputStream, "UTF-8");
            String str2 = null;
            ExtraSceneInfo extraSceneInfo = null;
            ExtraSceneInfo.ExpressInfo expressInfo = null;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        str = newPullParser.getName();
                        if (str.equals(f75c)) {
                            extraSceneInfo = new ExtraSceneInfo();
                        }
                    } else if (eventType == 3) {
                        if (newPullParser.getName().equals(BackgroundInfo.getXMLInfoTag())) {
                            this.S.add(extraSceneInfo);
                        }
                        str = "";
                    } else if (eventType == 4) {
                        if (str2.equals(f76d)) {
                            extraSceneInfo.setName(newPullParser.getText());
                        } else if (str2.equals(f77e)) {
                            extraSceneInfo.setHeadWear(Integer.parseInt(newPullParser.getText()));
                        } else if (str2.equals(i)) {
                            extraSceneInfo.setNeedOutLine(newPullParser.getText().equals("1"));
                        } else if (str2.equals(g)) {
                            extraSceneInfo.getClass();
                            ExtraSceneInfo.ExpressInfo expressInfo2 = new ExtraSceneInfo.ExpressInfo();
                            expressInfo2.setExpressIndex(a(newPullParser.getText()));
                            expressInfo = expressInfo2;
                        } else if (str2.equals(h)) {
                            a(newPullParser.getText(), expressInfo, extraSceneInfo);
                        } else if (str2.equals(j)) {
                            extraSceneInfo.setOldHeadWearPath(this.T + "/" + newPullParser.getText());
                        } else if (str2.equals(k)) {
                            if (extraSceneInfo.getRandom() > 0) {
                                a(extraSceneInfo, newPullParser.getText());
                            } else {
                                extraSceneInfo.setNewHeadWearPath(this.T + "/" + newPullParser.getText());
                            }
                        } else if (str2.equals(l)) {
                            extraSceneInfo.setExpCheckId(a(newPullParser.getText()));
                        } else if (str2.equals(m)) {
                            extraSceneInfo.setExpCheckValue(b(newPullParser.getText()));
                        } else if (str2.equals(n)) {
                            extraSceneInfo.setOldToothPath(this.T + "/" + newPullParser.getText());
                        } else if (str2.equals(o)) {
                            extraSceneInfo.setNewToothPath(this.T + "/" + newPullParser.getText());
                        } else if (str2.equals(p)) {
                            extraSceneInfo.setLoopType(Integer.parseInt(newPullParser.getText()));
                        } else if (str2.equals(r)) {
                            a(newPullParser.getText(), extraSceneInfo);
                        } else if (str2.equals(f78f)) {
                            extraSceneInfo.setHeadWearColor(Integer.parseInt(newPullParser.getText()));
                        } else if (str2.equals(s)) {
                            extraSceneInfo.setRandom(Integer.parseInt(newPullParser.getText()));
                        } else if (str2.equals("pause")) {
                            b(newPullParser.getText(), extraSceneInfo);
                        } else if (str2.equals(q)) {
                            c(newPullParser.getText(), extraSceneInfo);
                        } else if (str2.equals(u)) {
                            extraSceneInfo.setExpIdInPut(a(newPullParser.getText()));
                        } else if (str2.equals(v)) {
                            extraSceneInfo.setExpIdOutPut(a(newPullParser.getText()));
                        }
                    }
                    str2 = str;
                }
            }
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    private void a(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        int i2;
        if (this.R.getExtraStatus() == 1) {
            boolean l2 = l(aSAvatarProcessInfo);
            if (l2) {
                i2 = this.W + 1;
                this.W = i2;
            } else {
                i2 = 0;
            }
            this.W = i2;
            LOG.d("rabbitEars", "check = " + l2 + ", mCheckFrameCount = " + this.W);
            if (this.W < 3) {
                return;
            }
        }
        float[] expWeights = aSAvatarProcessInfo.getExpWeights();
        if (this.R.getExtraStatus() == 3) {
            this.U = this.R.getExpressInfoList().size() - 1;
        }
        a(this.R.getExpressInfoList().get(this.U), expWeights);
        if (this.R.getExtraStatus() != 3) {
            if (this.U >= this.R.getExpressInfoList().size() - 1) {
                this.R.setExtraStatus(3);
                this.U = 0;
            } else {
                this.U++;
                this.R.setExtraStatus(2);
            }
        }
        aSAvatarProcessInfo.setExpWeights(expWeights);
    }

    private void a(ExtraSceneInfo.ExpressInfo expressInfo, float[] fArr) {
        ArrayList<ExtraSceneInfo.HairMaskInfo> hairMaskInfoList;
        int[] expressIndex = expressInfo.getExpressIndex();
        ArrayList<Float> expressValue = expressInfo.getExpressValue();
        if (this.ac.size() <= 0 && (hairMaskInfoList = this.R.getHairMaskInfoList()) != null) {
            for (int i2 = 0; i2 < hairMaskInfoList.size(); i2++) {
                ExtraSceneInfo.HairMaskInfo hairMaskInfo = hairMaskInfoList.get(i2);
                if (this.Q.configHairStyleID == hairMaskInfo.getHairId()) {
                    this.ac.add(hairMaskInfo);
                }
            }
        }
        for (int i3 = 0; i3 < expressValue.size(); i3++) {
            if (expressIndex.length == expressValue.size()) {
                fArr[expressIndex[i3]] = expressValue.get(i3).floatValue();
                LOG.d("expWeights", "set express[" + expressIndex[i3] + "] = " + expressValue.get(i3));
            } else {
                fArr[expressIndex[0]] = expressValue.get(i3).floatValue();
                LOG.d("expWeights", "set express[" + expressIndex[0] + "] = " + expressValue.get(i3));
            }
            Iterator<ExtraSceneInfo.HairMaskInfo> it = this.ac.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ExtraSceneInfo.HairMaskInfo next = it.next();
                if (next.getExpId() == expressIndex[i3]) {
                    LOG.d("setHairRenderParam", "expid -> " + expressIndex[i3] + ", HairInfo -> " + next.toString());
                    this.P.setHairRenderParam(next.getBlendShape(), next.getMask());
                    break;
                }
            }
        }
        if (this.R.getNewHeadWearPathList() != null) {
            String str = this.R.getNewHeadWearPathList().get(0);
            if (!TextUtils.isEmpty(str)) {
                if (!this.R.getAccPath().equals(str)) {
                    this.P.setHeadWearTexture(str);
                    this.R.setAccPath(str);
                    LOG.d("rabbitEars", "new headwear path = " + str);
                } else {
                    return;
                }
            }
        }
        if (!TextUtils.isEmpty(this.R.getNewToothPath())) {
            this.P.setToothTexture(this.R.getNewToothPath());
            LOG.d("rabbitEars", "new tooth path = " + this.R.getNewToothPath());
        }
    }

    private void a(ExtraSceneInfo extraSceneInfo, String str) {
        for (int i2 = 0; i2 < extraSceneInfo.getRandom(); i2++) {
            for (int i3 = 0; i3 < extraSceneInfo.getRandom(); i3++) {
                if (i2 != i3) {
                    extraSceneInfo.setNewHeadWearPath(this.T + "/" + str + "Tex_" + i2 + "-" + i3 + "_acc.png");
                }
            }
        }
    }

    private void a(String str, ExtraSceneInfo.ExpressInfo expressInfo, ExtraSceneInfo extraSceneInfo) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            int i2 = 0;
            for (String str2 : split) {
                i2 = Integer.parseInt(str2);
                expressInfo.setExpressValue(1.0f);
            }
            if (i2 > 1) {
                for (int i3 = 0; i3 < i2; i3++) {
                    extraSceneInfo.setExpressInfo(expressInfo);
                }
                return;
            }
            extraSceneInfo.setExpressInfo(expressInfo);
        }
    }

    private void a(String str, ExtraSceneInfo extraSceneInfo) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf("-");
            int indexOf2 = str.indexOf("*");
            int parseInt = Integer.parseInt(str.substring(0, indexOf));
            int parseInt2 = Integer.parseInt(str.substring(indexOf + 1, indexOf2));
            int parseInt3 = Integer.parseInt(str.substring(indexOf2 + 1, str.length()));
            LOG.d("expWeights", "begin =" + parseInt + ", end =" + parseInt2 + ", num =" + parseInt3);
            ArrayList<ExtraSceneInfo.ExpressInfo> expressInfoList = extraSceneInfo.getExpressInfoList();
            if (expressInfoList != null && expressInfoList.size() > 0) {
                for (int i2 = 0; i2 < expressInfoList.size(); i2++) {
                    ExtraSceneInfo.ExpressInfo expressInfo = expressInfoList.get(i2);
                    if (expressInfo.getExpressIndex()[0] == parseInt) {
                        extraSceneInfo.setLoopBeginFrameIndex(i2);
                    }
                    if (expressInfo.getExpressIndex()[0] == parseInt2) {
                        expressInfo.setLoopStatus(2);
                        extraSceneInfo.setLoopEndFrameIndex(i2);
                        extraSceneInfo.setLoopNum(parseInt3);
                    }
                }
            }
        }
    }

    private int[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(",");
        int[] iArr = new int[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            iArr[i2] = Integer.parseInt(split[i2].trim());
        }
        return iArr;
    }

    private void b(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        int i2;
        float[] expWeights = aSAvatarProcessInfo.getExpWeights();
        a(this.R.getExpressInfoList().get(this.U), expWeights);
        if (this.U >= this.R.getExpressInfoList().size() - 1) {
            i2 = 0;
        } else {
            i2 = this.U + 1;
            this.U = i2;
        }
        this.U = i2;
        if (!(this.R.getExpIdInPut() == null || this.R.getExpIdOutPut() == null)) {
            for (int i3 = 0; i3 < this.R.getExpIdInPut().length; i3++) {
                expWeights[this.R.getExpIdInPut()[i3]] = -expWeights[this.R.getExpIdOutPut()[i3]];
                LOG.d("rabbitTeeth", "" + this.R.getExpIdInPut()[i3] + ", = " + this.R.getExpIdOutPut()[i3]);
            }
        }
        aSAvatarProcessInfo.setExpWeights(expWeights);
    }

    private void b(String str, ExtraSceneInfo extraSceneInfo) {
        if (!TextUtils.isEmpty(str)) {
            int parseInt = Integer.parseInt(str);
            LOG.d("expWeights", "index =" + parseInt);
            ArrayList<ExtraSceneInfo.ExpressInfo> expressInfoList = extraSceneInfo.getExpressInfoList();
            if (expressInfoList != null && expressInfoList.size() > 0) {
                for (int i2 = 0; i2 < expressInfoList.size(); i2++) {
                    ExtraSceneInfo.ExpressInfo expressInfo = expressInfoList.get(i2);
                    if (expressInfo.getExpressIndex()[0] == parseInt) {
                        expressInfo.setPauseStatus(true);
                        extraSceneInfo.setPauseIndex(i2);
                        return;
                    }
                }
            }
        }
    }

    private float[] b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(",");
        float[] fArr = new float[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            fArr[i2] = Float.parseFloat(split[i2].trim());
        }
        return fArr;
    }

    private void c(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        LOG.d("check_expweight", "[54] = " + aSAvatarProcessInfo.getExpWeights()[54] + ", [55] = " + aSAvatarProcessInfo.getExpWeights()[55]);
        int i2 = 0;
        if (l(aSAvatarProcessInfo)) {
            LOG.d("clownHat", "[55] = " + aSAvatarProcessInfo.getExpWeights()[55]);
            if (this.R.getExtraStatus() != 3) {
                this.W++;
            } else {
                return;
            }
        } else if (this.R.getExtraStatus() != 2) {
            if (this.R.getExtraStatus() == 3) {
                LOG.d("clownHat", "[55] = " + aSAvatarProcessInfo.getExpWeights()[55]);
                this.R.setExtraStatus(1);
                this.W = 0;
                if (this.R.getNewHeadWearPathList() != null) {
                    if (this.V < this.R.getNewHeadWearPathList().size() - 1) {
                        i2 = this.V + 1;
                        this.V = i2;
                    }
                    this.V = i2;
                    return;
                }
                return;
            }
            this.W = 0;
            return;
        }
        LOG.d("clownHat", "mCheckFrameCount = " + this.W);
        if (this.W > 3) {
            if (TextUtils.isEmpty(this.R.getAccPath())) {
                ExtraSceneInfo extraSceneInfo = this.R;
                extraSceneInfo.setAccPath(extraSceneInfo.getOldHeadWearPath());
                this.V = 0;
            }
            String str = this.R.getNewHeadWearPathList() != null ? this.R.getNewHeadWearPathList().get(this.V) : "";
            if (!TextUtils.isEmpty(str) && !this.R.getAccPath().equals(str)) {
                this.P.setHeadWearTexture(str);
                this.R.setAccPath(str);
                LOG.d("clownHat", "Set Acc ->  = " + str);
            }
            if (this.R.getExtraStatus() != 3) {
                float[] expWeights = aSAvatarProcessInfo.getExpWeights();
                ExtraSceneInfo.ExpressInfo expressInfo = this.R.getExpressInfoList().get(this.U);
                int[] expressIndex = expressInfo.getExpressIndex();
                ArrayList<Float> expressValue = expressInfo.getExpressValue();
                for (int i3 = 0; i3 < expressValue.size(); i3++) {
                    if (expressIndex.length == expressValue.size()) {
                        expWeights[expressIndex[i3]] = expressValue.get(i3).floatValue();
                        LOG.d("clownHat", "set express[" + expressIndex[i3] + "] = " + expressValue.get(i3));
                    } else {
                        expWeights[expressIndex[0]] = expressValue.get(i3).floatValue();
                        LOG.d("clownHat", "set express[" + expressIndex[0] + "] = " + expressValue.get(i3));
                    }
                }
                aSAvatarProcessInfo.setExpWeights(expWeights);
                if (this.U >= this.R.getExpressInfoList().size() - 1) {
                    this.R.setExtraStatus(3);
                    this.U = 0;
                    return;
                }
                this.U++;
                this.R.setExtraStatus(2);
            }
        }
    }

    private void c(String str, ExtraSceneInfo extraSceneInfo) {
        if (!TextUtils.isEmpty(str)) {
            extraSceneInfo.getClass();
            ExtraSceneInfo.HairMaskInfo hairMaskInfo = new ExtraSceneInfo.HairMaskInfo();
            String[] split = str.split(";");
            for (int i2 = 0; i2 < split.length; i2++) {
                boolean z2 = true;
                int parseInt = Integer.parseInt(split[i2].substring(split[i2].indexOf("=") + 1));
                if (split[i2].indexOf(G) != -1) {
                    hairMaskInfo.setHairId(parseInt);
                } else if (split[i2].indexOf(H) != -1) {
                    hairMaskInfo.setExpId(parseInt);
                } else if (split[i2].indexOf(I) != -1) {
                    if (parseInt != 1) {
                        z2 = false;
                    }
                    hairMaskInfo.setBlendShape(z2);
                } else if (split[i2].indexOf(J) != -1) {
                    if (parseInt != 1) {
                        z2 = false;
                    }
                    hairMaskInfo.setMask(z2);
                }
            }
            extraSceneInfo.setHairMaskInfo(hairMaskInfo);
        }
    }

    private void d(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        LOG.d("check_expweight", "[54] = " + aSAvatarProcessInfo.getExpWeights()[54] + ", [55] = " + aSAvatarProcessInfo.getExpWeights()[55]);
        if (!l(aSAvatarProcessInfo)) {
            if (this.R.getExtraStatus() != 2) {
                if (this.R.getExtraStatus() == 3) {
                    this.R.setExtraStatus(1);
                    this.W = 0;
                    return;
                }
                this.W = 0;
                return;
            }
        } else if (this.R.getExtraStatus() != 3) {
            this.W++;
        } else {
            return;
        }
        if (this.W > 3) {
            float[] expWeights = aSAvatarProcessInfo.getExpWeights();
            a(this.R.getExpressInfoList().get(this.U), expWeights);
            if (this.R.getExtraStatus() != 3) {
                if (this.U >= this.R.getExpressInfoList().size() - 1) {
                    this.R.setExtraStatus(3);
                    this.U = 0;
                } else {
                    this.U++;
                    this.R.setExtraStatus(2);
                }
            }
            aSAvatarProcessInfo.setExpWeights(expWeights);
        }
    }

    private void e(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        String str;
        float[] orientations = aSAvatarProcessInfo.getOrientations();
        boolean z2 = false;
        if (orientations != null) {
            if (orientations[1] < -15.0f && orientations[0] < 10.0f) {
                LOG.d("emojiHelmet", "--- right---");
                this.aa++;
                this.Z = 0;
            } else if (orientations[1] <= 15.0f || orientations[0] >= 10.0f) {
                this.Z = 0;
                this.aa = 0;
            } else {
                LOG.d("emojiHelmet", "--- left---");
                this.Z++;
                this.aa = 0;
            }
        }
        if (this.Z >= 3 || this.aa >= 3) {
            z2 = true;
        }
        LOG.d("emojiHelmet", "check -> " + z2);
        if (!z2) {
            this.R.setExtraStatus(1);
        } else if (this.R.getExtraStatus() != 3) {
            if (this.ab == null) {
                this.ab = new Random();
            }
            LOG.d("emojiHelmet", "old left = " + this.R.getEmojiHelmetLeftRandomNum() + ", old right = " + this.R.getEmojiHelmetRightRandomNum());
            while (true) {
                int nextInt = this.ab.nextInt(this.R.getRandom());
                LOG.d("emojiHelmet", "random = " + nextInt);
                if (this.Z < 3) {
                    if (!(this.aa < 3 || this.R.getEmojiHelmetLeftRandomNum() == nextInt || this.R.getEmojiHelmetRightRandomNum() == nextInt)) {
                        this.R.setEmojiHelmetRightRandomNum(nextInt);
                        break;
                    }
                } else if (!(this.R.getEmojiHelmetLeftRandomNum() == nextInt || this.R.getEmojiHelmetRightRandomNum() == nextInt)) {
                    this.R.setEmojiHelmetLeftRandomNum(nextInt);
                    break;
                }
            }
            LOG.d("emojiHelmet", "new left = " + this.R.getEmojiHelmetLeftRandomNum() + ", new right = " + this.R.getEmojiHelmetRightRandomNum());
            if (this.Z >= 3 || this.aa >= 3) {
                str = "Tex_" + this.R.getEmojiHelmetRightRandomNum() + "-" + this.R.getEmojiHelmetLeftRandomNum() + "_acc.png";
            } else {
                str = "";
            }
            Iterator<String> it = this.R.getNewHeadWearPathList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                if (next.indexOf(str) != -1) {
                    if (!this.R.getAccPath().equals(next)) {
                        this.P.setHeadWearTexture(next);
                        this.R.setAccPath(next);
                        LOG.d("emojiHelmet", "acc path = " + next);
                    } else {
                        return;
                    }
                }
            }
            this.R.setExtraStatus(3);
        }
    }

    private void f(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        LOG.d("check_expweight", "[54] = " + aSAvatarProcessInfo.getExpWeights()[54] + ", [55] = " + aSAvatarProcessInfo.getExpWeights()[55]);
        if (!l(aSAvatarProcessInfo)) {
            if (this.R.getExtraStatus() != 2) {
                if (this.R.getExtraStatus() == 3) {
                    this.R.setExtraStatus(1);
                    this.W = 0;
                    return;
                }
                this.W = 0;
                return;
            }
        } else if (this.R.getExtraStatus() != 3) {
            this.W++;
        } else {
            return;
        }
        if (this.W > 3) {
            float[] expWeights = aSAvatarProcessInfo.getExpWeights();
            ExtraSceneInfo.ExpressInfo expressInfo = this.R.getExpressInfoList().get(this.U);
            a(expressInfo, expWeights);
            if (this.R.getExtraStatus() != 3) {
                if (expressInfo.getLoopStatus() == 2) {
                    int loopCount = this.R.getLoopCount();
                    LOG.d("expWeights", "-- loopCount = " + loopCount + ", number = " + this.R.getLoopNum());
                    if (loopCount < this.R.getLoopNum()) {
                        this.U = this.R.getLoopBeginFrameIndex();
                        LOG.d("expWeights", "-- mExtraSceneIndex = " + this.U);
                        this.R.setLoopCount(loopCount + 1);
                        aSAvatarProcessInfo.setExpWeights(expWeights);
                        return;
                    }
                }
                if (this.U >= this.R.getExpressInfoList().size() - 1) {
                    LOG.d("expWeights", "--- end ---");
                    this.R.setExtraStatus(3);
                    this.R.setLoopCount(0);
                    this.U = 0;
                    this.Y = 0;
                } else {
                    this.U++;
                    LOG.d("expWeights", "--> mExtraSceneIndex = " + this.U);
                    this.R.setExtraStatus(2);
                }
            }
            aSAvatarProcessInfo.setExpWeights(expWeights);
        }
    }

    private void g(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        if (this.R.getExtraStatus() == 1) {
            if (aSAvatarProcessInfo.getExpWeights()[0] > 0.8f && aSAvatarProcessInfo.getExpWeights()[1] < 0.2f) {
                this.Z++;
                this.aa = 0;
            } else if (aSAvatarProcessInfo.getExpWeights()[1] <= 0.8f || aSAvatarProcessInfo.getExpWeights()[0] >= 0.2f) {
                this.Z = 0;
                this.aa = 0;
            } else {
                this.aa++;
                this.Z = 0;
            }
            boolean z2 = this.Z >= 3 || this.aa > 3;
            LOG.d("wizardHat", "check = " + z2);
            if (!z2) {
                return;
            }
        }
        float[] expWeights = aSAvatarProcessInfo.getExpWeights();
        ExtraSceneInfo.ExpressInfo expressInfo = this.R.getExpressInfoList().get(this.U);
        a(expressInfo, expWeights);
        if (this.R.getExtraStatus() != 3) {
            if (expressInfo.getLoopStatus() == 2) {
                int loopCount = this.R.getLoopCount();
                LOG.d("expWeights", "-- loopCount = " + loopCount + ", number = " + this.R.getLoopNum());
                if (loopCount < this.R.getLoopNum()) {
                    this.U = this.R.getLoopBeginFrameIndex();
                    LOG.d("expWeights", "-- mExtraSceneIndex = " + this.U);
                    this.R.setLoopCount(loopCount + 1);
                    aSAvatarProcessInfo.setExpWeights(expWeights);
                    return;
                }
            }
            if (this.U >= this.R.getExpressInfoList().size() - 1) {
                LOG.d("expWeights", "--- end ---");
                this.R.setExtraStatus(1);
                this.R.setLoopCount(0);
                this.U = 0;
                this.Y = 0;
                this.Z = 0;
                this.aa = 0;
            } else {
                this.U++;
                LOG.d("expWeights", "--> mExtraSceneIndex = " + this.U);
                this.R.setExtraStatus(2);
            }
        }
        aSAvatarProcessInfo.setExpWeights(expWeights);
    }

    private void h(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        boolean l2 = l(aSAvatarProcessInfo);
        LOG.d("check_expweight", "[58] = " + aSAvatarProcessInfo.getExpWeights()[58]);
        if (!l2) {
            if (this.R.getExtraStatus() != 2) {
                if (this.R.getExtraStatus() == 3) {
                    this.R.setExtraStatus(1);
                    return;
                }
                return;
            }
        } else if (this.R.getExtraStatus() == 3) {
            return;
        }
        float[] expWeights = aSAvatarProcessInfo.getExpWeights();
        a(this.R.getExpressInfoList().get(this.U), expWeights);
        if (this.R.getExtraStatus() != 3) {
            if (this.U >= this.R.getExpressInfoList().size() - 1) {
                this.R.setExtraStatus(3);
                this.U = 0;
            } else {
                this.U++;
                this.R.setExtraStatus(2);
            }
        }
        aSAvatarProcessInfo.setExpWeights(expWeights);
    }

    private void i(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        float[] orientations = aSAvatarProcessInfo.getOrientations();
        if (orientations != null && orientations.length >= 3) {
            int i2 = orientations[2] > 15.0f ? 1 : orientations[2] < -15.0f ? 2 : 0;
            if (i2 == 0 && this.R.getHeadPitchStatus() == 0) {
                j(aSAvatarProcessInfo);
                return;
            }
            if (i2 == 0) {
                if (this.R.getExtraStatus() != 2) {
                    if (this.R.getExtraStatus() == 3) {
                        this.R.setHeadPitchStatus(0);
                        this.R.setExtraStatus(1);
                        return;
                    }
                    return;
                }
            } else if (i2 == 2 && this.R.getExtraStatus() == 1) {
                j(aSAvatarProcessInfo);
                return;
            } else if (this.R.getExtraStatus() == 3) {
                j(aSAvatarProcessInfo);
                return;
            }
            float[] expWeights = aSAvatarProcessInfo.getExpWeights();
            ExtraSceneInfo.ExpressInfo expressInfo = this.R.getExpressInfoList().get(this.U);
            int[] expressIndex = expressInfo.getExpressIndex();
            ArrayList<Float> expressValue = expressInfo.getExpressValue();
            for (int i3 = 0; i3 < expressValue.size(); i3++) {
                if (expressIndex.length == expressValue.size()) {
                    expWeights[expressIndex[i3]] = expressValue.get(i3).floatValue();
                    LOG.d("expWeights", "set express[" + expressIndex[i3] + "] = " + expressValue.get(i3));
                } else {
                    expWeights[expressIndex[0]] = expressValue.get(i3).floatValue();
                    LOG.d("expWeights", "set express[" + expressIndex[0] + "] = " + expressValue.get(i3));
                }
            }
            if (i2 != 0) {
                this.R.setHeadPitchStatus(i2);
            }
            if (this.R.getHeadPitchStatus() != 2 && this.R.getHeadPitchStatus() == 1) {
                if (expressInfo.isPauseFrame()) {
                    aSAvatarProcessInfo.setExpWeights(expWeights);
                    return;
                }
                String oldHeadWearPath = this.R.getOldHeadWearPath();
                if (!TextUtils.isEmpty(oldHeadWearPath) && !this.R.getAccPath().equals(oldHeadWearPath)) {
                    LOG.d("check_expweight", "-> " + oldHeadWearPath);
                    this.P.setHeadWearTexture(oldHeadWearPath);
                    this.R.setAccPath(oldHeadWearPath);
                }
            }
            if (this.R.getExtraStatus() != 3) {
                if (this.U >= this.R.getExpressInfoList().size() - 1) {
                    this.R.setExtraStatus(3);
                    this.U = 0;
                } else {
                    this.U++;
                    this.R.setExtraStatus(2);
                }
            }
            aSAvatarProcessInfo.setExpWeights(expWeights);
        }
    }

    private void j(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        LOG.d("check_expweight", "[48] = " + aSAvatarProcessInfo.getExpWeights()[48] + ", [49] = " + aSAvatarProcessInfo.getExpWeights()[49] + ", [50] = " + aSAvatarProcessInfo.getExpWeights()[50] + ", [51] = " + aSAvatarProcessInfo.getExpWeights()[51] + ", [52] = " + aSAvatarProcessInfo.getExpWeights()[52] + ", [54] = " + aSAvatarProcessInfo.getExpWeights()[54] + ", [55] = " + aSAvatarProcessInfo.getExpWeights()[55] + ", [21] = " + aSAvatarProcessInfo.getExpWeights()[21]);
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            if (i2 >= this.R.getExpCheckIdList().size()) {
                i2 = 0;
                break;
            }
            int[] iArr = this.R.getExpCheckIdList().get(i2);
            float[] fArr = this.R.getExpCheckValueList().get(i2);
            boolean z3 = z2;
            int i3 = 0;
            while (true) {
                if (i3 >= iArr.length) {
                    z2 = z3;
                    break;
                }
                if (fArr[i3] >= 0.0f && aSAvatarProcessInfo.getExpWeights()[iArr[i3]] > fArr[i3]) {
                    LOG.d("check_astronautHat", "[" + iArr[i3] + "] = " + fArr[i3] + ", exp[" + iArr[i3] + "] = " + aSAvatarProcessInfo.getExpWeights()[iArr[i3]]);
                } else if (fArr[i3] >= 0.0f || aSAvatarProcessInfo.getExpWeights()[iArr[i3]] >= (-fArr[i3])) {
                    LOG.d("check_astronautHat", "--- false ---");
                    z2 = false;
                } else {
                    LOG.d("check_astronautHat", "[" + iArr[i3] + "] = " + (-fArr[i3]) + ", exp[" + iArr[i3] + "] = " + aSAvatarProcessInfo.getExpWeights()[iArr[i3]]);
                }
                i3++;
                z3 = true;
            }
            LOG.d("check_astronautHat", "--- false ---");
            z2 = false;
            if (z2) {
                break;
            }
            i2++;
        }
        if (this.R.getNewHeadWearPathList() != null && i2 >= 0) {
            String str = this.R.getNewHeadWearPathList().get(i2);
            if (!TextUtils.isEmpty(str) && !this.R.getAccPath().equals(str)) {
                if (i2 == this.X) {
                    this.W++;
                } else {
                    this.X = i2;
                    this.W = 0;
                }
                if (this.W > 3) {
                    this.P.setHeadWearTexture(str);
                    this.R.setAccPath(str);
                    LOG.d("check_expweight", "new headwear path = " + str);
                }
            }
        }
    }

    private void k(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        if (TextUtils.isEmpty(this.R.getAccPath())) {
            ExtraSceneInfo extraSceneInfo = this.R;
            extraSceneInfo.setAccPath(extraSceneInfo.getOldHeadWearPath());
        }
        float[] expWeights = aSAvatarProcessInfo.getExpWeights();
        LOG.d("check_expweight", "[48] = " + aSAvatarProcessInfo.getExpWeights()[48] + ", [49] = " + aSAvatarProcessInfo.getExpWeights()[49] + ", [50] = " + aSAvatarProcessInfo.getExpWeights()[50] + ", [51] = " + aSAvatarProcessInfo.getExpWeights()[51] + ", [52] = " + aSAvatarProcessInfo.getExpWeights()[52] + ", [54] = " + aSAvatarProcessInfo.getExpWeights()[54] + ", [55] = " + aSAvatarProcessInfo.getExpWeights()[55] + ", [21] = " + aSAvatarProcessInfo.getExpWeights()[21] + ", [58] = " + aSAvatarProcessInfo.getExpWeights()[58]);
        if (!(this.R.getExpIdInPut() == null || this.R.getExpIdOutPut() == null)) {
            for (int i2 = 0; i2 < this.R.getExpIdInPut().length; i2++) {
                expWeights[this.R.getExpIdInPut()[i2]] = expWeights[this.R.getExpIdOutPut()[i2]];
                LOG.d("dogHat", "[" + this.R.getExpIdInPut()[i2] + "] = " + expWeights[this.R.getExpIdInPut()[i2]] + ", [" + this.R.getExpIdOutPut()[i2] + "] = " + expWeights[this.R.getExpIdOutPut()[i2]]);
            }
            aSAvatarProcessInfo.setExpWeights(expWeights);
        }
        boolean z2 = false;
        for (int i3 = 0; i3 < this.R.getExpCheckIdList().size(); i3++) {
            int[] iArr = this.R.getExpCheckIdList().get(i3);
            float[] fArr = this.R.getExpCheckValueList().get(i3);
            boolean z3 = z2;
            int i4 = 0;
            while (true) {
                if (i4 >= iArr.length) {
                    z2 = z3;
                    break;
                } else if ((fArr[i4] < 0.0f || aSAvatarProcessInfo.getExpWeights()[iArr[i4]] < fArr[i4]) && (fArr[i4] >= 0.0f || aSAvatarProcessInfo.getExpWeights()[iArr[i4]] > (-fArr[i4]))) {
                    z2 = false;
                } else {
                    i4++;
                    z3 = true;
                }
            }
            z2 = false;
            if (z2) {
                break;
            }
        }
        ExtraSceneInfo extraSceneInfo2 = this.R;
        String oldHeadWearPath = z2 ? extraSceneInfo2.getNewHeadWearPathList().get(0) : extraSceneInfo2.getOldHeadWearPath();
        if (!TextUtils.isEmpty(oldHeadWearPath) && !this.R.getAccPath().equals(oldHeadWearPath)) {
            this.P.setHeadWearTexture(oldHeadWearPath);
            this.R.setAccPath(oldHeadWearPath);
            LOG.d("check_expweight", "new headwear path = " + oldHeadWearPath);
        }
    }

    private boolean l(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        if (this.R.getExpCheckIdList() == null || this.R.getExpCheckValueList() == null) {
            return false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.R.getExpCheckIdList().size(); i2++) {
            int[] iArr = this.R.getExpCheckIdList().get(i2);
            float[] fArr = this.R.getExpCheckValueList().get(i2);
            boolean z3 = z2;
            int i3 = 0;
            while (true) {
                if (i3 >= iArr.length) {
                    z2 = z3;
                    break;
                } else if ((fArr[i3] <= 0.0f || aSAvatarProcessInfo.getExpWeights()[iArr[i3]] < fArr[i3]) && (fArr[i3] > 0.0f || aSAvatarProcessInfo.getExpWeights()[iArr[i3]] > (-fArr[i3]))) {
                    z2 = false;
                } else {
                    i3++;
                    z3 = true;
                }
            }
            if (z2) {
                break;
            }
        }
        return z2;
    }

    public void checkExtraScene(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        if (aSAvatarProcessInfo == null) {
            LOG.d(f73a, "info is null");
        } else if (this.Q == null) {
            LOG.d(f73a, "mConfigValue is null");
        } else {
            LOG.d("ConfigValue", "mConfigValue -> " + this.Q.toString());
            char c2 = 65535;
            if (this.Q.configHeadwearStyleID != -1) {
                if (this.R == null) {
                    Iterator<ExtraSceneInfo> it = this.S.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ExtraSceneInfo next = it.next();
                        if (this.Q.configHeadwearStyleID == next.getHeadWear()) {
                            if (next.getHeadWearColor() == -1) {
                                this.R = next;
                                break;
                            } else if (this.Q.configHeadwearColorID == next.getHeadWearColor()) {
                                this.R = next;
                                break;
                            }
                        }
                    }
                }
                ExtraSceneInfo extraSceneInfo = this.R;
                if (extraSceneInfo == null) {
                    LOG.d(f73a, "mExtraSceneInfo is null");
                    return;
                }
                String name = extraSceneInfo.getName();
                switch (name.hashCode()) {
                    case -1528869837:
                        if (name.equals(A)) {
                            c2 = 7;
                            break;
                        }
                        break;
                    case -531597040:
                        if (name.equals(z)) {
                            c2 = 6;
                            break;
                        }
                        break;
                    case -5252658:
                        if (name.equals(D)) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case 5541034:
                        if (name.equals(E)) {
                            c2 = '\b';
                            break;
                        }
                        break;
                    case 599957095:
                        if (name.equals("AngelWings")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 729448247:
                        if (name.equals(x)) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 1152018798:
                        if (name.equals("RabbitTeeth")) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case 1161409278:
                        if (name.equals("ClownHat")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 1716027151:
                        if (name.equals(y)) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case 2052433887:
                        if (name.equals(F)) {
                            c2 = '\t';
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        d(aSAvatarProcessInfo);
                        return;
                    case 1:
                        c(aSAvatarProcessInfo);
                        return;
                    case 2:
                        h(aSAvatarProcessInfo);
                        return;
                    case 3:
                        b(aSAvatarProcessInfo);
                        return;
                    case 4:
                        a(aSAvatarProcessInfo);
                        return;
                    case 5:
                        f(aSAvatarProcessInfo);
                        return;
                    case 6:
                        g(aSAvatarProcessInfo);
                        return;
                    case 7:
                        e(aSAvatarProcessInfo);
                        return;
                    case '\b':
                        i(aSAvatarProcessInfo);
                        return;
                    case '\t':
                        k(aSAvatarProcessInfo);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void resetExtraScene() {
        ExtraSceneInfo extraSceneInfo;
        this.V = 0;
        this.W = 0;
        this.X = -1;
        this.U = 0;
        this.Z = 0;
        this.aa = 0;
        this.Y = 0;
        if (this.P != null && (extraSceneInfo = this.R) != null) {
            if (extraSceneInfo != null) {
                if (!TextUtils.isEmpty(extraSceneInfo.getOldToothPath())) {
                    this.P.setToothTexture(this.R.getOldToothPath());
                }
                if (!TextUtils.isEmpty(this.R.getOldHeadWearPath())) {
                    this.P.setHeadWearTexture(this.R.getOldHeadWearPath());
                    ExtraSceneInfo extraSceneInfo2 = this.R;
                    extraSceneInfo2.setAccPath(extraSceneInfo2.getOldHeadWearPath());
                }
                this.R.setIsOldHeadWearAcc(true);
                this.R.setExtraStatus(1);
                this.R.setLoopCount(0);
                this.R.setEmojiHelmetLeftRandomNum(0);
                this.R.setEmojiHelmetRightRandomNum(0);
                this.R.setHeadPitchStatus(0);
                List<ExtraSceneInfo.HairMaskInfo> list = this.ac;
                if (list != null && list.size() > 0) {
                    this.P.setHairRenderParam(true, true);
                }
                this.ac.clear();
            }
            this.R = null;
        }
    }

    public void setAvatarEngine(AvatarEngine avatarEngine) {
        LOG.d(f73a, "-- setAvatarEngine --");
        this.P = avatarEngine;
        this.P.getConfigValue(this.Q);
        this.ac.clear();
        this.R = null;
    }
}
