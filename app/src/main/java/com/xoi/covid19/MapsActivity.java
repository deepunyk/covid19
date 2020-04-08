package com.xoi.covid19;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    Double userLat, userLong, selectLat, selectLong, selectLatLong;
    LatLng userLatLng;
    MarkerOptions usermo, selectmo;
    Marker userMarker, selectMarker;
    ArrayList<String> nameArr = new ArrayList<>();
    ArrayList<String> latArr = new ArrayList<>();
    ArrayList<String> longArr = new ArrayList<>();
    ArrayList<String> addArr = new ArrayList<>();
    ArrayList<String> typeArr = new ArrayList<>();
    String url = "http://xtoinfinity.tech/maps/getMap.php";
    String city = "";
    FloatingActionButton fab, locFab, helpFab;
    ImageView navImg;
    Button confirmBut;
    CardView toolCard;
    int check = 0;
    SpinKitView loadSpin;
    TextView loadTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(this);

        loadSpin = (SpinKitView) findViewById(R.id.loadSpin);
        loadTxt = (TextView) findViewById(R.id.loadTxt);

        doneLoad();

        navImg = (ImageView)findViewById(R.id.navImg);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        locFab = (FloatingActionButton)findViewById(R.id.locFab);
        helpFab = (FloatingActionButton)findViewById(R.id.helpFab);
        confirmBut = (Button)findViewById(R.id.confirmBut);
        toolCard = (CardView) findViewById(R.id.toolCard);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.hide();
                locFab.hide();
                helpFab.hide();
                check = 1;
                toolCard.setVisibility(View.VISIBLE);
                userMarker.setVisible(true);
            }
        });

        helpFab.hide();

        locFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });

        helpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpFab.hide();
            }
        });

        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.show();
                locFab.show();
                check = 0;
                confirmBut.setVisibility(View.GONE);
                toolCard.setVisibility(View.GONE);
                helpFab.show();
                try {
                    selectMarker.remove();
                }catch (Exception e){

                }
            }
        });

        confirmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "";
                Intent go = new Intent(MapsActivity.this, addCommodityActivity.class);
                go.putExtra("lat", selectLat.toString());
                go.putExtra("long", selectLong.toString());
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(selectLat, selectLong, 1);
                    address = addresses.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go.putExtra("add", address);
                startActivity(go);
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            userLat = extras.getDouble("userLat");
            userLong = extras.getDouble("userLong");
            nameArr = extras.getStringArrayList("name");
            typeArr = extras.getStringArrayList("ser");
            latArr = extras.getStringArrayList("lat");
            longArr = extras.getStringArrayList("long");
            addArr = extras.getStringArrayList("add");
            setMarkers();
            BitmapDescriptor food = BitmapDescriptorFactory.fromResource(R.drawable.mapuser);
            userLatLng = new LatLng(userLat, userLong);
            usermo = new MarkerOptions().position(userLatLng);
            userMarker = mMap.addMarker(usermo.icon(food).title("Your current location"));
            userMarker.setPosition(userLatLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,18));
            if(extras.getString("action").equals("add")){
                fab.callOnClick();
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(check == 1) {try {
                    selectMarker.remove();
                }catch (Exception e){

                }
                    selectLat = latLng.latitude;
                    selectLong = latLng.longitude;
                    selectmo = new MarkerOptions().position(latLng);
                    selectMarker = mMap.addMarker(selectmo);
                    confirmBut.setVisibility(View.VISIBLE);
                    selectMarker.setPosition(latLng);
                }

            }
        });

    }


    private void setMarkers(){
        BitmapDescriptor milk = BitmapDescriptorFactory.fromResource(R.drawable.milk);
        BitmapDescriptor health = BitmapDescriptorFactory.fromResource(R.drawable.health);
        BitmapDescriptor food = BitmapDescriptorFactory.fromResource(R.drawable.groceries);
        BitmapDescriptor atm = BitmapDescriptorFactory.fromResource(R.drawable.atm);
        BitmapDescriptor pharmacy = BitmapDescriptorFactory.fromResource(R.drawable.drug);

            for (int i = 0; i < nameArr.size(); i++) {
                try {
                LatLng mark = new LatLng(Double.parseDouble(latArr.get(i)), Double.parseDouble(longArr.get(i)));
                if (typeArr.get(i).equals("Milk")) {
                    mMap.addMarker(new MarkerOptions().position(mark).title(nameArr.get(i)).icon(milk));
                } else if (typeArr.get(i).equals("Clinic")) {
                    mMap.addMarker(new MarkerOptions().position(mark).title(nameArr.get(i)).icon(health));
                }else if (typeArr.get(i).equals("ATM")) {
                    mMap.addMarker(new MarkerOptions().position(mark).title(nameArr.get(i)).icon(atm));
                }else if (typeArr.get(i).equals("Pharmacy")) {
                    mMap.addMarker(new MarkerOptions().position(mark).title(nameArr.get(i)).icon(pharmacy));
                } else if (typeArr.get(i).equals("Grocery")) {
                    mMap.addMarker(new MarkerOptions().position(mark).title(nameArr.get(i)).icon(food));
                } else {
                    mMap.addMarker(new MarkerOptions().position(mark).title(nameArr.get(i)));
                }
                }catch (Exception e){
                    Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
                }
            }

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    userLat = location.getLatitude();
                                    userLong = location.getLongitude();
                                    BitmapDescriptor food = BitmapDescriptorFactory.fromResource(R.drawable.mapuser);
                                    userLatLng = new LatLng(userLat, userLong);
                                    usermo = new MarkerOptions().position(userLatLng);
                                    userMarker = mMap.addMarker(usermo.icon(food).title("Your current location"));
                                    userMarker.setPosition(userLatLng);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,18));
                                }
                            }
                        }
                );
            } else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            userLat = mLastLocation.getLatitude();
            userLong = mLastLocation.getLongitude();
            BitmapDescriptor food = BitmapDescriptorFactory.fromResource(R.drawable.mapuser);
            userLatLng = new LatLng(userLat, userLong);
            usermo = new MarkerOptions().position(userLatLng);
            userMarker = mMap.addMarker(usermo.icon(food).title("Your current location"));
            userMarker.setPosition(userLatLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,18));
        }
    };


    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(MapsActivity.this,MapMainActivity.class);
        startActivity(go);
        finish();
    }

    private void load(){
        loadTxt.setVisibility(View.VISIBLE);
        loadSpin.setVisibility(View.VISIBLE);
    }

    private void doneLoad(){
        loadTxt.setVisibility(View.GONE);
        loadSpin.setVisibility(View.GONE);
    }
}
