package com.example.anara.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {

    //   Button joinButton;
    Button leaveButton;
    Button chatButton;
    TextView descTextView;
    ArrayList<String> groupid = new ArrayList<>();
    //    ArrayList<String> userid = new ArrayList<>();//to take parameter userid
    ArrayList<String> members = new ArrayList<>();
    Firebase myRef = new Firebase("https://burning-fire-7007.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        joinButton = (Button) findViewById(R.id.join);
        //       joinButton.setOnClickListener(this);
        leaveButton = (Button) findViewById(R.id.quit);
        leaveButton.setOnClickListener(this);
        chatButton = (Button) findViewById(R.id.chat);
        chatButton.setOnClickListener(this);

        descTextView = (TextView) findViewById(R.id.goal);

        Intent curr = this.getIntent();
        if (curr.getStringArrayListExtra("groupid") != null)
            groupid = curr.getStringArrayListExtra("groupid");
        /*
        if (curr.getStringArrayListExtra("userid") != null)
            userid = curr.getStringArrayListExtra("userid");
        else userid.add("no user id passed from creatgroup");*/

        Firebase myFirebaseRef = myRef.child("group");
        Query queryRef = myFirebaseRef.orderByChild("groupId").equalTo(groupid.get(0));

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String desc = (String) child.child("description").getValue();
                    String name = (String) child.child("groupName").getValue();
                    groupid.add(name);
                    descTextView.setText(name + ": \n" + desc);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //       Log.e("uHomeActivity", firebaseError.getMessage());
            }
        });

        final ListView userView = (ListView) findViewById(R.id.listView);
        Firebase.setAndroidContext(this);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header2, userView, false);
        userView.addHeaderView(header, null, false);
        //      Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com/groupMembers");
        Firebase mRef = myRef.child("groupMembers");
        Query userqueryRef = mRef.orderByChild("groupMem").equalTo(groupid.get(0));
        userqueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                members.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userId = (String) child.child("userMem").getValue();
                    Firebase gRef = myRef.child("user");
                    //new Firebase("https://burning-fire-7007.firebaseio.com/user");
                    Query qGref = gRef.orderByChild("userId").equalTo("dd0bf07e-db80-49c7-8410-3d47d44ebb20");
                    /*FirebaseListAdapter<Group> mAdapter = new FirebaseListAdapter<Group>(this, Group.class, android.R.layout.simple_list_item_1, qGref) {
                        @Override
                        protected void populateView(android.view.View view, Group group, int position) {
                            ((TextView)view.findViewById(R.id.groupName)).setText(group.getGroupName());
                        }

                    };*/
                    qGref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String name = (String) child.child("userName").getValue();
                                members.add(name);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(GroupActivity.this, android.R.layout.simple_list_item_multiple_choice, members);
                userView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void onClick(View v) {
        final Firebase membersListRef = new Firebase("https://burning-fire-7007.firebaseio.com/groupMembers");
        switch (v.getId()) {
            case R.id.chat:
                Intent intent = new Intent(GroupActivity.this, ChatActivity.class);
                //       ArrayList<String> list = new ArrayList<String>();
                //      list.add(groupid.get(0));
                intent.putStringArrayListExtra("groupid", groupid);
                startActivity(intent);
                /*
                joinButton.setVisibility(View.GONE);

                Firebase membersListRef = new Firebase("https://burning-fire-7007.firebaseio.com/groupMembers");
                GroupMembers gm = new GroupMembers();
                gm.setGroupMem(groupid.get(0));
                gm.setUserMem(membersListRef.getAuth().getUid());
                membersListRef.child(gm.getGroupMem()).setValue(gm);*/
                break;
         /*     case R.id.chat:

                Intent intent = new Intent(this,ChatActivity.class);
                ArrayList<String> list = new ArrayList<String>();
                list.add(groupid.get(0));
                intent.putStringArrayListExtra("groupid", list);
                startActivity(intent);*/
            //          break;
            case R.id.quit:
                startActivity(new Intent(GroupActivity.this, uHomeActivity.class));
/*
                final Query queryRef = membersListRef.orderByChild("groupId").equalTo(groupid.get(0));

                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String group = (String) child.child("groupMem").getValue();
                            //NEED TO FIX USERID HERE
                            if(child.child("userMem").getValue() == myRef.getAuth().getUid())
                            {
                                membersListRef.child(group).removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                break;*/
        }
                finish();
        }
    }