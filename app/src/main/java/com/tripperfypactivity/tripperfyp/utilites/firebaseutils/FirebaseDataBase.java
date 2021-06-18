package com.tripperfypactivity.tripperfyp.utilites.firebaseutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.listeners.FirebaseResponseListener;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.models.UserTripList;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static com.facebook.login.widget.ProfilePictureView.TAG;
import static com.tripperfypactivity.tripperfyp.utilites.Constans.USER_TABEL;

public class FirebaseDataBase {


    public static FirebaseResponseListener listener;

    public static void insertInTableWithoutId(Context context, HashMap<String, String> hashMap, String tableName, final FirebaseResponseListener listener1) {
        final DatabaseReference newRef = Shared.getDataBaseRef(tableName).push();
        listener = listener1;
        if (!GlobalUtils.isNetworkAvailable(context)) {
            newRef.setValue(hashMap);
            listener.onSuccess(true);
            listener = null;
            return;
        }
        newRef.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (listener != null)
                    listener.onSuccess(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (listener != null) {
                    listener.onFailure(false);
                }
            }
        });

    }

    public static void insertInTableWithId(Context context, HashMap<String, String> hashMap, String tableName, FirebaseResponseListener listener1) {
        final DatabaseReference newRef = Shared.getDataBaseRef(tableName);
        newRef.push().getKey();
        listener = listener1;
        if (!GlobalUtils.isNetworkAvailable(context)) {
            newRef.child(hashMap.get("id")).setValue(hashMap);
            listener.onSuccess(true);
            listener = null;
            return;
        }
        newRef.child(hashMap.get("id")).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (listener != null) {
                    listener.onSuccess(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (listener != null) {
                    listener.onFailure(false);
                }
            }
        });

    }

    public static void getUserData(String id) {

        DatabaseReference mDatabase = Shared.getDataBaseRef(USER_TABEL).child(id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Shared.user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ss = "";
            }

        });
    }


    public static Date getDateNearest(List<Date> dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        Date currentdate = null;
        try {
            currentdate = sdf.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new TreeSet<Date>(dates).lower(currentdate);
    }

}
