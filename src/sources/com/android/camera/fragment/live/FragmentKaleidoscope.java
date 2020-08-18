package com.android.camera.fragment.live;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningKaleidoscope;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.bumptech.glide.request.f;
import java.util.List;

public class FragmentKaleidoscope extends FragmentLiveBase {
    private static final int FRAGMENT_INFO = 65530;
    private static final String TAG = "FragmentKaleidoscope";
    private static final ComponentDataItem sNoneSticker = new ComponentDataItem(-1, -1, -1, "0");
    private KaleidoscopeAdapter mAdapter;
    private ComponentRunningKaleidoscope mComponentRunningKaleidoscope;
    int mFutureSelectIndex;
    private int mItemWidth;
    private List<ComponentDataItem> mKaleidoscopeList;
    private RecyclerView mKaleidoscopeRecyclerView;
    private LinearLayoutManagerWrapper mLayoutManager;
    private View mNoneItemView;
    private View mNoneSelectView;
    private View mRootView;
    int mSelectIndex = -1;
    private int mTotalWidth;

    private static class KaleidoscopeAdapter extends RecyclerView.Adapter<KaleidoItemHolder> {
        Context mContext;
        f mGlideOptions = new f().L(R.drawable.ic_live_sticker_placeholder);
        List<ComponentDataItem> mKaleidoscopeList;
        LayoutInflater mLayoutInflater;
        AdapterView.OnItemClickListener mListener;
        int mSelectIndex;

        class KaleidoItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener {
            public KaleidoItemHolder(View view) {
                super(view);
                view.setOnClickListener(this);
            }

            public void onClick(View view) {
                int adapterPosition = getAdapterPosition();
                KaleidoscopeAdapter kaleidoscopeAdapter = KaleidoscopeAdapter.this;
                if (adapterPosition != kaleidoscopeAdapter.mSelectIndex) {
                    kaleidoscopeAdapter.mListener.onItemClick(null, view, adapterPosition, getItemId());
                }
            }
        }

        public KaleidoscopeAdapter(Context context, List<ComponentDataItem> list, int i, AdapterView.OnItemClickListener onItemClickListener) {
            this.mContext = context;
            this.mKaleidoscopeList = list;
            this.mSelectIndex = i;
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mListener = onItemClickListener;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mKaleidoscopeList.size();
        }

        public void onBindViewHolder(KaleidoItemHolder kaleidoItemHolder, int i) {
            View view = kaleidoItemHolder.getView(R.id.item_selected_indicator);
            ComponentDataItem componentDataItem = this.mKaleidoscopeList.get(i);
            ((RecyclerView.ViewHolder) kaleidoItemHolder).itemView.setTag(componentDataItem);
            ((ImageView) kaleidoItemHolder.getView(R.id.item_image)).setImageResource(componentDataItem.mIconSelectedRes);
            if (i == this.mSelectIndex) {
                view.setVisibility(0);
            }
            if (i != this.mSelectIndex) {
                view.setVisibility(8);
            }
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public KaleidoItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new KaleidoItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_kaleidoscope_item, viewGroup, false));
        }

        public void setSelectIndex(int i) {
            this.mSelectIndex = i;
        }
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

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65530;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_kaleidoscope;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.live_sticker_item_size);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        final boolean isLayoutRTL = Util.isLayoutRTL(getContext());
        this.mKaleidoscopeRecyclerView = (RecyclerView) this.mRootView.findViewById(R.id.kaleidoscope_list);
        this.mNoneItemView = this.mRootView.findViewById(R.id.kaleidoscope_none_item);
        this.mNoneSelectView = this.mRootView.findViewById(R.id.kaleidoscope_none_selected_indicator);
        this.mSelectIndex = -1;
        this.mComponentRunningKaleidoscope = DataRepository.dataItemRunning().getComponentRunningKaleidoscope();
        this.mKaleidoscopeList = this.mComponentRunningKaleidoscope.getItems();
        String kaleidoscopeValue = this.mComponentRunningKaleidoscope.getKaleidoscopeValue();
        if (kaleidoscopeValue != null) {
            int i = 0;
            while (true) {
                if (i >= this.mKaleidoscopeList.size()) {
                    break;
                } else if (kaleidoscopeValue.equals(this.mKaleidoscopeList.get(i).mValue)) {
                    this.mSelectIndex = i;
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mNoneSelectView.setVisibility(this.mSelectIndex == -1 ? 0 : 8);
        this.mNoneItemView.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.live.FragmentKaleidoscope.AnonymousClass1 */

            public void onClick(View view) {
                FragmentKaleidoscope.this.onItemSelected(-1, null);
            }
        });
        this.mAdapter = new KaleidoscopeAdapter(getContext(), this.mKaleidoscopeList, this.mSelectIndex, new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.live.FragmentKaleidoscope.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentKaleidoscope.this.onItemSelected(i, view);
            }
        });
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "kaleidoscope_list");
        this.mLayoutManager.setOrientation(0);
        this.mKaleidoscopeRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mKaleidoscopeRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            /* class com.android.camera.fragment.live.FragmentKaleidoscope.AnonymousClass3 */
            final int mLeftMargin = FragmentKaleidoscope.this.getResources().getDimensionPixelSize(R.dimen.live_sticker_list_margin_left);
            final int mRightMargin = FragmentKaleidoscope.this.getResources().getDimensionPixelSize(R.dimen.live_sticker_list_margin_right);

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
        this.mKaleidoscopeRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mKaleidoscopeRecyclerView.setAdapter(this.mAdapter);
        setItemInCenter(this.mSelectIndex);
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(int i, View view) {
        this.mFutureSelectIndex = i;
        ComponentDataItem componentDataItem = i >= 0 ? this.mKaleidoscopeList.get(i) : sNoneSticker;
        Log.v(TAG, "select kaleidoscope " + i);
        int i2 = this.mSelectIndex;
        this.mSelectIndex = i;
        this.mNoneSelectView.setVisibility(componentDataItem == sNoneSticker ? 0 : 8);
        this.mAdapter.setSelectIndex(this.mSelectIndex);
        this.mAdapter.notifyItemChanged(i2);
        this.mAdapter.notifyItemChanged(this.mSelectIndex);
        scrollIfNeed(this.mSelectIndex);
        this.mComponentRunningKaleidoscope.setKaleidoscopeValue(componentDataItem.mValue);
        CameraStatUtils.trackKaleidoscopeValue(componentDataItem.mValue);
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).setKaleidoscope(componentDataItem.mValue);
    }
}
