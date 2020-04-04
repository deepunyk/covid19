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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    ArrayList<String> linkArr = new ArrayList<>();
    ArrayList<String> imgArr = new ArrayList<>();
    ArrayList<String> textArr = new ArrayList<>();
    String url = "https://www.mygov.in/covid-19/";
    ImageView navImg;
    SpinKitView loadSpin;
    TextView loadTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

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
                        Intent go = new Intent(VideoActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        getDetails();
    }

    private void getDetails() {
        load();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document doc = Jsoup.parse(response);
                        Element head = doc.getElementsByClass("event-wrapper").first();
                        Elements li = head.getElementsByClass("event-item");
                        for(Element lisingle : li){
                            linkArr.add(lisingle.select("a").first().absUrl("href"));
                            imgArr.add(lisingle.select("img").first().absUrl("src"));
                            textArr.add((lisingle.getElementsByClass("quiz-participate_btn").first().text())+" (Activity)");
                        }
                        head = doc.getElementsByClass("event-wrapper").get(1);
                        li = head.getElementsByClass("event-item");
                        for(Element lisingle : li){
                            String link = lisingle.select("iframe").first().absUrl("src");
                            link = link.substring(link.indexOf("embed/")+6);
                            String actUrl = "https://img.youtube.com/vi/"+link+"/hqdefault.jpg";
                            linkArr.add("https://www.youtube.com/watch?v="+link);
                            imgArr.add(actUrl);
                            textArr.add((lisingle.getElementsByTag("p").first().text())+" (Video)");
                        }
                        initRecyclerView();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VideoActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void initRecyclerView() {
        doneLoad();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        videoAdapter adapter = new videoAdapter(textArr,imgArr,linkArr,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(VideoActivity.this, MainActivity.class);
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
