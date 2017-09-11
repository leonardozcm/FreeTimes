package com.example.com.freetimes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59771 on 2017/9/10.
 */

public class TabLayoutFragment extends Fragment {
    private static final String ARG_PARAM1="flag";
    private static final String ARG_PARAM2="param2";

    private String mParam1;
    private String mParam2;

    private View view;

    private RecyclerView.LayoutManager layoutManager;

    private DaysAdapter daysAdapter;
    private List<String>stringList;
    private List<Day> daysList=new ArrayList<Day>();

    private RecyclerView recyclerView;

    public TabLayoutFragment() {
        //Required empty public constructor
    }

public static TabLayoutFragment newInstance(String param1,String param2){
    TabLayoutFragment fragment=new TabLayoutFragment();
    Bundle args=new Bundle();
    args.putString(ARG_PARAM1,param1);
    args.putString(ARG_PARAM2,param2);
    fragment.setArguments(args);
    return fragment;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
private void initView(){
    initDays();
    RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
    recyclerView.setLayoutManager(layoutManager);
    daysAdapter=new DaysAdapter(daysList);
    recyclerView.setAdapter(daysAdapter);

}
    private void initDays(){
        for(int n=0;n<30;n++){
            switch (n%5){
                case 0: Day day0=new Day(n+1,R.drawable.background_1);
                    daysList.add(day0);break;
                case 1: Day day1=new Day(n+1,R.drawable.background_2);
                    daysList.add(day1);break;
                case 2: Day day2=new Day(n+1,R.drawable.background_3);
                    daysList.add(day2);break;
                case 3: Day day3=new Day(n+1,R.drawable.background_4);
                    daysList.add(day3);break;
                case 4: Day day4=new Day(n+1,R.drawable.background_5);
                    daysList.add(day4);break;
                default:break;
            }

        }
    }
}
