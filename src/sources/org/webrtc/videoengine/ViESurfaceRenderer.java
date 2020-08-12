package org.webrtc.videoengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ViESurfaceRenderer implements SurfaceHolder.Callback {
    private static final String TAG = "WEBRTC";
    private Bitmap bitmap = null;
    private ByteBuffer byteBuffer = null;
    private float dstBottomScale = 1.0f;
    private float dstLeftScale = 0.0f;
    private Rect dstRect = new Rect();
    private float dstRightScale = 1.0f;
    private float dstTopScale = 0.0f;
    private Rect srcRect = new Rect();
    private SurfaceHolder surfaceHolder;

    public ViESurfaceRenderer(SurfaceView surfaceView) {
        this.surfaceHolder = surfaceView.getHolder();
        SurfaceHolder surfaceHolder2 = this.surfaceHolder;
        if (surfaceHolder2 != null) {
            surfaceHolder2.addCallback(this);
        }
    }

    private void changeDestRect(int i, int i2) {
        Rect rect = this.dstRect;
        rect.right = (int) (((float) rect.left) + (this.dstRightScale * ((float) i)));
        rect.bottom = (int) (((float) rect.top) + (this.dstBottomScale * ((float) i2)));
    }

    private void saveBitmapToJPEG(int i, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("/sdcard/render_%d.jpg", Long.valueOf(System.currentTimeMillis())));
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException | IOException unused) {
        }
    }

    public Bitmap CreateBitmap(int i, int i2) {
        Log.d(TAG, "CreateByteBitmap " + i + ":" + i2);
        if (this.bitmap == null) {
            try {
                Process.setThreadPriority(-4);
            } catch (Exception unused) {
            }
        }
        this.bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.RGB_565);
        Rect rect = this.srcRect;
        rect.left = 0;
        rect.top = 0;
        rect.bottom = i2;
        rect.right = i;
        return this.bitmap;
    }

    public ByteBuffer CreateByteBuffer(int i, int i2) {
        Log.d(TAG, "CreateByteBuffer " + i + ":" + i2);
        if (this.bitmap == null) {
            this.bitmap = CreateBitmap(i, i2);
            this.byteBuffer = ByteBuffer.allocateDirect(i * i2 * 2);
        }
        return this.byteBuffer;
    }

    public void DrawBitmap() {
        Canvas lockCanvas;
        if (this.bitmap != null && (lockCanvas = this.surfaceHolder.lockCanvas()) != null) {
            lockCanvas.drawBitmap(this.bitmap, this.srcRect, this.dstRect, (Paint) null);
            this.surfaceHolder.unlockCanvasAndPost(lockCanvas);
        }
    }

    public void DrawByteBuffer() {
        ByteBuffer byteBuffer2 = this.byteBuffer;
        if (byteBuffer2 != null) {
            byteBuffer2.rewind();
            this.bitmap.copyPixelsFromBuffer(this.byteBuffer);
            DrawBitmap();
        }
    }

    public void SetCoordinates(float f2, float f3, float f4, float f5) {
        Log.d(TAG, "SetCoordinates " + f2 + "," + f3 + ":" + f4 + "," + f5);
        this.dstLeftScale = f2;
        this.dstTopScale = f3;
        this.dstRightScale = f4;
        this.dstBottomScale = f5;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder2, int i, int i2, int i3) {
        Log.d(TAG, "ViESurfaceRender::surfaceChanged");
        changeDestRect(i2, i3);
        Log.d(TAG, "ViESurfaceRender::surfaceChanged in_width:" + i2 + " in_height:" + i3 + " srcRect.left:" + this.srcRect.left + " srcRect.top:" + this.srcRect.top + " srcRect.right:" + this.srcRect.right + " srcRect.bottom:" + this.srcRect.bottom + " dstRect.left:" + this.dstRect.left + " dstRect.top:" + this.dstRect.top + " dstRect.right:" + this.dstRect.right + " dstRect.bottom:" + this.dstRect.bottom);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder2) {
        Canvas lockCanvas = this.surfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            Rect surfaceFrame = this.surfaceHolder.getSurfaceFrame();
            if (surfaceFrame != null) {
                changeDestRect(surfaceFrame.right - surfaceFrame.left, surfaceFrame.bottom - surfaceFrame.top);
                Log.d(TAG, "ViESurfaceRender::surfaceCreated dst.left:" + surfaceFrame.left + " dst.top:" + surfaceFrame.top + " dst.right:" + surfaceFrame.right + " dst.bottom:" + surfaceFrame.bottom + " srcRect.left:" + this.srcRect.left + " srcRect.top:" + this.srcRect.top + " srcRect.right:" + this.srcRect.right + " srcRect.bottom:" + this.srcRect.bottom + " dstRect.left:" + this.dstRect.left + " dstRect.top:" + this.dstRect.top + " dstRect.right:" + this.dstRect.right + " dstRect.bottom:" + this.dstRect.bottom);
            }
            this.surfaceHolder.unlockCanvasAndPost(lockCanvas);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder2) {
        Log.d(TAG, "ViESurfaceRenderer::surfaceDestroyed");
        this.bitmap = null;
        this.byteBuffer = null;
    }
}
