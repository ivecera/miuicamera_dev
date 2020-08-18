package android.support.v4.media;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.BaseMediaPlayer;
import android.support.v4.media.MediaController2;
import android.support.v4.media.MediaMetadata2;
import android.support.v4.media.MediaPlaylistAgent;
import android.support.v4.media.MediaSession2;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.util.ObjectsCompat;
import android.text.TextUtils;
import android.util.Log;
import com.ss.android.vesdk.VEEditor;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;

/* access modifiers changed from: package-private */
@TargetApi(19)
public class MediaSession2ImplBase implements MediaSession2.SupportLibraryImpl {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MS2ImplBase";
    /* access modifiers changed from: private */
    public final AudioFocusHandler mAudioFocusHandler;
    private final AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public final MediaSession2.SessionCallback mCallback;
    private final Executor mCallbackExecutor;
    private final Context mContext;
    @GuardedBy("mLock")
    private MediaSession2.OnDataSourceMissingHelper mDsmHelper;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final MediaSession2 mInstance;
    final Object mLock = new Object();
    @GuardedBy("mLock")
    private MediaController2.PlaybackInfo mPlaybackInfo;
    @GuardedBy("mLock")
    private BaseMediaPlayer mPlayer;
    private final BaseMediaPlayer.PlayerEventCallback mPlayerEventCallback;
    @GuardedBy("mLock")
    private MediaPlaylistAgent mPlaylistAgent;
    private final MediaPlaylistAgent.PlaylistEventCallback mPlaylistEventCallback;
    private final MediaSession2Stub mSession2Stub;
    private final PendingIntent mSessionActivity;
    private final MediaSessionCompat mSessionCompat;
    private final MediaSessionLegacyStub mSessionLegacyStub;
    @GuardedBy("mLock")
    private SessionPlaylistAgentImplBase mSessionPlaylistAgent;
    private final SessionToken2 mSessionToken;
    @GuardedBy("mLock")
    private VolumeProviderCompat mVolumeProvider;

    private static class MyPlayerEventCallback extends BaseMediaPlayer.PlayerEventCallback {
        private final WeakReference<MediaSession2ImplBase> mSession;

        private MyPlayerEventCallback(MediaSession2ImplBase mediaSession2ImplBase) {
            this.mSession = new WeakReference<>(mediaSession2ImplBase);
        }

        /* access modifiers changed from: private */
        public MediaItem2 getMediaItem(MediaSession2ImplBase mediaSession2ImplBase, DataSourceDesc dataSourceDesc) {
            MediaPlaylistAgent playlistAgent = mediaSession2ImplBase.getPlaylistAgent();
            if (playlistAgent != null) {
                MediaItem2 mediaItem = playlistAgent.getMediaItem(dataSourceDesc);
                if (mediaItem == null && MediaSession2ImplBase.DEBUG) {
                    Log.d(MediaSession2ImplBase.TAG, "Could not find matching item for dsd=" + dataSourceDesc, new NoSuchElementException());
                }
                return mediaItem;
            } else if (!MediaSession2ImplBase.DEBUG) {
                return null;
            } else {
                Log.d(MediaSession2ImplBase.TAG, "Session is closed", new IllegalStateException());
                return null;
            }
        }

        private MediaSession2ImplBase getSession() {
            MediaSession2ImplBase mediaSession2ImplBase = this.mSession.get();
            if (mediaSession2ImplBase == null && MediaSession2ImplBase.DEBUG) {
                Log.d(MediaSession2ImplBase.TAG, "Session is closed", new IllegalStateException());
            }
            return mediaSession2ImplBase;
        }

