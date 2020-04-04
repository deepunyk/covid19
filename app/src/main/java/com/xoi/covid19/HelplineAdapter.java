package com.xoi.covid19;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.ViewHolder> {

    public HelplineAdapter(ArrayList<String> state, ArrayList<String> num, Context mContext) {
        this.state = state;
        this.num = num;
        this.mContext = mContext;
    }

    private ArrayList<String> state = new ArrayList<>();
    private ArrayList<String> num = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public HelplineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.helpline_layout, viewGroup, false);
        HelplineAdapter.ViewHolder holder = new HelplineAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelplineAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.stateTxt.setText(state.get(i));
        viewHolder.numTxt.setText(num.get(i));
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+num.get(i)));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return state.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView stateTxt, numTxt;
        private CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateTxt = (TextView) itemView.findViewById(R.id.stateTxt);
            numTxt = (TextView) itemView.findViewById(R.id.numTxt);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
        }
    }
}