package com.android.camera.aiwatermark.chain;

import android.content.Context;
import com.android.camera.aiwatermark.handler.AbstractHandler;
import com.android.camera.aiwatermark.handler.ChinaASDHandler;
import com.android.camera.aiwatermark.handler.ChinaCityHandler;
import com.android.camera.aiwatermark.handler.ChinaFestivalHandler;
import com.android.camera.aiwatermark.handler.ChinaScenicSpotsHandler;
import com.android.camera.aiwatermark.handler.ChinaTimeHandler;

public class ChinaPriorityChain extends AbstractPriorityChain {
    @Override // com.android.camera.aiwatermark.chain.AbstractPriorityChain
    public AbstractHandler createASDChain(Context context) {
        ChinaCityHandler chinaCityHandler = new ChinaCityHandler(true, context);
        chinaCityHandler.setNextHandler(new ChinaTimeHandler(true));
        return chinaCityHandler;
    }

    @Override // com.android.camera.aiwatermark.chain.AbstractPriorityChain
    public AbstractHandler createChain(Context context) {
        ChinaScenicSpotsHandler chinaScenicSpotsHandler = new ChinaScenicSpotsHandler(true);
        ChinaFestivalHandler chinaFestivalHandler = new ChinaFestivalHandler(true);
        ChinaASDHandler chinaASDHandler = new ChinaASDHandler(true, context);
        ChinaCityHandler chinaCityHandler = new ChinaCityHandler(true, context);
        ChinaTimeHandler chinaTimeHandler = new ChinaTimeHandler(true);
        chinaScenicSpotsHandler.setNextHandler(chinaFestivalHandler);
        chinaFestivalHandler.setNextHandler(chinaASDHandler);
        chinaASDHandler.setNextHandler(chinaCityHandler);
        chinaCityHandler.setNextHandler(chinaTimeHandler);
        return chinaScenicSpotsHandler;
    }
}
