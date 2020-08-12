package com.android.camera;

import com.android.camera.effect.EffectController;
import java.util.HashMap;
import java.util.Map;

public class EffectChangedListenerController {
    private static Map<Integer, EffectController.EffectChangedListener> mEffectChangedListenerMap = new HashMap();
    private static int mHoldKey;

    public static void addEffectChangedListener(EffectController.EffectChangedListener effectChangedListener) {
        mEffectChangedListenerMap.put(Integer.valueOf(mHoldKey), effectChangedListener);
    }

    public static void removeEffectChangedListenerMap(int i) {
        EffectController.getInstance().removeChangeListener(mEffectChangedListenerMap.remove(Integer.valueOf(i)));
        EffectController.releaseInstance();
    }

    public static void setHoldKey(int i) {
        mHoldKey = i;
    }
}
