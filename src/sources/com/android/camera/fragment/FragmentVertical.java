package com.android.camera.fragment;

import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.protocol.ModeProtocol;
import io.reactivex.Completable;
import java.util.List;

public class FragmentVertical extends BaseFragment implements ModeProtocol.VerticalProtocol {
    private TextView mLeftAlertStatus;
    private TextView mLeftLightingPattern;
    private TextView mRightAlertStatus;
    private TextView mRightLightingPattern;
    private String mStateValueText = "";
    private View mVerticalViewMenu;
    private int oldDegree;
    private int stringLightingRes;

    private void adjustViewHeight(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = (Util.sWindowHeight - ((int) (((float) getResources().getDisplayMetrics().widthPixels) / 0.75f))) - Util.getBottomHeight();
    }

    private void updateLightingRelativeView(boolean z, boolean z2) {
        AlphaOutOnSubscribe.directSetResult(this.mRightAlertStatus);
        AlphaOutOnSubscribe.directSetResult(this.mRightLightingPattern);
        AlphaOutOnSubscribe.directSetResult(this.mLeftAlertStatus);
        AlphaOutOnSubscribe.directSetResult(this.mLeftLightingPattern);
        if (z) {
            this.stringLightingRes = -1;
            this.mStateValueText = "";
        } else if (!DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171).equals("0")) {
            if (!isLandScape()) {
                int i = this.oldDegree;
                if (i == 90) {
                    starAnimatetViewGone(this.mRightAlertStatus, false);
                    starAnimatetViewGone(this.mRightLightingPattern, false);
                    starAnimatetViewGone(this.mLeftAlertStatus, z2);
                    starAnimatetViewGone(this.mLeftLightingPattern, z2);
                } else if (i == 270) {
                    starAnimatetViewGone(this.mLeftAlertStatus, false);
                    starAnimatetViewGone(this.mLeftLightingPattern, false);
                    starAnimatetViewGone(this.mRightAlertStatus, z2);
                    starAnimatetViewGone(this.mRightLightingPattern, z2);
                }
            } else if (isLeftLandScape()) {
                if (!TextUtils.isEmpty(this.mStateValueText)) {
                    startAnimateViewVisible(this.mLeftAlertStatus, z2);
                }
                if (this.stringLightingRes > 0) {
                    startAnimateViewVisible(this.mLeftLightingPattern, z2);
                }
            } else if (isRightLandScape()) {
                if (!TextUtils.isEmpty(this.mStateValueText)) {
                    startAnimateViewVisible(this.mRightAlertStatus, z2);
                }
                if (this.stringLightingRes > 0) {
                    startAnimateViewVisible(this.mRightLightingPattern, z2);
                }
            }
            this.oldDegree = ((BaseFragment) this).mDegree;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.VerticalProtocol
    public void alertLightingHint(int i) {
        int i2 = i != 3 ? i != 4 ? i != 5 ? -1 : R.string.lighting_hint_needed : R.string.lighting_hint_too_far : R.string.lighting_hint_too_close;
        if (i2 == -1) {
            this.mStateValueText = "";
            AlphaOutOnSubscribe.directSetResult(this.mRightAlertStatus);
            AlphaOutOnSubscribe.directSetResult(this.mLeftAlertStatus);
            return;
        }
        this.mStateValueText = getResources().getString(i2);
        this.mRightAlertStatus.setText(this.mStateValueText);
        this.mLeftAlertStatus.setText(this.mStateValueText);
        if (isLeftLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mLeftAlertStatus);
        } else if (isRightLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mRightAlertStatus);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VERTICAL;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vertical;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mVerticalViewMenu = view.findViewById(R.id.vertical_view_menu);
        Rect displayRect = Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0);
        int i = displayRect.top;
        int height = displayRect.height();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mVerticalViewMenu.getLayoutParams();
        marginLayoutParams.topMargin = i;
        marginLayoutParams.height = height;
        this.mLeftAlertStatus = (TextView) view.findViewById(R.id.alert_status_value_left);
        this.mLeftLightingPattern = (TextView) view.findViewById(R.id.lighting_pattern_left);
        this.mRightAlertStatus = (TextView) view.findViewById(R.id.alert_status_value_right);
        this.mRightLightingPattern = (TextView) view.findViewById(R.id.lighting_pattern_right);
        this.oldDegree = ((BaseFragment) this).mDegree;
        ViewCompat.setRotation(this.mLeftAlertStatus, 90.0f);
        ViewCompat.setRotation(this.mLeftLightingPattern, 90.0f);
        ViewCompat.setRotation(this.mRightAlertStatus, 270.0f);
        ViewCompat.setRotation(this.mRightLightingPattern, 270.0f);
    }

    @Override // com.android.camera.protocol.ModeProtocol.VerticalProtocol
    public boolean isAnyViewVisible() {
        return this.mLeftAlertStatus.getVisibility() == 0 || this.mRightAlertStatus.getVisibility() == 0 || this.mLeftLightingPattern.getVisibility() == 0 || this.mRightLightingPattern.getVisibility() == 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (isAnyViewVisible()) {
            updateLightingRelativeView(i2 == 3, false);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (((BaseFragment) this).mCurrentMode == 171) {
            updateLightingRelativeView(false, true);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(198, this);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.camera.protocol.ModeProtocol.VerticalProtocol
    public void setLightingPattern(String str) {
        char c2;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 49:
                if (str.equals("1")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 52:
                if (str.equals("4")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 53:
                if (str.equals("5")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 54:
                if (str.equals("6")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 55:
                if (str.equals("7")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 56:
                if (str.equals("8")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                this.stringLightingRes = -1;
                break;
            case 1:
                this.stringLightingRes = R.string.lighting_pattern_nature;
                break;
            case 2:
                this.stringLightingRes = R.string.lighting_pattern_stage;
                break;
            case 3:
                this.stringLightingRes = R.string.lighting_pattern_movie;
                break;
            case 4:
                this.stringLightingRes = R.string.lighting_pattern_rainbow;
                break;
            case 5:
                this.stringLightingRes = R.string.lighting_pattern_shutter;
                break;
            case 6:
                this.stringLightingRes = R.string.lighting_pattern_dot;
                break;
            case 7:
                this.stringLightingRes = R.string.lighting_pattern_leaf;
                break;
            case '\b':
                this.stringLightingRes = R.string.lighting_pattern_holi;
                break;
        }
        int i = this.stringLightingRes;
        if (i == -1) {
            AlphaOutOnSubscribe.directSetResult(this.mLeftLightingPattern);
            AlphaOutOnSubscribe.directSetResult(this.mRightLightingPattern);
            return;
        }
        this.mLeftLightingPattern.setText(i);
        this.mRightLightingPattern.setText(this.stringLightingRes);
        if (isLeftLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mLeftLightingPattern);
        } else if (isRightLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mRightLightingPattern);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(198, this);
    }
}
