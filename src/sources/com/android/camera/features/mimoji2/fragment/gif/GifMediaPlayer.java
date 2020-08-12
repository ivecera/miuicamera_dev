package com.android.camera.features.mimoji2.fragment.gif;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.features.mimoji2.fragment.gif.GifEditText;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.storage.MediaProviderUtil;
import com.android.camera.storage.Storage;
import com.ss.android.vesdk.VEEditor;
import com.xiaomi.MediaRecord.SystemUtil;
import com.xiaomi.Video2GifEditer.EffectMediaPlayer;
import com.xiaomi.Video2GifEditer.EffectNotifier;
import com.xiaomi.Video2GifEditer.EffectType;
import com.xiaomi.Video2GifEditer.MediaEffect;
import com.xiaomi.Video2GifEditer.MediaEffectGraph;
import com.xiaomi.Video2GifEditer.MediaProcess;
import java.io.File;
import java.util.HashMap;

public class GifMediaPlayer implements TextureView.SurfaceTextureListener {
    /* access modifiers changed from: private */
    public static final String TAG = "com.android.camera.features.mimoji2.fragment.gif.GifMediaPlayer";
    private static MiFitTextureView mFitTextureView;
    private static volatile boolean mIsLibLoaded = false;
    private static final MiLibLoader sLocalLibLoader = new MiLibLoader() {
        /* class com.android.camera.features.mimoji2.fragment.gif.GifMediaPlayer.AnonymousClass1 */

        @Override // com.android.camera.features.mimoji2.fragment.gif.MiLibLoader
        public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
            System.loadLibrary(str);
        }
    };
    private MediaProcess.Callback mCallback;
    /* access modifiers changed from: private */
    public Context mContext;
    private EffectMediaPlayer mEffectPlayer;
    private boolean mEnableReverseFilter;
    private boolean mEnableSpeedFilter;
    private boolean mEnableSubtitleFilter;
    private boolean mEnableVideoSegmentFilter;
    private MediaEffectGraph mMediaEffectGraph;
    private long mReverseFilterID;
    private long mSourceID;
    private long mSpeedFilterID;
    private long mSubtitleFilterID;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private GifEditText.TextJudge mTextJudge;
    private int mVideoHeight;
    private VideoInfo mVideoInfo;
    private long mVideoSegmentFilterID;
    private int mVideoWidth;

    /* renamed from: com.android.camera.features.mimoji2.fragment.gif.GifMediaPlayer$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$Video2GifEditer$EffectType = new int[EffectType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.ReverseFilter.ordinal()] = 1;
            $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.VideoSegmentFilter.ordinal()] = 2;
            $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.SubtitleFilter.ordinal()] = 3;
            try {
                $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.SetptsExtFilter.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    class VideoInfo {
        private Uri gifUri;
        private Uri uri;
        private String videoGifUrl;
        private String videoUrl;

        VideoInfo(String str) {
            this.videoUrl = str;
        }

        public Uri getGifUri() {
            if (this.gifUri == null) {
                String access$000 = GifMediaPlayer.TAG;
                Log.i(access$000, "videoGifUrl: " + this.videoGifUrl);
                File file = new File(this.videoGifUrl);
                String access$0002 = GifMediaPlayer.TAG;
                Log.i(access$0002, "shareFile: " + file.getPath() + " shareFile exist: " + file.exists());
                GifMediaPlayer gifMediaPlayer = GifMediaPlayer.this;
                this.gifUri = gifMediaPlayer.getFileUri(gifMediaPlayer.mContext, file);
                String access$0003 = GifMediaPlayer.TAG;
                Log.i(access$0003, "gifUri: " + this.gifUri);
            }
            return this.gifUri;
        }

        public Uri getUri() {
            if (this.uri == null) {
                this.uri = Uri.parse(this.videoUrl);
            }
            return this.uri;
        }

        public String getVideoGifUrl() {
            if (this.videoGifUrl == null) {
                this.videoGifUrl = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", VEEditor.MVConsts.TYPE_GIF);
                String access$000 = GifMediaPlayer.TAG;
                Log.i(access$000, "target=============:   " + this.videoGifUrl);
            }
            return this.videoGifUrl;
        }

        public String getVideoUrl() {
            return this.videoUrl;
        }
    }

    public GifMediaPlayer(Context context) {
        this.mContext = context;
        initPlayer(sLocalLibLoader);
    }

    private long CreateFilterByType(int i) {
        int i2 = AnonymousClass3.$SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.values()[i].ordinal()];
        if (i2 == 1) {
            return MediaEffect.CreateEffect(EffectType.ReverseFilter);
        }
        if (i2 == 2) {
            return MediaEffect.CreateEffect(EffectType.VideoSegmentFilter);
        }
        if (i2 == 3) {
            return MediaEffect.CreateEffect(EffectType.SubtitleFilter);
        }
        if (i2 != 4) {
            return 0;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("speed", "2");
        long CreateEffect = MediaEffect.CreateEffect(EffectType.SetptsExtFilter);
        MediaEffect.SetParamsForEffect(EffectType.SetptsExtFilter, CreateEffect, hashMap);
        return CreateEffect;
    }

    private void configureTransform(int i, int i2) {
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        float f2 = (float) i;
        float f3 = (float) i2;
        RectF rectF = new RectF(0.0f, 0.0f, f2, f3);
        RectF rectF2 = new RectF(0.0f, 0.0f, 1200.0f, 1200.0f);
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        if (1 == rotation || 3 == rotation) {
            rectF2.offset(centerX - rectF2.centerX(), centerY - rectF2.centerY());
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
            float max = Math.max(f3 / 1200.0f, f2 / 1200.0f);
            matrix.postScale(max, max, centerX, centerY);
            matrix.postRotate((float) ((rotation - 2) * 90), centerX, centerY);
        } else if (2 == rotation) {
            matrix.postRotate(180.0f, centerX, centerY);
        }
        mFitTextureView.setTransform(matrix);
    }

    private void initPlayer(MiLibLoader miLibLoader) {
        loadLibrariesOnce(miLibLoader);
        SystemUtil.Init(this.mContext, 123);
        this.mMediaEffectGraph = new MediaEffectGraph();
        this.mMediaEffectGraph.ConstructMediaEffectGraph();
        this.mEffectPlayer = new EffectMediaPlayer(this.mMediaEffectGraph);
        this.mEffectPlayer.ConstructMediaPlayer();
        this.mEffectPlayer.SetPlayerNotify(new EffectNotifier() {
            /* class com.android.camera.features.mimoji2.fragment.gif.GifMediaPlayer.AnonymousClass2 */

            @Override // com.xiaomi.Video2GifEditer.EffectNotifier
            public void OnReadyNow() {
            }

            @Override // com.xiaomi.Video2GifEditer.EffectNotifier
            public void OnReceiveFailed() {
            }

            @Override // com.xiaomi.Video2GifEditer.EffectNotifier
            public void OnReceiveFinish() {
            }
        });
        this.mEffectPlayer.setGravity(EffectMediaPlayer.SurfaceGravity.SurfaceGravityResizeAspectFit, 1920, 1920);
    }

    public static void loadLibrariesOnce(MiLibLoader miLibLoader) {
        synchronized (GifMediaPlayer.class) {
            if (!mIsLibLoaded) {
                if (miLibLoader == null) {
                    miLibLoader = sLocalLibLoader;
                }
                miLibLoader.loadLibrary("c++_shared");
                System.loadLibrary("mimoji_video2gif");
                mIsLibLoaded = true;
            }
        }
    }

    public void EnableReverseFilter(boolean z) {
        this.mEnableReverseFilter = z;
        this.mEffectPlayer.PausePreView();
        if (z) {
            this.mReverseFilterID = CreateFilterByType(4);
            long j = this.mReverseFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
            this.mEnableReverseFilter = true;
        } else {
            long j2 = this.mReverseFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mReverseFilterID = 0;
            }
            this.mEnableReverseFilter = false;
        }
        this.mEffectPlayer.ResumePreView();
    }

    public void EnableSpeedFilter(boolean z) {
        this.mEnableSpeedFilter = z;
        this.mEffectPlayer.PausePreView();
        if (z) {
            this.mSpeedFilterID = CreateFilterByType(7);
            long j = this.mSpeedFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
        } else {
            long j2 = this.mSpeedFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mSpeedFilterID = 0;
            }
        }
        this.mEffectPlayer.ResumePreView();
    }

    public void EnableSubtitleFilter(boolean z) {
        this.mEnableSubtitleFilter = z;
        this.mEffectPlayer.PausePreView();
        if (z) {
            this.mSubtitleFilterID = CreateFilterByType(21);
            long j = this.mSubtitleFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
        } else {
            long j2 = this.mSubtitleFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mSubtitleFilterID = 0;
            }
        }
        this.mEffectPlayer.ResumePreView();
    }

    public void EnableVideoSegmentFilter(boolean z) {
        this.mEnableVideoSegmentFilter = z;
        this.mEffectPlayer.PausePreView();
        if (z) {
            this.mVideoSegmentFilterID = CreateFilterByType(20);
            long j = this.mVideoSegmentFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
        } else {
            long j2 = this.mVideoSegmentFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mVideoSegmentFilterID = 0;
            }
        }
        this.mEffectPlayer.ResumePreView();
    }

    public void PlayCameraRecord() {
        if (new File(this.mVideoInfo.getVideoUrl()).exists()) {
            this.mEffectPlayer.StartPreView();
            this.mEffectPlayer.SetPlayLoop(true);
        }
    }

    public void SetSpeedRatio(String str) {
    }

    public void SetSubtitleText() {
        this.mEffectPlayer.PausePreView();
        if (this.mSubtitleFilterID != 0) {
            HashMap hashMap = new HashMap();
            hashMap.put("textname", this.mTextJudge.getTextNative());
            hashMap.put("posx", ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF);
            hashMap.put("posy", ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF);
            hashMap.put("subtitle_width", "100");
            hashMap.put("subtitle_height", "100");
            hashMap.put("font_size", "36");
            MediaEffect.SetParamsForEffect(EffectType.SubtitleFilter, this.mSubtitleFilterID, hashMap);
        }
        this.mEffectPlayer.ResumePreView();
    }

    public Uri getFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        }
        String str = TAG;
        Log.i(str, "getFileUri:" + file);
        return getImageContentUri(context, file);
    }

    public Uri getImageContentUri(Context context, File file) {
        String absolutePath = file.getAbsolutePath();
        String str = TAG;
        Log.i(str, "getImageContentUri filePath: " + absolutePath);
        ContentResolver contentResolver = context.getContentResolver();
        Cursor query = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{absolutePath}, null);
        if (query != null && query.moveToFirst()) {
            Log.i(TAG, "getImageContentUri cursor != null ");
            int i = query.getInt(query.getColumnIndex("_id"));
            Uri parse = Uri.parse("content://media/external/video/media");
            Uri withAppendedPath = Uri.withAppendedPath(parse, "" + i);
            String str2 = TAG;
            Log.i(str2, "getImageContentUri uriresult =  " + withAppendedPath);
            return withAppendedPath;
        } else if (!file.exists()) {
            return null;
        } else {
            Log.i(TAG, "getImageContentUri  11111");
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", absolutePath);
            contentValues.put("mime_type", Storage.MIME_GIF);
            Uri contentUriFromPath = MediaProviderUtil.getContentUriFromPath(context, absolutePath);
            String str3 = TAG;
            Log.d(str3, "insert result:2222  " + contentUriFromPath);
            if (contentUriFromPath == null) {
                contentUriFromPath = contentResolver.insert(Storage.getMediaUri(context, false, absolutePath), contentValues);
            }
            if (contentUriFromPath != null) {
                int update = contentResolver.update(contentUriFromPath, contentValues, null, null);
                String str4 = TAG;
                Log.d(str4, "insert result:444444    " + update);
            }
            String str5 = TAG;
            Log.i(str5, "getImageContentUri uriresult5555 =  " + contentUriFromPath);
            return contentUriFromPath;
        }
    }

    public VideoInfo getVideoInfo() {
        return this.mVideoInfo;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceTexture = surfaceTexture;
        this.mSurface = new Surface(this.mSurfaceTexture);
        this.mEffectPlayer.SetViewSurface(this.mSurface);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        configureTransform(i, i2);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void setFitTextureView(MiFitTextureView miFitTextureView) {
        mFitTextureView = miFitTextureView;
        mFitTextureView.setSurfaceTextureListener(this);
    }

    public void setSubtitleTextString(GifEditText.TextJudge textJudge) {
        this.mTextJudge = textJudge;
    }

    public void setVideoUrl(String str, MediaProcess.Callback callback) {
        this.mVideoInfo = new VideoInfo(str);
        this.mSourceID = this.mMediaEffectGraph.AddVideoSource(this.mVideoInfo.getVideoUrl(), false);
        this.mEffectPlayer.SetAudioMute(true);
        this.mCallback = callback;
    }

    public void startVideo2Gif() {
        this.mEffectPlayer.PausePreView();
        if (this.mEnableVideoSegmentFilter) {
            MediaProcess.AddVideoSegmentFilter();
        }
        if (this.mEnableReverseFilter) {
            MediaProcess.AddReverseFilter();
        }
        if (!TextUtils.isEmpty(this.mTextJudge.getTextNative())) {
            HashMap hashMap = new HashMap();
            hashMap.put("textname", this.mTextJudge.getTextNative());
            hashMap.put("posx", ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF);
            hashMap.put("posy", "370");
            hashMap.put("subtitle_width", "100");
            hashMap.put("subtitle_height", "100");
            hashMap.put("font_size", "36");
            MediaProcess.AddSubtitleFilter(hashMap);
        }
        float f2 = 1.0f;
        if (this.mEnableSpeedFilter) {
            f2 = 2.0f;
        }
        MediaProcess.Convert(this.mVideoInfo.getVideoUrl(), 2000, this.mVideoInfo.getVideoGifUrl(), true, 20, DurationConstant.DURATION_RESET_FALLBACK, 0, 5000, f2, this.mCallback);
        this.mEffectPlayer.ResumePreView();
    }
}
