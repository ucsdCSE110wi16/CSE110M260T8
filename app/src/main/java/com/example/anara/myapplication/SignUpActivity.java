package com.example.anara.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.firebase.client.*;

import java.util.ArrayList;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity{

    private View mProgressView;
    private View mSignUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        mProgressView = findViewById(R.id.signup_progress);
        mSignUpView = findViewById(R.id.signup_form);/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); blarg.*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void submit(View button) {
        View focusView = null;
        boolean cancel = false;
        EditText etPass = (EditText) findViewById(R.id.EditTextPass);
        EditText etPass1 = (EditText) findViewById(R.id.EditTextPass1);
        EditText etEmail = (EditText) findViewById(R.id.EditTextEmail);
        String password = etPass.getText().toString();
        String passConfirm = etPass1.getText().toString();
        final String email = etEmail.getText().toString();

        etPass.setError(null);
        etPass1.setError(null);
        etEmail.setError(null);

        if (TextUtils.isEmpty(password)) {
            etPass.setError(getString(R.string.error_field_required));
            focusView = etPass;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPass.setError(getString(R.string.error_invalid_password));
            focusView = etPass;
            cancel = true;
        }else if(!passConfirm.equals(password)){
            etPass.setError(getString(R.string.error_unconfirmed_password));
            focusView = etPass;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            final Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com");
            ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    Firebase userListRef = new Firebase("https://burning-fire-7007.firebaseio.com/user");
                    User stud = new User();
                    stud.setEmail(email);
                    stud.setUserId(ref.getAuth().getUid());
                    userListRef.child(stud.getUserId()).setValue(stud);
                    finish();
                    Intent intent = new Intent(SignUpActivity.this, uHomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Log.e("SignUpActivity", firebaseError.getMessage());
                }
            });

            finish();
            Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpView.setVisibility(show ? View.GONE : View.VISIBLE);
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
