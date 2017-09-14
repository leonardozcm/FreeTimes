package com.example.com.freetimes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.freetimes.Util.NewItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59771 on 2017/9/10.
 */

public class TabLayoutFragment2 extends Fragment {
    private static final String ARG_PARAM1="flag";
    private static final String ARG_PARAM2="param2";

    private String mParam1;
    private String mParam2;

    private View view;


    private EventsAdapter eventsAdapter;
    private List<Event> eventsList=new ArrayList<>();

    public TabLayoutFragment2() {
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

        view=inflater.inflate(R.layout.fragment_layout2,container,false);
        initEvents();//初始化事件
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager=new  LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new NewItemDecoration()) ;
        eventsAdapter=new EventsAdapter(eventsList);
        recyclerView.setAdapter(eventsAdapter);
        return view;
    }
    /*
    初始化事件
     */
    private void initEvents(){
        for(int n=0;n<30;n++){
            int day=0;
            switch (n%5) {
                case 0:
                    Event event0 = new Event("起床", day + 1, 6, 30, 7, 00);
                    eventsList.add(event0);
                    break;
                case 1:
                    Event event1 = new Event("早饭", day + 1, 7, 00, 7, 30);
                    eventsList.add(event1);
                    break;
                case 2:
                    Event event2 = new Event("上午课", day + 1, 8, 00, 9, 30);
                    eventsList.add(event2);
                    break;
                case 3:
                    Event event3 = new Event("下午课", day + 1, 13, 30, 14, 30);
                    eventsList.add(event3);
                    break;
                case 4:
                    Event event4 = new Event("睡觉", day + 1, 22, 00, 24, 00);
                    eventsList.add(event4);
                    break;
                default:
                    break;
            }
        }
    }
}
