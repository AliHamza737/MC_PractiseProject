package com.tripperfypactivity.tripperfyp.activiteis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.adapter.ShowAllTripAdapter;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;
import com.tripperfypactivity.tripperfyp.models.Trip;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllTripActivity extends BaseActivity implements OnRecyclerItemClickListener {


    ShowAllTripAdapter showAllTripAdapter;
    ArrayList<Trip> triplist;

    @BindView(R.id.listTrip)
    RecyclerView listTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trip);
        ButterKnife.bind(this);
        showRecyclerAdaptertrip();
        getTripList(Constans.USER_TRIP);

    }

    public void getTripList(final String tableName) {
        DatabaseReference ref = Shared.getDataBaseRef(tableName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Trip> trips = new ArrayList<Trip>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Trip trip = postSnapshot.getValue(Trip.class);
                    trips.add(trip);
                }
                triplist.addAll(trips);
                showAllTripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }
    public void showRecyclerAdaptertrip() {
        triplist = new ArrayList<Trip>();
        listTrip.setLayoutManager(new LinearLayoutManager(this));
        showAllTripAdapter = new ShowAllTripAdapter(this, triplist, this);
        listTrip.setAdapter(showAllTripAdapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
