package com.android.zxing;

import android.media.Image;
import com.android.camera2.Camera2Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PreviewDecodeManager {
    public static final int DECODE_TYPE_GOOGLE_LENS = 2;
    public static final int DECODE_TYPE_HAND_GESTURE = 1;
    public static final int DECODE_TYPE_QR = 0;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Integer, Decoder> mDecoders;
    private Camera2Proxy.PreviewCallback mPreviewCallback;

    private static class Singleton {
        public static final PreviewDecodeManager INSTANCE = new PreviewDecodeManager();

        private Singleton() {
        }
    }

    private PreviewDecodeManager() {
        this.mPreviewCallback = new Camera2Proxy.PreviewCallback() {
            /* class com.android.zxing.PreviewDecodeManager.AnonymousClass1 */

            @Override // com.android.camera2.Camera2Proxy.PreviewCallback
            public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
                for (Map.Entry entry : PreviewDecodeManager.this.mDecoders.entrySet()) {
                    Decoder decoder = (Decoder) entry.getValue();
                    if (decoder.needPreviewFrame()) {
                        if (decoder.isNeedImage()) {
                            decoder.onPreviewFrame(image);
                        } else {
                            decoder.onPreviewFrame(new PreviewImage(image, i));
                        }
                    }
                }
                return true;
            }
        };
        this.mDecoders = new ConcurrentHashMap<>();
    }

    public static PreviewDecodeManager getInstance() {
        return Singleton.INSTANCE;
    }

    public Camera2Proxy.PreviewCallback getPreviewCallback() {
        return this.mPreviewCallback;
    }

    public String getScanResult() {
        return ((QrDecoder) this.mDecoders.get(0)).getScanResult();
    }

    public void init(int i, int i2) {
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 == 2 && this.mDecoders.get(2) == null) {
                    this.mDecoders.put(2, new GoogleLensDecoder());
                }
            } else if (this.mDecoders.get(1) == null) {
                this.mDecoders.put(1, new HandGestureDecoder());
            }
        } else if (this.mDecoders.get(0) == null) {
            this.mDecoders.put(0, new QrDecoder());
        }
        for (Map.Entry<Integer, Decoder> entry : this.mDecoders.entrySet()) {
            entry.getValue().init(i);
        }
    }

    public void quit() {
        for (Map.Entry<Integer, Decoder> entry : this.mDecoders.entrySet()) {
            Decoder value = entry.getValue();
            value.stopDecode();
            value.quit();
        }
        this.mDecoders.clear();
    }

    public void reset() {
        for (Map.Entry<Integer, Decoder> entry : this.mDecoders.entrySet()) {
            entry.getValue().reset();
        }
    }

    public void resetScanResult() {
        Decoder decoder = this.mDecoders.get(0);
        if (decoder != null) {
            ((QrDecoder) decoder).resetScanResult();
        }
    }

    public void startDecode() {
        for (Map.Entry<Integer, Decoder> entry : this.mDecoders.entrySet()) {
            entry.getValue().startDecode();
        }
    }

    public void stopDecode(int i) {
        if (this.mDecoders.get(Integer.valueOf(i)) != null) {
            this.mDecoders.get(Integer.valueOf(i)).stopDecode();
        }
    }
}
