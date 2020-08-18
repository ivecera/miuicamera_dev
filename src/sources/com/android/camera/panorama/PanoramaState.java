package com.android.camera.panorama;

public class PanoramaState {
    public IPanoramaStateEventListener listener;

    public interface IPanoramaStateEventListener {
        void requestEnd(PanoramaState panoramaState, int i);
    }

    public PanoramaState() {
        clearListener();
    }

    public void clearListener() {
        this.listener = new IPanoramaStateEventListener() {
            /* class com.android.camera.panorama.PanoramaState.AnonymousClass1 */

            @Override // com.android.camera.panorama.PanoramaState.IPanoramaStateEventListener
            public void requestEnd(PanoramaState panoramaState, int i) {
            }
        };
    }

    public boolean onSaveImage(CaptureImage captureImage) {
        captureImage.close();
        return true;
    }

    public void setPanoramaStateEventListener(IPanoramaStateEventListener iPanoramaStateEventListener) {
        this.listener = iPanoramaStateEventListener;
    }
}
