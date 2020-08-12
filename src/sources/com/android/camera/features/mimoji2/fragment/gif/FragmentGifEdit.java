package com.android.camera.features.mimoji2.fragment.gif;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.storage.Storage;
import com.xiaomi.Video2GifEditer.MediaProcess;

public class FragmentGifEdit extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.MimojiGifEditor {
    public static final int GIF_EDIT_SHOW = 303;
    public static final int Gif_EDIT_HIDE = 202;
    public static final int REQUEST_CODE_SHARE_GIF = 304;
    public static final String TAG = "FragmentGifEdit";
    /* access modifiers changed from: private */
    public Context mContext;
    private MiFitTextureView mFitTextureView;
    private GifMediaPlayer mGifMediaPlayer;
    private View mGifViewLayout;
    private GifViewPresenter mGifViewPresenter;
    private boolean mIsContainEmoji;
    private boolean mIsShare;
    private ImageView mIvShare;
    private FrameLayout mLlTextureContainer;
    private String mVideoPath;

    private void initGifMediaPlayer() {
        this.mGifMediaPlayer = new GifMediaPlayer(this.mContext);
        this.mGifViewPresenter.setGifMediaPlayer(this.mGifMediaPlayer);
        this.mGifViewPresenter.completeVideoRecord(this.mVideoPath, this.mIsContainEmoji);
        this.mGifMediaPlayer.setFitTextureView(this.mFitTextureView);
        if (!this.mVideoPath.isEmpty()) {
            this.mGifMediaPlayer.setVideoUrl(this.mVideoPath, new MediaProcess.Callback() {
                /* class com.android.camera.features.mimoji2.fragment.gif.FragmentGifEdit.AnonymousClass1 */

                @Override // com.xiaomi.Video2GifEditer.MediaProcess.Callback
                public void OnConvertProgress(int i) {
                    if (i != 100) {
                        return;
                    }
                    if (FragmentGifEdit.this.isShare()) {
                        FragmentGifEdit.this.doShare();
                        return;
                    }
                    Toast.makeText(FragmentGifEdit.this.mContext, "Video convert GIF is finished!", 0).show();
                    FragmentGifEdit.this.onBackEvent(6);
                }
            });
            this.mGifMediaPlayer.PlayCameraRecord();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void completeVideoRecord(String str, boolean z) {
        this.mVideoPath = str;
        this.mIsContainEmoji = z;
    }

    public void doShare() {
        Uri gifUri = this.mGifMediaPlayer.getVideoInfo().getGifUri();
        setIsShare(false);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType(Storage.MIME_GIF);
        intent.putExtra("android.intent.extra.STREAM", gifUri);
        intent.addFlags(1);
        this.mContext.startActivity(Intent.createChooser(intent, "share gif"));
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_GIF;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_gif_bottom;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mGifViewLayout = view;
        this.mContext = getContext();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i = getResources().getDisplayMetrics().widthPixels;
        marginLayoutParams.height = getResources().getDisplayMetrics().heightPixels;
        marginLayoutParams.topMargin = Util.isContentViewExtendToTopEdges() ? Util.sStatusBarHeight : 0;
        this.mGifViewPresenter = new GifViewPresenter(getContext(), getFragmentManager());
        this.mGifViewPresenter.initView(this.mGifViewLayout);
        this.mIvShare = (ImageView) view.findViewById(R.id.iv_gif_share);
        this.mIvShare.setOnClickListener(this);
        this.mFitTextureView = (MiFitTextureView) view.findViewById(R.id.gif_texture_view);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mFitTextureView.getLayoutParams();
        marginLayoutParams2.height = i;
        marginLayoutParams2.width = i;
        this.mLlTextureContainer = (FrameLayout) view.findViewById(R.id.ll_operate_gif_words);
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mLlTextureContainer.getLayoutParams();
        marginLayoutParams3.height = i;
        marginLayoutParams3.width = i;
        initGifMediaPlayer();
    }

    public boolean isShare() {
        return this.mIsShare;
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        Log.i("FragmentGifEdit", "gif onActivityResult  resultCode:" + i2 + "  requestcode: " + i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate == null) {
            return true;
        }
        baseDelegate.delegateEvent(20);
        return true;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.iv_gif_share) {
            setIsShare(true);
            this.mGifViewPresenter.gifConfirmVideo2Gif();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_gif_bottom, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void operateGifPannelVisibleState(int i) {
        this.mGifViewPresenter.operateGifPannelVisibleState(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void reconfigPreviewRadio(int i) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(251, this);
    }

    public void setIsShare(boolean z) {
        this.mIsShare = z;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(251, this);
    }
}
