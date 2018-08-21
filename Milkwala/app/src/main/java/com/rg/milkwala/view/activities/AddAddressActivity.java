package com.rg.milkwala.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.AdressPrp;
import com.rg.milkwala.model.user.City.City;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.view.controls.TextViewBold;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.editTextAddess)
    EditText editTextAddess;
    @BindView(R.id.textViewCity)
    TextViewRegular textViewCity;
    @BindView(R.id.editTextState)
    EditText editTextState;
    @BindView(R.id.editTextPincode)
    EditText editTextPincode;
    @BindView(R.id.editTextCountry)
    EditText editTextCountry;
    @BindView(R.id.buttonUpdateNewAddress)
    Button buttonUpdateNewAddress;
    @BindView(R.id.textviewSelectlocation)
    TextView textviewSelectlocation;

    @BindView(R.id.textViewAddaddresss)
    TextViewBold textViewAddaddresss;
    List<City> cityList = new ArrayList<>();

    private AddAddressActivity activity;

    int PLACE_PICKER_REQUEST = 1;
    private String adress;
    private String latitude = "";
    private String longitude = "";

    String address = "";
    String city = "";
    String state = "";
    String pincode = "";
    String country = "";
    private CharSequence[] items;
    private String intentValue;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        activity = this;
        intentValue=getIntent().getStringExtra("value");
        setIntentData();
        getCityList();
        intentForLocation();
        mDatabase = FirebaseDatabase.getInstance().getReference("address");
    }

  public void  setIntentData(){
        if(intentValue.equalsIgnoreCase("addAddress")){
        }
      else{
            buttonUpdateNewAddress.setText(getString(R.string.update));
            textViewAddaddresss.setText(getString(R.string.updateAddress));
            editTextAddess.setText(getIntent().getStringExtra("address"));
            textViewCity.setText(getIntent().getStringExtra("city"));
            editTextState.setText(getIntent().getStringExtra("state"));
            editTextPincode.setText(getIntent().getStringExtra("pinCode"));
            editTextCountry.setText(getIntent().getStringExtra("country"));
            latitude=getIntent().getStringExtra("latitude");
            longitude=getIntent().getStringExtra("longitude");
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

    @OnClick(R.id.buttonUpdateNewAddress)
    public void updateInfo() {
        address = editTextAddess.getText().toString();
        city = textViewCity.getText().toString();
        state = editTextState.getText().toString();
        pincode = editTextPincode.getText().toString();
        country = editTextCountry.getText().toString();
        if (TextUtils.isEmpty(address.trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterAddress));
        } else if (TextUtils.isEmpty(city.trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterCity));
        } else if (TextUtils.isEmpty(state.trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterState));
        } else if (TextUtils.isEmpty(pincode.trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterPinCode));
        } else if (TextUtils.isEmpty(country.trim())) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterCountry));
        } else {
            if(buttonUpdateNewAddress.getText().toString().equalsIgnoreCase(getString(R.string.update))){
                String keyValue = getIntent().getStringExtra("key");
                updateData(keyValue);
            }
            else {
                updateData(mDatabase.push().getKey());
            }
        }
    }
    private void updateData(String key) {
        showProgressDialog(getString(R.string.loadingpleasewait));
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("address");
        AdressPrp addressPrp=new AdressPrp();
        addressPrp.setAddress(address);
        addressPrp.setState(state);
        addressPrp.setEmail(SharedPref.getInstance().getString(Constants.USER_EMAIL));
        addressPrp.setCity(city);
        addressPrp.setPincode(pincode);
        addressPrp.setCountry(country);
        addressPrp.setLatitude(latitude);
        addressPrp.setLongitude(longitude);
        mDatabase.child(key).setValue(addressPrp);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                hideProgressDialog();
                finish();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            hideProgressDialog();
            }
        });

    }

    public void intentForLocation() {
        textviewSelectlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(AddAddressActivity.this), PLACE_PICKER_REQUEST);
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
                adress = place.getAddress().toString();
                editTextAddess.setText(adress);
                LatLng latlong = place.getLatLng();
                latitude = latlong.latitude + "";
                longitude = latlong.longitude + "";
                editTextAddess.setEnabled(true);
                Log.e("latitiude is", latlong.latitude + "");
                Log.e("longitude is", latlong.longitude + "");
            }
        }
    }
    @OnClick(R.id.textViewCity)
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
                textViewCity.setText(items[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
