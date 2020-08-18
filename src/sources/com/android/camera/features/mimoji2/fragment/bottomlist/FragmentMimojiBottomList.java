package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.BubbleEditMimojiPresenter2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.live.FragmentLiveBase;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.arcsoft.avatar.BackgroundInfo;
import com.mi.config.b;
import io.reactivex.Completable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FragmentMimojiBottomList extends FragmentLiveBase implements MimojiModeProtocol.MimojiBottomList, View.OnClickListener {
    public static final String ADD_STATE = "add_state";
    public static final String CLOSE_STATE = "close_state";
    private static final int FRAGMENT_INFO = 4001;
    private static final String TAG = "FragmentMimojiBottomList";
    /* access modifiers changed from: private */
    public BubbleEditMimojiPresenter2 bubbleEditMimojiPresenter2;
    private Context mContext;
    private boolean mIsNeedClickHide;
    private boolean mIsRTL;
    private int mItemWidth;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLlProgress;
    private MimojiBgAdapter mMimojiBgAdapter;
    /* access modifiers changed from: private */
    public MimojiCreateItemAdapter2 mMimojiCreateItemAdapter2;
    /* access modifiers changed from: private */
    public List<MimojiInfo2> mMimojiInfo2List;
    /* access modifiers changed from: private */
    public MimojiInfo2 mMimojiInfo2Select;
    private RecyclerView mMimojiRecylerView;
    private MimojiTimbreAdapter mMimojiTimbreAdapter;
    private View mNoneItemView;
    /* access modifiers changed from: private */
    public View mNoneSelectItemView;
    private int mSelectIndex;
    private String mSelectState = "close_state";
    private int mTotalWidth;
    private MimojiStatusManager2 mimojiStatusManager2;
    private RelativeLayout popContainer;
    private RelativeLayout popParent;
    private final int[] resourceBg = {R.drawable.ic_mimoji_bg_wave, R.drawable.ic_mimoji_bg_rotate, R.drawable.ic_mimoji_bg_circle, R.drawable.ic_mimoji_bg_white, R.drawable.ic_mimoji_bg_pink, R.drawable.ic_mimoji_bg_blue};
    private final int[] resourceTimbre = {R.drawable.ic_mimoji_timbre_gentlemen, R.drawable.ic_mimoji_timbre_lady, R.drawable.ic_mimoji_timbre_girl, R.drawable.ic_mimoji_timbre_baby, R.drawable.ic_mimoji_timbre_robot};

    private void initAvatarList() {
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mMimojiRecylerView.setItemAnimator(defaultItemAnimator);
        this.mMimojiRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass2 */

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                FragmentMimojiBottomList.this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
            }
        });
        this.mSelectState = DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiState();
        this.mMimojiCreateItemAdapter2 = new MimojiCreateItemAdapter2(getContext(), this.mSelectState);
        this.mMimojiCreateItemAdapter2.setOnItemClickListener(new c(this));
        firstProgressShow(this.mimojiStatusManager2.IsLoading());
        filelistToMinojiInfo();
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), Util.ALGORITHM_NAME_MIMOJI_CAPTURE);
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
        RecyclerAdapterWrapper recyclerAdapterWrapper = new RecyclerAdapterWrapper(this.mMimojiCreateItemAdapter2);
        Space space = new Space(getContext());
        space.setMinimumWidth(dimensionPixelSize);
        recyclerAdapterWrapper.addHeader(space);
        Space space2 = new Space(getContext());
        space2.setMinimumWidth(dimensionPixelSize);
        recyclerAdapterWrapper.addFooter(space2);
        this.mMimojiRecylerView.setAdapter(recyclerAdapterWrapper);
        String currentMimojiState = this.mimojiStatusManager2.getCurrentMimojiState();
        if (!currentMimojiState.equals("close_state")) {
            int i = 1;
            while (i < this.mMimojiInfo2List.size()) {
                if (TextUtils.isEmpty(this.mMimojiInfo2List.get(i).mConfigPath) || !currentMimojiState.equals(this.mMimojiInfo2List.get(i).mConfigPath)) {
                    i++;
                } else {
                    this.mSelectIndex = i;
                    return;
                }
            }
        }
    }

    private void initBgList() {
        ArrayList arrayList = new ArrayList();
        CopyOnWriteArrayList<BackgroundInfo> backgroundInfos = AvatarEngineManager2.getInstance().getBackgroundInfos();
        if (backgroundInfos.size() != this.resourceBg.length) {
            Log.e(TAG, "mimoji bg resource size error");
            return;
        }
        MimojiBgInfo currentMimojiBgInfo = this.mimojiStatusManager2.getCurrentMimojiBgInfo();
        for (int i = 0; i < backgroundInfos.size(); i++) {
            MimojiBgInfo mimojiBgInfo = new MimojiBgInfo(backgroundInfos.get(i), this.resourceBg[i]);
            if (currentMimojiBgInfo != null && currentMimojiBgInfo.getBackgroundInfo().getName().equals(mimojiBgInfo.getBackgroundInfo().getName())) {
                mimojiBgInfo.setSelected(true);
                this.mSelectIndex = i;
            }
            arrayList.add(mimojiBgInfo);
        }
        this.mMimojiBgAdapter = new MimojiBgAdapter(arrayList);
        this.mMimojiBgAdapter.setOnRecyclerItemClickListener(new b(this));
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), Util.ALGORITHM_NAME_MIMOJI_CAPTURE);
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        this.mMimojiRecylerView.setAdapter(this.mMimojiBgAdapter);
    }

    private void initTimbreList() {
        ArrayList arrayList = new ArrayList();
        if (this.resourceTimbre.length != MimojiTimbreInfo.timbreTypes.length) {
            Log.e(TAG, "mimoji timbre resource size error");
            return;
        }
        MimojiTimbreInfo currentMimojiTimbreInfo = this.mimojiStatusManager2.getCurrentMimojiTimbreInfo();
        int i = 0;
        while (true) {
            int[] iArr = this.resourceTimbre;
            if (i < iArr.length) {
                MimojiTimbreInfo mimojiTimbreInfo = new MimojiTimbreInfo(MimojiTimbreInfo.timbreTypes[i], iArr[i]);
                if (currentMimojiTimbreInfo != null && currentMimojiTimbreInfo.getTimbreId() == mimojiTimbreInfo.getTimbreId()) {
                    mimojiTimbreInfo.setSelected(true);
                    this.mSelectIndex = i;
                }
                arrayList.add(mimojiTimbreInfo);
                i++;
            } else {
                this.mMimojiTimbreAdapter = new MimojiTimbreAdapter(arrayList);
                this.mMimojiTimbreAdapter.setOnRecyclerItemClickListener(new d(this));
                this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), Util.ALGORITHM_NAME_MIMOJI_CAPTURE);
                this.mLayoutManager.setOrientation(0);
                this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
                this.mMimojiRecylerView.setAdapter(this.mMimojiTimbreAdapter);
                this.mMimojiTimbreAdapter.setSelectState(this.mSelectIndex);
                return;
            }
        }
    }

    private boolean scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
            return true;
        } else if (i != this.mLayoutManager.findLastVisibleItemPosition() && i != this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            return false;
        } else {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiCreateItemAdapter2.getItemCount() - 1));
            return true;
        }
    }

    private void setSelectItemInCenter() {
        if (this.mimojiStatusManager2.getMimojiPanelState() != 0) {
            if (this.mSelectIndex < 0) {
                this.mNoneSelectItemView.setVisibility(0);
                return;
            }
            this.mNoneSelectItemView.setVisibility(8);
            this.mLayoutManager.scrollToPositionWithOffset(this.mSelectIndex, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.mimoji_delete_dialog_title);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.mimoji_delete, new DialogInterface.OnClickListener() {
            /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                if (FragmentMimojiBottomList.this.mMimojiInfo2Select != null && !TextUtils.isEmpty(FragmentMimojiBottomList.this.mMimojiInfo2Select.mPackPath)) {
                    FileUtils.deleteFile(FragmentMimojiBottomList.this.mMimojiInfo2Select.mPackPath);
                    FragmentMimojiBottomList.this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
                    FragmentMimojiBottomList.this.mNoneSelectItemView.setVisibility(0);
                    FragmentMimojiBottomList.this.mMimojiCreateItemAdapter2.updateSelect();
                    FragmentMimojiBottomList.this.filelistToMinojiInfo();
                    MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                    if (mimojiAvatarEngine2 != null) {
                        mimojiAvatarEngine2.onMimojiDeleted();
                    }
                    CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_DELETE, "delete");
                    CameraStatUtils.trackMimojiCount(Integer.toString(FragmentMimojiBottomList.this.mMimojiInfo2List.size() - 4));
                }
            }
        });
        builder.setNegativeButton(R.string.mimoji_cancle, new DialogInterface.OnClickListener() {
            /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    public /* synthetic */ void Qa() {
        int i = 1;
        if (this.mimojiStatusManager2.getMimojiPanelState() == 1 && this.mMimojiCreateItemAdapter2 != null) {
            filelistToMinojiInfo();
            Log.d(TAG, "refreshMimojiList AVATAR");
            this.mSelectIndex = -1;
            String currentMimojiState = this.mimojiStatusManager2.getCurrentMimojiState();
            while (true) {
                if (i < this.mMimojiInfo2List.size()) {
                    if (!TextUtils.isEmpty(this.mMimojiInfo2List.get(i).mConfigPath) && currentMimojiState.equals(this.mMimojiInfo2List.get(i).mConfigPath)) {
                        this.mSelectIndex = i;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            this.mMimojiCreateItemAdapter2.updateSelect();
            CameraStatUtils.trackMimojiCount(Integer.toString(this.mMimojiCreateItemAdapter2.getItemCount() - 4));
        }
        if (this.mMimojiRecylerView.getAdapter() != null) {
            this.mMimojiRecylerView.getAdapter().notifyDataSetChanged();
        }
    }

    public /* synthetic */ void a(MimojiBgInfo mimojiBgInfo, int i) {
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.onMimojiChangeBg(mimojiBgInfo);
        }
        this.mMimojiBgAdapter.clearState();
        this.mMimojiBgAdapter.setSelectState(i);
        this.mNoneSelectItemView.setVisibility(8);
    }

    public /* synthetic */ void a(MimojiInfo2 mimojiInfo2, int i, View view) {
        onItemSelected(mimojiInfo2, i, view, false);
    }

    public /* synthetic */ void a(MimojiTimbreInfo mimojiTimbreInfo, int i) {
        if (this.mMimojiTimbreAdapter.setSelectState(i)) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.onMimojiChangeTimbre(mimojiTimbreInfo);
            }
            if (this.mIsNeedClickHide) {
                MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
                if (mimojiVideoEditor != null) {
                    mimojiVideoEditor.changeTimbre();
                }
                Completable.create(new AlphaOutOnSubscribe(getView()).targetGone()).subscribe();
            }
        }
        this.mNoneSelectItemView.setVisibility(8);
    }

    public void filelistToMinojiInfo() {
        List<MimojiInfo2> list = this.mMimojiInfo2List;
        if (list != null) {
            list.clear();
        } else {
            this.mMimojiInfo2List = new ArrayList();
        }
        MimojiInfo2 mimojiInfo2 = new MimojiInfo2();
        mimojiInfo2.mConfigPath = "add_state";
        mimojiInfo2.mDirectoryName = Long.MAX_VALUE;
        this.mMimojiInfo2List.add(mimojiInfo2);
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(MimojiHelper2.CUSTOM_DIR);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File file2 : listFiles) {
                    MimojiInfo2 mimojiInfo22 = new MimojiInfo2();
                    mimojiInfo22.mAvatarTemplatePath = AvatarEngineManager2.PersonTemplatePath;
                    String name = file2.getName();
                    String absolutePath = file2.getAbsolutePath();
                    String str = name + "config.dat";
                    String str2 = name + "pic.png";
                    if (file2.isDirectory()) {
                        String str3 = MimojiHelper2.CUSTOM_DIR + name + "/" + str;
                        String str4 = MimojiHelper2.CUSTOM_DIR + name + "/" + str2;
                        if (!FileUtils.checkFileConsist(str3) || !FileUtils.checkFileConsist(str4)) {
                            arrayList.add(absolutePath);
                        } else {
                            mimojiInfo22.mConfigPath = str3;
                            mimojiInfo22.mThumbnailUrl = str4;
                            mimojiInfo22.mPackPath = absolutePath;
                            mimojiInfo22.mDirectoryName = Long.valueOf(name).longValue();
                            this.mMimojiInfo2List.add(mimojiInfo22);
                        }
                    } else {
                        arrayList.add(absolutePath);
                    }
                }
                Collections.sort(this.mMimojiInfo2List);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        MimojiInfo2 mimojiInfo23 = new MimojiInfo2();
        mimojiInfo23.mAvatarTemplatePath = AvatarEngineManager2.PigTemplatePath;
        mimojiInfo23.mConfigPath = "pig";
        mimojiInfo23.mThumbnailUrl = MimojiHelper2.DATA_DIR + "/pig.png";
        this.mMimojiInfo2List.add(mimojiInfo23);
        if (b.bv || b.cv || b.dv) {
            MimojiInfo2 mimojiInfo24 = new MimojiInfo2();
            mimojiInfo24.mAvatarTemplatePath = AvatarEngineManager2.RoyanTemplatePath;
            mimojiInfo24.mConfigPath = "royan";
            mimojiInfo24.mThumbnailUrl = MimojiHelper2.DATA_DIR + "/royan.png";
            this.mMimojiInfo2List.add(mimojiInfo24);
        }
        MimojiInfo2 mimojiInfo25 = new MimojiInfo2();
        mimojiInfo25.mAvatarTemplatePath = AvatarEngineManager2.BearTemplatePath;
        mimojiInfo25.mConfigPath = "bear";
        mimojiInfo25.mThumbnailUrl = MimojiHelper2.DATA_DIR + "/bear.png";
        this.mMimojiInfo2List.add(mimojiInfo25);
        MimojiInfo2 mimojiInfo26 = new MimojiInfo2();
        mimojiInfo26.mAvatarTemplatePath = AvatarEngineManager2.RabbitTemplatePath;
        mimojiInfo26.mConfigPath = "rabbit";
        mimojiInfo26.mThumbnailUrl = MimojiHelper2.DATA_DIR + "/rabbit.png";
        this.mMimojiInfo2List.add(mimojiInfo26);
        this.mMimojiCreateItemAdapter2.setMimojiInfoList(this.mMimojiInfo2List);
        for (int i = 0; i < arrayList.size(); i++) {
            FileUtils.deleteFile((String) arrayList.get(i));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public void firstProgressShow(boolean z) {
        if (getActivity() == null) {
            Log.e(TAG, "not attached to Activity , skip     firstProgressShow........");
            return;
        }
        if (this.mLlProgress == null) {
            initView(getView());
        }
        if (z) {
            this.mLlProgress.setVisibility(0);
            this.mMimojiRecylerView.setVisibility(8);
            return;
        }
        this.mLlProgress.setVisibility(8);
        this.mMimojiRecylerView.setVisibility(0);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4001;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.live_sticker_item_size);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mContext = getContext();
        this.mIsRTL = Util.isLayoutRTL(this.mContext);
        this.mMimojiRecylerView = (RecyclerView) view.findViewById(R.id.mimoji_list);
        this.mMimojiRecylerView.setFocusable(false);
        this.popContainer = (RelativeLayout) view.findViewById(R.id.ll_bubble_pop_occupation);
        this.popParent = (RelativeLayout) view.findViewById(R.id.rl_bubble_pop_parent);
        this.mLlProgress = (LinearLayout) view.findViewById(R.id.ll_updating);
        this.mNoneSelectItemView = view.findViewById(R.id.mimoji_none_selected_indicator);
        this.mNoneItemView = view.findViewById(R.id.mimoji_none_item);
        View view2 = this.mNoneItemView;
        view2.setTag(Integer.valueOf(view2.getId()));
        this.mNoneItemView.setOnClickListener(this);
        this.bubbleEditMimojiPresenter2 = new BubbleEditMimojiPresenter2(getContext(), this, this.popParent);
        this.mMimojiRecylerView.getItemAnimator().setAddDuration(0);
        this.mMimojiRecylerView.getItemAnimator().setChangeDuration(0);
        this.mMimojiRecylerView.getItemAnimator().setMoveDuration(0);
        this.mMimojiRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass1 */

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                FragmentMimojiBottomList.this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
            }
        });
        this.mSelectIndex = -1;
        this.mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        int mimojiPanelState = this.mimojiStatusManager2.getMimojiPanelState();
        if (mimojiPanelState == 0) {
            Log.d(TAG, "init MimojiPanelState close");
        } else if (mimojiPanelState == 1) {
            initAvatarList();
        } else if (mimojiPanelState == 2) {
            initBgList();
        } else if (mimojiPanelState == 3) {
            initTimbreList();
        }
        setSelectItemInCenter();
    }

    /* access modifiers changed from: protected */
    public void onAddItemSelected() {
        ((FragmentLiveBase) this).mIsNeedShowWhenExit = false;
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.onMimojiCreate();
        }
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.forceSwitchFront();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace, com.android.camera.fragment.live.FragmentLiveBase
    public boolean onBackEvent(int i) {
        String str = TAG;
        Log.d(str, "onBackEvent = " + i);
        if (this.mimojiStatusManager2.isInMimojiEdit() && i != 4) {
            return false;
        }
        this.mimojiStatusManager2.setMimojiPanelState(0);
        return super.onBackEvent(i);
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        if (intValue == 201) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.releaseRender();
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                mimojiEditor2.directlyEnterEditMode(this.mMimojiInfo2Select, 201);
            }
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT, MistatsConstants.BaseEvent.EDIT);
            this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
        } else if (intValue == 202) {
            showAlertDialog();
        } else if (intValue == 204) {
            MimojiModeProtocol.MimojiEditor2 mimojiEditor22 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor22 != null) {
                mimojiEditor22.directlyEnterEditMode(this.mMimojiInfo2Select, 204);
            }
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT, MistatsConstants.BaseEvent.EDIT);
            this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
        } else if (intValue == R.id.mimoji_none_item) {
            this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
            if (this.mimojiStatusManager2.getMimojiPanelState() == 1) {
                this.mNoneSelectItemView.setVisibility(0);
                MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                if (mimojiAvatarEngine22 != null) {
                    mimojiAvatarEngine22.onMimojiSelect(null);
                    mimojiAvatarEngine22.onMimojiChangeBg(null);
                }
                MimojiCreateItemAdapter2 mimojiCreateItemAdapter2 = this.mMimojiCreateItemAdapter2;
                if (mimojiCreateItemAdapter2 != null) {
                    mimojiCreateItemAdapter2.updateSelect();
                }
            } else if (this.mimojiStatusManager2.getMimojiPanelState() == 2) {
                this.mNoneSelectItemView.setVisibility(0);
                MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine23 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                if (mimojiAvatarEngine23 != null) {
                    mimojiAvatarEngine23.onMimojiChangeBg(null);
                }
                MimojiBgAdapter mimojiBgAdapter = this.mMimojiBgAdapter;
                if (mimojiBgAdapter != null) {
                    mimojiBgAdapter.clearState();
                }
            } else if (this.mimojiStatusManager2.getMimojiPanelState() == 3) {
                this.mNoneSelectItemView.setVisibility(0);
                MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine24 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                if (mimojiAvatarEngine24 != null) {
                    mimojiAvatarEngine24.onMimojiChangeTimbre(null);
                }
                MimojiTimbreAdapter mimojiTimbreAdapter = this.mMimojiTimbreAdapter;
                if (mimojiTimbreAdapter != null) {
                    mimojiTimbreAdapter.clearState();
                }
                if (this.mIsNeedClickHide) {
                    getView().setVisibility(8);
                    MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
                    if (mimojiVideoEditor != null) {
                        mimojiVideoEditor.changeTimbre();
                    }
                }
            }
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_NULL, MistatsConstants.BaseEvent.PREVIEW);
        }
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(MimojiInfo2 mimojiInfo2, int i, View view, boolean z) {
        if (mimojiInfo2 != null && !TextUtils.isEmpty(mimojiInfo2.mConfigPath)) {
            String str = mimojiInfo2.mConfigPath;
            String currentMimojiState = this.mimojiStatusManager2.getCurrentMimojiState();
            if (!str.equals("add_state")) {
                this.mimojiStatusManager2.setCurrentMimojiInfo(mimojiInfo2);
            }
            String str2 = TAG;
            Log.i(str2, "click　currentState:" + str + " lastState:" + currentMimojiState);
            this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
            if ("add_state".equals(mimojiInfo2.mConfigPath)) {
                onAddItemSelected();
                CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_ADD, MistatsConstants.BaseEvent.ADD);
            } else if (!z) {
                int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
                int findFirstCompletelyVisibleItemPosition = this.mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (i == findFirstVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition - 2) {
                    this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
                } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
                    this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiCreateItemAdapter2.getItemCount() - 1));
                } else if (i == this.mLayoutManager.findLastVisibleItemPosition() - 1 || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition() - 1) {
                    this.mLayoutManager.scrollToPosition(Math.min(i + 2, this.mMimojiCreateItemAdapter2.getItemCount()));
                } else {
                    processBubble(mimojiInfo2, str, currentMimojiState, view, z);
                }
                setAvatarAndSelect(str, mimojiInfo2);
            } else {
                processBubble(mimojiInfo2, str, currentMimojiState, view, z);
                setAvatarAndSelect(str, mimojiInfo2);
            }
        }
    }

    public void processBubble(MimojiInfo2 mimojiInfo2, String str, String str2, View view, boolean z) {
        boolean isPrefabModel = AvatarEngineManager2.isPrefabModel(mimojiInfo2.mConfigPath);
        if (str.equals(str2) && !str2.equals("add_state") && !str2.equals("close_state") && !z && !isPrefabModel) {
            this.mMimojiInfo2Select = mimojiInfo2;
            int width = view.getWidth();
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int height = this.popContainer.getHeight();
            int i2 = width / 2;
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_width) / 2;
            int i3 = (i + i2) - dimensionPixelSize;
            if (this.mIsRTL) {
                i3 = ((this.mTotalWidth - i) - width) + (i2 - dimensionPixelSize);
            }
            int i4 = height - dimensionPixelSize;
            String str3 = TAG;
            Log.i(str3, "coordinateY:" + i4);
            this.bubbleEditMimojiPresenter2.processBubbleAni(i3, i4, view);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public int refreshMimojiList() {
        if (this.mMimojiRecylerView == null || getActivity() == null) {
            return -1;
        }
        getActivity().runOnUiThread(new a(this));
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.live.FragmentLiveBase
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        this.mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        ModeCoordinatorImpl.getInstance().attachProtocol(248, this);
    }

    public void setAvatarAndSelect(String str, MimojiInfo2 mimojiInfo2) {
        this.mNoneSelectItemView.setVisibility(8);
        this.mMimojiCreateItemAdapter2.updateSelect();
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.onMimojiSelect(mimojiInfo2);
        }
    }

    public void setIsNeedClickHide(boolean z) {
        this.mIsNeedClickHide = z;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.live.FragmentLiveBase
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        BubbleEditMimojiPresenter2 bubbleEditMimojiPresenter22 = this.bubbleEditMimojiPresenter2;
        if (bubbleEditMimojiPresenter22 != null) {
            bubbleEditMimojiPresenter22.processBubbleAni(-2, -2, null);
        }
        ModeCoordinatorImpl.getInstance().detachProtocol(248, this);
        this.mimojiStatusManager2.setMimojiPanelState(0);
    }
}
