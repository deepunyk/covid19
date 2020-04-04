package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

public class HealthyParenting extends AppCompatActivity {

    private PhotoView img;
    private TextView titleTxt, descTxt, countTxt;
    private ImageView backImg, nextImg, downImg, navImg;
    private ArrayList<String> imgLink = new ArrayList<>();
    private ArrayList<String> downloadLink = new ArrayList<>();
    int count = 0;
    ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_parenting);

        imgLink.add("https://www.who.int/images/default-source/health-topics/coronavirus/healthy-parenting/english-tipsheet1-updated.tmb-1920v.jpg?sfvrsn=151412f9_2");
        imgLink.add("https://www.who.int/images/default-source/health-topics/coronavirus/healthy-parenting/english-tip-2-covid-19-parenting.tmb-1920v.jpg?sfvrsn=861f531_3");
        imgLink.add("https://www.who.int/images/default-source/health-topics/coronavirus/healthy-parenting/english-tip-3-covid-19-parenting.tmb-1920v.jpg?sfvrsn=759f0719_3");
        imgLink.add("https://www.who.int/images/default-source/health-topics/coronavirus/healthy-parenting/english-tipsheet4-updated.tmb-1920v.jpg?sfvrsn=fdd341d_2");
        imgLink.add("https://www.who.int/images/default-source/health-topics/coronavirus/healthy-parenting/english-tip-5-covid-19-parenting.tmb-1920v.jpg?sfvrsn=1f61d9ab_3");
        imgLink.add("https://www.who.int/images/default-source/health-topics/coronavirus/healthy-parenting/english-tip-6-covid-19-parenting.tmb-1920v.jpg?sfvrsn=28a081b9_4");

        downloadLink.add("https://www.who.int/docs/default-source/coronaviruse/healthy-parenting/english-tipsheet1-updated.pdf?sfvrsn=788a73e3_2");
        downloadLink.add("https://www.who.int/docs/default-source/coronaviruse/healthy-parenting/english-tip-2-covid-19-parenting.pdf?sfvrsn=c872a800_6");
        downloadLink.add("https://www.who.int/docs/default-source/coronaviruse/healthy-parenting/english-tip-3-covid-19-parenting.pdf?sfvrsn=492ecf57_6");
        downloadLink.add("https://www.who.int/docs/default-source/coronaviruse/healthy-parenting/english-tipsheet4-updated.pdf?sfvrsn=9257f2f6_2");
        downloadLink.add("https://www.who.int/docs/default-source/coronaviruse/healthy-parenting/english-tip-5-covid-19-parenting.pdf?sfvrsn=1b3ee706_6");
        downloadLink.add("https://www.who.int/docs/default-source/coronaviruse/healthy-parenting/english-tip-6-covid-19-parenting.pdf?sfvrsn=232558c1_8");

        img = (PhotoView )findViewById(R.id.img);
        downImg = (ImageView) findViewById(R.id.downImg);
        nextImg = (ImageView)findViewById(R.id.nextImg);
        backImg = (ImageView)findViewById(R.id.backImg);
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        descTxt = (TextView)findViewById(R.id.descTxt);
        countTxt = (TextView)findViewById(R.id.countTxt);
        parent = (ConstraintLayout) findViewById(R.id.parent_layout);

        img.setVisibility(View.GONE);
        backImg.setVisibility(View.GONE);
        downImg.setVisibility(View.GONE);

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
                        Intent go = new Intent(HealthyParenting.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        downImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission(downloadLink.get(count-1));
            }
        });

        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    parent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                    downImg.setVisibility(View.VISIBLE);
                    count++;
                    backImg.setVisibility(View.VISIBLE);
                    titleTxt.setVisibility(View.GONE);
                    descTxt.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    Glide.with(HealthyParenting.this)
                            .load(imgLink.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }else if(count > 0 && count < 5){
                    backImg.setVisibility(View.VISIBLE);
                    count++;
                    Glide.with(HealthyParenting.this)
                            .load(imgLink.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }else{
                    nextImg.setVisibility(View.GONE);
                    count++;
                    Glide.with(HealthyParenting.this)
                            .load(imgLink.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }
                countTxt.setText(""+count+"/6");

            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 6){
                    nextImg.setVisibility(View.VISIBLE);
                    count--;
                    Glide.with(HealthyParenting.this)
                            .load(imgLink.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);

                }else if(count > 2 && count < 6){
                    count--;
                    Glide.with(HealthyParenting.this)
                            .load(imgLink.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }else {
                    count--;
                    backImg.setVisibility(View.GONE);
                    titleTxt.setVisibility(View.GONE);
                    descTxt.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    nextImg.setVisibility(View.VISIBLE);
                    Glide.with(HealthyParenting.this)
                            .load(imgLink.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }
                countTxt.setText(""+count+"/6");

            }
        });
    }

    private void getPermission(String pdfurl){
        if (ActivityCompat.checkSelfPermission(HealthyParenting.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(HealthyParenting.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HealthyParenting.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Toast.makeText(HealthyParenting.this, "Downloading PDF", Toast.LENGTH_SHORT).show();
            DownloadManager downloadManager = (DownloadManager) HealthyParenting.this.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(pdfurl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
            downloadManager.enqueue(request);
        }
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(HealthyParenting.this,MainActivity.class);
        startActivity(go);
        finish();
    }
}
