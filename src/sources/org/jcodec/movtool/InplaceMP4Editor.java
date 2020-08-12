package org.jcodec.movtool;

import java.io.File;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jcodec.common.Preconditions;
import org.jcodec.common.Tuple;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.containers.mp4.BoxFactory;
import org.jcodec.containers.mp4.BoxUtil;
import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.Header;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.containers.mp4.boxes.MovieFragmentBox;

public class InplaceMP4Editor {
    private List<Tuple._2<MP4Util.Atom, ByteBuffer>> doTheFix(SeekableByteChannel seekableByteChannel, MP4Edit mP4Edit) throws IOException {
        MP4Util.Atom moov = getMoov(seekableByteChannel);
        Preconditions.checkNotNull(moov);
        ByteBuffer fetchBox = fetchBox(seekableByteChannel, moov);
        MovieBox movieBox = (MovieBox) parseBox(fetchBox);
        LinkedList linkedList = new LinkedList();
        if (BoxUtil.containsBox(movieBox, "mvex")) {
            LinkedList<Tuple._2> linkedList2 = new LinkedList();
            for (MP4Util.Atom atom : getFragments(seekableByteChannel)) {
                ByteBuffer fetchBox2 = fetchBox(seekableByteChannel, atom);
                linkedList.add(Tuple.pair(atom, fetchBox2));
                MovieFragmentBox movieFragmentBox = (MovieFragmentBox) parseBox(fetchBox2);
                movieFragmentBox.setMovie(movieBox);
                linkedList2.add(Tuple.pair(fetchBox2, movieFragmentBox));
            }
            mP4Edit.applyToFragment(movieBox, (MovieFragmentBox[]) Tuple._2_project1(linkedList2).toArray(new MovieFragmentBox[0]));
            for (Tuple._2 _2 : linkedList2) {
                if (!rewriteBox(_2.v0, _2.v1)) {
                    return null;
                }
            }
        } else {
            mP4Edit.apply(movieBox);
        }
        if (!rewriteBox(fetchBox, movieBox)) {
            return null;
        }
        linkedList.add(Tuple.pair(moov, fetchBox));
        return linkedList;
    }

    private ByteBuffer fetchBox(SeekableByteChannel seekableByteChannel, MP4Util.Atom atom) throws IOException {
        seekableByteChannel.setPosition(atom.getOffset());
        return NIOUtils.fetchFromChannel(seekableByteChannel, (int) atom.getHeader().getSize());
    }

    private List<MP4Util.Atom> getFragments(SeekableByteChannel seekableByteChannel) throws IOException {
        LinkedList linkedList = new LinkedList();
        for (MP4Util.Atom atom : MP4Util.getRootAtoms(seekableByteChannel)) {
            if ("moof".equals(atom.getHeader().getFourcc())) {
                linkedList.add(atom);
            }
        }
        return linkedList;
    }

    private MP4Util.Atom getMoov(SeekableByteChannel seekableByteChannel) throws IOException {
        for (MP4Util.Atom atom : MP4Util.getRootAtoms(seekableByteChannel)) {
            if ("moov".equals(atom.getHeader().getFourcc())) {
                return atom;
            }
        }
        return null;
    }

    private Box parseBox(ByteBuffer byteBuffer) {
        return BoxUtil.parseBox(byteBuffer, Header.read(byteBuffer), BoxFactory.getDefault());
    }

    private void replaceBox(SeekableByteChannel seekableByteChannel, MP4Util.Atom atom, ByteBuffer byteBuffer) throws IOException {
        seekableByteChannel.setPosition(atom.getOffset());
        seekableByteChannel.write(byteBuffer);
    }

    private boolean rewriteBox(ByteBuffer byteBuffer, Box box) {
        try {
            byteBuffer.clear();
            box.write(byteBuffer);
            if (byteBuffer.hasRemaining()) {
                if (byteBuffer.remaining() < 8) {
                    return false;
                }
                byteBuffer.putInt(byteBuffer.remaining());
                byteBuffer.put(new byte[]{102, 114, 101, 101});
            }
            byteBuffer.flip();
            return true;
        } catch (BufferOverflowException unused) {
            return false;
        }
    }

    public boolean copy(File file, File file2, MP4Edit mP4Edit) throws IOException {
        FileChannelWrapper fileChannelWrapper;
        boolean z;
        FileChannelWrapper fileChannelWrapper2 = null;
        try {
            fileChannelWrapper = NIOUtils.readableChannel(file);
            try {
                fileChannelWrapper2 = NIOUtils.writableChannel(file2);
                List<Tuple._2<MP4Util.Atom, ByteBuffer>> doTheFix = doTheFix(fileChannelWrapper, mP4Edit);
                if (doTheFix == null) {
                    z = false;
                } else {
                    Map asMap = Tuple.asMap(Tuple._2map0(doTheFix, new Tuple.Mapper<MP4Util.Atom, Long>() {
                        /* class org.jcodec.movtool.InplaceMP4Editor.AnonymousClass1 */

                        public Long map(MP4Util.Atom atom) {
                            return Long.valueOf(atom.getOffset());
                        }
                    }));
                    for (MP4Util.Atom atom : MP4Util.getRootAtoms(fileChannelWrapper)) {
                        ByteBuffer byteBuffer = (ByteBuffer) asMap.get(Long.valueOf(atom.getOffset()));
                        if (byteBuffer != null) {
                            fileChannelWrapper2.write(byteBuffer);
                        } else {
                            atom.copy(fileChannelWrapper, fileChannelWrapper2);
                        }
                    }
                    z = true;
                }
                NIOUtils.closeQuietly(fileChannelWrapper);
                NIOUtils.closeQuietly(fileChannelWrapper2);
                return z;
            } catch (Throwable th) {
                th = th;
                NIOUtils.closeQuietly(fileChannelWrapper);
                NIOUtils.closeQuietly(fileChannelWrapper2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileChannelWrapper = null;
            NIOUtils.closeQuietly(fileChannelWrapper);
            NIOUtils.closeQuietly(fileChannelWrapper2);
            throw th;
        }
    }

    public boolean modify(File file, MP4Edit mP4Edit) throws IOException {
        FileChannelWrapper fileChannelWrapper;
        boolean z;
        try {
            fileChannelWrapper = NIOUtils.rwChannel(file);
            try {
                List<Tuple._2<MP4Util.Atom, ByteBuffer>> doTheFix = doTheFix(fileChannelWrapper, mP4Edit);
                if (doTheFix == null) {
                    z = false;
                } else {
                    for (Tuple._2<MP4Util.Atom, ByteBuffer> _2 : doTheFix) {
                        replaceBox(fileChannelWrapper, _2.v0, _2.v1);
                    }
                    z = true;
                }
                NIOUtils.closeQuietly(fileChannelWrapper);
                return z;
            } catch (Throwable th) {
                th = th;
                NIOUtils.closeQuietly(fileChannelWrapper);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileChannelWrapper = null;
            NIOUtils.closeQuietly(fileChannelWrapper);
            throw th;
        }
    }

    public boolean replace(File file, MP4Edit mP4Edit) throws IOException {
        File parentFile = file.getParentFile();
        File file2 = new File(parentFile, "." + file.getName());
        if (!copy(file, file2, mP4Edit)) {
            return false;
        }
        file2.renameTo(file);
        return true;
    }
}
