package com.shibedays.wordlistsql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ttbot on 2/11/2018.
 */

public class WordListOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = WordListOpenHelper.class.getSimpleName();

    // Has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String WORD_LIST_TABLE = "word_entries";
    private static final String DATABASE_NAME = "wordList";

    // Column names
    // ID will be auto incremented by the DB
    public static final String KEY_ID = "_id";
    public static final String KEY_WORD = "word";

    // ... and a string array of columns
    private static final String[] COLUMNS = { KEY_ID, KEY_WORD };

    private static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_WORD + " TEXT );";


    private SQLiteDatabase mWriteableDB;
    private SQLiteDatabase mReadableDB;
    public WordListOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create database if it doesn't exist. If it exists the below line is not run
        db.execSQL(WORD_LIST_TABLE_CREATE);
        fillDatabaseWithData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", white will destroy all old data.");
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(db);
    }

    private void fillDatabaseWithData(SQLiteDatabase db){
        String[] words = {"Android", "Adapter", "ListView", "AsyncTask",
                            "Android Studio", "SQLiteDatabase", "SQLOpenHelper",
                            "Data Model", "ViewHolder", "Android Performance", "OnClickListener"};
        ContentValues values = new ContentValues();

        for(int i = 0; i < words.length; i++){
            // Put column/value pairs into the container
            // put() overrides existing values
            values.put(KEY_WORD, words[i]);
            db.insert(WORD_LIST_TABLE, null, values);
        }
    }

    public WordItem query(int pos){
        String query = "SELECT * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + KEY_WORD + " ASC " +
                "LIMIT " + pos + ",1";
        Cursor cur = null;

        WordItem entry = new WordItem();
        try{
            if(mReadableDB == null){
                mReadableDB = getReadableDatabase();
            }
            cur = mReadableDB.rawQuery(query, null);
            cur.moveToFirst();
            entry.setmID(cur.getInt(cur.getColumnIndex(KEY_ID)));
            entry.setmWord(cur.getString(cur.getColumnIndex(KEY_WORD)));
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            if(cur != null)
            cur.close();
        }
        return entry;
    }

    public long insert(String word){
        long newID = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word);

        try{
            if(mWriteableDB == null){
                mWriteableDB = getWritableDatabase();
            }
            newID = mWriteableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.e(TAG, "INSERT EXCEPTION " + e.getMessage());
        }
        return newID;
    }

    public long count(){
        if(mReadableDB == null){
            mReadableDB = getReadableDatabase();
        }

        return DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE);
    }

    public int delete(int id){
        int deleted = 0;
        try{
            if(mWriteableDB == null){
                mWriteableDB = getWritableDatabase();
            }
            deleted = mWriteableDB.delete(WORD_LIST_TABLE,
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e){
            Log.e(TAG, "DELETE EXCEPTION: " + e.getMessage());
        }

        return deleted;
    }

    public int update(int id, String word){
        int numRowsUpdated = -1;
        try {
            if(mWriteableDB == null){
                mWriteableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_WORD, word);
            numRowsUpdated = mWriteableDB.update(WORD_LIST_TABLE,
                    values,
                    KEY_ID + " = ? ",
                    new String[]{String.valueOf(id)});
        } catch (Exception e){
            Log.e(TAG, "UPDATE EXCEPTION: " + e.getMessage());
        }

        return numRowsUpdated;
    }
}
