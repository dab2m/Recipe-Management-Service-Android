package com.example.recipemanagementservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by mustafatozluoglu on 10.06.2019
 */
public class Home extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    Button bYeniYemekTarifi;
    Button bTariflerim;
    SearchView yemekArama;
    ListView yemekListesi;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bYeniYemekTarifi = (Button) findViewById(R.id.bYeniYemekTarifi);
        bTariflerim = (Button) findViewById(R.id.bTariflerim);


        bYeniYemekTarifi.setOnClickListener(this);
        bTariflerim.setOnClickListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        FoodAdapter foodAdapter = new FoodAdapter(this, Food.getData(), 1);
        recyclerView.setAdapter(foodAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bYeniYemekTarifi:
                startActivity(new Intent(this, RecipeAdding.class));
                break;
            case R.id.bTariflerim:
                startActivity(new Intent(this, MyRecipes.class));
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
