.class public Lcom/bumptech/glide/request/a/e;
.super Ljava/lang/Object;
.source "NoTransition.java"

# interfaces
.implements Lcom/bumptech/glide/request/a/f;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/bumptech/glide/request/a/e$a;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<R:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;",
        "Lcom/bumptech/glide/request/a/f<",
        "TR;>;"
    }
.end annotation


# static fields
.field static final bu:Lcom/bumptech/glide/request/a/e;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/bumptech/glide/request/a/e<",
            "*>;"
        }
    .end annotation
.end field

.field private static final cu:Lcom/bumptech/glide/request/a/g;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/bumptech/glide/request/a/g<",
            "*>;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/bumptech/glide/request/a/e;

    invoke-direct {v0}, Lcom/bumptech/glide/request/a/e;-><init>()V

    sput-object v0, Lcom/bumptech/glide/request/a/e;->bu:Lcom/bumptech/glide/request/a/e;

    new-instance v0, Lcom/bumptech/glide/request/a/e$a;

    invoke-direct {v0}, Lcom/bumptech/glide/request/a/e$a;-><init>()V

    sput-object v0, Lcom/bumptech/glide/request/a/e;->cu:Lcom/bumptech/glide/request/a/g;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static get()Lcom/bumptech/glide/request/a/f;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<R:",
            "Ljava/lang/Object;",
            ">()",
            "Lcom/bumptech/glide/request/a/f<",
            "TR;>;"
        }
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/a/e;->bu:Lcom/bumptech/glide/request/a/e;

    return-object v0
.end method

.method public static getFactory()Lcom/bumptech/glide/request/a/g;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<R:",
            "Ljava/lang/Object;",
            ">()",
            "Lcom/bumptech/glide/request/a/g<",
            "TR;>;"
        }
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/a/e;->cu:Lcom/bumptech/glide/request/a/g;

    return-object v0
.end method


# virtual methods
.method public a(Ljava/lang/Object;Lcom/bumptech/glide/request/a/f$a;)Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method
