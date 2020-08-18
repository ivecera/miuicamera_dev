package com.android.camera.fragment.live;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.sticker.download.DownloadView;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.network.download.Request;
import com.android.camera.network.download.Verifier;
import com.android.camera.network.live.TTLiveStickerResourceRequest;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.ResponseListener;
import com.android.camera.network.resource.LiveDownloadHelper;
import com.android.camera.network.resource.LiveResource;
import com.android.camera.network.resource.LiveResourceDownloadManager;
import com.android.camera.network.resource.OnLiveDownloadListener;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.sticker.LiveStickerInfo;
import com.bumptech.glide.c;
import com.bumptech.glide.request.f;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FragmentLiveSticker extends FragmentLiveBase implements CtaNoticeFragment.OnCtaNoticeClickListener {
    private static final int FRAGMENT_INFO = 4092;
    private static final String MAIN_URI = "snssdk1128://feed/";
    private static final String MARKET_URI = "market://details?id=com.ss.android.ugc.aweme&back=true&ref=camera&startDownload=false";
    private static final int MIN_SUPPORT_VERSION = 320;
    private static final String MORE_URI = "snssdk1128://openRecord/?recordOrigin=system&recordParam=withStickerPanel&gd_label=open_camera&label=";
    private static final String PACKAGE_NAME = "com.ss.android.ugc.aweme";
    private static final int STICKER_ITEM_SIZE = 10;
    private static final String TAG = "FragmentLiveSticker";
    /* access modifiers changed from: private */
    public static final LiveStickerInfo[] sLocalStickerList = (Util.isGlobalVersion() ? new LiveStickerInfo[]{new LiveStickerInfo("Film", "24991e783f23920397ac8aeed15994c2", "c34a7d7c2d512b3778c47c060bc10169.png"), new LiveStickerInfo("WhiteCat", "9b74385fe7e8cb81e1b88ce3b293bdf2", "9ec3702f2e5e96041e7e5b00d1fb39d2.png"), new LiveStickerInfo("ThreeScreens", "0a673064d64fce91ee41b405c6f74dca", "5063ed1901467ac59fb746d566fc22ad.png")} : new LiveStickerInfo[]{new LiveStickerInfo("瘦身", "0eb0e0214f7bc7f7bbfb4e9f4dba7f99", "f2e24fea41e33a1c0fc9a79b8d3b91e2.png", "保持全身在画面中哦"), new LiveStickerInfo("星空喵", "a75682e81788cc12f68682b9c9067f70", "8ca064318882fa610f4623b852accd36.png"), new LiveStickerInfo("浮生若梦", "24991e783f23920397ac8aeed15994c2", "e42237f75eeff4e5162f9b0130492e36.png")});
    /* access modifiers changed from: private */
    public static final LiveStickerInfo sMoreSticker = new LiveStickerInfo("", "_hide_more_", (int) R.drawable.ic_live_sticker_more);
    private static final LiveStickerInfo sNoneSticker = new LiveStickerInfo("", "", "off.png");
    private static List<LiveStickerInfo> sPersistStickerList = new ArrayList(Arrays.asList(sLocalStickerList));
    /* access modifiers changed from: private */
    public StickerItemAdapter mAdapter;
    private LiveDownloadHelper<LiveStickerInfo> mDownloadHelper = new LiveDownloadHelper<LiveStickerInfo>() {
        /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass8 */

        public Request createDownloadRequest(LiveStickerInfo liveStickerInfo) {
            Request request = new Request(((LiveResource) liveStickerInfo).id, Uri.parse(liveStickerInfo.url), new File(FileUtils.STICKER_RESOURCE_DIR + liveStickerInfo.hash + ".zip"));
            request.setVerifier(new Verifier.Md5(liveStickerInfo.hash));
            return request;
        }

        public boolean isDownloaded(LiveStickerInfo liveStickerInfo) {
            return false;
        }
    };
    private OnLiveDownloadListener mDownloadListener = new OnLiveDownloadListener() {
        /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass7 */

        @Override // com.android.camera.network.resource.OnLiveDownloadListener
        public void onFinish(String str, int i) {
            Log.v(FragmentLiveSticker.TAG, "finish " + str + ": " + i);
            final int i2 = 0;
            while (true) {
                if (i2 >= FragmentLiveSticker.this.mStickerList.size()) {
                    i2 = -1;
                    break;
                } else if (str.equals(((LiveResource) ((LiveStickerInfo) FragmentLiveSticker.this.mStickerList.get(i2))).id)) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                Log.w(FragmentLiveSticker.TAG, "sticker " + str + " not found");
                return;
            }
            final LiveStickerInfo liveStickerInfo = (LiveStickerInfo) FragmentLiveSticker.this.mStickerList.get(i2);
            liveStickerInfo.downloadState = i;
            if (i == 3 || i == 1) {
                CameraStatUtils.trackLiveStickerDownload(liveStickerInfo.name, true);
                Completable.fromAction(new Action() {
                    /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass7.AnonymousClass2 */

                    @Override // io.reactivex.functions.Action
                    public void run() throws Exception {
                        FileUtils.UnZipFileFolder(FileUtils.STICKER_RESOURCE_DIR + liveStickerInfo.hash + ".zip", FileUtils.STICKER_RESOURCE_DIR);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                    /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass7.AnonymousClass1 */

                    @Override // io.reactivex.CompletableObserver
                    public void onComplete() {
                        FragmentLiveSticker.this.mAdapter.notifyItemChanged(i2);
                        FragmentLiveSticker fragmentLiveSticker = FragmentLiveSticker.this;
                        int i = fragmentLiveSticker.mFutureSelectIndex;
                        int i2 = i2;
                        if (i == i2) {
                            fragmentLiveSticker.onItemSelected(i2, null);
                        }
                    }

                    @Override // io.reactivex.CompletableObserver
                    public void onError(Throwable th) {
                        Log.e(FragmentLiveSticker.TAG, "unzip " + liveStickerInfo.hash + ".zip failed", th);
                        liveStickerInfo.downloadState = 4;
                        FragmentLiveSticker.this.mAdapter.notifyItemChanged(i2);
                    }

                    @Override // io.reactivex.CompletableObserver
                    public void onSubscribe(Disposable disposable) {
                    }
                });
                return;
            }
            Log.e(FragmentLiveSticker.TAG, "download " + str + " failed, state = " + i);
            CameraStatUtils.trackLiveStickerDownload(liveStickerInfo.name, false);
            FragmentLiveSticker.this.showNetworkErrorHint();
            FragmentLiveSticker.this.mAdapter.notifyItemChanged(i2);
        }

        @Override // com.android.camera.network.resource.OnLiveDownloadListener
        public void onProgressUpdate(String str, int i) {
            Log.v(FragmentLiveSticker.TAG, "update " + str + ": " + i);
        }
    };
    int mFutureSelectIndex;
    private int mItemWidth;
    private LinearLayoutManagerWrapper mLayoutManager;
    private View mNoneItemView;
    private View mNoneSelectView;
    private View mRootView;
    int mSelectIndex = -1;
    /* access modifiers changed from: private */
    public List<LiveStickerInfo> mStickerList = sPersistStickerList;
    private RecyclerView mStickerListView;
    private int mTotalWidth;
    private View mUpdatingView;

    /* access modifiers changed from: private */
    public static class StickerItemAdapter extends RecyclerView.Adapter<StickerItemHolder> {
        Context mContext;
        f mGlideOptions = new f().L(R.drawable.ic_live_sticker_placeholder);
        LayoutInflater mLayoutInflater;
        AdapterView.OnItemClickListener mListener;
        int mSelectIndex;
        List<LiveStickerInfo> mStickerList;

        class StickerItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener, DownloadView.OnDownloadSuccessListener {
            boolean mWaitingDownloadSuccess;

            public StickerItemHolder(View view) {
                super(view);
                view.setOnClickListener(this);
                ((LiveDownloadView) getView(R.id.item_download)).setOnDownloadSuccessListener(this);
            }

            public void onClick(View view) {
                int adapterPosition = getAdapterPosition();
                StickerItemAdapter stickerItemAdapter = StickerItemAdapter.this;
                if (adapterPosition != stickerItemAdapter.mSelectIndex) {
                    stickerItemAdapter.mListener.onItemClick(null, view, adapterPosition, getItemId());
                }
            }

            @Override // com.android.camera.fragment.sticker.download.DownloadView.OnDownloadSuccessListener
            public void onDownloadSuccess(DownloadView downloadView) {
                int layoutPosition = getLayoutPosition();
                this.mWaitingDownloadSuccess = false;
                StickerItemAdapter.this.notifyItemChanged(layoutPosition);
            }
        }

        public StickerItemAdapter(Context context, List<LiveStickerInfo> list, int i, AdapterView.OnItemClickListener onItemClickListener) {
            this.mContext = context;
            this.mStickerList = list;
            this.mSelectIndex = i;
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mListener = onItemClickListener;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mStickerList.size();
        }

        public void onBindViewHolder(StickerItemHolder stickerItemHolder, int i) {
            int i2;
            LiveDownloadView liveDownloadView = (LiveDownloadView) stickerItemHolder.getView(R.id.item_download);
            ImageView imageView = (ImageView) stickerItemHolder.getView(R.id.item_image);
            View view = stickerItemHolder.getView(R.id.item_selected_indicator);
            LiveStickerInfo liveStickerInfo = this.mStickerList.get(i);
            ((RecyclerView.ViewHolder) stickerItemHolder).itemView.setTag(liveStickerInfo);
            if (!((LiveResource) liveStickerInfo).isLocal || (i2 = liveStickerInfo.iconId) <= 0) {
                c.H(this.mContext).load(liveStickerInfo.icon).b(this.mGlideOptions).a(imageView);
            } else {
                imageView.setImageResource(i2);
            }
            int downloadState = liveStickerInfo.getDownloadState();
            if (FragmentLiveSticker.sMoreSticker.hash.equals(liveStickerInfo.hash)) {
                downloadState = 1;
            }
            if (downloadState == 3) {
                stickerItemHolder.mWaitingDownloadSuccess = true;
                liveStickerInfo.downloadState = 1;
            }
            if (!stickerItemHolder.mWaitingDownloadSuccess || downloadState != 5) {
                liveDownloadView.setStateImage(downloadState);
                if (i == this.mSelectIndex) {
                    view.setVisibility(0);
                }
            }
            if (i != this.mSelectIndex) {
                view.setVisibility(8);
            }
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public StickerItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new StickerItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_live_sticker_item, viewGroup, false));
        }

        public void setSelectIndex(int i) {
            this.mSelectIndex = i;
        }
    }

    static {
        sPersistStickerList.add(sMoreSticker);
    }

    private void reload() {
        this.mUpdatingView.setVisibility(0);
        this.mStickerListView.setVisibility(4);
        this.mSelectIndex = -1;
        this.mAdapter.setSelectIndex(this.mSelectIndex);
        updateData();
    }

    private boolean scrollIfNeed(int i) {
        int max = (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) ? Math.max(0, i - 1) : (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) ? Math.min(i + 1, this.mLayoutManager.getItemCount() - 1) : i;
        if (max == i) {
            return false;
        }
        this.mLayoutManager.scrollToPosition(max);
        return true;
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
    }

    /* access modifiers changed from: private */
    public void showNetworkErrorHint() {
        ToastUtils.showToast(getActivity(), getResources().getString(R.string.live_sticker_network_error_hint), 80);
    }

    private void updateData() {
        new TTLiveStickerResourceRequest(CameraSettings.isLiveStickerInternalChannel() ? EffectConstants.CHANNEL_LOCAL_TEST : "default", "default").execute(!CameraSettings.isLiveStickerInternalChannel(), new ResponseListener() {
            /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass6 */

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponse(Object... objArr) {
                final List list = (List) objArr[0];
                Completable.fromAction(new Action() {
                    /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass6.AnonymousClass2 */

                    @Override // io.reactivex.functions.Action
                    public void run() {
                        HashSet hashSet = new HashSet();
                        for (LiveStickerInfo liveStickerInfo : list) {
                            liveStickerInfo.downloadState = LiveResourceDownloadManager.getInstance().getDownloadState(((LiveResource) liveStickerInfo).id);
                            liveStickerInfo.getDownloadState();
                            hashSet.add(liveStickerInfo.hash);
                            hashSet.add(liveStickerInfo.hash + ".zip");
                        }
                        for (LiveStickerInfo liveStickerInfo2 : FragmentLiveSticker.sLocalStickerList) {
                            hashSet.add(liveStickerInfo2.hash);
                        }
                        File[] listFiles = new File(FileUtils.STICKER_RESOURCE_DIR).listFiles();
                        for (File file : listFiles) {
                            String name = file.getName();
                            if (!hashSet.contains(name)) {
                                file.delete();
                                Log.i(FragmentLiveSticker.TAG, "remove deprecated sticker " + name);
                            }
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
                    /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass6.AnonymousClass1 */

                    @Override // io.reactivex.functions.Action
                    public void run() {
                        FragmentLiveSticker.this.updateStickerList(list);
                    }
                });
                Object[] objArr2 = new Object[1];
                objArr2[0] = Integer.valueOf(list == null ? -1 : list.size());
                Log.d(FragmentLiveSticker.TAG, String.format("getStickerList %d ", objArr2));
            }

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponseError(ErrorCode errorCode, String str, Object obj) {
                final ArrayList arrayList = new ArrayList();
                int length = (10 - FragmentLiveSticker.sLocalStickerList.length) + 1;
                for (int i = 0; i < length; i++) {
                    LiveStickerInfo liveStickerInfo = new LiveStickerInfo();
                    liveStickerInfo.iconId = R.drawable.ic_live_sticker_placeholder;
                    liveStickerInfo.url = "https://mi.com/";
                    liveStickerInfo.hash = "0123456789abcdef0123456789abcdef";
                    ((LiveResource) liveStickerInfo).id = String.valueOf(i);
                    arrayList.add(liveStickerInfo);
                }
                Completable.complete().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
                    /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass6.AnonymousClass3 */

                    @Override // io.reactivex.functions.Action
                    public void run() {
                        FragmentLiveSticker.this.updateStickerList(arrayList);
                    }
                });
                Log.e(FragmentLiveSticker.TAG, String.format("errorCode %d msg: %s", Integer.valueOf(errorCode.CODE), str));
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateStickerList(List<LiveStickerInfo> list) {
        this.mStickerList.clear();
        this.mStickerList.addAll(Arrays.asList(sLocalStickerList));
        if (list != null) {
            this.mStickerList.addAll(list);
            boolean isPackageAvailable = Util.isPackageAvailable(getActivity(), PACKAGE_NAME);
            if (!Util.isGlobalVersion() && DataRepository.dataItemGlobal().isTiktokMoreButtonEnabled(isPackageAvailable)) {
                this.mStickerList.add(sMoreSticker);
            }
            sPersistStickerList = this.mStickerList;
        }
        this.mSelectIndex = -1;
        String currentLiveSticker = CameraSettings.getCurrentLiveSticker();
        if (currentLiveSticker != null) {
            int i = 0;
            while (true) {
                if (i >= this.mStickerList.size()) {
                    break;
                } else if (currentLiveSticker.equals(this.mStickerList.get(i).hash)) {
                    this.mSelectIndex = i;
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mNoneSelectView.setVisibility(this.mSelectIndex == -1 ? 0 : 8);
        this.mAdapter.setSelectIndex(this.mSelectIndex);
        this.mAdapter.notifyDataSetChanged();
        setItemInCenter(this.mSelectIndex);
        this.mUpdatingView.setVisibility(8);
        this.mStickerListView.setVisibility(0);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4092;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_live_sticker;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.live_sticker_item_size);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        final boolean isLayoutRTL = Util.isLayoutRTL(getContext());
        this.mUpdatingView = this.mRootView.findViewById(R.id.live_sticker_updating);
        this.mStickerListView = (RecyclerView) this.mRootView.findViewById(R.id.live_sticker_list);
        this.mStickerListView.setFocusable(false);
        this.mNoneItemView = this.mRootView.findViewById(R.id.live_sticker_none_item);
        this.mNoneSelectView = this.mRootView.findViewById(R.id.live_sticker_none_selected_indicator);
        this.mSelectIndex = -1;
        String currentLiveSticker = CameraSettings.getCurrentLiveSticker();
        if (currentLiveSticker != null) {
            int i = 0;
            while (true) {
                if (i >= this.mStickerList.size()) {
                    break;
                } else if (currentLiveSticker.equals(this.mStickerList.get(i).hash)) {
                    this.mSelectIndex = i;
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mNoneSelectView.setVisibility(this.mSelectIndex == -1 ? 0 : 8);
        this.mNoneItemView.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass1 */

            public void onClick(View view) {
                CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_STICKER_OFF);
                FragmentLiveSticker.this.onItemSelected(-1, null);
            }
        });
        this.mAdapter = new StickerItemAdapter(getContext(), this.mStickerList, this.mSelectIndex, new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentLiveSticker.this.onItemSelected(i, view);
            }
        });
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "live_sticker_list");
        this.mLayoutManager.setOrientation(0);
        this.mStickerListView.setLayoutManager(this.mLayoutManager);
        this.mStickerListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass3 */
            final int mLeftMargin = FragmentLiveSticker.this.getResources().getDimensionPixelSize(R.dimen.live_sticker_list_margin_left);
            final int mRightMargin = FragmentLiveSticker.this.getResources().getDimensionPixelSize(R.dimen.live_sticker_list_margin_right);

            @Override // android.support.v7.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                if (isLayoutRTL) {
                    if (childAdapterPosition == 0) {
                        rect.set(0, 0, this.mLeftMargin, 0);
                    } else if (childAdapterPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                        rect.set(this.mRightMargin, 0, 0, 0);
                    }
                } else if (childAdapterPosition == 0) {
                    rect.set(this.mLeftMargin, 0, 0, 0);
                } else if (childAdapterPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                    rect.set(0, 0, this.mRightMargin, 0);
                }
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        defaultItemAnimator.setSupportsChangeAnimations(false);
        this.mStickerListView.setItemAnimator(defaultItemAnimator);
        this.mStickerListView.setAdapter(this.mAdapter);
        setItemInCenter(this.mSelectIndex);
        LiveResourceDownloadManager.getInstance().addDownloadListener(this.mDownloadListener);
        if (this.mSelectIndex == -1) {
            reload();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LiveResourceDownloadManager.getInstance().removeDownloadListener(this.mDownloadListener);
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(final int i, final View view) {
        String str;
        this.mFutureSelectIndex = i;
        final LiveStickerInfo liveStickerInfo = i >= 0 ? this.mStickerList.get(i) : sNoneSticker;
        int downloadState = liveStickerInfo.getDownloadState();
        Log.v(TAG, "select sticker " + i + ": " + liveStickerInfo.hash + ", " + downloadState + ", " + ((LiveResource) liveStickerInfo).isLocal + ", " + liveStickerInfo.url);
        boolean z = true;
        if (liveStickerInfo == sMoreSticker) {
            PackageManager packageManager = getActivity().getPackageManager();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(MORE_URI));
            if (packageManager.queryIntentActivities(intent, 65536).size() > 0) {
                try {
                    if (packageManager.getPackageInfo(PACKAGE_NAME, 0).versionCode < 320) {
                        intent = new Intent("android.intent.action.VIEW", Uri.parse(MAIN_URI));
                    }
                    z = false;
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            if (z) {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(MARKET_URI));
            }
            CameraStatUtils.trackLiveStickerMore(z);
            startActivity(intent);
        } else if (((LiveResource) liveStickerInfo).isLocal || downloadState == 5) {
            int i2 = this.mSelectIndex;
            this.mSelectIndex = i;
            this.mNoneSelectView.setVisibility(liveStickerInfo == sNoneSticker ? 0 : 8);
            this.mAdapter.setSelectIndex(this.mSelectIndex);
            this.mAdapter.notifyItemChanged(i2);
            this.mAdapter.notifyItemChanged(this.mSelectIndex);
            scrollIfNeed(this.mSelectIndex);
            CameraSettings.setCurrentLiveSticker(liveStickerInfo.hash, liveStickerInfo.name, liveStickerInfo.hint);
            ModeProtocol.StickerProtocol stickerProtocol = (ModeProtocol.StickerProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(178);
            if (stickerProtocol != null) {
                stickerProtocol.onStickerChanged(liveStickerInfo.hash);
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                String str2 = liveStickerInfo.hint;
                if ((str2 == null || str2.equals("")) && ((str = liveStickerInfo.hintIcon) == null || str.equals(""))) {
                    mainContentProtocol.setCenterHint(8, null, null, 0);
                } else {
                    mainContentProtocol.setCenterHint(0, liveStickerInfo.hint, liveStickerInfo.hintIcon, 5000);
                }
            }
        } else if (downloadState == 1 || downloadState == 3) {
            Completable.fromAction(new Action() {
                /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass5 */

                @Override // io.reactivex.functions.Action
                public void run() throws IOException {
                    String str = FileUtils.STICKER_RESOURCE_DIR + liveStickerInfo.hash + ".zip";
                    try {
                        Util.verifyZip(str, FileUtils.STICKER_RESOURCE_DIR + liveStickerInfo.hash, 32768);
                    } catch (IOException e2) {
                        new File(str).delete();
                        throw e2;
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                /* class com.android.camera.fragment.live.FragmentLiveSticker.AnonymousClass4 */

                @Override // io.reactivex.CompletableObserver
                public void onComplete() {
                    liveStickerInfo.downloadState = 5;
                    FragmentLiveSticker.this.onItemSelected(i, view);
                }

                @Override // io.reactivex.CompletableObserver
                public void onError(Throwable th) {
                    Log.e(FragmentLiveSticker.TAG, "verify " + liveStickerInfo.hash + ".zip failed", th);
                    liveStickerInfo.downloadState = 0;
                    FragmentLiveSticker.this.mAdapter.notifyItemChanged(i);
                    FragmentLiveSticker.this.onItemSelected(i, view);
                }

                @Override // io.reactivex.CompletableObserver
                public void onSubscribe(Disposable disposable) {
                }
            });
        } else if (downloadState != 2 && CtaNoticeFragment.checkCta(getActivity().getFragmentManager(), false, this, 1)) {
            liveStickerInfo.downloadState = 2;
            scrollIfNeed(i);
            this.mAdapter.notifyItemChanged(i);
            LiveResourceDownloadManager.getInstance().download(liveStickerInfo, this.mDownloadHelper);
        }
    }

    @Override // com.android.camera.fragment.CtaNoticeFragment.OnCtaNoticeClickListener
    public void onNegativeClick(DialogInterface dialogInterface, int i) {
    }

    @Override // com.android.camera.fragment.CtaNoticeFragment.OnCtaNoticeClickListener
    public void onPositiveClick(DialogInterface dialogInterface, int i) {
        reload();
    }
}
