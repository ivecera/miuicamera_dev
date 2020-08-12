package com.android.lens;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.Image;
import android.os.SystemProperties;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStatUtils;
import com.google.android.libraries.lens.lenslite.LensliteApi;
import com.google.android.libraries.lens.lenslite.LensliteUiContainer;
import com.google.android.libraries.lens.lenslite.LensliteUiController;
import com.google.android.libraries.lens.lenslite.api.LinkImage;
import com.xiaomi.stat.d;

public class LensAgent {
    private static final String TAG = "LensAgent";
    private volatile boolean mInitialized;
    private volatile boolean mIsResumed;
    private LensliteApi mLensliteApi;

    private static class SingletonHandler {
        /* access modifiers changed from: private */
        public static final LensAgent LENS_AGENT = new LensAgent();

        private SingletonHandler() {
        }
    }

    private LensAgent() {
    }

    private void applyCustomStyle(Context context, ViewGroup viewGroup) {
        LensliteUiController uiController = this.mLensliteApi.getUiController();
        Resources resources = context.getResources();
        Rect displayRect = Util.getDisplayRect(0);
        uiController.setChipLocation(new PointF(0.0f, (((float) displayRect.bottom) - resources.getDimension(R.dimen.chips_margin_preview_rect_bottom)) / Util.sPixelDensity));
        uiController.setChipDrawable(R.drawable.chips_bg);
        uiController.setOobeLocation(1, ((float) Util.sNavigationBarHeight) / Util.sPixelDensity);
        TextView textView = (TextView) viewGroup.findViewById(R.id.smarts_chip_text);
        if (textView != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(textView.getLayoutParams());
            layoutParams.gravity = 17;
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.setMargins(0, 0, 0, resources.getDimensionPixelOffset(R.dimen.chips_text_margin_bottom));
            textView.setLayoutParams(layoutParams);
            textView.setGravity(17);
            textView.setTextColor(-1);
            textView.setTextSize(0, resources.getDimension(R.dimen.chips_text_size));
        }
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.smarts_chip_close_button);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.chips_close);
            imageView.setImageTintList(ColorStateList.valueOf(-1));
            int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.chips_close_padding);
            imageView.setPadding(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(imageView.getLayoutParams());
            layoutParams2.gravity = 16;
            layoutParams2.width = -2;
            layoutParams2.height = -2;
            int i = SystemProperties.getInt("cancel_margin_left", resources.getDimensionPixelOffset(R.dimen.chips_close_margin_left));
            int i2 = SystemProperties.getInt("cancel_margin_right", resources.getDimensionPixelOffset(R.dimen.chips_close_margin_right));
            Log.d(TAG, "applyCustomStyle: cancel button marginLeft = " + i + ", marginRight = " + i2);
            layoutParams2.setMargins(i, 0, i2, 0);
            imageView.setLayoutParams(layoutParams2);
        }
        LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.chip_view);
        if (linearLayout != null) {
            linearLayout.setPadding(0, 0, 0, 0);
        }
        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.chip_animation_container);
        if (frameLayout != null) {
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(frameLayout.getLayoutParams());
            layoutParams3.gravity = 16;
            int i3 = SystemProperties.getInt("icon_margin_left", resources.getDimensionPixelOffset(R.dimen.chips_icon_margin_left));
            int i4 = SystemProperties.getInt("icon_margin_right", resources.getDimensionPixelOffset(R.dimen.chips_icon_margin_right));
            Log.d(TAG, "applyCustomStyle: icon marginLeft = " + i3 + ", marginRight = " + i4);
            layoutParams3.setMargins(i3, 0, i4, 0);
            frameLayout.setLayoutParams(layoutParams3);
        }
        uiController.setIconForResultType(0, context.getDrawable(R.drawable.chips_location));
        uiController.setIconForResultType(1, context.getDrawable(R.drawable.chips_mail));
        uiController.setIconForResultType(2, context.getDrawable(R.drawable.chips_browser));
        uiController.setIconForResultType(3, context.getDrawable(R.drawable.chips_phone));
        uiController.setIconForResultType(4, context.getDrawable(R.drawable.chips_contact));
        uiController.setIconForResultType(5, context.getDrawable(R.drawable.chips_qrcode));
        uiController.setIconForResultType(6, context.getDrawable(R.drawable.chips_qrcode));
        uiController.setIconForResultType(7, context.getDrawable(R.drawable.chips_qrcode));
        uiController.setIconForResultType(8, context.getDrawable(R.drawable.chips_qrcode));
        uiController.setIconForResultType(12, context.getDrawable(R.drawable.chips_qrcode));
        uiController.setIconForResultType(13, context.getDrawable(R.drawable.chips_mail));
        uiController.setIconForResultType(14, context.getDrawable(R.drawable.chips_calendar));
        uiController.setIconForResultType(18, context.getDrawable(R.drawable.chips_no_result));
    }

    public static LensAgent getInstance() {
        return SingletonHandler.LENS_AGENT;
    }

    public static boolean isConflictAiScene(int i) {
        if (i == -1 || i == 19 || i == 25) {
            return true;
        }
        return i != 31 ? i == 34 || i == 37 : DataRepository.dataItemFeature().Me();
    }

    static /* synthetic */ void r(int i) {
        Log.d(TAG, "onOobeStatusUpdated: " + i);
        if (DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_GOOGLE_LENS_OOBE, false)) {
            CameraStatUtils.trackGoogleLensOobeContinue(i == 3);
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_GOOGLE_LENS_OOBE, false).apply();
            if (i != 3) {
                DataRepository.dataItemGlobal().editor().remove("pref_camera_long_press_viewfinder_key").apply();
            }
        }
    }

    public void init(Activity activity, View view, ViewGroup viewGroup) {
        this.mLensliteApi = LensliteApi.create(activity.getApplicationContext(), 3);
        long currentTimeMillis = System.currentTimeMillis();
        this.mLensliteApi.onStart(new LensliteUiContainer(view, viewGroup), activity, a.INSTANCE);
        Log.d(TAG, "LensliteApi init cost " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        applyCustomStyle(activity.getApplicationContext(), viewGroup);
        this.mInitialized = true;
    }

    public void onFocusChange(@LensliteUiController.FocusType int i, float f2, float f3) {
        if (this.mInitialized) {
            Log.d(TAG, "onFocusChange: type = " + i + ", " + f2 + "x" + f3);
            try {
                this.mLensliteApi.getUiController().onFocusChange(i, new PointF(f2, f3));
            } catch (Exception e2) {
                Log.e(TAG, "onFocusChange: ", e2);
            }
        }
    }

    public void onNewImage(Image image, int i) {
        if (this.mInitialized) {
            if (this.mIsResumed) {
                this.mLensliteApi.onNewImage(LinkImage.create(image, i));
            } else {
                Log.w(TAG, "onNewImage: lens api not resume...");
            }
        }
    }

    public void onPause() {
        if (this.mInitialized) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.mIsResumed) {
                this.mIsResumed = false;
                this.mLensliteApi.onPause();
            }
            Log.d(TAG, "LensliteApi onPause cost " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        }
    }

    public void onResume() {
        if (this.mInitialized) {
            long currentTimeMillis = System.currentTimeMillis();
            if (!this.mIsResumed) {
                this.mLensliteApi.onResume();
                this.mIsResumed = true;
            }
            Log.d(TAG, "LensliteApi onResume cost " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        }
    }

    public void release() {
        if (this.mInitialized) {
            long currentTimeMillis = System.currentTimeMillis();
            this.mLensliteApi.onStop();
            Log.d(TAG, "LensliteApi release cost " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
            this.mInitialized = false;
        }
    }

    public void showOrHideChip(boolean z) {
        if (this.mInitialized) {
            this.mLensliteApi.getUiController().setLensSuggestionsEnabled(z);
        }
    }
}
