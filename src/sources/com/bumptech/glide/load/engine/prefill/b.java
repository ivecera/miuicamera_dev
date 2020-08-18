package com.bumptech.glide.load.engine.prefill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: PreFillQueue */
final class b {
    private final Map<c, Integer> Dp;
    private final List<c> Ep;
    private int Fp;
    private int Gp;

    public b(Map<c, Integer> map) {
        this.Dp = map;
        this.Ep = new ArrayList(map.keySet());
        for (Integer num : map.values()) {
            this.Fp += num.intValue();
        }
    }

    public int getSize() {
        return this.Fp;
    }

    public boolean isEmpty() {
        return this.Fp == 0;
    }

    public c remove() {
        c cVar = this.Ep.get(this.Gp);
        Integer num = this.Dp.get(cVar);
        if (num.intValue() == 1) {
            this.Dp.remove(cVar);
            this.Ep.remove(this.Gp);
        } else {
            this.Dp.put(cVar, Integer.valueOf(num.intValue() - 1));
        }
        this.Fp--;
        this.Gp = this.Ep.isEmpty() ? 0 : (this.Gp + 1) % this.Ep.size();
        return cVar;
    }
}
