package com.android.camera.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.camera.R;

public class ValuePreference extends Preference {
    private String mLabels;
    private int mMaxEms;

    public ValuePreference(Context context) {
        this(context, null);
    }

    public ValuePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ValuePreference, 0, 0);
        this.mLabels = obtainStyledAttributes.getString(0);
        this.mMaxEms = obtainStyledAttributes.getInt(1, -1);
        obtainStyledAttributes.recycle();
    }

    public ValuePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        TextView textView = (TextView) view.findViewById(16908304);
        if (this.mMaxEms != -1) {
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setMaxEms(this.mMaxEms);
            textView.setSingleLine();
        }
        super.onBindView(view);
    }

    public void setValue(String str) {
        if (!TextUtils.equals(str, this.mLabels)) {
            this.mLabels = str;
            setSummary(str);
            notifyChanged();
        }
    }
}
