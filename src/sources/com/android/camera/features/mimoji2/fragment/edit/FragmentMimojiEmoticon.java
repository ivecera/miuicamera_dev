package com.android.camera.features.mimoji2.fragment.edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiEmoticonInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.baseview.BaseNoScrollGridLayoutManager;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.storage.Storage;
import com.arcsoft.avatar.emoticon.EmoInfo;
import com.ss.android.vesdk.VEEditor;
import io.reactivex.Completable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miui.app.f;

public class FragmentMimojiEmoticon extends BaseFragment implements MimojiModeProtocol.MimojiEditor2.MimojiEmoticon, View.OnClickListener, ModeProtocol.HandleBackTrace {
    private static final int FRAGMENT_INFO = 4003;
    public static final String TAG = "FragmentMimojiEmoticon";
    private static final int THUMBNAIL_TOTAL_COUNT = 6;
    private RecyclerView mEmoticonRecyclerView;
    private boolean mIsNeedShare;
    /* access modifiers changed from: private */
    public boolean mIsRTL;
    private MimojiEmoticonAdapter mMimojiEmoticonAdapter;
    private f mProgressDialog;
    private AlertDialog mSaveDialog;
    private ImageView mSelectBtn;
    private ArrayList<EmoInfo> mSelectedEmoInfoList = new ArrayList<>();
    private ArrayList<Uri> mShareEmoInfoList = new ArrayList<>();
    private Paint mThumbnailPaint;

