package com.ggalasso.simpletest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edward on 9/8/2015.
 */
public class DBhelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DB_Name = "bgm_db";
    // Database Version
    private static final int DB_Version = 1;
    private static DBhelper ourInstance = null;
    private BoardGameHelper _bgh = BoardGameHelper.getInstance();
    private CategoryHelper _cah = CategoryHelper.getInstance();

    private DBhelper(Context context) {
        super(context, DB_Name, null, DB_Version);
        Log.i("BGCM-DBH", "Instantiated DBhelper");
    }

    public static DBhelper getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (DBhelper.class) {
                if (ourInstance == null) {
                    ourInstance = new DBhelper(context);
                }
            }
        }
        return ourInstance;
    }

    // Create Table Query
    public String createTable(ArrayList<String> columns, String tableName) {
        StringBuilder results = new StringBuilder();
        int colSize = columns.size();

        results.append("CREATE TABLE " + tableName + " (");
        //Prepend the first column to the results query
        results.append(columns.get(0));
        //Loop through starting at the second column
        for (int i = 1; i < colSize; i++) {
            results.append(", " + columns.get(i));
        }
        results.append(");");
        Log.d("BGCM-DBH", "Create table query string is: " + results.toString());

        return results.toString();
    }

    public void deleteDatabase(Context ctx) {
        Log.d("BGCM-DBH", "Attempting to Delete Database");
        ctx.deleteDatabase(DB_Name);
        Log.d("BGCM-DBH", "Database Deleted");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable(_bgh.getColumns(), _bgh.getTableName()));
        Log.d("BGCM-DBH", "Table " + _bgh.getTableName() + " was created.");
        db.execSQL(createTable(_cah.getColumns(), _cah.getTableName()));
        Log.d("BGCM-DBH", "Table " + _cah.getTableName() + " was created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + _bgh.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + _cah.getTableName());
        onCreate(db);
        Log.d("BGCM-DBH", "Table " + _bgh.getTableName() + " was upgraded, from " + oldVersion + " to " + newVersion);
    }

    public void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.d("BGCM-DBH", "Table " + tableName + " was dropped.");
    }

}
