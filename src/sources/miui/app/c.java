package miui.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.android.internal.app.AlertController;
import java.util.ArrayList;

/* compiled from: AlertControllerWrapper */
public class c extends AlertController {
    AlertController Wi;

    /* compiled from: AlertControllerWrapper */
    public static class a extends AlertController.AlertParams {
        public DialogInterface.OnClickListener Qi;
        public DialogInterface.OnShowListener Ri;
        public ArrayList<C0023a> Si;
        public boolean Ti;
        public boolean Ui;
        public CharSequence Vi;
        public DialogInterface.OnDismissListener mOnDismissListener;

        /* renamed from: miui.app.c$a$a  reason: collision with other inner class name */
        /* compiled from: AlertControllerWrapper */
        public static class C0023a {
            public int icon;
            public int id;
            public CharSequence label;

            public C0023a(CharSequence charSequence, int i, int i2) {
                this.label = charSequence;
                this.icon = i;
                this.id = i2;
            }
        }

        public a(Context context) {
            super(context);
        }

        private ListAdapter T(int i) {
            Cursor cursor = ((AlertController.AlertParams) this).mCursor;
            if (cursor == null) {
                ListAdapter listAdapter = ((AlertController.AlertParams) this).mAdapter;
                return listAdapter != null ? listAdapter : new ArrayAdapter(((AlertController.AlertParams) this).mContext, i, 16908308, ((AlertController.AlertParams) this).mItems);
            }
            return new SimpleCursorAdapter(((AlertController.AlertParams) this).mContext, i, cursor, new String[]{((AlertController.AlertParams) this).mLabelColumn}, new int[]{16908308});
        }

        private ListAdapter a(ListView listView, int i) {
            ListAdapter listAdapter;
            Cursor cursor = ((AlertController.AlertParams) this).mCursor;
            return cursor == null ? (!this.Ti || (listAdapter = ((AlertController.AlertParams) this).mAdapter) == null) ? new a(this, ((AlertController.AlertParams) this).mContext, i, 16908308, ((AlertController.AlertParams) this).mItems, listView) : listAdapter : new b(this, ((AlertController.AlertParams) this).mContext, cursor, false, listView, i);
        }

        private void a(AlertController alertController) {
        }

        public void apply(AlertController alertController) {
            View view = ((AlertController.AlertParams) this).mCustomTitleView;
            if (view != null) {
                alertController.setCustomTitle(view);
            } else {
                CharSequence charSequence = ((AlertController.AlertParams) this).mTitle;
                if (charSequence != null) {
                    alertController.setTitle(charSequence);
                }
            }
            Drawable drawable = ((AlertController.AlertParams) this).mIcon;
            if (drawable != null) {
                alertController.setIcon(drawable);
            }
            int i = ((AlertController.AlertParams) this).mIconId;
            if (i != 0) {
                alertController.setIcon(i);
            }
            CharSequence charSequence2 = ((AlertController.AlertParams) this).mMessage;
            if (charSequence2 != null) {
                alertController.setMessage(charSequence2);
            }
            CharSequence charSequence3 = ((AlertController.AlertParams) this).mPositiveButtonText;
            if (charSequence3 != null) {
                alertController.setButton(-1, charSequence3, ((AlertController.AlertParams) this).mPositiveButtonListener, (Message) null);
            }
            CharSequence charSequence4 = ((AlertController.AlertParams) this).mNegativeButtonText;
            if (charSequence4 != null) {
                alertController.setButton(-2, charSequence4, ((AlertController.AlertParams) this).mNegativeButtonListener, (Message) null);
            }
            CharSequence charSequence5 = ((AlertController.AlertParams) this).mNeutralButtonText;
            if (charSequence5 != null) {
                alertController.setButton(-3, charSequence5, ((AlertController.AlertParams) this).mNeutralButtonListener, (Message) null);
            }
            if (!(((AlertController.AlertParams) this).mItems == null && ((AlertController.AlertParams) this).mCursor == null && ((AlertController.AlertParams) this).mAdapter == null)) {
                a(alertController);
            }
            View view2 = ((AlertController.AlertParams) this).mView;
            if (view2 != null) {
                alertController.setView(view2);
            }
        }
    }

    public c(Context context, DialogInterface dialogInterface, Window window) {
        super(context, dialogInterface, window);
        this.Wi = AlertController.create(context, dialogInterface, window);
    }

    public void a(boolean z, CharSequence charSequence) {
    }

    public Button getButton(int i) {
        return this.Wi.getButton(i);
    }

    public ListView getListView() {
        return this.Wi.getListView();
    }

    public boolean[] ia() {
        return new boolean[0];
    }

    public void installContent() {
        this.Wi.installContent();
    }

    public boolean isChecked() {
        return false;
    }

    public TextView ja() {
        return null;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.Wi.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.Wi.onKeyUp(i, keyEvent);
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        this.Wi.setButton(i, charSequence, onClickListener, message);
    }

    public void setCustomTitle(View view) {
        this.Wi.setCustomTitle(view);
    }

    public void setIcon(int i) {
        this.Wi.setIcon(i);
    }

    public void setIcon(Drawable drawable) {
        this.Wi.setIcon(drawable);
    }

    public void setInverseBackgroundForced(boolean z) {
    }

    public void setMessage(CharSequence charSequence) {
        this.Wi.setMessage(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        this.Wi.setTitle(charSequence);
    }

    public void setView(View view) {
        this.Wi.setView(view);
    }
}
