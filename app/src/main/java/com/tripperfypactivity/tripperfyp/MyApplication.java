package com.tripperfypactivity.tripperfyp;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tripperfypactivity.tripperfyp.activiteis.Navigation_Activity;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.HashMap;

public class MyApplication extends MultiDexApplication implements LifecycleObserver {

    public static Navigation_Activity mainActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // Required initialization logic here!

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onAppBackgrounded() {
        // app in background
        if (mainActivity != null) {
            updateOnlineStatus();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onAppForegrounded() {
        // app in foreground
        if (mainActivity != null) {
        }
    }


    public void updateOnlineStatus(){
        final DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        final HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put(Constans.ONLINE_STATUS, "Offline");
        mDatabase.updateChildren(dataMap);
    }

}
