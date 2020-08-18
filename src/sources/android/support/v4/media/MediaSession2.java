package android.support.v4.media;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.mediacompat.Rating2;
import android.support.v4.media.MediaController2;
import android.support.v4.media.MediaInterface2;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

@TargetApi(19)
public class MediaSession2 implements MediaInterface2.SessionPlayer, AutoCloseable {
    public static final int ERROR_CODE_ACTION_ABORTED = 10;
    public static final int ERROR_CODE_APP_ERROR = 1;
    public static final int ERROR_CODE_AUTHENTICATION_EXPIRED = 3;
    public static final int ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5;
    public static final int ERROR_CODE_CONTENT_ALREADY_PLAYING = 8;
    public static final int ERROR_CODE_END_OF_QUEUE = 11;
    public static final int ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7;
    public static final int ERROR_CODE_NOT_SUPPORTED = 2;
    public static final int ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6;
    public static final int ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4;
    public static final int ERROR_CODE_SETUP_REQUIRED = 12;
    public static final int ERROR_CODE_SKIP_LIMIT_REACHED = 9;
    public static final int ERROR_CODE_UNKNOWN_ERROR = 0;
    static final String TAG = "MediaSession2";
    private final SupportLibraryImpl mImpl;

    public static final class Builder extends BuilderBase<MediaSession2, Builder, SessionCallback> {
        public Builder(Context context) {
            super(context);
        }

