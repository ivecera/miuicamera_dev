package com.android.camera.fragment.subtitle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.subtitle.recog.VoiceOnlineRecog;
import com.android.camera.module.loader.StartControl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import java.util.List;

public class FragmentSubtitle extends BaseFragment implements ModeProtocol.HandleBackTrace, ModeProtocol.SubtitleRecording, CtaNoticeFragment.OnCtaNoticeClickListener {
    private static final int SUBTITLE_CHECK_NETWORK = 102;
    private static final int SUBTITLE_CINEMATICASPECTRATIO_OFFSET = Util.dpToPixel(26.2f);
    private static final int SUBTITLE_FLIP_ROTATE = Util.dpToPixel(32.0f);
    private static final int SUBTITLE_LANDSCAPE_OFFSET = Util.dpToPixel(229.09f);
    private static final int SUBTITLE_NO_SPEAK_TIP = 101;
    private static final int SUBTITLE_SHOW_TIME_END = 100;
    private static final int SUBTITLE_VERTICAL_OFFSET = Util.dpToPixel(188.36f);
    private static final String TAG = "FragmentSubtitle";
    /* access modifiers changed from: private */
    public boolean isNetWorkAvailable;
    private boolean isRecording;
    private CtaNoticeFragment mCtaNoticeFragment;
    /* access modifiers changed from: private */
    public TextView mFlipShow;
    /* access modifiers changed from: private */
    public TextView mLeftShow;
    /* access modifiers changed from: private */
    public RecognitionListener mRecognitionListener;
    /* access modifiers changed from: private */
    public TextView mRightShow;
    private SoundWaveView mSwViewFilp;
    private SoundWaveView mSwViewLeft;
    private SoundWaveView mSwViewRight;
    private SoundWaveView mSwViewVertical;
    /* access modifiers changed from: private */
    public TextView mVerticalShow;
    private VoiceOnlineRecog mVoiceOnlineRecog;
    /* access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    public Handler subtitleHandler = new Handler() {
        /* class com.android.camera.fragment.subtitle.FragmentSubtitle.AnonymousClass1 */

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 100:
                    if (FragmentSubtitle.this.mLeftShow != null && FragmentSubtitle.this.mRightShow != null && FragmentSubtitle.this.mVerticalShow != null && FragmentSubtitle.this.mFlipShow != null) {
                        FragmentSubtitle.this.mLeftShow.setText("");
                        FragmentSubtitle.this.mRightShow.setText("");
                        FragmentSubtitle.this.mVerticalShow.setText("");
                        FragmentSubtitle.this.mFlipShow.setText("");
                        return;
                    }
                    return;
                case 101:
                    if (FragmentSubtitle.this.isAdded()) {
                        FragmentSubtitle.this.mRecognitionListener.onRecognitionListener(FragmentSubtitle.this.getResources().getString(R.string.hint_subtitle_prompt));
                        return;
                    }
                    return;
                case 102:
                    FragmentSubtitle.this.checkNetWorkStatus();
                    return;
                default:
                    return;
            }
        }
    };
    private View subtitleView;
    private int top;
    private ModeProtocol.TopAlert topAlert;
    private int verticalViewHeight;

    public class RecognitionListener {
        public RecognitionListener() {
        }

        public void onFailure() {
            FragmentSubtitle.this.checkNetWorkStatus();
            if (FragmentSubtitle.this.isNetWorkAvailable) {
                FragmentSubtitle.this.subtitleHandler.sendEmptyMessageDelayed(102, 500);
            }
        }

        @RequiresApi(api = 21)
        public void onRecognitionListener(String str) {
            FragmentSubtitle.this.subtitleHandler.removeCallbacksAndMessages(null);
            FragmentSubtitle.this.mLeftShow.setText(str);
            FragmentSubtitle.this.mRightShow.setText(str);
            FragmentSubtitle.this.mVerticalShow.setText(str);
            FragmentSubtitle.this.mFlipShow.setText(str);
            if (!FragmentSubtitle.this.isAdded() || !FragmentSubtitle.this.getResources().getString(R.string.hint_subtitle_prompt).equals(str)) {
                FragmentSubtitle.this.mLeftShow.setLetterSpacing(0.05f);
                FragmentSubtitle.this.mRightShow.setLetterSpacing(0.05f);
                FragmentSubtitle.this.mVerticalShow.setLetterSpacing(0.05f);
                FragmentSubtitle.this.mFlipShow.setLetterSpacing(0.05f);
                FragmentSubtitle.this.subtitleHandler.sendEmptyMessageDelayed(100, 2000);
                return;
            }
            FragmentSubtitle.this.mLeftShow.setLetterSpacing(0.0f);
            FragmentSubtitle.this.mRightShow.setLetterSpacing(0.0f);
            FragmentSubtitle.this.mVerticalShow.setLetterSpacing(0.0f);
            FragmentSubtitle.this.mFlipShow.setLetterSpacing(0.0f);
            FragmentSubtitle.this.subtitleHandler.sendEmptyMessageDelayed(100, 5000);
        }
    }

    private void setAnyViewGone() {
        this.mRightShow.setVisibility(8);
        this.mLeftShow.setVisibility(8);
        this.mVerticalShow.setVisibility(8);
        this.mFlipShow.setVisibility(8);
        this.mSwViewLeft.setVisibility(8);
        this.mSwViewFilp.setVisibility(8);
        this.mSwViewVertical.setVisibility(8);
        this.mSwViewRight.setVisibility(8);
        this.mSwViewLeft.resetAnimation();
        this.mSwViewRight.resetAnimation();
        this.mSwViewVertical.resetAnimation();
        this.mSwViewFilp.resetAnimation();
    }

    private void updateLightingRelativeView() {
        setAnyViewGone();
        if (isLandScape()) {
            ((ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams()).topMargin = ((this.verticalViewHeight - SUBTITLE_LANDSCAPE_OFFSET) / 2) + this.top;
            if (isLeftLandScape()) {
                this.mLeftShow.setVisibility(0);
                this.mSwViewLeft.setVisibility(0);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mSwViewLeft.getLayoutParams();
                if (CameraSettings.isCinematicAspectRatioEnabled(((BaseFragment) this).mCurrentMode)) {
                    marginLayoutParams.leftMargin = (Util.getCinematicAspectRatioMargin() - this.mSwViewLeft.getWidth()) / 2;
                } else {
                    marginLayoutParams.leftMargin = SUBTITLE_CINEMATICASPECTRATIO_OFFSET;
                }
                this.mSwViewLeft.startAnimation();
            } else if (isRightLandScape()) {
                this.mRightShow.setVisibility(0);
                this.mSwViewRight.setVisibility(0);
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mSwViewRight.getLayoutParams();
                if (CameraSettings.isCinematicAspectRatioEnabled(((BaseFragment) this).mCurrentMode)) {
                    marginLayoutParams2.rightMargin = (Util.getCinematicAspectRatioMargin() - this.mSwViewLeft.getWidth()) / 2;
                } else {
                    marginLayoutParams2.rightMargin = SUBTITLE_CINEMATICASPECTRATIO_OFFSET;
                }
                this.mRightShow.setVisibility(0);
                this.mSwViewRight.setVisibility(0);
                this.mSwViewRight.startAnimation();
            }
        } else if (isFlipRotate()) {
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams();
            if (this.topAlert.isTopToastShowing()) {
                marginLayoutParams3.topMargin = this.top + SUBTITLE_FLIP_ROTATE;
            } else {
                marginLayoutParams3.topMargin = this.top;
            }
            this.mFlipShow.setVisibility(0);
            this.mSwViewFilp.setVisibility(0);
            this.mSwViewFilp.startAnimation();
        } else {
            ((ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams()).topMargin = this.top - SUBTITLE_VERTICAL_OFFSET;
            this.mVerticalShow.setVisibility(0);
            this.mSwViewVertical.setVisibility(0);
            this.mSwViewVertical.startAnimation();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    public void checkNetWorkStatus() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            this.isNetWorkAvailable = false;
            Log.d(TAG, "checkNetWorkStatus: netWork is unavailable");
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                /* class com.android.camera.fragment.subtitle.FragmentSubtitle.AnonymousClass2 */

                public void run() {
                    if (FragmentSubtitle.this.isAdded()) {
                        ToastUtils.showToast(FragmentSubtitle.this.getContext(), FragmentSubtitle.this.getResources().getString(R.string.live_sticker_network_error_hint), 80);
                    }
                }
            });
            return;
        }
        this.isNetWorkAvailable = true;
        Log.d(TAG, "checkNetWorkStatus: netWork is available ");
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_SUBTITLE;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_subtitle;
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    public String getSubtitleContent() {
        return this.mVoiceOnlineRecog.getSubtitleContent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    @RequiresApi(api = 21)
    public void handleSubtitleRecordingPause() {
        Log.d(TAG, "handleSubtitleRecordingPause: ");
        this.isRecording = false;
        setAnyViewGone();
        this.subtitleView.setVisibility(8);
        this.mRecognitionListener.onRecognitionListener("");
        this.mVoiceOnlineRecog.pauseRecording();
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    public void handleSubtitleRecordingResume() {
        Log.d(TAG, "handleSubtitleRecordingResume: ");
        this.isRecording = true;
        this.subtitleView.setVisibility(0);
        updateLightingRelativeView();
        this.mVoiceOnlineRecog.resumeRecording();
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    public void handleSubtitleRecordingStart() {
        checkNetWorkStatus();
        CameraStatUtils.trackSubtitleRecordingStart();
        Log.d(TAG, "handleSubtitleRecordingStart: ");
        this.topAlert.alertSubtitleHint(8, R.string.pref_video_subtitle);
        this.isRecording = true;
        this.subtitleView.setVisibility(0);
        updateLightingRelativeView();
        if (this.isNetWorkAvailable) {
            this.subtitleHandler.sendEmptyMessageDelayed(101, 2000);
            this.mVoiceOnlineRecog.startRecording();
            return;
        }
        CameraStatUtils.trackWithoutNetwork();
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    @RequiresApi(api = 21)
    public void handleSubtitleRecordingStop() {
        Log.d(TAG, "handleSubtitleRecordingStop: ");
        this.topAlert.alertSubtitleHint(0, R.string.pref_video_subtitle);
        this.isRecording = false;
        this.subtitleView.setVisibility(0);
        this.mRecognitionListener.onRecognitionListener("");
        this.mVoiceOnlineRecog.stopRecording();
        setAnyViewGone();
    }

    @Override // com.android.camera.protocol.ModeProtocol.SubtitleRecording
    public void initPermission() {
        if (!((DataItemGlobal) DataRepository.provider().dataGlobal()).getCTACanCollect()) {
            this.mCtaNoticeFragment = CtaNoticeFragment.showCta(getActivity().getFragmentManager(), false, this, 2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.subtitleView = view.findViewById(R.id.subtitle_view_menu);
        Rect displayRect = Util.getDisplayRect(1);
        this.top = displayRect.top;
        this.verticalViewHeight = displayRect.height();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams();
        marginLayoutParams.topMargin = this.top;
        marginLayoutParams.height = this.verticalViewHeight;
        this.mLeftShow = (TextView) view.findViewById(R.id.voice_value_left);
        this.mRightShow = (TextView) view.findViewById(R.id.voice_value_right);
        this.mFlipShow = (TextView) view.findViewById(R.id.voice_value_flip);
        this.mVerticalShow = (TextView) view.findViewById(R.id.voice_value_vertical);
        this.mSwViewVertical = (SoundWaveView) view.findViewById(R.id.speed_sw_view);
        this.mSwViewLeft = (SoundWaveView) view.findViewById(R.id.speed_sw_view_left);
        this.mSwViewRight = (SoundWaveView) view.findViewById(R.id.speed_sw_view_right);
        this.mSwViewFilp = (SoundWaveView) view.findViewById(R.id.speed_sw_view_flip);
        registerProtocol();
        this.topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        this.mRecognitionListener = new RecognitionListener();
        this.mVoiceOnlineRecog = new VoiceOnlineRecog(getContext(), new RecognitionListener());
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        CtaNoticeFragment ctaNoticeFragment = this.mCtaNoticeFragment;
        if (ctaNoticeFragment != null) {
            ctaNoticeFragment.dismiss();
        }
        this.mVoiceOnlineRecog.onDestroy();
    }

    @Override // com.android.camera.fragment.CtaNoticeFragment.OnCtaNoticeClickListener
    public void onNegativeClick(DialogInterface dialogInterface, int i) {
        this.subtitleView.setVisibility(8);
        CameraSettings.setSubtitleEnabled(((BaseFragment) this).mCurrentMode, false);
        DataRepository.dataItemGlobal().setCurrentMode(162);
        ((Camera) getActivity()).onModeSelected(StartControl.create(162).setViewConfigType(1).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    @Override // com.android.camera.fragment.CtaNoticeFragment.OnCtaNoticeClickListener
    public void onPositiveClick(DialogInterface dialogInterface, int i) {
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(-1, -1);
    }

    /* access modifiers changed from: protected */
    public Animation provideExitAnimation() {
        return FragmentAnimationFactory.wrapperAnimation(-1, -1);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (DataRepository.dataItemRunning().getComponentRunningSubtitle().isEnabled(((BaseFragment) this).mCurrentMode) && this.isRecording) {
            updateLightingRelativeView();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(231, this);
        registerBackStack(modeCoordinator, this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(231, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
