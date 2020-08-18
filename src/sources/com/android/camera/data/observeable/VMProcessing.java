package com.android.camera.data.observeable;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.MainThread;
import com.android.camera.data.observeable.RxData;
import io.reactivex.functions.Consumer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class VMProcessing extends VMBase {
    public static final int EXECUTE_COMBINE = 7;
    public static final int EXECUTE_CONCAT = 3;
    public static final int PREVIEW_ERROR = 4;
    public static final int PREVIEW_PAUSE = 5;
    public static final int PREVIEW_PLAYING = 6;
    public static final int RECORDING_DONE = 2;
    public static final int RECORDING_INCHOATE = 1;
    public static final int SAVE_COMPLETE = 8;
    public static final int SAVE_ERROR = 9;
    public RxData<Integer> mRxProcessingState = new RxData<>(1);
    public RxData<String> mRxRecordingState;
    private List<String> mTempVideoList;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VmpState {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.observeable.VMBase
    public boolean achieveEndOfCycle() {
        return this.mRxProcessingState.get().intValue() == 8 || this.mRxProcessingState.get().intValue() == 9;
    }

    public int getCurrentState() {
        return this.mRxProcessingState.get().intValue();
    }

    public List<String> getTempVideoList() {
        if (this.mTempVideoList == null) {
            this.mTempVideoList = new ArrayList();
        }
        return this.mTempVideoList;
    }

    public void postState(int i) {
    }

    public void reset() {
        List<String> list = this.mTempVideoList;
        if (list != null) {
            list.clear();
        }
        rollbackData();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.observeable.VMBase
    public void rollbackData() {
        this.mRxProcessingState.setSilently(1);
    }

    public void startObservable(LifecycleOwner lifecycleOwner, Consumer<RxData.DataWrap<Integer>> consumer) {
        this.mRxProcessingState.observable(lifecycleOwner).subscribe(consumer);
    }

    @MainThread
    public void updateState(int i) {
        this.mRxProcessingState.set(Integer.valueOf(i));
        judge();
    }
}
