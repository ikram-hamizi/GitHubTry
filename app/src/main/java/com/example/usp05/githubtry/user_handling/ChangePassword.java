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
 * Created by minh on 4/25/18.
 */

public class ChangePassword extends Activity {
    private final DatabaseHelper helper = new DatabaseHelper(this);
    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        username = getIntent().getStringExtra("username");
    }

    public void onCPSave(View view) {
        if(view.getId() == R.id.cp_save) {
            String password = ((EditText) findViewById(R.id.cp_password)).getText().toString();
            String password2 = ((EditText) findViewById(R.id.cp_password2)).getText().toString();

            if(password.length() == 0 || password2.length() == 0) {
                Toast message = Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT);
                message.show();
            }
            else if(!password.equals(password2)) {
                Toast message = Toast.makeText(this, "Passwords are not the same", Toast.LENGTH_SHORT);
                message.show();
            }
            else {
                helper.updatePassword(username, password);
                Toast message = Toast.makeText(this, "Password Update Successful", Toast.LENGTH_SHORT);
                message.show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}
