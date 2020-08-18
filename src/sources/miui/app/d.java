package miui.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.app.AlertController;
import java.util.ArrayList;
import miui.app.c;

/* compiled from: AlertDialog */
public class d extends Dialog implements DialogInterface {
    public static final int ba = 2;
    public static final int ca = 3;
    public static final int da = 4;
    public static final int ea = 5;
    public static final int fa = 6;
    public static final int ga = 7;
    /* access modifiers changed from: private */
    public c mAlert;

    /* compiled from: AlertDialog */
    public static class a {
        private c.a P;
        private int mTheme;

        public a(Context context) {
            this(context, d.resolveDialogTheme(context, 0));
        }

        public a(Context context, int i) {
            this.P = new c.a(new ContextThemeWrapper(context, d.resolveDialogTheme(context, i)));
            this.P.Ti = i >= 4 && i <= 7;
            this.mTheme = i;
        }

        public a a(DialogInterface.OnClickListener onClickListener) {
            this.P.Qi = onClickListener;
            return this;
        }

        public a a(ListAdapter listAdapter, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mAdapter = listAdapter;
            ((AlertController.AlertParams) aVar).mIsMultiChoice = true;
            ((AlertController.AlertParams) aVar).mOnCheckboxClickListener = onMultiChoiceClickListener;
            return this;
        }

        public a a(CharSequence charSequence, int i, int i2) {
            c.a aVar = this.P;
            if (aVar.Si == null) {
                aVar.Si = new ArrayList<>();
            }
            this.P.Si.add(new c.a.C0023a(charSequence, i, i2));
            return this;
        }

        public a a(boolean z, CharSequence charSequence) {
            c.a aVar = this.P;
            aVar.Ui = z;
            aVar.Vi = charSequence;
            return this;
        }

        public a b(int i, int i2, int i3) {
            return a(((AlertController.AlertParams) this.P).mContext.getText(i), i2, i3);
        }

        public d create() {
            d dVar = new d(((AlertController.AlertParams) this.P).mContext, this.mTheme);
            this.P.apply(dVar.mAlert);
            dVar.setCancelable(((AlertController.AlertParams) this.P).mCancelable);
            if (((AlertController.AlertParams) this.P).mCancelable) {
                dVar.setCanceledOnTouchOutside(true);
            }
            dVar.setOnCancelListener(((AlertController.AlertParams) this.P).mOnCancelListener);
            dVar.setOnDismissListener(this.P.mOnDismissListener);
            dVar.setOnShowListener(this.P.Ri);
            DialogInterface.OnKeyListener onKeyListener = ((AlertController.AlertParams) this.P).mOnKeyListener;
            if (onKeyListener != null) {
                dVar.setOnKeyListener(onKeyListener);
            }
            return dVar;
        }

        public Context getContext() {
            return ((AlertController.AlertParams) this.P).mContext;
        }

        public a setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mAdapter = listAdapter;
            ((AlertController.AlertParams) aVar).mOnClickListener = onClickListener;
            return this;
        }

        public a setCancelable(boolean z) {
            ((AlertController.AlertParams) this.P).mCancelable = z;
            return this;
        }

