.class Lcom/android/camera/fragment/music/FragmentLiveMusicPager$6;
.super Ljava/lang/Object;
.source "FragmentLiveMusicPager.java"

# interfaces
.implements Landroid/media/MediaPlayer$OnPreparedListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/music/FragmentLiveMusicPager;->onMusicSelectedToPlay(Lcom/android/camera/fragment/music/LiveMusicInfo;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/music/FragmentLiveMusicPager;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/music/FragmentLiveMusicPager$6;->this$0:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onPrepared(Landroid/media/MediaPlayer;)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/music/FragmentLiveMusicPager$6;->this$0:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;

    invoke-static {v0}, Lcom/android/camera/fragment/music/FragmentLiveMusicPager;->access$700(Lcom/android/camera/fragment/music/FragmentLiveMusicPager;)V

    iget-object v0, p0, Lcom/android/camera/fragment/music/FragmentLiveMusicPager$6;->this$0:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;

    invoke-static {v0}, Lcom/android/camera/fragment/music/FragmentLiveMusicPager;->access$800(Lcom/android/camera/fragment/music/FragmentLiveMusicPager;)Lcom/android/camera/fragment/music/MusicOperation;

    move-result-object v0

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->getDuration()I

    move-result v1

    int-to-long v1, v1

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/fragment/music/MusicOperation;->onNewSessionStartPlay(J)V

    iget-object p0, p0, Lcom/android/camera/fragment/music/FragmentLiveMusicPager$6;->this$0:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;

    const/4 v0, 0x0

    invoke-static {p0, v0}, Lcom/android/camera/fragment/music/FragmentLiveMusicPager;->access$902(Lcom/android/camera/fragment/music/FragmentLiveMusicPager;Z)Z

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->start()V

    return-void
.end method