        @Override // android.support.v4.media.MediaSession2.BuilderBase
        @NonNull
        public MediaSession2 build() {
            if (((BuilderBase) this).mCallbackExecutor == null) {
                ((BuilderBase) this).mCallbackExecutor = new MainHandlerExecutor(((BuilderBase) this).mContext);
            }
            if (((BuilderBase) this).mCallback == null) {
                ((BuilderBase) this).mCallback = new SessionCallback() {
                    /* class android.support.v4.media.MediaSession2.Builder.AnonymousClass1 */
                };
            }
            return new MediaSession2(((BuilderBase) this).mContext, ((BuilderBase) this).mId, ((BuilderBase) this).mPlayer, ((BuilderBase) this).mPlaylistAgent, ((BuilderBase) this).mVolumeProvider, ((BuilderBase) this).mSessionActivity, ((BuilderBase) this).mCallbackExecutor, ((BuilderBase) this).mCallback);
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
        public Builder setSessionCallback(@NonNull Executor executor, @NonNull SessionCallback sessionCallback) {
            super.setSessionCallback(executor, sessionCallback);
            return this;
        }

        @Override // android.support.v4.media.MediaSession2.BuilderBase
        @NonNull
        public Builder setVolumeProvider(@Nullable VolumeProviderCompat volumeProviderCompat) {
            super.setVolumeProvider(volumeProviderCompat);
            return this;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    static abstract class BuilderBase<T extends MediaSession2, U extends BuilderBase<T, U, C>, C extends SessionCallback> {
        C mCallback;
        Executor mCallbackExecutor;
        final Context mContext;
        String mId;
        BaseMediaPlayer mPlayer;
        MediaPlaylistAgent mPlaylistAgent;
        PendingIntent mSessionActivity;
        VolumeProviderCompat mVolumeProvider;

        BuilderBase(Context context) {
            if (context != null) {
                this.mContext = context;
                this.mId = MediaSession2.TAG;
                return;
            }
            throw new IllegalArgumentException("context shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public abstract T build();

        /* access modifiers changed from: package-private */
        @NonNull
        public U setId(@NonNull String str) {
            if (str != null) {
                this.mId = str;
                return this;
            }
            throw new IllegalArgumentException("id shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public U setPlayer(@NonNull BaseMediaPlayer baseMediaPlayer) {
            if (baseMediaPlayer != null) {
                this.mPlayer = baseMediaPlayer;
                return this;
            }
            throw new IllegalArgumentException("player shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        public U setPlaylistAgent(@NonNull MediaPlaylistAgent mediaPlaylistAgent) {
            if (mediaPlaylistAgent != null) {
                this.mPlaylistAgent = mediaPlaylistAgent;
                return this;
            }
            throw new IllegalArgumentException("playlistAgent shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public U setSessionActivity(@Nullable PendingIntent pendingIntent) {
            this.mSessionActivity = pendingIntent;
            return this;
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public U setSessionCallback(@NonNull Executor executor, @NonNull C c2) {
            if (executor == null) {
                throw new IllegalArgumentException("executor shouldn't be null");
            } else if (c2 != null) {
                this.mCallbackExecutor = executor;
                this.mCallback = c2;
                return this;
            } else {
                throw new IllegalArgumentException("callback shouldn't be null");
            }
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public U setVolumeProvider(@Nullable VolumeProviderCompat volumeProviderCompat) {
            this.mVolumeProvider = volumeProviderCompat;
            return this;
        }
    }

    public static final class CommandButton {
        private static final String KEY_COMMAND = "android.media.media_session2.command_button.command";
        private static final String KEY_DISPLAY_NAME = "android.media.media_session2.command_button.display_name";
        private static final String KEY_ENABLED = "android.media.media_session2.command_button.enabled";
        private static final String KEY_EXTRAS = "android.media.media_session2.command_button.extras";
        private static final String KEY_ICON_RES_ID = "android.media.media_session2.command_button.icon_res_id";
        private SessionCommand2 mCommand;
        private String mDisplayName;
        private boolean mEnabled;
        private Bundle mExtras;
        private int mIconResId;

        public static final class Builder {
            private SessionCommand2 mCommand;
            private String mDisplayName;
            private boolean mEnabled;
            private Bundle mExtras;
            private int mIconResId;

            @NonNull
            public CommandButton build() {
                return new CommandButton(this.mCommand, this.mIconResId, this.mDisplayName, this.mExtras, this.mEnabled);
            }

            @NonNull
            public Builder setCommand(@Nullable SessionCommand2 sessionCommand2) {
                this.mCommand = sessionCommand2;
                return this;
            }

            @NonNull
            public Builder setDisplayName(@Nullable String str) {
                this.mDisplayName = str;
                return this;
            }

            @NonNull
            public Builder setEnabled(boolean z) {
                this.mEnabled = z;
                return this;
            }

            @NonNull
            public Builder setExtras(@Nullable Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            @NonNull
            public Builder setIconResId(int i) {
                this.mIconResId = i;
                return this;
            }
        }

        private CommandButton(@Nullable SessionCommand2 sessionCommand2, int i, @Nullable String str, Bundle bundle, boolean z) {
            this.mCommand = sessionCommand2;
            this.mIconResId = i;
            this.mDisplayName = str;
            this.mExtras = bundle;
            this.mEnabled = z;
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static CommandButton fromBundle(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            Builder builder = new Builder();
            builder.setCommand(SessionCommand2.fromBundle(bundle.getBundle(KEY_COMMAND)));
            builder.setIconResId(bundle.getInt(KEY_ICON_RES_ID, 0));
            builder.setDisplayName(bundle.getString(KEY_DISPLAY_NAME));
            builder.setExtras(bundle.getBundle(KEY_EXTRAS));
            builder.setEnabled(bundle.getBoolean(KEY_ENABLED));
            try {
                return builder.build();
            } catch (IllegalStateException unused) {
                return null;
            }
        }

        @Nullable
        public SessionCommand2 getCommand() {
            return this.mCommand;
        }

        @Nullable
        public String getDisplayName() {
            return this.mDisplayName;
        }

        @Nullable
        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getIconResId() {
            return this.mIconResId;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBundle(KEY_COMMAND, this.mCommand.toBundle());
            bundle.putInt(KEY_ICON_RES_ID, this.mIconResId);
            bundle.putString(KEY_DISPLAY_NAME, this.mDisplayName);
            bundle.putBundle(KEY_EXTRAS, this.mExtras);
            bundle.putBoolean(KEY_ENABLED, this.mEnabled);
            return bundle;
        }
    }

    static abstract class ControllerCb {
        ControllerCb() {
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ControllerCb)) {
                return false;
            }
            return getId().equals(((ControllerCb) obj).getId());
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public abstract IBinder getId();

        public int hashCode() {
            return getId().hashCode();
        }

        /* access modifiers changed from: package-private */
        public abstract void onAllowedCommandsChanged(@NonNull SessionCommandGroup2 sessionCommandGroup2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onBufferingStateChanged(@NonNull MediaItem2 mediaItem2, int i, long j) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onChildrenChanged(@NonNull String str, int i, @Nullable Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onCurrentMediaItemChanged(@Nullable MediaItem2 mediaItem2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onCustomCommand(@NonNull SessionCommand2 sessionCommand2, @Nullable Bundle bundle, @Nullable ResultReceiver resultReceiver) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onCustomLayoutChanged(@NonNull List<CommandButton> list) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onDisconnected() throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onError(int i, @Nullable Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetChildrenDone(@NonNull String str, int i, int i2, @Nullable List<MediaItem2> list, @Nullable Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetItemDone(@NonNull String str, @Nullable MediaItem2 mediaItem2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetLibraryRootDone(@Nullable Bundle bundle, @Nullable String str, @Nullable Bundle bundle2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetSearchResultDone(@NonNull String str, int i, int i2, @Nullable List<MediaItem2> list, @Nullable Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaybackInfoChanged(@NonNull MediaController2.PlaybackInfo playbackInfo) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaybackSpeedChanged(long j, long j2, float f2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlayerStateChanged(long j, long j2, int i) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaylistChanged(@NonNull List<MediaItem2> list, @Nullable MediaMetadata2 mediaMetadata2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaylistMetadataChanged(@Nullable MediaMetadata2 mediaMetadata2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onRepeatModeChanged(int i) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onRoutesInfoChanged(@Nullable List<Bundle> list) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onSearchResultChanged(@NonNull String str, int i, @Nullable Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onSeekCompleted(long j, long j2, long j3) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onShuffleModeChanged(int i) throws RemoteException;
    }

    public static final class ControllerInfo {
        private final ControllerCb mControllerCb;
        private final boolean mIsTrusted = false;
        private final String mPackageName;
        private final int mUid;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        ControllerInfo(@NonNull String str, int i, int i2, @NonNull ControllerCb controllerCb) {
            this.mUid = i2;
            this.mPackageName = str;
            this.mControllerCb = controllerCb;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ControllerInfo)) {
                return false;
            }
            return this.mControllerCb.equals(((ControllerInfo) obj).mControllerCb);
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public ControllerCb getControllerCb() {
            return this.mControllerCb;
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public IBinder getId() {
            return this.mControllerCb.getId();
        }

        @NonNull
        public String getPackageName() {
            return this.mPackageName;
        }

        public int getUid() {
            return this.mUid;
        }

        public int hashCode() {
            return this.mControllerCb.hashCode();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public boolean isTrusted() {
            return this.mIsTrusted;
        }

        public String toString() {
            return "ControllerInfo {pkg=" + this.mPackageName + ", uid=" + this.mUid + "})";
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ErrorCode {
    }

    static class MainHandlerExecutor implements Executor {
        private final Handler mHandler;

        MainHandlerExecutor(Context context) {
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void execute(Runnable runnable) {
            if (!this.mHandler.post(runnable)) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    public interface OnDataSourceMissingHelper {
        @Nullable
        DataSourceDesc onDataSourceMissing(@NonNull MediaSession2 mediaSession2, @NonNull MediaItem2 mediaItem2);
    }

    public static abstract class SessionCallback {
        public void onBufferingStateChanged(@NonNull MediaSession2 mediaSession2, @NonNull BaseMediaPlayer baseMediaPlayer, @NonNull MediaItem2 mediaItem2, int i) {
        }

        public boolean onCommandRequest(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull SessionCommand2 sessionCommand2) {
            return true;
        }

        @Nullable
        public SessionCommandGroup2 onConnect(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo) {
            SessionCommandGroup2 sessionCommandGroup2 = new SessionCommandGroup2();
            sessionCommandGroup2.addAllPredefinedCommands();
            return sessionCommandGroup2;
        }

        public void onCurrentMediaItemChanged(@NonNull MediaSession2 mediaSession2, @NonNull BaseMediaPlayer baseMediaPlayer, @Nullable MediaItem2 mediaItem2) {
        }

        public void onCustomCommand(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull SessionCommand2 sessionCommand2, @Nullable Bundle bundle, @Nullable ResultReceiver resultReceiver) {
        }

        public void onDisconnected(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo) {
        }

        public void onFastForward(@NonNull MediaSession2 mediaSession2, ControllerInfo controllerInfo) {
        }

        public void onMediaPrepared(@NonNull MediaSession2 mediaSession2, @NonNull BaseMediaPlayer baseMediaPlayer, @NonNull MediaItem2 mediaItem2) {
        }

        public void onPlayFromMediaId(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle) {
        }

        public void onPlayFromSearch(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle) {
        }

        public void onPlayFromUri(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull Uri uri, @Nullable Bundle bundle) {
        }

        public void onPlaybackSpeedChanged(@NonNull MediaSession2 mediaSession2, @NonNull BaseMediaPlayer baseMediaPlayer, float f2) {
        }

        public void onPlayerStateChanged(@NonNull MediaSession2 mediaSession2, @NonNull BaseMediaPlayer baseMediaPlayer, int i) {
        }

        public void onPlaylistChanged(@NonNull MediaSession2 mediaSession2, @NonNull MediaPlaylistAgent mediaPlaylistAgent, @NonNull List<MediaItem2> list, @Nullable MediaMetadata2 mediaMetadata2) {
        }

        public void onPlaylistMetadataChanged(@NonNull MediaSession2 mediaSession2, @NonNull MediaPlaylistAgent mediaPlaylistAgent, @Nullable MediaMetadata2 mediaMetadata2) {
        }

        public void onPrepareFromMediaId(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle) {
        }

        public void onPrepareFromSearch(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull String str, @Nullable Bundle bundle) {
        }

        public void onPrepareFromUri(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull Uri uri, @Nullable Bundle bundle) {
        }

        public void onRepeatModeChanged(@NonNull MediaSession2 mediaSession2, @NonNull MediaPlaylistAgent mediaPlaylistAgent, int i) {
        }

        public void onRewind(@NonNull MediaSession2 mediaSession2, ControllerInfo controllerInfo) {
        }

        public void onSeekCompleted(@NonNull MediaSession2 mediaSession2, @NonNull BaseMediaPlayer baseMediaPlayer, long j) {
        }

        public void onSelectRoute(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull Bundle bundle) {
        }

        public void onSetRating(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo, @NonNull String str, @NonNull Rating2 rating2) {
        }

        public void onShuffleModeChanged(@NonNull MediaSession2 mediaSession2, @NonNull MediaPlaylistAgent mediaPlaylistAgent, int i) {
        }

        public void onSubscribeRoutesInfo(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo) {
        }

        public void onUnsubscribeRoutesInfo(@NonNull MediaSession2 mediaSession2, @NonNull ControllerInfo controllerInfo) {
        }
    }

    interface SupportLibraryImpl extends MediaInterface2.SessionPlayer, AutoCloseable {
        AudioFocusHandler getAudioFocusHandler();

        SessionCallback getCallback();

        Executor getCallbackExecutor();

        @NonNull
        List<ControllerInfo> getConnectedControllers();

        Context getContext();

        MediaSession2 getInstance();

        MediaController2.PlaybackInfo getPlaybackInfo();

        PlaybackStateCompat getPlaybackStateCompat();

        @NonNull
        BaseMediaPlayer getPlayer();

        @NonNull
        MediaPlaylistAgent getPlaylistAgent();

        PendingIntent getSessionActivity();

        IBinder getSessionBinder();

        MediaSessionCompat getSessionCompat();

        @NonNull
        SessionToken2 getToken();

        @Nullable
        VolumeProviderCompat getVolumeProvider();

        boolean isClosed();

        void notifyRoutesInfoChanged(@NonNull ControllerInfo controllerInfo, @Nullable List<Bundle> list);

        void sendCustomCommand(@NonNull ControllerInfo controllerInfo, @NonNull SessionCommand2 sessionCommand2, @Nullable Bundle bundle, @Nullable ResultReceiver resultReceiver);

        void sendCustomCommand(@NonNull SessionCommand2 sessionCommand2, @Nullable Bundle bundle);

        void setAllowedCommands(@NonNull ControllerInfo controllerInfo, @NonNull SessionCommandGroup2 sessionCommandGroup2);

        void setCustomLayout(@NonNull ControllerInfo controllerInfo, @NonNull List<CommandButton> list);

        void updatePlayer(@NonNull BaseMediaPlayer baseMediaPlayer, @Nullable MediaPlaylistAgent mediaPlaylistAgent, @Nullable VolumeProviderCompat volumeProviderCompat);
    }

    MediaSession2(Context context, String str, BaseMediaPlayer baseMediaPlayer, MediaPlaylistAgent mediaPlaylistAgent, VolumeProviderCompat volumeProviderCompat, PendingIntent pendingIntent, Executor executor, SessionCallback sessionCallback) {
        this.mImpl = createImpl(context, str, baseMediaPlayer, mediaPlaylistAgent, volumeProviderCompat, pendingIntent, executor, sessionCallback);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void addPlaylistItem(int i, @NonNull MediaItem2 mediaItem2) {
        this.mImpl.addPlaylistItem(i, mediaItem2);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void clearOnDataSourceMissingHelper() {
        this.mImpl.clearOnDataSourceMissingHelper();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            this.mImpl.close();
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl createImpl(Context context, String str, BaseMediaPlayer baseMediaPlayer, MediaPlaylistAgent mediaPlaylistAgent, VolumeProviderCompat volumeProviderCompat, PendingIntent pendingIntent, Executor executor, SessionCallback sessionCallback) {
        return new MediaSession2ImplBase(this, context, str, baseMediaPlayer, mediaPlaylistAgent, volumeProviderCompat, pendingIntent, executor, sessionCallback);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public AudioFocusHandler getAudioFocusHandler() {
        return this.mImpl.getAudioFocusHandler();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public long getBufferedPosition() {
        return this.mImpl.getBufferedPosition();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public int getBufferingState() {
        return this.mImpl.getBufferingState();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SessionCallback getCallback() {
        return this.mImpl.getCallback();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Executor getCallbackExecutor() {
        return this.mImpl.getCallbackExecutor();
    }

    @NonNull
    public List<ControllerInfo> getConnectedControllers() {
        return this.mImpl.getConnectedControllers();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Context getContext() {
        return this.mImpl.getContext();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public MediaItem2 getCurrentMediaItem() {
        return this.mImpl.getCurrentMediaItem();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public long getCurrentPosition() {
        return this.mImpl.getCurrentPosition();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public long getDuration() {
        return this.mImpl.getDuration();
    }

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl getImpl() {
        return this.mImpl;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public float getPlaybackSpeed() {
        return this.mImpl.getPlaybackSpeed();
    }

    @NonNull
    public BaseMediaPlayer getPlayer() {
        return this.mImpl.getPlayer();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public int getPlayerState() {
        return this.mImpl.getPlayerState();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public List<MediaItem2> getPlaylist() {
        return this.mImpl.getPlaylist();
    }

    @NonNull
    public MediaPlaylistAgent getPlaylistAgent() {
        return this.mImpl.getPlaylistAgent();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public MediaMetadata2 getPlaylistMetadata() {
        return this.mImpl.getPlaylistMetadata();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public int getRepeatMode() {
        return this.mImpl.getRepeatMode();
    }

    /* access modifiers changed from: package-private */
    public IBinder getSessionBinder() {
        return this.mImpl.getSessionBinder();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public MediaSessionCompat getSessionCompat() {
        return this.mImpl.getSessionCompat();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public int getShuffleMode() {
        return this.mImpl.getShuffleMode();
    }

    @NonNull
    public SessionToken2 getToken() {
        return this.mImpl.getToken();
    }

    @Nullable
    public VolumeProviderCompat getVolumeProvider() {
        return this.mImpl.getVolumeProvider();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlayer
    public void notifyError(int i, @Nullable Bundle bundle) {
        this.mImpl.notifyError(i, bundle);
    }

    public void notifyRoutesInfoChanged(@NonNull ControllerInfo controllerInfo, @Nullable List<Bundle> list) {
        this.mImpl.notifyRoutesInfoChanged(controllerInfo, list);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void pause() {
        this.mImpl.pause();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void play() {
        this.mImpl.play();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void prepare() {
        this.mImpl.prepare();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void removePlaylistItem(@NonNull MediaItem2 mediaItem2) {
        this.mImpl.removePlaylistItem(mediaItem2);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void replacePlaylistItem(int i, @NonNull MediaItem2 mediaItem2) {
        this.mImpl.replacePlaylistItem(i, mediaItem2);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void reset() {
        this.mImpl.reset();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void seekTo(long j) {
        this.mImpl.seekTo(j);
    }

    public void sendCustomCommand(@NonNull ControllerInfo controllerInfo, @NonNull SessionCommand2 sessionCommand2, @Nullable Bundle bundle, @Nullable ResultReceiver resultReceiver) {
        this.mImpl.sendCustomCommand(controllerInfo, sessionCommand2, bundle, resultReceiver);
    }

    public void sendCustomCommand(@NonNull SessionCommand2 sessionCommand2, @Nullable Bundle bundle) {
        this.mImpl.sendCustomCommand(sessionCommand2, bundle);
    }

    public void setAllowedCommands(@NonNull ControllerInfo controllerInfo, @NonNull SessionCommandGroup2 sessionCommandGroup2) {
        this.mImpl.setAllowedCommands(controllerInfo, sessionCommandGroup2);
    }

    public void setCustomLayout(@NonNull ControllerInfo controllerInfo, @NonNull List<CommandButton> list) {
        this.mImpl.setCustomLayout(controllerInfo, list);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setOnDataSourceMissingHelper(@NonNull OnDataSourceMissingHelper onDataSourceMissingHelper) {
        this.mImpl.setOnDataSourceMissingHelper(onDataSourceMissingHelper);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void setPlaybackSpeed(float f2) {
        this.mImpl.setPlaybackSpeed(f2);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setPlaylist(@NonNull List<MediaItem2> list, @Nullable MediaMetadata2 mediaMetadata2) {
        this.mImpl.setPlaylist(list, mediaMetadata2);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setRepeatMode(int i) {
        this.mImpl.setRepeatMode(i);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setShuffleMode(int i) {
        this.mImpl.setShuffleMode(i);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlayer
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void skipBackward() {
        this.mImpl.skipBackward();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlayer
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void skipForward() {
        this.mImpl.skipForward();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void skipToNextItem() {
        this.mImpl.skipToNextItem();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void skipToPlaylistItem(@NonNull MediaItem2 mediaItem2) {
        this.mImpl.skipToPlaylistItem(mediaItem2);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void skipToPreviousItem() {
        this.mImpl.skipToPreviousItem();
    }

    public void updatePlayer(@NonNull BaseMediaPlayer baseMediaPlayer, @Nullable MediaPlaylistAgent mediaPlaylistAgent, @Nullable VolumeProviderCompat volumeProviderCompat) {
        this.mImpl.updatePlayer(baseMediaPlayer, mediaPlaylistAgent, volumeProviderCompat);
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void updatePlaylistMetadata(@Nullable MediaMetadata2 mediaMetadata2) {
        this.mImpl.updatePlaylistMetadata(mediaMetadata2);
    }
}
