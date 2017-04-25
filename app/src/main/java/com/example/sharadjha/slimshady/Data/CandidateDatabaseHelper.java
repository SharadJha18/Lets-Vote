package com.example.sharadjha.slimshady.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by SHAAN on 04-02-17.
 */
public class CandidateDatabaseHelper {

    public static final String KEY_ROWID = "id";
    public static final String NAME = "name";
    public static final String VOTERID = "voterid";
    public static final String DOB = "dob";
    public static final String MOBILE = "mobile";
    public static final String ADDRESS = "address";
    public static final String GENDER = "gender";
    public static final String PARTY = "party";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NOVOTES = "novotes";


    private static final String TABLE_NAME = "CANDIDATE";
    private static final String DATABASE_TABLE = "candidate_database";
    private static final int DATABASE_VERSION = 1;

    private DbHelper candHelper;
    private final Context candContext;
    private SQLiteDatabase candDatabase;

    public CandidateDatabaseHelper(Context c) {
        this.candContext = c;
    }

    public CandidateDatabaseHelper open() throws SQLException {
        candHelper = new DbHelper(candContext);
        candDatabase = candHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        candHelper.close();
    }


    public void putData(CandidateData candidateData) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, candidateData.getName());
        cv.put(VOTERID, candidateData.getVoterId());
        cv.put(DOB, candidateData.getDob());
        cv.put(MOBILE, candidateData.getMobile());
        cv.put(ADDRESS, candidateData.getAddress());
        cv.put(GENDER, candidateData.getGender());
        cv.put(PARTY, candidateData.getParty());
        cv.put(USERNAME, candidateData.getUsername());
        cv.put(PASSWORD, candidateData.getPassword());
        cv.put(NOVOTES, String.valueOf(candidateData.getNoOfVotes()));
        candDatabase.insert(TABLE_NAME, null, cv);
    }

    public void deletePrevious(String username) {
        //delete existing entry
        candDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                USERNAME + " = '" + username + "';");

    }

    public void editData(CandidateData candidateData) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, candidateData.getName());
        cv.put(VOTERID, candidateData.getVoterId());
        cv.put(DOB, candidateData.getDob());
        cv.put(MOBILE, candidateData.getMobile());
        cv.put(ADDRESS, candidateData.getAddress());
        cv.put(GENDER, candidateData.getGender());
        cv.put(PARTY, candidateData.getParty());
        cv.put(USERNAME, candidateData.getUsername());
        cv.put(PASSWORD, candidateData.getPassword());
        cv.put(NOVOTES, String.valueOf(candidateData.getNoOfVotes()));
        candDatabase.insert(TABLE_NAME, null, cv);
    }

    public Cursor getInfo() {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PARTY, USERNAME, PASSWORD, NOVOTES};
        Cursor cursor = candDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public CandidateData getWinnerCandidate() {
        int maxVotes = 0;
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PARTY, USERNAME, PASSWORD, NOVOTES};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new CandidateData();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iVoter = cursor.getColumnIndex(VOTERID);
        int iDob = cursor.getColumnIndex(DOB);
        int iMobile = cursor.getColumnIndex(MOBILE);
        int iAddress = cursor.getColumnIndex(ADDRESS);
        int iGender = cursor.getColumnIndex(GENDER);
        int iParty = cursor.getColumnIndex(PARTY);
        int iUsername = cursor.getColumnIndex(USERNAME);
        int iPassword = cursor.getColumnIndex(PASSWORD);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);

        CandidateData candidateData = null;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            candidateData = new CandidateData();
            int votes = Integer.parseInt(cursor.getString(iNoOfVotes));
            if (votes > maxVotes) {
                candidateData.setName(cursor.getString(iName));
                candidateData.setVoterId(cursor.getString(iVoter));
                candidateData.setDob(cursor.getString(iDob));
                candidateData.setMobile(cursor.getString(iMobile));
                candidateData.setAddress(cursor.getString(iAddress));
                candidateData.setGender(cursor.getString(iGender));
                candidateData.setParty(cursor.getString(iParty));
                candidateData.setUsername(cursor.getString(iUsername));
                candidateData.setPassword(cursor.getString(iPassword));
                candidateData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
                maxVotes = votes;
            }
        }

        return candidateData;
    }

    public ArrayList<CandidateData> getAllData() {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PARTY, USERNAME, PASSWORD, NOVOTES};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new ArrayList<CandidateData>();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iVoter = cursor.getColumnIndex(VOTERID);
        int iDob = cursor.getColumnIndex(DOB);
        int iMobile = cursor.getColumnIndex(MOBILE);
        int iAddress = cursor.getColumnIndex(ADDRESS);
        int iGender = cursor.getColumnIndex(GENDER);
        int iParty = cursor.getColumnIndex(PARTY);
        int iUsername = cursor.getColumnIndex(USERNAME);
        int iPassword = cursor.getColumnIndex(PASSWORD);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);

        ArrayList<CandidateData> candidateDatas = new ArrayList<>();
        CandidateData candidateData;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            candidateData = new CandidateData();
            candidateData.setName(cursor.getString(iName));
            candidateData.setVoterId(cursor.getString(iVoter));
            candidateData.setDob(cursor.getString(iDob));
            candidateData.setMobile(cursor.getString(iMobile));
            candidateData.setAddress(cursor.getString(iAddress));
            candidateData.setGender(cursor.getString(iGender));
            candidateData.setParty(cursor.getString(iParty));
            candidateData.setUsername(cursor.getString(iUsername));
            candidateData.setPassword(cursor.getString(iPassword));
            candidateData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
            candidateDatas.add(candidateData);
        }

        return candidateDatas;
    }

    public CandidateData getDataThroughName(String name) {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PARTY, USERNAME, PASSWORD, NOVOTES};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new CandidateData();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iVoter = cursor.getColumnIndex(VOTERID);
        int iDob = cursor.getColumnIndex(DOB);
        int iMobile = cursor.getColumnIndex(MOBILE);
        int iAddress = cursor.getColumnIndex(ADDRESS);
        int iGender = cursor.getColumnIndex(GENDER);
        int iParty = cursor.getColumnIndex(PARTY);
        int iUsername = cursor.getColumnIndex(USERNAME);
        int iPassword = cursor.getColumnIndex(PASSWORD);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);

        CandidateData candidateData = new CandidateData();
        candidateData.setUsername("empty");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(iName).equals(name)) {
                Log.e("name", "found");
                candidateData.setName(cursor.getString(iName));
                candidateData.setVoterId(cursor.getString(iVoter));
                candidateData.setDob(cursor.getString(iDob));
                candidateData.setMobile(cursor.getString(iMobile));
                candidateData.setAddress(cursor.getString(iAddress));
                candidateData.setGender(cursor.getString(iGender));
                candidateData.setParty(cursor.getString(iParty));
                candidateData.setUsername(cursor.getString(iUsername));
                candidateData.setPassword(cursor.getString(iPassword));
                candidateData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
//                Log.e("name", candidateData.getName());
//                Log.e("voterid", candidateData.getVoterId());
//                Log.e("dob", candidateData.getDob());
//                Log.e("mob", candidateData.getMobile());
//                Log.e("address", candidateData.getAddress());
//                Log.e("gender", candidateData.getGender());
            }
        }
        return candidateData;
    }


    public CandidateData getData(String usernameToEdit) {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PARTY, USERNAME, PASSWORD, NOVOTES};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new CandidateData();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iVoter = cursor.getColumnIndex(VOTERID);
        int iDob = cursor.getColumnIndex(DOB);
        int iMobile = cursor.getColumnIndex(MOBILE);
        int iAddress = cursor.getColumnIndex(ADDRESS);
        int iGender = cursor.getColumnIndex(GENDER);
        int iParty = cursor.getColumnIndex(PARTY);
        int iUsername = cursor.getColumnIndex(USERNAME);
        int iPassword = cursor.getColumnIndex(PASSWORD);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);

        CandidateData candidateData = new CandidateData();
        candidateData.setUsername("empty");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(iUsername).equals(usernameToEdit)) {
                Log.e("username", "found");
                candidateData.setName(cursor.getString(iName));
                candidateData.setVoterId(cursor.getString(iVoter));
                candidateData.setDob(cursor.getString(iDob));
                candidateData.setMobile(cursor.getString(iMobile));
                candidateData.setAddress(cursor.getString(iAddress));
                candidateData.setGender(cursor.getString(iGender));
                candidateData.setParty(cursor.getString(iParty));
                candidateData.setUsername(cursor.getString(iUsername));
                candidateData.setPassword(cursor.getString(iPassword));
                candidateData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
//                Log.e("name", candidateData.getName());
//                Log.e("voterid", candidateData.getVoterId());
//                Log.e("dob", candidateData.getDob());
//                Log.e("mob", candidateData.getMobile());
//                Log.e("address", candidateData.getAddress());
//                Log.e("gender", candidateData.getGender());
            }
        }
        return candidateData;
    }

    public void clearDatabase() {
        String AUTOINCREMENT_RESET = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "';";
        candDatabase.delete(TABLE_NAME, null, null);
        candDatabase.execSQL(AUTOINCREMENT_RESET);
    }

    public void deleteData(String username) {
        String DELETE = "DELETE FROM " + TABLE_NAME +
                " WHERE " + USERNAME + " = " + "'" + username + "';";
        candDatabase.execSQL(DELETE);
    }

    public String[] retrieveUsernames() {
        Cursor c = candDatabase.query(TABLE_NAME, new String[]{USERNAME}, null, null, null, null, null);
        if (c.getCount() == 0)
            return new String[]{"empty"};

        int iUsername = c.getColumnIndex(USERNAME);

        String[] result = new String[100];
        int i = 0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext(), i++) {
            result[i] = c.getString(iUsername);
        }

        return result;
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, TABLE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("CREATES","SUCCESS");
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " VARCHAR2(20) NOT NULL, " +
                    VOTERID + " VARCHAR2(20) NOT NULL, " +
                    DOB + " VARCHAR2(20) NOT NULL, " +
                    MOBILE + " VARCHAR2(11) NOT NULL, " +
                    ADDRESS + " VARCHAR2(150) NOT NULL, " +
                    GENDER + " VARCHAR2(20) NOT NULL, " +
                    PARTY + " VARCHAR2(7) NOT NULL, " +
                    USERNAME + " VARCHAR2(20) UNIQUE, " +
                    PASSWORD + " VARCHAR2(20), " +
                    NOVOTES + " VARCHAR2(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + ";");
            onCreate(db);
        }
    }

}