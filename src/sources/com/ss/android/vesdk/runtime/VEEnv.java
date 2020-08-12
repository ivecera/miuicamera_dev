package com.ss.android.vesdk.runtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.bef.effectsdk.EffectSDKUtils;
import com.ss.android.vesdk.runtime.persistence.VESP;
import java.io.File;

public class VEEnv {
    private String mDetectModelsDir;
    private String mWorkspace;

    @NonNull
    public String getEffectModelResourceDirPath() {
        if (TextUtils.isEmpty(this.mDetectModelsDir)) {
            this.mDetectModelsDir = (String) VESP.getInstance().get(VESP.KEY_MODELS_DIR_SP_KEY, "");
        }
        return this.mDetectModelsDir;
    }

    @NonNull
    public String getWorkspace() {
        return this.mWorkspace;
    }

    @Deprecated
    public void prepareModels(@NonNull Context context) throws Throwable {
        String str = this.mWorkspace;
        if (str != null) {
            File file = new File(str, "models");
            if (!file.exists()) {
                file.mkdirs();
            }
            this.mDetectModelsDir = file.getAbsolutePath();
            EffectSDKUtils.flushAlgorithmModelFiles(context, this.mDetectModelsDir);
        }
    }

    @NonNull
    public void setDetectModelsDir(String str) {
        this.mDetectModelsDir = str;
        VESP.getInstance().put(VESP.KEY_MODELS_DIR_SP_KEY, this.mDetectModelsDir, true);
    }

    public void setWorkspace(@NonNull String str) {
        this.mWorkspace = str;
    }
}
