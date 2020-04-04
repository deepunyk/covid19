package com.xoi.covid19;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.w3c.dom.Text;

import java.util.ArrayList;

import io.github.angebagui.mediumtextview.MediumTextView;

public class AfpFragment extends Fragment {

    View view;
    String url = "https://www.mohfw.gov.in/";
    private ArrayList<String> ArrImg = new ArrayList<>();
    private ArrayList<String> ArrLink = new ArrayList<>();
    AfpAdapter adapter;
    TextView titleTxt;
    CardView maskCard, parentCard, mythCard, workCard;
    SpinKitView loadSpin;
    TextView loadTxt;
    LinearLayout awareCard, afpCard;

    public AfpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_afp, container, false);

        titleTxt = (TextView)view.findViewById(R.id.titleTxt);
        maskCard = (CardView)view.findViewById(R.id.maskCard);
        workCard = (CardView)view.findViewById(R.id.workCard);
        parentCard = (CardView)view.findViewById(R.id.parentCard);
        mythCard = (CardView)view.findViewById(R.id.mythCard);

        awareCard = (LinearLayout) view.findViewById(R.id.awareCard);
        afpCard = (LinearLayout) view.findViewById(R.id.whoCard);


        loadSpin = (SpinKitView) view.findViewById(R.id.loadSpin);
        loadTxt = (TextView) view.findViewById(R.id.loadTxt);

        awareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awareCard.animate().scaleX(0.85f).scaleY(0.85f);
                awareCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        awareCard.setAlpha(1f);
                        awareCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(getActivity()).show("https://www.mohfw.gov.in/");

                    }
                }, 100);
            }
        });

        afpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afpCard.animate().scaleX(0.85f).scaleY(0.85f);
                afpCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        afpCard.setAlpha(1f);
                        afpCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(getActivity()).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public");

                    }
                }, 100);
            }
        });

        maskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maskCard.animate().scaleX(0.85f).scaleY(0.85f);
                maskCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        maskCard.setAlpha(1f);
                        maskCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(getActivity()).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/when-and-how-to-use-masks");

                    }
                }, 100);
            }
        });

        workCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workCard.animate().scaleX(0.85f).scaleY(0.85f);
                workCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        workCard.setAlpha(1f);
                        workCard.animate().scaleX(1f).scaleY(1f);
                        String pdfurl = "https://www.who.int/docs/default-source/coronaviruse/getting-workplace-ready-for-covid-19.pdf?sfvrsn=359a81e7_6";
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(pdfurl);
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setVisibleInDownloadsUi(true);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
                            downloadManager.enqueue(request);
                        }


                    }
                }, 100);
            }
        });

        parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentCard.animate().scaleX(0.85f).scaleY(0.85f);
                parentCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        parentCard.setAlpha(1f);
                        parentCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(getActivity()).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/healthy-parenting");

                    }
                }, 100);
            }
        });

        mythCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mythCard.animate().scaleX(0.85f).scaleY(0.85f);
                mythCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mythCard.setAlpha(1f);
                        mythCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(getActivity()).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/myth-busters");

                    }
                }, 100);
            }
        });

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
        doneLoad();
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

    private void load(){
        loadTxt.setVisibility(View.VISIBLE);
        loadSpin.setVisibility(View.VISIBLE);
    }

    private void doneLoad(){
        loadTxt.setVisibility(View.GONE);
        loadSpin.setVisibility(View.GONE);
    }
}
