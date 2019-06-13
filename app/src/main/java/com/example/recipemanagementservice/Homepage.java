package com.example.recipemanagementservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homepage extends AppCompatActivity implements View.OnClickListener {

    Button bYeniYemekTarifi;


    TextView homePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        homePage = (TextView) findViewById(R.id.homePage);

        bYeniYemekTarifi = (Button) findViewById(R.id.bYeniYemekTarifi);

        bYeniYemekTarifi.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
             case R.id.bYeniYemekTarifi:
                startActivity(new Intent(this, RecipeAdding.class));
                break;
        }
    }
}
