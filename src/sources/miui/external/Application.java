package miui.external;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import miui.external.SdkConstants;

public class Application extends android.app.Application implements SdkConstants {
    private static final String PACKAGE_NAME = "com.miui.core";
    private ApplicationDelegate mApplicationDelegate;
    private boolean mInitialized;
    private boolean mStarted;

    public Application() {
        if (loadSdk() && initializeSdk()) {
            this.mInitialized = true;
        }
    }

    private void handleGenericError(Throwable th) {
        while (th != null && th.getCause() != null) {
            if (!(th instanceof InvocationTargetException)) {
                if (!(th instanceof ExceptionInInitializerError)) {
                    break;
                }
                th = th.getCause();
            } else {
                th = th.getCause();
            }
        }
        Log.e(SdkConstants.LOG_TAG, "MIUI SDK encounter errors, please contact miuisdk@xiaomi.com for support.", th);
        SdkErrorInstrumentation.handleSdkError(SdkConstants.SdkError.GENERIC);
    }

    private void handleUnknownError(String str, int i) {
        Log.e(SdkConstants.LOG_TAG, "MIUI SDK encounter errors, please contact miuisdk@xiaomi.com for support. phase: " + str + " code: " + i);
        SdkErrorInstrumentation.handleSdkError(SdkConstants.SdkError.GENERIC);
    }

    private boolean initializeSdk() {
        return true;
    }

    private boolean loadSdk() {
        return true;
    }

    private boolean startSdk() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        if (this.mInitialized && startSdk()) {
            this.mApplicationDelegate = onCreateApplicationDelegate();
            ApplicationDelegate applicationDelegate = this.mApplicationDelegate;
            if (applicationDelegate != null) {
                applicationDelegate.attach(this);
            }
            this.mStarted = true;
        }
    }

    public final ApplicationDelegate getApplicationDelegate() {
        return this.mApplicationDelegate;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        ApplicationDelegate applicationDelegate = this.mApplicationDelegate;
        if (applicationDelegate != null) {
            applicationDelegate.onConfigurationChanged(configuration);
        } else {
            superOnConfigurationChanged(configuration);
        }
    }

    public final void onCreate() {
        if (this.mStarted) {
            ApplicationDelegate applicationDelegate = this.mApplicationDelegate;
            if (applicationDelegate != null) {
                applicationDelegate.onCreate();
            } else {
                superOnCreate();
            }
        }
    }

    public ApplicationDelegate onCreateApplicationDelegate() {
        return null;
    }

    public final void onLowMemory() {
        ApplicationDelegate applicationDelegate = this.mApplicationDelegate;
        if (applicationDelegate != null) {
            applicationDelegate.onLowMemory();
        } else {
            superOnLowMemory();
        }
    }

    public final void onTerminate() {
        ApplicationDelegate applicationDelegate = this.mApplicationDelegate;
        if (applicationDelegate != null) {
            applicationDelegate.onTerminate();
        } else {
            superOnTerminate();
        }
    }

    public final void onTrimMemory(int i) {
        ApplicationDelegate applicationDelegate = this.mApplicationDelegate;
        if (applicationDelegate != null) {
            applicationDelegate.onTrimMemory(i);
        } else {
            superOnTrimMemory(i);
        }
    }

    /* access modifiers changed from: package-private */
    public final void superOnConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: package-private */
    public final void superOnCreate() {
        super.onCreate();
    }

    /* access modifiers changed from: package-private */
    public final void superOnLowMemory() {
        super.onLowMemory();
    }

    /* access modifiers changed from: package-private */
    public final void superOnTerminate() {
        super.onTerminate();
    }

    /* access modifiers changed from: package-private */
    public final void superOnTrimMemory(int i) {
        super.onTrimMemory(i);
    }
}
