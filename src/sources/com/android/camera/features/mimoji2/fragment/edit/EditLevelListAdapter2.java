package com.android.camera.features.mimoji2.fragment.edit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.bean.MimojiLevelBean2;
import com.android.camera.features.mimoji2.utils.ClickCheck2;
import com.android.camera.features.mimoji2.widget.baseview.BaseNoScrollGridLayoutManager;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
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

public class EditLevelListAdapter2 extends RecyclerView.Adapter<ViewHolder> {
    private static final int LIST_COLUMN_NUM = 3;
    /* access modifiers changed from: private */
    public static final String TAG = "EditLevelListAdapter2";
    private AtomicBoolean isColorNeedNotify = new AtomicBoolean(true);
    private AvatarConfigItemClick2 mAvatarConfigItemClick2 = new AvatarConfigItemClick2() {
        /* class com.android.camera.features.mimoji2.fragment.edit.EditLevelListAdapter2.AnonymousClass2 */

        @Override // com.android.camera.features.mimoji2.fragment.edit.AvatarConfigItemClick2
        public void onConfigItemClick(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, boolean z, int i) {
            if (aSAvatarConfigInfo == null) {
                Log.d(EditLevelListAdapter2.TAG, "onConfigItemClick, ASAvatarConfigInfo is null");
                return;
            }
            String access$100 = EditLevelListAdapter2.TAG;
            Log.d(access$100, "onConfigItemClick, ASAvatarConfigInfo = " + aSAvatarConfigInfo);
            EditLevelListAdapter2.this.mItfGvOnItemClickListener.notifyUIChanged();
            AvatarEngineManager2.getInstance().setAllNeedUpdate(true, z);
            AvatarEngineManager2.getInstance().addAvatarConfig(aSAvatarConfigInfo);
            AvatarConfigUtils.updateConfigID(aSAvatarConfigInfo.configType, aSAvatarConfigInfo.configID, AvatarEngineManager2.getInstance().getASAvatarConfigValue());
            EditLevelListAdapter2.this.mRenderThread.setConfig(aSAvatarConfigInfo);
            if (!z) {
                return;
            }
            if (!EditLevelListAdapter2.this.mRenderThread.getIsRendering()) {
                EditLevelListAdapter2.this.mRenderThread.draw(false);
            } else {
                EditLevelListAdapter2.this.mRenderThread.setStopRender(true);
            }
        }
    };
    private Context mContext;
    /* access modifiers changed from: private */
    public ItfGvOnItemClickListener mItfGvOnItemClickListener;
    private CopyOnWriteArrayList<MimojiLevelBean2> mLevelDatas;
    private MimojiLevelBean2 mMimojiLevelBean2;
    private List<MimojiThumbnailRecyclerAdapter2> mMimojiThumbnailAdapters;
    /* access modifiers changed from: private */
    public MimojiThumbnailRenderThread2 mRenderThread;

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

    EditLevelListAdapter2(Context context, ItfGvOnItemClickListener itfGvOnItemClickListener) {
        this.mContext = context;
        this.mLevelDatas = new CopyOnWriteArrayList<>();
        this.mMimojiThumbnailAdapters = Collections.synchronizedList(new ArrayList());
        this.mItfGvOnItemClickListener = itfGvOnItemClickListener;
    }

