package com.example.user.chatapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.chatapplication.Database.chatDatabaseContract;
import com.example.user.chatapplication.Database.chatDbHelper;

import static com.example.user.chatapplication.Database.chatDatabaseContract.chatEntry.TABLE_NAME;

public class ChatActivity extends AppCompatActivity {
    private EditText enteredText;
    private RecyclerView chat;
    public static String SENT_KEY = "sent";
    public static String RECEIVE_KEY = "received";
    private chatDbHelper mDbHelper;
    private SQLiteDatabase db;
    private customAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        enteredText = (EditText) findViewById(R.id.enteredText);
        chat = (RecyclerView) findViewById(R.id.chatWindow);

        mDbHelper = new chatDbHelper(this);
        db = mDbHelper.getWritableDatabase();

        Cursor cursor = getAllMessages();
        //Toast.makeText(this, cursor.toString(), Toast.LENGTH_SHORT).show();
        chat.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new customAdapter(this, cursor);
        chat.setAdapter(mAdapter);
        chat.scrollToPosition(cursor.getCount() - 1);
    }

    private Cursor getAllMessages() {
        return db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                chatDatabaseContract.chatEntry.COLUMN_TIMESTAMP
        );
    }

    public void sendMessage(View view) {
        String message = enteredText.getText().toString();
        if(TextUtils.isEmpty(message)){
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(chatDatabaseContract.chatEntry.COLUMN_CHAT_MESSAGE, message);
        cv.put(chatDatabaseContract.chatEntry.COLUMN_SENT_RECEIVED, SENT_KEY);

        long insertId = db.insert(TABLE_NAME, null, cv);
        if(insertId > 0){
            //Toast.makeText(this, "inserted!", Toast.LENGTH_SHORT).show();
            sendRandomReply();
            Cursor c = getAllMessages();
            enteredText.setText(null);
            mAdapter.swapCursor(c);
            chat.scrollToPosition(c.getCount() - 1);
        }else{
            Toast.makeText(this, "failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRandomReply() {
        String response = "Random Response";
        ContentValues cvResponse = new ContentValues();
        cvResponse.put(chatDatabaseContract.chatEntry.COLUMN_CHAT_MESSAGE, response);
        cvResponse.put(chatDatabaseContract.chatEntry.COLUMN_SENT_RECEIVED, RECEIVE_KEY);
        long insertId = db.insert(TABLE_NAME, null, cvResponse);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clearChat){
            db.delete(TABLE_NAME, null, null);
            mAdapter.swapCursor(getAllMessages());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
