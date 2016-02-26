package com.example.anara.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.firebase.client.*;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class uHomeActivity extends ListActivity {
    Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com/groupMembers");
    Query queryRef = ref.orderByChild("userId").equalTo(ref.getAuth().getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_home);
        ListView groupView = (ListView) findViewById(android.R.id.list);
        Firebase.setAndroidContext(this);

        FirebaseListAdapter<Group> mAdapter = new FirebaseListAdapter<Group>(this, Group.class, android.R.layout.simple_list_item_1, queryRef) {
            @Override
            protected void populateView(android.view.View view, Group group, int position) {
                ((TextView)view.findViewById(R.id.groupName)).setText(group.getGroupName());
            }

        };
        /*queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                HashMap<String, Object> groups = (HashMap<String, Object>) snapshot.getValue();
                for (Object group : groups.values()) {
                    HashMap<String, Object> groupMap = (HashMap<String, Object>) group;
                    String groupId = (String) groupMap.remove("id");
                    Firebase gRef = new Firebase("https://burning-fire-7007.firebaseio.com/group");
                    Query querygRef = gRef.orderByChild("name");
                    querygRef.addChildEventListener(new ChildEventListener(){
                        @Override
                        public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                            HashMap<String, Object> groups = (HashMap<String, Object>) snapshot.getValue();
                        for(Object group : groups.values()) {
                            HashMap<String, Object> groupMap = (HashMap<String, Object>) group;
                            String groupId = (String) groupMap.remove("id");
                            if(!list.contains(groupId)) {
                                String name = (String) groupMap.remove("name");
                                String description = (String) groupMap.remove("desc");
                                Group result = new Group(groupId, name, description);
                                list.add(result);
                            }
                        }
                    }
                        public void onChildRemoved(DataSnapshot snapshot){}
                        public void onChildChanged(DataSnapshot snapshot, String string){}
                        public void onChildMoved(DataSnapshot snapshot, String string){}
                        public void onCancelled(FirebaseError e){}
                    });
                    // Query for all people contacts using the Contacts.People convenience class.
                    // Put a managed wrapper around the retrieved cursor so we don't have to worry about
                    // requerying or closing it as the activity changes state.
                    mCursor = this.getContentResolver().query(People.CONTENT_URI, null, null, null, null);
                    startManagingCursor(mCursor);

                    // Now create a new list adapter bound to the cursor.
                    // SimpleListAdapter is designed for binding to a Cursor.
                    ListAdapter adapter = new SimpleCursorAdapter(
                            this, // Context.
                            android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor
                            rows).
                            mCursor,                                              // Pass in the cursor to bind to.
                    new String[]{People.NAME, People.COMPANY},           // Array of cursor columns to bind to.
                            new int[]{android.R.id.text1, android.R.id.text2})*/
        ;  // Parallel array of which template objects to bind to those columns.

                    // Bind to our new adapter.
                groupView.setAdapter(mAdapter);
    }

    public void searchG(android.view.View button){
        Intent intent = new Intent(uHomeActivity.this, Search.class);
        startActivity(intent);
    }
/*
    public void create(android.view.View button){
        Intent intent = new Intent(uHomeActivity.this, CreatGroupActivity.class);
        startActivity(intent);
    }
*/
public void create(android.view.View button){

    //PASS THE USERID AS A PARAMETER TO CREATGROUPACTIVITY.JAVA
    Intent intent = new Intent(uHomeActivity.this, CreatGroupActivity.class);
    ArrayList<String> list = new ArrayList<String>();
    list.add(ref.getAuth().getUid());//NEED TO FIX THE USER ID HERE...
    intent.putStringArrayListExtra("userid", list);
    startActivity(intent);
}

    public void calendar(android.view.View button){
        Intent intent = new Intent(uHomeActivity.this, ChatActivity.class);
        startActivity(intent);
    }
}
