.class public Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;
.super Ljava/lang/Object;
.source "MimojiAvatarEngine2Impl.java"

# interfaces
.implements Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;
.implements Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;
.implements Lcom/android/camera2/Camera2Proxy$PreviewCallback;


# static fields
.field public static final DELETE_GIF:I = 0x3

.field public static final DELETE_MIMOJI_ALL:I = 0x0

.field public static final DELETE_MIMOJI_EMOTICON:I = 0x2

.field public static final DELETE_MIMOJI_VIDEO:I = 0x1

.field private static final FLAG_HAS_FACE:I = 0x5

.field private static final FLAG_INIT_FACE:I = 0x0

.field private static final HANDLER_RECORDING_CURRENT_FILE_SIZE:I = 0x3

.field private static final HANDLER_RECORDING_CURRENT_TIME:I = 0x1

.field private static final HANDLER_RECORDING_MAX_DURATION_REACHED:I = 0x2

.field private static final HANDLER_RECORDING_MAX_FILE_SIZE_REACHED:I = 0x4

.field private static final HANDLER_RESOURCE_ERROR_BROKEN:I = 0x0

.field private static final START_OFFSET_MS:J = 0x1c2L

.field private static final TAG:Ljava/lang/String; = "MimojiAvatarEngine2Impl"


# instance fields
.field private mActivityBase:Lcom/android/camera/ActivityBase;

.field private mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

.field private final mAvatarLock:Ljava/lang/Object;

.field private mAvatarTemplatePath:Ljava/lang/String;

.field private mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

.field private mCaptureCallback:Lcom/arcsoft/avatar/RecordModule$MediaResultCallback;

.field private mContext:Landroid/content/Context;

.field private mCountDownTimer:Landroid/os/CountDownTimer;

.field private mCurrentScreenOrientation:I

.field private mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

.field private mDeviceRotation:I

.field private mDisplayOrientation:I

.field private mDrawSize:Landroid/util/Size;

.field private mFaceDectectResult:I

.field private mGetThumCountDownLatch:Ljava/util/concurrent/CountDownLatch;

.field private mHandler:Landroid/os/Handler;

.field private volatile mIsAvatarInited:Z

.field private mIsFaceDetectSuccess:Z

.field private mIsFrontCamera:Z

.field private mIsNoFaceResult:I

.field private mIsRecordStopping:Z

.field private volatile mIsRecording:Z

.field private mIsShutterButtonClick:Z

.field private mIsStopRender:Z

.field private mLastNeedBeauty:Z

.field private mLoadHandler:Landroid/os/Handler;

.field private mLoadResourceHandler:Landroid/os/Handler;

.field private mLoadResourceThread:Landroid/os/HandlerThread;

.field private mLoadThread:Landroid/os/HandlerThread;

.field private mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

.field private mMaxVideoDurationInMs:I

.field private mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

.field private mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

.field private mNeedCapture:Z

.field private mOrientation:I

.field private mPreviewHeight:I

.field private mPreviewWidth:I

.field private mRecordModule:Lcom/arcsoft/avatar/RecordModule;

.field private mRecordingListener:Lcom/arcsoft/avatar/recoder/RecordingListener;

.field private mSquare:Z

