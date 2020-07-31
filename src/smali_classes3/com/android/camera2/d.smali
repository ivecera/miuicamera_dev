.class public final synthetic Lcom/android/camera2/d;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera2/MiCamera2;

.field private final synthetic Li:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera2/MiCamera2;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera2/d;->Hi:Lcom/android/camera2/MiCamera2;

    iput-boolean p2, p0, Lcom/android/camera2/d;->Li:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera2/d;->Hi:Lcom/android/camera2/MiCamera2;

    iget-boolean p0, p0, Lcom/android/camera2/d;->Li:Z

    invoke-virtual {v0, p0}, Lcom/android/camera2/MiCamera2;->y(Z)V

    return-void
.end method
