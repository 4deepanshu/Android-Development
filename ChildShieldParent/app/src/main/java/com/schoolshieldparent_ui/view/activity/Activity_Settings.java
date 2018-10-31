package com.schoolshieldparent_ui.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Settings extends AppCompatActivity {
    @BindView(R.id.checkBoxNotification)
    CheckBox checkBox;
    private boolean readyToPurchase = false;

    private static Activity_Settings instance;

    //    private BillingProcessor bp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        instance = this;
        setNotifiationFromPref();
        updateStatusOfPayment();
    }


    public static Activity_Settings getInstance() {
        return instance;
    }

    private void setNotifiationFromPref() {
        if (SharedPref.getNotifiationStatus(getApplicationContext())) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    @OnClick(R.id.layoutLogout)
    public void logout() {
        showAlertDialog(this, getString(R.string.logoutConfirm));
    }

    @OnClick(R.id.imageButton_Back)
    public void doBack() {
        finish();
        overridePendingTransition(0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

    }

    @OnClick(R.id.checkBoxNotificationLayout)
    public void setNotificationToggle() {
        if (checkBox.isChecked()) {
            WebServiceResult.AddNotification(SharedPref.getString(MyApplication.PARENT_ID), "0");
        } else {
            WebServiceResult.AddNotification(SharedPref.getString(MyApplication.PARENT_ID), "1");
        }
    }

    public void showAlertDialog(Activity activity, String message) {
        final Dialog dialogAlert = new Dialog(activity);
        dialogAlert.setCancelable(false);
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAlert.setContentView(R.layout.view_nochild_alert);
        TextView_Regular text = (TextView_Regular) dialogAlert.findViewById(R.id.message);
        text.setText(message);

        Button_Regular dialogButton = (Button_Regular) dialogAlert.findViewById(R.id.button);
        dialogButton.setText(getString(R.string.cancel));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();

            }
        });

        Button_Regular dialogButtonPositive = (Button_Regular) dialogAlert.findViewById(R.id.buttonCreate);
        dialogButtonPositive.setText(getString(R.string.logout));
        dialogButtonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutFrom();
                dialogAlert.dismiss();

            }
        });
        dialogAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialogAlert.show();

    }

    private void logoutFrom() {
        DialogManager.startProgressDialog(this);
        WebServiceResult.LogoutParent(SharedPref.getString(MyApplication.PARENT_ID), "");
    }

    @OnClick(R.id.termandc)
    public void showTermAndC() {
        startActivity(new Intent(this, Activity_TandC.class));
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.plans)
    public void showPlaymentOptions() {
        selectOption();

    }

    @OnClick(R.id.aboutus)
    public void showAboutUs() {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shieldbully.com/"));
            startActivity(browserIntent);
            overridePendingTransition(0, 0);

        } catch (NullPointerException e) {

        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updateStatusOfPayment() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.plans);
        if (MyApplication.isSubscriptionActivated) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
    }

    private void selectOption() {

        final Dialog dialog = new Dialog(Activity_Settings.this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_payment_options);

        final EditText_Regular editCode = (EditText_Regular) dialog.findViewById(R.id.editTextCode);

        final Button_Regular dialogButton = (Button_Regular) dialog.findViewById(R.id.button_ConfirmOption);

        final Button_Regular button_FromGoogle = (Button_Regular) dialog.findViewById(R.id.button_FromGoogle);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (editCode.getText().toString().length() == 0) {
                            SimpleAlertDialog.showSimpleAlertDialog(Activity_Settings.this, "Please enter code");
                        } else {
                            if (editCode.getText().toString().equalsIgnoreCase("123")) {
                                dialog.dismiss();
                                SharedPref.setPremimumVersionFree(Activity_Settings.this, true);
                                SimpleAlertDialog.showAlertDialog(Activity_Settings.this,
                                        getString(R.string.freeversionMessage));
                                MyApplication.isSubscriptionActivated = true;
                                updateStatusOfPayment();
                            } else {
                                SimpleAlertDialog.showAlertDialog(Activity_Settings.this, "Please enter valid code");

                            }
                        }

                    }

                });

                button_FromGoogle.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (!readyToPurchase) {
                            //  showToast("Can't start the payment process");
                            return;
                        }
                        //  bp.purchase(Activity_Settings.this, ApplicationVar.products.get(0));
                        dialog.dismiss();
                    }
                });
            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void updateLogout(UniversalResponce body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            if (SharedPref.clearPref(Activity_Settings.this)) {
                Intent intent = new Intent(getBaseContext(), Activity_Splash.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                CustomToast.showToast(this, getString(R.string.unableToLogout));

            }
        } else {
            CustomToast.showToast(this, getString(R.string.unableToLogout));

        }
    }

    public void updateNotificationStatus(UniversalResponce body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            if (checkBox.isChecked()) {
                SharedPref.setNotificationStatus(getApplicationContext(), false);
                checkBox.setChecked(false);
            } else {
                SharedPref.setNotificationStatus(getApplicationContext(), true);
                checkBox.setChecked(true);
            }

        } else {
        }
    }
}
