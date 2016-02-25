package com.example.anara.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//App landing page
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void signup(View button) {
        Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View button){
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
