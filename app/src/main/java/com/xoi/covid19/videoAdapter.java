package com.xoi.covid19;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

public class videoAdapter extends RecyclerView.Adapter<videoAdapter.ViewHolder>  {


    public videoAdapter(ArrayList<String> textArr, ArrayList<String> imgArr, ArrayList<String> linkArr, Context mContext) {
        this.textArr = textArr;
        this.imgArr = imgArr;
        this.linkArr = linkArr;
        this.mContext = mContext;
    }

    private ArrayList<String> textArr = new ArrayList<>();
    private ArrayList<String> imgArr = new ArrayList<>();
    private ArrayList<String> linkArr = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public videoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
        videoAdapter.ViewHolder holder = new videoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull videoAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.text.setText(textArr.get(i));
        Glide.with(mContext)
                .load(imgArr.get(i))
                .into(viewHolder.img);
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.parent_layout.animate().scaleX(0.85f).scaleY(0.85f);
                viewHolder.parent_layout.setAlpha(0.8f);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.parent_layout.setAlpha(1f);
                        viewHolder.parent_layout.animate().scaleX(1f).scaleY(1f);
                        new FinestWebView.Builder(mContext).show(linkArr.get(i));

                    }
                }, 100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return textArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView img;
        private CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            img = (ImageView) itemView.findViewById(R.id.img);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
        }
    }
}
