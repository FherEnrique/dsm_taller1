package com.dsm.exam.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dsm.exam.model.Auth;

public class AuthService extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dsmExam";
    private static final String TABLE_AUTH = "auth";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public AuthService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AUTH_TABLE = "CREATE TABLE " + TABLE_AUTH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT)";
        db.execSQL(CREATE_AUTH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTH);
        onCreate(db);
    }

    public void addDefaultUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, "Fhernando");
        values.put(KEY_EMAIL, "fclimaco@applaudostudios.dev");
        values.put(KEY_PASSWORD, "1234");

        db.insert(TABLE_AUTH, null, values);
        db.close();
    }

    public Auth getAuthUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor = db.query(TABLE_AUTH, new String[] { KEY_ID,
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD }, KEY_EMAIL + "=? AND " + KEY_PASSWORD + "=?",
                new String[] { email, password }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            return new Auth(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }

        return null;
    }
}
