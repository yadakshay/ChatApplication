package com.example.user.chatapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Akshay on 21-11-2017.
 */

public class chatDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat.db";
    private static final int DATABASE_VERSION = 1;

    public chatDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CHAT_TABLE = "CREATE TABLE " +
                chatDatabaseContract.chatEntry.TABLE_NAME + " (" +
                chatDatabaseContract.chatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                chatDatabaseContract.chatEntry.COLUMN_CHAT_MESSAGE + " TEXT NOT NULL, " +
                chatDatabaseContract.chatEntry.COLUMN_SENT_RECEIVED + " TEXT NOT NULL, " +
                chatDatabaseContract.chatEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_CHAT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + chatDatabaseContract.chatEntry.TABLE_NAME);
        onCreate(db);
    }
}

