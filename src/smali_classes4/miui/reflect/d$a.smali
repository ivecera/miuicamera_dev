.class Lmiui/reflect/d$a;
.super Ljava/lang/Object;
.source "ReflectTool.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/reflect/d;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "a"
.end annotation


# static fields
.field private static final STATIC:I = 0x141


# instance fields
.field private ny:Ljava/lang/String;

.field private oy:[I


# direct methods
.method public constructor <init>()V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "The quick fox jumps over the lazy dogs."

    iput-object v0, p0, Lmiui/reflect/d$a;->ny:Ljava/lang/String;

    const/4 v0, 0x5

    new-array v1, v0, [I

    iput-object v1, p0, Lmiui/reflect/d$a;->oy:[I

    iget-object p0, p0, Lmiui/reflect/d$a;->oy:[I

    const/4 v1, 0x1

    const/4 v2, 0x0

    aput v1, p0, v2

    const/4 v2, 0x2

    aput v2, p0, v1

    const/4 v1, 0x3

    aput v1, p0, v2

    const/4 v2, 0x4

    aput v2, p0, v1

    aput v0, p0, v2

    return-void
.end method


# virtual methods
.method public b([Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    array-length v0, p1

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_0

    aget-object v2, p1, v1

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public sum([I)I
    .locals 3

    array-length p0, p1

    const/4 v0, 0x0

    move v1, v0

    :goto_0
    if-ge v0, p0, :cond_0

    aget v2, p1, v0

    add-int/2addr v1, v2

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_0
    return v1
.end method
