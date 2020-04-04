package com.xoi.covid19;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thefinestartist.finestwebview.FinestWebView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class mythAdapter extends RecyclerView.Adapter<mythAdapter.ViewHolder>  {

    public mythAdapter(ArrayList<String> text, ArrayList<String> img, Context mContext) {
        this.text = text;
        this.img = img;
        this.mContext = mContext;
    }

    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public mythAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myth_layout, viewGroup, false);
        mythAdapter.ViewHolder holder = new mythAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mythAdapter.ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(img.get(i))
                .centerCrop()
                .into(viewHolder.mythImg);
        viewHolder.mythTxt.setText(text.get(i));
    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mythTxt;
        private ImageView mythImg;
        private CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mythTxt = (TextView) itemView.findViewById(R.id.mythTxt);
            mythImg = (ImageView) itemView.findViewById(R.id.mythImg);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
        }
    }
}
