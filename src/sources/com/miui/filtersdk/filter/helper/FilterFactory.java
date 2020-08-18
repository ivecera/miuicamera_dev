package com.miui.filtersdk.filter.helper;

import android.util.Log;
import com.miui.filtersdk.filter.base.ColorLookupFilter;
import java.util.ArrayList;

public class FilterFactory {
    private static final String TAG = "FilterFactory";

    /* renamed from: com.miui.filtersdk.filter.helper.FilterFactory$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory = new int[FilterCategory.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.AI.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.LIGHTING.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.NORMAL.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.BEAUTY.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.BEAUTY_INDIA.ordinal()] = 5;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.STICKER.ordinal()] = 6;
            try {
                $SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[FilterCategory.MI_LIVE.ordinal()] = 7;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static ColorLookupFilter getFilter(FilterType filterType, boolean z, boolean z2) {
        String[] resources = filterType.getResources();
        if (resources == null || resources.length == 0) {
            throw new IllegalStateException("Can't find the resources corresponding to [ " + filterType + ", " + z + ", " + z2 + "]");
        }
        ColorLookupFilter colorLookupFilter = null;
        switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$filter$helper$FilterCategory[filterType.getFilterCategory().ordinal()]) {
            case 1:
                colorLookupFilter = new ColorLookupFilter(z2 ? resources[2] : z ? resources[1] : resources[0], 64);
                break;
            case 2:
                colorLookupFilter = new ColorLookupFilter(resources[0], 64);
                break;
            case 3:
                colorLookupFilter = new ColorLookupFilter(resources[0], 512);
                break;
            case 4:
                colorLookupFilter = new ColorLookupFilter(z ? resources[1] : resources[0], 512);
                break;
            case 5:
                colorLookupFilter = new ColorLookupFilter(resources[0], 512);
                break;
            case 6:
                colorLookupFilter = new ColorLookupFilter(resources[0], 512);
                break;
            case 7:
                colorLookupFilter = new ColorLookupFilter(resources[0], 512);
                break;
        }
        String str = TAG;
        Log.d(str, "FilterType: " + filterType + "(" + filterType.ordinal() + ", " + colorLookupFilter + ")");
        return colorLookupFilter;
    }

    public static FilterType[] getFiltersByCategory(FilterCategory filterCategory) {
        ArrayList arrayList = new ArrayList();
        FilterType[] values = FilterType.values();
        for (FilterType filterType : values) {
            if (filterType.getFilterCategory() == filterCategory) {
                arrayList.add(filterType);
            }
        }
        return (FilterType[]) arrayList.toArray(new FilterType[arrayList.size()]);
    }
}
