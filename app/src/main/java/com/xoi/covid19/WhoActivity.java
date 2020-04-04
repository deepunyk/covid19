package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class WhoActivity extends AppCompatActivity {

    private ArrayList<String> askArr = new ArrayList<>();
    private ArrayList<String> readyArr = new ArrayList<>();
    private ArrayList<String> protectArr = new ArrayList<>();
    private ArrayList<String> pregArr = new ArrayList<>();
    private ArrayList<String> stressArr = new ArrayList<>();
    private ArrayList<String> healthArr = new ArrayList<>();
    private ArrayList<String> mainArr = new ArrayList<>();

    private ArrayList<String> downaskArr = new ArrayList<>();
    private ArrayList<String> downreadyArr = new ArrayList<>();
    private ArrayList<String> downprotectArr = new ArrayList<>();
    private ArrayList<String> downpregArr = new ArrayList<>();
    private ArrayList<String> downstressArr = new ArrayList<>();
    private ArrayList<String> downhealthArr = new ArrayList<>();
    private ArrayList<String> downmainArr = new ArrayList<>();

    private TextView toolHead, countTxt;
    private ImageView navImg, backImg, nextImg, downImg;
    private PhotoView img;
    private int count = 1;
    private String type = "ready";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);

        toolHead = (TextView)findViewById(R.id.toolHead);
        countTxt = (TextView)findViewById(R.id.countTxt);
        navImg = (ImageView)findViewById(R.id.navImg);
        backImg = (ImageView)findViewById(R.id.backImg);
        downImg = (ImageView)findViewById(R.id.downImg);
        nextImg = (ImageView)findViewById(R.id.nextImg);
        img = (PhotoView) findViewById(R.id.img);

        type = getIntent().getStringExtra("type");
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
                        Intent go = new Intent(WhoActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        switch (type){
            case "ask":
                addAsk();
                toolHead.setText("Ask WHO");
                mainArr.addAll(askArr);
                downmainArr.addAll(downaskArr);
                break;
            case "ready":
                addReady();
                toolHead.setText("Be Ready for coronavirus");
                mainArr.addAll(readyArr);
                downmainArr.addAll(downreadyArr);
                break;
            case "protect":
                addProtect();
                toolHead.setText("Protect yourself and others from getting sick");
                mainArr.addAll(protectArr);
                downmainArr.addAll(downprotectArr);
                break;
            case "preg":
                addPreg();
                toolHead.setText("COVID-19: Pregnancy & breastfeeding");
                mainArr.addAll(pregArr);
                downmainArr.addAll(downpregArr);
                break;
            case "stress":
                addStress();
                toolHead.setText("How to cope with stress during 2019-nCoV outbreak");
                mainArr.addAll(stressArr);
                downmainArr.addAll(downstressArr);
                break;
            case "health":
                addHealth();
                toolHead.setText("Stay healthy while travelling");
                mainArr.addAll(healthArr);
                downmainArr.addAll(downhealthArr);
                break;

        }

        downImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission(downmainArr.get(count-1));
            }
        });

        countTxt.setText(""+count+"/"+mainArr.size());

        Glide.with(WhoActivity.this)
                .load(mainArr.get(count-1))
                .placeholder(R.drawable.ic_who)
                .into(img);

        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    count++;
                    backImg.setVisibility(View.VISIBLE);
                    Glide.with(WhoActivity.this)
                            .load(mainArr.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }else if(count > 0 && count < mainArr.size()-1){
                    backImg.setVisibility(View.VISIBLE);
                    count++;
                    Glide.with(WhoActivity.this)
                            .load(mainArr.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }else{
                    nextImg.setVisibility(View.GONE);
                    count++;
                    Glide.with(WhoActivity.this)
                            .load(mainArr.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }
                countTxt.setText(""+count+"/"+mainArr.size());

            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == mainArr.size()){
                    nextImg.setVisibility(View.VISIBLE);
                    count--;
                    Glide.with(WhoActivity.this)
                            .load(mainArr.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);

                }else if(count > 2 && count < mainArr.size()){
                    count--;
                    Glide.with(WhoActivity.this)
                            .load(mainArr.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }else {
                    count--;
                    backImg.setVisibility(View.GONE);

                    img.setVisibility(View.VISIBLE);
                    nextImg.setVisibility(View.VISIBLE);
                    Glide.with(WhoActivity.this)
                            .load(mainArr.get(count-1))
                            .placeholder(R.drawable.ic_who)
                            .into(img);
                }
                countTxt.setText(""+count+"/"+mainArr.size());

            }
        });




    }

    private void addAsk(){
        askArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/safe-greetings.tmb-479v.png?sfvrsn=2e97004e_1");
        askArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/handshaking.tmb-479v.png?sfvrsn=4aed53c5_1");
        askArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/wearing-gloves.tmb-479v.png?sfvrsn=ec69b46a_1");

        downaskArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/safe-greetings.png");
        downaskArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/handshaking.png");
        downaskArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/wearing-gloves.png");
    }

    private void addReady(){
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-3.tmb-1920v.jpg?sfvrsn=1706a18f_6");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-2.tmb-1920v.jpg?sfvrsn=28a6f92d_1");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-1.tmb-1920v.jpg?sfvrsn=c81745a7_1");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-smart-if-you-develop.tmb-1920v.jpg?sfvrsn=1486258a_6");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-smart-inform.tmb-1920v.jpg?sfvrsn=f6dbe358_6");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-safe.tmb-1920v.jpg?sfvrsn=1f6e4aef_6");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-support.tmb-1920v.jpg?sfvrsn=1856f2a3_7");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-address-stigma.tmb-1920v.jpg?sfvrsn=4615bfbe_6");
        readyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-address-fear.tmb-1920v.jpg?sfvrsn=a8e99f14_6");

        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-3.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-2.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-1.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-smart-if-you-develop.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-safe.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-smart-inform.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-support.jpg");
        downreadyArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-address-stigma.jpg");
        downreadyArr.add("https://who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-address-fear.jpg");

    }

    private void addProtect(){
        protectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/blue-1.tmb-1920v.png?sfvrsn=3d15aa1c_1");
        protectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/blue-2.tmb-1920v.png?sfvrsn=2bc43de1_1");
        protectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/blue-3.tmb-1920v.png?sfvrsn=b1ef6d45_1");
        protectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/blue-4.tmb-1920v.png?sfvrsn=a5317377_5");

        downprotectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/blue-1.png");
        downprotectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/blue-2.png");
        downprotectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/blue-3.png");
        downprotectArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/blue-4.png");
    }

    private void addPreg(){
        pregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---1.tmb-crop300.png?sfvrsn=cbb4e2e6_1");
        pregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---2.tmb-crop300.png?sfvrsn=f46288e6_1");
        pregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---3.tmb-crop300.png?sfvrsn=c3edf5ad_1");
        pregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---4.tmb-crop300.png?sfvrsn=117d2bd8_1");
        pregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---5.tmb-crop300.png?sfvrsn=ae4a8c4b_1");
        pregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---breastfeeding---6.tmb-crop300.png?sfvrsn=6ecc990a_1");

        downpregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---1.png");
        downpregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---2.png");
        downpregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---3.png");
        downpregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---4.png");
        downpregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---pregnancy---5.png");
        downpregArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/pregnancy-breastfeeding/who---breastfeeding---6.png");
    }

    private void addStress(){
        stressArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stress/stress.tmb-1920v.jpg?sfvrsn=b8974505_5");
        stressArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stress/children-stress.tmb-1920v.jpg?sfvrsn=343355fd_1");

        downstressArr.add("https://www.who.int/docs/default-source/coronaviruse/coping-with-stress.pdf?sfvrsn=9845bc3a_8");
        downstressArr.add("https://www.who.int/docs/default-source/coronaviruse/helping-children-cope-with-stress-print.pdf?sfvrsn=f3a063ff_2");
    }

    private void addHealth(){
        healthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stay-healthy-while-travelling/1.tmb-1920v.png?sfvrsn=1a813eed_4");
        healthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stay-healthy-while-travelling/2.tmb-1920v.png?sfvrsn=13250c49_4");
        healthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stay-healthy-while-travelling/3.tmb-1920v.png?sfvrsn=5e5a641_4");
        healthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/stay-healthy-while-travelling/4.tmb-1920v.png?sfvrsn=9719c641_4");

        downhealthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/1.png");
        downhealthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/2.png");
        downhealthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/3.png");
        downhealthArr.add("https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/4.png");

    }

    private void getPermission(String pdfurl){
        if (ActivityCompat.checkSelfPermission(WhoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(WhoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WhoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Toast.makeText(WhoActivity.this, "Downloading Photo", Toast.LENGTH_SHORT).show();
            DownloadManager downloadManager = (DownloadManager) WhoActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
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
        Intent go = new Intent(WhoActivity.this,MainActivity.class);
        startActivity(go);
        finish();
    }
}
