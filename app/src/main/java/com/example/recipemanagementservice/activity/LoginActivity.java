package com.example.recipemanagementservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.network.ApiAuthenticationClient;
import com.example.recipemanagementservice.firebase.MyFirebaseInstanceIdService;
import com.example.recipemanagementservice.firebase.MyFirebaseMessagingService;

/**
 * Created by mustafatozluoglu on 5.06.2019
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
        MyFirebaseMessagingService m = new MyFirebaseMessagingService();
        final MyFirebaseInstanceIdService m2 = new MyFirebaseInstanceIdService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                m2.onTokenRefresh();
            }
        });
        thread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                user = etUsername.getText().toString();
                pass = etPassword.getText().toString();
                etUsername.setText("");
                etPassword.setText("");
                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
                    prefs.edit().putString("username", user).apply(); // MyRecipesActivity sayfasina username'i gecirmek icin kullanildi.
                    ApiAuthenticationClient apiAuthenticationClient = new ApiAuthenticationClient(user, pass);
                    AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(apiAuthenticationClient);
                    execute.execute();
                    etUsername.setText("");
                    etPassword.setText("");
                }
                break;
            case R.id.bRegister:
                startActivity(new Intent(this, RegisterActivity.class));
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
                isValidCredentials = apiAuthenticationClient.executeForLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Login Success
            if (isValidCredentials.contains("Success")) {
                goToHomeActivity();
            }
            // Login Failure
            else {
                Toast.makeText(getApplicationContext(), "Login Is Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void goToHomeActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("username", user);
        bundle.putString("password", pass);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}