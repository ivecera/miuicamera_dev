package com.android.camera.aiwatermark.handler;

import android.text.TextUtils;
import com.android.camera.aiwatermark.algo.ChinaDateConvertor;
import com.android.camera.aiwatermark.data.FestivalWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class ChinaFestivalHandler extends FestivalHandler {
    private static final String TAG = "ChinaFestivalHandler";

    public ChinaFestivalHandler(boolean z) {
        super(z);
    }

    private WatermarkItem findFestivalWatermark(String str, int i) {
        ArrayList<WatermarkItem> festivalWatermark = new FestivalWatermark().getFestivalWatermark(i);
        String str2 = TAG;
        Log.d(str2, "list.size() = " + festivalWatermark.size());
        Iterator<WatermarkItem> it = festivalWatermark.iterator();
        while (it.hasNext()) {
            WatermarkItem next = it.next();
            if (TextUtils.equals(str, next.getKey())) {
                return next;
            }
        }
        return null;
    }

    private String getChinaDate() {
        return ChinaDateConvertor.today();
    }

    private String getDate() {
        return ChinaDateConvertor.getDate();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler, com.android.camera.aiwatermark.handler.FestivalHandler
    public WatermarkItem findWatermark() {
        String chinaDate = ChinaDateConvertor.isChineseEve() ? WatermarkConstant.CHINESE_EVE : getChinaDate();
        String str = TAG;
        Log.d(str, "ChineseDate = " + chinaDate);
        WatermarkItem findFestivalWatermark = findFestivalWatermark(chinaDate, 1);
        if (findFestivalWatermark != null) {
            return findFestivalWatermark;
        }
        WatermarkItem findFestivalWatermark2 = findFestivalWatermark(getDate(), 0);
        if (findFestivalWatermark2 != null) {
            return findFestivalWatermark2;
        }
        Log.d(TAG, "Festival watermark null");
        return null;
    }
}
