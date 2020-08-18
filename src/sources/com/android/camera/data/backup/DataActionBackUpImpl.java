package com.android.camera.data.backup;

import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.FilterInfo;

public class DataActionBackUpImpl implements DataBackUp {
    private SparseArray<SparseArray<SimpleArrayMap>> mBackupArrays;

    @Override // com.android.camera.data.backup.DataBackUp
    public void backupRunning(DataItemRunning dataItemRunning, int i, int i2, boolean z) {
        if (this.mBackupArrays == null) {
            this.mBackupArrays = new SparseArray<>();
        }
        SparseArray<SimpleArrayMap> sparseArray = this.mBackupArrays.get(i);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
        }
        sparseArray.put(i2, dataItemRunning.cloneValues());
        this.mBackupArrays.put(i, sparseArray);
        if (z) {
            dataItemRunning.clearArrayMap();
        }
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public void clearBackUp() {
        SparseArray<SparseArray<SimpleArrayMap>> sparseArray = this.mBackupArrays;
        if (sparseArray != null) {
            sparseArray.clear();
        }
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public String getBackupFilter(int i, int i2) {
        SimpleArrayMap backupRunning = getBackupRunning(DataRepository.dataItemGlobal().getDataBackUpKey(i), i2);
        if (backupRunning == null) {
            return String.valueOf(FilterInfo.FILTER_ID_NONE);
        }
        String str = (String) backupRunning.get("pref_camera_shader_coloreffect_key");
        return str == null ? String.valueOf(FilterInfo.FILTER_ID_NONE) : str;
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public SimpleArrayMap getBackupRunning(int i, int i2) {
        SparseArray<SimpleArrayMap> sparseArray;
        SparseArray<SparseArray<SimpleArrayMap>> sparseArray2 = this.mBackupArrays;
        if (sparseArray2 == null || (sparseArray = sparseArray2.get(i)) == null) {
            return null;
        }
        return sparseArray.get(i2);
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public boolean getBackupSwitchState(int i, String str, int i2) {
        Object obj;
        SimpleArrayMap backupRunning = getBackupRunning(DataRepository.dataItemGlobal().getDataBackUpKey(i), i2);
        if (backupRunning == null || (obj = backupRunning.get(str)) == null) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public boolean isLastVideoFastMotion() {
        Boolean bool;
        SimpleArrayMap backupRunning = getBackupRunning(DataRepository.dataItemGlobal().getDataBackUpKey(169), 0);
        if (backupRunning == null || (bool = (Boolean) backupRunning.get("pref_video_speed_fast_key")) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public void removeOtherVideoMode() {
        if (this.mBackupArrays != null) {
            this.mBackupArrays.remove(DataRepository.dataItemGlobal().getDataBackUpKey(169));
        }
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public void revertRunning(DataItemRunning dataItemRunning, int i, int i2) {
        SparseArray<SimpleArrayMap> sparseArray;
        dataItemRunning.clearArrayMap();
        SparseArray<SparseArray<SimpleArrayMap>> sparseArray2 = this.mBackupArrays;
        if (sparseArray2 != null && (sparseArray = sparseArray2.get(i)) != null && sparseArray.get(i2) != null) {
            dataItemRunning.restoreArrayMap(sparseArray.get(i2));
        }
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public <P extends DataProvider.ProviderEvent> void startBackup(P p, int i) {
    }
}
