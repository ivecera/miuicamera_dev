package com.xiaomi.engine;

public class ResultData {
    private static final String TAG = "ResultData";
    private int mFlawResult;
    private int mResultId;

    public int getFlawResult() {
        return this.mFlawResult;
    }

    public int getResultId() {
        return this.mResultId;
    }

    public void setFlawResult(int i) {
        this.mFlawResult = i;
    }

    public void setResultId(int i) {
        this.mResultId = i;
    }

    public String toString() {
        return "ResultData{ mResultId=" + this.mResultId + ", mFlawResult=" + this.mFlawResult + '}';
    }
}
