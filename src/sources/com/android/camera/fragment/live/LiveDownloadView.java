package com.android.camera.fragment.live;

import android.content.Context;
import android.util.AttributeSet;
import com.android.camera.R;
import com.android.camera.fragment.sticker.download.DownloadView;

public class LiveDownloadView extends DownloadView {
    public LiveDownloadView(Context context) {
        super(context);
    }

    public LiveDownloadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.sticker.download.DownloadView
    public float getAlphaNormal() {
        return 0.9f;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.sticker.download.DownloadView
    public int getStateImageResource(int i) {
        if (i == 0) {
            return R.drawable.icon_live_sticker_download;
        }
        if (i == 2) {
            return R.drawable.icon_live_sticker_downloading;
        }
        if (i == 3) {
            return R.drawable.icon_live_sticker_downloaded;
        }
        if (i != 4) {
            return 0;
        }
        return R.drawable.icon_live_sticker_download;
    }
}
