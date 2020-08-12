package com.android.camera.fragment.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.R;

public class ThermalDialogFragment extends BaseDialogFragment {
    public static final String TAG = "ThermalDialog";

    public /* synthetic */ boolean a(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (getActivity() == null) {
            return false;
        }
        getActivity().finish();
        return false;
    }

    @Override // com.android.camera.fragment.dialog.BaseDialogFragment, android.support.v4.app.Fragment, android.support.v4.app.DialogFragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().setOnKeyListener(new k(this));
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_thermal_dialog, viewGroup, false);
        initViewOnTouchListener(inflate);
        ((TextView) inflate.findViewById(R.id.thermal_dialog_content)).setText(getResources().getString(R.string.ccc_temperature_warning_msg, 5));
        return inflate;
    }
}
