package com.android.camera.fragment.vv;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.fragment.BaseViewPagerFragment;
import com.android.camera.ui.TextureVideoView;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.request.f;
import java.util.Locale;

public class FragmentVVPreviewItem extends BaseViewPagerFragment implements View.OnClickListener {
    private View.OnClickListener mClickListener;
    private boolean mFirstPreviewItem;
    private f mGlideOptions;
    private int mImageHeight;
    private int mImageWidth;
    private int mIndex;
    private boolean mIsPlaying;
    private ImageView mPreviewStart;
    private TextureVideoView mTextureVideoView;
    private boolean mTransitionHide;
    private VVItem mVVItem;
    private boolean mVisible;

    private String getDurationString(long j) {
        return String.format(Locale.ENGLISH, "%02d", Integer.valueOf(Math.abs((int) Math.floor((double) (((float) j) / 1000.0f)))));
    }

    private void initView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.vv_preview_item_image);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.vv_preview_item_collapsing);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.vv_preview_item_preview);
        this.mPreviewStart.setOnClickListener(this);
        this.mTextureVideoView = (TextureVideoView) view.findViewById(R.id.vv_preview_texture_video);
        TextView textView = (TextView) view.findViewById(R.id.vv_preview_item_hint);
        view.setTag(Integer.valueOf(this.mIndex));
        imageView2.setTag(Integer.valueOf(this.mIndex));
        imageView2.setOnClickListener(this);
        if (this.mImageWidth > 0 && this.mImageHeight > 0) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            marginLayoutParams.width = this.mImageWidth;
            marginLayoutParams.height = this.mImageHeight;
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mTextureVideoView.getLayoutParams();
            marginLayoutParams2.width = this.mImageWidth;
            marginLayoutParams2.height = this.mImageHeight;
        }
        ViewCompat.setTransitionName(imageView, this.mVVItem.name);
        this.mTextureVideoView.setVisibility(4);
        this.mTextureVideoView.setOnClickListener(this);
        this.mTextureVideoView.setLoop(true);
        this.mGlideOptions = new f();
        this.mGlideOptions.B(false);
        this.mGlideOptions.a(o.NONE);
        c.H(getContext()).load(this.mVVItem.coverPath).b(this.mGlideOptions).a(imageView);
        Resources resources = getResources();
        VVItem vVItem = this.mVVItem;
        textView.setText(resources.getString(R.string.vv_duartion_hint, vVItem.name, Integer.valueOf(vVItem.getEssentialFragmentSize()), getDurationString(this.mVVItem.getTotalDuration())));
    }

    private void pausePlay() {
        if (this.mIsPlaying) {
            this.mIsPlaying = false;
            this.mTextureVideoView.pause();
        }
    }

    private void resumePlay() {
        if (!this.mIsPlaying) {
            this.mIsPlaying = true;
            this.mTextureVideoView.resume();
        }
    }

    private void startPlay() {
        if (!this.mIsPlaying) {
            this.mIsPlaying = true;
            this.mTextureVideoView.setVideoPath(this.mVVItem.previewVideoPath);
            this.mTextureVideoView.setVisibility(0);
            this.mTextureVideoView.start(0);
        }
    }

    private void stopPlay() {
        if (this.mIsPlaying) {
            this.mIsPlaying = false;
            this.mTextureVideoView.stop();
            this.mTextureVideoView.setVisibility(4);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.vv_preview_item_collapsing) {
            stopPlay();
            this.mClickListener.onClick(view);
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_vv_preview_item, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        stopPlay();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mVisible) {
            startPlay();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndJumpOut() {
        super.onViewCreatedAndJumpOut();
        this.mVisible = false;
        stopPlay();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        super.onViewCreatedAndVisibleToUser(z);
        this.mVisible = true;
        if (this.mFirstPreviewItem) {
            this.mFirstPreviewItem = false;
        }
        startPlay();
    }

    public void setData(int i, VVItem vVItem, int i2, int i3, View.OnClickListener onClickListener, int i4) {
        this.mIndex = i;
        this.mVVItem = vVItem;
        this.mImageWidth = i2;
        this.mImageHeight = i3;
        this.mClickListener = onClickListener;
        boolean z = false;
        this.mTransitionHide = Math.abs(i - i4) == 1;
        if (i == i4) {
            z = true;
        }
        this.mFirstPreviewItem = z;
    }
}
