.class public interface abstract Lcom/android/camera/protocol/ModeProtocol$AIWatermarkDetect;
.super Ljava/lang/Object;
.source "ModeProtocol.java"

# interfaces
.implements Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/protocol/ModeProtocol;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x609
    name = "AIWatermarkDetect"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xfe


# virtual methods
.method public abstract onASDChange(I)V
.end method

.method public abstract resetResult()V
.end method

.method public abstract setListener(Lcom/android/camera/aiwatermark/lisenter/IASDListener;)V
.end method
