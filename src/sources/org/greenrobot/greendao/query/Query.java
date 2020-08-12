package org.greenrobot.greendao.query;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;

public class Query<T> extends AbstractQueryWithLimit<T> {
    private final QueryData<T> queryData;

    private static final class QueryData<T2> extends AbstractQueryData<T2, Query<T2>> {
        private final int limitPosition;
        private final int offsetPosition;

        QueryData(AbstractDao<T2, ?> abstractDao, String str, String[] strArr, int i, int i2) {
            super(abstractDao, str, strArr);
            this.limitPosition = i;
            this.offsetPosition = i2;
        }

        /* access modifiers changed from: protected */
        @Override // org.greenrobot.greendao.query.AbstractQueryData
        public Query<T2> createQuery() {
            return new Query<>(this, ((AbstractQueryData) this).dao, ((AbstractQueryData) this).sql, (String[]) ((AbstractQueryData) this).initialValues.clone(), this.limitPosition, this.offsetPosition);
        }
    }

    private Query(QueryData<T> queryData2, AbstractDao<T, ?> abstractDao, String str, String[] strArr, int i, int i2) {
        super(abstractDao, str, strArr, i, i2);
        this.queryData = queryData2;
    }

    static <T2> Query<T2> create(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr, int i, int i2) {
        return (Query) new QueryData(abstractDao, str, AbstractQuery.toStringArray(objArr), i, i2).forCurrentThread();
    }

    public static <T2> Query<T2> internalCreate(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return create(abstractDao, str, objArr, -1, -1);
    }

    public Query<T> forCurrentThread() {
        return (Query) this.queryData.forCurrentThread(this);
    }

    public List<T> list() {
        checkThread();
        return ((AbstractQuery) this).daoAccess.loadAllAndCloseCursor(((AbstractQuery) this).dao.getDatabase().rawQuery(((AbstractQuery) this).sql, ((AbstractQuery) this).parameters));
    }

    public CloseableListIterator<T> listIterator() {
        return listLazyUncached().listIteratorAutoClose();
    }

    public LazyList<T> listLazy() {
        checkThread();
        return new LazyList<>(((AbstractQuery) this).daoAccess, ((AbstractQuery) this).dao.getDatabase().rawQuery(((AbstractQuery) this).sql, ((AbstractQuery) this).parameters), true);
    }

    public LazyList<T> listLazyUncached() {
        checkThread();
        return new LazyList<>(((AbstractQuery) this).daoAccess, ((AbstractQuery) this).dao.getDatabase().rawQuery(((AbstractQuery) this).sql, ((AbstractQuery) this).parameters), false);
    }

    @Override // org.greenrobot.greendao.query.AbstractQueryWithLimit
    public /* bridge */ /* synthetic */ void setLimit(int i) {
        super.setLimit(i);
    }

    @Override // org.greenrobot.greendao.query.AbstractQueryWithLimit
    public /* bridge */ /* synthetic */ void setOffset(int i) {
        super.setOffset(i);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public Query<T> setParameter(int i, Boolean bool) {
        return (Query) super.setParameter(i, bool);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery, org.greenrobot.greendao.query.AbstractQueryWithLimit, org.greenrobot.greendao.query.AbstractQueryWithLimit
    public Query<T> setParameter(int i, Object obj) {
        return (Query) super.setParameter(i, obj);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public Query<T> setParameter(int i, Date date) {
        return (Query) super.setParameter(i, date);
    }

    public T unique() {
        checkThread();
        return ((AbstractQuery) this).daoAccess.loadUniqueAndCloseCursor(((AbstractQuery) this).dao.getDatabase().rawQuery(((AbstractQuery) this).sql, ((AbstractQuery) this).parameters));
    }

    public T uniqueOrThrow() {
        T unique = unique();
        if (unique != null) {
            return unique;
        }
        throw new DaoException("No entity found for query");
    }
}
