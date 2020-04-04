package com.xoi.covid19;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MythFragmentAdapter extends RecyclerView.Adapter<MythFragmentAdapter.ViewHolder>  {

    public MythFragmentAdapter(ArrayList<String> link, Context mContext) {
        this.link = link;
        this.mContext = mContext;
    }

    private ArrayList<String> link = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public MythFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_myth_layout, viewGroup, false);
        MythFragmentAdapter.ViewHolder holder = new MythFragmentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MythFragmentAdapter.ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(link.get(i))
                .into(viewHolder.img);

        viewHolder.shareBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "COVID19 India");
                    String shareMessage= "\nHey, check out this fact\n\n";
                    shareMessage = shareMessage + link.get(i);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share using"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return link.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private Button shareBut;
        private CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            shareBut = (Button)itemView.findViewById(R.id.shareBut);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
        }
    }
}
