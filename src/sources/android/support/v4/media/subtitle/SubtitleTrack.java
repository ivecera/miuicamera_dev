package android.support.v4.media.subtitle;

import android.graphics.Canvas;
import android.media.MediaFormat;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.media.SubtitleData2;
import android.support.v4.media.subtitle.MediaTimeProvider;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

@RequiresApi(28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class SubtitleTrack implements MediaTimeProvider.OnMediaTimeListener {
    private static final String TAG = "SubtitleTrack";
    public boolean DEBUG = false;
    /* access modifiers changed from: private */
    public final ArrayList<Cue> mActiveCues = new ArrayList<>();
    private CueList mCues;
    private MediaFormat mFormat;
    protected Handler mHandler = new Handler();
    private long mLastTimeMs;
    private long mLastUpdateTimeMs;
    private long mNextScheduledTimeMs = -1;
    /* access modifiers changed from: private */
    public Runnable mRunnable;
    private final LongSparseArray<Run> mRunsByEndTime = new LongSparseArray<>();
    private final LongSparseArray<Run> mRunsByID = new LongSparseArray<>();
    protected MediaTimeProvider mTimeProvider;
    protected boolean mVisible;

    /* access modifiers changed from: package-private */
    public static class Cue {
        public long mEndTimeMs;
        public long[] mInnerTimesMs;
        public Cue mNextInRun;
        public long mRunID;
        public long mStartTimeMs;

        Cue() {
        }

        public void onTime(long j) {
        }
    }

    static class CueList {
        private static final String TAG = "CueList";
        public boolean DEBUG = false;
        /* access modifiers changed from: private */
        public SortedMap<Long, ArrayList<Cue>> mCues = new TreeMap();

        class EntryIterator implements Iterator<Pair<Long, Cue>> {
            private long mCurrentTimeMs;
            private boolean mDone;
            private Pair<Long, Cue> mLastEntry;
            private Iterator<Cue> mLastListIterator;
            private Iterator<Cue> mListIterator;
            private SortedMap<Long, ArrayList<Cue>> mRemainingCues;

            EntryIterator(SortedMap<Long, ArrayList<Cue>> sortedMap) {
                if (CueList.this.DEBUG) {
                    Log.v(CueList.TAG, sortedMap + "");
                }
                this.mRemainingCues = sortedMap;
                this.mLastListIterator = null;
                nextKey();
            }

            private void nextKey() {
                do {
                    try {
                        if (this.mRemainingCues != null) {
                            this.mCurrentTimeMs = this.mRemainingCues.firstKey().longValue();
                            this.mListIterator = this.mRemainingCues.get(Long.valueOf(this.mCurrentTimeMs)).iterator();
                            try {
                                this.mRemainingCues = this.mRemainingCues.tailMap(Long.valueOf(this.mCurrentTimeMs + 1));
                            } catch (IllegalArgumentException unused) {
                                this.mRemainingCues = null;
                            }
                            this.mDone = false;
                        } else {
                            throw new NoSuchElementException("");
                        }
                    } catch (NoSuchElementException unused2) {
                        this.mDone = true;
                        this.mRemainingCues = null;
                        this.mListIterator = null;
                        return;
                    }
                } while (!this.mListIterator.hasNext());
            }

            public boolean hasNext() {
                return !this.mDone;
            }

            @Override // java.util.Iterator
            public Pair<Long, Cue> next() {
                if (!this.mDone) {
                    this.mLastEntry = new Pair<>(Long.valueOf(this.mCurrentTimeMs), this.mListIterator.next());
                    Iterator<Cue> it = this.mListIterator;
                    this.mLastListIterator = it;
                    if (!it.hasNext()) {
                        nextKey();
                    }
                    return this.mLastEntry;
                }
                throw new NoSuchElementException("");
            }

            public void remove() {
                if (this.mLastListIterator != null) {
                    Pair<Long, Cue> pair = this.mLastEntry;
                    if (((Cue) pair.second).mEndTimeMs == ((Long) pair.first).longValue()) {
                        this.mLastListIterator.remove();
                        this.mLastListIterator = null;
                        if (((ArrayList) CueList.this.mCues.get(this.mLastEntry.first)).size() == 0) {
                            CueList.this.mCues.remove(this.mLastEntry.first);
                        }
                        Cue cue = (Cue) this.mLastEntry.second;
                        CueList.this.removeEvent(cue, cue.mStartTimeMs);
                        long[] jArr = cue.mInnerTimesMs;
                        if (jArr != null) {
                            for (long j : jArr) {
                                CueList.this.removeEvent(cue, j);
                            }
                            return;
                        }
                        return;
                    }
                }
                throw new IllegalStateException("");
            }
        }

        CueList() {
        }

        private boolean addEvent(Cue cue, long j) {
            ArrayList<Cue> arrayList = this.mCues.get(Long.valueOf(j));
            if (arrayList == null) {
                arrayList = new ArrayList<>(2);
                this.mCues.put(Long.valueOf(j), arrayList);
            } else if (arrayList.contains(cue)) {
                return false;
            }
            arrayList.add(cue);
            return true;
        }

        /* access modifiers changed from: private */
        public void removeEvent(Cue cue, long j) {
            ArrayList<Cue> arrayList = this.mCues.get(Long.valueOf(j));
            if (arrayList != null) {
                arrayList.remove(cue);
                if (arrayList.size() == 0) {
                    this.mCues.remove(Long.valueOf(j));
                }
            }
        }

        public void add(Cue cue) {
            long j = cue.mStartTimeMs;
            if (j < cue.mEndTimeMs && addEvent(cue, j)) {
                long j2 = cue.mStartTimeMs;
                long[] jArr = cue.mInnerTimesMs;
                if (jArr != null) {
                    for (long j3 : jArr) {
                        if (j3 > j2 && j3 < cue.mEndTimeMs) {
                            addEvent(cue, j3);
                            j2 = j3;
                        }
                    }
                }
                addEvent(cue, cue.mEndTimeMs);
            }
        }

        public Iterable<Pair<Long, Cue>> entriesBetween(final long j, final long j2) {
            return new Iterable<Pair<Long, Cue>>() {
                /* class android.support.v4.media.subtitle.SubtitleTrack.CueList.AnonymousClass1 */

                @Override // java.lang.Iterable
                public Iterator<Pair<Long, Cue>> iterator() {
                    if (CueList.this.DEBUG) {
                        Log.d(CueList.TAG, "slice (" + j + ", " + j2 + "]=");
                    }
                    try {
                        return new EntryIterator(CueList.this.mCues.subMap(Long.valueOf(j + 1), Long.valueOf(j2 + 1)));
                    } catch (IllegalArgumentException unused) {
                        return new EntryIterator(null);
                    }
                }
            };
        }

        public long nextTimeAfter(long j) {
            try {
                SortedMap<Long, ArrayList<Cue>> tailMap = this.mCues.tailMap(Long.valueOf(j + 1));
                if (tailMap != null) {
                    return tailMap.firstKey().longValue();
                }
            } catch (IllegalArgumentException | NoSuchElementException unused) {
            }
            return -1;
        }

        public void remove(Cue cue) {
            removeEvent(cue, cue.mStartTimeMs);
            long[] jArr = cue.mInnerTimesMs;
            if (jArr != null) {
                for (long j : jArr) {
                    removeEvent(cue, j);
                }
            }
            removeEvent(cue, cue.mEndTimeMs);
        }
    }

    public interface RenderingWidget {

        public interface OnChangedListener {
            void onChanged(RenderingWidget renderingWidget);
        }

        void draw(Canvas canvas);

        void onAttachedToWindow();

        void onDetachedFromWindow();

        void setOnChangedListener(OnChangedListener onChangedListener);

        void setSize(int i, int i2);

        void setVisible(boolean z);
    }

    private static class Run {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public long mEndTimeMs;
        public Cue mFirstCue;
        public Run mNextRunAtEndTimeMs;
        public Run mPrevRunAtEndTimeMs;
        public long mRunID;
        private long mStoredEndTimeMs;

        private Run() {
            this.mEndTimeMs = -1;
            this.mRunID = 0;
            this.mStoredEndTimeMs = -1;
        }

        public void removeAtEndTimeMs() {
            Run run = this.mPrevRunAtEndTimeMs;
            if (run != null) {
                run.mNextRunAtEndTimeMs = this.mNextRunAtEndTimeMs;
                this.mPrevRunAtEndTimeMs = null;
            }
            Run run2 = this.mNextRunAtEndTimeMs;
            if (run2 != null) {
                run2.mPrevRunAtEndTimeMs = run;
                this.mNextRunAtEndTimeMs = null;
            }
        }

        public void storeByEndTimeMs(LongSparseArray<Run> longSparseArray) {
            int indexOfKey = longSparseArray.indexOfKey(this.mStoredEndTimeMs);
            if (indexOfKey >= 0) {
                if (this.mPrevRunAtEndTimeMs == null) {
                    Run run = this.mNextRunAtEndTimeMs;
                    if (run == null) {
                        longSparseArray.removeAt(indexOfKey);
                    } else {
                        longSparseArray.setValueAt(indexOfKey, run);
                    }
                }
                removeAtEndTimeMs();
            }
            long j = this.mEndTimeMs;
            if (j >= 0) {
                this.mPrevRunAtEndTimeMs = null;
                this.mNextRunAtEndTimeMs = longSparseArray.get(j);
                Run run2 = this.mNextRunAtEndTimeMs;
                if (run2 != null) {
                    run2.mPrevRunAtEndTimeMs = this;
                }
                longSparseArray.put(this.mEndTimeMs, this);
                this.mStoredEndTimeMs = this.mEndTimeMs;
            }
        }
    }

    public SubtitleTrack(MediaFormat mediaFormat) {
        this.mFormat = mediaFormat;
        this.mCues = new CueList();
        clearActiveCues();
        this.mLastTimeMs = -1;
    }

    private void removeRunsByEndTimeIndex(int i) {
        Run valueAt = this.mRunsByEndTime.valueAt(i);
        while (valueAt != null) {
            Cue cue = valueAt.mFirstCue;
            while (cue != null) {
                this.mCues.remove(cue);
                Cue cue2 = cue.mNextInRun;
                cue.mNextInRun = null;
                cue = cue2;
            }
            this.mRunsByID.remove(valueAt.mRunID);
            Run run = valueAt.mNextRunAtEndTimeMs;
            valueAt.mPrevRunAtEndTimeMs = null;
            valueAt.mNextRunAtEndTimeMs = null;
            valueAt = run;
        }
        this.mRunsByEndTime.removeAt(i);
    }

    private synchronized void takeTime(long j) {
        this.mLastTimeMs = j;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00da, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f9, code lost:
        return false;
     */
    public synchronized boolean addCue(Cue cue) {
        this.mCues.add(cue);
        if (cue.mRunID != 0) {
            Run run = this.mRunsByID.get(cue.mRunID);
            if (run == null) {
                run = new Run();
                this.mRunsByID.put(cue.mRunID, run);
                run.mEndTimeMs = cue.mEndTimeMs;
            } else if (run.mEndTimeMs < cue.mEndTimeMs) {
                run.mEndTimeMs = cue.mEndTimeMs;
            }
            cue.mNextInRun = run.mFirstCue;
            run.mFirstCue = cue;
        }
        final long j = -1;
        if (this.mTimeProvider != null) {
            try {
                j = this.mTimeProvider.getCurrentTimeUs(false, true) / 1000;
            } catch (IllegalStateException unused) {
            }
        }
        if (this.DEBUG) {
            Log.v(TAG, "mVisible=" + this.mVisible + ", " + cue.mStartTimeMs + " <= " + j + ", " + cue.mEndTimeMs + " >= " + this.mLastTimeMs);
        }
        if (this.mVisible && cue.mStartTimeMs <= j && cue.mEndTimeMs >= this.mLastTimeMs) {
            if (this.mRunnable != null) {
                this.mHandler.removeCallbacks(this.mRunnable);
            }
            this.mRunnable = new Runnable() {
                /* class android.support.v4.media.subtitle.SubtitleTrack.AnonymousClass1 */

                public void run() {
                    synchronized (this) {
                        Runnable unused = SubtitleTrack.this.mRunnable = null;
                        SubtitleTrack.this.updateActiveCues(true, j);
                        SubtitleTrack.this.updateView(SubtitleTrack.this.mActiveCues);
                    }
                }
            };
            if (this.mHandler.postDelayed(this.mRunnable, 10)) {
                if (this.DEBUG) {
                    Log.v(TAG, "scheduling update");
                }
            } else if (this.DEBUG) {
                Log.w(TAG, "failed to schedule subtitle view update");
            }
        } else if (this.mVisible && cue.mEndTimeMs >= this.mLastTimeMs && (cue.mStartTimeMs < this.mNextScheduledTimeMs || this.mNextScheduledTimeMs < 0)) {
            scheduleTimedEvents();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void clearActiveCues() {
        if (this.DEBUG) {
            Log.v(TAG, "Clearing " + this.mActiveCues.size() + " active cues");
        }
        this.mActiveCues.clear();
        this.mLastUpdateTimeMs = -1;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        for (int size = this.mRunsByEndTime.size() - 1; size >= 0; size--) {
            removeRunsByEndTimeIndex(size);
        }
        super.finalize();
    }

    /* access modifiers changed from: protected */
    public void finishedRun(long j) {
        Run run;
        if (j != 0 && j != -1 && (run = this.mRunsByID.get(j)) != null) {
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public final MediaFormat getFormat() {
        return this.mFormat;
    }

    public abstract RenderingWidget getRenderingWidget();

    public int getTrackType() {
        return getRenderingWidget() == null ? 3 : 4;
    }

    public void hide() {
        if (this.mVisible) {
            MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
            if (mediaTimeProvider != null) {
                mediaTimeProvider.cancelNotifications(this);
            }
            RenderingWidget renderingWidget = getRenderingWidget();
            if (renderingWidget != null) {
                renderingWidget.setVisible(false);
            }
            this.mVisible = false;
        }
    }

    public void onData(SubtitleData2 subtitleData2) {
        long startTimeUs = subtitleData2.getStartTimeUs() + 1;
        onData(subtitleData2.getData(), true, startTimeUs);
        setRunDiscardTimeMs(startTimeUs, (subtitleData2.getStartTimeUs() + subtitleData2.getDurationUs()) / 1000);
    }

    /* access modifiers changed from: protected */
    public abstract void onData(byte[] bArr, boolean z, long j);

    @Override // android.support.v4.media.subtitle.MediaTimeProvider.OnMediaTimeListener
    public void onSeek(long j) {
        if (this.DEBUG) {
            Log.d(TAG, "onSeek " + j);
        }
        synchronized (this) {
            long j2 = j / 1000;
            updateActiveCues(true, j2);
            takeTime(j2);
        }
        updateView(this.mActiveCues);
        scheduleTimedEvents();
    }

    @Override // android.support.v4.media.subtitle.MediaTimeProvider.OnMediaTimeListener
    public void onStop() {
        synchronized (this) {
            if (this.DEBUG) {
                Log.d(TAG, "onStop");
            }
            clearActiveCues();
            this.mLastTimeMs = -1;
        }
        updateView(this.mActiveCues);
        this.mNextScheduledTimeMs = -1;
        this.mTimeProvider.notifyAt(-1, this);
    }

    @Override // android.support.v4.media.subtitle.MediaTimeProvider.OnMediaTimeListener
    public void onTimedEvent(long j) {
        if (this.DEBUG) {
            Log.d(TAG, "onTimedEvent " + j);
        }
        synchronized (this) {
            long j2 = j / 1000;
            updateActiveCues(false, j2);
            takeTime(j2);
        }
        updateView(this.mActiveCues);
        scheduleTimedEvents();
    }

    /* access modifiers changed from: protected */
    public void scheduleTimedEvents() {
        if (this.mTimeProvider != null) {
            this.mNextScheduledTimeMs = this.mCues.nextTimeAfter(this.mLastTimeMs);
            if (this.DEBUG) {
                Log.d(TAG, "sched @" + this.mNextScheduledTimeMs + " after " + this.mLastTimeMs);
            }
            MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
            long j = this.mNextScheduledTimeMs;
            mediaTimeProvider.notifyAt(j >= 0 ? j * 1000 : -1, this);
        }
    }

    public void setRunDiscardTimeMs(long j, long j2) {
        Run run;
        if (j != 0 && j != -1 && (run = this.mRunsByID.get(j)) != null) {
            run.mEndTimeMs = j2;
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        return;
     */
    public synchronized void setTimeProvider(MediaTimeProvider mediaTimeProvider) {
        if (this.mTimeProvider != mediaTimeProvider) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.cancelNotifications(this);
            }
            this.mTimeProvider = mediaTimeProvider;
            if (this.mTimeProvider != null) {
                this.mTimeProvider.scheduleUpdate(this);
            }
        }
    }

    public void show() {
        if (!this.mVisible) {
            this.mVisible = true;
            RenderingWidget renderingWidget = getRenderingWidget();
            if (renderingWidget != null) {
                renderingWidget.setVisible(true);
            }
            MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
            if (mediaTimeProvider != null) {
                mediaTimeProvider.scheduleUpdate(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0007, code lost:
        if (r6.mLastUpdateTimeMs > r8) goto L_0x0009;
     */
    public synchronized void updateActiveCues(boolean z, long j) {
        if (!z) {
        }
        clearActiveCues();
        Iterator<Pair<Long, Cue>> it = this.mCues.entriesBetween(this.mLastUpdateTimeMs, j).iterator();
        while (it.hasNext()) {
            Pair<Long, Cue> next = it.next();
            Cue cue = (Cue) next.second;
            if (cue.mEndTimeMs == ((Long) next.first).longValue()) {
                if (this.DEBUG) {
                    Log.v(TAG, "Removing " + cue);
                }
                this.mActiveCues.remove(cue);
                if (cue.mRunID == 0) {
                    it.remove();
                }
            } else if (cue.mStartTimeMs == ((Long) next.first).longValue()) {
                if (this.DEBUG) {
                    Log.v(TAG, "Adding " + cue);
                }
                if (cue.mInnerTimesMs != null) {
                    cue.onTime(j);
                }
                this.mActiveCues.add(cue);
            } else if (cue.mInnerTimesMs != null) {
                cue.onTime(j);
            }
        }
        while (this.mRunsByEndTime.size() > 0 && this.mRunsByEndTime.keyAt(0) <= j) {
            removeRunsByEndTimeIndex(0);
        }
        this.mLastUpdateTimeMs = j;
    }

    public abstract void updateView(ArrayList<Cue> arrayList);
}
