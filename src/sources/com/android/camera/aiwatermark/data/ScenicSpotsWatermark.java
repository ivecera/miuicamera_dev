package com.android.camera.aiwatermark.data;

import com.android.camera.aiwatermark.util.WatermarkConstant;
import java.util.ArrayList;
import java.util.HashMap;

public class ScenicSpotsWatermark extends AbstractWatermarkData {
    private HashMap<String, Region> mChinaMap = new HashMap<>();
    private HashMap<String, Region> mIndiaMap = new HashMap<>();

    private HashMap<String, Region> getChinaRegion() {
        HashMap<String, Region> hashMap = new HashMap<>();
        hashMap.put(WatermarkConstant.GREAT_WALL, new Region(40.352948d, 40.36147d, 116.008341d, 116.02163d));
        hashMap.put(WatermarkConstant.THE_PALACE_MUSEUM, new Region(39.911795d, 39.921797d, 116.38528d, 116.396291d));
        hashMap.put(WatermarkConstant.TEMPLE_OF_HEAVEN, new Region(39.873963d, 39.886436d, 116.398855d, 116.413415d));
        hashMap.put(WatermarkConstant.THE_SUMMER_PALACE, new Region(39.980175d, 40.000908d, 116.261077d, 116.276022d));
        hashMap.put(WatermarkConstant.WEST_LAKE, new Region(30.231458d, 30.259842d, 120.131927d, 120.15292d));
        hashMap.put(WatermarkConstant.SUZHOU_GARDENS, new Region(31.297664d, 31.324914d, 120.591308d, 120.636326d));
        hashMap.put(WatermarkConstant.MOUNT_HUANG, new Region(30.083376d, 30.196727d, 118.0883d, 118.219734d));
        hashMap.put(WatermarkConstant.THE_TERRACOTTA_ARMY, new Region(34.381643d, 34.387646d, 109.27575d, 109.284215d));
        hashMap.put(WatermarkConstant.ZHANG_JIA_JIE, new Region(29.007309d, 29.430007d, 110.279177d, 110.672471d));
        hashMap.put(WatermarkConstant.HONG_YA_CAVE, new Region(29.561129d, 29.563247d, 106.57395d, 106.580355d));
        hashMap.put(WatermarkConstant.TANG_PARADISE, new Region(34.209208d, 34.216619d, 108.969443d, 108.979326d));
        hashMap.put(WatermarkConstant.GU_LANG_ISLAND, new Region(24.436717d, 24.453097d, 118.057854d, 118.077808d));
        hashMap.put(WatermarkConstant.THE_POTALA_PALACE, new Region(29.652546d, 29.657926d, 91.115778d, 91.121411d));
        hashMap.put(WatermarkConstant.THE_BUND, new Region(31.231413d, 31.243881d, 121.487579d, 121.49412d));
        hashMap.put(WatermarkConstant.VICTORIA_HARBOR, new Region(22.282453d, 22.30243d, 114.139181d, 114.201111d));
        return hashMap;
    }

    private HashMap<String, Region> getIndiaRegion() {
        HashMap<String, Region> hashMap = new HashMap<>();
        hashMap.put(WatermarkConstant.TAJ_MAHAL, new Region(27.169956d, 27.1755d, 78.040237d, 78.04369d));
        hashMap.put(WatermarkConstant.RED_FORT, new Region(28.650783d, 28.660576d, 77.237706d, 77.244214d));
        hashMap.put(WatermarkConstant.GATEWAY_MUMBAI, new Region(18.921739d, 18.922262d, 72.834264d, 72.834951d));
        hashMap.put(WatermarkConstant.GATEWAY_DELHI, new Region(28.612749d, 28.61313d, 77.22937d, 77.229744d));
        hashMap.put(WatermarkConstant.JATRAPATTI_SHIWAJI, new Region(18.939416d, 18.940478d, 72.834834d, 72.836427d));
        hashMap.put(WatermarkConstant.AGRA_FORT, new Region(27.176115d, 27.183133d, 78.017816d, 78.024911d));
        hashMap.put(WatermarkConstant.AMBER_FORT, new Region(26.980169d, 26.987746d, 75.850224d, 75.855752d));
        hashMap.put(WatermarkConstant.HAWA_MAHAL, new Region(26.923365d, 26.924633d, 75.826361d, 75.827123d));
        return hashMap;
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForAI() {
        return getWatermarkByType(5);
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForManual() {
        return getWatermarkByType(1);
    }

    public HashMap<String, Region> getRegionMap(int i) {
        if (i == 1) {
            if (this.mChinaMap.isEmpty()) {
                this.mChinaMap = getChinaRegion();
            }
            return this.mChinaMap;
        } else if (i != 2) {
            return null;
        } else {
            if (this.mIndiaMap.isEmpty()) {
                this.mIndiaMap = getIndiaRegion();
            }
            return this.mIndiaMap;
        }
    }
}
