package com.example.sharadjha.slimshady;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sharadjha.slimshady.Data.CandidateData;
import com.example.sharadjha.slimshady.Data.CandidateDatabaseHelper;

import java.util.Calendar;

public class CandidateRegistration extends AppCompatActivity {
    private Button bSubmit;
    private TextInputLayout canNameLabel;
    private TextInputLayout canVoterIdLabel;
    private TextInputLayout canDobLabel;
    private TextInputLayout canAddLabel;
    private TextInputLayout canMobLabel;
    private TextInputLayout canPartyLabel;
    private TextInputLayout canUserLabel;
    private TextInputLayout canPassLabel;
    private TextInputLayout canConPassLabel;
    private EditText etCanName;
    private EditText etCanVoterId;
    private EditText etCanDob;
    private EditText etCanAdd;
    private EditText etCanMob;
    private EditText etCanParty;
    private EditText etCanUser;
    private EditText etCanPass;
    private EditText etCanConPass;
    private Context context;
    private RadioGroup rgGender;
    private Toast toast;
    private String gender;
    private ImageButton bDatePicker;
    private LinearLayout datePickerContainer;
    private DatePicker datePicker;
    private String date;
    private boolean isEditing;
    private String usernameToEdit;
    private String previousUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidateregistration);
        context = getBaseContext();
        init();

        isEditing = getIntent().getBooleanExtra("isEditing", false);
        Log.e("isEditing", "" + isEditing);
        if (isEditing) {
            usernameToEdit = getIntent().getStringExtra("username");
            previousUsername = usernameToEdit;
            CandidateDatabaseHelper db = new CandidateDatabaseHelper(context);
            CandidateData candidateData = new CandidateData();
            try {
                db.open();
                candidateData = db.getData(usernameToEdit);
                db.close();
            } catch (SQLException e) {
                Snackbar.make(getCurrentFocus(), e.getMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
            etCanName.setText(candidateData.getName());
            etCanVoterId.setText(candidateData.getVoterId());
            etCanDob.setText(candidateData.getDob());
            etCanMob.setText(candidateData.getMobile());
            etCanAdd.setText(candidateData.getAddress());
            etCanPass.setText(candidateData.getPassword());
            etCanConPass.setText(candidateData.getPassword());
            etCanParty.setText(candidateData.getParty());
            etCanUser.setText(candidateData.getUsername());
            setGender(candidateData.getGender());
        }
        bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                CandidateData candidateData = new CandidateData();
                putDataInCandidate(candidateData);
                if (isDataValid(candidateData)) {
                    CandidateDatabaseHelper db = new CandidateDatabaseHelper(context);
                    try {
                        db.open();
                        db.putData(candidateData);
                        if (isEditing) {
                            if (candidateData.getUsername().equals(previousUsername)) {
                                db.deletePrevious(previousUsername);
                            }
                            db.editData(candidateData);
                        } else {
                            db.putData(candidateData);
                        }
                        db.close();
                    } catch (SQLException e) {
                        Snackbar.make(getCurrentFocus(), e.getMessage(),
                                Snackbar.LENGTH_LONG).show();
                    }
                    Toast.makeText(getBaseContext(), "Sign Up Successful!!!\nYou would be now" +
                            " redirected to the Login Page", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), LoginPage.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(getCurrentFocus(), "Please fill the details properly",
                            Snackbar.LENGTH_LONG).show();
                }
            }

        });

        bDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                datePickerContainer.setVisibility(View.VISIBLE);
//                makeCircularRevealAppear();
            }
        });

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = "";
                date = getStrMonth(datePicker.getMonth() + 1) + " " +
                        datePicker.getDayOfMonth() + ", " +
                        datePicker.getYear();
                datePickerContainer.setVisibility(View.GONE);
