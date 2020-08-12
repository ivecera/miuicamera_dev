package com.ss.android.ttve.utils;

import com.ss.android.ttve.monitor.MonitorUtils;
import com.ss.android.ugc.effectmanager.common.listener.IMonitorService;
import org.json.JSONObject;

public class EffectPlatformMonitor implements IMonitorService {
    @Override // com.ss.android.ugc.effectmanager.common.listener.IMonitorService
    public void monitorCommonLog(String str, String str2, JSONObject jSONObject) {
    }

    @Override // com.ss.android.ugc.effectmanager.common.listener.IMonitorService
    public void monitorCommonLog(String str, JSONObject jSONObject) {
    }

    @Override // com.ss.android.ugc.effectmanager.common.listener.IMonitorService
    public void monitorDirectOnTimer(String str, String str2, float f2) {
    }

    @Override // com.ss.android.ugc.effectmanager.common.listener.IMonitorService
    public void monitorOnTimer(String str, String str2, float f2) {
    }

    @Override // com.ss.android.ugc.effectmanager.common.listener.IMonitorService
    public void monitorStatusAndDuration(String str, int i, JSONObject jSONObject, JSONObject jSONObject2) {
        MonitorUtils.monitorStatusAndDuration(str, i, jSONObject, jSONObject2);
    }

    @Override // com.ss.android.ugc.effectmanager.common.listener.IMonitorService
    public void monitorStatusRate(String str, int i, JSONObject jSONObject) {
        MonitorUtils.monitorStatusRate(str, i, jSONObject);
    }
}
