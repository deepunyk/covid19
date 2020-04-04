package com.xoi.covid19;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thefinestartist.finestwebview.FinestWebView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class AfpAdapter extends RecyclerView.Adapter<AfpAdapter.ViewHolder>  {


    public AfpAdapter(ArrayList<String> link, ArrayList<String> img, Context mContext) {
        this.link = link;
        this.img = img;
        this.mContext = mContext;
    }

    private ArrayList<String> link = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_afp, viewGroup, false);
        AfpAdapter.ViewHolder holder = new AfpAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(img.get(i))
                .centerCrop()
                .into(viewHolder.photo);
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.parent_layout.animate().scaleX(0.9f).scaleY(0.9f);
                viewHolder.parent_layout.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.parent_layout.setAlpha(1f);
                        viewHolder.parent_layout.animate().scaleX(1f).scaleY(1f);
                        getPermission(link.get(i));
                    }
                }, 80);
            }
        });
    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private CardView parent_layout;
        Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
            photo = (ImageView)itemView.findViewById(R.id.img);
        }
    }

    private void getPermission(String pdfurl){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Toast.makeText(mContext, "Downloading PDF", Toast.LENGTH_SHORT).show();
            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(pdfurl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
            downloadManager.enqueue(request);
        }
    }
}
