package com.tripperfypactivity.tripperfyp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.adapter.ShowMemberAdapter;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;
import com.tripperfypactivity.tripperfyp.models.TripMemberList;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.tripperfypactivity.tripperfyp.utilites.Constans.TRIP_MEMBER_LIST;


public class MemberFragment extends Fragment implements OnRecyclerItemClickListener {
    Context mContext;
    ShowMemberAdapter showMembersList;
    ArrayList<TripMemberList> memberlist;

    @BindView(R.id.list)
    RecyclerView list;
    Unbinder unbinder;

    public MemberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRecyclerAdapter();
        getUserListOfData();
        return view;
    }

    public void getUserListOfData() {
        if(Shared.tripid!= null) {
            DatabaseReference ref = Shared.getDataBaseRef(Constans.USER_TRIP).child(Shared.tripid).child(TRIP_MEMBER_LIST);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    ArrayList<TripMemberList> user = new ArrayList<TripMemberList>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        TripMemberList user1 = postSnapshot.getValue(TripMemberList.class);
                        user.add(user1);
                    }
                    memberlist.addAll(user);
                    showMembersList.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setRecyclerAdapter() {
        memberlist = new ArrayList<TripMemberList>();
        list.setLayoutManager(new LinearLayoutManager(mContext));
        showMembersList = new ShowMemberAdapter(mContext, memberlist, this);
        list.setAdapter(showMembersList);
    }

    @Override
    public void onItemClick(int position) {
        // memberlist.get(position).getId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
