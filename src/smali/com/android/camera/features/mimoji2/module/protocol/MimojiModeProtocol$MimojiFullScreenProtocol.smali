.class public interface abstract Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiFullScreenProtocol;
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
    name = "MimojiFullScreenProtocol"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xf9


# virtual methods
.method public abstract concatResult(Ljava/lang/String;)V
.end method

.method public abstract getMimojiVideoSavePath()Ljava/lang/String;
.end method

.method public abstract isMimojiRecordPreviewShowing()Z
.end method

.method public abstract onCombineError()V
.end method

.method public abstract onCombineSuccess()V
.end method

.method public abstract onMimojiSaveToLocalFinished(Landroid/net/Uri;)V
.end method

.method public abstract setPreviewCover(Landroid/graphics/Bitmap;)V
.end method

.method public abstract startMimojiRecordPreview()V
.end method

.method public abstract startMimojiRecordSaving()V
.end method
