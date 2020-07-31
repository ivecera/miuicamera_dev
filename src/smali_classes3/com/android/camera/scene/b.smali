.class public final synthetic Lcom/android/camera/scene/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/scene/ASDResultParse;

.field private final synthetic Li:I

.field private final synthetic Mi:I

.field private final synthetic Ni:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/scene/ASDResultParse;III)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/scene/b;->Hi:Lcom/android/camera/scene/ASDResultParse;

    iput p2, p0, Lcom/android/camera/scene/b;->Li:I

    iput p3, p0, Lcom/android/camera/scene/b;->Mi:I

    iput p4, p0, Lcom/android/camera/scene/b;->Ni:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/scene/b;->Hi:Lcom/android/camera/scene/ASDResultParse;

    iget v1, p0, Lcom/android/camera/scene/b;->Li:I

    iget v2, p0, Lcom/android/camera/scene/b;->Mi:I

    iget p0, p0, Lcom/android/camera/scene/b;->Ni:I

    invoke-virtual {v0, v1, v2, p0}, Lcom/android/camera/scene/ASDResultParse;->a(III)V

    return-void
.end method
