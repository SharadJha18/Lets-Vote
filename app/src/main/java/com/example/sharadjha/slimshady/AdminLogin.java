package com.example.sharadjha.slimshady;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    private Button bAdminLogin;
    private EditText etAdminUser;
    private EditText etAdminPass;
    private TextInputLayout usernameLabel;
    private TextInputLayout passwordLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Admin Login");

        init();

        bAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordLabel.setErrorEnabled(false);
                usernameLabel.setErrorEnabled(false);
                String username = etAdminUser.getText().toString().trim();
                String password = etAdminPass.getText().toString().trim();
                if (username.equals("admin")) {
                    if (password.equals("123")) {
                        // login successful
                        Toast.makeText(getBaseContext(), "Welcome Mr. Sharad Jha", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(),AdminProfile.class);
                        startActivity(intent);
                    } else {
                        passwordLabel.setError("Incorrect Password");
                    }
                } else if (username.equals("")) {
                    usernameLabel.setError("Field can't be empty");
                } else {
                    usernameLabel.setError("Username not in database");
                }
                if (password.equals("")) {
                    passwordLabel.setError("Field can't be empty");
                }
            }
        });

    }

    private void init() {
        bAdminLogin = (Button) findViewById(R.id.bAdminLogin);
        etAdminPass = (EditText) findViewById(R.id.etAdminPass);
        etAdminUser = (EditText) findViewById(R.id.etAdminUser);
        usernameLabel = (TextInputLayout) findViewById(R.id.usernameLabel);
        passwordLabel = (TextInputLayout) findViewById(R.id.passwordLabel);

        etAdminUser.setText("admin");
        etAdminPass.setText("123");

    }
}
