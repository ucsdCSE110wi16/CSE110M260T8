package com.example.anara.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.firebase.client.Firebase;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); blarg.*/

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void submit(View button) {

        EditText etName = (EditText) findViewById(R.id.EditTextName);
        EditText etPass = (EditText) findViewById(R.id.EditTextPass);
        EditText etPass1 = (EditText) findViewById(R.id.EditTextPass1);
        EditText etEmail = (EditText) findViewById(R.id.EditTextEmail);
        String name = etName.getText().toString();
        String password = etPass.getText().toString();
        String passConfirm = etPass1.getText().toString();
        CharSequence email = etEmail.getText().toString();

        if (passConfirm.equals(password)) {
            Firebase userListRef = new Firebase("https://burning-fire-7007.firebaseio.com/user");
            User stud = new User();
            stud.setUserName(name);
            stud.setPassword(password);
            stud.setEmail(email);
            stud.setUserId(userListRef.push().getKey());
            userListRef.child(stud.getUserName()).setValue(stud);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Your password entries must match!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        Intent output = new Intent();
        setResult(RESULT_OK, output);
        finish();
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
