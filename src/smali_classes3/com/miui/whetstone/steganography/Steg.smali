.class public Lcom/miui/whetstone/steganography/Steg;
.super Ljava/lang/Object;
.source "Steg.java"


# instance fields
.field private final PASS_NONE:I

.field private final PASS_SIMPLE_XOR:I

.field private inBitmap:Landroid/graphics/Bitmap;

.field private key:Ljava/lang/String;

.field private passmode:I


# direct methods
.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput v0, p0, Lcom/miui/whetstone/steganography/Steg;->PASS_NONE:I

    const/4 v1, 0x1

    iput v1, p0, Lcom/miui/whetstone/steganography/Steg;->PASS_SIMPLE_XOR:I

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/miui/whetstone/steganography/Steg;->key:Ljava/lang/String;

    iput v0, p0, Lcom/miui/whetstone/steganography/Steg;->passmode:I

    iput-object v1, p0, Lcom/miui/whetstone/steganography/Steg;->inBitmap:Landroid/graphics/Bitmap;

    return-void
.end method

.method private bytesAvaliableInBitmap()I
    .locals 1

    iget-object v0, p0, Lcom/miui/whetstone/steganography/Steg;->inBitmap:Landroid/graphics/Bitmap;

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {v0}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v0

    iget-object p0, p0, Lcom/miui/whetstone/steganography/Steg;->inBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getHeight()I

    move-result p0

    mul-int/2addr v0, p0

    mul-int/lit8 v0, v0, 0x3

    div-int/lit8 v0, v0, 0x8

    add-int/lit8 v0, v0, -0xc

    return v0
.end method

.method private setInputBitmap(Landroid/graphics/Bitmap;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/whetstone/steganography/Steg;->inBitmap:Landroid/graphics/Bitmap;

    return-void
.end method

.method public static withInput(Landroid/graphics/Bitmap;)Lcom/miui/whetstone/steganography/Steg;
    .locals 1

    new-instance v0, Lcom/miui/whetstone/steganography/Steg;

    invoke-direct {v0}, Lcom/miui/whetstone/steganography/Steg;-><init>()V

    invoke-direct {v0, p0}, Lcom/miui/whetstone/steganography/Steg;->setInputBitmap(Landroid/graphics/Bitmap;)V

    return-object v0
.end method

.method public static withInput(Ljava/io/File;)Lcom/miui/whetstone/steganography/Steg;
    .locals 1

    new-instance v0, Lcom/miui/whetstone/steganography/Steg;

    invoke-direct {v0}, Lcom/miui/whetstone/steganography/Steg;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object p0

    invoke-direct {v0, p0}, Lcom/miui/whetstone/steganography/Steg;->setInputBitmap(Landroid/graphics/Bitmap;)V

    return-object v0
.end method

.method public static withInput(Ljava/lang/String;)Lcom/miui/whetstone/steganography/Steg;
    .locals 1

    new-instance v0, Lcom/miui/whetstone/steganography/Steg;

    invoke-direct {v0}, Lcom/miui/whetstone/steganography/Steg;-><init>()V

    invoke-static {p0}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object p0

    invoke-direct {v0, p0}, Lcom/miui/whetstone/steganography/Steg;->setInputBitmap(Landroid/graphics/Bitmap;)V

    return-object v0
.end method


# virtual methods
.method public decode()Lcom/miui/whetstone/steganography/DecodedObject;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    new-instance v0, Lcom/miui/whetstone/steganography/DecodedObject;

    iget-object p0, p0, Lcom/miui/whetstone/steganography/Steg;->inBitmap:Landroid/graphics/Bitmap;

    invoke-static {p0}, Lcom/miui/whetstone/steganography/BitmapEncoder;->decode(Landroid/graphics/Bitmap;)[B

    move-result-object p0

    invoke-direct {v0, p0}, Lcom/miui/whetstone/steganography/DecodedObject;-><init>([B)V

    return-object v0
.end method

.method public encode(Ljava/io/File;)Lcom/miui/whetstone/steganography/EncodedObject;
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    new-instance p0, Ljava/lang/RuntimeException;

    const-string p1, "Not implemented yet"

    invoke-direct {p0, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public encode(Ljava/lang/String;)Lcom/miui/whetstone/steganography/EncodedObject;
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/miui/whetstone/steganography/Steg;->encode([B)Lcom/miui/whetstone/steganography/EncodedObject;

    move-result-object p0

    return-object p0
.end method

.method public encode([B)Lcom/miui/whetstone/steganography/EncodedObject;
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    array-length v0, p1

    invoke-direct {p0}, Lcom/miui/whetstone/steganography/Steg;->bytesAvaliableInBitmap()I

    move-result v1

    if-gt v0, v1, :cond_0

    new-instance v0, Lcom/miui/whetstone/steganography/EncodedObject;

    iget-object p0, p0, Lcom/miui/whetstone/steganography/Steg;->inBitmap:Landroid/graphics/Bitmap;

    invoke-static {p0, p1}, Lcom/miui/whetstone/steganography/BitmapEncoder;->encode(Landroid/graphics/Bitmap;[B)Landroid/graphics/Bitmap;

    move-result-object p0

    invoke-direct {v0, p0}, Lcom/miui/whetstone/steganography/EncodedObject;-><init>(Landroid/graphics/Bitmap;)V

    return-object v0

    :cond_0
    new-instance p1, Ljava/lang/IllegalArgumentException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Not enough space in bitmap to hold data (max:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/miui/whetstone/steganography/Steg;->bytesAvaliableInBitmap()I

    move-result p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p0, ")"

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {p1, p0}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p1
.end method

.method public withPassword(Ljava/lang/String;)Lcom/miui/whetstone/steganography/Steg;
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, p1, v0}, Lcom/miui/whetstone/steganography/Steg;->withPassword(Ljava/lang/String;I)Lcom/miui/whetstone/steganography/Steg;

    return-object p0
.end method

.method public withPassword(Ljava/lang/String;I)Lcom/miui/whetstone/steganography/Steg;
    .locals 0

    iput-object p1, p0, Lcom/miui/whetstone/steganography/Steg;->key:Ljava/lang/String;

    iput p2, p0, Lcom/miui/whetstone/steganography/Steg;->passmode:I

    new-instance p0, Ljava/lang/RuntimeException;

    const-string p1, "Not implemented yet"

    invoke-direct {p0, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p0
.end method
