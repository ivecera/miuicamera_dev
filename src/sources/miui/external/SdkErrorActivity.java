package miui.external;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import miui.external.SdkConstants;

public class SdkErrorActivity extends Activity implements SdkConstants {
    private DialogInterface.OnClickListener mDismissListener = new DialogInterface.OnClickListener() {
        /* class miui.external.SdkErrorActivity.AnonymousClass1 */

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            SdkErrorActivity.this.finish();
            System.exit(0);
        }
    };
    private String mLanguage;
    private DialogInterface.OnClickListener mUpdateListener = new DialogInterface.OnClickListener() {
        /* class miui.external.SdkErrorActivity.AnonymousClass2 */

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            final Dialog access$000 = SdkErrorActivity.this.createUpdateDialog();
            new SdkDialogFragment(access$000).show(SdkErrorActivity.this.getFragmentManager(), "SdkUpdatePromptDialog");
            new AsyncTask<Void, Void, Boolean>() {
                /* class miui.external.SdkErrorActivity.AnonymousClass2.AnonymousClass1 */

                /* access modifiers changed from: protected */
                public Boolean doInBackground(Void... voidArr) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    return Boolean.valueOf(SdkErrorActivity.this.updateSdk());
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Boolean bool) {
                    access$000.dismiss();
                    new SdkDialogFragment(bool.booleanValue() ? SdkErrorActivity.this.createUpdateSuccessfulDialog() : SdkErrorActivity.this.createUpdateFailedDialog()).show(SdkErrorActivity.this.getFragmentManager(), "SdkUpdateFinishDialog");
                }
            }.execute(new Void[0]);
        }
    };

    /* renamed from: miui.external.SdkErrorActivity$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$miui$external$SdkConstants$SdkError = new int[SdkConstants.SdkError.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            $SwitchMap$miui$external$SdkConstants$SdkError[SdkConstants.SdkError.NO_SDK.ordinal()] = 1;
            $SwitchMap$miui$external$SdkConstants$SdkError[SdkConstants.SdkError.LOW_SDK_VERSION.ordinal()] = 2;
        }
    }

    class SdkDialogFragment extends DialogFragment {
        private Dialog mDialog;

        public SdkDialogFragment(Dialog dialog) {
            this.mDialog = dialog;
        }

        public Dialog onCreateDialog(Bundle bundle) {
            return this.mDialog;
        }
    }

    private Dialog createDoubleActionDialog(String str, String str2, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2) {
        return new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton(17039370, onClickListener).setNegativeButton(17039360, onClickListener2).setIcon(17301543).setCancelable(false).create();
    }

    private Dialog createGenericErrorDialog() {
        String str;
        String str2;
        if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
            str2 = "MIUI SDK发生错误";
            str = "请重新安装MIUI SDK再运行本程序。";
        } else {
            str2 = "MIUI SDK encounter errors";
            str = "Please re-install MIUI SDK and then re-run this application.";
        }
        return createSingleActionDialog(str2, str, this.mDismissListener);
    }

    private Dialog createLowSdkVersionDialog() {
        String str;
        String str2;
        String str3 = "MIUI SDK版本过低";
        if (!supportUpdateSdk()) {
            if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
                str2 = "请先升级MIUI SDK再运行本程序。";
            } else {
                str2 = "Please upgrade MIUI SDK and then re-run this application.";
                str3 = "MIUI SDK too old";
            }
            return createSingleActionDialog(str3, str2, this.mDismissListener);
        }
        if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
            str = "请先升级MIUI SDK再运行本程序。是否现在升级？";
        } else {
            str = "Please upgrade MIUI SDK and then re-run this application. Upgrade now?";
            str3 = "MIUI SDK too old";
        }
        return createDoubleActionDialog(str3, str, this.mUpdateListener, this.mDismissListener);
    }

    private Dialog createNoSdkDialog() {
        String str;
        String str2;
        if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
            str2 = "没有找到MIUI SDK";
            str = "请先安装MIUI SDK再运行本程序。";
        } else {
            str2 = "MIUI SDK not found";
            str = "Please install MIUI SDK and then re-run this application.";
        }
        return createSingleActionDialog(str2, str, this.mDismissListener);
    }

    private Dialog createSingleActionDialog(String str, String str2, DialogInterface.OnClickListener onClickListener) {
        return new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton(17039370, onClickListener).setIcon(17301543).setCancelable(false).create();
    }

    /* access modifiers changed from: private */
    public Dialog createUpdateDialog() {
        String str;
        String str2;
        if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
            str2 = "MIUI SDK正在更新";
            str = "请稍候...";
        } else {
            str2 = "MIUI SDK updating";
            str = "Please wait...";
        }
        return ProgressDialog.show(this, str2, str, true, false);
    }

    /* access modifiers changed from: private */
    public Dialog createUpdateFailedDialog() {
        String str;
        String str2;
        if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
            str2 = "MIUI SDK更新失败";
            str = "请稍后重试。";
        } else {
            str2 = "MIUI SDK update failed";
            str = "Please try it later.";
        }
        return createSingleActionDialog(str2, str, this.mDismissListener);
    }

    /* access modifiers changed from: private */
    public Dialog createUpdateSuccessfulDialog() {
        String str;
        String str2;
        if (Locale.CHINESE.getLanguage().equals(this.mLanguage)) {
            str2 = "MIUI SDK更新完成";
            str = "请重新运行本程序。";
        } else {
            str2 = "MIUI SDK updated";
            str = "Please re-run this application.";
        }
        return createSingleActionDialog(str2, str, this.mDismissListener);
    }

    private boolean supportUpdateSdk() {
        try {
            return ((Boolean) SdkEntranceHelper.getSdkEntrance().getMethod("supportUpdate", Map.class).invoke(null, null)).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean updateSdk() {
        try {
            HashMap hashMap = new HashMap();
            return ((Boolean) SdkEntranceHelper.getSdkEntrance().getMethod("update", Map.class).invoke(null, hashMap)).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        setTheme(16973909);
        super.onCreate(bundle);
        this.mLanguage = Locale.getDefault().getLanguage();
        Intent intent = getIntent();
        SdkConstants.SdkError sdkError = intent != null ? (SdkConstants.SdkError) intent.getSerializableExtra(SdkConstants.SdkError.INTENT_EXTRA_KEY) : null;
        if (sdkError == null) {
            sdkError = SdkConstants.SdkError.GENERIC;
        }
        int i = AnonymousClass3.$SwitchMap$miui$external$SdkConstants$SdkError[sdkError.ordinal()];
        new SdkDialogFragment(i != 1 ? i != 2 ? createGenericErrorDialog() : createLowSdkVersionDialog() : createNoSdkDialog()).show(getFragmentManager(), "SdkErrorPromptDialog");
    }
}
