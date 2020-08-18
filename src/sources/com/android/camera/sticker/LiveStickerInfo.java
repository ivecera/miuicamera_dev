package com.android.camera.sticker;

import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.network.download.Verifier;
import com.android.camera.network.resource.LiveResource;
import java.io.File;

public class LiveStickerInfo extends LiveResource {
    public int downloadState;
    public String hash;
    public String hint;
    public String hintIcon;
    public String icon;
    public int iconId;
    public String name;
    public String url;

    public LiveStickerInfo() {
        this.downloadState = 0;
    }

    public LiveStickerInfo(String str, String str2, int i) {
        this.downloadState = 0;
        ((LiveResource) this).isLocal = true;
        this.name = str;
        this.iconId = i;
        this.hash = str2;
    }

    public LiveStickerInfo(String str, String str2, String str3) {
        this.downloadState = 0;
        ((LiveResource) this).isLocal = true;
        this.name = str;
        this.icon = "file:///android_asset/live/" + str3;
        this.hash = str2;
    }

    public LiveStickerInfo(String str, String str2, String str3, String str4) {
        this(str, str2, str3);
        this.hint = str4;
    }

    private boolean isLocalExists() {
        return new File(FileUtils.STICKER_RESOURCE_DIR + this.hash).exists();
    }

    public int getDownloadState() {
        if (!((LiveResource) this).isLocal || this.downloadState != 0) {
            int i = this.downloadState;
            if ((i == 0 || i == 2 || i == 4) && isDownloaded()) {
                this.downloadState = 1;
            }
        } else if (isLocalExists()) {
            this.downloadState = 1;
        }
        return this.downloadState;
    }

    public boolean isDownloaded() {
        File file = new File(FileUtils.STICKER_RESOURCE_DIR + this.hash + ".zip");
        if (!file.exists()) {
            return false;
        }
        byte[] hash2 = Verifier.hash(file, "MD5", 32768);
        if (this.hash.length() != hash2.length * 2) {
            return false;
        }
        for (int i = 0; i < hash2.length; i++) {
            int i2 = i * 2;
            if (hash2[i] != ((byte) ((Character.digit(this.hash.charAt(i2), 16) << 4) | Character.digit(this.hash.charAt(i2 + 1), 16)))) {
                return false;
            }
        }
        return true;
    }
}
