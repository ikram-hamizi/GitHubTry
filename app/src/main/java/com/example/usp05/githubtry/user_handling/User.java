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

    public User(){}

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword2() {
        return password2;
    }

    void setPassword2(String password2) {
        this.password2 = password2;
    }

    String getSecQuestion1() {
        return secQuestion1;
    }

    void setSecQuestion1(String secQuestion1) {
        this.secQuestion1 = secQuestion1;
    }

    String getSecQuestion2() {
        return secQuestion2;
    }

    void setSecQuestion2(String secQuestion2) {
        this.secQuestion2 = secQuestion2;
    }

    String getSecQuestion3() {
        return secQuestion3;
    }

    void setSecQuestion3(String secQuestion3) {
        this.secQuestion3 = secQuestion3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }
}
