package com.example.recipemanagementservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Created by mustafatozluoglu on 10.06.2019
 */
public class Home extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    TextView homePage;
    Button bYeniYemekTarifi;
    SearchView yemekArama;
    ListView yemekListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