.field private mTextureId:[I

.field private mTotalRecordingTime:J

.field private mUiHandler:Landroid/os/Handler;

.field private mVideoFileStream:Ljava/io/FileOutputStream;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>(Lcom/android/camera/ActivityBase;)V
    .locals 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/16 v0, 0x5a

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    const/4 v0, 0x0

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentScreenOrientation:I

    const-string v1, ""

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsShutterButtonClick:Z

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsStopRender:Z

    iput v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mFaceDectectResult:I

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFaceDetectSuccess:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mNeedCapture:Z

    new-instance v2, Landroid/os/HandlerThread;

    const-string v3, "LoadConfig"

    invoke-direct {v2, v3}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadThread:Landroid/os/HandlerThread;

    new-instance v2, Landroid/os/HandlerThread;

    const-string v3, "LoadResource"

    invoke-direct {v2, v3}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadResourceThread:Landroid/os/HandlerThread;

    new-instance v2, Ljava/lang/Object;

    invoke-direct {v2}, Ljava/lang/Object;-><init>()V

    iput-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    new-array v1, v1, [I

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mTextureId:[I

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecordStopping:Z

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLastNeedBeauty:Z

    const/4 v1, -0x1

    iput v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsNoFaceResult:I

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mSquare:Z

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$5;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCaptureCallback:Lcom/arcsoft/avatar/RecordModule$MediaResultCallback;

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$7;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$7;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordingListener:Lcom/arcsoft/avatar/recoder/RecordingListener;

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$8;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v2

    invoke-direct {v1, p0, v2}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$8;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Landroid/os/Looper;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mHandler:Landroid/os/Handler;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result v1

    if-eqz v1, :cond_0

    const/16 v1, 0x1388

    iput v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMaxVideoDurationInMs:I

    goto :goto_0

    :cond_0
    const/16 v1, 0x3a98

    iput v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMaxVideoDurationInMs:I

    :goto_0
    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCameraAppImpl()Lcom/android/camera/CameraAppImpl;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/Application;->getApplicationContext()Landroid/content/Context;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa6

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xf7

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadResourceThread:Landroid/os/HandlerThread;

    invoke-virtual {v1}, Landroid/os/HandlerThread;->start()V

    new-instance v1, Landroid/os/Handler;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadResourceThread:Landroid/os/HandlerThread;

    invoke-virtual {v2}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadResourceHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadThread:Landroid/os/HandlerThread;

    invoke-virtual {v1}, Landroid/os/HandlerThread;->start()V

    new-instance v1, Landroid/os/Handler;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadThread:Landroid/os/HandlerThread;

    invoke-virtual {v2}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadHandler:Landroid/os/Handler;

    new-instance v1, Landroid/os/Handler;

    invoke-virtual {p1}, Landroid/app/Activity;->getMainLooper()Landroid/os/Looper;

    move-result-object p1

    invoke-direct {v1, p1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mUiHandler:Landroid/os/Handler;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->setIsAvatarInited(Z)V

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string p1, "MimojiAvatarEngine2Impl:  constructor"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private CaptureCallback(Ljava/nio/ByteBuffer;)V
    .locals 14

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDrawSize:Landroid/util/Size;

    invoke-virtual {v0}, Landroid/util/Size;->getWidth()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDrawSize:Landroid/util/Size;

    invoke-virtual {v1}, Landroid/util/Size;->getHeight()I

    move-result v1

    sget-object v2, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v0, v1, v2}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/graphics/Bitmap;->copyPixelsFromBuffer(Ljava/nio/Buffer;)V

    new-instance v8, Landroid/graphics/Matrix;

    invoke-direct {v8}, Landroid/graphics/Matrix;-><init>()V

    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    const/16 v1, 0x10e

    const/high16 v2, -0x40800000    # -1.0f

    const/high16 v3, 0x3f800000    # 1.0f

    if-eqz p1, :cond_2

    if-eqz p1, :cond_1

    iget p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    const/16 v4, 0x5a

    if-eq p1, v4, :cond_2

    if-ne p1, v1, :cond_1

    goto :goto_0

    :cond_1
    iget p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    rem-int/lit16 p1, p1, 0xb4

    if-nez p1, :cond_3

    invoke-virtual {v8, v2, v3}, Landroid/graphics/Matrix;->postScale(FF)Z

    goto :goto_1

    :cond_2
    :goto_0
    invoke-virtual {v8, v3, v2}, Landroid/graphics/Matrix;->postScale(FF)Z

    :cond_3
    :goto_1
    const/4 v4, 0x0

    const/4 v5, 0x0

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDrawSize:Landroid/util/Size;

    invoke-virtual {p1}, Landroid/util/Size;->getWidth()I

    move-result v6

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDrawSize:Landroid/util/Size;

    invoke-virtual {p1}, Landroid/util/Size;->getHeight()I

    move-result v7

    const/4 v9, 0x0

    move-object v3, v0

    invoke-static/range {v3 .. v9}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;

    move-result-object p1

    const/4 v2, 0x0

    invoke-static {v2}, Lcom/android/camera/CameraSettings;->getEncodingQuality(Z)Lcom/android/camera/EncodingQuality;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/android/camera/EncodingQuality;->toInteger(Z)I

    move-result v3

    invoke-static {p1, v3}, Lcom/android/camera/Util;->getBitmapData(Landroid/graphics/Bitmap;I)[B

    move-result-object v3

    iget-boolean v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    if-eqz v4, :cond_4

    iget v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    rem-int/lit16 v5, v4, 0xb4

    if-nez v5, :cond_4

    add-int/lit16 v4, v4, 0xb4

    rem-int/lit16 v4, v4, 0x168

    goto :goto_2

    :cond_4
    iget v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    :goto_2
    iget-boolean v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    if-eqz v5, :cond_5

    move-object v5, v0

    goto :goto_3

    :cond_5
    move-object v5, p1

    :goto_3
    iget-boolean v6, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    const/4 v7, 0x0

    invoke-static {v7, v5, v4, v6}, Lcom/android/camera/Thumbnail;->createThumbnail(Landroid/net/Uri;Landroid/graphics/Bitmap;IZ)Lcom/android/camera/Thumbnail;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/Thumbnail;->startWaitingForUri()V

    iget-object v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-virtual {v5}, Lcom/android/camera/ActivityBase;->getThumbnailUpdater()Lcom/android/camera/ThumbnailUpdater;

    move-result-object v5

    const/4 v6, 0x1

    invoke-virtual {v5, v4, v6, v6}, Lcom/android/camera/ThumbnailUpdater;->setThumbnail(Lcom/android/camera/Thumbnail;ZZ)V

    iget-object v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-virtual {v4}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object v4

    check-cast v4, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    if-eqz v4, :cond_6

    invoke-virtual {v4}, Lcom/android/camera/module/BaseModule;->getActualCameraId()I

    move-result v4

    move v9, v4

    goto :goto_4

    :cond_6
    move v9, v2

    :goto_4
    new-instance v4, Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v10

    const/4 v12, -0x4

    const/4 v13, 0x0

    move-object v8, v4

    invoke-direct/range {v8 .. v13}, Lcom/xiaomi/camera/core/ParallelTaskData;-><init>(IJILjava/lang/String;)V

    invoke-virtual {v4, v3, v2}, Lcom/xiaomi/camera/core/ParallelTaskData;->fillJpegData([BI)V

    iget-boolean v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    iget v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    invoke-static {v3, v5}, Lcom/android/camera/Util;->getJpegRotation(II)I

    move-result v3

    add-int/2addr v3, v1

    rem-int/lit16 v3, v3, 0x168

    new-instance v1, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    iget-object v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDrawSize:Landroid/util/Size;

    const/16 v6, 0x100

    invoke-direct {v1, v5, v5, v5, v6}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;-><init>(Landroid/util/Size;Landroid/util/Size;Landroid/util/Size;I)V

    invoke-static {}, Lcom/android/camera/LocationManager;->instance()Lcom/android/camera/LocationManager;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/camera/LocationManager;->getCurrentLocation()Landroid/location/Location;

    move-result-object v5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result v6

    invoke-virtual {v1, v6}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setHasDualWaterMark(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-virtual {v1, v3}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setJpegRotation(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-static {v2}, Lcom/android/camera/CameraSettings;->getEncodingQuality(Z)Lcom/android/camera/EncodingQuality;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/android/camera/EncodingQuality;->toInteger(Z)I

    move-result v3

    invoke-virtual {v1, v3}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setJpegQuality(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    sget v3, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    invoke-virtual {v1, v3}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setFilterId(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    const/4 v3, -0x1

    iget v6, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mOrientation:I

    if-ne v3, v6, :cond_7

    goto :goto_5

    :cond_7
    move v2, v6

    :goto_5
    invoke-virtual {v1, v2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setOrientation(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTimeWaterMarkOpen()Z

    move-result v2

    if-eqz v2, :cond_8

    invoke-static {}, Lcom/android/camera/Util;->getTimeWatermark()Ljava/lang/String;

    move-result-object v2

    goto :goto_6

    :cond_8
    move-object v2, v7

    :goto_6
    invoke-virtual {v1, v2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setTimeWaterMarkString(Ljava/lang/String;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getDeviceWaterMarkParam()Lcom/android/camera/effect/renders/DeviceWatermarkParam;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setDeviceWatermarkParam(Lcom/android/camera/effect/renders/DeviceWatermarkParam;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getPictureInfo()Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setPictureInfo(Lcom/xiaomi/camera/core/PictureInfo;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-virtual {v1, v5}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setLocation(Landroid/location/Location;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object v1

    invoke-virtual {v1}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->build()Lcom/xiaomi/camera/core/ParallelTaskDataParameter;

    move-result-object v1

    invoke-virtual {v4, v1}, Lcom/xiaomi/camera/core/ParallelTaskData;->fillParameter(Lcom/xiaomi/camera/core/ParallelTaskDataParameter;)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-interface {v1}, Lcom/android/camera/AppController;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v1

    invoke-virtual {v1, v4, v7, v7}, Lcom/android/camera/storage/ImageSaver;->onParallelProcessFinish(Lcom/xiaomi/camera/core/ParallelTaskData;Landroid/hardware/camera2/CaptureResult;Landroid/hardware/camera2/CameraCharacteristics;)Z

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->recycle()V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/MimojiModule;->onMimojiCaptureCallback()V

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    return-object p0
.end method

.method static synthetic access$100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/AvatarEngine;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    return-object p0
.end method

.method static synthetic access$1002(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecordStopping:Z

    return p1
.end method

.method static synthetic access$1100(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Landroid/os/Handler;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mHandler:Landroid/os/Handler;

    return-object p0
.end method

.method static synthetic access$1200(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/nio/ByteBuffer;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->CaptureCallback(Ljava/nio/ByteBuffer;)V

    return-void
.end method

.method static synthetic access$1300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Landroid/os/Handler;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadHandler:Landroid/os/Handler;

    return-object p0
.end method

.method static synthetic access$200()Ljava/lang/String;
    .locals 1

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/arcsoft/avatar/RecordModule;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    return-object p0
.end method

.method static synthetic access$400(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)J
    .locals 2

    iget-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mTotalRecordingTime:J

    return-wide v0
.end method

.method static synthetic access$402(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;J)J
    .locals 0

    iput-wide p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mTotalRecordingTime:J

    return-wide p1
.end method

.method static synthetic access$500(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMaxVideoDurationInMs:I

    return p0
.end method

.method static synthetic access$600(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Lcom/android/camera/ActivityBase;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    return-object p0
.end method

.method static synthetic access$700(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/io/FileOutputStream;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mVideoFileStream:Ljava/io/FileOutputStream;

    return-object p0
.end method

.method static synthetic access$702(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/io/FileOutputStream;)Ljava/io/FileOutputStream;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mVideoFileStream:Ljava/io/FileOutputStream;

    return-object p1
.end method

.method static synthetic access$800(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)Ljava/util/concurrent/CountDownLatch;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mGetThumCountDownLatch:Ljava/util/concurrent/CountDownLatch;

    return-object p0
.end method

.method static synthetic access$902(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecording:Z

    return p1
.end method

.method private animateCapture()V
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraSoundOpen()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/ActivityBase;->playCameraSound(I)V

    return-void
.end method

.method public static create(Lcom/android/camera/ActivityBase;)Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;
    .locals 1

    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;-><init>(Lcom/android/camera/ActivityBase;)V

    return-object v0
.end method

.method private createAvatar([BII)V
    .locals 14

    move-object v0, p0

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    if-eq v1, v2, :cond_0

    iput-object v2, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->setTemplatePath(Ljava/lang/String;)V

    :cond_0
    new-instance v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileResult;

    invoke-direct {v1}, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileResult;-><init>()V

    iget-object v2, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    monitor-enter v2

    :try_start_0
    iget-object v3, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object v4, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    mul-int/lit8 v7, p2, 0x4

    const/4 v9, 0x0

    const/4 v10, 0x0

    const/4 v12, 0x0

    sget-object v13, Lcom/android/camera/features/mimoji2/module/impl/c;->INSTANCE:Lcom/android/camera/features/mimoji2/module/impl/c;

    move/from16 v5, p2

    move/from16 v6, p3

    move-object v8, p1

    move-object v11, v1

    invoke-virtual/range {v3 .. v13}, Lcom/arcsoft/avatar/AvatarEngine;->avatarProfile(Ljava/lang/String;III[BIZLcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileResult;Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileInfo;Lcom/arcsoft/avatar/AvatarConfig$UpdateProgressCallback;)I

    move-result v3

    monitor-exit v2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    sget-object v2, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "avatarProfile res: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", status:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v5, v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileResult;->status:I

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", gender: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v5, v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileResult;->gender:I

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Lcom/arcsoft/avatar/util/LOG;->d(Ljava/lang/String;Ljava/lang/String;)V

    iget v1, v1, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProfileResult;->status:I

    const/16 v2, 0xfe

    if-eq v1, v2, :cond_b

    const/16 v2, 0xf6

    if-ne v1, v2, :cond_1

    goto/16 :goto_1

    :cond_1
    const/4 v2, 0x1

    const/4 v3, 0x0

    if-ne v1, v2, :cond_2

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0184

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto/16 :goto_0

    :cond_2
    and-int/lit8 v2, v1, 0x2

    if-nez v2, :cond_3

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f017f

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto/16 :goto_0

    :cond_3
    and-int/lit8 v2, v1, 0x4

    if-nez v2, :cond_4

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0183

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    :cond_4
    and-int/lit8 v2, v1, 0x8

    if-nez v2, :cond_5

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0182

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    :cond_5
    and-int/lit8 v2, v1, 0x10

    if-nez v2, :cond_6

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0180

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    :cond_6
    and-int/lit8 v2, v1, 0x20

    if-nez v2, :cond_7

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0185

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    :cond_7
    and-int/lit8 v2, v1, 0x40

    if-nez v2, :cond_8

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0181

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    :cond_8
    and-int/lit16 v1, v1, 0x80

    if-nez v1, :cond_9

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f017e

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    :cond_9
    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const v2, 0x7f0f0187

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    :goto_0
    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-eqz v1, :cond_a

    new-instance v2, Lcom/android/camera/features/mimoji2/module/impl/f;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/module/impl/f;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    invoke-virtual {v1, v2}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_a
    return-void

    :cond_b
    :goto_1
    sget-object v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "result = "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mUiHandler:Landroid/os/Handler;

    new-instance v2, Lcom/android/camera/features/mimoji2/module/impl/e;

    invoke-direct {v2, p0}, Lcom/android/camera/features/mimoji2/module/impl/e;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void

    :catchall_0
    move-exception v0

    :try_start_1
    monitor-exit v2
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v0
.end method

.method private getDeviceWaterMarkParam()Lcom/android/camera/effect/renders/DeviceWatermarkParam;
    .locals 9

    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result p0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCameraWaterMarkOpen()Z

    move-result v0

    if-nez p0, :cond_0

    if-eqz v0, :cond_1

    :cond_0
    const/4 p0, 0x0

    const/4 v0, 0x1

    :cond_1
    move v2, p0

    move v3, v0

    const/4 p0, 0x0

    if-eqz v2, :cond_2

    const v0, 0x7f070099

    invoke-static {v0, p0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v0

    const v1, 0x7f070097

    invoke-static {v1, p0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v1

    const v4, 0x7f070098

    invoke-static {v4, p0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result p0

    :goto_0
    move v8, p0

    move v6, v0

    move v7, v1

    goto :goto_1

    :cond_2
    if-eqz v3, :cond_3

    const v0, 0x7f0700cc

    invoke-static {v0, p0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v0

    const v1, 0x7f0700ca

    invoke-static {v1, p0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v1

    const v4, 0x7f0700cb

    invoke-static {v4, p0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result p0

    goto :goto_0

    :cond_3
    move v6, p0

    move v7, v6

    move v8, v7

    :goto_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelRearOn()Z

    move-result v4

    new-instance p0, Lcom/android/camera/effect/renders/DeviceWatermarkParam;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getDualCameraWaterMarkFilePathVendor()Ljava/lang/String;

    move-result-object v5

    move-object v1, p0

    invoke-direct/range {v1 .. v8}, Lcom/android/camera/effect/renders/DeviceWatermarkParam;-><init>(ZZZLjava/lang/String;FFF)V

    return-object p0
.end method

.method private getMimojiPara()Ljava/util/Map;
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isNeedShowAvatar()Z

    move-result v1

    const-string v2, "attr_mimoji_category"

    if-eqz v1, :cond_5

    new-instance v0, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;

    invoke-direct {v0}, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;-><init>()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v1, v0}, Lcom/arcsoft/avatar/AvatarEngine;->getConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)V

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getMimojiConfigValue(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigValue;)Ljava/util/Map;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    const-string p0, "custom"

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PigTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    const-string p0, "pig"

    goto :goto_0

    :cond_1
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->BearTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    const-string p0, "bear"

    goto :goto_0

    :cond_2
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    sget-object v3, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->RoyanTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3

    const-string p0, "royan"

    goto :goto_0

    :cond_3
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->RabbitTemplatePath:Ljava/lang/String;

    invoke-virtual {p0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_4

    const-string p0, "rabbit"

    goto :goto_0

    :cond_4
    const-string p0, ""

    :goto_0
    invoke-interface {v0, v2, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_1

    :cond_5
    const-string p0, "null"

    invoke-interface {v0, v2, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :goto_1
    return-object v0
.end method

.method private getPictureInfo()Lcom/xiaomi/camera/core/PictureInfo;
    .locals 2

    new-instance v0, Lcom/xiaomi/camera/core/PictureInfo;

    invoke-direct {v0}, Lcom/xiaomi/camera/core/PictureInfo;-><init>()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isFrontMirror()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setFrontMirror(Z)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setSensorType(Z)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setBokehFrontCamera(Z)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    const-string v1, "off"

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setHdrType(Ljava/lang/String;)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getOperatingMode()I

    move-result p0

    invoke-virtual {v0, p0}, Lcom/xiaomi/camera/core/PictureInfo;->setOpMode(I)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/xiaomi/camera/core/PictureInfo;->end()V

    return-object p0
.end method

.method private initMimojiResource()V
    .locals 6

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->yb()Ljava/lang/String;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->getMimojiModleVersion()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->DATA_DIR:Ljava/lang/String;

    invoke-static {v1}, Lcom/android/camera/module/impl/component/FileUtils;->checkFileDirectoryConsist(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->MODEL_PATH:Ljava/lang/String;

    invoke-static {v1}, Lcom/android/camera/module/impl/component/FileUtils;->checkFileDirectoryConsist(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    return-void

    :cond_0
    sget-object v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v2, "MimojiAvatarEngine2Impl: initMimojiResource unzip..."

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v1

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setIsLoading(Z)V

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->MIMOJI_DIR:Ljava/lang/String;

    invoke-static {v1}, Lcom/android/camera/module/impl/component/FileUtils;->delDir(Ljava/lang/String;)Z

    const v1, 0x8000

    :try_start_0
    iget-object v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const-string v4, "vendor/camera/mimoji/data.zip"

    sget-object v5, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->MIMOJI_DIR:Ljava/lang/String;

    invoke-static {v3, v4, v5, v1}, Lcom/android/camera/Util;->verifyFileZip(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v1

    sget-object v2, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v3, "verify asset data zip failed..."

    invoke-static {v2, v3, v1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    const/4 v2, 0x0

    :goto_0
    if-nez v2, :cond_1

    const/4 v1, 0x0

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->setMimojiModleVersion(Ljava/lang/String;)V

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsStopRender:Z

    if-nez v1, :cond_1

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->initMimojiResource()V

    return-void

    :cond_1
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadResourceHandler:Landroid/os/Handler;

    new-instance v2, Lcom/android/camera/features/mimoji2/module/impl/d;

    invoke-direct {v2, p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/d;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method private isFrontMirror()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result p0

    if-eqz p0, :cond_1

    const/4 p0, 0x1

    return p0

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontMirror()Z

    move-result p0

    return p0
.end method

.method static synthetic o(I)V
    .locals 0

    return-void
.end method

.method private onProfileFinish()V
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "onProfileFinish"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xd4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onPostSavingFinish()V

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    const/4 v2, -0x1

    invoke-interface {v0, v1, v2}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertMimojiFaceDetect(ZI)V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf7

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->mimojiEnd()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz v0, :cond_2

    const/16 v1, 0xcb

    invoke-interface {v0, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->startMimojiEdit(I)V

    :cond_2
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-eqz p0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/MimojiModule;->onMimojiCreateCompleted(Z)V

    :cond_3
    const-string p0, "mimoji_click_create_capture"

    const-string v0, "create"

    invoke-static {p0, v0}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiClick(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method private quit()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadThread:Landroid/os/HandlerThread;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->quitSafely()Z

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadResourceThread:Landroid/os/HandlerThread;

    invoke-virtual {p0}, Landroid/os/HandlerThread;->quitSafely()Z

    return-void
.end method

.method private reloadConfig()V
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "MimojiAvatarEngine2Impl reloadConfig"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getMode()I

    move-result v0

    const/4 v1, 0x2

    if-eq v0, v1, :cond_2

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x6

    if-ne v0, v1, :cond_6

    sget-object v0, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v1, v0}, Lcom/arcsoft/avatar/AvatarEngine;->setTemplatePath(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object v1, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TempEditConfigPath:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/arcsoft/avatar/AvatarEngine;->loadConfig(Ljava/lang/String;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf7

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->resetClickEnable(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->resetConfig()V

    goto :goto_1

    :cond_1
    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v0, "MimojiAvatarEngine2Impl reloadConfig: error mimojiEditor is null"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    :cond_2
    :goto_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiInfo()Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-eqz v1, :cond_3

    sget-object v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v2, "mimoji void reloadConfig[]\u3000extrascene init"

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {v1}, Lcom/arcsoft/avatar/RecordModule;->resetExtraScene()V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    sget-object v2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->PersonTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/RecordModule;->setExtraSceneTemplatePath(Ljava/lang/String;)V

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isNeedShowAvatar()Z

    move-result v1

    if-eqz v1, :cond_6

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    iget-object v2, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_4

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-object v2, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-virtual {v1, v2}, Lcom/arcsoft/avatar/AvatarEngine;->setTemplatePath(Ljava/lang/String;)V

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    :cond_4
    iget-object v0, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->isPrefabModel(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_5

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v1, v0}, Lcom/arcsoft/avatar/AvatarEngine;->loadConfig(Ljava/lang/String;)V

    :cond_5
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v0, p0}, Lcom/arcsoft/avatar/RecordModule;->updateAvatarConfigInfo(Lcom/arcsoft/avatar/AvatarEngine;)V

    :cond_6
    :goto_1
    return-void
.end method

.method private updateBeauty()V
    .locals 3

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p0

    check-cast p0, Lcom/android/camera/module/BaseModule;

    instance-of v0, p0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    if-nez v0, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningShine()Lcom/android/camera/data/data/runing/ComponentRunningShine;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->supportBeautyLevel()Z

    move-result v1

    if-eqz v1, :cond_2

    const/4 v0, 0x3

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->setFaceBeautyLevel(I)V

    goto :goto_0

    :cond_2
    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->supportSmoothLevel()Z

    move-result v0

    if-eqz v0, :cond_3

    const/16 v0, 0x28

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->setFaceBeautySmoothLevel(I)V

    :cond_3
    :goto_0
    const/4 v0, 0x1

    new-array v0, v0, [I

    const/4 v1, 0x0

    const/16 v2, 0xd

    aput v2, v0, v1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/BaseModule;->updatePreferenceInWorkThread([I)V

    return-void
.end method

.method private updateVideoOrientation(I)V
    .locals 3

    const/16 v0, 0x13b

    if-le p1, v0, :cond_0

    const/16 v1, 0x168

    if-le p1, v1, :cond_1

    :cond_0
    const/16 v1, 0x2d

    if-ltz p1, :cond_2

    if-gt p1, v1, :cond_2

    :cond_1
    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentScreenOrientation:I

    goto :goto_0

    :cond_2
    const/16 v2, 0x87

    if-le p1, v1, :cond_3

    if-gt p1, v2, :cond_3

    const/16 p1, 0x5a

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentScreenOrientation:I

    goto :goto_0

    :cond_3
    const/16 v1, 0xe1

    if-le p1, v2, :cond_4

    if-gt p1, v1, :cond_4

    const/16 p1, 0xb4

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentScreenOrientation:I

    goto :goto_0

    :cond_4
    if-le p1, v1, :cond_5

    if-gt p1, v0, :cond_5

    const/16 p1, 0x10e

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentScreenOrientation:I

    :cond_5
    :goto_0
    return-void
.end method

.method static synthetic zf()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf8

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;->firstProgressShow(Z)V

    goto :goto_0

    :cond_0
    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "mimojiBottomList finish == null"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    return-void
.end method


# virtual methods
.method public synthetic Af()V
    .locals 9

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->deleteMimojiCache(I)Z

    sget-object v0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_CACHE_DIR:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/FileUtils;->makeNoMediaDir(Ljava/lang/String;)Z

    invoke-static {}, Lcom/android/camera/CameraSettings;->getVideoEncoder()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getVideoCache()Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordingListener:Lcom/arcsoft/avatar/recoder/RecordingListener;

    iget v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentScreenOrientation:I

    iget v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mPreviewWidth:I

    iget v6, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mPreviewHeight:I

    const v7, 0x989680

    const/4 p0, 0x5

    if-ne v0, p0, :cond_0

    const-string p0, "video/hevc"

    goto :goto_0

    :cond_0
    const-string p0, "video/avc"

    :goto_0
    move-object v8, p0

    invoke-virtual/range {v1 .. v8}, Lcom/arcsoft/avatar/RecordModule;->startRecording(Ljava/lang/String;Lcom/arcsoft/avatar/recoder/RecordingListener;IIIILjava/lang/String;)V

    :cond_1
    return-void
.end method

.method public synthetic b(IIIIZ)V
    .locals 9

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "avatar start init | "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->queryAvatar()Lcom/arcsoft/avatar/AvatarEngine;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-nez p1, :cond_0

    new-instance p1, Lcom/arcsoft/avatar/RecordModule;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCaptureCallback:Lcom/arcsoft/avatar/RecordModule$MediaResultCallback;

    invoke-direct {p1, v1, v2}, Lcom/arcsoft/avatar/RecordModule;-><init>(Landroid/content/Context;Lcom/arcsoft/avatar/RecordModule$MediaResultCallback;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-object v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-object v7, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    move v4, p2

    move v5, p3

    move v6, p4

    move v8, p5

    invoke-virtual/range {v3 .. v8}, Lcom/arcsoft/avatar/RecordModule;->init(IIILcom/arcsoft/avatar/AvatarEngine;Z)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p1, p2}, Lcom/arcsoft/avatar/RecordModule;->setmImageOrientation(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p1, p5}, Lcom/arcsoft/avatar/RecordModule;->setMirror(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p1, p3, p4}, Lcom/arcsoft/avatar/RecordModule;->setPreviewSize(II)V

    :goto_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    invoke-static {p1}, Lcom/android/camera/Util;->getPreviewRect(Landroid/content/Context;)Landroid/graphics/Rect;

    move-result-object p1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result p2

    iget-object p3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p3, p2}, Lcom/arcsoft/avatar/RecordModule;->setBackgroundToSquare(Z)V

    const/4 p3, 0x0

    if-eqz p2, :cond_1

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    sget p4, Lcom/android/camera/Util;->sWindowHeight:I

    iget p5, p1, Landroid/graphics/Rect;->bottom:I

    sub-int/2addr p4, p5

    sget p5, Lcom/android/camera/Util;->sWindowHeight:I

    div-int/lit8 p5, p5, 0x4

    sub-int/2addr p4, p5

    const/16 p5, 0x1f4

    invoke-virtual {p2, p3, p4, p5, p5}, Lcom/arcsoft/avatar/RecordModule;->setDrawScope(IIII)V

    goto :goto_1

    :cond_1
    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    sget p4, Lcom/android/camera/Util;->sWindowHeight:I

    iget p5, p1, Landroid/graphics/Rect;->bottom:I

    sub-int/2addr p4, p5

    iget p5, p1, Landroid/graphics/Rect;->right:I

    iget v1, p1, Landroid/graphics/Rect;->bottom:I

    iget v2, p1, Landroid/graphics/Rect;->top:I

    sub-int/2addr v1, v2

    invoke-virtual {p2, p3, p4, p5, v1}, Lcom/arcsoft/avatar/RecordModule;->setDrawScope(IIII)V

    :goto_1
    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    sget-object p4, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->BgTemplatePath:Ljava/lang/String;

    invoke-virtual {p2, p4}, Lcom/arcsoft/avatar/RecordModule;->getBackgroundBmpInfo(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object p2

    if-eqz p2, :cond_2

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p4

    invoke-virtual {p4, p2}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->setBackgroundInfos(Ljava/util/ArrayList;)V

    :cond_2
    new-instance p2, Landroid/util/Size;

    iget p4, p1, Landroid/graphics/Rect;->right:I

    iget p5, p1, Landroid/graphics/Rect;->bottom:I

    iget p1, p1, Landroid/graphics/Rect;->top:I

    sub-int/2addr p5, p1

    invoke-direct {p2, p4, p5}, Landroid/util/Size;-><init>(II)V

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDrawSize:Landroid/util/Size;

    iput-boolean p3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsStopRender:Z

    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsAvatarInited:Z

    if-nez p1, :cond_3

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string p2, "avatar need really init"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object p2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TRACK_DATA:Ljava/lang/String;

    sget-object p3, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->FACE_MODEL:Ljava/lang/String;

    invoke-virtual {p1, p2, p3}, Lcom/arcsoft/avatar/AvatarEngine;->init(Ljava/lang/String;Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    const/4 p2, 0x1

    const/high16 p3, 0x3f800000    # 1.0f

    invoke-virtual {p1, p2, p3}, Lcom/arcsoft/avatar/AvatarEngine;->setRenderScene(ZF)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    sget-object p2, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->TRACK_DATA:Ljava/lang/String;

    invoke-virtual {p1, p2}, Lcom/arcsoft/avatar/AvatarEngine;->createOutlineEngine(Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->reloadConfig()V

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->onMimojiInitFinish()V

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public backToPreview(ZZ)V
    .locals 5

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiInfo()Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsStopRender:Z

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->onMimojiSelect(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v2, 0xa2

    invoke-virtual {p1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v2

    const/16 v3, 0xac

    invoke-virtual {v2, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v2

    check-cast v2, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v3

    const/16 v4, 0xaf

    invoke-virtual {v3, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v3

    check-cast v3, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->processingMimojiBack()V

    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->reInitTipImage()V

    const/4 p1, -0x1

    invoke-interface {v2, v0, p1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertMimojiFaceDetect(ZI)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getMimojiRecordState()I

    move-result p1

    const/4 v4, 0x1

    if-ne p1, v4, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result p1

    if-eqz p1, :cond_0

    new-array p1, v1, [I

    fill-array-data p1, :array_0

    invoke-interface {v2, v4, p1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->enableMenuItem(Z[I)V

    goto :goto_0

    :cond_0
    const/4 p1, 0x3

    new-array p1, p1, [I

    fill-array-data p1, :array_1

    invoke-interface {v2, v4, p1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->enableMenuItem(Z[I)V

    :goto_0
    if-eqz p2, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getMimojiPanelState()I

    move-result p1

    if-eq p1, v4, :cond_1

    invoke-interface {v3, v4}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showMimojiPanel(I)V

    :cond_1
    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->updateMimojiBottomTipImage()V

    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->hideCenterTipImage()V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    if-eqz p1, :cond_3

    const/high16 p2, 0x3f800000    # 1.0f

    invoke-virtual {p1, v4, p2}, Lcom/arcsoft/avatar/AvatarEngine;->setRenderScene(ZF)V

    :cond_3
    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->setDisableSingleTapUp(Z)V

    return-void

    nop

    :array_0
    .array-data 4
        0xc5
        0xa2
    .end array-data

    :array_1
    .array-data 4
        0xc5
        0xc1
        0xa2
    .end array-data
.end method

.method public changeIsNoFaceResult(Z)Z
    .locals 3

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-eqz p1, :cond_0

    iget p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsNoFaceResult:I

    if-eqz p1, :cond_2

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsNoFaceResult:I

    return v1

    :cond_0
    iget p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsNoFaceResult:I

    const/4 v2, 0x5

    if-le p1, v2, :cond_1

    return v1

    :cond_1
    add-int/2addr p1, v1

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsNoFaceResult:I

    :cond_2
    return v0
.end method

.method public changeToSquare(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mSquare:Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mSquare:Z

    invoke-virtual {v0, v1}, Lcom/arcsoft/avatar/RecordModule;->setBackgroundToSquare(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/camera/Util;->getPreviewRect(Landroid/content/Context;)Landroid/graphics/Rect;

    move-result-object v0

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    const/4 p1, 0x0

    sget v1, Lcom/android/camera/Util;->sWindowHeight:I

    iget v0, v0, Landroid/graphics/Rect;->bottom:I

    sub-int v0, v1, v0

    div-int/lit8 v1, v1, 0x4

    sub-int/2addr v0, v1

    const/16 v1, 0x1f4

    invoke-virtual {p0, p1, v0, v1, v1}, Lcom/arcsoft/avatar/RecordModule;->setDrawScope(IIII)V

    :cond_0
    return-void
.end method

.method public checkIsNeedChangBg()V
    .locals 7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->IsLoading()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v0

    const/16 v1, 0xb5

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiBgInfo()Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    move-result-object v1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result v2

    const/4 v3, 0x0

    if-eqz v1, :cond_8

    iget-object v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v4}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result v4

    if-nez v4, :cond_8

    if-nez v2, :cond_8

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    const/4 v4, 0x0

    if-eqz v2, :cond_1

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v2

    invoke-virtual {v2}, Lcom/arcsoft/avatar/BackgroundInfo;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v5

    invoke-virtual {v5}, Lcom/arcsoft/avatar/BackgroundInfo;->getName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_2

    :cond_1
    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->clone()Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    const/high16 v2, 0x3f800000    # 1.0f

    invoke-virtual {v1, v4, v2}, Lcom/arcsoft/avatar/AvatarEngine;->setRenderScene(ZF)V

    :cond_2
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getIsNeedRefresh()Z

    move-result v1

    if-eqz v1, :cond_9

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {v1, v4}, Lcom/arcsoft/avatar/RecordModule;->setBackgroundToSquare(Z)V

    const/4 v1, -0x1

    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    move-result v2

    const v5, 0xd1ef

    const/4 v6, 0x1

    if-eq v2, v5, :cond_4

    const v4, 0x171fa6

    if-eq v2, v4, :cond_3

    goto :goto_0

    :cond_3
    const-string v2, "16x9"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_5

    move v1, v6

    goto :goto_0

    :cond_4
    const-string v2, "4x3"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_5

    move v1, v4

    :cond_5
    :goto_0
    if-eqz v1, :cond_7

    if-eq v1, v6, :cond_6

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v0

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/arcsoft/avatar/BackgroundInfo;->setResolutionMode(I)V

    goto :goto_1

    :cond_6
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v0

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Lcom/arcsoft/avatar/BackgroundInfo;->setResolutionMode(I)V

    goto :goto_1

    :cond_7
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/arcsoft/avatar/BackgroundInfo;->setResolutionMode(I)V

    :goto_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->nextFrame()I

    move-result v0

    :try_start_0
    new-instance v1, Ljava/io/FileInputStream;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v2

    invoke-virtual {v2, v0}, Lcom/arcsoft/avatar/BackgroundInfo;->getBackGroundPath(I)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/FileInputStream;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Landroid/graphics/BitmapFactory;->decodeStream(Ljava/io/InputStream;)Landroid/graphics/Bitmap;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object v2

    invoke-virtual {v1, v0, v2}, Lcom/arcsoft/avatar/RecordModule;->setBackground(Landroid/graphics/Bitmap;Lcom/arcsoft/avatar/BackgroundInfo;)V

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V
    :try_end_0
    .catch Ljava/io/FileNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_2

    :catch_0
    move-exception v0

    iput-object v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "checkIsNeedChangBg  : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/io/FileNotFoundException;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_2

    :cond_8
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    if-eqz v0, :cond_9

    iput-object v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCurrentTempMimojiBgInfo:Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p0, v3, v3}, Lcom/arcsoft/avatar/RecordModule;->setBackground(Landroid/graphics/Bitmap;Lcom/arcsoft/avatar/BackgroundInfo;)V

    :cond_9
    :goto_2
    return-void
.end method

.method public deleteMimojiCache(I)Z
    .locals 2

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mimoji boolean deleteMimojiCache[type] : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x1

    if-eqz p1, :cond_2

    if-eq p1, p0, :cond_1

    const/4 v0, 0x2

    if-eq p1, v0, :cond_0

    goto :goto_0

    :cond_0
    :try_start_0
    sget-object p1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_CACHE_DIR:Ljava/lang/String;

    invoke-static {p1}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    goto :goto_0

    :cond_1
    sget-object p1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_CACHE_DIR:Ljava/lang/String;

    invoke-static {p1}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    goto :goto_0

    :cond_2
    sget-object p1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_CACHE_DIR:Ljava/lang/String;

    invoke-static {p1}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z

    sget-object p1, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->EMOTICON_CACHE_DIR:Ljava/lang/String;

    invoke-static {p1}, Lcom/android/camera/module/impl/component/FileUtils;->deleteFile(Ljava/lang/String;)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :goto_0
    return p0

    :catch_0
    move-exception p0

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mimoji void deleteMimojiCache[] "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x0

    return p0
.end method

.method public getIsNoFaceResult()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsNoFaceResult:I

    const/4 v0, 0x5

    if-gt p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method protected getOperatingMode()I
    .locals 0

    const p0, 0x800b

    return p0
.end method

.method public getTimeValue()Ljava/lang/String;
    .locals 6

    iget-wide v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mTotalRecordingTime:J

    const-wide/16 v2, 0x3e8

    const-wide/16 v4, 0x3a98

    invoke-static/range {v0 .. v5}, Lcom/android/camera/Util;->clamp(JJJ)J

    move-result-wide v0

    const/4 p0, 0x0

    const/4 v2, 0x1

    invoke-static {v0, v1, p0, v2}, Lcom/android/camera/Util;->millisecondToTimeString(JZZ)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public getVideoCache()Ljava/lang/String;
    .locals 0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result p0

    if-eqz p0, :cond_0

    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->GIF_CACHE_FILE:Ljava/lang/String;

    return-object p0

    :cond_0
    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->VIDEO_NORMAL_CACHE_FILE:Ljava/lang/String;

    return-object p0
.end method

.method public initAvatarEngine(IIIIZ)V
    .locals 9

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "initAvatarEngine with parameters : displayOrientation = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", width = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", height = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", isFrontCamera = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDisplayOrientation:I

    iput p3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mPreviewWidth:I

    iput p4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mPreviewHeight:I

    iput-boolean p5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    iput p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mOrientation:I

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->initMimojiResource()V

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v4

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadHandler:Landroid/os/Handler;

    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/a;

    move-object v2, v0

    move-object v3, p0

    move v5, p1

    move v6, p3

    move v7, p4

    move v8, p5

    invoke-direct/range {v2 .. v8}, Lcom/android/camera/features/mimoji2/module/impl/a;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;IIIIZ)V

    invoke-virtual {p2, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public isNeedShowAvatar()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiInfo()Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    if-eqz v1, :cond_0

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    const-string v2, "add_state"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v0, v0, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    const-string v1, "close_state"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isOnCreateMimoji()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result p0

    return p0
.end method

.method public isProcessorReady()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isRecordStopping()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecordStopping:Z

    return p0
.end method

.method public isRecording()Z
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Recording = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecording:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecording:Z

    return p0
.end method

.method public isShowGifOperate()Z
    .locals 0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public onCaptureImage()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mNeedCapture:Z

    invoke-interface {v0}, Lcom/android/camera/AppController;->getCurrentModuleIndex()I

    move-result v0

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->getFlashMode(I)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getMimojiPara()Ljava/util/Map;

    move-result-object v2

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    invoke-static {v2, v0, v1, p0}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiCaptureOrRecord(Ljava/util/Map;Ljava/lang/String;ZZ)V

    :cond_1
    :goto_0
    return-void
.end method

.method public onCreateCapture()Z
    .locals 5

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "onCreateCapture"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mFaceDectectResult:I

    const/4 v1, 0x0

    if-nez v0, :cond_3

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFaceDetectSuccess:Z

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->releaseRender()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object v0

    instance-of v2, v0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    const/4 v3, 0x1

    if-eqz v2, :cond_1

    check-cast v0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->setFaceBeautyLevel(I)V

    new-array v2, v3, [I

    const/16 v4, 0xd

    aput v4, v2, v1

    invoke-virtual {v0, v2}, Lcom/android/camera/module/BaseModule;->updatePreferenceInWorkThread([I)V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa2

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    invoke-interface {v0, v3}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->showOrHideMimojiProgress(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xaf

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz v0, :cond_2

    const/16 v1, 0x13

    const v2, 0x7f0f01ae

    const/4 v4, 0x2

    invoke-interface {v0, v1, v2, v4}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showTips(III)V

    :cond_2
    iput-boolean v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsShutterButtonClick:Z

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->animateCapture()V

    return v3

    :cond_3
    :goto_0
    return v1
.end method

.method public onDeviceRotationChange(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->updateVideoOrientation(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p0, :cond_0

    invoke-interface {p0, p1}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->onDeviceRotationChange(I)V

    :cond_0
    return-void
.end method

.method public onDrawFrame(Landroid/graphics/Rect;IIZ)V
    .locals 10

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-eqz v0, :cond_4

    if-eqz p1, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsStopRender:Z

    if-nez v0, :cond_4

    const/4 v0, 0x0

    if-eqz p4, :cond_0

    invoke-static {v0, v0, p2, p3}, Landroid/opengl/GLES20;->glViewport(IIII)V

    goto :goto_0

    :cond_0
    sget p2, Lcom/android/camera/Util;->sWindowHeight:I

    iget p3, p1, Landroid/graphics/Rect;->bottom:I

    sub-int/2addr p2, p3

    iget p4, p1, Landroid/graphics/Rect;->right:I

    iget p1, p1, Landroid/graphics/Rect;->top:I

    sub-int/2addr p3, p1

    invoke-static {v0, p2, p4, p3}, Landroid/opengl/GLES20;->glViewport(IIII)V

    iget-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mNeedCapture:Z

    if-eqz p1, :cond_2

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string p2, "onCapture start"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p1}, Lcom/arcsoft/avatar/RecordModule;->capture()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-eqz p1, :cond_1

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p1

    check-cast p1, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    const/4 p2, 0x3

    invoke-virtual {p1, p2}, Lcom/android/camera/features/mimoji2/module/MimojiModule;->setCameraStatePublic(I)V

    :cond_1
    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mNeedCapture:Z

    :cond_2
    :goto_0
    const/high16 p1, 0x3f800000    # 1.0f

    const/4 p2, 0x0

    invoke-static {p2, p2, p2, p1}, Landroid/opengl/GLES20;->glClearColor(FFFF)V

    const/16 p1, 0x4000

    invoke-static {p1}, Landroid/opengl/GLES20;->glClear(I)V

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    const/16 v2, 0x5a

    iget-boolean v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    iget v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    const/4 v5, 0x0

    const/4 v6, 0x0

    iget-object v7, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mTextureId:[I

    const/4 v8, 0x0

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isNeedShowAvatar()Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getIsNoFaceResult()Z

    move-result p0

    if-nez p0, :cond_3

    const/4 v0, 0x1

    :cond_3
    move v9, v0

    invoke-virtual/range {v1 .. v9}, Lcom/arcsoft/avatar/RecordModule;->startRender(IZIIZ[I[BZ)V

    :cond_4
    return-void
.end method

.method public onMimojiChangeBg(Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;)V
    .locals 2

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setCurrentMimojiBgInfo(Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;)V

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, " onMimojiChangeBg  : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    if-nez p1, :cond_0

    const-string p1, "null"

    goto :goto_0

    :cond_0
    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->getBackgroundInfo()Lcom/arcsoft/avatar/BackgroundInfo;

    move-result-object p1

    invoke-virtual {p1}, Lcom/arcsoft/avatar/BackgroundInfo;->getName()Ljava/lang/String;

    move-result-object p1

    :goto_0
    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onMimojiChangeTimbre(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;)V
    .locals 2

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setCurrentMimojiTimbreInfo(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;)V

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mimoji void onMimojiChangeTimbre[mimojiTimbreInfo]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onMimojiCreate()V
    .locals 3

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "start create mimoji"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setMode(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->mimojiStart()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xd4

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->prepareCreateMimoji()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xaf

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz p0, :cond_0

    const/16 v0, 0x13

    const v1, 0x7f0f0179

    const/4 v2, 0x2

    invoke-interface {p0, v0, v1, v2}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showTips(III)V

    :cond_0
    return-void
.end method

.method public onMimojiDeleted()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setCurrentMimojiInfo(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {p0}, Lcom/arcsoft/avatar/RecordModule;->resetExtraScene()V

    return-void
.end method

.method public onMimojiInitFinish()V
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "onMimojiInitFinish"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

    invoke-virtual {v0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->requestRender()V

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->setIsAvatarInited(Z)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v0, p0}, Lcom/arcsoft/avatar/RecordModule;->updateAvatarConfigInfo(Lcom/arcsoft/avatar/AvatarEngine;)V

    return-void
.end method

.method public onMimojiSelect(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)V
    .locals 4

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "mimoji void onMimojiSelect[mimojiInfo2]"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz p1, :cond_4

    iget-object v0, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_4

    iget-object v0, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    const-string v1, "add_state"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_4

    iget-object v0, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    const-string v1, "close_state"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setCurrentMimojiInfo(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)V

    iget-object v0, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    iget-object p1, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    sget-object v1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "change mimoji with path = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, ", and config = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    monitor-enter v1

    :try_start_0
    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {v2}, Lcom/arcsoft/avatar/RecordModule;->resetExtraScene()V

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-virtual {p1}, Ljava/lang/String;->isEmpty()Z

    move-result v3

    if-nez v3, :cond_2

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->isPrefabModel(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_2

    if-nez v2, :cond_1

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    invoke-virtual {v2, v0, p1}, Lcom/arcsoft/avatar/RecordModule;->changeHumanTemplate(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {v0, p1}, Lcom/arcsoft/avatar/AvatarEngine;->loadConfig(Ljava/lang/String;)V

    goto :goto_0

    :cond_2
    if-nez v2, :cond_3

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {p1, v0}, Lcom/arcsoft/avatar/AvatarEngine;->setTemplatePath(Ljava/lang/String;)V

    :cond_3
    :goto_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    invoke-virtual {p1, p0}, Lcom/arcsoft/avatar/RecordModule;->updateAvatarConfigInfo(Lcom/arcsoft/avatar/AvatarEngine;)V

    monitor-exit v1

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0

    :cond_4
    :goto_1
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setCurrentMimojiInfo(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xf8

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;

    if-eqz p0, :cond_5

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;->refreshMimojiList()I

    :cond_5
    return-void
.end method

.method public onPreviewFrame(Landroid/media/Image;Lcom/android/camera2/Camera2Proxy;I)Z
    .locals 8

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    const/4 p3, 0x1

    if-nez p2, :cond_0

    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string p1, "MimojiAvatarEngine2Impl onPreviewFrame mRecordModule null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return p3

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p2

    const/16 v0, 0xf7

    invoke-virtual {p2, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p2

    check-cast p2, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiEdit()Z

    move-result p2

    const/4 v0, 0x0

    if-eqz p2, :cond_3

    iget-boolean p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsAvatarInited:Z

    if-eqz p2, :cond_1

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    if-nez p2, :cond_3

    :cond_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p1, :cond_2

    invoke-interface {p1, v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->resetClickEnable(Z)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    invoke-interface {p0, p3}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->requestRender(Z)V

    :cond_2
    sget-object p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string p1, "MimojiAvatarEngine2Impl onPreviewFrame need init, waiting......"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return p3

    :cond_3
    invoke-static {p1}, Lcom/arcsoft/avatar/util/AsvloffscreenUtil;->buildNV21SingleBuffer(Landroid/media/Image;)Lcom/arcsoft/avatar/util/ASVLOFFSCREEN;

    move-result-object p2

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsShutterButtonClick:Z

    if-eqz v1, :cond_5

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsShutterButtonClick:Z

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->setIsAvatarInited(Z)V

    invoke-virtual {p1}, Landroid/media/Image;->getPlanes()[Landroid/media/Image$Plane;

    move-result-object v1

    aget-object v1, v1, v0

    invoke-virtual {v1}, Landroid/media/Image$Plane;->getRowStride()I

    move-result v1

    invoke-virtual {p2}, Lcom/arcsoft/avatar/util/ASVLOFFSCREEN;->getYData()[B

    move-result-object v2

    invoke-virtual {p1}, Landroid/media/Image;->getWidth()I

    move-result v3

    invoke-virtual {p1}, Landroid/media/Image;->getHeight()I

    move-result p1

    invoke-static {v2, v3, p1, v1}, Lcom/android/camera/features/mimoji2/utils/BitmapUtils2;->rawByteArray2RGBABitmap([BIII)Landroid/graphics/Bitmap;

    move-result-object p1

    iget-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    if-eqz v1, :cond_4

    const/16 v1, -0x5a

    goto :goto_0

    :cond_4
    const/16 v1, 0x5a

    :goto_0
    invoke-static {p1, v1}, Lcom/android/camera/features/mimoji2/utils/BitmapUtils2;->rotateBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object p1

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v1

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v2

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getRowBytes()I

    move-result v3

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    mul-int/2addr v3, v4

    invoke-static {v3}, Ljava/nio/ByteBuffer;->allocate(I)Ljava/nio/ByteBuffer;

    move-result-object v3

    invoke-static {}, Ljava/nio/ByteOrder;->nativeOrder()Ljava/nio/ByteOrder;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/nio/ByteBuffer;->order(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;

    move-result-object v3

    invoke-virtual {p1, v3}, Landroid/graphics/Bitmap;->copyPixelsToBuffer(Ljava/nio/Buffer;)V

    invoke-virtual {v3}, Ljava/nio/ByteBuffer;->array()[B

    move-result-object p1

    invoke-direct {p0, p1, v1, v2}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->createAvatar([BII)V

    :cond_5
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiEdit()Z

    move-result p1

    if-eqz p1, :cond_6

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p1, :cond_a

    new-instance v6, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProcessInfo;

    invoke-direct {v6}, Lcom/arcsoft/avatar/AvatarConfig$ASAvatarProcessInfo;-><init>()V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    monitor-enter p1

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    const/16 v3, 0x5a

    iget-boolean v4, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    iget v5, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mOrientation:I

    const/4 v7, 0x1

    move-object v2, p2

    invoke-virtual/range {v1 .. v7}, Lcom/arcsoft/avatar/AvatarEngine;->avatarProcessWithInfoEx(Lcom/arcsoft/avatar/util/ASVLOFFSCREEN;IZILcom/arcsoft/avatar/AvatarConfig$ASAvatarProcessInfo;Z)I

    monitor-exit p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    invoke-interface {p1, v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->requestRender(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    invoke-interface {p1, p3}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->resetClickEnable(Z)V

    goto :goto_1

    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit p1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0

    :cond_6
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p1, :cond_7

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiEmoticon()Z

    move-result p1

    if-nez p1, :cond_7

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    invoke-interface {p1, p3}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->requestRender(Z)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    invoke-interface {p1, v0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->resetClickEnable(Z)V

    :cond_7
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    monitor-enter p1

    :try_start_2
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->checkIsNeedChangBg()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mOrientation:I

    iget v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    iget-boolean v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    invoke-static {v1, v2, v3}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->getOutlineOrientation(IIZ)I

    move-result v1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->isNeedShowAvatar()Z

    move-result v2

    invoke-virtual {v0, p2, v1, v2}, Lcom/arcsoft/avatar/RecordModule;->startProcess(Lcom/arcsoft/avatar/util/ASVLOFFSCREEN;IZ)Z

    move-result v0

    monitor-exit p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->changeIsNoFaceResult(Z)Z

    move-result p1

    if-eqz p1, :cond_8

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result p1

    if-nez p1, :cond_8

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mUiHandler:Landroid/os/Handler;

    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$6;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$6;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    invoke-virtual {p1, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_8
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getIsNoFaceResult()Z

    move-result p1

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLastNeedBeauty:Z

    if-eq v0, p1, :cond_9

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLastNeedBeauty:Z

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->updateBeauty()V

    :cond_9
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

    invoke-virtual {p1}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->requestRender()V

    :cond_a
    :goto_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result p1

    if-eqz p1, :cond_b

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarLock:Ljava/lang/Object;

    monitor-enter p1

    :try_start_3
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatar:Lcom/arcsoft/avatar/AvatarEngine;

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mOrientation:I

    iget v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mDeviceRotation:I

    iget-boolean v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    invoke-static {v1, v2, v3}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->getOutlineOrientation(IIZ)I

    move-result v1

    invoke-virtual {v0, p2, v1}, Lcom/arcsoft/avatar/AvatarEngine;->outlineProcessEx(Lcom/arcsoft/avatar/util/ASVLOFFSCREEN;I)I

    move-result p2

    iput p2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mFaceDectectResult:I

    monitor-exit p1
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz p1, :cond_b

    iget p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mFaceDectectResult:I

    invoke-interface {p1, p0}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->mimojiFaceDetect(I)V

    goto :goto_2

    :catchall_1
    move-exception p0

    :try_start_4
    monitor-exit p1
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    throw p0

    :cond_b
    :goto_2
    return p3

    :catchall_2
    move-exception p0

    :try_start_5
    monitor-exit p1
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    throw p0
.end method

.method public onRecordStart()V
    .locals 4

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "start record..."

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecording:Z

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-interface {v0}, Lcom/android/camera/AppController;->getCurrentModuleIndex()I

    move-result v0

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->getFlashMode(I)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->getMimojiPara()Ljava/util/Map;

    move-result-object v1

    iget-boolean v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFrontCamera:Z

    const/4 v3, 0x0

    invoke-static {v1, v0, v3, v2}, Lcom/android/camera/statistic/CameraStatUtils;->trackMimojiCaptureOrRecord(Ljava/util/Map;Ljava/lang/String;ZZ)V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecording:Z

    iput-boolean v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecordStopping:Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

    new-instance v1, Lcom/android/camera/features/mimoji2/module/impl/g;

    invoke-direct {v1, p0}, Lcom/android/camera/features/mimoji2/module/impl/g;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    invoke-virtual {v0, v1}, Landroid/opengl/GLSurfaceView;->queueEvent(Ljava/lang/Runnable;)V

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->updateRecordingTime()V

    :cond_1
    :goto_0
    return-void
.end method

.method public onRecordStop(Z)V
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "stop record..."

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsRecordStopping:Z

    if-eqz p1, :cond_0

    new-instance p1, Ljava/util/concurrent/CountDownLatch;

    invoke-direct {p1, v0}, Ljava/util/concurrent/CountDownLatch;-><init>(I)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mGetThumCountDownLatch:Ljava/util/concurrent/CountDownLatch;

    :cond_0
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCountDownTimer:Landroid/os/CountDownTimer;

    if-eqz p1, :cond_1

    invoke-virtual {p1}, Landroid/os/CountDownTimer;->cancel()V

    :cond_1
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;

    invoke-direct {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$4;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;)V

    invoke-virtual {p1, v0}, Landroid/opengl/GLSurfaceView;->queueEvent(Ljava/lang/Runnable;)V

    return-void
.end method

.method public onResume()V
    .locals 2

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "reset"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mRecordModule:Lcom/arcsoft/avatar/RecordModule;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/arcsoft/avatar/RecordModule;->reset()V

    :cond_0
    return-void
.end method

.method public synthetic r(Ljava/lang/String;)V
    .locals 6

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    const v2, 0x8000

    :try_start_0
    iget-object v3, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mContext:Landroid/content/Context;

    const-string v4, "vendor/camera/mimoji/model2.zip"

    sget-object v5, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->MIMOJI_DIR:Ljava/lang/String;

    invoke-static {v3, v4, v5, v2}, Lcom/android/camera/Util;->verifyFileZip(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;I)V

    sget-object v2, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "init model spend time = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    sub-long/2addr v4, v0

    invoke-virtual {v3, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setIsLoading(Z)V

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->setMimojiModleVersion(Ljava/lang/String;)V

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mAvatarTemplatePath = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mUiHandler:Landroid/os/Handler;

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/b;->INSTANCE:Lcom/android/camera/features/mimoji2/module/impl/b;

    invoke-virtual {p0, p1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    sget-object p1, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v0, "verify asset model zip failed..."

    invoke-static {p1, v0, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    sget-object p0, Lcom/android/camera/features/mimoji2/widget/helper/MimojiHelper2;->MIMOJI_DIR:Ljava/lang/String;

    invoke-static {p0}, Lcom/android/camera/module/impl/component/FileUtils;->delDir(Ljava/lang/String;)Z

    :goto_0
    return-void
.end method

.method public registerProtocol()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf6

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public release()V
    .locals 4

    sget-object v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v1, "avatar release"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->setCurrentMimojiBgInfo(Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;)V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mGetThumCountDownLatch:Ljava/util/concurrent/CountDownLatch;

    if-eqz v0, :cond_0

    :try_start_0
    invoke-virtual {v0}, Ljava/util/concurrent/CountDownLatch;->await()V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    :cond_0
    :goto_0
    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v0

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mLoadHandler:Landroid/os/Handler;

    new-instance v3, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;

    invoke-direct {v3, p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$2;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;I)V

    invoke-virtual {v2, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mVideoFileStream:Ljava/io/FileOutputStream;

    if-eqz v0, :cond_1

    :try_start_1
    invoke-virtual {v0}, Ljava/io/FileOutputStream;->close()V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_1

    goto :goto_1

    :catch_1
    move-exception v0

    sget-object v2, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->TAG:Ljava/lang/String;

    const-string v3, "fail to close file stream"

    invoke-static {v2, v3, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_1
    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mVideoFileStream:Ljava/io/FileOutputStream;

    :cond_1
    iput-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    return-void
.end method

.method public releaseRender()V
    .locals 3

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v0

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsStopRender:Z

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {v1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInPreviewSurface()Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCameraView:Lcom/android/camera/ui/V6CameraGLSurfaceView;

    new-instance v2, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$1;

    invoke-direct {v2, p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$1;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;I)V

    invoke-virtual {v1, v2}, Landroid/opengl/GLSurfaceView;->queueEvent(Ljava/lang/Runnable;)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMimojiEditor2:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;->releaseRender()V

    :cond_1
    :goto_0
    return-void
.end method

.method public setDetectSuccess(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsFaceDetectSuccess:Z

    return-void
.end method

.method public setDisableSingleTapUp(Z)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p0

    check-cast p0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/module/MimojiModule;->setDisableSingleTapUp(Z)V

    return-void
.end method

.method public setIsAvatarInited(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mIsAvatarInited:Z

    return-void
.end method

.method public unRegisterProtocol()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xf6

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->release()V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->quit()V

    return-void
.end method

.method protected updateRecordingTime()V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCountDownTimer:Landroid/os/CountDownTimer;

    if-eqz v0, :cond_0

    const-wide/16 v1, 0x0

    iput-wide v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mTotalRecordingTime:J

    invoke-virtual {v0}, Landroid/os/CountDownTimer;->cancel()V

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result v0

    if-eqz v0, :cond_1

    const/16 v0, 0x1388

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMaxVideoDurationInMs:I

    goto :goto_0

    :cond_1
    const/16 v0, 0x3a98

    iput v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMaxVideoDurationInMs:I

    :goto_0
    new-instance v0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$3;

    iget v1, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMaxVideoDurationInMs:I

    int-to-long v1, v1

    const-wide/16 v3, 0x1c2

    add-long/2addr v3, v1

    const-wide/16 v5, 0x3e8

    move-object v1, v0

    move-object v2, p0

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl$3;-><init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;JJ)V

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCountDownTimer:Landroid/os/CountDownTimer;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mCountDownTimer:Landroid/os/CountDownTimer;

    invoke-virtual {p0}, Landroid/os/CountDownTimer;->start()Landroid/os/CountDownTimer;

    return-void
.end method

.method public synthetic xf()V
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->setDisableSingleTapUp(Z)V

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->onProfileFinish()V

    return-void
.end method

.method public synthetic yf()V
    .locals 3

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xd4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onPostSavingFinish()V

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    const/4 v2, -0x1

    invoke-interface {v0, v1, v2}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertMimojiFaceDetect(ZI)V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->mimojiEnd()V

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->mActivityBase:Lcom/android/camera/ActivityBase;

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/module/MimojiModule;

    invoke-virtual {v0, v1}, Lcom/android/camera/features/mimoji2/module/MimojiModule;->onMimojiCreateCompleted(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xa2

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->showOrHideMimojiProgress(Z)V

    invoke-virtual {p0, v1, v1}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->backToPreview(ZZ)V

    return-void
.end method
