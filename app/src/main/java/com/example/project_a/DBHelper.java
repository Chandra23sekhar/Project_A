package com.example.project_a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbName = "users.db";
    private static final String rented_vehicles = "rented_vehicles"; // table name for storing the rented vehicles details
    private static final int dbVersion = 1;


//    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, dbName, factory, dbVersion);
//    }

    public DBHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables on create
        db.execSQL("CREATE TABLE rented_vehicles (vehicle_id varchar(10), date VARCHAR(10), source_loc VARCHAR(400), dest_loc VARCHAR(400), num_people INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addRental(String vehicle_id, String date, String source_loc, String dest_loc, int num_people) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("vehicle_id", vehicle_id);
        contentValues.put("date", date);
        contentValues.put("source_loc", source_loc);
        contentValues.put("dest_loc", dest_loc);
        contentValues.put("num_people", num_people);

        long result = sqLiteDatabase.insert(rented_vehicles, null, contentValues);
        sqLiteDatabase.close();

        return result;
    }

    public String showSpecificTable(String tableName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cur = sqLiteDatabase.rawQuery("SELECT * FROM " + tableName + " ;", null);

        String final_result = "";

        while (cur.moveToNext()) {
            final_result += cur.getString(0) + ":" + cur.getString(1) + cur.getString(2) + "\n";
        }
        sqLiteDatabase.close();
        return final_result;
    }


    /**
     * NOT WORKING, (App crashing)
     **/
    public void deleteDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        this.deleteDatabase();
//        long res = sqLiteDatabase.execSQL("DROP ");
    }
}
