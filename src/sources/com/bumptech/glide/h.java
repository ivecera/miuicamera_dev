package com.bumptech.glide;

import com.bumptech.glide.request.RequestFutureTarget;

/* compiled from: RequestBuilder */
class h implements Runnable {
    final /* synthetic */ j this$0;
    final /* synthetic */ RequestFutureTarget val$target;

    h(j jVar, RequestFutureTarget requestFutureTarget) {
        this.this$0 = jVar;
        this.val$target = requestFutureTarget;
    }

    public void run() {
        if (!this.val$target.isCancelled()) {
            j jVar = this.this$0;
            RequestFutureTarget requestFutureTarget = this.val$target;
            jVar.a(requestFutureTarget, requestFutureTarget);
        }
    }
}
