package com.example.anara.myapplication;

/**
 * Created by anara on 2/7/2016.
 */
public class User {
    String name;
    String password;
    CharSequence email;
    String id;

    public String getUserName() { return name; }

    public void setUserName(String name) { this.name = name; }

    public CharSequence getEmail() { return email; }

    public void setEmail(CharSequence email) { this.email = email;}

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUserId() {return id; }

    public void setUserId( String id) { this.id = id; }

    public User(){

    }
}