        @Override // android.support.v4.media.BaseMediaPlayer.PlayerEventCallback
        public void onBufferingStateChanged(final BaseMediaPlayer baseMediaPlayer, final DataSourceDesc dataSourceDesc, final int i) {
            final MediaSession2ImplBase session = getSession();
            if (session != null && dataSourceDesc != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass4 */

                    public void run() {
                        final MediaItem2 access$300 = MyPlayerEventCallback.this.getMediaItem(session, dataSourceDesc);
                        if (access$300 != null) {
                            session.getCallback().onBufferingStateChanged(session.getInstance(), baseMediaPlayer, access$300, i);
                            session.notifyToAllControllers(new NotifyRunnable() {
                                /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass4.AnonymousClass1 */

                                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                                    MediaItem2 mediaItem2 = access$300;
                                    AnonymousClass4 r4 = AnonymousClass4.this;
                                    controllerCb.onBufferingStateChanged(mediaItem2, i, baseMediaPlayer.getBufferedPosition());
                                }
                            });
                        }
                    }
                });
            }
        }

        @Override // android.support.v4.media.BaseMediaPlayer.PlayerEventCallback
        public void onCurrentDataSourceChanged(final BaseMediaPlayer baseMediaPlayer, final DataSourceDesc dataSourceDesc) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass1 */

                    public void run() {
                        final MediaItem2 mediaItem2;
                        DataSourceDesc dataSourceDesc = dataSourceDesc;
                        if (dataSourceDesc == null) {
                            mediaItem2 = null;
                        } else {
                            mediaItem2 = MyPlayerEventCallback.this.getMediaItem(session, dataSourceDesc);
                            if (mediaItem2 == null) {
                                Log.w(MediaSession2ImplBase.TAG, "Cannot obtain media item from the dsd=" + dataSourceDesc);
                                return;
                            }
                        }
                        session.getCallback().onCurrentMediaItemChanged(session.getInstance(), baseMediaPlayer, mediaItem2);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass1.AnonymousClass1 */

                            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                                controllerCb.onCurrentMediaItemChanged(mediaItem2);
                            }
                        });
                    }
                });
            }
        }

        @Override // android.support.v4.media.BaseMediaPlayer.PlayerEventCallback
        public void onMediaPrepared(final BaseMediaPlayer baseMediaPlayer, final DataSourceDesc dataSourceDesc) {
            final MediaSession2ImplBase session = getSession();
            if (session != null && dataSourceDesc != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass2 */

                    public void run() {
                        MediaMetadata2 mediaMetadata2;
                        MediaItem2 access$300 = MyPlayerEventCallback.this.getMediaItem(session, dataSourceDesc);
                        if (access$300 != null) {
                            if (access$300.equals(session.getCurrentMediaItem())) {
                                long duration = session.getDuration();
                                if (duration >= 0) {
                                    MediaMetadata2 metadata = access$300.getMetadata();
                                    if (metadata == null) {
                                        mediaMetadata2 = new MediaMetadata2.Builder().putLong("android.media.metadata.DURATION", duration).putString("android.media.metadata.MEDIA_ID", access$300.getMediaId()).build();
                                    } else if (!metadata.containsKey("android.media.metadata.DURATION")) {
                                        mediaMetadata2 = new MediaMetadata2.Builder(metadata).putLong("android.media.metadata.DURATION", duration).build();
                                    } else {
                                        long j = metadata.getLong("android.media.metadata.DURATION");
                                        if (duration != j) {
                                            Log.w(MediaSession2ImplBase.TAG, "duration mismatch for an item. duration from player=" + duration + " duration from metadata=" + j + ". May be a timing issue?");
                                        }
                                        mediaMetadata2 = null;
                                    }
                                    if (mediaMetadata2 != null) {
                                        access$300.setMetadata(mediaMetadata2);
                                        session.notifyToAllControllers(new NotifyRunnable() {
                                            /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass2.AnonymousClass1 */

                                            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                                            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                                                controllerCb.onPlaylistChanged(session.getPlaylist(), session.getPlaylistMetadata());
                                            }
                                        });
                                    }
                                } else {
                                    return;
                                }
                            }
                            session.getCallback().onMediaPrepared(session.getInstance(), baseMediaPlayer, access$300);
                        }
                    }
                });
            }
        }

        @Override // android.support.v4.media.BaseMediaPlayer.PlayerEventCallback
        public void onPlaybackSpeedChanged(final BaseMediaPlayer baseMediaPlayer, final float f2) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass5 */

                    public void run() {
                        session.getCallback().onPlaybackSpeedChanged(session.getInstance(), baseMediaPlayer, f2);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass5.AnonymousClass1 */

                            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                                controllerCb.onPlaybackSpeedChanged(SystemClock.elapsedRealtime(), session.getCurrentPosition(), f2);
                            }
                        });
                    }
                });
            }
        }

        @Override // android.support.v4.media.BaseMediaPlayer.PlayerEventCallback
        public void onPlayerStateChanged(final BaseMediaPlayer baseMediaPlayer, final int i) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass3 */

                    public void run() {
                        session.mAudioFocusHandler.onPlayerStateChanged(i);
                        session.getCallback().onPlayerStateChanged(session.getInstance(), baseMediaPlayer, i);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass3.AnonymousClass1 */

                            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                                controllerCb.onPlayerStateChanged(SystemClock.elapsedRealtime(), baseMediaPlayer.getCurrentPosition(), i);
                            }
                        });
                    }
                });
            }
        }

        @Override // android.support.v4.media.BaseMediaPlayer.PlayerEventCallback
        public void onSeekCompleted(final BaseMediaPlayer baseMediaPlayer, final long j) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass6 */

                    public void run() {
                        session.getCallback().onSeekCompleted(session.getInstance(), baseMediaPlayer, j);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            /* class android.support.v4.media.MediaSession2ImplBase.MyPlayerEventCallback.AnonymousClass6.AnonymousClass1 */

                            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                                controllerCb.onSeekCompleted(SystemClock.elapsedRealtime(), session.getCurrentPosition(), j);
                            }
                        });
                    }
                });
            }
        }
    }

    private static class MyPlaylistEventCallback extends MediaPlaylistAgent.PlaylistEventCallback {
        private final WeakReference<MediaSession2ImplBase> mSession;

        private MyPlaylistEventCallback(MediaSession2ImplBase mediaSession2ImplBase) {
            this.mSession = new WeakReference<>(mediaSession2ImplBase);
        }

        @Override // android.support.v4.media.MediaPlaylistAgent.PlaylistEventCallback
        public void onPlaylistChanged(MediaPlaylistAgent mediaPlaylistAgent, List<MediaItem2> list, MediaMetadata2 mediaMetadata2) {
            MediaSession2ImplBase mediaSession2ImplBase = this.mSession.get();
            if (mediaSession2ImplBase != null) {
                mediaSession2ImplBase.notifyPlaylistChangedOnExecutor(mediaPlaylistAgent, list, mediaMetadata2);
            }
        }

        @Override // android.support.v4.media.MediaPlaylistAgent.PlaylistEventCallback
        public void onPlaylistMetadataChanged(MediaPlaylistAgent mediaPlaylistAgent, MediaMetadata2 mediaMetadata2) {
            MediaSession2ImplBase mediaSession2ImplBase = this.mSession.get();
            if (mediaSession2ImplBase != null) {
                mediaSession2ImplBase.notifyPlaylistMetadataChangedOnExecutor(mediaPlaylistAgent, mediaMetadata2);
            }
        }

        @Override // android.support.v4.media.MediaPlaylistAgent.PlaylistEventCallback
        public void onRepeatModeChanged(MediaPlaylistAgent mediaPlaylistAgent, int i) {
            MediaSession2ImplBase mediaSession2ImplBase = this.mSession.get();
            if (mediaSession2ImplBase != null) {
                mediaSession2ImplBase.notifyRepeatModeChangedOnExecutor(mediaPlaylistAgent, i);
            }
        }

        @Override // android.support.v4.media.MediaPlaylistAgent.PlaylistEventCallback
        public void onShuffleModeChanged(MediaPlaylistAgent mediaPlaylistAgent, int i) {
            MediaSession2ImplBase mediaSession2ImplBase = this.mSession.get();
            if (mediaSession2ImplBase != null) {
                mediaSession2ImplBase.notifyShuffleModeChangedOnExecutor(mediaPlaylistAgent, i);
            }
        }
    }

    @FunctionalInterface
    interface NotifyRunnable {
        void run(MediaSession2.ControllerCb controllerCb) throws RemoteException;
    }

    MediaSession2ImplBase(MediaSession2 mediaSession2, Context context, String str, BaseMediaPlayer baseMediaPlayer, MediaPlaylistAgent mediaPlaylistAgent, VolumeProviderCompat volumeProviderCompat, PendingIntent pendingIntent, Executor executor, MediaSession2.SessionCallback sessionCallback) {
        this.mContext = context;
        this.mInstance = mediaSession2;
        this.mHandlerThread = new HandlerThread("MediaController2_Thread");
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mSession2Stub = new MediaSession2Stub(this);
        this.mSessionLegacyStub = new MediaSessionLegacyStub(this);
        this.mSessionActivity = pendingIntent;
        this.mCallback = sessionCallback;
        this.mCallbackExecutor = executor;
        this.mAudioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        this.mPlayerEventCallback = new MyPlayerEventCallback();
        this.mPlaylistEventCallback = new MyPlaylistEventCallback();
        this.mAudioFocusHandler = new AudioFocusHandler(context, getInstance());
        String serviceName = getServiceName(context, MediaLibraryService2.SERVICE_INTERFACE, str);
        String serviceName2 = getServiceName(context, MediaSessionService2.SERVICE_INTERFACE, str);
        if (serviceName2 == null || serviceName == null) {
            if (serviceName != null) {
                this.mSessionToken = new SessionToken2(new SessionToken2ImplBase(Process.myUid(), 2, context.getPackageName(), serviceName, str, this.mSession2Stub));
            } else if (serviceName2 != null) {
                this.mSessionToken = new SessionToken2(new SessionToken2ImplBase(Process.myUid(), 1, context.getPackageName(), serviceName2, str, this.mSession2Stub));
            } else {
                this.mSessionToken = new SessionToken2(new SessionToken2ImplBase(Process.myUid(), 0, context.getPackageName(), null, str, this.mSession2Stub));
            }
            this.mSessionCompat = new MediaSessionCompat(context, str, this.mSessionToken);
            this.mSessionCompat.setCallback(this.mSessionLegacyStub, this.mHandler);
            this.mSessionCompat.setSessionActivity(pendingIntent);
            updatePlayer(baseMediaPlayer, mediaPlaylistAgent, volumeProviderCompat);
            return;
        }
        throw new IllegalArgumentException("Ambiguous session type. Multiple session services define the same id=" + str);
    }

    private MediaController2.PlaybackInfo createPlaybackInfo(VolumeProviderCompat volumeProviderCompat, AudioAttributesCompat audioAttributesCompat) {
        int i = 2;
        if (volumeProviderCompat != null) {
            return MediaController2.PlaybackInfo.createPlaybackInfo(2, audioAttributesCompat, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume());
        }
        int legacyStreamType = getLegacyStreamType(audioAttributesCompat);
        if (Build.VERSION.SDK_INT >= 21 && this.mAudioManager.isVolumeFixed()) {
            i = 0;
        }
        return MediaController2.PlaybackInfo.createPlaybackInfo(1, audioAttributesCompat, i, this.mAudioManager.getStreamMaxVolume(legacyStreamType), this.mAudioManager.getStreamVolume(legacyStreamType));
    }

    private int getLegacyStreamType(@Nullable AudioAttributesCompat audioAttributesCompat) {
        int legacyStreamType;
        if (audioAttributesCompat == null || (legacyStreamType = audioAttributesCompat.getLegacyStreamType()) == Integer.MIN_VALUE) {
            return 3;
        }
        return legacyStreamType;
    }

    private static String getServiceName(Context context, String str, String str2) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(str);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 128);
        String str3 = null;
        if (queryIntentServices != null) {
            for (int i = 0; i < queryIntentServices.size(); i++) {
                String sessionId = SessionToken2.getSessionId(queryIntentServices.get(i));
                if (!(sessionId == null || !TextUtils.equals(str2, sessionId) || queryIntentServices.get(i).serviceInfo == null)) {
                    if (str3 == null) {
                        str3 = queryIntentServices.get(i).serviceInfo.name;
                    } else {
                        throw new IllegalArgumentException("Ambiguous session type. Multiple session services define the same id=" + str2);
                    }
                }
            }
        }
        return str3;
    }

    private void notifyAgentUpdatedNotLocked(MediaPlaylistAgent mediaPlaylistAgent) {
        List<MediaItem2> playlist = mediaPlaylistAgent.getPlaylist();
        final List<MediaItem2> playlist2 = getPlaylist();
        if (!ObjectsCompat.equals(playlist, playlist2)) {
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass9 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onPlaylistChanged(playlist2, MediaSession2ImplBase.this.getPlaylistMetadata());
                }
            });
        } else {
            MediaMetadata2 playlistMetadata = mediaPlaylistAgent.getPlaylistMetadata();
            final MediaMetadata2 playlistMetadata2 = getPlaylistMetadata();
            if (!ObjectsCompat.equals(playlistMetadata, playlistMetadata2)) {
                notifyToAllControllers(new NotifyRunnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass10 */

                    @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                    public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                        controllerCb.onPlaylistMetadataChanged(playlistMetadata2);
                    }
                });
            }
        }
        MediaItem2 currentMediaItem = mediaPlaylistAgent.getCurrentMediaItem();
        final MediaItem2 currentMediaItem2 = getCurrentMediaItem();
        if (!ObjectsCompat.equals(currentMediaItem, currentMediaItem2)) {
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass11 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onCurrentMediaItemChanged(currentMediaItem2);
                }
            });
        }
        final int repeatMode = getRepeatMode();
        if (mediaPlaylistAgent.getRepeatMode() != repeatMode) {
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass12 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onRepeatModeChanged(repeatMode);
                }
            });
        }
        final int shuffleMode = getShuffleMode();
        if (mediaPlaylistAgent.getShuffleMode() != shuffleMode) {
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass13 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onShuffleModeChanged(shuffleMode);
                }
            });
        }
    }

    private void notifyPlayerUpdatedNotLocked(BaseMediaPlayer baseMediaPlayer) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        final long currentPosition = getCurrentPosition();
        final int playerState = getPlayerState();
        notifyToAllControllers(new NotifyRunnable() {
            /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass14 */

            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                controllerCb.onPlayerStateChanged(elapsedRealtime, currentPosition, playerState);
            }
        });
        final MediaItem2 currentMediaItem = getCurrentMediaItem();
        if (currentMediaItem != null) {
            final int bufferingState = getBufferingState();
            final long bufferedPosition = getBufferedPosition();
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass15 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onBufferingStateChanged(currentMediaItem, bufferingState, bufferedPosition);
                }
            });
        }
        final float playbackSpeed = getPlaybackSpeed();
        if (playbackSpeed != baseMediaPlayer.getPlaybackSpeed()) {
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass16 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onPlaybackSpeedChanged(elapsedRealtime, currentPosition, playbackSpeed);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyPlaylistChangedOnExecutor(MediaPlaylistAgent mediaPlaylistAgent, final List<MediaItem2> list, final MediaMetadata2 mediaMetadata2) {
        synchronized (this.mLock) {
            if (mediaPlaylistAgent == this.mPlaylistAgent) {
                this.mCallback.onPlaylistChanged(this.mInstance, mediaPlaylistAgent, list, mediaMetadata2);
                notifyToAllControllers(new NotifyRunnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass17 */

                    @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                    public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                        controllerCb.onPlaylistChanged(list, mediaMetadata2);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyPlaylistMetadataChangedOnExecutor(MediaPlaylistAgent mediaPlaylistAgent, final MediaMetadata2 mediaMetadata2) {
        synchronized (this.mLock) {
            if (mediaPlaylistAgent == this.mPlaylistAgent) {
                this.mCallback.onPlaylistMetadataChanged(this.mInstance, mediaPlaylistAgent, mediaMetadata2);
                notifyToAllControllers(new NotifyRunnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass18 */

                    @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                    public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                        controllerCb.onPlaylistMetadataChanged(mediaMetadata2);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyRepeatModeChangedOnExecutor(MediaPlaylistAgent mediaPlaylistAgent, final int i) {
        synchronized (this.mLock) {
            if (mediaPlaylistAgent == this.mPlaylistAgent) {
                this.mCallback.onRepeatModeChanged(this.mInstance, mediaPlaylistAgent, i);
                notifyToAllControllers(new NotifyRunnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass19 */

                    @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                    public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                        controllerCb.onRepeatModeChanged(i);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyShuffleModeChangedOnExecutor(MediaPlaylistAgent mediaPlaylistAgent, final int i) {
        synchronized (this.mLock) {
            if (mediaPlaylistAgent == this.mPlaylistAgent) {
                this.mCallback.onShuffleModeChanged(this.mInstance, mediaPlaylistAgent, i);
                notifyToAllControllers(new NotifyRunnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass20 */

                    @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                    public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                        controllerCb.onShuffleModeChanged(i);
                    }
                });
            }
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void addPlaylistItem(int i, @NonNull MediaItem2 mediaItem2) {
        MediaPlaylistAgent mediaPlaylistAgent;
        if (i < 0) {
            throw new IllegalArgumentException("index shouldn't be negative");
        } else if (mediaItem2 != null) {
            synchronized (this.mLock) {
                mediaPlaylistAgent = this.mPlaylistAgent;
            }
            if (mediaPlaylistAgent != null) {
                mediaPlaylistAgent.addPlaylistItem(i, mediaItem2);
            } else if (DEBUG) {
                Log.d(TAG, "API calls after the close()", new IllegalStateException());
            }
        } else {
            throw new IllegalArgumentException("item shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void clearOnDataSourceMissingHelper() {
        synchronized (this.mLock) {
            this.mDsmHelper = null;
            if (this.mSessionPlaylistAgent != null) {
                this.mSessionPlaylistAgent.clearOnDataSourceMissingHelper();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        return;
     */
    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mPlayer != null) {
                this.mAudioFocusHandler.close();
                this.mPlayer.unregisterPlayerEventCallback(this.mPlayerEventCallback);
                this.mPlayer = null;
                this.mSessionCompat.release();
                notifyToAllControllers(new NotifyRunnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass2 */

                    @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                    public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                        controllerCb.onDisconnected();
                    }
                });
                this.mHandler.removeCallbacksAndMessages(null);
                if (this.mHandlerThread.isAlive()) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        this.mHandlerThread.quitSafely();
                    } else {
                        this.mHandlerThread.quit();
                    }
                }
            }
        }
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public AudioFocusHandler getAudioFocusHandler() {
        return this.mAudioFocusHandler;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public long getBufferedPosition() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            return baseMediaPlayer.getBufferedPosition();
        }
        if (!DEBUG) {
            return -1;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return -1;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public int getBufferingState() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            return baseMediaPlayer.getBufferingState();
        }
        if (!DEBUG) {
            return 0;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return 0;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public MediaSession2.SessionCallback getCallback() {
        return this.mCallback;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public Executor getCallbackExecutor() {
        return this.mCallbackExecutor;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @NonNull
    public List<MediaSession2.ControllerInfo> getConnectedControllers() {
        return this.mSession2Stub.getConnectedControllers();
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public Context getContext() {
        return this.mContext;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public MediaItem2 getCurrentMediaItem() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            return mediaPlaylistAgent.getCurrentMediaItem();
        }
        if (!DEBUG) {
            return null;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return null;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public long getCurrentPosition() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            return baseMediaPlayer.getCurrentPosition();
        }
        if (!DEBUG) {
            return -1;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return -1;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public long getDuration() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            return baseMediaPlayer.getDuration();
        }
        if (!DEBUG) {
            return -1;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return -1;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @NonNull
    public MediaSession2 getInstance() {
        return this.mInstance;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public MediaController2.PlaybackInfo getPlaybackInfo() {
        MediaController2.PlaybackInfo playbackInfo;
        synchronized (this.mLock) {
            playbackInfo = this.mPlaybackInfo;
        }
        return playbackInfo;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public float getPlaybackSpeed() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            return baseMediaPlayer.getPlaybackSpeed();
        }
        if (!DEBUG) {
            return 1.0f;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return 1.0f;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public PlaybackStateCompat getPlaybackStateCompat() {
        PlaybackStateCompat build;
        synchronized (this.mLock) {
            build = new PlaybackStateCompat.Builder().setState(MediaUtils2.convertToPlaybackStateCompatState(getPlayerState(), getBufferingState()), getCurrentPosition(), getPlaybackSpeed()).setActions(3670015).setBufferedPosition(getBufferedPosition()).build();
        }
        return build;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @NonNull
    public BaseMediaPlayer getPlayer() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        return baseMediaPlayer;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public int getPlayerState() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            return baseMediaPlayer.getPlayerState();
        }
        if (!DEBUG) {
            return 3;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return 3;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public List<MediaItem2> getPlaylist() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            return mediaPlaylistAgent.getPlaylist();
        }
        if (!DEBUG) {
            return null;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return null;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @NonNull
    public MediaPlaylistAgent getPlaylistAgent() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        return mediaPlaylistAgent;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public MediaMetadata2 getPlaylistMetadata() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            return mediaPlaylistAgent.getPlaylistMetadata();
        }
        if (!DEBUG) {
            return null;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return null;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public int getRepeatMode() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            return mediaPlaylistAgent.getRepeatMode();
        }
        if (!DEBUG) {
            return 0;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return 0;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public PendingIntent getSessionActivity() {
        return this.mSessionActivity;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @NonNull
    public IBinder getSessionBinder() {
        return this.mSession2Stub.asBinder();
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public MediaSessionCompat getSessionCompat() {
        return this.mSessionCompat;
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public int getShuffleMode() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            return mediaPlaylistAgent.getShuffleMode();
        }
        if (!DEBUG) {
            return 0;
        }
        Log.d(TAG, "API calls after the close()", new IllegalStateException());
        return 0;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @NonNull
    public SessionToken2 getToken() {
        return this.mSessionToken;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    @Nullable
    public VolumeProviderCompat getVolumeProvider() {
        VolumeProviderCompat volumeProviderCompat;
        synchronized (this.mLock) {
            volumeProviderCompat = this.mVolumeProvider;
        }
        return volumeProviderCompat;
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public boolean isClosed() {
        return !this.mHandlerThread.isAlive();
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlayer
    public void notifyError(final int i, @Nullable final Bundle bundle) {
        notifyToAllControllers(new NotifyRunnable() {
            /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass7 */

            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                controllerCb.onError(i, bundle);
            }
        });
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public void notifyRoutesInfoChanged(@NonNull MediaSession2.ControllerInfo controllerInfo, @Nullable final List<Bundle> list) {
        notifyToController(controllerInfo, new NotifyRunnable() {
            /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass8 */

            @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
            public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                controllerCb.onRoutesInfoChanged(list);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyToAllControllers(@NonNull NotifyRunnable notifyRunnable) {
        List<MediaSession2.ControllerInfo> connectedControllers = getConnectedControllers();
        for (int i = 0; i < connectedControllers.size(); i++) {
            notifyToController(connectedControllers.get(i), notifyRunnable);
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyToController(@NonNull final MediaSession2.ControllerInfo controllerInfo, @NonNull NotifyRunnable notifyRunnable) {
        if (controllerInfo != null) {
            try {
                notifyRunnable.run(controllerInfo.getControllerCb());
            } catch (DeadObjectException e2) {
                if (DEBUG) {
                    Log.d(TAG, controllerInfo.toString() + " is gone", e2);
                }
                this.mSession2Stub.removeControllerInfo(controllerInfo);
                this.mCallbackExecutor.execute(new Runnable() {
                    /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass21 */

                    public void run() {
                        MediaSession2ImplBase.this.mCallback.onDisconnected(MediaSession2ImplBase.this.getInstance(), controllerInfo);
                    }
                });
            } catch (RemoteException e3) {
                Log.w(TAG, "Exception in " + controllerInfo.toString(), e3);
            }
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void pause() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            if (this.mAudioFocusHandler.onPauseRequested()) {
                baseMediaPlayer.pause();
            } else {
                Log.w(TAG, "pause() wouldn't be called of the failure in audio focus");
            }
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void play() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            if (this.mAudioFocusHandler.onPlayRequested()) {
                baseMediaPlayer.play();
            } else {
                Log.w(TAG, "play() wouldn't be called because of the failure in audio focus");
            }
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void prepare() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            baseMediaPlayer.prepare();
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void removePlaylistItem(@NonNull MediaItem2 mediaItem2) {
        MediaPlaylistAgent mediaPlaylistAgent;
        if (mediaItem2 != null) {
            synchronized (this.mLock) {
                mediaPlaylistAgent = this.mPlaylistAgent;
            }
            if (mediaPlaylistAgent != null) {
                mediaPlaylistAgent.removePlaylistItem(mediaItem2);
            } else if (DEBUG) {
                Log.d(TAG, "API calls after the close()", new IllegalStateException());
            }
        } else {
            throw new IllegalArgumentException("item shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void replacePlaylistItem(int i, @NonNull MediaItem2 mediaItem2) {
        MediaPlaylistAgent mediaPlaylistAgent;
        if (i < 0) {
            throw new IllegalArgumentException("index shouldn't be negative");
        } else if (mediaItem2 != null) {
            synchronized (this.mLock) {
                mediaPlaylistAgent = this.mPlaylistAgent;
            }
            if (mediaPlaylistAgent != null) {
                mediaPlaylistAgent.replacePlaylistItem(i, mediaItem2);
            } else if (DEBUG) {
                Log.d(TAG, "API calls after the close()", new IllegalStateException());
            }
        } else {
            throw new IllegalArgumentException("item shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void reset() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            baseMediaPlayer.reset();
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void seekTo(long j) {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            baseMediaPlayer.seekTo(j);
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public void sendCustomCommand(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull final SessionCommand2 sessionCommand2, @Nullable final Bundle bundle, @Nullable final ResultReceiver resultReceiver) {
        if (controllerInfo == null) {
            throw new IllegalArgumentException("controller shouldn't be null");
        } else if (sessionCommand2 != null) {
            notifyToController(controllerInfo, new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass6 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onCustomCommand(sessionCommand2, bundle, resultReceiver);
                }
            });
        } else {
            throw new IllegalArgumentException("command shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public void sendCustomCommand(@NonNull final SessionCommand2 sessionCommand2, @Nullable final Bundle bundle) {
        if (sessionCommand2 != null) {
            notifyToAllControllers(new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass5 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onCustomCommand(sessionCommand2, bundle, null);
                }
            });
            return;
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public void setAllowedCommands(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull final SessionCommandGroup2 sessionCommandGroup2) {
        if (controllerInfo == null) {
            throw new IllegalArgumentException("controller shouldn't be null");
        } else if (sessionCommandGroup2 != null) {
            this.mSession2Stub.setAllowedCommands(controllerInfo, sessionCommandGroup2);
            notifyToController(controllerInfo, new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass4 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onAllowedCommandsChanged(sessionCommandGroup2);
                }
            });
        } else {
            throw new IllegalArgumentException("commands shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public void setCustomLayout(@NonNull MediaSession2.ControllerInfo controllerInfo, @NonNull final List<MediaSession2.CommandButton> list) {
        if (controllerInfo == null) {
            throw new IllegalArgumentException("controller shouldn't be null");
        } else if (list != null) {
            notifyToController(controllerInfo, new NotifyRunnable() {
                /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass3 */

                @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                    controllerCb.onCustomLayoutChanged(list);
                }
            });
        } else {
            throw new IllegalArgumentException("layout shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setOnDataSourceMissingHelper(@NonNull MediaSession2.OnDataSourceMissingHelper onDataSourceMissingHelper) {
        if (onDataSourceMissingHelper != null) {
            synchronized (this.mLock) {
                this.mDsmHelper = onDataSourceMissingHelper;
                if (this.mSessionPlaylistAgent != null) {
                    this.mSessionPlaylistAgent.setOnDataSourceMissingHelper(onDataSourceMissingHelper);
                }
            }
            return;
        }
        throw new IllegalArgumentException("helper shouldn't be null");
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaybackControl
    public void setPlaybackSpeed(float f2) {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        if (baseMediaPlayer != null) {
            baseMediaPlayer.setPlaybackSpeed(f2);
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setPlaylist(@NonNull List<MediaItem2> list, @Nullable MediaMetadata2 mediaMetadata2) {
        MediaPlaylistAgent mediaPlaylistAgent;
        if (list != null) {
            synchronized (this.mLock) {
                mediaPlaylistAgent = this.mPlaylistAgent;
            }
            if (mediaPlaylistAgent != null) {
                mediaPlaylistAgent.setPlaylist(list, mediaMetadata2);
            } else if (DEBUG) {
                Log.d(TAG, "API calls after the close()", new IllegalStateException());
            }
        } else {
            throw new IllegalArgumentException("list shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setRepeatMode(int i) {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            mediaPlaylistAgent.setRepeatMode(i);
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void setShuffleMode(int i) {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            mediaPlaylistAgent.setShuffleMode(i);
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlayer
    public void skipBackward() {
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlayer
    public void skipForward() {
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void skipToNextItem() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            mediaPlaylistAgent.skipToNextItem();
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void skipToPlaylistItem(@NonNull MediaItem2 mediaItem2) {
        MediaPlaylistAgent mediaPlaylistAgent;
        if (mediaItem2 != null) {
            synchronized (this.mLock) {
                mediaPlaylistAgent = this.mPlaylistAgent;
            }
            if (mediaPlaylistAgent != null) {
                mediaPlaylistAgent.skipToPlaylistItem(mediaItem2);
            } else if (DEBUG) {
                Log.d(TAG, "API calls after the close()", new IllegalStateException());
            }
        } else {
            throw new IllegalArgumentException("item shouldn't be null");
        }
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void skipToPreviousItem() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            mediaPlaylistAgent.skipToPreviousItem();
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }

    @Override // android.support.v4.media.MediaSession2.SupportLibraryImpl
    public void updatePlayer(@NonNull BaseMediaPlayer baseMediaPlayer, @Nullable MediaPlaylistAgent mediaPlaylistAgent, @Nullable VolumeProviderCompat volumeProviderCompat) {
        boolean z;
        boolean z2;
        boolean z3;
        BaseMediaPlayer baseMediaPlayer2;
        MediaPlaylistAgent mediaPlaylistAgent2;
        if (baseMediaPlayer != null) {
            final MediaController2.PlaybackInfo createPlaybackInfo = createPlaybackInfo(volumeProviderCompat, baseMediaPlayer.getAudioAttributes());
            synchronized (this.mLock) {
                z = true;
                z2 = this.mPlayer != baseMediaPlayer;
                z3 = this.mPlaylistAgent != mediaPlaylistAgent;
                if (this.mPlaybackInfo == createPlaybackInfo) {
                    z = false;
                }
                baseMediaPlayer2 = this.mPlayer;
                mediaPlaylistAgent2 = this.mPlaylistAgent;
                this.mPlayer = baseMediaPlayer;
                if (mediaPlaylistAgent == null) {
                    this.mSessionPlaylistAgent = new SessionPlaylistAgentImplBase(this, this.mPlayer);
                    if (this.mDsmHelper != null) {
                        this.mSessionPlaylistAgent.setOnDataSourceMissingHelper(this.mDsmHelper);
                    }
                    mediaPlaylistAgent = this.mSessionPlaylistAgent;
                }
                this.mPlaylistAgent = mediaPlaylistAgent;
                this.mVolumeProvider = volumeProviderCompat;
                this.mPlaybackInfo = createPlaybackInfo;
            }
            if (volumeProviderCompat == null) {
                this.mSessionCompat.setPlaybackToLocal(getLegacyStreamType(baseMediaPlayer.getAudioAttributes()));
            }
            if (baseMediaPlayer != baseMediaPlayer2) {
                baseMediaPlayer.registerPlayerEventCallback(this.mCallbackExecutor, this.mPlayerEventCallback);
                if (baseMediaPlayer2 != null) {
                    baseMediaPlayer2.unregisterPlayerEventCallback(this.mPlayerEventCallback);
                }
            }
            if (mediaPlaylistAgent != mediaPlaylistAgent2) {
                mediaPlaylistAgent.registerPlaylistEventCallback(this.mCallbackExecutor, this.mPlaylistEventCallback);
                if (mediaPlaylistAgent2 != null) {
                    mediaPlaylistAgent2.unregisterPlaylistEventCallback(this.mPlaylistEventCallback);
                }
            }
            if (baseMediaPlayer2 != null) {
                if (z3) {
                    notifyAgentUpdatedNotLocked(mediaPlaylistAgent2);
                }
                if (z2) {
                    notifyPlayerUpdatedNotLocked(baseMediaPlayer2);
                }
                if (z) {
                    notifyToAllControllers(new NotifyRunnable() {
                        /* class android.support.v4.media.MediaSession2ImplBase.AnonymousClass1 */

                        @Override // android.support.v4.media.MediaSession2ImplBase.NotifyRunnable
                        public void run(MediaSession2.ControllerCb controllerCb) throws RemoteException {
                            controllerCb.onPlaybackInfoChanged(createPlaybackInfo);
                        }
                    });
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("player shouldn't be null");
    }

    @Override // android.support.v4.media.MediaInterface2.SessionPlaylistControl
    public void updatePlaylistMetadata(@Nullable MediaMetadata2 mediaMetadata2) {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        if (mediaPlaylistAgent != null) {
            mediaPlaylistAgent.updatePlaylistMetadata(mediaMetadata2);
        } else if (DEBUG) {
            Log.d(TAG, "API calls after the close()", new IllegalStateException());
        }
    }
}
