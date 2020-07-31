.class Lcom/android/camera/ui/V6GestureRecognizer$1;
.super Lcom/android/camera/ui/zoom/ScaleGestureDetector;
.source "V6GestureRecognizer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/ui/V6GestureRecognizer;-><init>(Lcom/android/camera/ActivityBase;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/ui/V6GestureRecognizer;


# direct methods
.method constructor <init>(Lcom/android/camera/ui/V6GestureRecognizer;Landroid/content/Context;Lcom/android/camera/ui/zoom/ScaleGestureDetector$OnScaleGestureListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/ui/V6GestureRecognizer$1;->this$0:Lcom/android/camera/ui/V6GestureRecognizer;

    invoke-direct {p0, p2, p3}, Lcom/android/camera/ui/zoom/ScaleGestureDetector;-><init>(Landroid/content/Context;Lcom/android/camera/ui/zoom/ScaleGestureDetector$OnScaleGestureListener;)V

    return-void
.end method


# virtual methods
.method public getScaledMinimumScalingSpan()I
    .locals 0

    const/4 p0, 0x0

    return p0
.end method
