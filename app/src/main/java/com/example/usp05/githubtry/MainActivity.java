package com.example.usp05.githubtry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usp05.githubtry.inventory_display.InventoryActivity;
import com.example.usp05.githubtry.user_handling.UserDatabaseHelper;
import com.example.usp05.githubtry.user_handling.Register;

public class MainActivity extends AppCompatActivity {
    private final UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginClick(View view) {
        if((view.getId() == R.id.loginButton)) {
            EditText username = findViewById(R.id.usernameLogin);
            EditText password = findViewById(R.id.passwordLogin);
            String usernameStr = username.getText().toString();
            String passwordStr = password.getText().toString();

            if(usernameStr.isEmpty()) {
                username.setError("Enter username");
            }
            else if(passwordStr.isEmpty()) {
                password.setError("Enter password");
            }
            else if("not found".equals(helper.searchUsernameAndPassword(usernameStr, passwordStr))) {
                Toast message = Toast.makeText(this, "Incorrect Username and Password!", Toast.LENGTH_SHORT);
                message.show();
            }
            else {
//                Intent i = new Intent(MainActivity.this, Inventory.class);
                Intent i = new Intent(this, InventoryActivity.class);
                i.putExtra("username", usernameStr);
                startActivity(i);
            }
        }
    }

    public void onSignUpClick(View view) {
        if(view.getId() == R.id.signUpButton) {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        }
    }
}
