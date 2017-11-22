package com.example.user.chatapplication.Database;

import android.provider.BaseColumns;

/**
 * Created by Akshay on 21-11-2017.
 */

public class chatDatabaseContract {

    //constructor
    private chatDatabaseContract(){}

    public static final class chatEntry implements BaseColumns {
        public static final String TABLE_NAME = "chatTable";
        public static final String COLUMN_CHAT_MESSAGE = "chatMessage";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_SENT_RECEIVED= "sentReceived";
    }
}
