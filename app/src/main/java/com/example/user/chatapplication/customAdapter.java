package com.example.user.chatapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.chatapplication.Database.chatDatabaseContract;

import static com.example.user.chatapplication.ChatActivity.SENT_KEY;

/**
 * Created by Akshay on 22-11-2017.
 */

public class customAdapter extends RecyclerView.Adapter<customAdapter.customAdapterViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public customAdapter(Context c, Cursor cursor) {
        mContext = c;
        mCursor = cursor;
    }

    @Override
    public customAdapter.customAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_layout, parent, false);
        return new customAdapter.customAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(customAdapter.customAdapterViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return; // return if null

        String text = mCursor.getString(mCursor.getColumnIndex(chatDatabaseContract.chatEntry.COLUMN_CHAT_MESSAGE));
        String messageType = mCursor.getString(mCursor.getColumnIndex(chatDatabaseContract.chatEntry.COLUMN_SENT_RECEIVED));
        long id = mCursor.getLong(mCursor.getColumnIndex(chatDatabaseContract.chatEntry._ID));

        if(messageType.matches(SENT_KEY)){
            holder.relativeLayout.setBackgroundResource(R.drawable.outgoing_message);
            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.messageTextView.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            holder.messageTextView.setLayoutParams(lp);

            LinearLayout.LayoutParams layParam =
                    (LinearLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
            layParam.gravity = Gravity.RIGHT;
            holder.relativeLayout.setLayoutParams(layParam);
        }else{
            holder.relativeLayout.setBackgroundResource(R.drawable.incomming_message);;
            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.messageTextView.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            holder.messageTextView.setLayoutParams(lp);

            LinearLayout.LayoutParams layParam =
                    (LinearLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
            layParam.gravity = Gravity.LEFT;
            holder.relativeLayout.setLayoutParams(layParam);
        }
        holder.messageTextView.setText(text);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class customAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView messageTextView;
        RelativeLayout relativeLayout;
        public customAdapterViewHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageText);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.layoutContainingBackground);
        }
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
