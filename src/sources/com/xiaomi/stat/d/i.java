package com.xiaomi.stat.d;

import android.text.TextUtils;
import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import com.xiaomi.stat.am;
import com.xiaomi.stat.d;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class i {

    /* renamed from: a  reason: collision with root package name */
    public static final int f561a = 10000;

    /* renamed from: b  reason: collision with root package name */
    public static final int f562b = 15000;

    /* renamed from: c  reason: collision with root package name */
    private static final String f563c = "GET";

    /* renamed from: d  reason: collision with root package name */
    private static final String f564d = "POST";

    /* renamed from: e  reason: collision with root package name */
    private static final String f565e = "&";

    /* renamed from: f  reason: collision with root package name */
    private static final String f566f = "=";
    private static final String g = "UTF-8";

    private i() {
    }

    public static String a(String str) throws IOException {
        return a(str, null, false);
    }

    /* JADX DEBUG: Additional 3 move instruction added to help type inference */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.io.OutputStream} */
    /* JADX WARN: Multi-variable type inference failed */
    private static String a(String str, String str2, Map<String, String> map, boolean z) {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        InputStream inputStream;
        OutputStream outputStream2;
        String str3;
        String str4;
        InputStream inputStream2 = null;
        inputStream2 = null;
        inputStream2 = null;
        if (map == null) {
            str3 = null;
        } else {
            try {
                str3 = a(map, z);
            } catch (IOException e2) {
                e = e2;
                httpURLConnection = null;
                outputStream2 = null;
                inputStream = outputStream2;
                outputStream = outputStream2;
                try {
                    k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e.getMessage()));
                    j.a(inputStream);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                    outputStream = outputStream;
                    j.a(inputStream2);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                httpURLConnection = null;
                outputStream = null;
                j.a(inputStream2);
                j.a(outputStream);
                j.a(httpURLConnection);
                throw th;
            }
        }
        if (!"GET".equals(str) || str3 == null) {
            str4 = str2;
        } else {
            str4 = str2 + "? " + str3;
        }
        httpURLConnection = (HttpURLConnection) new URL(str4).openConnection();
        try {
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            if ("GET".equals(str)) {
                httpURLConnection.setRequestMethod("GET");
            } else if ("POST".equals(str) && str3 != null) {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", HttpRequest.CONTENT_TYPE_FORM);
                httpURLConnection.setDoOutput(true);
                byte[] bytes = str3.getBytes("UTF-8");
                outputStream = httpURLConnection.getOutputStream();
                try {
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    int responseCode = httpURLConnection.getResponseCode();
                    inputStream = httpURLConnection.getInputStream();
                    byte[] b2 = j.b(inputStream);
                    k.b(String.format("HttpUtil %s succeed url: %s, code: %s", str, str2, Integer.valueOf(responseCode)));
                    String str5 = new String(b2, "UTF-8");
                    j.a(inputStream);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    return str5;
                } catch (IOException e3) {
                    e = e3;
                    inputStream = null;
                    k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e.getMessage()));
                    j.a(inputStream);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    j.a(inputStream2);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    throw th;
                }
            }
            outputStream = null;
            int responseCode2 = httpURLConnection.getResponseCode();
            inputStream = httpURLConnection.getInputStream();
            try {
                byte[] b22 = j.b(inputStream);
                k.b(String.format("HttpUtil %s succeed url: %s, code: %s", str, str2, Integer.valueOf(responseCode2)));
                String str52 = new String(b22, "UTF-8");
                j.a(inputStream);
                j.a(outputStream);
                j.a(httpURLConnection);
                return str52;
            } catch (IOException e4) {
                e = e4;
                outputStream = outputStream;
                k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e.getMessage()));
                j.a(inputStream);
                j.a(outputStream);
                j.a(httpURLConnection);
                return null;
            }
        } catch (IOException e5) {
            e = e5;
            outputStream2 = null;
            inputStream = outputStream2;
            outputStream = outputStream2;
            k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e.getMessage()));
            j.a(inputStream);
            j.a(outputStream);
            j.a(httpURLConnection);
            return null;
        } catch (Throwable th4) {
            th = th4;
            outputStream = null;
            j.a(inputStream2);
            j.a(outputStream);
            j.a(httpURLConnection);
            throw th;
        }
    }

    public static String a(String str, Map<String, String> map) throws IOException {
        return a(str, map, true);
    }

    public static String a(String str, Map<String, String> map, boolean z) throws IOException {
        return a("GET", str, map, z);
    }

    public static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            ArrayList<String> arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    sb.append(map.get(str));
                }
            }
        }
        sb.append(am.c());
        return g.c(sb.toString());
    }

    private static String a(Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                if (!TextUtils.isEmpty(entry.getKey())) {
                    if (sb.length() > 0) {
                        sb.append(f565e);
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    sb.append(f566f);
                    sb.append(URLEncoder.encode(entry.getValue() == null ? "null" : entry.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException unused) {
                k.e("format params failed");
            }
        }
        if (z) {
            String a2 = a(map);
            if (sb.length() > 0) {
                sb.append(f565e);
            }
            sb.append(URLEncoder.encode(d.f525f, "UTF-8"));
            sb.append(f566f);
            sb.append(URLEncoder.encode(a2, "UTF-8"));
        }
        return sb.toString();
    }

    public static String b(String str, Map<String, String> map) throws IOException {
        return b(str, map, true);
    }

    public static String b(String str, Map<String, String> map, boolean z) throws IOException {
        return a("POST", str, map, z);
    }
}
