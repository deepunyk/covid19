package com.xoi.covid19;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class donateAdapter extends RecyclerView.Adapter<donateAdapter.ViewHolder>  {

    public donateAdapter(ArrayList<String> title, ArrayList<String> type, ArrayList<String> desc, ArrayList<String> link, Context mContext) {
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.link = link;
        this.mContext = mContext;
    }

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private ArrayList<String> link = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public donateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donate_layout, viewGroup, false);
        donateAdapter.ViewHolder holder = new donateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull donateAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.titleTxt.setText(title.get(i));
        viewHolder.descTxt.setText(desc.get(i));
        viewHolder.typeTxt.setText(type.get(i));
        viewHolder.webBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(mContext).show(link.get(i));
            }
        });
        if (link.get(i).equals("")){
            viewHolder.webBut.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTxt, typeTxt;
        private ExpandableTextView descTxt;
        private CardView parent_layout;
        private Button webBut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            typeTxt = (TextView) itemView.findViewById(R.id.typeTxt);
            parent_layout = (CardView) itemView.findViewById(R.id.parent_layout);
            descTxt = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
            webBut = (Button) itemView.findViewById(R.id.webBut);
        }
    }
}
