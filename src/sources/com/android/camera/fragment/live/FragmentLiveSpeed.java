package com.android.camera.fragment.live;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.List;

public class FragmentLiveSpeed extends FragmentLiveBase {
    private static final int FRAGMENT_INFO = 4093;
    private static final String TAG = "FragmentLiveSpeed";
    private static final List<LiveSpeedItem> sSpeedItemList = new ArrayList();
    private SpeedItemAdapter mAdapter;
    private LinearLayoutManagerWrapper mLayoutManager;
    private View mRootView;
    private int mSelectIndex;
    private RecyclerView mSpeedListView;

    private static class LiveSpeedItem {
        int mTextId;

        public LiveSpeedItem(int i) {
            this.mTextId = i;
        }

        public int getTextId() {
            return this.mTextId;
        }
    }

    private static class SpeedItemAdapter extends RecyclerView.Adapter<SpeedItemHolder> {
        int mColorNormal;
        int mColorSelected;
        LayoutInflater mLayoutInflater;
        AdapterView.OnItemClickListener mListener;
        int mSelectIndex;
        List<LiveSpeedItem> mSpeedItemList;

        class SpeedItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener {
            public SpeedItemHolder(View view) {
                super(view);
                view.setOnClickListener(this);
            }

            public void onClick(View view) {
                int adapterPosition = getAdapterPosition();
                SpeedItemAdapter speedItemAdapter = SpeedItemAdapter.this;
                int i = speedItemAdapter.mSelectIndex;
                if (adapterPosition != i) {
                    speedItemAdapter.mSelectIndex = adapterPosition;
                    speedItemAdapter.mListener.onItemClick(null, view, adapterPosition, getItemId());
                    SpeedItemAdapter.this.notifyItemChanged(i);
                    SpeedItemAdapter.this.notifyItemChanged(adapterPosition);
                }
            }
        }

        public SpeedItemAdapter(Context context, List<LiveSpeedItem> list, int i, AdapterView.OnItemClickListener onItemClickListener) {
            this.mSpeedItemList = list;
            this.mSelectIndex = i;
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mListener = onItemClickListener;
            this.mColorNormal = context.getResources().getColor(R.color.white);
            this.mColorSelected = context.getResources().getColor(R.color.common_color_00a8ff);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mSpeedItemList.size();
        }

        public LiveSpeedItem getSticker(int i) {
            return this.mSpeedItemList.get(i);
        }

        public void onBindViewHolder(SpeedItemHolder speedItemHolder, int i) {
            LiveSpeedItem liveSpeedItem = this.mSpeedItemList.get(i);
            ((RecyclerView.ViewHolder) speedItemHolder).itemView.setTag(liveSpeedItem);
            TextView textView = (TextView) speedItemHolder.getView(R.id.item_text);
            if (i == this.mSelectIndex) {
                textView.setTextColor(this.mColorSelected);
            } else {
                textView.setTextColor(this.mColorNormal);
            }
            textView.setText(liveSpeedItem.getTextId());
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public SpeedItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new SpeedItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_live_speed_item, viewGroup, false));
        }
    }

    static {
        for (int i : CameraSettings.sLiveSpeedTextList) {
            sSpeedItemList.add(new LiveSpeedItem(i));
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4093;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_live_speed;
    }

    /* access modifiers changed from: protected */
    public List<LiveSpeedItem> getSpeedItemList() {
        return sSpeedItemList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        final boolean isLayoutRTL = Util.isLayoutRTL(getContext());
        this.mSpeedListView = (RecyclerView) this.mRootView.findViewById(R.id.live_speed_list);
        this.mSpeedListView.setFocusable(false);
        this.mSelectIndex = Integer.valueOf(CameraSettings.getCurrentLiveSpeed()).intValue();
        this.mAdapter = new SpeedItemAdapter(getContext(), getSpeedItemList(), this.mSelectIndex, new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.live.FragmentLiveSpeed.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentLiveSpeed.this.onItemSelected(i);
            }
        });
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "live_speed_list");
        this.mLayoutManager.setOrientation(0);
        this.mSpeedListView.setLayoutManager(this.mLayoutManager);
        this.mSpeedListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            /* class com.android.camera.fragment.live.FragmentLiveSpeed.AnonymousClass2 */
            final int mListMargin = FragmentLiveSpeed.this.getResources().getDimensionPixelSize(R.dimen.live_speed_list_margin);

            @Override // android.support.v7.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                int itemCount = ((FragmentLiveSpeed.this.getResources().getDisplayMetrics().widthPixels - (this.mListMargin * 2)) - (recyclerView.getAdapter().getItemCount() * FragmentLiveSpeed.this.getResources().getDimensionPixelSize(R.dimen.live_speed_item_width))) / (recyclerView.getAdapter().getItemCount() - 1);
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                if (isLayoutRTL) {
                    if (childAdapterPosition == 0) {
                        rect.set(0, 0, this.mListMargin, 0);
                    } else if (childAdapterPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                        rect.set(this.mListMargin, 0, itemCount, 0);
                    } else {
                        rect.set(0, 0, itemCount, 0);
                    }
                } else if (childAdapterPosition == 0) {
                    rect.set(this.mListMargin, 0, 0, 0);
                } else if (childAdapterPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                    rect.set(itemCount, 0, this.mListMargin, 0);
                } else {
                    rect.set(itemCount, 0, 0, 0);
                }
            }
        });
        this.mSpeedListView.setAdapter(this.mAdapter);
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(int i) {
        CameraSettings.setCurrentLiveSpeed(String.valueOf(i));
        ModeProtocol.LiveSpeedChanges liveSpeedChanges = (ModeProtocol.LiveSpeedChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(232);
        if (liveSpeedChanges != null) {
            liveSpeedChanges.setRecordSpeed(i);
        }
    }
}
