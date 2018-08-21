package com.rg.milkwala.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
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
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.model.user.City.City;
import com.rg.milkwala.view.controls.EditTextRegular;
import com.rg.milkwala.view.controls.TextViewRegular;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActicity extends BaseActivity implements OnMapReadyCallback, View.OnTouchListener {
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.imagetrans)
    ImageView imagetrans;

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;

    @BindView(R.id.textViewEmail)
    TextViewRegular textViewEmail;

    @BindView(R.id.textViewName)
    EditTextRegular editTextName;

    @BindView(R.id.textViewAddress)
    EditTextRegular editTextAddress;

    @BindView(R.id.textViewLocation)
    TextViewRegular textViewLocation;

    @BindView(R.id.textViewState)
    EditTextRegular editTextState;

    @BindView(R.id.textviewSelectlocation)
    TextViewRegular textviewSelectlocation;
    private double latitude;
    private double longitude;
    private EditProfileActicity activity;
    private GoogleMap mMap;
    private CharSequence[] items;
    int PLACE_PICKER_REQUEST = 1;
    List<City> cityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_acticity);
        ButterKnife.bind(this);
        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imagetrans.setOnTouchListener(this);
        setMap();
        getCityList();
        setAlldata();
    }

    public void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void setAlldata() {
        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        Picasso.with(activity).load(getIntent().getStringExtra("image")).into(imageViewProfile);
        textViewEmail.setText(getIntent().getStringExtra("email"));
        editTextName.setText(getIntent().getStringExtra("name"));
        editTextName.requestFocus();
        editTextAddress.setText(getIntent().getStringExtra("address"));
        textViewLocation.setText(getIntent().getStringExtra("location"));
        editTextState.setText(getIntent().getStringExtra("state"));


        // set custom color to the titlte
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString youPaidMessage = new SpannableString(getString(R.string.EditProfile).toUpperCase());
        youPaidMessage.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, (getString(R.string.EditProfile).length()), 0);
        builder.append(youPaidMessage);
        getSupportActionBar().setTitle(builder);
        //

    }

    private void updateProfile() {
        if (TextUtils.isEmpty(editTextName.getText().toString().trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseenterusername));
        } else if (TextUtils.isEmpty(editTextAddress.getText().toString().trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterAddress));
        } else if (TextUtils.isEmpty(editTextState.getText().toString().trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterState));
        } else if (TextUtils.isEmpty(textViewLocation.getText().toString().trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterCity));
        } else {
            updateInfo();
        }
    }

    private void updateInfo() {

        showProgressDialog(getString(R.string.loadingpleasewait));
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        // Creating new user node, which returns the unique key value
        // new user node would be /users/$userid/
        mDatabase.orderByChild("email").equalTo(getIntent().getStringExtra("email")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgressDialog();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    mDatabase.child(child.getKey()).child("address").setValue(editTextAddress.getText().toString());
                    mDatabase.child(child.getKey()).child("city").setValue(textViewLocation.getText().toString());
                    mDatabase.child(child.getKey()).child("state").setValue(editTextState.getText().toString());
                    mDatabase.child(child.getKey()).child("userName").setValue(editTextName.getText().toString());
                    hideProgressDialog();
                    finish();
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
                Log.e("error", databaseError.getMessage());
                Dialogs.getInstance().showtoast(activity, getString(R.string.databaseerror));
                hideProgressDialog();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateInfoAction:
                updateProfile();
                break;
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
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

    private void getCityList() {
        showProgressDialog(getString(R.string.loadingpleasewait));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("city");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    City city = data.getValue(City.class);
                    cityList.add(city);
                }
                hideProgressDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.textViewLocation)
    public void onClickCity() {
        items = new CharSequence[cityList.size()];
        for (int i = 0; i < cityList.size(); i++) {
            items[i] = cityList.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.SelectyourCity)).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textViewLocation.setText(items[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @OnClick(R.id.textviewSelectlocation)
    public void intentForLocation() {
        textviewSelectlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(EditProfileActicity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String adress = place.getAddress().toString();
                editTextAddress.setText(adress);
                LatLng latlong = place.getLatLng();
                Log.e("latitiude is", latlong.latitude + "");
                Log.e("longitude is", latlong.longitude + "");
            }
        }
    }
}
