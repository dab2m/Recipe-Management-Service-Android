package com.example.recipemanagementservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mustafatozluoglu on 5.06.2019
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

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
                    prefs.edit().putString("username", user).commit(); // MyRecipes sayfasina username'i gecirmek icin kullanildi.
                    Intent i = new Intent(this, MyRecipes.class);

                    startActivity(new Intent(this, Home.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Username and Password do not match!", Toast.LENGTH_LONG).show();
                }

                etUsername.setText("");
                etPassword.setText("");

                break;
            case R.id.bRegister:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    public String getUsername(){
        return user;
    }

    public void setUsername(String username){
        this.user = username;
    }


}
