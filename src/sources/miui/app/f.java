package miui.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.R;
import java.text.NumberFormat;

/* compiled from: ProgressDialog */
public class f extends d {
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    /* access modifiers changed from: private */
    public TextView ha;
    private int ia = 0;
    private String ja;
    /* access modifiers changed from: private */
    public NumberFormat ka;
    private int la;
    /* access modifiers changed from: private */
    public CharSequence mMessage;
    /* access modifiers changed from: private */
    public TextView mMessageView;
    /* access modifiers changed from: private */
    public ProgressBar mProgress;
    private int ma;
    private int na;
    private int oa;
    private int pa;
    private Drawable qa;
    private Drawable ra;
    private boolean ta;
    private boolean ua;
    private Handler va;

    public f(Context context) {
        super(context);
        Dm();
    }

    public f(Context context, int i) {
        super(context, i);
        Dm();
    }

    private void Dm() {
        this.ja = "%1d/%2d";
        this.ka = NumberFormat.getPercentInstance();
        this.ka.setMaximumFractionDigits(0);
    }

    private void Em() {
        Handler handler;
        if (this.ia == 1 && (handler = this.va) != null && !handler.hasMessages(0)) {
            this.va.sendEmptyMessage(0);
        }
    }

    public static f show(Context context, CharSequence charSequence, CharSequence charSequence2) {
        return show(context, charSequence, charSequence2, false);
    }

    public static f show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return show(context, charSequence, charSequence2, z, false, null);
    }

    public static f show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2) {
        return show(context, charSequence, charSequence2, z, z2, null);
    }

    public static f show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2, DialogInterface.OnCancelListener onCancelListener) {
        f fVar = new f(context);
        fVar.setTitle(charSequence);
        fVar.setMessage(charSequence2);
        fVar.setIndeterminate(z);
        fVar.setCancelable(z2);
        fVar.setOnCancelListener(onCancelListener);
        fVar.show();
        return fVar;
    }

    public int getMax() {
        ProgressBar progressBar = this.mProgress;
        return progressBar != null ? progressBar.getMax() : this.la;
    }

    public int getProgress() {
        ProgressBar progressBar = this.mProgress;
        return progressBar != null ? progressBar.getProgress() : this.ma;
    }

    public int getSecondaryProgress() {
        ProgressBar progressBar = this.mProgress;
        return progressBar != null ? progressBar.getSecondaryProgress() : this.na;
    }

    public void incrementProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementProgressBy(i);
            Em();
            return;
        }
        this.oa += i;
    }

    public void incrementSecondaryProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementSecondaryProgressBy(i);
            Em();
            return;
        }
        this.pa += i;
    }

    public boolean isIndeterminate() {
        ProgressBar progressBar = this.mProgress;
        return progressBar != null ? progressBar.isIndeterminate() : this.ta;
    }

    /* access modifiers changed from: protected */
    @Override // miui.app.d
    public void onCreate(Bundle bundle) {
        View view;
        LayoutInflater from = LayoutInflater.from(getContext());
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        if (this.ia == 1) {
            this.va = new e(this);
            view = from.inflate(obtainStyledAttributes.getResourceId(13, 17367086), (ViewGroup) null);
            this.ha = (TextView) view.findViewById(16909264);
        } else {
            view = from.inflate(obtainStyledAttributes.getResourceId(18, 17367253), (ViewGroup) null);
        }
        this.mProgress = (ProgressBar) view.findViewById(16908301);
        this.mMessageView = (TextView) view.findViewById(16908299);
        setView(view);
        obtainStyledAttributes.recycle();
        int i = this.la;
        if (i > 0) {
            setMax(i);
        }
        int i2 = this.ma;
        if (i2 > 0) {
            setProgress(i2);
        }
        int i3 = this.na;
        if (i3 > 0) {
            setSecondaryProgress(i3);
        }
        int i4 = this.oa;
        if (i4 > 0) {
            incrementProgressBy(i4);
        }
        int i5 = this.pa;
        if (i5 > 0) {
            incrementSecondaryProgressBy(i5);
        }
        Drawable drawable = this.qa;
        if (drawable != null) {
            setProgressDrawable(drawable);
        }
        Drawable drawable2 = this.ra;
        if (drawable2 != null) {
            setIndeterminateDrawable(drawable2);
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            setMessage(charSequence);
        }
        setIndeterminate(this.ta);
        Em();
        super.onCreate(bundle);
    }

    public void onStart() {
        super.onStart();
        this.ua = true;
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.ua = false;
    }

    public void setIndeterminate(boolean z) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminate(z);
        } else {
            this.ta = z;
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(drawable);
        } else {
            this.ra = drawable;
        }
    }

    public void setMax(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setMax(i);
            Em();
            return;
        }
        this.la = i;
    }

    @Override // miui.app.d
    public void setMessage(CharSequence charSequence) {
        if (this.mProgress != null) {
            if (this.ia == 1) {
                this.mMessage = charSequence;
            }
            this.mMessageView.setText(charSequence);
            return;
        }
        this.mMessage = charSequence;
    }

    public void setProgress(int i) {
        if (this.ua) {
            this.mProgress.setProgress(i);
            Em();
            return;
        }
        this.ma = i;
    }

    public void setProgressDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setProgressDrawable(drawable);
        } else {
            this.qa = drawable;
        }
    }

    public void setProgressNumberFormat(String str) {
        this.ja = str;
        Em();
    }

    public void setProgressPercentFormat(NumberFormat numberFormat) {
        this.ka = numberFormat;
        Em();
    }

    public void setProgressStyle(int i) {
        this.ia = i;
    }

    public void setSecondaryProgress(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setSecondaryProgress(i);
            Em();
            return;
        }
        this.na = i;
    }
}
