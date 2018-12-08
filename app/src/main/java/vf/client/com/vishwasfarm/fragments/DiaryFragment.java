package vf.client.com.vishwasfarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.adapter.MyDiaryAdapter;

public class DiaryFragment extends Fragment {

    private RecyclerView myDiaryList;
    private MyDiaryAdapter myDiaryAdapter;

    public DiaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_diary, container, false);
        myDiaryList = (RecyclerView) rootView.findViewById(R.id.diary_list);


        //List<VishwasMySubscription> mMySubscriptionList;
        myDiaryAdapter=new MyDiaryAdapter(this,R.layout.my_subscriptions_adapter);
        myDiaryList.setAdapter(myDiaryAdapter);
        myDiaryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

}