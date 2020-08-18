package com.miui.extravideo.common;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MediaUtils {
    private static void addVideoToTrack(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(mediaExtractor.getTrackFormat(mediaExtractor.getSampleTrackIndex()).getInteger("max-input-size"));
        allocate.position(0);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        readBufferByExtractor(allocate, bufferInfo, mediaExtractor);
        while (bufferInfo.size > 0) {
            allocate.position(0);
            mediaMuxer.writeSampleData(i, allocate, bufferInfo);
            if ((bufferInfo.flags & 4) == 0) {
                mediaExtractor.advance();
                allocate.position(0);
                readBufferByExtractor(allocate, bufferInfo, mediaExtractor);
            } else {
                return;
            }
        }
    }

    public static long computePresentationTime(int i, int i2) {
        return (long) (((i * 1000000) / i2) + 132);
    }

    private static MediaExtractor generateExtractor(String str) throws IOException {
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(str);
        int i = 0;
        while (true) {
            if (i >= mediaExtractor.getTrackCount()) {
                break;
            } else if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith("video/")) {
                mediaExtractor.selectTrack(i);
                break;
            } else {
                i++;
            }
        }
        return mediaExtractor;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0082  */
    public static int mixVideo(String str, String str2, String str3, int i) {
        MediaExtractor mediaExtractor;
        MediaMuxer mediaMuxer;
        MediaExtractor mediaExtractor2;
        MediaMuxer mediaMuxer2 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        MediaExtractor mediaExtractor3 = null;
        try {
            mediaMuxer = new MediaMuxer(str, 0);
            try {
                mediaMuxer.setOrientationHint(i);
                mediaExtractor = generateExtractor(str2);
            } catch (Exception e2) {
                e = e2;
                mediaExtractor = null;
                mediaExtractor2 = null;
                mediaMuxer2 = mediaMuxer;
                try {
                    e.printStackTrace();
                    if (mediaMuxer2 != null) {
                        mediaMuxer2.stop();
                        mediaMuxer2.release();
                    }
                    if (mediaExtractor != null) {
                        mediaExtractor.release();
                    }
                    if (mediaExtractor2 != null) {
                        mediaExtractor2.release();
                    }
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    mediaMuxer = mediaMuxer2;
                    mediaExtractor3 = mediaExtractor2;
                    if (mediaMuxer != null) {
                        mediaMuxer.stop();
                        mediaMuxer.release();
                    }
                    if (mediaExtractor != null) {
                        mediaExtractor.release();
                    }
                    if (mediaExtractor3 != null) {
                        mediaExtractor3.release();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                mediaExtractor = null;
                if (mediaMuxer != null) {
                }
                if (mediaExtractor != null) {
                }
                if (mediaExtractor3 != null) {
                }
                throw th;
            }
            try {
                mediaExtractor3 = generateExtractor(str3);
                int addTrack = mediaMuxer.addTrack(mediaExtractor.getTrackFormat(mediaExtractor.getSampleTrackIndex()));
                int addTrack2 = mediaMuxer.addTrack(mediaExtractor3.getTrackFormat(mediaExtractor3.getSampleTrackIndex()));
                mediaMuxer.start();
                addVideoToTrack(mediaExtractor, mediaMuxer, addTrack);
                addVideoToTrack(mediaExtractor3, mediaMuxer, addTrack2);
                mediaMuxer.stop();
                mediaMuxer.release();
                if (mediaExtractor != null) {
                    mediaExtractor.release();
                }
                if (mediaExtractor3 == null) {
                    return addTrack2;
                }
                mediaExtractor3.release();
                return addTrack2;
            } catch (Exception e3) {
                e = e3;
                mediaExtractor2 = mediaExtractor3;
                mediaMuxer2 = mediaMuxer;
                e.printStackTrace();
                if (mediaMuxer2 != null) {
                }
                if (mediaExtractor != null) {
                }
                if (mediaExtractor2 != null) {
                }
                return -1;
            } catch (Throwable th3) {
                th = th3;
                if (mediaMuxer != null) {
                }
                if (mediaExtractor != null) {
                }
                if (mediaExtractor3 != null) {
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            mediaExtractor = null;
            mediaExtractor2 = null;
            e.printStackTrace();
            if (mediaMuxer2 != null) {
            }
            if (mediaExtractor != null) {
            }
            if (mediaExtractor2 != null) {
            }
            return -1;
        } catch (Throwable th4) {
            th = th4;
            mediaExtractor = null;
            mediaMuxer = null;
            if (mediaMuxer != null) {
            }
            if (mediaExtractor != null) {
            }
            if (mediaExtractor3 != null) {
            }
            throw th;
        }
    }

    private static void readBufferByExtractor(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo, MediaExtractor mediaExtractor) {
        bufferInfo.size = mediaExtractor.readSampleData(byteBuffer, 0);
        bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
        bufferInfo.flags = mediaExtractor.getSampleFlags();
        bufferInfo.offset = 0;
    }
}
