package com.example.recipemanagementservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.database.DatabaseHelper;

/**
 * Created by mustafatozluoglu on 5.06.2019
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);
    Button bLogin;
    Button bRegister;
    EditText etUsername;
    EditText etPassword;
    private String user;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                user = etUsername.getText().toString();
                pass = etPassword.getText().toString();
                String password = helper.searchPass(user);
                if (pass.equals(password)) {
                    SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
                    prefs.edit().putString("username", user).commit(); // MyRecipesActivity sayfasina username'i gecirmek icin kullanildi.
                    Intent i = new Intent(this, MyRecipesActivity.class);
                    startActivity(new Intent(this, HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Username and Password do not match!", Toast.LENGTH_LONG).show();
                }
                etUsername.setText("");
                etPassword.setText("");
                break;
            case R.id.bRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}