package android.support.v4.media.subtitle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaFormat;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.mediacompat.R;
import android.support.v4.media.SubtitleData2;
import android.support.v4.media.subtitle.Cea608CCParser;
import android.support.v4.media.subtitle.ClosedCaptionWidget;
import android.support.v4.media.subtitle.SubtitleController;
import android.support.v4.media.subtitle.SubtitleTrack;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

@RequiresApi(28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ClosedCaptionRenderer extends SubtitleController.Renderer {
    private Cea608CCWidget mCCWidget;
    private final Context mContext;

    class Cea608CCWidget extends ClosedCaptionWidget implements Cea608CCParser.DisplayListener {
        private static final String DUMMY_TEXT = "1234567890123456789012345678901234";
        /* access modifiers changed from: private */
        public final Rect mTextBounds;

        private class CCLayout extends LinearLayout implements ClosedCaptionWidget.ClosedCaptionLayout {
            private static final int MAX_ROWS = 15;
            private static final float SAFE_AREA_RATIO = 0.9f;
            private final CCLineBox[] mLineBoxes = new CCLineBox[15];

            CCLayout(Context context) {
                super(context);
                setGravity(GravityCompat.START);
                setOrientation(1);
                for (int i = 0; i < 15; i++) {
                    this.mLineBoxes[i] = new CCLineBox(getContext());
                    addView(this.mLineBoxes[i], -2, -2);
                }
            }

            /* access modifiers changed from: protected */
            public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int i5;
                int i6;
                int i7 = i3 - i;
                int i8 = i4 - i2;
                int i9 = i7 * 3;
                int i10 = i8 * 4;
                if (i9 >= i10) {
                    i6 = i10 / 3;
                    i5 = i8;
                } else {
                    i5 = i9 / 4;
                    i6 = i7;
                }
                int i11 = (int) (((float) i6) * 0.9f);
                int i12 = (int) (((float) i5) * 0.9f);
                int i13 = (i7 - i11) / 2;
                int i14 = (i8 - i12) / 2;
                int i15 = 0;
                while (i15 < 15) {
                    i15++;
                    this.mLineBoxes[i15].layout(i13, ((i12 * i15) / 15) + i14, i13 + i11, ((i12 * i15) / 15) + i14);
                }
            }

            /* access modifiers changed from: protected */
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                int i3 = measuredWidth * 3;
                int i4 = measuredHeight * 4;
                if (i3 >= i4) {
                    measuredWidth = i4 / 3;
                } else {
                    measuredHeight = i3 / 4;
                }
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(((int) (((float) measuredHeight) * 0.9f)) / 15, 1073741824);
                int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((int) (((float) measuredWidth) * 0.9f), 1073741824);
                for (int i5 = 0; i5 < 15; i5++) {
                    this.mLineBoxes[i5].measure(makeMeasureSpec2, makeMeasureSpec);
                }
            }

            @Override // android.support.v4.media.subtitle.ClosedCaptionWidget.ClosedCaptionLayout
            public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
                for (int i = 0; i < 15; i++) {
                    this.mLineBoxes[i].setCaptionStyle(captionStyle);
                }
            }

            @Override // android.support.v4.media.subtitle.ClosedCaptionWidget.ClosedCaptionLayout
            public void setFontScale(float f2) {
            }

            /* access modifiers changed from: package-private */
            public void update(SpannableStringBuilder[] spannableStringBuilderArr) {
                for (int i = 0; i < 15; i++) {
                    if (spannableStringBuilderArr[i] != null) {
                        this.mLineBoxes[i].setText(spannableStringBuilderArr[i], TextView.BufferType.SPANNABLE);
                        this.mLineBoxes[i].setVisibility(0);
                    } else {
                        this.mLineBoxes[i].setVisibility(4);
                    }
                }
            }
        }

        private class CCLineBox extends TextView {
            private static final float EDGE_OUTLINE_RATIO = 0.1f;
            private static final float EDGE_SHADOW_RATIO = 0.05f;
            private static final float FONT_PADDING_RATIO = 0.75f;
            private int mBgColor = ViewCompat.MEASURED_STATE_MASK;
            private int mEdgeColor = 0;
            private int mEdgeType = 0;
            private float mOutlineWidth;
            private float mShadowOffset;
            private float mShadowRadius;
            private int mTextColor = -1;

            CCLineBox(Context context) {
                super(context);
                setGravity(17);
                setBackgroundColor(0);
                setTextColor(-1);
                setTypeface(Typeface.MONOSPACE);
                setVisibility(4);
                Resources resources = getContext().getResources();
                this.mOutlineWidth = (float) resources.getDimensionPixelSize(R.dimen.subtitle_outline_width);
                this.mShadowRadius = (float) resources.getDimensionPixelSize(R.dimen.subtitle_shadow_radius);
                this.mShadowOffset = (float) resources.getDimensionPixelSize(R.dimen.subtitle_shadow_offset);
            }

            private void drawEdgeOutline(Canvas canvas) {
                TextPaint paint = getPaint();
                Paint.Style style = paint.getStyle();
                Paint.Join strokeJoin = paint.getStrokeJoin();
                float strokeWidth = paint.getStrokeWidth();
                setTextColor(this.mEdgeColor);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setStrokeWidth(this.mOutlineWidth);
                super.onDraw(canvas);
                setTextColor(this.mTextColor);
                paint.setStyle(style);
                paint.setStrokeJoin(strokeJoin);
                paint.setStrokeWidth(strokeWidth);
                setBackgroundSpans(0);
                super.onDraw(canvas);
                setBackgroundSpans(this.mBgColor);
            }

            private void drawEdgeRaisedOrDepressed(Canvas canvas) {
                TextPaint paint = getPaint();
                Paint.Style style = paint.getStyle();
                paint.setStyle(Paint.Style.FILL);
                boolean z = this.mEdgeType == 3;
                int i = -1;
                int i2 = z ? -1 : this.mEdgeColor;
                if (z) {
                    i = this.mEdgeColor;
                }
                float f2 = this.mShadowRadius;
                float f3 = f2 / 2.0f;
                float f4 = -f3;
                setShadowLayer(f2, f4, f4, i2);
                super.onDraw(canvas);
                setBackgroundSpans(0);
                setShadowLayer(this.mShadowRadius, f3, f3, i);
                super.onDraw(canvas);
                paint.setStyle(style);
                setBackgroundSpans(this.mBgColor);
            }

            private void setBackgroundSpans(int i) {
                Cea608CCParser.MutableBackgroundColorSpan[] mutableBackgroundColorSpanArr;
                CharSequence text = getText();
                if (text instanceof Spannable) {
                    Spannable spannable = (Spannable) text;
                    for (Cea608CCParser.MutableBackgroundColorSpan mutableBackgroundColorSpan : (Cea608CCParser.MutableBackgroundColorSpan[]) spannable.getSpans(0, spannable.length(), Cea608CCParser.MutableBackgroundColorSpan.class)) {
                        mutableBackgroundColorSpan.setBackgroundColor(i);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onDraw(Canvas canvas) {
                int i = this.mEdgeType;
                if (i == -1 || i == 0 || i == 2) {
                    super.onDraw(canvas);
                } else if (i == 1) {
                    drawEdgeOutline(canvas);
                } else {
                    drawEdgeRaisedOrDepressed(canvas);
                }
            }

            /* access modifiers changed from: protected */
            public void onMeasure(int i, int i2) {
                float size = ((float) View.MeasureSpec.getSize(i2)) * FONT_PADDING_RATIO;
                setTextSize(0, size);
                this.mOutlineWidth = (0.1f * size) + 1.0f;
                this.mShadowRadius = (size * EDGE_SHADOW_RATIO) + 1.0f;
                this.mShadowOffset = this.mShadowRadius;
                setScaleX(1.0f);
                getPaint().getTextBounds(Cea608CCWidget.DUMMY_TEXT, 0, 34, Cea608CCWidget.this.mTextBounds);
                setScaleX(((float) View.MeasureSpec.getSize(i)) / ((float) Cea608CCWidget.this.mTextBounds.width()));
                super.onMeasure(i, i2);
            }

            /* access modifiers changed from: package-private */
            public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
                this.mTextColor = captionStyle.foregroundColor;
                this.mBgColor = captionStyle.backgroundColor;
                this.mEdgeType = captionStyle.edgeType;
                this.mEdgeColor = captionStyle.edgeColor;
                setTextColor(this.mTextColor);
                if (this.mEdgeType == 2) {
                    float f2 = this.mShadowRadius;
                    float f3 = this.mShadowOffset;
                    setShadowLayer(f2, f3, f3, this.mEdgeColor);
                } else {
                    setShadowLayer(0.0f, 0.0f, 0.0f, 0);
                }
                invalidate();
            }
        }

        Cea608CCWidget(ClosedCaptionRenderer closedCaptionRenderer, Context context) {
            this(closedCaptionRenderer, context, null);
        }

        Cea608CCWidget(ClosedCaptionRenderer closedCaptionRenderer, Context context, AttributeSet attributeSet) {
            this(closedCaptionRenderer, context, attributeSet, 0);
        }

        Cea608CCWidget(ClosedCaptionRenderer closedCaptionRenderer, Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        Cea608CCWidget(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mTextBounds = new Rect();
        }

        @Override // android.support.v4.media.subtitle.ClosedCaptionWidget
        public ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(Context context) {
            return new CCLayout(context);
        }

        @Override // android.support.v4.media.subtitle.Cea608CCParser.DisplayListener
        public CaptioningManager.CaptionStyle getCaptionStyle() {
            return ((ClosedCaptionWidget) this).mCaptionStyle;
        }

        @Override // android.support.v4.media.subtitle.Cea608CCParser.DisplayListener
        public void onDisplayChanged(SpannableStringBuilder[] spannableStringBuilderArr) {
            ((CCLayout) ((ClosedCaptionWidget) this).mClosedCaptionLayout).update(spannableStringBuilderArr);
            SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener = ((ClosedCaptionWidget) this).mListener;
            if (onChangedListener != null) {
                onChangedListener.onChanged(this);
            }
        }
    }

    static class Cea608CaptionTrack extends SubtitleTrack {
        private final Cea608CCParser mCCParser = new Cea608CCParser(this.mRenderingWidget);
        private final Cea608CCWidget mRenderingWidget;

        Cea608CaptionTrack(Cea608CCWidget cea608CCWidget, MediaFormat mediaFormat) {
            super(mediaFormat);
            this.mRenderingWidget = cea608CCWidget;
        }

        @Override // android.support.v4.media.subtitle.SubtitleTrack
        public SubtitleTrack.RenderingWidget getRenderingWidget() {
            return this.mRenderingWidget;
        }

        @Override // android.support.v4.media.subtitle.SubtitleTrack
        public void onData(byte[] bArr, boolean z, long j) {
            this.mCCParser.parse(bArr);
        }

        @Override // android.support.v4.media.subtitle.SubtitleTrack
        public void updateView(ArrayList<SubtitleTrack.Cue> arrayList) {
        }
    }

    public ClosedCaptionRenderer(Context context) {
        this.mContext = context;
    }

    @Override // android.support.v4.media.subtitle.SubtitleController.Renderer
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (SubtitleData2.MIMETYPE_TEXT_CEA_608.equals(mediaFormat.getString("mime"))) {
            if (this.mCCWidget == null) {
                this.mCCWidget = new Cea608CCWidget(this, this.mContext);
            }
            return new Cea608CaptionTrack(this.mCCWidget, mediaFormat);
        }
        throw new RuntimeException("No matching format: " + mediaFormat.toString());
    }

    @Override // android.support.v4.media.subtitle.SubtitleController.Renderer
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return SubtitleData2.MIMETYPE_TEXT_CEA_608.equals(mediaFormat.getString("mime"));
        }
        return false;
    }
}
