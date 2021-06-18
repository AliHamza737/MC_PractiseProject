package com.tripperfypactivity.tripperfyp.activiteis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login_Activity extends BaseActivity {



    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    // First commit
    private String TAG = "Test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    public void ChackIfUserDataExist(String email, String password) {

        Shared.getAuthUser().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            if (Shared.getUser().isEmailVerified())
//                            {
                            getUserListOfData();
//                            }
//                           else{
//                                GlobalUtils.showToast(Login_Activity.this,"Please Chack Verify Email");
//                            }
                        } else {
                            stopAnim();
                            GlobalUtils.showToast(Login_Activity.this, "Email or Password Not Valid");

                        }
                    }
                });

    }

    public void getUserListOfData() {

        DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user;
                user = snapshot.getValue(User.class);
                Boolean userDataChak = Boolean.parseBoolean(snapshot.child("saveData").getValue().toString());
                if (userDataChak) {
                    stopAnim();
//                    Shared.currenttrip();
                    startActivity(new Intent(Login_Activity.this, Navigation_Activity.class));
                } else {
                    stopAnim();
                    startActivity(new Intent(Login_Activity.this, UserInfoActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ss = "";
            }

        });
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }


    public void onLogin() {
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            GlobalUtils.showToast(this, getString(R.string.email_error));
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            GlobalUtils.showToast(this, getString(R.string.password_error));
        } else {
            startAnim();
            ChackIfUserDataExist(email.getText().toString().trim(), password.getText().toString().trim());
        }
    }
    public void internetPopup() {
        String context;
        AlertDialog.Builder dialog = new AlertDialog.Builder(Login_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle("");
        dialog.setMessage("No Internet Connection");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }
    public void internetcheck(Context context){
        if(GlobalUtils.isNetworkAvailable(context)){
            onLogin();
        }else{
            internetPopup();
        }
    }


    @OnClick({R.id.btn_login, R.id.link_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                internetcheck(this);
                break;
            case R.id.link_signup:
                startActivity(new Intent(Login_Activity.this, Registraion_Activity.class));
                break;
        }
    }
}
