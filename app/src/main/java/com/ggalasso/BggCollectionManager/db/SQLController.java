package com.ggalasso.BggCollectionManager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ggalasso.BggCollectionManager.db.Schema.CategoryHelper;
import com.ggalasso.BggCollectionManager.db.Schema.DBhelper;
import com.ggalasso.BggCollectionManager.model.UtilityConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Edward on 9/8/2015.
 */
public class SQLController {


    Context context;
    SQLiteDatabase database;

    private DBhelper dbHelper;

    public SQLController() {

    }

    public SQLController(Context ctx) {
        context = ctx;
        //dbHelper = new DBhelper(context);
    }

    private SQLController openConnection() throws SQLException {
        dbHelper = DBhelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void open() {
        try {
            openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void destroyDB() {
        open();
        dbHelper.deleteDatabase(context);
        close();
    }

    public Cursor fetchFromDB(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        Cursor cursor = database.query(
                table,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy,
                limit
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Integer fetchTableCount(String tableName) {
        open();
        Integer count = 0;
        String query = "SELECT COUNT(*) FROM " + tableName;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToNext();
            count = cursor.getInt(0);
        }
        Log.d("BGCM-SQL", "Fetch Table Count = " + count);
        close();
        return count;
    }

//    public Cursor fetchRawQuery(String tableName, String[] selectionArgs) {
//        open();
//        String tableQuery = "SELECT * FROM " + tableName;
//        Log.d("BGCM-SQL", "ATTEMPTING RAQ QUERY = " + tableQuery + " with args: " + selectionArgs.toString());
//        Cursor cursor = database.rawQuery(tableQuery, selectionArgs);
//        close();
//        return cursor;
//    }

    public Integer fetchTableCount(String tableName, String whereClause) {
        open();
        Integer count = 0;
        //mg_bg_id = 8
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToNext();
            count = cursor.getInt(0);
        }
        Log.d("BGCM-SQL", "Fetch Table Count = " + count);
        close();
        return count;
    }


    public void dropTable(String tableName) {
        open();
        dbHelper.dropTable(database, tableName);
        close();
        String[] test = new String[9];
    }

    public void deleteAllRowsFromTable(String tableName) {
        open();
        database.delete(tableName, null, null);
        Log.d("BGCM-SQL", "Deleted all rows from " + tableName);
        close();
    }

    public void deleteFromTableWhere(String tableName, String whereClause) {
        open();
        Integer numRowsDeleted = database.delete(tableName, whereClause, null);
        Log.d("BGCM-SQL", "Deleted rows from " + tableName + " WHERE " + whereClause + "\n\r Number of rows deleted: "
                + numRowsDeleted);
        close();
    }


    protected String getRowValues(String key, ArrayList<String> values) {
        String sql_rows = "";
        for (String value : values) {
            sql_rows += "(\"" + key + "\", \"" + value + "\"),";
        }
        return sql_rows;
    }

    protected String getRowValue(String key, String value) {
        return "(\"" + key + "\", \"" + value + "\"),";
    }

    @NonNull
    protected String getColumns(List<String> columns) {
        String sql_columns = "";
        for (String column : columns) {
            sql_columns += column + ", ";
        }
        sql_columns = sql_columns.substring(0, sql_columns.length() - UtilityConstants.TRIM_COMMA_AND_SPACE.getValue());
        sql_columns += ")";
        return sql_columns;
    }

    protected void insertToDatabase(String tableName, ContentValues cv) {
        open();
        database.insert(tableName, null, cv);
        close();
    }

    protected void insertToDatabase(String insertSQL) {
        open();
        database.execSQL(insertSQL);
        close();
    }

    protected Cursor executeDBQuery(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor csr = database.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
        return csr;
    }

    public void getAllDatabaseTableData(String tableName) {
        open();
        String query = "SELECT * FROM " + tableName + ";";
        Cursor cur = database.rawQuery(query, null);
        int columnCount = cur.getColumnCount();
        int coltype = 0;
        String results = "";
        if (cur != null) {
            while (cur.moveToNext()) {
                for (int i = 0; i < columnCount; i++) {
                    coltype = cur.getType(i);
                    if (coltype == 0) {
                        results += "NULL,";
                    } else if (coltype == 1) {
                        results += Integer.toString(cur.getInt(i)) + ",";
                    } else if (coltype == 3) {
                        results += cur.getString(i) + ",";
                    } else {
                        results += "NOEXIST,";
                    }
                }
                results += "\n";
            }
            Log.d("BGCM-SQL", results);
        } else {
            Log.d("BGCM-SQL", "No data for table " + tableName);
        }
        close();
    }

    public static <T> int genericPrimitiveListCounter(T[] list, T itemToCount) {
        int count = 0;
        if (itemToCount == null) {
            for (T listItem : list) if (listItem == null) count++;
        } else {
            for (T listItem : list) if (itemToCount.equals(listItem)) count++;
        }
        return count;
    }

//    public <T> ArrayList<T> SelectWithoutWrapper(T foo){
//        ArrayList<T> list = new ArrayList<>();
//    }

    public <T> ArrayList<String> getFieldsForObject(Class<T> foo) {
        ArrayList<String> list = new ArrayList<>();
        Field[] fields = foo.getDeclaredFields();

        try {

            for (Field field : fields) {
                list.add(field.getName());
            }
        } catch (Exception ex) {
            return list;
        }
        return list;
    }

    // This is working throw away code that will help when we get further along in our
    // research of generics
    public <T> ArrayList<T> SelectAll(Class<T> foo) {
        ArrayList<T> list = new ArrayList<>();
        Field[] fields = foo.getDeclaredFields();
        Class cls[] = new Class[]{String.class, String.class, String.class};

        try {
            Constructor<?> constructor = foo.getConstructor(cls);//foo.getConstructor(foo);
            Constructor<?>[] constructors = foo.getConstructors();

            for (Constructor<?> c : constructors) {
                Class<?>[] pTypes = c.getParameterTypes();
                String test = "";
            }

            list.add((T) constructor.newInstance("TestName", "5", "Category"));
            list.add((T) constructor.newInstance("TestName1", "5", "Category"));
            //list.add(constructor);

        } catch (Exception ex) {
            return list;
        }
        return list;
    }

    // This is working throw away code that will help when we get further along in our
    // research of generics
    public <T> ArrayList<T> usingGenericCanWeGetToTdotClass(Class<T> foo) {
        ArrayList<T> list = new ArrayList<>();
        Field[] fields = foo.getDeclaredFields();
        Class cls[] = new Class[]{String.class, String.class, String.class};

        try {
            Constructor<?> constructor = foo.getConstructor(cls);//foo.getConstructor(foo);
            Constructor<?>[] constructors = foo.getConstructors();

            for (Constructor<?> c : constructors) {
                Class<?>[] pTypes = c.getParameterTypes();
                String test = "";
            }

            list.add((T) constructor.newInstance("TestName", "5", "Category"));
            list.add((T) constructor.newInstance("TestName1", "5", "Category"));
            //list.add(constructor);

        } catch (Exception ex) {
            return list;
        }
        return list;
    }
}
