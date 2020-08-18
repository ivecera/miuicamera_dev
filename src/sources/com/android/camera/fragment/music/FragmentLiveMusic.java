package com.android.camera.fragment.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import java.util.ArrayList;
import java.util.List;

public class FragmentLiveMusic extends DialogFragment implements View.OnClickListener {
    public static final int LOCAL = 1;
    public static final int ONLINE = 0;
    public static final String TAG = "FragmentLiveMusic";
    private ImageView mCloseImageView;
    private int mCurrentItemIndex;
    private TextView mHotMusicText;
    private TextView mLocalMusicText;
    private int mOldOriginVolumeStream;
    private ViewPager mViewPager;

    public interface Mp3DownloadCallback {
        void onCompleted(LiveMusicInfo liveMusicInfo);

        void onFailed();
    }

    private class MusicPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        public MusicPagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
            super(fragmentManager);
            this.mFragmentList = list;
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            List<Fragment> list = this.mFragmentList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // android.support.v4.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.mFragmentList.get(i);
        }
    }

    /* access modifiers changed from: private */
    public void onClickLocal() {
        int color = ContextCompat.getColor(getContext(), R.color.beautycamera_beauty_advanced_item_backgroud_pressed);
        if (this.mCurrentItemIndex == 0) {
            this.mCurrentItemIndex = 1;
            this.mLocalMusicText.setTextColor(color);
            this.mLocalMusicText.setAlpha(1.0f);
            this.mHotMusicText.setAlpha(0.5f);
            this.mHotMusicText.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            this.mViewPager.setCurrentItem(1, false);
        }
    }

    /* access modifiers changed from: private */
    public void onClickOnline() {
        int color = ContextCompat.getColor(getContext(), R.color.beautycamera_beauty_advanced_item_backgroud_pressed);
        if (this.mCurrentItemIndex == 1) {
            this.mCurrentItemIndex = 0;
            this.mLocalMusicText.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            this.mLocalMusicText.setAlpha(0.5f);
            this.mHotMusicText.setAlpha(1.0f);
            this.mHotMusicText.setTextColor(color);
            this.mViewPager.setCurrentItem(0, false);
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mHotMusicText = (TextView) view.findViewById(R.id.music_hotmusic);
        this.mLocalMusicText = (TextView) view.findViewById(R.id.music_localmusic);
        this.mCloseImageView = (ImageView) view.findViewById(R.id.music_item_close);
        this.mCloseImageView.setOnClickListener(this);
        this.mViewPager = (ViewPager) view.findViewById(R.id.music_item_viewpager);
        ArrayList arrayList = new ArrayList();
        FragmentLiveMusicPager fragmentLiveMusicPager = new FragmentLiveMusicPager();
        if (!Util.isGlobalVersion()) {
            Bundle bundle = new Bundle();
            bundle.putInt("ITEM_TYPE", 0);
            fragmentLiveMusicPager.setArguments(bundle);
            arrayList.add(fragmentLiveMusicPager);
            this.mHotMusicText.setOnClickListener(this);
            this.mLocalMusicText.setOnClickListener(this);
        } else {
            view.findViewById(R.id.music_select_title).setVisibility(8);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("ITEM_TYPE", 1);
        FragmentLiveMusicPager fragmentLiveMusicPager2 = new FragmentLiveMusicPager();
        fragmentLiveMusicPager2.setArguments(bundle2);
        arrayList.add(fragmentLiveMusicPager2);
        this.mViewPager.setAdapter(new MusicPagerAdapter(getChildFragmentManager(), arrayList));
        this.mViewPager.setCurrentItem(0);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /* class com.android.camera.fragment.music.FragmentLiveMusic.AnonymousClass1 */

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f2, int i2) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (i == 0) {
                    FragmentLiveMusic.this.onClickOnline();
                } else if (i == 1) {
                    FragmentLiveMusic.this.onClickLocal();
                }
            }
        });
        if (Util.isContentViewExtendToTopEdges()) {
            CompatibilityUtils.setCutoutModeShortEdges(getDialog().getWindow());
        }
        getDialog().getWindow().getDecorView().setSystemUiVisibility(768);
        getDialog().getWindow().addFlags(Integer.MIN_VALUE);
        this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
        getActivity().setVolumeControlStream(3);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.music_hotmusic:
                onClickOnline();
                return;
            case R.id.music_item_close:
                FragmentUtils.removeFragmentByTag(getFragmentManager(), TAG);
                return;
            case R.id.music_localmusic:
                onClickLocal();
                return;
            default:
                return;
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_live_music, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment, android.support.v4.app.DialogFragment
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
    }
}
