package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.annotation.MainThread;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageLoader {
    private int mBatchResponseDelayMs = 100;
    /* access modifiers changed from: private */
    public final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap<>();
    private final ImageCache mCache;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap<>();
    private final RequestQueue mRequestQueue;
    /* access modifiers changed from: private */
    public Runnable mRunnable;

    /* access modifiers changed from: private */
    public static class BatchedImageRequest {
        /* access modifiers changed from: private */
        public final List<ImageContainer> mContainers = new ArrayList();
        private VolleyError mError;
        private final Request<?> mRequest;
        /* access modifiers changed from: private */
        public Bitmap mResponseBitmap;

        public BatchedImageRequest(Request<?> request, ImageContainer imageContainer) {
            this.mRequest = request;
            this.mContainers.add(imageContainer);
        }

        public void addContainer(ImageContainer imageContainer) {
            this.mContainers.add(imageContainer);
        }

        public VolleyError getError() {
            return this.mError;
        }

        public boolean removeContainerAndCancelIfNecessary(ImageContainer imageContainer) {
            this.mContainers.remove(imageContainer);
            if (this.mContainers.size() != 0) {
                return false;
            }
            this.mRequest.cancel();
            return true;
        }

        public void setError(VolleyError volleyError) {
            this.mError = volleyError;
        }
    }

    public interface ImageCache {
        Bitmap getBitmap(String str);

        void putBitmap(String str, Bitmap bitmap);
    }

    public class ImageContainer {
        /* access modifiers changed from: private */
        public Bitmap mBitmap;
        private final String mCacheKey;
        /* access modifiers changed from: private */
        public final ImageListener mListener;
        private final String mRequestUrl;

        public ImageContainer(Bitmap bitmap, String str, String str2, ImageListener imageListener) {
            this.mBitmap = bitmap;
            this.mRequestUrl = str;
            this.mCacheKey = str2;
            this.mListener = imageListener;
        }

        @MainThread
        public void cancelRequest() {
            Threads.throwIfNotOnMainThread();
            if (this.mListener != null) {
                BatchedImageRequest batchedImageRequest = (BatchedImageRequest) ImageLoader.this.mInFlightRequests.get(this.mCacheKey);
                if (batchedImageRequest == null) {
                    BatchedImageRequest batchedImageRequest2 = (BatchedImageRequest) ImageLoader.this.mBatchedResponses.get(this.mCacheKey);
                    if (batchedImageRequest2 != null) {
                        batchedImageRequest2.removeContainerAndCancelIfNecessary(this);
                        if (batchedImageRequest2.mContainers.size() == 0) {
                            ImageLoader.this.mBatchedResponses.remove(this.mCacheKey);
                        }
                    }
                } else if (batchedImageRequest.removeContainerAndCancelIfNecessary(this)) {
                    ImageLoader.this.mInFlightRequests.remove(this.mCacheKey);
                }
            }
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public String getRequestUrl() {
            return this.mRequestUrl;
        }
    }

    public interface ImageListener extends Response.ErrorListener {
        void onResponse(ImageContainer imageContainer, boolean z);
    }

    public ImageLoader(RequestQueue requestQueue, ImageCache imageCache) {
        this.mRequestQueue = requestQueue;
        this.mCache = imageCache;
    }

    private void batchResponse(String str, BatchedImageRequest batchedImageRequest) {
        this.mBatchedResponses.put(str, batchedImageRequest);
        if (this.mRunnable == null) {
            this.mRunnable = new Runnable() {
                /* class com.android.volley.toolbox.ImageLoader.AnonymousClass4 */

                public void run() {
                    for (BatchedImageRequest batchedImageRequest : ImageLoader.this.mBatchedResponses.values()) {
                        for (ImageContainer imageContainer : batchedImageRequest.mContainers) {
                            if (imageContainer.mListener != null) {
                                if (batchedImageRequest.getError() == null) {
                                    Bitmap unused = imageContainer.mBitmap = batchedImageRequest.mResponseBitmap;
                                    imageContainer.mListener.onResponse(imageContainer, false);
                                } else {
                                    imageContainer.mListener.onErrorResponse(batchedImageRequest.getError());
                                }
                            }
                        }
                    }
                    ImageLoader.this.mBatchedResponses.clear();
                    Runnable unused2 = ImageLoader.this.mRunnable = null;
                }
            };
            this.mHandler.postDelayed(this.mRunnable, (long) this.mBatchResponseDelayMs);
        }
    }

    private static String getCacheKey(String str, int i, int i2, ImageView.ScaleType scaleType) {
        StringBuilder sb = new StringBuilder(str.length() + 12);
        sb.append("#W");
        sb.append(i);
        sb.append("#H");
        sb.append(i2);
        sb.append("#S");
        sb.append(scaleType.ordinal());
        sb.append(str);
        return sb.toString();
    }

    public static ImageListener getImageListener(final ImageView imageView, final int i, final int i2) {
        return new ImageListener() {
            /* class com.android.volley.toolbox.ImageLoader.AnonymousClass1 */

            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                int i = i2;
                if (i != 0) {
                    imageView.setImageResource(i);
                }
            }

            @Override // com.android.volley.toolbox.ImageLoader.ImageListener
            public void onResponse(ImageContainer imageContainer, boolean z) {
                if (imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                    return;
                }
                int i = i;
                if (i != 0) {
                    imageView.setImageResource(i);
                }
            }
        };
    }

    public ImageContainer get(String str, ImageListener imageListener) {
        return get(str, imageListener, 0, 0);
    }

    public ImageContainer get(String str, ImageListener imageListener, int i, int i2) {
        return get(str, imageListener, i, i2, ImageView.ScaleType.CENTER_INSIDE);
    }

    @MainThread
    public ImageContainer get(String str, ImageListener imageListener, int i, int i2, ImageView.ScaleType scaleType) {
        Threads.throwIfNotOnMainThread();
        String cacheKey = getCacheKey(str, i, i2, scaleType);
        Bitmap bitmap = this.mCache.getBitmap(cacheKey);
        if (bitmap != null) {
            ImageContainer imageContainer = new ImageContainer(bitmap, str, null, null);
            imageListener.onResponse(imageContainer, true);
            return imageContainer;
        }
        ImageContainer imageContainer2 = new ImageContainer(null, str, cacheKey, imageListener);
        imageListener.onResponse(imageContainer2, true);
        BatchedImageRequest batchedImageRequest = this.mInFlightRequests.get(cacheKey);
        if (batchedImageRequest == null) {
            batchedImageRequest = this.mBatchedResponses.get(cacheKey);
        }
        if (batchedImageRequest != null) {
            batchedImageRequest.addContainer(imageContainer2);
            return imageContainer2;
        }
        Request<Bitmap> makeImageRequest = makeImageRequest(str, i, i2, scaleType, cacheKey);
        this.mRequestQueue.add(makeImageRequest);
        this.mInFlightRequests.put(cacheKey, new BatchedImageRequest(makeImageRequest, imageContainer2));
        return imageContainer2;
    }

    public boolean isCached(String str, int i, int i2) {
        return isCached(str, i, i2, ImageView.ScaleType.CENTER_INSIDE);
    }

    @MainThread
    public boolean isCached(String str, int i, int i2, ImageView.ScaleType scaleType) {
        Threads.throwIfNotOnMainThread();
        return this.mCache.getBitmap(getCacheKey(str, i, i2, scaleType)) != null;
    }

    /* access modifiers changed from: protected */
    public Request<Bitmap> makeImageRequest(String str, int i, int i2, ImageView.ScaleType scaleType, final String str2) {
        return new ImageRequest(str, new Response.Listener<Bitmap>() {
            /* class com.android.volley.toolbox.ImageLoader.AnonymousClass2 */

            public void onResponse(Bitmap bitmap) {
                ImageLoader.this.onGetImageSuccess(str2, bitmap);
            }
        }, i, i2, scaleType, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            /* class com.android.volley.toolbox.ImageLoader.AnonymousClass3 */

            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                ImageLoader.this.onGetImageError(str2, volleyError);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onGetImageError(String str, VolleyError volleyError) {
        BatchedImageRequest remove = this.mInFlightRequests.remove(str);
        if (remove != null) {
            remove.setError(volleyError);
            batchResponse(str, remove);
        }
    }

    /* access modifiers changed from: protected */
    public void onGetImageSuccess(String str, Bitmap bitmap) {
        this.mCache.putBitmap(str, bitmap);
        BatchedImageRequest remove = this.mInFlightRequests.remove(str);
        if (remove != null) {
            Bitmap unused = remove.mResponseBitmap = bitmap;
            batchResponse(str, remove);
        }
    }

    public void setBatchedResponseDelay(int i) {
        this.mBatchResponseDelayMs = i;
    }
}
