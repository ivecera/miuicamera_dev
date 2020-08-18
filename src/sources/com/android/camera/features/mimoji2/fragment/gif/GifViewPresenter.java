package com.android.camera.features.mimoji2.fragment.gif;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.features.mimoji2.fragment.gif.GifEditText;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class GifViewPresenter implements View.OnClickListener, GifEditText.ItfTextChanged, ModeProtocol.MimojiGifEditor {
    public static final int GIF_EDIT_SHOW = 303;
    public static final int Gif_EDIT_HIDE = 202;
    public static final String TAG = "FragmentGifEdit";
    private FragmentManager fragmentManager;
    public Context mContext;
    private boolean mEntryDrag = false;
    private GifEditText mEtGifWords;
    private FrameLayout mFlEtContainer;
    private GifMediaPlayer mGifMediaPlayer;
    private View mGifViewLayout;
    public boolean mIsAccelerateOpen = false;
    public boolean mIsClearBgOpen = false;
    public boolean mIsContainEmoji = false;
    public boolean mIsReverseOpen = false;
    /* access modifiers changed from: private */
    public boolean mIsShowClearDrawable;
    private ImageView mIvBack;
    private ImageView mIvClear;
    private ImageView mIvConfirm;
    private LinearLayout mLlAccelerate;
    private LinearLayout mLlAccelerate2;
    private LinearLayout mLlClearBg;
    private LinearLayout mLlClearBgOut;
    private LinearLayout mLlReverse;
    private LinearLayout mLlReverse2;
    private LinearLayout mLlStroke;
    private LinearLayout mLlThreeButton;
    private LinearLayout mLlTwoButton;
    private RelativeLayout mRlEtContainer;
    private RelativeLayout mRlGifBottomOperate;
    private GifEditText.TextJudge mTextJudge;
    private DragTextView mTvGifWords;
    int mbottomMargin;
    int mleftMargin;
    int mrightMargin;
    int mtopMargin;
    private RelativeLayout rlGifConsume;

    GifViewPresenter(Context context, FragmentManager fragmentManager2) {
        this.mContext = context;
        this.fragmentManager = fragmentManager2;
    }

    private void clearEditState() {
        this.mEtGifWords.setText("");
    }

    private void doAccelerateGif() {
        this.mGifMediaPlayer.EnableSpeedFilter(this.mIsAccelerateOpen);
    }

    private void doClearGifBackgroud() {
        this.mGifMediaPlayer.EnableVideoSegmentFilter(this.mIsClearBgOpen);
    }

    private void doReverseGif() {
        this.mGifMediaPlayer.EnableReverseFilter(this.mIsReverseOpen);
    }

    private void gifBack() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(20);
        }
    }

    private void initGifView(View view) {
        this.mRlGifBottomOperate = (RelativeLayout) view.findViewById(R.id.rl_operate_gif_bottom_panel);
        this.mRlEtContainer = (RelativeLayout) view.findViewById(R.id.rl_Et_container);
        this.rlGifConsume = (RelativeLayout) view.findViewById(R.id.rl_gif_full_screen_consume_click);
        this.rlGifConsume.setOnClickListener(this);
        this.rlGifConsume.setVisibility(0);
        this.mLlThreeButton = (LinearLayout) view.findViewById(R.id.ll_fuc_opera);
        this.mLlTwoButton = (LinearLayout) view.findViewById(R.id.ll_fuc_opera2);
        this.mLlClearBg = (LinearLayout) view.findViewById(R.id.ll_clear_bg);
        this.mLlClearBgOut = (LinearLayout) view.findViewById(R.id.ll_clear_bg_out);
        this.mLlClearBg.setOnClickListener(this);
        this.mLlAccelerate = (LinearLayout) view.findViewById(R.id.ll_accelerate);
        this.mLlAccelerate.setOnClickListener(this);
        this.mLlAccelerate2 = (LinearLayout) view.findViewById(R.id.ll_accelerate2);
        this.mLlAccelerate2.setOnClickListener(this);
        this.mLlReverse = (LinearLayout) view.findViewById(R.id.ll_reverse);
        this.mLlReverse.setOnClickListener(this);
        this.mLlReverse2 = (LinearLayout) view.findViewById(R.id.ll_reverse2);
        this.mLlReverse2.setOnClickListener(this);
        this.mIvBack = (ImageView) view.findViewById(R.id.iv_gif_back);
        this.mIvBack.setOnClickListener(this);
        this.mIvConfirm = (ImageView) view.findViewById(R.id.iv_gif_confirm);
        this.mIvConfirm.setOnClickListener(this);
        this.mIvClear = (ImageView) view.findViewById(R.id.iv_delete_gif_txt);
        this.mIvClear.setOnClickListener(this);
        this.mEtGifWords = (GifEditText) view.findViewById(R.id.et_gif_words);
        this.mEtGifWords.addTextChangedListener(this);
        this.mFlEtContainer = (FrameLayout) view.findViewById(R.id.fl_Et_container);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mFlEtContainer.getLayoutParams();
        layoutParams.addRule(14);
        this.mFlEtContainer.setLayoutParams(layoutParams);
        this.mLlStroke = (LinearLayout) view.findViewById(R.id.ll_stoke);
        this.mEtGifWords.addTextChangedListener(new TextWatcher() {
            /* class com.android.camera.features.mimoji2.fragment.gif.GifViewPresenter.AnonymousClass1 */

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                boolean unused = GifViewPresenter.this.mIsShowClearDrawable = charSequence.length() > 0;
                GifViewPresenter gifViewPresenter = GifViewPresenter.this;
                gifViewPresenter.setBgStyle(gifViewPresenter.mIsShowClearDrawable);
                GifViewPresenter gifViewPresenter2 = GifViewPresenter.this;
                gifViewPresenter2.setClearIconVisible(gifViewPresenter2.mIsShowClearDrawable);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setBgStyle(boolean z) {
        LinearLayout linearLayout;
        if (!z || (linearLayout = this.mLlStroke) == null) {
            this.mLlStroke.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.shape_gif_null_words));
        } else {
            linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.shape_gif_edit_words));
        }
    }

    /* access modifiers changed from: private */
    public void setClearIconVisible(boolean z) {
        this.mIvClear.setVisibility(z ? 0 : 4);
    }

    @Override // com.android.camera.features.mimoji2.fragment.gif.GifEditText.ItfTextChanged
    public void afterTextChanged(GifEditText.TextJudge textJudge) {
        this.mTextJudge = textJudge;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void completeVideoRecord(String str, boolean z) {
        Log.i("FragmentGifEdit", "completeVideoRecord:" + str + "isContainEmoji:" + z);
        this.mIsContainEmoji = z;
        updateOperateState();
    }

    public void gifConfirmVideo2Gif() {
        this.mGifMediaPlayer.setSubtitleTextString(this.mTextJudge);
        this.mGifMediaPlayer.startVideo2Gif();
    }

    public void initView(View view) {
        initGifView(view);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_gif_txt:
                clearEditState();
                return;
            case R.id.iv_gif_back:
                gifBack();
                return;
            case R.id.iv_gif_confirm:
                gifConfirmVideo2Gif();
                return;
            case R.id.ll_accelerate:
            case R.id.ll_accelerate2:
                this.mIsAccelerateOpen = !this.mIsAccelerateOpen;
                updateOperateState();
                doAccelerateGif();
                return;
            case R.id.ll_clear_bg:
                this.mIsClearBgOpen = !this.mIsClearBgOpen;
                updateOperateState();
                doClearGifBackgroud();
                return;
            case R.id.ll_reverse:
            case R.id.ll_reverse2:
                this.mIsReverseOpen = !this.mIsReverseOpen;
                updateOperateState();
                doReverseGif();
                return;
            default:
                return;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void operateGifPannelVisibleState(int i) {
        if (i == 202) {
            this.mRlGifBottomOperate.setVisibility(8);
        } else if (i == 303) {
            this.mRlGifBottomOperate.setVisibility(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void reconfigPreviewRadio(int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(251, this);
    }

    public void release(boolean z) {
    }

    public void setGifMediaPlayer(GifMediaPlayer gifMediaPlayer) {
        this.mGifMediaPlayer = gifMediaPlayer;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(251, this);
    }

    public void updateOperateState() {
        if (this.mIsAccelerateOpen) {
            this.mLlAccelerate.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
            this.mLlAccelerate2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
        } else {
            this.mLlAccelerate.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
            this.mLlAccelerate2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
        }
        if (this.mIsContainEmoji) {
            this.mLlThreeButton.setVisibility(8);
            this.mLlTwoButton.setVisibility(0);
        } else {
            this.mLlThreeButton.setVisibility(0);
            this.mLlTwoButton.setVisibility(8);
            if (this.mIsClearBgOpen) {
                this.mLlClearBg.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
            } else {
                this.mLlClearBg.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
            }
        }
        if (this.mIsReverseOpen) {
            this.mLlReverse.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
            this.mLlReverse2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
        } else {
            this.mLlReverse.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
            this.mLlReverse2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
        }
        this.mRlGifBottomOperate.invalidate();
    }
}
