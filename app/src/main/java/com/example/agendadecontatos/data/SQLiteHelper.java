package com.example.agendadecontatos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "agenda.db";
    public static final String DATABASE_TABLE = "contatos";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "nome";
    public static final String KEY_FONE = "fone";
    public static final String KEY_EMAIL = "email";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_TABLE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, " +  KEY_EMAIL + " TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
