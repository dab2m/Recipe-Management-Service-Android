package com.example.recipemanagementservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.database.DatabaseHelper;
import com.example.recipemanagementservice.model.UserModel;

/**
 * Created by mustafatozluoglu on 5.06.2019
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                } else {
                    UserModel user = new UserModel(username, password);
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    if (db.searchPass(username) == null) {
                        db.insertUserData(user);
                        etUsername.setText("");
                        etPassword.setText("");
                        Toast.makeText(getApplicationContext(), "RegisterActivity is successful", Toast.LENGTH_LONG).show();
                    } else {
                        etPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Username must be unique!", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}