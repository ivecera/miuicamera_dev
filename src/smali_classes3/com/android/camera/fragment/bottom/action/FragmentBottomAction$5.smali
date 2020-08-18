.class Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;
.super Landroid/animation/AnimatorListenerAdapter;
.source "FragmentBottomAction.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->processingPause()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationEnd(Landroid/animation/Animator;)V
    .locals 2

    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {p1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$200(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Lcom/android/camera/ui/CameraSnapView;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/ui/CameraSnapView;->addSegmentNow()V

    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {p1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$100(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Lcom/android/camera/ui/ModeSelectView;

    move-result-object p1

    const/16 v0, 0x8

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setVisibility(I)V

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {v1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$300(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Lcom/android/camera/fragment/bottom/BottomActionMenu;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/fragment/bottom/BottomActionMenu;->getView()Landroid/view/View;

    move-result-object v1

    invoke-direct {p1, v1}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    invoke-virtual {p1}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {p1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$400(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Landroid/widget/ImageView;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/ImageView;->getVisibility()I

    move-result p1

    iget-object v1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {v1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$400(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Landroid/widget/ImageView;

    move-result-object v1

    if-nez p1, :cond_0

    move p1, v0

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    invoke-virtual {v1, p1}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {p1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$500(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)I

    move-result p1

    const/16 v1, 0xb7

    if-ne p1, v1, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->c_36658_0x0002_OR_c_36658_0x0001_IF_india()Z

    move-result p1

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {p1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$600(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Landroid/widget/ImageView;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction$5;->this$0:Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;

    invoke-static {p1}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->access$700(Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;)Landroid/animation/ValueAnimator;

    move-result-object p1

    invoke-virtual {p1, p0}, Landroid/animation/ValueAnimator;->removeListener(Landroid/animation/Animator$AnimatorListener;)V

    return-void
.end method
