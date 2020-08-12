package org.jcodec.movtool;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.containers.mp4.Chunk;
import org.jcodec.containers.mp4.ChunkReader;
import org.jcodec.containers.mp4.ChunkWriter;
import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.AliasBox;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.ChunkOffsetsBox;
import org.jcodec.containers.mp4.boxes.DataRefBox;
import org.jcodec.containers.mp4.boxes.Header;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.containers.mp4.boxes.NodeBox;
import org.jcodec.containers.mp4.boxes.TrakBox;
import org.jcodec.containers.mp4.boxes.UrlBox;
import org.jcodec.platform.Platform;

public class Flattern {
    public List<ProgressListener> listeners = new ArrayList();

    public interface ProgressListener {
        void trigger(int i);
    }

    private int calcProgress(int i, int i2, int i3) {
        int i4 = (i2 * 100) / i;
        if (i3 >= i4) {
            return i3;
        }
        for (ProgressListener progressListener : this.listeners) {
            progressListener.trigger(i4);
        }
        return i4;
    }

    private int calcSpaceReq(MovieBox movieBox) {
        TrakBox[] tracks = movieBox.getTracks();
        int i = 0;
        for (TrakBox trakBox : tracks) {
            ChunkOffsetsBox stco = trakBox.getStco();
            if (stco != null) {
                i += stco.getChunkOffsets().length * 4;
            }
        }
        return i;
    }

