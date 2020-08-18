package com.android.camera.storage;

import android.net.Uri;
import com.android.camera.Thumbnail;
import com.android.camera.effect.draw_mode.DrawJPEGAttribute;

public interface SaverCallback {
    public static final int FILE_TYPE_HIDE_IMAGE = 3;
    public static final int FILE_TYPE_IMAGE = 2;
    public static final int FILE_TYPE_VIDEO = 1;

    boolean needThumbnail(boolean z);

    void notifyNewMediaData(Uri uri, String str, int i);

    void onSaveFinish(int i);

    void postHideThumbnailProgressing();

    void postUpdateThumbnail(Thumbnail thumbnail, boolean z);

    void processorJpegSync(boolean z, DrawJPEGAttribute... drawJPEGAttributeArr);

    void updatePreviewThumbnailUri(int i, Uri uri);
}
