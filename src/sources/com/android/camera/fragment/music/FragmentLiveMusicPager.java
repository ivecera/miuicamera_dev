package com.android.camera.fragment.music;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.network.NetworkDependencies;
import com.android.camera.network.live.BaseRequestException;
import com.android.camera.network.live.TTLiveMusicResourceRequest;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.ResponseListener;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.resource.SimpleNetworkDownloadRequest;
import com.android.camera.resource.tmmusic.TMMusicCacheLoadRequest;
import com.android.camera.resource.tmmusic.TMMusicItemRequest;
import com.android.camera.resource.tmmusic.TMMusicList;
import com.android.camera.resource.tmmusic.TMMusicListMapToMusicInfo;
import com.android.camera.resource.tmmusic.TMMusicStationsRequest;
import com.ss.android.vesdk.VEEditor;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import miui.app.f;

public class FragmentLiveMusicPager extends Fragment implements View.OnClickListener, View.OnTouchListener, CtaNoticeFragment.OnCtaNoticeClickListener {
    private static final long MAX_REQUEST_TIME = 10800000;
    /* access modifiers changed from: private */
    public static final String TAG = "FragmentLiveMusicPager";
    /* access modifiers changed from: private */
    public LiveMusicInfo mCurrentSelectMusic;
    /* access modifiers changed from: private */
    public LinearLayout mCurrentSelectedMusicLayout;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private boolean mEnableTrack;
    private AudioManager.OnAudioFocusChangeListener mFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass5 */

