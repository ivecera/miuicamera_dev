package android.support.v4.media;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaSession2;
import android.support.v4.media.MediaSessionService2;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class MediaLibraryService2 extends MediaSessionService2 {
    public static final String SERVICE_INTERFACE = "android.media.MediaLibraryService2";

    public static final class LibraryRoot {
        public static final String EXTRA_OFFLINE = "android.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public LibraryRoot(@NonNull String str, @Nullable Bundle bundle) {
            if (str != null) {
                this.mRootId = str;
                this.mExtras = bundle;
                return;
            }
            throw new IllegalArgumentException("rootId shouldn't be null");
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String getRootId() {
            return this.mRootId;
        }
    }

    public static final class MediaLibrarySession extends MediaSession2 {

        public static final class Builder extends MediaSession2.BuilderBase<MediaLibrarySession, Builder, MediaLibrarySessionCallback> {
            public Builder(@NonNull MediaLibraryService2 mediaLibraryService2, @NonNull Executor executor, @NonNull MediaLibrarySessionCallback mediaLibrarySessionCallback) {
                super(mediaLibraryService2);
                setSessionCallback(executor, mediaLibrarySessionCallback);
            }

            @Override // android.support.v4.media.MediaSession2.BuilderBase
            @NonNull
            public MediaLibrarySession build() {
                if (((MediaSession2.BuilderBase) this).mCallbackExecutor == null) {
                    ((MediaSession2.BuilderBase) this).mCallbackExecutor = new MediaSession2.MainHandlerExecutor(((MediaSession2.BuilderBase) this).mContext);
                }
                if (((MediaSession2.BuilderBase) this).mCallback == null) {
                    ((MediaSession2.BuilderBase) this).mCallback = new MediaLibrarySessionCallback() {
                        /* class android.support.v4.media.MediaLibraryService2.MediaLibrarySession.Builder.AnonymousClass1 */
                    };
                }
                return new MediaLibrarySession(((MediaSession2.BuilderBase) this).mContext, ((MediaSession2.BuilderBase) this).mId, ((MediaSession2.BuilderBase) this).mPlayer, ((MediaSession2.BuilderBase) this).mPlaylistAgent, ((MediaSession2.BuilderBase) this).mVolumeProvider, ((MediaSession2.BuilderBase) this).mSessionActivity, ((MediaSession2.BuilderBase) this).mCallbackExecutor, ((MediaSession2.BuilderBase) this).mCallback);
            }

            @Override // android.support.v4.media.MediaSession2.BuilderBase
            @NonNull
            public Builder setId(@NonNull String str) {
                super.setId(str);
                return this;
            }

            @Override // android.support.v4.media.MediaSession2.BuilderBase
            @NonNull
            public Builder setPlayer(@NonNull BaseMediaPlayer baseMediaPlayer) {
                super.setPlayer(baseMediaPlayer);
                return this;
            }

            @Override // android.support.v4.media.MediaSession2.BuilderBase
            @NonNull
            public Builder setPlaylistAgent(@NonNull MediaPlaylistAgent mediaPlaylistAgent) {
                super.setPlaylistAgent(mediaPlaylistAgent);
                return this;
            }

            @Override // android.support.v4.media.MediaSession2.BuilderBase
            @NonNull
            public Builder setSessionActivity(@Nullable PendingIntent pendingIntent) {
                super.setSessionActivity(pendingIntent);
                return this;
            }

            @Override // android.support.v4.media.MediaSession2.BuilderBase
            @NonNull
            public Builder setVolumeProvider(@Nullable VolumeProviderCompat volumeProviderCompat) {
                super.setVolumeProvider(volumeProviderCompat);
                return this;
            }
        }

        public static class MediaLibrarySessionCallback extends MediaSession2.SessionCallback {
            @Nullable
            public List<MediaItem2> onGetChildren(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, int i2, @Nullable Bundle bundle) {
                return null;
            }

            @Nullable
            public MediaItem2 onGetItem(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str) {
                return null;
            }

            @Nullable
            public LibraryRoot onGetLibraryRoot(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @Nullable Bundle bundle) {
                return null;
            }

            @Nullable
            public List<MediaItem2> onGetSearchResult(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, int i2, @Nullable Bundle bundle) {
                return null;
            }

            public void onSearch(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle) {
            }

            public void onSubscribe(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle) {
            }

            public void onUnsubscribe(@NonNull MediaLibrarySession mediaLibrarySession, @NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str) {
            }
        }

        /* access modifiers changed from: package-private */
        public interface SupportLibraryImpl extends MediaSession2.SupportLibraryImpl {
            @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
            MediaLibrarySessionCallback getCallback();

            @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
            MediaLibrarySession getInstance();

            IBinder getLegacySessionBinder();

            void notifyChildrenChanged(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, @Nullable Bundle bundle);

            void notifyChildrenChanged(@NonNull String str, int i, @Nullable Bundle bundle);

            void notifySearchResultChanged(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, @Nullable Bundle bundle);

            void onGetChildrenOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, int i2, @Nullable Bundle bundle);

            void onGetItemOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str);

            void onGetLibraryRootOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @Nullable Bundle bundle);

            void onGetSearchResultOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, int i2, @Nullable Bundle bundle);

            void onSearchOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle);

            void onSubscribeOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle);

            void onUnsubscribeOnExecutor(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str);
        }

        MediaLibrarySession(Context context, String str, BaseMediaPlayer baseMediaPlayer, MediaPlaylistAgent mediaPlaylistAgent, VolumeProviderCompat volumeProviderCompat, PendingIntent pendingIntent, Executor executor, MediaSession2.SessionCallback sessionCallback) {
            super(context, str, baseMediaPlayer, mediaPlaylistAgent, volumeProviderCompat, pendingIntent, executor, sessionCallback);
        }

        /* access modifiers changed from: package-private */
        @Override // android.support.v4.media.MediaSession2
        public SupportLibraryImpl createImpl(Context context, String str, BaseMediaPlayer baseMediaPlayer, MediaPlaylistAgent mediaPlaylistAgent, VolumeProviderCompat volumeProviderCompat, PendingIntent pendingIntent, Executor executor, MediaSession2.SessionCallback sessionCallback) {
            return new MediaLibrarySessionImplBase(this, context, str, baseMediaPlayer, mediaPlaylistAgent, volumeProviderCompat, pendingIntent, executor, sessionCallback);
        }

        /* access modifiers changed from: package-private */
        @Override // android.support.v4.media.MediaSession2
        public MediaLibrarySessionCallback getCallback() {
            return (MediaLibrarySessionCallback) super.getCallback();
        }

        /* access modifiers changed from: package-private */
        @Override // android.support.v4.media.MediaSession2
        public SupportLibraryImpl getImpl() {
            return (SupportLibraryImpl) super.getImpl();
        }

        public void notifyChildrenChanged(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, @Nullable Bundle bundle) {
            getImpl().notifyChildrenChanged(controllerInfo, str, i, bundle);
        }

        public void notifyChildrenChanged(@NonNull String str, int i, @Nullable Bundle bundle) {
            getImpl().notifyChildrenChanged(str, i, bundle);
        }

        public void notifySearchResultChanged(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull String str, int i, @Nullable Bundle bundle) {
            getImpl().notifySearchResultChanged(controllerInfo, str, i, bundle);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.v4.media.MediaSessionService2
    public MediaSessionService2.SupportLibraryImpl createImpl() {
        return new MediaLibraryService2ImplBase();
    }

    @Override // android.support.v4.media.MediaSessionService2
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override // android.support.v4.media.MediaSessionService2
    public void onCreate() {
        super.onCreate();
        if (!(getSession() instanceof MediaLibrarySession)) {
            throw new RuntimeException("Expected MediaLibrarySession, but returned MediaSession2");
        }
    }

    @Override // android.support.v4.media.MediaSessionService2
    @NonNull
    public abstract MediaLibrarySession onCreateSession(String str);
}
