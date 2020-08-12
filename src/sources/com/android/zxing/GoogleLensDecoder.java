package com.android.zxing;

import android.media.Image;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.lens.LensAgent;

class GoogleLensDecoder extends Decoder {
    private int mSensorOrientation;

    GoogleLensDecoder() {
    }

    @Override // com.android.zxing.Decoder
    public void init(int i) {
        this.mSensorOrientation = Camera2DataContainer.getInstance().getCapabilities(i).getSensorOrientation();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.zxing.Decoder
    public boolean isNeedImage() {
        return true;
    }

    @Override // com.android.zxing.Decoder
    public boolean needPreviewFrame() {
        return true;
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(Image image) {
        super.onPreviewFrame(image);
        LensAgent.getInstance().onNewImage(image, this.mSensorOrientation);
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(PreviewImage previewImage) {
    }

    @Override // com.android.zxing.Decoder
    public void reset() {
    }

    @Override // com.android.zxing.Decoder
    public void startDecode() {
    }
}
