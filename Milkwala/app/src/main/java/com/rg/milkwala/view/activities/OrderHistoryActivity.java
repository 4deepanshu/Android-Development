package com.rg.milkwala.view.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.controller.utils.DividerItemDecoration;
import com.rg.milkwala.model.user.Orders;
import com.rg.milkwala.view.adapters.OrderHistoryAdapter;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends BaseActivity {
    TextViewRegular textViewNoOrderYet;
    private List<Orders> orderList = new ArrayList<>();
    private OrderHistoryAdapter adapter;
    RecyclerView recylerView;
    Activity activity;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        textViewNoOrderYet = (TextViewRegular) findViewById(R.id.textViewNoOrderYet);
        recylerView = (RecyclerView) findViewById(R.id.recylerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activity = this;
        setToolbar();
        getOrders();
    }
    public void setToolbar() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString youPaidMessage = new SpannableString(getString(R.string.order_history));
        youPaidMessage.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, (getString(R.string.order_history)).length(), 0);
        builder.append(youPaidMessage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(builder);
    }


    public void getOrders() {
        showProgressDialog(getString(R.string.loadingpleasewait));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Orders order = postSnapshot.getValue(Orders.class);
                        orderList.add(order);
                }
                if (orderList.size() >= 1) {
                    textViewNoOrderYet.setVisibility(View.GONE);
                    setAdapter(orderList);
                } else {
                    textViewNoOrderYet.setVisibility(View.VISIBLE);
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewNoOrderYet.setVisibility(View.VISIBLE);
                Dialogs.getInstance().showtoast(activity, databaseError.getMessage());
                hideProgressDialog();
            }
        });
    }

    public void setAdapter(List<Orders> orderList) {
        adapter = new OrderHistoryAdapter(activity, orderList);
        recylerView.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider)));
        recylerView.setLayoutManager(new LinearLayoutManager(activity));
        recylerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
