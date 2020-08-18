package com.ss.android.ugc.effectmanager.link;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.ss.android.ugc.effectmanager.common.WeakHandler;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.utils.LogUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.link.model.blackRoom.BlackRoom;
import com.ss.android.ugc.effectmanager.link.model.configuration.LinkSelectorConfiguration;
import com.ss.android.ugc.effectmanager.link.model.host.Host;
import com.ss.android.ugc.effectmanager.link.task.result.HostListStatusUpdateTaskResult;
import com.ss.android.ugc.effectmanager.link.task.task.HostListStatusUpdateTask;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkSelector implements WeakHandler.IHandler {
    private static final String TAG = "HostSelector";
    private static final String TASK_FLAG = "SpeedMeasure";
    private volatile boolean isRun = false;
    private String mBestHostUrl;
    private BlackRoom mBlackRoom;
    private Context mContext;
    private EffectContext mEffectContext;
    private Handler mHandler = new WeakHandler(this);
    /* access modifiers changed from: private */
    public boolean mIsEnableLinkSelector;
    /* access modifiers changed from: private */
    public boolean mIsLazy;
    private boolean mIsNetworkChangeMonitor;
    /* access modifiers changed from: private */
    public List<Host> mOptedHosts = new ArrayList();
    private List<Host> mOriginHosts = new ArrayList();
    private NetworkChangeReceiver mReceiver;
    private int mRepeatTime;
    private String mSpeedApi;
    private int mSpeedTimeOut;

    class NetworkChangeReceiver extends BroadcastReceiver {
        NetworkChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (LinkSelector.this.mIsEnableLinkSelector) {
                LogUtils.e(LinkSelector.TAG, "network state change");
                if (!LinkSelector.this.mOptedHosts.isEmpty() || !LinkSelector.this.mIsLazy) {
                    LinkSelector.this.startOptHosts();
                }
            }
        }
    }

    public LinkSelector(EffectContext effectContext) {
        this.mOptedHosts.clear();
        this.mBlackRoom = new BlackRoom();
        this.mEffectContext = effectContext;
        this.mReceiver = null;
    }

    private void lockToBlackRoom(String str) {
        try {
            URI uri = new URL(str.replace(" ", "%20")).toURI();
            Host host = new Host(uri.getHost(), uri.getScheme());
            for (Host host2 : this.mOptedHosts) {
                if (host.hostEquals(host2)) {
                    this.mBlackRoom.lock(host2);
                    updateBestHost();
                    return;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void setNetworkChangeOpt() {
        if (this.mIsNetworkChangeMonitor && this.mReceiver == null && isLinkSelectorAvailable()) {
            this.mReceiver = new NetworkChangeReceiver();
            this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public void destroy() {
        Context context;
        NetworkChangeReceiver networkChangeReceiver = this.mReceiver;
        if (networkChangeReceiver != null && (context = this.mContext) != null) {
            context.unregisterReceiver(networkChangeReceiver);
        }
    }

    public String getBestHostUrl() {
        return this.mBestHostUrl;
    }

    public Context getContext() {
        return this.mContext;
    }

    public List<Host> getOriginHosts() {
        return this.mOriginHosts;
    }

    public int getRepeatTime() {
        return this.mRepeatTime;
    }

    public String getSpeedApi() {
        return this.mSpeedApi;
    }

    public int getSpeedTimeOut() {
        return this.mSpeedTimeOut;
    }

    @Override // com.ss.android.ugc.effectmanager.common.WeakHandler.IHandler
    public void handleMsg(Message message) {
        if (message.what == 31) {
            Object obj = message.obj;
            if (obj instanceof HostListStatusUpdateTaskResult) {
                HostListStatusUpdateTaskResult hostListStatusUpdateTaskResult = (HostListStatusUpdateTaskResult) obj;
                ExceptionResult exceptionResult = hostListStatusUpdateTaskResult.getExceptionResult();
                List<Host> hosts = hostListStatusUpdateTaskResult.getHosts();
                if (exceptionResult == null) {
                    LogUtils.d(TAG, "on sort done = " + hosts.size() + " selector:" + this + " thread:" + Thread.currentThread());
                    this.mOptedHosts.clear();
                    this.mOptedHosts.addAll(hosts);
                    updateBestHost();
                }
                this.isRun = false;
            }
        }
    }

    public boolean isLazy() {
        return this.mIsLazy;
    }

    public boolean isLinkSelectorAvailable() {
        return this.mIsEnableLinkSelector && this.mOriginHosts.size() > 1;
    }

    public boolean isNetworkAvailable() {
        if (getContext() == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        } catch (Exception unused) {
            return false;
        }
    }

    public void linkSelectorConfigure(@NonNull LinkSelectorConfiguration linkSelectorConfiguration) {
        this.mSpeedTimeOut = linkSelectorConfiguration.getSpeedTimeOut();
        this.mRepeatTime = linkSelectorConfiguration.getRepeatTime();
        this.mIsEnableLinkSelector = linkSelectorConfiguration.isEnableLinkSelector();
        this.mContext = linkSelectorConfiguration.getContext();
        this.mSpeedApi = linkSelectorConfiguration.getSpeedApi();
        this.mOriginHosts.clear();
        this.mOriginHosts.addAll(linkSelectorConfiguration.getOriginHosts());
        this.mBestHostUrl = this.mOriginHosts.get(0).getItemName();
        this.mIsNetworkChangeMonitor = linkSelectorConfiguration.isNetworkChangeMonitor();
        this.mIsLazy = linkSelectorConfiguration.isLazy();
        LogUtils.e(TAG, "link selector configure");
        setNetworkChangeOpt();
    }

    public void onApiError(String str) {
        if (isNetworkAvailable()) {
            LogUtils.e(TAG, "on link api error:" + str);
            lockToBlackRoom(str);
        }
    }

    public void onApiSuccess(String str) {
        LogUtils.d(TAG, "on link api success:" + str);
    }

    public void startOptHosts() {
        if (isLinkSelectorAvailable() && !this.isRun && isNetworkAvailable()) {
            LogUtils.e(TAG, "hosts measure start");
            this.mEffectContext.getEffectConfiguration().getTaskManager().commit(new HostListStatusUpdateTask(this, this.mHandler, TASK_FLAG));
            this.isRun = true;
        }
    }

    public void updateBestHost() {
        if (!isLinkSelectorAvailable()) {
            this.mBestHostUrl = getOriginHosts().get(0).getItemName();
            return;
        }
        Host host = null;
        int i = 0;
        while (true) {
            if (i >= this.mOptedHosts.size()) {
                break;
            }
            Host host2 = this.mOptedHosts.get(i);
            if (this.mBlackRoom.checkHostAvailable(host2)) {
                host = host2;
                break;
            }
            i++;
        }
        if (host == null) {
            host = getOriginHosts().get(0);
            startOptHosts();
        }
        this.mBestHostUrl = host.getItemName();
    }

    public void updateHosts(List<Host> list, boolean z) {
        if (z) {
            this.mOriginHosts.clear();
            this.mOriginHosts.addAll(list);
            return;
        }
        this.mOriginHosts.addAll(list);
    }
}
