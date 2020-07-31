.class Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;
.super Ljava/lang/Object;
.source "VirtualCameraReprocessor.java"

# interfaces
.implements Landroid/media/ImageReader$OnImageAvailableListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->initPicImageReader(III)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;


# direct methods
.method constructor <init>(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)V
    .locals 0

    iput-object p1, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onImageAvailable(Landroid/media/ImageReader;)V
    .locals 10

    invoke-virtual {p1}, Landroid/media/ImageReader;->acquireNextImage()Landroid/media/Image;

    move-result-object v0

    invoke-static {}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$100()Ljava/lang/String;

    move-result-object v1

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "onImageAvailable: received reprocessed image "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {v0}, Lcom/xiaomi/media/imagecodec/JpegUtil;->getPlanesExtra(Landroid/media/Image;)[Landroid/media/Image$Plane;

    move-result-object v1

    const/4 v2, 0x0

    invoke-static {v1, v2}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$1400([Landroid/media/Image$Plane;I)[B

    move-result-object v3

    if-nez v3, :cond_0

    invoke-static {v0}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$1500(Landroid/media/Image;)[B

    move-result-object v3

    :cond_0
    const/4 v4, 0x1

    invoke-static {v1, v4}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$1400([Landroid/media/Image$Plane;I)[B

    move-result-object v1

    invoke-virtual {v0}, Landroid/media/Image;->close()V

    iget-object v0, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {v0}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$300(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)Ljava/lang/Object;

    move-result-object v0

    monitor-enter v0

    :try_start_0
    iget-object v5, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {v5}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$400(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)Lcom/xiaomi/camera/imagecodec/ReprocessData;

    move-result-object v5

    if-eqz v5, :cond_2

    iget-object v5, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {v5}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$400(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)Lcom/xiaomi/camera/imagecodec/ReprocessData;

    move-result-object v5

    invoke-virtual {v5}, Lcom/xiaomi/camera/imagecodec/ReprocessData;->getResultListener()Lcom/xiaomi/camera/imagecodec/ReprocessData$OnDataAvailableListener;

    move-result-object v5

    iget-object v6, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {v6}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$400(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)Lcom/xiaomi/camera/imagecodec/ReprocessData;

    move-result-object v6

    invoke-virtual {v6}, Lcom/xiaomi/camera/imagecodec/ReprocessData;->getImageTag()Ljava/lang/String;

    move-result-object v6

    invoke-interface {v5, v3, v1, v6}, Lcom/xiaomi/camera/imagecodec/ReprocessData$OnDataAvailableListener;->onJpegAvailable([B[BLjava/lang/String;)V

    invoke-static {}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$100()Ljava/lang/String;

    move-result-object v1

    const-string v3, "%s return for %s. cost=%d"

    const/4 v5, 0x3

    new-array v5, v5, [Ljava/lang/Object;

    invoke-virtual {p1}, Landroid/media/ImageReader;->getImageFormat()I

    move-result p1

    const/16 v6, 0x100

    if-ne p1, v6, :cond_1

    const-string p1, "jpeg"

    goto :goto_0

    :cond_1
    const-string p1, "heic"

    :goto_0
    aput-object p1, v5, v2

    iget-object p1, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {p1}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$400(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)Lcom/xiaomi/camera/imagecodec/ReprocessData;

    move-result-object p1

    invoke-virtual {p1}, Lcom/xiaomi/camera/imagecodec/ReprocessData;->getImageTag()Ljava/lang/String;

    move-result-object p1

    aput-object p1, v5, v4

    const/4 p1, 0x2

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v6

    iget-object v2, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {v2}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$1600(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)J

    move-result-wide v8

    sub-long/2addr v6, v8

    invoke-static {v6, v7}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    aput-object v2, v5, p1

    invoke-static {v3, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    const/4 v1, 0x0

    invoke-static {p1, v1}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$402(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;Lcom/xiaomi/camera/imagecodec/ReprocessData;)Lcom/xiaomi/camera/imagecodec/ReprocessData;

    goto :goto_1

    :cond_2
    invoke-static {}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$100()Ljava/lang/String;

    move-result-object p1

    const-string v1, "onImageAvailable: null task!"

    invoke-static {p1, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    iget-object p0, p0, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor$5;->this$0:Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;

    invoke-static {p0}, Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;->access$500(Lcom/xiaomi/camera/imagecodec/impl/VirtualCameraReprocessor;)V

    return-void

    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0
.end method
