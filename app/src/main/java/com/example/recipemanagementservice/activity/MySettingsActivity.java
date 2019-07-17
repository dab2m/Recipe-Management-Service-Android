package com.example.recipemanagementservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipemanagementservice.R;

/**
 * Created by mustafatozluoglu on 06.07.2019
 */
public class MySettingsActivity extends AppCompatActivity {

    Button btOnayla;
    EditText et_n_gun;
    int n_gun = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        btOnayla = (Button) findViewById(R.id.btOnayla);
        et_n_gun = (EditText) findViewById(R.id.et_n_gun);
        btOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_n_gun.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please fill the field!", Toast.LENGTH_LONG).show();
                else
                    n_gun = Integer.parseInt(String.valueOf(et_n_gun.getText()));
            }
        });
    }

    public int getN_gun() {
        return n_gun;
    }
}