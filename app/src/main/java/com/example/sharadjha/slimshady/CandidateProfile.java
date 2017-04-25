package com.example.sharadjha.slimshady;

import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharadjha.slimshady.Data.CandidateData;
import com.example.sharadjha.slimshady.Data.CandidateDatabaseHelper;

public class CandidateProfile extends AppCompatActivity {

    private TextView tvAddress;
    private TextView tvName;
    private TextView tvVoterId;
    private TextView tvDob;
    private TextView tvMobile;
    private Button bEditProfile;
    private String username;
    private TextView tvGender;
    private Button bViewResult;
    private TextView tvNoOfVotes;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);
        init();
        if (getIntent().getBooleanExtra("buttonDisabled", false)) {
            bViewResult.setVisibility(View.GONE);
            bEditProfile.setVisibility(View.GONE);
        }
        username = getIntent().getStringExtra("username");
        CandidateDatabaseHelper db = new CandidateDatabaseHelper(getBaseContext());
        CandidateData candidateData = new CandidateData();
        try {
            db.open();
            candidateData = db.getData(username);
            db.close();
        } catch (SQLException e) {
            Snackbar.make(getCurrentFocus(), e.getMessage(),
                    Snackbar.LENGTH_LONG).show();
        }
        tvAddress.setText(candidateData.getAddress());
        tvName.setText(candidateData.getName());
        tvVoterId.setText(candidateData.getVoterId());
        tvDob.setText(candidateData.getDob());
        tvMobile.setText(candidateData.getMobile());
        tvGender.setText(candidateData.getGender());
        tvUsername.setText(candidateData.getUsername());
        tvNoOfVotes.setText("" + candidateData.getNoOfVotes());
        Log.e("#Votes", "" + candidateData.getNoOfVotes());
        getSupportActionBar().setTitle(candidateData.getName());

        // Setting OnClickListener
        bEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CandidateRegistration.class);
                intent.putExtra("isEditing", true);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        bViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Result.class);
                startActivity(intent);
            }
        });

    }

    private void init() {

        bEditProfile = (Button) findViewById(R.id.bEditProfile);
        bViewResult = (Button) findViewById(R.id.bViewResult);

        tvName = (TextView) findViewById(R.id.tvName);
        tvVoterId = (TextView) findViewById(R.id.tvVoterId);
        tvDob = (TextView) findViewById(R.id.tvDob);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvNoOfVotes = (TextView) findViewById(R.id.tvNoOfVotes);
    }
}
