.class public Lcom/bumptech/glide/request/f;
.super Ljava/lang/Object;
.source "RequestOptions.java"

# interfaces
.implements Ljava/lang/Cloneable;


# static fields
.field private static final PRIORITY:I = 0x8

.field private static final SIGNATURE:I = 0x400

.field private static final THEME:I = 0x8000

.field private static final UNSET:I = -0x1

.field private static final Ys:I = 0x2

.field private static final Zs:I = 0x4

.field private static final _s:I = 0x10

.field private static final bt:I = 0x20

.field private static final ct:I = 0x40

.field private static final dt:I = 0x80

.field private static final et:I = 0x100

.field private static final ft:I = 0x200

.field private static final gt:I = 0x1000

.field private static final ht:I = 0x2000

.field private static final jt:I = 0x4000

.field private static final kt:I = 0x10000

.field private static final lt:I = 0x20000

.field private static final mt:I = 0x40000

.field private static final nt:I = 0x80000

.field private static final ot:I = 0x100000

.field private static pt:Lcom/bumptech/glide/request/f; = null
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static qt:Lcom/bumptech/glide/request/f; = null
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static final rq:I = 0x800

.field private static rt:Lcom/bumptech/glide/request/f;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static st:Lcom/bumptech/glide/request/f;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static tt:Lcom/bumptech/glide/request/f;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static ut:Lcom/bumptech/glide/request/f;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static vt:Lcom/bumptech/glide/request/f;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private static wt:Lcom/bumptech/glide/request/f;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field


# instance fields
.field private Mn:Z

.field private Ms:F

.field private Ns:Landroid/graphics/drawable/Drawable;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private Om:Z

.field private Os:I

.field private Ps:Landroid/graphics/drawable/Drawable;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private Qs:I

.field private Rs:I

.field private Ss:I

.field private Ts:Z

.field private Us:Landroid/graphics/drawable/Drawable;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private Vs:I

.field private Ws:Z

.field private Xs:Z

.field private fields:I

.field private options:Lcom/bumptech/glide/load/g;
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation
.end field

.field private priority:Lcom/bumptech/glide/Priority;
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation
.end field

.field private qm:Lcom/bumptech/glide/load/c;
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation
.end field

.field private rm:Ljava/lang/Class;
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation
.end field

.field private theme:Landroid/content/res/Resources$Theme;
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation
.end field

.field private tn:Z

.field private vm:Lcom/bumptech/glide/load/engine/o;
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation
.end field

.field private vn:Z

.field private wm:Z

.field private xl:Ljava/util/Map;
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Lcom/bumptech/glide/load/j<",
            "*>;>;"
        }
    .end annotation
.end field

.field private xm:Z


# direct methods
.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/high16 v0, 0x3f800000    # 1.0f

    iput v0, p0, Lcom/bumptech/glide/request/f;->Ms:F

    sget-object v0, Lcom/bumptech/glide/load/engine/o;->AUTOMATIC:Lcom/bumptech/glide/load/engine/o;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    sget-object v0, Lcom/bumptech/glide/Priority;->NORMAL:Lcom/bumptech/glide/Priority;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->tn:Z

    const/4 v1, -0x1

    iput v1, p0, Lcom/bumptech/glide/request/f;->Rs:I

    iput v1, p0, Lcom/bumptech/glide/request/f;->Ss:I

    invoke-static {}, Lcom/bumptech/glide/e/b;->obtain()Lcom/bumptech/glide/e/b;

    move-result-object v1

    iput-object v1, p0, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    new-instance v1, Lcom/bumptech/glide/load/g;

    invoke-direct {v1}, Lcom/bumptech/glide/load/g;-><init>()V

    iput-object v1, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    new-instance v1, Lcom/bumptech/glide/util/CachedHashCodeArrayMap;

    invoke-direct {v1}, Lcom/bumptech/glide/util/CachedHashCodeArrayMap;-><init>()V

    iput-object v1, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    const-class v1, Ljava/lang/Object;

    iput-object v1, p0, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->xm:Z

    return-void
.end method

