package com.android.camera.aiwatermark.chain;

public class PriorityChainFactory {
    public AbstractPriorityChain createPriorityChain(int i) {
        if (i != 0) {
            if (i == 1) {
                return new ChinaPriorityChain();
            }
            if (i == 2) {
                return new IndiaPriorityChain();
            }
        }
        return null;
    }
}
