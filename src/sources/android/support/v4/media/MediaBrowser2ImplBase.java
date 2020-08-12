package android.support.v4.media;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowser2;
import android.util.Log;
import java.util.concurrent.Executor;

class MediaBrowser2ImplBase extends MediaController2ImplBase implements MediaBrowser2.SupportLibraryImpl {
    MediaBrowser2ImplBase(Context context, MediaController2 mediaController2, SessionToken2 sessionToken2, Executor executor, MediaBrowser2.BrowserCallback browserCallback) {
        super(context, mediaController2, sessionToken2, executor, browserCallback);
    }

    @Override // android.support.v4.media.MediaController2ImplBase, android.support.v4.media.MediaController2.SupportLibraryImpl
    public MediaBrowser2.BrowserCallback getCallback() {
        return (MediaBrowser2.BrowserCallback) super.getCallback();
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void getChildren(String str, int i, int i2, Bundle bundle) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(29);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.getChildren(((MediaController2ImplBase) this).mControllerStub, str, i, i2, bundle);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void getItem(String str) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(30);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.getItem(((MediaController2ImplBase) this).mControllerStub, str);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void getLibraryRoot(Bundle bundle) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(31);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.getLibraryRoot(((MediaController2ImplBase) this).mControllerStub, bundle);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void getSearchResult(String str, int i, int i2, Bundle bundle) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(32);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.getSearchResult(((MediaController2ImplBase) this).mControllerStub, str, i, i2, bundle);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void search(String str, Bundle bundle) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(33);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.search(((MediaController2ImplBase) this).mControllerStub, str, bundle);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void subscribe(String str, Bundle bundle) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(34);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.subscribe(((MediaController2ImplBase) this).mControllerStub, str, bundle);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }

    @Override // android.support.v4.media.MediaBrowser2.SupportLibraryImpl
    public void unsubscribe(String str) {
        IMediaSession2 sessionInterfaceIfAble = getSessionInterfaceIfAble(35);
        if (sessionInterfaceIfAble != null) {
            try {
                sessionInterfaceIfAble.unsubscribe(((MediaController2ImplBase) this).mControllerStub, str);
            } catch (RemoteException e2) {
                Log.w("MC2ImplBase", "Cannot connect to the service or the session is gone", e2);
            }
        }
    }
}
