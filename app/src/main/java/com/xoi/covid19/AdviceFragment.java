package com.xoi.covid19;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
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
import com.thefinestartist.finestwebview.FinestWebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class AdviceFragment extends Fragment {

    View view;
    String url = "https://www.mohfw.gov.in/";
    private ArrayList<String> ArrImg = new ArrayList<>();
    private ArrayList<String> ArrLink = new ArrayList<>();
    AfpAdapter adapter;
    CardView ask, travel, protect, preg, ready,stress;
    Intent go;

    public AdviceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.advice_fragment, container, false);

        ask = (CardView)view.findViewById(R.id.askCard);
        travel = (CardView)view.findViewById(R.id.travelCard);
        protect = (CardView)view.findViewById(R.id.protectCard);
        preg = (CardView)view.findViewById(R.id.pregCard);
        ready = (CardView)view.findViewById(R.id.readyCard);
        stress = (CardView)view.findViewById(R.id.stressCard);

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ask.animate().scaleX(0.85f).scaleY(0.85f);
                ask.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ask.setAlpha(1f);
                        ask.animate().scaleX(1f).scaleY(1f);
                        go = new Intent(getActivity(), WhoActivity.class);
                        go.putExtra("type", "ask");
                        startActivity(go);
                        getActivity().finish();

                    }
                }, 100);
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel.animate().scaleX(0.85f).scaleY(0.85f);
                travel.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        travel.setAlpha(1f);
                        travel.animate().scaleX(1f).scaleY(1f);
                        go = new Intent(getActivity(), WhoActivity.class);
                        go.putExtra("type", "health");
                        startActivity(go);
                        getActivity().finish();

                    }
                }, 100);
            }
        });

        protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protect.animate().scaleX(0.85f).scaleY(0.85f);
                protect.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        protect.setAlpha(1f);
                        protect.animate().scaleX(1f).scaleY(1f);
                        go = new Intent(getActivity(), WhoActivity.class);
                        go.putExtra("type", "protect");
                        startActivity(go);
                        getActivity().finish();

                    }
                }, 100);
            }
        });

        preg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preg.animate().scaleX(0.85f).scaleY(0.85f);
                preg.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preg.setAlpha(1f);
                        preg.animate().scaleX(1f).scaleY(1f);
                        go = new Intent(getActivity(), WhoActivity.class);
                        go.putExtra("type", "preg");
                        startActivity(go);
                        getActivity().finish();

                    }
                }, 100);
            }
        });

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready.animate().scaleX(0.85f).scaleY(0.85f);
                ready.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ready.setAlpha(1f);
                        ready.animate().scaleX(1f).scaleY(1f);
                        go = new Intent(getActivity(), WhoActivity.class);
                        go.putExtra("type", "ready");
                        startActivity(go);
                        getActivity().finish();

                    }
                }, 100);
            }
        });

        stress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stress.animate().scaleX(0.85f).scaleY(0.85f);
                stress.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stress.setAlpha(1f);
                        stress.animate().scaleX(1f).scaleY(1f);
                        go = new Intent(getActivity(), WhoActivity.class);
                        go.putExtra("type", "stress");
                        startActivity(go);
                        getActivity().finish();

                    }
                }, 100);
            }
        });

        getDetails();
        return view;
    }

    private void getDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearArray();
                        Document doc = Jsoup.parse(response);
                        Elements heads = doc.getElementsByClass("grid element-item english");
                        for(Element head: heads){
                            ArrLink.add(head.select("a").first().absUrl("href"));
                            ArrImg.add("https://www.mohfw.gov.in/"+ head.select("img").first().attr("src"));
                        }
                        initRecyclerView();
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

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        //LinearLayoutManager lm  = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager(lm);
        adapter = new AfpAdapter(ArrLink,ArrImg,getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void clearArray(){
        ArrImg.clear();
        ArrLink.clear();
    }
}
