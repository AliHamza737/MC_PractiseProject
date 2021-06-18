package com.tripperfypactivity.tripperfyp.activiteis;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.listeners.FirebaseResponseListener;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static com.tripperfypactivity.tripperfyp.utilites.Constans.USER_TRIP;
import static com.tripperfypactivity.tripperfyp.utilites.Shared.user;

public class Addtrip_Activity extends BaseActivity implements FirebaseResponseListener {


    @BindView(R.id.tripname)
    EditText tripname;
    @BindView(R.id.start_location)
    TextView startLocation;
    @BindView(R.id.end_loction)
    TextView endLoction;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_dete)
    TextView endDete;
    @BindView(R.id.expences)
    EditText expences;
    @BindView(R.id.input_Save)
    Button inputSave;
    private int month, year, day;
    int PLACE_PICKER_REQUEST_End = 111;
    int PLACE_PICKER_REQUEST_Start = 101;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    String firstName, lastName;
    double startlongitude,startlatitude,endlatitude,endlongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtrip);
        getUserListOfData();
        ButterKnife.bind(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST_Start) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String placeName = String.format("Place: %s", place.getName());
                startlatitude = place.getLatLng().latitude;
                startlongitude = place.getLatLng().longitude;
                startLocation.setText(placeName);

            }
        }
        if (requestCode == PLACE_PICKER_REQUEST_End) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String placeName = String.format("Place: %s", place.getName());
                endlatitude = place.getLatLng().latitude;
                endlongitude = place.getLatLng().longitude;
                endLoction.setText(placeName);

            }
        }
    }

    public void startLocation() {

        try {
            startActivityForResult(builder.build(Addtrip_Activity.this), PLACE_PICKER_REQUEST_Start);
            // for fragment
            //startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void endLocation() {

        try {
            // for activty
            startActivityForResult(builder.build(Addtrip_Activity.this), PLACE_PICKER_REQUEST_End);
            // for fragment
            //startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void startDate() {
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        };
        new DatePickerDialog(this, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }


    public void endDate() {

        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDete.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        };
        new DatePickerDialog(this, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void getUserListOfData() {

        DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user = new User();
                user = snapshot.getValue(User.class);
                firstName = (user.firstName);
                lastName = (user.lastName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ss = "";
            }

        });
    }

    public void serverdate() {

        // this will create a new unique key
        DatabaseReference date = Shared.getDataBaseRef("date");
        HashMap<Object, Object> value = new HashMap<>();
        value.put("timestamp", (String.valueOf(ServerValue.TIMESTAMP)));
        date.setValue(value);


    }

    public void getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);


        DatabaseReference ref = getInstance().getReference();
        String key = ref.push().getKey(); // this will create a new unique key
        Map<String, Object> value = new HashMap<>();
        value.put("timestamp", formatter.format(calendar.getTime()));
        ref.child(key).setValue(value);


    }

    public void addTrip(String tripname, String startloc, String endloc, String startdate, String enddate, String expences, String userid) {


        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put(Constans.TRIP_NAME, tripname);
        dataMap.put(Constans.TRIP_START_LOCATION, startloc);
        dataMap.put(Constans.TRIP_END_LOCATION, endloc);
        dataMap.put(Constans.TRIP_START_DATE, startdate);
        dataMap.put(Constans.TRIP_END_DATE, enddate);
        dataMap.put(Constans.TRIP_EXPENCES, expences);
        dataMap.put(Constans.TOTAL_TRIP_EXPENCES, "0");
        dataMap.put(Constans.TRIP_ADMIN, userid);
        dataMap.put(Constans.START_TRIP_LATITUDE, String.valueOf(startlatitude));
        dataMap.put(Constans.START_TRIP_LONGITUDE, String.valueOf(startlongitude));
        dataMap.put(Constans.END_TRIP_LATITUDE, String.valueOf(endlatitude));
        dataMap.put(Constans.END_TRIP_LONGITUDE, String.valueOf(endlongitude));


        final DatabaseReference myRef = Shared.getDataBaseRef(USER_TRIP);
        String id = myRef.push().getKey();
        dataMap.put("id", id);
        Shared.tripid=id;
        Shared.serverdate(dataMap);
        myRef.child(id).setValue(dataMap);

        HashMap<String, String> memberdataMap = new HashMap<>();
        memberdataMap.put(Constans.USER_FIRST_NAME, firstName);
        memberdataMap.put(Constans.USER_LAST_NAME, lastName);
        memberdataMap.put("id", userid);
        myRef.child(id).child(Constans.TRIP_MEMBER_LIST).push().setValue(memberdataMap);

        HashMap<String, String> tripListData = new HashMap<>();
        tripListData.put(Constans.TRIP_START_DATE, startdate);
        tripListData.put(Constans.TRIP_END_DATE, enddate);
        tripListData.put("id", id);
        DatabaseReference uDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(userid);
        DatabaseReference m = uDatabase.child(Constans.USER_TRIP_LIST).push();
        m.setValue(tripListData);


        startActivity(new Intent(this, Navigation_Activity.class));
//        newTrip.setTripName(tripname);
//        newTrip.setStartLocation(startloc);
//        newTrip.setEndLocation(endloc);
//        newTrip.setStartDate(startdate);
//        newTrip.setEndDate(enddate);
//        newTrip.setexPences(expences);
//        final DatabaseReference myRef = Shared.getDataBaseRef(USER_TRIP);
//          String id = myRef.push().getKey();
//        Trip newTrip = new Trip(tripname,startloc,endloc,startdate,enddate,expences,id);
//
//        myRef.child(id).setValue(newTrip).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                GlobalUtils.showToast(Addtrip_Activity.this,getString(R.string.tripsucces));
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                GlobalUtils.showToast(Addtrip_Activity.this,getString(R.string.triperror));
//            }
//        });


    }


    @Override
    public void onSuccess(boolean isOffline) {
        GlobalUtils.showToast(Addtrip_Activity.this, getString(R.string.tripsucces));
    }

    @Override
    public void onFailure(boolean isOffline) {
        GlobalUtils.showToast(Addtrip_Activity.this, getString(R.string.triperror));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @OnClick({R.id.start_location, R.id.end_loction, R.id.start_date, R.id.end_dete, R.id.input_Save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_location:
                startLocation();
                break;
            case R.id.end_loction:
                endLocation();
                break;
            case R.id.start_date:
                startDate();
                break;
            case R.id.end_dete:
                endDate();
                break;
            case R.id.input_Save:
                addTrip(tripname.getText().toString().trim(), startLocation.getText().toString().trim(),
                        endLoction.getText().toString().trim(), startDate.getText().toString().trim(),
                        endDete.getText().toString().trim(),
                        expences.getText().toString().trim(), Shared.getUser().getUid());
                break;
        }
    }
}
