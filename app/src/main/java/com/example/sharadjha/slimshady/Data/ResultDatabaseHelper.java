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
public class ResultDatabaseHelper {

    public static final String KEY_ROWID = "id";
    public static final String NAME = "name";
    public static final String WINNER = "wincan";
    public static final String IMAGE = "image";
    public static final String NOVOTES = "novotes";
    public static final String DECLARED = "declared";

    private static final String TABLE_NAME = "RESULT";
    private static final String DATABASE_TABLE = "result_database";
    private static final int DATABASE_VERSION = 1;

    private DbHelper candHelper;
    private final Context candContext;
    private SQLiteDatabase candDatabase;

    public ResultDatabaseHelper(Context c) {
        this.candContext = c;
    }

    public ResultDatabaseHelper open() throws SQLException {
        candHelper = new DbHelper(candContext);
        candDatabase = candHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        candHelper.close();
    }


    public void putData(ResultData resultData) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, resultData.getName());
        cv.put(WINNER, resultData.getWinningCandidate());
        cv.put(IMAGE, String.valueOf(resultData.getCandidateImage()));
        cv.put(NOVOTES, String.valueOf(resultData.getNoOfVotes()));
        cv.put(DECLARED, String.valueOf(resultData.isDeclared()));
        candDatabase.insert(TABLE_NAME, null, cv);
    }

    public void deletePrevious(String username) {
        //delete existing entry
        candDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                NAME + " = '" + username + "';");

    }

    public void editData(ResultData resultData) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, resultData.getName());
        cv.put(WINNER, resultData.getWinningCandidate());
        cv.put(IMAGE, String.valueOf(resultData.getCandidateImage()));
        cv.put(NOVOTES, String.valueOf(resultData.getNoOfVotes()));
        cv.put(DECLARED, String.valueOf(resultData.isDeclared()));
        candDatabase.insert(TABLE_NAME, null, cv);
    }

    public Cursor getInfo() {
        String[] columns = new String[]{KEY_ROWID, NAME, WINNER, IMAGE, NOVOTES, DECLARED};
        Cursor cursor = candDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public ArrayList<ResultData> getAllData() {
        String[] columns = new String[]{KEY_ROWID, NAME, WINNER, IMAGE, NOVOTES, DECLARED};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new ArrayList<ResultData>();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iWinner = cursor.getColumnIndex(WINNER);
        int iResultImage = cursor.getColumnIndex(IMAGE);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);
        int iDeclared = cursor.getColumnIndex(DECLARED);

        ArrayList<ResultData> resultDatas = new ArrayList<>();
        ResultData resultData;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            resultData = new ResultData();
            resultData.setName(cursor.getString(iName));
            resultData.setWinningCandidate(cursor.getString(iWinner));
            resultData.setCandidateImage(Integer.parseInt(cursor.getString(iResultImage)));
            resultData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
            resultData.setDeclared(Boolean.parseBoolean(cursor.getString(iDeclared)));
            resultDatas.add(resultData);
        }

        return resultDatas;
    }

    public ResultData getDataThroughName(String name) {
        String[] columns = new String[]{KEY_ROWID, NAME, IMAGE, NOVOTES, DECLARED};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new ResultData();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iWinner = cursor.getColumnIndex(WINNER);
        int iResultImage = cursor.getColumnIndex(IMAGE);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);
        int iDeclared = cursor.getColumnIndex(DECLARED);

        ResultData resultData = new ResultData();
        resultData.setName("empty");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(iName).equals(name)) {
                Log.e("name", "found");
                resultData.setName(cursor.getString(iName));
                resultData.setWinningCandidate(cursor.getString(iWinner));
                resultData.setCandidateImage(Integer.parseInt(cursor.getString(iResultImage)));
                resultData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
                resultData.setDeclared(Boolean.parseBoolean(cursor.getString(iDeclared)));
            }
        }
        return resultData;
    }


    public ResultData getData(String usernameToEdit) {
        String[] columns = new String[]{KEY_ROWID, NAME, WINNER, IMAGE, NOVOTES, DECLARED};
        Cursor cursor = candDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return new ResultData();

        int iRowId = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(NAME);
        int iWinner = cursor.getColumnIndex(WINNER);
        int iResultImage = cursor.getColumnIndex(IMAGE);
        int iNoOfVotes = cursor.getColumnIndex(NOVOTES);
        int iDeclared = cursor.getColumnIndex(DECLARED);

        ResultData resultData = new ResultData();
        resultData.setName("empty");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(iName).equals(usernameToEdit)) {
                Log.e("username", "found");
                resultData.setName(cursor.getString(iName));
                resultData.setCandidateImage(Integer.parseInt(cursor.getString(iResultImage)));
                resultData.setWinningCandidate(cursor.getString(iWinner));
                resultData.setNoOfVotes(Integer.parseInt(cursor.getString(iNoOfVotes)));
                resultData.setDeclared(Boolean.parseBoolean(cursor.getString(iDeclared)));
            }
        }
        return resultData;
    }

    public void clearDatabase() {
        String AUTOINCREMENT_RESET = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "';";
        candDatabase.delete(TABLE_NAME, null, null);
        candDatabase.execSQL(AUTOINCREMENT_RESET);
    }

    public void deleteData(String name) {
        String DELETE = "DELETE FROM " + TABLE_NAME +
                " WHERE " + NAME + " = " + "'" + name + "';";
        candDatabase.execSQL(DELETE);
    }

    public String[] retrieveUsernames() {
        Cursor c = candDatabase.query(TABLE_NAME, new String[]{NAME}, null, null, null, null, null);
        if (c.getCount() == 0)
            return new String[]{"empty"};

        int iUsername = c.getColumnIndex(NAME);

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
            Log.e("CREATES", "SUCCESS");
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " VARCHAR2(20) NOT NULL, " +
                    WINNER + " VARCHAR2(30), " +
                    IMAGE + " VARCHAR2(100), " +
                    NOVOTES + " VARCHAR2(10), " +
                    DECLARED + " VARCHAR2(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + ";");
            onCreate(db);
        }
    }

}