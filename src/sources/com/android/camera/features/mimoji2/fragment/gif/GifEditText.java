package com.android.camera.features.mimoji2.fragment.gif;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import com.android.camera.R;
import com.android.camera.Util;

public class GifEditText extends EditText {
    private Context mContext;
    /* access modifiers changed from: private */
    public boolean mIsOverLimit;
    /* access modifiers changed from: private */
    public ItfTextChanged mItfTextChanged;

    /* access modifiers changed from: package-private */
    public interface ItfTextChanged {
        void afterTextChanged(TextJudge textJudge);
    }

    class TextJudge {
        private boolean isSingleLine = true;
        /* access modifiers changed from: private */
        public StringBuilder textNative = new StringBuilder(32);
        /* access modifiers changed from: private */
        public StringBuilder textShow = new StringBuilder(32);

        TextJudge() {
        }

        public String getTextNative() {
            return this.textNative.toString();
        }

        public String getTextShow() {
            return this.textShow.toString();
        }

        public boolean isSingleLine() {
            return this.isSingleLine;
        }

        public void setSingleLine(boolean z) {
            this.isSingleLine = z;
        }
    }

    public GifEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditTextParam();
    }

    public GifEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initEditTextParam();
    }

    public GifEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initEditTextParam();
    }

    private void initEditTextParam() {
        setSingleLine(false);
        setInputType(131072);
        setBackgroundColor(getResources().getColor(R.color.transparent));
        setTypeface(Util.getFZMiaoWuJWTypeface(this.mContext));
        setMaxLines(2);
        setIncludeFontPadding(false);
        setHorizontallyScrolling(false);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        addTextChangedListener(new TextWatcher() {
            /* class com.android.camera.features.mimoji2.fragment.gif.GifEditText.AnonymousClass1 */

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TextJudge maxGifHeight = GifEditText.this.getMaxGifHeight();
                if (GifEditText.this.mItfTextChanged != null) {
                    GifEditText.this.mItfTextChanged.afterTextChanged(maxGifHeight);
                }
                if (GifEditText.this.mIsOverLimit) {
                    boolean unused = GifEditText.this.mIsOverLimit = false;
                    GifEditText.this.setText(maxGifHeight.getTextShow());
                    GifEditText.this.setSelection(GifEditText.this.getText().length());
                }
            }
        });
    }

    public void addTextChangedListener(ItfTextChanged itfTextChanged) {
        this.mItfTextChanged = itfTextChanged;
    }

    public TextJudge getMaxGifHeight() {
        Layout layout = getLayout();
        String obj = getText().toString();
        TextJudge textJudge = new TextJudge();
        if (getLineCount() > 2) {
            this.mIsOverLimit = true;
        }
        int i = 0;
        int i2 = 0;
        while (i < getLineCount() && i <= 1) {
            int lineEnd = layout.getLineEnd(i);
            String substring = obj.substring(i2, lineEnd);
            textJudge.textShow.append(substring);
            if (i == 1) {
                textJudge.setSingleLine(false);
                textJudge.textNative.append("\n");
            }
            textJudge.textNative.append(substring);
            i++;
            i2 = lineEnd;
        }
        return textJudge;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }
}
