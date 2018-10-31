package com.schoolshieldparent_ui.view.activity;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.location.AllLocation;
import com.schoolshieldparent_ui.model.location.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Location extends AppCompatActivity implements OnMapReadyCallback {

    public static Activity_Location instance;
    private GoogleMap googleMap;
    @BindView(R.id.textViewlocation)
    TextView textViewlocation;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.normal)
    Button normal;
    @BindView(R.id.satelite)
    Button satelite;
    @BindView(R.id.hybrid)
    Button hybrid;
    private String address = "";
    private String city = "";
    private String state = "";
    private String country = "";
    List<Result> AllResult = new ArrayList<>();
    List<AllLocation> Toplocations = new ArrayList<>();
    boolean latest = true;
    private Dialog dialogAlert;
    private String newdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        instance = this;
        textView11.setText(getString(R.string.location));
        normal.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mapInitialization();
    }

    private void setMarkerOnMap() {
        if (latest == true) {
            textViewlocation.setText(getString(R.string.allLocation));
            setLocationOnMap(AllResult.get(0).getTop10());
            latest = false;
        }
    }


    @OnClick(R.id.textViewlocation)
    public void textViewClick() {
        if (latest == true) {
            textViewlocation.setText(getString(R.string.allLocation));
            setLocationOnMap(AllResult.get(0).getTop10());
            latest = false;
        } else {
            textViewlocation.setText(getString(R.string.latest));
            setLocationOnMap(AllResult.get(0).getAllLocation());
            latest = true;
        }
    }

    @OnClick(R.id.normal)
    public void setNormal() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        normal.setBackgroundColor(getResources().getColor(R.color.tab_background_selected));
        satelite.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
        hybrid.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));

    }

    @OnClick(R.id.satelite)
    public void setSatelite() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        normal.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
        satelite.setBackgroundColor(getResources().getColor(R.color.tab_background_selected));
        hybrid.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
    }

    @OnClick(R.id.hybrid)
    public void setHyprid() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        normal.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
        satelite.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
        hybrid.setBackgroundColor(getResources().getColor(R.color.tab_background_selected));
    }

    private void getLocations() {
        DialogManager.startProgressDialog(this);
        WebServiceResult.StudentsLocation(MyApplication.currentChildID, TimeZone.getDefault().getID()+"");
    }

    private void mapInitialization() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocations();
    }

    public static Activity_Location getInstance() {
        return instance;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setTrafficEnabled(true);
        this.googleMap.setIndoorEnabled(true);
        this.googleMap.setBuildingsEnabled(true);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);

        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setTitle(getAddress(marker.getPosition().latitude, marker.getPosition().longitude));
                return false;
            }
        });


    }

    public void updateLocations(Result result) {
        AllResult.clear();
        AllResult.add(result);
        setMarkerOnMap();

    }

    private void setLocationOnMap(List<AllLocation> allLocation) {
        if (allLocation.size() == 0) {
            SimpleAlertDialog.showSimpleAlertDialog(this, getString(R.string.locationNotFound));
        } else {
            googleMap.clear();
            for (int i = 0; i < allLocation.size(); i++) {

                if (i == 0) {
                    final LatLng newMarkerLatLong = new LatLng(Double.parseDouble(allLocation.get(i).getLatitude()), Double.parseDouble(allLocation.get(i).getLongitude()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newMarkerLatLong, 25));
                    googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);
                    Marker newMarker = googleMap.addMarker(new MarkerOptions().position(newMarkerLatLong));
                    newMarker.setSnippet(changedateFormat(allLocation.get(i).getDate()));
                    newMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
                    newMarker.showInfoWindow();
                } else {
                    final LatLng newMarkerLatLong = new LatLng(Double.parseDouble(allLocation.get(i).getLatitude()), Double.parseDouble(allLocation.get(i).getLongitude()));
                    Marker newMarker = googleMap.addMarker(new MarkerOptions().position(newMarkerLatLong));
                    newMarker.setSnippet(changedateFormat(allLocation.get(i).getDate()));
                    newMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
                    newMarker.showInfoWindow();
                }
            }
        }
    }

    private String changedateFormat(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = format.parse(date);

            format = new SimpleDateFormat("hh:mm:ss,yyyy-MM-dd ");
            newdate = format.format(newDate);
        } catch (Exception e) {
        }
        return newdate;
    }


    public String getAddress(double latitude, double longitude) {
        String completeAddress="";

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
              completeAddress = address + ", " + city + ", " + state + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
            completeAddress=getString(R.string.unknownlocation);
        }
        return completeAddress;
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        finish();
        overridePendingTransition(0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

    }

}
