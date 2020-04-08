package com.xoi.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.webianks.library.scroll_choice.ScrollChoice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapMainActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    private TextView distTxt, locTxt;
    private boolean click = false;
    private RecyclerView recyclerView;
    private SharedPreferences sp;
    private String district = "", service = "All";
    Double userLat, userLong;
    String typeArr[] = {"All","Grocery","Milk","Clinic","ATM","Pharmacy"};
    String districtArr[] = {"Udupi","Mangalore","Bangalore"};
    private Button cancelBut, applyBut;
    private MaterialSpinner distSpin, serviceSpin;
    private FloatingActionButton filterFab, addFab;
    private LinearLayout filterLayout;
    private ConstraintLayout locLayout;
    private String url = "http://xtoinfinity.tech/maps/getMap.php";
    ArrayList<String> nameArr = new ArrayList<>();
    ArrayList<String> latArr = new ArrayList<>();
    ArrayList<String> longArr = new ArrayList<>();
    ArrayList<String> serArr = new ArrayList<>();
    ArrayList<String> addArr = new ArrayList<>();
    LottieAnimationView emptyAnim;
    TextView emptyTxt,loadTxt;
    SpinKitView loadSpin;
    String mainResponse = "";
    int PERMISSION_ID = 44;
    CardView mapsLayout;
    ImageView navImg;
    int state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);

        sp = this.getSharedPreferences("com.xoi.covid19",MODE_PRIVATE);

        navImg = (ImageView)findViewById(R.id.navImg);

        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navImg.animate().scaleX(0.85f).scaleY(0.85f);
                navImg.setAlpha(0.8f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navImg.setAlpha(1f);
                        navImg.animate().scaleX(1f).scaleY(1f);
                        Intent go = new Intent(MapMainActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        locTxt = (TextView)findViewById(R.id.locTxt);
        filterFab = (FloatingActionButton)findViewById(R.id.filterFab);
        addFab = (FloatingActionButton)findViewById(R.id.addFab);
        applyBut = (Button) findViewById(R.id.applyBut);
        cancelBut = (Button) findViewById(R.id.cancelBut);
        distSpin = (MaterialSpinner) findViewById(R.id.distSpin);
        serviceSpin = (MaterialSpinner) findViewById(R.id.serSpin);
        filterLayout = (LinearLayout) findViewById(R.id.filterLayout);
        locLayout = (ConstraintLayout) findViewById(R.id.locLayout);
        mapsLayout = (CardView) findViewById(R.id.mapsLayout);
        emptyAnim = (LottieAnimationView)findViewById(R.id.emptyanim);
        emptyTxt = (TextView) findViewById(R.id.emptyTxt);
        loadTxt = (TextView) findViewById(R.id.loadTxt);
        loadSpin = (SpinKitView) findViewById(R.id.loadSpin);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        if(sp.contains("district")){
            district = sp.getString("district","");
            locTxt.setText(district);
            showOne();
            getShops();
        }else{
            loadTxt.setVisibility(View.GONE);
            loadSpin.setVisibility(View.GONE);
            showTwo();
        }

        distSpin.setItems(districtArr);
        serviceSpin.setItems(typeArr);

        locLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTwo();
                state = 1;
            }
        });

        applyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 0;
                emptyTxt.setVisibility(View.GONE);
                emptyAnim.setVisibility(View.GONE);
                district = districtArr[distSpin.getSelectedIndex()];
                service = typeArr[serviceSpin.getSelectedIndex()];
                sp.edit().putString("district",district).apply();
                locTxt.setText(district);
                showOne();
                getShops();
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 0;
                if(mainResponse.equals("no")){
                    emptyTxt.setVisibility(View.VISIBLE);
                    emptyAnim.setVisibility(View.VISIBLE);
                }
                showOne();
            }
        });

        filterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 1;
                showTwo();
            }
        });

        mapsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapMainActivity.this, MapsActivity.class);
                i.putExtra("userLat", userLat);
                i.putExtra("userLong",userLong);
                i.putExtra("action","show");
                i.putStringArrayListExtra("name",nameArr);
                i.putStringArrayListExtra("lat",latArr);
                i.putStringArrayListExtra("long",longArr);
                i.putStringArrayListExtra("ser",serArr);
                i.putStringArrayListExtra("add",addArr);
                startActivity(i);
            }
        });
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapMainActivity.this, MapsActivity.class);
                i.putExtra("userLat", userLat);
                i.putExtra("userLong",userLong);
                i.putExtra("action","add");
                i.putStringArrayListExtra("name",nameArr);
                i.putStringArrayListExtra("lat",latArr);
                i.putStringArrayListExtra("long",longArr);
                i.putStringArrayListExtra("ser",serArr);
                i.putStringArrayListExtra("add",addArr);
                startActivity(i);
            }
        });
    }

    private void getShops(){
        clearArr();
        emptyTxt.setVisibility(View.GONE);
        emptyAnim.setVisibility(View.GONE);
        load();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"?dis="+district+"&type="+service,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mainResponse = response;
                        if(response.equals("no")){
                            doneLoad();
                            mapsLayout.setVisibility(View.GONE);
                            emptyTxt.setVisibility(View.VISIBLE);
                            emptyAnim.setVisibility(View.VISIBLE);
                        }else {
                            parseItems(response);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapMainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(MapMainActivity.this);
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("map");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                nameArr.add(jo.optString("Name"));
                serArr.add(jo.optString("Type"));
                latArr.add(jo.optString("Lat"));
                longArr.add(jo.optString("Lon"));
                addArr.add(jo.optString("Address"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initRecyclerView() {
        doneLoad();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        MapMainAdapter adapter = new MapMainAdapter(serArr,nameArr,addArr,latArr,longArr,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if(state == 0) {
            Intent go = new Intent(MapMainActivity.this, MainActivity.class);
            startActivity(go);
            finish();
        }
        else{
            showOne();
            state = 0;
        }
    }

    private void load(){
        loadTxt.setVisibility(View.VISIBLE);
        loadSpin.setVisibility(View.VISIBLE);
    }

    private void doneLoad(){
        loadTxt.setVisibility(View.GONE);
        loadSpin.setVisibility(View.GONE);
    }

    private void showTwo(){
        addFab.hide();
        mapsLayout.setVisibility(View.GONE);
        emptyTxt.setVisibility(View.GONE);
        emptyAnim.setVisibility(View.GONE);
        locLayout.setVisibility(View.GONE);
        filterFab.hide();
        filterLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        if(sp.contains("district")){
            cancelBut.setVisibility(View.VISIBLE);
        }else{
            cancelBut.setVisibility(View.GONE);
        }
    }

    private void showOne(){
        addFab.show();
        mapsLayout.setVisibility(View.VISIBLE);
        locLayout.setVisibility(View.VISIBLE);
        filterFab.show();
        filterLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void clearArr(){
        nameArr.clear();
        latArr.clear();
        longArr.clear();
        serArr.clear();
        addArr.clear();
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
}
