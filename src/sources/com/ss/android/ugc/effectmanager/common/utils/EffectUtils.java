package com.ss.android.ugc.effectmanager.common.utils;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectRequest;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.exception.MD5Exception;
import com.ss.android.ugc.effectmanager.common.exception.UrlNotExistException;
import com.ss.android.ugc.effectmanager.common.model.UrlModel;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.model.ProviderEffect;
import com.ss.android.ugc.effectmanager.network.EffectNetWorkerWrapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EffectUtils {
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0041 A[SYNTHETIC, Splitter:B:29:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004b A[SYNTHETIC, Splitter:B:34:0x004b] */
    public static File convertStreamToFile(InputStream inputStream, String str) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(str);
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream2.write(bArr, 0, read);
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    fileOutputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return file;
            } catch (IOException e4) {
                e = e4;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    FileUtils.removeFile(str);
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (inputStream != null) {
                }
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (IOException e7) {
            e = e7;
            e.printStackTrace();
            FileUtils.removeFile(str);
            throw e;
        }
    }

    public static String convertStreamToString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    try {
                        break;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            inputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
            inputStream.close();
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
        return sb.toString();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static File download(EffectConfiguration effectConfiguration, String str, String str2) throws Exception {
        return download(effectConfiguration.getEffectNetWorker(), str, str2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static File download(EffectNetWorkerWrapper effectNetWorkerWrapper, String str, String str2) throws Exception {
        InputStream execute = effectNetWorkerWrapper.execute(new EffectRequest("GET", str));
        if (execute != null) {
            return convertStreamToFile(execute, str2);
        }
        throw new NetworkErrorException(ErrorConstants.EXCEPTION_DOWNLOAD_ERROR);
    }

    public static void downloadEffect(EffectConfiguration effectConfiguration, Effect effect) throws Exception {
        List<String> url = getUrl(effect.getFileUrl());
        if (url == null || url.isEmpty()) {
            throw new UrlNotExistException(ErrorConstants.EXCEPTION_DOWNLOAD_URL_ERROR);
        }
        int i = 0;
        while (i < url.size()) {
            try {
                String fileMD5 = MD5Utils.getFileMD5(download(effectConfiguration, url.get(i), effect.getZipPath()));
                if (fileMD5.equals(effect.getFileUrl().getUri())) {
                    FileUtils.unZip(effect.getZipPath(), effect.getUnzipPath());
                    return;
                }
                FileUtils.removeFile(effect.getZipPath());
                if (i != url.size() - 1) {
                    i++;
                } else {
                    throw new MD5Exception("downloadMD5: " + fileMD5 + " expectMD5:" + effect.getFileUrl().getUri());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (i == url.size() - 1) {
                    FileUtils.removeDir(effect.getUnzipPath());
                    throw e2;
                }
            }
        }
    }

    @NonNull
    public static String getUrl(@NonNull ProviderEffect providerEffect) {
        return (providerEffect.getSticker() == null || providerEffect.getSticker().getUrl() == null) ? "" : providerEffect.getSticker().getUrl();
    }

    public static List<String> getUrl(UrlModel urlModel) {
        return (urlModel == null || isUrlModelEmpty(urlModel)) ? new ArrayList() : urlModel.getUrlList();
    }

    public static boolean isEffectValid(Effect effect) {
        if (effect == null) {
            return false;
        }
        return !isUrlModelEmpty(effect.getFileUrl());
    }

    public static boolean isUrlModelEmpty(UrlModel urlModel) {
        return urlModel == null || urlModel.getUrlList() == null || urlModel.getUrlList().isEmpty();
    }

    public static void throwIllegalNullException(String str) {
        throw new IllegalArgumentException(str + " should not null");
    }
}
