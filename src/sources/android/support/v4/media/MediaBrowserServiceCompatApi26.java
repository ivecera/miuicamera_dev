package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(26)
class MediaBrowserServiceCompatApi26 {
    private static final String TAG = "MBSCompatApi26";
    /* access modifiers changed from: private */
    public static Field sResultFlags;

    static class MediaBrowserServiceAdaptor extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            super(context, serviceCompatProxy);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
            ((ServiceCompatProxy) ((MediaBrowserServiceCompatApi21.MediaBrowserServiceAdaptor) this).mServiceProxy).onLoadChildren(str, new ResultWrapper(result), bundle);
        }
    }

    static class ResultWrapper {
        MediaBrowserService.Result mResultObj;

        ResultWrapper(MediaBrowserService.Result result) {
            this.mResultObj = result;
        }

        public void detach() {
            this.mResultObj.detach();
        }

        /* access modifiers changed from: package-private */
        public List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcel parcel : list) {
                parcel.setDataPosition(0);
                arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return arrayList;
        }

        public void sendResult(List<Parcel> list, int i) {
            try {
                MediaBrowserServiceCompatApi26.sResultFlags.setInt(this.mResultObj, i);
            } catch (IllegalAccessException e2) {
                Log.w(MediaBrowserServiceCompatApi26.TAG, e2);
            }
            this.mResultObj.sendResult(parcelListToItemList(list));
        }
    }

    public interface ServiceCompatProxy extends MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        void onLoadChildren(String str, ResultWrapper resultWrapper, Bundle bundle);
    }

    static {
        try {
            sResultFlags = MediaBrowserService.Result.class.getDeclaredField("mFlags");
            sResultFlags.setAccessible(true);
        } catch (NoSuchFieldException e2) {
            Log.w(TAG, e2);
        }
    }

    private MediaBrowserServiceCompatApi26() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceCompatProxy);
    }

    public static Bundle getBrowserRootHints(Object obj) {
        return ((MediaBrowserService) obj).getBrowserRootHints();
    }

    public static void notifyChildrenChanged(Object obj, String str, Bundle bundle) {
        ((MediaBrowserService) obj).notifyChildrenChanged(str, bundle);
    }
}
