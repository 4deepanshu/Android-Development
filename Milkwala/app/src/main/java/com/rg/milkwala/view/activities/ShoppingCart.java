package com.rg.milkwala.view.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.citrus.sdk.CitrusClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.rg.milkwala.MyApplication;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.DividerItemDecoration;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.model.user.Orders;
import com.rg.milkwala.view.adapters.ShoppingCartAdapter;
import com.rg.milkwala.view.controls.ButtonRegular;
import com.rg.milkwala.view.controls.TextViewRegular;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingCart extends BaseActivity {
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.textViewProductPrice)
    TextViewRegular textViewProductPrice;
    @BindView(R.id.textViewTotalPrice)
    TextViewRegular textViewTotalPrice;
    @BindView(R.id.textViewNoItem)
    TextViewRegular textViewNoItem;
    @BindView(R.id.buttonAddress)
    ButtonRegular buttonAddress;
    @BindView(R.id.contentLinear)
    LinearLayout contentLinear;
    private ShoppingCart activity;
    private float totalPrice = 0;
    ShoppingCartAdapter adapter;
    private String intentValue;
    private String currentDate;
    private String nextDate;
    List<Orders> listOfOrder = new ArrayList<>();
    private CitrusClient citrusClient;

    int dummyprice = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        activity = this;
        setToolbar();
        currentdate();
        totalPrice();
        setAdapter();
        intentValue = getIntent().getStringExtra("value");
        handleIntentValue();
    }

    public void handleIntentValue() {
        if (intentValue.equalsIgnoreCase("AddressActivity")) {
            buttonAddress.setText(getString(R.string.proceedToPay) + totalPrice + "Rs.");
        }
    }

    public void setToolbar() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString youPaidMessage = new SpannableString(getString(R.string.shoppingcart));
        youPaidMessage.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, (getString(R.string.shoppingcart)).length(), 0);
        builder.append(youPaidMessage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(builder);
    }

    public void setAdapter() {
        if (MyApplication.CART_ITEMS.size() == 0) {
            textViewNoItem.setVisibility(View.VISIBLE);
            contentLinear.setVisibility(View.GONE);
        } else {
            contentLinear.setVisibility(View.VISIBLE);
            adapter = new ShoppingCartAdapter(activity);
            recylerView.setLayoutManager(new LinearLayoutManager(activity));
            recylerView.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider)));
            recylerView.setAdapter(adapter);
            deletedata();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void totalPrice() {
        for (int i = 0; i < MyApplication.CART_ITEMS.size(); i++) {
            if (MyApplication.CART_ITEMS.get(i).getOnce()) {
                totalPrice += ((Integer.parseInt(MyApplication.CART_ITEMS.get(i).getQuantity().substring(0, MyApplication.CART_ITEMS.get(i).getQuantity().indexOf(" ")))) * MyApplication.CART_ITEMS.get(i).getOncePrice());
            } else {
                totalPrice += (30 * (Integer.parseInt(MyApplication.CART_ITEMS.get(i).getQuantity().substring(0, MyApplication.CART_ITEMS.get(i).getQuantity().indexOf(" "))) * (MyApplication.CART_ITEMS.get(i).getMothlyPrice())));
            }
        }
        textViewProductPrice.setText(totalPrice + "");
        textViewTotalPrice.setText(totalPrice + "");
    }

    public void deletedata() {
        adapter.setOnItemClickListner(new ShoppingCartAdapter.Clicklistner() {
            @Override
            public void onItemCLick(int position, View v) {
                if (MyApplication.CART_ITEMS.get(position).getMonthly()) {
                    decreasePrice(30 * (Integer.parseInt(MyApplication.CART_ITEMS.get(position).getQuantity().substring(0, MyApplication.CART_ITEMS.get(position).getQuantity().indexOf(" "))) * MyApplication.CART_ITEMS.get(position).getMothlyPrice()));
                } else {
                    decreasePrice((Integer.parseInt(MyApplication.CART_ITEMS.get(position).getQuantity().substring(0, MyApplication.CART_ITEMS.get(position).getQuantity().indexOf(" "))) * MyApplication.CART_ITEMS.get(position).getOncePrice()));
                }
                MyApplication.CART_ITEMS.remove(MyApplication.CART_ITEMS.get(position));
                if (MyApplication.CART_ITEMS.size() == 0) {
                    textViewNoItem.setVisibility(View.VISIBLE);
                    contentLinear.setVisibility(View.GONE);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

        });
    }

    private void decreasePrice(float i) {
        totalPrice = totalPrice - i;
        textViewProductPrice.setText(totalPrice + "");
        textViewTotalPrice.setText(totalPrice + "");
    }

    @OnClick(R.id.buttonAddress)
    public void addressList() {

        if (buttonAddress.getText().toString().equalsIgnoreCase(getString(R.string.proceedTOAdddress))) {
            Intent intent = new Intent(ShoppingCart.this, AddressActivity.class);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.open_up, R.anim.blank);
        } else {
            showProgressDialog(getString(R.string.loadingpleasewait));
            PayPalPayment payment = new PayPalPayment(new BigDecimal("100"), "USD", "hipster jeans",
                    PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, MyApplication.config);
            intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, 0);

            // this was the original app functionality...................
           /* for (int i = 0; i < MyApplication.CART_ITEMS.size(); i++) {
                if (MyApplication.CART_ITEMS.get(i).getOnce()) {
                    setOrderData(MyApplication.CART_ITEMS.get(i).getMonthly(), MyApplication.CART_ITEMS.get(i).getOnce(), MyApplication.CART_ITEMS.get(i).getMothlyPrice(), MyApplication.CART_ITEMS.get(i).getOncePrice(), MyApplication.CART_ITEMS.get(i).getProductId(), MyApplication.CART_ITEMS.get(i).getQuantity(), currentDate, "1");
                } else {
                    setOrderData(MyApplication.CART_ITEMS.get(i).getMonthly(), MyApplication.CART_ITEMS.get(i).getOnce(), MyApplication.CART_ITEMS.get(i).getMothlyPrice(), MyApplication.CART_ITEMS.get(i).getOncePrice(), MyApplication.CART_ITEMS.get(i).getProductId(), MyApplication.CART_ITEMS.get(i).getQuantity(), currentDate, "30");
                }
            }
            hideProgressDialog();
            alertDialogAdminMessage(getString(R.string.youOrderGeneratedSuccesfully));*/

            //..................................................................
        }
    }

    public void currentdate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        currentDate = simpleDateFormat.format(new Date());
    }

    /*public void nextDaydate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1); // number of days to add
        nextDate = simpleDateFormat.format(c.getTime());
    }*/

    public void createOrder(Orders listOfOrder) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        mDatabase.child(mDatabase.push().getKey()).setValue(listOfOrder);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                buttonAddress.setText(getString(R.string.proceedToPay) + " " + totalPrice + "Rs.");
            }
        } else if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));

                        // this is for student project..................................
                        for (int i = 0; i < MyApplication.CART_ITEMS.size(); i++) {
                            if (MyApplication.CART_ITEMS.get(i).getOnce()) {
                                setOrderData(MyApplication.CART_ITEMS.get(i).getMonthly(), MyApplication.CART_ITEMS.get(i).getOnce(), MyApplication.CART_ITEMS.get(i).getMothlyPrice(), MyApplication.CART_ITEMS.get(i).getOncePrice(), MyApplication.CART_ITEMS.get(i).getProductId(), MyApplication.CART_ITEMS.get(i).getQuantity(), currentDate, "1");
                            }else {
                                setOrderData(MyApplication.CART_ITEMS.get(i).getMonthly(), MyApplication.CART_ITEMS.get(i).getOnce(), MyApplication.CART_ITEMS.get(i).getMothlyPrice(), MyApplication.CART_ITEMS.get(i).getOncePrice(), MyApplication.CART_ITEMS.get(i).getProductId(), MyApplication.CART_ITEMS.get(i).getQuantity(), currentDate, "30");
                            }
                        }
                        MyApplication.CART_ITEMS.clear();
                        hideProgressDialog();
                        alertDialogAdminMessage(getString(R.string.youOrderGeneratedSuccesfully));
                        finish();
                        //.........................................................................


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public void setOrderData(boolean isMonthly, boolean isOnce, Float monhtlyPrice, Float oncePrice, int productId, String quantity, String orderDate, String orderCount) {
        Orders order = new Orders();
        String email = SharedPref.getInstance().getString(Constants.USER_EMAIL);
        order.setEmail(email);
        order.setUserName(SharedPref.getInstance().getString(Constants.USER_NAME));
        order.setUserId(SharedPref.getInstance().getString(Constants.USER_ID));
        order.setMonthly(isMonthly);
        order.setOnce(isOnce);
        order.setMothlyPrice(monhtlyPrice);
        order.setOncePrice(oncePrice);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setOrdereddate(orderDate);
        order.setDelivered(false);
        order.setImage(MyApplication.SELECTED_PRODUCT.getImage1());
        order.setProductName(MyApplication.SELECTED_PRODUCT.getName());
        order.setOrderCounts(orderCount);
        createOrder(order);
    }

    /*public void alertDialogBalance() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.nothavesufficientbalance));
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < MyApplication.CART_ITEMS.size(); i++) {
                    setOrderData(MyApplication.CART_ITEMS.get(i).getMonthly(), MyApplication.CART_ITEMS.get(i).getOnce(), MyApplication.CART_ITEMS.get(i).getMothlyPrice(), MyApplication.CART_ITEMS.get(i).getOncePrice(), MyApplication.CART_ITEMS.get(i).getProductId(), "UnPaid", MyApplication.CART_ITEMS.get(i).getQuantity(), currentDate);
                }
                dialog.dismiss();
                alertDialogAdminMessage(getString(R.string.notificationSendToAdmin));
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }*/

    public void alertDialogAdminMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                MyApplication.CART_ITEMS.clear();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


}
