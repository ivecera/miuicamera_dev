package com.android.camera.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;

public class SplashProvider extends ContentProvider {
    private static final String FILE_NAME = "splash.png";
    private static final String METHOD_GET_SPLASH = "getCameraSplash";
    private static final String TAG = "SplashProvider";
    private static final long TIME_OUT = 3000;
    private final ConditionVariable mConditionVariable = new ConditionVariable();

    private File getSplashFile(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (file.exists() && file.isFile() && file.length() > 0) {
            return file;
        }
        this.mConditionVariable.close();
        Schedulers.io().scheduleDirect(new a(this, context, file));
        Log.d(TAG, "getSplashFile: block E...");
        this.mConditionVariable.block(3000);
        Log.d(TAG, "getSplashFile: block X...");
        return file;
    }

    public /* synthetic */ void a(Context context, File file) {
        Drawable drawable = context.getDrawable(R.drawable.camera_preview_bottom_action_bar);
        if (drawable == null) {
            Log.w(TAG, "getSplashFile: bottom drawable is null!");
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(Util.sWindowWidth, Util.sWindowHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        int i = Util.sWindowHeight - Util.sBottomMargin;
        drawable.setBounds(0, i - Math.round(((float) Util.sBottomBarHeight) * 0.7f), Util.sWindowWidth, i);
        drawable.draw(canvas);
        try {
            Util.saveToFile(createBitmap, file.getPath(), Bitmap.CompressFormat.PNG);
        } catch (IOException unused) {
            Log.w(TAG, "getSplashFile: save splash bitmap failed!");
        }
        this.mConditionVariable.open();
    }

    @Nullable
    public Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        Context context;
        Bundle bundle2 = new Bundle();
        if (((str.hashCode() == 689159266 && str.equals(METHOD_GET_SPLASH)) ? (char) 0 : 65535) != 0 || (context = getContext()) == null) {
            return bundle2;
        }
        File splashFile = getSplashFile(context);
        Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.android.camera.fileProvider", splashFile);
        Log.d(TAG, "getSplashFile: path = " + splashFile.getAbsolutePath() + ", uri = " + uriForFile);
        getContext().grantUriPermission(getCallingPackage(), uriForFile, 1);
        bundle2.putParcelable(METHOD_GET_SPLASH, uriForFile);
        return bundle2;
    }

    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        Log.i(TAG, "onCreate: ");
        return true;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        return null;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }
}
