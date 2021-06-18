package com.tripperfypactivity.tripperfyp.activiteis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.listeners.FirebaseResponseListener;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;
import com.tripperfypactivity.tripperfyp.utilites.firebaseutils.FirebaseDataBase;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registraion_Activity extends BaseActivity implements FirebaseResponseListener {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.cpassword)
    EditText cpassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

//    FirebaseDatabase database;
//    DatabaseReference myRef;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion);
        ButterKnife.bind(this);


    }

    public void sendVerificationEmail(){
        FirebaseUser user = Shared.getUser();
        final String id = user.getUid();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    saveDataToFireBase(email.getText().toString().trim(), password.getText().toString().trim(), id);
                }
            }
        });

    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

    public void popup() {
        String context;
        AlertDialog.Builder dialog = new AlertDialog.Builder(Registraion_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Email Verification");
        dialog.setMessage("Email verification has been sent, please check your Email");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Registraion_Activity.this, Login_Activity.class));
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public void internetPopup() {
        String context;
        AlertDialog.Builder dialog = new AlertDialog.Builder(Registraion_Activity.this);
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

    public void onSaveClick() {

        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            GlobalUtils.showToast(this, getString(R.string.email_error));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            GlobalUtils.showToast(this, getString(R.string.email_pateren_error));
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            GlobalUtils.showToast(this, getString(R.string.password_error));
        } else if (cpassword.getText().toString().trim().length() < 6) {
            GlobalUtils.showToast(this, getString(R.string.password_length_error));
        } else if (!password.getText().toString().trim().equals(cpassword.getText().toString().trim())) {
            GlobalUtils.showToast(this, getString(R.string.password_not_mtach));
        } else {
            startAnim();
            signinAthentication(email.getText().toString().trim(), password.getText().toString().trim());
        }

    }

    public void signinAthentication(String checkemail, String checkpassword) {

        Shared.getAuthUser().createUserWithEmailAndPassword(checkemail, checkpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            stopAnim();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                GlobalUtils.showToast(Registraion_Activity.this,getString(R.string.Email_exist));
                            }
                        } else {
                            sendVerificationEmail();
                        }


                    }
                });

    }

    public void saveDataToFireBase(String email, String password, String id) {

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put(Constans.USER_EMAIL, email);
        dataMap.put(Constans.USER_PASSWORD, password);
        dataMap.put(Constans.CREAT_INFO_SAVE, "false");
       // Shared.userInfoFalse(dataMap);
        dataMap.put("id", id);
        FirebaseDataBase.insertInTableWithId(this,dataMap, Constans.USER_TABEL, this);
        // User user = new User(firstName,lastName,email,userName,password,id);

//        newRef.child(id).setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                stopAnim();
//                popup();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                GlobalUtils.showToast(Registraion_Activity.this, "Fail to save data");
//            }
//        });


    }

    public void internetcheck(Context context){
        if(GlobalUtils.isNetworkAvailable(context)){
            onSaveClick();
        }else{
            internetPopup();
        }
    }


    @OnClick({R.id.btn_signup, R.id.link_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                internetcheck(this);
                break;
            case R.id.link_login:
                startActivity(new Intent(Registraion_Activity.this, Login_Activity.class));
                break;
        }
    }

    @Override
    public void onSuccess(boolean isOffline) {
        stopAnim();
        popup();
    }

    @Override
    public void onFailure(boolean isOffline) {
        GlobalUtils.showToast(Registraion_Activity.this, "No Internet Connection");
    }
}
