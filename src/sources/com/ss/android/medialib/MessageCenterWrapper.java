package com.ss.android.medialib;

import com.bef.effectsdk.message.MessageCenter;
import java.util.ArrayList;
import java.util.List;

public class MessageCenterWrapper implements MessageCenter.Listener {
    private static MessageCenterWrapper INSTANCE;
    private List<MessageCenter.Listener> listeners = new ArrayList();
    private int mCount = 0;

    private MessageCenterWrapper() {
    }

    public static MessageCenterWrapper getInstance() {
        if (INSTANCE == null) {
            synchronized (MessageCenterWrapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MessageCenterWrapper();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void addListener(MessageCenter.Listener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        return;
     */
    public synchronized void destroy() {
        if (this.mCount > 0) {
            this.mCount--;
            if (this.mCount == 0) {
                MessageCenter.destroy();
            }
        }
    }

    public synchronized void init() {
        if (this.mCount == 0) {
            MessageCenter.init();
            MessageCenter.setListener(this);
        }
        this.mCount++;
    }

    @Override // com.bef.effectsdk.message.MessageCenter.Listener
    public void onMessageReceived(int i, int i2, int i3, String str) {
        synchronized (this) {
            for (MessageCenter.Listener listener : this.listeners) {
                listener.onMessageReceived(i, i2, i3, str);
            }
        }
    }

    public synchronized void removeListener(MessageCenter.Listener listener) {
        this.listeners.remove(listener);
    }
}
