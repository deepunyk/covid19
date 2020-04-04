package com.xoi.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.thefinestartist.finestwebview.FinestWebView;

public class ContactActivity extends AppCompatActivity {

    ImageView gyt, gfb, gtwit, gig, wyt, wfb, wtwit, wig, whoImg, govImg, navImg;
    CardView teamCard, volCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        navImg = (ImageView)findViewById(R.id.navImg);
        gyt = (ImageView)findViewById(R.id.govYt);
        gfb = (ImageView)findViewById(R.id.govFb);
        gtwit = (ImageView)findViewById(R.id.govTwit);
        gig = (ImageView)findViewById(R.id.govIg);
        wyt = (ImageView)findViewById(R.id.whoYt);
        wfb = (ImageView)findViewById(R.id.whoFb);
        wtwit = (ImageView)findViewById(R.id.whoTwit);
        wig = (ImageView)findViewById(R.id.whoIg);
        whoImg = (ImageView)findViewById(R.id.whoImg);
        govImg = (ImageView)findViewById(R.id.govImg);
        teamCard = (CardView) findViewById(R.id.teamCard);
        volCard = (CardView) findViewById(R.id.volCard);

        volCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volCard.animate().scaleX(0.85f).scaleY(0.85f);
                volCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        volCard.setAlpha(1f);
                        volCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://self4society.mygov.in/volunteer/");

                    }
                }, 50);
            }
        });

        gyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyt.animate().scaleX(0.85f).scaleY(0.85f);
                gyt.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gyt.setAlpha(1f);
                        gyt.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("http://youtube.com/mygovindia");

                    }
                }, 100);
            }
        });
        gfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gfb.animate().scaleX(0.85f).scaleY(0.85f);
                gfb.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gfb.setAlpha(1f);
                        gfb.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://facebook.com/MyGovIndia/");

                    }
                }, 100);
            }
        });
        gtwit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtwit.animate().scaleX(0.85f).scaleY(0.85f);
                gtwit.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gtwit.setAlpha(1f);
                        gtwit.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://twitter.com/mygovindia");

                    }
                }, 100);
            }
        });
        gig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gig.animate().scaleX(0.85f).scaleY(0.85f);
                gig.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gig.setAlpha(1f);
                        gig.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://www.instagram.com/mygovindia/");

                    }
                }, 100);
            }
        });
        wyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wyt.animate().scaleX(0.85f).scaleY(0.85f);
                wyt.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wyt.setAlpha(1f);
                        wyt.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("http://www.youtube.com/user/who?sub_confirmation=1");

                    }
                }, 100);
            }
        });
        wfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wfb.animate().scaleX(0.85f).scaleY(0.85f);
                wfb.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wfb.setAlpha(1f);
                        wfb.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("http://www.facebook.com/WHO");

                    }
                }, 100);
            }
        });
        wtwit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wtwit.animate().scaleX(0.85f).scaleY(0.85f);
                wtwit.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wtwit.setAlpha(1f);
                        wtwit.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("http://twitter.com/intent/follow?source=followbutton&variant=1.0&screen_name=who");

                    }
                }, 100);
            }
        });
        wig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wig.animate().scaleX(0.85f).scaleY(0.85f);
                wig.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wig.setAlpha(1f);
                        wig.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("http://instagram.com/who");

                    }
                }, 100);
            }
        });
        whoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whoImg.animate().scaleX(0.85f).scaleY(0.85f);
                whoImg.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        whoImg.setAlpha(1f);
                        whoImg.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://www.who.int/emergencies/diseases/novel-coronavirus-2019");

                    }
                }, 100);
            }
        });
        govImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                govImg.animate().scaleX(0.85f).scaleY(0.85f);
                govImg.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        govImg.setAlpha(1f);
                        govImg.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://www.mygov.in/covid-19/");

                    }
                }, 100);
            }
        });
        teamCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamCard.animate().scaleX(0.85f).scaleY(0.85f);
                teamCard.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        teamCard.setAlpha(1f);
                        teamCard.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(ContactActivity.this).show("https://www.instagram.com/x_to_infinity/");

                    }
                }, 100);
            }
        });

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
                        Intent go = new Intent(ContactActivity.this, MainActivity.class);
                        startActivity(go);
                        finish();
                    }
                }, 100);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent go = new Intent(ContactActivity.this,MainActivity.class);
        startActivity(go);
        finish();
    }
}
