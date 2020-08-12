package com.android.camera.fragment.beauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.beauty.SingleCheckAdapter;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.SeekBarCompat;
import java.util.List;

public class BokehSmoothLevelFragment extends BaseBeautyFragment {
    private static final int INTERVAL = 1;
    /* access modifiers changed from: private */
    public SeekBarCompat mAdjustSeekBar;
    /* access modifiers changed from: private */
    public boolean mIsRTL;
    /* access modifiers changed from: private */
    public int mSeekBarMaxProgress = 100;
    private ModeProtocol.StickerProtocol mStickerImpl;

    private void initView(View view) {
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mAdjustSeekBar = (SeekBarCompat) view.findViewById(R.id.beauty_level_adjust_seekbar);
        initBeautyItems();
        this.mSeekBarMaxProgress = 100;
        this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
        this.mAdjustSeekBar.setMax(this.mSeekBarMaxProgress);
        this.mAdjustSeekBar.setSeekBarPinProgress(0);
        this.mAdjustSeekBar.setProgress((int) CameraSettings.getVideoBokehRatio());
        this.mAdjustSeekBar.setOnSeekBarChangeListener(new SeekBarCompat.OnSeekBarCompatChangeListener() {
            /* class com.android.camera.fragment.beauty.BokehSmoothLevelFragment.AnonymousClass1 */

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                BokehSmoothLevelFragment.this.onLevelSelected(i);
            }

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mAdjustSeekBar.setOnSeekBarCompatTouchListener(new SeekBarCompat.OnSeekBarCompatTouchListener() {
            /* class com.android.camera.fragment.beauty.BokehSmoothLevelFragment.AnonymousClass2 */

            private int getNextProgress(MotionEvent motionEvent) {
                int width = BokehSmoothLevelFragment.this.mAdjustSeekBar.getWidth();
                int x = (int) (((BokehSmoothLevelFragment.this.mIsRTL ? ((float) width) - motionEvent.getX() : motionEvent.getX()) / ((float) width)) * ((float) BokehSmoothLevelFragment.this.mSeekBarMaxProgress));
                int pinProgress = BokehSmoothLevelFragment.this.mAdjustSeekBar.getPinProgress();
                if (pinProgress > 0 && (motionEvent.getAction() == 2 || motionEvent.getAction() == 1)) {
                    if (Math.abs(x + 0) <= 1) {
                        x = 0;
                    } else if (Math.abs(x - pinProgress) <= 1) {
                        x = pinProgress;
                    } else if (Math.abs(x - BokehSmoothLevelFragment.this.mSeekBarMaxProgress) <= 1) {
                        x = BokehSmoothLevelFragment.this.mSeekBarMaxProgress;
                    }
                }
                return BokehSmoothLevelFragment.this.mAdjustSeekBar.isCenterTwoWayMode() ? Util.clamp(x - pinProgress, 0 - pinProgress, BokehSmoothLevelFragment.this.mSeekBarMaxProgress - pinProgress) : Util.clamp(x, 0, BokehSmoothLevelFragment.this.mSeekBarMaxProgress);
            }

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (!BokehSmoothLevelFragment.this.mAdjustSeekBar.getThumb().getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        return true;
                    }
                } else if (!(action == 1 || action == 2)) {
                    return false;
                }
                BokehSmoothLevelFragment.this.mAdjustSeekBar.setProgress(getNextProgress(motionEvent));
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void onLevelSelected(int i) {
        CameraSettings.setVideoBokehRatio((float) i);
        ShineHelper.onVideoBokehRatioChanged();
    }

    /* access modifiers changed from: protected */
    public int beautyLevelToPosition(int i, int i2) {
        return Util.clamp(i, 0, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mAdjustSeekBar;
    }

    /* access modifiers changed from: protected */
    public List<SingleCheckAdapter.LevelItem> initBeautyItems() {
        return null;
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_beauty_smooth, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndJumpOut() {
        super.onViewCreatedAndJumpOut();
        this.mAdjustSeekBar.setEnabled(false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        super.onViewCreatedAndVisibleToUser(z);
        this.mAdjustSeekBar.setEnabled(true);
    }

    /* access modifiers changed from: protected */
    public int provideItemHorizontalMargin() {
        return getResources().getDimensionPixelSize(R.dimen.beautycamera_beauty_level_item_margin);
    }

    public void setEnableClick(boolean z) {
        this.mAdjustSeekBar.setEnabled(z);
    }
}
