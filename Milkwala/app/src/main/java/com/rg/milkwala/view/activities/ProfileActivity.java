package com.rg.milkwala.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.model.user.User;
import com.rg.milkwala.view.controls.TextViewRegular;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements OnMapReadyCallback, View.OnTouchListener {
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.imagetrans)
    ImageView imagetrans;

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;

    @BindView(R.id.textViewEmail)
    TextViewRegular textViewEmail;

    @BindView(R.id.textViewName)
    TextViewRegular textViewName;

    @BindView(R.id.textViewAddress)
    TextViewRegular textViewAddress;

    @BindView(R.id.textViewLocation)
    TextViewRegular textViewLocation;

  /*  @BindView(R.id.textViewPrice)
    TextViewRegular textViewPrice;*/

    @BindView(R.id.textViewState)
    TextViewRegular textViewState;

    private GoogleMap mMap;
    private ProfileActivity activity;
    private double latitude;
    private double longitude;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        activity = this;
        imagetrans.setOnTouchListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    public void setAllData() {
        if (user.getLatitude() != null && user.getLongitude() != null) {
            latitude = Double.parseDouble(user.getLatitude());
            longitude = Double.parseDouble(user.getLongitude());
            LatLng location = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
        }
        Picasso.with(activity).load(user.getQrCodeImage()).into(imageViewProfile);
        textViewEmail.setText(user.getEmail());
        textViewName.setText(user.getUserName());
      //  textViewPrice.setText(user.getCashWallet()+" Rs");

        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString youPaidMessage = new SpannableString(user.getUserName().toUpperCase());
        youPaidMessage.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, (user.getUserName().toUpperCase()).length(), 0);
        builder.append(youPaidMessage);

        getSupportActionBar().setTitle(builder);
        textViewAddress.setText(user.getAddress());
        textViewState.setText(user.getState());
        textViewLocation.setText(user.getCity());

    }

    private void getUserInfo() {
        showProgressDialog(getString(R.string.loadingpleasewait));
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String email = SharedPref.getInstance().getString(Constants.USER_EMAIL);
        mDatabase.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    user = data.getValue(User.class);
                    break;
                }
                setAllData();
                hideProgressDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });
    }


    public void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;

            case MotionEvent.ACTION_UP:
                scrollView.requestDisallowInterceptTouchEvent(false);
                return true;

            case MotionEvent.ACTION_MOVE:
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            default:
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(ProfileActivity.this, EditProfileActicity.class);
                intent.putExtra("image", user.getQrCodeImage());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("name", user.getUserName());
                intent.putExtra("address", user.getAddress());
                intent.putExtra("state", user.getState());
                intent.putExtra("location", user.getCity());
                intent.putExtra("latitude", user.getLatitude());
                intent.putExtra("longitude", user.getLongitude());
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
