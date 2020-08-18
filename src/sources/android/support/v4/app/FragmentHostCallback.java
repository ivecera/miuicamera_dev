package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Preconditions;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E> extends FragmentContainer {
    @Nullable
    private final Activity mActivity;
    @NonNull
    private final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    @NonNull
    private final Handler mHandler;
    private final int mWindowAnimations;

    FragmentHostCallback(@Nullable Activity activity, @NonNull Context context, @NonNull Handler handler, int i) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        Preconditions.checkNotNull(context, "context == null");
        this.mContext = context;
        Preconditions.checkNotNull(handler, "handler == null");
        this.mHandler = handler;
        this.mWindowAnimations = i;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FragmentHostCallback(@NonNull Context context, @NonNull Handler handler, int i) {
        this(context instanceof Activity ? (Activity) context : null, context, handler, i);
    }

    FragmentHostCallback(@NonNull FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, fragmentActivity.mHandler, 0);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Activity getActivity() {
        return this.mActivity;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: package-private */
    public FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Handler getHandler() {
        return this.mHandler;
    }

    /* access modifiers changed from: package-private */
    public void onAttachFragment(Fragment fragment) {
    }

    public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // android.support.v4.app.FragmentContainer
    @Nullable
    public View onFindViewById(int i) {
        return null;
    }

    @Nullable
    public abstract E onGetHost();

    @NonNull
    public LayoutInflater onGetLayoutInflater() {
        return LayoutInflater.from(this.mContext);
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    @Override // android.support.v4.app.FragmentContainer
    public boolean onHasView() {
        return true;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return true;
    }

    public boolean onShouldShowRequestPermissionRationale(@NonNull String str) {
        return false;
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i) {
        onStartActivityFromFragment(fragment, intent, i, null);
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i, @Nullable Bundle bundle) {
        if (i == -1) {
            this.mContext.startActivity(intent);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }

    public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        if (i == -1) {
            ActivityCompat.startIntentSenderForResult(this.mActivity, intentSender, i, intent, i2, i3, i4, bundle);
            return;
        }
        throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
    }

    public void onSupportInvalidateOptionsMenu() {
    }
}
