package com.tripperfypactivity.tripperfyp.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.fragments.ExpencesFragment;
import com.tripperfypactivity.tripperfyp.listeners.FirebaseResponseListener;
import com.tripperfypactivity.tripperfyp.models.TripMemberList;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tripperfypactivity.tripperfyp.utilites.Constans.TRIP_MEMBER_LIST;
import static com.tripperfypactivity.tripperfyp.utilites.Constans.USER_TRIP_EXPENCES;


public class AddExpencesActivity extends BaseActivity implements FirebaseResponseListener {


    @BindView(R.id.discription)
    EditText discription;
    @BindView(R.id.exp_type_spinner)
    Spinner expTypeSpinner;
    @BindView(R.id.member_spinner)
    Spinner memberSpinner;
    @BindView(R.id.saveexpences)
    Button saveexpences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expences);
        ButterKnife.bind(this);
        setSpinner();
        setListSpinner();
    }

    public ArrayList<String> getMemberList() {
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
        arrayList.add("Add New");
        return arrayList;
    }

    public ArrayList<String> getUserListOfData() {
        final ArrayList<String> arrayList = new ArrayList<>();
        if (Shared.currentTrip != null) {
            DatabaseReference ref = Shared.getDataBaseRef(Constans.USER_TRIP).child(Shared.currentTrip.getId()).child(TRIP_MEMBER_LIST);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        TripMemberList user = postSnapshot.getValue(TripMemberList.class);
                        arrayList.add(user.getFirstName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return arrayList;
    }

    public void setListSpinner() {
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getMemberList());
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberSpinner.setAdapter(dataAdapter1);
        memberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void setSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, geTypeList());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expTypeSpinner.setAdapter(dataAdapter);
        expTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void saveExpences(String discriotion, String type, String membername) {
        HashMap<String, String> datasave = new HashMap<>();
        datasave.put("discription", discriotion);
        datasave.put("type", type);
        datasave.put("memberName", membername);

        final DatabaseReference myRef = Shared.getDataBaseRef(USER_TRIP_EXPENCES);
        String id = myRef.push().getKey();
        myRef.child(Shared.currentTrip.getId()).push().setValue(datasave);
        //   FirebaseDataBase.insertInTableWithoutId(this,datasave, Constans.USER_TRIP_EXPENCES, this);
        startActivity(new Intent(this, ExpencesFragment.class));

    }

    @OnClick(R.id.saveexpences)
    public void onViewClicked() {
        // getListOfData(Constans.USER_TABEL);
        saveExpences(discription.getText().toString(), expTypeSpinner.getSelectedItem().toString(), memberSpinner.getSelectedItem().toString());
    }


    @Override
    public void onSuccess(boolean isOffline) {

    }

    @Override
    public void onFailure(boolean isOffline) {

    }
}

