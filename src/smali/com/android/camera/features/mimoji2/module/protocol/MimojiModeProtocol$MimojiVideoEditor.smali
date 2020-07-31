.class public interface abstract Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiVideoEditor;
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
    name = "MimojiVideoEditor"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xfc


# virtual methods
.method public abstract changeTimbre()V
.end method

.method public abstract combineVideoAudio(Ljava/lang/String;)V
.end method

.method public abstract init(Lcom/android/camera/ui/TextureVideoView;Ljava/lang/String;)Z
.end method

.method public abstract isPlaying()Z
.end method

.method public abstract onDestory()V
.end method

.method public abstract pausePlay()Z
.end method

.method public abstract resumePlay()Z
.end method

.method public abstract setRecordParameter(III)V
.end method

.method public abstract startPlay()V
.end method

.method public abstract video2gif(Ljava/util/List;)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar/emoticon/EmoInfo;",
            ">;)V"
        }
    .end annotation
.end method
