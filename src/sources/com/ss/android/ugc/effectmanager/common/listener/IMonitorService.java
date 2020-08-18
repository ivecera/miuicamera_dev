package com.ss.android.ugc.effectmanager.common.listener;

import org.json.JSONObject;

public interface IMonitorService {
    void monitorCommonLog(String str, String str2, JSONObject jSONObject);

    void monitorCommonLog(String str, JSONObject jSONObject);

    void monitorDirectOnTimer(String str, String str2, float f2);

    void monitorOnTimer(String str, String str2, float f2);

    void monitorStatusAndDuration(String str, int i, JSONObject jSONObject, JSONObject jSONObject2);

    void monitorStatusRate(String str, int i, JSONObject jSONObject);
}
