package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.WatermarkItem;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHandler extends AbstractHandler {
    public TimeHandler(boolean z) {
        super(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        return new WatermarkItem("time", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 48, 1);
    }

    public int getDayOfWeek() {
        return Calendar.getInstance().get(7) - 1;
    }
}