.method public static C(Z)Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    if-eqz p0, :cond_1

    sget-object p0, Lcom/bumptech/glide/request/f;->pt:Lcom/bumptech/glide/request/f;

    if-nez p0, :cond_0

    new-instance p0, Lcom/bumptech/glide/request/f;

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;-><init>()V

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/bumptech/glide/request/f;->B(Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object p0

    sput-object p0, Lcom/bumptech/glide/request/f;->pt:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object p0, Lcom/bumptech/glide/request/f;->pt:Lcom/bumptech/glide/request/f;

    return-object p0

    :cond_1
    sget-object p0, Lcom/bumptech/glide/request/f;->qt:Lcom/bumptech/glide/request/f;

    if-nez p0, :cond_2

    new-instance p0, Lcom/bumptech/glide/request/f;

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;-><init>()V

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/bumptech/glide/request/f;->B(Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object p0

    sput-object p0, Lcom/bumptech/glide/request/f;->qt:Lcom/bumptech/glide/request/f;

    :cond_2
    sget-object p0, Lcom/bumptech/glide/request/f;->qt:Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public static G(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
            to = 0x64L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->F(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static H(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # I
        .annotation build Landroid/support/annotation/DrawableRes;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->error(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static K(I)Lcom/bumptech/glide/request/f;
    .locals 0
    .param p0    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    invoke-static {p0, p0}, Lcom/bumptech/glide/request/f;->m(II)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static M(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # I
        .annotation build Landroid/support/annotation/DrawableRes;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->L(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static O(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->N(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static Tj()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/f;->tt:Lcom/bumptech/glide/request/f;

    if-nez v0, :cond_0

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Sj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    sput-object v0, Lcom/bumptech/glide/request/f;->tt:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object v0, Lcom/bumptech/glide/request/f;->tt:Lcom/bumptech/glide/request/f;

    return-object v0
.end method

.method private Tn()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Mn:Z

    if-nez v0, :cond_0

    return-object p0

    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    const-string v0, "You cannot modify locked RequestOptions, consider clone()"

    invoke-direct {p0, v0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static Vj()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/f;->st:Lcom/bumptech/glide/request/f;

    if-nez v0, :cond_0

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Uj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    sput-object v0, Lcom/bumptech/glide/request/f;->st:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object v0, Lcom/bumptech/glide/request/f;->st:Lcom/bumptech/glide/request/f;

    return-object v0
.end method

.method public static Xj()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/f;->ut:Lcom/bumptech/glide/request/f;

    if-nez v0, :cond_0

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Wj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    sput-object v0, Lcom/bumptech/glide/request/f;->ut:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object v0, Lcom/bumptech/glide/request/f;->ut:Lcom/bumptech/glide/request/f;

    return-object v0
.end method

.method public static a(Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->c(Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method private a(Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;
    .locals 2
    .param p1    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;Z)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-direct {p0, p1, p2}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    new-instance v0, Lcom/bumptech/glide/load/resource/bitmap/r;

    invoke-direct {v0, p1, p2}, Lcom/bumptech/glide/load/resource/bitmap/r;-><init>(Lcom/bumptech/glide/load/j;Z)V

    const-class v1, Landroid/graphics/Bitmap;

    invoke-direct {p0, v1, p1, p2}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    const-class v1, Landroid/graphics/drawable/Drawable;

    invoke-direct {p0, v1, v0, p2}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    const-class v1, Landroid/graphics/drawable/BitmapDrawable;

    invoke-virtual {v0}, Lcom/bumptech/glide/load/resource/bitmap/r;->Hj()Lcom/bumptech/glide/load/j;

    move-result-object v0

    invoke-direct {p0, v1, v0, p2}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    const-class v0, Lcom/bumptech/glide/load/resource/gif/b;

    new-instance v1, Lcom/bumptech/glide/load/resource/gif/e;

    invoke-direct {v1, p1}, Lcom/bumptech/glide/load/resource/gif/e;-><init>(Lcom/bumptech/glide/load/j;)V

    invoke-direct {p0, v0, v1, p2}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method private a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;
    .locals 0
    .param p1    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;Z)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    if-eqz p3, :cond_0

    invoke-virtual {p0, p1, p2}, Lcom/bumptech/glide/request/f;->b(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1, p2}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    :goto_0
    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->xm:Z

    return-object p0
.end method

.method private a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Ljava/lang/Class;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/lang/Class<",
            "TT;>;",
            "Lcom/bumptech/glide/load/j<",
            "TT;>;Z)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-direct {p0, p1, p2, p3}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    invoke-static {p2}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-interface {v0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x800

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    iget p2, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v0, 0x10000

    or-int/2addr p2, v0

    iput p2, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/4 p2, 0x0

    iput-boolean p2, p0, Lcom/bumptech/glide/request/f;->xm:Z

    if-eqz p3, :cond_1

    iget p2, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 p3, 0x20000

    or-int/2addr p2, p3

    iput p2, p0, Lcom/bumptech/glide/request/f;->fields:I

    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->wm:Z

    :cond_1
    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public static ak()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/f;->rt:Lcom/bumptech/glide/request/f;

    if-nez v0, :cond_0

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->fitCenter()Lcom/bumptech/glide/request/f;

    move-result-object v0

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    sput-object v0, Lcom/bumptech/glide/request/f;->rt:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object v0, Lcom/bumptech/glide/request/f;->rt:Lcom/bumptech/glide/request/f;

    return-object v0
.end method

.method public static b(Landroid/graphics/Bitmap$CompressFormat;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Landroid/graphics/Bitmap$CompressFormat;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->a(Landroid/graphics/Bitmap$CompressFormat;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static b(Lcom/bumptech/glide/Priority;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/Priority;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/Priority;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static b(Lcom/bumptech/glide/load/DecodeFormat;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/load/DecodeFormat;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/DecodeFormat;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static b(Lcom/bumptech/glide/load/engine/o;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/load/engine/o;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/engine/o;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static b(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/load/f;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p1    # Ljava/lang/Object;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/bumptech/glide/load/f<",
            "TT;>;TT;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static b(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method private c(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static d(J)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # J
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0, p1}, Lcom/bumptech/glide/request/f;->c(J)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method private d(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    const/4 v0, 0x1

    invoke-direct {p0, p1, p2, v0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static g(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Landroid/graphics/drawable/Drawable;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->f(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static h(F)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # F
        .annotation build Landroid/support/annotation/FloatRange;
            from = 0.0
            to = 1.0
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->g(F)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static h(Lcom/bumptech/glide/load/c;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Lcom/bumptech/glide/load/c;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->g(Lcom/bumptech/glide/load/c;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method private isSet(I)Z
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-static {p0, p1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result p0

    return p0
.end method

.method public static j(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Landroid/graphics/drawable/Drawable;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->i(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static k(Ljava/lang/Class;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # Ljava/lang/Class;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class<",
            "*>;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0}, Lcom/bumptech/glide/request/f;->j(Ljava/lang/Class;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public static m(II)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p0    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .param p1    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0, p0, p1}, Lcom/bumptech/glide/request/f;->l(II)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method private static r(II)Z
    .locals 0

    and-int/2addr p0, p1

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public static xk()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/f;->wt:Lcom/bumptech/glide/request/f;

    if-nez v0, :cond_0

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Zj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    sput-object v0, Lcom/bumptech/glide/request/f;->wt:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object v0, Lcom/bumptech/glide/request/f;->wt:Lcom/bumptech/glide/request/f;

    return-object v0
.end method

.method public static yk()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/request/f;->vt:Lcom/bumptech/glide/request/f;

    if-nez v0, :cond_0

    new-instance v0, Lcom/bumptech/glide/request/f;

    invoke-direct {v0}, Lcom/bumptech/glide/request/f;-><init>()V

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->_j()Lcom/bumptech/glide/request/f;

    move-result-object v0

    invoke-virtual {v0}, Lcom/bumptech/glide/request/f;->Rj()Lcom/bumptech/glide/request/f;

    move-result-object v0

    sput-object v0, Lcom/bumptech/glide/request/f;->vt:Lcom/bumptech/glide/request/f;

    :cond_0
    sget-object v0, Lcom/bumptech/glide/request/f;->vt:Lcom/bumptech/glide/request/f;

    return-object v0
.end method


# virtual methods
.method public A(Z)Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->A(Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->Om:Z

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v0, 0x80000

    or-int/2addr p1, v0

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public Ak()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->CENTER_INSIDE:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/k;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/k;-><init>()V

    invoke-direct {p0, v0, v1}, Lcom/bumptech/glide/request/f;->c(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public B(Z)Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, v1}, Lcom/bumptech/glide/request/f;->B(Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    xor-int/2addr p1, v1

    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->tn:Z

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x100

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public Bk()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->Uq:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/l;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/l;-><init>()V

    invoke-virtual {p0, v0, v1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public Ck()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->FIT_CENTER:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/s;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/s;-><init>()V

    invoke-direct {p0, v0, v1}, Lcom/bumptech/glide/request/f;->c(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public D(Z)Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->D(Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->vn:Z

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v0, 0x100000

    or-int/2addr p1, v0

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public E(Z)Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->E(Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-boolean p1, p0, Lcom/bumptech/glide/request/f;->Xs:Z

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v0, 0x40000

    or-int/2addr p1, v0

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public F(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
            to = 0x64L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/e;->sq:Lcom/bumptech/glide/load/f;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public I(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # I
        .annotation build Landroid/support/annotation/DrawableRes;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->I(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput p1, p0, Lcom/bumptech/glide/request/f;->Vs:I

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x4000

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public J(I)Lcom/bumptech/glide/request/f;
    .locals 0
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    invoke-virtual {p0, p1, p1}, Lcom/bumptech/glide/request/f;->l(II)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public L(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # I
        .annotation build Landroid/support/annotation/DrawableRes;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->L(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput p1, p0, Lcom/bumptech/glide/request/f;->Qs:I

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x80

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public N(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # I
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/model/a/b;->TIMEOUT:Lcom/bumptech/glide/load/f;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public Rj()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Mn:Z

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    const-string v0, "You cannot auto lock an already locked options object, try clone() first"

    invoke-direct {p0, v0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0

    :cond_1
    :goto_0
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->lock()Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public Sj()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->Uq:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/j;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/j;-><init>()V

    invoke-virtual {p0, v0, v1}, Lcom/bumptech/glide/request/f;->b(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public final T()Ljava/lang/Class;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    return-object p0
.end method

.method public Uj()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->CENTER_INSIDE:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/k;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/k;-><init>()V

    invoke-direct {p0, v0, v1}, Lcom/bumptech/glide/request/f;->d(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public Wj()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->CENTER_INSIDE:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/l;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/l;-><init>()V

    invoke-virtual {p0, v0, v1}, Lcom/bumptech/glide/request/f;->b(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public Yj()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/o;->br:Lcom/bumptech/glide/load/f;

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    invoke-virtual {p0, v0, v1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public Zj()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/gif/g;->Zr:Lcom/bumptech/glide/load/f;

    const/4 v1, 0x1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    invoke-virtual {p0, v0, v1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public _j()Lcom/bumptech/glide/request/f;
    .locals 3
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->_j()Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iget-object v0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-interface {v0}, Ljava/util/Map;->clear()V

    iget v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    and-int/lit16 v0, v0, -0x801

    iput v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->wm:Z

    iget v1, p0, Lcom/bumptech/glide/request/f;->fields:I

    const v2, -0x20001

    and-int/2addr v1, v2

    iput v1, p0, Lcom/bumptech/glide/request/f;->fields:I

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    iget v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v1, 0x10000

    or-int/2addr v0, v1

    iput v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->xm:Z

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public a(Landroid/content/res/Resources$Theme;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Landroid/content/res/Resources$Theme;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->a(Landroid/content/res/Resources$Theme;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-object p1, p0, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    const v0, 0x8000

    or-int/2addr p1, v0

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public a(Landroid/graphics/Bitmap$CompressFormat;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Landroid/graphics/Bitmap$CompressFormat;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/e;->tq:Lcom/bumptech/glide/load/f;

    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public a(Lcom/bumptech/glide/Priority;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/Priority;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/Priority;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    check-cast p1, Lcom/bumptech/glide/Priority;

    iput-object p1, p0, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit8 p1, p1, 0x8

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public a(Lcom/bumptech/glide/load/DecodeFormat;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/DecodeFormat;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/o;->Yq:Lcom/bumptech/glide/load/f;

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    sget-object v0, Lcom/bumptech/glide/load/resource/gif/g;->Yq:Lcom/bumptech/glide/load/f;

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public a(Lcom/bumptech/glide/load/engine/o;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/engine/o;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/engine/o;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    check-cast p1, Lcom/bumptech/glide/load/engine/o;

    iput-object p1, p0, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit8 p1, p1, 0x4

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/f;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Ljava/lang/Object;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/bumptech/glide/load/f<",
            "TT;>;TT;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    invoke-static {p2}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    invoke-virtual {v0, p1, p2}, Lcom/bumptech/glide/load/g;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/load/g;

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->Wq:Lcom/bumptech/glide/load/f;

    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method final a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;)Lcom/bumptech/glide/request/f;

    const/4 p1, 0x0

    invoke-direct {p0, p2, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Ljava/lang/Class;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/lang/Class<",
            "TT;>;",
            "Lcom/bumptech/glide/load/j<",
            "TT;>;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public varargs a([Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # [Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "([",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    new-instance v0, Lcom/bumptech/glide/load/d;

    invoke-direct {v0, p1}, Lcom/bumptech/glide/load/d;-><init>([Lcom/bumptech/glide/load/j;)V

    const/4 p1, 0x1

    invoke-direct {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public b(Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method final b(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Lcom/bumptech/glide/request/f;->b(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;)Lcom/bumptech/glide/request/f;

    invoke-virtual {p0, p2}, Lcom/bumptech/glide/request/f;->c(Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public b(Lcom/bumptech/glide/request/f;)Lcom/bumptech/glide/request/f;
    .locals 2
    .param p1    # Lcom/bumptech/glide/request/f;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->b(Lcom/bumptech/glide/request/f;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/4 v1, 0x2

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p1, Lcom/bumptech/glide/request/f;->Ms:F

    iput v0, p0, Lcom/bumptech/glide/request/f;->Ms:F

    :cond_1
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v1, 0x40000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_2

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->Xs:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Xs:Z

    :cond_2
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v1, 0x100000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_3

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->vn:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->vn:Z

    :cond_3
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/4 v1, 0x4

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_4

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    :cond_4
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x8

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_5

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    :cond_5
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x10

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    :cond_6
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x20

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_7

    iget v0, p1, Lcom/bumptech/glide/request/f;->Os:I

    iput v0, p0, Lcom/bumptech/glide/request/f;->Os:I

    :cond_7
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x40

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_8

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    :cond_8
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x80

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_9

    iget v0, p1, Lcom/bumptech/glide/request/f;->Qs:I

    iput v0, p0, Lcom/bumptech/glide/request/f;->Qs:I

    :cond_9
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x100

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_a

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->tn:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->tn:Z

    :cond_a
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x200

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_b

    iget v0, p1, Lcom/bumptech/glide/request/f;->Ss:I

    iput v0, p0, Lcom/bumptech/glide/request/f;->Ss:I

    iget v0, p1, Lcom/bumptech/glide/request/f;->Rs:I

    iput v0, p0, Lcom/bumptech/glide/request/f;->Rs:I

    :cond_b
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x400

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_c

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    :cond_c
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x1000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_d

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    :cond_d
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x2000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_e

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    :cond_e
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x4000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_f

    iget v0, p1, Lcom/bumptech/glide/request/f;->Vs:I

    iput v0, p0, Lcom/bumptech/glide/request/f;->Vs:I

    :cond_f
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const v1, 0x8000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_10

    iget-object v0, p1, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    iput-object v0, p0, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    :cond_10
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v1, 0x10000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_11

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->Ts:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    :cond_11
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v1, 0x20000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_12

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->wm:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->wm:Z

    :cond_12
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/16 v1, 0x800

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_13

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    iget-object v1, p1, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-interface {v0, v1}, Ljava/util/Map;->putAll(Ljava/util/Map;)V

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->xm:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->xm:Z

    :cond_13
    iget v0, p1, Lcom/bumptech/glide/request/f;->fields:I

    const/high16 v1, 0x80000

    invoke-static {v0, v1}, Lcom/bumptech/glide/request/f;->r(II)Z

    move-result v0

    if-eqz v0, :cond_14

    iget-boolean v0, p1, Lcom/bumptech/glide/request/f;->Om:Z

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Om:Z

    :cond_14
    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    if-nez v0, :cond_15

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-interface {v0}, Ljava/util/Map;->clear()V

    iget v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    and-int/lit16 v0, v0, -0x801

    iput v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->wm:Z

    iget v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    const v1, -0x20001

    and-int/2addr v0, v1

    iput v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->xm:Z

    :cond_15
    iget v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    iget v1, p1, Lcom/bumptech/glide/request/f;->fields:I

    or-int/2addr v0, v1

    iput v0, p0, Lcom/bumptech/glide/request/f;->fields:I

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    iget-object p1, p1, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    invoke-virtual {v0, p1}, Lcom/bumptech/glide/load/g;->b(Lcom/bumptech/glide/load/g;)V

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public b(Ljava/lang/Class;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Ljava/lang/Class;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/lang/Class<",
            "TT;>;",
            "Lcom/bumptech/glide/load/j<",
            "TT;>;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    const/4 v0, 0x1

    invoke-direct {p0, p1, p2, v0}, Lcom/bumptech/glide/request/f;->a(Ljava/lang/Class;Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public final bk()I
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->Os:I

    return p0
.end method

.method public c(J)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # J
        .annotation build Landroid/support/annotation/IntRange;
            from = 0x0L
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/VideoDecoder;->Er:Lcom/bumptech/glide/load/f;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p1

    invoke-virtual {p0, v0, p1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/f;Ljava/lang/Object;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public c(Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/j;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/j<",
            "Landroid/graphics/Bitmap;",
            ">;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    const/4 v0, 0x1

    invoke-direct {p0, p1, v0}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/j;Z)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public final ck()Landroid/graphics/drawable/Drawable;
    .locals 0
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    return-object p0
.end method

.method public clone()Lcom/bumptech/glide/request/f;
    .locals 3
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    :try_start_0
    invoke-super {p0}, Ljava/lang/Object;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/bumptech/glide/request/f;

    new-instance v1, Lcom/bumptech/glide/load/g;

    invoke-direct {v1}, Lcom/bumptech/glide/load/g;-><init>()V

    iput-object v1, v0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    iget-object v1, v0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    iget-object v2, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    invoke-virtual {v1, v2}, Lcom/bumptech/glide/load/g;->b(Lcom/bumptech/glide/load/g;)V

    new-instance v1, Lcom/bumptech/glide/util/CachedHashCodeArrayMap;

    invoke-direct {v1}, Lcom/bumptech/glide/util/CachedHashCodeArrayMap;-><init>()V

    iput-object v1, v0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    iget-object v1, v0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-interface {v1, p0}, Ljava/util/Map;->putAll(Ljava/util/Map;)V

    const/4 p0, 0x0

    iput-boolean p0, v0, Lcom/bumptech/glide/request/f;->Mn:Z

    iput-boolean p0, v0, Lcom/bumptech/glide/request/f;->Ws:Z
    :try_end_0
    .catch Ljava/lang/CloneNotSupportedException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v0

    :catch_0
    move-exception p0

    new-instance v0, Ljava/lang/RuntimeException;

    invoke-direct {v0, p0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw v0
.end method

.method public bridge synthetic clone()Ljava/lang/Object;
    .locals 0
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/CloneNotSupportedException;
        }
    .end annotation

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public final dk()Landroid/graphics/drawable/Drawable;
    .locals 0
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    return-object p0
.end method

.method public final ek()I
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->Vs:I

    return p0
.end method

.method public equals(Ljava/lang/Object;)Z
    .locals 3

    instance-of v0, p1, Lcom/bumptech/glide/request/f;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    check-cast p1, Lcom/bumptech/glide/request/f;

    iget v0, p1, Lcom/bumptech/glide/request/f;->Ms:F

    iget v2, p0, Lcom/bumptech/glide/request/f;->Ms:F

    invoke-static {v0, v2}, Ljava/lang/Float;->compare(FF)I

    move-result v0

    if-nez v0, :cond_0

    iget v0, p0, Lcom/bumptech/glide/request/f;->Os:I

    iget v2, p1, Lcom/bumptech/glide/request/f;->Os:I

    if-ne v0, v2, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    invoke-static {v0, v2}, Lcom/bumptech/glide/util/l;->d(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget v0, p0, Lcom/bumptech/glide/request/f;->Qs:I

    iget v2, p1, Lcom/bumptech/glide/request/f;->Qs:I

    if-ne v0, v2, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    invoke-static {v0, v2}, Lcom/bumptech/glide/util/l;->d(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget v0, p0, Lcom/bumptech/glide/request/f;->Vs:I

    iget v2, p1, Lcom/bumptech/glide/request/f;->Vs:I

    if-ne v0, v2, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    invoke-static {v0, v2}, Lcom/bumptech/glide/util/l;->d(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->tn:Z

    iget-boolean v2, p1, Lcom/bumptech/glide/request/f;->tn:Z

    if-ne v0, v2, :cond_0

    iget v0, p0, Lcom/bumptech/glide/request/f;->Rs:I

    iget v2, p1, Lcom/bumptech/glide/request/f;->Rs:I

    if-ne v0, v2, :cond_0

    iget v0, p0, Lcom/bumptech/glide/request/f;->Ss:I

    iget v2, p1, Lcom/bumptech/glide/request/f;->Ss:I

    if-ne v0, v2, :cond_0

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->wm:Z

    iget-boolean v2, p1, Lcom/bumptech/glide/request/f;->wm:Z

    if-ne v0, v2, :cond_0

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    iget-boolean v2, p1, Lcom/bumptech/glide/request/f;->Ts:Z

    if-ne v0, v2, :cond_0

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Xs:Z

    iget-boolean v2, p1, Lcom/bumptech/glide/request/f;->Xs:Z

    if-ne v0, v2, :cond_0

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Om:Z

    iget-boolean v2, p1, Lcom/bumptech/glide/request/f;->Om:Z

    if-ne v0, v2, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    invoke-virtual {v0, v2}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    if-ne v0, v2, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    invoke-virtual {v0, v2}, Lcom/bumptech/glide/load/g;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-interface {v0, v2}, Ljava/util/Map;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    invoke-virtual {v0, v2}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    iget-object v2, p1, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    invoke-static {v0, v2}, Lcom/bumptech/glide/util/l;->d(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    iget-object p1, p1, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    invoke-static {p0, p1}, Lcom/bumptech/glide/util/l;->d(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public error(I)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # I
        .annotation build Landroid/support/annotation/DrawableRes;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->error(I)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput p1, p0, Lcom/bumptech/glide/request/f;->Os:I

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit8 p1, p1, 0x20

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public f(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Landroid/graphics/drawable/Drawable;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->f(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-object p1, p0, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit8 p1, p1, 0x10

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public fitCenter()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->FIT_CENTER:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/s;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/s;-><init>()V

    invoke-direct {p0, v0, v1}, Lcom/bumptech/glide/request/f;->d(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method

.method public final fk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->Om:Z

    return p0
.end method

.method public g(F)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # F
        .annotation build Landroid/support/annotation/FloatRange;
            from = 0.0
            to = 1.0
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->g(F)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    const/4 v0, 0x0

    cmpg-float v0, p1, v0

    if-ltz v0, :cond_1

    const/high16 v0, 0x3f800000    # 1.0f

    cmpl-float v0, p1, v0

    if-gtz v0, :cond_1

    iput p1, p0, Lcom/bumptech/glide/request/f;->Ms:F

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit8 p1, p1, 0x2

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0

    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "sizeMultiplier must be between 0 and 1"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public g(Lcom/bumptech/glide/load/c;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Lcom/bumptech/glide/load/c;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->g(Lcom/bumptech/glide/load/c;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    check-cast p1, Lcom/bumptech/glide/load/c;

    iput-object p1, p0, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x400

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public final getOptions()Lcom/bumptech/glide/load/g;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    return-object p0
.end method

.method public final getPriority()Lcom/bumptech/glide/Priority;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    return-object p0
.end method

.method public final getSignature()Lcom/bumptech/glide/load/c;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    return-object p0
.end method

.method public final getTheme()Landroid/content/res/Resources$Theme;
    .locals 0
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    return-object p0
.end method

.method public final getTransformations()Ljava/util/Map;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Lcom/bumptech/glide/load/j<",
            "*>;>;"
        }
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    return-object p0
.end method

.method public final gk()I
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->Rs:I

    return p0
.end method

.method public h(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Landroid/graphics/drawable/Drawable;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->h(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-object p1, p0, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x2000

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public hashCode()I
    .locals 2

    iget v0, p0, Lcom/bumptech/glide/request/f;->Ms:F

    invoke-static {v0}, Lcom/bumptech/glide/util/l;->hashCode(F)I

    move-result v0

    iget v1, p0, Lcom/bumptech/glide/request/f;->Os:I

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->n(II)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->Ns:Landroid/graphics/drawable/Drawable;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget v1, p0, Lcom/bumptech/glide/request/f;->Qs:I

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->n(II)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget v1, p0, Lcom/bumptech/glide/request/f;->Vs:I

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->n(II)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->Us:Landroid/graphics/drawable/Drawable;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-boolean v1, p0, Lcom/bumptech/glide/request/f;->tn:Z

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->c(ZI)I

    move-result v0

    iget v1, p0, Lcom/bumptech/glide/request/f;->Rs:I

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->n(II)I

    move-result v0

    iget v1, p0, Lcom/bumptech/glide/request/f;->Ss:I

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->n(II)I

    move-result v0

    iget-boolean v1, p0, Lcom/bumptech/glide/request/f;->wm:Z

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->c(ZI)I

    move-result v0

    iget-boolean v1, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->c(ZI)I

    move-result v0

    iget-boolean v1, p0, Lcom/bumptech/glide/request/f;->Xs:Z

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->c(ZI)I

    move-result v0

    iget-boolean v1, p0, Lcom/bumptech/glide/request/f;->Om:Z

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->c(ZI)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->priority:Lcom/bumptech/glide/Priority;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->options:Lcom/bumptech/glide/load/g;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->xl:Ljava/util/Map;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-object v1, p0, Lcom/bumptech/glide/request/f;->qm:Lcom/bumptech/glide/load/c;

    invoke-static {v1, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result v0

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->theme:Landroid/content/res/Resources$Theme;

    invoke-static {p0, v0}, Lcom/bumptech/glide/util/l;->a(Ljava/lang/Object;I)I

    move-result p0

    return p0
.end method

.method public final hj()Lcom/bumptech/glide/load/engine/o;
    .locals 0
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->vm:Lcom/bumptech/glide/load/engine/o;

    return-object p0
.end method

.method public final hk()I
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->Ss:I

    return p0
.end method

.method public i(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Landroid/graphics/drawable/Drawable;
        .annotation build Landroid/support/annotation/Nullable;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->i(Landroid/graphics/drawable/Drawable;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput-object p1, p0, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit8 p1, p1, 0x40

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public final ik()Landroid/graphics/drawable/Drawable;
    .locals 0
    .annotation build Landroid/support/annotation/Nullable;
    .end annotation

    iget-object p0, p0, Lcom/bumptech/glide/request/f;->Ps:Landroid/graphics/drawable/Drawable;

    return-object p0
.end method

.method public final isLocked()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->Mn:Z

    return p0
.end method

.method public j(Ljava/lang/Class;)Lcom/bumptech/glide/request/f;
    .locals 1
    .param p1    # Ljava/lang/Class;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class<",
            "*>;)",
            "Lcom/bumptech/glide/request/f;"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/bumptech/glide/request/f;->j(Ljava/lang/Class;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-static {p1}, Lcom/bumptech/glide/util/i;->checkNotNull(Ljava/lang/Object;)Ljava/lang/Object;

    check-cast p1, Ljava/lang/Class;

    iput-object p1, p0, Lcom/bumptech/glide/request/f;->rm:Ljava/lang/Class;

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x1000

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public final jk()I
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->Qs:I

    return p0
.end method

.method public final kk()F
    .locals 0

    iget p0, p0, Lcom/bumptech/glide/request/f;->Ms:F

    return p0
.end method

.method public l(II)Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    iget-boolean v0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/bumptech/glide/request/f;->clone()Lcom/bumptech/glide/request/f;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Lcom/bumptech/glide/request/f;->l(II)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0

    :cond_0
    iput p1, p0, Lcom/bumptech/glide/request/f;->Ss:I

    iput p2, p0, Lcom/bumptech/glide/request/f;->Rs:I

    iget p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    or-int/lit16 p1, p1, 0x200

    iput p1, p0, Lcom/bumptech/glide/request/f;->fields:I

    invoke-direct {p0}, Lcom/bumptech/glide/request/f;->Tn()Lcom/bumptech/glide/request/f;

    return-object p0
.end method

.method public final lk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->vn:Z

    return p0
.end method

.method public lock()Lcom/bumptech/glide/request/f;
    .locals 1
    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/request/f;->Mn:Z

    return-object p0
.end method

.method mj()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->xm:Z

    return p0
.end method

.method public final mk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->Xs:Z

    return p0
.end method

.method protected nk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->Ws:Z

    return p0
.end method

.method public final pk()Z
    .locals 1

    const/4 v0, 0x4

    invoke-direct {p0, v0}, Lcom/bumptech/glide/request/f;->isSet(I)Z

    move-result p0

    return p0
.end method

.method public final qk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->tn:Z

    return p0
.end method

.method public final rk()Z
    .locals 1

    const/16 v0, 0x8

    invoke-direct {p0, v0}, Lcom/bumptech/glide/request/f;->isSet(I)Z

    move-result p0

    return p0
.end method

.method public final sk()Z
    .locals 1

    const/16 v0, 0x100

    invoke-direct {p0, v0}, Lcom/bumptech/glide/request/f;->isSet(I)Z

    move-result p0

    return p0
.end method

.method public final tk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->Ts:Z

    return p0
.end method

.method public final uk()Z
    .locals 0

    iget-boolean p0, p0, Lcom/bumptech/glide/request/f;->wm:Z

    return p0
.end method

.method public final vk()Z
    .locals 1

    const/16 v0, 0x800

    invoke-direct {p0, v0}, Lcom/bumptech/glide/request/f;->isSet(I)Z

    move-result p0

    return p0
.end method

.method public final wk()Z
    .locals 1

    iget v0, p0, Lcom/bumptech/glide/request/f;->Ss:I

    iget p0, p0, Lcom/bumptech/glide/request/f;->Rs:I

    invoke-static {v0, p0}, Lcom/bumptech/glide/util/l;->o(II)Z

    move-result p0

    return p0
.end method

.method public zk()Lcom/bumptech/glide/request/f;
    .locals 2
    .annotation build Landroid/support/annotation/CheckResult;
    .end annotation

    .annotation build Landroid/support/annotation/NonNull;
    .end annotation

    sget-object v0, Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;->Uq:Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;

    new-instance v1, Lcom/bumptech/glide/load/resource/bitmap/j;

    invoke-direct {v1}, Lcom/bumptech/glide/load/resource/bitmap/j;-><init>()V

    invoke-virtual {p0, v0, v1}, Lcom/bumptech/glide/request/f;->a(Lcom/bumptech/glide/load/resource/bitmap/DownsampleStrategy;Lcom/bumptech/glide/load/j;)Lcom/bumptech/glide/request/f;

    move-result-object p0

    return-object p0
.end method
