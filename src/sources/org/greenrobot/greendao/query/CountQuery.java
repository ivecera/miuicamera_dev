package org.greenrobot.greendao.query;

import android.database.Cursor;
import java.util.Date;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;

public class CountQuery<T> extends AbstractQuery<T> {
    private final QueryData<T> queryData;

    private static final class QueryData<T2> extends AbstractQueryData<T2, CountQuery<T2>> {
        private QueryData(AbstractDao<T2, ?> abstractDao, String str, String[] strArr) {
            super(abstractDao, str, strArr);
        }

        /* access modifiers changed from: protected */
        @Override // org.greenrobot.greendao.query.AbstractQueryData
        public CountQuery<T2> createQuery() {
            return new CountQuery<>(this, ((AbstractQueryData) this).dao, ((AbstractQueryData) this).sql, (String[]) ((AbstractQueryData) this).initialValues.clone());
        }
    }

    private CountQuery(QueryData<T> queryData2, AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        super(abstractDao, str, strArr);
        this.queryData = queryData2;
    }

    static <T2> CountQuery<T2> create(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return (CountQuery) new QueryData(abstractDao, str, AbstractQuery.toStringArray(objArr)).forCurrentThread();
    }

    public long count() {
        checkThread();
        Cursor rawQuery = ((AbstractQuery) this).dao.getDatabase().rawQuery(((AbstractQuery) this).sql, ((AbstractQuery) this).parameters);
        try {
            if (!rawQuery.moveToNext()) {
                throw new DaoException("No result for count");
            } else if (!rawQuery.isLast()) {
                throw new DaoException("Unexpected row count: " + rawQuery.getCount());
            } else if (rawQuery.getColumnCount() == 1) {
                return rawQuery.getLong(0);
            } else {
                throw new DaoException("Unexpected column count: " + rawQuery.getColumnCount());
            }
        } finally {
            rawQuery.close();
        }
    }

    public CountQuery<T> forCurrentThread() {
        return (CountQuery) this.queryData.forCurrentThread(this);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public CountQuery<T> setParameter(int i, Boolean bool) {
        return (CountQuery) super.setParameter(i, bool);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public CountQuery<T> setParameter(int i, Object obj) {
        super.setParameter(i, obj);
        return this;
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public CountQuery<T> setParameter(int i, Date date) {
        return (CountQuery) super.setParameter(i, date);
    }
}
