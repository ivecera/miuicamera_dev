.class public final synthetic Lcom/android/camera/fragment/aiwatermark/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/fragment/aiwatermark/b;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/fragment/aiwatermark/b;

    invoke-direct {v0}, Lcom/android/camera/fragment/aiwatermark/b;-><init>()V

    sput-object v0, Lcom/android/camera/fragment/aiwatermark/b;->INSTANCE:Lcom/android/camera/fragment/aiwatermark/b;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 0

    invoke-static {p1, p2}, Lcom/android/camera/fragment/aiwatermark/FragmentWatermark;->b(Landroid/view/View;Landroid/view/MotionEvent;)Z

    move-result p0

    return p0
.end method
