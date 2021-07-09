package algonquin.cst2335.desi0068;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageListFragment extends Fragment {

    RecyclerView chatList;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    MyChatAdapter adt;
    SQLiteDatabase db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);

        EditText text = chatLayout.findViewById(R.id.editText);
        Button send = chatLayout.findViewById(R.id.sendbtn);
        Button receive = chatLayout.findViewById(R.id.retrievebtn);
        chatList = chatLayout.findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter());

        MyOpenHelper opener = new MyOpenHelper(getContext());
        db = opener.getWritableDatabase();

        Cursor results = db.rawQuery("SELECT * FROM " + MyOpenHelper.TABLE_NAME + ";", null);
        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);

        //The cursor has a moveToNext() function which advances the current row by one
        while(results.moveToNext()) {
            long id = results.getInt(_idCol);
            String message = results.getString(messageCol);
            String time = results.getString(timeCol);
            int sendOrReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message, sendOrReceive, time, id));
        }

        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
       // LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false ));

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        send.setOnClickListener(clk -> {
            ChatMessage thisMessage = new ChatMessage(text.getText().toString(), 1, currentDateandTime);

            //A ContentValues is like an Intent, or a SharedPreferences object, where you associate variables with their values. Except here the variables are the column names.
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSendOrReceive());
            newRow.put(MyOpenHelper.col_time_sent, thisMessage.getTimeSent());
            long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
            thisMessage.setId(newId);

            messages.add(thisMessage);
            text.setText("");
            adt.notifyItemInserted(messages.size() - 1);
        });

        receive.setOnClickListener(clk -> {
            ChatMessage thisMessage = new ChatMessage(text.getText().toString(), 2, currentDateandTime);

            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSendOrReceive());
            newRow.put(MyOpenHelper.col_time_sent, thisMessage.getTimeSent());
            long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
            thisMessage.setId(newId);

            messages.add(thisMessage);
            text.setText("");
            adt.notifyItemInserted(messages.size() - 1);
        });

        return chatLayout;

    }


    private class MyRowView extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;
        int position = -1;

        public MyRowView(View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                ChatRoom parentActivity = (ChatRoom)getContext();
                int position = getAbsoluteAdapterPosition();
                parentActivity.userClickedMessage(messages.get(position), position);
               /*
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want to delete this message: " + messageText.getText())
                        .setTitle("Question: ")
                        .setNegativeButton("No", (dialog, c1) -> {
                        })
                        .setPositiveButton("Yes", (dialog, c1) -> {

                            position = getAbsoluteAdapterPosition();

                            ChatMessage removedMessage = messages.get(position);
                            messages.remove(position);
                            adt.notifyItemRemoved(position);

                            db.delete(MyOpenHelper.TABLE_NAME, "_id=?", new String[] {Long.toString(removedMessage.getId())});

                            Snackbar.make(messageText, "You deleted message # " + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", click -> {

                                        messages.add(position, removedMessage);//reinsert
                                        adt.notifyItemInserted(position);

                                        db.execSQL("INSERT INTO " + MyOpenHelper.TABLE_NAME + " VALUES('" + removedMessage.getId() +
                                                "','" + removedMessage.getMessage() +
                                                "','" + removedMessage.getSendOrReceive() +
                                                "','" + removedMessage.getTimeSent() + "');");
                                        // db.rawQuery("INSERT INTO " + MyOpenHelper.TABLE_NAME + " 'WHERE' _id 'like ? AND 'TimeSent 'LIKE ? AND 'SendOrReceive 'LIKE ?;", new String[]{"?", "?", "?"});

                                    }).show();

                        })
                        .create().show(); */

            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

        public void setPosition(int p) {
            position = p;
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowView> {

        @Override
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
            return thisRow.getSendOrReceive();//return 1 for send, 2 for receive
        }

        @Override
        public MyRowView onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            int layoutID;
            if (viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;
            View loadedRow = inflater.inflate(layoutID, parent, false);
            return new MyRowView(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyRowView holder, int position) {
            holder.setPosition(position);
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
        }


        @Override
        public int getItemCount() {
            return messages.size();
        }
    }


    public class ChatMessage {
        String message;
        int sendOrReceive;
        String timeSent;
        long id;

        public void setId(long l) {
            id = l;
        }

        public long getId() {
            return id;
        }

        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public ChatMessage(String message, int sendOrReceive, String timeSent, long id ){
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public String getTimeSent() {
            return timeSent;
        }
    }

}
