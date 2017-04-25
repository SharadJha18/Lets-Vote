package com.example.sharadjha.slimshady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SignUpPage extends AppCompatActivity {
    private Button bVoterSignUp,bCanSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Intent intent = new Intent(getBaseContext(), VoterSignUp.class);
        startActivity(intent);
        bVoterSignUp = (Button) findViewById(R.id.bVoterSignUp);
        bVoterSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), VoterSignUp.class);
                startActivity(intent);
            }
        });

        bCanSignUp = (Button) findViewById(R.id.bCandidateSignUp);
        Log.e("null", "" + (bCanSignUp == null));
        bCanSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CandidateRegistration.class);
                startActivity(intent);
            }
        });

    }
}