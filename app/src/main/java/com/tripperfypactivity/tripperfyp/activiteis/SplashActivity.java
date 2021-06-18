package com.tripperfypactivity.tripperfyp.activiteis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.NotificationUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;
import com.tripperfypactivity.tripperfyp.utilites.firebaseutils.FirebaseDataBase;

import java.util.HashMap;

public class SplashActivity extends BaseActivity {


    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getIntent().getData() != null) {
            String da = "";
        } else {
            getDynamicLink();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Shared.getUser() != null) {
                    updateOnlineStatus();
                    FirebaseDataBase.getUserData(Shared.getUser().getUid());
                    Shared.currenttrip();
                    startActivity(new Intent(SplashActivity.this, Navigation_Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    NotificationUtils.showNotificationMessage(SplashActivity.this, "test", "message", "111111");
                    startActivity(new Intent(SplashActivity.this, Login_Activity.class));
                    finish();
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void getDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            String offerKey = deepLink.getQueryParameter("tripid");
                            if (offerKey != null)
                                startActivity(new Intent(SplashActivity.this, AcceptInviteActivity.class).putExtra("ID", offerKey));
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Tag", "getDynamicLink:onFailure", e);
                    }
                });
    }


    public void updateOnlineStatus() {
        final DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        final HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put(Constans.ONLINE_STATUS, "Online");
        mDatabase.updateChildren(dataMap);
    }


}
