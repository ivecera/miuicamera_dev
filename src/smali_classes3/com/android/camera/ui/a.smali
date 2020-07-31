.class public final synthetic Lcom/android/camera/ui/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/ui/MutiStateButton;

.field private final synthetic Li:F

.field private final synthetic Mi:F

.field private final synthetic Ni:F

.field private final synthetic Oi:F


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/MutiStateButton;FFFF)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/a;->Hi:Lcom/android/camera/ui/MutiStateButton;

    iput p2, p0, Lcom/android/camera/ui/a;->Li:F

    iput p3, p0, Lcom/android/camera/ui/a;->Mi:F

    iput p4, p0, Lcom/android/camera/ui/a;->Ni:F

    iput p5, p0, Lcom/android/camera/ui/a;->Oi:F

    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/ui/a;->Hi:Lcom/android/camera/ui/MutiStateButton;

    iget v1, p0, Lcom/android/camera/ui/a;->Li:F

    iget v2, p0, Lcom/android/camera/ui/a;->Mi:F

    iget v3, p0, Lcom/android/camera/ui/a;->Ni:F

    iget v4, p0, Lcom/android/camera/ui/a;->Oi:F

    move-object v5, p1

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/ui/MutiStateButton;->a(FFFFLandroid/animation/ValueAnimator;)V

    return-void
.end method
