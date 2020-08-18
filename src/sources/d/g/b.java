package d.g;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: IOUtils */
public class b {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final ThreadLocal<SoftReference<byte[]>> Gy = new ThreadLocal<>();
    private static final ThreadLocal<SoftReference<char[]>> Hy = new ThreadLocal<>();
    private static final String LINE_SEPARATOR;

    static {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println();
        printWriter.flush();
        LINE_SEPARATOR = stringWriter.toString();
        printWriter.close();
    }

    protected b() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static InputStream M(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public static void a(InputStream inputStream, Writer writer) throws IOException {
        copy(new InputStreamReader(inputStream), writer);
    }

    public static void a(InputStream inputStream, Writer writer, String str) throws IOException {
        copy((str == null || str.length() == 0) ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str), writer);
    }

    public static void a(OutputStream outputStream, String str) throws IOException {
        if (str != null) {
            outputStream.write(str.getBytes());
        }
    }

    public static void a(OutputStream outputStream, String str, String str2) throws IOException {
        if (str != null) {
            outputStream.write((str2 == null || str2.length() == 0) ? str.getBytes() : str.getBytes(str2));
        }
    }

    public static void a(OutputStream outputStream, Collection<Object> collection, String str) throws IOException {
        if (collection != null) {
            if (str == null) {
                str = LINE_SEPARATOR;
            }
            for (Object obj : collection) {
                if (obj != null) {
                    outputStream.write(obj.toString().getBytes());
                }
                outputStream.write(str.getBytes());
            }
        }
    }

    public static void a(OutputStream outputStream, Collection<Object> collection, String str, String str2) throws IOException {
        if (collection != null) {
            if (str == null) {
                str = LINE_SEPARATOR;
            }
            for (Object obj : collection) {
                if (obj != null) {
                    outputStream.write(obj.toString().getBytes(str2));
                }
                outputStream.write(str.getBytes(str2));
            }
        }
    }

    public static void a(OutputStream outputStream, byte[] bArr) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    public static void a(OutputStream outputStream, char[] cArr) throws IOException {
        if (cArr != null) {
            outputStream.write(new String(cArr).getBytes());
        }
    }

    public static void a(OutputStream outputStream, char[] cArr, String str) throws IOException {
        if (cArr != null) {
            outputStream.write((str == null || str.length() == 0) ? new String(cArr).getBytes() : new String(cArr).getBytes(str));
        }
    }

    public static void a(Reader reader, OutputStream outputStream) throws IOException {
        copy(reader, new OutputStreamWriter(outputStream));
    }

    public static void a(Reader reader, OutputStream outputStream, String str) throws IOException {
        copy(reader, (str == null || str.length() == 0) ? new OutputStreamWriter(outputStream) : new OutputStreamWriter(outputStream, str));
    }

    public static void a(Writer writer, String str) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    public static void a(Writer writer, Collection<Object> collection, String str) throws IOException {
        if (collection != null) {
            if (str == null) {
                str = LINE_SEPARATOR;
            }
            for (Object obj : collection) {
                if (obj != null) {
                    writer.write(obj.toString());
                }
                writer.write(str);
            }
        }
    }

    public static void a(Writer writer, byte[] bArr) throws IOException {
        if (bArr != null) {
            writer.write(new String(bArr));
        }
    }

    public static void a(Writer writer, byte[] bArr, String str) throws IOException {
        if (bArr != null) {
            writer.write((str == null || str.length() == 0) ? new String(bArr) : new String(bArr, str));
        }
    }

    public static void a(Writer writer, char[] cArr) throws IOException {
        if (cArr != null) {
            writer.write(cArr);
        }
    }

    public static byte[] a(Reader reader, String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(reader, byteArrayOutputStream, str);
        return byteArrayOutputStream.toByteArray();
    }

    public static List<String> b(Reader reader) throws IOException {
        BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            arrayList.add(readLine);
        }
    }

    public static void b(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException unused) {
            }
        }
    }

    public static List<String> c(InputStream inputStream, String str) throws IOException {
        return b((str == null || str.length() == 0) ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str));
    }

    public static byte[] c(Reader reader) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(reader, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:1|2|3|4|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
    }

    public static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException unused) {
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] qo = qo();
        long j = 0;
        while (true) {
            int read = inputStream.read(qo);
            if (read != -1) {
                outputStream.write(qo, 0, read);
                j += (long) read;
            } else {
                outputStream.flush();
                return j;
            }
        }
    }

    public static long copy(Reader reader, Writer writer) throws IOException {
        char[] ro = ro();
        long j = 0;
        while (true) {
            int read = reader.read(ro);
            if (read != -1) {
                writer.write(ro, 0, read);
                j += (long) read;
            } else {
                writer.flush();
                return j;
            }
        }
    }

    public static String d(InputStream inputStream, String str) throws IOException {
        StringWriter stringWriter = new StringWriter();
        a(inputStream, stringWriter, str);
        return stringWriter.toString();
    }

    public static String d(Reader reader) throws IOException {
        StringWriter stringWriter = new StringWriter();
        copy(reader, stringWriter);
        return stringWriter.toString();
    }

    public static List<String> k(InputStream inputStream) throws IOException {
        return b(new InputStreamReader(inputStream));
    }

    public static byte[] l(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String m(InputStream inputStream) throws IOException {
        StringWriter stringWriter = new StringWriter();
        a(inputStream, stringWriter);
        return stringWriter.toString();
    }

    private static byte[] qo() {
        SoftReference<byte[]> softReference = Gy.get();
        byte[] bArr = softReference != null ? softReference.get() : null;
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = new byte[4096];
        Gy.set(new SoftReference<>(bArr2));
        return bArr2;
    }

    private static char[] ro() {
        SoftReference<char[]> softReference = Hy.get();
        char[] cArr = softReference != null ? softReference.get() : null;
        if (cArr != null) {
            return cArr;
        }
        char[] cArr2 = new char[4096];
        Hy.set(new SoftReference<>(cArr2));
        return cArr2;
    }

    public static InputStream t(String str, String str2) throws UnsupportedEncodingException {
        return new ByteArrayInputStream((str2 == null || str2.length() == 0) ? str.getBytes() : str.getBytes(str2));
    }
}
