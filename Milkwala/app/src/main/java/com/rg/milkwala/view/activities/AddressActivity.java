package com.rg.milkwala.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.DividerItemDecoration;
import com.rg.milkwala.controller.utils.ItemDecorationAlbumColumns;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.AdressPrp;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.view.adapters.AdressAdapter;
import com.rg.milkwala.view.controls.TextViewBold;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.textViewNoAddress)
    TextViewRegular textViewNoAddress;
    @BindView(R.id.textViewDone)
    TextViewBold textViewDone;
    private AddressActivity activity;
    AdressAdapter adapter;
    public AdressPrp adress;
    List<AdressPrp> listAdsress = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    List<Boolean> checkBoxlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        activity = this;
        getAddressList();
    }

    public void setAdapter( List<String> keys, List<AdressPrp> listAdsress) {
        adapter = new AdressAdapter(activity,keys,listAdsress);
        recylerView.setLayoutManager(new LinearLayoutManager(activity));
        recylerView.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider)));
        recylerView.setAdapter(adapter);
    }


    @OnClick(R.id.textViewAddress)
    public void addAdress() {
        startActivity(new Intent(activity, AddAddressActivity.class).putExtra("value", "addAddress"));
    }

    public void getAddressList() {
        showProgressDialog(getString(R.string.loadingpleasewait));
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("address");
        mDatabase.orderByChild("email").equalTo(SharedPref.getInstance().getString(Constants.USER_EMAIL)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkBoxlist.clear();
                listAdsress.clear();
                keys.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                     adress = data.getValue(AdressPrp.class);
                     keys.add(data.getKey());
                     listAdsress.add(adress);
                }
                hideProgressDialog();
                if (listAdsress.size() != 0) {
                    setAdapter(keys,listAdsress);
                    textViewNoAddress.setVisibility(View.GONE);
                    textViewDone.setVisibility(View.VISIBLE);
                } else {
                    textViewDone.setVisibility(View.GONE);
                    textViewNoAddress.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });
    }


    @OnClick(R.id.textViewDone)
    public  void intentForShoppingCart(){
        Intent intent = new Intent();
        setResult(1,intent);
        finish();
    }
}