        public void onAudioFocusChange(int i) {
            if (i == -3 && FragmentLiveMusicPager.this.mMediaPlayer != null) {
                FragmentLiveMusicPager.this.mMediaPlayer.setVolume(0.2f, 0.2f);
            }
        }
    };
    private boolean mIsDestroyed;
    /* access modifiers changed from: private */
    public boolean mIsLoadingAnimationStart;
    /* access modifiers changed from: private */
    public boolean mIsMediaPreparing;
    private int mItemType;
    private List<LiveMusicInfo> mLiveMusicInfoList;
    private ProgressBar mMediaLoadingProgressBar;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    private MusicAdapter mMusicAdapter;
    /* access modifiers changed from: private */
    public MusicOperation mMusicOperation;
    private int mMusicPlayPosition;
    private LinearLayout mNetworkExceptionLayout;
    private ImageView mPlayingImageView;
    /* access modifiers changed from: private */
    public f mProgressDialog;
    private RecyclerView mRecyclerView;
    private LinearLayout mUpdateLayout;

    private void initAdapter() {
        this.mMusicOperation = new MusicOperation();
        if (this.mItemType == 0) {
            this.mLiveMusicInfoList = new ArrayList();
            this.mMusicAdapter = new MusicAdapter(getContext(), this, this, this.mLiveMusicInfoList);
            if (CtaNoticeFragment.checkCta(getActivity().getFragmentManager(), false, this, 1)) {
                loadOnlineMusicByFeature();
            }
        } else {
            this.mNetworkExceptionLayout.setVisibility(8);
            this.mUpdateLayout.setVisibility(8);
            this.mRecyclerView.setVisibility(0);
            this.mLiveMusicInfoList = MusicUtils.getMusicListFromLocalFolder(FileUtils.MUSIC_LOCAL, getContext());
            this.mMusicAdapter = new MusicAdapter(getContext(), this, this, this.mLiveMusicInfoList);
        }
        this.mRecyclerView.setAdapter(this.mMusicAdapter);
    }

    private boolean isMusicFileExists(LiveMusicInfo liveMusicInfo) {
        String str = liveMusicInfo.mPlayUrl;
        if (this.mItemType == 0) {
            str = FileUtils.MUSIC_ONLINE + liveMusicInfo.mTitle + ".mp3";
        }
        return new File(str).exists();
    }

    /* access modifiers changed from: private */
    public void loadOnlineMusicByFeature() {
        if (!NetworkDependencies.isConnected(getContext())) {
            onLoadError();
        } else if (DataRepository.dataItemFeature().c_0x03()) {
            loadTikTokMusic();
        } else {
            loadTMMusic();
        }
    }

    private void loadTMMusic() {
        getContext();
        if (isAdded()) {
            this.mEnableTrack = true;
            this.mDisposable.add(Observable.concat(new TMMusicCacheLoadRequest().startObservable(TMMusicList.class), new TMMusicStationsRequest(true).startObservable(TMMusicList.class).flatMap(c.INSTANCE)).firstElement().map(new TMMusicListMapToMusicInfo()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new a(this), new b(this)));
        }
    }

    private void loadTikTokMusic() {
        if (getContext() != null) {
            TTLiveMusicResourceRequest tTLiveMusicResourceRequest = new TTLiveMusicResourceRequest();
            long liveMusicFirstRequestTime = DataRepository.dataItemRunning().getLiveMusicFirstRequestTime();
            long currentTimeMillis = System.currentTimeMillis() - liveMusicFirstRequestTime;
            if (liveMusicFirstRequestTime <= 0 || currentTimeMillis >= MAX_REQUEST_TIME) {
                tTLiveMusicResourceRequest.execute(new ResponseListener() {
                    /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass2 */

                    @Override // com.android.camera.network.net.base.ResponseListener
                    public void onResponse(Object... objArr) {
                        final List list = (List) objArr[0];
                        if (FragmentLiveMusicPager.this.isAdded()) {
                            Completable.fromAction(new Action() {
                                /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass2.AnonymousClass1 */

                                @Override // io.reactivex.functions.Action
                                public void run() {
                                    DataRepository.dataItemRunning().setLiveMusicFirstRequestTime(System.currentTimeMillis());
                                    FragmentLiveMusicPager.this.c(list);
                                }
                            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
                        }
                    }

                    @Override // com.android.camera.network.net.base.ResponseListener
                    public void onResponseError(ErrorCode errorCode, String str, Object obj) {
                        Completable.fromAction(new Action() {
                            /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass2.AnonymousClass2 */

                            @Override // io.reactivex.functions.Action
                            public void run() {
                                FragmentLiveMusicPager.this.onLoadError();
                            }
                        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
                        String access$300 = FragmentLiveMusicPager.TAG;
                        Log.e(access$300, "request online music failed, errorCode =  " + errorCode);
                    }
                });
                return;
            }
            try {
                c(tTLiveMusicResourceRequest.loadFromCache());
            } catch (BaseRequestException e2) {
                onLoadError();
                String str = TAG;
                Log.e(str, "request online music failed " + e2.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public void onLoadError() {
        LinearLayout linearLayout = this.mNetworkExceptionLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void onMusicPauseOrStopPlay() {
        this.mPlayingImageView.setBackgroundResource(R.drawable.ic_live_music_play);
    }

    private void onMusicSelectedToPlay(LiveMusicInfo liveMusicInfo) {
        String str = liveMusicInfo.mPlayUrl;
        if (!TextUtils.isEmpty(str) && !this.mIsDestroyed) {
            ((AudioManager) getContext().getSystemService(VEEditor.MVConsts.TYPE_AUDIO)).requestAudioFocus(this.mFocusChangeListener, 3, 1);
            this.mPlayingImageView.setVisibility(4);
            this.mMediaLoadingProgressBar.setVisibility(0);
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            if (!(mediaPlayer == null || this.mCurrentSelectMusic == null)) {
                mediaPlayer.stop();
                this.mMediaPlayer.reset();
                this.mMusicOperation.endPlaySession();
            }
            this.mMusicOperation.onSelected(liveMusicInfo.mRequestItemID, liveMusicInfo.mCategoryId);
            this.mCurrentSelectMusic = liveMusicInfo;
            try {
                this.mMediaPlayer.setDataSource(str);
                this.mMediaPlayer.prepareAsync();
                this.mIsMediaPreparing = true;
                this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass6 */

                    public void onPrepared(MediaPlayer mediaPlayer) {
                        FragmentLiveMusicPager.this.onMusicStartPlay();
                        FragmentLiveMusicPager.this.mMusicOperation.onNewSessionStartPlay((long) mediaPlayer.getDuration());
                        boolean unused = FragmentLiveMusicPager.this.mIsMediaPreparing = false;
                        mediaPlayer.start();
                    }
                });
                this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass7 */

                    public void onCompletion(MediaPlayer mediaPlayer) {
                        FragmentLiveMusicPager.this.mMediaPlayer.stop();
                        FragmentLiveMusicPager.this.mMediaPlayer.reset();
                        FragmentLiveMusicPager.this.onMusicPauseOrStopPlay();
                        FragmentLiveMusicPager.this.mMusicOperation.endPlaySession();
                        LiveMusicInfo unused = FragmentLiveMusicPager.this.mCurrentSelectMusic = null;
                    }
                });
            } catch (IOException e2) {
                String str2 = TAG;
                Log.e(str2, "mediaplayer play failed " + e2.getMessage());
            }
        }
    }

    private void onMusicSelectedToUse(LiveMusicInfo liveMusicInfo) {
        this.mMusicOperation.onSelected(liveMusicInfo.mRequestItemID, liveMusicInfo.mCategoryId);
        this.mMusicOperation.onSelectedToUse();
        String str = liveMusicInfo.mPlayUrl;
        if (this.mItemType == 0) {
            str = FileUtils.MUSIC_ONLINE + liveMusicInfo.mTitle + ".mp3";
        }
        ModeProtocol.LiveAudioChanges liveAudioChanges = (ModeProtocol.LiveAudioChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(211);
        if (liveAudioChanges != null) {
            liveAudioChanges.setAudioPath(str);
        }
        CameraSettings.setCurrentLiveMusic(str, liveMusicInfo.mTitle + "-" + liveMusicInfo.mAuthor);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.updateConfigItem(245);
        }
        FragmentUtils.removeFragmentByTag(getParentFragment().getFragmentManager(), FragmentLiveMusic.TAG);
    }

    /* access modifiers changed from: private */
    public void onMusicStartPlay() {
        this.mMediaLoadingProgressBar.setVisibility(4);
        this.mPlayingImageView.setVisibility(0);
        this.mPlayingImageView.setBackgroundResource(R.drawable.ic_live_music_pause);
    }

    private void selectMusic(LiveMusicInfo liveMusicInfo) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.reset();
            this.mMusicOperation.endPlaySession();
        }
        if (isMusicFileExists(liveMusicInfo)) {
            onMusicSelectedToUse(liveMusicInfo);
        } else if (this.mItemType == 0) {
            startDownloadMusicForUse(liveMusicInfo);
        }
    }

    private void startDownloadAnimation() {
        this.mIsLoadingAnimationStart = true;
        this.mProgressDialog = new f(getActivity());
        String string = getString(R.string.live_music_downloading_tips);
        this.mProgressDialog.setCancelable(true);
        this.mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass8 */

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                if (!FragmentLiveMusicPager.this.mProgressDialog.isShowing()) {
                    return true;
                }
                FragmentLiveMusicPager.this.mProgressDialog.dismiss();
                if (!FragmentLiveMusicPager.this.mIsLoadingAnimationStart) {
                    return true;
                }
                FragmentLiveMusicPager.this.mCurrentSelectedMusicLayout.setBackgroundColor(-1);
                FragmentLiveMusicPager.this.mCurrentSelectedMusicLayout.setAlpha(1.0f);
                return true;
            }
        });
        this.mProgressDialog.setMessage(string);
        this.mProgressDialog.show();
    }

    private void startDownloadMusicForUse(LiveMusicInfo liveMusicInfo) {
        if (this.mItemType == 0) {
            if (!NetworkDependencies.isConnected(getContext())) {
                LinearLayout linearLayout = this.mCurrentSelectedMusicLayout;
                if (linearLayout != null) {
                    linearLayout.setBackgroundColor(-1);
                    this.mCurrentSelectedMusicLayout.setAlpha(1.0f);
                }
                Toast.makeText(getActivity(), (int) R.string.live_music_network_exception, 1).show();
                return;
            }
            startDownloadAnimation();
            String str = FileUtils.MUSIC_ONLINE + liveMusicInfo.mTitle + ".mp3";
            if (liveMusicInfo.downloadState == 6) {
                startTTMusicDetailInfoRequest(false, str, liveMusicInfo);
            } else {
                startDownloadRequest(str, liveMusicInfo);
            }
        }
    }

    /* access modifiers changed from: private */
    public void startDownloadRequest(String str, LiveMusicInfo liveMusicInfo) {
        new SimpleNetworkDownloadRequest(liveMusicInfo.mPlayUrl, str).startObservable(liveMusicInfo).observeOn(AndroidSchedulers.mainThread()).subscribe(new d(this), new e(this));
    }

    /* access modifiers changed from: private */
    public void startPlayOrStopMusic(LiveMusicInfo liveMusicInfo) {
        LiveMusicInfo liveMusicInfo2 = this.mCurrentSelectMusic;
        if (liveMusicInfo2 == null || !liveMusicInfo2.equals(liveMusicInfo)) {
            if (!NetworkDependencies.isConnected(getContext())) {
                Toast.makeText(getActivity(), (int) R.string.live_music_network_exception, 1).show();
                return;
            }
            this.mPlayingImageView.setVisibility(4);
            this.mMediaLoadingProgressBar.setVisibility(0);
            if (!TextUtils.isEmpty(liveMusicInfo.mPlayUrl)) {
                onMusicSelectedToPlay(liveMusicInfo);
            } else if (this.mItemType == 0 && liveMusicInfo.downloadState == 6) {
                startTTMusicDetailInfoRequest(true, null, liveMusicInfo);
            }
        } else if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mMusicOperation.onPausePlay();
            onMusicPauseOrStopPlay();
        } else {
            this.mMusicOperation.onResumePlay();
            this.mMediaPlayer.start();
            onMusicStartPlay();
        }
    }

    private void startTTMusicDetailInfoRequest(final boolean z, final String str, LiveMusicInfo liveMusicInfo) {
        new TMMusicItemRequest(liveMusicInfo).startObservable(LiveMusicInfo.class).subscribe(new Consumer<LiveMusicInfo>() {
            /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass3 */

            public void accept(LiveMusicInfo liveMusicInfo) throws Exception {
                liveMusicInfo.downloadState = 0;
                if (!z) {
                    FragmentLiveMusicPager.this.startDownloadRequest(str, liveMusicInfo);
                } else if (!TextUtils.isEmpty(liveMusicInfo.mPlayUrl)) {
                    FragmentLiveMusicPager.this.startPlayOrStopMusic(liveMusicInfo);
                }
            }
        }, new Consumer<Throwable>() {
            /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass4 */

            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
            }
        });
    }

    private void stopDownloadAnimation() {
        this.mIsLoadingAnimationStart = false;
        f fVar = this.mProgressDialog;
        if (fVar != null) {
            fVar.dismiss();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: updateAdapter */
    public void c(List<LiveMusicInfo> list) {
        this.mLiveMusicInfoList.addAll(list);
        if (this.mItemType == 0) {
            this.mUpdateLayout.setVisibility(8);
            this.mNetworkExceptionLayout.setVisibility(8);
            this.mRecyclerView.setVisibility(0);
        }
        this.mMusicAdapter.notifyDataSetChanged();
    }

    public /* synthetic */ void a(LiveMusicInfo liveMusicInfo) throws Exception {
        stopDownloadAnimation();
        onMusicSelectedToUse(liveMusicInfo);
    }

    public /* synthetic */ void d(Throwable th) throws Exception {
        th.printStackTrace();
        onLoadError();
    }

    public /* synthetic */ void e(Throwable th) throws Exception {
        stopDownloadAnimation();
        LinearLayout linearLayout = this.mCurrentSelectedMusicLayout;
        if (linearLayout != null) {
            linearLayout.setBackgroundColor(-1);
            this.mCurrentSelectedMusicLayout.setAlpha(1.0f);
        }
        if (getActivity() != null) {
            Toast.makeText(getActivity(), (int) R.string.live_music_network_exception, 1).show();
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.music_recycler_view);
        this.mRecyclerView.setFocusable(false);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "music_recycler_view");
        linearLayoutManagerWrapper.setOrientation(1);
        this.mRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mUpdateLayout = (LinearLayout) view.findViewById(R.id.music_updating_layout);
        this.mNetworkExceptionLayout = (LinearLayout) view.findViewById(R.id.music_network_exception);
        this.mNetworkExceptionLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.music.FragmentLiveMusicPager.AnonymousClass1 */

            public void onClick(View view) {
                if (CtaNoticeFragment.checkCta(FragmentLiveMusicPager.this.getActivity().getFragmentManager(), false, FragmentLiveMusicPager.this, 1)) {
                    FragmentLiveMusicPager.this.loadOnlineMusicByFeature();
                }
            }
        });
        this.mItemType = getArguments().getInt("ITEM_TYPE");
        this.mMediaPlayer = new MediaPlayer();
        this.mIsLoadingAnimationStart = false;
        this.mIsMediaPreparing = false;
        initAdapter();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.music_network_exception:
                this.mUpdateLayout.setVisibility(0);
                this.mNetworkExceptionLayout.setVisibility(8);
                loadOnlineMusicByFeature();
                return;
            case R.id.music_play:
                if (!this.mIsMediaPreparing) {
                    LiveMusicInfo liveMusicInfo = (LiveMusicInfo) view.getTag();
                    if (this.mPlayingImageView != null) {
                        onMusicPauseOrStopPlay();
                    }
                    ImageView imageView = (ImageView) view.findViewById(R.id.music_play);
                    if (!imageView.equals(this.mPlayingImageView)) {
                        this.mPlayingImageView = imageView;
                    }
                    this.mMediaLoadingProgressBar = (ProgressBar) ((ViewGroup) view.getParent()).findViewById(R.id.music_loading);
                    startPlayOrStopMusic(liveMusicInfo);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_live_music_pager, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mIsDestroyed = true;
        this.mMediaPlayer.release();
        this.mRecyclerView.setAdapter(null);
    }

    @Override // com.android.camera.fragment.CtaNoticeFragment.OnCtaNoticeClickListener
    public void onNegativeClick(DialogInterface dialogInterface, int i) {
        this.mUpdateLayout.setVisibility(8);
        this.mNetworkExceptionLayout.setVisibility(0);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mMediaPlayer.isPlaying()) {
            onMusicPauseOrStopPlay();
            this.mMediaPlayer.stop();
            this.mMediaPlayer.reset();
            this.mMusicOperation.endPlaySession();
            this.mCurrentSelectMusic = null;
        }
    }

    @Override // com.android.camera.fragment.CtaNoticeFragment.OnCtaNoticeClickListener
    public void onPositiveClick(DialogInterface dialogInterface, int i) {
        loadOnlineMusicByFeature();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.mDisposable.clear();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mCurrentSelectedMusicLayout = (LinearLayout) view.findViewById(R.id.music_layout);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mCurrentSelectedMusicLayout.setBackgroundColor(-3355444);
            this.mCurrentSelectedMusicLayout.setAlpha(0.5f);
        } else if (actionMasked == 1) {
            selectMusic((LiveMusicInfo) view.getTag());
        } else if (actionMasked != 2 && actionMasked == 3) {
            this.mCurrentSelectedMusicLayout.setBackgroundColor(-1);
            this.mCurrentSelectedMusicLayout.setAlpha(1.0f);
        }
        return true;
    }

    @Override // android.support.v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        MediaPlayer mediaPlayer;
        super.setUserVisibleHint(z);
        if (!z && (mediaPlayer = this.mMediaPlayer) != null && mediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            onMusicPauseOrStopPlay();
        }
    }
}
