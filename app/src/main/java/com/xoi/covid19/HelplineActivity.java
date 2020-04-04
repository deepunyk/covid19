package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelplineActivity extends AppCompatActivity {

    String url = "https://api.rootnet.in/covid19-in/contacts";
    private ArrayList<String> stateArr = new ArrayList<>();
    private ArrayList<String> numArr = new ArrayList<>();
    ImageView navImg;
    SpinKitView loadSpin;
    TextView loadTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        navImg = (ImageView)findViewById(R.id.navImg);

        loadSpin = (SpinKitView) findViewById(R.id.loadSpin);
        loadTxt = (TextView) findViewById(R.id.loadTxt);

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
                        Intent go = new Intent(HelplineActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });


        getDetails();
    }

    private void getDetails(){
        load();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HelplineActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(HelplineActivity.this);
        queue.add(stringRequest);
    }
    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce).getJSONObject("data").getJSONObject("contacts");
            JSONArray jarray = jobj.getJSONArray("regional");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                stateArr.add(jo.optString("loc"));
                numArr.add(jo.optString("number"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(HelplineActivity.this, ""+e, Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView() {
        doneLoad();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        HelplineAdapter adapter = new HelplineAdapter(stateArr,numArr,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(HelplineActivity.this,MainActivity.class);
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
