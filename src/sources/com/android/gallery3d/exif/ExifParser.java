package com.android.gallery3d.exif;

import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

class ExifParser {
    protected static final short BIG_ENDIAN_TAG = 19789;
    protected static final int DEFAULT_IFD0_OFFSET = 8;
    public static final int EVENT_COMPRESSED_IMAGE = 3;
    public static final int EVENT_END = 5;
    public static final int EVENT_NEW_TAG = 1;
    public static final int EVENT_START_OF_IFD = 0;
    public static final int EVENT_UNCOMPRESSED_STRIP = 4;
    public static final int EVENT_VALUE_OF_REGISTERED_TAG = 2;
    protected static final int EXIF_HEADER = 1165519206;
    protected static final short EXIF_HEADER_TAIL = 0;
    protected static final short LITTLE_ENDIAN_TAG = 18761;
    private static final boolean LOGV = false;
    protected static final int OFFSET_SIZE = 2;
    public static final int OPTION_IFD_0 = 1;
    public static final int OPTION_IFD_1 = 2;
    public static final int OPTION_IFD_EXIF = 4;
    public static final int OPTION_IFD_GPS = 8;
    public static final int OPTION_IFD_INTEROPERABILITY = 16;
    public static final int OPTION_THUMBNAIL = 32;
    private static final String TAG = "ExifParser";
    private static final short TAG_EXIF_IFD = ExifInterface.getTrueTagKey(ExifInterface.TAG_EXIF_IFD);
    private static final short TAG_GPS_IFD = ExifInterface.getTrueTagKey(ExifInterface.TAG_GPS_IFD);
    private static final short TAG_INTEROPERABILITY_IFD = ExifInterface.getTrueTagKey(ExifInterface.TAG_INTEROPERABILITY_IFD);
    private static final short TAG_JPEG_INTERCHANGE_FORMAT = ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT);
    private static final short TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
    protected static final int TAG_SIZE = 12;
    private static final short TAG_STRIP_BYTE_COUNTS = ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_BYTE_COUNTS);
    private static final short TAG_STRIP_OFFSETS = ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_OFFSETS);
    protected static final short TIFF_HEADER_TAIL = 42;
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private int mApp1End;
    private boolean mContainExifData = false;
    private final TreeMap<Integer, Object> mCorrespondingEvent = new TreeMap<>();
    private byte[] mDataAboveIfd0;
    private int mIfd0Position;
    private int mIfdStartOffset = 0;
    private int mIfdType;
    private ImageEvent mImageEvent;
    private final ExifInterface mInterface;
    private ExifTag mJpegSizeTag;
    private boolean mNeedToParseOffsetsInCurrentIfd;
    private int mNumOfTagInIfd = 0;
    private int mOffsetToApp1EndFromSOF = 0;
    private final int mOptions;
    private int mStripCount;
    private ExifTag mStripSizeTag;
    private ExifTag mTag;
    private int mTiffStartPosition;
    private final CountedDataInputStream mTiffStream;

    private static class ExifTagEvent {
        boolean isRequested;
        ExifTag tag;

        ExifTagEvent(ExifTag exifTag, boolean z) {
            this.tag = exifTag;
            this.isRequested = z;
        }
    }

    private static class IfdEvent {
        int ifd;
        boolean isRequested;

        IfdEvent(int i, boolean z) {
            this.ifd = i;
            this.isRequested = z;
        }
    }

    private static class ImageEvent {
        int stripIndex;
        int type;

        ImageEvent(int i) {
            this.stripIndex = 0;
            this.type = i;
        }

        ImageEvent(int i, int i2) {
            this.type = i;
            this.stripIndex = i2;
        }
    }

    private ExifParser(InputStream inputStream, int i, ExifInterface exifInterface) throws IOException, ExifInvalidFormatException {
        if (inputStream != null) {
            this.mInterface = exifInterface;
            this.mContainExifData = seekTiffData(inputStream);
            this.mTiffStream = new CountedDataInputStream(inputStream);
            this.mOptions = i;
            if (this.mContainExifData) {
                parseTiffHeader();
                long readUnsignedInt = this.mTiffStream.readUnsignedInt();
                if (readUnsignedInt <= 2147483647L) {
                    int i2 = (int) readUnsignedInt;
                    this.mIfd0Position = i2;
                    this.mIfdType = 0;
                    if (isIfdRequested(0) || needToParseOffsetsInCurrentIfd()) {
                        registerIfd(0, readUnsignedInt);
                        if (readUnsignedInt != 8) {
                            this.mDataAboveIfd0 = new byte[(i2 - 8)];
                            read(this.mDataAboveIfd0);
                            return;
                        }
                        return;
                    }
                    return;
                }
                throw new ExifInvalidFormatException("Invalid offset " + readUnsignedInt);
            }
            return;
        }
        throw new IOException("Null argument inputStream to ExifParser");
    }

    private boolean checkAllowed(int i, int i2) {
        int i3 = this.mInterface.getTagInfo().get(i2);
        if (i3 == 0) {
            return false;
        }
        return ExifInterface.isIfdAllowed(i3, i);
    }

    private void checkOffsetOrImageTag(ExifTag exifTag) {
        if (exifTag.getComponentCount() != 0) {
            short tagId = exifTag.getTagId();
            int ifd = exifTag.getIfd();
            if (tagId != TAG_EXIF_IFD || !checkAllowed(ifd, ExifInterface.TAG_EXIF_IFD)) {
                if (tagId != TAG_GPS_IFD || !checkAllowed(ifd, ExifInterface.TAG_GPS_IFD)) {
                    if (tagId != TAG_INTEROPERABILITY_IFD || !checkAllowed(ifd, ExifInterface.TAG_INTEROPERABILITY_IFD)) {
                        if (tagId != TAG_JPEG_INTERCHANGE_FORMAT || !checkAllowed(ifd, ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT)) {
                            if (tagId != TAG_JPEG_INTERCHANGE_FORMAT_LENGTH || !checkAllowed(ifd, ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH)) {
                                if (tagId != TAG_STRIP_OFFSETS || !checkAllowed(ifd, ExifInterface.TAG_STRIP_OFFSETS)) {
                                    if (tagId == TAG_STRIP_BYTE_COUNTS && checkAllowed(ifd, ExifInterface.TAG_STRIP_BYTE_COUNTS) && isThumbnailRequested() && exifTag.hasValue()) {
                                        this.mStripSizeTag = exifTag;
                                    }
                                } else if (!isThumbnailRequested()) {
                                } else {
                                    if (exifTag.hasValue()) {
                                        for (int i = 0; i < exifTag.getComponentCount(); i++) {
                                            if (exifTag.getDataType() == 3) {
                                                registerUncompressedStrip(i, exifTag.getValueAt(i));
                                            } else {
                                                registerUncompressedStrip(i, exifTag.getValueAt(i));
                                            }
                                        }
                                        return;
                                    }
                                    this.mCorrespondingEvent.put(Integer.valueOf(exifTag.getOffset()), new ExifTagEvent(exifTag, false));
                                }
                            } else if (isThumbnailRequested()) {
                                this.mJpegSizeTag = exifTag;
                            }
                        } else if (isThumbnailRequested()) {
                            registerCompressedImage(exifTag.getValueAt(0));
                        }
                    } else if (isIfdRequested(3)) {
                        registerIfd(3, exifTag.getValueAt(0));
                    }
                } else if (isIfdRequested(4)) {
                    registerIfd(4, exifTag.getValueAt(0));
                }
            } else if (isIfdRequested(2) || isIfdRequested(3)) {
                registerIfd(2, exifTag.getValueAt(0));
            }
        }
    }

    private boolean isIfdRequested(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i == 4 && (this.mOptions & 8) != 0 : (this.mOptions & 16) != 0 : (this.mOptions & 4) != 0 : (this.mOptions & 2) != 0 : (this.mOptions & 1) != 0;
    }

    private boolean isThumbnailRequested() {
        return (this.mOptions & 32) != 0;
    }

    private boolean needToParseOffsetsInCurrentIfd() {
        int i = this.mIfdType;
        if (i == 0) {
            return isIfdRequested(2) || isIfdRequested(4) || isIfdRequested(3) || isIfdRequested(1);
        }
        if (i == 1) {
            return isThumbnailRequested();
        }
        if (i != 2) {
            return false;
        }
        return isIfdRequested(3);
    }

    protected static ExifParser parse(InputStream inputStream, int i, ExifInterface exifInterface) throws IOException, ExifInvalidFormatException {
        return new ExifParser(inputStream, i, exifInterface);
    }

    protected static ExifParser parse(InputStream inputStream, ExifInterface exifInterface) throws IOException, ExifInvalidFormatException {
        return new ExifParser(inputStream, 63, exifInterface);
    }

    private void parseTiffHeader() throws IOException, ExifInvalidFormatException {
        short readShort = this.mTiffStream.readShort();
        if (18761 == readShort) {
            this.mTiffStream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        } else if (19789 == readShort) {
            this.mTiffStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        } else {
            throw new ExifInvalidFormatException("Invalid TIFF header");
        }
        if (this.mTiffStream.readShort() != 42) {
            throw new ExifInvalidFormatException("Invalid TIFF header");
        }
    }

    private ExifTag readTag() throws IOException, ExifInvalidFormatException {
        short readShort = this.mTiffStream.readShort();
        short readShort2 = this.mTiffStream.readShort();
        long readUnsignedInt = this.mTiffStream.readUnsignedInt();
        if (readUnsignedInt > 2147483647L) {
            throw new ExifInvalidFormatException("Number of component is larger then Integer.MAX_VALUE");
        } else if (!ExifTag.isValidType(readShort2)) {
            Log.w(TAG, String.format(Locale.ENGLISH, "Tag %04x: Invalid data type %d", Short.valueOf(readShort), Short.valueOf(readShort2)));
            this.mTiffStream.skip(4);
            return null;
        } else {
            int i = (int) readUnsignedInt;
            ExifTag exifTag = new ExifTag(readShort, readShort2, i, this.mIfdType, i != 0);
            int dataSize = exifTag.getDataSize();
            if (dataSize > 4) {
                long readUnsignedInt2 = this.mTiffStream.readUnsignedInt();
                if (readUnsignedInt2 > 2147483647L) {
                    throw new ExifInvalidFormatException("offset is larger then Integer.MAX_VALUE");
                } else if (readUnsignedInt2 >= ((long) this.mIfd0Position) || readShort2 != 7) {
                    exifTag.setOffset((int) readUnsignedInt2);
                } else {
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.mDataAboveIfd0, ((int) readUnsignedInt2) - 8, bArr, 0, i);
                    exifTag.setValue(bArr);
                }
            } else {
                boolean hasDefinedCount = exifTag.hasDefinedCount();
                exifTag.setHasDefinedCount(false);
                readFullTagValue(exifTag);
                exifTag.setHasDefinedCount(hasDefinedCount);
                this.mTiffStream.skip((long) (4 - dataSize));
                exifTag.setOffset(this.mTiffStream.getReadByteCount() - 4);
            }
            return exifTag;
        }
    }

    private void registerCompressedImage(long j) {
        this.mCorrespondingEvent.put(Integer.valueOf((int) j), new ImageEvent(3));
    }

    private void registerIfd(int i, long j) {
        this.mCorrespondingEvent.put(Integer.valueOf((int) j), new IfdEvent(i, isIfdRequested(i)));
    }

    private void registerUncompressedStrip(int i, long j) {
        this.mCorrespondingEvent.put(Integer.valueOf((int) j), new ImageEvent(4, i));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005e, code lost:
        com.android.camera.log.Log.w(com.android.gallery3d.exif.ExifParser.TAG, "Invalid JPEG format.");
     */
    private boolean seekTiffData(InputStream inputStream) throws IOException, ExifInvalidFormatException {
        CountedDataInputStream countedDataInputStream = new CountedDataInputStream(inputStream);
        if (countedDataInputStream.readShort() == -40) {
            short readShort = countedDataInputStream.readShort();
            while (true) {
                if (readShort == -39 || JpegHeader.isSofMarker(readShort)) {
                    break;
                }
                int readUnsignedShort = countedDataInputStream.readUnsignedShort();
                if (readShort == -31 && readUnsignedShort >= 8) {
                    int readInt = countedDataInputStream.readInt();
                    short readShort2 = countedDataInputStream.readShort();
                    readUnsignedShort -= 6;
                    if (readInt == EXIF_HEADER && readShort2 == 0) {
                        this.mTiffStartPosition = countedDataInputStream.getReadByteCount();
                        this.mApp1End = readUnsignedShort;
                        this.mOffsetToApp1EndFromSOF = this.mTiffStartPosition + this.mApp1End;
                        return true;
                    }
                }
                if (readUnsignedShort < 2) {
                    break;
                }
                long j = (long) (readUnsignedShort - 2);
                if (j != countedDataInputStream.skip(j)) {
                    break;
                }
                readShort = countedDataInputStream.readShort();
            }
            return false;
        }
        throw new ExifInvalidFormatException("Invalid JPEG format");
    }

    private void skipTo(int i) throws IOException {
        this.mTiffStream.skipTo((long) i);
        while (!this.mCorrespondingEvent.isEmpty() && this.mCorrespondingEvent.firstKey().intValue() < i) {
            this.mCorrespondingEvent.pollFirstEntry();
        }
    }

    /* access modifiers changed from: protected */
    public ByteOrder getByteOrder() {
        return this.mTiffStream.getByteOrder();
    }

    /* access modifiers changed from: protected */
    public int getCompressedImageSize() {
        ExifTag exifTag = this.mJpegSizeTag;
        if (exifTag == null) {
            return 0;
        }
        return (int) exifTag.getValueAt(0);
    }

    /* access modifiers changed from: protected */
    public int getCurrentIfd() {
        return this.mIfdType;
    }

    /* access modifiers changed from: protected */
    public int getOffsetToExifEndFromSOF() {
        return this.mOffsetToApp1EndFromSOF;
    }

    /* access modifiers changed from: protected */
    public int getStripCount() {
        return this.mStripCount;
    }

    /* access modifiers changed from: protected */
    public int getStripIndex() {
        return this.mImageEvent.stripIndex;
    }

    /* access modifiers changed from: protected */
    public int getStripSize() {
        ExifTag exifTag = this.mStripSizeTag;
        if (exifTag == null) {
            return 0;
        }
        return (int) exifTag.getValueAt(0);
    }

    /* access modifiers changed from: protected */
    public ExifTag getTag() {
        return this.mTag;
    }

    /* access modifiers changed from: protected */
    public int getTagCountInCurrentIfd() {
        return this.mNumOfTagInIfd;
    }

    /* access modifiers changed from: protected */
    public int getTiffStartPosition() {
        return this.mTiffStartPosition;
    }

    /* access modifiers changed from: protected */
    public int next() throws IOException, ExifInvalidFormatException {
        if (!this.mContainExifData) {
            return 5;
        }
        int readByteCount = this.mTiffStream.getReadByteCount();
        int i = this.mIfdStartOffset + 2 + (this.mNumOfTagInIfd * 12);
        if (readByteCount < i) {
            this.mTag = readTag();
            ExifTag exifTag = this.mTag;
            if (exifTag == null) {
                return next();
            }
            if (this.mNeedToParseOffsetsInCurrentIfd) {
                checkOffsetOrImageTag(exifTag);
            }
            return 1;
        }
        if (readByteCount == i) {
            if (this.mIfdType == 0) {
                long readUnsignedLong = readUnsignedLong();
                if ((isIfdRequested(1) || isThumbnailRequested()) && readUnsignedLong != 0) {
                    registerIfd(1, readUnsignedLong);
                }
            } else {
                int intValue = this.mCorrespondingEvent.size() > 0 ? this.mCorrespondingEvent.firstEntry().getKey().intValue() - this.mTiffStream.getReadByteCount() : 4;
                if (intValue < 4) {
                    Log.w(TAG, "Invalid size of link to next IFD: " + intValue);
                } else {
                    long readUnsignedLong2 = readUnsignedLong();
                    if (readUnsignedLong2 != 0) {
                        Log.w(TAG, "Invalid link to next IFD: " + readUnsignedLong2);
                    }
                }
            }
        }
        while (this.mCorrespondingEvent.size() != 0) {
            Map.Entry<Integer, Object> pollFirstEntry = this.mCorrespondingEvent.pollFirstEntry();
            Object value = pollFirstEntry.getValue();
            try {
                skipTo(pollFirstEntry.getKey().intValue());
                if (value instanceof IfdEvent) {
                    IfdEvent ifdEvent = (IfdEvent) value;
                    this.mIfdType = ifdEvent.ifd;
                    this.mNumOfTagInIfd = this.mTiffStream.readUnsignedShort();
                    this.mIfdStartOffset = pollFirstEntry.getKey().intValue();
                    if ((this.mNumOfTagInIfd * 12) + this.mIfdStartOffset + 2 > this.mApp1End) {
                        Log.w(TAG, "Invalid size of IFD " + this.mIfdType);
                        return 5;
                    }
                    this.mNeedToParseOffsetsInCurrentIfd = needToParseOffsetsInCurrentIfd();
                    if (ifdEvent.isRequested) {
                        return 0;
                    }
                    skipRemainingTagsInCurrentIfd();
                } else if (value instanceof ImageEvent) {
                    this.mImageEvent = (ImageEvent) value;
                    return this.mImageEvent.type;
                } else {
                    ExifTagEvent exifTagEvent = (ExifTagEvent) value;
                    this.mTag = exifTagEvent.tag;
                    if (this.mTag.getDataType() != 7) {
                        readFullTagValue(this.mTag);
                        checkOffsetOrImageTag(this.mTag);
                    }
                    if (exifTagEvent.isRequested) {
                        return 2;
                    }
                }
            } catch (IOException unused) {
                Log.w(TAG, "Failed to skip to data at: " + pollFirstEntry.getKey() + " for " + value.getClass().getName() + ", the file may be broken.");
            }
        }
        return 5;
    }

    /* access modifiers changed from: protected */
    public int read(byte[] bArr) throws IOException {
        return this.mTiffStream.read(bArr);
    }

    /* access modifiers changed from: protected */
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.mTiffStream.read(bArr, i, i2);
    }

    /* access modifiers changed from: protected */
    public void readFullTagValue(ExifTag exifTag) throws IOException {
        short dataType = exifTag.getDataType();
        if (dataType == 2 || dataType == 7 || dataType == 1) {
            int componentCount = exifTag.getComponentCount();
            if (this.mCorrespondingEvent.size() > 0 && this.mCorrespondingEvent.firstEntry().getKey().intValue() < this.mTiffStream.getReadByteCount() + componentCount) {
                Object value = this.mCorrespondingEvent.firstEntry().getValue();
                if (value instanceof ImageEvent) {
                    Log.w(TAG, "Thumbnail overlaps value for tag: \n" + exifTag.toString());
                    Map.Entry<Integer, Object> pollFirstEntry = this.mCorrespondingEvent.pollFirstEntry();
                    Log.w(TAG, "Invalid thumbnail offset: " + pollFirstEntry.getKey());
                } else {
                    if (value instanceof IfdEvent) {
                        Log.w(TAG, "Ifd " + ((IfdEvent) value).ifd + " overlaps value for tag: \n" + exifTag.toString());
                    } else if (value instanceof ExifTagEvent) {
                        Log.w(TAG, "Tag value for tag: \n" + ((ExifTagEvent) value).tag.toString() + " overlaps value for tag: \n" + exifTag.toString());
                    }
                    int intValue = this.mCorrespondingEvent.firstEntry().getKey().intValue() - this.mTiffStream.getReadByteCount();
                    Log.w(TAG, "Invalid size of tag: \n" + exifTag.toString() + " setting count to: " + intValue);
                    exifTag.forceSetComponentCount(intValue);
                }
            }
        }
        int i = 0;
        switch (exifTag.getDataType()) {
            case 1:
            case 7:
                byte[] bArr = new byte[exifTag.getComponentCount()];
                read(bArr);
                exifTag.setValue(bArr);
                return;
            case 2:
                exifTag.setValue(readString(exifTag.getComponentCount()));
                return;
            case 3:
                int[] iArr = new int[exifTag.getComponentCount()];
                int length = iArr.length;
                while (i < length) {
                    iArr[i] = readUnsignedShort();
                    i++;
                }
                exifTag.setValue(iArr);
                return;
            case 4:
                long[] jArr = new long[exifTag.getComponentCount()];
                int length2 = jArr.length;
                while (i < length2) {
                    jArr[i] = readUnsignedLong();
                    i++;
                }
                exifTag.setValue(jArr);
                return;
            case 5:
                Rational[] rationalArr = new Rational[exifTag.getComponentCount()];
                int length3 = rationalArr.length;
                while (i < length3) {
                    rationalArr[i] = readUnsignedRational();
                    i++;
                }
                exifTag.setValue(rationalArr);
                return;
            case 6:
            case 8:
            default:
                return;
            case 9:
                int[] iArr2 = new int[exifTag.getComponentCount()];
                int length4 = iArr2.length;
                while (i < length4) {
                    iArr2[i] = readLong();
                    i++;
                }
                exifTag.setValue(iArr2);
                return;
            case 10:
                Rational[] rationalArr2 = new Rational[exifTag.getComponentCount()];
                int length5 = rationalArr2.length;
                while (i < length5) {
                    rationalArr2[i] = readRational();
                    i++;
                }
                exifTag.setValue(rationalArr2);
                return;
        }
    }

    /* access modifiers changed from: protected */
    public int readLong() throws IOException {
        return this.mTiffStream.readInt();
    }

    /* access modifiers changed from: protected */
    public Rational readRational() throws IOException {
        return new Rational((long) readLong(), (long) readLong());
    }

    /* access modifiers changed from: protected */
    public String readString(int i) throws IOException {
        return readString(i, US_ASCII);
    }

    /* access modifiers changed from: protected */
    public String readString(int i, Charset charset) throws IOException {
        return i > 0 ? this.mTiffStream.readString(i, charset) : "";
    }

    /* access modifiers changed from: protected */
    public long readUnsignedLong() throws IOException {
        return ((long) readLong()) & 4294967295L;
    }

    /* access modifiers changed from: protected */
    public Rational readUnsignedRational() throws IOException {
        return new Rational(readUnsignedLong(), readUnsignedLong());
    }

    /* access modifiers changed from: protected */
    public int readUnsignedShort() throws IOException {
        return this.mTiffStream.readShort() & ExifInterface.ColorSpace.UNCALIBRATED;
    }

    /* access modifiers changed from: protected */
    public void registerForTagValue(ExifTag exifTag) {
        if (exifTag.getOffset() >= this.mTiffStream.getReadByteCount()) {
            this.mCorrespondingEvent.put(Integer.valueOf(exifTag.getOffset()), new ExifTagEvent(exifTag, true));
        }
    }

    /* access modifiers changed from: protected */
    public void skipRemainingTagsInCurrentIfd() throws IOException, ExifInvalidFormatException {
        int i = this.mIfdStartOffset + 2 + (this.mNumOfTagInIfd * 12);
        int readByteCount = this.mTiffStream.getReadByteCount();
        if (readByteCount <= i) {
            if (this.mNeedToParseOffsetsInCurrentIfd) {
                while (readByteCount < i) {
                    this.mTag = readTag();
                    readByteCount += 12;
                    ExifTag exifTag = this.mTag;
                    if (exifTag != null) {
                        checkOffsetOrImageTag(exifTag);
                    }
                }
            } else {
                skipTo(i);
            }
            long readUnsignedLong = readUnsignedLong();
            if (this.mIfdType != 0) {
                return;
            }
            if ((isIfdRequested(1) || isThumbnailRequested()) && readUnsignedLong > 0) {
                registerIfd(1, readUnsignedLong);
            }
        }
    }
}
