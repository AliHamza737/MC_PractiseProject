package com.tripperfypactivity.tripperfyp.utilites;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.models.UserTripList;
import com.tripperfypactivity.tripperfyp.utilites.firebaseutils.FirebaseDataBase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static com.tripperfypactivity.tripperfyp.utilites.Constans.USER_TRIP_LIST;

public class Shared {
 public static User user;
  public static   UserTripList currentTrip=null;
  public static String tripid=null;
  public  static UserTripList trip;

    public static FirebaseAuth getAuthUser() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static FirebaseDatabase getDataBase() {
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getDataBaseRef(String tabelName) {
        return getDataBase().getReference(tabelName);
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

    public static void serverdate(HashMap value) {
        Map<String, String> date =  ServerValue.TIMESTAMP;
        value.put(Constans.CREAT_TIMEDATE, date);
    }

    public static User getUserLogin(){
        if(user==null){
            FirebaseDataBase.getUserData(Shared.getUser().getUid());
            return user=new User();
        }else {
            return user;
        }
    }

    public static void currenttrip() {
         trip =Shared.currentTrip;
        final List<Date> theseDates = new ArrayList<Date>();
        final DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    HashMap<String, UserTripList> map = (HashMap<String, UserTripList>) snapshot.child("USER_TRIP_LIST").getValue();
                if(map!=null) {
                    for (String key : map.keySet()) {
                        Gson gson = new Gson();
                        String json = gson.toJson(map.get(key));
                        UserTripList userTripList = gson.fromJson(json, UserTripList.class);

                        String startdate = userTripList.getStartDate();
                        String endtdate = userTripList.getEndDate();

                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                        try {
                            Date starDate = format.parse(startdate);
                            Date endDate = format.parse(endtdate);

                            theseDates.add(starDate);


                            if (date(starDate, endDate)) {
                                Shared.currentTrip = userTripList;
                            }
//                       if(Shared.currentTrip.equals(null)){
//                            Shared.currentTrip=trip;
//                       }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ss = "";
            }

        });
    }



    public static boolean date(Date startdate, Date enddate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        try {
            Date currentdate = sdf.parse(date);
            if (Shared.currentTrip != null) {
                // Before
                if (startdate.before(currentdate) || startdate.equals(currentdate)&&
                        enddate.after(currentdate)|| enddate.equals(currentdate)) {
                    return true;
                }
                // during trip
//                else if (startdate.after(sdf.parse(Shared.currentTrip.startDate)) || startdate.equals(sdf.parse(Shared.currentTrip.startDate)) &&
//                        startdate.before(sdf.parse(Shared.currentTrip.endDate))||startdate.equals(sdf.parse(Shared.currentTrip.endDate))) {
//                    // return notification which trip want to go
//                 //   return true;
//                }
//                //after
//                else if (startdate.after(currentdate) || startdate.equals(currentdate) &&
//                        (sdf.parse(Shared.currentTrip.endDate)).before(currentdate)&& startdate.after(sdf.parse(Shared.currentTrip.endDate)) ) {
//                    return true;
//                }
////                if (startdate.after(currentdate) || startdate.equals(currentdate) &&
////                        startdate.after(sdf.parse(Shared.currentTrip.startDate)) && enddate.before(sdf.parse((Shared.currentTrip.endDate)))) {
////                    return true;
////                }else if (startdate.after(currentdate) || startdate.equals(currentdate) &&
////                        startdate.equals(sdf.parse(Shared.currentTrip.startDate)) && enddate.equals(sdf.parse((Shared.currentTrip.endDate)))) {
////                    return true;
////                } else if (startdate.after(currentdate) || startdate.equals(currentdate) &&
////                        startdate.before(sdf.parse(Shared.currentTrip.startDate)) && enddate.after(sdf.parse((Shared.currentTrip.startDate)))) {
////                    return true;
////                }
            }
//            if (startdate.before(currentdate) || startdate.equals(currentdate) &&
//                    startdate.before(sdf.parse(Shared.currentTrip.startDate))) {
//                return true;
//            }
            if (Shared.currentTrip == null  || startdate.before(currentdate)
                    && enddate.after(currentdate) || enddate.equals(currentdate)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
