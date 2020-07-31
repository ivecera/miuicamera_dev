.class public interface abstract Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;
.super Ljava/lang/Object;
.source "MimojiModeProtocol.java"

# interfaces
.implements Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x609
    name = "MimojiEmoticon"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xfa


# virtual methods
.method public abstract backToPreview()V
.end method

.method public abstract coverEmoticonError()V
.end method

.method public abstract coverEmoticonSuccess()V
.end method

.method public abstract release()V
.end method

.method public abstract updateEmoticonGifProgress(I)V
.end method

.method public abstract updateEmoticonPictureProgress(Ljava/lang/String;Lcom/arcsoft/avatar/emoticon/EmoInfo;Z)V
.end method

.method public abstract updateEmoticonThumbnailProgress(ILcom/arcsoft/avatar/emoticon/EmoInfo;)V
.end method
