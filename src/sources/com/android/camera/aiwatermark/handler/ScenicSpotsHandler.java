package com.android.camera.aiwatermark.handler;

import android.location.Location;
import android.text.TextUtils;
import com.android.camera.LocationManager;
import com.android.camera.aiwatermark.data.Region;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class ScenicSpotsHandler extends AbstractHandler {
    private static final String TAG = "ScenicSpotsHandler";

    public ScenicSpotsHandler(boolean z) {
        super(z);
    }

    private Location getLocation() {
        return LocationManager.instance().getCurrentLocation();
    }

    /* access modifiers changed from: protected */
    public String findScenicSpot() {
        Location location = getLocation();
        String str = TAG;
        Log.d(str, "loc=" + location);
        if (location == null) {
            return null;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        HashMap<String, Region> regionMap = getRegionMap();
        if (regionMap == null || regionMap.isEmpty()) {
            return null;
        }
        for (String str2 : regionMap.keySet()) {
            boolean isInRegion = getRegionMap().get(str2).isInRegion(latitude, longitude);
            String str3 = TAG;
            Log.d(str3, "key=" + str2 + "; isInRegion=" + isInRegion);
            if (isInRegion) {
                return str2;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        String findScenicSpot = findScenicSpot();
        if (findScenicSpot == null) {
            return null;
        }
        Iterator<WatermarkItem> it = getWatermarkList().iterator();
        while (it.hasNext()) {
            WatermarkItem next = it.next();
            if (TextUtils.equals(findScenicSpot, next.getKey())) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Region> getRegionMap();

    /* access modifiers changed from: protected */
    public abstract ArrayList<WatermarkItem> getWatermarkList();
}
