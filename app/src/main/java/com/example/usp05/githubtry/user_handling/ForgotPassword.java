package com.example.usp05.githubtry.user_handling;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usp05.githubtry.R;

/**
 * Created by minh on 4/25/18.
 */

public class ForgotPassword extends Activity {
    private final DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
    }

    public void onChangePasswordClick(View view) {
        if(view.getId() == R.id.fp_changePassword) {
            String username = ((EditText) findViewById(R.id.fp_username)).getText().toString();
            String secQuestion1 = ((EditText) findViewById(R.id.fp_secQues1)).getText().toString();
            String secQuestion2 = ((EditText) findViewById(R.id.fp_secQues2)).getText().toString();
            String secQuestion3 = ((EditText) findViewById(R.id.fp_secQues3)).getText().toString();

            // checks to make sure fields are not empty
            if(username.isEmpty()|| secQuestion1.isEmpty()|| secQuestion2.isEmpty() || secQuestion3.isEmpty()) {
                Toast message = Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT);
                message.show();
            }
            else {
                // checks that user exists in database before attempting to change password

                if(helper.searchUsername(username).equals("not found")) {
                    Toast message = Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT);
                    message.show();
                }
                else {
                    // gets sec answers from the database to compare
                    Cursor cursor = helper.getSecAnswers(username);
                    String secAnswer1 = "", secAnswer2 = "", secAnswer3 = "";
                    if(cursor.moveToFirst()) {
                        do {
                            secAnswer1 = cursor.getString(0);
                            secAnswer2 = cursor.getString(1);
                            secAnswer3 = cursor.getString(2);
                        } while ( cursor.moveToNext());
                    }
                    cursor.close();

                    // checks to make sure answers to security question are correct
                    if(!(secAnswer1.equals(secQuestion1) && secAnswer2.equals(secQuestion2)
                            && secAnswer3.equals(secQuestion3))) {
                        Toast message = Toast.makeText(this, "Answers to security questions are incorrect!", Toast.LENGTH_SHORT);
                        message.show();
                    }
                    else {
                        Intent intent = new Intent(this, ChangePassword.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                }
            }
        }
    }

}
