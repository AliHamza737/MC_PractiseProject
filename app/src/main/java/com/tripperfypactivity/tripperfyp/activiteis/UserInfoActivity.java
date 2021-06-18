package com.tripperfypactivity.tripperfyp.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.userFirstName)
    EditText userFirstName;
    @BindView(R.id.userLastName)
    EditText userLastName;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.userPhoneno)
    EditText userPhoneno;
    @BindView(R.id.userCity)
    EditText userCity;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.userSaveData)
    Button userSaveData;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        int gender = radio.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(gender);
    }

    public void onSaveClick() {
        if (TextUtils.isEmpty(userFirstName.getText().toString().trim())) {
            userFirstName.setError(getString(R.string.first_name_error));
            //  GlobalUtils.showToast(this, getString(R.string.first_name_error));
        } else if (TextUtils.isEmpty(userLastName.getText().toString().trim())) {
            userLastName.setError(getString(R.string.last_name_error));
        } else if (TextUtils.isEmpty(UserName.getText().toString().trim())) {
            UserName.setError(getString(R.string.user_name_error));
        } else if (TextUtils.isEmpty(userPhoneno.getText().toString().trim())) {
            userPhoneno.setError(getString(R.string.phoneno_error));
        } else if (TextUtils.isEmpty(userCity.getText().toString().trim())) {
            userCity.setError(getString(R.string.city_error));
        } else {
            startAnim();
            saveUserInfo(userFirstName.getText().toString(), userLastName.getText().toString(), UserName.getText().toString(), userPhoneno.getText().toString()
                    , userCity.getText().toString(), radioButton.getText().toString());
        }

    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

    public void saveUserInfo(String userFirstName, String userLastName, String userName, String userPhoneno, String userCity, String userGender) {


        final HashMap<String, String> userdata = new HashMap<>();
        userdata.put(Constans.USER_FIRST_NAME, userFirstName);
        userdata.put(Constans.USER_LAST_NAME, userLastName);
        userdata.put(Constans.USER_USER_NAME, userName);
        userdata.put(Constans.USER_USER_PHONENO, userPhoneno);
        userdata.put(Constans.USER_USER_CITY, userCity);
        userdata.put(Constans.USER_USER_GENDER, userGender);
        userdata.put(Constans.CREAT_INFO_SAVE, "true");
        userdata.put(Constans.ONLINE_STATUS,"Online");
        userdata.put(Constans.USER_URL,"");

        Map<String, String> date = ServerValue.TIMESTAMP;
        userdata.put(Constans.CREAT_TIMEDATE, date.toString());

        final DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user;
                user = snapshot.getValue(User.class);
                userdata.put(Constans.USER_EMAIL, user.email);

                mDatabase.setValue(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        GlobalUtils.showToast(UserInfoActivity.this, Constans.USER_SAVE_DATA);
                        stopAnim();
                        startActivity(new Intent(UserInfoActivity.this, Navigation_Activity.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ss = "";
            }

        });

    }

    @OnClick(R.id.userSaveData)
    public void onViewClicked() {
        onSaveClick();
    }
}