    public static void main1(String[] strArr) throws Exception {
        if (strArr.length < 2) {
            System.out.println("Syntax: self <ref movie> <out movie>");
            System.exit(-1);
        }
        File file = new File(strArr[1]);
        Platform.deleteFile(file);
        FileChannelWrapper fileChannelWrapper = null;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(new File(strArr[0]));
            new Flattern().flattern(MP4Util.parseFullMovieChannel(fileChannelWrapper), file);
        } finally {
            if (fileChannelWrapper != null) {
                fileChannelWrapper.close();
            }
        }
    }

    private void writeHeader(Header header, SeekableByteChannel seekableByteChannel) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(16);
        header.write(allocate);
        allocate.flip();
        seekableByteChannel.write(allocate);
    }

    public void addProgressListener(ProgressListener progressListener) {
        this.listeners.add(progressListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0016  */
    public void flattern(MP4Util.Movie movie, File file) throws IOException {
        FileChannelWrapper fileChannelWrapper;
        Platform.deleteFile(file);
        try {
            fileChannelWrapper = NIOUtils.writableChannel(file);
            try {
                flatternChannel(movie, fileChannelWrapper);
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
            } catch (Throwable th) {
                th = th;
                if (fileChannelWrapper != null) {
                    fileChannelWrapper.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileChannelWrapper = null;
            if (fileChannelWrapper != null) {
            }
            throw th;
        }
    }

    public void flatternChannel(MP4Util.Movie movie, SeekableByteChannel seekableByteChannel) throws IOException {
        long j;
        int i;
        int i2;
        movie.getFtyp();
        MovieBox moov = movie.getMoov();
        if (moov.isPureRefMovie()) {
            seekableByteChannel.setPosition(0);
            MP4Util.writeFullMovie(seekableByteChannel, movie);
            seekableByteChannel.write(ByteBuffer.allocate(calcSpaceReq(moov)));
            long position = seekableByteChannel.position();
            String str = "mdat";
            writeHeader(Header.createHeader(str, 4294967297L), seekableByteChannel);
            SeekableByteChannel[][] inputs = getInputs(moov);
            TrakBox[] tracks = moov.getTracks();
            ChunkReader[] chunkReaderArr = new ChunkReader[tracks.length];
            ChunkWriter[] chunkWriterArr = new ChunkWriter[tracks.length];
            Chunk[] chunkArr = new Chunk[tracks.length];
            long[] jArr = new long[tracks.length];
            int i3 = 0;
            int i4 = 0;
            while (i4 < tracks.length) {
                chunkReaderArr[i4] = new ChunkReader(tracks[i4]);
                int size = i3 + chunkReaderArr[i4].size();
                chunkWriterArr[i4] = new ChunkWriter(tracks[i4], inputs[i4], seekableByteChannel);
                chunkArr[i4] = chunkReaderArr[i4].next();
                if (tracks[i4].isVideo()) {
                    jArr[i4] = (long) (moov.getTimescale() * 2);
                }
                i4++;
                str = str;
                i3 = size;
            }
            int i5 = 0;
            int i6 = 0;
            while (true) {
                int i7 = -1;
                int i8 = 0;
                while (i8 < chunkReaderArr.length) {
                    if (chunkArr[i8] == null) {
                        i2 = i5;
                        j = position;
                        i = i6;
                    } else {
                        if (i7 == -1) {
                            i2 = i5;
                            j = position;
                            i = i6;
                        } else {
                            i2 = i5;
                            i = i6;
                            j = position;
                            if (moov.rescale(chunkArr[i8].getStartTv(), (long) tracks[i8].getTimescale()) + jArr[i8] >= moov.rescale(chunkArr[i7].getStartTv(), (long) tracks[i7].getTimescale()) + jArr[i7]) {
                            }
                        }
                        i7 = i8;
                    }
                    i8++;
                    i5 = i2;
                    i6 = i;
                    position = j;
                }
                if (i7 == -1) {
                    break;
                }
                chunkWriterArr[i7].write(chunkArr[i7]);
                chunkArr[i7] = chunkReaderArr[i7].next();
                i5++;
                i6 = calcProgress(i3, i5, i6);
                i3 = i3;
                position = position;
            }
            for (int i9 = 0; i9 < tracks.length; i9++) {
                chunkWriterArr[i9].apply();
            }
            long position2 = seekableByteChannel.position() - position;
            seekableByteChannel.setPosition(0);
            MP4Util.writeFullMovie(seekableByteChannel, movie);
            long position3 = position - seekableByteChannel.position();
            if (position3 >= 0) {
                writeHeader(Header.createHeader("free", position3), seekableByteChannel);
                seekableByteChannel.setPosition(position);
                writeHeader(Header.createHeader(str, position2), seekableByteChannel);
                return;
            }
            throw new RuntimeException("Not enough space to write the header");
        }
        throw new IllegalArgumentException("movie should be reference");
    }

    /* access modifiers changed from: protected */
    public SeekableByteChannel[][] getInputs(MovieBox movieBox) throws IOException {
        TrakBox[] tracks = movieBox.getTracks();
        SeekableByteChannel[][] seekableByteChannelArr = new SeekableByteChannel[tracks.length][];
        int i = 0;
        while (i < tracks.length) {
            DataRefBox dataRefBox = (DataRefBox) NodeBox.findFirstPath(tracks[i], DataRefBox.class, Box.path("mdia.minf.dinf.dref"));
            if (dataRefBox != null) {
                List<Box> boxes = dataRefBox.getBoxes();
                SeekableByteChannel[] seekableByteChannelArr2 = new SeekableByteChannel[boxes.size()];
                SeekableByteChannel[] seekableByteChannelArr3 = new SeekableByteChannel[boxes.size()];
                for (int i2 = 0; i2 < seekableByteChannelArr2.length; i2++) {
                    seekableByteChannelArr3[i2] = resolveDataRef(boxes.get(i2));
                }
                seekableByteChannelArr[i] = seekableByteChannelArr3;
                i++;
            } else {
                throw new RuntimeException("No data references");
            }
        }
        return seekableByteChannelArr;
    }

    public SeekableByteChannel resolveDataRef(Box box) throws IOException {
        if (box instanceof UrlBox) {
            String url = ((UrlBox) box).getUrl();
            if (url.startsWith("file://")) {
                return NIOUtils.readableChannel(new File(url.substring(7)));
            }
            throw new RuntimeException("Only file:// urls are supported in data reference");
        } else if (box instanceof AliasBox) {
            String unixPath = ((AliasBox) box).getUnixPath();
            if (unixPath != null) {
                return NIOUtils.readableChannel(new File(unixPath));
            }
            throw new RuntimeException("Could not resolve alias");
        } else {
            throw new RuntimeException(box.getHeader().getFourcc() + " dataref type is not supported");
        }
    }
}
