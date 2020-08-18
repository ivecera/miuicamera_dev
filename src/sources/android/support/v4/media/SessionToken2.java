package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;

public final class SessionToken2 {
    static final String KEY_PACKAGE_NAME = "android.media.token.package_name";
    static final String KEY_SERVICE_NAME = "android.media.token.service_name";
    static final String KEY_SESSION_BINDER = "android.media.token.session_binder";
    static final String KEY_SESSION_ID = "android.media.token.session_id";
    static final String KEY_TOKEN_LEGACY = "android.media.token.LEGACY";
    static final String KEY_TYPE = "android.media.token.type";
    static final String KEY_UID = "android.media.token.uid";
    private static final String TAG = "SessionToken2";
    public static final int TYPE_LIBRARY_SERVICE = 2;
    public static final int TYPE_SESSION = 0;
    static final int TYPE_SESSION_LEGACY = 100;
    public static final int TYPE_SESSION_SERVICE = 1;
    static final int UID_UNKNOWN = -1;
    private static final long WAIT_TIME_MS_FOR_SESSION_READY = 300;
    private final SupportLibraryImpl mImpl;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface OnSessionToken2CreatedListener {
        void onSessionToken2Created(MediaSessionCompat.Token token, SessionToken2 sessionToken2);
    }

    interface SupportLibraryImpl {
        Object getBinder();

        @Nullable
        ComponentName getComponentName();

        @NonNull
        String getPackageName();

        @Nullable
        String getServiceName();

        String getSessionId();

        int getType();

        int getUid();

        Bundle toBundle();
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface TokenType {
    }

    public SessionToken2(@NonNull Context context, @NonNull ComponentName componentName) {
        this.mImpl = new SessionToken2ImplBase(context, componentName);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    SessionToken2(SupportLibraryImpl supportLibraryImpl) {
        this.mImpl = supportLibraryImpl;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void createSessionToken2(@NonNull final Context context, @NonNull final MediaSessionCompat.Token token, @NonNull Executor executor, @NonNull final OnSessionToken2CreatedListener onSessionToken2CreatedListener) {
        if (context == null) {
            throw new IllegalArgumentException("context shouldn't be null");
        } else if (token == null) {
            throw new IllegalArgumentException("token shouldn't be null");
        } else if (executor == null) {
            throw new IllegalArgumentException("executor shouldn't be null");
        } else if (onSessionToken2CreatedListener != null) {
            executor.execute(new Runnable() {
                /* class android.support.v4.media.SessionToken2.AnonymousClass1 */

                public void run() {
                    try {
                        final MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(context, token);
                        mediaControllerCompat.registerCallback(new MediaControllerCompat.Callback() {
                            /* class android.support.v4.media.SessionToken2.AnonymousClass1.AnonymousClass1 */

                            @Override // android.support.v4.media.session.MediaControllerCompat.Callback
                            public void onSessionReady() {
                                synchronized (onSessionToken2CreatedListener) {
                                    onSessionToken2CreatedListener.onSessionToken2Created(token, mediaControllerCompat.getSessionToken2());
                                    onSessionToken2CreatedListener.notify();
                                }
                            }
                        });
                        if (mediaControllerCompat.isSessionReady()) {
                            onSessionToken2CreatedListener.onSessionToken2Created(token, mediaControllerCompat.getSessionToken2());
                        }
                        synchronized (onSessionToken2CreatedListener) {
                            onSessionToken2CreatedListener.wait(SessionToken2.WAIT_TIME_MS_FOR_SESSION_READY);
                            if (!mediaControllerCompat.isSessionReady()) {
                                SessionToken2 sessionToken2 = new SessionToken2(new SessionToken2ImplLegacy(token));
                                token.setSessionToken2(sessionToken2);
                                onSessionToken2CreatedListener.onSessionToken2Created(token, sessionToken2);
                            }
                        }
                    } catch (RemoteException e2) {
                        Log.e(SessionToken2.TAG, "Failed to create session token2.", e2);
                    } catch (InterruptedException e3) {
                        Log.e(SessionToken2.TAG, "Failed to create session token2.", e3);
                    }
                }
            });
        } else {
            throw new IllegalArgumentException("listener shouldn't be null");
        }
    }

    public static SessionToken2 fromBundle(@NonNull Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getInt(KEY_TYPE, -1) == 100 ? new SessionToken2(SessionToken2ImplLegacy.fromBundle(bundle)) : new SessionToken2(SessionToken2ImplBase.fromBundle(bundle));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static String getSessionId(ResolveInfo resolveInfo) {
        ServiceInfo serviceInfo;
        if (resolveInfo == null || (serviceInfo = resolveInfo.serviceInfo) == null) {
            return null;
        }
        return serviceInfo.metaData == null ? "" : resolveInfo.serviceInfo.metaData.getString(MediaSessionService2.SERVICE_META_DATA, "");
    }

    private static String getSessionIdFromService(PackageManager packageManager, String str, ComponentName componentName) {
        ServiceInfo serviceInfo;
        Intent intent = new Intent(str);
        intent.setPackage(componentName.getPackageName());
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 128);
        if (queryIntentServices == null) {
            return null;
        }
        for (int i = 0; i < queryIntentServices.size(); i++) {
            ResolveInfo resolveInfo = queryIntentServices.get(i);
            if (resolveInfo != null && (serviceInfo = resolveInfo.serviceInfo) != null && TextUtils.equals(serviceInfo.name, componentName.getClassName())) {
                return getSessionId(resolveInfo);
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionToken2)) {
            return false;
        }
        return this.mImpl.equals(((SessionToken2) obj).mImpl);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Object getBinder() {
        return this.mImpl.getBinder();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ComponentName getComponentName() {
        return this.mImpl.getComponentName();
    }

    public String getId() {
        return this.mImpl.getSessionId();
    }

    @NonNull
    public String getPackageName() {
        return this.mImpl.getPackageName();
    }

    @Nullable
    public String getServiceName() {
        return this.mImpl.getServiceName();
    }

    public int getType() {
        return this.mImpl.getType();
    }

    public int getUid() {
        return this.mImpl.getUid();
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isLegacySession() {
        return this.mImpl instanceof SessionToken2ImplLegacy;
    }

    public Bundle toBundle() {
        return this.mImpl.toBundle();
    }

    public String toString() {
        return this.mImpl.toString();
    }
}
