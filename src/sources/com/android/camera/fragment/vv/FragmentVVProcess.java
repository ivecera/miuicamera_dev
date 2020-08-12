package com.android.camera.fragment.vv;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.RxData;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.fragment.vv.page.PageIndicatorView;
import com.android.camera.fragment.vv.page.PagerGridLayoutManager;
import com.android.camera.fragment.vv.page.PagerGridSnapHelper;
import com.android.camera.log.Log;
import com.android.camera.module.LiveModuleSubVV;
import com.android.camera.module.Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.TextureVideoView;
import com.android.camera.ui.vv.VVProgressView;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentVVProcess extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.LiveVVProcess, CameraSnapView.SnapListener {
    private static final String TAG = "FragmentVVProcess";
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private CameraSnapView mCameraSnapView;
    private ProgressBar mCombineProgress;
    private ProgressBar mConcatProgress;
    private int mCurrentIndex;
    private List<Long> mDurationList;
    private Handler mHandler;
    private boolean mIsIntentAction;
    private TextView mLandscapeHint;
    /* access modifiers changed from: private */
    public PageIndicatorView mPageIndicatorView;
    private PagerGridSnapHelper mPagerGridSnapHelper;
    private boolean mPaused;
    private boolean mPendingShare;
    private ImageView mPreviewBack;
    private ImageView mPreviewCombine;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewNext;
    private ImageView mPreviewPrevious;
    private ImageView mPreviewShare;
    /* access modifiers changed from: private */
    public ImageView mPreviewStart;
    private VVProgressView mProgressView;
    private View mRootView;
    private ContentValues mSaveContentValues;
    private Uri mSavedUri;
    private ImageView mSegmentPreview;
    private ImageView mSegmentReverse;
    private VVShareAdapter mShareAdapter;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    private TextView mStabilityHint;
    /* access modifiers changed from: private */
    public TextureVideoView mTextureVideoView;
    /* access modifiers changed from: private */
    public View mVVDialog;
    private TextView mVVDialogCancel;
    private TextView mVVDialogConfirm;
    private TextView mVVDialogMessage;
    /* access modifiers changed from: private */
    public View mVVShare;
    private TextView mVVShareMessage;
    private boolean mVideoRecordingPaused;
    private boolean mVideoRecordingStarted;
    private VMProcessing mVmProcessing;
    /* access modifiers changed from: private */
    public boolean mWaitingResultSurfaceTexture;

    private void animateIn(View view) {
        if (view.getVisibility() != 0) {
            Completable.create(new AlphaInOnSubscribe(view)).subscribe();
        }
    }

    private void animateOut(View view) {
        if (view.getVisibility() == 0) {
            Completable.create(new AlphaOutOnSubscribe(view)).subscribe();
        }
    }

    private boolean checkAndShare() {
        if (this.mSavedUri == null) {
            return false;
        }
        showShareSheet();
        return true;
    }

    private Intent getShareIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", this.mSavedUri);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        return intent;
    }

    /* access modifiers changed from: private */
    public boolean hideShareSheet() {
        if (this.mVVShare.getVisibility() != 0) {
            return false;
        }
        this.mVVShare.setVisibility(8);
        this.mPreviewStart.setVisibility(0);
        return true;
    }

    /* access modifiers changed from: private */
    public boolean hideVVDialog() {
        if (this.mVVDialog.getVisibility() != 0) {
            return false;
        }
        this.mVVDialog.setVisibility(8);
        return true;
    }

    private void initHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler() {
                /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass12 */

                public void handleMessage(Message message) {
                    super.handleMessage(message);
                }
            };
        }
    }

    private void initTextureView() {
        this.mTextureVideoView = new TextureVideoView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        Rect displayRect = Util.getDisplayRect();
        layoutParams.topMargin = displayRect.top;
        layoutParams.height = displayRect.height();
        this.mPreviewLayout.removeAllViews();
        this.mPreviewLayout.setBackground(null);
        this.mPreviewLayout.addView(this.mTextureVideoView, layoutParams);
        this.mTextureVideoView.setLoop(true);
        this.mTextureVideoView.setClearSurface(true);
        this.mTextureVideoView.setScaleType(6);
        this.mTextureVideoView.setVisibility(4);
        this.mTextureVideoView.setMediaPlayerCallback(new TextureVideoView.MediaPlayerCallback() {
            /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass6 */

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            }

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public void onCompletion(MediaPlayer mediaPlayer) {
                FragmentVVProcess.this.stopSegmentPreview(false);
            }

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Log.e("onError:", i + " | " + i2);
                return false;
            }

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                Log.e("onInfo:", i + " | " + i2);
                return false;
            }

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public void onPrepared(MediaPlayer mediaPlayer) {
            }

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public void onSurfaceReady(Surface surface) {
                if (FragmentVVProcess.this.mWaitingResultSurfaceTexture) {
                    boolean unused = FragmentVVProcess.this.mWaitingResultSurfaceTexture = false;
                    FragmentVVProcess.this.mTextureVideoView.setVideoSpecifiedSize(1920, 1080);
                    FragmentVVProcess.this.startPlay(surface);
                }
            }

            @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            }
        });
        this.mTextureVideoView.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass7 */

            public void onClick(View view) {
                if (FragmentVVProcess.this.mVVShare.getVisibility() != 0 && FragmentVVProcess.this.mVVDialog.getVisibility() != 0) {
                    FragmentVVProcess.this.pausePlay(false, true);
                }
            }
        });
    }

    private void intoResultPreview() {
        if (!this.mWaitingResultSurfaceTexture && !isFullSegmentsPlaying()) {
            Log.d(TAG, "startConcat");
            this.mVmProcessing.updateState(3);
            if (this.mTextureVideoView.getPreviewSurface() != null) {
                startPlay(this.mTextureVideoView.getPreviewSurface());
            } else {
                this.mWaitingResultSurfaceTexture = true;
            }
        }
    }

    private boolean isFullSegmentsPlaying() {
        return this.mVmProcessing.getCurrentState() == 6;
    }

    /* access modifiers changed from: private */
    public boolean isRecordingFinished() {
        List<Long> list = this.mDurationList;
        if (list == null) {
            return false;
        }
        for (Long l : list) {
            if (l.longValue() <= 0) {
                return false;
            }
        }
        return true;
    }

    private void onProcessingSateChanged(int i) {
        String str = TAG;
        Log.d(str, "newState: " + i);
        switch (i) {
            case 1:
            default:
                return;
            case 2:
                this.mPreviewStart.setVisibility(0);
                this.mConcatProgress.setVisibility(8);
                return;
            case 3:
                animateOut(this.mSegmentPreview);
                animateOut(this.mSegmentReverse);
                AlphaOutOnSubscribe.directSetResult(this.mPreviewNext);
                animateIn(this.mPreviewBack);
                animateIn(this.mPreviewShare);
                AlphaInOnSubscribe.directSetResult(this.mPreviewCombine);
                Drawable drawable = this.mPreviewCombine.getDrawable();
                if (drawable != null && (drawable instanceof Animatable)) {
                    ((Animatable) drawable).start();
                }
                animateIn(this.mConcatProgress);
                this.mPreviewLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                this.mTextureVideoView.setVisibility(0);
                return;
            case 4:
            case 5:
                this.mPreviewStart.setVisibility(0);
                this.mConcatProgress.setVisibility(8);
                return;
            case 6:
                this.mPreviewStart.setVisibility(8);
                this.mConcatProgress.setVisibility(8);
                return;
            case 7:
                if (this.mPendingShare) {
                    this.mPreviewShare.setVisibility(8);
                    this.mShareProgress.setVisibility(0);
                    return;
                }
                this.mPreviewStart.setVisibility(8);
                this.mPreviewCombine.setVisibility(8);
                this.mCombineProgress.setVisibility(0);
                return;
            case 8:
                onResultCombineFinished(true);
                return;
            case 9:
                onResultCombineFinished(false);
                return;
        }
    }

    private void onSegmentsChanged() {
        int i = 0;
        for (Long l : this.mDurationList) {
            if (l.longValue() > 0) {
                i++;
            }
        }
        animateIn(this.mProgressView);
        if (i == 0) {
            animateOut(this.mSegmentReverse);
            animateOut(this.mSegmentPreview);
            animateOut(this.mPreviewNext);
        } else if (i == this.mDurationList.size()) {
            animateIn(this.mSegmentReverse);
            animateIn(this.mSegmentPreview);
            animateIn(this.mPreviewNext);
        } else {
            animateIn(this.mSegmentReverse);
            animateIn(this.mSegmentPreview);
            animateOut(this.mPreviewNext);
        }
    }

    /* access modifiers changed from: private */
    public void pausePlay(boolean z, boolean z2) {
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null && textureVideoView.isPlaying()) {
            stopSegmentPreview(z2);
        } else if (isFullSegmentsPlaying()) {
            if (z) {
                this.mVmProcessing.updateState(2);
            } else {
                this.mVmProcessing.updateState(5);
            }
            ModeProtocol.LiveConfigVV liveConfigVV = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            if (liveConfigVV != null) {
                if (z2) {
                    CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_ALL_PAUSE);
                }
                liveConfigVV.pausePlay();
            }
        } else if (z && this.mVmProcessing.getCurrentState() == 5) {
            this.mVmProcessing.updateState(2);
        }
    }

    private void previewLastSegment() {
        int i = 0;
        for (int i2 = 0; i2 < this.mDurationList.size(); i2++) {
            if (this.mDurationList.get(i2).longValue() > 0) {
                i = i2;
            }
        }
        this.mBottomActionView.setVisibility(4);
        this.mPreviewLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.mTextureVideoView.setVisibility(0);
        this.mTextureVideoView.setVideoPath(((ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228)).getSegmentPath(i));
        this.mTextureVideoView.start();
    }

    private void resumePlay() {
        if (!isFullSegmentsPlaying()) {
            if (this.mVmProcessing.getCurrentState() != 5) {
                intoResultPreview();
                return;
            }
            ModeProtocol.LiveConfigVV liveConfigVV = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            if (liveConfigVV == null) {
                Log.w(TAG, "resumePlay failed, vv is null");
                return;
            }
            this.mVmProcessing.updateState(6);
            liveConfigVV.resumePlay();
        }
    }

    private void shareMore() {
        try {
            getContext().startActivity(Intent.createChooser(getShareIntent(), getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            String str = TAG;
            Log.e(str, "failed to share video " + this.mSavedUri, e2);
        }
    }

    private void showReverseConfirmDialog() {
        if (this.mVVDialog.getVisibility() != 0) {
            CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_REVERSE);
            this.mVVDialogMessage.setText(R.string.live_reverse_message);
            this.mVVDialogConfirm.setText(R.string.live_reverse_confirm);
            this.mVVDialogCancel.setText(R.string.snap_cancel);
            this.mVVDialogConfirm.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass1 */

                public void onClick(View view) {
                    CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_REVERSE_CONFIRM);
                    ((LiveModuleSubVV) ((ActivityBase) FragmentVVProcess.this.getContext()).getCurrentModule()).doReverse();
                    boolean unused = FragmentVVProcess.this.hideVVDialog();
                }
            });
            this.mVVDialogCancel.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass2 */

                public void onClick(View view) {
                    boolean unused = FragmentVVProcess.this.hideVVDialog();
                }
            });
            this.mVVDialog.setVisibility(0);
        }
    }

    private void showShareSheet() {
        this.mPendingShare = false;
        if (!this.mPaused) {
            pausePlay(false, false);
            Intent shareIntent = getShareIntent();
            PackageManager packageManager = getContext().getPackageManager();
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(shareIntent, 65536);
            if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
                Log.d(TAG, "no IntentActivities");
                return;
            }
            int i = this.mShareRecyclerView.getLayoutParams().width / 4;
            VVShareAdapter vVShareAdapter = this.mShareAdapter;
            if (vVShareAdapter == null || vVShareAdapter.getItemCount() != queryIntentActivities.size()) {
                this.mShareAdapter = new VVShareAdapter(packageManager, queryIntentActivities, this, i);
                PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(2, 4, 1);
                pagerGridLayoutManager.setPageListener(new PagerGridLayoutManager.PageListener() {
                    /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass10 */

                    @Override // com.android.camera.fragment.vv.page.PagerGridLayoutManager.PageListener
                    public void onPageSelect(int i) {
                        FragmentVVProcess.this.mPageIndicatorView.setSelectedPage(i);
                    }

                    @Override // com.android.camera.fragment.vv.page.PagerGridLayoutManager.PageListener
                    public void onPageSizeChanged(int i) {
                    }
                });
                int ceil = (int) Math.ceil((double) (((float) queryIntentActivities.size()) / 8.0f));
                this.mPageIndicatorView.initIndicator(ceil);
                if (ceil <= 1) {
                    this.mPageIndicatorView.setVisibility(8);
                } else {
                    this.mPageIndicatorView.setVisibility(0);
                }
                if (this.mPagerGridSnapHelper == null) {
                    this.mPagerGridSnapHelper = new PagerGridSnapHelper();
                    this.mShareRecyclerView.setLayoutManager(pagerGridLayoutManager);
                    this.mPagerGridSnapHelper.attachToRecyclerView(this.mShareRecyclerView);
                }
                this.mShareRecyclerView.setAdapter(this.mShareAdapter);
                this.mVVShareMessage.setText(R.string.snap_cancel);
                this.mVVShareMessage.setOnClickListener(new View.OnClickListener() {
                    /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass11 */

                    public void onClick(View view) {
                        boolean unused = FragmentVVProcess.this.hideShareSheet();
                    }
                });
            } else {
                this.mShareAdapter.setShareInfoList(queryIntentActivities);
                this.mShareAdapter.notifyDataSetChanged();
            }
            this.mPreviewStart.setVisibility(8);
            Completable.create(new AlphaInOnSubscribe(this.mVVShare)).subscribe();
        }
    }

    /* access modifiers changed from: private */
    public void startPlay(Surface surface) {
        if (!isFullSegmentsPlaying()) {
            this.mVmProcessing.updateState(6);
            ModeProtocol.LiveConfigVV liveConfigVV = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            if (liveConfigVV != null) {
                liveConfigVV.startPlay(surface);
            }
        }
    }

    private void startSave() {
        Log.d(TAG, "startSave");
        if (!this.mWaitingResultSurfaceTexture) {
            this.mVmProcessing.updateState(7);
            final String asString = this.mSaveContentValues.getAsString("_data");
            Completable.create(new CompletableOnSubscribe() {
                /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass5 */

                @Override // io.reactivex.CompletableOnSubscribe
                public void subscribe(CompletableEmitter completableEmitter) throws Exception {
                    ModeProtocol.LiveConfigVV liveConfigVV = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
                    if (liveConfigVV != null) {
                        liveConfigVV.combineVideoAudio(asString);
                    }
                    completableEmitter.onComplete();
                }
            }).subscribeOn(GlobalConstant.sCameraSetupScheduler).subscribe();
        }
    }

    private void startShare(String str, String str2) {
        ComponentName componentName = new ComponentName(str, str2);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.STREAM", this.mSavedUri);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        try {
            getContext().startActivity(Intent.createChooser(intent, getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            String str3 = TAG;
            Log.e(str3, "failed to share video " + this.mSavedUri, e2);
        }
    }

    /* access modifiers changed from: private */
    public void stopSegmentPreview(boolean z) {
        if (z) {
            CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_SEGMENT_EXIT);
        }
        this.mTextureVideoView.stop();
        this.mTextureVideoView.setVisibility(4);
        this.mBottomActionView.setVisibility(0);
        this.mPreviewLayout.setBackground(null);
    }

    public /* synthetic */ void a(RxData.DataWrap dataWrap) throws Exception {
        onProcessingSateChanged(((Integer) dataWrap.get()).intValue());
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public boolean canSnap() {
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_PROCESS;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_process;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public ContentValues getSaveContentValues() {
        return this.mSaveContentValues;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.vv_preview_layout);
        this.mConcatProgress = (ProgressBar) view.findViewById(R.id.vv_concat_progress);
        this.mCombineProgress = (ProgressBar) view.findViewById(R.id.vv_save_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.vv_share_progress);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.vv_preview_save_circle);
        this.mCameraSnapView.setSnapListener(this);
        this.mPreviewCombine = (ImageView) view.findViewById(R.id.vv_preview_save);
        this.mPreviewBack = (ImageView) view.findViewById(R.id.vv_preview_back);
        this.mPreviewShare = (ImageView) view.findViewById(R.id.vv_preview_share);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.vv_preview_play);
        this.mPreviewNext = (ImageView) view.findViewById(R.id.vv_preview_next);
        this.mSegmentReverse = (ImageView) view.findViewById(R.id.vv_segment_reverse);
        this.mSegmentPreview = (ImageView) view.findViewById(R.id.vv_segment_preview);
        this.mLandscapeHint = (TextView) view.findViewById(R.id.vv_process_hint);
        this.mStabilityHint = (TextView) view.findViewById(R.id.vv_stability_hint);
        this.mVVDialog = view.findViewById(R.id.vv_dialog);
        this.mVVDialogMessage = (TextView) this.mVVDialog.findViewById(R.id.vv_dialog_message);
        this.mVVDialogConfirm = (TextView) this.mVVDialog.findViewById(R.id.vv_dialog_positive);
        this.mVVDialogCancel = (TextView) this.mVVDialog.findViewById(R.id.vv_dialog_negative);
        this.mVVShare = view.findViewById(R.id.vv_share);
        this.mVVShareMessage = (TextView) this.mVVShare.findViewById(R.id.vv_share_message);
        this.mShareRecyclerView = (RecyclerView) this.mVVShare.findViewById(R.id.vv_share_recyclerview);
        this.mShareRecyclerView.setFocusable(false);
        this.mPageIndicatorView = (PageIndicatorView) this.mVVShare.findViewById(R.id.vv_share_recyclerview_indicator);
        this.mProgressView = (VVProgressView) view.findViewById(R.id.vv_progress);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mProgressView.getLayoutParams();
        Rect displayRect = Util.getDisplayRect();
        int i = displayRect.top;
        marginLayoutParams.topMargin = ((displayRect.height() - marginLayoutParams.height) / 2) + i;
        int height = (i - ((Util.sWindowHeight - i) - displayRect.height())) / 2;
        ((ViewGroup.MarginLayoutParams) this.mLandscapeHint.getLayoutParams()).topMargin = height;
        ((ViewGroup.MarginLayoutParams) this.mStabilityHint.getLayoutParams()).topMargin = height;
        ((ViewGroup.MarginLayoutParams) this.mVVDialog.getLayoutParams()).topMargin = height;
        ((ViewGroup.MarginLayoutParams) this.mVVShare.getLayoutParams()).topMargin = height;
        ViewCompat.setRotation(this.mPreviewStart, 90.0f);
        ViewCompat.setRotation(this.mCameraSnapView, 90.0f);
        ViewCompat.setRotation(this.mPreviewCombine, 90.0f);
        ViewCompat.setRotation(this.mPreviewBack, 90.0f);
        ViewCompat.setRotation(this.mPreviewShare, 90.0f);
        ViewCompat.setRotation(this.mPreviewNext, 90.0f);
        ViewCompat.setRotation(this.mSegmentReverse, 90.0f);
        ViewCompat.setRotation(this.mSegmentPreview, 90.0f);
        ViewCompat.setRotation(this.mLandscapeHint, 90.0f);
        ViewCompat.setRotation(this.mStabilityHint, 90.0f);
        ViewCompat.setRotation(this.mVVDialog, 90.0f);
        ViewCompat.setRotation(this.mVVShare, 90.0f);
        this.mCameraSnapView.setOnClickListener(this);
        this.mPreviewCombine.setOnClickListener(this);
        this.mPreviewBack.setOnClickListener(this);
        this.mPreviewShare.setOnClickListener(this);
        this.mPreviewStart.setOnClickListener(this);
        this.mSegmentReverse.setOnClickListener(this);
        this.mSegmentPreview.setOnClickListener(this);
        this.mPreviewNext.setOnClickListener(this);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.vv_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.vv_preview_bottom_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams();
        marginLayoutParams2.height = Math.round(((float) Util.sBottomBarHeight) * 0.7f);
        marginLayoutParams2.bottomMargin = Util.sBottomMargin;
        marginLayoutParams2.topMargin = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        if (((BaseFragment) this).mCurrentMode != 179) {
            quit();
        }
        this.mVmProcessing = (VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class);
        this.mVmProcessing.startObservable(this, new c(this));
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView == null || !textureVideoView.isPlaying()) {
            return hideVVDialog() || hideShareSheet();
        }
        stopSegmentPreview(true);
        return true;
    }

    public void onClick(View view) {
        if (this.mConcatProgress.getVisibility() != 0 && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0) {
            if (view.getId() == R.id.live_share_item || !(this.mVVShare.getVisibility() == 0 || this.mVVDialog.getVisibility() == 0)) {
                switch (view.getId()) {
                    case R.id.live_share_item:
                        hideShareSheet();
                        ResolveInfo resolveInfo = (ResolveInfo) view.getTag();
                        startShare(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                        return;
                    case R.id.vv_preview_back:
                        CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_EXIT);
                        showExitConfirm();
                        return;
                    case R.id.vv_preview_next:
                        if (isRecordingFinished()) {
                            CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_ALL);
                            intoResultPreview();
                            return;
                        }
                        return;
                    case R.id.vv_preview_play:
                        CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_ALL_RESUME);
                        resumePlay();
                        return;
                    case R.id.vv_preview_save:
                        CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_SAVE);
                        if (this.mSavedUri != null) {
                            quitLiveRecordPreview(true);
                            return;
                        } else {
                            startSave();
                            return;
                        }
                    case R.id.vv_preview_share:
                        CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_SHARE);
                        if (!checkAndShare()) {
                            this.mPendingShare = true;
                            startSave();
                            return;
                        }
                        return;
                    case R.id.vv_segment_preview:
                        VMProcessing vMProcessing = this.mVmProcessing;
                        if (vMProcessing == null || vMProcessing.getCurrentState() == 1) {
                            CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_PLAY_SEGMENT);
                            previewLastSegment();
                            return;
                        }
                        return;
                    case R.id.vv_segment_reverse:
                        VMProcessing vMProcessing2 = this.mVmProcessing;
                        if (vMProcessing2 == null || vMProcessing2.getCurrentState() == 1) {
                            CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_REVERSE);
                            showReverseConfirmDialog();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onCombinePrepare(ContentValues contentValues) {
        this.mSavedUri = null;
        this.mSaveContentValues = contentValues;
        initHandler();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onKeyCodeCamera() {
        if (isAdded() && isRecordingFinished()) {
            onSnapClick();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onLiveSaveToLocalFinished(Uri uri) {
        this.mSavedUri = uri;
        if (this.mPendingShare) {
            this.mPreviewShare.setVisibility(0);
            this.mShareProgress.setVisibility(8);
            showShareSheet();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mPaused = true;
        pausePlay(true, false);
        hideVVDialog();
        hideShareSheet();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onRecordingFragmentUpdate(int i, long j) {
        this.mVideoRecordingStarted = false;
        this.mDurationList.set(i, Long.valueOf(j));
        this.mProgressView.updateDuration(i, j);
        onSegmentsChanged();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onRecordingNewFragmentStart(int i, long j) {
        this.mCurrentIndex = i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onResultCombineFinished(boolean z) {
        Log.d(TAG, "combineSuccess");
        if (this.mPendingShare) {
            Log.d(TAG, "combineSuccess and share");
            ((LiveModuleSubVV) ((ActivityBase) getContext()).getCurrentModule()).startSaveToLocal();
            return;
        }
        Log.d(TAG, "combineSuccess and finish");
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass9 */

            public void run() {
                FragmentVVProcess.this.quitLiveRecordPreview(true);
            }
        });
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void onResultPreviewFinished(boolean z) {
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass8 */

            public void run() {
                FragmentVVProcess.this.mPreviewStart.setVisibility(0);
            }
        });
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.mPaused = false;
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapClick() {
        ModeProtocol.CameraAction cameraAction;
        if (this.mConcatProgress.getVisibility() != 0 && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0) {
            if (this.mPreviewNext.getVisibility() == 0) {
                this.mPreviewNext.performClick();
            } else if (this.mPreviewCombine.getVisibility() == 0) {
                this.mPreviewCombine.performClick();
            } else {
                Module currentModule = ((Camera) getContext()).getCurrentModule();
                if ((currentModule == null || !currentModule.isIgnoreTouchEvent() || currentModule.isFrameAvailable()) && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null && ((BaseFragment) this).mCurrentMode == 179 && !this.mVideoRecordingStarted) {
                    CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_START_SEGMENT);
                    cameraAction.onShutterButtonClick(10);
                }
            }
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPress() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPressCancelIn() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPressCancelOut() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapPrepare() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onTrackSnapMissTaken(long j) {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onTrackSnapTaken(long j) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void prepare(VVItem vVItem) {
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        this.mRootView.setVisibility(0);
        ((BaseFragment) this).mCurrentMode = 179;
        this.mCameraSnapView.setParameters(((BaseFragment) this).mCurrentMode, false, false);
        this.mCameraSnapView.hideRoundPaintItem();
        this.mCameraSnapView.showPaintCenterVVItem();
        this.mConcatProgress.setVisibility(8);
        this.mCombineProgress.setVisibility(8);
        this.mShareProgress.setVisibility(8);
        this.mPreviewCombine.setVisibility(8);
        this.mPreviewBack.setVisibility(8);
        this.mPreviewShare.setVisibility(8);
        this.mPreviewStart.setVisibility(8);
        this.mPreviewNext.setVisibility(8);
        this.mSegmentReverse.setVisibility(8);
        this.mSegmentPreview.setVisibility(8);
        this.mLandscapeHint.setVisibility(0);
        this.mStabilityHint.setVisibility(8);
        this.mDurationList = new ArrayList(vVItem.durationList.size());
        for (Long l : vVItem.durationList) {
            this.mDurationList.add(Long.valueOf(-l.longValue()));
        }
        this.mProgressView.setDurationList(this.mDurationList);
        AlphaInOnSubscribe.directSetResult(this.mProgressView);
        initTextureView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void processingFinish() {
        this.mVideoRecordingStarted = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void processingPause() {
        this.mStabilityHint.setVisibility(8);
        this.mCameraSnapView.triggerAnimation(BottomAnimationConfig.generate(false, ((BaseFragment) this).mCurrentMode, false, false, false).configVariables());
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void processingPrepare() {
        this.mVideoRecordingStarted = true;
        this.mCameraSnapView.prepareRecording(BottomAnimationConfig.generate(false, ((BaseFragment) this).mCurrentMode, true, false, false).configVariables());
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void processingResume() {
        long j;
        animateOut(this.mSegmentReverse);
        animateOut(this.mSegmentPreview);
        animateOut(this.mProgressView);
        this.mLandscapeHint.setVisibility(8);
        if (this.mCurrentIndex == 0) {
            this.mStabilityHint.setVisibility(0);
        }
        BottomAnimationConfig configVariables = BottomAnimationConfig.generate(false, ((BaseFragment) this).mCurrentMode, true, false, false).configVariables();
        Iterator<Long> it = this.mDurationList.iterator();
        while (true) {
            j = 0;
            if (!it.hasNext()) {
                break;
            }
            long longValue = it.next().longValue();
            if (longValue < 0) {
                j = Math.abs(longValue);
                break;
            }
        }
        configVariables.setSpecifiedDuration((int) j);
        this.mCameraSnapView.triggerAnimation(configVariables);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void processingStart() {
        processingResume();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void quit() {
        this.mRootView.setVisibility(8);
    }

    public void quitLiveRecordPreview(boolean z) {
        if (this.mConcatProgress.getVisibility() == 0) {
            this.mConcatProgress.setVisibility(8);
        }
        if (this.mCombineProgress.getVisibility() == 0) {
            this.mCombineProgress.setVisibility(8);
        }
        if (this.mShareProgress.getVisibility() == 0) {
            this.mShareProgress.setVisibility(8);
        }
        pausePlay(false, false);
        this.mVmProcessing.reset();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(TAG, "concat error, action null");
        } else if (z) {
            cameraAction.onReviewDoneClicked();
        } else {
            cameraAction.onReviewCancelClicked();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(230, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void showExitConfirm() {
        if (this.mVVDialog.getVisibility() != 0) {
            if (isRecordingFinished()) {
                CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_FINISH_EXIT);
            } else {
                CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_EXIT);
            }
            this.mVVDialogMessage.setText(R.string.live_edit_exit_message);
            this.mVVDialogConfirm.setText(R.string.live_edit_exit_confirm);
            this.mVVDialogCancel.setText(R.string.snap_cancel);
            this.mVVDialogConfirm.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass3 */

                public void onClick(View view) {
                    if (FragmentVVProcess.this.isRecordingFinished()) {
                        CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_FINISH_EXIT_CONFIRM);
                    } else {
                        CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_CLICK_EXIT_CONFIRM);
                    }
                    FragmentVVProcess.this.quitLiveRecordPreview(false);
                    boolean unused = FragmentVVProcess.this.hideVVDialog();
                }
            });
            this.mVVDialogCancel.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.fragment.vv.FragmentVVProcess.AnonymousClass4 */

                public void onClick(View view) {
                    boolean unused = FragmentVVProcess.this.hideVVDialog();
                }
            });
            Completable.create(new AlphaInOnSubscribe(this.mVVDialog)).subscribe();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(230, this);
        unRegisterBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVProcess
    public void updateRecordingTime(String str) {
        this.mCameraSnapView.setDurationText(str);
    }
}
