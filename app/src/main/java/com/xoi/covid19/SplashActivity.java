package com.xoi.covid19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImg;
    AlertDialog alert;
    String version = "", sVer = "", sMessage = "", sLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImg = (ImageView)findViewById(R.id.splashImg);

        final int []imageArray={R.drawable.splashone,R.drawable.splashtwo,R.drawable.splashthree};

        final Handler handler1 = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                splashImg.setImageResource(imageArray[i]);
                splashImg.animate().rotationBy(360f).setDuration(200);
                i++;
                if(i>imageArray.length-1)
                {
                    i=0;
                }
                handler1.postDelayed(this, 600);
            }
        };

        handler1.postDelayed(runnable, 10);



        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app requires Internet connectivity to work properly. Please connect to the Internet and restart the app")
                    .setTitle("Please connect to the Internet")
                    .setCancelable(false)
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                            alert.hide();
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            alert = builder.create();
            alert.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        PackageInfo pInfo = SplashActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                        version = pInfo.versionName;
                        checkUpdate();
                    } catch (PackageManager.NameNotFoundException e) {
                        Toast.makeText(SplashActivity.this, ""+e, Toast.LENGTH_LONG).show();

                    }

                }
            }, 1500);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app requires Internet connectivity to work properly. Please connect to the Internet and restart the app")
                    .setTitle("Please connect to the Internet")
                    .setCancelable(false)
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                            alert.hide();
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void checkUpdate(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://xtoinfinity.tech/maps/checkUpdate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarray = jobj.getJSONArray("update");
                            JSONObject jo = jarray.getJSONObject(0);
                            sVer = jo.optString("version");
                            sMessage = jo.optString("message");
                            sLink = jo.optString("link");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SplashActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                        if(version.equals(sVer)){
                            Intent go = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(go);
                            finish();
                        }else{
                            new AlertDialog.Builder(SplashActivity.this)
                                    .setTitle("New Update Found")
                                    .setMessage(sMessage)
                                    .setPositiveButton("Download Update", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sLink));
                                            startActivity(browserIntent);
                                        }
                                    })
                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setIcon(R.drawable.ic_cloud_download_black_24dp)
                                    .show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SplashActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(SplashActivity.this);
        queue.add(stringRequest);
    }
}
