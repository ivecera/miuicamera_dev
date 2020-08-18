package miui.app;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import com.android.internal.app.AlertController;
import miui.app.c;

/* compiled from: AlertControllerWrapper */
class b extends CursorAdapter {
    final /* synthetic */ ListView Uh;
    private final int Vh;
    private final int Wh;
    final /* synthetic */ c.a this$0;
    final /* synthetic */ int val$layout;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    b(c.a aVar, Context context, Cursor cursor, boolean z, ListView listView, int i) {
        super(context, cursor, z);
        this.this$0 = aVar;
        this.Uh = listView;
        this.val$layout = i;
        Cursor cursor2 = getCursor();
        this.Vh = cursor2.getColumnIndexOrThrow(((AlertController.AlertParams) this.this$0).mLabelColumn);
        this.Wh = cursor2.getColumnIndexOrThrow(((AlertController.AlertParams) this.this$0).mIsCheckedColumn);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ((CheckedTextView) view.findViewById(16908308)).setText(cursor.getString(this.Vh));
        if (!this.this$0.Ti) {
            ListView listView = this.Uh;
            int position = cursor.getPosition();
            int i = cursor.getInt(this.Wh);
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            listView.setItemChecked(position, z);
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return ((AlertController.AlertParams) this.this$0).mInflater.inflate(this.val$layout, (ViewGroup) null);
    }
}
