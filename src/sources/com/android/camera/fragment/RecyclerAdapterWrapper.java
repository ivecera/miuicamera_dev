package com.android.camera.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterWrapper<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_VIEW_TYPE = -2000;
    private static final int HEADER_VIEW_TYPE = -1000;
    private final T mBase;
    private final Class<? extends RecyclerView.ViewHolder> mBaseHolderClass;
    private final List<View> mFooters = new ArrayList();
    private final List<View> mHeaders = new ArrayList();
    private RecyclerView.AdapterDataObserver o;

    public RecyclerAdapterWrapper(T t) {
        this.mBase = t;
        Type genericSuperclass = this.mBase.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            if (type instanceof Class) {
                this.mBaseHolderClass = (Class) type;
            } else {
                this.mBaseHolderClass = RecyclerView.ViewHolder.class;
            }
        } else {
            this.mBaseHolderClass = RecyclerView.ViewHolder.class;
        }
        T t2 = this.mBase;
        AnonymousClass1 r0 = new RecyclerView.AdapterDataObserver() {
            /* class com.android.camera.fragment.RecyclerAdapterWrapper.AnonymousClass1 */

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                RecyclerAdapterWrapper.this.notifyDataSetChanged();
            }

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2) {
                super.onItemRangeChanged(i, i2);
                RecyclerAdapterWrapper recyclerAdapterWrapper = RecyclerAdapterWrapper.this;
                recyclerAdapterWrapper.notifyItemRangeChanged(recyclerAdapterWrapper.getHeaderCount() + i, i2);
            }

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i, int i2) {
                super.onItemRangeInserted(i, i2);
                RecyclerAdapterWrapper recyclerAdapterWrapper = RecyclerAdapterWrapper.this;
                recyclerAdapterWrapper.notifyItemRangeInserted(recyclerAdapterWrapper.getHeaderCount() + i, i2);
            }

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i, int i2) {
                super.onItemRangeRemoved(i, i2);
                RecyclerAdapterWrapper recyclerAdapterWrapper = RecyclerAdapterWrapper.this;
                recyclerAdapterWrapper.notifyItemRangeRemoved(recyclerAdapterWrapper.getHeaderCount() + i, i2);
            }
        };
        this.o = r0;
        t2.registerAdapterDataObserver(r0);
    }

    private boolean isFooter(int i) {
        return i >= FOOTER_VIEW_TYPE && i < this.mFooters.size() + FOOTER_VIEW_TYPE;
    }

    private boolean isHeader(int i) {
        return i >= -1000 && i < this.mHeaders.size() + -1000;
    }

    public void addFooter(@NonNull View view) {
        if (view != null) {
            this.mFooters.add(view);
            return;
        }
        throw new IllegalArgumentException("You can't have a null footer!");
    }

    public void addHeader(@NonNull View view) {
        if (view != null) {
            this.mHeaders.add(view);
            return;
        }
        throw new IllegalArgumentException("You can't have a null header!");
    }

    public void detach() {
        T t;
        RecyclerView.AdapterDataObserver adapterDataObserver = this.o;
        if (adapterDataObserver != null && (t = this.mBase) != null) {
            t.unregisterAdapterDataObserver(adapterDataObserver);
        }
    }

    public View getFooter(int i) {
        if (i < this.mFooters.size()) {
            return this.mFooters.get(i);
        }
        return null;
    }

    public int getFooterCount() {
        return this.mFooters.size();
    }

    public View getHeader(int i) {
        if (i < this.mHeaders.size()) {
            return this.mHeaders.get(i);
        }
        return null;
    }

    public int getHeaderCount() {
        return this.mHeaders.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mHeaders.size() + this.mBase.getItemCount() + this.mFooters.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i < this.mHeaders.size() ? i - 1000 : i < this.mHeaders.size() + this.mBase.getItemCount() ? this.mBase.getItemViewType(i - this.mHeaders.size()) : ((i + FOOTER_VIEW_TYPE) - this.mHeaders.size()) - this.mBase.getItemCount();
    }

    public T getWrappedAdapter() {
        return this.mBase;
    }

    public boolean isContainsFooter(@NonNull View view) {
        if (view != null) {
            return this.mFooters.contains(view);
        }
        throw new IllegalArgumentException("You can't have a null footer!");
    }

    public boolean isContainsHeader(@NonNull View view) {
        if (view != null) {
            return this.mHeaders.contains(view);
        }
        throw new IllegalArgumentException("You can't have a null footer!");
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mBase.onAttachedToRecyclerView(recyclerView);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (i >= this.mHeaders.size() && i < this.mHeaders.size() + this.mBase.getItemCount()) {
            this.mBase.onBindViewHolder(viewHolder, i - this.mHeaders.size());
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (isHeader(i)) {
            return new RecyclerView.ViewHolder(this.mHeaders.get(Math.abs(i + 1000))) {
                /* class com.android.camera.fragment.RecyclerAdapterWrapper.AnonymousClass2 */
            };
        } else if (!isFooter(i)) {
            return this.mBase.onCreateViewHolder(viewGroup, i);
        } else {
            return new RecyclerView.ViewHolder(this.mFooters.get(Math.abs(i + 2000))) {
                /* class com.android.camera.fragment.RecyclerAdapterWrapper.AnonymousClass3 */
            };
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mBase.onDetachedFromRecyclerView(recyclerView);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        return this.mBaseHolderClass.isInstance(viewHolder) ? this.mBase.onFailedToRecycleView((RecyclerView.ViewHolder) this.mBaseHolderClass.cast(viewHolder)) : super.onFailedToRecycleView(viewHolder);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        if (this.mBaseHolderClass.isInstance(viewHolder)) {
            this.mBase.onViewAttachedToWindow((RecyclerView.ViewHolder) this.mBaseHolderClass.cast(viewHolder));
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        if (this.mBaseHolderClass.isInstance(viewHolder)) {
            this.mBase.onViewDetachedFromWindow((RecyclerView.ViewHolder) this.mBaseHolderClass.cast(viewHolder));
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (this.mBaseHolderClass.isInstance(viewHolder)) {
            this.mBase.onViewRecycled((RecyclerView.ViewHolder) this.mBaseHolderClass.cast(viewHolder));
        }
    }

    public void removeAllFooter() {
        this.mFooters.clear();
    }

    public void removeAllHeader() {
        this.mHeaders.clear();
    }

    public void removeFooter(@NonNull View view) {
        if (view != null) {
            this.mFooters.remove(view);
            return;
        }
        throw new IllegalArgumentException("You can't have a null header!");
    }

    public void removeHeader(@NonNull View view) {
        if (view != null) {
            this.mHeaders.remove(view);
            return;
        }
        throw new IllegalArgumentException("You can't have a null header!");
    }
}