    /* access modifiers changed from: private */
    public void onGvItemClick(MimojiThumbnailRecyclerAdapter2 mimojiThumbnailRecyclerAdapter2, int i, int i2) {
        String str = TAG;
        Log.d(str, "outerPosition = " + i + " Select index = " + i2);
        CopyOnWriteArrayList<MimojiLevelBean2> copyOnWriteArrayList = this.mLevelDatas;
        if (copyOnWriteArrayList == null || i < 0 || i >= copyOnWriteArrayList.size()) {
            Log.e(TAG, "gv mLevelDatas error");
            return;
        }
        MimojiLevelBean2 mimojiLevelBean2 = this.mLevelDatas.get(i);
        if (i2 >= 0 && i2 < mimojiLevelBean2.mThumnails.size()) {
            AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = mimojiLevelBean2.mThumnails.get(i2);
            AvatarEngineManager2.getInstance().setInnerConfigSelectIndex(mimojiLevelBean2.mConfigType, (float) i2);
            if (aSAvatarConfigInfo != null) {
                updateSelectView(mimojiThumbnailRecyclerAdapter2, i, i2);
                this.mAvatarConfigItemClick2.onConfigItemClick(aSAvatarConfigInfo, false, i);
                return;
            }
            Log.e(TAG, "onGvItemClick AvatarConfig.ASAvatarConfigInfo is null");
            mimojiThumbnailRecyclerAdapter2.notifyDataSetChanged();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f1  */
    private synchronized void showColor(ViewHolder viewHolder, MimojiLevelBean2 mimojiLevelBean2, int i) {
        LinearLayoutManagerWrapper colorLayoutManagerMap;
        int i2;
        RecyclerView recyclerView = viewHolder.mColorRecycleView;
        TextView textView = viewHolder.mTvSubTitle;
        int itemCount = getItemCount();
        AvatarEngineManager2.getInstance();
        if (AvatarEngineManager2.showConfigTypeName(mimojiLevelBean2.mConfigType)) {
            textView.setVisibility(0);
            textView.setText(this.mMimojiLevelBean2.mConfigTypeName);
        } else {
            textView.setVisibility(8);
        }
        ArrayList<AvatarConfig.ASAvatarConfigInfo> colorConfigInfos = mimojiLevelBean2.getColorConfigInfos();
        if (colorConfigInfos != null && AvatarEngineManager2.getInstance().getColorType(mimojiLevelBean2.mConfigType) >= 0) {
            if (colorConfigInfos.size() != 0) {
                recyclerView.setVisibility(0);
                if ((!this.isColorNeedNotify.get() || recyclerView.getChildCount() == 0) && recyclerView.getVisibility() == 0) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("fmoji topic:");
                    sb.append(mimojiLevelBean2.mConfigTypeName);
                    sb.append("----");
                    sb.append(mimojiLevelBean2.mConfigType);
                    sb.append("----");
                    AvatarEngineManager2.getInstance();
                    sb.append(AvatarEngineManager2.showConfigTypeName(mimojiLevelBean2.mConfigType));
                    Log.i(str, sb.toString());
                    colorLayoutManagerMap = AvatarEngineManager2.getInstance().getColorLayoutManagerMap(recyclerView.hashCode() + i);
                    if (recyclerView.getLayoutManager() == null || colorLayoutManagerMap == null) {
                        if (colorLayoutManagerMap == null) {
                            colorLayoutManagerMap = new LinearLayoutManagerWrapper(this.mContext, "color_select");
                            colorLayoutManagerMap.setOrientation(0);
                            AvatarEngineManager2.getInstance().putColorLayoutManagerMap(recyclerView.hashCode() + i, colorLayoutManagerMap);
                        }
                        recyclerView.setLayoutManager(colorLayoutManagerMap);
                    }
                    ColorListAdapter2 colorListAdapter2 = new ColorListAdapter2(this.mContext, this.mAvatarConfigItemClick2, colorLayoutManagerMap);
                    recyclerView.setAdapter(colorListAdapter2);
                    colorListAdapter2.setData(colorConfigInfos);
                    float innerConfigSelectIndex = AvatarEngineManager2.getInstance().getInnerConfigSelectIndex(colorConfigInfos.get(0).configType);
                    int i3 = this.mContext.getResources().getDisplayMetrics().widthPixels;
                    int i4 = 0;
                    for (i2 = 0; i2 < colorConfigInfos.size(); i2++) {
                        if (innerConfigSelectIndex == ((float) colorConfigInfos.get(i2).configID)) {
                            i4 = i2;
                        }
                    }
                    Log.i(TAG, "fmoji show color :" + mimojiLevelBean2.mConfigTypeName + "color size:" + colorConfigInfos.size() + " colorSelectPositon : " + i4 + "   curHolderPosition : " + i);
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
        sb2.append(mimojiLevelBean2.mConfigTypeName);
        sb2.append("----");
        sb2.append(mimojiLevelBean2.mConfigType);
        sb2.append("----");
        AvatarEngineManager2.getInstance();
        sb2.append(AvatarEngineManager2.showConfigTypeName(mimojiLevelBean2.mConfigType));
        Log.i(str2, sb2.toString());
        colorLayoutManagerMap = AvatarEngineManager2.getInstance().getColorLayoutManagerMap(recyclerView.hashCode() + i);
        if (colorLayoutManagerMap == null) {
        }
        recyclerView.setLayoutManager(colorLayoutManagerMap);
        ColorListAdapter2 colorListAdapter22 = new ColorListAdapter2(this.mContext, this.mAvatarConfigItemClick2, colorLayoutManagerMap);
        recyclerView.setAdapter(colorListAdapter22);
        colorListAdapter22.setData(colorConfigInfos);
        float innerConfigSelectIndex2 = AvatarEngineManager2.getInstance().getInnerConfigSelectIndex(colorConfigInfos.get(0).configType);
        int i32 = this.mContext.getResources().getDisplayMetrics().widthPixels;
        int i42 = 0;
        while (i2 < colorConfigInfos.size()) {
        }
        Log.i(TAG, "fmoji show color :" + mimojiLevelBean2.mConfigTypeName + "color size:" + colorConfigInfos.size() + " colorSelectPositon : " + i42 + "   curHolderPosition : " + i);
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
        CopyOnWriteArrayList<MimojiLevelBean2> copyOnWriteArrayList = this.mLevelDatas;
        if (copyOnWriteArrayList == null) {
            return 0;
        }
        return copyOnWriteArrayList.size();
    }

    public void notifyThumbnailUpdate(int i, int i2, int i3) {
        if (i != AvatarEngineManager2.getInstance().getSelectType()) {
            Log.d(TAG, "update wrong !!!!");
            return;
        }
        String str = TAG;
        Log.d(str, "notifyThumbnailUpdate.... index = " + i2 + ", position = " + i3);
        CopyOnWriteArrayList<MimojiLevelBean2> copyOnWriteArrayList = this.mLevelDatas;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() == 0 || i2 > this.mLevelDatas.size() - 1) {
            Log.e(TAG, "mLevelDatas Exception !!!!");
            return;
        }
        List<MimojiThumbnailRecyclerAdapter2> list = this.mMimojiThumbnailAdapters;
        if (list == null || i2 < 0 || i2 > list.size() - 1) {
            Log.e(TAG, "mHandler message error !!!!");
            return;
        }
        this.mMimojiLevelBean2 = this.mLevelDatas.get(i2);
        ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = this.mMimojiLevelBean2.mThumnails;
        MimojiThumbnailRecyclerAdapter2 mimojiThumbnailRecyclerAdapter2 = this.mMimojiThumbnailAdapters.get(i2);
        if (arrayList == null || i3 < 0 || i3 >= arrayList.size()) {
            Log.e(TAG, "fmoji position message error !!!!");
        } else {
            mimojiThumbnailRecyclerAdapter2.updateData(i3, arrayList.get(i3));
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        this.mMimojiLevelBean2 = this.mLevelDatas.get(i);
        RecyclerView recyclerView = viewHolder.mThumbnailGV;
        showColor(viewHolder, this.mMimojiLevelBean2, i);
        if (i < this.mMimojiThumbnailAdapters.size()) {
            ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = this.mMimojiLevelBean2.mThumnails;
            int i2 = 0;
            int size = arrayList == null ? 0 : arrayList.size();
            if (size == 0) {
                recyclerView.setVisibility(8);
                return;
            }
            recyclerView.setVisibility(0);
            final MimojiThumbnailRecyclerAdapter2 mimojiThumbnailRecyclerAdapter2 = this.mMimojiThumbnailAdapters.get(i);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.getItemAnimator().setRemoveDuration(0);
            recyclerView.getItemAnimator().setMoveDuration(0);
            if (recyclerView.getLayoutManager() == null) {
                recyclerView.setLayoutManager(new BaseNoScrollGridLayoutManager(this.mContext, 3));
            }
            recyclerView.setAdapter(mimojiThumbnailRecyclerAdapter2);
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
            mimojiThumbnailRecyclerAdapter2.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<AvatarConfig.ASAvatarConfigInfo>() {
                /* class com.android.camera.features.mimoji2.fragment.edit.EditLevelListAdapter2.AnonymousClass1 */

                public void OnRecyclerItemClickListener(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, int i) {
                    if (ClickCheck2.getInstance().checkClickable()) {
                        EditLevelListAdapter2.this.onGvItemClick(mimojiThumbnailRecyclerAdapter2, i, i);
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

    public synchronized void refreshData(List<MimojiLevelBean2> list, boolean z, boolean z2) {
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
                                this.mMimojiThumbnailAdapters.get(i).setDataList(list.get(i).mThumnails);
                            }
                            i++;
                        }
                    }
                } else {
                    setLevelDatas(list);
                    this.mMimojiThumbnailAdapters.clear();
                    while (i < this.mLevelDatas.size()) {
                        this.mMimojiThumbnailAdapters.add(new MimojiThumbnailRecyclerAdapter2(this.mContext, this.mLevelDatas.get(i).mConfigType));
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

    public void setLevelDatas(List<MimojiLevelBean2> list) {
        this.mLevelDatas.clear();
        if (list != null && !list.isEmpty()) {
            this.mLevelDatas.addAll(list);
        }
    }

    public void setRenderThread(MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2) {
        this.mRenderThread = mimojiThumbnailRenderThread2;
    }

    public void updateSelectView(MimojiThumbnailRecyclerAdapter2 mimojiThumbnailRecyclerAdapter2, int i, int i2) {
        MimojiLevelBean2 mimojiLevelBean2 = this.mLevelDatas.get(i);
        if (i2 < mimojiLevelBean2.mThumnails.size()) {
            String str = FragmentMimojiEdit2.TAG;
            Log.i(str, "click Thumbnail mConfigType:" + this.mMimojiLevelBean2.mConfigType + " configName:" + this.mMimojiLevelBean2.mConfigTypeName + "configId :" + mimojiLevelBean2.mThumnails.get(i2).configID);
            mimojiThumbnailRecyclerAdapter2.setSelectItem(mimojiLevelBean2.mConfigType, mimojiLevelBean2.mThumnails.get(i2).configID);
            mimojiThumbnailRecyclerAdapter2.notifyDataSetChanged();
        }
    }
}
