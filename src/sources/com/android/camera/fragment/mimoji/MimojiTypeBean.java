package com.android.camera.fragment.mimoji;

import com.android.camera.features.mimoji2.widget.autoselectview.SelectItemBean;
import com.arcsoft.avatar.AvatarConfig;

public class MimojiTypeBean extends SelectItemBean {
    private AvatarConfig.ASAvatarConfigType ASAvatarConfigType;

    public AvatarConfig.ASAvatarConfigType getASAvatarConfigType() {
        return this.ASAvatarConfigType;
    }

    public void setASAvatarConfigType(AvatarConfig.ASAvatarConfigType aSAvatarConfigType) {
        this.ASAvatarConfigType = aSAvatarConfigType;
    }
}
