package com.bumptech.glide.a;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: DiskLruCache */
public final class b implements Closeable {
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    /* access modifiers changed from: private */
    public final File directory;
    final ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new a(null));
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    /* access modifiers changed from: private */
    public Writer journalWriter;
    private final Callable<Void> kk = new a(this);
    private final LinkedHashMap<String, c> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    /* access modifiers changed from: private */
    public int redundantOpCount;
    private long size = 0;
    /* access modifiers changed from: private */
    public final int valueCount;

    /* compiled from: DiskLruCache */
    private static final class a implements ThreadFactory {
        private a() {
        }

        /* synthetic */ a(a aVar) {
            this();
        }

        public synchronized Thread newThread(Runnable runnable) {
            Thread thread;
            thread = new Thread(runnable, "glide-disk-lru-cache-thread");
            thread.setPriority(1);
            return thread;
        }
    }

    /* renamed from: com.bumptech.glide.a.b$b  reason: collision with other inner class name */
    /* compiled from: DiskLruCache */
    public final class C0005b {
        /* access modifiers changed from: private */
        public final c entry;
        private boolean ik;
        /* access modifiers changed from: private */
        public final boolean[] written;

        private C0005b(c cVar) {
            this.entry = cVar;
            this.written = cVar.readable ? null : new boolean[b.this.valueCount];
        }

        /* synthetic */ C0005b(b bVar, c cVar, a aVar) {
            this(cVar);
        }

        private InputStream U(int i) throws IOException {
            synchronized (b.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (!this.entry.readable) {
                    return null;
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(this.entry.x(i));
                        return fileInputStream;
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                }
            }
        }

        public void abort() throws IOException {
            b.this.a(this, false);
        }

        public void abortUnlessCommitted() {
            if (!this.ik) {
                try {
                    abort();
                } catch (IOException unused) {
                }
            }
        }

        public void commit() throws IOException {
            b.this.a(this, true);
            this.ik = true;
        }

        public String getString(int i) throws IOException {
            InputStream U = U(i);
            if (U != null) {
                return b.n(U);
            }
            return null;
        }

        public void set(int i, String str) throws IOException {
            OutputStreamWriter outputStreamWriter = null;
            try {
                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(w(i)), e.UTF_8);
                try {
                    outputStreamWriter2.write(str);
                    e.closeQuietly(outputStreamWriter2);
                } catch (Throwable th) {
                    th = th;
                    outputStreamWriter = outputStreamWriter2;
                    e.closeQuietly(outputStreamWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                e.closeQuietly(outputStreamWriter);
                throw th;
            }
        }

        public File w(int i) throws IOException {
            File y;
            synchronized (b.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[i] = true;
                    }
                    y = this.entry.y(i);
                    if (!b.this.directory.exists()) {
                        b.this.directory.mkdirs();
                    }
                } else {
                    throw new IllegalStateException();
                }
            }
            return y;
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: DiskLruCache */
    public final class c {
        File[] cleanFiles;
        /* access modifiers changed from: private */
        public C0005b currentEditor;
        File[] dirtyFiles;
        /* access modifiers changed from: private */
        public final String key;
        /* access modifiers changed from: private */
        public final long[] lengths;
        /* access modifiers changed from: private */
        public boolean readable;
        /* access modifiers changed from: private */
        public long sequenceNumber;

        private c(String str) {
            this.key = str;
            this.lengths = new long[b.this.valueCount];
            this.cleanFiles = new File[b.this.valueCount];
            this.dirtyFiles = new File[b.this.valueCount];
            StringBuilder sb = new StringBuilder(str);
            sb.append('.');
            int length = sb.length();
            for (int i = 0; i < b.this.valueCount; i++) {
                sb.append(i);
                this.cleanFiles[i] = new File(b.this.directory, sb.toString());
                sb.append(".tmp");
                this.dirtyFiles[i] = new File(b.this.directory, sb.toString());
                sb.setLength(length);
            }
        }

        /* synthetic */ c(b bVar, String str, a aVar) {
            this(str);
        }

        private IOException invalidLengths(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        /* access modifiers changed from: private */
        public void setLengths(String[] strArr) throws IOException {
            if (strArr.length == b.this.valueCount) {
                int i = 0;
                while (i < strArr.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strArr[i]);
                        i++;
                    } catch (NumberFormatException unused) {
                        invalidLengths(strArr);
                        throw null;
                    }
                }
                return;
            }
            invalidLengths(strArr);
            throw null;
        }

        public String bj() throws IOException {
            StringBuilder sb = new StringBuilder();
            long[] jArr = this.lengths;
            for (long j : jArr) {
                sb.append(' ');
                sb.append(j);
            }
            return sb.toString();
        }

        public File x(int i) {
            return this.cleanFiles[i];
        }

        public File y(int i) {
            return this.dirtyFiles[i];
        }
    }

    /* compiled from: DiskLruCache */
    public final class d {
        private final File[] jk;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private d(String str, long j, File[] fileArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j;
            this.jk = fileArr;
            this.lengths = jArr;
        }

        /* synthetic */ d(b bVar, String str, long j, File[] fileArr, long[] jArr, a aVar) {
            this(str, j, fileArr, jArr);
        }

        public C0005b edit() throws IOException {
            return b.this.edit(this.key, this.sequenceNumber);
        }

        public long getLength(int i) {
            return this.lengths[i];
        }

        public String getString(int i) throws IOException {
            return b.n(new FileInputStream(this.jk[i]));
        }

        public File w(int i) {
            return this.jk[i];
        }
    }

    private b(File file, int i, int i2, long j) {
        this.directory = file;
        this.appVersion = i;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = i2;
        this.maxSize = j;
    }

    public static b a(File file, int i, int i2, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 > 0) {
            File file2 = new File(file, JOURNAL_FILE_BACKUP);
            if (file2.exists()) {
                File file3 = new File(file, JOURNAL_FILE);
                if (file3.exists()) {
                    file2.delete();
                } else {
                    a(file2, file3, false);
                }
            }
            b bVar = new b(file, i, i2, j);
            if (bVar.journalFile.exists()) {
                try {
                    bVar.readJournal();
                    bVar.processJournal();
                    return bVar;
                } catch (IOException e2) {
                    PrintStream printStream = System.out;
                    printStream.println("DiskLruCache " + file + " is corrupt: " + e2.getMessage() + ", removing");
                    bVar.delete();
                }
            }
            file.mkdirs();
            b bVar2 = new b(file, i, i2, j);
            bVar2.rebuildJournal();
            return bVar2;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0107, code lost:
        return;
     */
    public synchronized void a(C0005b bVar, boolean z) throws IOException {
        c a2 = bVar.entry;
        if (a2.currentEditor == bVar) {
            if (z && !a2.readable) {
                int i = 0;
                while (i < this.valueCount) {
                    if (!bVar.written[i]) {
                        bVar.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                    } else if (!a2.y(i).exists()) {
                        bVar.abort();
                        return;
                    } else {
                        i++;
                    }
                }
            }
            for (int i2 = 0; i2 < this.valueCount; i2++) {
                File y = a2.y(i2);
                if (!z) {
                    g(y);
                } else if (y.exists()) {
                    File x = a2.x(i2);
                    y.renameTo(x);
                    long j = a2.lengths[i2];
                    long length = x.length();
                    a2.lengths[i2] = length;
                    this.size = (this.size - j) + length;
                }
            }
            this.redundantOpCount++;
            C0005b unused = a2.currentEditor = (C0005b) null;
            if (a2.readable || z) {
                boolean unused2 = a2.readable = true;
                this.journalWriter.append((CharSequence) CLEAN);
                this.journalWriter.append(' ');
                this.journalWriter.append((CharSequence) a2.key);
                this.journalWriter.append((CharSequence) a2.bj());
                this.journalWriter.append('\n');
                if (z) {
                    long j2 = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1 + j2;
                    long unused3 = a2.sequenceNumber = j2;
                }
            } else {
                this.lruEntries.remove(a2.key);
                this.journalWriter.append((CharSequence) REMOVE);
                this.journalWriter.append(' ');
                this.journalWriter.append((CharSequence) a2.key);
                this.journalWriter.append('\n');
            }
            this.journalWriter.flush();
            if (this.size > this.maxSize || journalRebuildRequired()) {
                this.executorService.submit(this.kk);
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            g(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
        return null;
     */
    public synchronized C0005b edit(String str, long j) throws IOException {
        checkNotClosed();
        c cVar = this.lruEntries.get(str);
        if (j == -1 || (cVar != null && cVar.sequenceNumber == j)) {
            if (cVar == null) {
                cVar = new c(this, str, null);
                this.lruEntries.put(str, cVar);
            } else if (cVar.currentEditor != null) {
                return null;
            }
            C0005b bVar = new C0005b(this, cVar, null);
            C0005b unused = cVar.currentEditor = bVar;
            this.journalWriter.append((CharSequence) DIRTY);
            this.journalWriter.append(' ');
            this.journalWriter.append((CharSequence) str);
            this.journalWriter.append('\n');
            this.journalWriter.flush();
            return bVar;
        }
    }

    private static void g(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    /* access modifiers changed from: private */
    public boolean journalRebuildRequired() {
        int i = this.redundantOpCount;
        return i >= 2000 && i >= this.lruEntries.size();
    }

    /* access modifiers changed from: private */
    public static String n(InputStream inputStream) throws IOException {
        return e.readFully(new InputStreamReader(inputStream, e.UTF_8));
    }

    private void processJournal() throws IOException {
        g(this.journalFileTmp);
        Iterator<c> it = this.lruEntries.values().iterator();
        while (it.hasNext()) {
            c next = it.next();
            int i = 0;
            if (next.currentEditor == null) {
                while (i < this.valueCount) {
                    this.size += next.lengths[i];
                    i++;
                }
            } else {
                C0005b unused = next.currentEditor = (C0005b) null;
                while (i < this.valueCount) {
                    g(next.x(i));
                    g(next.y(i));
                    i++;
                }
                it.remove();
            }
        }
    }

    private void readJournal() throws IOException {
        d dVar = new d(new FileInputStream(this.journalFile), e.US_ASCII);
        try {
            String readLine = dVar.readLine();
            String readLine2 = dVar.readLine();
            String readLine3 = dVar.readLine();
            String readLine4 = dVar.readLine();
            String readLine5 = dVar.readLine();
            if (!MAGIC.equals(readLine) || !"1".equals(readLine2) || !Integer.toString(this.appVersion).equals(readLine3) || !Integer.toString(this.valueCount).equals(readLine4) || !"".equals(readLine5)) {
                throw new IOException("unexpected journal header: [" + readLine + ", " + readLine2 + ", " + readLine4 + ", " + readLine5 + "]");
            }
            int i = 0;
            while (true) {
                try {
                    readJournalLine(dVar.readLine());
                    i++;
                } catch (EOFException unused) {
                    this.redundantOpCount = i - this.lruEntries.size();
                    if (dVar.cj()) {
                        rebuildJournal();
                    } else {
                        this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), e.US_ASCII));
                    }
                    return;
                }
            }
        } finally {
            e.closeQuietly(dVar);
        }
    }

    private void readJournalLine(String str) throws IOException {
        String str2;
        int indexOf = str.indexOf(32);
        if (indexOf != -1) {
            int i = indexOf + 1;
            int indexOf2 = str.indexOf(32, i);
            if (indexOf2 == -1) {
                str2 = str.substring(i);
                if (indexOf == 6 && str.startsWith(REMOVE)) {
                    this.lruEntries.remove(str2);
                    return;
                }
            } else {
                str2 = str.substring(i, indexOf2);
            }
            c cVar = this.lruEntries.get(str2);
            if (cVar == null) {
                cVar = new c(this, str2, null);
                this.lruEntries.put(str2, cVar);
            }
            if (indexOf2 != -1 && indexOf == 5 && str.startsWith(CLEAN)) {
                String[] split = str.substring(indexOf2 + 1).split(" ");
                boolean unused = cVar.readable = true;
                C0005b unused2 = cVar.currentEditor = (C0005b) null;
                cVar.setLengths(split);
            } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(DIRTY)) {
                C0005b unused3 = cVar.currentEditor = new C0005b(this, cVar, null);
            } else if (indexOf2 != -1 || indexOf != 4 || !str.startsWith(READ)) {
                throw new IOException("unexpected journal line: " + str);
            }
        } else {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), e.US_ASCII));
        try {
            bufferedWriter.write(MAGIC);
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.appVersion));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.valueCount));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (c cVar : this.lruEntries.values()) {
                if (cVar.currentEditor != null) {
                    bufferedWriter.write("DIRTY " + cVar.key + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + cVar.key + cVar.bj() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.journalFile.exists()) {
                a(this.journalFile, this.journalFileBackup, true);
            }
            a(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), e.US_ASCII));
        } catch (Throwable th) {
            bufferedWriter.close();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            remove(this.lruEntries.entrySet().iterator().next().getKey());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        if (this.journalWriter != null) {
            Iterator it = new ArrayList(this.lruEntries.values()).iterator();
            while (it.hasNext()) {
                c cVar = (c) it.next();
                if (cVar.currentEditor != null) {
                    cVar.currentEditor.abort();
                }
            }
            trimToSize();
            this.journalWriter.close();
            this.journalWriter = null;
        }
    }

    public void delete() throws IOException {
        close();
        e.deleteContents(this.directory);
    }

    public C0005b edit(String str) throws IOException {
        return edit(str, -1);
    }

    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        this.journalWriter.flush();
    }

    public synchronized d get(String str) throws IOException {
        checkNotClosed();
        c cVar = this.lruEntries.get(str);
        if (cVar == null) {
            return null;
        }
        if (!cVar.readable) {
            return null;
        }
        for (File file : cVar.cleanFiles) {
            if (!file.exists()) {
                return null;
            }
        }
        this.redundantOpCount++;
        this.journalWriter.append((CharSequence) READ);
        this.journalWriter.append(' ');
        this.journalWriter.append((CharSequence) str);
        this.journalWriter.append('\n');
        if (journalRebuildRequired()) {
            this.executorService.submit(this.kk);
        }
        return new d(this, str, cVar.sequenceNumber, cVar.cleanFiles, cVar.lengths, null);
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized boolean isClosed() {
        return this.journalWriter == null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x008c, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008e, code lost:
        return false;
     */
    public synchronized boolean remove(String str) throws IOException {
        checkNotClosed();
        c cVar = this.lruEntries.get(str);
        if (cVar != null) {
            if (cVar.currentEditor == null) {
                for (int i = 0; i < this.valueCount; i++) {
                    File x = cVar.x(i);
                    if (x.exists()) {
                        if (!x.delete()) {
                            throw new IOException("failed to delete " + x);
                        }
                    }
                    this.size -= cVar.lengths[i];
                    cVar.lengths[i] = 0;
                }
                this.redundantOpCount++;
                this.journalWriter.append((CharSequence) REMOVE);
                this.journalWriter.append(' ');
                this.journalWriter.append((CharSequence) str);
                this.journalWriter.append('\n');
                this.lruEntries.remove(str);
                if (journalRebuildRequired()) {
                    this.executorService.submit(this.kk);
                }
            }
        }
    }

    public synchronized void setMaxSize(long j) {
        this.maxSize = j;
        this.executorService.submit(this.kk);
    }

    public synchronized long size() {
        return this.size;
    }
}
