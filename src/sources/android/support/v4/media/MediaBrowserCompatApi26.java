package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompatApi21;
import java.util.List;

@RequiresApi(26)
class MediaBrowserCompatApi26 {

    interface SubscriptionCallback extends MediaBrowserCompatApi21.SubscriptionCallback {
        void onChildrenLoaded(@NonNull String str, List<?> list, @NonNull Bundle bundle);

        void onError(@NonNull String str, @NonNull Bundle bundle);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
        SubscriptionCallbackProxy(T t) {
            super(t);
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onChildrenLoaded(@NonNull String str, List<MediaBrowser.MediaItem> list, @NonNull Bundle bundle) {
            ((MediaBrowserCompatApi21.SubscriptionCallbackProxy) this).mSubscriptionCallback.onChildrenLoaded(str, list, bundle);
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
            ((MediaBrowserCompatApi21.SubscriptionCallbackProxy) this).mSubscriptionCallback.onError(str, bundle);
        }
    }

    private MediaBrowserCompatApi26() {
    }

    static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy(subscriptionCallback);
    }

    public static void subscribe(Object obj, String str, Bundle bundle, Object obj2) {
        ((MediaBrowser) obj).subscribe(str, bundle, (MediaBrowser.SubscriptionCallback) obj2);
    }

    public static void unsubscribe(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).unsubscribe(str, (MediaBrowser.SubscriptionCallback) obj2);
    }
}
