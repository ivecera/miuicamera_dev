package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.widget.baseview.BaseNoScrollGridLayoutManager;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.util.AvatarConfigUtils;
import com.arcsoft.avatar.util.LOG;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditLevelListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int LIST_COLUMN_NUM = 3;
    /* access modifiers changed from: private */
    public static final String TAG = "EditLevelListAdapter";
    private AtomicBoolean isColorNeedNotify = new AtomicBoolean(true);
    private AvatarConfigItemClick mAvatarConfigItemClick = new AvatarConfigItemClick() {
        /* class com.android.camera.fragment.mimoji.EditLevelListAdapter.AnonymousClass2 */

        @Override // com.android.camera.fragment.mimoji.AvatarConfigItemClick
        public void onConfigItemClick(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, boolean z, int i) {
            if (aSAvatarConfigInfo == null) {
                Log.d(EditLevelListAdapter.TAG, "onConfigItemClick, ASAvatarConfigInfo is null");
                return;
            }
            String access$200 = EditLevelListAdapter.TAG;
            Log.d(access$200, "onConfigItemClick, ASAvatarConfigInfo = " + aSAvatarConfigInfo);
            EditLevelListAdapter.this.mItfGvOnItemClickListener.notifyUIChanged();
            AvatarEngineManager.getInstance().setAllNeedUpdate(true, z);
            AvatarEngineManager.getInstance().addAvatarConfig(aSAvatarConfigInfo);
            AvatarConfigUtils.updateConfigID(aSAvatarConfigInfo.configType, aSAvatarConfigInfo.configID, AvatarEngineManager.getInstance().getASAvatarConfigValue());
            EditLevelListAdapter.this.mRenderThread.setConfig(aSAvatarConfigInfo);
            if (!z) {
                return;
            }
            if (!EditLevelListAdapter.this.mRenderThread.getIsRendering()) {
                EditLevelListAdapter.this.mRenderThread.draw(false);
            } else {
                EditLevelListAdapter.this.mRenderThread.setStopRender(true);
            }
        }
    };
    /* access modifiers changed from: private */
    public ClickCheck mClickCheck;
    private Context mContext;
    /* access modifiers changed from: private */
    public ItfGvOnItemClickListener mItfGvOnItemClickListener;
    private CopyOnWriteArrayList<MimojiLevelBean> mLevelDatas;
    private MimojiLevelBean mMimojiLevelBean;
    private List<MimojiThumbnailRecyclerAdapter> mMimojiThumbnailAdapters;
    /* access modifiers changed from: private */
    public MimojiThumbnailRenderThread mRenderThread;

    public interface ItfGvOnItemClickListener {
        void notifyUIChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mColorRecycleView;
        RecyclerView mThumbnailGV;
        TextView mTvSubTitle;

        public ViewHolder(View view) {
            super(view);
            this.mTvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
            this.mColorRecycleView = (RecyclerView) view.findViewById(R.id.color_select);
            this.mColorRecycleView.setFocusable(false);
            this.mThumbnailGV = (RecyclerView) view.findViewById(R.id.thumbnail_gride_view);
            this.mThumbnailGV.setFocusable(false);
        }
    }

    EditLevelListAdapter(Context context, ItfGvOnItemClickListener itfGvOnItemClickListener) {
        this.mContext = context;
        this.mLevelDatas = new CopyOnWriteArrayList<>();
        this.mMimojiThumbnailAdapters = Collections.synchronizedList(new ArrayList());
        this.mItfGvOnItemClickListener = itfGvOnItemClickListener;
    }

    /* access modifiers changed from: private */
    public void onGvItemClick(MimojiThumbnailRecyclerAdapter mimojiThumbnailRecyclerAdapter, int i, int i2) {
        String str = TAG;
        Log.d(str, "outerPosition = " + i + " Select index = " + i2);
        CopyOnWriteArrayList<MimojiLevelBean> copyOnWriteArrayList = this.mLevelDatas;
        if (copyOnWriteArrayList == null || i < 0 || i >= copyOnWriteArrayList.size()) {
            Log.e(TAG, "gv mLevelDatas error");
            return;
        }
        MimojiLevelBean mimojiLevelBean = this.mLevelDatas.get(i);
        if (i2 >= 0 && i2 < mimojiLevelBean.thumnails.size()) {
            AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = mimojiLevelBean.thumnails.get(i2);
            AvatarEngineManager.getInstance().setInnerConfigSelectIndex(mimojiLevelBean.configType, (float) i2);
            if (aSAvatarConfigInfo != null) {
                updateSelectView(mimojiThumbnailRecyclerAdapter, i, i2);
                this.mAvatarConfigItemClick.onConfigItemClick(aSAvatarConfigInfo, false, i);
                return;
            }
            Log.e(TAG, "onGvItemClick AvatarConfig.ASAvatarConfigInfo is null");
            mimojiThumbnailRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f6  */
    private synchronized void showColor(ViewHolder viewHolder, MimojiLevelBean mimojiLevelBean, int i) {
        LinearLayoutManagerWrapper colorLayoutManagerMap;
        int i2;
        RecyclerView recyclerView = viewHolder.mColorRecycleView;
        TextView textView = viewHolder.mTvSubTitle;
        int itemCount = getItemCount();
        AvatarEngineManager.getInstance();
        if (AvatarEngineManager.showConfigTypeName(mimojiLevelBean.configType)) {
            textView.setVisibility(0);
            textView.setText(this.mMimojiLevelBean.configTypeName);
        } else {
            textView.setVisibility(8);
        }
        ArrayList<AvatarConfig.ASAvatarConfigInfo> colorConfigInfos = mimojiLevelBean.getColorConfigInfos();
        if (colorConfigInfos != null && AvatarEngineManager.getInstance().getColorType(mimojiLevelBean.configType) >= 0) {
            if (colorConfigInfos.size() != 0) {
                recyclerView.setVisibility(0);
                if ((!this.isColorNeedNotify.get() || recyclerView.getChildCount() == 0) && recyclerView.getVisibility() == 0) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("fmoji topic:");
                    sb.append(mimojiLevelBean.configTypeName);
                    sb.append("----");
                    sb.append(mimojiLevelBean.configType);
                    sb.append("----");
                    AvatarEngineManager.getInstance();
                    sb.append(AvatarEngineManager.showConfigTypeName(mimojiLevelBean.configType));
                    Log.i(str, sb.toString());
                    colorLayoutManagerMap = AvatarEngineManager.getInstance().getColorLayoutManagerMap(recyclerView.hashCode() + i);
                    if (recyclerView.getLayoutManager() == null || colorLayoutManagerMap == null) {
                        if (colorLayoutManagerMap == null) {
                            colorLayoutManagerMap = new LinearLayoutManagerWrapper(this.mContext, "color_select");
                            colorLayoutManagerMap.setOrientation(0);
                            AvatarEngineManager.getInstance().putColorLayoutManagerMap(recyclerView.hashCode() + i, colorLayoutManagerMap);
                        }
                        recyclerView.setLayoutManager(colorLayoutManagerMap);
                    }
                    ColorListAdapter colorListAdapter = new ColorListAdapter(this.mContext, this.mAvatarConfigItemClick, colorLayoutManagerMap);
                    colorListAdapter.setClickCheck(this.mClickCheck);
                    recyclerView.setAdapter(colorListAdapter);
                    colorListAdapter.setData(colorConfigInfos);
                    float innerConfigSelectIndex = AvatarEngineManager.getInstance().getInnerConfigSelectIndex(colorConfigInfos.get(0).configType);
                    int i3 = this.mContext.getResources().getDisplayMetrics().widthPixels;
                    int i4 = 0;
                    for (i2 = 0; i2 < colorConfigInfos.size(); i2++) {
                        if (innerConfigSelectIndex == ((float) colorConfigInfos.get(i2).configID)) {
                            i4 = i2;
                        }
                    }
                    Log.i(TAG, "fmoji show color :" + mimojiLevelBean.configTypeName + "color size:" + colorConfigInfos.size() + " colorSelectPositon : " + i4 + "   curHolderPosition : " + i);
                    colorLayoutManagerMap.scrollToPositionWithOffset(i4, i3 / 2);
                    if (this.isColorNeedNotify.get() && i >= itemCount - 1) {
                        this.isColorNeedNotify.set(false);
                    }
                } else {
                    LOG.d(TAG, "fmoji show color isColorNeedNotify : " + this.isColorNeedNotify.get());
                }
            }
        }
        recyclerView.setVisibility(8);
        if (!this.isColorNeedNotify.get()) {
        }
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("fmoji topic:");
        sb2.append(mimojiLevelBean.configTypeName);
        sb2.append("----");
        sb2.append(mimojiLevelBean.configType);
        sb2.append("----");
        AvatarEngineManager.getInstance();
        sb2.append(AvatarEngineManager.showConfigTypeName(mimojiLevelBean.configType));
        Log.i(str2, sb2.toString());
        colorLayoutManagerMap = AvatarEngineManager.getInstance().getColorLayoutManagerMap(recyclerView.hashCode() + i);
        if (colorLayoutManagerMap == null) {
        }
        recyclerView.setLayoutManager(colorLayoutManagerMap);
        ColorListAdapter colorListAdapter2 = new ColorListAdapter(this.mContext, this.mAvatarConfigItemClick, colorLayoutManagerMap);
        colorListAdapter2.setClickCheck(this.mClickCheck);
        recyclerView.setAdapter(colorListAdapter2);
        colorListAdapter2.setData(colorConfigInfos);
        float innerConfigSelectIndex2 = AvatarEngineManager.getInstance().getInnerConfigSelectIndex(colorConfigInfos.get(0).configType);
        int i32 = this.mContext.getResources().getDisplayMetrics().widthPixels;
        int i42 = 0;
        while (i2 < colorConfigInfos.size()) {
        }
        Log.i(TAG, "fmoji show color :" + mimojiLevelBean.configTypeName + "color size:" + colorConfigInfos.size() + " colorSelectPositon : " + i42 + "   curHolderPosition : " + i);
        colorLayoutManagerMap.scrollToPositionWithOffset(i42, i32 / 2);
        this.isColorNeedNotify.set(false);
    }

    public boolean getIsColorNeedNotify() {
        return this.isColorNeedNotify.get();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        String str = TAG;
        Log.i(str, "mLevelDatas getItemCount size: " + this.mLevelDatas.size());
        CopyOnWriteArrayList<MimojiLevelBean> copyOnWriteArrayList = this.mLevelDatas;
        if (copyOnWriteArrayList == null) {
            return 0;
        }
        return copyOnWriteArrayList.size();
    }

    public void notifyThumbnailUpdate(int i, int i2, int i3) {
        if (i != AvatarEngineManager.getInstance().getSelectType()) {
            Log.d(TAG, "update wrong !!!!");
            return;
        }
        String str = TAG;
        Log.d(str, "notifyThumbnailUpdate.... index = " + i2 + ", position = " + i3);
        CopyOnWriteArrayList<MimojiLevelBean> copyOnWriteArrayList = this.mLevelDatas;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() == 0 || i2 > this.mLevelDatas.size() - 1) {
            Log.e(TAG, "mLevelDatas Exception !!!!");
            return;
        }
        List<MimojiThumbnailRecyclerAdapter> list = this.mMimojiThumbnailAdapters;
        if (list == null || i2 < 0 || i2 > list.size() - 1) {
            Log.e(TAG, "mHandler message error !!!!");
            return;
        }
        this.mMimojiLevelBean = this.mLevelDatas.get(i2);
        ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = this.mMimojiLevelBean.thumnails;
        MimojiThumbnailRecyclerAdapter mimojiThumbnailRecyclerAdapter = this.mMimojiThumbnailAdapters.get(i2);
        if (arrayList == null || i3 < 0 || i3 >= arrayList.size()) {
            Log.e(TAG, "fmoji position message error !!!!");
        } else {
            mimojiThumbnailRecyclerAdapter.updateData(i3, arrayList.get(i3));
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        this.mMimojiLevelBean = this.mLevelDatas.get(i);
        RecyclerView recyclerView = viewHolder.mThumbnailGV;
        showColor(viewHolder, this.mMimojiLevelBean, i);
        if (i < this.mMimojiThumbnailAdapters.size()) {
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = this.mMimojiLevelBean.thumnails;
            int i2 = 0;
            int size = arrayList == null ? 0 : arrayList.size();
            if (size == 0) {
                recyclerView.setVisibility(8);
                return;
            }
            recyclerView.setVisibility(0);
            final MimojiThumbnailRecyclerAdapter mimojiThumbnailRecyclerAdapter = this.mMimojiThumbnailAdapters.get(i);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.getItemAnimator().setRemoveDuration(0);
            recyclerView.getItemAnimator().setMoveDuration(0);
            if (recyclerView.getLayoutManager() == null) {
                recyclerView.setLayoutManager(new BaseNoScrollGridLayoutManager(this.mContext, 3));
            }
            recyclerView.setAdapter(mimojiThumbnailRecyclerAdapter);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
            int i3 = size / 3;
            if (size % 3 != 0) {
                i2 = 1;
            }
            int i4 = i3 + i2;
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_level_icon_margin);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_level_icon_size);
            int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_level_icon_margin);
            if (i != 0) {
                layoutParams.topMargin = (dimensionPixelSize3 / 3) * 2;
            } else if (viewHolder.mColorRecycleView.getVisibility() == 0) {
                layoutParams.topMargin = dimensionPixelSize3 / 2;
            } else {
                layoutParams.topMargin = dimensionPixelSize3;
            }
            layoutParams.height = (dimensionPixelSize2 * i4) + (dimensionPixelSize * (i4 - 1));
            recyclerView.setLayoutParams(layoutParams);
            mimojiThumbnailRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<AvatarConfig.ASAvatarConfigInfo>() {
                /* class com.android.camera.fragment.mimoji.EditLevelListAdapter.AnonymousClass1 */

                public void OnRecyclerItemClickListener(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, int i) {
                    if (EditLevelListAdapter.this.mClickCheck == null || EditLevelListAdapter.this.mClickCheck.checkClickable()) {
                        EditLevelListAdapter.this.onGvItemClick(mimojiThumbnailRecyclerAdapter, i, i);
                    }
                }
            });
            return;
        }
        recyclerView.setVisibility(8);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.mimoji_edit_level_item, viewGroup, false));
    }

    public synchronized void refreshData(List<MimojiLevelBean> list, boolean z, boolean z2) {
        Log.i(TAG, "mLevelDatas refreshData list size: " + list.size() + "   mLevelDatas size" + this.mLevelDatas.size() + "  isColor : " + z2 + "   loadThumbnailFromCache : " + z);
        if (list != null) {
            if (list.size() != 0) {
                int i = 0;
                if (this.mLevelDatas == null || this.mLevelDatas.size() != list.size() || this.mMimojiThumbnailAdapters.size() == 0 || this.mMimojiThumbnailAdapters.get(0).getItemCount() <= 0 || getItemCount() == 0) {
                    z2 = false;
                }
                if (z2) {
                    while (true) {
                        if (i >= list.size()) {
                            break;
                        } else if (i >= this.mMimojiThumbnailAdapters.size()) {
                            break;
                        } else {
                            if (z) {
                                this.mMimojiThumbnailAdapters.get(i).setDataList(list.get(i).thumnails);
                            }
                            i++;
                        }
                    }
                } else {
                    setLevelDatas(list);
                    this.mMimojiThumbnailAdapters.clear();
                    while (i < this.mLevelDatas.size()) {
                        this.mMimojiThumbnailAdapters.add(new MimojiThumbnailRecyclerAdapter(this.mContext, this.mLevelDatas.get(i).configType));
                        i++;
                    }
                    notifyDataSetChanged();
                }
                Log.d(TAG, "fmoji refreshData isColorNeedNotify = " + this.isColorNeedNotify.get());
                return;
            }
        }
        Log.i(TAG, "mLevelDatas refreshData list size error");
    }

    public void setIsColorNeedNotify(boolean z) {
        this.isColorNeedNotify.set(z);
    }

    public void setLevelDatas(List<MimojiLevelBean> list) {
        this.mLevelDatas.clear();
        boolean z = false;
        while (!z) {
            z = this.mLevelDatas.addAll(list);
        }
    }

    public void setRenderThread(MimojiThumbnailRenderThread mimojiThumbnailRenderThread) {
        this.mRenderThread = mimojiThumbnailRenderThread;
    }

    public void setmClickCheck(ClickCheck clickCheck) {
        this.mClickCheck = clickCheck;
    }

    public void updateSelectView(MimojiThumbnailRecyclerAdapter mimojiThumbnailRecyclerAdapter, int i, int i2) {
        MimojiLevelBean mimojiLevelBean = this.mLevelDatas.get(i);
        if (i2 < mimojiLevelBean.thumnails.size()) {
            String str = FragmentMimojiEdit.TAG;
            Log.i(str, "click Thumbnail configType:" + this.mMimojiLevelBean.configType + " configName:" + this.mMimojiLevelBean.configTypeName + "configId :" + mimojiLevelBean.thumnails.get(i2).configID);
            mimojiThumbnailRecyclerAdapter.setSelectItem(mimojiLevelBean.configType, mimojiLevelBean.thumnails.get(i2).configID);
            mimojiThumbnailRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
