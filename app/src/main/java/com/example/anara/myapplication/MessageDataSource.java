package com.example.anara.myapplication;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Chang on 2/24/2016.
 */
public class MessageDataSource {

    private static final Firebase theBase = new Firebase("https://burning-fire-7007.firebaseio.com/chat");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddss");
    private static final String TAG = "MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";

    public static void saveMessage(Messages message, String convoId){
        Date date = message.getMessageDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getMsg());
        msg.put(COLUMN_SENDER, convoId);
        theBase.child(convoId).child(key).setValue(msg);
    }

    public static MessagesListener addMessagesListener(String convoId, final MessagesCallbacks callbacks){
        MessagesListener listener = new MessagesListener(callbacks);
        theBase.child(convoId).addChildEventListener(listener);
        return listener;
    }

    public static void stop(MessagesListener listener){
        theBase.removeEventListener(listener);
    }

    public static class MessagesListener implements ChildEventListener{

        private MessagesCallbacks callbacks;

        public MessagesListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String, String> msg = (HashMap<String, String>)dataSnapshot.getValue();
            Messages message = new Messages();
            message.setMsgId(msg.get(COLUMN_SENDER));
            message.setMsgId(msg.get(COLUMN_TEXT));

            try {
                message.setMessageDate(sDateFormat.parse(dataSnapshot.getKey()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(callbacks != null){
                callbacks.onMessageAdded(message);
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    public interface MessagesCallbacks{
        public void onMessageAdded(Messages message);
    }

}
