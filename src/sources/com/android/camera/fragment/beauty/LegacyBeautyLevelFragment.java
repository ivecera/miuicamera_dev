package com.android.camera.fragment.beauty;

import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.beauty.SingleCheckAdapter;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class LegacyBeautyLevelFragment extends BeautyLevelFragment {
    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BeautyLevelFragment
    public int beautyLevelToPosition(int i, int i2) {
        return Util.clamp(i, 0, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BeautyLevelFragment
    public List<SingleCheckAdapter.LevelItem> initBeautyItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SingleCheckAdapter.LevelItem((int) R.drawable.ic_config_front_beauty_off));
        arrayList.add(new SingleCheckAdapter.LevelItem((int) R.drawable.f1));
        arrayList.add(new SingleCheckAdapter.LevelItem((int) R.drawable.f2));
        arrayList.add(new SingleCheckAdapter.LevelItem((int) R.drawable.f3));
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BeautyLevelFragment
    public int provideItemHorizontalMargin() {
        return getResources().getDimensionPixelSize(R.dimen.legacy_beauty_level_item_margin);
    }
}
