package com.example.recipemanagementservice.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipemanagementservice.R;
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

                    ApiAuthenticationClient apiAuthenticationClient = new ApiAuthenticationClient(username, password);
                    AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(apiAuthenticationClient);
                    execute.execute();
                }
                break;
        }
    }

    protected class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String isValidCredentials;

        public ExecuteNetworkOperation(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                isValidCredentials = apiAuthenticationClient.executeForRegister();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Register Success
            if (isValidCredentials.contains("Success")) {
                Toast.makeText(getApplicationContext(), "Register is succesful", Toast.LENGTH_LONG).show();
                etUsername.setText("");
                etPassword.setText("");
            }
            // Register Failure
            else {
                Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}