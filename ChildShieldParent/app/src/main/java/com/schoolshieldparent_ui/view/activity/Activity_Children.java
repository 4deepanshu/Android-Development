package com.schoolshieldparent_ui.view.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.children.GetChildren;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_Children;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Children extends AppCompatActivity {

    private static Activity_Children instance;
    @BindView(R.id.listviewChild)
    ListView listViewChild;
    @BindView(R.id.imageButton_Add)
    ImageButton buttonAdd;
    Adapter_Children adapter;
    @BindView(R.id.info)
    TextView_Regular textViewInfo;


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private TextView_Regular age;
    private Dialog dialogAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);
        ButterKnife.bind(this);
        instance = Activity_Children.this;
        getSupportActionBar().hide();
        getChildList("");
        setSwipeLoader();
    }

    public static Activity_Children getInstance() {
        return instance;
    }

    public void getChildList(String refresh) {
        if (refresh.equalsIgnoreCase(getString(R.string.refresh))) {
            WebServiceResult.ParentsAllChildren(SharedPref.getString(MyApplication.PARENT_ID));
        } else {
            DialogManager.startProgressDialog(getInstance());
            WebServiceResult.ParentsAllChildren(SharedPref.getString(MyApplication.PARENT_ID));
        }
    }

    String gender = "";
    RadioButton male;
    RadioButton female;
    String DOB = "";
    @BindView(R.id.swipeContainerChilred)
    SwipeRefreshLayout swipeLayout;

    private void setSwipeLoader() {

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                getChildList(getString(R.string.refresh));

            }
        });

        swipeLayout.setColorSchemeResources(android.R.color.black,

                android.R.color.black,

                android.R.color.black,

                android.R.color.black);
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        finish();
        overridePendingTransition(0, 0);

    }

    @OnClick(R.id.imageButton_Add)
    public void addChild() {

        final Dialog dialog = new Dialog(instance);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_addchild);

        final EditText_Regular firstName = (EditText_Regular) dialog.findViewById(R.id.editTextFirstName);
        final EditText_Regular lastName = (EditText_Regular) dialog.findViewById(R.id.editTextLastName);
        age = (TextView_Regular) dialog.findViewById(R.id.textViewAge);
        age.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                showDialog(999);
            }
        });

        male = (RadioButton) dialog.findViewById(R.id.male);
        female = (RadioButton) dialog.findViewById(R.id.female);

        male.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gender = getString(R.string.male);
                female.setChecked(false);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                male.setChecked(false);
                gender = getString(R.string.female);
            }
        });

        final Button_Regular dialogButton = (Button_Regular) dialog.findViewById(R.id.button_AddChild);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String fName = firstName.getText().toString();
                        String lName = lastName.getText().toString();

                        if (validation(fName, lName, lName, DOB, gender)) {
                            dialog.dismiss();
                            Random r = new Random();
                            int pasword = r.nextInt(99999 - 1000) + 1000;
                            DialogManager.startProgressDialog(getInstance());
                            WebServiceResult.AddChild(fName, lName, pasword + "", SharedPref.getString(MyApplication.PARENT_ID) + "", DOB, gender);
                        }

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

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentdate = df.format(c.getTime());
        String yearDate = new StringBuilder().append(day).append("/").append(month).append("/").append(year).toString();
        DOB = yearDate;
        if (getAge(year, month,day) >= 1) {
            age.setText(getAge(year, month,day) + " " + getString(R.string.yearsold) + "  " + yearDate);
        } else {
            DOB = "";
            age.setText("");
            CustomToast.showToast(this, getString(R.string.agemustbegreaterthan1year));
        }

    }

    public int getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        return a;
    }



    private boolean validation(String fname, String lname, String password, String age1, String gender1) {
        if (EditText_Regular.isEmpty(fname)) {
            CustomToast.showToast(this, getString(R.string.enterFname));
            return false;
        } else if (EditText_Regular.isEmpty(lname)) {
            CustomToast.showToast(this, getString(R.string.enterLname));
            return false;
        } else if (age1.toString().equalsIgnoreCase("")) {
            CustomToast.showToast(this, getString(R.string.ageofChild));
            return false;
        } else if (gender1.equalsIgnoreCase("")) {
            CustomToast.showToast(this, getString(R.string.genderofChild));
            return false;
        }
        return true;
    }

    public void updateAddChildStatus(String status) {
        setAdapter(status);
    }

    public void setAdapter(String status) {
        if (status.equalsIgnoreCase("1")) {
            showAddedSucess();
            getChildList(getString(R.string.refresh));
            Activity_Home.getInstance().getChild(getString(R.string.refresh));
        } else if (status.equalsIgnoreCase("0")) {
            SimpleAlertDialog.showAlertDialog(this, getString(R.string.allreadyExist));
        } else {
            SimpleAlertDialog.showAlertDialog(this, getString(R.string.unabletoaddchild));
        }

    }


    private void showAddedSucess() {
        dialogAlert = new Dialog(this);
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAlert.setContentView(R.layout.view_childcreated_alert);
        dialogAlert.setCancelable(false);
        Button_Regular dialogButton = (Button_Regular) dialogAlert.findViewById(R.id.button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        Button_Regular buttonDonwload = (Button_Regular) dialogAlert.findViewById(R.id.buttonDonwload);
        buttonDonwload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getString(R.string.thisapppackagename); // getPackageName()
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.marketdetail) + appPackageName)));
                    overridePendingTransition(0, 0);

                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.applicationStoreLink))));
                    overridePendingTransition(0, 0);

                }
            }
        });
        buttonDonwload.setVisibility(View.GONE);
        Button buttonShare = (Button) dialogAlert.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsBody = getString(R.string.downloadChildFromURl);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.schoolshielbullyparent));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, smsBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.shareschoolshieldbullychild)));
                overridePendingTransition(0, 0);

            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialogAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialogAlert.show();
        dialogAlert.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

    }


    public void updateChildrenList(GetChildren body, int type) {
        setchildList(body);
    }

    private void setchildList(GetChildren body) {

        if (body.getResult().getChildrens().size() >= 5) {
            buttonAdd.setVisibility(View.GONE);
        }
        if (body.getResult().getChildrens().size() > 0) {
            textViewInfo.setVisibility(View.GONE);
        } else {
            textViewInfo.setVisibility(View.VISIBLE);
        }
        adapter = new Adapter_Children(getInstance(), body.getResult().getChildrens());
        listViewChild.setAdapter(adapter);

    }

    public void updateChildPassword() {
        getChildList(getString(R.string.refresh));
    }
}
