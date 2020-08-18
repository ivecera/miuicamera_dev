package com.android.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class AssistantCamera extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent.hasExtra(CameraIntentManager.EXTRA_FROM_VOICE_ROOT)) {
            intent.removeExtra(CameraIntentManager.EXTRA_FROM_VOICE_ROOT);
        }
        if (isVoiceInteractionRoot()) {
            intent.putExtra(CameraIntentManager.EXTRA_FROM_VOICE_ROOT, true);
        }
        int hashCode = intent.hashCode();
        intent.putExtra(CameraIntentManager.EXTRA_ASSISTANT_HASH, hashCode);
        KeyKeeper.getInstance().setAssistantHash(hashCode);
        startCamera(getIntent());
        finish();
    }

    public void startCamera(Intent intent) {
        intent.setClass(this, Camera.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
