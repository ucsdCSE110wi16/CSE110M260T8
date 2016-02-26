package com.example.anara.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.firebase.client.*;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class uHomeActivity extends AppCompatActivity {

    protected ArrayList<String> groupNames = new ArrayList<>();
    Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_home);
        final ListView groupView = (ListView) findViewById(android.R.id.list);
        Firebase.setAndroidContext(this);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, groupView, false);
        groupView.addHeaderView(header, null, false);

        Firebase mRef = ref.child("groupMembers");
        Query queryRef = mRef.orderByChild("userMem").equalTo(ref.getAuth().getUid());
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupNames.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String groupId = (String) child.child("groupMem").getValue();
                    Firebase gRef = new Firebase("https://burning-fire-7007.firebaseio.com/group");
                    Query qGref = gRef.orderByChild("groupId").equalTo(groupId);
                    /*FirebaseListAdapter<Group> mAdapter = new FirebaseListAdapter<Group>(this, Group.class, android.R.layout.simple_list_item_1, qGref) {
                        @Override
                        protected void populateView(android.view.View view, Group group, int position) {
                            ((TextView)view.findViewById(R.id.groupName)).setText(group.getGroupName());
                        }

                    };*/
                    qGref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot child: dataSnapshot.getChildren()) {
                                String name = (String) child.child("groupName").getValue();
                                groupNames.add(name);
                                Log.v("uHomeActivity","groups = " + groupNames);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(uHomeActivity.this, android.R.layout.simple_list_item_multiple_choice,groupNames);

                groupView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("uHomeActivity", firebaseError.getMessage());
            }
        });
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
        Intent intent = new Intent(uHomeActivity.this, CalActivity.class);
        startActivity(intent);
    }
}
