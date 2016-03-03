package com.example.anara.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {

    Button joinButton;
    Button chatButton;
    TextView descTextView;
    ListView listView;
    ArrayList<String> groupid = new ArrayList<>();//to get the groupid from prev activity
    ArrayList<String> members = new ArrayList<>();
    Firebase myRef = new Firebase("https://burning-fire-7007.firebaseio.com");
    boolean inGroup = false; //to hold the curr user's status
    String grpName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        joinButton = (Button) findViewById(R.id.join);
        joinButton.setOnClickListener(this);
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
                    grpName = (String) child.child("groupName").getValue();
                    groupid.add(grpName);
                    descTextView.setText(grpName + ": \n" + desc);
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
        Firebase mRef = myRef.child("groupMembers");
        final Query userqueryRef = mRef.orderByChild("groupMem").equalTo(groupid.get(0));

        userqueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                members.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userId = (String) child.child("userMem").getValue();//UID

                    Firebase gRef = new Firebase("https://burning-fire-7007.firebaseio.com/user");
                    Query qGref = gRef.orderByChild("userId").equalTo(userId);
                    qGref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String email = (String) child.child("email").getValue();
                                if (email == myRef.getAuth().getProviderData().get("email"))
                                    inGroup = true;
                                members.add(email);
                            }
                        }

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
                if(!inGroup)
                {
                    Toast toast = Toast.makeText(GroupActivity.this, "Please join "+grpName+" first!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                Intent intent = new Intent(GroupActivity.this, ChatActivity.class);
                intent.putStringArrayListExtra("groupid", groupid);
                startActivity(intent);
                }
                break;
            case R.id.join:
                System.out.println("in join");
                if(inGroup){
                    Toast toast = Toast.makeText(GroupActivity.this, "You are in the group already!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(GroupActivity.this, "Welcome to group"+grpName+"!", Toast.LENGTH_SHORT);
                    toast.show();
                    GroupMembers gm = new GroupMembers();
                    gm.setGroupMem(groupid.get(0));
                    gm.setUserMem(myRef.getAuth().getUid());
                    membersListRef.child(gm.getGroupMem()).setValue(gm);
                    inGroup = true;

                }
                /*
                final Query queryRef = membersListRef.orderByChild("groupId").equalTo(groupid.get(0));
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            System.out.println(child.child("userMem").getValue());
                            if(child.child("userMem").getValue()== myRef.getAuth().getUid())
                            {
                                System.out.println("userMem == Uid");

                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                }); */
                break;
        }
           //     finish();
        }
    }