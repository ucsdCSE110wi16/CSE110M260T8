package com.example.anara.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {

    Button joinButton;
    Button leaveButton;
    Button chatButton;
    TextView descTextView;
    ArrayList<String> groupid;
    ArrayList<String> members;
    private ArrayAdapter arrayAdapter;

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
        joinButton = (Button)findViewById(R.id.join);
        joinButton.setOnClickListener(this);
        leaveButton = (Button)findViewById(R.id.quit);
        leaveButton.setOnClickListener(this);
        chatButton = (Button)findViewById(R.id.chat);
        chatButton.setOnClickListener(this);

        descTextView = (TextView)findViewById(R.id.goal);
        ListView memberListView =(ListView)findViewById(R.id.listView);

        //
        Intent curr = this.getIntent();
        if(curr.getStringArrayListExtra("groupid")!= null)
            groupid = curr.getStringArrayListExtra("groupid");



        Firebase myFirebaseRef = new Firebase("https://burning-fire-7007.firebaseio.com/group");
        Query queryRef = myFirebaseRef.orderByChild("groupId").equalTo(groupid.get(0));
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null)
                    descTextView.setText("Group doesn't exist.");

                else
                    descTextView.setText("Group Description: " );//+ snapshot.child("").getValue().toString()
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
        /*
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null)
                    descTextView.setText("Group doesn't exist.");

                else
                    descTextView.setText("Group Description: "+snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, members);
        memberListView.setAdapter(arrayAdapter);
        */
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join:
                Intent intent = new Intent(this,ChatActivity.class);
                ArrayList<String> list = new ArrayList<String>();
                list.add(groupid.get(0));
                intent.putStringArrayListExtra("groupid", list);
                startActivity(intent);
                /*
                joinButton.setVisibility(View.GONE);

                Firebase membersListRef = new Firebase("https://burning-fire-7007.firebaseio.com/groupMembers");
                GroupMembers gm = new GroupMembers();
                gm.setGroupMem(groupid.get(0));
                gm.setUserMem(membersListRef.getAuth().getUid());
                membersListRef.child(gm.getGroupMem()).setValue(gm);*/
                break;
            case R.id.chat:
                /*
                Intent intent = new Intent(this,ChatActivity.class);
                ArrayList<String> list = new ArrayList<String>();
                list.add(groupid.get(0));
                intent.putStringArrayListExtra("groupid", list);
                startActivity(intent);*/
                break;
            case R.id.quit:
                startActivity(new Intent(this,uHomeActivity.class));
                break;
        }
        finish();
    }
}