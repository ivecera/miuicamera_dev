package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: LazyHeaders */
public final class p implements n {
    private volatile Map<String, String> _p;
    private final Map<String, List<o>> headers;

    /* compiled from: LazyHeaders */
    public static final class a {
        private static final String Xp = "User-Agent";
        private static final String Yp = getSanitizedUserAgent();
        private static final Map<String, List<o>> Zp;
        private boolean Vp = true;
        private boolean Wp = true;
        private Map<String, List<o>> headers = Zp;

        static {
            HashMap hashMap = new HashMap(2);
            if (!TextUtils.isEmpty(Yp)) {
                hashMap.put("User-Agent", Collections.singletonList(new b(Yp)));
            }
            Zp = Collections.unmodifiableMap(hashMap);
        }

        private Map<String, List<o>> En() {
            HashMap hashMap = new HashMap(this.headers.size());
            for (Map.Entry<String, List<o>> entry : this.headers.entrySet()) {
                hashMap.put(entry.getKey(), new ArrayList(entry.getValue()));
            }
            return hashMap;
        }

        private void Fn() {
            if (this.Vp) {
                this.Vp = false;
                this.headers = En();
            }
        }

        private List<o> P(String str) {
            List<o> list = this.headers.get(str);
            if (list != null) {
                return list;
            }
            ArrayList arrayList = new ArrayList();
            this.headers.put(str, arrayList);
            return arrayList;
        }

        @VisibleForTesting
        static String getSanitizedUserAgent() {
            String property = System.getProperty("http.agent");
            if (TextUtils.isEmpty(property)) {
                return property;
            }
            int length = property.length();
            StringBuilder sb = new StringBuilder(property.length());
            for (int i = 0; i < length; i++) {
                char charAt = property.charAt(i);
                if ((charAt > 31 || charAt == '\t') && charAt < 127) {
                    sb.append(charAt);
                } else {
                    sb.append('?');
                }
            }
            return sb.toString();
        }

        public a a(String str, o oVar) {
            if (this.Wp && "User-Agent".equalsIgnoreCase(str)) {
                return b(str, oVar);
            }
            Fn();
            P(str).add(oVar);
            return this;
        }

        public a addHeader(String str, String str2) {
            return a(str, new b(str2));
        }

        public a b(String str, o oVar) {
            Fn();
            if (oVar == null) {
                this.headers.remove(str);
            } else {
                List<o> P = P(str);
                P.clear();
                P.add(oVar);
            }
            if (this.Wp && "User-Agent".equalsIgnoreCase(str)) {
                this.Wp = false;
            }
            return this;
        }

        public p build() {
            this.Vp = true;
            return new p(this.headers);
        }

        public a setHeader(String str, String str2) {
            return b(str, str2 == null ? null : new b(str2));
        }
    }

    /* compiled from: LazyHeaders */
    static final class b implements o {
        private final String value;

        b(String str) {
            this.value = str;
        }

        @Override // com.bumptech.glide.load.model.o
        public String I() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (obj instanceof b) {
                return this.value.equals(((b) obj).value);
            }
            return false;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return "StringHeaderFactory{value='" + this.value + '\'' + '}';
        }
    }

    p(Map<String, List<o>> map) {
        this.headers = Collections.unmodifiableMap(map);
    }

    private Map<String, String> Gn() {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, List<o>> entry : this.headers.entrySet()) {
            String g = g(entry.getValue());
            if (!TextUtils.isEmpty(g)) {
                hashMap.put(entry.getKey(), g);
            }
        }
        return hashMap;
    }

    @NonNull
    private String g(@NonNull List<o> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String I = list.get(i).I();
            if (!TextUtils.isEmpty(I)) {
                sb.append(I);
                if (i != list.size() - 1) {
                    sb.append(',');
                }
            }
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof p) {
            return this.headers.equals(((p) obj).headers);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.model.n
    public Map<String, String> getHeaders() {
        if (this._p == null) {
            synchronized (this) {
                if (this._p == null) {
                    this._p = Collections.unmodifiableMap(Gn());
                }
            }
        }
        return this._p;
    }

    public int hashCode() {
        return this.headers.hashCode();
    }

    public String toString() {
        return "LazyHeaders{headers=" + this.headers + '}';
    }
}
