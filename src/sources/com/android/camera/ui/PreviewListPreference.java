package com.android.camera.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.List;

public class PreviewListPreference extends ListPreference {
    private CharSequence[] mDefaultValues;
    private CharSequence[] mLabels;

    public PreviewListPreference(Context context) {
        this(context, null);
    }

    public PreviewListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        CharSequence[] charSequenceArr = this.mDefaultValues;
        if (charSequenceArr != null) {
            setDefaultValue(findSupportedDefaultValue(charSequenceArr));
        }
    }

    private CharSequence findSupportedDefaultValue(CharSequence[] charSequenceArr) {
        CharSequence[] entryValues = getEntryValues();
        if (entryValues == null) {
            return null;
        }
        for (CharSequence charSequence : entryValues) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (charSequence != null && charSequence.equals(charSequence2)) {
                    return charSequence2;
                }
            }
        }
        return null;
    }

    public void filterUnsupported(List<String> list) {
        CharSequence[] entries = getEntries();
        CharSequence[] entryValues = getEntryValues();
        CharSequence[] charSequenceArr = this.mLabels;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int length = entries.length;
        for (int i = 0; i < length; i++) {
            if (list.indexOf(entryValues[i].toString()) >= 0) {
                arrayList.add(entries[i]);
                arrayList2.add(entryValues[i]);
                if (charSequenceArr != null) {
                    arrayList3.add(charSequenceArr[i]);
                }
            }
        }
        int size = arrayList.size();
        setEntries((CharSequence[]) arrayList.toArray(new CharSequence[size]));
        setEntryValues((CharSequence[]) arrayList2.toArray(new CharSequence[size]));
        if (this.mLabels != null) {
            this.mLabels = (CharSequence[]) arrayList3.toArray(new CharSequence[size]);
        }
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue != null && peekValue.type == 1) {
            this.mDefaultValues = typedArray.getTextArray(i);
        }
        CharSequence[] charSequenceArr = this.mDefaultValues;
        return charSequenceArr != null ? charSequenceArr[0] : typedArray.getString(i);
    }

    @Override // android.preference.ListPreference
    public void setEntryValues(CharSequence[] charSequenceArr) {
        super.setEntryValues(charSequenceArr);
        CharSequence[] charSequenceArr2 = this.mDefaultValues;
        if (charSequenceArr2 != null) {
            setDefaultValue(findSupportedDefaultValue(charSequenceArr2));
        }
    }

    public void setShowArrow(boolean z) {
    }

    public void setValue(String str) {
        super.setValue(str);
        setSummary(getEntry());
    }
}
