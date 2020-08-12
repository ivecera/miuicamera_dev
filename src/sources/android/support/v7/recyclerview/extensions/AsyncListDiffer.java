package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class AsyncListDiffer<T> {
    /* access modifiers changed from: private */
    public final AsyncDifferConfig<T> mConfig;
    @Nullable
    private List<T> mList;
    /* access modifiers changed from: private */
    public int mMaxScheduledGeneration;
    @NonNull
    private List<T> mReadOnlyList = Collections.emptyList();
    private final ListUpdateCallback mUpdateCallback;

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.mUpdateCallback = listUpdateCallback;
        this.mConfig = asyncDifferConfig;
    }

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.mUpdateCallback = new AdapterListUpdateCallback(adapter);
        this.mConfig = new AsyncDifferConfig.Builder(itemCallback).build();
    }

    /* access modifiers changed from: private */
    public void latchList(@NonNull List<T> list, @NonNull DiffUtil.DiffResult diffResult) {
        this.mList = list;
        this.mReadOnlyList = Collections.unmodifiableList(list);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mReadOnlyList;
    }

    public void submitList(@Nullable final List<T> list) {
        final List<T> list2 = this.mList;
        if (list != list2) {
            final int i = this.mMaxScheduledGeneration + 1;
            this.mMaxScheduledGeneration = i;
            if (list == null) {
                int size = list2.size();
                this.mList = null;
                this.mReadOnlyList = Collections.emptyList();
                this.mUpdateCallback.onRemoved(0, size);
            } else if (list2 == null) {
                this.mList = list;
                this.mReadOnlyList = Collections.unmodifiableList(list);
                this.mUpdateCallback.onInserted(0, list.size());
            } else {
                this.mConfig.getBackgroundThreadExecutor().execute(new Runnable() {
                    /* class android.support.v7.recyclerview.extensions.AsyncListDiffer.AnonymousClass1 */

                    public void run() {
                        final DiffUtil.DiffResult calculateDiff = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                            /* class android.support.v7.recyclerview.extensions.AsyncListDiffer.AnonymousClass1.AnonymousClass1 */

                            @Override // android.support.v7.util.DiffUtil.Callback
                            public boolean areContentsTheSame(int i, int i2) {
                                T t = list2.get(i);
                                T t2 = list.get(i2);
                                if (t != null && t2 != null) {
                                    return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(t, t2);
                                }
                                if (t == null && t2 == null) {
                                    return true;
                                }
                                throw new AssertionError();
                            }

                            @Override // android.support.v7.util.DiffUtil.Callback
                            public boolean areItemsTheSame(int i, int i2) {
                                T t = list2.get(i);
                                T t2 = list.get(i2);
                                return (t == null || t2 == null) ? t == null && t2 == null : AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(t, t2);
                            }

                            @Override // android.support.v7.util.DiffUtil.Callback
                            @Nullable
                            public Object getChangePayload(int i, int i2) {
                                T t = list2.get(i);
                                T t2 = list.get(i2);
                                if (t != null && t2 != null) {
                                    return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(t, t2);
                                }
                                throw new AssertionError();
                            }

                            @Override // android.support.v7.util.DiffUtil.Callback
                            public int getNewListSize() {
                                return list.size();
                            }

                            @Override // android.support.v7.util.DiffUtil.Callback
                            public int getOldListSize() {
                                return list2.size();
                            }
                        });
                        AsyncListDiffer.this.mConfig.getMainThreadExecutor().execute(new Runnable() {
                            /* class android.support.v7.recyclerview.extensions.AsyncListDiffer.AnonymousClass1.AnonymousClass2 */

                            public void run() {
                                int access$100 = AsyncListDiffer.this.mMaxScheduledGeneration;
                                AnonymousClass1 r1 = AnonymousClass1.this;
                                if (access$100 == i) {
                                    AsyncListDiffer.this.latchList(list, calculateDiff);
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
