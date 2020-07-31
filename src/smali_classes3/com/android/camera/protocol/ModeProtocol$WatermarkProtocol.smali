.class public interface abstract Lcom/android/camera/protocol/ModeProtocol$WatermarkProtocol;
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
    name = "WatermarkProtocol"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/protocol/ModeProtocol$WatermarkProtocol$DismissType;
    }
.end annotation


# static fields
.field public static final DISMISS_ALPHA:I = 0x3

.field public static final DISMISS_SILENT:I = 0x1

.field public static final DISMISS_SLIDE:I = 0x2

.field public static final KEEP:I = 0x0

.field public static final TYPE_TAG:I = 0xfd


# virtual methods
.method public abstract dismiss(I)Z
.end method

.method public abstract isWatermarkPanelShow()Z
.end method

.method public abstract show()V
.end method

.method public abstract switchType(Ljava/lang/String;Z)V
.end method
