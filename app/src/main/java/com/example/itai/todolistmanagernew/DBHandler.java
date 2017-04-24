package com.example.itai.todolistmanagernew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Itai on 22/04/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String TODO_TABLE = "TodoTable";
    public static final String TASK_COLUMN = "Task";
    public static final String DATABASE_NAME = "ToDos.db";
    public static final int DATABASE_VERSION = 1;

    private Context mContext;
    SQLiteDatabase mDB;
    public SQLiteDatabase getopenedWritableDB() {return mDB;}

    public DBHandler(Context context)
    {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mDB = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE + TODO_TABLE +
        " (_id TEXT primary key, " +
                TASK_COLUMN + " TEXT );");
    }


    // If you change the database schema, you must increment the database version.



//    public DBHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);
//    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(String tableName, String s, ContentValues values)
    {
        SQLiteDatabase db =mDB;
        long retVal = -1;
        try{
            retVal = db.insert(tableName,"",values);
        }catch(Exception e){
            e.printStackTrace();
        }
        return retVal;
    }

    public Cursor query(SQLiteQueryBuilder qb, String[] projection, String selection, String[] selectionArgs, Object o, Object o1, String sortOrder){
        SQLiteDatabase db = mDB;
        Cursor c = qb.query(db, projection,selection,selectionArgs,null,null,sortOrder);
        return c;
    }
}