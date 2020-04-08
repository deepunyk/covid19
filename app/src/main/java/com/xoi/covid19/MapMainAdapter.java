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

import java.util.ArrayList;

public class MapMainAdapter extends RecyclerView.Adapter<MapMainAdapter.ViewHolder> {

    public MapMainAdapter(ArrayList<String> type, ArrayList<String> name, ArrayList<String> add, ArrayList<String> lat, ArrayList<String> lon, Context mContext) {
        this.type = type;
        this.name = name;
        this.add = add;
        this.lat = lat;
        this.lon = lon;
        this.mContext = mContext;
    }

    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> add = new ArrayList<>();
    private ArrayList<String> lat = new ArrayList<>();
    private ArrayList<String> lon = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public MapMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.map_layout, viewGroup, false);
        MapMainAdapter.ViewHolder holder = new MapMainAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MapMainAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.addTxt.setText(add.get(i));
        viewHolder.nameTxt.setText(name.get(i));
        viewHolder.typeTxt.setText(type.get(i));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView typeTxt, nameTxt, addTxt;
        private CardView parent_layout;
        Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
            typeTxt = (TextView) itemView.findViewById(R.id.typeTxt);
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            addTxt = (TextView) itemView.findViewById(R.id.addTxt);
        }
    }
}