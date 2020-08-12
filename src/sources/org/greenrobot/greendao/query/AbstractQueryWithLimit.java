package org.greenrobot.greendao.query;

import org.greenrobot.greendao.AbstractDao;

abstract class AbstractQueryWithLimit<T> extends AbstractQuery<T> {
    protected final int limitPosition;
    protected final int offsetPosition;

    protected AbstractQueryWithLimit(AbstractDao<T, ?> abstractDao, String str, String[] strArr, int i, int i2) {
        super(abstractDao, str, strArr);
        this.limitPosition = i;
        this.offsetPosition = i2;
    }

    public void setLimit(int i) {
        checkThread();
        int i2 = this.limitPosition;
        if (i2 != -1) {
            ((AbstractQuery) this).parameters[i2] = Integer.toString(i);
            return;
        }
        throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
    }

    public void setOffset(int i) {
        checkThread();
        int i2 = this.offsetPosition;
        if (i2 != -1) {
            ((AbstractQuery) this).parameters[i2] = Integer.toString(i);
            return;
        }
        throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public AbstractQueryWithLimit<T> setParameter(int i, Object obj) {
        if (i < 0 || !(i == this.limitPosition || i == this.offsetPosition)) {
            super.setParameter(i, obj);
            return this;
        }
        throw new IllegalArgumentException("Illegal parameter index: " + i);
    }
}
