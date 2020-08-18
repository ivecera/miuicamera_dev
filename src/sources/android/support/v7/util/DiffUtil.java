package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>() {
        /* class android.support.v7.util.DiffUtil.AnonymousClass1 */

        public int compare(Snake snake, Snake snake2) {
            int i = snake.x - snake2.x;
            return i == 0 ? snake.y - snake2.y : i;
        }
    };

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        @Nullable
        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] iArr, int[] iArr2, boolean z) {
            this.mSnakes = list;
            this.mOldItemStatuses = iArr;
            this.mNewItemStatuses = iArr2;
            Arrays.fill(this.mOldItemStatuses, 0);
            Arrays.fill(this.mNewItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = z;
            addRootSnake();
            findMatchingItems();
        }

        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
            if (snake == null || snake.x != 0 || snake.y != 0) {
                Snake snake2 = new Snake();
                snake2.x = 0;
                snake2.y = 0;
                snake2.removal = false;
                snake2.size = 0;
                snake2.reverse = false;
                this.mSnakes.add(0, snake2);
            }
        }

        private void dispatchAdditions(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onInserted(i, i2);
                return;
            }
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                int i5 = i3 + i4;
                int i6 = this.mNewItemStatuses[i5] & 31;
                if (i6 == 0) {
                    listUpdateCallback.onInserted(i, 1);
                    for (PostponedUpdate postponedUpdate : list) {
                        postponedUpdate.currentPos++;
                    }
                } else if (i6 == 4 || i6 == 8) {
                    int i7 = this.mNewItemStatuses[i5] >> 5;
                    listUpdateCallback.onMoved(removePostponedUpdate(list, i7, true).currentPos, i);
                    if (i6 == 4) {
                        listUpdateCallback.onChanged(i, 1, this.mCallback.getChangePayload(i7, i5));
                    }
                } else if (i6 == 16) {
                    list.add(new PostponedUpdate(i5, i, false));
                } else {
                    throw new IllegalStateException("unknown flag for pos " + i5 + " " + Long.toBinaryString((long) i6));
                }
            }
        }

        private void dispatchRemovals(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onRemoved(i, i2);
                return;
            }
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                int i5 = i3 + i4;
                int i6 = this.mOldItemStatuses[i5] & 31;
                if (i6 == 0) {
                    listUpdateCallback.onRemoved(i + i4, 1);
                    for (PostponedUpdate postponedUpdate : list) {
                        postponedUpdate.currentPos--;
                    }
                } else if (i6 == 4 || i6 == 8) {
                    int i7 = this.mOldItemStatuses[i5] >> 5;
                    PostponedUpdate removePostponedUpdate = removePostponedUpdate(list, i7, false);
                    listUpdateCallback.onMoved(i + i4, removePostponedUpdate.currentPos - 1);
                    if (i6 == 4) {
                        listUpdateCallback.onChanged(removePostponedUpdate.currentPos - 1, 1, this.mCallback.getChangePayload(i5, i7));
                    }
                } else if (i6 == 16) {
                    list.add(new PostponedUpdate(i5, i + i4, true));
                } else {
                    throw new IllegalStateException("unknown flag for pos " + i5 + " " + Long.toBinaryString((long) i6));
                }
            }
        }

        private void findAddition(int i, int i2, int i3) {
            if (this.mOldItemStatuses[i - 1] == 0) {
                findMatchingItem(i, i2, i3, false);
            }
        }

        private boolean findMatchingItem(int i, int i2, int i3, boolean z) {
            int i4;
            int i5;
            if (z) {
                i2--;
                i5 = i;
                i4 = i2;
            } else {
                i5 = i - 1;
                i4 = i5;
            }
            while (i3 >= 0) {
                Snake snake = this.mSnakes.get(i3);
                int i6 = snake.x;
                int i7 = snake.size;
                int i8 = i6 + i7;
                int i9 = snake.y + i7;
                int i10 = 8;
                if (z) {
                    for (int i11 = i5 - 1; i11 >= i8; i11--) {
                        if (this.mCallback.areItemsTheSame(i11, i4)) {
                            if (!this.mCallback.areContentsTheSame(i11, i4)) {
                                i10 = 4;
                            }
                            this.mNewItemStatuses[i4] = (i11 << 5) | 16;
                            this.mOldItemStatuses[i11] = (i4 << 5) | i10;
                            return true;
                        }
                    }
                    continue;
                } else {
                    for (int i12 = i2 - 1; i12 >= i9; i12--) {
                        if (this.mCallback.areItemsTheSame(i4, i12)) {
                            if (!this.mCallback.areContentsTheSame(i4, i12)) {
                                i10 = 4;
                            }
                            int i13 = i - 1;
                            this.mOldItemStatuses[i13] = (i12 << 5) | 16;
                            this.mNewItemStatuses[i12] = (i13 << 5) | i10;
                            return true;
                        }
                    }
                    continue;
                }
                i5 = snake.x;
                i2 = snake.y;
                i3--;
            }
            return false;
        }

        private void findMatchingItems() {
            int i = this.mOldListSize;
            int i2 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = this.mSnakes.get(size);
                int i3 = snake.x;
                int i4 = snake.size;
                int i5 = i3 + i4;
                int i6 = snake.y + i4;
                if (this.mDetectMoves) {
                    while (i > i5) {
                        findAddition(i, i2, size);
                        i--;
                    }
                    while (i2 > i6) {
                        findRemoval(i, i2, size);
                        i2--;
                    }
                }
                for (int i7 = 0; i7 < snake.size; i7++) {
                    int i8 = snake.x + i7;
                    int i9 = snake.y + i7;
                    int i10 = this.mCallback.areContentsTheSame(i8, i9) ? 1 : 2;
                    this.mOldItemStatuses[i8] = (i9 << 5) | i10;
                    this.mNewItemStatuses[i9] = (i8 << 5) | i10;
                }
                i = snake.x;
                i2 = snake.y;
            }
        }

        private void findRemoval(int i, int i2, int i3) {
            if (this.mNewItemStatuses[i2 - 1] == 0) {
                findMatchingItem(i, i2, i3, true);
            }
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int i, boolean z) {
            int size = list.size() - 1;
            while (size >= 0) {
                PostponedUpdate postponedUpdate = list.get(size);
                if (postponedUpdate.posInOwnerList == i && postponedUpdate.removal == z) {
                    list.remove(size);
                    while (size < list.size()) {
                        list.get(size).currentPos += z ? 1 : -1;
                        size++;
                    }
                    return postponedUpdate;
                }
                size--;
            }
            return null;
        }

        public void dispatchUpdatesTo(@NonNull ListUpdateCallback listUpdateCallback) {
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayList arrayList = new ArrayList();
            int i = this.mOldListSize;
            int i2 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = this.mSnakes.get(size);
                int i3 = snake.size;
                int i4 = snake.x + i3;
                int i5 = snake.y + i3;
                if (i4 < i) {
                    dispatchRemovals(arrayList, batchingListUpdateCallback, i4, i - i4, i4);
                }
                if (i5 < i2) {
                    dispatchAdditions(arrayList, batchingListUpdateCallback, i4, i2 - i5, i5);
                }
                for (int i6 = i3 - 1; i6 >= 0; i6--) {
                    int[] iArr = this.mOldItemStatuses;
                    int i7 = snake.x;
                    if ((iArr[i7 + i6] & 31) == 2) {
                        batchingListUpdateCallback.onChanged(i7 + i6, 1, this.mCallback.getChangePayload(i7 + i6, snake.y + i6));
                    }
                }
                i = snake.x;
                i2 = snake.y;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        public void dispatchUpdatesTo(@NonNull RecyclerView.Adapter adapter) {
            dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public List<Snake> getSnakes() {
            return this.mSnakes;
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(@NonNull T t, @NonNull T t2);

        public abstract boolean areItemsTheSame(@NonNull T t, @NonNull T t2);

        @Nullable
        public Object getChangePayload(@NonNull T t, @NonNull T t2) {
            return null;
        }
    }

    private static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.posInOwnerList = i;
            this.currentPos = i2;
            this.removal = z;
        }
    }

    static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int i, int i2, int i3, int i4) {
            this.oldListStart = i;
            this.oldListEnd = i2;
            this.newListStart = i3;
            this.newListEnd = i4;
        }
    }

    static class Snake {
        boolean removal;
        boolean reverse;
        int size;
        int x;
        int y;

        Snake() {
        }
    }

    private DiffUtil() {
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback) {
        return calculateDiff(callback, true);
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback, boolean z) {
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int abs = Math.abs(oldListSize - newListSize) + oldListSize + newListSize;
        int i = abs * 2;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            Range range = (Range) arrayList2.remove(arrayList2.size() - 1);
            Snake diffPartial = diffPartial(callback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, iArr, iArr2, abs);
            if (diffPartial != null) {
                if (diffPartial.size > 0) {
                    arrayList.add(diffPartial);
                }
                diffPartial.x += range.oldListStart;
                diffPartial.y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range) arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (diffPartial.reverse) {
                    range2.oldListEnd = diffPartial.x;
                    range2.newListEnd = diffPartial.y;
                } else if (diffPartial.removal) {
                    range2.oldListEnd = diffPartial.x - 1;
                    range2.newListEnd = diffPartial.y;
                } else {
                    range2.oldListEnd = diffPartial.x;
                    range2.newListEnd = diffPartial.y - 1;
                }
                arrayList2.add(range2);
                if (!diffPartial.reverse) {
                    int i2 = diffPartial.x;
                    int i3 = diffPartial.size;
                    range.oldListStart = i2 + i3;
                    range.newListStart = diffPartial.y + i3;
                } else if (diffPartial.removal) {
                    int i4 = diffPartial.x;
                    int i5 = diffPartial.size;
                    range.oldListStart = i4 + i5 + 1;
                    range.newListStart = diffPartial.y + i5;
                } else {
                    int i6 = diffPartial.x;
                    int i7 = diffPartial.size;
                    range.oldListStart = i6 + i7;
                    range.newListStart = diffPartial.y + i7 + 1;
                }
                arrayList2.add(range);
            } else {
                arrayList3.add(range);
            }
        }
        Collections.sort(arrayList, SNAKE_COMPARATOR);
        return new DiffResult(callback, arrayList, iArr, iArr2, z);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: boolean} */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0042, code lost:
        if (r24[r13 - 1] < r24[r13 + r5]) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ba, code lost:
        if (r25[r13 - 1] < r25[r13 + 1]) goto L_0x00c7;
     */
    private static Snake diffPartial(Callback callback, int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) {
        boolean z;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z2;
        int i11;
        int i12;
        int i13 = i2 - i;
        int i14 = i4 - i3;
        int i15 = 1;
        if (i13 < 1 || i14 < 1) {
            return null;
        }
        int i16 = i13 - i14;
        int i17 = ((i13 + i14) + 1) / 2;
        int i18 = (i5 - i17) - 1;
        int i19 = i5 + i17 + 1;
        boolean z3 = false;
        Arrays.fill(iArr, i18, i19, 0);
        Arrays.fill(iArr2, i18 + i16, i19 + i16, i13);
        Object[] objArr = i16 % 2 != 0 ? 1 : null;
        int i20 = 0;
        while (i20 <= i17) {
            int i21 = -i20;
            int i22 = i21;
            while (i22 <= i20) {
                if (i22 != i21) {
                    if (i22 != i20) {
                        int i23 = i5 + i22;
                    }
                    i11 = iArr[(i5 + i22) - i15] + i15;
                    z2 = i15;
                    i12 = i11 - i22;
                    while (i11 < i13 && i12 < i14 && callback.areItemsTheSame(i + i11, i3 + i12)) {
                        i11++;
                        i12++;
                    }
                    int i24 = i5 + i22;
                    iArr[i24] = i11;
                    if (objArr != null || i22 < (i16 - i20) + 1 || i22 > (i16 + i20) - 1 || iArr[i24] < iArr2[i24]) {
                        i22 += 2;
                        z3 = false;
                        i15 = 1;
                    } else {
                        Snake snake = new Snake();
                        snake.x = iArr2[i24];
                        snake.y = snake.x - i22;
                        snake.size = iArr[i24] - iArr2[i24];
                        snake.removal = z2;
                        snake.reverse = false;
                        return snake;
                    }
                }
                i11 = iArr[i5 + i22 + i15];
                z2 = z3;
                i12 = i11 - i22;
                while (i11 < i13) {
                    i11++;
                    i12++;
                }
                int i242 = i5 + i22;
                iArr[i242] = i11;
                if (objArr != null) {
                }
                i22 += 2;
                z3 = false;
                i15 = 1;
            }
            boolean z4 = z3;
            int i25 = i21;
            while (i25 <= i20) {
                int i26 = i25 + i16;
                if (i26 != i20 + i16) {
                    if (i26 != i21 + i16) {
                        int i27 = i5 + i26;
                        i10 = 1;
                        i9 = 1;
                    } else {
                        i10 = 1;
                    }
                    i6 = iArr2[(i5 + i26) + i10] - i10;
                    z = i10;
                    i7 = i6 - i26;
                    while (true) {
                        if (i6 > 0 && i7 > 0) {
                            i8 = i13;
                            if (!callback.areItemsTheSame((i + i6) - 1, (i3 + i7) - 1)) {
                                break;
                            }
                            i6--;
                            i7--;
                            i13 = i8;
                        } else {
                            i8 = i13;
                        }
                    }
                    i8 = i13;
                    int i28 = i5 + i26;
                    iArr2[i28] = i6;
                    if (objArr == null || i26 < i21 || i26 > i20 || iArr[i28] < iArr2[i28]) {
                        i25 += 2;
                        i13 = i8;
                        z4 = false;
                    } else {
                        Snake snake2 = new Snake();
                        snake2.x = iArr2[i28];
                        snake2.y = snake2.x - i26;
                        snake2.size = iArr[i28] - iArr2[i28];
                        snake2.removal = z;
                        snake2.reverse = true;
                        return snake2;
                    }
                } else {
                    i9 = 1;
                }
                i6 = iArr2[(i5 + i26) - i9];
                z = z4;
                i7 = i6 - i26;
                while (true) {
                    if (i6 > 0) {
                        break;
                    }
                    break;
                    i6--;
                    i7--;
                    i13 = i8;
                }
                i8 = i13;
                int i282 = i5 + i26;
                iArr2[i282] = i6;
                if (objArr == null) {
                }
                i25 += 2;
                i13 = i8;
                z4 = false;
            }
            i20++;
            i15 = 1;
            i13 = i13;
            z3 = false;
        }
        throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
    }
}
