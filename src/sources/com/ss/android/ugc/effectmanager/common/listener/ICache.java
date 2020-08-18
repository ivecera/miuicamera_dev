package com.ss.android.ugc.effectmanager.common.listener;

import java.io.InputStream;
import java.util.regex.Pattern;

public interface ICache {
    void clear();

    boolean has(String str);

    InputStream queryToStream(String str);

    String queryToString(String str);

    boolean remove(String str);

    void removePattern(Pattern pattern);

    void save(String str, String str2);
}
