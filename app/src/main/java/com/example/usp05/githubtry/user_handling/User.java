package com.example.usp05.githubtry.user_handling;

/**
 * Created by minh on 3/24/18.
 */

public class User {
    private String username;
    private String password;
    private String password2;
    private String secQuestion1;
    private String secQuestion2;
    private String secQuestion3;

    public User(String username, String password, String password2, String secQuestion1, String secQuestion2, String secQuestion3) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.secQuestion1 = secQuestion1;
        this.secQuestion2 = secQuestion2;
        this.secQuestion3 = secQuestion3;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    private void setUsername(String username) {
//        this.username = username;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getUsername() {
        return username;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    private void setPassword(String password) {
//        this.password = password;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getPassword() {
        return password;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    private void setPassword2(String password2) {
//        this.password2 = password2;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getPassword2() {
        return password2;
    }

    // --Commented out by Inspection (4/16/18 9:54 PM):private void setSecQuestion1(String secQuestion1) { this.secQuestion1 = secQuestion1; }

    public String getSecQuestion1() {
        return secQuestion1;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    private void setSecQuestion2(String secQuestion2) {
//        this.secQuestion2 = secQuestion2;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getSecQuestion2() {
        return secQuestion2;
    }

// --Commented out by Inspection START (4/16/18 9:54 PM):
//    private void setSecQuestion3(String secQuestion3) {
//        this.secQuestion3 = secQuestion3;
//    }
// --Commented out by Inspection STOP (4/16/18 9:54 PM)

    public String getSecQuestion3() {
        return secQuestion3;
    }
}
