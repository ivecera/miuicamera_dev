package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.c;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GlideException extends Exception {
    private static final StackTraceElement[] Ux = new StackTraceElement[0];
    private static final long serialVersionUID = 1;
    private final List<Throwable> causes;
    private Class<?> dataClass;
    private DataSource dataSource;
    private String detailMessage;
    private c key;

    private static final class a implements Appendable {
        private static final String INDENT = "  ";
        private static final String In = "";
        private boolean Hn = true;
        private final Appendable appendable;

        a(Appendable appendable2) {
            this.appendable = appendable2;
        }

        @NonNull
        private CharSequence a(@Nullable CharSequence charSequence) {
            return charSequence == null ? "" : charSequence;
        }

        @Override // java.lang.Appendable
        public Appendable append(char c2) throws IOException {
            boolean z = false;
            if (this.Hn) {
                this.Hn = false;
                this.appendable.append(INDENT);
            }
            if (c2 == '\n') {
                z = true;
            }
            this.Hn = z;
            this.appendable.append(c2);
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(@Nullable CharSequence charSequence) throws IOException {
            CharSequence a2 = a(charSequence);
            return append(a2, 0, a2.length());
        }

        @Override // java.lang.Appendable
        public Appendable append(@Nullable CharSequence charSequence, int i, int i2) throws IOException {
            CharSequence a2 = a(charSequence);
            boolean z = false;
            if (this.Hn) {
                this.Hn = false;
                this.appendable.append(INDENT);
            }
            if (a2.length() > 0 && a2.charAt(i2 - 1) == '\n') {
                z = true;
            }
            this.Hn = z;
            this.appendable.append(a2, i, i2);
            return this;
        }
    }

    public GlideException(String str) {
        this(str, Collections.emptyList());
    }

    public GlideException(String str, Throwable th) {
        this(str, Collections.singletonList(th));
    }

    public GlideException(String str, List<Throwable> list) {
        this.detailMessage = str;
        setStackTrace(Ux);
        this.causes = list;
    }

    private void a(Appendable appendable) {
        a(this, appendable);
        a(um(), new a(appendable));
    }

    private static void a(Throwable th, Appendable appendable) {
        try {
            appendable.append(th.getClass().toString()).append(": ").append(th.getMessage()).append('\n');
        } catch (IOException unused) {
            throw new RuntimeException(th);
        }
    }

    private void a(Throwable th, List<Throwable> list) {
        if (th instanceof GlideException) {
            for (Throwable th2 : ((GlideException) th).um()) {
                a(th2, list);
            }
            return;
        }
        list.add(th);
    }

    private static void a(List<Throwable> list, Appendable appendable) {
        try {
            b(list, appendable);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static void b(List<Throwable> list, Appendable appendable) throws IOException {
        int size = list.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            appendable.append("Cause (").append(String.valueOf(i2)).append(" of ").append(String.valueOf(size)).append("): ");
            Throwable th = list.get(i);
            if (th instanceof GlideException) {
                ((GlideException) th).a(appendable);
            } else {
                a(th, appendable);
            }
            i = i2;
        }
    }

    public void F(String str) {
        List<Throwable> vm = vm();
        int size = vm.size();
        int i = 0;
        while (i < size) {
            StringBuilder sb = new StringBuilder();
            sb.append("Root cause (");
            int i2 = i + 1;
            sb.append(i2);
            sb.append(" of ");
            sb.append(size);
            sb.append(")");
            Log.i(str, sb.toString(), vm.get(i));
            i = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, DataSource dataSource2) {
        a(cVar, dataSource2, null);
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, DataSource dataSource2, Class<?> cls) {
        this.key = cVar;
        this.dataSource = dataSource2;
        this.dataClass = cls;
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public String getMessage() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder(71);
        sb.append(this.detailMessage);
        String str3 = "";
        if (this.dataClass != null) {
            str = ", " + this.dataClass;
        } else {
            str = str3;
        }
        sb.append(str);
        if (this.dataSource != null) {
            str2 = ", " + this.dataSource;
        } else {
            str2 = str3;
        }
        sb.append(str2);
        if (this.key != null) {
            str3 = ", " + this.key;
        }
        sb.append(str3);
        List<Throwable> vm = vm();
        if (vm.isEmpty()) {
            return sb.toString();
        }
        if (vm.size() == 1) {
            sb.append("\nThere was 1 cause:");
        } else {
            sb.append("\nThere were ");
            sb.append(vm.size());
            sb.append(" causes:");
        }
        for (Throwable th : vm) {
            sb.append('\n');
            sb.append(th.getClass().getName());
            sb.append('(');
            sb.append(th.getMessage());
            sb.append(')');
        }
        sb.append("\n call GlideException#logRootCauses(String) for more detail");
        return sb.toString();
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        a(printStream);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        a(printWriter);
    }

    public List<Throwable> um() {
        return this.causes;
    }

    public List<Throwable> vm() {
        ArrayList arrayList = new ArrayList();
        a(this, arrayList);
        return arrayList;
    }
}
