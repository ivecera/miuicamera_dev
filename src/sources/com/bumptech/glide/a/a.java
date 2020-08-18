package com.bumptech.glide.a;

import java.util.concurrent.Callable;

/* compiled from: DiskLruCache */
class a implements Callable<Void> {
    final /* synthetic */ b this$0;

    a(b bVar) {
        this.this$0 = bVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        return null;
     */
    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        synchronized (this.this$0) {
            if (this.this$0.journalWriter == null) {
                return null;
            }
            this.this$0.trimToSize();
            if (this.this$0.journalRebuildRequired()) {
                this.this$0.rebuildJournal();
                int unused = this.this$0.redundantOpCount = 0;
            }
        }
    }
}
