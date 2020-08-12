package com.android.camera.features.mimoji2.module.protocol;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.TextureVideoView;
import com.arcsoft.avatar.emoticon.EmoInfo;
import java.util.List;

public interface MimojiModeProtocol extends ModeProtocol {

    public interface MimojiAvatarEngine2 extends ModeProtocol.BaseProtocol {
        public static final int TYPE_TAG = 246;

        void backToPreview(boolean z, boolean z2);

        void changeToSquare(boolean z);

        boolean deleteMimojiCache(int i);

        String getTimeValue();

        String getVideoCache();

        void initAvatarEngine(int i, int i2, int i3, int i4, boolean z);

        boolean isOnCreateMimoji();

        boolean isRecordStopping();

        boolean isRecording();

        void onCaptureImage();

        boolean onCreateCapture();

        void onDeviceRotationChange(int i);

        void onDrawFrame(Rect rect, int i, int i2, boolean z);

        void onMimojiChangeBg(MimojiBgInfo mimojiBgInfo);

        void onMimojiChangeTimbre(MimojiTimbreInfo mimojiTimbreInfo);

        void onMimojiCreate();

        void onMimojiDeleted();

        void onMimojiInitFinish();

        void onMimojiSelect(MimojiInfo2 mimojiInfo2);

        void onRecordStart();

        void onRecordStop(boolean z);

        void onResume();

        void release();

        void releaseRender();

        void setDetectSuccess(boolean z);

        void setDisableSingleTapUp(boolean z);
    }

    public interface MimojiBottomList extends ModeProtocol.BaseProtocol {
        public static final int TYPE_TAG = 248;

        void firstProgressShow(boolean z);

        int refreshMimojiList();
    }

    public interface MimojiEditor2 extends ModeProtocol.BaseProtocol {
        public static final int TYPE_TAG = 247;

        public interface MimojiEmoticon extends ModeProtocol.BaseProtocol {
            public static final int TYPE_TAG = 250;

            void backToPreview();

            void coverEmoticonError();

            void coverEmoticonSuccess();

            void release();

            void updateEmoticonGifProgress(int i);

            void updateEmoticonPictureProgress(String str, EmoInfo emoInfo, boolean z);

            void updateEmoticonThumbnailProgress(int i, EmoInfo emoInfo);
        }

        void createEmoticonPicture(List<EmoInfo> list);

        void createEmoticonThumbnail();

        void createEmoticonVideo(List<EmoInfo> list);

        void directlyEnterEditMode(MimojiInfo2 mimojiInfo2, int i);

        void goBack(boolean z, boolean z2);

        void onDeviceRotationChange(int i);

        void onTypeConfigSelect(int i);

        void quitCoverEmoticon();

        void releaseRender();

        void requestRender(boolean z);

        void resetClickEnable(boolean z);

        void resetConfig();

        void showEmoticon();

        void startMimojiEdit(int i);
    }

    public interface MimojiFullScreenProtocol extends ModeProtocol.BaseProtocol {
        public static final int TYPE_TAG = 249;

        void concatResult(String str);

        String getMimojiVideoSavePath();

        boolean isMimojiRecordPreviewShowing();

        void onCombineError();

        void onCombineSuccess();

        void onMimojiSaveToLocalFinished(Uri uri);

        void setPreviewCover(Bitmap bitmap);

        void startMimojiRecordPreview();

        void startMimojiRecordSaving();
    }

    public interface MimojiVideoEditor extends ModeProtocol.BaseProtocol {
        public static final int TYPE_TAG = 252;

        void changeTimbre();

        void combineVideoAudio(String str);

        boolean init(TextureVideoView textureVideoView, String str);

        boolean isPlaying();

        void onDestory();

        boolean pausePlay();

        boolean resumePlay();

        void setRecordParameter(int i, int i2, int i3);

        void startPlay();

        void video2gif(List<EmoInfo> list);
    }
}
