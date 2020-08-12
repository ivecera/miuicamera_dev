package miui.app;

import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import com.android.camera.R;

/* compiled from: ProgressDialog */
class e extends Handler {
    final /* synthetic */ f this$0;

    e(f fVar) {
        this.this$0 = fVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        this.this$0.mMessageView.setText(this.this$0.mMessage);
        if (this.this$0.ka != null && this.this$0.ha != null) {
            int progress = this.this$0.mProgress.getProgress();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            String format = this.this$0.ka.format(((double) progress) / ((double) this.this$0.mProgress.getMax()));
            spannableStringBuilder.append((CharSequence) format);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(this.this$0.getContext().getResources().getColor(R.color.progress_percent_color)), 0, format.length(), 34);
            this.this$0.ha.setText(spannableStringBuilder);
        }
    }
}
