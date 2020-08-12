package com.android.camera.aiwatermark.data;

import com.android.camera.aiwatermark.parser.ASDParser;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractWatermarkData {
    private static final String TAG = "AbstractWatermarkData";
    private static ArrayList<WatermarkItem> mAllWatermarks = new ArrayList<>();
    private static volatile Object mLock = new Object();

    static {
        ASDParser aSDParser = new ASDParser();
        Log.d(TAG, "parser start");
        synchronized (AbstractWatermarkData.class) {
            mAllWatermarks.addAll(aSDParser.parseByPattern(2));
            String str = TAG;
            Log.d(str, "mAllWatermarks.size()=" + mAllWatermarks.size());
            AbstractWatermarkData.class.notifyAll();
        }
        Log.d(TAG, "parser end");
    }

    public abstract ArrayList<WatermarkItem> getForAI();

    public abstract ArrayList<WatermarkItem> getForManual();

    public ArrayList<WatermarkItem> getWatermarkByType(int i) {
        String str = TAG;
        Log.d(str, "getWatermarkByType type = " + i);
        if (mLock == null) {
            synchronized (AbstractWatermarkData.class) {
                if (mLock == null) {
                    try {
                        AbstractWatermarkData.class.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
        ArrayList<WatermarkItem> arrayList = new ArrayList<>();
        Iterator<WatermarkItem> it = mAllWatermarks.iterator();
        while (it.hasNext()) {
            WatermarkItem next = it.next();
            if (next.getType() == i) {
                arrayList.add(next);
            }
        }
        String str2 = TAG;
        Log.d(str2, "getWatermarkByType list.size() = " + arrayList.size());
        return arrayList;
    }
}
