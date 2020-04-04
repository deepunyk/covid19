package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    ExtendedEditText nameTxt;
    MaterialSpinner typeSpin;
    String typeArr[] = {"Grocery","Milk","Hospital"};
    Button addBut;
    String name, type, lat,lon, url = "http://xtoinfinity.tech/maps/addLocation.php";
    ImageView navImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity);

        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("long");

        addBut = (Button)findViewById(R.id.addBut);
        nameTxt = (ExtendedEditText)findViewById(R.id.nameTxt);
        typeSpin = (MaterialSpinner)findViewById(R.id.typeSpin);
        typeSpin.setItems(typeArr);

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
                        Intent go = new Intent(addCommodityActivity.this, MapsActivity.class);
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
}
