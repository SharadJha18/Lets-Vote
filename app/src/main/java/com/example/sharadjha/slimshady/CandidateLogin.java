package com.example.sharadjha.slimshady;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharadjha.slimshady.Data.CandidateData;
import com.example.sharadjha.slimshady.Data.CandidateDatabaseHelper;

import java.util.ArrayList;

public class CandidateLogin extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatelogin);
        getSupportActionBar().setTitle("Candidate Login");

        init();
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString();

                ArrayList<CandidateData> voterDatas = new ArrayList<CandidateData>();
                CandidateDatabaseHelper db = new CandidateDatabaseHelper(context);

                try {
                    db.open();
                    voterDatas = db.getAllData();
                    db.close();
                } catch (SQLException e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                CandidateData currentCandidate;
                int i;
                for (i = 0; i < voterDatas.size(); i++) {
                    currentCandidate = voterDatas.get(i);
                    if (currentCandidate.getUsername().equals(username)) {
                        if (currentCandidate.getPassword().equals(password)) {
                            Snackbar.make(getCurrentFocus(), "Login Successfull!!!",
                                    Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), CandidateProfile.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            break;
                        } else {
                            Snackbar.make(getCurrentFocus(), "Incorrect Password!!!",
                                    Snackbar.LENGTH_LONG).show();
                            break;
                        }
                    }
                }

                if (i == voterDatas.size()) {
                    Snackbar.make(getCurrentFocus(), "Invalid Username!!!", Snackbar
                            .LENGTH_LONG).show();
                }
            }
        });

    }

    private void init() {
        context = getBaseContext();

        bLogin = (Button) findViewById(R.id.bLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }
}
