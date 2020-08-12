package com.android.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.camera.ui.Rotatable;
import com.android.camera.ui.RotateLayout;

public class RotateDialogController implements Rotatable {
    private static final long ANIM_DURATION = 150;
    private static final String TAG = "RotateDialogController";
    private Activity mActivity;
    private View mDialogRootLayout;
    private Animation mFadeInAnim;
    private Animation mFadeOutAnim;
    private int mLayoutResourceID;
    private RotateLayout mRotateDialog;
    private TextView mRotateDialogButton1;
    private TextView mRotateDialogButton2;
    private View mRotateDialogButtonLayout;
    private ProgressBar mRotateDialogSpinner;
    private TextView mRotateDialogText;
    private TextView mRotateDialogTitle;
    private View mRotateDialogTitleLayout;

    public RotateDialogController(Activity activity, int i) {
        this.mActivity = activity;
        this.mLayoutResourceID = i == 0 ? R.layout.rotate_dialog : i;
    }

    static /* synthetic */ boolean c(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return (i == 25 || i == 24) ? true : false;
    }

    private void fadeInDialog() {
        this.mDialogRootLayout.startAnimation(this.mFadeInAnim);
        this.mDialogRootLayout.setVisibility(0);
    }

    private void fadeOutDialog() {
        this.mDialogRootLayout.startAnimation(this.mFadeOutAnim);
        this.mDialogRootLayout.setVisibility(8);
    }

    private void inflateDialogLayout() {
        if (this.mDialogRootLayout == null) {
            View inflate = this.mActivity.getLayoutInflater().inflate(this.mLayoutResourceID, (ViewGroup) this.mActivity.getWindow().getDecorView());
            this.mDialogRootLayout = inflate.findViewById(R.id.rotate_dialog_root_layout);
            this.mRotateDialog = (RotateLayout) inflate.findViewById(R.id.rotate_dialog_layout);
            this.mRotateDialogTitleLayout = inflate.findViewById(R.id.rotate_dialog_title_layout);
            this.mRotateDialogButtonLayout = inflate.findViewById(R.id.rotate_dialog_button_layout);
            this.mRotateDialogTitle = (TextView) inflate.findViewById(R.id.rotate_dialog_title);
            this.mRotateDialogSpinner = (ProgressBar) inflate.findViewById(R.id.rotate_dialog_spinner);
            this.mRotateDialogText = (TextView) inflate.findViewById(R.id.rotate_dialog_text);
            this.mRotateDialogButton1 = (Button) inflate.findViewById(R.id.rotate_dialog_button1);
            this.mRotateDialogButton2 = (Button) inflate.findViewById(R.id.rotate_dialog_button2);
            this.mFadeInAnim = AnimationUtils.loadAnimation(this.mActivity, 17432576);
            this.mFadeOutAnim = AnimationUtils.loadAnimation(this.mActivity, 17432577);
            this.mFadeInAnim.setDuration(ANIM_DURATION);
            this.mFadeOutAnim.setDuration(ANIM_DURATION);
        }
    }

