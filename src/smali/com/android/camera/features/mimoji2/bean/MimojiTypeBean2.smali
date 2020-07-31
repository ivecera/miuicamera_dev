.class public Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;
.super Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;
.source "MimojiTypeBean2.java"


# instance fields
.field private mASAvatarConfigType:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/features/mimoji2/widget/autoselectview/SelectItemBean;-><init>()V

    return-void
.end method


# virtual methods
.method public getASAvatarConfigType()Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;->mASAvatarConfigType:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;

    return-object p0
.end method

.method public setASAvatarConfigType(Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/bean/MimojiTypeBean2;->mASAvatarConfigType:Lcom/arcsoft/avatar/AvatarConfig$ASAvatarConfigType;

    return-void
.end method
