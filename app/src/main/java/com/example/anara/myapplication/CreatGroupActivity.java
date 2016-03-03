package com.example.anara.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class CreatGroupActivity extends AppCompatActivity {

   Firebase myRef = new Firebase("https://burning-fire-7007.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_group);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void submit(View button) {

        EditText etName = (EditText) findViewById(R.id.EditTextName);
        EditText etDesc = (EditText) findViewById(R.id.EditTextDesc);
        String name = etName.getText().toString();
        String description = etDesc.getText().toString();

        if(name == "" || description == ""){
            Toast toast = Toast.makeText(CreatGroupActivity.this, "Can't be empty!", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
        else {
            Firebase groupListRef = myRef.child("group");
            Group grp = new Group();
            grp.setGroupName(name);
            grp.setDescription(description);
            grp.setGroupId(groupListRef.push().getKey());
            groupListRef.child(grp.getGroupId()).setValue(grp);

            Firebase membersListRef = myRef.child("groupMembers");
            GroupMembers gm = new GroupMembers();
            gm.setGroupMem(grp.getGroupId());
            gm.setUserMem(myRef.getAuth().getUid());
            membersListRef.child(gm.getGroupMem()).setValue(gm);

            Intent intent = new Intent(CreatGroupActivity.this, GroupActivity.class);
            ArrayList<String> list = new ArrayList<String>();
            list.add(grp.getGroupId());
            intent.putStringArrayListExtra("groupid", list);
            startActivity(intent);

            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
