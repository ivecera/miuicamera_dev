package com.android.camera.network.resource;

import android.net.Uri;
import android.util.LongSparseArray;
import com.android.camera.log.Log;
import com.android.camera.network.download.GalleryDownloadManager;
import com.android.camera.network.download.Request;
import com.android.camera.network.download.Verifier;
import com.android.camera.network.net.BaseGalleryRequest;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.ResponseListener;
import com.android.camera.network.resource.RequestContracts;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ResourceDownloadManager {
    private static final String TAG = "ResourceDownloadManager";
    private static ResourceDownloadManager mInstance;
    /* access modifiers changed from: private */
    public LongSparseArray<Integer> mDownloadState = new LongSparseArray<>();
    private List<OnDownloadListener> mListeners = new ArrayList();
    /* access modifiers changed from: private */
    public GalleryDownloadManager.OnCompleteListener mOnCompleteListener = new GalleryDownloadManager.OnCompleteListener() {
        /* class com.android.camera.network.resource.ResourceDownloadManager.AnonymousClass2 */

        @Override // com.android.camera.network.download.GalleryDownloadManager.OnCompleteListener, com.android.camera.network.download.DownloadTask.OnCompleteListener
        public void onRequestComplete(Request request, int i) {
            Log.v(ResourceDownloadManager.TAG, "download finish " + i);
            long parseLong = Long.parseLong(request.getTag());
            synchronized (ResourceDownloadManager.this.object) {
                ResourceDownloadManager.this.mDownloadState.remove(parseLong);
            }
            if (i == 0) {
                ResourceDownloadManager.this.dispatchListener(parseLong, 3);
            } else {
                ResourceDownloadManager.this.dispatchListener(parseLong, 4);
            }
        }
    };
    /* access modifiers changed from: private */
    public Object object = new Object();

    private ResourceDownloadManager() {
    }

    /* access modifiers changed from: private */
    public void dispatchListener(long j, int i) {
        for (OnDownloadListener onDownloadListener : this.mListeners) {
            onDownloadListener.onFinish(j, i);
        }
    }

    public static ResourceDownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (ResourceDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new ResourceDownloadManager();
                }
            }
        }
        return mInstance;
    }

    public void addDownloadListener(OnDownloadListener onDownloadListener) {
        this.mListeners.add(onDownloadListener);
    }

    public void download(Resource resource, DownloadHelper downloadHelper) {
        final long j = resource.id;
        Log.v(TAG, "downloading " + j);
        final File file = new File(downloadHelper.getDownloadPath(resource));
        if (file.exists()) {
            Log.v(TAG, "file downloaded " + file);
            dispatchListener(j, 1);
        }
        BaseGalleryRequest baseGalleryRequest = new BaseGalleryRequest(0, RequestContracts.Download.URL);
        baseGalleryRequest.addParam("id", String.valueOf(j));
        baseGalleryRequest.execute(new ResponseListener() {
            /* class com.android.camera.network.resource.ResourceDownloadManager.AnonymousClass1 */

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponse(Object... objArr) {
                try {
                    JSONObject jSONObject = (JSONObject) objArr[0];
                    String string = jSONObject.getString(RequestContracts.Download.JSON_KEY_URL);
                    String string2 = jSONObject.getString(RequestContracts.Download.JSON_KEY_SHA1);
                    Log.v(ResourceDownloadManager.TAG, String.format("download %s, %s", string, string2));
                    Request request = new Request(Long.toString(j), Uri.parse(string), file);
                    request.setVerifier(new Verifier.Sha1(string2));
                    synchronized (this) {
                        ResourceDownloadManager.this.mDownloadState.put(j, 2);
                    }
                    GalleryDownloadManager.INSTANCE.enqueue(request, ResourceDownloadManager.this.mOnCompleteListener);
                } catch (JSONException e2) {
                    Log.w(ResourceDownloadManager.TAG, e2);
                    ResourceDownloadManager.this.dispatchListener(j, 4);
                }
            }

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponseError(ErrorCode errorCode, String str, Object obj) {
                ResourceDownloadManager.this.dispatchListener(j, 4);
            }
        });
    }

    public int getDownloadState(long j) {
        return this.mDownloadState.get(j, 0).intValue();
    }

    public void removeDownloadListener(OnDownloadListener onDownloadListener) {
        this.mListeners.remove(onDownloadListener);
    }
}
