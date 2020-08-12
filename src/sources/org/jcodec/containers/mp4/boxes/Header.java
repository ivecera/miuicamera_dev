package org.jcodec.containers.mp4.boxes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.jcodec.common.JCodecUtil2;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.io.StringReader;
import org.jcodec.platform.Platform;

public class Header {
    public static final byte[] FOURCC_FREE = {102, 114, 101, 101};
    private static final long MAX_UNSIGNED_INT = 4294967296L;
    private String fourcc;
    private boolean lng;
    private long size;

    public Header(String str) {
        this.fourcc = str;
    }

    public static Header createHeader(String str, long j) {
        Header header = new Header(str);
        header.size = j;
        return header;
    }

    public static int estimateHeaderSize(int i) {
        return ((long) (i + 8)) > MAX_UNSIGNED_INT ? 16 : 8;
    }

    public static Header newHeader(String str, long j, boolean z) {
        Header header = new Header(str);
        header.size = j;
        header.lng = z;
        return header;
    }

    public static Header read(ByteBuffer byteBuffer) {
        long j = 0;
        while (byteBuffer.remaining() >= 4) {
            j = Platform.unsignedInt(byteBuffer.getInt());
            if (j != 0) {
                break;
            }
        }
        if (byteBuffer.remaining() < 4 || (j < 8 && j != 1)) {
            return null;
        }
        String readString = NIOUtils.readString(byteBuffer, 4);
        boolean z = false;
        if (j == 1) {
            if (byteBuffer.remaining() < 8) {
                return null;
            }
            z = true;
            j = byteBuffer.getLong();
        }
        return newHeader(readString, j, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Header.class != obj.getClass()) {
            return false;
        }
        Header header = (Header) obj;
        String str = this.fourcc;
        if (str == null) {
            if (header.fourcc != null) {
                return false;
            }
        } else if (!str.equals(header.fourcc)) {
            return false;
        }
        return true;
    }

    public long getBodySize() {
        return this.size - headerSize();
    }

    public String getFourcc() {
        return this.fourcc;
    }

    public long getSize() {
        return this.size;
    }

    public int hashCode() {
        String str = this.fourcc;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public long headerSize() {
        return (this.lng || this.size > MAX_UNSIGNED_INT) ? 16 : 8;
    }

    public byte[] readContents(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; ((long) i) < this.size - headerSize(); i++) {
            byteArrayOutputStream.write(inputStream.read());
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void setBodySize(int i) {
        this.size = ((long) i) + headerSize();
    }

    public void skip(InputStream inputStream) throws IOException {
        StringReader.sureSkip(inputStream, this.size - headerSize());
    }

    public void write(ByteBuffer byteBuffer) {
        long j = this.size;
        if (j > MAX_UNSIGNED_INT) {
            byteBuffer.putInt(1);
        } else {
            byteBuffer.putInt((int) j);
        }
        byte[] asciiString = JCodecUtil2.asciiString(this.fourcc);
        if (asciiString == null || asciiString.length != 4) {
            byteBuffer.put(FOURCC_FREE);
        } else {
            byteBuffer.put(asciiString);
        }
        long j2 = this.size;
        if (j2 > MAX_UNSIGNED_INT) {
            byteBuffer.putLong(j2);
        }
    }

    public void writeChannel(SeekableByteChannel seekableByteChannel) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(16);
        write(allocate);
        allocate.flip();
        seekableByteChannel.write(allocate);
    }
}
