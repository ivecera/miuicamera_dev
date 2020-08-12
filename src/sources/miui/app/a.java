package miui.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.internal.app.AlertController;
import miui.app.c;

/* compiled from: AlertControllerWrapper */
class a extends ArrayAdapter<CharSequence> {
    final /* synthetic */ ListView Uh;
    final /* synthetic */ c.a this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    a(c.a aVar, Context context, int i, int i2, CharSequence[] charSequenceArr, ListView listView) {
        super(context, i, i2, charSequenceArr);
        this.this$0 = aVar;
        this.Uh = listView;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        boolean[] zArr;
        View view2 = super.getView(i, view, viewGroup);
        c.a aVar = this.this$0;
        if (!aVar.Ti && (zArr = ((AlertController.AlertParams) aVar).mCheckedItems) != null && zArr[i]) {
            this.Uh.setItemChecked(i, true);
        }
        return view2;
    }
}
