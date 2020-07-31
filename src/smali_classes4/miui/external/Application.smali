.class public Lmiui/external/Application;
.super Landroid/app/Application;
.source "Application.java"

# interfaces
.implements Lmiui/external/SdkConstants;


# static fields
.field private static final PACKAGE_NAME:Ljava/lang/String; = "com.miui.core"


# instance fields
.field private mApplicationDelegate:Lmiui/external/ApplicationDelegate;

.field private mInitialized:Z

.field private mStarted:Z


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    invoke-direct {p0}, Lmiui/external/Application;->loadSdk()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-direct {p0}, Lmiui/external/Application;->initializeSdk()Z

    move-result v0

    if-nez v0, :cond_1

    return-void

    :cond_1
    const/4 v0, 0x1

    iput-boolean v0, p0, Lmiui/external/Application;->mInitialized:Z

    return-void
.end method

.method private handleGenericError(Ljava/lang/Throwable;)V
    .locals 1

    :goto_0
    if-eqz p1, :cond_2

    invoke-virtual {p1}, Ljava/lang/Throwable;->getCause()Ljava/lang/Throwable;

    move-result-object p0

    if-nez p0, :cond_0

    goto :goto_1

    :cond_0
    instance-of p0, p1, Ljava/lang/reflect/InvocationTargetException;

    if-eqz p0, :cond_1

    invoke-virtual {p1}, Ljava/lang/Throwable;->getCause()Ljava/lang/Throwable;

    move-result-object p1

    goto :goto_0

    :cond_1
    instance-of p0, p1, Ljava/lang/ExceptionInInitializerError;

    if-eqz p0, :cond_2

    invoke-virtual {p1}, Ljava/lang/Throwable;->getCause()Ljava/lang/Throwable;

    move-result-object p1

    goto :goto_0

    :cond_2
    :goto_1
    const-string p0, "miuisdk"

    const-string v0, "MIUI SDK encounter errors, please contact miuisdk@xiaomi.com for support."

    invoke-static {p0, v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    sget-object p0, Lmiui/external/SdkConstants$SdkError;->GENERIC:Lmiui/external/SdkConstants$SdkError;

    invoke-static {p0}, Lmiui/external/SdkErrorInstrumentation;->handleSdkError(Lmiui/external/SdkConstants$SdkError;)V

    return-void
.end method

.method private handleUnknownError(Ljava/lang/String;I)V
    .locals 1

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "MIUI SDK encounter errors, please contact miuisdk@xiaomi.com for support. phase: "

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, " code: "

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "miuisdk"

    invoke-static {p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    sget-object p0, Lmiui/external/SdkConstants$SdkError;->GENERIC:Lmiui/external/SdkConstants$SdkError;

    invoke-static {p0}, Lmiui/external/SdkErrorInstrumentation;->handleSdkError(Lmiui/external/SdkConstants$SdkError;)V

    return-void
.end method

.method private initializeSdk()Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method private loadSdk()Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method private startSdk()Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method


# virtual methods
.method protected attachBaseContext(Landroid/content/Context;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V

    iget-boolean p1, p0, Lmiui/external/Application;->mInitialized:Z

    if-nez p1, :cond_0

    return-void

    :cond_0
    invoke-direct {p0}, Lmiui/external/Application;->startSdk()Z

    move-result p1

    if-nez p1, :cond_1

    return-void

    :cond_1
    invoke-virtual {p0}, Lmiui/external/Application;->onCreateApplicationDelegate()Lmiui/external/ApplicationDelegate;

    move-result-object p1

    iput-object p1, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    iget-object p1, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    if-eqz p1, :cond_2

    invoke-virtual {p1, p0}, Lmiui/external/ApplicationDelegate;->attach(Lmiui/external/Application;)V

    :cond_2
    const/4 p1, 0x1

    iput-boolean p1, p0, Lmiui/external/Application;->mStarted:Z

    return-void
.end method

.method public final getApplicationDelegate()Lmiui/external/ApplicationDelegate;
    .locals 0

    iget-object p0, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    return-object p0
.end method

.method public final onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 1

    iget-object v0, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Lmiui/external/ApplicationDelegate;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1}, Lmiui/external/Application;->superOnConfigurationChanged(Landroid/content/res/Configuration;)V

    :goto_0
    return-void
.end method

.method public final onCreate()V
    .locals 1

    iget-boolean v0, p0, Lmiui/external/Application;->mStarted:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Lmiui/external/ApplicationDelegate;->onCreate()V

    goto :goto_0

    :cond_1
    invoke-virtual {p0}, Lmiui/external/Application;->superOnCreate()V

    :goto_0
    return-void
.end method

.method public onCreateApplicationDelegate()Lmiui/external/ApplicationDelegate;
    .locals 0

    const/4 p0, 0x0

    return-object p0
.end method

.method public final onLowMemory()V
    .locals 1

    iget-object v0, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lmiui/external/ApplicationDelegate;->onLowMemory()V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lmiui/external/Application;->superOnLowMemory()V

    :goto_0
    return-void
.end method

.method public final onTerminate()V
    .locals 1

    iget-object v0, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lmiui/external/ApplicationDelegate;->onTerminate()V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lmiui/external/Application;->superOnTerminate()V

    :goto_0
    return-void
.end method

.method public final onTrimMemory(I)V
    .locals 1

    iget-object v0, p0, Lmiui/external/Application;->mApplicationDelegate:Lmiui/external/ApplicationDelegate;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Lmiui/external/ApplicationDelegate;->onTrimMemory(I)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1}, Lmiui/external/Application;->superOnTrimMemory(I)V

    :goto_0
    return-void
.end method

.method final superOnConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/Application;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    return-void
.end method

.method final superOnCreate()V
    .locals 0

    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    return-void
.end method

.method final superOnLowMemory()V
    .locals 0

    invoke-super {p0}, Landroid/app/Application;->onLowMemory()V

    return-void
.end method

.method final superOnTerminate()V
    .locals 0

    invoke-super {p0}, Landroid/app/Application;->onTerminate()V

    return-void
.end method

.method final superOnTrimMemory(I)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/Application;->onTrimMemory(I)V

    return-void
.end method
