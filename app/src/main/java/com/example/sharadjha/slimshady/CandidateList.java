package com.example.sharadjha.slimshady;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sharadjha.slimshady.Data.CandidateData;
import com.example.sharadjha.slimshady.Data.CandidateDatabaseHelper;

public class CandidateList extends AppCompatActivity {
    private Button bVote;
    private RadioButton rbCan1, rbCan2, rbCan3, rbCan4;
    private RadioGroup radioGroup;
    private View view;
    private Toast toast;
    private Context context;
    private String selectedCandidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        getSupportActionBar().setTitle("Election 2017");

        context = this;
        bVote = (Button) findViewById(R.id.bvote);
        bVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVote(selectedCandidate);
                Toast.makeText(getBaseContext(), "Vote Successful !!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        view = getCurrentFocus();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbCan1 = (RadioButton) findViewById(R.id.rbCan1);
        rbCan2 = (RadioButton) findViewById(R.id.rbCan2);
        rbCan3 = (RadioButton) findViewById(R.id.rbCan3);
        rbCan4 = (RadioButton) findViewById(R.id.rbCan4);
        toast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG);

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                rbCan1.clearAnimation();
                rbCan2.clearAnimation();
                rbCan3.clearAnimation();
                rbCan4.clearAnimation();
                radioButton.startAnimation(animation);
                toast.cancel();
                toast = Toast.makeText(getBaseContext(), radioButton.getText() + " selected", Toast.LENGTH_LONG);
                toast.show();

                selectedCandidate = radioButton.getText().toString();
            }
        });

    }

    private void addVote(String name) {
        CandidateData candidateData = new CandidateData();
        CandidateDatabaseHelper db = new CandidateDatabaseHelper(context);
        try {
            db.open();
            candidateData = db.getDataThroughName(name);
            candidateData.setNoOfVotes(candidateData.getNoOfVotes() + 1);
            Log.e("#VOTES", "" + candidateData.getNoOfVotes());
            db.deleteData(candidateData.getUsername());
            db.putData(candidateData);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
