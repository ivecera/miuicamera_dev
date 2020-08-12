package com.android.camera.features.mimoji2.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
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
import com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList;
import com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.MimojiViewUtil;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.fullscreen.ShareAdapter;
import com.android.camera.fragment.fullscreen.ShareInfo;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.TextureVideoView;
import d.h.a.x;
import d.h.a.z;
import io.reactivex.Completable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentMimojiFullScreen extends BaseFragment implements MimojiModeProtocol.MimojiFullScreenProtocol, ModeProtocol.HandleBackTrace, View.OnClickListener {
    public static final int FRAGMENT_INFO = 4004;
    private static final String TAG = "MIMOJIFullScreen";
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private ViewGroup mBottomTimbreLayout;
    private CameraSnapView mCameraSnapView;
    private ProgressBar mCancelProgress;
    private ProgressBar mCombineProgress;
    private ProgressBar mConcatProgress;
    /* access modifiers changed from: private */
    public AlertDialog mExitDialog;
    private FragmentMimojiBottomList mFragmentMimojiBottomList;
    private FragmentMimojiEdit2 mFragmentMimojiEdit2;
    private View mLiveViewLayout;
    private ViewStub mLiveViewStub;
    private ImageView mMimojiChangeTimbreBtn;
    private FrameLayout mMimojiChangeTimbreLayout;
    private ImageView mPreviewBack;
    private ImageView mPreviewCombine;
    private Bitmap mPreviewCoverBitmap;
    private ImageView mPreviewCoverView;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewShare;
    private ImageView mPreviewStart;
    private TextureVideoView mPreviewTextureView;
    private String mSavedPath;
    private ShareAdapter mShareAdapter;
    private View mShareCancel;
    private ViewGroup mShareLayout;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    private TextView mTimeView;

    private boolean checkAndShare() {
        if (FileUtils.checkFileConsist(this.mSavedPath)) {
            try {
                showShareSheet();
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private FragmentMimojiEdit2 getFragmentMiMoji() {
        FragmentMimojiEdit2 fragmentMimojiEdit2 = this.mFragmentMimojiEdit2;
        if (fragmentMimojiEdit2 == null) {
            Log.d(TAG, "getFragmentMiMoji(): fragment is null");
            return null;
        } else if (fragmentMimojiEdit2.isAdded()) {
            return this.mFragmentMimojiEdit2;
        } else {
            Log.d(TAG, "getFragmentMiMoji(): fragment is not added yet");
            return null;
        }
    }

    private Intent getShareIntent() {
        String str = FileUtils.checkFileConsist(this.mSavedPath) ? this.mSavedPath : null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Uri addVideoSync = ((ActivityBase) getActivity()).getImageSaver().addVideoSync(str, Util.genContentValues(2, str), false);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", addVideoSync);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        return intent;
    }

    private void hideShareSheet() {
        if (this.mShareLayout.getVisibility() == 0) {
            Completable.create(new SlideOutOnSubscribe(this.mShareLayout, 80).setInterpolator(new x()).setDurationTime(200)).subscribe();
        }
    }

    private void hideTimbreLayout() {
        DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(0);
        Completable.create(new AlphaOutOnSubscribe(this.mMimojiChangeTimbreLayout).targetGone()).subscribe();
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, true);
    }

    private void initLiveView(View view) {
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.live_preview_layout);
        this.mBottomTimbreLayout = (ViewGroup) view.findViewById(R.id.fl_mimoji_change_timbre);
        this.mPreviewTextureView = (TextureVideoView) view.findViewById(R.id.tvv_mimoji_preview);
        this.mMimojiChangeTimbreBtn = (ImageView) view.findViewById(R.id.btn_mimoji_change_timbre);
        this.mMimojiChangeTimbreLayout = (FrameLayout) view.findViewById(R.id.fl_bottom_mimoji_change_timbre);
        this.mPreviewCoverView = (ImageView) view.findViewById(R.id.image_mimoji_cover);
        this.mConcatProgress = (ProgressBar) view.findViewById(R.id.live_concat_progress);
        this.mCombineProgress = (ProgressBar) view.findViewById(R.id.live_save_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.live_share_progress);
        this.mCancelProgress = (ProgressBar) view.findViewById(R.id.live_back_progress);
        this.mTimeView = (TextView) view.findViewById(R.id.live_preview_recording_time_view);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.live_preview_save_circle);
        if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 1) {
            this.mCameraSnapView.setParameters(174, false, false);
        } else {
            this.mCameraSnapView.setParameters(((BaseFragment) this).mCurrentMode, false, false);
        }
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
        this.mMimojiChangeTimbreBtn.setOnClickListener(this);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.live_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomActionView.setOnClickListener(this);
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.live_preview_bottom_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams();
        marginLayoutParams.height = Math.round(((float) Util.sBottomBarHeight) * 0.7f);
        marginLayoutParams.bottomMargin = Util.sBottomMargin;
        marginLayoutParams.topMargin = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        ((ViewGroup.MarginLayoutParams) this.mTimeView.getLayoutParams()).height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        this.mBottomActionView.setBackgroundResource(R.color.fullscreen_background);
    }

    private void onPreviewResume() {
        Log.d(TAG, "mimoji void onPreviewResume[]");
        if (this.mPreviewCoverBitmap == null || MimojiViewUtil.getViewIsVisible(this.mConcatProgress) || !MimojiViewUtil.setViewVisible(this.mPreviewLayout, true) || !MimojiViewUtil.setViewVisible(this.mPreviewCoverView, true)) {
            onCombineError();
            return;
        }
        MimojiViewUtil.setViewVisible(this.mPreviewStart, true);
        this.mPreviewCoverView.setImageBitmap(this.mPreviewCoverBitmap);
    }

    private void pausePlay() {
        Log.d(TAG, "mimoji void pausePlay[]");
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null && mimojiVideoEditor.pausePlay()) {
            MimojiViewUtil.setViewVisible(this.mPreviewStart, true);
        }
    }

    private void removeTimbreLayout() {
        DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(0);
        FragmentMimojiBottomList fragmentMimojiBottomList = this.mFragmentMimojiBottomList;
        if (fragmentMimojiBottomList != null) {
            fragmentMimojiBottomList.unRegisterProtocol();
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentUtils.removeFragmentByTag(childFragmentManager, getFragmentTag() + this.mFragmentMimojiBottomList.getFragmentTag());
            this.mFragmentMimojiBottomList = null;
        }
    }

    private void resumePlay() {
        Log.d(TAG, "mimoji void resumePlay[]");
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor == null) {
            return;
        }
        if (mimojiVideoEditor.resumePlay()) {
            MimojiViewUtil.setViewVisible(this.mPreviewStart, false);
        } else {
            Log.e(TAG, "mimoji void resumePlay fail");
        }
    }

    private void shareMore() {
        try {
            getContext().startActivity(Intent.createChooser(getShareIntent(), getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            Log.e(TAG, "failed to share video shareMore ", e2);
        }
    }

    private void showExitConfirm() {
        this.mPreviewBack.setVisibility(0);
        this.mCancelProgress.setVisibility(8);
        if (this.mExitDialog == null) {
            CameraStatUtils.trackLiveClick("exit");
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.live_edit_exit_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_edit_exit_confirm, new DialogInterface.OnClickListener() {
                /* class com.android.camera.features.mimoji2.fragment.FragmentMimojiFullScreen.AnonymousClass1 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_EXIT_CONFIRM);
                    AlertDialog unused = FragmentMimojiFullScreen.this.mExitDialog = null;
                    MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                    if (mimojiAvatarEngine2 != null) {
                        mimojiAvatarEngine2.deleteMimojiCache(1);
                    }
                    FragmentMimojiFullScreen.this.onCombineError();
                }
            });
            builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                /* class com.android.camera.features.mimoji2.fragment.FragmentMimojiFullScreen.AnonymousClass2 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog unused = FragmentMimojiFullScreen.this.mExitDialog = null;
                }
            });
            this.mExitDialog = builder.show();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009e  */
    private void showShareSheet() {
        boolean z;
        Intent shareIntent = getShareIntent();
        if (shareIntent == null) {
            onCombineError();
            return;
        }
        List<ResolveInfo> queryIntentActivities = getContext().getPackageManager().queryIntentActivities(shareIntent, 65536);
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
                /* class com.android.camera.features.mimoji2.fragment.FragmentMimojiFullScreen.AnonymousClass3 */

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
                LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "FullScreen");
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

    private void showTimbreLayout() {
        DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(3);
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreLayout, true);
        FragmentMimojiBottomList fragmentMimojiBottomList = this.mFragmentMimojiBottomList;
        if (fragmentMimojiBottomList == null) {
            this.mFragmentMimojiBottomList = new FragmentMimojiBottomList();
            this.mFragmentMimojiBottomList.registerProtocol();
            this.mFragmentMimojiBottomList.setDegree(((BaseFragment) this).mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiBottomList fragmentMimojiBottomList2 = this.mFragmentMimojiBottomList;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.fl_bottom_mimoji_change_timbre, fragmentMimojiBottomList2, getFragmentTag() + this.mFragmentMimojiBottomList.getFragmentTag());
        } else {
            fragmentMimojiBottomList.refreshMimojiList();
            Completable.create(new AlphaInOnSubscribe(this.mFragmentMimojiBottomList.getView())).subscribe();
        }
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, false);
        this.mFragmentMimojiBottomList.setIsNeedClickHide(true);
    }

    private void startCombine() {
        if (FileUtils.checkFileConsist(this.mSavedPath)) {
            try {
                String str = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", "mp4");
                FileUtils.copyFile(new File(this.mSavedPath), new File(str));
                this.mSavedPath = str;
                onCombineSuccess();
            } catch (Exception unused) {
                onCombineError();
            }
        } else {
            onCombineError();
        }
    }

    private void startPlay() {
        MimojiViewUtil.setViewVisible(this.mConcatProgress, false);
        MimojiViewUtil.setViewVisible(this.mPreviewStart, false);
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.startPlay();
        }
    }

    private void startShare(String str, String str2, Uri uri) {
        ComponentName componentName = new ComponentName(str, str2);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        try {
            getContext().startActivity(Intent.createChooser(intent, getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            Log.e(TAG, "failed to share video " + uri, e2);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void concatResult(String str) {
        Log.d(TAG, "mimoji void concatResult[]");
        if (getActivity() != null) {
            getActivity().runOnUiThread(new a(this, str));
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4004;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji_full_screen;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public String getMimojiVideoSavePath() {
        return this.mSavedPath;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mLiveViewStub = (ViewStub) view.findViewById(R.id.mimoji_record_preview);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public boolean isMimojiRecordPreviewShowing() {
        return MimojiViewUtil.getViewIsVisible(this.mLiveViewLayout);
    }

    public /* synthetic */ void l(String str) {
        if (!canProvide() || this.mPreviewLayout == null) {
            onCombineError();
            return;
        }
        Rect previewRect = Util.getPreviewRect(getActivity());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mPreviewLayout.getLayoutParams();
        layoutParams.topMargin = previewRect.top;
        layoutParams.height = previewRect.height();
        this.mPreviewLayout.setLayoutParams(layoutParams);
        boolean z = true;
        MimojiViewUtil.setViewVisible(this.mPreviewLayout, true);
        MimojiViewUtil.setViewVisible(this.mBottomTimbreLayout, true);
        MimojiViewUtil.setViewVisible(this.mPreviewTextureView, true);
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, true);
        boolean z2 = false;
        if (this.mMimojiChangeTimbreBtn != null) {
            if (DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiTimbreInfo() == null) {
                z = false;
            }
            this.mMimojiChangeTimbreBtn.setImageResource(z ? R.drawable.ic_mimoji_change_timbre_on : R.drawable.ic_mimoji_change_timbre_normal);
        }
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            z2 = mimojiVideoEditor.init(this.mPreviewTextureView, str);
        }
        if (z2) {
            this.mSavedPath = str;
            startPlay();
            return;
        }
        onCombineError();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mFragmentMimojiEdit2 == null && DataRepository.dataItemFeature().Bd()) {
            this.mFragmentMimojiEdit2 = new FragmentMimojiEdit2();
            this.mFragmentMimojiEdit2.registerProtocol();
            this.mFragmentMimojiEdit2.setDegree(((BaseFragment) this).mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiEdit2 fragmentMimojiEdit2 = this.mFragmentMimojiEdit2;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.fragment_full_screen_mimoji_edit, fragmentMimojiEdit2, fragmentMimojiEdit2.getFragmentTag());
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (this.mFragmentMimojiEdit2 == null && DataRepository.dataItemFeature().Bd()) {
            this.mFragmentMimojiEdit2 = new FragmentMimojiEdit2();
            this.mFragmentMimojiEdit2.registerProtocol();
            this.mFragmentMimojiEdit2.setDegree(((BaseFragment) this).mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiEdit2 fragmentMimojiEdit2 = this.mFragmentMimojiEdit2;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.fragment_full_screen_mimoji_edit, fragmentMimojiEdit2, fragmentMimojiEdit2.getFragmentTag());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        if (MimojiViewUtil.getViewIsVisible(this.mLiveViewLayout)) {
            if (MimojiViewUtil.getViewIsVisible(this.mShareLayout)) {
                hideShareSheet();
            } else {
                showExitConfirm();
            }
            return true;
        }
        FragmentMimojiEdit2 fragmentMiMoji = getFragmentMiMoji();
        if (fragmentMiMoji != null) {
            return fragmentMiMoji.onBackEvent(i);
        }
        return false;
    }

    public void onClick(View view) {
        if (this.mConcatProgress.getVisibility() != 0 && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0 && this.mCancelProgress.getVisibility() != 0) {
            switch (view.getId()) {
                case R.id.btn_mimoji_change_timbre:
                    showTimbreLayout();
                    return;
                case R.id.live_preview_back:
                    this.mCancelProgress.setVisibility(0);
                    this.mPreviewBack.setVisibility(8);
                    showExitConfirm();
                    return;
                case R.id.live_preview_bottom_action:
                default:
                    return;
                case R.id.live_preview_layout:
                    FragmentMimojiBottomList fragmentMimojiBottomList = this.mFragmentMimojiBottomList;
                    if (fragmentMimojiBottomList == null || !MimojiViewUtil.getViewIsVisible(fragmentMimojiBottomList.getView())) {
                        pausePlay();
                        return;
                    } else {
                        hideTimbreLayout();
                        return;
                    }
                case R.id.live_preview_play:
                    hideShareSheet();
                    startPlay();
                    return;
                case R.id.live_preview_save:
                case R.id.live_preview_save_circle:
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SAVE);
                    pausePlay();
                    this.mPreviewStart.setVisibility(8);
                    this.mPreviewCombine.setVisibility(8);
                    MimojiViewUtil.setViewVisible(this.mCombineProgress, true);
                    startCombine();
                    return;
                case R.id.live_preview_share:
                    if (this.mConcatProgress.getVisibility() == 0) {
                        Log.d(TAG, "concat not finished, skip share~");
                        return;
                    }
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SHARE);
                    if (!checkAndShare()) {
                        Log.d(TAG, "uri null");
                        onCombineError();
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
                    }
                    String str = null;
                    if (FileUtils.checkFileConsist(this.mSavedPath)) {
                        str = this.mSavedPath;
                    }
                    if (!TextUtils.isEmpty(str)) {
                        try {
                            startShare(shareInfo.packageName, shareInfo.className, ((ActivityBase) getActivity()).getImageSaver().addVideoSync(str, Util.genContentValues(2, str), false));
                            return;
                        } catch (Exception unused) {
                            onCombineError();
                            return;
                        }
                    } else {
                        onCombineError();
                        return;
                    }
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void onCombineError() {
        if (((BaseFragment) this).mCurrentMode == 184 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
            Log.d(TAG, "onCombineError");
            quitMimojiRecordPreview();
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction != null) {
                cameraAction.onReviewCancelClicked();
                return;
            }
            Log.e(TAG, "mimoji void onCombineError[] cameraAction null");
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            } else {
                Log.e(TAG, "mimoji void onCombineError[] recordState null");
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void onCombineSuccess() {
        if (((BaseFragment) this).mCurrentMode == 184 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
            Log.d(TAG, "combineSuccess");
            quitMimojiRecordPreview();
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction != null) {
                cameraAction.onReviewDoneClicked();
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void onMimojiSaveToLocalFinished(Uri uri) {
        Log.d(TAG, "MIMOJI onMimojiSaveToLocalFinished" + uri);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        onPreviewResume();
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onStop() {
        onCombineError();
        super.onStop();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i2 == 3) {
            AlertDialog alertDialog = this.mExitDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mExitDialog = null;
            }
            onCombineError();
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

    public void quitMimojiRecordPreview() {
        Log.d(TAG, "quitMimojiRecordPreview ");
        setPreviewCover(null);
        MimojiViewUtil.setViewVisible(this.mConcatProgress, false);
        MimojiViewUtil.setViewVisible(this.mCombineProgress, false);
        MimojiViewUtil.setViewVisible(this.mShareProgress, false);
        MimojiViewUtil.setViewVisible(this.mLiveViewLayout, false);
        removeTimbreLayout();
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.onDestory();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(249, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void setPreviewCover(Bitmap bitmap) {
        Bitmap bitmap2 = this.mPreviewCoverBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mPreviewCoverBitmap.recycle();
        }
        this.mPreviewCoverBitmap = bitmap;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void startMimojiRecordPreview() {
        this.mSavedPath = null;
        DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(0);
        if (this.mLiveViewLayout == null) {
            this.mLiveViewLayout = this.mLiveViewStub.inflate();
            initLiveView(this.mLiveViewLayout);
        }
        MimojiViewUtil.setViewVisible(this.mPreviewLayout, false);
        MimojiViewUtil.setViewVisible(this.mCombineProgress, false);
        MimojiViewUtil.setViewVisible(this.mShareProgress, false);
        MimojiViewUtil.setViewVisible(this.mCancelProgress, false);
        MimojiViewUtil.setViewVisible(this.mShareLayout, false);
        MimojiViewUtil.setViewVisible(this.mBottomTimbreLayout, false);
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreLayout, false);
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, false);
        MimojiViewUtil.setViewVisible(this.mLiveViewLayout, true);
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
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            this.mTimeView.setText(mimojiAvatarEngine2.getTimeValue());
            this.mTimeView.setVisibility(0);
        }
        MimojiViewUtil.setViewVisible(this.mPreviewStart, false);
        MimojiViewUtil.setViewVisible(this.mConcatProgress, true);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void startMimojiRecordSaving() {
        this.mCameraSnapView.performClick();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(249, this);
        removeTimbreLayout();
        unRegisterBackStack(modeCoordinator, this);
    }
}
