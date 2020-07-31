.class public Lcom/miui/whetstone/steganography/DecodedObject;
.super Ljava/lang/Object;
.source "DecodedObject.java"


# instance fields
.field private final bytes:[B


# direct methods
.method public constructor <init>([B)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/miui/whetstone/steganography/DecodedObject;->bytes:[B

    return-void
.end method


# virtual methods
.method public intoByteArray()[B
    .locals 0

    iget-object p0, p0, Lcom/miui/whetstone/steganography/DecodedObject;->bytes:[B

    return-object p0
.end method

.method public intoFile(Ljava/io/File;)Ljava/io/File;
    .locals 0

    new-instance p0, Ljava/lang/RuntimeException;

    const-string p1, "Not implemented yet"

    invoke-direct {p0, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public intoFile(Ljava/lang/String;)Ljava/io/File;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0, v0}, Lcom/miui/whetstone/steganography/DecodedObject;->intoFile(Ljava/io/File;)Ljava/io/File;

    move-result-object p0

    return-object p0
.end method

.method public intoString()Ljava/lang/String;
    .locals 1

    new-instance v0, Ljava/lang/String;

    iget-object p0, p0, Lcom/miui/whetstone/steganography/DecodedObject;->bytes:[B

    invoke-direct {v0, p0}, Ljava/lang/String;-><init>([B)V

    return-object v0
.end method
