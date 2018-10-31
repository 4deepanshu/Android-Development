package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.children.Childs;
import com.schoolshieldparent_ui.model.children.GetChildren;
import com.schoolshieldparent_ui.model.myinfo.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_Parent_Info_Child;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_MyInfo extends AppCompatActivity {
    @BindView(R.id.textView_Name)
    TextView textViewParentName;
    @BindView(R.id.textView_Email)
    TextView textViewParentEmail;
    @BindView(R.id.listView1)
    ListView listViewChild;
    @BindView(R.id.textView2)
    TextView_Regular textViewChild;


    static Activity_MyInfo instance;
    private List<Childs> childList = new ArrayList<>();
    private Adapter_Parent_Info_Child adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_myinfo );
        instance = this;
        getSupportActionBar().hide();
        ButterKnife.bind( this );
        getParentInfo();
        setSwipeLoader();

    }

    private void getParentInfo() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.ParentInfo();
    }

    public static Activity_MyInfo getInstance() {
        return instance;
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        this.finish();
        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }

    public void updateParentInfo(Result result) {
        if (result.getParent() != null) {
            textViewParentEmail.setText( result.getParent().getParentEmail() );
            textViewParentName.setText( result.getParent().getParentName() );
            getAllChild();
        }

    }

    private void getAllChild() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.MyInfoChildren( SharedPref.getString( MyApplication.PARENT_ID ) );

    }

    private void setChildAdapter(List<Childs> childrens) {
        Adapter_Parent_Info_Child adapter = new Adapter_Parent_Info_Child( this,
                childrens );
        listViewChild.setAdapter( adapter );
    }


    public void updateChildList(String studentId) {
        getAllChild();
        Activity_Home.getInstance().getChild( getString( R.string.refresh ) );
        if (studentId.equalsIgnoreCase( MyApplication.currentChildID + "" )) {
            MyApplication.currentChildID = -1;
            MyApplication.currentChildName = "";
        }
    }

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private void setSwipeLoader() {

        swipeLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing( false );
                getParentInfo();
            }
        } );
        swipeLayout.setColorSchemeResources( android.R.color.black,

                android.R.color.black,

                android.R.color.black,

                android.R.color.black );
    }

    public void updateChildren(GetChildren body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase( "1" )) {
            setChildAdapter( body.getResult().getChildrens() );
        } else if (body.getResult().getStatus().toString().equalsIgnoreCase( "2" )) {
            textViewChild.setText( getString( R.string.thereisnochildyet ) );
            setChildAdapter( body.getResult().getChildrens() );

        }
    }
}
