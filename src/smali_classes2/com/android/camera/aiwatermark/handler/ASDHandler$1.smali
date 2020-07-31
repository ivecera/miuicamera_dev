.class Lcom/android/camera/aiwatermark/handler/ASDHandler$1;
.super Ljava/lang/Object;
.source "ASDHandler.java"

# interfaces
.implements Lcom/android/camera/aiwatermark/lisenter/IASDListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/aiwatermark/handler/ASDHandler;->createASDListner()Lcom/android/camera/aiwatermark/lisenter/IASDListener;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;


# direct methods
.method constructor <init>(Lcom/android/camera/aiwatermark/handler/ASDHandler;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onASDChange(I)V
    .locals 5

    invoke-static {}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onASDChange spots = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-static {v0}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$100(Lcom/android/camera/aiwatermark/handler/ASDHandler;)I

    move-result v0

    if-ne v0, p1, :cond_0

    return-void

    :cond_0
    invoke-static {p1}, Lcom/android/camera/aiwatermark/algo/ASDEngine;->spots2ASDKey(I)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-static {v1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$200(Lcom/android/camera/aiwatermark/handler/ASDHandler;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-eqz v2, :cond_2

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/aiwatermark/data/WatermarkItem;

    invoke-virtual {v2}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getKey()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    move v0, v3

    goto :goto_0

    :cond_2
    move v0, v4

    :goto_0
    if-nez v0, :cond_4

    if-nez p1, :cond_3

    goto :goto_1

    :cond_3
    move v3, v4

    :cond_4
    :goto_1
    if-eqz v3, :cond_7

    iget-object v0, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-static {v0, p1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$102(Lcom/android/camera/aiwatermark/handler/ASDHandler;I)I

    iget-object p1, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-virtual {p1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->findWatermark()Lcom/android/camera/aiwatermark/data/WatermarkItem;

    move-result-object p1

    invoke-static {}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "item="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-nez p1, :cond_6

    iget-object p1, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-static {p1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$300(Lcom/android/camera/aiwatermark/handler/ASDHandler;)Lcom/android/camera/aiwatermark/handler/AbstractHandler;

    move-result-object p1

    if-nez p1, :cond_5

    iget-object p1, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-virtual {p1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->getASDChain()Lcom/android/camera/aiwatermark/handler/AbstractHandler;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$302(Lcom/android/camera/aiwatermark/handler/ASDHandler;Lcom/android/camera/aiwatermark/handler/AbstractHandler;)Lcom/android/camera/aiwatermark/handler/AbstractHandler;

    :cond_5
    iget-object p1, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-static {p1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$300(Lcom/android/camera/aiwatermark/handler/ASDHandler;)Lcom/android/camera/aiwatermark/handler/AbstractHandler;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/aiwatermark/handler/AbstractHandler;->handleRequest()Lcom/android/camera/aiwatermark/data/WatermarkItem;

    move-result-object p1

    :cond_6
    iget-object p0, p0, Lcom/android/camera/aiwatermark/handler/ASDHandler$1;->this$0:Lcom/android/camera/aiwatermark/handler/ASDHandler;

    invoke-static {p0, p1}, Lcom/android/camera/aiwatermark/handler/ASDHandler;->access$400(Lcom/android/camera/aiwatermark/handler/ASDHandler;Lcom/android/camera/aiwatermark/data/WatermarkItem;)V

    :cond_7
    return-void
.end method
