package com.android.camera.aiwatermark.handler;

import android.location.Location;
import com.android.camera.LocationManager;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class CityHandler extends AbstractHandler {
    private static final String TAG = "CityHandler";

    public CityHandler(boolean z) {
        super(z);
    }

    private Location getLocation() {
        return LocationManager.instance().getCurrentLocation();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        String address;
        Location location = getLocation();
        if (!(location == null || (address = getAddress(location.getLatitude(), location.getLongitude())) == null)) {
            Iterator<WatermarkItem> it = getCityWatermarkList().iterator();
            while (it.hasNext()) {
                WatermarkItem next = it.next();
                if (next.getKey().toLowerCase().contains(address.toLowerCase())) {
                    return next;
                }
            }
        }
        Log.d(TAG, "CityHandler findWatermark");
        return null;
    }

    public abstract String getAddress(double d2, double d3);

    public abstract ArrayList<WatermarkItem> getCityWatermarkList();
}
