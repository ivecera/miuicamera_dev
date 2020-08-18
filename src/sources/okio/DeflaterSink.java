package okio;

import java.io.IOException;
import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class DeflaterSink implements Sink {
    private boolean closed;
    private final Deflater deflater;
    private final BufferedSink sink;

    DeflaterSink(BufferedSink bufferedSink, Deflater deflater2) {
        if (bufferedSink == null) {
            throw new IllegalArgumentException("source == null");
        } else if (deflater2 != null) {
            this.sink = bufferedSink;
            this.deflater = deflater2;
        } else {
            throw new IllegalArgumentException("inflater == null");
        }
    }

    public DeflaterSink(Sink sink2, Deflater deflater2) {
        this(Okio.buffer(sink2), deflater2);
    }

    @IgnoreJRERequirement
    private void deflate(boolean z) throws IOException {
        Segment writableSegment;
        int i;
        Buffer buffer = this.sink.buffer();
        while (true) {
            writableSegment = buffer.writableSegment(1);
            if (z) {
                Deflater deflater2 = this.deflater;
                byte[] bArr = writableSegment.data;
                int i2 = writableSegment.limit;
                i = deflater2.deflate(bArr, i2, 8192 - i2, 2);
            } else {
                Deflater deflater3 = this.deflater;
                byte[] bArr2 = writableSegment.data;
                int i3 = writableSegment.limit;
                i = deflater3.deflate(bArr2, i3, 8192 - i3);
            }
            if (i > 0) {
                writableSegment.limit += i;
                buffer.size += (long) i;
                this.sink.emitCompleteSegments();
            } else if (this.deflater.needsInput()) {
                break;
            }
        }
        if (writableSegment.pos == writableSegment.limit) {
            buffer.head = writableSegment.pop();
            SegmentPool.recycle(writableSegment);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, okio.Sink
    public void close() throws IOException {
        if (!this.closed) {
            try {
                finishDeflate();
                th = null;
            } catch (Throwable th) {
                th = th;
            }
            try {
                this.deflater.end();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                }
            }
            try {
                this.sink.close();
            } catch (Throwable th3) {
                if (th == null) {
                    th = th3;
                }
            }
            this.closed = true;
            if (th != null) {
                Util.sneakyRethrow(th);
                throw null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void finishDeflate() throws IOException {
        this.deflater.finish();
        deflate(false);
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        deflate(true);
        this.sink.flush();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "DeflaterSink(" + this.sink + ")";
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        Util.checkOffsetAndCount(buffer.size, 0, j);
        while (j > 0) {
            Segment segment = buffer.head;
            int min = (int) Math.min(j, (long) (segment.limit - segment.pos));
            this.deflater.setInput(segment.data, segment.pos, min);
            deflate(false);
            long j2 = (long) min;
            buffer.size -= j2;
            segment.pos += min;
            if (segment.pos == segment.limit) {
                buffer.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            j -= j2;
        }
    }
}
