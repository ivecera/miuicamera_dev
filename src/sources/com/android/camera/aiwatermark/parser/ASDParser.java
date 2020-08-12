package com.android.camera.aiwatermark.parser;

import android.content.Context;
import android.text.TextUtils;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ASDParser extends AbstractParser {
    private static final String TAG = AbstractParser.class.getSimpleName();

    @Override // com.android.camera.aiwatermark.parser.AbstractParser
    public ArrayList<WatermarkItem> parseJson() {
        ArrayList<WatermarkItem> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONObject(readJson()).getJSONArray(WatermarkConstant.ROOT_TAG);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(new WatermarkItem(jSONObject.getString(WatermarkConstant.ITEM_KEY), jSONObject.getInt("type"), null, jSONObject.getInt("id"), jSONObject.getInt("location"), jSONObject.getInt(WatermarkConstant.ITEM_COUNTRY)));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.parser.AbstractParser
    public ArrayList<WatermarkItem> parseXml() {
        try {
            Context androidContext = CameraAppImpl.getAndroidContext();
            InputStream openRawResource = androidContext.getResources().openRawResource(R.raw.watermark);
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new InputStreamReader(openRawResource));
            int c_35955_0x0002_OR_1 = DataRepository.dataItemFeature().c_35955_0x0002_OR_1();
            int eventType = newPullParser.getEventType();
            Log.d(TAG, "start ... type =" + eventType);
            WatermarkItem watermarkItem = null;
            String str = null;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (eventType != 1) {
                if (eventType != 0) {
                    char c2 = 65535;
                    if (eventType != 2) {
                        if (eventType != 3) {
                            if (eventType != 4) {
                            }
                        } else if (TextUtils.equals(WatermarkConstant.ITEM_TAG, newPullParser.getName())) {
                            int identifier = androidContext.getResources().getIdentifier("ic_wp_" + ((AbstractParser) this).markItem.getKey(), "drawable", androidContext.getPackageName());
                            if (identifier != -1) {
                                ((AbstractParser) this).markItem.setResId(identifier);
                            }
                            int identifier2 = androidContext.getResources().getIdentifier("ic_wr_" + ((AbstractParser) this).markItem.getKey(), "drawable", androidContext.getPackageName());
                            if (identifier2 != -1) {
                                ((AbstractParser) this).markItem.setResRvItem(identifier2);
                            }
                            if (c_35955_0x0002_OR_1 == ((AbstractParser) this).markItem.getCountry()) {
                                ((AbstractParser) this).watermarkItems.add(((AbstractParser) this).markItem);
                            }
                            ((AbstractParser) this).markItem = watermarkItem;
                        } else if (TextUtils.equals(WatermarkConstant.ROOT_TAG, newPullParser.getName())) {
                            Log.d(TAG, "end this paser watermarkItems=" + ((AbstractParser) this).watermarkItems.size());
                            return ((AbstractParser) this).watermarkItems;
                        }
                    }
                    if (newPullParser.getName() != null) {
                        String name = newPullParser.getName();
                        switch (name.hashCode()) {
                            case -213424028:
                                if (name.equals(WatermarkConstant.ITEM_TAG)) {
                                    c2 = 0;
                                    break;
                                }
                                break;
                            case 3355:
                                if (name.equals("id")) {
                                    c2 = 3;
                                    break;
                                }
                                break;
                            case 106079:
                                if (name.equals(WatermarkConstant.ITEM_KEY)) {
                                    c2 = 1;
                                    break;
                                }
                                break;
                            case 3575610:
                                if (name.equals("type")) {
                                    c2 = 2;
                                    break;
                                }
                                break;
                            case 957831062:
                                if (name.equals(WatermarkConstant.ITEM_COUNTRY)) {
                                    c2 = 5;
                                    break;
                                }
                                break;
                            case 1901043637:
                                if (name.equals("location")) {
                                    c2 = 4;
                                    break;
                                }
                                break;
                        }
                        if (c2 == 0) {
                            ((AbstractParser) this).markItem = new WatermarkItem(str, i, null, i2, i3, i4);
                        } else if (c2 == 1) {
                            str = newPullParser.nextText();
                            ((AbstractParser) this).markItem.setKey(str);
                        } else if (c2 == 2) {
                            int parseInt = Integer.parseInt(newPullParser.nextText());
                            ((AbstractParser) this).markItem.setType(parseInt);
                            i = parseInt;
                        } else if (c2 == 3) {
                            int parseInt2 = Integer.parseInt(newPullParser.nextText());
                            ((AbstractParser) this).markItem.setResId(parseInt2);
                            i2 = parseInt2;
                        } else if (c2 == 4) {
                            int parseInt3 = Integer.parseInt(newPullParser.nextText());
                            ((AbstractParser) this).markItem.setLocation(parseInt3);
                            i3 = parseInt3;
                        } else if (c2 == 5) {
                            int parseInt4 = Integer.parseInt(newPullParser.nextText());
                            ((AbstractParser) this).markItem.setCountry(parseInt4);
                            i4 = parseInt4;
                        }
                    }
                } else {
                    ((AbstractParser) this).watermarkItems.clear();
                }
                Log.d(TAG, "before next()");
                eventType = newPullParser.next();
                watermarkItem = null;
            }
        } catch (IOException e2) {
            Log.d(TAG, "ioexception");
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            Log.d(TAG, "XmlPullParserException e=" + e3.getMessage());
            e3.printStackTrace();
        }
        return ((AbstractParser) this).watermarkItems;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x007a A[SYNTHETIC, Splitter:B:43:0x007a] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0082 A[Catch:{ IOException -> 0x007e }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0087 A[Catch:{ IOException -> 0x007e }] */
    public String readJson() {
        InputStreamReader inputStreamReader;
        InputStream inputStream;
        BufferedReader bufferedReader;
        IOException e2;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            inputStream = CameraAppImpl.getAndroidContext().getResources().openRawResource(R.raw.watermark);
            try {
                inputStreamReader = new InputStreamReader(inputStream);
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                        } catch (IOException e3) {
                            e2 = e3;
                            try {
                                e2.printStackTrace();
                                if (bufferedReader != null) {
                                }
                                if (inputStreamReader != null) {
                                }
                                if (inputStream != null) {
                                }
                                return sb.toString();
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader2 = bufferedReader;
                                if (bufferedReader2 != null) {
                                    try {
                                        bufferedReader2.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (inputStreamReader != null) {
                                    inputStreamReader.close();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                throw th;
                            }
                        }
                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();
                    try {
                        bufferedReader.close();
                        inputStreamReader.close();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                } catch (IOException e6) {
                    bufferedReader = null;
                    e2 = e6;
                    e2.printStackTrace();
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    return sb.toString();
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader2 != null) {
                    }
                    if (inputStreamReader != null) {
                    }
                    if (inputStream != null) {
                    }
                    throw th;
                }
            } catch (IOException e7) {
                bufferedReader = null;
                e2 = e7;
                inputStreamReader = null;
                e2.printStackTrace();
                if (bufferedReader != null) {
                }
                if (inputStreamReader != null) {
                }
                if (inputStream != null) {
                }
                return sb.toString();
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = null;
                if (bufferedReader2 != null) {
                }
                if (inputStreamReader != null) {
                }
                if (inputStream != null) {
                }
                throw th;
            }
        } catch (IOException e8) {
            inputStreamReader = null;
            bufferedReader = null;
            e2 = e8;
            inputStream = null;
            e2.printStackTrace();
            if (bufferedReader != null) {
            }
            if (inputStreamReader != null) {
            }
            if (inputStream != null) {
            }
            return sb.toString();
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            inputStreamReader = null;
            if (bufferedReader2 != null) {
            }
            if (inputStreamReader != null) {
            }
            if (inputStream != null) {
            }
            throw th;
        }
        return sb.toString();
    }
}
