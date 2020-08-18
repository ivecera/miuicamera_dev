package com.bumptech.glide.request.target;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.util.i;

/* compiled from: NotificationTarget */
public class j extends m<Bitmap> {
    private final String Kt;
    private final Context context;
    private final Notification notification;
    private final int notificationId;
    private final RemoteViews remoteViews;
    private final int viewId;

    public j(Context context2, int i, int i2, int i3, RemoteViews remoteViews2, Notification notification2, int i4, String str) {
        super(i, i2);
        i.b(context2, "Context must not be null!");
        this.context = context2;
        i.b(notification2, "Notification object can not be null!");
        this.notification = notification2;
        i.b(remoteViews2, "RemoteViews object can not be null!");
        this.remoteViews = remoteViews2;
        this.viewId = i3;
        this.notificationId = i4;
        this.Kt = str;
    }

    public j(Context context2, int i, RemoteViews remoteViews2, Notification notification2, int i2) {
        this(context2, i, remoteViews2, notification2, i2, null);
    }

    public j(Context context2, int i, RemoteViews remoteViews2, Notification notification2, int i2, String str) {
        this(context2, Integer.MIN_VALUE, Integer.MIN_VALUE, i, remoteViews2, notification2, i2, str);
    }

    private void update() {
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService("notification");
        i.checkNotNull(notificationManager);
        notificationManager.notify(this.Kt, this.notificationId, this.notification);
    }

    public void a(@NonNull Bitmap bitmap, @Nullable f<? super Bitmap> fVar) {
        this.remoteViews.setImageViewBitmap(this.viewId, bitmap);
        update();
    }

    @Override // com.bumptech.glide.request.target.o
    public /* bridge */ /* synthetic */ void a(@NonNull Object obj, @Nullable f fVar) {
        a((Bitmap) obj, (f<? super Bitmap>) fVar);
    }
}
