package android.support.v13.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import java.util.Arrays;

@Deprecated
public class FragmentCompat {
    static final FragmentCompatImpl IMPL;
    private static PermissionCompatDelegate sDelegate;

    @RequiresApi(15)
    static class FragmentCompatApi15Impl extends FragmentCompatBaseImpl {
        FragmentCompatApi15Impl() {
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl, android.support.v13.app.FragmentCompat.FragmentCompatBaseImpl
        public void setUserVisibleHint(Fragment fragment, boolean z) {
            fragment.setUserVisibleHint(z);
        }
    }

    @RequiresApi(23)
    static class FragmentCompatApi23Impl extends FragmentCompatApi15Impl {
        FragmentCompatApi23Impl() {
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl, android.support.v13.app.FragmentCompat.FragmentCompatBaseImpl
        public void requestPermissions(Fragment fragment, String[] strArr, int i) {
            fragment.requestPermissions(strArr, i);
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl, android.support.v13.app.FragmentCompat.FragmentCompatBaseImpl
        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
            return fragment.shouldShowRequestPermissionRationale(str);
        }
    }

    @RequiresApi(24)
    static class FragmentCompatApi24Impl extends FragmentCompatApi23Impl {
        FragmentCompatApi24Impl() {
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl, android.support.v13.app.FragmentCompat.FragmentCompatBaseImpl, android.support.v13.app.FragmentCompat.FragmentCompatApi15Impl
        public void setUserVisibleHint(Fragment fragment, boolean z) {
            fragment.setUserVisibleHint(z);
        }
    }

    static class FragmentCompatBaseImpl implements FragmentCompatImpl {
        FragmentCompatBaseImpl() {
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl
        public void requestPermissions(final Fragment fragment, final String[] strArr, final int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                /* class android.support.v13.app.FragmentCompat.FragmentCompatBaseImpl.AnonymousClass1 */

                public void run() {
                    int[] iArr = new int[strArr.length];
                    Activity activity = fragment.getActivity();
                    if (activity != null) {
                        PackageManager packageManager = activity.getPackageManager();
                        String packageName = activity.getPackageName();
                        int length = strArr.length;
                        for (int i = 0; i < length; i++) {
                            iArr[i] = packageManager.checkPermission(strArr[i], packageName);
                        }
                    } else {
                        Arrays.fill(iArr, -1);
                    }
                    ((OnRequestPermissionsResultCallback) fragment).onRequestPermissionsResult(i, strArr, iArr);
                }
            });
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl
        public void setUserVisibleHint(Fragment fragment, boolean z) {
        }

        @Override // android.support.v13.app.FragmentCompat.FragmentCompatImpl
        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
            return false;
        }
    }

    interface FragmentCompatImpl {
        void requestPermissions(Fragment fragment, String[] strArr, int i);

        void setUserVisibleHint(Fragment fragment, boolean z);

        boolean shouldShowRequestPermissionRationale(Fragment fragment, String str);
    }

    @Deprecated
    public interface OnRequestPermissionsResultCallback {
        @Deprecated
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    @Deprecated
    public interface PermissionCompatDelegate {
        @Deprecated
        boolean requestPermissions(Fragment fragment, String[] strArr, int i);
    }

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 24) {
            IMPL = new FragmentCompatApi24Impl();
        } else if (i >= 23) {
            IMPL = new FragmentCompatApi23Impl();
        } else if (i >= 15) {
            IMPL = new FragmentCompatApi15Impl();
        } else {
            IMPL = new FragmentCompatBaseImpl();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Deprecated
    public static PermissionCompatDelegate getPermissionCompatDelegate() {
        return sDelegate;
    }

    @Deprecated
    public static void requestPermissions(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
        PermissionCompatDelegate permissionCompatDelegate = sDelegate;
        if (permissionCompatDelegate == null || !permissionCompatDelegate.requestPermissions(fragment, strArr, i)) {
            IMPL.requestPermissions(fragment, strArr, i);
        }
    }

    @Deprecated
    public static void setMenuVisibility(Fragment fragment, boolean z) {
        fragment.setMenuVisibility(z);
    }

    @Deprecated
    public static void setPermissionCompatDelegate(PermissionCompatDelegate permissionCompatDelegate) {
        sDelegate = permissionCompatDelegate;
    }

    @Deprecated
    public static void setUserVisibleHint(Fragment fragment, boolean z) {
        IMPL.setUserVisibleHint(fragment, z);
    }

    @Deprecated
    public static boolean shouldShowRequestPermissionRationale(@NonNull Fragment fragment, @NonNull String str) {
        return IMPL.shouldShowRequestPermissionRationale(fragment, str);
    }
}
