.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/bottomlist/d;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera/features/mimoji2/widget/baseview/OnRecyclerItemClickListener;


# instance fields
.field private final synthetic Hi:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/d;->Hi:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    return-void
.end method


# virtual methods
.method public final OnRecyclerItemClickListener(Ljava/lang/Object;I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/d;->Hi:Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/FragmentMimojiBottomList;->a(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;I)V

    return-void
.end method
