package com.android.camera.data.provider;

import android.util.SparseArray;
import com.android.camera.data.cloud.DataCloud;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.extra.DataItemLive;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.DataItemRunning;
import com.mi.config.a;

public class DataProviderMgr {
    private DataProvider mDataProvider;

    private final class DataProviderImpl implements DataProvider {
        private DataCloud.CloudManager mDataCloudManager;
        private DataItemGlobal mDataGlobal = new DataItemGlobal(this.mDataItemFeature);
        private SparseArray<DataItemConfig> mDataItemConfigs;
        private a mDataItemFeature = new a();
        private DataItemLive mDataItemLive;
        private DataItemRunning mDataRunning;

        public DataProviderImpl(DataCloud.CloudManager cloudManager) {
            this.mDataCloudManager = cloudManager;
            this.mDataGlobal.injectCloud(cloudManager.provideDataCloudGlobal());
            this.mDataItemConfigs = new SparseArray<>(4);
            this.mDataRunning = new DataItemRunning();
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataConfig() {
            return dataConfig(dataGlobal().getCurrentCameraId(), dataGlobal().getIntentType());
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataConfig(int i) {
            return dataConfig(i, dataGlobal().getIntentType());
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataConfig(int i, int i2) {
            int provideLocalId = DataItemConfig.provideLocalId(i, i2);
            DataItemConfig dataItemConfig = this.mDataItemConfigs.get(provideLocalId);
            if (dataItemConfig != null) {
                return dataItemConfig;
            }
            DataItemConfig dataItemConfig2 = new DataItemConfig(i, i2);
            dataItemConfig2.injectCloud(this.mDataCloudManager.provideDataCloudConfig(i));
            this.mDataItemConfigs.put(provideLocalId, dataItemConfig2);
            return dataItemConfig2;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public a dataFeature() {
            return this.mDataItemFeature;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemGlobal dataGlobal() {
            return this.mDataGlobal;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemLive dataLive() {
            if (this.mDataItemLive == null) {
                this.mDataItemLive = new DataItemLive();
            }
            return this.mDataItemLive;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataNormalConfig() {
            return dataConfig(dataGlobal().getCurrentCameraId(), 0);
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemRunning dataRunning() {
            return this.mDataRunning;
        }
    }

    public DataProviderMgr(DataCloud.CloudManager cloudManager) {
        this.mDataProvider = new DataProviderImpl(cloudManager);
    }

    public DataProvider provider() {
        return this.mDataProvider;
    }
}
