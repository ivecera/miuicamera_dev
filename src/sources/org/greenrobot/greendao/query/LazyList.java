package org.greenrobot.greendao.query;

import android.database.Cursor;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.InternalQueryDaoAccess;

public class LazyList<E> implements List<E>, Closeable {
    private final Cursor cursor;
    private final InternalQueryDaoAccess<E> daoAccess;
    private final List<E> entities;
    private volatile int loadedCount;
    private final ReentrantLock lock;
    /* access modifiers changed from: private */
    public final int size;

    protected class LazyIterator implements CloseableListIterator<E> {
        private final boolean closeWhenDone;
        private int index;

        public LazyIterator(int i, boolean z) {
            this.index = i;
            this.closeWhenDone = z;
        }

        @Override // java.util.ListIterator
        public void add(E e2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            LazyList.this.close();
        }

        public boolean hasNext() {
            return this.index < LazyList.this.size;
        }

        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public E next() {
            if (this.index < LazyList.this.size) {
                E e2 = LazyList.this.get(this.index);
                this.index++;
                if (this.index == LazyList.this.size && this.closeWhenDone) {
                    close();
                }
                return e2;
            }
            throw new NoSuchElementException();
        }

        public int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public E previous() {
            int i = this.index;
            if (i > 0) {
                this.index = i - 1;
                return LazyList.this.get(this.index);
            }
            throw new NoSuchElementException();
        }

        public int previousIndex() {
            return this.index - 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        public void set(E e2) {
            throw new UnsupportedOperationException();
        }
    }

    LazyList(InternalQueryDaoAccess<E> internalQueryDaoAccess, Cursor cursor2, boolean z) {
        this.cursor = cursor2;
        this.daoAccess = internalQueryDaoAccess;
        this.size = cursor2.getCount();
        if (z) {
            this.entities = new ArrayList(this.size);
            for (int i = 0; i < this.size; i++) {
                this.entities.add(null);
            }
        } else {
            this.entities = null;
        }
        if (this.size == 0) {
            cursor2.close();
        }
        this.lock = new ReentrantLock();
    }

    @Override // java.util.List
    public void add(int i, E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void checkCached() {
        if (this.entities == null) {
            throw new DaoException("This operation only works with cached lazy lists");
        }
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.cursor.close();
    }

    public boolean contains(Object obj) {
        loadRemaining();
        return this.entities.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        loadRemaining();
        return this.entities.containsAll(collection);
    }

    @Override // java.util.List
    public E get(int i) {
        List<E> list = this.entities;
        if (list != null) {
            E e2 = list.get(i);
            if (e2 == null) {
                this.lock.lock();
                try {
                    e2 = this.entities.get(i);
                    if (e2 == null) {
                        e2 = loadEntity(i);
                        this.entities.set(i, e2);
                        this.loadedCount++;
                        if (this.loadedCount == this.size) {
                            this.cursor.close();
                        }
                    }
                } finally {
                    this.lock.unlock();
                }
            }
            return e2;
        }
        this.lock.lock();
        try {
            return loadEntity(i);
        } finally {
            this.lock.unlock();
        }
    }

    public int getLoadedCount() {
        return this.loadedCount;
    }

    public int indexOf(Object obj) {
        loadRemaining();
        return this.entities.indexOf(obj);
    }

    public boolean isClosed() {
        return this.cursor.isClosed();
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isLoadedCompletely() {
        return this.loadedCount == this.size;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new LazyIterator(0, false);
    }

    public int lastIndexOf(Object obj) {
        loadRemaining();
        return this.entities.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i) {
        return new LazyIterator(i, false);
    }

    @Override // java.util.List
    public CloseableListIterator<E> listIterator() {
        return new LazyIterator(0, false);
    }

    public CloseableListIterator<E> listIteratorAutoClose() {
        return new LazyIterator(0, true);
    }

    /* access modifiers changed from: protected */
    public E loadEntity(int i) {
        if (this.cursor.moveToPosition(i)) {
            E loadCurrent = this.daoAccess.loadCurrent(this.cursor, 0, true);
            if (loadCurrent != null) {
                return loadCurrent;
            }
            throw new DaoException("Loading of entity failed (null) at position " + i);
        }
        throw new DaoException("Could not move to cursor location " + i);
    }

    public void loadRemaining() {
        checkCached();
        int size2 = this.entities.size();
        for (int i = 0; i < size2; i++) {
            get(i);
        }
    }

    public E peek(int i) {
        List<E> list = this.entities;
        if (list != null) {
            return list.get(i);
        }
        return null;
    }

    @Override // java.util.List
    public E remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public E set(int i, E e2) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.size;
    }

    @Override // java.util.List
    public List<E> subList(int i, int i2) {
        checkCached();
        for (int i3 = i; i3 < i2; i3++) {
            get(i3);
        }
        return this.entities.subList(i, i2);
    }

    public Object[] toArray() {
        loadRemaining();
        return this.entities.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        loadRemaining();
        return this.entities.toArray(tArr);
    }
}
