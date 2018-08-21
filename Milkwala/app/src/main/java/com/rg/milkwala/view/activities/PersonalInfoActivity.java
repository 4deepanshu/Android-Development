package com.rg.milkwala.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.WriterException;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.City.City;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.model.user.User;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalInfoActivity extends BaseActivity {
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
    @BindView(R.id.buttonUpdateInfo)
    Button buttonUpdateInfo;
    @BindView(R.id.textviewSelectlocation)
    TextView textviewSelectlocation;
    private PersonalInfoActivity activity;
    int PLACE_PICKER_REQUEST = 1;
    private String adress;
    private String latitude = "";
    private String longitude = "";
    User user;
    public String updateon;
    public CharSequence[] items;
    private String useremail;
    List<City> cityList = new ArrayList<>();


    FirebaseStorage storage = FirebaseStorage.getInstance();
    //creating a storage reference. Replace the below URL with your Firebase storage URL.
    StorageReference storageRef = storage.getReferenceFromUrl(Constants.FIREBASE_BUCKET);


    String address = "";
    String city = "";
    String state = "";
    String pincode = "";
    String country = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        activity = this;
        intentForLocation();
        useremail = getIntent().getStringExtra("email");
        getCityList();
        currentdate();
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


    @OnClick(R.id.buttonUpdateInfo)
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
            updatedata();
        }
    }

    private void updatedata() {


        //region Set QR Code Size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        //endregion

        QRGEncoder qrgEncoder = new QRGEncoder(useremail, null, QRGContents.Type.TEXT, smallerDimension);
        try {
            // Getting QR-Code as Bitmap
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            uploadQrCode(bitmap);
        } catch (WriterException e) {
            Log.v("QR ERROR", e.toString());
        }


    }


    private void uploadQrCode(Bitmap bitmap) {

        StorageReference myfileRef = storageRef.child(getIntent().getStringExtra("email")+".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = myfileRef.putBytes(data);
        showProgressDialog(getString(R.string.loadingpleasewait));
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                hideProgressDialog();
                Toast.makeText(PersonalInfoActivity.this, R.string.unabletocreateqr, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            //    hideProgressDialog();
                String QR_URL = taskSnapshot.getDownloadUrl().toString();
                updateProfile(QR_URL);
            }
        });
    }


    private void updateProfile(final String qrCodeImage) {

       // showProgressDialog(getString(R.string.loadingpleasewait));
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        // Creating new user node, which returns the unique key value
        // new user node would be /users/$userid/
        mDatabase.orderByChild("email").equalTo(useremail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgressDialog();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    mDatabase.child(child.getKey()).child("address").setValue(address);
                    mDatabase.child(child.getKey()).child("city").setValue(city);
                    mDatabase.child(child.getKey()).child("state").setValue(state);
                    mDatabase.child(child.getKey()).child("pincode").setValue(pincode);
                    mDatabase.child(child.getKey()).child("country").setValue(country);
                    mDatabase.child(child.getKey()).child("latitude").setValue(latitude);
                    mDatabase.child(child.getKey()).child("longitude").setValue(longitude);
                    mDatabase.child(child.getKey()).child("updateOn").setValue(updateon);
                    mDatabase.child(child.getKey()).child("deviceToken").setValue(FirebaseInstanceId.getInstance().getToken());
                    mDatabase.child(child.getKey()).child("qrCodeImage").setValue(qrCodeImage);
                    hideProgressDialog();
                    SharedPref.getInstance().setString(Constants.USER_NAME, getIntent().getStringExtra("userName"));
                    SharedPref.getInstance().setString(Constants.USER_ID, getIntent().getStringExtra("userId"));
                    SharedPref.getInstance().setString(Constants.USER_EMAIL, getIntent().getStringExtra("email"));
                    SharedPref.getInstance().setString(Constants.USER_PASSWORD, getIntent().getStringExtra("password"));
                    startActivity(new Intent(PersonalInfoActivity.this, HomeActivity.class));
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


    public void intentForLocation() {
        textviewSelectlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(PersonalInfoActivity.this), PLACE_PICKER_REQUEST);
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

    public void currentdate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        updateon = simpleDateFormat.format(new Date());
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
