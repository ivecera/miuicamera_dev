package com.android.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import androidx.annotation.VisibleForTesting;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher extends Thread {
    private final Cache mCache;
    private final ResponseDelivery mDelivery;
    private final Network mNetwork;
    private final BlockingQueue<Request<?>> mQueue;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<Request<?>> blockingQueue, Network network, Cache cache, ResponseDelivery responseDelivery) {
        this.mQueue = blockingQueue;
        this.mNetwork = network;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
    }

    @TargetApi(14)
    private void addTrafficStatsTag(Request<?> request) {
        if (Build.VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
        }
    }

    private void parseAndDeliverNetworkError(Request<?> request, VolleyError volleyError) {
        this.mDelivery.postError(request, request.parseNetworkError(volleyError));
    }

    private void processRequest() throws InterruptedException {
        processRequest(this.mQueue.take());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void processRequest(Request<?> request) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        request.sendEvent(3);
        try {
            request.addMarker("network-queue-take");
            if (request.isCanceled()) {
                request.finish("network-discard-cancelled");
                request.notifyListenerResponseNotUsable();
                request.sendEvent(4);
                return;
            }
            addTrafficStatsTag(request);
            NetworkResponse performRequest = this.mNetwork.performRequest(request);
            request.addMarker("network-http-complete");
            if (!performRequest.notModified || !request.hasHadResponseDelivered()) {
                Response<?> parseNetworkResponse = request.parseNetworkResponse(performRequest);
                request.addMarker("network-parse-complete");
                if (request.shouldCache() && parseNetworkResponse.cacheEntry != null) {
                    this.mCache.put(request.getCacheKey(), parseNetworkResponse.cacheEntry);
                    request.addMarker("network-cache-written");
                }
                request.markDelivered();
                this.mDelivery.postResponse(request, parseNetworkResponse);
                request.notifyListenerResponseReceived(parseNetworkResponse);
                request.sendEvent(4);
                return;
            }
            request.finish("not-modified");
            request.notifyListenerResponseNotUsable();
            request.sendEvent(4);
        } catch (VolleyError e2) {
            e2.setNetworkTimeMs(SystemClock.elapsedRealtime() - elapsedRealtime);
            parseAndDeliverNetworkError(request, e2);
            request.notifyListenerResponseNotUsable();
        } catch (Exception e3) {
            VolleyLog.e(e3, "Unhandled exception %s", e3.toString());
            VolleyError volleyError = new VolleyError(e3);
            volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.mDelivery.postError(request, volleyError);
            request.notifyListenerResponseNotUsable();
        } catch (Throwable th) {
            request.sendEvent(4);
            throw th;
        }
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException unused) {
                if (this.mQuit) {
                    Thread.currentThread().interrupt();
                    return;
                }
                VolleyLog.e("Ignoring spurious interrupt of NetworkDispatcher thread; use quit() to terminate it", new Object[0]);
            }
        }
    }
}
