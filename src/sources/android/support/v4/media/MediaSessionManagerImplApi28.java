package android.support.v4.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaSessionManager;

@RequiresApi(28)
class MediaSessionManagerImplApi28 extends MediaSessionManagerImplApi21 {
    MediaSessionManager mObject;

    static final class RemoteUserInfo implements MediaSessionManager.RemoteUserInfoImpl {
        MediaSessionManager.RemoteUserInfo mObject;

        RemoteUserInfo(String str, int i, int i2) {
            this.mObject = new MediaSessionManager.RemoteUserInfo(str, i, i2);
        }

        @Override // android.support.v4.media.MediaSessionManager.RemoteUserInfoImpl
        public String getPackageName() {
            return this.mObject.getPackageName();
        }

        @Override // android.support.v4.media.MediaSessionManager.RemoteUserInfoImpl
        public int getPid() {
            return this.mObject.getPid();
        }

        @Override // android.support.v4.media.MediaSessionManager.RemoteUserInfoImpl
        public int getUid() {
            return this.mObject.getUid();
        }
    }

    MediaSessionManagerImplApi28(Context context) {
        super(context);
        this.mObject = (android.media.session.MediaSessionManager) context.getSystemService("media_session");
    }

    @Override // android.support.v4.media.MediaSessionManagerImplBase, android.support.v4.media.MediaSessionManager.MediaSessionManagerImpl, android.support.v4.media.MediaSessionManagerImplApi21
    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        if (remoteUserInfoImpl instanceof RemoteUserInfo) {
            return this.mObject.isTrustedForMediaControl(((RemoteUserInfo) remoteUserInfoImpl).mObject);
        }
        return false;
    }
}
