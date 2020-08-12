package com.ss.android.medialib;

import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
    private static final String DEFAULT_FOLDER_NAME = "BDMedia";
    protected static String msFolderPath;

    public static boolean checkFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static File createFile(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            if (!z) {
                file.mkdirs();
            } else {
                try {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file.createNewFile();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x00df A[SYNTHETIC, Splitter:B:105:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x00e9 A[SYNTHETIC, Splitter:B:110:0x00e9] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x00f3 A[SYNTHETIC, Splitter:B:115:0x00f3] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x00fd A[SYNTHETIC, Splitter:B:120:0x00fd] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0082 A[SYNTHETIC, Splitter:B:57:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x008c A[SYNTHETIC, Splitter:B:62:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0096 A[SYNTHETIC, Splitter:B:67:0x0096] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00a0 A[SYNTHETIC, Splitter:B:72:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00b2 A[SYNTHETIC, Splitter:B:82:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x00bc A[SYNTHETIC, Splitter:B:87:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x00c6 A[SYNTHETIC, Splitter:B:92:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x00d0 A[SYNTHETIC, Splitter:B:97:0x00d0] */
    public static boolean fileChannelCopy(String str, String str2) {
        FileChannel fileChannel;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileChannel fileChannel2;
        FileChannel fileChannel3;
        FileChannel fileChannel4;
        if (!isSdcardWritable()) {
            return false;
        }
        FileInputStream fileInputStream2 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        FileChannel fileChannel5 = null;
        FileInputStream fileInputStream3 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
                try {
                    fileChannel = fileInputStream.getChannel();
                    try {
                        fileChannel5 = fileOutputStream.getChannel();
                        fileChannel.transferTo(0, fileChannel.size(), fileChannel5);
                        try {
                            fileInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                        if (fileChannel5 == null) {
                            return true;
                        }
                        try {
                            fileChannel5.close();
                            return true;
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            return true;
                        }
                    } catch (FileNotFoundException e6) {
                        e = e6;
                        fileChannel2 = fileChannel5;
                        fileInputStream2 = fileInputStream;
                        e.printStackTrace();
                        if (fileInputStream2 != null) {
                        }
                        if (fileChannel != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (fileChannel2 != null) {
                        }
                        return false;
                    } catch (IOException e7) {
                        e = e7;
                        fileChannel4 = fileChannel5;
                        fileInputStream3 = fileInputStream;
                        try {
                            e.printStackTrace();
                            if (fileInputStream3 != null) {
                            }
                            if (fileChannel != null) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (fileChannel4 != null) {
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream = fileInputStream3;
                            fileChannel5 = fileChannel4;
                            if (fileInputStream != null) {
                            }
                            if (fileChannel != null) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (fileChannel5 != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                        }
                        if (fileChannel != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (fileChannel5 != null) {
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e8) {
                    e = e8;
                    fileChannel3 = null;
                    fileInputStream2 = fileInputStream;
                    fileChannel2 = fileChannel;
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                    }
                    if (fileChannel != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (fileChannel2 != null) {
                    }
                    return false;
                } catch (IOException e9) {
                    e = e9;
                    fileChannel = null;
                    fileInputStream3 = fileInputStream;
                    fileChannel4 = fileChannel;
                    e.printStackTrace();
                    if (fileInputStream3 != null) {
                    }
                    if (fileChannel != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (fileChannel4 != null) {
                    }
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    fileChannel = null;
                    if (fileInputStream != null) {
                    }
                    if (fileChannel != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (fileChannel5 != null) {
                    }
                    throw th;
                }
            } catch (FileNotFoundException e10) {
                e = e10;
                fileOutputStream = null;
                fileChannel3 = null;
                fileInputStream2 = fileInputStream;
                fileChannel2 = fileChannel;
                e.printStackTrace();
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e11) {
                        e11.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e12) {
                        e12.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e13) {
                        e13.printStackTrace();
                    }
                }
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e14) {
                        e14.printStackTrace();
                    }
                }
                return false;
            } catch (IOException e15) {
                e = e15;
                fileOutputStream = null;
                fileChannel = null;
                fileInputStream3 = fileInputStream;
                fileChannel4 = fileChannel;
                e.printStackTrace();
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException e16) {
                        e16.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e17) {
                        e17.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e18) {
                        e18.printStackTrace();
                    }
                }
                if (fileChannel4 != null) {
                    try {
                        fileChannel4.close();
                    } catch (IOException e19) {
                        e19.printStackTrace();
                    }
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                fileChannel = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e20) {
                        e20.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e21) {
                        e21.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
                if (fileChannel5 != null) {
                    try {
                        fileChannel5.close();
                    } catch (IOException e23) {
                        e23.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e24) {
            e = e24;
            fileOutputStream = null;
            fileChannel3 = null;
            fileChannel2 = fileChannel;
            e.printStackTrace();
            if (fileInputStream2 != null) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel2 != null) {
            }
            return false;
        } catch (IOException e25) {
            e = e25;
            fileOutputStream = null;
            fileChannel = null;
            fileChannel4 = fileChannel;
            e.printStackTrace();
            if (fileInputStream3 != null) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel4 != null) {
            }
            return false;
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
            fileChannel = null;
            fileInputStream = null;
            if (fileInputStream != null) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel5 != null) {
            }
            throw th;
        }
    }

    public static String getPath() {
        if (msFolderPath == null) {
            msFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DEFAULT_FOLDER_NAME;
            File file = new File(msFolderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return msFolderPath;
    }

    public static boolean isSdcardWritable() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception unused) {
            return false;
        }
    }
}