    static /* synthetic */ void Ga() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(19);
        }
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (mimojiEditor2 != null) {
            mimojiEditor2.goBack(false, false);
            return;
        }
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.backToPreview(false, true);
        }
    }

    static /* synthetic */ void a(boolean[] zArr, DialogInterface dialogInterface, int i, boolean z) {
        zArr[0] = z;
    }

    private boolean checkInitThumbnaiFinish() {
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        return mimojiEmoticonAdapter != null && mimojiEmoticonAdapter.getItemCount() == 6;
    }

    private void deleteEmoticonCache() {
        FileUtils.deleteFile(MimojiHelper2.EMOTICON_MP4_CACHE_DIR);
        FileUtils.deleteFile(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
        FileUtils.deleteFile(MimojiHelper2.EMOTICON_JPEG_CACHE_DIR);
    }

    private void dissmissDialog() {
        f fVar = this.mProgressDialog;
        if (fVar != null) {
            fVar.dismiss();
            this.mProgressDialog = null;
        }
        AlertDialog alertDialog = this.mSaveDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mSaveDialog = null;
        }
    }

    private void getEmoticonThumbnail() {
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (mimojiEditor2 != null) {
            mimojiEditor2.createEmoticonThumbnail();
        } else {
            Log.e(TAG, "mimoji void initEmoticon[] null");
        }
    }

    private void saveEmoticonGif(boolean z) {
        this.mSelectedEmoInfoList.clear();
        this.mIsNeedShare = false;
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        if (mimojiEmoticonAdapter == null || mimojiEmoticonAdapter.getItemCount() != 0) {
            for (MimojiEmoticonInfo mimojiEmoticonInfo : this.mMimojiEmoticonAdapter.getDataList()) {
                if (mimojiEmoticonInfo.isSelected()) {
                    this.mSelectedEmoInfoList.add(mimojiEmoticonInfo.getEmoInfo());
                }
            }
            if (this.mSelectedEmoInfoList.size() == 0) {
                ToastUtils.showToast(getContext(), "请选择至少一个表情");
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mSelectedEmoInfoList);
            File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((EmoInfo) it.next()).getEmoName().equals(fileName)) {
                            it.remove();
                        }
                    }
                }
            }
            if (arrayList.size() == 0) {
                coverEmoticonSuccess();
                return;
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                showProgressDialog("开始生成表情包");
                if (!z) {
                    mimojiEditor2.createEmoticonVideo(arrayList);
                } else {
                    mimojiEditor2.createEmoticonPicture(arrayList);
                }
            } else {
                Log.e(TAG, "mimoji void saveEmoticonGif[] null");
            }
        } else {
            backToPreview();
            ToastUtils.showToast(getContext(), "状态异常");
        }
    }

    private void shareEmoticonGif() {
        this.mSelectedEmoInfoList.clear();
        this.mShareEmoInfoList.clear();
        this.mIsNeedShare = true;
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        if (mimojiEmoticonAdapter == null || mimojiEmoticonAdapter.getItemCount() != 0) {
            for (MimojiEmoticonInfo mimojiEmoticonInfo : this.mMimojiEmoticonAdapter.getDataList()) {
                if (mimojiEmoticonInfo.isSelected()) {
                    this.mSelectedEmoInfoList.add(mimojiEmoticonInfo.getEmoInfo());
                }
            }
            if (this.mSelectedEmoInfoList.size() == 0) {
                ToastUtils.showToast(getContext(), "请选择至少一个表情");
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mSelectedEmoInfoList);
            File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((EmoInfo) it.next()).getEmoName().equals(fileName)) {
                            it.remove();
                        }
                    }
                }
            }
            if (arrayList.size() == 0) {
                coverEmoticonSuccess();
                return;
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                showProgressDialog("开始生成表情包");
                mimojiEditor2.createEmoticonVideo(arrayList);
                return;
            }
            return;
        }
        backToPreview();
        ToastUtils.showToast(getContext(), "状态异常");
    }

    private void showProgressDialog(String str) {
        if (getActivity() != null) {
            if (TextUtils.isEmpty(str)) {
                dissmissDialog();
                return;
            }
            if (this.mProgressDialog == null) {
                this.mProgressDialog = new f(getActivity());
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setOnKeyListener(new d(this));
            }
            this.mProgressDialog.setMessage(str);
            f fVar = this.mProgressDialog;
            if (fVar != null && !fVar.isShowing()) {
                this.mProgressDialog.show();
            }
        }
    }

    private void showSaveDialog() {
        if (getActivity() != null) {
            dissmissDialog();
            boolean[] zArr = {true};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("保存表情包");
            builder.setCancelable(false);
            builder.setMultiChoiceItems(new String[]{"同时保存为图片"}, zArr, new m(zArr));
            builder.setPositiveButton("保存", new i(this, zArr));
            builder.setNegativeButton(R.string.mimoji_cancle, new h(this));
            this.mSaveDialog = builder.show();
        }
    }

    public /* synthetic */ void Ha() {
        dissmissDialog();
        ToastUtils.showToast(getActivity(), "表情包生成失败");
    }

    public /* synthetic */ void a(MimojiEmoticonInfo mimojiEmoticonInfo, int i) {
        mimojiEmoticonInfo.setSelected(!mimojiEmoticonInfo.isSelected());
        this.mMimojiEmoticonAdapter.setSelectState(mimojiEmoticonInfo, i);
    }

    public /* synthetic */ void a(EmoInfo emoInfo, int i) {
        if (this.mThumbnailPaint == null) {
            this.mThumbnailPaint = new Paint();
            this.mThumbnailPaint.setAntiAlias(true);
        }
        Log.d(TAG, "mimoji void updateEmoticonThumbnailProgress[num, emoInfo]");
        Bitmap bitmap = null;
        if (emoInfo.getThumbnailData() != null) {
            Bitmap createBitmap = Bitmap.createBitmap(AvatarEngineManager2.CONFIG_EMO_THUM_SIZE.getWidth(), AvatarEngineManager2.CONFIG_EMO_THUM_SIZE.getHeight(), Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(emoInfo.getThumbnailData()));
            if (createBitmap != null) {
                bitmap = Util.getRoundedCornerBitmap(createBitmap, 20.0f);
            } else {
                String str = TAG;
                Log.e(str, "mimoji void updateEmoticonThumbnailProgress[num, emoInfo]" + i + " , " + emoInfo.getEmoName());
            }
        }
        if (bitmap == null || bitmap.isRecycled()) {
            Log.e(TAG, "mimoji thumbnail null");
        } else if (this.mMimojiEmoticonAdapter != null) {
            MimojiEmoticonInfo mimojiEmoticonInfo = new MimojiEmoticonInfo(emoInfo, bitmap);
            mimojiEmoticonInfo.setSelected(true);
            this.mMimojiEmoticonAdapter.addData(mimojiEmoticonInfo);
        }
    }

    public /* synthetic */ void a(boolean[] zArr, DialogInterface dialogInterface, int i) {
        saveEmoticonGif(zArr[0]);
    }

    public /* synthetic */ boolean b(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                mimojiEditor2.quitCoverEmoticon();
                dissmissDialog();
            } else {
                Log.e(TAG, "mimoji void saveEmoticonGif[] null");
            }
        }
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void backToPreview() {
        getActivity().runOnUiThread(k.INSTANCE);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void coverEmoticonError() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new g(this));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void coverEmoticonSuccess() {
        StringBuilder sb;
        String str;
        File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
        if (!file.exists() || !file.isDirectory()) {
            ToastUtils.showToast(getContext(), "gif　缓存失效");
            backToPreview();
            return;
        }
        int i = 500;
        if (this.mIsNeedShare) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            int i2 = 0;
            while (i2 < length) {
                File file2 = listFiles[i2];
                String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                Iterator<EmoInfo> it = this.mSelectedEmoInfoList.iterator();
                while (it.hasNext()) {
                    if (it.next().getEmoName().equals(fileName)) {
                        Uri uri = null;
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(Storage.DIRECTORY);
                            sb2.append(File.separator);
                            sb2.append(FileUtils.createtFileName("MIMOJI_GIF_" + fileName + "_", VEEditor.MVConsts.TYPE_GIF));
                            String sb3 = sb2.toString();
                            FileUtils.copyFile(file2, new File(sb3));
                            uri = ((ActivityBase) getActivity()).getImageSaver().addGifSync(sb3, i, i);
                            Log.d(TAG, "mimoji void shareEmoticonGif[] f.getAbsolutePath() : " + file2.getAbsolutePath() + " \n  " + file2.getPath() + "   " + file2.getCanonicalPath());
                            if (uri != null) {
                                this.mShareEmoInfoList.add(uri);
                            }
                            str = TAG;
                            sb = new StringBuilder();
                        } catch (Exception e2) {
                            Log.e(TAG, "failed to add video to media store", e2);
                            str = TAG;
                            sb = new StringBuilder();
                        } catch (Throwable th) {
                            Log.d(TAG, "Current video URI: " + ((Object) null));
                            throw th;
                        }
                        sb.append("Current video URI: ");
                        sb.append(uri);
                        Log.d(str, sb.toString());
                    }
                    i = 500;
                }
                i2++;
                i = 500;
            }
            if (this.mShareEmoInfoList.size() == 0) {
                ToastUtils.showToast(getContext(), "分享文件不存在");
                return;
            } else if (this.mShareEmoInfoList.size() == 1) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.STREAM", this.mShareEmoInfoList.get(0));
                intent.setType(Storage.MIME_GIF);
                startActivity(Intent.createChooser(intent, "mimoji文件分享"));
            } else {
                Intent intent2 = new Intent("android.intent.action.SEND_MULTIPLE");
                intent2.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mShareEmoInfoList);
                intent2.setType(Storage.MIME_GIF);
                startActivity(Intent.createChooser(intent2, "mimoji多文件分享"));
            }
        } else {
            File[] listFiles2 = file.listFiles();
            for (File file3 : listFiles2) {
                String fileName2 = FileUtils.getFileName(file3.getAbsolutePath());
                Iterator<EmoInfo> it2 = this.mSelectedEmoInfoList.iterator();
                while (it2.hasNext()) {
                    if (it2.next().getEmoName().equals(fileName2)) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(Storage.DIRECTORY);
                        sb4.append(File.separator);
                        sb4.append(FileUtils.createtFileName("MIMOJI_GIF_" + fileName2 + "_", VEEditor.MVConsts.TYPE_GIF));
                        String sb5 = sb4.toString();
                        try {
                            FileUtils.copyFile(file3, new File(sb5));
                            try {
                                ((ActivityBase) getActivity()).getImageSaver().addGif(sb5, 500, 500);
                            } catch (IOException e3) {
                                e = e3;
                            }
                        } catch (IOException e4) {
                            e = e4;
                            e.printStackTrace();
                        }
                    }
                }
            }
            ToastUtils.showToast(getActivity(), "保存成功");
        }
        deleteEmoticonCache();
        backToPreview();
    }

    public /* synthetic */ void f(DialogInterface dialogInterface, int i) {
        dissmissDialog();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4003;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji_emoticon;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        view.findViewById(R.id.btn_save_emoticon).setOnClickListener(this);
        view.findViewById(R.id.btn_share_emoticon).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        this.mSelectBtn = (ImageView) view.findViewById(R.id.btn_select_all);
        this.mSelectBtn.setOnClickListener(this);
        this.mEmoticonRecyclerView = (RecyclerView) view.findViewById(R.id.rv_emoticon);
        this.mIsRTL = Util.isLayoutRTL(getContext());
        deleteEmoticonCache();
        if (this.mMimojiEmoticonAdapter == null) {
            this.mMimojiEmoticonAdapter = new MimojiEmoticonAdapter(null);
            this.mEmoticonRecyclerView.setLayoutManager(new BaseNoScrollGridLayoutManager(getContext(), 2));
            this.mEmoticonRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEmoticon.AnonymousClass1 */
                final int margin = FragmentMimojiEmoticon.this.getResources().getDimensionPixelSize(R.dimen.mimoji_emoticon_offset);

                @Override // android.support.v7.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                    if (FragmentMimojiEmoticon.this.mIsRTL) {
                        if (childAdapterPosition % 2 == 0) {
                            rect.set(this.margin, 0, 0, 0);
                        }
                    } else if (childAdapterPosition % 2 == 0) {
                        rect.set(0, 0, this.margin, 0);
                    }
                }
            });
            this.mEmoticonRecyclerView.setAdapter(this.mMimojiEmoticonAdapter);
        }
        this.mMimojiEmoticonAdapter.setOnRecyclerItemClickListener(new j(this));
        this.mMimojiEmoticonAdapter.setOnAllSelectStateChangeListener(new f(this));
        getEmoticonThumbnail();
    }

    public /* synthetic */ void l(int i) {
        if (i != 0) {
            showProgressDialog("正在努力生成表情包，剩余 " + i + " 个...");
            return;
        }
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.video2gif(this.mSelectedEmoInfoList);
        }
    }

    public /* synthetic */ void o(boolean z) {
        this.mSelectBtn.setImageResource(z ? R.drawable.bg_btn_mimoji_emoticon_all_selected : R.drawable.bg_btn_mimoji_emoticon_all_unselected);
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        backToPreview();
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                backToPreview();
                return;
            case R.id.btn_confirm:
            case R.id.btn_mimoji_change_timbre:
            default:
                return;
            case R.id.btn_save_emoticon:
                if (checkInitThumbnaiFinish()) {
                    showSaveDialog();
                    return;
                }
                return;
            case R.id.btn_select_all:
                if (checkInitThumbnaiFinish()) {
                    if (this.mMimojiEmoticonAdapter.getIsAllSelected()) {
                        this.mMimojiEmoticonAdapter.clearState();
                        return;
                    } else {
                        this.mMimojiEmoticonAdapter.selectAll();
                        return;
                    }
                } else {
                    return;
                }
            case R.id.btn_share_emoticon:
                if (checkInitThumbnaiFinish()) {
                    shareEmoticonGif();
                    return;
                }
                return;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        String str = TAG;
        Log.d(str, "provideAnimateElement, animateInElements" + list + "resetType = " + i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(250, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void release() {
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        if (mimojiEmoticonAdapter != null && mimojiEmoticonAdapter.getItemCount() > 0) {
            Iterator it = ((ArrayList) this.mMimojiEmoticonAdapter.getDataList()).iterator();
            while (it.hasNext()) {
                MimojiEmoticonInfo mimojiEmoticonInfo = (MimojiEmoticonInfo) it.next();
                if (mimojiEmoticonInfo.getBitmap() != null && !mimojiEmoticonInfo.getBitmap().isRecycled()) {
                    mimojiEmoticonInfo.getBitmap().recycle();
                }
                mimojiEmoticonInfo.setBitmap(null);
            }
        }
        dissmissDialog();
        this.mMimojiEmoticonAdapter = null;
        AvatarEngineManager2.getInstance().setEmoManager(null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(0);
        ModeCoordinatorImpl.getInstance().detachProtocol(250, this);
        release();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void updateEmoticonGifProgress(int i) {
        if (getContext() != null) {
            ((Activity) getContext()).runOnUiThread(new e(this, i));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void updateEmoticonPictureProgress(String str, EmoInfo emoInfo, boolean z) {
        if (getActivity() != null) {
            Log.d(TAG, "mimoji void updateEmoticonPictureProgress[path, emoInfo, isFinal]: " + str);
            if (z) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.mSelectedEmoInfoList);
                File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
                if (file.exists() && file.isDirectory()) {
                    for (File file2 : file.listFiles()) {
                        String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((EmoInfo) it.next()).getEmoName().equals(fileName)) {
                                it.remove();
                            }
                        }
                    }
                }
                MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
                if (mimojiEditor2 != null) {
                    mimojiEditor2.createEmoticonVideo(arrayList);
                } else {
                    Log.e(TAG, "mimoji void saveEmoticonGif[] null");
                }
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void updateEmoticonThumbnailProgress(int i, EmoInfo emoInfo) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new l(this, emoInfo, i));
        }
    }
}
