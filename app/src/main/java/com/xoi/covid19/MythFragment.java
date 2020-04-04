package com.xoi.covid19;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MythFragment extends Fragment {

    private ArrayList<String> link = new ArrayList<>();
    View view;

    public MythFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myth, container, false);

        clearArr();
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-sun-exposure.tmb-1024v.jpg?sfvrsn=658ce588_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-recovery.tmb-1024v.jpg?sfvrsn=1404cfd0_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-breathing-exercice.tmb-1920v.jpg?sfvrsn=db06f4a9_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-alcohol.tmb-1920v.jpg?sfvrsn=19ea13fb_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/52.tmb-1920v.png?sfvrsn=862374e_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-cold-snow.tmb-1920v.png?sfvrsn=1e557ba_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-hot-bath.tmb-1920v.png?sfvrsn=f1ebbc_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-mosquito-bite.tmb-1920v.png?sfvrsn=a1d90f6_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbusters-27.tmb-1920v.png?sfvrsn=d17bc6bb_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/mythbusters-31.tmb-1920v.png?sfvrsn=e5989655_1");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbusters-25.tmb-1920v.png?sfvrsn=d3bf829c_2");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbusters-33.tmb-1920v.png?sfvrsn=47bfd0aa_2");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/11.tmb-1920v.png?sfvrsn=97f2a51e_2");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/23.tmb-1920v.png?sfvrsn=c65dad38_3");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/19.tmb-1920v.png?sfvrsn=52adfc93_3");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/mythbuster-2.tmb-1920v.png?sfvrsn=635d24e5_3");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/mythbuster-3.tmb-1920v.png?sfvrsn=10657e42_3");
        link.add("https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbuster-4.tmb-1920v.png?sfvrsn=e163bada_3");

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        //LinearLayoutManager lm  = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(lm);
        MythFragmentAdapter adapter = new MythFragmentAdapter(link,getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void clearArr(){
        link.clear();
    }

}

