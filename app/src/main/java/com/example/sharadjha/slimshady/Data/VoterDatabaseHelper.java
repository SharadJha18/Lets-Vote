package com.example.sharadjha.slimshady.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SHAAN on 04-02-17.
 */
public class VoterDatabaseHelper {

    public static final String KEY_ROWID = "id";
    public static final String NAME = "name";
    public static final String VOTERID = "voterid";
    public static final String DOB = "dob";
    public static final String MOBILE = "mobile";
    public static final String ADDRESS = "address";
    public static final String GENDER = "gender";
    public static final String PASSWORD = "password";

    private static final String TABLE_NAME = "VOTER";
    private static final String DATABASE_TABLE = "voter_database";
    private static final int DATABASE_VERSION = 1;

    private DbHelper voterHelper;
    private final Context voterContext;
    private SQLiteDatabase voterDatabase;

    public VoterDatabaseHelper(Context c) {
        this.voterContext = c;
    }

    public VoterDatabaseHelper open() throws SQLException {
        voterHelper = new DbHelper(voterContext);
        voterDatabase = voterHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        voterHelper.close();
    }


    public void putData(VoterData voterData) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, voterData.getName());
        cv.put(VOTERID, voterData.getVoterId());
        cv.put(DOB, voterData.getDob());
        cv.put(MOBILE, voterData.getMobile());
        cv.put(ADDRESS, voterData.getAddress());
        cv.put(GENDER, voterData.getGender());
        cv.put(PASSWORD, voterData.getPassword());
        voterDatabase.insert(TABLE_NAME, null, cv);
    }

    public void deletePrevious(String username) {
        //delete existing entry
        voterDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                VOTERID + " = '" + username + "';");
    }

    public void editData(VoterData voterData) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, voterData.getName());
        cv.put(VOTERID, voterData.getVoterId());
        cv.put(DOB, voterData.getDob());
        cv.put(MOBILE, voterData.getMobile());
        cv.put(ADDRESS, voterData.getAddress());
        cv.put(GENDER, voterData.getGender());
        cv.put(PASSWORD, voterData.getPassword());

        voterDatabase.insert(TABLE_NAME, null, cv);
    }

    public Cursor getInfo() {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PASSWORD};
        Cursor cursor = voterDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public ArrayList<VoterData> getAllData() {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PASSWORD};
        Cursor cursor = voterDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new ArrayList<VoterData>();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iVoter = cursor.getColumnIndex(VOTERID);
        int iDob = cursor.getColumnIndex(DOB);
        int iMobile = cursor.getColumnIndex(MOBILE);
        int iAddress = cursor.getColumnIndex(ADDRESS);
        int iGender = cursor.getColumnIndex(GENDER);
        int iPassword = cursor.getColumnIndex(PASSWORD);

        ArrayList<VoterData> voterDatas = new ArrayList<>();
        VoterData voterData;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            voterData = new VoterData();
            voterData.setName(cursor.getString(iName));
            voterData.setVoterId(cursor.getString(iVoter));
            voterData.setDob(cursor.getString(iDob));
            voterData.setMobile(cursor.getString(iMobile));
            voterData.setAddress(cursor.getString(iAddress));
            voterData.setGender(cursor.getString(iGender));
            voterData.setPassword(cursor.getString(iPassword));
            voterDatas.add(voterData);
        }

        return voterDatas;
    }

    public VoterData getData(String voterId) {
        String[] columns = new String[]{KEY_ROWID, NAME, VOTERID, DOB, MOBILE,
                ADDRESS, GENDER, PASSWORD};
        Cursor cursor = voterDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new VoterData();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iVoter = cursor.getColumnIndex(VOTERID);
        int iDob = cursor.getColumnIndex(DOB);
        int iMobile = cursor.getColumnIndex(MOBILE);
        int iAddress = cursor.getColumnIndex(ADDRESS);
        int iGender = cursor.getColumnIndex(GENDER);
        int iPassword = cursor.getColumnIndex(PASSWORD);

        VoterData voterData = new VoterData();
        voterData.setVoterId("empty");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(iVoter).equals(voterId)) {
                voterData.setName(cursor.getString(iName));
                voterData.setVoterId(cursor.getString(iVoter));
                voterData.setDob(cursor.getString(iDob));
                voterData.setMobile(cursor.getString(iMobile));
                voterData.setAddress(cursor.getString(iAddress));
                voterData.setGender(cursor.getString(iGender));
                voterData.setPassword(cursor.getString(iPassword));
            }
        }
        return voterData;
    }

    public void clearDatabase() {
        String AUTOINCREMENT_RESET = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "';";
        voterDatabase.delete(TABLE_NAME, null, null);
        voterDatabase.execSQL(AUTOINCREMENT_RESET);
    }

    public void deleteData(String username) {
        String DELETE = "DELETE FROM " + TABLE_NAME +
                " WHERE " + VOTERID + " = " + "'" + username + "';";
        voterDatabase.execSQL(DELETE);
    }

    public String[] retrieveVoterIds() {
        Cursor c = voterDatabase.query(TABLE_NAME, new String[]{VOTERID}, null, null, null, null, null);
        if (c.getCount() == 0)
            return new String[]{"empty"};

        int iVoterId = c.getColumnIndex(VOTERID);

        String[] result = new String[100];
        int i = 0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext(), i++) {
            result[i] = c.getString(iVoterId);
        }

        return result;
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, TABLE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " VARCHAR2(20) NOT NULL, " +
                    VOTERID + " VARCHAR2(20) UNIQUE NOT NULL, " +
                    DOB + " VARCHAR2(20) NOT NULL, " +
                    MOBILE + " VARCHAR2(11) NOT NULL, " +
                    ADDRESS + " VARCHAR2(150) NOT NULL, " +
                    GENDER + " VARCHAR2(20) NOT NULL, " +
                    PASSWORD + " VARCHAR2(20));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + ";");
            onCreate(db);
        }
    }

}