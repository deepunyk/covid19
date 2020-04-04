package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.google.android.gms.common.data.DataHolder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DonateActivity extends AppCompatActivity {

    ArrayList<String> nameArr = new ArrayList<>();
    ArrayList<String> typeArr = new ArrayList<>();
    ArrayList<String> descArr = new ArrayList<>();
    ArrayList<String> linkArr = new ArrayList<>();
    String url = "https://www.investindia.gov.in/bip/resources/state-and-national-relief-funds-accepting-donations-covid-19";
    ImageView navImg;
    SpinKitView loadSpin;
    TextView loadTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

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
                        Intent go = new Intent(DonateActivity.this, MainActivity.class);
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
                        Element head = doc.getElementsByClass("Table").first();
                        Elements trs = head.getElementsByTag("tr");

                        for(Element tr : trs){
                            String descstr = "";
                            Elements td = tr.getElementsByTag("td");
                            nameArr.add(td.get(0).text());
                            typeArr.add(td.get(1).text());
                            for(Element p : td.get(2).getElementsByTag("p"))
                            {
                                if(p.text().equals("LINK")){

                                }else {
                                    descstr += p.text() + "\n\n";
                                }
                            }
                            try {
                                for (Element p : td.get(2).getElementsByTag("li")) {
                                    descstr += p.text() + "\n\n";
                                }
                            }catch (Exception e){

                            }
                            if(descstr.equals("")){
                                descstr = td.get(2).text();
                            }
                            descArr.add(descstr);
                            try {
                                linkArr.add(td.select("a").first().absUrl("href"));
                            }catch (Exception e){
                                linkArr.add("");
                            }
                        }
                        nameArr.remove(0);
                        typeArr.remove(0);
                        descArr.remove(0);
                        linkArr.remove(0);
                        initRecyclerView();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DonateActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
        donateAdapter adapter = new donateAdapter(nameArr,typeArr,descArr,linkArr,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        Intent go = new Intent(DonateActivity.this, MainActivity.class);
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
