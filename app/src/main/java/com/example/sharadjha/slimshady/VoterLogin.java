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

import com.example.sharadjha.slimshady.Data.VoterData;
import com.example.sharadjha.slimshady.Data.VoterDatabaseHelper;

import java.util.ArrayList;

public class VoterLogin extends AppCompatActivity {
    private EditText etVoterId;
    private EditText etPassword;
    private Button bLogin;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterlogin);

        bLogin = (Button) findViewById(R.id.bLogin);
        etVoterId = (EditText) findViewById(R.id.etVoterId);
        etPassword = (EditText) findViewById(R.id.etVoterPass);

        context = getBaseContext();
        // Setting the onClickListeners
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                String voterId = etVoterId.getText().toString().trim();
                String password = etPassword.getText().toString();
                ArrayList<VoterData> voterDatas = new ArrayList<VoterData>();
                VoterDatabaseHelper db = new VoterDatabaseHelper(context);
                try {
                    db.open();
                    voterDatas = db.getAllData();
                    db.close();
                } catch (SQLException e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                VoterData currentVoter;
                int i;
                for (i = 0; i < voterDatas.size(); i++) {
                    currentVoter = voterDatas.get(i);
                    if (currentVoter.getVoterId().equals(voterId)) {
                        if (currentVoter.getPassword().equals(password)) {
                            Snackbar.make(getCurrentFocus(), "Login Successfull!!!",
                                    Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), VoterProfile.class);
                            intent.putExtra("voterId", voterId);
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
                    Snackbar.make(getCurrentFocus(), "Invalid Voter Id!!!", Snackbar
                            .LENGTH_LONG).show();
                }
            }
        });

    }

}
