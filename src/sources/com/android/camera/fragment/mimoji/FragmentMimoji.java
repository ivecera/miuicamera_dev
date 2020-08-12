package com.android.camera.fragment.mimoji;

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
import com.android.camera.data.DataRepository;
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
import com.mi.config.b;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentMimoji extends FragmentLiveBase implements ModeProtocol.MimojiAlert, View.OnClickListener {
    public static final String ADD_STATE = "add_state";
    public static final String CLOSE_STATE = "close_state";
    private static final int FRAGMENT_INFO = 4001;
    private static final String TAG = "FragmentMimoji";
    /* access modifiers changed from: private */
    public BubbleEditMimojiPresenter bubbleEditMimojiPresenter;
    private Context mContext;
    private AlertDialog mDeleteDialog;
    private boolean mIsRTL;
    private int mItemWidth;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLlProgress;
    /* access modifiers changed from: private */
    public MimojiCreateItemAdapter mMimojiCreateItemAdapter;
    /* access modifiers changed from: private */
    public List<MimojiInfo> mMimojiInfoList;
    /* access modifiers changed from: private */
    public MimojiInfo mMimojiInfoSelect;
    private RecyclerView mMimojiRecylerView;
    private View mNoneItemView;
    /* access modifiers changed from: private */
    public View mNoneSelectItemView;
    private int mSelectIndex;
    private String mSelectState = "close_state";
    private int mTotalWidth;
    private RelativeLayout popContainer;
    private RelativeLayout popParent;

    private boolean scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
            return true;
        } else if (i != this.mLayoutManager.findLastVisibleItemPosition() && i != this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            return false;
        } else {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiCreateItemAdapter.getItemCount() - 1));
            return true;
        }
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = this.mDeleteDialog;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.mimoji_delete_dialog_title);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.mimoji_delete, new DialogInterface.OnClickListener() {
                /* class com.android.camera.fragment.mimoji.FragmentMimoji.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (FragmentMimoji.this.mMimojiInfoSelect != null && !TextUtils.isEmpty(FragmentMimoji.this.mMimojiInfoSelect.mPackPath)) {
                        FileUtils.deleteFile(FragmentMimoji.this.mMimojiInfoSelect.mPackPath);
                        FragmentMimoji.this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, null);
                        FragmentMimoji.this.mNoneSelectItemView.setVisibility(0);
                        FragmentMimoji.this.mMimojiCreateItemAdapter.updateSelect();
                        FragmentMimoji.this.filelistToMinojiInfo();
                        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
                        if (mimojiAvatarEngine != null) {
                            mimojiAvatarEngine.onMimojiDeleted();
                        }
                        CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_DELETE, "delete");
                        CameraStatUtils.trackMimojiCount(Integer.toString(FragmentMimoji.this.mMimojiInfoList.size() - 4));
                    }
                }
            });
            builder.setNegativeButton(R.string.mimoji_cancle, new DialogInterface.OnClickListener() {
                /* class com.android.camera.fragment.mimoji.FragmentMimoji.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            this.mDeleteDialog = builder.show();
        } else if (!alertDialog.isShowing()) {
            this.mDeleteDialog.show();
        }
    }

    public /* synthetic */ void a(MimojiInfo mimojiInfo, int i, View view) {
        onItemSelected(mimojiInfo, i, view, false);
    }

    public void filelistToMinojiInfo() {
        this.mMimojiInfoList = new ArrayList();
        MimojiInfo mimojiInfo = new MimojiInfo();
        mimojiInfo.mConfigPath = "add_state";
        mimojiInfo.mDirectoryName = Long.MAX_VALUE;
        this.mMimojiInfoList.add(mimojiInfo);
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(MimojiHelper.CUSTOM_DIR);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File file2 : listFiles) {
                    MimojiInfo mimojiInfo2 = new MimojiInfo();
                    mimojiInfo2.mAvatarTemplatePath = AvatarEngineManager.PersonTemplatePath;
                    String name = file2.getName();
                    String absolutePath = file2.getAbsolutePath();
                    String str = name + "config.dat";
                    String str2 = name + "pic.png";
                    if (file2.isDirectory()) {
                        String str3 = MimojiHelper.CUSTOM_DIR + name + "/" + str;
                        String str4 = MimojiHelper.CUSTOM_DIR + name + "/" + str2;
                        if (!FileUtils.checkFileConsist(str3) || !FileUtils.checkFileConsist(str4)) {
                            arrayList.add(absolutePath);
                        } else {
                            mimojiInfo2.mConfigPath = str3;
                            mimojiInfo2.mThumbnailUrl = str4;
                            mimojiInfo2.mPackPath = absolutePath;
                            mimojiInfo2.mDirectoryName = Long.valueOf(name).longValue();
                            this.mMimojiInfoList.add(mimojiInfo2);
                        }
                    } else {
                        arrayList.add(absolutePath);
                    }
                }
                Collections.sort(this.mMimojiInfoList);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        MimojiInfo mimojiInfo3 = new MimojiInfo();
        mimojiInfo3.mAvatarTemplatePath = AvatarEngineManager.PigTemplatePath;
        mimojiInfo3.mConfigPath = "pig";
        mimojiInfo3.mThumbnailUrl = MimojiHelper.DATA_DIR + "/pig.png";
        this.mMimojiInfoList.add(mimojiInfo3);
        if (b.bv || b.cv || b.dv) {
            MimojiInfo mimojiInfo4 = new MimojiInfo();
            mimojiInfo4.mAvatarTemplatePath = AvatarEngineManager.RoyanTemplatePath;
            mimojiInfo4.mConfigPath = "royan";
            mimojiInfo4.mThumbnailUrl = MimojiHelper.DATA_DIR + "/royan.png";
            this.mMimojiInfoList.add(mimojiInfo4);
        }
        MimojiInfo mimojiInfo5 = new MimojiInfo();
        mimojiInfo5.mAvatarTemplatePath = AvatarEngineManager.BearTemplatePath;
        mimojiInfo5.mConfigPath = "bear";
        mimojiInfo5.mThumbnailUrl = MimojiHelper.DATA_DIR + "/bear.png";
        this.mMimojiInfoList.add(mimojiInfo5);
        MimojiInfo mimojiInfo6 = new MimojiInfo();
        mimojiInfo6.mAvatarTemplatePath = AvatarEngineManager.RabbitTemplatePath;
        mimojiInfo6.mConfigPath = "rabbit";
        mimojiInfo6.mThumbnailUrl = MimojiHelper.DATA_DIR + "/rabbit.png";
        this.mMimojiInfoList.add(mimojiInfo6);
        this.mMimojiCreateItemAdapter.setMimojiInfoList(this.mMimojiInfoList);
        for (int i = 0; i < arrayList.size(); i++) {
            FileUtils.deleteFile((String) arrayList.get(i));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiAlert
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
        this.mNoneItemView = view.findViewById(R.id.mimoji_none_item);
        this.mMimojiRecylerView = (RecyclerView) view.findViewById(R.id.mimoji_list);
        this.mMimojiRecylerView.setFocusable(false);
        this.popContainer = (RelativeLayout) view.findViewById(R.id.ll_bubble_pop_occupation);
        this.popParent = (RelativeLayout) view.findViewById(R.id.rl_bubble_pop_parent);
        this.mLlProgress = (LinearLayout) view.findViewById(R.id.ll_updating);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mMimojiRecylerView.setItemAnimator(defaultItemAnimator);
        this.mNoneSelectItemView = view.findViewById(R.id.mimoji_none_selected_indicator);
        this.bubbleEditMimojiPresenter = new BubbleEditMimojiPresenter(getContext(), this, this.popParent);
        this.mMimojiRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.mimoji.FragmentMimoji.AnonymousClass1 */

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                FragmentMimoji.this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, null);
            }
        });
        this.mNoneItemView.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.mimoji.FragmentMimoji.AnonymousClass2 */

            public void onClick(View view) {
                FragmentMimoji.this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, null);
                DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo(null);
                FragmentMimoji.this.mNoneSelectItemView.setVisibility(0);
                ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
                if (mimojiAvatarEngine != null) {
                    mimojiAvatarEngine.onMimojiSelect(null);
                }
                if (FragmentMimoji.this.mMimojiCreateItemAdapter != null) {
                    FragmentMimoji.this.mMimojiCreateItemAdapter.updateSelect();
                }
                CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_NULL, MistatsConstants.BaseEvent.PREVIEW);
            }
        });
        this.mMimojiCreateItemAdapter = new MimojiCreateItemAdapter(getContext(), this.mSelectState);
        this.mMimojiCreateItemAdapter.setOnItemClickListener(new a(this));
        firstProgressShow(DataRepository.dataItemLive().getMimojiStatusManager().IsLoading());
        filelistToMinojiInfo();
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), Util.ALGORITHM_NAME_MIMOJI_CAPTURE);
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
        RecyclerAdapterWrapper recyclerAdapterWrapper = new RecyclerAdapterWrapper(this.mMimojiCreateItemAdapter);
        Space space = new Space(getContext());
        space.setMinimumWidth(dimensionPixelSize);
        recyclerAdapterWrapper.addHeader(space);
        Space space2 = new Space(getContext());
        space2.setMinimumWidth(dimensionPixelSize);
        recyclerAdapterWrapper.addFooter(space2);
        this.mMimojiRecylerView.setAdapter(recyclerAdapterWrapper);
        this.mSelectIndex = -1;
        String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
        int i = 1;
        while (true) {
            if (i < this.mMimojiInfoList.size()) {
                if (!TextUtils.isEmpty(this.mMimojiInfoList.get(i).mConfigPath) && currentMimojiState.equals(this.mMimojiInfoList.get(i).mConfigPath)) {
                    this.mSelectIndex = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        setItemInCenter(this.mSelectIndex);
        if (currentMimojiState.equals("close_state")) {
            this.mNoneSelectItemView.setVisibility(0);
        } else {
            this.mMimojiCreateItemAdapter.updateSelect();
            MimojiInfo mimojiInfoSelected = this.mMimojiCreateItemAdapter.getMimojiInfoSelected();
            if (mimojiInfoSelected != null) {
                onItemSelected(mimojiInfoSelected, -1, null, true);
            }
        }
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(true);
    }

    /* access modifiers changed from: protected */
    public void onAddItemSelected() {
        ((FragmentLiveBase) this).mIsNeedShowWhenExit = false;
        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine != null) {
            mimojiAvatarEngine.onMimojiCreate();
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
        if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiEdit() && i != 4) {
            return false;
        }
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(false);
        return super.onBackEvent(i);
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        if (intValue == 101) {
            ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
            if (mimojiAvatarEngine != null) {
                mimojiAvatarEngine.releaseRender();
            }
            ModeProtocol.MimojiEditor mimojiEditor = (ModeProtocol.MimojiEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(224);
            if (mimojiEditor != null) {
                mimojiEditor.directlyEnterEditMode(this.mMimojiInfoSelect, 101);
            }
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT, MistatsConstants.BaseEvent.EDIT);
            this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, null);
        } else if (intValue == 102) {
            showAlertDialog();
        }
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(MimojiInfo mimojiInfo, int i, View view, boolean z) {
        if (mimojiInfo != null && !TextUtils.isEmpty(mimojiInfo.mConfigPath)) {
            String str = mimojiInfo.mConfigPath;
            String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
            if (!str.equals("add_state")) {
                DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo(mimojiInfo);
            }
            String str2 = TAG;
            Log.i(str2, "click　currentState:" + str + " lastState:" + currentMimojiState);
            this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, null);
            if ("add_state".equals(mimojiInfo.mConfigPath)) {
                onAddItemSelected();
                CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_ADD, MistatsConstants.BaseEvent.ADD);
            } else if (!z) {
                int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
                int findFirstCompletelyVisibleItemPosition = this.mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (i == findFirstVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition - 2) {
                    this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
                } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
                    this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiCreateItemAdapter.getItemCount() - 1));
                } else if (i == this.mLayoutManager.findLastVisibleItemPosition() - 1 || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition() - 1) {
                    this.mLayoutManager.scrollToPosition(Math.min(i + 2, this.mMimojiCreateItemAdapter.getItemCount()));
                } else {
                    processBubble(mimojiInfo, str, currentMimojiState, view, z);
                }
                setAvatarAndSelect(str, mimojiInfo);
            } else {
                processBubble(mimojiInfo, str, currentMimojiState, view, z);
                setAvatarAndSelect(str, mimojiInfo);
            }
        }
    }

    public void processBubble(MimojiInfo mimojiInfo, String str, String str2, View view, boolean z) {
        boolean isPrefabModel = AvatarEngineManager.isPrefabModel(mimojiInfo.mConfigPath);
        if (str.equals(str2) && !str2.equals("add_state") && !str2.equals("close_state") && !z && !isPrefabModel) {
            this.mMimojiInfoSelect = mimojiInfo;
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
            this.bubbleEditMimojiPresenter.processBubbleAni(i3, i4, view);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiAlert
    public int refreshMimojiList() {
        if (this.mMimojiCreateItemAdapter == null) {
            return 0;
        }
        Log.d(TAG, "refreshMimojiList");
        filelistToMinojiInfo();
        this.mSelectIndex = -1;
        String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
        int i = 1;
        while (true) {
            if (i < this.mMimojiInfoList.size()) {
                if (!TextUtils.isEmpty(this.mMimojiInfoList.get(i).mConfigPath) && currentMimojiState.equals(this.mMimojiInfoList.get(i).mConfigPath)) {
                    this.mSelectIndex = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        setItemInCenter(this.mSelectIndex);
        this.mMimojiCreateItemAdapter.updateSelect();
        return this.mMimojiInfoList.size() - 4;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.live.FragmentLiveBase
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        ModeCoordinatorImpl.getInstance().attachProtocol(226, this);
    }

    public void setAvatarAndSelect(String str, MimojiInfo mimojiInfo) {
        this.mNoneSelectItemView.setVisibility(8);
        DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo(mimojiInfo);
        this.mMimojiCreateItemAdapter.updateSelect();
        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine != null) {
            mimojiAvatarEngine.onMimojiSelect(mimojiInfo);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.live.FragmentLiveBase
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, null);
        ModeCoordinatorImpl.getInstance().detachProtocol(226, this);
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(false);
    }
}
