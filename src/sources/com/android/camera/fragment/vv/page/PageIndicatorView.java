package com.android.camera.fragment.vv.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.camera.R;
import com.android.camera.Util;
import java.util.ArrayList;
import java.util.List;

public class PageIndicatorView extends LinearLayout {
    private int dotSize;
    private List<View> indicatorViews;
    private Context mContext;
    private int margins;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PageIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = null;
        this.dotSize = 5;
        this.margins = 4;
        this.indicatorViews = null;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setGravity(17);
        setOrientation(0);
        this.dotSize = Util.dpToPixel((float) this.dotSize);
        this.margins = Util.dpToPixel((float) this.margins);
    }

    public void initIndicator(int i) {
        List<View> list = this.indicatorViews;
        if (list == null) {
            this.indicatorViews = new ArrayList();
        } else {
            list.clear();
            removeAllViews();
        }
        int i2 = this.dotSize;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
        int i3 = this.margins;
        layoutParams.setMargins(i3, i3, i3, i3);
        for (int i4 = 0; i4 < i; i4++) {
            View view = new View(this.mContext);
            view.setBackgroundResource(R.drawable.bg_vv_indicator_unselected);
            addView(view, layoutParams);
            this.indicatorViews.add(view);
        }
        if (this.indicatorViews.size() > 0) {
            this.indicatorViews.get(0).setBackgroundResource(R.drawable.bg_vv_indicator_selected);
        }
    }

    public void setSelectedPage(int i) {
        if (this.indicatorViews != null) {
            for (int i2 = 0; i2 < this.indicatorViews.size(); i2++) {
                if (i2 == i) {
                    this.indicatorViews.get(i2).setBackgroundResource(R.drawable.bg_vv_indicator_selected);
                } else {
                    this.indicatorViews.get(i2).setBackgroundResource(R.drawable.bg_vv_indicator_unselected);
                }
            }
        }
    }
}
