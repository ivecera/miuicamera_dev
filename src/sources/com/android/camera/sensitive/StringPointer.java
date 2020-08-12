package com.android.camera.sensitive;

import android.os.Parcel;
import android.os.Parcelable;

public class StringPointer implements Parcelable, CharSequence, Comparable<StringPointer> {
    public static final Parcelable.Creator<StringPointer> CREATOR = new Parcelable.Creator<StringPointer>() {
        /* class com.android.camera.sensitive.StringPointer.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public StringPointer createFromParcel(Parcel parcel) {
            return new StringPointer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public StringPointer[] newArray(int i) {
            return new StringPointer[i];
        }
    };
    private int hash = 0;
    protected final int length;
    protected final int offset;
    protected final char[] value;

    protected StringPointer(Parcel parcel) {
        this.value = parcel.createCharArray();
        this.offset = parcel.readInt();
        this.length = parcel.readInt();
        this.hash = parcel.readInt();
    }

    public StringPointer(String str) {
        this.value = str.toCharArray();
        this.offset = 0;
        this.length = this.value.length;
    }

    public StringPointer(char[] cArr, int i, int i2) {
        this.value = cArr;
        this.offset = i;
        this.length = i2;
    }

    public char charAt(int i) {
        return this.value[this.offset + i];
    }

    public int compareTo(StringPointer stringPointer) {
        int i = this.length;
        int i2 = stringPointer.length;
        int min = Math.min(i, i2);
        char[] cArr = this.value;
        char[] cArr2 = stringPointer.value;
        for (int i3 = 0; i3 < min; i3++) {
            char c2 = cArr[this.offset + i3];
            char c3 = cArr2[stringPointer.offset + i3];
            if (c2 != c3) {
                return c2 - c3;
            }
        }
        return i - i2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StringPointer) {
            StringPointer stringPointer = (StringPointer) obj;
            if (this.length == stringPointer.length) {
                char[] cArr = this.value;
                char[] cArr2 = stringPointer.value;
                for (int i = 0; i < this.length; i++) {
                    if (cArr[this.offset + i] != cArr2[stringPointer.offset + i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void fill(int i, int i2, char c2) {
        while (i < i2) {
            this.value[this.offset + i] = c2;
            i++;
        }
    }

    public int hashCode() {
        int i = this.hash;
        if (i == 0 && this.length > 0) {
            for (int i2 = 0; i2 < this.length; i2++) {
                i = (i * 31) + this.value[this.offset + i2];
            }
            this.hash = i;
        }
        return i;
    }

    public int length() {
        return this.length;
    }

    public boolean nextStartsWith(int i, StringPointer stringPointer) {
        int i2 = stringPointer.length;
        if (i2 > this.length - i) {
            return false;
        }
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (this.value[this.offset + i + i3] != stringPointer.value[stringPointer.offset + i3]) {
                return false;
            }
        }
        return true;
    }

    public int nextTwoCharHash(int i) {
        char[] cArr = this.value;
        int i2 = this.offset;
        return (cArr[i2 + i] * 31) + cArr[i2 + i + 1];
    }

    public int nextTwoCharMix(int i) {
        char[] cArr = this.value;
        int i2 = this.offset;
        return cArr[i2 + i + 1] | (cArr[i2 + i] << 16);
    }

    public CharSequence subSequence(int i, int i2) {
        return substring(i, i2);
    }

    public StringPointer substring(int i) {
        return new StringPointer(this.value, this.offset + i, this.length - i);
    }

    public StringPointer substring(int i, int i2) {
        return new StringPointer(this.value, this.offset + i, i2 - i);
    }

    public String toString() {
        return new String(this.value, this.offset, this.length);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharArray(this.value);
        parcel.writeInt(this.offset);
        parcel.writeInt(this.length);
        parcel.writeInt(this.hash);
    }
}
