package com.example.recipemanagementservice.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipemanagementservice.R;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Berk on 24.07.2019
 */
public class MySettingsActivity extends AppCompatActivity {

    Button btOnayla;
    EditText etNday;
    private int nDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        btOnayla = (Button) findViewById(R.id.btOnayla);
        etNday = (EditText) findViewById(R.id.et_n_gun);
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE); // LoginActivity sayfasindan username'i almak icin kullanildi!
        final String username = prefs.getString("username", "UNKNOWN");
        btOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNday.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please fill the field!", Toast.LENGTH_LONG).show();
                else {
                    nDay = Integer.parseInt(String.valueOf(etNday.getText()));
                    etNday.setText("");
                }
                postNdaysDelete(nDay+"", username);
            }
        });
    }

    public void postNdaysDelete(final String nDay, final String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://recipemanagementservice495.herokuapp.com/post.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ngun", nDay);
                    jsonParam.put("username", username);
                    Log.i("JSON", jsonParam.toString());
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}