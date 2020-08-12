package com.android.camera.network.resource;

import com.ss.android.ugc.effectmanager.common.EffectConstants;

public class LiveResource {
    public String id;
    public boolean isLocal;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LiveResource{id=");
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.isLocal ? "local" : EffectConstants.CHANNEL_ONLINE);
        sb.append('}');
        return sb.toString();
    }
}
