package com.rg.milkwala.view.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;

import com.rg.milkwala.MyApplication;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.model.user.Orders;
import com.rg.milkwala.view.adapters.PagerAdapter;
import com.rg.milkwala.view.controls.ButtonRegular;
import com.rg.milkwala.view.controls.TextViewBold;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class DescriptionActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.textViewQuantity)
    TextViewRegular textViewQuantity;
    @BindView(R.id.textViewName)
    TextViewRegular textViewName;
    @BindView(R.id.textViewRate)
    TextViewBold textViewRate;
    @BindView(R.id.textViewDescription)
    TextViewRegular textViewDescription;
    @BindView(R.id.radioButtonOnce)
    RadioButton radioButtonOnce;
    @BindView(R.id.addToCart)
    ButtonRegular addToCart;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textViewDate)
    TextViewRegular textViewDate;
    Boolean isOnce = true;
    Boolean isMonthly = false;
    int QUANTITY = 0;
    private String currentDate;
    private String nextDate;
    private DescriptionActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_actvity);
        ButterKnife.bind(this);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setDefaultData();
        setPagerAdapter();
        currentdate();

    }


    private void setPagerAdapter() {

        List<String> IMAGES = new ArrayList<>();
        if (MyApplication.SELECTED_PRODUCT.getImage1().length() > 0) {
            IMAGES.add(MyApplication.SELECTED_PRODUCT.getImage1());
        }
        if (MyApplication.SELECTED_PRODUCT.getImage2().length() > 0) {
            IMAGES.add(MyApplication.SELECTED_PRODUCT.getImage2());

        }
        viewPager.setAdapter(new PagerAdapter(this, IMAGES));
        indicator.setViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.SELECTED_PRODUCT.getOutOfStock()) {
            addToCart.setText(getString(R.string.outofstock));
            addToCart.setEnabled(false);
        }
    }


    @OnClick(R.id.addToCart)
    public void addToCart() {

        orderInfo(isMonthly, isOnce, MyApplication.SELECTED_PRODUCT.getSubscriptionPrice(), MyApplication.SELECTED_PRODUCT.getOnetimePrice(), MyApplication.SELECTED_PRODUCT.getId(), textViewQuantity.getText().toString(), nextDate, currentDate);
    }
    public void orderInfo(boolean isMonthly, boolean isOnce, float monhtlyPrice, float oncePrice, int productId, String quantity, String deliveryDate, String ordereddate) {
        Orders order = new Orders();
        order.setEmail(SharedPref.getInstance().getString(Constants.USER_EMAIL));
        order.setUserName(SharedPref.getInstance().getString(Constants.USER_NAME));
        order.setUserId(SharedPref.getInstance().getString(Constants.USER_ID));
        order.setMonthly(isMonthly);
        order.setOnce(isOnce);
        order.setMothlyPrice(monhtlyPrice);
        order.setOncePrice(oncePrice);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setDeliveryDate("");
        order.setDeliveryStatus("pending");
        order.setOrdereddate("");
        order.setImage(MyApplication.SELECTED_PRODUCT.getImage1());
        order.setProductName(MyApplication.SELECTED_PRODUCT.getName());
        MyApplication.CART_ITEMS.add(order);
        Dialogs.getInstance().showtoast(activity, getString(R.string.itemaddedtocart));
    }

    private void setDefaultData() {
        QUANTITY = MyApplication.SELECTED_PRODUCT.getMinimumOrderAmount();
        textViewQuantity.setText(QUANTITY + " " + getString(R.string.ltr));
        radioButtonOnce.setChecked(true);
        textViewName.setText(MyApplication.SELECTED_PRODUCT.getName());
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String withTwoDecimalPoint = df.format(MyApplication.SELECTED_PRODUCT.getOnetimePrice());
        textViewRate.setText(withTwoDecimalPoint+ "");
        textViewDescription.setText(MyApplication.SELECTED_PRODUCT.getDescription());
    }

    @OnClick(R.id.textViewIncrement)
    public void onClickIncreaseQuantity() {
        if (QUANTITY < MyApplication.SELECTED_PRODUCT.getMaximumOrderAmount()) {
            QUANTITY++;
            textViewQuantity.setText(QUANTITY + " " + getString(R.string.ltr));
        }
    }
    @OnClick(R.id.textViewDecrement)
    public void onClickDecreaseQuantity() {
        if (QUANTITY > MyApplication.SELECTED_PRODUCT.getMinimumOrderAmount()) {
            QUANTITY--;
            textViewQuantity.setText(QUANTITY + " " + getString(R.string.ltr));
        }
    }

    @OnClick(R.id.radioButtonMonthly)
    public void onMonthySelected() {
        isMonthly = true;
        isOnce = false;
        textViewRate.setText(MyApplication.SELECTED_PRODUCT.getSubscriptionPrice() + "");
    }

    @OnClick(R.id.radioButtonOnce)
    public void onOnceSelected() {
        isOnce = true;
        isMonthly = false;
        textViewRate.setText(MyApplication.SELECTED_PRODUCT.getOnetimePrice() + "");
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


    public void currentdate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = simpleDateFormat.format(new Date());
        textViewDate.setText(currentDate);
    }
   /* public void nextDaydate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1); // number of days to add
        nextDate = simpleDateFormat.format(c.getTime());
    }
*/
}
