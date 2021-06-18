package com.tripperfypactivity.tripperfyp.activiteis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.adapter.ShowTripRecyclerAdapter;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;
import com.tripperfypactivity.tripperfyp.models.Trip;

import java.util.ArrayList;

public class RecylerView_Activity extends AppCompatActivity implements OnRecyclerItemClickListener {
    ShowTripRecyclerAdapter showTripRecyclerAdapter;
    ArrayList<String> animalNames;
    ArrayList<Trip> tripArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view);
        // data to populate the RecyclerView with

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showTripRecyclerAdapter = new ShowTripRecyclerAdapter(this, animalNames, this);
        recyclerView.setAdapter(showTripRecyclerAdapter);
    }

    @Override
    public void onItemClick(int position) {
        tripArrayList.get(position).getId();
        Toast.makeText(this, animalNames.get(position), Toast.LENGTH_LONG).show();
    }
}
