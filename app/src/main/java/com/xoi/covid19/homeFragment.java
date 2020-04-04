package com.xoi.covid19;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.thefinestartist.finestwebview.FinestWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {

    View view;
    SpinKitView loadSpin;
    TextView loadTxt;
    String url = "https://www.mohfw.gov.in/";
    TextView confirmTxt, recoverTxt, deathTxt, dateTxt;
    private ArrayList<String> confirmed = new ArrayList<>();
    private ArrayList<String> recovered = new ArrayList<>();
    private ArrayList<String> deaths = new ArrayList<>();
    private ArrayList<String> state = new ArrayList<>();
    private ArrayList<String> num = new ArrayList<>();
    int count = 1;
    Button moreBut;
    MaterialSpinner stateSpin;
    AnyChartView stateChart;
    List<DataEntry> stateData = new ArrayList<>();

    public homeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        confirmTxt = (TextView)view.findViewById(R.id.confirmTxt);
        recoverTxt = (TextView)view.findViewById(R.id.recoverTxt);
        deathTxt = (TextView)view.findViewById(R.id.deathTxt);
        dateTxt = (TextView)view.findViewById(R.id.dateTxt);
        moreBut = (Button) view.findViewById(R.id.moreBut);
        stateSpin = (MaterialSpinner)view.findViewById(R.id.stateSpin);
        stateChart = (AnyChartView)view.findViewById(R.id.stateChart);
        loadSpin = (SpinKitView) view.findViewById(R.id.loadSpin);
        loadTxt = (TextView) view.findViewById(R.id.loadTxt);

        moreBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(getActivity()).show("https://www.mohfw.gov.in/");
            }
        });

        stateSpin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                confirmTxt.setText(confirmed.get(position));
                recoverTxt.setText(recovered.get(position));
                deathTxt.setText(deaths.get(position));
            }
        });
        clearArr();
        getDetails();
        return view;
    }

    private void getDetails() {
        load();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        doneLoad();
                        Document doc = Jsoup.parse(response);
                        Element up = doc.getElementsByClass("table table-striped").first();
                        Element tbod = up.getElementsByTag("tbody").first();
                        Elements trs = tbod.getElementsByTag("tr");
                        for(Element tr : trs){
                            Elements tds = tr.getElementsByTag("td");
                            try {
                                if(tds.size()==5) {
                                    num.add(tds.get(0).text());
                                    state.add(tds.get(1).text());
                                    confirmed.add(tds.get(2).text());
                                    recovered.add(tds.get(3).text());
                                    deaths.add(tds.get(4).text());
                                }else if(tds.size()==4){
                                    num.add("0");
                                    state.add("Total");
                                    confirmed.add(tds.get(1).text());
                                    recovered.add(tds.get(2).text());
                                    deaths.add(tds.get(3).text());
                                }
                            }catch (Exception e){

                            }
                            confirmTxt.setText(confirmed.get(confirmed.size()-1));
                            recoverTxt.setText(recovered.get(recovered.size()-1));
                            deathTxt.setText(deaths.get(deaths.size()-1));
                        }
                        stateSpin.setItems(state);
                        stateSpin.setSelectedIndex(state.size()-1);

                        for(int i = 0; i< state.size()-1;i++){

                            stateData.add(new ValueDataEntry(state.get(i),Integer.parseInt(confirmed.get(i))));
                        }
                        Pie pie;
                        pie = AnyChart.pie();

                        pie.data(stateData);
                        stateChart.setChart(pie);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void clearArr(){
        num.clear();
        state.clear();
        confirmed.clear();
        recovered.clear();
        deaths.clear();
    }

    private void load(){
        loadTxt.setVisibility(View.VISIBLE);
        loadSpin.setVisibility(View.VISIBLE);
    }

    private void doneLoad(){
        loadTxt.setVisibility(View.GONE);
        loadSpin.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        clearArr();
    }
}

