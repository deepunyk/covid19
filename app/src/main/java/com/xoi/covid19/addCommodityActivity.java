package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.HashMap;
import java.util.Map;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class addCommodityActivity extends AppCompatActivity {

    ExtendedEditText nameTxt, addTxt;
    MaterialSpinner typeSpin, distSpin;
    String typeArr[] = {"Grocery","Milk","Clinic","ATM","Pharmacy"};
    String districtArr[] = {"Udupi","Mangalore","Bangalore"};
    Button addBut;
    String name, type, lat,lon, address, district, url = "http://xtoinfinity.tech/maps/addLoc.php";
    ImageView navImg;
    ConstraintLayout parent_layout;
    LinearLayout linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity);

        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("long");
        address = getIntent().getStringExtra("add");

        addBut = (Button)findViewById(R.id.addBut);
        nameTxt = (ExtendedEditText)findViewById(R.id.nameTxt);
        addTxt = (ExtendedEditText)findViewById(R.id.addTxt);
        typeSpin = (MaterialSpinner)findViewById(R.id.typeSpin);
        distSpin = (MaterialSpinner)findViewById(R.id.distSpin);
        parent_layout = (ConstraintLayout) findViewById(R.id.parent_layout);
        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
        typeSpin.setItems(typeArr);
        distSpin.setItems(districtArr);
        addTxt.setText(address);

        navImg = (ImageView)findViewById(R.id.navImg);

        parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(addCommodityActivity.this);
            }
        });
        linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(addCommodityActivity.this);
            }
        });

        typeSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(addCommodityActivity.this);
            }
        });

        distSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(addCommodityActivity.this);
            }
        });

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
                        Intent go = new Intent(addCommodityActivity.this, MapsActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTxt.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(addCommodityActivity.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                }else {
                    type = typeArr[typeSpin.getSelectedIndex()];
                    district = districtArr[distSpin.getSelectedIndex()];
                    addCom();
                }
            }
        });
    }

    public void addCom(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(addCommodityActivity.this, "Added successfully, thank you for your support", Toast.LENGTH_SHORT).show();
                        Intent go = new Intent(addCommodityActivity.this, MapMainActivity.class);
                        startActivity(go);
                        finish();
                    }
                },
                new Response.ErrorListener() {
            
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addCommodityActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("type",type);
                params.put("name",name);
                params.put("lat",lat);
                params.put("long",lon);
                params.put("dist",district);
                params.put("add",address);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(addCommodityActivity.this, MainActivity.class);
        startActivity(go);
        finish();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
