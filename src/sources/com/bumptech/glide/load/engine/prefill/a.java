package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.prefill.c;
import com.bumptech.glide.util.l;
import java.util.HashMap;

/* compiled from: BitmapPreFiller */
public final class a {
    private final DecodeFormat Cp;
    private final d Xi;
    private final o Yi;
    private BitmapPreFillRunner current;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public a(o oVar, d dVar, DecodeFormat decodeFormat) {
        this.Yi = oVar;
        this.Xi = dVar;
        this.Cp = decodeFormat;
    }

    private static int a(c cVar) {
        return l.g(cVar.getWidth(), cVar.getHeight(), cVar.getConfig());
    }

    public void b(c.a... aVarArr) {
        BitmapPreFillRunner bitmapPreFillRunner = this.current;
        if (bitmapPreFillRunner != null) {
            bitmapPreFillRunner.cancel();
        }
        c[] cVarArr = new c[aVarArr.length];
        for (int i = 0; i < aVarArr.length; i++) {
            c.a aVar = aVarArr[i];
            if (aVar.getConfig() == null) {
                DecodeFormat decodeFormat = this.Cp;
                aVar.setConfig((decodeFormat == DecodeFormat.Gx || decodeFormat == DecodeFormat.Hx) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            cVarArr[i] = aVar.build();
        }
        this.current = new BitmapPreFillRunner(this.Xi, this.Yi, generateAllocationOrder(cVarArr));
        this.handler.post(this.current);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public b generateAllocationOrder(c... cVarArr) {
        long maxSize = (this.Yi.getMaxSize() - this.Yi.da()) + this.Xi.getMaxSize();
        int i = 0;
        for (c cVar : cVarArr) {
            i += cVar.getWeight();
        }
        float f2 = ((float) maxSize) / ((float) i);
        HashMap hashMap = new HashMap();
        for (c cVar2 : cVarArr) {
            hashMap.put(cVar2, Integer.valueOf(Math.round(((float) cVar2.getWeight()) * f2) / a(cVar2)));
        }
        return new b(hashMap);
    }
}
