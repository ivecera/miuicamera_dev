package com.android.camera.fragment.vv;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.VlogViewModel;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.resource.BaseResourceList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentVVGallery extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private VVGalleryAdapter mGalleryAdapter;
    private int mHolderWidth;
    private LinearLayoutManagerWrapper mLayoutManager;
    private int mPreviewIndex = -1;
    private View mProgressView;
    private RecyclerView mRecyclerView;
    private View mRecyclerViewLayout;
    private ResourceSelectedListener mResourceSelectedListener;
    private int mTotalWidth;
    private VVList mVVList;

    private static class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected int mEffectListLeft;
        protected int mHorizontalPadding;
        protected int mVerticalPadding;

        public EffectItemPadding(Context context) {
            this.mHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.effect_item_padding_horizontal);
            this.mVerticalPadding = context.getResources().getDimensionPixelSize(R.dimen.effect_item_padding_vertical);
            this.mEffectListLeft = context.getResources().getDimensionPixelSize(R.dimen.effect_list_padding_left);
        }

        @Override // android.support.v7.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = recyclerView.getChildPosition(view) == 0 ? this.mEffectListLeft : 0;
            int i2 = this.mVerticalPadding;
            rect.set(i, i2, this.mHorizontalPadding, i2);
        }
    }

    private void initList() {
        if (isAdded()) {
            DataRepository.dataItemLive().setVVVersion(((BaseResourceList) this.mVVList).version);
            this.mResourceSelectedListener.onResourceReady();
            int i = this.mPreviewIndex;
            if (i < 0 || i >= this.mVVList.getSize()) {
                this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(0));
            } else {
                this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(this.mPreviewIndex));
            }
            this.mRecyclerViewLayout.setVisibility(0);
            this.mProgressView.setVisibility(8);
            this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "vv_gallery");
            this.mLayoutManager.setOrientation(0);
            this.mGalleryAdapter = new VVGalleryAdapter(this.mVVList, this.mLayoutManager, this.mPreviewIndex, this, this.mResourceSelectedListener);
            this.mRecyclerView.setLayoutManager(this.mLayoutManager);
            this.mRecyclerView.addItemDecoration(new EffectItemPadding(getContext()));
            this.mRecyclerView.setAdapter(this.mGalleryAdapter);
            int i2 = this.mPreviewIndex;
            if (i2 >= 0) {
                setItemInCenter(i2);
            }
            DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
            defaultItemAnimator.setChangeDuration(150);
            defaultItemAnimator.setMoveDuration(150);
            defaultItemAnimator.setAddDuration(150);
            this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        }
    }

    private void loadItemList() {
        this.mVVList = ((VlogViewModel) DataRepository.dataItemObservable().get(VlogViewModel.class)).getVVList();
        if (this.mVVList != null) {
            initList();
            return;
        }
        this.mRecyclerViewLayout.setVisibility(8);
        this.mProgressView.setVisibility(0);
        this.mDisposable.add(((VlogViewModel) DataRepository.dataItemObservable().get(VlogViewModel.class)).getVVListObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new b(this), a.INSTANCE));
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mHolderWidth / 2));
    }

    private void transformToPreview(int i, View view) {
        FragmentVVPreview fragmentVVPreview = new FragmentVVPreview();
        fragmentVVPreview.setPreviewData(i, this.mVVList);
        fragmentVVPreview.setResourceSelectedListener(this.mResourceSelectedListener);
        fragmentVVPreview.registerProtocol();
        getFragmentManager().beginTransaction().addSharedElement(view, ViewCompat.getTransitionName(view)).addToBackStack(getFragmentTag()).add(R.id.vv_lift, fragmentVVPreview, fragmentVVPreview.getFragmentTag()).hide(this).commitAllowingStateLoss();
    }

    public /* synthetic */ void a(VVList vVList) throws Exception {
        this.mVVList = vVList;
        initList();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_GALLERY;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_gallery;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mProgressView = view.findViewById(R.id.vv_updating);
        this.mRecyclerViewLayout = view.findViewById(R.id.vv_list_layout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.vv_list);
        this.mRecyclerView.setFocusable(false);
        Context context = getContext();
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.vv_list_item_image_width);
        loadItemList();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.ConfigChanges configChanges;
        if (!isVisible() || i != 1 || (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) == null) {
            return false;
        }
        configChanges.onConfigChanged(216);
        return true;
    }

    public void onClick(View view) {
        transformToPreview(((Integer) view.getTag()).intValue(), view.findViewById(R.id.vv_gallery_item_image));
    }

    @Override // android.support.v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        int i;
        VVGalleryAdapter vVGalleryAdapter;
        super.onHiddenChanged(z);
        if (!z && (i = this.mPreviewIndex) != -1 && (vVGalleryAdapter = this.mGalleryAdapter) != null) {
            vVGalleryAdapter.onSelected(i, null, false);
            setItemInCenter(this.mPreviewIndex);
            this.mPreviewIndex = -1;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.mDisposable.clear();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    public void setPreviewData(int i) {
        this.mPreviewIndex = i;
    }

    public void setResourceSelectedListener(ResourceSelectedListener resourceSelectedListener) {
        this.mResourceSelectedListener = resourceSelectedListener;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
    }
}
