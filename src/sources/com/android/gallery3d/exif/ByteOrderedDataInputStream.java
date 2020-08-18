package com.android.gallery3d.exif;

import com.android.camera.log.Log;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

class ByteOrderedDataInputStream extends InputStream implements DataInput {
    private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
    private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
    private static final String TAG = "ByteOrderedDataInputStream";
    private ByteOrder mByteOrder;
    private DataInputStream mDataInputStream;
    private InputStream mInputStream;
    private final int mLength;
    private int mPosition;

    public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
        this.mByteOrder = ByteOrder.BIG_ENDIAN;
        this.mInputStream = inputStream;
        this.mDataInputStream = new DataInputStream(inputStream);
        this.mLength = this.mDataInputStream.available();
        this.mPosition = 0;
        this.mDataInputStream.mark(this.mLength);
    }

    public ByteOrderedDataInputStream(byte[] bArr) throws IOException {
        this(new ByteArrayInputStream(bArr));
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.mDataInputStream.available();
    }

    public int getLength() {
        return this.mLength;
    }

    public int peek() {
        return this.mPosition;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.mPosition;
        if (i == this.mLength) {
            return -1;
        }
        this.mPosition = i + 1;
        return this.mDataInputStream.read();
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        this.mPosition++;
        return this.mDataInputStream.readBoolean();
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        this.mPosition++;
        if (this.mPosition <= this.mLength) {
            int read = this.mDataInputStream.read();
            if (read >= 0) {
                return (byte) read;
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        this.mPosition += 2;
        return this.mDataInputStream.readChar();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr) throws IOException {
        this.mPosition += bArr.length;
        if (this.mPosition > this.mLength) {
            throw new EOFException();
        } else if (this.mDataInputStream.read(bArr, 0, bArr.length) != bArr.length) {
            throw new IOException("Couldn't read up to the length of buffer");
        }
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        this.mPosition += i2;
        if (this.mPosition > this.mLength) {
            throw new EOFException();
        } else if (this.mDataInputStream.read(bArr, i, i2) != i2) {
            throw new IOException("Couldn't read up to the length of buffer");
        }
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        this.mPosition += 4;
        if (this.mPosition <= this.mLength) {
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
                }
                if (byteOrder == BIG_ENDIAN) {
                    return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
                }
                throw new IOException("Invalid byte order: " + this.mByteOrder);
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        Log.d(TAG, "Currently unsupported");
        return null;
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        this.mPosition += 8;
        if (this.mPosition <= this.mLength) {
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            int read5 = this.mDataInputStream.read();
            int read6 = this.mDataInputStream.read();
            int read7 = this.mDataInputStream.read();
            int read8 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4 | read5 | read6 | read7 | read8) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    return (((long) read8) << 56) + (((long) read7) << 48) + (((long) read6) << 40) + (((long) read5) << 32) + (((long) read4) << 24) + (((long) read3) << 16) + (((long) read2) << 8) + ((long) read);
                }
                if (byteOrder == BIG_ENDIAN) {
                    return (((long) read) << 56) + (((long) read2) << 48) + (((long) read3) << 40) + (((long) read4) << 32) + (((long) read5) << 24) + (((long) read6) << 16) + (((long) read7) << 8) + ((long) read8);
                }
                throw new IOException("Invalid byte order: " + this.mByteOrder);
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        int i;
        this.mPosition += 2;
        if (this.mPosition <= this.mLength) {
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    i = (read2 << 8) + read;
                } else if (byteOrder == BIG_ENDIAN) {
                    i = (read << 8) + read2;
                } else {
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                return (short) i;
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        this.mPosition += 2;
        return this.mDataInputStream.readUTF();
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        this.mPosition++;
        return this.mDataInputStream.readUnsignedByte();
    }

    public long readUnsignedInt() throws IOException {
        return ((long) readInt()) & 4294967295L;
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        this.mPosition += 2;
        if (this.mPosition <= this.mLength) {
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    return (read2 << 8) + read;
                }
                if (byteOrder == BIG_ENDIAN) {
                    return (read << 8) + read2;
                }
                throw new IOException("Invalid byte order: " + this.mByteOrder);
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    public void seek(long j) throws IOException {
        int i = this.mPosition;
        if (((long) i) > j) {
            this.mPosition = 0;
            this.mDataInputStream.reset();
            this.mDataInputStream.mark(this.mLength);
        } else {
            j -= (long) i;
        }
        int i2 = (int) j;
        if (skipBytes(i2) != i2) {
            throw new IOException("Couldn't seek up to the byteCount");
        }
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.mByteOrder = byteOrder;
    }

    @Override // java.io.DataInput
    public int skipBytes(int i) throws IOException {
        int min = Math.min(i, this.mLength - this.mPosition);
        int i2 = 0;
        while (i2 < min) {
            i2 += this.mDataInputStream.skipBytes(min - i2);
        }
        this.mPosition += i2;
        return i2;
    }
}
