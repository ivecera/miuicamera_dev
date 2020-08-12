package com.android.camera.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class VideoTagView implements View.OnClickListener {
    private static final int DEFAULT_TAG_MARGIN_LEFT = Util.dpToPixel(14.9f);
    private static final int DEFAULT_TAG_MARGIN_RIGHT = Util.dpToPixel(13.1f);
    private static final int FIRST_TAG_GONE_DELAY = 101;
    private static final int MAX_TAG_MARGIN_LEFT = Util.dpToPixel(15.64f);
    private static final int SPLIT_LINE_MARGIN = Util.dpToPixel(8.7f);
    private static final String TAG = "VideoTagView";
    private static final int VERTICAL_TAG = 100;
    private DataItemGlobal dataItemGlobal;
    private boolean isFirstShowTag;
    private boolean isRecordingPause;
    /* access modifiers changed from: private */
    public boolean isShowTagValue;
    private boolean isTagRecording;
    private Context mContext;
    private int mDefaultTagWidth;
    private int mExpandTagWidth;
    /* access modifiers changed from: private */
    public TextView mFirstTagTip;
    private long mNeedRemoveTime;
    private long mPauseRecordingTime;
    private long mStartRecordingTime;
    private LinearLayout mTagContent;
    private TextView mTagCountValue;
    private LinearLayout mTagDefaultLayout;
    private RelativeLayout mTagExpandLayout;
    private RelativeLayout mTagFullLayout;
    private int recordingPauseTagCount = 0;
    /* access modifiers changed from: private */
    public ValueAnimator shapeHideAnimator;
    private ValueAnimator shapeShowAnimator;
    private StringBuilder srtBuilder = new StringBuilder();
    private int videoTagCount = 0;
    private Handler videoTagHandler = new Handler() {
        /* class com.android.camera.ui.VideoTagView.AnonymousClass1 */

        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 100) {
                boolean unused = VideoTagView.this.isShowTagValue = false;
                VideoTagView.this.shapeHideAnimator.start();
                VideoTagView.this.resetVerticalTagView();
            } else if (i == 101) {
                VideoTagView.this.mFirstTagTip.setVisibility(8);
            }
        }
    };

    private String getTime(long j) {
        long j2 = (j - this.mStartRecordingTime) - this.mNeedRemoveTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return simpleDateFormat.format(Long.valueOf(j2));
    }

    private void initTagViewAnimator() {
        this.mTagDefaultLayout.setPaddingRelative(MAX_TAG_MARGIN_LEFT, 0, SPLIT_LINE_MARGIN, 0);
        this.mTagDefaultLayout.measure(0, 0);
        this.mTagExpandLayout.measure(0, 0);
        this.mExpandTagWidth = this.mTagExpandLayout.getMeasuredWidth() + this.mTagDefaultLayout.getMeasuredWidth() + DEFAULT_TAG_MARGIN_RIGHT;
        this.shapeShowAnimator = ValueAnimator.ofInt(this.mDefaultTagWidth, this.mExpandTagWidth);
        this.shapeShowAnimator.setDuration(300L);
        this.shapeHideAnimator = ValueAnimator.ofInt(this.mExpandTagWidth, this.mDefaultTagWidth);
        this.shapeShowAnimator.setDuration(300L);
        AnonymousClass2 r0 = new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.VideoTagView.AnonymousClass2 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                VideoTagView.this.setTagLayoutWidth(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        };
        this.shapeShowAnimator.addUpdateListener(r0);
        this.shapeHideAnimator.addUpdateListener(r0);
    }

    /* access modifiers changed from: private */
    public void resetVerticalTagView() {
        if (this.isTagRecording) {
            this.mTagFullLayout.setVisibility(0);
        } else {
            this.mTagFullLayout.setVisibility(8);
        }
        this.mTagDefaultLayout.setPaddingRelative(DEFAULT_TAG_MARGIN_LEFT, 0, DEFAULT_TAG_MARGIN_RIGHT, 0);
        this.mTagFullLayout.setEnabled(true);
        this.mTagExpandLayout.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void setTagLayoutWidth(int i) {
        ViewGroup.LayoutParams layoutParams = this.mTagContent.getLayoutParams();
        layoutParams.width = i;
        this.mTagContent.setLayoutParams(layoutParams);
        if (i == this.mExpandTagWidth) {
            if (this.isShowTagValue) {
                this.mTagExpandLayout.setVisibility(0);
            } else {
                resetVerticalTagView();
            }
        }
    }

    private void updateTagValueView(TextView textView) {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (this.isFirstShowTag && currentMode != 180) {
            this.mFirstTagTip.setVisibility(0);
            this.dataItemGlobal.setVideoTagNote();
            this.isFirstShowTag = false;
            this.videoTagHandler.sendEmptyMessageDelayed(101, 2000);
        }
        int i = this.videoTagCount;
        if (i < 10) {
            textView.setText("0" + this.videoTagCount);
        } else {
            textView.setText(String.valueOf(i));
        }
        this.videoTagHandler.sendEmptyMessageDelayed(100, 1000);
        initTagViewAnimator();
        this.shapeShowAnimator.start();
    }

    private void updateTagView() {
        resetVerticalTagView();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTagContent.getLayoutParams();
        layoutParams.width = this.mDefaultTagWidth;
        this.mTagContent.setLayoutParams(layoutParams);
    }

    public String getVideoTagContent() {
        return this.srtBuilder.toString();
    }

    public void init(View view, Context context) {
        this.mContext = context;
        this.mTagFullLayout = (RelativeLayout) view.findViewById(R.id.video_tag_layout);
        this.mFirstTagTip = (TextView) view.findViewById(R.id.tv_first_tag_tip);
        this.mTagContent = (LinearLayout) this.mTagFullLayout.findViewById(R.id.video_tag_content);
        this.mTagDefaultLayout = (LinearLayout) this.mTagFullLayout.findViewById(R.id.video_tag_initial);
        this.mTagExpandLayout = (RelativeLayout) this.mTagFullLayout.findViewById(R.id.video_tag_save_layout);
        this.mTagCountValue = (TextView) this.mTagFullLayout.findViewById(R.id.video_tag_value);
        this.mTagFullLayout.setOnClickListener(this);
        this.mTagDefaultLayout.measure(0, 0);
        this.mTagExpandLayout.measure(0, 0);
        this.mDefaultTagWidth = this.mTagDefaultLayout.getMeasuredWidth() + DEFAULT_TAG_MARGIN_RIGHT + DEFAULT_TAG_MARGIN_LEFT;
        this.mExpandTagWidth = this.mTagExpandLayout.getMeasuredWidth() + this.mTagDefaultLayout.getMeasuredWidth() + MAX_TAG_MARGIN_LEFT;
    }

    public void onClick(View view) {
        String str;
        this.isShowTagValue = true;
        this.mTagFullLayout.setEnabled(false);
        if (this.isRecordingPause) {
            int i = this.recordingPauseTagCount;
            if (i == 0) {
                this.recordingPauseTagCount = i + 1;
                str = getTime(this.mPauseRecordingTime);
            } else {
                updateTagValueView(this.mTagCountValue);
                return;
            }
        } else {
            str = getTime(System.currentTimeMillis());
        }
        this.videoTagCount++;
        this.srtBuilder.append(this.videoTagCount + "\n");
        this.srtBuilder.append(String.format("%s\n", str));
        this.srtBuilder.append("\n");
        updateTagValueView(this.mTagCountValue);
    }

    public void pause() {
        Log.d(TAG, "handleTagRecordingPause: ");
        this.isRecordingPause = true;
        this.recordingPauseTagCount = 0;
        this.mPauseRecordingTime = System.currentTimeMillis();
    }

    public void resume() {
        Log.d(TAG, "handleTagRecordingResume: ");
        this.isRecordingPause = false;
        this.mNeedRemoveTime = System.currentTimeMillis() - this.mPauseRecordingTime;
    }

    public void start(String str) {
        Log.d(TAG, "handleTagRecordingStart: ");
        this.dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        this.isFirstShowTag = this.dataItemGlobal.isFirstShowTag();
        this.mStartRecordingTime = System.currentTimeMillis();
        this.videoTagCount = 0;
        this.mNeedRemoveTime = 0;
        this.isShowTagValue = true;
        this.isRecordingPause = false;
        this.isTagRecording = true;
        updateTagView();
        if (this.srtBuilder.length() != 0) {
            StringBuilder sb = this.srtBuilder;
            sb.delete(0, sb.length());
        }
    }

    public void stop() {
        Log.d(TAG, "handleTagRecordingStop: ");
        this.isRecordingPause = false;
        this.videoTagCount = 0;
        this.isTagRecording = false;
        this.mTagFullLayout.setVisibility(8);
    }
}
