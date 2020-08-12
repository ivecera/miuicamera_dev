package com.android.camera.aiwatermark.handler;

import android.content.Context;
import android.text.TextUtils;
import com.android.camera.aiwatermark.algo.ASDEngine;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.lisenter.IASDListener;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ASDHandler extends AbstractHandler {
    /* access modifiers changed from: private */
    public static final String TAG = "ASDHandler";
    /* access modifiers changed from: private */
    public AbstractHandler mASDHandler = null;
    private IASDListener mASDListener = null;
    protected Context mContext = null;
    /* access modifiers changed from: private */
    public ArrayList<WatermarkItem> mList = new ArrayList<>();
    /* access modifiers changed from: private */
    public int mSpots = 0;

    public ASDHandler(boolean z, Context context) {
        super(z);
        this.mContext = context;
    }

    private IASDListener createASDListner() {
        return new IASDListener() {
            /* class com.android.camera.aiwatermark.handler.ASDHandler.AnonymousClass1 */

            @Override // com.android.camera.aiwatermark.lisenter.IASDListener
            public void onASDChange(int i) {
                boolean z;
                boolean z2;
                Log.d(ASDHandler.TAG, "onASDChange spots = " + i);
                if (ASDHandler.this.mSpots != i) {
                    String spots2ASDKey = ASDEngine.spots2ASDKey(i);
                    Iterator it = ASDHandler.this.mList.iterator();
                    while (true) {
                        z = true;
                        if (it.hasNext()) {
                            if (TextUtils.equals(spots2ASDKey, ((WatermarkItem) it.next()).getKey())) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (!z2 && i != 0) {
                        z = false;
                    }
                    if (z) {
                        int unused = ASDHandler.this.mSpots = i;
                        WatermarkItem findWatermark = ASDHandler.this.findWatermark();
                        Log.d(ASDHandler.TAG, "item=" + findWatermark);
                        if (findWatermark == null) {
                            if (ASDHandler.this.mASDHandler == null) {
                                ASDHandler aSDHandler = ASDHandler.this;
                                AbstractHandler unused2 = aSDHandler.mASDHandler = aSDHandler.getASDChain();
                            }
                            findWatermark = ASDHandler.this.mASDHandler.handleRequest();
                        }
                        ASDHandler.this.updateWatermark(findWatermark);
                    }
                }
            }
        };
    }

    private void registerASDListener(IASDListener iASDListener) {
        ModeProtocol.AIWatermarkDetect aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
        if (aIWatermarkDetect != null) {
            aIWatermarkDetect.setListener(iASDListener);
        }
    }

    /* access modifiers changed from: private */
    public void updateWatermark(WatermarkItem watermarkItem) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.updateWatermarkSample(watermarkItem);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        String str = TAG;
        Log.d(str, "ASDHandler findWatermark mASDListener = " + this.mASDListener);
        DataRepository.dataItemRunning().getComponentRunningAIWatermark().setIWatermarkEnable(true);
        if (this.mASDListener == null) {
            this.mASDListener = createASDListner();
        }
        registerASDListener(this.mASDListener);
        if (this.mList.size() == 0) {
            this.mList = getASDWatermarkList();
        }
        String str2 = TAG;
        Log.d(str2, "findWatermark mList.size() = " + this.mList.size());
        String spots2ASDKey = ASDEngine.spots2ASDKey(this.mSpots);
        String str3 = TAG;
        Log.d(str3, "key = " + spots2ASDKey);
        Iterator<WatermarkItem> it = this.mList.iterator();
        while (it.hasNext()) {
            WatermarkItem next = it.next();
            if (TextUtils.equals(spots2ASDKey, next.getKey())) {
                return next;
            }
        }
        Log.d(TAG, "ASD watermark null");
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract AbstractHandler getASDChain();

    /* access modifiers changed from: protected */
    public abstract ArrayList<WatermarkItem> getASDWatermarkList();
}
