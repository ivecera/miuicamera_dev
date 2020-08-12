package com.android.camera.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils {
    public static void addFragmentWithTag(FragmentActivity fragmentActivity, int i, Fragment fragment, String str) {
        if (!fragmentActivity.isFinishing()) {
            FragmentTransaction beginTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(i, fragment, str);
            try {
                beginTransaction.commitAllowingStateLoss();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void addFragmentWithTag(FragmentManager fragmentManager, int i, Fragment fragment, String str) {
        if (fragmentManager != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.replace(i, fragment, str);
            try {
                beginTransaction.commitAllowingStateLoss();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static Fragment getFragmentByTag(FragmentActivity fragmentActivity, String str) {
        return fragmentActivity.getSupportFragmentManager().findFragmentByTag(str);
    }

    public static Fragment getFragmentByTag(FragmentManager fragmentManager, String str) {
        return fragmentManager.findFragmentByTag(str);
    }

    public static boolean hasFragment(FragmentManager fragmentManager, String str) {
        return fragmentManager.findFragmentByTag(str) != null;
    }

    public static boolean removeFragmentByTag(FragmentActivity fragmentActivity, String str) {
        Fragment findFragmentByTag;
        if (fragmentActivity.isFinishing() || str == null || (findFragmentByTag = fragmentActivity.getSupportFragmentManager().findFragmentByTag(str)) == null) {
            return false;
        }
        FragmentTransaction beginTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        beginTransaction.remove(findFragmentByTag);
        try {
            beginTransaction.commitAllowingStateLoss();
            return true;
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public static boolean removeFragmentByTag(FragmentManager fragmentManager, String str) {
        Fragment findFragmentByTag;
        if (fragmentManager == null || str == null || (findFragmentByTag = fragmentManager.findFragmentByTag(str)) == null) {
            return false;
        }
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.remove(findFragmentByTag);
        try {
            beginTransaction.commitAllowingStateLoss();
            return true;
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return true;
        }
    }
}
