package com.example.anara.myapplication;

/*
 * Created by anara on 2/7/2016.
 */
public class User {
    String password;
    String email;
    String id;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUserId() {return id; }

    public void setUserId( String id) { this.id = id; }

    public User(){

    }
}
