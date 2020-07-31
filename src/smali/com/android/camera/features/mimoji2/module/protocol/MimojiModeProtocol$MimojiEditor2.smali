.class public interface abstract Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2;
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
    name = "MimojiEditor2"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiEditor2$MimojiEmoticon;
    }
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xf7


# virtual methods
.method public abstract createEmoticonPicture(Ljava/util/List;)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;)V"
        }
    .end annotation
.end method

.method public abstract createEmoticonThumbnail()V
.end method

.method public abstract createEmoticonVideo(Ljava/util/List;)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;)V"
        }
    .end annotation
.end method

.method public abstract directlyEnterEditMode(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;I)V
.end method

.method public abstract goBack(ZZ)V
.end method

.method public abstract onDeviceRotationChange(I)V
.end method

.method public abstract onTypeConfigSelect(I)V
.end method

.method public abstract quitCoverEmoticon()V
.end method

.method public abstract releaseRender()V
.end method

.method public abstract requestRender(Z)V
.end method

.method public abstract resetClickEnable(Z)V
.end method

.method public abstract resetConfig()V
.end method

.method public abstract showEmoticon()V
.end method

.method public abstract startMimojiEdit(I)V
.end method
