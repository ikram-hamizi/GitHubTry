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

            String username = ((EditText) findViewById(R.id.usernameRegister)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwordRegister)).getText().toString();
            String password2 = ((EditText) findViewById(R.id.password2Register)).getText().toString();
            String secQuestion1 = ((EditText) findViewById(R.id.secQuestion1)).getText().toString();
            String secQuestion2 = ((EditText) findViewById(R.id.secQuestion2)).getText().toString();
            String secQuestion3 = ((EditText) findViewById(R.id.secQuestion3)).getText().toString();

            if(UDS.checkUser(username)) {
                Toast message = Toast.makeText(this, R.string.badUsername, Toast.LENGTH_SHORT);
                message.show();
            }
            else if(username.isEmpty() || password.isEmpty() || password2.isEmpty() || secQuestion1.isEmpty()
                    || secQuestion2.isEmpty() || secQuestion3.isEmpty()) {
                Toast message = Toast.makeText(this, R.string.emptyRegistrationFields, Toast.LENGTH_SHORT);
                message.show();
            }
            else if (!(password.equals(password2))) {
                Toast message = Toast.makeText(this, R.string.inequalRegistrationPasswords, Toast.LENGTH_SHORT);
                message.show();
            }
            else {
                // insert details into database
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setSecurityAnswer1(secQuestion1);
                newUser.setSecurityAnswer2(secQuestion2);
                newUser.setSecurityAnswer3(secQuestion3);

                UDS.createUser(newUser);

                Toast message = Toast.makeText(this, R.string.goodRegistration, Toast.LENGTH_SHORT);
                message.show();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }
    }
}
