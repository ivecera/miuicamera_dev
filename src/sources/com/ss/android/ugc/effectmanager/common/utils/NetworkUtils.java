package com.ss.android.ugc.effectmanager.common.utils;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class NetworkUtils {
    public static String buildRequestUrl(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.append("?");
        if (map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    sb.append("&");
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                }
            }
        }
        return new String(sb);
    }

    public static String encodeParameters(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), str));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue(), str));
                sb.append('&');
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Encoding not supported: " + str, e2);
        }
    }

    public static String filterUrl(String str, String str2) {
        Pattern compile = Pattern.compile(".*(?=://)");
        Pattern compile2 = Pattern.compile("(?<=://)([^/]*)");
        URI create = URI.create(str);
        return compile2.matcher(compile.matcher(str2).replaceFirst(create.getScheme())).replaceFirst(create.getHost());
    }

    public static <T> String toJson(List<T> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (list == null || list.isEmpty()) {
            sb.append("]");
            return sb.toString();
        }
        for (T t : list) {
            sb.append("\"");
            sb.append(t.toString());
            sb.append("\"");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
