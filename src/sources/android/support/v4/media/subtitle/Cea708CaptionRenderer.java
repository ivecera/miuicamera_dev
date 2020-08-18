package android.support.v4.media.subtitle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.media.SubtitleData2;
import android.support.v4.media.subtitle.Cea708CCParser;
import android.support.v4.media.subtitle.ClosedCaptionWidget;
import android.support.v4.media.subtitle.SubtitleController;
import android.support.v4.media.subtitle.SubtitleTrack;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.widget.RelativeLayout;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@RequiresApi(28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Cea708CaptionRenderer extends SubtitleController.Renderer {
    private Cea708CCWidget mCCWidget;
    private final Context mContext;

    class Cea708CCWidget extends ClosedCaptionWidget implements Cea708CCParser.DisplayListener {
        private final CCHandler mCCHandler;

        class CCHandler implements Handler.Callback {
            private static final int CAPTION_ALL_WINDOWS_BITMAP = 255;
            private static final long CAPTION_CLEAR_INTERVAL_MS = 60000;
            private static final int CAPTION_WINDOWS_MAX = 8;
            private static final boolean DEBUG = false;
            private static final int MSG_CAPTION_CLEAR = 2;
            private static final int MSG_DELAY_CANCEL = 1;
            private static final String TAG = "CCHandler";
            private static final int TENTHS_OF_SECOND_IN_MILLIS = 100;
            private final CCLayout mCCLayout;
            private final CCWindowLayout[] mCaptionWindowLayouts = new CCWindowLayout[8];
            private CCWindowLayout mCurrentWindowLayout;
            private final Handler mHandler;
            private boolean mIsDelayed = false;
            private final ArrayList<Cea708CCParser.CaptionEvent> mPendingCaptionEvents = new ArrayList<>();

            CCHandler(CCLayout cCLayout) {
                this.mCCLayout = cCLayout;
                this.mHandler = new Handler(this);
            }

            private void clearWindows(int i) {
                if (i != 0) {
                    Iterator<CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
                    while (it.hasNext()) {
                        it.next().clear();
                    }
                }
            }

            private void defineWindow(Cea708CCParser.CaptionWindow captionWindow) {
                int i;
                if (captionWindow != null && (i = captionWindow.id) >= 0) {
                    CCWindowLayout[] cCWindowLayoutArr = this.mCaptionWindowLayouts;
                    if (i < cCWindowLayoutArr.length) {
                        CCWindowLayout cCWindowLayout = cCWindowLayoutArr[i];
                        if (cCWindowLayout == null) {
                            cCWindowLayout = new CCWindowLayout(Cea708CCWidget.this, this.mCCLayout.getContext());
                        }
                        cCWindowLayout.initWindow(this.mCCLayout, captionWindow);
                        this.mCaptionWindowLayouts[i] = cCWindowLayout;
                        this.mCurrentWindowLayout = cCWindowLayout;
                    }
                }
            }

            private void delay(int i) {
                if (i >= 0 && i <= 255) {
                    this.mIsDelayed = true;
                    Handler handler = this.mHandler;
                    handler.sendMessageDelayed(handler.obtainMessage(1), (long) (i * 100));
                }
            }

            private void delayCancel() {
                this.mIsDelayed = false;
                processPendingBuffer();
            }

            private void deleteWindows(int i) {
                if (i != 0) {
                    Iterator<CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
                    while (it.hasNext()) {
                        CCWindowLayout next = it.next();
                        next.removeFromCaptionView();
                        this.mCaptionWindowLayouts[next.getCaptionWindowId()] = null;
                    }
                }
            }

            private void displayWindows(int i) {
                if (i != 0) {
                    Iterator<CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
                    while (it.hasNext()) {
                        it.next().show();
                    }
                }
            }

            private ArrayList<CCWindowLayout> getWindowsFromBitmap(int i) {
                CCWindowLayout cCWindowLayout;
                ArrayList<CCWindowLayout> arrayList = new ArrayList<>();
                for (int i2 = 0; i2 < 8; i2++) {
                    if (!(((1 << i2) & i) == 0 || (cCWindowLayout = this.mCaptionWindowLayouts[i2]) == null)) {
                        arrayList.add(cCWindowLayout);
                    }
                }
                return arrayList;
            }

            private void hideWindows(int i) {
                if (i != 0) {
                    Iterator<CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
                    while (it.hasNext()) {
                        it.next().hide();
                    }
                }
            }

            private void processPendingBuffer() {
                Iterator<Cea708CCParser.CaptionEvent> it = this.mPendingCaptionEvents.iterator();
                while (it.hasNext()) {
                    processCaptionEvent(it.next());
                }
                this.mPendingCaptionEvents.clear();
            }

            private void sendBufferToCurrentWindow(String str) {
                CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
                if (cCWindowLayout != null) {
                    cCWindowLayout.sendBuffer(str);
                    this.mHandler.removeMessages(2);
                    Handler handler = this.mHandler;
                    handler.sendMessageDelayed(handler.obtainMessage(2), CAPTION_CLEAR_INTERVAL_MS);
                }
            }

            private void sendControlToCurrentWindow(char c2) {
                CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
                if (cCWindowLayout != null) {
                    cCWindowLayout.sendControl(c2);
                }
            }

            private void setCurrentWindowLayout(int i) {
                CCWindowLayout cCWindowLayout;
                if (i >= 0) {
                    CCWindowLayout[] cCWindowLayoutArr = this.mCaptionWindowLayouts;
                    if (i < cCWindowLayoutArr.length && (cCWindowLayout = cCWindowLayoutArr[i]) != null) {
                        this.mCurrentWindowLayout = cCWindowLayout;
                    }
                }
            }

            private void setPenAttr(Cea708CCParser.CaptionPenAttr captionPenAttr) {
                CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
                if (cCWindowLayout != null) {
                    cCWindowLayout.setPenAttr(captionPenAttr);
                }
            }

            private void setPenColor(Cea708CCParser.CaptionPenColor captionPenColor) {
                CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
                if (cCWindowLayout != null) {
                    cCWindowLayout.setPenColor(captionPenColor);
                }
            }

            private void setPenLocation(Cea708CCParser.CaptionPenLocation captionPenLocation) {
                CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
                if (cCWindowLayout != null) {
                    cCWindowLayout.setPenLocation(captionPenLocation.row, captionPenLocation.column);
                }
            }

            private void setWindowAttr(Cea708CCParser.CaptionWindowAttr captionWindowAttr) {
                CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
                if (cCWindowLayout != null) {
                    cCWindowLayout.setWindowAttr(captionWindowAttr);
                }
            }

            private void toggleWindows(int i) {
                if (i != 0) {
                    Iterator<CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
                    while (it.hasNext()) {
                        CCWindowLayout next = it.next();
                        if (next.isShown()) {
                            next.hide();
                        } else {
                            next.show();
                        }
                    }
                }
            }

            public boolean handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    delayCancel();
                    return true;
                } else if (i != 2) {
                    return false;
                } else {
                    clearWindows(255);
                    return true;
                }
            }

            public void processCaptionEvent(Cea708CCParser.CaptionEvent captionEvent) {
                if (this.mIsDelayed) {
                    this.mPendingCaptionEvents.add(captionEvent);
                    return;
                }
                switch (captionEvent.type) {
                    case 1:
                        sendBufferToCurrentWindow((String) captionEvent.obj);
                        return;
                    case 2:
                        sendControlToCurrentWindow(((Character) captionEvent.obj).charValue());
                        return;
                    case 3:
                        setCurrentWindowLayout(((Integer) captionEvent.obj).intValue());
                        return;
                    case 4:
                        clearWindows(((Integer) captionEvent.obj).intValue());
                        return;
                    case 5:
                        displayWindows(((Integer) captionEvent.obj).intValue());
                        return;
                    case 6:
                        hideWindows(((Integer) captionEvent.obj).intValue());
                        return;
                    case 7:
                        toggleWindows(((Integer) captionEvent.obj).intValue());
                        return;
                    case 8:
                        deleteWindows(((Integer) captionEvent.obj).intValue());
                        return;
                    case 9:
                        delay(((Integer) captionEvent.obj).intValue());
                        return;
                    case 10:
                        delayCancel();
                        return;
                    case 11:
                        reset();
                        return;
                    case 12:
                        setPenAttr((Cea708CCParser.CaptionPenAttr) captionEvent.obj);
                        return;
                    case 13:
                        setPenColor((Cea708CCParser.CaptionPenColor) captionEvent.obj);
                        return;
                    case 14:
                        setPenLocation((Cea708CCParser.CaptionPenLocation) captionEvent.obj);
                        return;
                    case 15:
                        setWindowAttr((Cea708CCParser.CaptionWindowAttr) captionEvent.obj);
                        return;
                    case 16:
                        defineWindow((Cea708CCParser.CaptionWindow) captionEvent.obj);
                        return;
                    default:
                        return;
                }
            }

            public void reset() {
                this.mCurrentWindowLayout = null;
                this.mIsDelayed = false;
                this.mPendingCaptionEvents.clear();
                for (int i = 0; i < 8; i++) {
                    CCWindowLayout[] cCWindowLayoutArr = this.mCaptionWindowLayouts;
                    if (cCWindowLayoutArr[i] != null) {
                        cCWindowLayoutArr[i].removeFromCaptionView();
                    }
                    this.mCaptionWindowLayouts[i] = null;
                }
                this.mCCLayout.setVisibility(4);
                this.mHandler.removeMessages(2);
            }
        }

        class CCLayout extends ScaledLayout implements ClosedCaptionWidget.ClosedCaptionLayout {
            private static final float SAFE_TITLE_AREA_SCALE_END_X = 0.9f;
            private static final float SAFE_TITLE_AREA_SCALE_END_Y = 0.9f;
            private static final float SAFE_TITLE_AREA_SCALE_START_X = 0.1f;
            private static final float SAFE_TITLE_AREA_SCALE_START_Y = 0.1f;
            private final ScaledLayout mSafeTitleAreaLayout;

            CCLayout(Context context) {
                super(context);
                this.mSafeTitleAreaLayout = new ScaledLayout(context);
                addView(this.mSafeTitleAreaLayout, new ScaledLayout.ScaledLayoutParams(0.1f, 0.9f, 0.1f, 0.9f));
            }

            public void addOrUpdateViewToSafeTitleArea(CCWindowLayout cCWindowLayout, ScaledLayout.ScaledLayoutParams scaledLayoutParams) {
                if (this.mSafeTitleAreaLayout.indexOfChild(cCWindowLayout) < 0) {
                    this.mSafeTitleAreaLayout.addView(cCWindowLayout, scaledLayoutParams);
                } else {
                    this.mSafeTitleAreaLayout.updateViewLayout(cCWindowLayout, scaledLayoutParams);
                }
            }

            public void removeViewFromSafeTitleArea(CCWindowLayout cCWindowLayout) {
                this.mSafeTitleAreaLayout.removeView(cCWindowLayout);
            }

            @Override // android.support.v4.media.subtitle.ClosedCaptionWidget.ClosedCaptionLayout
            public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
                int childCount = this.mSafeTitleAreaLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ((CCWindowLayout) this.mSafeTitleAreaLayout.getChildAt(i)).setCaptionStyle(captionStyle);
                }
            }

            @Override // android.support.v4.media.subtitle.ClosedCaptionWidget.ClosedCaptionLayout
            public void setFontScale(float f2) {
                int childCount = this.mSafeTitleAreaLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ((CCWindowLayout) this.mSafeTitleAreaLayout.getChildAt(i)).setFontScale(f2);
                }
            }
        }

        class CCView extends SubtitleView {
            CCView(Cea708CCWidget cea708CCWidget, Context context) {
                this(cea708CCWidget, context, null);
            }

            CCView(Cea708CCWidget cea708CCWidget, Context context, AttributeSet attributeSet) {
                this(cea708CCWidget, context, attributeSet, 0);
            }

            CCView(Cea708CCWidget cea708CCWidget, Context context, AttributeSet attributeSet, int i) {
                this(context, attributeSet, i, 0);
            }

            CCView(Context context, AttributeSet attributeSet, int i, int i2) {
                super(context, attributeSet, i, i2);
            }

            /* access modifiers changed from: package-private */
            public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
                if (captionStyle.hasForegroundColor()) {
                    setForegroundColor(captionStyle.foregroundColor);
                }
                if (captionStyle.hasBackgroundColor()) {
                    setBackgroundColor(captionStyle.backgroundColor);
                }
                if (captionStyle.hasEdgeType()) {
                    setEdgeType(captionStyle.edgeType);
                }
                if (captionStyle.hasEdgeColor()) {
                    setEdgeColor(captionStyle.edgeColor);
                }
                setTypeface(captionStyle.getTypeface());
            }
        }

        private class CCWindowLayout extends RelativeLayout implements View.OnLayoutChangeListener {
            private static final int ANCHOR_HORIZONTAL_16_9_MAX = 209;
            private static final int ANCHOR_HORIZONTAL_MODE_CENTER = 1;
            private static final int ANCHOR_HORIZONTAL_MODE_LEFT = 0;
            private static final int ANCHOR_HORIZONTAL_MODE_RIGHT = 2;
            private static final int ANCHOR_MODE_DIVIDER = 3;
            private static final int ANCHOR_RELATIVE_POSITIONING_MAX = 99;
            private static final int ANCHOR_VERTICAL_MAX = 74;
            private static final int ANCHOR_VERTICAL_MODE_BOTTOM = 2;
            private static final int ANCHOR_VERTICAL_MODE_CENTER = 1;
            private static final int ANCHOR_VERTICAL_MODE_TOP = 0;
            private static final int MAX_COLUMN_COUNT_16_9 = 42;
            private static final float PROPORTION_PEN_SIZE_LARGE = 1.25f;
            private static final float PROPORTION_PEN_SIZE_SMALL = 0.75f;
            private static final String TAG = "CCWindowLayout";
            private final SpannableStringBuilder mBuilder;
            private CCLayout mCCLayout;
            private CCView mCCView;
            private CaptioningManager.CaptionStyle mCaptionStyle;
            private int mCaptionWindowId;
            private final List<CharacterStyle> mCharacterStyles;
            private float mFontScale;
            private int mLastCaptionLayoutHeight;
            private int mLastCaptionLayoutWidth;
            private int mRow;
            private int mRowLimit;
            private float mTextSize;
            private String mWidestChar;

            CCWindowLayout(Cea708CCWidget cea708CCWidget, Context context) {
                this(cea708CCWidget, context, null);
            }

            CCWindowLayout(Cea708CCWidget cea708CCWidget, Context context, AttributeSet attributeSet) {
                this(cea708CCWidget, context, attributeSet, 0);
            }

            CCWindowLayout(Cea708CCWidget cea708CCWidget, Context context, AttributeSet attributeSet, int i) {
                this(context, attributeSet, i, 0);
            }

            CCWindowLayout(Context context, AttributeSet attributeSet, int i, int i2) {
                super(context, attributeSet, i, i2);
                this.mRowLimit = 0;
                this.mBuilder = new SpannableStringBuilder();
                this.mCharacterStyles = new ArrayList();
                this.mRow = -1;
                this.mCCView = new CCView(Cea708CCWidget.this, context);
                addView(this.mCCView, new RelativeLayout.LayoutParams(-2, -2));
                CaptioningManager captioningManager = (CaptioningManager) context.getSystemService("captioning");
                this.mFontScale = captioningManager.getFontScale();
                setCaptionStyle(captioningManager.getUserStyle());
                this.mCCView.setText("");
                updateWidestChar();
            }

            private int getScreenColumnCount() {
                return 42;
            }

            private void updateText(String str, boolean z) {
                if (!z) {
                    this.mBuilder.clear();
                }
                if (str != null && str.length() > 0) {
                    int length = this.mBuilder.length();
                    this.mBuilder.append((CharSequence) str);
                    for (CharacterStyle characterStyle : this.mCharacterStyles) {
                        SpannableStringBuilder spannableStringBuilder = this.mBuilder;
                        spannableStringBuilder.setSpan(characterStyle, length, spannableStringBuilder.length(), 33);
                    }
                }
                String[] split = TextUtils.split(this.mBuilder.toString(), "\n");
                String join = TextUtils.join("\n", Arrays.copyOfRange(split, Math.max(0, split.length - (this.mRowLimit + 1)), split.length));
                SpannableStringBuilder spannableStringBuilder2 = this.mBuilder;
                spannableStringBuilder2.delete(0, spannableStringBuilder2.length() - join.length());
                int length2 = this.mBuilder.length() - 1;
                int i = 0;
                while (i <= length2 && this.mBuilder.charAt(i) <= ' ') {
                    i++;
                }
                int i2 = length2;
                while (i2 >= i && this.mBuilder.charAt(i2) <= ' ') {
                    i2--;
                }
                if (i == 0 && i2 == length2) {
                    this.mCCView.setText(this.mBuilder);
                    return;
                }
                SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
                spannableStringBuilder3.append((CharSequence) this.mBuilder);
                if (i2 < length2) {
                    spannableStringBuilder3.delete(i2 + 1, length2 + 1);
                }
                if (i > 0) {
                    spannableStringBuilder3.delete(0, i);
                }
                this.mCCView.setText(spannableStringBuilder3);
            }

            private void updateTextSize() {
                if (this.mCCLayout != null) {
                    StringBuilder sb = new StringBuilder();
                    int screenColumnCount = getScreenColumnCount();
                    for (int i = 0; i < screenColumnCount; i++) {
                        sb.append(this.mWidestChar);
                    }
                    String sb2 = sb.toString();
                    Paint paint = new Paint();
                    paint.setTypeface(this.mCaptionStyle.getTypeface());
                    float f2 = 0.0f;
                    float f3 = 255.0f;
                    while (f2 < f3) {
                        float f4 = (f2 + f3) / 2.0f;
                        paint.setTextSize(f4);
                        if (((float) this.mCCLayout.getWidth()) * 0.8f > paint.measureText(sb2)) {
                            f2 = f4 + 0.01f;
                        } else {
                            f3 = f4 - 0.01f;
                        }
                    }
                    this.mTextSize = f3 * this.mFontScale;
                    this.mCCView.setTextSize(this.mTextSize);
                }
            }

            private void updateWidestChar() {
                Paint paint = new Paint();
                paint.setTypeface(this.mCaptionStyle.getTypeface());
                Charset forName = Charset.forName("ISO-8859-1");
                float f2 = 0.0f;
                for (int i = 0; i < 256; i++) {
                    String str = new String(new byte[]{(byte) i}, forName);
                    float measureText = paint.measureText(str);
                    if (f2 < measureText) {
                        this.mWidestChar = str;
                        f2 = measureText;
                    }
                }
                updateTextSize();
            }

            public void appendText(String str) {
                updateText(str, true);
            }

            public void clear() {
                clearText();
                hide();
            }

            public void clearText() {
                this.mBuilder.clear();
                this.mCCView.setText("");
            }

            public int getCaptionWindowId() {
                return this.mCaptionWindowId;
            }

            public void hide() {
                setVisibility(4);
                requestLayout();
            }

            /* JADX WARNING: Removed duplicated region for block: B:44:0x0122  */
            /* JADX WARNING: Removed duplicated region for block: B:50:0x013a  */
            /* JADX WARNING: Removed duplicated region for block: B:53:0x015e  */
            /* JADX WARNING: Removed duplicated region for block: B:54:0x0162  */
            public void initWindow(CCLayout cCLayout, Cea708CCParser.CaptionWindow captionWindow) {
                float f2;
                float f3;
                float f4;
                float f5;
                CCLayout cCLayout2 = this.mCCLayout;
                if (cCLayout2 != cCLayout) {
                    if (cCLayout2 != null) {
                        cCLayout2.removeOnLayoutChangeListener(this);
                    }
                    this.mCCLayout = cCLayout;
                    this.mCCLayout.addOnLayoutChangeListener(this);
                    updateWidestChar();
                }
                int i = 99;
                float f6 = ((float) captionWindow.anchorVertical) / ((float) (captionWindow.relativePositioning ? 99 : 74));
                float f7 = (float) captionWindow.anchorHorizontal;
                if (!captionWindow.relativePositioning) {
                    i = 209;
                }
                float f8 = f7 / ((float) i);
                float f9 = 0.0f;
                float f10 = 1.0f;
                if (f6 < 0.0f || f6 > 1.0f) {
                    Log.i(TAG, "The vertical position of the anchor point should be at the range of 0 and 1 but " + f6);
                    f6 = Math.max(0.0f, Math.min(f6, 1.0f));
                }
                if (f8 < 0.0f || f8 > 1.0f) {
                    Log.i(TAG, "The horizontal position of the anchor point should be at the range of 0 and 1 but " + f8);
                    f8 = Math.max(0.0f, Math.min(f8, 1.0f));
                }
                int i2 = 17;
                int i3 = captionWindow.anchorId;
                int i4 = i3 % 3;
                int i5 = i3 / 3;
                if (i4 != 0) {
                    if (i4 == 1) {
                        float min = Math.min(1.0f - f8, f8);
                        int min2 = Math.min(getScreenColumnCount(), captionWindow.columnCount + 1);
                        StringBuilder sb = new StringBuilder();
                        for (int i6 = 0; i6 < min2; i6++) {
                            sb.append(this.mWidestChar);
                        }
                        Paint paint = new Paint();
                        paint.setTypeface(this.mCaptionStyle.getTypeface());
                        paint.setTextSize(this.mTextSize);
                        float measureText = this.mCCLayout.getWidth() > 0 ? (paint.measureText(sb.toString()) / 2.0f) / (((float) this.mCCLayout.getWidth()) * 0.8f) : 0.0f;
                        if (measureText <= 0.0f || measureText >= f8) {
                            this.mCCView.setAlignment(Layout.Alignment.ALIGN_CENTER);
                            f2 = f8 + min;
                            f3 = f8 - min;
                            i2 = 1;
                        } else {
                            this.mCCView.setAlignment(Layout.Alignment.ALIGN_NORMAL);
                            f8 -= measureText;
                        }
                    } else if (i4 != 2) {
                        f3 = 0.0f;
                        f2 = 1.0f;
                    } else {
                        i2 = 5;
                        f2 = f8;
                        f3 = 0.0f;
                    }
                    if (i5 == 0) {
                        if (i5 == 1) {
                            i2 |= 16;
                            float min3 = Math.min(1.0f - f6, f6);
                            f9 = f6 - min3;
                            f10 = f6 + min3;
                        } else if (i5 == 2) {
                            i2 |= 80;
                            f4 = f6;
                            f5 = 0.0f;
                            CCLayout cCLayout3 = this.mCCLayout;
                            cCLayout3.getClass();
                            cCLayout3.addOrUpdateViewToSafeTitleArea(this, new ScaledLayout.ScaledLayoutParams(f5, f4, f3, f2));
                            setCaptionWindowId(captionWindow.id);
                            setRowLimit(captionWindow.rowCount);
                            setGravity(i2);
                            if (captionWindow.visible) {
                                show();
                                return;
                            } else {
                                hide();
                                return;
                            }
                        }
                        f5 = f9;
                    } else {
                        i2 |= 48;
                        f5 = f6;
                    }
                    f4 = f10;
                    CCLayout cCLayout32 = this.mCCLayout;
                    cCLayout32.getClass();
                    cCLayout32.addOrUpdateViewToSafeTitleArea(this, new ScaledLayout.ScaledLayoutParams(f5, f4, f3, f2));
                    setCaptionWindowId(captionWindow.id);
                    setRowLimit(captionWindow.rowCount);
                    setGravity(i2);
                    if (captionWindow.visible) {
                    }
                } else {
                    this.mCCView.setAlignment(Layout.Alignment.ALIGN_NORMAL);
                }
                f3 = f8;
                f2 = 1.0f;
                i2 = 3;
                if (i5 == 0) {
                }
                f4 = f10;
                CCLayout cCLayout322 = this.mCCLayout;
                cCLayout322.getClass();
                cCLayout322.addOrUpdateViewToSafeTitleArea(this, new ScaledLayout.ScaledLayoutParams(f5, f4, f3, f2));
                setCaptionWindowId(captionWindow.id);
                setRowLimit(captionWindow.rowCount);
                setGravity(i2);
                if (captionWindow.visible) {
                }
            }

            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                int i9 = i3 - i;
                int i10 = i4 - i2;
                if (i9 != this.mLastCaptionLayoutWidth || i10 != this.mLastCaptionLayoutHeight) {
                    this.mLastCaptionLayoutWidth = i9;
                    this.mLastCaptionLayoutHeight = i10;
                    updateTextSize();
                }
            }

            public void removeFromCaptionView() {
                CCLayout cCLayout = this.mCCLayout;
                if (cCLayout != null) {
                    cCLayout.removeViewFromSafeTitleArea(this);
                    this.mCCLayout.removeOnLayoutChangeListener(this);
                    this.mCCLayout = null;
                }
            }

            public void sendBuffer(String str) {
                appendText(str);
            }

            public void sendControl(char c2) {
            }

            public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
                this.mCaptionStyle = captionStyle;
                this.mCCView.setCaptionStyle(captionStyle);
            }

            public void setCaptionWindowId(int i) {
                this.mCaptionWindowId = i;
            }

            public void setFontScale(float f2) {
                this.mFontScale = f2;
                updateTextSize();
            }

            public void setPenAttr(Cea708CCParser.CaptionPenAttr captionPenAttr) {
                this.mCharacterStyles.clear();
                if (captionPenAttr.italic) {
                    this.mCharacterStyles.add(new StyleSpan(2));
                }
                if (captionPenAttr.underline) {
                    this.mCharacterStyles.add(new UnderlineSpan());
                }
                int i = captionPenAttr.penSize;
                if (i == 0) {
                    this.mCharacterStyles.add(new RelativeSizeSpan((float) PROPORTION_PEN_SIZE_SMALL));
                } else if (i == 2) {
                    this.mCharacterStyles.add(new RelativeSizeSpan((float) PROPORTION_PEN_SIZE_LARGE));
                }
                int i2 = captionPenAttr.penOffset;
                if (i2 == 0) {
                    this.mCharacterStyles.add(new SubscriptSpan());
                } else if (i2 == 2) {
                    this.mCharacterStyles.add(new SuperscriptSpan());
                }
            }

            public void setPenColor(Cea708CCParser.CaptionPenColor captionPenColor) {
            }

            public void setPenLocation(int i, int i2) {
                int i3 = this.mRow;
                if (i3 >= 0) {
                    while (i3 < i) {
                        appendText("\n");
                        i3++;
                    }
                }
                this.mRow = i;
            }

            public void setRowLimit(int i) {
                if (i >= 0) {
                    this.mRowLimit = i;
                    return;
                }
                throw new IllegalArgumentException("A rowLimit should have a positive number");
            }

            public void setText(String str) {
                updateText(str, false);
            }

            public void setWindowAttr(Cea708CCParser.CaptionWindowAttr captionWindowAttr) {
            }

            public void show() {
                setVisibility(0);
                requestLayout();
            }
        }

        class ScaledLayout extends ViewGroup {
            private static final boolean DEBUG = false;
            private static final String TAG = "ScaledLayout";
            private Rect[] mRectArray;
            private final Comparator<Rect> mRectTopLeftSorter = new Comparator<Rect>() {
                /* class android.support.v4.media.subtitle.Cea708CaptionRenderer.Cea708CCWidget.ScaledLayout.AnonymousClass1 */

                public int compare(Rect rect, Rect rect2) {
                    int i = rect.top;
                    int i2 = rect2.top;
                    return i != i2 ? i - i2 : rect.left - rect2.left;
                }
            };

            class ScaledLayoutParams extends ViewGroup.LayoutParams {
                public static final float SCALE_UNSPECIFIED = -1.0f;
                public float scaleEndCol;
                public float scaleEndRow;
                public float scaleStartCol;
                public float scaleStartRow;

                ScaledLayoutParams(float f2, float f3, float f4, float f5) {
                    super(-1, -1);
                    this.scaleStartRow = f2;
                    this.scaleEndRow = f3;
                    this.scaleStartCol = f4;
                    this.scaleEndCol = f5;
                }

                ScaledLayoutParams(Context context, AttributeSet attributeSet) {
                    super(-1, -1);
                }
            }

            ScaledLayout(Context context) {
                super(context);
            }

            /* access modifiers changed from: protected */
            public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
                return layoutParams instanceof ScaledLayoutParams;
            }

            public void dispatchDraw(Canvas canvas) {
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    if (childAt.getVisibility() != 8) {
                        Rect[] rectArr = this.mRectArray;
                        if (i < rectArr.length) {
                            int save = canvas.save();
                            canvas.translate((float) (rectArr[i].left + paddingLeft), (float) (rectArr[i].top + paddingTop));
                            childAt.draw(canvas);
                            canvas.restoreToCount(save);
                        } else {
                            return;
                        }
                    }
                }
            }

            @Override // android.view.ViewGroup
            public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
                return new ScaledLayoutParams(getContext(), attributeSet);
            }

            /* access modifiers changed from: protected */
            public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                int childCount = getChildCount();
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    if (childAt.getVisibility() != 8) {
                        Rect[] rectArr = this.mRectArray;
                        childAt.layout(rectArr[i5].left + paddingLeft, rectArr[i5].top + paddingTop, rectArr[i5].right + paddingTop, rectArr[i5].bottom + paddingLeft);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onMeasure(int i, int i2) {
                int i3;
                int i4;
                int i5;
                int size = View.MeasureSpec.getSize(i);
                int size2 = View.MeasureSpec.getSize(i2);
                int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
                int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
                int childCount = getChildCount();
                this.mRectArray = new Rect[childCount];
                int i6 = 0;
                while (i6 < childCount) {
                    View childAt = getChildAt(i6);
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    if (layoutParams instanceof ScaledLayoutParams) {
                        ScaledLayoutParams scaledLayoutParams = (ScaledLayoutParams) layoutParams;
                        float f2 = scaledLayoutParams.scaleStartRow;
                        float f3 = scaledLayoutParams.scaleEndRow;
                        float f4 = scaledLayoutParams.scaleStartCol;
                        float f5 = scaledLayoutParams.scaleEndCol;
                        if (f2 < 0.0f || f2 > 1.0f) {
                            throw new RuntimeException("A child of ScaledLayout should have a range of scaleStartRow between 0 and 1");
                        } else if (f3 < f2 || i4 > 0) {
                            throw new RuntimeException("A child of ScaledLayout should have a range of scaleEndRow between scaleStartRow and 1");
                        } else if (f5 < 0.0f || f5 > 1.0f) {
                            throw new RuntimeException("A child of ScaledLayout should have a range of scaleStartCol between 0 and 1");
                        } else if (f5 < f4 || i5 > 0) {
                            throw new RuntimeException("A child of ScaledLayout should have a range of scaleEndCol between scaleStartCol and 1");
                        } else {
                            float f6 = (float) paddingLeft;
                            float f7 = (float) paddingTop;
                            this.mRectArray[i6] = new Rect((int) (f4 * f6), (int) (f2 * f7), (int) (f5 * f6), (int) (f3 * f7));
                            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (f6 * (f5 - f4)), 1073741824);
                            childAt.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
                            if (childAt.getMeasuredHeight() > this.mRectArray[i6].height()) {
                                int measuredHeight = ((childAt.getMeasuredHeight() - this.mRectArray[i6].height()) + 1) / 2;
                                Rect[] rectArr = this.mRectArray;
                                rectArr[i6].bottom += measuredHeight;
                                rectArr[i6].top -= measuredHeight;
                                if (rectArr[i6].top < 0) {
                                    rectArr[i6].bottom -= rectArr[i6].top;
                                    rectArr[i6].top = 0;
                                }
                                Rect[] rectArr2 = this.mRectArray;
                                if (rectArr2[i6].bottom > paddingTop) {
                                    rectArr2[i6].top -= rectArr2[i6].bottom - paddingTop;
                                    rectArr2[i6].bottom = paddingTop;
                                }
                            }
                            childAt.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec((int) (f7 * (f3 - f2)), 1073741824));
                            i6++;
                            paddingLeft = paddingLeft;
                            size = size;
                            size2 = size2;
                            childCount = childCount;
                        }
                    } else {
                        throw new RuntimeException("A child of ScaledLayout cannot have the UNSPECIFIED scale factors");
                    }
                }
                int[] iArr = new int[childCount];
                Rect[] rectArr3 = new Rect[childCount];
                int i7 = 0;
                for (int i8 = 0; i8 < childCount; i8++) {
                    if (getChildAt(i8).getVisibility() == 0) {
                        iArr[i7] = i7;
                        rectArr3[i7] = this.mRectArray[i8];
                        i7++;
                    }
                }
                Arrays.sort(rectArr3, 0, i7, this.mRectTopLeftSorter);
                int i9 = 0;
                while (true) {
                    i3 = i7 - 1;
                    if (i9 >= i3) {
                        break;
                    }
                    int i10 = i9 + 1;
                    for (int i11 = i10; i11 < i7; i11++) {
                        if (Rect.intersects(rectArr3[i9], rectArr3[i11])) {
                            iArr[i11] = iArr[i9];
                            rectArr3[i11].set(rectArr3[i11].left, rectArr3[i9].bottom, rectArr3[i11].right, rectArr3[i9].bottom + rectArr3[i11].height());
                        }
                    }
                    i9 = i10;
                }
                while (i3 >= 0) {
                    if (rectArr3[i3].bottom > paddingTop) {
                        int i12 = rectArr3[i3].bottom - paddingTop;
                        for (int i13 = 0; i13 <= i3; i13++) {
                            if (iArr[i3] == iArr[i13]) {
                                rectArr3[i13].set(rectArr3[i13].left, rectArr3[i13].top - i12, rectArr3[i13].right, rectArr3[i13].bottom - i12);
                            }
                        }
                    }
                    i3--;
                }
                setMeasuredDimension(size, size2);
            }
        }

        Cea708CCWidget(Cea708CaptionRenderer cea708CaptionRenderer, Context context) {
            this(cea708CaptionRenderer, context, null);
        }

        Cea708CCWidget(Cea708CaptionRenderer cea708CaptionRenderer, Context context, AttributeSet attributeSet) {
            this(cea708CaptionRenderer, context, attributeSet, 0);
        }

        Cea708CCWidget(Cea708CaptionRenderer cea708CaptionRenderer, Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        Cea708CCWidget(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mCCHandler = new CCHandler((CCLayout) ((ClosedCaptionWidget) this).mClosedCaptionLayout);
        }

        @Override // android.support.v4.media.subtitle.ClosedCaptionWidget
        public ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(Context context) {
            return new CCLayout(context);
        }

        @Override // android.support.v4.media.subtitle.Cea708CCParser.DisplayListener
        public void emitEvent(Cea708CCParser.CaptionEvent captionEvent) {
            this.mCCHandler.processCaptionEvent(captionEvent);
            setSize(getWidth(), getHeight());
            SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener = ((ClosedCaptionWidget) this).mListener;
            if (onChangedListener != null) {
                onChangedListener.onChanged(this);
            }
        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            ((ViewGroup) ((ClosedCaptionWidget) this).mClosedCaptionLayout).draw(canvas);
        }
    }

    static class Cea708CaptionTrack extends SubtitleTrack {
        private final Cea708CCParser mCCParser = new Cea708CCParser(this.mRenderingWidget);
        private final Cea708CCWidget mRenderingWidget;

        Cea708CaptionTrack(Cea708CCWidget cea708CCWidget, MediaFormat mediaFormat) {
            super(mediaFormat);
            this.mRenderingWidget = cea708CCWidget;
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

    public Cea708CaptionRenderer(Context context) {
        this.mContext = context;
    }

    @Override // android.support.v4.media.subtitle.SubtitleController.Renderer
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (SubtitleData2.MIMETYPE_TEXT_CEA_708.equals(mediaFormat.getString("mime"))) {
            if (this.mCCWidget == null) {
                this.mCCWidget = new Cea708CCWidget(this, this.mContext);
            }
            return new Cea708CaptionTrack(this.mCCWidget, mediaFormat);
        }
        throw new RuntimeException("No matching format: " + mediaFormat.toString());
    }

    @Override // android.support.v4.media.subtitle.SubtitleController.Renderer
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return SubtitleData2.MIMETYPE_TEXT_CEA_708.equals(mediaFormat.getString("mime"));
        }
        return false;
    }
}
