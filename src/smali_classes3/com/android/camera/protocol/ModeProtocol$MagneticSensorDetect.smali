.class public interface abstract Lcom/android/camera/protocol/ModeProtocol$MagneticSensorDetect;
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
    name = "MagneticSensorDetect"
.end annotation


# static fields
.field public static final TYPE_TAG:I = 0xa10


# virtual methods
.method public abstract isLockHDRChecker()Z
.end method

.method public abstract onMagneticSensorChanged(Landroid/hardware/SensorEvent;)V
.end method

.method public abstract recordMagneticInfo()V
.end method

.method public abstract resetMagneticInfo()V
.end method

.method public abstract updateMagneticDetection()V
.end method

.method public abstract updatePreview(Landroid/hardware/camera2/CaptureResult;)V
.end method
