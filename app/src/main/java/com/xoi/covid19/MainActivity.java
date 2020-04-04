package com.xoi.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.thefinestartist.finestwebview.FinestWebView;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    BottomNavigationViewEx mainBN;
    ViewPager vp;
    com.xoi.covid19.MainPager pageAdapter;
    private DrawerLayout dl;
    private NavigationView nv;
    private ImageView navImg, moreImg;
    private TextView headTxt, subTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBN = findViewById(R.id.mainBN);
        vp = (ViewPager)findViewById(R.id.mainVP);
        navImg = (ImageView)findViewById(R.id.navImg);
        moreImg = (ImageView)findViewById(R.id.moreImg);
        dl = (DrawerLayout)findViewById(R.id.drawerLayout);
        nv = (NavigationView)findViewById(R.id.navView);
        headTxt = (TextView)findViewById(R.id.headTxt);
        subTxt = (TextView)findViewById(R.id.subTxt);

        headTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(MainActivity.this).show("https://www.mygov.in/covid-19/");
            }
        });

        subTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(MainActivity.this).show("https://www.mygov.in/covid-19/");
            }
        });

        nv.setCheckedItem(R.id.home);
        navImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(GravityCompat.START);
            }
        });

        moreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.moremenu);
                popup.show();
            }
        });


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        break;
                    case R.id.tech:
                        Intent go = new Intent(MainActivity.this, TechGuideActivity.class);
                        startActivity(go);
                        finish();
                        break;
                    case R.id.map:
                        Intent go4 = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(go4);
                        finish();
                        break;
                    case R.id.activity:
                        Intent go3 = new Intent(MainActivity.this, VideoActivity.class);
                        startActivity(go3);
                        finish();
                        break;
                    case R.id.donate:
                        Intent go5 = new Intent(MainActivity.this, DonateActivity.class);
                        startActivity(go5);
                        finish();
                        break;
                    case R.id.book:
                        Intent go1 = new Intent(MainActivity.this, mythActivity.class);
                        startActivity(go1);
                        finish();
                        break;
                    case R.id.community:
                        Intent go9 = new Intent(MainActivity.this, JoinActivity.class);
                        startActivity(go9);
                        finish();
                        break;
                    case R.id.tracker:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://covid19india-network.now.sh/"));
                        startActivity(browserIntent);

                    break;

                    case R.id.doctor:
                        new FinestWebView.Builder(MainActivity.this)
                                .toolbarColor(getResources().getColor(R.color.colorPrimary))
                                .titleColor(getResources().getColor(R.color.white))
                                .menuTextColor(getResources().getColor(R.color.colorPrimary))
                                .urlColor(getResources().getColor(R.color.colorPrimaryLight))
                                .iconDefaultColor(getResources().getColor(R.color.white))

                                .show("https://covid.apollo247.com/?utm_source=linkedin&utm_medium=organic&utm_campaign=bot_scanner");
                        break;
                    case R.id.contact:
                        Intent go2 = new Intent(MainActivity.this, ContactActivity.class);
                        startActivity(go2);
                        finish();
                        break;
                    case R.id.parent:
                        Intent go8 = new Intent(MainActivity.this, HealthyParenting.class);
                        startActivity(go8);
                        finish();
                        break;
                    case R.id.help:
                        Intent go7 = new Intent(MainActivity.this, HelplineActivity.class);
                        startActivity(go7);
                        finish();
                        break;
                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        pageAdapter = new com.xoi.covid19.MainPager(getSupportFragmentManager(),mainBN.getItemCount());
        vp.setAdapter(pageAdapter);

        mainBN.setupWithViewPager(vp);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent go = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(go);
                finish();
                break;
            case R.id.share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "COVID-19 India");
                    String shareMessage= "\nGet the latest details about COVID-19. Details officially from World Health Organization and Government of India. App features include : COVID-19 stats, latest updates from GOI, patient tracker, symptom checker, mythbusters, activites and videos by GOI and many more.\n\n";
                    shareMessage = shareMessage + "http://xtoinfinity.tech/";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share using"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
            case R.id.feedback:
                Intent go1 = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(go1);
                finish();
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        nv.setCheckedItem(R.id.home);
    }
}
