package com.android.volley.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class NetworkImageView extends ImageView {
    @Nullable
    Bitmap mDefaultImageBitmap;
    /* access modifiers changed from: private */
    public int mDefaultImageId;
    /* access modifiers changed from: private */
    @Nullable
    public Bitmap mErrorImageBitmap;
    /* access modifiers changed from: private */
    public int mErrorImageId;
    private ImageLoader.ImageContainer mImageContainer;
    private ImageLoader mImageLoader;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void setDefaultImageOrNull() {
        int i = this.mDefaultImageId;
        if (i != 0) {
            setImageResource(i);
            return;
        }
        Bitmap bitmap = this.mDefaultImageBitmap;
        if (bitmap != null) {
            setImageBitmap(bitmap);
        } else {
            setImageBitmap(null);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void loadImageIfNecessary(final boolean z) {
        boolean z2;
        boolean z3;
        int width = getWidth();
        int height = getHeight();
        ImageView.ScaleType scaleType = getScaleType();
        boolean z4 = true;
        if (getLayoutParams() != null) {
            z3 = getLayoutParams().width == -2;
            z2 = getLayoutParams().height == -2;
        } else {
            z3 = false;
            z2 = false;
        }
        if (!z3 || !z2) {
            z4 = false;
        }
        if (width != 0 || height != 0 || z4) {
            if (TextUtils.isEmpty(this.mUrl)) {
                ImageLoader.ImageContainer imageContainer = this.mImageContainer;
                if (imageContainer != null) {
                    imageContainer.cancelRequest();
                    this.mImageContainer = null;
                }
                setDefaultImageOrNull();
                return;
            }
            ImageLoader.ImageContainer imageContainer2 = this.mImageContainer;
            if (!(imageContainer2 == null || imageContainer2.getRequestUrl() == null)) {
                if (!this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                    this.mImageContainer.cancelRequest();
                    setDefaultImageOrNull();
                } else {
                    return;
                }
            }
            if (z3) {
                width = 0;
            }
            this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageLoader.ImageListener() {
                /* class com.android.volley.toolbox.NetworkImageView.AnonymousClass1 */

                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    if (NetworkImageView.this.mErrorImageId != 0) {
                        NetworkImageView networkImageView = NetworkImageView.this;
                        networkImageView.setImageResource(networkImageView.mErrorImageId);
                    } else if (NetworkImageView.this.mErrorImageBitmap != null) {
                        NetworkImageView networkImageView2 = NetworkImageView.this;
                        networkImageView2.setImageBitmap(networkImageView2.mErrorImageBitmap);
                    }
                }

                @Override // com.android.volley.toolbox.ImageLoader.ImageListener
                public void onResponse(final ImageLoader.ImageContainer imageContainer, boolean z) {
                    if (z && z) {
                        NetworkImageView.this.post(new Runnable() {
                            /* class com.android.volley.toolbox.NetworkImageView.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                AnonymousClass1.this.onResponse(imageContainer, false);
                            }
                        });
                    } else if (imageContainer.getBitmap() != null) {
                        NetworkImageView.this.setImageBitmap(imageContainer.getBitmap());
                    } else if (NetworkImageView.this.mDefaultImageId != 0) {
                        NetworkImageView networkImageView = NetworkImageView.this;
                        networkImageView.setImageResource(networkImageView.mDefaultImageId);
                    } else {
                        NetworkImageView networkImageView2 = NetworkImageView.this;
                        Bitmap bitmap = networkImageView2.mDefaultImageBitmap;
                        if (bitmap != null) {
                            networkImageView2.setImageBitmap(bitmap);
                        }
                    }
                }
            }, width, z2 ? 0 : height, scaleType);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        ImageLoader.ImageContainer imageContainer = this.mImageContainer;
        if (imageContainer != null) {
            imageContainer.cancelRequest();
            setImageBitmap(null);
            this.mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        loadImageIfNecessary(true);
    }

    public void setDefaultImageBitmap(Bitmap bitmap) {
        this.mDefaultImageId = 0;
        this.mDefaultImageBitmap = bitmap;
    }

    public void setDefaultImageResId(int i) {
        this.mDefaultImageBitmap = null;
        this.mDefaultImageId = i;
    }

    public void setErrorImageBitmap(Bitmap bitmap) {
        this.mErrorImageId = 0;
        this.mErrorImageBitmap = bitmap;
    }

    public void setErrorImageResId(int i) {
        this.mErrorImageBitmap = null;
        this.mErrorImageId = i;
    }

    @MainThread
    public void setImageUrl(String str, ImageLoader imageLoader) {
        Threads.throwIfNotOnMainThread();
        this.mUrl = str;
        this.mImageLoader = imageLoader;
        loadImageIfNecessary(false);
    }
}
