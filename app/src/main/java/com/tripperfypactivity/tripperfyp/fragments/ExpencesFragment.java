package com.tripperfypactivity.tripperfyp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.activiteis.AddExpencesActivity;
import com.tripperfypactivity.tripperfyp.adapter.ShowTripExpencesAdapter;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;
import com.tripperfypactivity.tripperfyp.models.Expences;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExpencesFragment extends Fragment implements OnRecyclerItemClickListener {


    Context mContext;
    ShowTripExpencesAdapter showTripRecyclerAdapter;
    ArrayList<Expences> expences;
    @BindView(R.id.type_spinner)
    Spinner typeSpinner;
    @BindView(R.id.list_spinner)
    Spinner listSpinner;
    @BindView(R.id.exp)
    LinearLayout exp;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.addexpences)
    FloatingActionButton addexpences;


    public ExpencesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expences, container, false);
        ButterKnife.bind(this, view);
        setSpinner();
        setRecyclerAdapter();
        getExpencelist();
        return view;

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public ArrayList<String> getTypeSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("All");
        arrayList.add("By Name");
        arrayList.add("By Type");
        return arrayList;
    }

    public ArrayList<String> geMemberList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Salman");
        arrayList.add("Hamza");
        arrayList.add("Fatik");
        arrayList.add("Faizan");
        arrayList.add("Sarmad");
        arrayList.add("Mohsin");
        return arrayList;
    }

    public ArrayList<String> geTypeList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Dinner");
        arrayList.add("Launch");
        arrayList.add("Fuel");
        arrayList.add("Rent");
        arrayList.add("Breakfast");
        return arrayList;
    }

    public void setSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getTypeSpinner());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (typeSpinner.getSelectedItemPosition() == 1) {
                    setListSpinner(geMemberList());
                } else if (typeSpinner.getSelectedItemPosition() == 2) {
                    setListSpinner(geTypeList());
                } else {
                    setListSpinner(new ArrayList<String>());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setListSpinner(ArrayList<String> arrayList) {
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, arrayList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listSpinner.setAdapter(dataAdapter1);
        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getExpencelist() {
        if(Shared.currentTrip!= null){
        DatabaseReference ref = Shared.getDataBaseRef(Constans.USER_TRIP_EXPENCES).child(Shared.currentTrip.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Expences> exp = new ArrayList<Expences>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Expences exp1 = postSnapshot.getValue(Expences.class);
                    exp.add(exp1);
                }
                expences.addAll(exp);
                showTripRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });}
        else{

        }
    }

    public void setRecyclerAdapter() {
        expences = new ArrayList<>();
        list.setLayoutManager(new LinearLayoutManager(mContext));
        showTripRecyclerAdapter = new ShowTripExpencesAdapter(mContext, expences, this);
        list.setAdapter(showTripRecyclerAdapter);
    }

    @Override
    public void onItemClick(int position) {
        //   expences.get(position).getId();
    }
 public void showexpences(){
        if(Shared.currentTrip.tripadmin.equals(Shared.getAuthUser().getUid())){
            View b=addexpences;
            b.setVisibility(View.VISIBLE);
        }
 }

    @OnClick(R.id.addexpences)
    public void onViewClicked() {
        startActivity(new Intent(mContext, AddExpencesActivity.class));
    }
}
