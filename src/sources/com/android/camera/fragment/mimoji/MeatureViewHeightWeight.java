package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

public class MeatureViewHeightWeight {
    public static void setGridViewHeightBasedOnChildren(Context context, GridView gridView, int i) {
        if (gridView.getAdapter() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
            layoutParams.height = 330 * (i % 3 != 0 ? (i / 3) + 1 : i / 3);
            layoutParams.width = displayMetrics.widthPixels;
            gridView.setLayoutParams(layoutParams);
        }
    }
}
