.class public interface abstract Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiAvatarEngine2;
.super Ljava/lang/Object;
.source "MimojiModeProtocol.java"

# interfaces
.implements Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x609
    name = "MimojiAvatarEngine2"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xf6


# virtual methods
.method public abstract backToPreview(ZZ)V
.end method

.method public abstract changeToSquare(Z)V
.end method

.method public abstract deleteMimojiCache(I)Z
.end method

.method public abstract getTimeValue()Ljava/lang/String;
.end method

.method public abstract getVideoCache()Ljava/lang/String;
.end method

.method public abstract initAvatarEngine(IIIIZ)V
.end method

.method public abstract isOnCreateMimoji()Z
.end method

.method public abstract isRecordStopping()Z
.end method

.method public abstract isRecording()Z
.end method

.method public abstract onCaptureImage()V
.end method

.method public abstract onCreateCapture()Z
.end method

.method public abstract onDeviceRotationChange(I)V
.end method

.method public abstract onDrawFrame(Landroid/graphics/Rect;IIZ)V
.end method

.method public abstract onMimojiChangeBg(Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;)V
.end method

.method public abstract onMimojiChangeTimbre(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;)V
.end method

.method public abstract onMimojiCreate()V
.end method

.method public abstract onMimojiDeleted()V
.end method

.method public abstract onMimojiInitFinish()V
.end method

.method public abstract onMimojiSelect(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;)V
.end method

.method public abstract onRecordStart()V
.end method

.method public abstract onRecordStop(Z)V
.end method

.method public abstract onResume()V
.end method

.method public abstract release()V
.end method

.method public abstract releaseRender()V
.end method

.method public abstract setDetectSuccess(Z)V
.end method

.method public abstract setDisableSingleTapUp(Z)V
.end method
