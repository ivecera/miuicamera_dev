.class public Lcom/android/camera/GeneralUtils;
.super Ljava/lang/Object;
.source "GeneralUtils.java"


# static fields
.field public static final STREET_SNAP_CHANNEL_ID:Ljava/lang/String; = "com.android.camera.streetsnap"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static applyNotchFlag(Landroid/view/Window;)V
    .locals 4

    const-class v0, Landroid/view/Window;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/16 v2, 0x700

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/4 v3, 0x0

    aput-object v2, v1, v3

    const-string v2, "addExtraFlags"

    const-string v3, "(I)V"

    invoke-static {v0, p0, v2, v3, v1}, Lmiui/reflect/d;->a(Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public static clearInterpolator(Landroid/view/View;)V
    .locals 0

    return-void
.end method

.method public static editModeTitleLayout()I
    .locals 1

    const v0, 0x7f0b0009

    return v0
.end method

.method public static final isAppLocked(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public static miuiResArrayField(Ljava/lang/String;Ljava/lang/String;)Lmiui/reflect/b;
    .locals 1

    const-class v0, Lcom/android/internal/R$array;

    invoke-static {v0, p0, p1}, Lmiui/reflect/b;->a(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)Lmiui/reflect/b;

    move-result-object p0

    return-object p0
.end method

.method public static miuiResBoolField(Ljava/lang/String;Ljava/lang/String;)Lmiui/reflect/b;
    .locals 1

    const-class v0, Lcom/android/internal/R$bool;

    invoke-static {v0, p0, p1}, Lmiui/reflect/b;->a(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)Lmiui/reflect/b;

    move-result-object p0

    return-object p0
.end method

.method public static miuiWidgetButtonDialog()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public static notifyForDetail(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Z)V
    .locals 2

    :try_start_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "android.intent.action.VIEW"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    if-eqz p4, :cond_0

    const-string p4, "video/*"

    goto :goto_0

    :cond_0
    const-string p4, "image/*"

    :goto_0
    invoke-virtual {v0, p1, p4}, Landroid/content/Intent;->setDataAndType(Landroid/net/Uri;Ljava/lang/String;)Landroid/content/Intent;

    const/4 p1, 0x0

    invoke-static {p0, p1, v0, p1}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object p4

    new-instance v0, Landroid/app/Notification$Builder;

    invoke-direct {v0, p0}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, p2}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object p2

    invoke-virtual {p2, p3}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object p2

    invoke-virtual {p2, p4}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    move-result-object p2

    const p3, 0x1080041

    invoke-virtual {p2, p3}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object p2

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide p3

    invoke-virtual {p2, p3, p4}, Landroid/app/Notification$Builder;->setWhen(J)Landroid/app/Notification$Builder;

    move-result-object p2

    const-string p3, "notification"

    invoke-virtual {p0, p3}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Landroid/app/NotificationManager;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const p4, 0x7f0f007e

    invoke-virtual {p0, p4}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p0

    const-string p4, "com.android.camera.streetsnap"

    invoke-static {p3, p4, p0, p2}, Lcom/android/camera/lib/compatibility/util/CompatibilityUtils;->addChannelForNotificationBuilder(Landroid/app/NotificationManager;Ljava/lang/String;Ljava/lang/CharSequence;Landroid/app/Notification$Builder;)V

    invoke-virtual {p2}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    move-result-object p0

    iget p2, p0, Landroid/app/Notification;->flags:I

    or-int/lit8 p2, p2, 0x10

    iput p2, p0, Landroid/app/Notification;->flags:I

    invoke-virtual {p3, p1, p0}, Landroid/app/NotificationManager;->notify(ILandroid/app/Notification;)V
    :try_end_0
    .catch Ljava/lang/NullPointerException; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    return-void
.end method
