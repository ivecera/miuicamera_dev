.class Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;
.super Ljava/lang/Object;
.source "GifMediaPlayer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "VideoInfo"
.end annotation


# instance fields
.field private gifUri:Landroid/net/Uri;

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

.field private uri:Landroid/net/Uri;

.field private videoGifUrl:Ljava/lang/String;

.field private videoUrl:Ljava/lang/String;


# direct methods
.method constructor <init>(Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoUrl:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public getGifUri()Landroid/net/Uri;
    .locals 4

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->gifUri:Landroid/net/Uri;

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "videoGifUrl: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoGifUrl:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Ljava/io/File;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoGifUrl:Ljava/lang/String;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->access$000()Ljava/lang/String;

    move-result-object v1

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "shareFile: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " shareFile exist: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->this$0:Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;

    invoke-static {v1}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->access$100(Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;)Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v1, v2, v0}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->getFileUri(Landroid/content/Context;Ljava/io/File;)Landroid/net/Uri;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->gifUri:Landroid/net/Uri;

    invoke-static {}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "gifUri: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->gifUri:Landroid/net/Uri;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->gifUri:Landroid/net/Uri;

    return-object p0
.end method

.method public getUri()Landroid/net/Uri;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->uri:Landroid/net/Uri;

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoUrl:Ljava/lang/String;

    invoke-static {v0}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->uri:Landroid/net/Uri;

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->uri:Landroid/net/Uri;

    return-object p0
.end method

.method public getVideoGifUrl()Ljava/lang/String;
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoGifUrl:Ljava/lang/String;

    if-nez v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/android/camera/storage/Storage;->DIRECTORY:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "MIMOJI_"

    const-string v2, "gif"

    invoke-static {v1, v2}, Lcom/android/camera/module/impl/component/FileUtils;->createtFileName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoGifUrl:Ljava/lang/String;

    invoke-static {}, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "target=============:   "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoGifUrl:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoGifUrl:Ljava/lang/String;

    return-object p0
.end method

.method public getVideoUrl()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/gif/GifMediaPlayer$VideoInfo;->videoUrl:Ljava/lang/String;

    return-object p0
.end method
