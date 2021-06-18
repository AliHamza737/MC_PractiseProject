package com.tripperfypactivity.tripperfyp.activiteis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.tripperfypactivity.tripperfyp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcceptInviteActivity extends AppCompatActivity {

    @BindView(R.id.trip_id)
    TextView tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accecpt_invite);
        ButterKnife.bind(this);
        Log.d("id",getIntent().getStringExtra("ID"));
        tripId.setText(getIntent().getStringExtra("ID"));
    }

}
