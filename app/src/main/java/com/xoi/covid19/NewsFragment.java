package com.xoi.covid19;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class NewsFragment  extends Fragment {

    View view;
    String url = "https://www.mohfw.gov.in/";
    private ArrayList<String> titleArr = new ArrayList<>();
    private ArrayList<String> dateArr = new ArrayList<>();
    private ArrayList<String> linkArr = new ArrayList<>();
    SpinKitView loadSpin;
    TextView loadTxt;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

        loadSpin = (SpinKitView) view.findViewById(R.id.loadSpin);
        loadTxt = (TextView) view.findViewById(R.id.loadTxt);
        getDetails();
        return view;
    }

    private void getDetails() {
        load();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearArray();
                        Document doc = Jsoup.parse(response);
                        Elements up = doc.getElementsByClass("update-box");
                        for (Element head : up) {
                            Elements td = head.getElementsByTag("strong");
                            dateArr.add(td.get(0).text());
                            Element link = head.select("a").first();
                            String absHref = link.attr("abs:href");
                            linkArr.add(absHref);
                            Element titleEle = head.select("a").first();
                            titleArr.add(titleEle.text());
                        }
                        initRecyclerView();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);

    }

    private void initRecyclerView() {
        doneLoad();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        NewsAdapter adapter = new NewsAdapter(titleArr, linkArr, dateArr, getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void clearArray() {
        dateArr.clear();
        titleArr.clear();
        linkArr.clear();
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