package com.android.camera.protocol;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import com.android.camera.protocol.ModeProtocol;
import java.util.concurrent.ConcurrentHashMap;

public class ModeCoordinatorImpl implements ModeProtocol.ModeCoordinator {
    private static ModeCoordinatorImpl sInstance;
    private SparseArray<ModeProtocol.BaseProtocol> featureLocalMap;
    private int mHolderKey;
    private ConcurrentHashMap<Integer, ModeProtocol.BaseProtocol> protocolMap = new ConcurrentHashMap<>();

    public static void create(int i) {
        getInstance();
        sInstance.mHolderKey = i;
    }

    @Deprecated
    public static void destroyAll(int i) {
        ModeCoordinatorImpl modeCoordinatorImpl = sInstance;
        if (modeCoordinatorImpl != null && modeCoordinatorImpl.mHolderKey == i) {
            modeCoordinatorImpl.destroyWorkspace();
            sInstance = null;
        }
    }

    private void destroyWorkspace() {
        this.protocolMap.clear();
    }

    public static void forceDestroy() {
        ModeCoordinatorImpl modeCoordinatorImpl = sInstance;
        if (modeCoordinatorImpl != null) {
            modeCoordinatorImpl.destroyWorkspace();
            sInstance = null;
        }
    }

    public static ModeCoordinatorImpl getInstance() {
        if (sInstance == null) {
            synchronized (ModeCoordinatorImpl.class) {
                if (sInstance == null) {
                    sInstance = new ModeCoordinatorImpl();
                }
            }
        }
        return sInstance;
    }

    public static boolean isAlive(int i) {
        ModeCoordinatorImpl modeCoordinatorImpl = sInstance;
        return (modeCoordinatorImpl == null || modeCoordinatorImpl.mHolderKey != i) ? false : true;
    }

    private <P extends ModeProtocol.BaseProtocol> P nullCatcher(Class<P> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeCoordinator
    public <P extends ModeProtocol.BaseProtocol> void attachProtocol(int i, @Nullable P p) {
        this.protocolMap.put(Integer.valueOf(i), p);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeCoordinator
    public <P extends ModeProtocol.BaseProtocol> void detachProtocol(int i, P p) {
        if (getAttachProtocol(i) == p) {
            this.protocolMap.remove(Integer.valueOf(i));
        }
    }

    public int getActiveProtocolSize() {
        return this.protocolMap.size();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeCoordinator
    public <P extends ModeProtocol.BaseProtocol> P getAttachProtocol(int i) {
        return this.protocolMap.get(Integer.valueOf(i));
    }
}
