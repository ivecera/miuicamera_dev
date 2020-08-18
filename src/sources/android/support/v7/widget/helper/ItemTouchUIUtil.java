package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface ItemTouchUIUtil {
    void clearView(View view);

    void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i, boolean z);

    void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i, boolean z);

    void onSelected(View view);
}
