package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.support.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiresApi(21)
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    static {
        try {
            sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(List.class);
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    private ParceledListSliceAdapterApi21() {
    }

    static Object newInstance(List<MediaBrowser.MediaItem> list) {
        try {
            return sConstructor.newInstance(list);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
