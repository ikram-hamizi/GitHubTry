package com.example.usp05.githubtry;

/**
 * Created by minh on 3/24/18.
 */

public class User {
    private String username, password, password2, secQuestion1, secQuestion2, secQuestion3;

    public User(String username, String password, String password2, String secQuestion1, String secQuestion2, String secQuestion3) {
        setUsername(username);
        setPassword(password);
        setPassword2(password2);
        setSecQuestion1(secQuestion1);
        setSecQuestion2(secQuestion2);
        setSecQuestion3(secQuestion3);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }


    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword2() {
        return this.password2;
    }


    public void setSecQuestion1(String secQuestion1) { this.secQuestion1 = secQuestion1; }

    public String getSecQuestion1() {
        return this.secQuestion1;
    }


    public void setSecQuestion2(String secQuestion2) {
        this.secQuestion2 = secQuestion2;
    }

    public String getSecQuestion2() {
        return this.secQuestion2;
    }


    public void setSecQuestion3(String secQuestion3) {
        this.secQuestion3 = secQuestion3;
    }

    public String getSecQuestion3() {
        return this.secQuestion3;
    }
}
