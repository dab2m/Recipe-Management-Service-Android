package com.example.recipemanagementservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mustafatozluoglu on 5.06.2019
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button bLogin;
    EditText etUsername;
    EditText etPassword;
    TextView loginPage;
    TextView tvRegisterLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        loginPage = (TextView) findViewById(R.id.loginPage);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                String password = helper.searchPass(user);

                if (pass.equals(password)) {
                    startActivity(new Intent(this, Homepage.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Username and Password do not match!", Toast.LENGTH_LONG).show();
                }

                etUsername.setText("");
                etPassword.setText("");

                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }


}