//                makeCircularRevealDisappear();
                etCanDob.setText(date);
            }
        });

        toast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                toast.cancel();
                toast = Toast.makeText(getBaseContext(), radioButton.getText() + " selected", Toast.LENGTH_LONG);
                gender = radioButton.getText().toString();
                toast.show();
            }
        });

    }

    private void setGender(String gender) {
        RadioButton rbMale, rbFemale, rbOthers;
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        rbOthers = (RadioButton) findViewById(R.id.rbOthers);
        switch (gender)
        {
            case "Male":
                rbMale.setChecked(true);
                break;
            case "Female":
                rbFemale.setChecked(true);
                break;
            case "Others":
                rbOthers.setChecked(true);
                break;
        }
    }

    private void putDataInCandidate(CandidateData candidateData) {
        candidateData.setName(etCanName.getText().toString().trim());
        candidateData.setVoterId(etCanVoterId.getText().toString().trim());
        candidateData.setAddress(etCanAdd.getText().toString());
        candidateData.setDob(etCanDob.getText().toString().trim());
        candidateData.setGender(gender);
        candidateData.setMobile(etCanMob.getText().toString().trim());
        candidateData.setParty(etCanParty.getText().toString().trim());
        candidateData.setUsername(etCanUser.getText().toString().trim());
        candidateData.setPassword(etCanPass.getText().toString());
    }

    public boolean isDataValid(CandidateData candidateData) {

        boolean flag = true;

        canNameLabel.setErrorEnabled(false);
        canVoterIdLabel.setErrorEnabled(false);
        canDobLabel.setErrorEnabled(false);
        canAddLabel.setErrorEnabled(false);
        canMobLabel.setErrorEnabled(false);
        canPartyLabel.setErrorEnabled(false);
        canUserLabel.setErrorEnabled(false);
        canPassLabel.setErrorEnabled(false);
        canConPassLabel.setErrorEnabled(false);

        if (!(candidateData.getName().length() != 0)) {
            canNameLabel.setError("Field can't be empty");
            flag = false;
        }
        if (!(candidateData.getVoterId().length() != 0)) {
            canVoterIdLabel.setError("Field can't be empty");
            flag = false;
        }
        if (!(candidateData.getAddress().length() != 0)) {
            canAddLabel.setError("Field can't be empty");
            flag = false;
        }
        if (!(candidateData.getDob().length() != 0)) {
            canDobLabel.setError("Field can't be empty");
            flag = false;
        }
        if (!(candidateData.getMobile().length() != 0)) {
            canMobLabel.setError("Field can't be empty");
            flag = false;
        } else if (!(candidateData.getMobile().matches("[0-9]+") &&
                candidateData.getMobile().length() == 10)) {
            canMobLabel.setError("Please enter a valid Phone No");
            flag = false;
        }
        if (!(candidateData.getParty().length() != 0)) {
            canPartyLabel.setError("Field can't be empty");
            flag = false;
        }
        if (!(candidateData.getUsername().length() != 0)) {
            canUserLabel.setError("Field can't be empty");
            flag = false;
        } else if (isEditing == false && (!checkUsernameWithDatabase(candidateData.getUsername()))) {
            canUserLabel.setError("Username already taken");
            flag = false;
        }
        if (!(candidateData.getPassword().length() != 0)) {
            canPassLabel.setError("Field can't be empty");
            flag = false;
        }
        if (!(etCanConPass.getText().toString().length() != 0)) {
            canConPassLabel.setError("Field can't be empty");
            flag = false;
        } else if (!(candidateData.getPassword().equals(etCanConPass.getText().toString()))) {
            this.etCanConPass.setText("");
            canConPassLabel.setError("Passwords do not match");
            flag = false;
        }
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getBaseContext(), "Please select your Gender", Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    private boolean checkUsernameWithDatabase(String username) {
        CandidateDatabaseHelper db = new CandidateDatabaseHelper(context);
        String[] usernames = new String[100];
        try {
            db.open();
            usernames = db.retrieveUsernames();
            db.close();
        } catch (SQLException e) {
            Snackbar.make(getCurrentFocus(), e.getMessage(),
                    Snackbar.LENGTH_LONG).show();
        }
        for (int i = 0; i < usernames.length; i++) {
            if (username.equals(usernames[i])) {
                return false;
            }
        }
        return true;
    }

    private String getStrMonth(int mth) {
        String month = "";
        switch (mth) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
        }
        return month;
    }

    private void init() {
        canNameLabel = (TextInputLayout) findViewById(R.id.canNameLabel);
        canVoterIdLabel = (TextInputLayout) findViewById(R.id.canIdLabel);
        canDobLabel = (TextInputLayout) findViewById(R.id.canDobLabel);
        canAddLabel = (TextInputLayout) findViewById(R.id.canAddLabel);
        canMobLabel = (TextInputLayout) findViewById(R.id.canMobLabel);
        canPartyLabel = (TextInputLayout) findViewById(R.id.canPartyLabel);
        canUserLabel = (TextInputLayout) findViewById(R.id.canUserLabel);
        canPassLabel = (TextInputLayout) findViewById(R.id.canPassLabel);
        canConPassLabel = (TextInputLayout) findViewById(R.id.canConPassLabel);

        etCanName = (EditText) findViewById(R.id.etCanName);
        etCanVoterId = (EditText) findViewById(R.id.etCanId);
        etCanDob = (EditText) findViewById(R.id.etCanDob);
        etCanAdd = (EditText) findViewById(R.id.etCanAdd);
        etCanMob = (EditText) findViewById(R.id.etCanMob);
        etCanParty = (EditText) findViewById(R.id.etCanParty);
        etCanUser = (EditText) findViewById(R.id.etCanUser);
        etCanPass = (EditText) findViewById(R.id.etCanPass);
        etCanConPass = (EditText) findViewById(R.id.etCanConPass);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        bDatePicker = (ImageButton) findViewById(R.id.bDatePicker);
        datePickerContainer = (LinearLayout) findViewById(R.id.datePickerContainer);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePickerContainer.setVisibility(View.GONE);

    }
}