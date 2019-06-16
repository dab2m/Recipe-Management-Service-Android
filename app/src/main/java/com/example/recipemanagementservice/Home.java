package com.example.recipemanagementservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
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

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePage = (TextView) findViewById(R.id.homePage);

        bYeniYemekTarifi = (Button) findViewById(R.id.bYeniYemekTarifi);

        bYeniYemekTarifi.setOnClickListener(this);

        recyclerView=(RecyclerView)findViewById(R.id.recylerview);
        FoodAdapter foodAdapter=new FoodAdapter(this, Food.getData());
        recyclerView.setAdapter(foodAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.goHomepage:
                startActivity(new Intent(this, Home.class));
                break;
            case R.id.goRecipeAdding:
                startActivity(new Intent(this, RecipeAdding.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
