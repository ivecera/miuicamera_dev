package com.android.camera.aiwatermark.handler;

import android.content.Context;
import com.android.camera.aiwatermark.chain.PriorityChainFactory;
import com.android.camera.aiwatermark.data.ASDWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;

public class ChinaASDHandler extends ASDHandler {
    public ChinaASDHandler(boolean z, Context context) {
        super(z, context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ASDHandler, com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        return super.findWatermark();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ASDHandler
    public AbstractHandler getASDChain() {
        return new PriorityChainFactory().createPriorityChain(1).createASDChain(((ASDHandler) this).mContext);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ASDHandler
    public ArrayList<WatermarkItem> getASDWatermarkList() {
        return new ASDWatermark().getForAI();
    }
}
