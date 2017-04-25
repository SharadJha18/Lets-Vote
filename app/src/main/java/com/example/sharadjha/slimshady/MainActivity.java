package com.example.sharadjha.slimshady;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sharadjha.slimshady.Data.CandidateData;
import com.example.sharadjha.slimshady.Data.CandidateDatabaseHelper;
import com.example.sharadjha.slimshady.Data.ResultData;
import com.example.sharadjha.slimshady.Data.ResultDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    boolean flag = true;
    private Button bSignUp,bLogin;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayInitialDialog();
        ImageView ivLogo = (ImageView) findViewById(R.id.ivLogo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        ivLogo.startAnimation(animation);

        context = getBaseContext();

        final String MY_PREFS_FILE = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_FILE, MODE_PRIVATE);
        Boolean firstTime = prefs.getBoolean("firstTime", true);
        if (firstTime) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_FILE, MODE_PRIVATE).edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
            initializeApp();
        }

        bSignUp = (Button) findViewById(R.id.bSignUp);
               bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                }
                Intent intent = new Intent(getBaseContext(), VoterSignUp.class);
                startActivity(intent, bundle);
            }
        });
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
            }
        });
    }

    private void displayInitialDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
//        final View view = factory.inflate(R.layout.dialog_image_view, null);
//        alertadd.setView(view);
        alertadd.setTitle("Hi Guys!!!");
        alertadd.setMessage("This is a Voting System app developed by " +
                "\n\nSharad Jha \n\n");
        alertadd.setPositiveButton("OK!!!", new DialogInterface.OnClickListener
                () {
            public void onClick(DialogInterface dialog, int sumthin) {
                dialog.dismiss();
            }
        });
        alertadd.show();
    }

    private void registerCandidates() {
        CandidateDatabaseHelper db = new CandidateDatabaseHelper(context);
        try {
            db.open();
            db.clearDatabase();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CandidateData candidateData = new CandidateData();
        candidateData.setName("Narendra Modi");
        candidateData.setDob("May 4 1968");
        candidateData.setMobile("9999111133");
        candidateData.setGender("Male");
        candidateData.setUsername("nm");
        candidateData.setParty("BJP");
        candidateData.setAddress("Delhi Parliament");
        candidateData.setVoterId("1234");
        candidateData.setPassword("nm");
        try {
            db.open();
            db.putData(candidateData);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        candidateData = new CandidateData();
        candidateData.setName("Arvind Kejriwal");
        candidateData.setDob("September 19, 1977");
        candidateData.setMobile("8854662174");
        candidateData.setGender("Male");
        candidateData.setUsername("ak");
        candidateData.setParty("AAP");
        candidateData.setAddress("Delhi Parliament");
        candidateData.setVoterId("2013");
        candidateData.setPassword("ak");
        try {
            db.open();
            db.putData(candidateData);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        candidateData = new CandidateData();
        candidateData.setName("Rahul Gandhi");
        candidateData.setDob("June 19, 1989");
        candidateData.setMobile("8866557421");
        candidateData.setGender("Male");
        candidateData.setUsername("rg");
        candidateData.setParty("Congress");
        candidateData.setAddress("Delhi Parliament");
        candidateData.setVoterId("5678");
        candidateData.setPassword("rg");
        try {
            db.open();
            db.putData(candidateData);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        candidateData = new CandidateData();
        candidateData.setName("Akhilesh Yadav");
        candidateData.setDob("January 1, 1985");
        candidateData.setMobile("9965428800");
        candidateData.setGender("Male");
        candidateData.setUsername("ay");
        candidateData.setParty("BJP");
        candidateData.setAddress("Delhi Parliament");
        candidateData.setVoterId("8899");
        candidateData.setPassword("ay");
        try {
            db.open();
            db.putData(candidateData);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resId = item.getItemId();
        switch (resId) {
            case R.id.bReinitialize:
                initializeApp();
                break;
        }
        return true;
    }

    private void initializeApp() {
        registerCandidates();
        initializeResultDatabase();
        Toast.makeText(getBaseContext(), "Application has been Re-Initialized!!!", Toast
                .LENGTH_LONG).show();

    }

    private void initializeResultDatabase() {
        ResultDatabaseHelper db = new ResultDatabaseHelper(context);
        ResultData resultData = new ResultData();
        try {
            db.open();
            db.clearDatabase();
            db.putData(resultData);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;
    }
}
