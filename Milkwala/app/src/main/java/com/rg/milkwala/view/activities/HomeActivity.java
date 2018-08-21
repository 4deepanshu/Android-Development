package com.rg.milkwala.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rg.milkwala.MyApplication;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.ItemDecorationAlbumColumns;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.product.Product;
import com.rg.milkwala.view.adapters.HomeAdapter;
import com.rg.milkwala.view.adapters.HomePagerAdapter;
import com.rg.milkwala.view.controls.EditTextRegular;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.vpHome)ViewPager vpHome;
    @BindView(R.id.indicator)CircleIndicator indicator;



    TextViewRegular textViewNotificationsCount;

    //Firebase database object
    private FirebaseDatabase database;
    //


    HomeAdapter adapter;
    private HomeActivity activity;
    int i = 0;
    List<Product> productList = new ArrayList<>();
    private int click = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivit);
        ButterKnife.bind(this);
        setActionBar();
        activity = this;
        recylerView.addItemDecoration(new ItemDecorationAlbumColumns(2, 2));
        getProducts();
        changeRecyclerViewStyle();
    }
@OnClick(R.id.btnMobile)
public  void getMobiles(){
    getProductsbyCategory(1);
}
    @OnClick(R.id.btnLaptop)
    public  void getLaptop(){

        getProductsbyCategory(2);

    }
    @OnClick(R.id.btnWatches)
    public  void getWatches(){
        getProductsbyCategory(3);

    }
    @OnClick(R.id.btnTv)
    public  void getTv(){
        getProductsbyCategory(4);

    }
    @OnClick(R.id.btnGadget)
    public  void getGadget(){
        getProductsbyCategory(5);

    }
    @OnClick(R.id.btnAll)
    public  void getAll(){
       getProducts();

    }
    public void setActionBar() {

        //CUSTOMIZE TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //


        //SET CUSTOM ACTIONBAR
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.productlisting_actionbar);
        getSupportActionBar().setElevation(0);
        Toolbar parent = (Toolbar) getSupportActionBar().getCustomView().getParent();
        parent.setContentInsetsAbsolute(0, 0);
        getSupportActionBar().show();
        ActionBar actionBar = getSupportActionBar();
        View view = getSupportActionBar().getCustomView();
        ImageButton imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButtonMenu);
        ImageButton imageButtonCart = (ImageButton) view.findViewById(R.id.imageButtonCart);
        textViewNotificationsCount = (TextViewRegular) view.findViewById(R.id.textViewNotificationsCount);
        imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateMenu(v);
            }
        });

        imageButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ShoppingCart.class).putExtra("value", "HomeActivity"));
            }
        });

    }

    private void populateMenu(View v) {
        PopupMenu popup = new PopupMenu(HomeActivity.this, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.user_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    SharedPref.getInstance().clear();
                    Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                } else if (item.getItemId() == R.id.orderhistory) {
                    startActivity(new Intent(HomeActivity.this, OrderHistoryActivity.class));
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }


    public void setAdapter(List<Product> productList) {
        vpHome.setAdapter(new HomePagerAdapter(productList,activity));
        indicator.setViewPager(vpHome);
        adapter = new HomeAdapter(productList, activity);
        recylerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recylerView.setAdapter(adapter);
        recylerView.setNestedScrollingEnabled(false);
        i = 0;
    }


    public void changeRecyclerViewStyle() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    if (click == 1) {
                        recylerView.addItemDecoration(new ItemDecorationAlbumColumns(2, 1));
                        click++;
                    }
                    recylerView.setLayoutManager(new GridLayoutManager(activity, 1));
                    recylerView.setAdapter(adapter);
                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_grid));
                    i = 1;
                } else if (i == 1) {
                    recylerView.setLayoutManager(new GridLayoutManager(activity, 2));
                    recylerView.setAdapter(adapter);
                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_list));
                    i = 0;
                }
            }
        });
    }

    public void getProducts() {
        showProgressDialog(getString(R.string.loadproducts));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                setAdapter(productList);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });
    }

    public void getProductsbyCategory(final int category) {
        showProgressDialog(getString(R.string.loadproducts));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if(product.getCategory()==category){
                        productList.add(product);
                    }
                }
                setAdapter(productList);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (textViewNotificationsCount != null) {
            if (MyApplication.CART_ITEMS.size() > 0) {
                textViewNotificationsCount.setText(MyApplication.CART_ITEMS.size() + "");
                textViewNotificationsCount.setVisibility(View.VISIBLE);
            } else {
                textViewNotificationsCount.setVisibility(View.GONE);
            }
        }
    }
}
