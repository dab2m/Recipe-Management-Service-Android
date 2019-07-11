package com.example.recipemanagementservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mustafatozluoglu on 10.06.2019
 */
public class Home extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    JSONParser jsonParser;
    ProgressDialog progressDialog;
    FoodAdapterForHome foodAdapter;

    private static String homepageURL = "http://recipemanagementservice495.herokuapp.com/rest.php?list";

    Button bYeniYemekTarifi;
    Button bTariflerim;
    Button bAyarlar;
    Button bBildirim;
    Button bBegen;
    SearchView yemekArama; // TODO search ozelligi eklenecek
    ListView yemekListesi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        yemekListesi = (ListView) findViewById(R.id.yemekListesi);

        new getRecipe().execute();


        bYeniYemekTarifi = (Button) findViewById(R.id.bYeniYemekTarifi);
        bTariflerim = (Button) findViewById(R.id.bTariflerim);
        bAyarlar = (Button) findViewById(R.id.bAyarlar);
        bBildirim = (Button) findViewById(R.id.bBildirim);
        bBegen = (Button) findViewById(R.id.bBegen);


        bYeniYemekTarifi.setOnClickListener(this);
        bTariflerim.setOnClickListener(this);
        bAyarlar.setOnClickListener(this);
        bBildirim.setOnClickListener(this);
        //bBegen.setOnClickListener(this); //TODO begen butonuna null geliyor yorumdan cikarinca program patliyor.

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
            case R.id.bAyarlar:
                startActivity(new Intent(this, MySettings.class));
                break;
            case R.id.bBildirim:
                startActivity(new Intent(this, MyNotification.class));
                break;
            case R.id.bBegen:
                // TODO begenme islemi yapilacak
                System.out.println("liked.");
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

    private class getRecipe extends AsyncTask<Void, Void, Void> {

        ArrayList<Food> recipeArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Home.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            foodAdapter = new FoodAdapterForHome(Home.this, recipeArrayList);

            if (progressDialog.isShowing()) {
                yemekListesi.setAdapter(foodAdapter);
                progressDialog.dismiss();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonParser = new JSONParser();
            String jsonString = null;
            try {
                jsonString = jsonParser.makeServiceCall(homepageURL);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.d("JSON_RESPONSE", jsonString);

            if (jsonString != null) {


                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray recipes = jsonObject.getJSONArray("Recipes");

                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject recipe = recipes.getJSONObject(i);
                        String recipeId = recipe.getString("recipeId");
                        String recipeName = recipe.getString("recipeName");
                        String recipeImage = recipe.getString("recipeImage");
                        String recipeDescription = recipe.getString("recipeDescription");
                        JSONArray recipeTagsArray = recipe.getJSONArray("recipeTags");
                        StringBuilder recipeTags = new StringBuilder();
                        for (int j = 0; j < recipeTagsArray.length(); j++)
                            recipeTags.append(recipeTagsArray.getString(j)).append(", ");
                        recipeTags = new StringBuilder(recipeTags.substring(0, recipeTags.length() - 2));
                        String created = recipe.getString("created");
                        String recipeDate = recipe.getString("recipeDate");
                        int likes = recipe.getInt("likes");

                        Food food = new Food(recipeId, recipeName, recipeImage, recipeDescription, recipeTags.toString(), created, recipeDate, likes);
                        recipeArrayList.add(food);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.d("JSON_RESPONSE", "Empty page resource!");
            }
            return null;
        }
    }

}
