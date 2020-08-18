package com.android.camera.aiwatermark.handler;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.android.camera.aiwatermark.data.CityWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChinaCityHandler extends CityHandler {
    private static final String TAG = "ChinaCityHandler";
    private Context mContext = null;

    public ChinaCityHandler(boolean z, Context context) {
        super(z);
        this.mContext = context;
    }

    @Override // com.android.camera.aiwatermark.handler.CityHandler
    public String getAddress(double d2, double d3) {
        Geocoder geocoder = new Geocoder(this.mContext, Locale.ENGLISH);
        String str = null;
        try {
            List<Address> fromLocation = geocoder.getFromLocation(d2, d3, 1);
            String str2 = TAG;
            Log.d(str2, "list=" + fromLocation.size());
            if (fromLocation != null) {
                for (Address address : fromLocation) {
                    String str3 = TAG;
                    Log.d(str3, "address=" + address.toString());
                    str = address.getLocality();
                }
            }
        } catch (IOException unused) {
            Log.e(TAG, "[getAddress] io exception");
        }
        return str;
    }

    @Override // com.android.camera.aiwatermark.handler.CityHandler
    public ArrayList<WatermarkItem> getCityWatermarkList() {
        return new CityWatermark().getForAI();
    }
}
