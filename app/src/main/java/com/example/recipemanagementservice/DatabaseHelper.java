package com.example.recipemanagementservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by mustafatozluoglu on 6.06.2019
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeration";
    public static final String COL_1 = "Username";
    public static final String COL_2 = "Password";
    public static final String CREATE_REGISTER_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COL_1 + " TEXT, "
            + COL_2 + " TEXT )";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_REGISTER_TABLE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insertData(User user) {
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, user.getUsername());
        contentValues.put(COL_2, user.getPassword());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public String searchPass(String username) {
        db = this.getReadableDatabase();
        String query = "select username, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String user, pass = null;

        if (cursor.moveToFirst()) {
            do {
                user = cursor.getString(0);
                if (user.equals(username)) {
                    pass = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return pass;
    }

}
