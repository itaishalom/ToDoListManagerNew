package com.example.itai.todolistmanagernew;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.itai.todolistmanagernew.DBHandler.TODO_TABLE;

/**
 * Created by Itai on 22/04/2017.
 */

public class ToDoContentProvider extends ContentProvider implements android.content.ComponentCallbacks2{
public ToDoContentProvider(){}
    private DBHandler mDBHandler;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String TODO_INFO = "Info";
 //   private static final String TABLE_NAME = "ToDoTable";
    private static final int TODO_LIST_TABLE = 1;
    private static final int TODO_LIST_TABLE_BY_ID = 2;

    private static final String TAG = "ContentProvider";
    public static final String PROVIDER = "com.example.itai.todolistmanagernew.provider";
    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI(PROVIDER, TODO_TABLE, TODO_LIST_TABLE);          //INSERT
        sUriMatcher.addURI(PROVIDER, TODO_TABLE + "/*", TODO_LIST_TABLE_BY_ID);     //DELETE

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
  //      sUriMatcher.addURI("com.example.app.provider", "table3/#", 2);
    }



    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        mDBHandler = new DBHandler(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case TODO_LIST_TABLE:
                qb.setTables(TODO_TABLE);
                break;
        }
        Log.d(TAG,"Running query: "+ qb.buildQuery(null,null,null,null,null,null));
        Cursor c = mDBHandler.query(qb,projection,selection,selectionArgs,null,null,sortOrder);
        Log.d(TAG,"Found " +c.getCount() + " entries");
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableNAme = "";
        switch (sUriMatcher.match(uri)) {
            case TODO_LIST_TABLE:
                tableNAme = TODO_TABLE;

                //String Info = values.get()
            //    values.put("_id",ID);
        }
        if(!tableNAme.isEmpty()){
            long rowID = mDBHandler.insert(tableNAme,"",values);
                    if(rowID > 0){
                        Log.d(TAG,"New insert to table "+tableNAme);
                    }
                    return uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "";
        switch (sUriMatcher.match(uri)) {
            case TODO_LIST_TABLE_BY_ID:
                table = TODO_TABLE;
        }
        return mDBHandler.mDB.delete(table, "_id= '" + uri.getLastPathSegment() + "'",null);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