    public static AlertDialog showSystemAlertDialog(Context context, String str, CharSequence charSequence, String str2, final Runnable runnable, String str3, final Runnable runnable2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str);
        builder.setMessage(charSequence);
        builder.setCancelable(false);
        builder.setOnKeyListener(h.INSTANCE);
        if (str2 != null) {
            builder.setPositiveButton(str2, new DialogInterface.OnClickListener() {
                /* class com.android.camera.RotateDialogController.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Runnable runnable = runnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
        }
        if (str3 != null) {
            builder.setNegativeButton(str3, new DialogInterface.OnClickListener() {
                /* class com.android.camera.RotateDialogController.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Runnable runnable = runnable2;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
        }
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    public static AlertDialog showSystemChoiceDialog(final Context context, String str, String str2, String str3, String str4, final Runnable runnable, final Runnable runnable2) {
        AnonymousClass5 r0 = new DialogInterface.OnKeyListener() {
            /* class com.android.camera.RotateDialogController.AnonymousClass5 */

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return (i == 25 || i == 24) ? true : false;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.v6_choice_alertdialog, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.alert_declaration)).setText(str2);
        String string = context.getResources().getString(R.string.view_privacy_policy);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() {
            /* class com.android.camera.RotateDialogController.AnonymousClass6 */

            public void onClick(View view) {
                ActivityLauncher.launchPrivacyPolicyWebpage(context);
            }

            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(context.getResources().getColor(17170432));
            }
        }, 0, string.length(), 33);
        TextView textView = (TextView) inflate.findViewById(R.id.view_privacy_policy);
        textView.setClickable(true);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if (Util.isAccessible() == 1) {
            textView.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.RotateDialogController.AnonymousClass7 */

                public void onClick(View view) {
                    ActivityLauncher.launchPrivacyPolicyWebpage(context);
                }
            });
        }
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.alert_declaration_checkbox);
        checkBox.setText(str3);
        builder.setOnKeyListener(r0);
        builder.setTitle(str);
        builder.setCancelable(false);
        builder.setView(inflate);
        if (str4 != null) {
            builder.setPositiveButton(str4, new DialogInterface.OnClickListener() {
                /* class com.android.camera.RotateDialogController.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (checkBox.isChecked() == 1) {
                        Runnable runnable = runnable;
                        if (runnable != null) {
                            runnable.run();
                            return;
                        }
                        return;
                    }
                    Runnable runnable2 = runnable2;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
        }
        AlertDialog create = builder.create();
        WindowManager.LayoutParams attributes = create.getWindow().getAttributes();
        attributes.type = 99;
        create.getWindow().setAttributes(attributes);
        create.show();
        return create;
    }

    public void dismissDialog() {
        View view = this.mDialogRootLayout;
        if (view != null && view.getVisibility() != 8) {
            fadeOutDialog();
        }
    }

    public void resetRotateDialog() {
        inflateDialogLayout();
        this.mRotateDialogTitleLayout.setVisibility(8);
        this.mRotateDialogSpinner.setVisibility(8);
        this.mRotateDialogButton1.setVisibility(8);
        this.mRotateDialogButton2.setVisibility(8);
        this.mRotateDialogButtonLayout.setVisibility(8);
    }

    @Override // com.android.camera.ui.Rotatable
    public void setOrientation(int i, boolean z) {
        inflateDialogLayout();
        this.mRotateDialog.setOrientation(i, z);
    }

    public void showAlertDialog(String str, String str2, String str3, final Runnable runnable, String str4, final Runnable runnable2) {
        resetRotateDialog();
        if (str != null) {
            this.mRotateDialogTitle.setText(str);
            this.mRotateDialogTitleLayout.setVisibility(0);
        }
        this.mRotateDialogText.setText(str2);
        if (str3 != null) {
            this.mRotateDialogButton1.setText(str3);
            this.mRotateDialogButton1.setContentDescription(str3);
            this.mRotateDialogButton1.setVisibility(0);
            this.mRotateDialogButton1.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.RotateDialogController.AnonymousClass1 */

                public void onClick(View view) {
                    Runnable runnable = runnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                    RotateDialogController.this.dismissDialog();
                }
            });
            this.mRotateDialogButtonLayout.setVisibility(0);
        }
        if (str4 != null) {
            this.mRotateDialogButton2.setText(str4);
            this.mRotateDialogButton2.setContentDescription(str4);
            this.mRotateDialogButton2.setVisibility(0);
            this.mRotateDialogButton2.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.RotateDialogController.AnonymousClass2 */

                public void onClick(View view) {
                    Runnable runnable = runnable2;
                    if (runnable != null) {
                        runnable.run();
                    }
                    RotateDialogController.this.dismissDialog();
                }
            });
            this.mRotateDialogButtonLayout.setVisibility(0);
        }
        fadeInDialog();
    }

    public void showWaitingDialog(String str) {
        resetRotateDialog();
        this.mRotateDialogText.setText(str);
        this.mRotateDialogSpinner.setVisibility(0);
        fadeInDialog();
    }
}
