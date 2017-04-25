package com.example.sharadjha.slimshady;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharadjha.slimshady.Data.ResultData;
import com.example.sharadjha.slimshady.Data.ResultDatabaseHelper;
import com.example.sharadjha.slimshady.Data.VoterData;
import com.example.sharadjha.slimshady.Data.VoterDatabaseHelper;

public class VoterProfile extends AppCompatActivity {
    private Button bStartVoting;
    private TextView tvAddress;
    private TextView tvName;
    private TextView tvVoterId;
    private TextView tvDob;
    private TextView tvMobile;
    private String voterId;
    private Button bEditProfile;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterprofile);
        context = getBaseContext();
        init();
        if (getIntent().getBooleanExtra("buttonDisabled", false)) {
            bStartVoting.setVisibility(View.GONE);
            bEditProfile.setVisibility(View.GONE);
        }
        ResultData resultData = new ResultData();
        ResultDatabaseHelper rdb = new ResultDatabaseHelper(context);
        try {
            rdb.open();
            resultData = rdb.getData("winner");
            rdb.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (resultData.isDeclared()) {
            bStartVoting.setText("View Results");
            bStartVoting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), Result.class);
                    startActivity(intent);
                }
            });
        } else {
            bStartVoting.setText("Start Voting");
            bStartVoting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), CandidateList.class);
                    startActivity(intent);
                }
            });

        }

        voterId = getIntent().getStringExtra("voterId");
        VoterDatabaseHelper db = new VoterDatabaseHelper(getBaseContext());
        VoterData voterData = new VoterData();
        try {
            db.open();
            voterData = db.getData(voterId);
            db.close();
        } catch (SQLException e) {
            Snackbar.make(getCurrentFocus(), e.getMessage(),
                    Snackbar.LENGTH_LONG).show();
        }
        tvAddress.setText(voterData.getAddress());
        tvName.setText(voterData.getName());
        tvVoterId.setText(voterData.getVoterId());
        tvDob.setText(voterData.getDob());
        tvMobile.setText(voterData.getMobile());
        getSupportActionBar().setTitle(voterData.getName());


        // Setting OnClickListener
        bEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), VoterSignUp.class);
                intent.putExtra("isEditing", true);
                intent.putExtra("voterId", voterId);
                startActivity(intent);
            }
        });

    }

    private void init() {

        bStartVoting = (Button) findViewById(R.id.bStartVoting);
        bEditProfile = (Button) findViewById(R.id.bEditProfile);

        tvName = (TextView) findViewById(R.id.tvName);
        tvVoterId = (TextView) findViewById(R.id.tvVoterId);
        tvDob = (TextView) findViewById(R.id.tvDob);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

    }
}
