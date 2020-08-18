package org.greenrobot.greendao.query;

import android.database.Cursor;
import java.util.Date;
import org.greenrobot.greendao.AbstractDao;

public class CursorQuery<T> extends AbstractQueryWithLimit<T> {
    private final QueryData<T> queryData;

    private static final class QueryData<T2> extends AbstractQueryData<T2, CursorQuery<T2>> {
        private final int limitPosition;
        private final int offsetPosition;

        QueryData(AbstractDao abstractDao, String str, String[] strArr, int i, int i2) {
            super(abstractDao, str, strArr);
            this.limitPosition = i;
            this.offsetPosition = i2;
        }

        /* access modifiers changed from: protected */
        @Override // org.greenrobot.greendao.query.AbstractQueryData
        public CursorQuery<T2> createQuery() {
            return new CursorQuery<>(this, ((AbstractQueryData) this).dao, ((AbstractQueryData) this).sql, (String[]) ((AbstractQueryData) this).initialValues.clone(), this.limitPosition, this.offsetPosition);
        }
    }

    private CursorQuery(QueryData<T> queryData2, AbstractDao<T, ?> abstractDao, String str, String[] strArr, int i, int i2) {
        super(abstractDao, str, strArr, i, i2);
        this.queryData = queryData2;
    }

    static <T2> CursorQuery<T2> create(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr, int i, int i2) {
        return (CursorQuery) new QueryData(abstractDao, str, AbstractQuery.toStringArray(objArr), i, i2).forCurrentThread();
    }

    public static <T2> CursorQuery<T2> internalCreate(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return create(abstractDao, str, objArr, -1, -1);
    }

    public CursorQuery forCurrentThread() {
        return (CursorQuery) this.queryData.forCurrentThread(this);
    }

    public Cursor query() {
        checkThread();
        return ((AbstractQuery) this).dao.getDatabase().rawQuery(((AbstractQuery) this).sql, ((AbstractQuery) this).parameters);
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
    public CursorQuery<T> setParameter(int i, Boolean bool) {
        return (CursorQuery) super.setParameter(i, bool);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery, org.greenrobot.greendao.query.AbstractQueryWithLimit, org.greenrobot.greendao.query.AbstractQueryWithLimit
    public CursorQuery<T> setParameter(int i, Object obj) {
        return (CursorQuery) super.setParameter(i, obj);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public CursorQuery<T> setParameter(int i, Date date) {
        return (CursorQuery) super.setParameter(i, date);
    }
}
