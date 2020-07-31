.class Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;
.super Ljava/lang/Object;
.source "CameraZoomAnimateDrawable.java"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->startTouchDownAnimation(Landroid/graphics/Point;Lcom/android/camera/ui/ZoomView$zoomValueChangeListener;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

.field final synthetic val$listener:Lcom/android/camera/ui/ZoomView$zoomValueChangeListener;


# direct methods
.method constructor <init>(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;Lcom/android/camera/ui/ZoomView$zoomValueChangeListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    iput-object p2, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->val$listener:Lcom/android/camera/ui/ZoomView$zoomValueChangeListener;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 3

    iget-object p1, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {p1}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$100(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)F

    move-result v0

    invoke-static {p1, v0}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$016(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;F)F

    iget-object p1, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {p1}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$000(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)F

    move-result v0

    iget-object v1, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {v1}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$200(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)F

    move-result v1

    iget-object v2, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {v2}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$300(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)F

    move-result v2

    invoke-static {v0, v1, v2}, Lcom/android/camera/Util;->clamp(FFF)F

    move-result v0

    invoke-static {p1, v0}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$002(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;F)F

    iget-object p1, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {p1}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$400(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)Lcom/android/camera/ui/drawable/zoom/CameraZoomAdjustPaint;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {v0}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$000(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)F

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAdjustPaint;->setCurrentZoomRatio(F)V

    iget-object p1, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->val$listener:Lcom/android/camera/ui/ZoomView$zoomValueChangeListener;

    if-eqz p1, :cond_0

    iget-object v0, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-static {v0}, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;->access$000(Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;)F

    move-result v0

    invoke-interface {p1, v0}, Lcom/android/camera/ui/ZoomView$zoomValueChangeListener;->onZoomValueChanged(F)V

    :cond_0
    iget-object p0, p0, Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable$1;->this$0:Lcom/android/camera/ui/drawable/zoom/CameraZoomAnimateDrawable;

    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->invalidateSelf()V

    return-void
.end method
