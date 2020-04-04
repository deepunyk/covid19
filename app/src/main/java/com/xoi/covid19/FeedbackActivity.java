package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    EditText feedTxt;
    Button submitBut;
    String feed;
    String name, type, lat,lon, url = "http://xtoinfinity.tech/maps/addFeedback.php";
    SpinKitView loadSpin;
    TextView loadTxt;
    ImageView navImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedTxt = (EditText)findViewById(R.id.feedTxt);
        submitBut = (Button) findViewById(R.id.submitBut);

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
                        Intent go = new Intent(FeedbackActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feed = feedTxt.getText().toString().trim();
                if(feed.equals("")){
                    Toast.makeText(FeedbackActivity.this, "Please enter something.", Toast.LENGTH_SHORT).show();
                }else{
                    putFeed();
                }
            }
        });
    }

    private void putFeed(){
        load();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        doneLoad();
                        Toast.makeText(FeedbackActivity.this, "Your feedback has been recorded, thank you for your support", Toast.LENGTH_SHORT).show();
                        Intent go = new Intent(FeedbackActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FeedbackActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("text",feed);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void load(){
        feedTxt.setFocusable(false);
        submitBut.setVisibility(View.GONE);
        loadTxt.setVisibility(View.VISIBLE);
        loadSpin.setVisibility(View.VISIBLE);
    }

    private void doneLoad(){
        feedTxt.setFocusable(true);
        submitBut.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.GONE);
        loadSpin.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(FeedbackActivity.this, MainActivity.class);
        startActivity(go);
        finish();
    }
}
