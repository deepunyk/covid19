package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import com.thefinestartist.finestwebview.FinestWebView;

public class TechGuideActivity extends AppCompatActivity {

    CardView workCard, guideCard, reductionCard, infectionCard, PoeCard;
    ImageView navImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_guide);

        workCard = (CardView)findViewById(R.id.workCard);
        guideCard = (CardView)findViewById(R.id.guideCard);
        reductionCard = (CardView)findViewById(R.id.reductionCard);
        infectionCard = (CardView)findViewById(R.id.infectionCard);
        PoeCard = (CardView)findViewById(R.id.PoeCard);

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
                        Intent go = new Intent(TechGuideActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });

        infectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infectionCard.animate().scaleX(0.85f).scaleY(0.85f);
                infectionCard.setAlpha(0.7f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        infectionCard.setAlpha(1f);
                        infectionCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(TechGuideActivity.this).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/technical-guidance/infection-prevention-and-control");

                    }
                }, 50);
            }
        });

        workCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workCard.animate().scaleX(0.85f).scaleY(0.85f);
                workCard.setAlpha(0.8f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        workCard.setAlpha(1f);
                        workCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(TechGuideActivity.this).show("https://blog.trello.com/work-from-home-guides");

                    }
                }, 100);
            }
        });

        guideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideCard.animate().scaleX(0.85f).scaleY(0.85f);
                guideCard.setAlpha(0.8f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        guideCard.setAlpha(1f);
                        guideCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(TechGuideActivity.this).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/technical-guidance/guidance-for-schools-workplaces-institutions");

                    }
                }, 100);
            }
        });

        reductionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reductionCard.animate().scaleX(0.85f).scaleY(0.85f);
                reductionCard.setAlpha(0.8f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reductionCard.setAlpha(1f);
                        reductionCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(TechGuideActivity.this).show("https://www.who.int/health-topics/coronavirus/who-recommendations-to-reduce-risk-of-transmission-of-emerging-pathogens-from-animals-to-humans-in-live-animal-markets");

                    }
                }, 100);
            }
        });

        PoeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoeCard.animate().scaleX(0.85f).scaleY(0.85f);
                PoeCard.setAlpha(0.8f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PoeCard.setAlpha(1f);
                        PoeCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(TechGuideActivity.this).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/technical-guidance/points-of-entry-and-mass-gatherings");

                    }
                }, 100);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(TechGuideActivity.this,MainActivity.class);
        startActivity(go);
        finish();
    }
}
