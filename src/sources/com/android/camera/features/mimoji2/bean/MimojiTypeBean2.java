package com.android.camera.features.mimoji2.bean;

import com.android.camera.features.mimoji2.widget.autoselectview.SelectItemBean;
import com.arcsoft.avatar.AvatarConfig;

public class MimojiTypeBean2 extends SelectItemBean {
    private AvatarConfig.ASAvatarConfigType mASAvatarConfigType;

    public AvatarConfig.ASAvatarConfigType getASAvatarConfigType() {
        return this.mASAvatarConfigType;
    }

    public void setASAvatarConfigType(AvatarConfig.ASAvatarConfigType aSAvatarConfigType) {
        this.mASAvatarConfigType = aSAvatarConfigType;
    }
}
