package android.support.v4.media.subtitle;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v4.media.subtitle.SubtitleTrack;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;

@RequiresApi(28)
abstract class ClosedCaptionWidget extends ViewGroup implements SubtitleTrack.RenderingWidget {
    protected CaptioningManager.CaptionStyle mCaptionStyle;
    private final CaptioningManager.CaptioningChangeListener mCaptioningListener;
    protected ClosedCaptionLayout mClosedCaptionLayout;
    private boolean mHasChangeListener;
    protected SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final CaptioningManager mManager;

    interface ClosedCaptionLayout {
        void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle);

        void setFontScale(float f2);
    }

    ClosedCaptionWidget(Context context) {
        this(context, null);
    }

    ClosedCaptionWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    ClosedCaptionWidget(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    ClosedCaptionWidget(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCaptioningListener = new CaptioningManager.CaptioningChangeListener() {
            /* class android.support.v4.media.subtitle.ClosedCaptionWidget.AnonymousClass1 */

            public void onFontScaleChanged(float f2) {
                ClosedCaptionWidget.this.mClosedCaptionLayout.setFontScale(f2);
            }

            public void onUserStyleChanged(CaptioningManager.CaptionStyle captionStyle) {
                ClosedCaptionWidget closedCaptionWidget = ClosedCaptionWidget.this;
                closedCaptionWidget.mCaptionStyle = captionStyle;
                closedCaptionWidget.mClosedCaptionLayout.setCaptionStyle(closedCaptionWidget.mCaptionStyle);
            }
        };
        setLayerType(1, null);
        this.mManager = (CaptioningManager) context.getSystemService("captioning");
        this.mCaptionStyle = this.mManager.getUserStyle();
        this.mClosedCaptionLayout = createCaptionLayout(context);
        this.mClosedCaptionLayout.setCaptionStyle(this.mCaptionStyle);
        this.mClosedCaptionLayout.setFontScale(this.mManager.getFontScale());
        addView((ViewGroup) this.mClosedCaptionLayout, -1, -1);
        requestLayout();
    }

    private void manageChangeListener() {
        boolean z = isAttachedToWindow() && getVisibility() == 0;
        if (this.mHasChangeListener != z) {
            this.mHasChangeListener = z;
            if (z) {
                this.mManager.addCaptioningChangeListener(this.mCaptioningListener);
            } else {
                this.mManager.removeCaptioningChangeListener(this.mCaptioningListener);
            }
        }
    }

    public abstract ClosedCaptionLayout createCaptionLayout(Context context);

    @Override // android.support.v4.media.subtitle.SubtitleTrack.RenderingWidget
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        manageChangeListener();
    }

    @Override // android.support.v4.media.subtitle.SubtitleTrack.RenderingWidget
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        manageChangeListener();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ((ViewGroup) this.mClosedCaptionLayout).layout(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ((ViewGroup) this.mClosedCaptionLayout).measure(i, i2);
    }

    @Override // android.support.v4.media.subtitle.SubtitleTrack.RenderingWidget
    public void setOnChangedListener(SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener) {
        this.mListener = onChangedListener;
    }

    @Override // android.support.v4.media.subtitle.SubtitleTrack.RenderingWidget
    public void setSize(int i, int i2) {
        measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        layout(0, 0, i, i2);
    }

    @Override // android.support.v4.media.subtitle.SubtitleTrack.RenderingWidget
    public void setVisible(boolean z) {
        if (z) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        manageChangeListener();
    }
}
