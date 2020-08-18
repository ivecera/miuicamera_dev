package com.bumptech.glide;

import com.bumptech.glide.request.target.o;

/* compiled from: RequestManager */
class l implements Runnable {
    final /* synthetic */ m this$0;
    final /* synthetic */ o val$target;

    l(m mVar, o oVar) {
        this.this$0 = mVar;
        this.val$target = oVar;
    }

    public void run() {
        this.this$0.d(this.val$target);
    }
}
