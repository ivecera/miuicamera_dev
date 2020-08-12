package com.android.camera.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.camera.Camera;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class HibernationFragment extends DialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener, ModeProtocol.HandleBackTrace {
    public static final String TAG = "Hibernation";

    private void adjustViewSize(View view) {
        int i = getResources().getDisplayMetrics().widthPixels;
        int i2 = getResources().getDisplayMetrics().heightPixels;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.width != i || layoutParams.height != i2) {
            layoutParams.width = i;
            layoutParams.height = i2;
        }
    }

    private void resumeMode() {
        ((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).removeBackStack(this);
        if (isAdded()) {
            Camera camera = (Camera) getActivity();
            if (!camera.isActivityPaused() && !camera.isSwitchingModule()) {
                camera.onAwaken();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public final boolean canProvide() {
        return isAdded();
    }

    @Override // android.support.v4.app.Fragment, android.support.v4.app.DialogFragment
    public void onActivityCreated(Bundle bundle) {
        Window window = getDialog().getWindow();
        if (DataRepository.dataItemFeature().c_0x44()) {
            window.requestFeature(1);
        }
        super.onActivityCreated(bundle);
        window.setLayout(-1, -1);
        if (Util.isContentViewExtendToTopEdges()) {
            CompatibilityUtils.setCutoutModeShortEdges(window);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        dismissAllowingStateLoss();
        resumeMode();
        return i == 1 || i == 2;
    }

    @Override // android.support.v4.app.DialogFragment
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        resumeMode();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.hibernation_cover) {
            onBackEvent(5);
        }
    }

    @Override // android.support.v4.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog onCreateDialog = super.onCreateDialog(bundle);
        onCreateDialog.getWindow().setGravity(48);
        return onCreateDialog;
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_hibernation, viewGroup, false);
        inflate.findViewById(R.id.hibernation_cover).setOnClickListener(this);
        adjustViewSize(inflate.findViewById(R.id.hibernation_layout));
        return inflate;
    }

    @Override // android.support.v4.app.Fragment, android.support.v4.app.DialogFragment
    public void onDestroyView() {
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack != null) {
            backStack.removeBackStack(this);
        }
        super.onDestroyView();
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1) {
            return false;
        }
        if (i != 4 && i != 259) {
            return false;
        }
        onBackEvent(5);
        return true;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(this);
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack != null) {
            backStack.addInBackStack(this);
        }
    }
}