        public a setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String str) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mCursor = cursor;
            ((AlertController.AlertParams) aVar).mLabelColumn = str;
            ((AlertController.AlertParams) aVar).mOnClickListener = onClickListener;
            return this;
        }

        public a setCustomTitle(View view) {
            ((AlertController.AlertParams) this.P).mCustomTitleView = view;
            return this;
        }

        public a setIcon(int i) {
            ((AlertController.AlertParams) this.P).mIconId = i;
            return this;
        }

        public a setIcon(Drawable drawable) {
            ((AlertController.AlertParams) this.P).mIcon = drawable;
            return this;
        }

        public a setIconAttribute(int i) {
            return this;
        }

        public a setItems(int i, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mItems = ((AlertController.AlertParams) aVar).mContext.getResources().getTextArray(i);
            ((AlertController.AlertParams) this.P).mOnClickListener = onClickListener;
            return this;
        }

        public a setItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mItems = charSequenceArr;
            ((AlertController.AlertParams) aVar).mOnClickListener = onClickListener;
            return this;
        }

        public a setMessage(int i) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mMessage = ((AlertController.AlertParams) aVar).mContext.getText(i);
            return this;
        }

        public a setMessage(CharSequence charSequence) {
            ((AlertController.AlertParams) this.P).mMessage = charSequence;
            return this;
        }

        public a setMultiChoiceItems(int i, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mItems = ((AlertController.AlertParams) aVar).mContext.getResources().getTextArray(i);
            c.a aVar2 = this.P;
            ((AlertController.AlertParams) aVar2).mOnCheckboxClickListener = onMultiChoiceClickListener;
            ((AlertController.AlertParams) aVar2).mCheckedItems = zArr;
            ((AlertController.AlertParams) aVar2).mIsMultiChoice = true;
            return this;
        }

        public a setMultiChoiceItems(Cursor cursor, String str, String str2, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mCursor = cursor;
            ((AlertController.AlertParams) aVar).mOnCheckboxClickListener = onMultiChoiceClickListener;
            ((AlertController.AlertParams) aVar).mIsCheckedColumn = str;
            ((AlertController.AlertParams) aVar).mLabelColumn = str2;
            ((AlertController.AlertParams) aVar).mIsMultiChoice = true;
            return this;
        }

        public a setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mItems = charSequenceArr;
            ((AlertController.AlertParams) aVar).mOnCheckboxClickListener = onMultiChoiceClickListener;
            ((AlertController.AlertParams) aVar).mCheckedItems = zArr;
            ((AlertController.AlertParams) aVar).mIsMultiChoice = true;
            return this;
        }

        public a setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mNegativeButtonText = ((AlertController.AlertParams) aVar).mContext.getText(i);
            ((AlertController.AlertParams) this.P).mNegativeButtonListener = onClickListener;
            return this;
        }

        public a setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mNegativeButtonText = charSequence;
            ((AlertController.AlertParams) aVar).mNegativeButtonListener = onClickListener;
            return this;
        }

        public a setNeutralButton(int i, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mNeutralButtonText = ((AlertController.AlertParams) aVar).mContext.getText(i);
            ((AlertController.AlertParams) this.P).mNeutralButtonListener = onClickListener;
            return this;
        }

        public a setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mNeutralButtonText = charSequence;
            ((AlertController.AlertParams) aVar).mNeutralButtonListener = onClickListener;
            return this;
        }

        public a setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            ((AlertController.AlertParams) this.P).mOnCancelListener = onCancelListener;
            return this;
        }

        public a setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public a setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
            ((AlertController.AlertParams) this.P).mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public a setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            ((AlertController.AlertParams) this.P).mOnKeyListener = onKeyListener;
            return this;
        }

        public a setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.P.Ri = onShowListener;
            return this;
        }

        public a setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mPositiveButtonText = ((AlertController.AlertParams) aVar).mContext.getText(i);
            ((AlertController.AlertParams) this.P).mPositiveButtonListener = onClickListener;
            return this;
        }

        public a setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mPositiveButtonText = charSequence;
            ((AlertController.AlertParams) aVar).mPositiveButtonListener = onClickListener;
            return this;
        }

        public a setSingleChoiceItems(int i, int i2, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mItems = ((AlertController.AlertParams) aVar).mContext.getResources().getTextArray(i);
            c.a aVar2 = this.P;
            ((AlertController.AlertParams) aVar2).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) aVar2).mCheckedItem = i2;
            ((AlertController.AlertParams) aVar2).mIsSingleChoice = true;
            return this;
        }

        public a setSingleChoiceItems(Cursor cursor, int i, String str, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mCursor = cursor;
            ((AlertController.AlertParams) aVar).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) aVar).mCheckedItem = i;
            ((AlertController.AlertParams) aVar).mLabelColumn = str;
            ((AlertController.AlertParams) aVar).mIsSingleChoice = true;
            return this;
        }

        public a setSingleChoiceItems(ListAdapter listAdapter, int i, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mAdapter = listAdapter;
            ((AlertController.AlertParams) aVar).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) aVar).mCheckedItem = i;
            ((AlertController.AlertParams) aVar).mIsSingleChoice = true;
            return this;
        }

        public a setSingleChoiceItems(CharSequence[] charSequenceArr, int i, DialogInterface.OnClickListener onClickListener) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mItems = charSequenceArr;
            ((AlertController.AlertParams) aVar).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) aVar).mCheckedItem = i;
            ((AlertController.AlertParams) aVar).mIsSingleChoice = true;
            return this;
        }

        public a setTitle(int i) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mTitle = ((AlertController.AlertParams) aVar).mContext.getText(i);
            return this;
        }

        public a setTitle(CharSequence charSequence) {
            ((AlertController.AlertParams) this.P).mTitle = charSequence;
            return this;
        }

        public a setView(int i) {
            c.a aVar = this.P;
            ((AlertController.AlertParams) aVar).mView = ((AlertController.AlertParams) aVar).mInflater.inflate(i, (ViewGroup) null);
            return this;
        }

        public a setView(View view) {
            ((AlertController.AlertParams) this.P).mView = view;
            return this;
        }

        public d show() {
            d create = create();
            create.show();
            return create;
        }
    }

    protected d(Context context) {
        this(context, resolveDialogTheme(context, 0));
    }

    protected d(Context context, int i) {
        super(context, resolveDialogTheme(context, i));
        this.mAlert = new c(context, this, getWindow());
    }

    static int resolveDialogTheme(Context context, int i) {
        if (i == 2) {
            return 16974857;
        }
        if (i == 3) {
            return 16974864;
        }
        if (i >= 16777216) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    public Button getButton(int i) {
        return this.mAlert.getButton(i);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    public boolean[] ia() {
        return this.mAlert.ia();
    }

    public boolean isChecked() {
        return this.mAlert.isChecked();
    }

    public TextView ja() {
        return this.mAlert.ja();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mAlert.onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mAlert.onKeyUp(i, keyEvent) || super.onKeyUp(i, keyEvent);
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null);
    }

    public void setButton(int i, CharSequence charSequence, Message message) {
        this.mAlert.setButton(i, charSequence, null, message);
    }

    public void setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setIcon(int i) {
        this.mAlert.setIcon(i);
    }

    public void setIcon(Drawable drawable) {
        this.mAlert.setIcon(drawable);
    }

    public void setIconAttribute(int i) {
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mAlert.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }
}
