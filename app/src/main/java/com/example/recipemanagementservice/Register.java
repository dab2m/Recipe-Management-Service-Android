package com.example.recipemanagementservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mustafatozluoglu on 5.06.2019
 */
public class Register extends AppCompatActivity implements View.OnClickListener {

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
                    //unique username olup olmadigini kontrol et
                    User user = new User(username, password);
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.insertData(user);
                    etUsername.setText("");
                    etPassword.setText("");

                    Toast.makeText(getApplicationContext(), "Register is succesful", Toast.LENGTH_LONG).show();

                }

                break;
        }
    }
}
