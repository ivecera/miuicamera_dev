package com.android.camera.aftersales.strategies;

import com.android.camera.aftersales.interfaces.ITrimStrategy;
import com.android.camera.module.impl.component.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SimpleTrimStrategy implements ITrimStrategy {
    public static final boolean DEBUG = false;
    public static final int LINE_SIZE = 36;
    public static final int MAX_ALLOW_LINES = 116508;
    public static final int MAX_SIZE = 4194304;
    private String mFilePath;

    public SimpleTrimStrategy(String str) {
        this.mFilePath = str;
    }

    @Override // com.android.camera.aftersales.interfaces.ITrimStrategy
    public void doTrim() {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(this.mFilePath, "rw");
            try {
                long filePointer = randomAccessFile.getFilePointer();
                int i = 0;
                while (true) {
                    if (i >= 38836) {
                        break;
                    } else if (randomAccessFile.readLine() == null) {
                        break;
                    } else {
                        i++;
                    }
                }
                long filePointer2 = randomAccessFile.getFilePointer();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = randomAccessFile.read(bArr);
                    if (-1 != read) {
                        randomAccessFile.seek(filePointer);
                        randomAccessFile.write(bArr, 0, read);
                        long j = (long) read;
                        filePointer2 += j;
                        filePointer += j;
                        randomAccessFile.seek(filePointer2);
                    } else {
                        randomAccessFile.setLength(filePointer);
                        FileUtils.close(randomAccessFile);
                        return;
                    }
                }
            } catch (FileNotFoundException e2) {
                e = e2;
                randomAccessFile2 = randomAccessFile;
                e.printStackTrace();
                FileUtils.close(randomAccessFile2);
            } catch (IOException e3) {
                e = e3;
                randomAccessFile2 = randomAccessFile;
                e.printStackTrace();
                FileUtils.close(randomAccessFile2);
            } catch (Throwable th) {
                th = th;
                FileUtils.close(randomAccessFile);
                throw th;
            }
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            FileUtils.close(randomAccessFile2);
        } catch (IOException e5) {
            e = e5;
            e.printStackTrace();
            FileUtils.close(randomAccessFile2);
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile = randomAccessFile2;
            FileUtils.close(randomAccessFile);
            throw th;
        }
    }

    @Override // com.android.camera.aftersales.interfaces.ITrimStrategy
    public boolean exceedLimit() {
        return new File(this.mFilePath).length() > 4194304;
    }
}
