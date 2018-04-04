package com.example.usp05.githubtry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by minh on 3/24/18.
 */

public class Register extends Activity {
    DatabaseHelper helper = new DatabaseHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void onRegisterClick(View view) {
        if(view.getId() == R.id.registerButton) {
            EditText username = (EditText)findViewById(R.id.usernameRegister);
            EditText password = (EditText)findViewById(R.id.passwordRegister);
            EditText password2 = (EditText)findViewById(R.id.password2Register);
            EditText secQuestion1 = (EditText)findViewById(R.id.secQuestion1);
            EditText secQuestion2 = (EditText)findViewById(R.id.secQuestion2);
            EditText secQuestion3 = (EditText)findViewById(R.id.secQuestion3);

            String usernameStr = username.getText().toString();
            String passwordStr = password.getText().toString();
            String password2Str = password2.getText().toString();
            String secQuestion1Str = secQuestion1.getText().toString();
            String secQuestion2Str = secQuestion2.getText().toString();
            String secQuestion3Str = secQuestion3.getText().toString();

            String searchUsername = helper.searchUsername(usernameStr);

            if(searchUsername.equals("found")) {
                Toast message = Toast.makeText(Register.this, "Username is already taken!", Toast.LENGTH_SHORT);
                message.show();
            }
            else if(usernameStr.equals("") || password.equals("") || password2.equals("") ||
                    secQuestion1Str.equals("") || secQuestion2Str.equals("") || secQuestion3Str.equals("")) {
                Toast message = Toast.makeText(Register.this, "Entries cannot be empty!", Toast.LENGTH_SHORT);
                message.show();
            }
            else if (!(passwordStr.equals(password2Str))) {
                Toast message = Toast.makeText(Register.this, "Passwords are not the same!", Toast.LENGTH_SHORT);
                message.show();
            }
            else {
                // insert details into database
                User a = new User(usernameStr, passwordStr, password2Str, secQuestion1Str, secQuestion2Str, secQuestion3Str);
//                a.setUsername(usernameStr);
//                a.setPassword(passwordStr);
//                a.setPassword2(password2Str);
//                a.setSecQuestion1(secQuestion1Str);
//                a.setSecQuestion2(secQuestion2Str);
//                a.setSecQuestion3(secQuestion3Str);
                helper.insertUser(a);
                Toast message = Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT);
                message.show();
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
            }
        }
    }
}
