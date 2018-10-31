package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.GridView;
import android.widget.Spinner;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.children.Childs;
import com.schoolshieldparent_ui.model.children.GetChildren;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_Menu;
import com.schoolshieldparent_ui.view.adapter.Adapter_SelectChild_home_spinner;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class Activity_Home extends AppCompatActivity {
    @BindView(R.id.gridViewMenu)
    GridView gridViewMenu;
    @BindView(R.id.spinner_child)
    Spinner spinnerChild;
    private int count = 0;
    List<Childs> childList = new ArrayList<>();
    private Adapter_SelectChild_home_spinner adapterChild;
    static Activity_Home instance = null;
    public static boolean AppInBackground=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppInBackground=true;
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        instance = this;
        setTitleCapital();
        getChild("");
        Adapter_Menu adapter = new Adapter_Menu(this, 0);
        gridViewMenu.setAdapter(adapter);
    }


    public static Activity_Home getInstance() {
        return instance;
    }

    public void getChild(String from) {
        if (from.equalsIgnoreCase(getString(R.string.refresh))) {
            WebServiceResult.ParentsChildren(SharedPref.getString(MyApplication.PARENT_ID));
        } else {
            DialogManager.startProgressDialog(this);
            WebServiceResult.ParentsChildren(SharedPref.getString(MyApplication.PARENT_ID));
        }
    }

    private void setTitleCapital() {
        TextView_Regular textView = (TextView_Regular) findViewById(R.id.textView1);
        textView.setText(textView.getText().toString().toUpperCase());
    }

    @Override
    public void onBackPressed() {
        if (count == 1) {
            count = 0;
            super.onBackPressed();
            overridePendingTransition(0, 0);
        } else {
            CustomToast.showToast(this, getString(R.string.pleaseenterbacktoquit));
            count++;
        }
    }

    @OnItemSelected(R.id.spinner_child)
    public void selectedChild(int position) {
        if (childList.get(position).getStudentStatus().equalsIgnoreCase("===")) {
            MyApplication.currentChildID = -2;
            MyApplication.currentChildName = "";
        } else if (childList.get(position).getStudentStatus().equalsIgnoreCase("1")) {
            MyApplication.currentChildID = Integer
                    .parseInt(childList.get(position).getStudentId());
            MyApplication.currentChildName = childList.get(position).getStudentName() + " " + childList.get(position).getStudentLname();
        } else {
            MyApplication.currentChildID = -1;
            MyApplication.currentChildName = "";
            SimpleAlertDialog.showAlertDialog(this, getString(R.string.childNotActivated));
        }
    }

    private String studentStatus;

    public void updateChildren(GetChildren body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            setAdapter(body);
        } else if (body.getResult().getStatus().toString().equalsIgnoreCase("2")) {
            Adapter_Menu adapter = new Adapter_Menu(this, 0);
            gridViewMenu.setAdapter(adapter);
            spinnerChild.setEnabled(true);
            adapterChild = new Adapter_SelectChild_home_spinner(this, body.getResult().getChildrens());
            spinnerChild.setAdapter(adapterChild);
        }
    }

    private void setAdapter(GetChildren body) {
        childList.clear();
        Childs childs = new Childs();
        childs.setStudentName(getString(R.string.chooseachild));
        childs.setStudentLname("");
        childs.setStudentStatus("===");
        childList.add(0, childs);
        childList.addAll(body.getResult().getChildrens());
        if (body.getResult().getChildrens().size() > 0) {
            Adapter_Menu adapter = new Adapter_Menu(this, 1);
            gridViewMenu.setAdapter(adapter);
            adapterChild = new Adapter_SelectChild_home_spinner(this, childList);
            spinnerChild.setAdapter(adapterChild);
            spinnerChild.setEnabled(true);
        } else {
            Adapter_Menu adapter = new Adapter_Menu(this, 0);
            gridViewMenu.setAdapter(adapter);
            spinnerChild.setEnabled(true);
            adapterChild = new Adapter_SelectChild_home_spinner(this, body.getResult().getChildrens());
            spinnerChild.setAdapter(adapterChild);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        instance = this;

    }



}
