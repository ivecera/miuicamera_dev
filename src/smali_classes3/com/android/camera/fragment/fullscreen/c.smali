.class public final synthetic Lcom/android/camera/fragment/fullscreen/c;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera/fragment/fullscreen/FragmentFullScreen$OnFrameUpdatedCallback;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;

.field private final synthetic Li:Lcom/android/camera/protocol/ModeProtocol$LiveVideoEditor;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;Lcom/android/camera/protocol/ModeProtocol$LiveVideoEditor;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/fullscreen/c;->Hi:Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;

    iput-object p2, p0, Lcom/android/camera/fragment/fullscreen/c;->Li:Lcom/android/camera/protocol/ModeProtocol$LiveVideoEditor;

    return-void
.end method


# virtual methods
.method public final onUpdate()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/fullscreen/c;->Hi:Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;

    iget-object p0, p0, Lcom/android/camera/fragment/fullscreen/c;->Li:Lcom/android/camera/protocol/ModeProtocol$LiveVideoEditor;

    invoke-virtual {v0, p0}, Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;->a(Lcom/android/camera/protocol/ModeProtocol$LiveVideoEditor;)V

    return-void
.end method
