package com.android.camera.fragment.fullscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.constant.ShareConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.mimoji.FragmentMimojiEdit;
import com.android.camera.log.Log;
import com.android.camera.module.LiveModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.CameraSnapView;
import com.ss.android.vesdk.TERecorder;
import com.ss.android.vesdk.VECommonCallback;
import d.h.a.j;
import d.h.a.x;
import d.h.a.z;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentFullScreen extends BaseFragment implements ModeProtocol.FullScreenProtocol, ModeProtocol.HandleBackTrace, View.OnClickListener {
    public static final int FRAGMENT_INFO = 4086;
    private static final String TAG = "FullScreen";
    private static final int WAIT_RECORD_SEGMENT_CREATED = 0;
    private static final int WAIT_RECORD_SEGMENT_CREATED_TIME_OUT = 5000;
    private static final int WAIT_RECORD_SEGMENT_ERROR_TIME_OUT = 5000;
    private static final int WAIT_RECORD_SEGMENT_TO_CAPTURE = 1;
    private static final int WAIT_RECORD_SEGMENT_WHEN_ERROR = 2;
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private CameraSnapView mCameraSnapView;
    private ProgressBar mCancelProgress;
    private VECommonCallback mCombineListener;
    /* access modifiers changed from: private */
    public ProgressBar mCombineProgress;
    private boolean mCombineReady;
    private TERecorder.OnConcatFinishedListener mConcatListener;
    private ProgressBar mConcatProgress;
    private boolean mConcatReady;
    private VECommonCallback mErrorListener;
    /* access modifiers changed from: private */
    public AlertDialog mExitDialog;
    /* access modifiers changed from: private */
    public Bitmap mFirstFrame;
    private FragmentMimojiEdit mFragmentMimojiEdit;
    private Handler mHandler;
    private boolean mIsIntentAction;
    /* access modifiers changed from: private */
    public boolean mIsPlaying;
    private View mLiveViewLayout;
    private ViewStub mLiveViewStub;
    /* access modifiers changed from: private */
    public boolean mPaused;
    private boolean mPendingSaveFinish;
    private boolean mPendingShare;
    private boolean mPendingStartPlay;
    private Runnable mPendingTask;
    private ImageView mPreviewBack;
    private ImageView mPreviewCombine;
    private ImageView mPreviewCover;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewShare;
    /* access modifiers changed from: private */
    public ImageView mPreviewStart;
    private TextureView mPreviewTextureView;
    private ContentValues mSaveContentValues;
    private Uri mSavedUri;
    /* access modifiers changed from: private */
    public View mScreenLightIndicator;
    /* access modifiers changed from: private */
    public View mScreenLightRootView;
    private ShareAdapter mShareAdapter;
    private View mShareCancel;
    private ViewGroup mShareLayout;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    /* access modifiers changed from: private */
    public TextureListenerWrapper mTextureListener;
    private TextView mTimeView;
    private ViewGroup mTopLayout;

    /* access modifiers changed from: private */
    public interface OnFrameUpdatedCallback {
        void onUpdate();
    }

    /* access modifiers changed from: private */
    public class TextureListenerWrapper implements TextureView.SurfaceTextureListener {
        private OnFrameUpdatedCallback mOnFrameUpdatedCallback;
        private volatile boolean mRequestCapture;
        private TextureView.SurfaceTextureListener mSuperListener;
        private TextureView mTextureHolder;

        public TextureListenerWrapper(TextureView textureView) {
            this.mTextureHolder = textureView;
            this.mSuperListener = textureView.getSurfaceTextureListener();
            if (this.mSuperListener != null) {
                textureView.setSurfaceTextureListener(this);
                return;
            }
            throw new IllegalStateException("plz call this after videoEditor.init()~");
        }

        private Bitmap getFrame() {
            try {
                TextureView textureView = this.mTextureHolder;
                if (textureView != null) {
                    return textureView.getBitmap();
                }
                return null;
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public void clearRequest() {
            this.mOnFrameUpdatedCallback = null;
            this.mRequestCapture = false;
        }

        public boolean hasCaptureRequest() {
            return this.mRequestCapture;
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            this.mSuperListener.onSurfaceTextureAvailable(surfaceTexture, i, i2);
            Log.d(FragmentFullScreen.TAG, "onSurfaceTextureAvailable : " + surfaceTexture);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            boolean onSurfaceTextureDestroyed = this.mSuperListener.onSurfaceTextureDestroyed(surfaceTexture);
            Log.d(FragmentFullScreen.TAG, "onSurfaceTextureDestroyed : " + surfaceTexture);
            return onSurfaceTextureDestroyed;
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            this.mSuperListener.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
            Log.d(FragmentFullScreen.TAG, "onSurfaceTextureAvailable : " + surfaceTexture);
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            this.mSuperListener.onSurfaceTextureUpdated(surfaceTexture);
            if (!FragmentFullScreen.this.hasFrame() && FragmentFullScreen.this.mIsPlaying) {
                Log.d(FragmentFullScreen.TAG, "onSurfaceTextureUpdated : " + surfaceTexture);
                Bitmap unused = FragmentFullScreen.this.mFirstFrame = getFrame();
            }
            OnFrameUpdatedCallback onFrameUpdatedCallback = this.mOnFrameUpdatedCallback;
            if (onFrameUpdatedCallback != null) {
                onFrameUpdatedCallback.onUpdate();
                this.mOnFrameUpdatedCallback = null;
            }
        }

        public void requestCapture() {
            this.mRequestCapture = true;
        }

        public void requestUpdateCallbackOnce(OnFrameUpdatedCallback onFrameUpdatedCallback) {
            this.mOnFrameUpdatedCallback = onFrameUpdatedCallback;
        }
    }

    private void animateIn() {
    }

    private void attempt(Runnable runnable) {
        Handler handler;
        if (runnable != null) {
            ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
            if ((liveConfigChanges == null || liveConfigChanges.getSegmentSize() == 0) && (handler = this.mHandler) != null) {
                this.mPendingTask = runnable;
                handler.sendEmptyMessageDelayed(0, 5000);
                return;
            }
            runnable.run();
        }
    }

    private void attemptCaptureFrame() {
        Handler handler;
        if (this.mTextureListener == null) {
            Log.d(TAG, "mPreviewTextureManager is null~");
            return;
        }
        Log.d(TAG, "attemptCaptureFrame");
        this.mTextureListener.clearRequest();
        showEmptyView();
        ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
        if ((liveConfigChanges == null || liveConfigChanges.getSegmentSize() == 0) && (handler = this.mHandler) != null) {
            handler.sendEmptyMessageDelayed(1, 5000);
            return;
        }
        this.mTextureListener.requestCapture();
        startConcatVideoIfNeed();
    }

    private boolean checkAndShare() {
        if (this.mSavedUri == null) {
            return false;
        }
        startShare();
        return true;
    }

    /* access modifiers changed from: private */
    public void concatResult(Pair<String, String> pair) {
        if (canProvide()) {
            String str = (String) pair.first;
            String str2 = (String) pair.second;
            if (!str.isEmpty() || !str2.isEmpty()) {
                this.mPendingStartPlay = false;
                this.mConcatReady = true;
                Log.d(TAG, "concat: " + str + " | " + str2);
                ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
                initCombineListener();
                this.mPreviewTextureView = new TextureView(getContext());
                initCoverView();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                Rect displayRect = Util.getDisplayRect();
                layoutParams.topMargin = displayRect.top;
                layoutParams.height = displayRect.height();
                this.mPreviewLayout.removeAllViews();
                this.mPreviewLayout.addView(this.mPreviewTextureView, layoutParams);
                this.mPreviewLayout.addView(this.mPreviewCover, layoutParams);
                if (!liveVideoEditor.init(this.mPreviewTextureView, str, str2, this.mCombineListener, this.mErrorListener)) {
                    onCombineError();
                    return;
                }
                TextureListenerWrapper textureListenerWrapper = this.mTextureListener;
                boolean hasCaptureRequest = textureListenerWrapper != null ? textureListenerWrapper.hasCaptureRequest() : false;
                this.mTextureListener = new TextureListenerWrapper(this.mPreviewTextureView);
                if (hasCaptureRequest) {
                    Log.d(TAG, "concat finish and capture frame");
                    startCaptureFrame(liveVideoEditor);
                } else if (this.mConcatProgress.getVisibility() == 0) {
                    Log.d(TAG, "concat finish and start preview");
                    this.mConcatProgress.setVisibility(8);
                    startPlay();
                } else if (this.mCombineProgress.getVisibility() == 0) {
                    Log.d(TAG, "concat finish and start save");
                    this.mPreviewCombine.setVisibility(8);
                    setProgressBarVisible(0);
                    startCombine();
                } else if (this.mShareProgress.getVisibility() == 0) {
                    Log.d(TAG, "concat finish and pending share");
                    startCombine();
                } else if (this.mPreviewCover.getVisibility() == 0) {
                    Log.d(TAG, "concat finish ,capture frame & stop play");
                    startCaptureFrame(liveVideoEditor);
                }
            } else {
                onCombineError();
            }
        }
    }

    private FragmentMimojiEdit getFragmentMiMoji() {
        FragmentMimojiEdit fragmentMimojiEdit = this.mFragmentMimojiEdit;
        if (fragmentMimojiEdit == null) {
            Log.d(TAG, "getFragmentMiMoji(): fragment is null");
            return null;
        } else if (fragmentMimojiEdit.isAdded()) {
            return this.mFragmentMimojiEdit;
        } else {
            Log.d(TAG, "getFragmentMiMoji(): fragment is not added yet");
            return null;
        }
    }

    private Intent getShareIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(getActivity(), "com.android.camera.fileProvider", new File(this.mSaveContentValues.getAsString("_data"))));
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        return intent;
    }

    /* access modifiers changed from: private */
    public boolean hasFrame() {
        Bitmap bitmap = this.mFirstFrame;
        return bitmap != null && !bitmap.isRecycled();
    }

    private void hideCover() {
        ImageView imageView = this.mPreviewCover;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    private void hideShareSheet() {
        if (this.mShareLayout.getVisibility() != 4) {
            Completable.create(new SlideOutOnSubscribe(this.mShareLayout, 80).setInterpolator(new x()).setDurationTime(200)).subscribe();
        }
    }

    private void initCombineListener() {
        this.mCombineListener = new VECommonCallback() {
            /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass6 */

            @Override // com.ss.android.vesdk.VECommonCallback
            public void onCallback(int i, int i2, float f2, String str) {
                if (i == 4098) {
                    Log.d(FragmentFullScreen.TAG, "play finished and loop");
                } else if (i == 4101) {
                } else {
                    if (i == 4103) {
                        FragmentFullScreen.this.onCombineSuccess();
                    } else if (i == 4105) {
                    } else {
                        if (i != 4112) {
                            Log.d(FragmentFullScreen.TAG, "CombineCallback: " + i);
                            return;
                        }
                        Log.d(FragmentFullScreen.TAG, "CombineStart");
                    }
                }
            }
        };
        this.mErrorListener = new VECommonCallback() {
            /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass7 */

            @Override // com.ss.android.vesdk.VECommonCallback
            public void onCallback(int i, int i2, float f2, String str) {
                Log.e(FragmentFullScreen.TAG, "CombineError: " + i + " | " + i2 + " | " + f2 + " | " + str);
                FragmentFullScreen.this.onCombineError();
            }
        };
    }

    private void initConcatListener() {
        this.mConcatListener = new TERecorder.OnConcatFinishedListener() {
            /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass5 */

            @Override // com.ss.android.vesdk.TERecorder.OnConcatFinishedListener
            public void onConcatFinished(int i) {
                Log.d(FragmentFullScreen.TAG, "onConcatFinished " + i);
                ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
                if (!FragmentFullScreen.this.mPaused && liveConfigChanges != null) {
                    FragmentFullScreen.this.concatResult(liveConfigChanges.getConcatResult());
                }
            }
        };
    }

    private void initCoverView() {
        if (this.mPreviewCover == null) {
            this.mPreviewCover = new ImageView(getContext());
            this.mPreviewCover.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            this.mPreviewCover.setVisibility(8);
        }
    }

    private void initHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass2 */

                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    int i = message.what;
                    if (i == 0) {
                        FragmentFullScreen.this.runPendingTask();
                    } else if (i == 1) {
                        if (FragmentFullScreen.this.mTextureListener != null) {
                            FragmentFullScreen.this.mTextureListener.requestCapture();
                        }
                        FragmentFullScreen.this.startConcatVideoIfNeed();
                    } else if (i == 2) {
                        FragmentFullScreen.this.quitLiveRecordPreview(false);
                    }
                }
            };
        }
    }

    private void initLiveView(View view) {
        this.mTopLayout = (ViewGroup) view.findViewById(R.id.live_preview_top);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopLayout.getLayoutParams();
        marginLayoutParams.topMargin = Util.sTopMargin;
        marginLayoutParams.height = Util.sTopBarHeight;
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.live_preview_layout);
        this.mConcatProgress = (ProgressBar) view.findViewById(R.id.live_concat_progress);
        this.mCombineProgress = (ProgressBar) view.findViewById(R.id.live_save_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.live_share_progress);
        this.mCancelProgress = (ProgressBar) view.findViewById(R.id.live_back_progress);
        this.mTimeView = (TextView) view.findViewById(R.id.live_preview_recording_time_view);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.live_preview_save_circle);
        this.mCameraSnapView.setParameters(((BaseFragment) this).mCurrentMode, false, false);
        this.mCameraSnapView.hideRoundPaintItem();
        this.mCameraSnapView.setSnapClickEnable(false);
        this.mPreviewCombine = (ImageView) view.findViewById(R.id.live_preview_save);
        this.mPreviewBack = (ImageView) view.findViewById(R.id.live_preview_back);
        this.mPreviewShare = (ImageView) view.findViewById(R.id.live_preview_share);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.live_preview_play);
        this.mShareLayout = (ViewGroup) view.findViewById(R.id.live_share_layout);
        this.mShareRecyclerView = (RecyclerView) this.mShareLayout.findViewById(R.id.live_share_list);
        this.mShareRecyclerView.setFocusable(false);
        this.mShareCancel = this.mShareLayout.findViewById(R.id.live_share_cancel);
        this.mShareCancel.setOnClickListener(this);
        this.mPreviewLayout.setOnClickListener(this);
        this.mCameraSnapView.setOnClickListener(this);
        this.mPreviewCombine.setOnClickListener(this);
        this.mPreviewBack.setOnClickListener(this);
        this.mPreviewShare.setOnClickListener(this);
        this.mPreviewStart.setOnClickListener(this);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.live_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomActionView.setOnClickListener(this);
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.live_preview_bottom_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams();
        marginLayoutParams2.height = Math.round(((float) Util.sBottomBarHeight) * 0.7f);
        marginLayoutParams2.bottomMargin = Util.sBottomMargin;
        marginLayoutParams2.topMargin = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        ((ViewGroup.MarginLayoutParams) this.mTimeView.getLayoutParams()).height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        this.mBottomActionView.setBackgroundResource(R.color.fullscreen_background);
    }

    /* access modifiers changed from: private */
    public void onCombineError() {
        Log.d(TAG, "onCombineError");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new f(this));
        }
    }

    /* access modifiers changed from: private */
    public void onCombineSuccess() {
        Log.d(TAG, "combineSuccess");
        this.mCombineReady = true;
        if (!this.mPaused) {
            if (this.mPendingShare) {
                Log.d(TAG, "combineSuccess and share");
                ((LiveModule) ((ActivityBase) getContext()).getCurrentModule()).startSaveToLocal();
            } else if (this.mPendingSaveFinish) {
                Log.d(TAG, "combineSuccess and finish");
                this.mHandler.post(new b(this));
            } else {
                Log.d(TAG, "combineSuccess and skip");
            }
        }
    }

    private void onPlayCompleted() {
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass8 */

            public void run() {
                boolean unused = FragmentFullScreen.this.mIsPlaying = false;
                FragmentFullScreen.this.mPreviewStart.setVisibility(0);
            }
        });
    }

    private void pausePlay() {
        if (this.mIsPlaying) {
            this.mIsPlaying = false;
            this.mPreviewStart.setVisibility(0);
            ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
            if (liveVideoEditor != null) {
                liveVideoEditor.pausePlay();
            }
        }
    }

    private void resumePlay() {
        if (!this.mIsPlaying) {
            this.mIsPlaying = true;
            this.mPreviewStart.setVisibility(8);
            ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
            if (liveVideoEditor != null) {
                liveVideoEditor.resumePlay();
            }
        }
    }

    /* access modifiers changed from: private */
    public void runPendingTask() {
        Runnable runnable = this.mPendingTask;
        if (runnable != null) {
            runnable.run();
            this.mPendingTask = null;
        }
    }

    private void setProgressBarVisible(int i) {
        if (this.mCombineProgress.getVisibility() != i) {
            if (i == 0) {
                this.mCombineProgress.setAlpha(0.0f);
                this.mCombineProgress.setVisibility(0);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(300L);
                ofFloat.setStartDelay(160);
                ofFloat.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass9 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Float f2 = (Float) valueAnimator.getAnimatedValue();
                        FragmentFullScreen.this.mCombineProgress.setAlpha(f2.floatValue());
                        FragmentFullScreen.this.mCombineProgress.setScaleX((f2.floatValue() * 0.1f) + 0.9f);
                        FragmentFullScreen.this.mCombineProgress.setScaleY((f2.floatValue() * 0.1f) + 0.9f);
                    }
                });
                ofFloat.start();
                return;
            }
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat2.setDuration(300L);
            ofFloat2.setInterpolator(new j());
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass10 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FragmentFullScreen.this.mCombineProgress.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat2.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass11 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    FragmentFullScreen.this.mCombineProgress.setVisibility(8);
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                }
            });
            ofFloat2.start();
        }
    }

    private void shareMore() {
        try {
            getContext().startActivity(Intent.createChooser(getShareIntent(), getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            Log.e(TAG, "failed to share video " + this.mSavedUri, e2);
        }
    }

    private void showCover() {
        ImageView imageView = this.mPreviewCover;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
    }

    private void showEmptyView() {
        initCoverView();
        if (this.mPreviewLayout.indexOfChild(this.mPreviewCover) == -1) {
            this.mPreviewLayout.addView(this.mPreviewCover);
        }
        showCover();
        this.mPreviewStart.setVisibility(8);
        this.mConcatProgress.setVisibility(0);
        this.mPreviewLayout.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void showExitConfirm() {
        this.mPreviewBack.setVisibility(0);
        this.mCancelProgress.setVisibility(8);
        if (this.mExitDialog == null) {
            CameraStatUtils.trackLiveClick("exit");
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.live_edit_exit_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_edit_exit_confirm, new DialogInterface.OnClickListener() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_EXIT_CONFIRM);
                    AlertDialog unused = FragmentFullScreen.this.mExitDialog = null;
                    FragmentFullScreen.this.quitLiveRecordPreview(false);
                }
            });
            builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog unused = FragmentFullScreen.this.mExitDialog = null;
                }
            });
            this.mExitDialog = builder.show();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0098  */
    private void showShareSheet() {
        boolean z;
        List<ResolveInfo> queryIntentActivities = getContext().getPackageManager().queryIntentActivities(getShareIntent(), 65536);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            Log.d(TAG, "no IntentActivities");
            return;
        }
        ArrayList arrayList = new ArrayList();
        int length = ShareConstant.DEFAULT_SHARE_LIST.length;
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (arrayList.size() == length) {
                break;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (ShareConstant.DEFAULT_SHARE_LIST[i].equals(resolveInfo.activityInfo.name)) {
                    arrayList.add(new ShareInfo(i, resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name, ShareConstant.DEFAULT_SHARE_LIST_ICON[i], ShareConstant.DEFAULT_SHARE_LIST_NAME[i]));
                    break;
                } else {
                    i++;
                }
            }
        }
        if (arrayList.isEmpty()) {
            Log.d(TAG, "no default share entry");
        } else if (((ShareInfo) arrayList.get(0)).index <= 1 || arrayList.size() >= 2) {
            z = false;
            if (!z) {
                shareMore();
                return;
            }
            arrayList.add(new ShareInfo(length + 1, "com.android.camera", "more", R.drawable.ic_live_share_more, R.string.accessibility_more));
            Collections.sort(arrayList, new Comparator<ShareInfo>() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass12 */

                public int compare(ShareInfo shareInfo, ShareInfo shareInfo2) {
                    int i = shareInfo.index;
                    int i2 = shareInfo2.index;
                    if (i == i2) {
                        return 0;
                    }
                    return i > i2 ? 1 : -1;
                }
            });
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
            int i2 = Util.sWindowWidth;
            int max = Math.max((i2 - (dimensionPixelSize * 2)) / arrayList.size(), (int) (((float) (i2 - dimensionPixelSize)) / 5.5f));
            ShareAdapter shareAdapter = this.mShareAdapter;
            if (shareAdapter == null || shareAdapter.getItemCount() != arrayList.size()) {
                this.mShareAdapter = new ShareAdapter(arrayList, this, max);
                LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), TAG);
                linearLayoutManagerWrapper.setOrientation(0);
                this.mShareRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
                RecyclerAdapterWrapper recyclerAdapterWrapper = new RecyclerAdapterWrapper(this.mShareAdapter);
                Space space = new Space(getContext());
                space.setMinimumWidth(dimensionPixelSize);
                recyclerAdapterWrapper.addHeader(space);
                Space space2 = new Space(getContext());
                space2.setMinimumWidth(dimensionPixelSize);
                recyclerAdapterWrapper.addFooter(space2);
                this.mShareRecyclerView.setAdapter(recyclerAdapterWrapper);
            } else {
                this.mShareAdapter.setShareInfoList(arrayList);
                this.mShareAdapter.notifyDataSetChanged();
            }
            if (Util.sNavigationBarHeight > 0) {
                ((ViewGroup.MarginLayoutParams) this.mShareLayout.getLayoutParams()).bottomMargin = Util.sNavigationBarHeight;
            }
            Completable.create(new SlideInOnSubscribe(this.mShareLayout, 80).setInterpolator(new z()).setDurationTime(200)).subscribe();
            return;
        } else {
            Log.d(TAG, "not match default share strategy");
        }
        z = true;
        if (!z) {
        }
    }

    private void startCaptureFrame(ModeProtocol.LiveVideoEditor liveVideoEditor) {
        this.mIsPlaying = true;
        this.mTextureListener.requestUpdateCallbackOnce(new c(this, liveVideoEditor));
        liveVideoEditor.startPlay();
    }

    /* access modifiers changed from: private */
    public void startCombine() {
        this.mCombineReady = false;
        String asString = this.mSaveContentValues.getAsString("_data");
        ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
        if (liveVideoEditor != null) {
            liveVideoEditor.combineVideoAudio(asString, this.mCombineListener, this.mErrorListener);
        }
    }

    /* access modifiers changed from: private */
    public void startConcatVideoIfNeed() {
        Log.d(TAG, "startConcatVideo");
        boolean z = false;
        this.mConcatReady = false;
        this.mIsPlaying = false;
        initConcatListener();
        ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
        if (liveConfigChanges != null && liveConfigChanges.onRecordConcat(this.mConcatListener)) {
            z = true;
        }
        if (!z) {
            Log.d(TAG, "concat failed");
            concatResult(new Pair<>("", ""));
        }
    }

    private void startPlay() {
        ModeProtocol.LiveVideoEditor liveVideoEditor;
        if (!this.mPaused && !this.mIsPlaying && (liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209)) != null) {
            this.mIsPlaying = true;
            if (this.mTextureListener == null) {
                this.mPreviewStart.setVisibility(8);
                this.mPreviewLayout.setVisibility(0);
                hideCover();
            } else {
                this.mPreviewLayout.setVisibility(0);
                this.mTextureListener.requestUpdateCallbackOnce(new g(this));
            }
            liveVideoEditor.startPlay();
        }
    }

    private void startShare() {
        this.mPendingShare = false;
        showShareSheet();
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
            Log.e(TAG, "failed to share video " + this.mSavedUri, e2);
        }
    }

    private void updateCoverView(ImageView imageView) {
        if (imageView == null) {
            Log.w(TAG, "cover view is null~");
        } else if (hasFrame()) {
            imageView.setImageBitmap(this.mFirstFrame);
        } else {
            Log.w(TAG, "frame didn't created~");
        }
    }

    public /* synthetic */ void Ma() {
        if (((ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null) {
            quitLiveRecordPreview(false);
        } else {
            this.mHandler.sendEmptyMessageDelayed(2, 5000);
        }
    }

    public /* synthetic */ void Na() {
        quitLiveRecordPreview(true);
    }

    public /* synthetic */ void Oa() {
        this.mFirstFrame = null;
        this.mTextureListener.requestCapture();
        startConcatVideoIfNeed();
    }

    public /* synthetic */ void Pa() {
        this.mPreviewStart.setVisibility(8);
        hideCover();
    }

    public /* synthetic */ void a(ModeProtocol.LiveVideoEditor liveVideoEditor) {
        this.mIsPlaying = false;
        updateCoverView(this.mPreviewCover);
        liveVideoEditor.pausePlay();
        this.mConcatProgress.setVisibility(8);
        this.mPreviewStart.setVisibility(0);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4086;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_full_screen;
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public ContentValues getSaveContentValues() {
        return this.mSaveContentValues;
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void hideScreenLight() {
        if (this.mScreenLightIndicator.getVisibility() != 8) {
            Completable.create(new AlphaOutOnSubscribe(this.mScreenLightRootView).setDurationTime(200)).subscribe(new Action() {
                /* class com.android.camera.fragment.fullscreen.FragmentFullScreen.AnonymousClass1 */

                @Override // io.reactivex.functions.Action
                public void run() throws Exception {
                    FragmentFullScreen.this.mScreenLightIndicator.setVisibility(8);
                    FragmentFullScreen.this.mScreenLightRootView.setVisibility(8);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mScreenLightRootView = view.findViewById(R.id.screen_light_root_view);
        this.mScreenLightIndicator = view.findViewById(R.id.screen_light_indicator);
        this.mLiveViewStub = (ViewStub) view.findViewById(R.id.live_record_preview);
        if (Util.isCameraUnderScreen()) {
            ((ViewGroup.MarginLayoutParams) this.mScreenLightRootView.getLayoutParams()).topMargin = getResources().getDimensionPixelSize(R.dimen.camera_under_screen_lighting_view_margin_top);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public boolean isLiveRecordPreviewShown() {
        View view = this.mLiveViewLayout;
        return view != null && view.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public boolean isLiveRecordSaving() {
        return this.mPendingSaveFinish || this.mPendingShare;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mFragmentMimojiEdit == null && DataRepository.dataItemFeature().od()) {
            this.mFragmentMimojiEdit = new FragmentMimojiEdit();
            this.mFragmentMimojiEdit.registerProtocol();
            this.mFragmentMimojiEdit.setDegree(((BaseFragment) this).mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiEdit fragmentMimojiEdit = this.mFragmentMimojiEdit;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.full_screen_lift, fragmentMimojiEdit, fragmentMimojiEdit.getFragmentTag());
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        View view = this.mLiveViewLayout;
        if (view != null && view.getVisibility() == 0 && this.mCombineReady) {
            if (this.mPendingSaveFinish) {
                onCombineSuccess();
            } else if (this.mPendingShare) {
                this.mPendingShare = false;
                this.mPreviewShare.setVisibility(0);
                this.mShareProgress.setVisibility(8);
            }
        }
        if (this.mFragmentMimojiEdit == null && DataRepository.dataItemFeature().c_0x58()) {
            this.mFragmentMimojiEdit = new FragmentMimojiEdit();
            this.mFragmentMimojiEdit.registerProtocol();
            this.mFragmentMimojiEdit.setDegree(((BaseFragment) this).mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiEdit fragmentMimojiEdit = this.mFragmentMimojiEdit;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.full_screen_lift, fragmentMimojiEdit, fragmentMimojiEdit.getFragmentTag());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        View view = this.mLiveViewLayout;
        if (view == null || view.getVisibility() != 0) {
            FragmentMimojiEdit fragmentMiMoji = getFragmentMiMoji();
            return fragmentMiMoji != null && fragmentMiMoji.onBackEvent(i);
        }
        if (this.mShareLayout.getVisibility() == 0) {
            hideShareSheet();
        } else {
            showExitConfirm();
        }
        return true;
    }

    public void onClick(View view) {
        if (this.mConcatProgress.getVisibility() != 0 && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0 && this.mCancelProgress.getVisibility() != 0) {
            switch (view.getId()) {
                case R.id.live_preview_back:
                    this.mCancelProgress.setVisibility(0);
                    this.mPreviewBack.setVisibility(8);
                    attempt(new d(this));
                    return;
                case R.id.live_preview_bottom_action:
                case R.id.live_preview_bottom_parent:
                case R.id.live_preview_recording_time_view:
                case R.id.live_preview_top:
                case R.id.live_record_preview:
                case R.id.live_save_progress:
                case R.id.live_share_icon:
                default:
                    return;
                case R.id.live_preview_layout:
                    pausePlay();
                    return;
                case R.id.live_preview_play:
                    if (!this.mConcatReady) {
                        Log.d(TAG, "concat not finished, show play progress");
                        this.mPreviewStart.setVisibility(8);
                        this.mConcatProgress.setVisibility(0);
                        attempt(new a(this));
                        return;
                    }
                    hideShareSheet();
                    startPlay();
                    return;
                case R.id.live_preview_save:
                case R.id.live_preview_save_circle:
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SAVE);
                    if (this.mSavedUri != null) {
                        this.mPendingSaveFinish = true;
                        onCombineSuccess();
                        return;
                    } else if (!this.mConcatReady) {
                        Log.d(TAG, "concat not finished, show save progress and wait to save");
                        this.mPendingSaveFinish = true;
                        this.mPreviewCombine.setVisibility(8);
                        setProgressBarVisible(0);
                        attempt(new a(this));
                        return;
                    } else if (!this.mCombineReady) {
                        pausePlay();
                        this.mPreviewStart.setVisibility(8);
                        this.mPreviewCombine.setVisibility(8);
                        setProgressBarVisible(0);
                        this.mPendingSaveFinish = true;
                        attempt(new e(this));
                        return;
                    } else {
                        return;
                    }
                case R.id.live_preview_share:
                    if (this.mConcatProgress.getVisibility() == 0) {
                        Log.d(TAG, "concat not finished, skip share~");
                        return;
                    }
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SHARE);
                    if (!checkAndShare()) {
                        this.mPendingShare = true;
                        this.mPreviewShare.setVisibility(8);
                        this.mShareProgress.setVisibility(0);
                        if (!this.mConcatReady) {
                            Log.d(TAG, "concat not finished, show share progress and wait to share");
                            attempt(new a(this));
                            return;
                        }
                        pausePlay();
                        attempt(new e(this));
                        return;
                    }
                    return;
                case R.id.live_share_cancel:
                    hideShareSheet();
                    return;
                case R.id.live_share_item:
                    ShareInfo shareInfo = (ShareInfo) view.getTag();
                    hideShareSheet();
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SHARE_SHEET + shareInfo.index);
                    if (shareInfo.className.equals("more")) {
                        shareMore();
                        return;
                    } else {
                        startShare(shareInfo.packageName, shareInfo.className);
                        return;
                    }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void onLiveSaveToLocalFinished(Uri uri) {
        this.mSavedUri = uri;
        if (!this.mPaused && this.mPendingShare) {
            this.mPreviewShare.setVisibility(0);
            this.mShareProgress.setVisibility(8);
            startShare();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause + ");
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(0)) {
            this.mHandler.removeMessages(0);
            this.mPendingTask = null;
            this.mPreviewCombine.setVisibility(0);
            this.mPreviewStart.setVisibility(0);
            this.mPreviewShare.setVisibility(0);
            this.mPreviewBack.setVisibility(0);
            this.mConcatProgress.setVisibility(8);
            this.mCombineProgress.setVisibility(8);
            this.mShareProgress.setVisibility(8);
            this.mCancelProgress.setVisibility(8);
        }
        Handler handler2 = this.mHandler;
        if (handler2 != null && handler2.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        Handler handler3 = this.mHandler;
        if (handler3 != null && handler3.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        this.mPaused = true;
        this.mConcatReady = false;
        this.mCombineReady = false;
        pausePlay();
        Log.d(TAG, "onPause - ");
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void onRecordSegmentCreated() {
        Log.d(TAG, "onRecordSegmentCreated");
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(0)) {
            this.mHandler.removeMessages(0);
            this.mHandler.post(new h(this));
        }
        Handler handler2 = this.mHandler;
        if (handler2 != null && handler2.hasMessages(1)) {
            this.mHandler.removeMessages(1);
            this.mHandler.post(new i(this));
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume + " + this.mPendingShare + "," + this.mPendingSaveFinish + "," + this.mPendingStartPlay + ",mTextureListener + " + this.mTextureListener);
        if (this.mPendingShare) {
            this.mShareProgress.setVisibility(8);
            this.mPreviewShare.setVisibility(0);
            this.mPendingShare = false;
        }
        if (this.mPendingSaveFinish) {
            this.mPreviewCombine.setVisibility(0);
            this.mCombineProgress.setVisibility(8);
            this.mPendingSaveFinish = false;
        }
        this.mPaused = false;
        if (this.mTextureListener != null) {
            if (hasFrame()) {
                this.mPreviewStart.setVisibility(0);
                this.mConcatProgress.setVisibility(8);
                this.mTextureListener.clearRequest();
                updateCoverView(this.mPreviewCover);
                showCover();
            } else {
                attemptCaptureFrame();
            }
        } else if (this.mPendingStartPlay) {
            showEmptyView();
            attempt(new a(this));
        }
        Log.d(TAG, "onResume - ");
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        hideScreenLight();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        View view = this.mLiveViewLayout;
        if (view != null && view.getVisibility() == 0) {
            if (i2 == 3) {
                AlertDialog alertDialog = this.mExitDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    this.mExitDialog = null;
                }
                this.mLiveViewLayout.setVisibility(8);
                return;
            }
            this.mConcatReady = false;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return null;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        View view = this.mLiveViewLayout;
        if (view != null && view.getVisibility() == 0) {
            list.add(this.mPreviewStart);
            list.add(this.mCameraSnapView);
            list.add(this.mPreviewCombine);
            list.add(this.mPreviewBack);
            list.add(this.mPreviewShare);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void quitLiveRecordPreview(boolean z) {
        Log.d(TAG, "quitLiveRecordPreview " + z);
        if (this.mConcatProgress.getVisibility() == 0) {
            this.mConcatProgress.setVisibility(8);
        }
        if (this.mCombineProgress.getVisibility() == 0) {
            this.mCombineProgress.setVisibility(8);
        }
        if (this.mShareProgress.getVisibility() == 0) {
            this.mShareProgress.setVisibility(8);
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(TAG, "concat error, action null");
            return;
        }
        this.mLiveViewLayout.setVisibility(8);
        this.mPendingSaveFinish = false;
        if (z) {
            cameraAction.onReviewDoneClicked();
        } else {
            cameraAction.onReviewCancelClicked();
        }
        ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
        if (liveVideoEditor != null) {
            liveVideoEditor.onDestory();
        }
        this.mCombineListener = null;
        this.mErrorListener = null;
        ImageView imageView = this.mPreviewCover;
        if (imageView != null) {
            imageView.setImageBitmap(null);
            this.mPreviewCover = null;
        }
        Bitmap bitmap = this.mFirstFrame;
        if (bitmap != null) {
            bitmap.recycle();
            this.mFirstFrame = null;
        }
        if (this.mTextureListener != null) {
            this.mTextureListener = null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(196, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void setScreenLightColor(int i) {
        this.mScreenLightRootView.setBackgroundColor(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public boolean showScreenLight() {
        if (this.mScreenLightIndicator.getVisibility() == 0) {
            return false;
        }
        this.mScreenLightIndicator.setVisibility(0);
        Completable.create(new AlphaInOnSubscribe(this.mScreenLightRootView).setDurationTime(100)).subscribe();
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void startLiveRecordPreview(ContentValues contentValues) {
        this.mSavedUri = null;
        this.mSaveContentValues = contentValues;
        initHandler();
        if (this.mLiveViewLayout == null) {
            this.mLiveViewLayout = this.mLiveViewStub.inflate();
            initLiveView(this.mLiveViewLayout);
        }
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        this.mPreviewLayout.setVisibility(8);
        this.mPreviewLayout.removeAllViews();
        this.mLiveViewLayout.setVisibility(0);
        this.mCombineProgress.setVisibility(8);
        this.mShareProgress.setVisibility(8);
        this.mCancelProgress.setVisibility(8);
        this.mShareLayout.setVisibility(4);
        ViewCompat.setRotation(this.mPreviewStart, (float) ((BaseFragment) this).mDegree);
        ViewCompat.setRotation(this.mCameraSnapView, (float) ((BaseFragment) this).mDegree);
        ViewCompat.setRotation(this.mPreviewCombine, (float) ((BaseFragment) this).mDegree);
        ViewCompat.setRotation(this.mPreviewBack, (float) ((BaseFragment) this).mDegree);
        ViewCompat.setRotation(this.mPreviewShare, (float) ((BaseFragment) this).mDegree);
        Completable.create(new AlphaInOnSubscribe(this.mCameraSnapView)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewCombine)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewBack)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewStart)).subscribe();
        if (((ActivityBase) getContext()).startFromKeyguard()) {
            this.mPreviewShare.setVisibility(8);
        } else {
            Completable.create(new AlphaInOnSubscribe(this.mPreviewShare)).subscribe();
        }
        this.mTimeView.setText(((ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201)).getTimeValue());
        this.mTimeView.setVisibility(0);
        this.mPreviewStart.setVisibility(8);
        this.mConcatProgress.setVisibility(0);
        this.mConcatReady = false;
        this.mCombineReady = false;
        this.mPendingShare = false;
        this.mPendingSaveFinish = false;
        this.mPendingStartPlay = true;
        startConcatVideoIfNeed();
    }

    @Override // com.android.camera.protocol.ModeProtocol.FullScreenProtocol
    public void startLiveRecordSaving() {
        if (isAdded() && this.mCameraSnapView.getVisibility() == 0) {
            this.mCameraSnapView.performClick();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(196, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
