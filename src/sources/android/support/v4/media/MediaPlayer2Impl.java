package android.support.v4.media;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.DeniedByServerException;
import android.media.MediaDataSource;
import android.media.MediaDrm;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.media.PlaybackParams;
import android.media.ResourceBusyException;
import android.media.SubtitleData;
import android.media.SyncParams;
import android.media.TimedMetaData;
import android.media.UnsupportedSchemeException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.BaseMediaPlayer;
import android.support.v4.media.MediaPlayer2;
import android.support.v4.media.PlaybackParams2;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Preconditions;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import com.android.camera.CameraIntentManager;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@TargetApi(28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class MediaPlayer2Impl extends MediaPlayer2 {
    private static final int SOURCE_STATE_ERROR = -1;
    private static final int SOURCE_STATE_INIT = 0;
    private static final int SOURCE_STATE_PREPARED = 2;
    private static final int SOURCE_STATE_PREPARING = 1;
    private static final String TAG = "MediaPlayer2Impl";
    /* access modifiers changed from: private */
    public static ArrayMap<Integer, Integer> sErrorEventMap = new ArrayMap<>();
    /* access modifiers changed from: private */
    public static ArrayMap<Integer, Integer> sInfoEventMap = new ArrayMap<>();
    /* access modifiers changed from: private */
    public static ArrayMap<Integer, Integer> sPrepareDrmStatusMap = new ArrayMap<>();
    /* access modifiers changed from: private */
    public static ArrayMap<Integer, Integer> sStateMap = new ArrayMap<>();
    /* access modifiers changed from: private */
    public BaseMediaPlayerImpl mBaseMediaPlayerImpl;
    /* access modifiers changed from: private */
    @GuardedBy("mTaskLock")
    public Task mCurrentTask;
    private Pair<Executor, MediaPlayer2.DrmEventCallback> mDrmEventCallbackRecord;
    private final Handler mEndPositionHandler;
    private HandlerThread mHandlerThread = new HandlerThread("MediaPlayer2TaskThread");
    private final Object mLock = new Object();
    private Pair<Executor, MediaPlayer2.EventCallback> mMp2EventCallbackRecord;
    @GuardedBy("mTaskLock")
    private final ArrayDeque<Task> mPendingTasks = new ArrayDeque<>();
    /* access modifiers changed from: private */
    public MediaPlayerSourceQueue mPlayer;
    private ArrayMap<BaseMediaPlayer.PlayerEventCallback, Executor> mPlayerEventCallbackMap = new ArrayMap<>();
    private final Handler mTaskHandler;
    /* access modifiers changed from: private */
    public final Object mTaskLock = new Object();

    /* access modifiers changed from: private */
    public class BaseMediaPlayerImpl extends BaseMediaPlayer {
        private BaseMediaPlayerImpl() {
        }

        @Override // java.lang.AutoCloseable
        public void close() throws Exception {
            MediaPlayer2Impl.this.close();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public AudioAttributesCompat getAudioAttributes() {
            return MediaPlayer2Impl.this.getAudioAttributes();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public long getBufferedPosition() {
            return MediaPlayer2Impl.this.getBufferedPosition();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public int getBufferingState() {
            return MediaPlayer2Impl.this.getBufferingState();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public DataSourceDesc getCurrentDataSource() {
            return MediaPlayer2Impl.this.getCurrentDataSource();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public long getCurrentPosition() {
            return MediaPlayer2Impl.this.getCurrentPosition();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public long getDuration() {
            return MediaPlayer2Impl.this.getDuration();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public float getPlaybackSpeed() {
            return MediaPlayer2Impl.this.getPlaybackParams().getSpeed().floatValue();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public int getPlayerState() {
            return MediaPlayer2Impl.this.getPlayerState();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public float getPlayerVolume() {
            return MediaPlayer2Impl.this.getPlayerVolume();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void loopCurrent(boolean z) {
            MediaPlayer2Impl.this.loopCurrent(z);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void pause() {
            MediaPlayer2Impl.this.pause();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void play() {
            MediaPlayer2Impl.this.play();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void prepare() {
            MediaPlayer2Impl.this.prepare();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void registerPlayerEventCallback(Executor executor, BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
            MediaPlayer2Impl.this.registerPlayerEventCallback(executor, playerEventCallback);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void reset() {
            MediaPlayer2Impl.this.reset();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void seekTo(long j) {
            MediaPlayer2Impl.this.seekTo(j);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void setAudioAttributes(AudioAttributesCompat audioAttributesCompat) {
            MediaPlayer2Impl.this.setAudioAttributes(audioAttributesCompat);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void setDataSource(DataSourceDesc dataSourceDesc) {
            MediaPlayer2Impl.this.setDataSource(dataSourceDesc);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void setNextDataSource(DataSourceDesc dataSourceDesc) {
            MediaPlayer2Impl.this.setNextDataSource(dataSourceDesc);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void setNextDataSources(List<DataSourceDesc> list) {
            MediaPlayer2Impl.this.setNextDataSources(list);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void setPlaybackSpeed(float f2) {
            MediaPlayer2Impl mediaPlayer2Impl = MediaPlayer2Impl.this;
            mediaPlayer2Impl.setPlaybackParams(new PlaybackParams2.Builder(mediaPlayer2Impl.getPlaybackParams().getPlaybackParams()).setSpeed(f2).build());
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void setPlayerVolume(float f2) {
            MediaPlayer2Impl.this.setPlayerVolume(f2);
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void skipToNext() {
            MediaPlayer2Impl.this.skipToNext();
        }

        @Override // android.support.v4.media.BaseMediaPlayer
        public void unregisterPlayerEventCallback(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
            MediaPlayer2Impl.this.unregisterPlayerEventCallback(playerEventCallback);
        }
    }

    /* access modifiers changed from: private */
    public static class DataSourceError {
        final DataSourceDesc mDSD;
        final int mExtra;
        final int mWhat;

        DataSourceError(DataSourceDesc dataSourceDesc, int i, int i2) {
            this.mDSD = dataSourceDesc;
            this.mWhat = i;
            this.mExtra = i2;
        }
    }

    /* access modifiers changed from: private */
    public interface DrmEventNotifier {
        void notify(MediaPlayer2.DrmEventCallback drmEventCallback);
    }

    public static final class DrmInfoImpl extends MediaPlayer2.DrmInfo {
        private Map<UUID, byte[]> mMapPssh;
        private UUID[] mSupportedSchemes;

        private DrmInfoImpl(Parcel parcel) {
            Log.v(MediaPlayer2Impl.TAG, "DrmInfoImpl(" + parcel + ") size " + parcel.dataSize());
            int readInt = parcel.readInt();
            byte[] bArr = new byte[readInt];
            parcel.readByteArray(bArr);
            Log.v(MediaPlayer2Impl.TAG, "DrmInfoImpl() PSSH: " + arrToHex(bArr));
            this.mMapPssh = parsePSSH(bArr, readInt);
            Log.v(MediaPlayer2Impl.TAG, "DrmInfoImpl() PSSH: " + this.mMapPssh);
            int readInt2 = parcel.readInt();
            this.mSupportedSchemes = new UUID[readInt2];
            for (int i = 0; i < readInt2; i++) {
                byte[] bArr2 = new byte[16];
                parcel.readByteArray(bArr2);
                this.mSupportedSchemes[i] = bytesToUUID(bArr2);
                Log.v(MediaPlayer2Impl.TAG, "DrmInfoImpl() supportedScheme[" + i + "]: " + this.mSupportedSchemes[i]);
            }
            Log.v(MediaPlayer2Impl.TAG, "DrmInfoImpl() Parcel psshsize: " + readInt + " supportedDRMsCount: " + readInt2);
        }

        private DrmInfoImpl(Map<UUID, byte[]> map, UUID[] uuidArr) {
            this.mMapPssh = map;
            this.mSupportedSchemes = uuidArr;
        }

        private String arrToHex(byte[] bArr) {
            String str = "0x";
            for (int i = 0; i < bArr.length; i++) {
                str = str + String.format("%02x", Byte.valueOf(bArr[i]));
            }
            return str;
        }

        private UUID bytesToUUID(byte[] bArr) {
            long j = 0;
            long j2 = 0;
            for (int i = 0; i < 8; i++) {
                int i2 = (7 - i) * 8;
                j |= (((long) bArr[i]) & 255) << i2;
                j2 |= (((long) bArr[i + 8]) & 255) << i2;
            }
            return new UUID(j, j2);
        }

        private DrmInfoImpl makeCopy() {
            return new DrmInfoImpl(this.mMapPssh, this.mSupportedSchemes);
        }

        private Map<UUID, byte[]> parsePSSH(byte[] bArr, int i) {
            byte b2;
            byte b3;
            HashMap hashMap = new HashMap();
            int i2 = i;
            int i3 = 0;
            int i4 = 0;
            while (i2 > 0) {
                if (i2 < 16) {
                    Log.w(MediaPlayer2Impl.TAG, String.format("parsePSSH: len is too short to parse UUID: (%d < 16) pssh: %d", Integer.valueOf(i2), Integer.valueOf(i)));
                    return null;
                }
                int i5 = i3 + 16;
                UUID bytesToUUID = bytesToUUID(Arrays.copyOfRange(bArr, i3, i5));
                int i6 = i2 - 16;
                if (i6 < 4) {
                    Log.w(MediaPlayer2Impl.TAG, String.format("parsePSSH: len is too short to parse datalen: (%d < 4) pssh: %d", Integer.valueOf(i6), Integer.valueOf(i)));
                    return null;
                }
                int i7 = i5 + 4;
                byte[] copyOfRange = Arrays.copyOfRange(bArr, i5, i7);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    b3 = ((copyOfRange[2] & 255) << 16) | ((copyOfRange[3] & 255) << 24) | ((copyOfRange[1] & 255) << 8);
                    b2 = copyOfRange[0];
                } else {
                    b3 = ((copyOfRange[1] & 255) << 16) | ((copyOfRange[0] & 255) << 24) | ((copyOfRange[2] & 255) << 8);
                    b2 = copyOfRange[3];
                }
                byte b4 = b3 | (b2 & 255);
                int i8 = i6 - 4;
                if (i8 < b4) {
                    Log.w(MediaPlayer2Impl.TAG, String.format("parsePSSH: len is too short to parse data: (%d < %d) pssh: %d", Integer.valueOf(i8), Integer.valueOf(b4), Integer.valueOf(i)));
                    return null;
                }
                int i9 = i7 + b4;
                byte[] copyOfRange2 = Arrays.copyOfRange(bArr, i7, i9);
                i2 = i8 - b4;
                Log.v(MediaPlayer2Impl.TAG, String.format("parsePSSH[%d]: <%s, %s> pssh: %d", Integer.valueOf(i4), bytesToUUID, arrToHex(copyOfRange2), Integer.valueOf(i)));
                i4++;
                hashMap.put(bytesToUUID, copyOfRange2);
                i3 = i9;
            }
            return hashMap;
        }

        @Override // android.support.v4.media.MediaPlayer2.DrmInfo
        public Map<UUID, byte[]> getPssh() {
            return this.mMapPssh;
        }

        @Override // android.support.v4.media.MediaPlayer2.DrmInfo
        public List<UUID> getSupportedSchemes() {
            return Arrays.asList(this.mSupportedSchemes);
        }
    }

    /* access modifiers changed from: private */
    public class MediaPlayerSource {
        final AtomicInteger mBufferedPercentage = new AtomicInteger(0);
        int mBufferingState = 0;
        volatile DataSourceDesc mDSD;
        int mMp2State = 1001;
        boolean mPlayPending;
        final MediaPlayer mPlayer = new MediaPlayer();
        int mPlayerState = 0;
        int mSourceState = 0;

        MediaPlayerSource(DataSourceDesc dataSourceDesc) {
            this.mDSD = dataSourceDesc;
            MediaPlayer2Impl.this.setUpListeners(this);
        }

        /* access modifiers changed from: package-private */
        public DataSourceDesc getDSD() {
            return this.mDSD;
        }
    }

    /* access modifiers changed from: private */
    public class MediaPlayerSourceQueue {
        AudioAttributesCompat mAudioAttributes;
        Integer mAudioSessionId;
        Integer mAuxEffect;
        Float mAuxEffectSendLevel;
        PlaybackParams mPlaybackParams;
        List<MediaPlayerSource> mQueue = new ArrayList();
        Surface mSurface;
        SyncParams mSyncParams;
        Float mVolume = Float.valueOf(1.0f);

        MediaPlayerSourceQueue() {
            this.mQueue.add(new MediaPlayerSource(null));
        }

        /* access modifiers changed from: package-private */
        public synchronized void attachAuxEffect(int i) {
            getCurrentPlayer().attachAuxEffect(i);
            this.mAuxEffect = Integer.valueOf(i);
        }

        /* access modifiers changed from: package-private */
        public synchronized void deselectTrack(int i) {
            getCurrentPlayer().deselectTrack(i);
        }

        /* access modifiers changed from: package-private */
        public synchronized AudioAttributesCompat getAudioAttributes() {
            return this.mAudioAttributes;
        }

        /* access modifiers changed from: package-private */
        public synchronized int getAudioSessionId() {
            return getCurrentPlayer().getAudioSessionId();
        }

        /* access modifiers changed from: package-private */
        public synchronized long getBufferedPosition() {
            MediaPlayerSource mediaPlayerSource;
            mediaPlayerSource = this.mQueue.get(0);
            return (((long) mediaPlayerSource.mPlayer.getDuration()) * ((long) mediaPlayerSource.mBufferedPercentage.get())) / 100;
        }

        /* access modifiers changed from: package-private */
        public synchronized int getBufferingState() {
            return this.mQueue.get(0).mBufferingState;
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaPlayer getCurrentPlayer() {
            return this.mQueue.get(0).mPlayer;
        }

        /* access modifiers changed from: package-private */
        public synchronized long getCurrentPosition() {
            return (long) getCurrentPlayer().getCurrentPosition();
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaPlayer.DrmInfo getDrmInfo() {
            return getCurrentPlayer().getDrmInfo();
        }

        /* access modifiers changed from: package-private */
        public synchronized String getDrmPropertyString(String str) throws MediaPlayer.NoDrmSchemeException {
            return getCurrentPlayer().getDrmPropertyString(str);
        }

        /* access modifiers changed from: package-private */
        public synchronized long getDuration() {
            return (long) getCurrentPlayer().getDuration();
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaPlayerSource getFirst() {
            return this.mQueue.get(0);
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaDrm.KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, String str, int i, Map<String, String> map) throws MediaPlayer.NoDrmSchemeException {
            return getCurrentPlayer().getKeyRequest(bArr, bArr2, str, i, map);
        }

        /* access modifiers changed from: package-private */
        public synchronized int getMediaPlayer2State() {
            return this.mQueue.get(0).mMp2State;
        }

        /* access modifiers changed from: package-private */
        public synchronized PersistableBundle getMetrics() {
            return getCurrentPlayer().getMetrics();
        }

        /* access modifiers changed from: package-private */
        public synchronized PlaybackParams getPlaybackParams() {
            return getCurrentPlayer().getPlaybackParams();
        }

        /* access modifiers changed from: package-private */
        public synchronized int getPlayerState() {
            return this.mQueue.get(0).mPlayerState;
        }

        /* access modifiers changed from: package-private */
        public synchronized int getSelectedTrack(int i) {
            return getCurrentPlayer().getSelectedTrack(i);
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaPlayerSource getSourceForPlayer(MediaPlayer mediaPlayer) {
            for (MediaPlayerSource mediaPlayerSource : this.mQueue) {
                if (mediaPlayerSource.mPlayer == mediaPlayer) {
                    return mediaPlayerSource;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public synchronized SyncParams getSyncParams() {
            return getCurrentPlayer().getSyncParams();
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaTimestamp2 getTimestamp() {
            MediaTimestamp timestamp;
            timestamp = getCurrentPlayer().getTimestamp();
            return timestamp == null ? null : new MediaTimestamp2(timestamp);
        }

        /* access modifiers changed from: package-private */
        public synchronized MediaPlayer.TrackInfo[] getTrackInfo() {
            return getCurrentPlayer().getTrackInfo();
        }

        /* access modifiers changed from: package-private */
        public synchronized int getVideoHeight() {
            return getCurrentPlayer().getVideoHeight();
        }

        /* access modifiers changed from: package-private */
        public synchronized int getVideoWidth() {
            return getCurrentPlayer().getVideoWidth();
        }

        /* access modifiers changed from: package-private */
        public synchronized float getVolume() {
            return this.mVolume.floatValue();
        }

        /* access modifiers changed from: package-private */
        public synchronized void moveToNext() {
            MediaPlayerSource remove = this.mQueue.remove(0);
            remove.mPlayer.release();
            if (!this.mQueue.isEmpty()) {
                final MediaPlayerSource mediaPlayerSource = this.mQueue.get(0);
                if (remove.mPlayerState != mediaPlayerSource.mPlayerState) {
                    MediaPlayer2Impl.this.notifyPlayerEvent(new PlayerEventNotifier() {
                        /* class android.support.v4.media.MediaPlayer2Impl.MediaPlayerSourceQueue.AnonymousClass2 */

                        @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                        public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                            playerEventCallback.onPlayerStateChanged(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, mediaPlayerSource.mPlayerState);
                        }
                    });
                }
                MediaPlayer2Impl.this.notifyPlayerEvent(new PlayerEventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.MediaPlayerSourceQueue.AnonymousClass3 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                    public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                        playerEventCallback.onCurrentDataSourceChanged(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, mediaPlayerSource.mDSD);
                    }
                });
            } else {
                throw new IllegalStateException("player/source queue emptied");
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized DataSourceError onCompletion(MediaPlayer mediaPlayer) {
            if (!this.mQueue.isEmpty() && mediaPlayer == getCurrentPlayer()) {
                if (this.mQueue.size() == 1) {
                    setMp2State(mediaPlayer, 1003);
                    final DataSourceDesc dsd = this.mQueue.get(0).getDSD();
                    MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                        /* class android.support.v4.media.MediaPlayer2Impl.MediaPlayerSourceQueue.AnonymousClass1 */

                        @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                        public void notify(MediaPlayer2.EventCallback eventCallback) {
                            eventCallback.onInfo(MediaPlayer2Impl.this, dsd, 6, 0);
                        }
                    });
                    return null;
                }
                moveToNext();
            }
            return playCurrent();
        }

        /* access modifiers changed from: package-private */
        public synchronized void onError(MediaPlayer mediaPlayer) {
            setMp2State(mediaPlayer, 1005);
            setBufferingState(mediaPlayer, 0);
        }

        /* access modifiers changed from: package-private */
        public synchronized DataSourceError onPrepared(MediaPlayer mediaPlayer) {
            for (int i = 0; i < this.mQueue.size(); i++) {
                MediaPlayerSource mediaPlayerSource = this.mQueue.get(i);
                if (mediaPlayer == mediaPlayerSource.mPlayer) {
                    if (i == 0) {
                        if (mediaPlayerSource.mPlayPending) {
                            mediaPlayerSource.mPlayPending = false;
                            mediaPlayerSource.mPlayer.start();
                            setMp2State(mediaPlayerSource.mPlayer, 1004);
                        } else {
                            setMp2State(mediaPlayerSource.mPlayer, 1002);
                        }
                    }
                    mediaPlayerSource.mSourceState = 2;
                    setBufferingState(mediaPlayerSource.mPlayer, 1);
                    return prepareAt(i + 1);
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public synchronized void pause() {
            MediaPlayer currentPlayer = getCurrentPlayer();
            currentPlayer.pause();
            setMp2State(currentPlayer, 1003);
        }

        /* access modifiers changed from: package-private */
        public synchronized void play() {
            MediaPlayerSource mediaPlayerSource = this.mQueue.get(0);
            if (mediaPlayerSource.mSourceState == 2) {
                mediaPlayerSource.mPlayer.start();
                setMp2State(mediaPlayerSource.mPlayer, 1004);
            } else {
                throw new IllegalStateException();
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized DataSourceError playCurrent() {
            DataSourceError dataSourceError;
            dataSourceError = null;
            final MediaPlayerSource mediaPlayerSource = this.mQueue.get(0);
            if (this.mSurface != null) {
                mediaPlayerSource.mPlayer.setSurface(this.mSurface);
            }
            if (this.mVolume != null) {
                mediaPlayerSource.mPlayer.setVolume(this.mVolume.floatValue(), this.mVolume.floatValue());
            }
            if (this.mAudioAttributes != null) {
                mediaPlayerSource.mPlayer.setAudioAttributes((AudioAttributes) this.mAudioAttributes.unwrap());
            }
            if (this.mAuxEffect != null) {
                mediaPlayerSource.mPlayer.attachAuxEffect(this.mAuxEffect.intValue());
            }
            if (this.mAuxEffectSendLevel != null) {
                mediaPlayerSource.mPlayer.setAuxEffectSendLevel(this.mAuxEffectSendLevel.floatValue());
            }
            if (this.mSyncParams != null) {
                mediaPlayerSource.mPlayer.setSyncParams(this.mSyncParams);
            }
            if (this.mPlaybackParams != null) {
                mediaPlayerSource.mPlayer.setPlaybackParams(this.mPlaybackParams);
            }
            if (mediaPlayerSource.mSourceState == 2) {
                mediaPlayerSource.mPlayer.start();
                setMp2State(mediaPlayerSource.mPlayer, 1004);
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.MediaPlayerSourceQueue.AnonymousClass4 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        eventCallback.onInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), 2, 0);
                    }
                });
            } else {
                if (mediaPlayerSource.mSourceState == 0) {
                    dataSourceError = prepareAt(0);
                }
                mediaPlayerSource.mPlayPending = true;
            }
            return dataSourceError;
        }

        /* access modifiers changed from: package-private */
        public synchronized void prepare() {
            getCurrentPlayer().prepareAsync();
        }

        /* access modifiers changed from: package-private */
        public synchronized void prepareAsync() {
            MediaPlayer currentPlayer = getCurrentPlayer();
            currentPlayer.prepareAsync();
            setBufferingState(currentPlayer, 2);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0058, code lost:
            return null;
         */
        public synchronized DataSourceError prepareAt(int i) {
            if (i < this.mQueue.size() && this.mQueue.get(i).mSourceState == 0) {
                if (i == 0 || getPlayerState() != 0) {
                    MediaPlayerSource mediaPlayerSource = this.mQueue.get(i);
                    try {
                        if (this.mAudioSessionId != null) {
                            mediaPlayerSource.mPlayer.setAudioSessionId(this.mAudioSessionId.intValue());
                        }
                        mediaPlayerSource.mSourceState = 1;
                        MediaPlayer2Impl.handleDataSource(mediaPlayerSource);
                        mediaPlayerSource.mPlayer.prepareAsync();
                        return null;
                    } catch (Exception unused) {
                        DataSourceDesc dsd = mediaPlayerSource.getDSD();
                        setMp2State(mediaPlayerSource.mPlayer, 1005);
                        return new DataSourceError(dsd, 1, MediaPlayer2.MEDIA_ERROR_UNSUPPORTED);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void prepareDrm(UUID uuid) throws ResourceBusyException, MediaPlayer.ProvisioningServerErrorException, MediaPlayer.ProvisioningNetworkErrorException, UnsupportedSchemeException {
            getCurrentPlayer().prepareDrm(uuid);
        }

        /* access modifiers changed from: package-private */
        public synchronized byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws DeniedByServerException, MediaPlayer.NoDrmSchemeException {
            return getCurrentPlayer().provideKeyResponse(bArr, bArr2);
        }

        /* access modifiers changed from: package-private */
        public synchronized void release() {
            getCurrentPlayer().release();
        }

        /* access modifiers changed from: package-private */
        public synchronized void releaseDrm() throws MediaPlayer.NoDrmSchemeException {
            getCurrentPlayer().stop();
            getCurrentPlayer().releaseDrm();
        }

        /* access modifiers changed from: package-private */
        public synchronized void reset() {
            MediaPlayerSource mediaPlayerSource = this.mQueue.get(0);
            mediaPlayerSource.mPlayer.reset();
            mediaPlayerSource.mBufferedPercentage.set(0);
            this.mVolume = Float.valueOf(1.0f);
            this.mSurface = null;
            this.mAuxEffect = null;
            this.mAuxEffectSendLevel = null;
            this.mAudioAttributes = null;
            this.mAudioSessionId = null;
            this.mSyncParams = null;
            this.mPlaybackParams = null;
            setMp2State(mediaPlayerSource.mPlayer, 1001);
            setBufferingState(mediaPlayerSource.mPlayer, 0);
        }

        /* access modifiers changed from: package-private */
        public synchronized void restoreKeys(byte[] bArr) throws MediaPlayer.NoDrmSchemeException {
            getCurrentPlayer().restoreKeys(bArr);
        }

        /* access modifiers changed from: package-private */
        public synchronized void seekTo(long j, int i) {
            getCurrentPlayer().seekTo(j, i);
        }

        /* access modifiers changed from: package-private */
        public synchronized void selectTrack(int i) {
            getCurrentPlayer().selectTrack(i);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setAudioAttributes(AudioAttributesCompat audioAttributesCompat) {
            this.mAudioAttributes = audioAttributesCompat;
            getCurrentPlayer().setAudioAttributes(this.mAudioAttributes == null ? null : (AudioAttributes) this.mAudioAttributes.unwrap());
        }

        /* access modifiers changed from: package-private */
        public synchronized void setAudioSessionId(int i) {
            getCurrentPlayer().setAudioSessionId(i);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setAuxEffectSendLevel(float f2) {
            getCurrentPlayer().setAuxEffectSendLevel(f2);
            this.mAuxEffectSendLevel = Float.valueOf(f2);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setBufferingState(MediaPlayer mediaPlayer, final int i) {
            for (final MediaPlayerSource mediaPlayerSource : this.mQueue) {
                if (mediaPlayerSource.mPlayer == mediaPlayer) {
                    if (mediaPlayerSource.mBufferingState != i) {
                        mediaPlayerSource.mBufferingState = i;
                        MediaPlayer2Impl.this.notifyPlayerEvent(new PlayerEventNotifier() {
                            /* class android.support.v4.media.MediaPlayer2Impl.MediaPlayerSourceQueue.AnonymousClass6 */

                            @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                            public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                                playerEventCallback.onBufferingStateChanged(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, mediaPlayerSource.getDSD(), i);
                            }
                        });
                        return;
                    }
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void setDrmPropertyString(String str, String str2) throws MediaPlayer.NoDrmSchemeException {
            getCurrentPlayer().setDrmPropertyString(str, str2);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setFirst(DataSourceDesc dataSourceDesc) throws IOException {
            if (this.mQueue.isEmpty()) {
                this.mQueue.add(0, new MediaPlayerSource(dataSourceDesc));
            } else {
                this.mQueue.get(0).mDSD = dataSourceDesc;
                MediaPlayer2Impl.this.setUpListeners(this.mQueue.get(0));
            }
            MediaPlayer2Impl.handleDataSource(this.mQueue.get(0));
        }

        /* access modifiers changed from: package-private */
        public synchronized void setLooping(boolean z) {
            getCurrentPlayer().setLooping(z);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setMp2State(MediaPlayer mediaPlayer, int i) {
            for (MediaPlayerSource mediaPlayerSource : this.mQueue) {
                if (mediaPlayerSource.mPlayer == mediaPlayer) {
                    if (mediaPlayerSource.mMp2State != i) {
                        mediaPlayerSource.mMp2State = i;
                        final int intValue = ((Integer) MediaPlayer2Impl.sStateMap.get(Integer.valueOf(i))).intValue();
                        if (mediaPlayerSource.mPlayerState != intValue) {
                            mediaPlayerSource.mPlayerState = intValue;
                            MediaPlayer2Impl.this.notifyPlayerEvent(new PlayerEventNotifier() {
                                /* class android.support.v4.media.MediaPlayer2Impl.MediaPlayerSourceQueue.AnonymousClass5 */

                                @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                                public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                                    playerEventCallback.onPlayerStateChanged(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, intValue);
                                }
                            });
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized DataSourceError setNext(DataSourceDesc dataSourceDesc) {
            MediaPlayerSource mediaPlayerSource = new MediaPlayerSource(dataSourceDesc);
            if (this.mQueue.isEmpty()) {
                this.mQueue.add(mediaPlayerSource);
                return prepareAt(0);
            }
            this.mQueue.add(1, mediaPlayerSource);
            return prepareAt(1);
        }

        /* access modifiers changed from: package-private */
        public synchronized DataSourceError setNextMultiple(List<DataSourceDesc> list) {
            ArrayList arrayList = new ArrayList();
            for (DataSourceDesc dataSourceDesc : list) {
                arrayList.add(new MediaPlayerSource(dataSourceDesc));
            }
            if (this.mQueue.isEmpty()) {
                this.mQueue.addAll(arrayList);
                return prepareAt(0);
            }
            this.mQueue.addAll(1, arrayList);
            return prepareAt(1);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setOnDrmConfigHelper(MediaPlayer.OnDrmConfigHelper onDrmConfigHelper) {
            getCurrentPlayer().setOnDrmConfigHelper(onDrmConfigHelper);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setPlaybackParams(PlaybackParams playbackParams) {
            getCurrentPlayer().setPlaybackParams(playbackParams);
            this.mPlaybackParams = playbackParams;
        }

        /* access modifiers changed from: package-private */
        public synchronized void setSurface(Surface surface) {
            this.mSurface = surface;
            getCurrentPlayer().setSurface(surface);
        }

        /* access modifiers changed from: package-private */
        public synchronized void setSyncParams(SyncParams syncParams) {
            getCurrentPlayer().setSyncParams(syncParams);
            this.mSyncParams = syncParams;
        }

        /* access modifiers changed from: package-private */
        public synchronized void setVolume(float f2) {
            this.mVolume = Float.valueOf(f2);
            getCurrentPlayer().setVolume(f2, f2);
        }

        /* access modifiers changed from: package-private */
        public synchronized void skipToNext() {
            if (this.mQueue.size() > 1) {
                MediaPlayerSource mediaPlayerSource = this.mQueue.get(0);
                moveToNext();
                if (mediaPlayerSource.mPlayerState == 2 || mediaPlayerSource.mPlayPending) {
                    playCurrent();
                }
            } else {
                throw new IllegalStateException("No next source available");
            }
        }
    }

    /* access modifiers changed from: private */
    public interface Mp2EventNotifier {
        void notify(MediaPlayer2.EventCallback eventCallback);
    }

    public static final class NoDrmSchemeExceptionImpl extends MediaPlayer2.NoDrmSchemeException {
        public NoDrmSchemeExceptionImpl(String str) {
            super(str);
        }
    }

    /* access modifiers changed from: private */
    public interface PlayerEventNotifier {
        void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback);
    }

    public static final class ProvisioningNetworkErrorExceptionImpl extends MediaPlayer2.ProvisioningNetworkErrorException {
        public ProvisioningNetworkErrorExceptionImpl(String str) {
            super(str);
        }
    }

    public static final class ProvisioningServerErrorExceptionImpl extends MediaPlayer2.ProvisioningServerErrorException {
        public ProvisioningServerErrorExceptionImpl(String str) {
            super(str);
        }
    }

    /* access modifiers changed from: private */
    public abstract class Task implements Runnable {
        /* access modifiers changed from: private */
        public DataSourceDesc mDSD;
        /* access modifiers changed from: private */
        public final int mMediaCallType;
        /* access modifiers changed from: private */
        public final boolean mNeedToWaitForEventToComplete;

        Task(int i, boolean z) {
            this.mMediaCallType = i;
            this.mNeedToWaitForEventToComplete = z;
        }

        /* access modifiers changed from: private */
        public void sendCompleteNotification(final int i) {
            if (this.mMediaCallType != 1003) {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.Task.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        Task task = Task.this;
                        eventCallback.onCallCompleted(MediaPlayer2Impl.this, task.mDSD, Task.this.mMediaCallType, i);
                    }
                });
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void process() throws IOException, MediaPlayer2.NoDrmSchemeException;

        public void run() {
            int i;
            try {
                process();
                i = 0;
            } catch (IllegalStateException unused) {
                i = 1;
            } catch (IllegalArgumentException unused2) {
                i = 2;
            } catch (SecurityException unused3) {
                i = 3;
            } catch (IOException unused4) {
                i = 4;
            } catch (Exception unused5) {
                i = Integer.MIN_VALUE;
            }
            this.mDSD = MediaPlayer2Impl.this.getCurrentDataSource();
            if (!this.mNeedToWaitForEventToComplete || i != 0) {
                sendCompleteNotification(i);
                synchronized (MediaPlayer2Impl.this.mTaskLock) {
                    Task unused6 = MediaPlayer2Impl.this.mCurrentTask = null;
                    MediaPlayer2Impl.this.processPendingTask_l();
                }
            }
        }
    }

    public static final class TrackInfoImpl extends MediaPlayer2.TrackInfo {
        final MediaFormat mFormat;
        final int mTrackType;

        TrackInfoImpl(int i, MediaFormat mediaFormat) {
            this.mTrackType = i;
            this.mFormat = mediaFormat;
        }

        @Override // android.support.v4.media.MediaPlayer2.TrackInfo
        public MediaFormat getFormat() {
            int i = this.mTrackType;
            if (i == 3 || i == 4) {
                return this.mFormat;
            }
            return null;
        }

        @Override // android.support.v4.media.MediaPlayer2.TrackInfo
        public String getLanguage() {
            String string = this.mFormat.getString(EffectConfiguration.KEY_SYS_LANGUAGE);
            return string == null ? "und" : string;
        }

        @Override // android.support.v4.media.MediaPlayer2.TrackInfo
        public int getTrackType() {
            return this.mTrackType;
        }

        @Override // android.support.v4.media.MediaPlayer2.TrackInfo
        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append(TrackInfoImpl.class.getName());
            sb.append('{');
            int i = this.mTrackType;
            if (i == 1) {
                sb.append(CameraIntentManager.CameraMode.VIDEO);
            } else if (i == 2) {
                sb.append("AUDIO");
            } else if (i == 3) {
                sb.append("TIMEDTEXT");
            } else if (i != 4) {
                sb.append("UNKNOWN");
            } else {
                sb.append("SUBTITLE");
            }
            sb.append(", " + this.mFormat.toString());
            sb.append("}");
            return sb.toString();
        }
    }

    static {
        sInfoEventMap.put(1, 1);
        sInfoEventMap.put(2, 2);
        sInfoEventMap.put(3, 3);
        sInfoEventMap.put(700, 700);
        sInfoEventMap.put(701, 701);
        ArrayMap<Integer, Integer> arrayMap = sInfoEventMap;
        Integer valueOf = Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_BUFFERING_END);
        arrayMap.put(valueOf, valueOf);
        sInfoEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_BAD_INTERLEAVING), Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_BAD_INTERLEAVING));
        sInfoEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_NOT_SEEKABLE), Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_NOT_SEEKABLE));
        sInfoEventMap.put(802, 802);
        sInfoEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_AUDIO_NOT_PLAYING), Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_AUDIO_NOT_PLAYING));
        sInfoEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_VIDEO_NOT_PLAYING), Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_VIDEO_NOT_PLAYING));
        sInfoEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_UNSUPPORTED_SUBTITLE), Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_UNSUPPORTED_SUBTITLE));
        sInfoEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_SUBTITLE_TIMED_OUT), Integer.valueOf((int) MediaPlayer2.MEDIA_INFO_SUBTITLE_TIMED_OUT));
        sErrorEventMap.put(1, 1);
        sErrorEventMap.put(200, 200);
        sErrorEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_ERROR_IO), Integer.valueOf((int) MediaPlayer2.MEDIA_ERROR_IO));
        sErrorEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_ERROR_MALFORMED), Integer.valueOf((int) MediaPlayer2.MEDIA_ERROR_MALFORMED));
        sErrorEventMap.put(Integer.valueOf((int) MediaPlayer2.MEDIA_ERROR_UNSUPPORTED), Integer.valueOf((int) MediaPlayer2.MEDIA_ERROR_UNSUPPORTED));
        sErrorEventMap.put(-110, -110);
        sPrepareDrmStatusMap.put(0, 0);
        sPrepareDrmStatusMap.put(1, 1);
        sPrepareDrmStatusMap.put(2, 2);
        sPrepareDrmStatusMap.put(2, 2);
        sStateMap.put(1001, 0);
        sStateMap.put(1002, 1);
        sStateMap.put(1003, 1);
        sStateMap.put(1004, 2);
        sStateMap.put(1005, 3);
    }

    public MediaPlayer2Impl() {
        this.mHandlerThread.start();
        Looper looper = this.mHandlerThread.getLooper();
        this.mEndPositionHandler = new Handler(looper);
        this.mTaskHandler = new Handler(looper);
        this.mPlayer = new MediaPlayerSourceQueue();
    }

    private void addTask(Task task) {
        synchronized (this.mTaskLock) {
            this.mPendingTasks.add(task);
            processPendingTask_l();
        }
    }

    /* access modifiers changed from: private */
    public int getBufferingState() {
        return this.mPlayer.getBufferingState();
    }

    /* access modifiers changed from: private */
    public int getPlayerState() {
        return this.mPlayer.getPlayerState();
    }

    /* access modifiers changed from: private */
    public static void handleDataSource(MediaPlayerSource mediaPlayerSource) throws IOException {
        final DataSourceDesc dsd = mediaPlayerSource.getDSD();
        Preconditions.checkNotNull(dsd, "the DataSourceDesc cannot be null");
        MediaPlayer mediaPlayer = mediaPlayerSource.mPlayer;
        int type = dsd.getType();
        if (type == 1) {
            mediaPlayer.setDataSource(new MediaDataSource() {
                /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass14 */
                Media2DataSource mDataSource = dsd.getMedia2DataSource();

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    this.mDataSource.close();
                }

                @Override // android.media.MediaDataSource
                public long getSize() throws IOException {
                    return this.mDataSource.getSize();
                }

                @Override // android.media.MediaDataSource
                public int readAt(long j, byte[] bArr, int i, int i2) throws IOException {
                    return this.mDataSource.readAt(j, bArr, i, i2);
                }
            });
        } else if (type == 2) {
            mediaPlayer.setDataSource(dsd.getFileDescriptor(), dsd.getFileDescriptorOffset(), dsd.getFileDescriptorLength());
        } else if (type == 3) {
            mediaPlayer.setDataSource(dsd.getUriContext(), dsd.getUri(), dsd.getUriHeaders(), dsd.getUriCookies());
        }
    }

    /* access modifiers changed from: private */
    public void handleDataSourceError(final DataSourceError dataSourceError) {
        if (dataSourceError != null) {
            notifyMediaPlayer2Event(new Mp2EventNotifier() {
                /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass1 */

                @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                public void notify(MediaPlayer2.EventCallback eventCallback) {
                    MediaPlayer2Impl mediaPlayer2Impl = MediaPlayer2Impl.this;
                    DataSourceError dataSourceError = dataSourceError;
                    eventCallback.onError(mediaPlayer2Impl, dataSourceError.mDSD, dataSourceError.mWhat, dataSourceError.mExtra);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyDrmEvent(final DrmEventNotifier drmEventNotifier) {
        final Pair<Executor, MediaPlayer2.DrmEventCallback> pair;
        synchronized (this.mLock) {
            pair = this.mDrmEventCallbackRecord;
        }
        if (pair != null) {
            ((Executor) pair.first).execute(new Runnable() {
                /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass26 */

                public void run() {
                    drmEventNotifier.notify((MediaPlayer2.DrmEventCallback) pair.second);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyMediaPlayer2Event(final Mp2EventNotifier mp2EventNotifier) {
        final Pair<Executor, MediaPlayer2.EventCallback> pair;
        synchronized (this.mLock) {
            pair = this.mMp2EventCallbackRecord;
        }
        if (pair != null) {
            ((Executor) pair.first).execute(new Runnable() {
                /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass24 */

                public void run() {
                    mp2EventNotifier.notify((MediaPlayer2.EventCallback) pair.second);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyPlayerEvent(final PlayerEventNotifier playerEventNotifier) {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mPlayerEventCallbackMap);
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            final BaseMediaPlayer.PlayerEventCallback playerEventCallback = (BaseMediaPlayer.PlayerEventCallback) arrayMap.keyAt(i);
            ((Executor) arrayMap.valueAt(i)).execute(new Runnable() {
                /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass25 */

                public void run() {
                    playerEventNotifier.notify(playerEventCallback);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    @GuardedBy("mTaskLock")
    public void processPendingTask_l() {
        if (this.mCurrentTask == null && !this.mPendingTasks.isEmpty()) {
            Task removeFirst = this.mPendingTasks.removeFirst();
            this.mCurrentTask = removeFirst;
            this.mTaskHandler.post(removeFirst);
        }
    }

    /* access modifiers changed from: private */
    public void registerPlayerEventCallback(@NonNull Executor executor, @NonNull BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
        if (playerEventCallback == null) {
            throw new IllegalArgumentException("Illegal null PlayerEventCallback");
        } else if (executor != null) {
            synchronized (this.mLock) {
                this.mPlayerEventCallbackMap.put(playerEventCallback, executor);
            }
        } else {
            throw new IllegalArgumentException("Illegal null Executor for the PlayerEventCallback");
        }
    }

    /* access modifiers changed from: private */
    public void setEndPositionTimerIfNeeded(final MediaPlayer.OnCompletionListener onCompletionListener, final MediaPlayerSource mediaPlayerSource, MediaTimestamp mediaTimestamp) {
        if (mediaPlayerSource == this.mPlayer.getFirst()) {
            this.mEndPositionHandler.removeCallbacksAndMessages(null);
            DataSourceDesc dsd = mediaPlayerSource.getDSD();
            if (dsd.getEndPosition() != 576460752303423487L && mediaTimestamp.getMediaClockRate() > 0.0f) {
                long anchorMediaTimeUs = mediaTimestamp.getAnchorMediaTimeUs();
                long endPosition = (long) (((float) (dsd.getEndPosition() - ((anchorMediaTimeUs + ((System.nanoTime() - mediaTimestamp.getAnchorSytemNanoTime()) / 1000)) / 1000))) / mediaTimestamp.getMediaClockRate());
                Handler handler = this.mEndPositionHandler;
                AnonymousClass27 r2 = new Runnable() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass27 */

                    public void run() {
                        if (MediaPlayer2Impl.this.mPlayer.getFirst() == mediaPlayerSource) {
                            MediaPlayer2Impl.this.mPlayer.pause();
                            onCompletionListener.onCompletion(mediaPlayerSource.mPlayer);
                        }
                    }
                };
                long j = 0;
                if (endPosition >= 0) {
                    j = endPosition;
                }
                handler.postDelayed(r2, j);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setPlaybackParamsInternal(final PlaybackParams playbackParams) {
        PlaybackParams playbackParams2 = this.mPlayer.getPlaybackParams();
        this.mPlayer.setPlaybackParams(playbackParams);
        if (playbackParams2.getSpeed() != playbackParams.getSpeed()) {
            notifyPlayerEvent(new PlayerEventNotifier() {
                /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass23 */

                @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                    playerEventCallback.onPlaybackSpeedChanged(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, playbackParams.getSpeed());
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setUpListeners(final MediaPlayerSource mediaPlayerSource) {
        MediaPlayer mediaPlayer = mediaPlayerSource.mPlayer;
        final AnonymousClass28 r1 = new MediaPlayer.OnPreparedListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass28 */

            public void onPrepared(MediaPlayer mediaPlayer) {
                MediaPlayer2Impl mediaPlayer2Impl = MediaPlayer2Impl.this;
                mediaPlayer2Impl.handleDataSourceError(mediaPlayer2Impl.mPlayer.onPrepared(mediaPlayer));
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass28.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass28 r3 = AnonymousClass28.this;
                        eventCallback.onInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), 100, 0);
                    }
                });
                MediaPlayer2Impl.this.notifyPlayerEvent(new PlayerEventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass28.AnonymousClass2 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                    public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                        playerEventCallback.onMediaPrepared(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, mediaPlayerSource.getDSD());
                    }
                });
                synchronized (MediaPlayer2Impl.this.mTaskLock) {
                    if (MediaPlayer2Impl.this.mCurrentTask != null && MediaPlayer2Impl.this.mCurrentTask.mMediaCallType == 6 && MediaPlayer2Impl.this.mCurrentTask.mDSD == mediaPlayerSource.getDSD() && MediaPlayer2Impl.this.mCurrentTask.mNeedToWaitForEventToComplete) {
                        MediaPlayer2Impl.this.mCurrentTask.sendCompleteNotification(0);
                        Task unused = MediaPlayer2Impl.this.mCurrentTask = null;
                        MediaPlayer2Impl.this.processPendingTask_l();
                    }
                }
            }
        };
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass29 */

            public void onPrepared(MediaPlayer mediaPlayer) {
                if (mediaPlayerSource.getDSD().getStartPosition() != 0) {
                    MediaPlayerSource mediaPlayerSource = mediaPlayerSource;
                    mediaPlayerSource.mPlayer.seekTo((long) ((int) mediaPlayerSource.getDSD().getStartPosition()), 3);
                    return;
                }
                r1.onPrepared(mediaPlayer);
            }
        });
        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass30 */

            public void onVideoSizeChanged(MediaPlayer mediaPlayer, final int i, final int i2) {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass30.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass30 r0 = AnonymousClass30.this;
                        eventCallback.onVideoSizeChanged(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), i, i2);
                    }
                });
            }
        });
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass31 */

            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                if (i == 3) {
                    MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                        /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass31.AnonymousClass1 */

                        @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                        public void notify(MediaPlayer2.EventCallback eventCallback) {
                            AnonymousClass31 r3 = AnonymousClass31.this;
                            eventCallback.onInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), 3, 0);
                        }
                    });
                    return false;
                } else if (i == 701) {
                    MediaPlayer2Impl.this.mPlayer.setBufferingState(mediaPlayer, 2);
                    return false;
                } else if (i != 702) {
                    return false;
                } else {
                    MediaPlayer2Impl.this.mPlayer.setBufferingState(mediaPlayer, 1);
                    return false;
                }
            }
        });
        final AnonymousClass32 r2 = new MediaPlayer.OnCompletionListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass32 */

            public void onCompletion(MediaPlayer mediaPlayer) {
                MediaPlayer2Impl mediaPlayer2Impl = MediaPlayer2Impl.this;
                mediaPlayer2Impl.handleDataSourceError(mediaPlayer2Impl.mPlayer.onCompletion(mediaPlayer));
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass32.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass32 r3 = AnonymousClass32.this;
                        eventCallback.onInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), 5, 0);
                    }
                });
            }
        };
        mediaPlayer.setOnCompletionListener(r2);
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass33 */

            public boolean onError(MediaPlayer mediaPlayer, final int i, final int i2) {
                MediaPlayer2Impl.this.mPlayer.onError(mediaPlayer);
                synchronized (MediaPlayer2Impl.this.mTaskLock) {
                    if (MediaPlayer2Impl.this.mCurrentTask != null && MediaPlayer2Impl.this.mCurrentTask.mNeedToWaitForEventToComplete) {
                        MediaPlayer2Impl.this.mCurrentTask.sendCompleteNotification(Integer.MIN_VALUE);
                        Task unused = MediaPlayer2Impl.this.mCurrentTask = null;
                        MediaPlayer2Impl.this.processPendingTask_l();
                    }
                }
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass33.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        int intValue = ((Integer) MediaPlayer2Impl.sErrorEventMap.getOrDefault(Integer.valueOf(i), 1)).intValue();
                        AnonymousClass33 r1 = AnonymousClass33.this;
                        eventCallback.onError(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), intValue, i2);
                    }
                });
                return true;
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass34 */

            public void onSeekComplete(MediaPlayer mediaPlayer) {
                MediaPlayerSource mediaPlayerSource = mediaPlayerSource;
                if (mediaPlayerSource.mMp2State != 1001 || mediaPlayerSource.getDSD().getStartPosition() == 0) {
                    synchronized (MediaPlayer2Impl.this.mTaskLock) {
                        if (MediaPlayer2Impl.this.mCurrentTask != null && MediaPlayer2Impl.this.mCurrentTask.mMediaCallType == 14 && MediaPlayer2Impl.this.mCurrentTask.mNeedToWaitForEventToComplete) {
                            MediaPlayer2Impl.this.mCurrentTask.sendCompleteNotification(0);
                            Task unused = MediaPlayer2Impl.this.mCurrentTask = null;
                            MediaPlayer2Impl.this.processPendingTask_l();
                        }
                    }
                    final long currentPosition = MediaPlayer2Impl.this.getCurrentPosition();
                    MediaPlayer2Impl.this.notifyPlayerEvent(new PlayerEventNotifier() {
                        /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass34.AnonymousClass1 */

                        @Override // android.support.v4.media.MediaPlayer2Impl.PlayerEventNotifier
                        public void notify(BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
                            playerEventCallback.onSeekCompleted(MediaPlayer2Impl.this.mBaseMediaPlayerImpl, currentPosition);
                        }
                    });
                    return;
                }
                r1.onPrepared(mediaPlayer);
            }
        });
        mediaPlayer.setOnTimedMetaDataAvailableListener(new MediaPlayer.OnTimedMetaDataAvailableListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass35 */

            public void onTimedMetaDataAvailable(MediaPlayer mediaPlayer, final TimedMetaData timedMetaData) {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass35.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass35 r0 = AnonymousClass35.this;
                        eventCallback.onTimedMetaDataAvailable(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), timedMetaData);
                    }
                });
            }
        });
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass36 */

            public boolean onInfo(MediaPlayer mediaPlayer, final int i, final int i2) {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass36.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        int intValue = ((Integer) MediaPlayer2Impl.sInfoEventMap.getOrDefault(Integer.valueOf(i), 1)).intValue();
                        AnonymousClass36 r1 = AnonymousClass36.this;
                        eventCallback.onInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), intValue, i2);
                    }
                });
                return true;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass37 */

            public void onBufferingUpdate(MediaPlayer mediaPlayer, final int i) {
                if (i >= 100) {
                    MediaPlayer2Impl.this.mPlayer.setBufferingState(mediaPlayer, 3);
                }
                mediaPlayerSource.mBufferedPercentage.set(i);
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass37.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass37 r0 = AnonymousClass37.this;
                        eventCallback.onInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), MediaPlayer2.MEDIA_INFO_BUFFERING_UPDATE, i);
                    }
                });
            }
        });
        mediaPlayer.setOnMediaTimeDiscontinuityListener(new MediaPlayer.OnMediaTimeDiscontinuityListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass38 */

            public void onMediaTimeDiscontinuity(MediaPlayer mediaPlayer, final MediaTimestamp mediaTimestamp) {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass38.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass38 r0 = AnonymousClass38.this;
                        eventCallback.onMediaTimeDiscontinuity(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), new MediaTimestamp2(mediaTimestamp));
                    }
                });
                MediaPlayer2Impl.this.setEndPositionTimerIfNeeded(r2, mediaPlayerSource, mediaTimestamp);
            }
        });
        mediaPlayer.setOnSubtitleDataListener(new MediaPlayer.OnSubtitleDataListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass39 */

            public void onSubtitleData(MediaPlayer mediaPlayer, final SubtitleData subtitleData) {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass39.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass39 r0 = AnonymousClass39.this;
                        eventCallback.onSubtitleData(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), new SubtitleData2(subtitleData));
                    }
                });
            }
        });
        mediaPlayer.setOnDrmInfoListener(new MediaPlayer.OnDrmInfoListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass40 */

            public void onDrmInfo(MediaPlayer mediaPlayer, final MediaPlayer.DrmInfo drmInfo) {
                MediaPlayer2Impl.this.notifyDrmEvent(new DrmEventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass40.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.DrmEventNotifier
                    public void notify(MediaPlayer2.DrmEventCallback drmEventCallback) {
                        AnonymousClass40 r0 = AnonymousClass40.this;
                        drmEventCallback.onDrmInfo(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), new DrmInfoImpl(drmInfo.getPssh(), drmInfo.getSupportedSchemes()));
                    }
                });
            }
        });
        mediaPlayer.setOnDrmPreparedListener(new MediaPlayer.OnDrmPreparedListener() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass41 */

            public void onDrmPrepared(MediaPlayer mediaPlayer, final int i) {
                MediaPlayer2Impl.this.notifyDrmEvent(new DrmEventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass41.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.DrmEventNotifier
                    public void notify(MediaPlayer2.DrmEventCallback drmEventCallback) {
                        int intValue = ((Integer) MediaPlayer2Impl.sPrepareDrmStatusMap.getOrDefault(Integer.valueOf(i), 3)).intValue();
                        AnonymousClass41 r3 = AnonymousClass41.this;
                        drmEventCallback.onDrmPrepared(MediaPlayer2Impl.this, mediaPlayerSource.getDSD(), intValue);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void unregisterPlayerEventCallback(@NonNull BaseMediaPlayer.PlayerEventCallback playerEventCallback) {
        if (playerEventCallback != null) {
            synchronized (this.mLock) {
                this.mPlayerEventCallbackMap.remove(playerEventCallback);
            }
            return;
        }
        throw new IllegalArgumentException("Illegal null PlayerEventCallback");
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void attachAuxEffect(final int i) {
        addTask(new Task(1, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass18 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.attachAuxEffect(i);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void clearDrmEventCallback() {
        synchronized (this.mLock) {
            this.mDrmEventCallbackRecord = null;
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void clearEventCallback() {
        synchronized (this.mLock) {
            this.mMp2EventCallbackRecord = null;
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void clearPendingCommands() {
        synchronized (this.mTaskLock) {
            this.mPendingTasks.clear();
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void close() {
        this.mPlayer.release();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mHandlerThread = null;
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void deselectTrack(final int i) {
        addTask(new Task(2, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass21 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.deselectTrack(i);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    @NonNull
    public AudioAttributesCompat getAudioAttributes() {
        return this.mPlayer.getAudioAttributes();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public int getAudioSessionId() {
        return this.mPlayer.getAudioSessionId();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public BaseMediaPlayer getBaseMediaPlayer() {
        BaseMediaPlayerImpl baseMediaPlayerImpl;
        synchronized (this.mLock) {
            if (this.mBaseMediaPlayerImpl == null) {
                this.mBaseMediaPlayerImpl = new BaseMediaPlayerImpl();
            }
            baseMediaPlayerImpl = this.mBaseMediaPlayerImpl;
        }
        return baseMediaPlayerImpl;
    }

    @Override // android.support.v4.media.MediaPlayer2
    public long getBufferedPosition() {
        return this.mPlayer.getBufferedPosition();
    }

    @Override // android.support.v4.media.MediaPlayer2
    @NonNull
    public DataSourceDesc getCurrentDataSource() {
        return this.mPlayer.getFirst().getDSD();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public long getCurrentPosition() {
        return this.mPlayer.getCurrentPosition();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public MediaPlayer2.DrmInfo getDrmInfo() {
        MediaPlayer.DrmInfo drmInfo = this.mPlayer.getDrmInfo();
        if (drmInfo == null) {
            return null;
        }
        return new DrmInfoImpl(drmInfo.getPssh(), drmInfo.getSupportedSchemes());
    }

    @Override // android.support.v4.media.MediaPlayer2
    @NonNull
    public MediaDrm.KeyRequest getDrmKeyRequest(@Nullable byte[] bArr, @Nullable byte[] bArr2, @Nullable String str, int i, @Nullable Map<String, String> map) throws MediaPlayer2.NoDrmSchemeException {
        try {
            return this.mPlayer.getKeyRequest(bArr, bArr2, str, i, map);
        } catch (MediaPlayer.NoDrmSchemeException e2) {
            throw new MediaPlayer2.NoDrmSchemeException(e2.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    @NonNull
    public String getDrmPropertyString(@NonNull String str) throws MediaPlayer2.NoDrmSchemeException {
        try {
            return this.mPlayer.getDrmPropertyString(str);
        } catch (MediaPlayer.NoDrmSchemeException e2) {
            throw new MediaPlayer2.NoDrmSchemeException(e2.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public long getDuration() {
        return this.mPlayer.getDuration();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public float getMaxPlayerVolume() {
        return 1.0f;
    }

    @Override // android.support.v4.media.MediaPlayer2
    public PersistableBundle getMetrics() {
        return this.mPlayer.getMetrics();
    }

    @Override // android.support.v4.media.MediaPlayer2
    @NonNull
    public PlaybackParams2 getPlaybackParams() {
        return new PlaybackParams2.Builder(this.mPlayer.getPlaybackParams()).build();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public float getPlayerVolume() {
        return this.mPlayer.getVolume();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public int getSelectedTrack(int i) {
        return this.mPlayer.getSelectedTrack(i);
    }

    @Override // android.support.v4.media.MediaPlayer2
    public int getState() {
        return this.mPlayer.getMediaPlayer2State();
    }

    @Override // android.support.v4.media.MediaPlayer2
    @Nullable
    public MediaTimestamp2 getTimestamp() {
        return this.mPlayer.getTimestamp();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public List<MediaPlayer2.TrackInfo> getTrackInfo() {
        MediaPlayer.TrackInfo[] trackInfo = this.mPlayer.getTrackInfo();
        ArrayList arrayList = new ArrayList();
        for (MediaPlayer.TrackInfo trackInfo2 : trackInfo) {
            arrayList.add(new TrackInfoImpl(trackInfo2.getTrackType(), trackInfo2.getFormat()));
        }
        return arrayList;
    }

    @Override // android.support.v4.media.MediaPlayer2
    public int getVideoHeight() {
        return this.mPlayer.getVideoHeight();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public int getVideoWidth() {
        return this.mPlayer.getVideoWidth();
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void loopCurrent(final boolean z) {
        addTask(new Task(3, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass10 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.setLooping(z);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void notifyWhenCommandLabelReached(final Object obj) {
        addTask(new Task(1003, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass12 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.notifyMediaPlayer2Event(new Mp2EventNotifier() {
                    /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass12.AnonymousClass1 */

                    @Override // android.support.v4.media.MediaPlayer2Impl.Mp2EventNotifier
                    public void notify(MediaPlayer2.EventCallback eventCallback) {
                        AnonymousClass12 r1 = AnonymousClass12.this;
                        eventCallback.onCommandLabelReached(MediaPlayer2Impl.this, obj);
                    }
                });
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void pause() {
        addTask(new Task(4, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass4 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.pause();
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void play() {
        addTask(new Task(5, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass2 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.play();
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void prepare() {
        addTask(new Task(6, true) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass3 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() throws IOException {
                MediaPlayer2Impl.this.mPlayer.prepareAsync();
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void prepareDrm(@NonNull UUID uuid) throws UnsupportedSchemeException, ResourceBusyException, MediaPlayer2.ProvisioningNetworkErrorException, MediaPlayer2.ProvisioningServerErrorException {
        try {
            this.mPlayer.prepareDrm(uuid);
        } catch (MediaPlayer.ProvisioningNetworkErrorException e2) {
            throw new MediaPlayer2.ProvisioningNetworkErrorException(e2.getMessage());
        } catch (MediaPlayer.ProvisioningServerErrorException e3) {
            throw new MediaPlayer2.ProvisioningServerErrorException(e3.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public byte[] provideDrmKeyResponse(@Nullable byte[] bArr, @NonNull byte[] bArr2) throws MediaPlayer2.NoDrmSchemeException, DeniedByServerException {
        try {
            return this.mPlayer.provideKeyResponse(bArr, bArr2);
        } catch (MediaPlayer.NoDrmSchemeException e2) {
            throw new MediaPlayer2.NoDrmSchemeException(e2.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void releaseDrm() throws MediaPlayer2.NoDrmSchemeException {
        try {
            this.mPlayer.releaseDrm();
        } catch (MediaPlayer.NoDrmSchemeException e2) {
            throw new MediaPlayer2.NoDrmSchemeException(e2.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void reset() {
        this.mPlayer.reset();
        synchronized (this.mLock) {
            this.mMp2EventCallbackRecord = null;
            this.mPlayerEventCallbackMap.clear();
            this.mDrmEventCallbackRecord = null;
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void restoreDrmKeys(@NonNull byte[] bArr) throws MediaPlayer2.NoDrmSchemeException {
        try {
            this.mPlayer.restoreKeys(bArr);
        } catch (MediaPlayer.NoDrmSchemeException e2) {
            throw new MediaPlayer2.NoDrmSchemeException(e2.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void seekTo(final long j, final int i) {
        addTask(new Task(14, true) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass16 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.seekTo(j, i);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void selectTrack(final int i) {
        addTask(new Task(15, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass20 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.selectTrack(i);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setAudioAttributes(@NonNull final AudioAttributesCompat audioAttributesCompat) {
        addTask(new Task(16, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass6 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.setAudioAttributes(audioAttributesCompat);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setAudioSessionId(final int i) {
        addTask(new Task(17, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass17 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.setAudioSessionId(i);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setAuxEffectSendLevel(final float f2) {
        addTask(new Task(18, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass19 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.setAuxEffectSendLevel(f2);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setDataSource(@NonNull final DataSourceDesc dataSourceDesc) {
        addTask(new Task(19, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass7 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                Preconditions.checkNotNull(dataSourceDesc, "the DataSourceDesc cannot be null");
                try {
                    MediaPlayer2Impl.this.mPlayer.setFirst(dataSourceDesc);
                } catch (IOException e2) {
                    Log.e(MediaPlayer2Impl.TAG, "process: setDataSource", e2);
                }
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setDrmEventCallback(@NonNull Executor executor, @NonNull MediaPlayer2.DrmEventCallback drmEventCallback) {
        if (drmEventCallback == null) {
            throw new IllegalArgumentException("Illegal null EventCallback");
        } else if (executor != null) {
            synchronized (this.mLock) {
                this.mDrmEventCallbackRecord = new Pair<>(executor, drmEventCallback);
            }
        } else {
            throw new IllegalArgumentException("Illegal null Executor for the EventCallback");
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setDrmPropertyString(@NonNull String str, @NonNull String str2) throws MediaPlayer2.NoDrmSchemeException {
        try {
            this.mPlayer.setDrmPropertyString(str, str2);
        } catch (MediaPlayer.NoDrmSchemeException e2) {
            throw new MediaPlayer2.NoDrmSchemeException(e2.getMessage());
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setEventCallback(@NonNull Executor executor, @NonNull MediaPlayer2.EventCallback eventCallback) {
        if (eventCallback == null) {
            throw new IllegalArgumentException("Illegal null EventCallback");
        } else if (executor != null) {
            synchronized (this.mLock) {
                this.mMp2EventCallbackRecord = new Pair<>(executor, eventCallback);
            }
        } else {
            throw new IllegalArgumentException("Illegal null Executor for the EventCallback");
        }
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setNextDataSource(@NonNull final DataSourceDesc dataSourceDesc) {
        addTask(new Task(22, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass8 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                Preconditions.checkNotNull(dataSourceDesc, "the DataSourceDesc cannot be null");
                MediaPlayer2Impl mediaPlayer2Impl = MediaPlayer2Impl.this;
                mediaPlayer2Impl.handleDataSourceError(mediaPlayer2Impl.mPlayer.setNext(dataSourceDesc));
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setNextDataSources(@NonNull final List<DataSourceDesc> list) {
        addTask(new Task(23, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass9 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                List list = list;
                if (list == null || list.size() == 0) {
                    throw new IllegalArgumentException("data source list cannot be null or empty.");
                }
                for (DataSourceDesc dataSourceDesc : list) {
                    if (dataSourceDesc == null) {
                        throw new IllegalArgumentException("DataSourceDesc in the source list cannot be null.");
                    }
                }
                MediaPlayer2Impl mediaPlayer2Impl = MediaPlayer2Impl.this;
                mediaPlayer2Impl.handleDataSourceError(mediaPlayer2Impl.mPlayer.setNextMultiple(list));
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setOnDrmConfigHelper(final MediaPlayer2.OnDrmConfigHelper onDrmConfigHelper) {
        this.mPlayer.setOnDrmConfigHelper(new MediaPlayer.OnDrmConfigHelper() {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass22 */

            public void onDrmConfig(MediaPlayer mediaPlayer) {
                MediaPlayerSource sourceForPlayer = MediaPlayer2Impl.this.mPlayer.getSourceForPlayer(mediaPlayer);
                onDrmConfigHelper.onDrmConfig(MediaPlayer2Impl.this, sourceForPlayer == null ? null : sourceForPlayer.getDSD());
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setPlaybackParams(@NonNull final PlaybackParams2 playbackParams2) {
        addTask(new Task(24, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass15 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.setPlaybackParamsInternal(playbackParams2.getPlaybackParams());
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setPlayerVolume(final float f2) {
        addTask(new Task(26, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass11 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.setVolume(f2);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void setSurface(final Surface surface) {
        addTask(new Task(27, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass13 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.setSurface(surface);
            }
        });
    }

    @Override // android.support.v4.media.MediaPlayer2
    public void skipToNext() {
        addTask(new Task(29, false) {
            /* class android.support.v4.media.MediaPlayer2Impl.AnonymousClass5 */

            /* access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaPlayer2Impl.Task
            public void process() {
                MediaPlayer2Impl.this.mPlayer.skipToNext();
            }
        });
    }
}
