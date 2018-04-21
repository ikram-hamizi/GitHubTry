package com.example.usp05.githubtry.user_handling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usp05.githubtry.MainActivity;
import com.example.usp05.githubtry.R;

/**
 * Created by minh on 3/24/18.
 */

public class Register extends Activity {
    private UserDatabaseSingleton UDS = UserDatabaseSingleton.getInstance(this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void onRegisterClick(View view) {
        if(view.getId() == R.id.registerButton) {
            EditText username = findViewById(R.id.usernameRegister);
            EditText password = findViewById(R.id.passwordRegister);
            EditText password2 = findViewById(R.id.password2Register);
            EditText secQuestion1 = findViewById(R.id.secQuestion1);
            EditText secQuestion2 = findViewById(R.id.secQuestion2);
            EditText secQuestion3 = findViewById(R.id.secQuestion3);

            String usernameStr = username.getText().toString();
            String passwordStr = password.getText().toString();
            String password2Str = password2.getText().toString();
            String secQuestion1Str = secQuestion1.getText().toString();
            String secQuestion2Str = secQuestion2.getText().toString();
            String secQuestion3Str = secQuestion3.getText().toString();

            if(UDS.checkUser(usernameStr)) {
                Toast message = Toast.makeText(this, R.string.badUsername, Toast.LENGTH_SHORT);
                message.show();
            }
            else if(((usernameStr != null) && usernameStr.isEmpty()) || "".equals(password) || "".equals(password2) ||
                    ((secQuestion1Str != null) && secQuestion1Str.isEmpty()) || ((secQuestion2Str != null) && secQuestion2Str.isEmpty()) || ((secQuestion3Str != null) && secQuestion3Str.isEmpty())) {
                Toast message = Toast.makeText(this, R.string.emptyRegistrationFields, Toast.LENGTH_SHORT);
                message.show();
            }
            else if (!(passwordStr.equals(password2Str))) {
                Toast message = Toast.makeText(this, R.string.inequalRegistrationPasswords, Toast.LENGTH_SHORT);
                message.show();
            }
            else {
                // insert details into database
                User newUser = new User();
                newUser.setUsername(usernameStr);
                newUser.setPassword(passwordStr);
                newUser.setSecurityAnswer1(secQuestion1Str);
                newUser.setSecurityAnswer2(secQuestion2Str);
                newUser.setSecurityAnswer3(secQuestion3Str);

                UDS.createUser(newUser);

                Toast message = Toast.makeText(this, R.string.goodRegistration, Toast.LENGTH_SHORT);
                message.show();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }
    }
}
