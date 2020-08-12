package com.android.camera.fragment.vv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.BaseFragmentPagerAdapter;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;

public class FragmentVVPreview extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace {
    /* access modifiers changed from: private */
    public BaseFragmentPagerAdapter mPreviewAdapter;
    private int mPreviewIndex;
    private ViewGroup mPreviewLayout;
    /* access modifiers changed from: private */
    public ViewPager mPreviewViewPager;
    /* access modifiers changed from: private */
    public ResourceSelectedListener mResourceSelectedListener;
    /* access modifiers changed from: private */
    public VVList mVVList;

    private void initViewPager() {
        ViewGroup.LayoutParams layoutParams = this.mPreviewViewPager.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.vv_preview_image_height);
        int i = (int) ((((float) dimensionPixelSize) / 9.0f) * 16.0f);
        layoutParams.width = i;
        final int i2 = Util.sWindowWidth;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.vv_preview_page_margin);
        final int i3 = (i2 - i) / 2;
        this.mPreviewViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /* class com.android.camera.fragment.vv.FragmentVVPreview.AnonymousClass1 */

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f2, int i2) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (FragmentVVPreview.this.mResourceSelectedListener != null) {
                    FragmentVVPreview.this.mResourceSelectedListener.onResourceSelected((VVItem) FragmentVVPreview.this.mVVList.getItem(i));
                }
            }
        });
        this.mPreviewLayout.setOnTouchListener(new View.OnTouchListener() {
            /* class com.android.camera.fragment.vv.FragmentVVPreview.AnonymousClass2 */
            int nextItem = -1;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action != 1 || FragmentVVPreview.this.mPreviewViewPager == null || FragmentVVPreview.this.mPreviewAdapter == null) {
                        return false;
                    }
                    if (this.nextItem >= 0) {
                        FragmentVVPreview.this.mPreviewViewPager.setCurrentItem(this.nextItem);
                        this.nextItem = -1;
                    }
                    return true;
                } else if (FragmentVVPreview.this.mPreviewViewPager == null || FragmentVVPreview.this.mPreviewAdapter == null) {
                    return false;
                } else {
                    float x = motionEvent.getX();
                    int i = i3;
                    if (x < ((float) i)) {
                        int currentItem = FragmentVVPreview.this.mPreviewViewPager.getCurrentItem();
                        if (currentItem > 0) {
                            this.nextItem = currentItem - 1;
                        }
                    } else if (x < ((float) (i2 - i))) {
                        return false;
                    } else {
                        int currentItem2 = FragmentVVPreview.this.mPreviewViewPager.getCurrentItem();
                        if (currentItem2 < FragmentVVPreview.this.mPreviewAdapter.getCount() - 1) {
                            this.nextItem = currentItem2 + 1;
                        }
                    }
                    return true;
                }
            }
        });
        this.mPreviewViewPager.setPageMargin(dimensionPixelSize2);
        this.mPreviewViewPager.setPageTransformer(true, new VVPreviewTransformer());
        ArrayList arrayList = new ArrayList(this.mVVList.getSize());
        for (int i4 = 0; i4 < this.mVVList.getSize(); i4++) {
            FragmentVVPreviewItem fragmentVVPreviewItem = new FragmentVVPreviewItem();
            fragmentVVPreviewItem.setData(i4, (VVItem) this.mVVList.getItem(i4), i, dimensionPixelSize, this, this.mPreviewIndex);
            arrayList.add(fragmentVVPreviewItem);
        }
        this.mPreviewAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
        this.mPreviewViewPager.setAdapter(this.mPreviewAdapter);
        this.mPreviewViewPager.setOffscreenPageLimit(2);
        this.mPreviewViewPager.setCurrentItem(this.mPreviewIndex, false);
    }

    private void transformToGallery(int i, View view) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        if (view != null) {
            beginTransaction.addSharedElement(view, ViewCompat.getTransitionName(view));
        }
        FragmentVVGallery fragmentVVGallery = (FragmentVVGallery) FragmentUtils.getFragmentByTag(getFragmentManager(), String.valueOf((int) BaseFragmentDelegate.FRAGMENT_VV_GALLERY));
        if (fragmentVVGallery == null) {
            FragmentVVGallery fragmentVVGallery2 = new FragmentVVGallery();
            fragmentVVGallery2.setResourceSelectedListener(this.mResourceSelectedListener);
            fragmentVVGallery2.registerProtocol();
            fragmentVVGallery2.setPreviewData(this.mPreviewViewPager.getCurrentItem());
            beginTransaction.addToBackStack(getFragmentTag()).replace(R.id.vv_lift, fragmentVVGallery2, fragmentVVGallery2.getFragmentTag()).commitAllowingStateLoss();
            return;
        }
        fragmentVVGallery.setPreviewData(this.mPreviewViewPager.getCurrentItem());
        beginTransaction.remove(this).show(fragmentVVGallery).commitAllowingStateLoss();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_PREVIEW;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_preview;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mPreviewLayout = (ViewGroup) view.findViewById(R.id.vv_preview_layout);
        this.mPreviewViewPager = (ViewPager) view.findViewById(R.id.vv_viewpager);
        initViewPager();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i == 2) {
            return false;
        }
        transformToGallery(0, null);
        return true;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.vv_preview_item_collapsing) {
            transformToGallery(((Integer) view.getTag()).intValue(), ((ViewGroup) view.getParent()).findViewById(R.id.vv_preview_item_image));
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(17760257));
        setSharedElementReturnTransition(null);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    public void setPreviewData(int i, VVList vVList) {
        this.mPreviewIndex = i;
        this.mVVList = vVList;
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
