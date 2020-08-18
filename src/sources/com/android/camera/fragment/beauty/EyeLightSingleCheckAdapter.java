package com.android.camera.fragment.beauty;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import java.util.List;

public class EyeLightSingleCheckAdapter extends RecyclerView.Adapter<SingleCheckViewHolder> {
    /* access modifiers changed from: private */
    public int mBackgroundColorNormal;
    /* access modifiers changed from: private */
    public int mBackgroundColorPressed;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int mItemHorizontalMargin = 0;
    /* access modifiers changed from: private */
    public int mPreSelectedItem = 0;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public int mSelectedItem = 0;
    private List<EyeLightItem> mSingleCheckList;
    private AdapterView.OnItemClickListener onItemClickListener;

    public static class EyeLightItem {
        private int mImageResource;
        private int mTextResource;
        private String mType;

        public EyeLightItem(String str, int i, int i2) {
            this.mImageResource = i;
            this.mTextResource = i2;
            this.mType = str;
        }

        public int getImageResource() {
            return this.mImageResource;
        }

        public int getTextResource() {
            return this.mTextResource;
        }

        public String getType() {
            return this.mType;
        }
    }

    class SingleCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /* access modifiers changed from: private */
        public View itemView;
        private EyeLightSingleCheckAdapter mAdapter;
        private ImageView mBase;

        public SingleCheckViewHolder(View view, EyeLightSingleCheckAdapter eyeLightSingleCheckAdapter) {
            super(view);
            this.mAdapter = eyeLightSingleCheckAdapter;
            this.itemView = view;
            this.mBase = (ImageView) view.findViewById(R.id.eye_light_item_icon);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBase.getLayoutParams();
            marginLayoutParams.setMarginStart(EyeLightSingleCheckAdapter.this.mItemHorizontalMargin);
            marginLayoutParams.setMarginEnd(EyeLightSingleCheckAdapter.this.mItemHorizontalMargin);
            view.setOnClickListener(this);
        }

        private void colorAnimate(final ImageView imageView, int i, int i2) {
            ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(i), Integer.valueOf(i2));
            ofObject.setDuration(200L);
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.beauty.EyeLightSingleCheckAdapter.SingleCheckViewHolder.AnonymousClass1 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    imageView.setBackgroundTintList(ColorStateList.valueOf(((Integer) valueAnimator.getAnimatedValue()).intValue()));
                }
            });
            ofObject.start();
        }

        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != EyeLightSingleCheckAdapter.this.mSelectedItem) {
                EyeLightSingleCheckAdapter eyeLightSingleCheckAdapter = EyeLightSingleCheckAdapter.this;
                int unused = eyeLightSingleCheckAdapter.mPreSelectedItem = eyeLightSingleCheckAdapter.mSelectedItem;
                int unused2 = EyeLightSingleCheckAdapter.this.mSelectedItem = adapterPosition;
                SingleCheckViewHolder singleCheckViewHolder = (SingleCheckViewHolder) EyeLightSingleCheckAdapter.this.mRecyclerView.findViewHolderForAdapterPosition(EyeLightSingleCheckAdapter.this.mPreSelectedItem);
                SingleCheckViewHolder singleCheckViewHolder2 = (SingleCheckViewHolder) EyeLightSingleCheckAdapter.this.mRecyclerView.findViewHolderForAdapterPosition(EyeLightSingleCheckAdapter.this.mSelectedItem);
                EyeLightSingleCheckAdapter.this.mContext.getResources();
                if (singleCheckViewHolder != null) {
                    colorAnimate(singleCheckViewHolder.mBase, EyeLightSingleCheckAdapter.this.mBackgroundColorPressed, EyeLightSingleCheckAdapter.this.mBackgroundColorNormal);
                }
                colorAnimate(singleCheckViewHolder2.mBase, EyeLightSingleCheckAdapter.this.mBackgroundColorNormal, EyeLightSingleCheckAdapter.this.mBackgroundColorPressed);
                if (singleCheckViewHolder == null) {
                    this.mAdapter.notifyItemChanged(EyeLightSingleCheckAdapter.this.mPreSelectedItem);
                }
                this.mAdapter.onItemHolderClick(this);
            }
        }

        public void setDataToView(EyeLightItem eyeLightItem, int i) throws Exception {
            String str;
            this.itemView.setTag(eyeLightItem);
            Resources resources = EyeLightSingleCheckAdapter.this.mContext.getResources();
            if (Util.isAccessible()) {
                View view = this.itemView;
                if (i == EyeLightSingleCheckAdapter.this.mSelectedItem) {
                    str = resources.getString(eyeLightItem.getTextResource()) + resources.getString(R.string.accessibility_open);
                } else {
                    str = resources.getString(eyeLightItem.getTextResource()) + resources.getString(R.string.accessibility_closed);
                }
                view.setContentDescription(str);
            }
            this.mBase.setImageResource(eyeLightItem.getImageResource());
            this.mBase.setBackgroundTintList(ColorStateList.valueOf(i == EyeLightSingleCheckAdapter.this.mSelectedItem ? EyeLightSingleCheckAdapter.this.mBackgroundColorPressed : EyeLightSingleCheckAdapter.this.mBackgroundColorNormal));
        }
    }

    public EyeLightSingleCheckAdapter(Context context, List<EyeLightItem> list, int i) {
        this.mContext = context;
        this.mSingleCheckList = list;
        this.mItemHorizontalMargin = i;
        this.mBackgroundColorPressed = this.mContext.getResources().getColor(R.color.beautycamera_eye_light_item_background_pressed);
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (uiStyle == 1 || uiStyle == 3) {
            this.mBackgroundColorNormal = this.mContext.getResources().getColor(R.color.beautycamera_eye_light_item_background_normal_full_screen);
        } else {
            this.mBackgroundColorNormal = this.mContext.getResources().getColor(R.color.beautycamera_eye_light_item_background_normal_half_screen);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mSingleCheckList.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    public void onBindViewHolder(SingleCheckViewHolder singleCheckViewHolder, int i) {
        try {
            singleCheckViewHolder.setDataToView(this.mSingleCheckList.get(i), i);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public SingleCheckViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SingleCheckViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.eye_light_item, viewGroup, false), this);
    }

    public void onItemHolderClick(SingleCheckViewHolder singleCheckViewHolder) {
        AdapterView.OnItemClickListener onItemClickListener2 = this.onItemClickListener;
        if (onItemClickListener2 != null) {
            onItemClickListener2.onItemClick(null, singleCheckViewHolder.itemView, singleCheckViewHolder.getAdapterPosition(), singleCheckViewHolder.getItemId());
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void setSelectedPosition(int i) {
        this.mSelectedItem = i;
    }
}
