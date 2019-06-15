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
    public User user;


    private static final String DATABASE_NAME = "yemek_tarifi.sqlite";

    public static final String REGISTER_TABLE_NAME = "registeration";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String CREATE_REGISTER_TABLE = "CREATE TABLE "
            + REGISTER_TABLE_NAME + " ("
            + USERNAME + " TEXT, "
            + PASSWORD + " TEXT )";

    public static final String FOOD_TABLE_NAME = "foods";
    public static final String FOOD_NAME = "Food_Name";
    public static final String FOOD_IMG = "Food_Image";
    public static final String FOOD_DESC = "Food_Description";
    public static final String FOOD_TAGS = "Food_Tags";
    public static final String CREATE_FOOD_TABLE = "CREATE TABLE "
            + FOOD_TABLE_NAME + " ("
            + FOOD_NAME + " TEXT, "
            + FOOD_IMG + " BLOB, "
            + FOOD_DESC + " TEXT, "
            + FOOD_TAGS + " TEXT )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_REGISTER_TABLE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REGISTER_TABLE_NAME);
        this.onCreate(db);
    }

    public void insertUserData(User user) {
        this.user = user;
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, user.getUsername());
        contentValues.put(PASSWORD, user.getPassword());

        db.insert(REGISTER_TABLE_NAME, null, contentValues);
        db.close();
    }



    public void insertFoodData(Food food){
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_NAME,food.getFoodName());
        contentValues.put(FOOD_IMG,food.getFoodImage());
        contentValues.put(FOOD_DESC,food.getFoodDescription());
        contentValues.put(FOOD_TAGS,food.getFoodTag());

        db.insert(FOOD_TABLE_NAME,null,contentValues);
        db.close();

    }

    public String searchPass(String username) {
        db = this.getReadableDatabase();
        String query = "select username, password from " + REGISTER_TABLE_NAME;
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
