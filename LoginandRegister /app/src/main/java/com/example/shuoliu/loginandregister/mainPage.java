package com.example.shuoliu.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class mainPage extends AppCompatActivity implements View.OnClickListener {

    Button bSearch, bBack, bCalender, bLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bSearch = (Button) findViewById(R.id.bSearch);
        bBack = (Button) findViewById(R.id.bBack);
        bCalender = (Button) findViewById(R.id.bCalender);
        bLogout = (Button) findViewById(R.id.bLogout);

        bSearch.setOnClickListener(this);
        bBack.setOnClickListener(this);
        bCalender.setOnClickListener(this);
        bLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSearch:

                break;
            case R.id.bLogout:

                break;
            case R.id.bCalender:

                break;
            case R.id.bBack:

                break;


        }
    }
}