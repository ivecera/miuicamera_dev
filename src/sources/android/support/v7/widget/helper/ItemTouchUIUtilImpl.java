package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemTouchUIUtilImpl implements ItemTouchUIUtil {
    static final ItemTouchUIUtil INSTANCE = new ItemTouchUIUtilImpl();

    ItemTouchUIUtilImpl() {
    }

    private static float findMaxElevation(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        float f2 = 0.0f;
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (childAt != view) {
                float elevation = ViewCompat.getElevation(childAt);
                if (elevation > f2) {
                    f2 = elevation;
                }
            }
        }
        return f2;
    }

    @Override // android.support.v7.widget.helper.ItemTouchUIUtil
    public void clearView(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag != null && (tag instanceof Float)) {
                ViewCompat.setElevation(view, ((Float) tag).floatValue());
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
        }
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override // android.support.v7.widget.helper.ItemTouchUIUtil
    public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i, boolean z) {
        if (Build.VERSION.SDK_INT >= 21 && z && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
            Float valueOf = Float.valueOf(ViewCompat.getElevation(view));
            ViewCompat.setElevation(view, findMaxElevation(recyclerView, view) + 1.0f);
            view.setTag(R.id.item_touch_helper_previous_elevation, valueOf);
        }
        view.setTranslationX(f2);
        view.setTranslationY(f3);
    }

    @Override // android.support.v7.widget.helper.ItemTouchUIUtil
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i, boolean z) {
    }

    @Override // android.support.v7.widget.helper.ItemTouchUIUtil
    public void onSelected(View view) {
    }
}
