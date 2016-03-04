package com.example.anara.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.*;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class uHomeActivity extends AppCompatActivity {

    protected ArrayList<String> groupNames = new ArrayList<>();
    protected ArrayList<String> groupIds = new ArrayList<>();
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
                groupIds.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String groupId = (String) child.child("groupMem").getValue();
                    Firebase gRef = new Firebase("https://burning-fire-7007.firebaseio.com/group");
                    Query qGref = gRef.orderByChild("groupId").equalTo(groupId);

                    qGref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String name = (String) child.child("groupName").getValue();
                                groupNames.add(name);
                                String id = (String) child.child("groupId").getValue();
                                groupIds.add(id);
                                Log.v("uHomeActivity", "groups = " + groupNames);
                                Log.v("uHomeActivity", "ids = " + groupIds);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.e("uHomeActivity", firebaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("uHomeActivity", firebaseError.getMessage());
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(uHomeActivity.this, android.R.layout.simple_list_item_multiple_choice, groupNames);

        groupView.setAdapter(adapter);

        groupView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               Intent intent = new Intent(uHomeActivity.this, GroupActivity.class);
                ArrayList<String> list = new ArrayList<String>();
                list.add(groupIds.get(position-1));
                Log.v("uHomeActivity", "GroupId:" +list.get(0));
                intent.putStringArrayListExtra("groupid", list);
                startActivity(intent);
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

    public void logout(android.view.View button){
        ref.unauth();
        Intent intent = new Intent(uHomeActivity.this, StartActivity.class);
        startActivity(intent);
    }



}
