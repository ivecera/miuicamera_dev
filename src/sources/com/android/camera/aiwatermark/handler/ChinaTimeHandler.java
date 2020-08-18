package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChinaTimeHandler extends TimeHandler {
    private static final String TAG = "ChinaTimeHandler";
    private static final String TEMPLATE = "yyyy-MM-dd HH:mm";
    private static final String[] WEEK = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public ChinaTimeHandler(boolean z) {
        super(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler, com.android.camera.aiwatermark.handler.TimeHandler
    public WatermarkItem findWatermark() {
        String str = new SimpleDateFormat(TEMPLATE).format(new Date()) + " " + WEEK[getDayOfWeek()];
        Log.d(TAG, "ChinaTime watermarkText = " + str);
        return new WatermarkItem("time", 9, str, -1, 48);
    }
}
