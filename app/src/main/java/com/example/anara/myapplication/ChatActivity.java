package com.example.anara.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends ActionBarActivity implements View.OnClickListener,
        MessageDataSource.MessagesCallbacks {

    public static final String USER_EXTRA = "USER";
    public static final String TAG = "ChatActivity";
    private ArrayList<Messages> mMessages;
    private MessagesAdapter messageAdapter;
    private String mRecipient;
    private ListView mListView;
    private Date mLastMessageDate = new Date();
    private String mConvoId;
    private MessageDataSource.MessagesListener mListener;
    //List<Messages> Message_List;
    //private MessageDataSource dataSource;
    //public static SharedPreferences noteprefs;
    //HashMap<String, String> MapListMessages = new HashMap<String, String>();
    ArrayList<String> groupid;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_chat);

        mListView = (ListView)findViewById(R.id.messages_list);
        mMessages = new ArrayList<>();
        messageAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(messageAdapter);

        Intent curr = this.getIntent();
        if(curr.getStringArrayListExtra("groupid")!= null)
            groupid = curr.getStringArrayListExtra("groupid");

        mRecipient = groupid.get(1);

        setTitle(mRecipient);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        mConvoId = groupid.get(0);

        mListener = MessageDataSource.addMessagesListener(mConvoId, this);

    }

    @Override
    public void onClick(View view) {
        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Messages msg = new Messages();
        msg.setMessageDate(new Date());
        msg.setMsg(newMessage);
        msg.setMsgId(groupid.get(2));

        MessageDataSource.saveMessage(msg, mConvoId);
    }
    @Override
    public void onMessageAdded(Messages message){
        mMessages.add(message);
        messageAdapter.notifyDataSetChanged();
    }
    /*
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putStringArrayListExtra("groupid", groupid);
        startActivity(intent);
        //super.onBackPressed();  // optional depending on your needs
    }
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }

    private class MessagesAdapter extends ArrayAdapter<Messages>{
        MessagesAdapter(ArrayList<Messages> messages){
            super(ChatActivity.this, R.layout.message, R.id.message, messages);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            convertView = super.getView(position, convertView, parent);
            Messages message = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.message);
            String nameString = message.getMsgId() + ": " + message.getMsg();
            nameView.setText(nameString);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getMsgId().equals(groupid.get(2))){

               if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                   nameView.setBackground(getDrawable(R.drawable.bubble_right_green));
                }else{
                   nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                }

                layoutParams.gravity = Gravity.RIGHT;
            }else{

                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_left_gray));
                } else{
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                }

                layoutParams.gravity = Gravity.LEFT;
            }

            nameView.setLayoutParams(layoutParams);

            return convertView;
        }
    }

}
