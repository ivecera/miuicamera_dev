package android.support.v4.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaSessionManager;

@RequiresApi(21)
class MediaSessionManagerImplApi21 extends MediaSessionManagerImplBase {
    MediaSessionManagerImplApi21(Context context) {
        super(context);
        ((MediaSessionManagerImplBase) this).mContext = context;
    }

    private boolean hasMediaControlPermission(@NonNull MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        return getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", remoteUserInfoImpl.getPid(), remoteUserInfoImpl.getUid()) == 0;
    }

    @Override // android.support.v4.media.MediaSessionManagerImplBase, android.support.v4.media.MediaSessionManager.MediaSessionManagerImpl
    public boolean isTrustedForMediaControl(@NonNull MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        return hasMediaControlPermission(remoteUserInfoImpl) || super.isTrustedForMediaControl(remoteUserInfoImpl);
    }
}
