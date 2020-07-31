.class public Lmiui/cloud/backup/data/e$a;
.super Ljava/lang/Object;
.source "PrefsBackupHelper.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/cloud/backup/data/e;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "a"
.end annotation


# instance fields
.field private Zx:Ljava/lang/String;

.field private _x:Ljava/lang/String;

.field private by:Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation
.end field

.field private mDefaultValue:Ljava/lang/Object;


# direct methods
.method private constructor <init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Ljava/lang/Class<",
            "*>;",
            "Ljava/lang/Object;",
            ")V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lmiui/cloud/backup/data/e$a;->Zx:Ljava/lang/String;

    iput-object p2, p0, Lmiui/cloud/backup/data/e$a;->_x:Ljava/lang/String;

    iput-object p3, p0, Lmiui/cloud/backup/data/e$a;->by:Ljava/lang/Class;

    iput-object p4, p0, Lmiui/cloud/backup/data/e$a;->mDefaultValue:Ljava/lang/Object;

    return-void
.end method

.method public static a(Ljava/lang/String;Ljava/lang/String;I)Lmiui/cloud/backup/data/e$a;
    .locals 2

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/Integer;

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    invoke-direct {v0, p0, p1, v1, p2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static a(Ljava/lang/String;Ljava/lang/String;J)Lmiui/cloud/backup/data/e$a;
    .locals 2

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/Long;

    invoke-static {p2, p3}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p2

    invoke-direct {v0, p0, p1, v1, p2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static b(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lmiui/cloud/backup/data/e$a;
    .locals 2

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/String;

    invoke-direct {v0, p0, p1, v1, p2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static b(Ljava/lang/String;Ljava/lang/String;Z)Lmiui/cloud/backup/data/e$a;
    .locals 2

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/Boolean;

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p2

    invoke-direct {v0, p0, p1, v1, p2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static p(Ljava/lang/String;Ljava/lang/String;)Lmiui/cloud/backup/data/e$a;
    .locals 3

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/Boolean;

    const/4 v2, 0x0

    invoke-direct {v0, p0, p1, v1, v2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static q(Ljava/lang/String;Ljava/lang/String;)Lmiui/cloud/backup/data/e$a;
    .locals 3

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/Integer;

    const/4 v2, 0x0

    invoke-direct {v0, p0, p1, v1, v2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static r(Ljava/lang/String;Ljava/lang/String;)Lmiui/cloud/backup/data/e$a;
    .locals 3

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/Long;

    const/4 v2, 0x0

    invoke-direct {v0, p0, p1, v1, v2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method

.method public static s(Ljava/lang/String;Ljava/lang/String;)Lmiui/cloud/backup/data/e$a;
    .locals 3

    new-instance v0, Lmiui/cloud/backup/data/e$a;

    const-class v1, Ljava/lang/String;

    const/4 v2, 0x0

    invoke-direct {v0, p0, p1, v1, v2}, Lmiui/cloud/backup/data/e$a;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)V

    return-object v0
.end method


# virtual methods
.method public Am()Ljava/lang/Class;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation

    iget-object p0, p0, Lmiui/cloud/backup/data/e$a;->by:Ljava/lang/Class;

    return-object p0
.end method

.method public getDefaultValue()Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lmiui/cloud/backup/data/e$a;->mDefaultValue:Ljava/lang/Object;

    return-object p0
.end method

.method public ym()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lmiui/cloud/backup/data/e$a;->Zx:Ljava/lang/String;

    return-object p0
.end method

.method public zm()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lmiui/cloud/backup/data/e$a;->_x:Ljava/lang/String;

    return-object p0
.end method
